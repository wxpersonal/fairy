package me.weix.fairy.service.impl;

import me.weix.fairy.mapper.UserMapper;
import me.weix.fairy.pojo.User;
import me.weix.fairy.pojo.vo.AjaxResult;
import me.weix.fairy.service.ILoginService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: WeiX
 * @Date: 2017/10/27
 * @description :
 */
public class LoginServiceImpl implements ILoginService {

    @Resource
    private UserMapper userMapper;

    @Override
    public AjaxResult login(String username, String password, boolean rememberMe) {

        if (StringUtils.isBlank(username)) {
            return new AjaxResult(false, "账户名不能为空！");
        }
        if (StringUtils.isBlank(password)) {
            return new AjaxResult(false, "密码不能为空！");
        }
        Subject subject = SecurityUtils.getSubject();
        //当前登录用户存入shiro session
        User user = userMapper.getUserByUsername(username);
        subject.getSession().setAttribute("user", user);
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
        usernamePasswordToken.setRememberMe(rememberMe);
        subject.login(usernamePasswordToken);

        return new AjaxResult(true, "登录成功！");
    }
}
