package tasks

import contributors.MockGithubService
import contributors.expectedResults
import contributors.testRequestData
import kotlin.test.Test
import kotlin.test.assertEquals

class Request1BlockingKtTest {
    @Test
    fun testAggregation() {
        val users = loadContributorsBlocking(MockGithubService, testRequestData)
        assertEquals(
            expectedResults.users,
            users,
            "List of contributors should be sorted " +
                "by the number of contributions in a descending order",
        )
    }
}
