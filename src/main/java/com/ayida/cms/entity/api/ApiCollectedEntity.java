package com.ayida.cms.entity.api;

import com.google.gson.annotations.Expose;

public class ApiCollectedEntity
{
	@Expose
	private Integer code;
	
	@Expose
	private boolean collected;

	public Integer getCode()
	{
		return code;
	}

	public void setCode(Integer code)
	{
		this.code = code;
	}

	public boolean isCollected()
	{
		return collected;
	}

	public void setCollected(boolean collected)
	{
		this.collected = collected;
	}
}
