package com.ayida.cms.service.impl;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ayida.cms.service.SearchWordCache;
import com.ayida.cms.service.SearchWordService;

@Service(value = "searchWordCache")
@Transactional
public class SearchWordCacheImpl implements SearchWordCache
{
	private static final Logger log = LoggerFactory
			.getLogger(SearchWordCacheImpl.class);

	// 间隔时间
	private int interval = 1 * 30 * 1000; // 30秒
	// 最后刷新时间
	private long refreshTime = System.currentTimeMillis();

	@Autowired
	@Qualifier("searchWords")
	private Ehcache cache;

	@Autowired
	private SearchWordService searchWordService;

	@Override
	public void cacheWord(String name)
	{
		Element e = cache.get(name);
		// 搜索次数
		Integer hits;
		if (null != e)
		{
			hits = (Integer) e.getValue() + 1;
		}
		else
		{
			hits = 1;
		}
		cache.put(new Element(name, hits));
		/** 刷新入库 **/
		refreshToDb();
	}

	/**
	 * 刷新到数据库中
	 */
	private void refreshToDb()
	{
		long time = System.currentTimeMillis();
		if (time > refreshTime + interval)
		{
			refreshTime = time;
			int count = searchWordService.refreshToDB(cache);
			// 清除缓存
			cache.removeAll();
			log.info("refresh to DB:{}", count);
		}
	}

	/**
	 * 刷新间隔时间
	 * 
	 * @param interval
	 *            单位秒
	 */
	public void setInterval(int interval)
	{
		this.interval = interval * 1000;
	}

	/**
	 * 销毁BEAN时，缓存入库。
	 */
	public void destroy() throws Exception
	{
		int count = searchWordService.refreshToDB(cache);
		log.info("Bean destroy.refresh cache flows to DB: {}", count);
	}
}
