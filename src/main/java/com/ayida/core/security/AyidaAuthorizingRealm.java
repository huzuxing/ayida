package com.ayida.core.security;

import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.ayida.cms.entity.user.User;
import com.ayida.cms.service.UserService;
public class AyidaAuthorizingRealm extends AuthorizingRealm
{
	private static final Logger log = LoggerFactory
			.getLogger(AyidaAuthorizingRealm.class);

	/**
	 * 授权(non-Javadoc)
	 * 
	 * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache
	 *      .shiro.subject.PrincipalCollection)
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principal)
	{
		String username = (String)principal.getPrimaryPrincipal();
		User user = userService.findByUsername(username);
		SimpleAuthorizationInfo authc = null;
		if (null != user)
		{
			authc = new SimpleAuthorizationInfo();
			Set<String> permissions = user.getPerms();
			if (!CollectionUtils.isEmpty(permissions))
			{
				//若该用户的权限不为空，则将权限集赋予认证对象，便于其他地方进行权限检测
				authc.setStringPermissions(permissions);
			}
		}
		return authc;
	}

	/**
	 * 身份验证--登录 (non-Javadoc)
	 * 
	 * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org
	 *      .apache.shiro.authc.AuthenticationToken)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken paramAuthenticationToken)
			throws AuthenticationException
	{
		UsernamePasswordToken token = (UsernamePasswordToken) paramAuthenticationToken;
		User user = userService.findByUsername(token.getUsername());
		if (null != user)
		{
			return new SimpleAuthenticationInfo(user.getUsername(),
					user.getPassword(), getName());
		}
		else
		{
			return null;
		}
	}

	@Autowired
	private UserService userService;
}
