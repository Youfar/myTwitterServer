package com.oh.spring.security;

import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;

/**
 * @author cho.oh
 */
public class LoginUser extends User{
    private Integer userId;
    private String userName;
    private String password;
    private String userMail;

    public LoginUser(com.oh.spring.entity.User user) {
        super(user.getUsername(), user.getPassword(), true, true, true, true, new ArrayList<>());
        this.userId = user.getUserId();
        this.userName = user.getUsername();
        this.password = user.getPassword();
        this.userMail = user.getEmail();
    }

    public Integer getLoginUserId() {
        return userId;
    }

    public String getLoginUserName() {
        return userName;
    }

    public String getLoginUserMail() {
        return userMail;
    }
}
