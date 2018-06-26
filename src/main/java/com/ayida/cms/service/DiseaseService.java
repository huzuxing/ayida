package com.ayida.cms.service;

import java.util.List;
import java.util.Map;

import com.ayida.cms.entity.disease.Disease;

public interface DiseaseService
{
	public Disease save(Disease bean);

	public Disease findById(Integer id);

	public int updateBean(Disease bean);

	public int deleteById(Integer id);

	public Disease findByName(String name);

	public List<Disease> getAllDisease();

	public List<Disease> getDiseasePagerList(Integer pageNo, Integer pageSize,
			Map<String, Object> params);
}
