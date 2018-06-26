package com.ayida.cms.dao;

import java.util.List;

import com.ayida.cms.entity.disease.Disease;
import com.ayida.common.mybatis.Pager;
public interface DiseaseDAO
{
	public Disease save(Disease bean);

	public Disease findById(Integer id);

	public int updateBean(Disease bean);

	public int deleteById(Integer id);

	public Disease findByName(String name);

	public List<Disease> getAllDisease();

	public List<Disease> getDiseasePagerList(Pager<Disease> pager);

}
