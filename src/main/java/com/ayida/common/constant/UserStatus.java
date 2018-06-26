package com.ayida.common.constant;


/**
 * 用户状态设计
 * @author John Hu
 *
 */
/**
 * @author John Hu
 *
 */
public class UserStatus
{
	/**
	 * ABLED = 1 用户可有效
	 */
	public static final Integer ABLED = 1;
	
	/**
	 * DISABLED = 0 用户为禁用状态
	 */
	public static final Integer DISABLED = 0;
	
	/**
	 * 普通会员
	 */
	public static final Integer MEMBER = 1;
	
	/**
	 * 高级会员
	 */
	public static final Integer ADVANCED_MEMBER = 2;
	
	/**
	 * 管理员
	 */
	public static final Integer ADMIN = 3;
	
	/**
	 * 超级管理员
	 */
	public static final Integer SUPER_ADMIN = 4;
	
}
