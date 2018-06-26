package com.ayida.cms.entity.log;

import java.util.Date;

public class Log implements java.io.Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer userId;

	private Integer category;

	private Date logTime;

	private String ip;

	private String url;

	private String title;

	private String content;

	public Log(Integer id, Integer userId, Integer category, Date logTime,
			String ip, String url, String title, String content)
	{
		this.id = id;
		this.userId = userId;
		this.category = category;
		this.logTime = logTime;
		this.ip = ip;
		this.url = url;
		this.title = title;
		this.content = content;
	}

	/**
	 * @return the id
	 */
	public Integer getId()
	{
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id)
	{
		this.id = id;
	}

	/**
	 * @return the userId
	 */
	public Integer getUserId()
	{
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Integer userId)
	{
		this.userId = userId;
	}

	/**
	 * @return the category
	 */
	public Integer getCategory()
	{
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(Integer category)
	{
		this.category = category;
	}

	/**
	 * @return the logTime
	 */
	public Date getLogTime()
	{
		return logTime;
	}

	/**
	 * @param logTime
	 *            the logTime to set
	 */
	public void setLogTime(Date logTime)
	{
		this.logTime = logTime;
	}

	/**
	 * @return the ip
	 */
	public String getIp()
	{
		return ip;
	}

	/**
	 * @param ip
	 *            the ip to set
	 */
	public void setIp(String ip)
	{
		this.ip = ip;
	}

	/**
	 * @return the url
	 */
	public String getUrl()
	{
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url)
	{
		this.url = url;
	}

	/**
	 * @return the title
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * @return the content
	 */
	public String getContent()
	{
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content)
	{
		this.content = content;
	}

}
