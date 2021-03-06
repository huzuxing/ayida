package com.ayida.cms.dao;

import java.util.List;
import java.util.Map;

import com.ayida.cms.entity.doctor.Doctor;
import com.ayida.common.mybatis.Pager;
public interface DoctorDAO
{
	/**
	 * save User
	 * 
	 * @param user
	 * @return
	 */
	public Doctor save(Doctor bean);

	public Doctor findById(Integer id);

	public Pager<Doctor> getCollectedDoctorsByUserId(Pager<Doctor> page);

	public int updateBean(Doctor bean);

	public int deleteById(Integer id);

	public List<Doctor> getAllDoctors();

	public List<Doctor> getDoctorPagerList(Pager<Doctor> pager);

	public List<Doctor> findDoctorBySearching(Map<String, Object> map);

	public void saveDoctorSubprofessional(Integer doctorId, Integer subId);

	public void saveDoctorHospital(Integer doctorId, Integer hospitalId);
	
	public Doctor isCollected(Integer userId, Integer doctorId);
}
