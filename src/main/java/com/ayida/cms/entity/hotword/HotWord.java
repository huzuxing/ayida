package com.ayida.cms.entity.hotword;

import com.google.gson.annotations.Expose;

public class HotWord extends BaseHotWord
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Expose
	private Integer parentId;

	/**
	 * @return the parentId
	 */
	public Integer getParentId()
	{
		return parentId;
	}

	/**
	 * @param parentId
	 *            the parentId to set
	 */
	public void setParentId(Integer parentId)
	{
		this.parentId = parentId;
	}

}
