package com.ayida.cms.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ayida.cms.dao.DoctorDAO;
import com.ayida.cms.entity.doctor.Doctor;
import com.ayida.common.mybatis.BaseDAO;
import com.ayida.common.mybatis.Pager;

@Repository(value = "docDao")
public class DoctorDAOImpl extends BaseDAO<Doctor> implements DoctorDAO
{

	private static final String DOCTORDAO = "com.ayida.cms.dao.DoctorDAO.";

	@Override
	public Doctor save(Doctor bean)
	{
		return super.save(bean);
	}

	@Override
	public Doctor findById(Integer id)
	{
		return get(getIdMap(id));
	}

	@Override
	public Pager<Doctor> getCollectedDoctorsByUserId(Pager<Doctor> page)
	{
		List<Doctor> list = sqlSession.selectList(
				getMethodPath("getDoctorsByUserId"), page);
		page.setResults(list);
		return page;
	}
	public int updateBean(Doctor bean)
	{
		Map<String, Doctor> map = new HashMap<String, Doctor>();
		map.put("doctor", bean);
		return update(map);
	}

	@Override
	public int deleteById(Integer id)
	{
		return delete(getIdMap(id));
	}

	@Override
	public List<Doctor> getAllDoctors()
	{
		return getAll();
	}

	@Override
	public List<Doctor> getDoctorPagerList(Pager<Doctor> pager)
	{
		return getPagerList(pager);
	}

	@Override
	protected String getMethodPath(String methodName)
	{
		return DOCTORDAO + methodName;
	}

	@Override
	protected Map<String, Integer> getIdMap(Integer id)
	{
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("id", id);
		return map;
	}

	@Override
	public List<Doctor> findDoctorBySearching(Map<String, Object> map)
	{
		return sqlSession.selectList(getMethodPath("findDoctorBySearching"),
				map);
	}

	@Override
	public void saveDoctorSubprofessional(Integer doctorId, Integer subId)
	{
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("doctorId", doctorId);
		map.put("subId", subId);
		sqlSession.insert(getMethodPath("saveDoctorSubprofessional"), map);
	}

	@Override
	public void saveDoctorHospital(Integer doctorId, Integer hospitalId)
	{
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("doctorId", doctorId);
		map.put("hospitalId", hospitalId);
		sqlSession.insert(getMethodPath("saveDoctorHospital"), map);
	}

	@Override
	public Doctor isCollected(Integer userId, Integer doctorId)
	{
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("userId", userId);
		map.put("doctorId", doctorId);
		return sqlSession.selectOne(getMethodPath("isCollected"), map);
	}
}
