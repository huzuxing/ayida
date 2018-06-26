package com.ayida.cms.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ayida.cms.dao.SubProfessionalDAO;
import com.ayida.cms.entity.doctor.SubProfessional;
import com.ayida.common.mybatis.BaseDAO;
import com.ayida.common.mybatis.Pager;

@Repository(value = "subProfessionalDao")
public class SubProfessionalDAOImpl extends BaseDAO<SubProfessional>
		implements
			SubProfessionalDAO
{
	private static final String SUBPROFESSIONALDAO = "com.ayida.cms.dao.SubProfessionalDAO.";

	@Override
	public SubProfessional save(SubProfessional bean)
	{
		return super.save(bean);
	}

	@Override
	public SubProfessional findById(Integer id)
	{
		return get(getIdMap(id));
	}

	@Override
	public int updateBean(SubProfessional bean)
	{
		Map<String, SubProfessional> map = new HashMap<String, SubProfessional>();
		map.put("bean", bean);
		return update(map);
	}

	@Override
	public int deleteById(Integer id)
	{
		return delete(getIdMap(id));
	}

	@Override
	public List<SubProfessional> getAllSubProfessional()
	{
		return getAll();
	}

	@Override
	public List<SubProfessional> getSubProfessionalPagerList(
			Pager<SubProfessional> pager)
	{
		return getPagerList(pager);
	}

	@Override
	protected String getMethodPath(String methodName)
	{
		return SUBPROFESSIONALDAO + methodName;
	}

	@Override
	protected Map<String, Integer> getIdMap(Integer id)
	{
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("id", id);
		return map;
	}

	@Override
	public SubProfessional findByName(String name)
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", name);
		return sqlSession.selectOne(getMethodPath("findByName"), map);
	}

	@Override
	public void saveSubDisease(Integer subId, Integer diseaseId)
	{
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("subId", subId);
		map.put("diseaseId", diseaseId);
		sqlSession.insert(getMethodPath("saveSubDisease"), map);
	}

}
