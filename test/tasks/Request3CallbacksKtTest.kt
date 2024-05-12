package tasks

import contributors.MockGithubService
import contributors.expectedResults
import contributors.testRequestData
import kotlin.test.Test
import kotlin.test.assertEquals

class Request3CallbacksKtTest {
    @Test
    fun testDataIsLoaded() {
        loadContributorsCallbacks(MockGithubService, testRequestData) {
            assertEquals(
                expectedResults.users,
                it,
                "Wrong result for 'loadContributorsCallbacks'",
            )
        }
    }
}
