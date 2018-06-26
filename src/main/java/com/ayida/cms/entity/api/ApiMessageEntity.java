package com.ayida.cms.entity.api;

import com.google.gson.annotations.Expose;

public class ApiMessageEntity
{
	@Expose
	private Integer code;
	
	@Expose
	private String text;

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
