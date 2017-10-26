package me.weix.fairy.config.shiro.authc;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * Created by Administrator on 2017/6/20.
 */
public class CustomUsernamepasswordToken extends UsernamePasswordToken {

    //登录类型
    private Integer loginType;

    public Integer getLoginType() {
        return this.loginType;
    }

    public void setLoginType(Integer loginType) {
        this.loginType = loginType;
    }

    public CustomUsernamepasswordToken(String username, String password, Integer loginType) {
        super(username, password);
        this.loginType = loginType;
    }

    public CustomUsernamepasswordToken(String username, String password, boolean rememberMe, String host, Integer loginType) {
        super(username, password, rememberMe, host);
        this.loginType = loginType;
    }
}
