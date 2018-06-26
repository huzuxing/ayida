package com.ayida.cms.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ayida.cms.dao.SearchWordDAO;
import com.ayida.cms.entity.hotword.SearchWord;
import com.ayida.common.mybatis.BaseDAO;
import com.ayida.common.mybatis.Pager;
@Repository(value = "searchDao")
public class SearchWordDAOImpl extends BaseDAO<SearchWord>
		implements
			SearchWordDAO
{
	private static final String SEARCH_WORD_DAO = "com.ayida.cms.dao.SearchWordDAO.";

	@Override
	protected String getMethodPath(String methodName)
	{
		return SEARCH_WORD_DAO + methodName;
	}

	@Override
	protected Map<String, Integer> getIdMap(Integer id)
	{
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("id", id);
		return map;
	}

	@Override
	public SearchWord save(SearchWord bean)
	{
		return super.save(bean);
	}

	@Override
	public int updateSearchWord(SearchWord bean)
	{
		Map<String, SearchWord> map = new HashMap<String, SearchWord>();
		map.put("searchWord", bean);
		return update(map);
	}

	@Override
	public SearchWord findByName(String name)
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", name);
		return sqlSession.selectOne(getMethodPath("findByName"), map);
	}

	@Override
	public List<SearchWord> findListByName(String name)
	{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", name);
		Pager<SearchWord> page = new Pager<SearchWord>();
		page.setPageNo(1);
		page.setPageSize(10);
		page.setParams(params);
		return sqlSession.selectList(getMethodPath("getListByName"), page);
	}

}
