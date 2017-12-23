import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by xupingmao on 2017/6/27.
 */
public class BigDecimalTest {

    @Test(expected = ArithmeticException.class)
    public void arithmeticExceptionTest() {
        BigDecimal a = BigDecimal.valueOf(1);
        BigDecimal b = BigDecimal.valueOf(3);
        BigDecimal c = a.divide(b);
        System.out.println(c);
    }

    @Test
    public void bigDecimalScale() {
        BigDecimal a = BigDecimal.valueOf(1);
        BigDecimal b = BigDecimal.valueOf(3);
        BigDecimal bigDecimal = a.divide(b, 2, RoundingMode.HALF_UP);
        System.out.println(bigDecimal);
        Assert.assertEquals("0.33", bigDecimal.toString());

        BigDecimal value = BigDecimal.valueOf(0.555).divide(BigDecimal.valueOf(1), 2, RoundingMode.HALF_UP);
        System.out.println(value);
        Assert.assertEquals("0.56", value.toString());

    }
}
