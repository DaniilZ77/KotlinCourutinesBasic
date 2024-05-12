package tasks

import contributors.User
import kotlin.test.Test
import kotlin.test.assertEquals

class AggregationKtTest {
    @Test
    fun testAggregation() {
        val actual =
            listOf(
                User("Alice", 1),
                User("Bob", 3),
                User("Alice", 2),
                User("Bob", 7),
                User("Charlie", 3),
                User("Alice", 5),
            ).aggregate()
        val expected =
            listOf(
                User("Bob", 10),
                User("Alice", 8),
                User("Charlie", 3),
            )
        assertEquals(expected, actual, "Wrong result for 'aggregation'")
    }
}
