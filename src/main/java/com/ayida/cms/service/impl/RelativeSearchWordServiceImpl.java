package com.ayida.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ayida.cms.dao.RelativeSearchWordDAO;
import com.ayida.cms.entity.hotword.RelativeSearchWord;
import com.ayida.cms.service.RelativeSearchWordService;
import com.ayida.cms.service.RelativeWordLuceneListener;
import com.ayida.common.util.ChineseCharToEn;

@Service(value = "relativeService")
@Transactional
public class RelativeSearchWordServiceImpl implements RelativeSearchWordService
{
	@Autowired
	private RelativeSearchWordDAO relativeWordDao;

	@Autowired
	private RelativeWordLuceneListener relativeWordListener;

	@Override
	public RelativeSearchWord save(Integer id, String doctorName,
			String diseaseName, String cityName, String hospitalBelongings,
			Integer professionalTitles, Integer degree)
	{
		RelativeSearchWord bean = new RelativeSearchWord();
		bean.setId(id);
		bean.setDoctorName(doctorName);
		/** 保存汉字首字母 **/
		bean.setDoctorNameInitial(ChineseCharToEn.getAllFirstLetter(doctorName));
		bean.setDiseaseName(diseaseName);
		bean.setDiseaseNameInitial(ChineseCharToEn
				.getAllFirstLetter(diseaseName));
		bean.setCityName(cityName);
		bean.setProfessionalTitles(professionalTitles);
		bean.setDegree(degree);
		bean.setHospitalBelongings(hospitalBelongings);
		relativeWordDao.save(bean);
		/** 执行监听(创建索引) **/
		afterSave(bean);
		return bean;
	}

	@Override
	public List<RelativeSearchWord> findByName(String name)
	{
		return relativeWordDao.findByName(name);
	}

	@Override
	public List<RelativeSearchWord> findAll()
	{
		return relativeWordDao.findAll();
	}

	@Override
	public RelativeSearchWord update(RelativeSearchWord bean)
	{
		/** 获得汉字首字母 **/
		bean.setDoctorNameInitial(ChineseCharToEn.getAllFirstLetter(bean
				.getDoctorName()));
		bean.setDiseaseNameInitial(ChineseCharToEn.getAllFirstLetter(bean
				.getDiseaseName()));
		RelativeSearchWord entity = relativeWordDao.update(bean);
		/** 执行监听(更新索引) **/
		afterUpdate(entity);
		return entity;
	}

	@Override
	public RelativeSearchWord delete(RelativeSearchWord bean)
	{
		if (null != bean)
		{
			relativeWordDao.delete(bean.getId());
			/** 执行监听(删除索引) **/
			afterDelete(bean);
		}
		return bean;
	}

	private void afterSave(RelativeSearchWord bean)
	{
		relativeWordListener.afterSave(bean);
	}

	private void afterUpdate(RelativeSearchWord bean)
	{
		relativeWordListener.afterChange(bean, null);
	}

	private void afterDelete(RelativeSearchWord bean)
	{
		relativeWordListener.afterDelete(bean);
	}

	@Override
	public RelativeSearchWord findById(Integer id)
	{
		return relativeWordDao.findById(id);
	}
}
