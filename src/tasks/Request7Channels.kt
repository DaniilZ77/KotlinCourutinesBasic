package tasks

import contributors.*
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

suspend fun loadContributorsChannels(
    service: GitHubService,
    req: RequestData,
    updateResults: suspend (List<User>, completed: Boolean) -> Unit,
) {
    coroutineScope {
        val repos =
            service
                .getOrgRepos(req.org)
                .also { logRepos(req, it) }
                .body() ?: emptyList()

        val channel = Channel<List<User>>()

        var all = mutableListOf<User>()

        launch {
            repeat(repos.size) {
                all.addAll(channel.receive())
                all = all.aggregate().toMutableList()
                updateResults(all, it + 1 == repos.size)
            }
        }

        repos.map { repo ->
            async {
                channel.send(
                    service
                        .getRepoContributors(req.org, repo.name)
                        .also { logUsers(repo, it) }
                        .bodyList().aggregate(),
                )
            }
        }
    }
}
