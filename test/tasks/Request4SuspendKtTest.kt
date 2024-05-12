package tasks

import contributors.MockGithubService
import contributors.expectedResults
import contributors.testRequestData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Request4SuspendKtTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testSuspend() =
        runTest {
            val startTime = System.currentTimeMillis()
            val result = loadContributorsSuspend(MockGithubService, testRequestData)
            assertEquals(
                expectedResults.users,
                result,
                "Wrong result for 'loadContributorsSuspend'",
            )
            val totalTime = currentTime
            assertEquals(
                expectedResults.timeFromStart,
                totalTime,
                "The calls run consequently, so the total virtual time should be 4000 ms: " +
                    "1000 for repos request plus (1000 + 1200 + 800) = 3000 for sequential contributors requests)",
            )
            assertTrue(
                "The calls run consequently, so the total time should be around 4000 ms: " +
                    "1000 for repos request plus (1000 + 1200 + 800) = 3000 for sequential contributors requests)",
            ) {
                totalTime in expectedResults.timeFromStart..(expectedResults.timeFromStart + 500)
            }
        }
}
