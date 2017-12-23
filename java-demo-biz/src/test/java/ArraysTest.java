import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by xupingmao on 2017/7/6.
 */
public class ArraysTest {

    @Test
    public void asList() {
        List<Integer> integers = Arrays.asList(1, 2);
        System.out.println(integers.getClass());
    }
}
