package tasks

import contributors.MockGithubService
import contributors.concurrentProgressResults
import contributors.testRequestData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class Request7ChannelsKtTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testChannels() =
        runTest {
            val startTime = System.currentTimeMillis()
            var index = 0
            loadContributorsChannels(MockGithubService, testRequestData) {
                    users, _ ->
                val expected = concurrentProgressResults[index++]
                val time = currentTime

                assertEquals(
                    expected.timeFromStart,
                    time,
                    "Expected intermediate result after virtual ${expected.timeFromStart} ms:",
                )
                assertEquals(
                    expected.users,
                    users,
                    "Wrong intermediate result after $time:",
                )
            }
        }
}
