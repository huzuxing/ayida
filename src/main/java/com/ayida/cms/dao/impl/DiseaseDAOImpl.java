package com.ayida.cms.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ayida.cms.dao.DiseaseDAO;
import com.ayida.cms.entity.disease.Disease;
import com.ayida.common.mybatis.BaseDAO;
import com.ayida.common.mybatis.Pager;

@Repository(value = "diseaseDao")
public class DiseaseDAOImpl extends BaseDAO<Disease> implements DiseaseDAO
{

	private static final String DISEASEDAO = "com.ayida.cms.dao.DiseaseDAO.";

	@Override
	public Disease save(Disease bean)
	{
		return super.save(bean);
	}

	@Override
	public Disease findById(Integer id)
	{
		return get(getIdMap(id));
	}

	@Override
	public int updateBean(Disease bean)
	{
		Map<String, Disease> map = new HashMap<String, Disease>();
		map.put("disease", bean);
		return update(map);
	}

	@Override
	public int deleteById(Integer id)
	{
		return delete(getIdMap(id));
	}

	@Override
	public List<Disease> getAllDisease()
	{
		return getAll();
	}

	@Override
	public List<Disease> getDiseasePagerList(Pager<Disease> pager)
	{
		return getPagerList(pager);
	}

	@Override
	protected String getMethodPath(String methodName)
	{
		return DISEASEDAO + methodName;
	}

	@Override
	protected Map<String, Integer> getIdMap(Integer id)
	{
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("id", id);
		return map;
	}

	@Override
	public Disease findByName(String name)
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", name);
		return sqlSession.selectOne(getMethodPath("findByName"), map);
	}

}
