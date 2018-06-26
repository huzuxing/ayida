package com.ayida.cms.entity.hotword;

import com.google.gson.annotations.Expose;

public class SearchWord extends BaseHotWord
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Expose
	private Integer searchCount;
	
	@Expose
	private String nameInitial;

	public String getNameInitial()
	{
		return nameInitial;
	}

	public void setNameInitial(String nameInitial)
	{
		this.nameInitial = nameInitial;
	}

	/**
	 * @return the searchCount
	 */
	public Integer getSearchCount()
	{
		return searchCount;
	}

	/**
	 * @param searchCount
	 *            the searchCount to set
	 */
	public void setSearchCount(Integer searchCount)
	{
		this.searchCount = searchCount;
	}
	
	public void init()
	{
		if (null == getSearchCount())
		{
			this.setSearchCount(0);
		}
	}
}
