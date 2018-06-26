package com.ayida.cms.entity.user;

import java.util.Date;

import com.google.gson.annotations.Expose;

public class AdminUser extends BaseUser
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Expose
	private Integer errorCount;

	@Expose
	private Date errorTime;

	/**
	 * @return the errorCount
	 */
	public Integer getErrorCount()
	{
		return errorCount;
	}

	/**
	 * @param errorCount
	 *            the errorCount to set
	 */
	public void setErrorCount(Integer errorCount)
	{
		this.errorCount = errorCount;
	}

	/**
	 * @return the errorTime
	 */
	public Date getErrorTime()
	{
		return errorTime;
	}

	/**
	 * @param errorTime
	 *            the errorTime to set
	 */
	public void setErrorTime(Date errorTime)
	{
		this.errorTime = errorTime;
	}
	
	/**
	 * 初始化一些数据
	 */
	public void init()
	{
		if (null == getLoginCount())
		{
			setLoginCount(0);
		}
	}
}
