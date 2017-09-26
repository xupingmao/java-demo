package com.xpm.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by xupingmao on 2017/9/22.
 */
public class JdbcMain {

    public static void main(String[] args) throws SQLException {
        DBUtils tool = new DBUtils();
        ResultSet rs = tool.executeQuery("SELECT id, name FROM b_user limit 10");
        try {
            tool.printColumnDef(rs);
            while (rs.next()) {
                System.out.println(DBUtils.getColumnList(rs));
            }
        } finally {
            tool.close();
        }
    }
}
