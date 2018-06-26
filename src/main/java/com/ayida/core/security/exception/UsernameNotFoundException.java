package com.ayida.core.security.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 用户名没有找到异常
 * @author John Hu
 *
 */
@SuppressWarnings("serial")
public class UsernameNotFoundException extends AuthenticationException
{

	public UsernameNotFoundException()
	{
		
	}
	
	public UsernameNotFoundException(String msg)
	{
		super(msg);
	}

}
