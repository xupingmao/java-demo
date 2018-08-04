package com.xpm.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by xupingmao on 2017/9/22.
 */
public class PartitionQueryMain {


    public static void main(String[] args) throws SQLException {
        String partitionPrefix = "dzj.mq_buffer_partition_";
        int partitionStart = 1;
        int partitionEnd = 10;
        String bizKey = "2017092059C26A53E4B022BBB4C5F0C1";

        DBUtils tool = new DBUtils("jdbc:mysql://192.168.0.173:3306/dzj");

        try {
            for (int i = partitionStart; i <= partitionEnd; i++) {
                String sql = String.format("SELECT * FROM %s WHERE biz_key = ? LIMIT 10", partitionPrefix+i);
                System.out.println(sql);
                System.out.println("==========================");
                ResultSet rs = tool.executeQuery(sql, bizKey);
                while (rs.next()) {
                    DBUtils.printResultSet(rs);
                }

            }
        } finally {
            tool.close();
        }
    }
}
