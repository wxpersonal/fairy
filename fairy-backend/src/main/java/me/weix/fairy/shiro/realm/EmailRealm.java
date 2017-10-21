package me.weix.fairy.shiro.realm;

import me.weix.fairy.pojo.Permission;
import me.weix.fairy.pojo.Role;
import me.weix.fairy.pojo.User;
import me.weix.fairy.service.IUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author: WeiX
 * @Date: 2017/4/25
 * @Description:
 */
public class EmailRealm extends AuthorizingRealm {

	@Autowired
	private IUserService userService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

		String email = (String) principalCollection.getPrimaryPrincipal();
		User user = userService.getUserByEmail(email);

		// 获取用户所有角色
		List<Role> roleList = userService.getRolesByUserId(user.getId());
		Set<String> roles = new HashSet<String>();
		for (Role r : roleList) {
			roles.add(r.getCode());
		}

		// 获取用户所有权限
		List<Permission> permissionList = userService.getPermissionsByUserId(user.getId());
		Set<String> permissions = new HashSet<String>();
		for (Permission p : permissionList) {
			permissions.add(p.getCode());
		}

		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		simpleAuthorizationInfo.setRoles(roles);
		simpleAuthorizationInfo.setStringPermissions(permissions);
		return simpleAuthorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

		String principal = (String) authenticationToken.getPrincipal();
		String credential = (String) authenticationToken.getCredentials();
		return new SimpleAuthenticationInfo(principal, credential, this.getName());
	}
}
