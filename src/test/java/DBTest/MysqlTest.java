package DBTest;

import com.csdsx.db.DBUtil;
import com.sun.rowset.CachedRowSetImpl;

import javax.sql.rowset.CachedRowSet;
import java.sql.Connection;
import java.sql.SQLException;


/**
 * Created by xxsy on 2016/3/15.
 */
public class MysqlTest {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        String str = "你好asd";
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
        String regex2 = "[^(a-zA-Z0-9\\u4e00-\\u9fa5)]{1,16}";



        String value = "aa1aaa";  // 长度不够
        System.out.println(value.matches(regex2));

        System.out.println(str.length());
        //DBUtil.init();
        CachedRowSet set = DBUtil.query_com("select * from user");
        CachedRowSetImpl set_ = (CachedRowSetImpl) set;
        System.out.println("总记录:"+set.size());
        while (set.next()) {
            String username = set.getString("username");
            String password = set.getString("password");
            set_.updateString("username", "admin");
            set_.updateRow();
            Connection conn = DBUtil.getComConn();
            conn.setAutoCommit(false);
            set_.acceptChanges(conn);
            System.out.println(username+"\t"+password+'\n');//set.getInt(1)+"\t"+set.getString(2)+"\t"+set.getInt(3));
        }
//
//        //分页
//        int page = 4;
//        int size = 10;
//        //rowSet.populate(rs, (page - 1) * size + 1);
//        //rowSet.setPageSize(size);
//
//
//
//        //Class.forName("com.mysql.jdbc.Driver");//加载数据库驱动
//        //String url="jdbc:mysql://192.168.171.128:3306/testshop";//数据库连接子协议
//        //Connection conn= DriverManager.getConnection(url, "test", "123456");
//        int count = 0;
//        while(true)
//        {
//            Runnable r = new Runnable(){
//
//                @Override
//                public void run() {
////                    while (true) {
//                        try {
//                            Thread.sleep(10);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        QueryRunner runner = new QueryRunner(DBUtil.getDs_com());
//                        String sql = "select * from user";
//                        TTT obj = null;
//                        try {
//                            obj = (TTT) runner.query(sql, new BeanHandler(TTT.class));
//                        } catch (SQLException e) {
//                            e.printStackTrace();
//                        }
//                        System.out.println(obj.getUsername() + obj.getPassword());
////                    }
//                }
//            };
//            if(count++>10)
//                break;
//            try {
//                Thread.sleep(1000);
//              new Thread(r).start();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
////        while(true)
//        {
//            try {
//                Thread.sleep(20*1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        QueryRunner runner = new QueryRunner(DBUtil.getDs_com());
//        String sql = "select * from user";
//        TTT obj = null;
//        try {
//            obj = (TTT) runner.query(sql, new BeanHandler(TTT.class));
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        System.out.println(obj.getUsername() + obj.getPassword());
        while(true)
        {
            try {
                Thread.sleep(20*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
