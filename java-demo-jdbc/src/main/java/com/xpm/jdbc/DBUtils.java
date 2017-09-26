package com.xpm.jdbc;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by xupingmao on 2017/9/22.
 */
public class DBUtils {

    private java.sql.Connection connection;
    private java.sql.PreparedStatement preparedStatement;

    private static String URL = "jdbc:mysql://192.168.0.173:3306/dzj";

    public DBUtils() throws SQLException {
        this(URL);
    }

    public DBUtils(String url) throws SQLException {
        connection = DriverManager.getConnection(url, "root", "123456");
        // 自动提交，单条SQL自动加事务，如果使用事务需要关闭autoCommit
        connection.setAutoCommit(true);
    }


    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ResultSet executeQuery(String sql, Object... params) {
        try {
            preparedStatement = connection.prepareStatement(sql);
            if (params != null) {
                int i = 1;
                for (Object p : params) {
                    preparedStatement.setObject(i++, p);
                }
            }
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Object> getColumnList(ResultSet resultSet) throws SQLException {
        List<Object> values = new ArrayList();
        int columnCount = resultSet.getMetaData().getColumnCount();
        for (int i = 0; i < columnCount; i++) {
            Object object = resultSet.getObject(i+1);
            values.add(object);
        }
        return values;
    }


    public static void printResultSet(ResultSet resultSet) throws SQLException {
        int columnCount = resultSet.getMetaData().getColumnCount();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < columnCount; i++) {
            Object object = resultSet.getObject(i+1);
            sb.append(object);
            sb.append("\t");
        }
        System.out.println(sb.toString());
    }

    public static void printColumnDef(ResultSet rs) throws SQLException {
        int columnCount = rs.getMetaData().getColumnCount();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < columnCount; i++) {
            String columnLabel = rs.getMetaData().getColumnLabel(i + 1);
            sb.append(columnLabel);
            sb.append("\t");
        }
        System.out.println(sb.toString());
    }
}
