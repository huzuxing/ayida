package com.ayida.cms.service.impl;

import java.util.List;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ayida.cms.dao.SearchWordDAO;
import com.ayida.cms.entity.hotword.SearchWord;
import com.ayida.cms.service.SearchWordService;
import com.ayida.common.util.ChineseCharToEn;

@Service(value = "searchWordSerivce")
@Transactional
public class SearchWordServiceImpl implements SearchWordService
{

	@Autowired
	private SearchWordDAO searchDao;

	@Transactional
	public SearchWord save(SearchWord bean)
	{
		bean.setNameInitial(ChineseCharToEn.getAllFirstLetter(bean.getName()));
		bean.init();
		return searchDao.save(bean);
	}

	@Transactional
	public int updateSearchWord(SearchWord bean)
	{
		return searchDao.updateSearchWord(bean);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int refreshToDB(Ehcache cache)
	{
		int count = 0;
		List<String> list = cache.getKeys();
		for (String word : list)
		{
			Element e = cache.get(word);
			if (null == e)
			{
				return count;
			}
			count = (Integer) e.getValue();
			SearchWord searchWord = findByName(word);
			if (null != searchWord)
			{
				searchWord.setSearchCount(count + searchWord.getSearchCount());
				updateSearchWord(searchWord);
			}
			else
			{
				searchWord = new SearchWord();
				searchWord.setName(word);
				searchWord.setSearchCount(count);
				save(searchWord);
			}
		}
		return count;
	}

	@Override
	public SearchWord findByName(String name)
	{
		return searchDao.findByName(name);
	}

	@Override
	public List<SearchWord> findListByName(String name)
	{
		return searchDao.findListByName(name);
	}

}
