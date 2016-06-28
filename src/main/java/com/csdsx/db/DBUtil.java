package com.csdsx.db;

//import com.alibaba.druid.pool.DruidDataSource;
//import com.alibaba.druid.pool.DruidDataSourceFactory;
//import com.alibaba.druid.pool.DruidPooledConnection;
import com.sun.rowset.CachedRowSetImpl;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import javax.sql.rowset.CachedRowSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by xxsy on 2016/3/15.
 * 初始化创建initialSize个连接
 * 有操作取出一个连接
 * 如果正在使用的连接数等于maxActive 会阻塞 直到有新的连接 如果等待时间超过了maxWait 会报错
 * 如果没有达到maxActive 判断当前是否有空闲连接 没有则新建一个连接
 * 如果当前总连接少于miniIdle 则会建立新的空闲连接 保证miniIdle
 * 如果某个连接在空闲timeBetweenEvictionRunsMillis时间后仍然没有使用 则会物理性关闭
 * 有的数据库自带超时限制 或者 网络中断等 连接池的连接会出现失效 可以设置testWhileIdle=true
 * 含义是获取连接时如果检测到当前连接不活跃时间超过了timeBetweenEvictionRunsMillis 则去手动
 * 检测下当前连接的有效性 保证有效后才加以使用
 * java -cp druid-1.0.18.jar com.alibaba.druid.filter.config.ConfigTools you_password
 * 加密数据库密码
 */
public class DBUtil {
    private static HikariDataSource ds_com;
    private static HikariDataSource ds_log;
    public static HikariDataSource getDs_com() {
        return ds_com;
    }
    public static HikariDataSource getDs_log() {
        return ds_log;
    }
    public static void init() {
        HikariConfig config = new HikariConfig("conf/db1.properties");
//            HikariDataSource ds = new HikariDataSource(config);
        ds_com = new HikariDataSource(config);
        ds_log = new HikariDataSource(config);
    }

    public static Connection getComConn() {
        try {
            return ds_com.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Connection getLogConn() {
        try {
            return ds_log.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int update_com(String sql) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getComConn();
            ps = conn.prepareStatement(sql);
            int effectRows = ps.executeUpdate();
            ps.close();
            conn.close();
            return effectRows;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            ps = null;
            conn = null;
        }
    }

    public static int update_log(String sql) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getLogConn();
            ps = conn.prepareStatement(sql);
            int effectRows = ps.executeUpdate();
            ps.close();
            conn.close();
            return effectRows;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            ps = null;
            conn = null;
        }
    }

    public static CachedRowSet query_com(String sql) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getComConn();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            CachedRowSet cachedRowSet = new CachedRowSetImpl();
            //需要jdk1.7
            //CachedRowSet cachedRowSet = RowSetProvider.newFactory().createCachedRowSet();
            cachedRowSet.populate(rs);
            rs.close();
            ps.close();
            conn.close();
            return cachedRowSet;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            ps = null;
            conn = null;
        }
    }

    public static CachedRowSet query_log(String sql) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getLogConn();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            CachedRowSet cachedRowSet = new CachedRowSetImpl();
            //CachedRowSet cachedRowSet = RowSetProvider.newFactory().createCachedRowSet();
            cachedRowSet.populate(rs);
            rs.close();
            ps.close();
            conn.close();
            return cachedRowSet;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            rs = null;
            ps = null;
            conn = null;
        }
    }

    public static <T>T query_com_bean(String sql, Class<T> cls, Object[] params) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DBUtil.getDs_com());
        return (T)queryRunner.query(sql, new BeanHandler(cls), params);
    }

}
