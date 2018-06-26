package com.ayida.cms.service;

import java.util.List;

import com.ayida.cms.entity.hotword.RelativeSearchWord;

public interface RelativeSearchWordService
{
	public RelativeSearchWord save(Integer id, String doctorName,
			String diseaseName, String cityName, String hospitalBelongings,
			Integer professionalTitles, Integer degree);

	public List<RelativeSearchWord> findByName(String name);

	public List<RelativeSearchWord> findAll();

	public RelativeSearchWord update(RelativeSearchWord bean);

	public RelativeSearchWord delete(RelativeSearchWord bean);

	public RelativeSearchWord findById(Integer id);
}
