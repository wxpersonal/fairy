package me.weix.fairy.service;

import me.weix.fairy.pojo.Menu;
import me.weix.fairy.pojo.vo.AjaxResult;

import java.util.List;

/**
 * @Author: WeiX
 * @Date: 2017/10/27
 * @description :
 */
public interface ILoginService {

    AjaxResult login(String username,String password, boolean rememberMe);
}
