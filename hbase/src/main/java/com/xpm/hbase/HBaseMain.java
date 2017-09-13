package com.xpm.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by xupingmao on 2017/9/13.
 */
public class HBaseMain {

    public static final byte[] CF = "cf".getBytes();

    public static void main(String[] args) throws IOException {

        Configuration conf = HBaseConfiguration.create();
        conf.addResource("META-INF/hbase-site.xml");

        Connection connection = ConnectionFactory.createConnection(conf);
        Table table = connection.getTable(TableName.valueOf("test"));

        Get get = new Get("row1".getBytes());
        Result result = table.get(get);
        byte[] value = result.getValue(CF, "a".getBytes());
        System.out.println(BitUtils.toString(value));

        Put put = new Put("row-x".getBytes());
        put.addColumn(CF, "b".getBytes(), "Test".getBytes());
        table.put(put);


        for (int i = 0; i < 10; i++) {
            put = new Put(("row" + i).getBytes());
            put.addColumn(CF, "b".getBytes(), ("Hello,World[" + i + "]").getBytes());
            table.put(put);
            put.getRow();
        }

        // TODO 单行事务

    }
}
