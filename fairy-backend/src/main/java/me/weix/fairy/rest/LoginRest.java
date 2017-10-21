package me.weix.fairy.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import me.weix.fairy.pojo.User;
import me.weix.fairy.pojo.vo.AjaxResult;
import me.weix.fairy.service.IUserService;
import me.weix.fairy.util.RedisUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
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
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path(value = "id/{id}")
    public AjaxResult login(@QueryParam("username") String username,
                            @QueryParam("password") String password,
                            @QueryParam("rememberMe") String rememberMe) {

        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        String serverSessionId = session.getId().toString();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
        if (Boolean.valueOf(rememberMe)) {
            usernamePasswordToken.setRememberMe(true);
        } else {
            usernamePasswordToken.setRememberMe(false);
        }
        subject.login(usernamePasswordToken);

        // 判断是否已登录，如果已登录，则回跳


//        User byId = userService.getById(id);
        return new AjaxResult();
    }
}
