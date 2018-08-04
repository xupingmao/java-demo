import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.joda.time.DateTimeZone;
import org.junit.Test;

/**
 * Created by xupingmao on 2017/6/14.
 */
public class JodaTimeTest {


    @Test
    public void createdTime() {
        DateTime dt = DateTime.now();
        DateTime dt2 = DateTime.now(DateTimeZone.UTC);
        DateTime parse = DateTime.parse("2017-06-27T20:10");
        DateTime utcTime = new DateTime(dt, DateTimeZone.UTC);
        System.out.println(dt);
        System.out.println(parse);
        System.out.println(dt2);
        System.out.println(utcTime);
    }
}
