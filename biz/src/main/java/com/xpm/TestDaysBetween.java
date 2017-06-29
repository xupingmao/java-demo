package com.xpm;


import org.joda.time.DateTime;
import org.joda.time.Days;

/**
 * Created by xupingmao on 2017/6/10.
 */
public class TestDaysBetween {

    public static void main(String[] args) {
        DateTime dt1 = DateTime.parse("2017-01-01T01:00:00");
        DateTime dt2 = DateTime.parse("2017-01-01T02:00:00");
        Integer days = Days.daysBetween(dt1, dt2).getDays();
        System.out.println(days);

        dt1 = DateTime.parse("2017-01-01T01:00:00");
        dt2 = DateTime.parse("2017-01-01T20:00:00");
        days = Days.daysBetween(dt1, dt2).getDays();
        System.out.println(days);

        dt1 = DateTime.parse("2017-01-01T20:00:00");
        dt2 = DateTime.parse("2017-01-02T01:00:00");
        days = Days.daysBetween(dt1, dt2).getDays();
        System.out.println(days);
    }
}
