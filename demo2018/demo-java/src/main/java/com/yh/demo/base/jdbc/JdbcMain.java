package com.yh.demo.base.jdbc;

import java.sql.*;

/**
 * 基本功能演示
 * @author yanghan
 * @date 2021/4/29
 */
public class JdbcMain {

    private static final String URL = "jdbc:mysql://localhost:3306/test_base?useUnicode=true&autoReconnect=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
    private static final String USER = "root";
    private static final String PASSWORD = "yanghan";

    public static void main(String[] args) throws Exception {
//        query();
//        add();
//        update();
//        delete();

        addBatch();
    }

    public static void query() throws SQLException {
        // 建立连接，需要关闭资源，采用try（resource）形式
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            // 预编译，防止SQL注入
            String sql = "SELECT * FROM sys_dict WHERE key_ = ? LIMIT 2";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                // 添加参数，01是性别的数据字典值
                ps.setObject(1, "是否");
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        // 输出第2列数据，索引从1开始
                        System.out.println(rs.getString(3) + "|" + rs.getString(4));
                    }
                }
            }
        }
    }

    public static void add() throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO sys_dict (key_, code_, name_,sn_) VALUES (?,?,?,?)",
                    // 该参数表示需要返回自增主键
                    Statement.RETURN_GENERATED_KEYS)) {
                // 插入一条数据库，索引从1开始
                ps.setString(1, "学科");
                ps.setString(2, "01");
                ps.setString(3, "数学");
                ps.setInt(4, 1);
                int n = ps.executeUpdate();
                System.out.println("插入" + (n > 0 ? "成功" : "失败"));
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        System.out.println("自增主键：" + rs.getInt(1));
                    }
                }
            }
        }
    }

    public static void update() throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "UPDATE sys_dict SET name_ = '语文' WHERE key_ = ? AND code_ = ?")) {
                ps.setString(1, "学科");
                ps.setString(2, "01");
                int n = ps.executeUpdate();
                System.out.println("更新" + (n > 0 ? "成功" : "失败"));
            }
        }
    }

    public static void delete() throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM sys_dict WHERE key_ = ? AND code_ = ?")) {
                ps.setString(1, "学科");
                ps.setString(2, "01");
                int n = ps.executeUpdate();
                System.out.println("删除" + (n > 0 ? "成功" : "失败"));
            }
        }
    }

    public static void transcationSQL() throws SQLException {
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        try {
            // 关闭自动提交；改为手动
            conn.setAutoCommit(false);
            // 执行多条SQL语句:
            //insert(); update(); delete();
            // 提交事务:
            conn.commit();
        } catch (SQLException e) {
            // 回滚事务:
            conn.rollback();
        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }
    }

    public static void addBatch() throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO sys_dict (key_, code_, name_,sn_) VALUES (?,?,?,?)")) {
                // 对同一个PreparedStatement反复设置参数
                for (int i = 1; i <= 3; i++) {
                    ps.setString(1, "学科");
                    ps.setString(2, "0" + i);
                    ps.setString(3, "学科" + i);
                    ps.setInt(4, i);
                    ps.addBatch(); // 添加到batch
                }
                // 执行batch:
                int[] ns = ps.executeBatch();
                for (int n : ns) {
                    // 每条SQL的执行结果
                    System.out.println("插入" + (n > 0 ? "成功" : "失败"));
                }
            }
        }

    }
}
