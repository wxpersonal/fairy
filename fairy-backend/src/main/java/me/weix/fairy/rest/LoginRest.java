package me.weix.fairy.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.weix.fairy.pojo.User;
import me.weix.fairy.pojo.vo.AjaxResult;
import me.weix.fairy.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @Author: WeiX
 * @Date: 2017/4/25
 * @Description: 登录控制器
 */

@Api(value = "login")
@Path("login")
public class LoginRest {

    @Autowired
    private IUserService userService;

    @ApiOperation(value = "登录")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public AjaxResult login(@QueryParam("username") String username,
                            @QueryParam("password") String password,
                            @QueryParam("rememberMe") Boolean rememberMe) {

        if (StringUtils.isBlank(username)) {
            return new AjaxResult(false, "账户名不能为空！");
        }
        if (StringUtils.isBlank(password)) {
            return new AjaxResult(false, "密码不能为空！");
        }
        Subject subject = SecurityUtils.getSubject();
        //当前登录用户存入shiro session
        User user = userService.getUserByUsername(username);
        subject.getSession().setAttribute("user", user);
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
        if (Boolean.valueOf(rememberMe)) {
            usernamePasswordToken.setRememberMe(true);
        } else {
            usernamePasswordToken.setRememberMe(false);
        }
        subject.login(usernamePasswordToken);
        return new AjaxResult(true, "登录成功！");
    }
}
