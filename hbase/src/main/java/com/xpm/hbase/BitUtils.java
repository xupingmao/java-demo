package com.xpm.hbase;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * @see org.apache.hadoop.hbase.util.Bytes
 * Created by xupingmao on 2017/9/13.
 */
public class BitUtils {

    private static Charset UTF8 = Charset.forName("UTF-8");

    public static String toString(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        return new String(bytes, UTF8);
    }

    public static Long toLong(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        return ByteBuffer.wrap(bytes).getLong();
    }

    public static Integer toInt(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        return ByteBuffer.wrap(bytes).getInt();
    }

    public static Float toFloat(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        return ByteBuffer.wrap(bytes).getFloat();
    }

    public static Double toDouble(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        return ByteBuffer.wrap(bytes).getDouble();
    }

    public static byte[] fromLong(long value) {
        return ByteBuffer.allocate(8).putLong(value).array();
    }

    public static byte[] fromInt(int value) {
        return ByteBuffer.allocate(4).putInt(value).array();
    }

    public static byte[] fromFloat(float value) {
        return ByteBuffer.allocate(4).putFloat(value).array();
    }

    public static byte[] fromDouble(double value) {
        return ByteBuffer.allocate(8).putDouble(value).array();
    }
}
