package awaitility;

import lombok.extern.slf4j.Slf4j;
import org.awaitility.Awaitility;
import org.awaitility.Durations;
import org.awaitility.core.ConditionEvaluationLogger;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

@Slf4j
public class SimpleTest {

    private int amount = 15;

    public int getAmount(){
        System.out.println(amount--);
        return amount;
    }

    public boolean isAmountLessThan(int expectedAmount){
        System.out.println(amount--);
        return amount < expectedAmount;
    }

    @Test
    public void checkWaiting(){
        Awaitility.await("the amount of cards is not enough")
                .with()
                .conditionEvaluationListener(new ConditionEvaluationLogger(log::info))
                .atMost(7, TimeUnit.SECONDS)
                .pollDelay(1, TimeUnit.SECONDS)
                .pollInterval(1, TimeUnit.SECONDS)
                //.until(() -> isAmountLessThan(10));
                .until(() -> getAmount(), Matchers.lessThan(10));
    }

}
