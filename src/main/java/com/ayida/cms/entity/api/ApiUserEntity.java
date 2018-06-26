package com.ayida.cms.entity.api;

import java.util.List;

import com.ayida.cms.entity.user.User;
import com.google.gson.annotations.Expose;

public class ApiUserEntity
{
	@Expose
	private Integer code;
	
	@Expose
	private String text;
	
	@Expose
	private User user;
	
	@Expose
	private List<User> users;

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}

	public List<User> getUsers()
	{
		return users;
	}

	public void setUsers(List<User> users)
	{
		this.users = users;
	}

	public Integer getCode()
	{
		return code;
	}

	public void setCode(Integer code)
	{
		this.code = code;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

}
