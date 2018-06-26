package com.ayida.cms.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ayida.cms.dao.RelativeSearchWordDAO;
import com.ayida.cms.entity.hotword.RelativeSearchWord;
import com.ayida.common.mybatis.BaseDAO;
import com.ayida.common.util.ChineseCharToEn;

@Repository(value = "relativeWordDao")
public class RelativeSearchWordDAOImpl extends BaseDAO<RelativeSearchWord>
		implements
			RelativeSearchWordDAO
{

	private static final String RELATIVEWORDDAO = "com.ayida.cms.dao.RelativeSearchWordDAO.";

	@Override
	public List<RelativeSearchWord> findByName(String name)
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", name);
		/** 获取首字母 **/
		String nameInitial = ChineseCharToEn.getAllFirstLetter(name);
		map.put("nameInitial", nameInitial);
		return sqlSession.selectList(getMethodPath("findByName"), map);
	}

	@Override
	public RelativeSearchWord save(RelativeSearchWord bean)
	{
		return super.save(bean);
	}

	@Override
	protected String getMethodPath(String methodName)
	{
		return RELATIVEWORDDAO + methodName;
	}

	@Override
	protected Map<String, Integer> getIdMap(Integer id)
	{
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("id", id);
		return map;
	}

	@Override
	public List<RelativeSearchWord> findAll()
	{
		return getAll();
	}

	@Override
	public RelativeSearchWord findById(Integer id)
	{
		return get(getIdMap(id));
	}

	@Override
	public RelativeSearchWord update(RelativeSearchWord bean)
	{
		Map<String, RelativeSearchWord> map = new HashMap<String, RelativeSearchWord>();
		map.put("bean", bean);
		super.update(map);
		return bean;
	}

	@Override
	public int delete(Integer id)
	{
		return super.delete(getIdMap(id));
	}

}
