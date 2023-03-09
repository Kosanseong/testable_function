package testable_function

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.DayOfWeek

class TestableFunctionTest {
    val account: Double = 10.0
    val target = TestableFunction(account)

    @Test
    fun `일요일은 10퍼센트 할인된 가격이어야 한다`() {
        val discountedAccount = target.discount()

        assertEquals(9.0, discountedAccount)
    }

    @Test
    fun `리팩토링 된 일요일엔 10퍼센트 할인된 가격이어야 한다`() {
        val now = DayOfWeek.SUNDAY

        val refactoredDiscountAccount = target.refactoredDiscount(now)

        assertEquals(9.0, refactoredDiscountAccount)
    }
}