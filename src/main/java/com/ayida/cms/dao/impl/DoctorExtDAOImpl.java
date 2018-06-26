package com.ayida.cms.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ayida.cms.dao.DoctorExtDAO;
import com.ayida.cms.entity.doctor.DoctorExt;
import com.ayida.common.mybatis.BaseDAO;

@Repository(value = "docExtDao")
public class DoctorExtDAOImpl extends BaseDAO<DoctorExt>
		implements
			DoctorExtDAO
{
	private static final String EXTDAO = "com.ayida.cms.dao.DoctorExtDAO.";

	@Override
	public DoctorExt save(DoctorExt bean)
	{
		return super.save(bean);
	}

	@Override
	public DoctorExt findById(Integer id)
	{
		return get(getIdMap(id));
	}

	@Override
	public int updateById(Integer id)
	{
		return update(getIdMap(id));
	}

	@Override
	public int deleteById(Integer id)
	{
		return delete(getIdMap(id));
	}

	@Override
	protected String getMethodPath(String methodName)
	{
		return EXTDAO + methodName;
	}

	@Override
	protected Map<String, Integer> getIdMap(Integer id)
	{
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("id", id);
		return map;
	}

}
