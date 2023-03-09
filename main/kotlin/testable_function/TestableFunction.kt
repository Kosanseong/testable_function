package testable_function

import java.time.DayOfWeek
import java.time.LocalDateTime

class TestableFunction(
    amount: Double,
) {
    private var _amount = amount

    /**
     * 해당 코드는 테스트 하기 어려운 코드이다.
     * 왜냐하면, LocalDateTime이라는 내부 라이브러리를 사용해서 now 변수를 우리가 컨트롤하지 못하기 때문이다.
     * 이코드는 개발자가 테스트 돌리는 시점마다 테스트 결과가 달라지는 함수이다.
     * 이 말은 언제나 동일한 결과를 반환해주지 못하는 멱등성이 보장되지 않는 함수로 좋지 않다.
     */
    fun discount(): Double {
        val now = LocalDateTime.now()

        if(now.dayOfWeek == DayOfWeek.SUNDAY) {
            this._amount = this._amount * 0.9
        }

        return _amount
    }


    /**
     * 코틀린은 인자 기본값을 지원한다.
     * 즉 인자를 넣어줄 때(테스트 코드를 짤 때)는 내가 원하는 값을 넣고
     * 인자를 넣지 않을 때(런타임에서는 now여야 할때, 다른 클래스에 의존성을 미루고 싶지 않을때)는 기본값으로 세팅한 값이 들어간다.
     * 이렇게하면 멱등성이 보장되기 때문에 테스트하기 한결 수월하진 코드가 된다.
     */
    fun refactoredDiscount(now: DayOfWeek = LocalDateTime.now().dayOfWeek): Double {
        if(now == DayOfWeek.SUNDAY) {
            this._amount = this._amount * 0.9
        }

        return _amount
    }
}
/**
 * 추가적으로, 향로님의 블로그를 보고 만든 코드이다.
 * 다만 내가 궁금했던 것은 저렇게 인자를 기본값으로 못하는 언어들은 어떡할 것인가
 * 실무할 때 이 고민을 많이 해보았지만, 떠오르진 않앗다
 * 블로그에선 이런 의존성 있는 코드는 미룰 수 있는데까지 미루는 것도 방법이라 하셨다.
 * 하지만 이 방식은 별로 좋지 않다라고 생각하였는데 그 이유는
 * 첫번째로 블로그에서 나온대로 Controller 테스트가 결국 테스트를 하기 좋지 못한 코드가 된다는 점
 * 두번째는 다른 레이어에서 필요하지 않은 인자를 계속해서 넘겨줘야 했던점
 * 이 두가지였다.
 * 왜냐하면 전 직장에서 내가 받았던 코드는 Controller - UserTypeService - Handler - Service(실제 now가 사용되는 시점) - Repository 로 레이어가 구성 되어 있었는데
 * 레이어가 너무 길어서 실제 해당 now를 사용하는 코드까지 now를 들고 운반해야하는 상황이 의미 없는 인자가 계속 들어가 있는 사실이 너무 마음에 안들었다.
 * 그렇기 때문에, 나는 어느정도 트레이드 오프를 감수한 상태로 코드를 짤 수 밖에 없었다. (그런 상황이 테스트 코드에 그대로 보여지기도 했지만... 그때 당시 나의 수준에선 컨트롤러까지 올리는 것 말고는 따로 방법이 생각이 나지 않았다...)
 * 블로그에서 말씀하신대로 해당 코드의 의존성을 외부에서 주입해주고 테스트를 위한
 * 테스트 더블 객체를 만들어주는 함수를 따로 두면 된다고 나와있다.(테스트 더블과 해당 방법을 처음 알았다.)
 * 난 자바보단 코틀린을 더 좋아하여 기본 인자를 넘기겠지만, 의존성 주입이라는 것이 머리에 남기 때문에 다른 곳에서도 사용할 수 있을 것 같다.
 */