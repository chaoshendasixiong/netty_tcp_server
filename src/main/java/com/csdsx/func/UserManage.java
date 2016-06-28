package com.csdsx.func;

import com.csdsx.Msg.MsgHandler;
import com.csdsx.bean.User;
import com.csdsx.bean.UserReg;
import com.csdsx.db.DBUtil;
import com.csdsx.util.GlobalUtil;
import com.csdsx.util.SessionUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

/**
 *
 */
public class UserManage {
    public static Logger log = LogManager.getLogger(UserManage.class);
/**
 * 功能:用户注册
 * 流程:有限判断 长度 正则匹配 2次密码一致 验证码
 * 输入:
 * 输出:
 */
    public static void user_reg(MsgHandler msg) {
        String s = msg.getParam();
        Gson gson = new Gson();
        UserReg userReg = gson.fromJson(s, UserReg.class);
        String username = userReg.getUsername();
        String password = userReg.getPassword();
        String password2 = userReg.getPassword2();
        String querycode = userReg.getQuerycode();
        String phone = userReg.getPhone();
        JsonObject obj = new JsonObject();
        boolean flag = true;

        //TODO 开发阶段取消限制 用户名的长度 密码的长度 用户名和密码匹配正则
        //判断用户名和密码的长度
        int un_len = username.length();
        int pw_len = password.length();

        if(un_len >=1 && un_len<=16 && pw_len >=6 && pw_len <=16) {
        }
        //匹配正则
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
        //判断前后两次密码是否一致
        if(flag&&password.equals(password2)) {

        }else{
            flag = false;
            obj.addProperty("m", "2次输入的密码不正确");
            obj.addProperty("s", 1);
        }
        //上述条件都满足 判断手机号是否被抢注
        String sql = "SELECT id FROM m_user WHERE phone=?";
        //TODO 判断手机号是否被抢注 放在获取验证码里
        //判断验证码是否一致
        String server_code = "123456";
        if(flag&&server_code.equals(querycode)) {

        }else{
            flag = false;
            obj.addProperty("m", "验证码输入错误");
            obj.addProperty("s", 1);
        }
        if(flag) {
            String token = GlobalUtil.genToken();
            obj.addProperty("s", 0);
            JsonObject child = new JsonObject();
            child.addProperty("username", username);
            child.addProperty("password", password);
            child.addProperty("token", token);
            obj.add("r", child);
        }
        msg.setResult(obj.toString());
        msg.commit();
    }

    public static void user_login(MsgHandler msg) {
        log.info("user_login");
        String body = msg.getParam();
        Gson gson = new Gson();
        User user = gson.fromJson(body, User.class);
        String username = user.getUsername();
        String password = user.getPassword();
        //从数据库中查询该用户信息
        String sql = "SELECT * FROM m_user WHERE username=? AND delete_status=1";
        try {
            user = DBUtil.query_com_bean(sql, User.class, new Object[]{username});
        } catch (SQLException e) {
            e.printStackTrace();
            user = null;
        }
        //比较username和password dev阶段不进行md5比较
        //if(user.getUsername().equals(username) && user.getPassword().equals(GlobalUtil.Md5(password))){
        JsonObject obj = new JsonObject();

        if(user!=null && user.getUsername().equals(username) && user.getPassword().equals(password)){
            //查询该人的状态 auth未认证 和status状态
            if(user.getAuth()==1) {
                //登陆成功
                SessionUtil.initSession(msg.getCh(), user);
                String token = GlobalUtil.genToken();
                obj.addProperty("s", 0);
                JsonObject child = new JsonObject();
                child.addProperty("username", username);
                child.addProperty("password", password);
                child.addProperty("token", token);
                obj.add("r", child);
            }else{
                obj.addProperty("s", 1);
                obj.addProperty("m", "账号未认证");
            }
        }else{
            obj.addProperty("s", 1);
            obj.addProperty("m", "用户名或密码不正确");
        }
        msg.setResult(obj.toString());
        msg.commit();
    }
    public static void user_quit(MsgHandler msg) {
        log.info("user_quit");
    }

    /**
     * 修改密码 前提条件 必须成功登录
     * @param msg
     */
    public static void user_changepwd(MsgHandler msg) {
        log.info("user_changepwd");
        String s = msg.getParam();
        Gson gson = new Gson();
        UserReg userReg = gson.fromJson(s, UserReg.class);
        String username = userReg.getUsername();
        String password = userReg.getPassword();
        String password2 = userReg.getPassword2();
        String querycode = userReg.getQuerycode();
        JsonObject obj = new JsonObject();
        boolean flag = true;

        //TODO 开发阶段取消限制 密码的长度 密码匹配正则
        //判断用户名的长度 密码的长度
        int un_len = username.length();
        int pw_len = password.length();

        if(un_len>=1 && un_len <= 16 && pw_len >=6 && pw_len<=16) {
        }
        //匹配正则
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
        //判断前后两次密码是否一致
        if(flag&&password.equals(password2)) {

        }else{
            flag = false;
            obj.addProperty("m", "2次输入的密码不正确");
            obj.addProperty("s", 1);
        }
        //判断验证码是否一致
        String server_code = "123456";
        if(flag&&server_code.equals(querycode)) {

        }else{
            flag = false;
            obj.addProperty("m", "验证码输入错误");
            obj.addProperty("s", 1);
        }
        //如果上述条件都满足 再去数据库查询改用户信息 判断用户名是否一致
        if(flag) {
            User user = null;
            String sql = "SELECT * FROM m_user WHERE username=? AND delete_status=1";
            try {
                user = DBUtil.query_com_bean(sql, User.class, new Object[]{username});
            } catch (SQLException e) {
                user = null;
            }
            //一致 则去修改密码
            if(user.getUsername().equals(username)) {
                //TODO 修改新密码
            }else{
                obj.addProperty("m", "用户名不正确");
                obj.addProperty("s", 1);
            }
        }
        msg.setResult(obj.toString());
        msg.commit();
    }
}
