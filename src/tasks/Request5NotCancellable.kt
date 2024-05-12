package tasks

import contributors.*
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

suspend fun loadContributorsNotCancellable(
    service: GitHubService,
    req: RequestData,
): List<User> {
    val deferreds: MutableList<Deferred<List<User>>> = mutableListOf()
    GlobalScope.async {
        val repos =
            service
                .getOrgRepos(req.org)
                .also { logRepos(req, it) }
                .body() ?: emptyList()

        repos.map { repo ->
            log("starting loading for ${repo.name}")
            val deferred =
                async {
                    service
                        .getRepoContributors(req.org, repo.name)
                        .also { logUsers(repo, it) }
                        .bodyList()
                }
            deferreds.add(deferred)
        }
    }
    return deferreds.awaitAll().flatten().aggregate()
}
