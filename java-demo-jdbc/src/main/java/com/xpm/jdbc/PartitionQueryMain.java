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
        String bizKey = "2017092159C392B4E4B0D14812AF9778";

        DBTools tool = new DBTools();

        try {
            for (int i = partitionStart; i <= partitionEnd; i++) {
                String sql = String.format("SELECT * FROM %s WHERE biz_key = ? LIMIT 10", partitionPrefix+i);
                System.out.println(sql);
                System.out.println("==========================");
                ResultSet rs = tool.executeQuery(sql, bizKey);
                while (rs.next()) {
                    tool.printResultSet(rs);
                }

            }
        } finally {
            tool.close();
        }
    }
}
