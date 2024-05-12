package tasks

import contributors.*

suspend fun loadContributorsProgress(
    service: GitHubService,
    req: RequestData,
    updateResults: suspend (List<User>, completed: Boolean) -> Unit,
) {
    val repos =
        service
            .getOrgRepos(req.org)
            .also { logRepos(req, it) }
            .body() ?: emptyList()

    var all = mutableListOf<User>()
    repos.forEachIndexed { index, repo ->
        all.addAll(
            service
                .getRepoContributors(req.org, repo.name)
                .also { logUsers(repo, it) }
                .bodyList().aggregate(),
        )
        all = all.aggregate().toMutableList()
        updateResults(all.aggregate(), index + 1 == repos.size)
    }
}
