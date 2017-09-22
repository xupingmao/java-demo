package com.xpm.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * Created by xupingmao on 2017/9/22.
 */
public class JdbcMain {

    public static void main(String[] args) throws SQLException {
        DBTools tool = new DBTools();
        ResultSet rs = tool.executeQuery("SELECT id, name FROM b_user limit 10");
        try {
            tool.printColumnDef(rs);
            while (rs.next()) {
                tool.printResultSet(rs);
            }
        } finally {
            tool.close();
        }
    }
}
