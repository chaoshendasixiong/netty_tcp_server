package com.csdsx.bean;

/**
 * Created by xxsy on 2016/4/6.
 */
public class UserReg {
    private String username;
    private String password;
    private String password2;
    private String querycode;
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getQuerycode() {
        return querycode;
    }

    public void setQuerycode(String querycode) {
        this.querycode = querycode;
    }
}
