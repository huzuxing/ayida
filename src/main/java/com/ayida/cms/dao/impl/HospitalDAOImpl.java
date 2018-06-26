package com.ayida.cms.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ayida.cms.dao.HospitalDAO;
import com.ayida.cms.entity.hospital.Hospital;
import com.ayida.common.mybatis.BaseDAO;
import com.ayida.common.mybatis.Pager;
@Repository(value = "hospitalDao")
public class HospitalDAOImpl extends BaseDAO<Hospital> implements HospitalDAO
{
	private static final String HOSPITALDAO = "com.ayida.cms.dao.HospitalDAO.";
 
	@Override
	public Hospital save(Hospital bean)
	{
		return super.save(bean);
	}
	
	@Override
	public List<Hospital> getAll()
	{
		return super.getAll();
	}
	
	@Override
	protected String getMethodPath(String methodName)
	{
		return HOSPITALDAO + methodName;
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
	public int update(Hospital bean)
	{
		Map<String, Hospital> map = new HashMap<String, Hospital>();
		map.put("bean", bean);
		return super.update(map);
	}

	@Override
	public Hospital findById(Integer id)
	{
		return get(getIdMap(id));
	}

	@Override
	public Hospital findByName(String name)
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", name);
		return sqlSession.selectOne(getMethodPath("findByName"), map);
	}

	@Override
	public List<Hospital> getDoctorListByPager(Pager page)
	{
		return getPagerList(page);
	}

}
