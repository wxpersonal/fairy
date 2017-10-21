package me.weix.fairy.service;

import me.weix.fairy.pojo.Permission;
import me.weix.fairy.pojo.Role;
import me.weix.fairy.pojo.User;

import java.util.List;

public interface IUserService extends IBaseService<User> {

	List<Role> getRolesByUserId(Integer userId);

	List<Permission> getPermissionsByUserId(Integer userId);

	User getUserByEmail(String email);

	User getUserByMobile(String mobile);

}
