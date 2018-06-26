package com.ayida.common.mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.type.Alias;

import com.ayida.common.constant.Constants;
import com.google.gson.annotations.Expose;

@Alias("Pager")
public class Pager<T>
{
	/** 页码 **/
	@Expose
	private Integer pageNo;

	/** 页容量 **/
	@Expose
	private Integer pageSize;

	/** 总页数 **/
	@Expose
	private Integer totalPage;

	/** 总记录数 **/
	@Expose
	private Integer totalCount;

	/** 当前页结果集 **/
	@Expose
	private List<T> results;

	/** 其他参数封装 **/
	@Expose
	private Map<String, Object> params = new HashMap<String, Object>();

	public Pager(){}
	
	public Pager(Integer pageNo, Integer pageSize, Integer totalPage,
			Integer totalCount, List<T> results, Map<String, Object> params)
	{
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.totalPage = totalPage;
		this.totalCount = totalCount;
		this.results = results;
		this.params = params;
	}

	public Integer getTotalCount()
	{
		return totalCount;
	}
	
	public void setTotalCount(Integer totalCount)
	{
		this.totalCount = totalCount;
	}

	public Map<String, Object> getParams()
	{
		return params;
	}

	public void setParams(Map<String, Object> params)
	{
		this.params = params;
	}

	public List<T> getResults()
	{
		return results;
	}

	public void setResults(List<T> results)
	{
		this.results = results;
	}

	public Integer getPageNo()
	{
		return pageNo;
	}

	public void setPageNo(Integer pageNo)
	{
		if (pageNo < 1 || null == pageNo)
			this.pageNo = 1;
		this.pageNo = pageNo;
	}

	public Integer getPageSize()
	{
		return pageSize;
	}

	public void setPageSize(Integer pageSize)
	{
		if (pageSize < 0 || null == pageSize)
			this.pageSize = Constants.DEFAULT_PAGESIZE;
		this.pageSize = pageSize;
	}
	

	public void setTotalPage(Integer totalPage)
	{
		this.totalPage = totalPage;
	}

	public Integer getTotalPage()
	{
		totalPage = totalCount % pageSize == 0
				? totalCount / pageSize
				: totalCount / pageSize + 1;
		return totalPage;
	}

	public boolean isFirstPage()
	{
		return pageNo <= 1;
	}

	public boolean isLastPage()
	{
		return pageNo >= getTotalPage();
	}

	public Integer getNextPage()
	{
		if (isLastPage())
		{
			return getTotalPage();
		}
		return pageNo + 1;
	}

	public Integer getPrePage()
	{
		if (isFirstPage())
		{
			return pageNo;
		}
		return pageNo - 1;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Page [PageNo=" + pageNo)
				.append(", PageSize=" + pageSize)
				.append(", TotalPage=" + totalPage)
				.append(", TotalCount=" + totalCount).append("]");
		return builder.toString();
	}
}
