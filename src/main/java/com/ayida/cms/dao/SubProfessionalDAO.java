package com.ayida.cms.dao;

import java.util.List;

import com.ayida.cms.entity.doctor.SubProfessional;
import com.ayida.common.mybatis.Pager;

public interface SubProfessionalDAO
{
	public SubProfessional save(SubProfessional bean);
	
	public void saveSubDisease(Integer subId, Integer diseaseId);

	public SubProfessional findById(Integer id);

	public int updateBean(SubProfessional bean);

	public int deleteById(Integer id);

	public SubProfessional findByName(String name);

	public List<SubProfessional> getAllSubProfessional();

	public List<SubProfessional> getSubProfessionalPagerList(
			Pager<SubProfessional> pager);
}
