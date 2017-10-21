package me.weix.fairy.service.impl;

import me.weix.fairy.mapper.PermissionMapper;
import me.weix.fairy.mapper.RoleMapper;
import me.weix.fairy.mapper.UserMapper;
import me.weix.fairy.pojo.Permission;
import me.weix.fairy.pojo.Role;
import me.weix.fairy.pojo.User;
import me.weix.fairy.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: WeiX
 * @Date: 2017/4/25
 * @Description:
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements IUserService {

	@Resource
	private RoleMapper roleMapper;

	@Resource
	private PermissionMapper permissionMapper;

	@Resource
	private UserMapper userMapper;

	@Override
	public List<Role> getRolesByUserId(Integer userId) {
		return roleMapper.getRolesByUserId(userId);
	}

	@Override
	public List<Permission> getPermissionsByUserId(Integer userId) {

		List<Permission> permissionList = new ArrayList<Permission>();
		List<Role> roleList = getRolesByUserId(userId);
		for (Role role : roleList) {
			List<Permission> permissions = permissionMapper.getPermissionsByRoleId(role.getId());
			permissionList.addAll(permissions);
		}
		return permissionList;
	}

	@Override
	public User getUserByEmail(String email) {
		return userMapper.getUserByEmail(email);
	}

	@Override
	public User getUserByMobile(String mobile) {
		return userMapper.getUserByMobile(mobile);
	}
}
