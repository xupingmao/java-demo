package com.xpm.jdk;

import org.junit.Assert;
import org.junit.Test;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Formatter;

/**
 * Created by xupingmao on 2017/10/14.
 */
public class DoubleTest {

    public String format(String fmt, Object... args) {
        return new Formatter().format(fmt, args).toString();
    }

    @Test
    public void convertToString() {
        System.out.println(Double.toString(1.0));
        System.out.println(Double.toString(1.01));

        System.out.println(format("%f", 1.0));
        System.out.println(format("%g", 1.0));
        System.out.println(format("%G", 1.0));

        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        String format = decimalFormat.format(1.0);

        Assert.assertEquals(decimalFormat.format(1.0), "1");
        Assert.assertEquals(decimalFormat.format(1.2), "1.2");
        Assert.assertEquals(decimalFormat.format(1.234), "1.2");

        System.out.println(format);
        System.out.println(decimalFormat.format(1.23));
        System.out.println(decimalFormat.format(1234.4));
    }
}
