package me.weix.fairy.config.shiro.filter;

import me.weix.fairy.config.shiro.authc.CustomUsernamepasswordToken;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {

    /**
     * 重写该方法,为了将loginType参数保存到token中
     *
     * @param request  请求
     * @param response 响应
     * @return
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        String username = getUsername(request);
        String password = getPassword(request);
        Integer loginType = Integer.parseInt(getLoginType(request));
        return createToken(username, password, request, response, loginType);
    }

    private AuthenticationToken createToken(String username, String password, ServletRequest request, ServletResponse response, Integer loginType) {
        boolean rememberMe = isRememberMe(request);
        String host = getHost(request);
        return createToken(username, password, rememberMe, host, loginType);
    }

    private AuthenticationToken createToken(String username, String password, boolean rememberMe, String host, Integer loginType) {

        return new CustomUsernamepasswordToken(username, password, rememberMe, host, loginType);
    }

    private AuthenticationToken createToken(String username, String password, Integer loginType) {
        return new CustomUsernamepasswordToken(username, password, loginType);
    }

    private String getLoginType(ServletRequest request) {
        return WebUtils.getCleanParam(request, "loginType");
    }

}