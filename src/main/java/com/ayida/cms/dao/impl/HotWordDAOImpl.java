package com.ayida.cms.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ayida.cms.dao.HotWordDAO;
import com.ayida.cms.entity.hotword.HotWord;
import com.ayida.common.mybatis.BaseDAO;
import com.ayida.common.mybatis.Pager;
@Repository(value = "hotWordDao")
public class HotWordDAOImpl extends BaseDAO<HotWord> implements HotWordDAO
{
	private static final String HOTWORDDAO = "com.ayida.cms.dao.HotWordDAO.";

	@Override
	public HotWord save(HotWord bean)
	{
		return super.save(bean);
	}

	@Override
	public List<HotWord> getAll()
	{
		return super.getAll();
	}

	@Override
	protected String getMethodPath(String methodName)
	{
		return HOTWORDDAO + methodName;
	}

	@Override
	protected Map<String, Integer> getIdMap(Integer id)
	{
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("id", id);
		return map;
	}

	@Override
	public int delete(Integer id)
	{
		return super.delete(getIdMap(id));
	}

	@Override
	public HotWord update(HotWord bean)
	{
		Map<String, HotWord> map = new HashMap<String, HotWord>();
		map.put("bean", bean);
		super.update(map);
		return bean;
	}

	@Override
	public HotWord findById(Integer id)
	{
		return get(getIdMap(id));
	}

	@Override
	public HotWord findByName(String name)
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", name);
		return sqlSession.selectOne(getMethodPath("findByName"), map);
	}

	@Override
	public List<HotWord> getListByPager(Pager<HotWord> page)
	{
		return getPagerList(page);
	}

	@Override
	public List<HotWord> getChildList(Integer parentId)
	{
		return sqlSession.selectList(getMethodPath("getChildList"),
				getIdMap(parentId));
	}

	@Override
	public void deleteChild(Integer parentId)
	{
		sqlSession.delete(getMethodPath("deleteChild"), getIdMap(parentId));
	}

}
