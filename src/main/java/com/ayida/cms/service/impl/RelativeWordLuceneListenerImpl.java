package com.ayida.cms.service.impl;

import java.io.IOException;
import java.util.Map;

import org.apache.lucene.queryparser.classic.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ayida.cms.entity.hotword.RelativeSearchWord;
import com.ayida.cms.lucene.LuceneContentSvc;
import com.ayida.cms.service.RelativeWordListenerAbstract;

@Component(value = "relativeWordListener")
public class RelativeWordLuceneListenerImpl
		extends
			RelativeWordListenerAbstract
{

	private static final Logger log = LoggerFactory
			.getLogger(RelativeWordLuceneListenerImpl.class);

	@Autowired
	private LuceneContentSvc luceneContentSvc;

	@Override
	public void preSave(RelativeSearchWord bean)
	{
		super.preSave(bean);
	}

	@Override
	public void afterSave(RelativeSearchWord bean)
	{
		try
		{
			/** 保存成功后创建索引 **/
			luceneContentSvc.createIndex(bean);
		}
		catch (IOException e)
		{
			log.error("create index failed after save bean:", e);
		}
	}

	@Override
	public Map<String, Object> preChange(RelativeSearchWord bean)
	{
		return super.preChange(bean);
	}

	@Override
	public void afterChange(RelativeSearchWord bean, Map<String, Object> map)
	{
		try
		{
			/** 内容更改后更新索引 **/
			luceneContentSvc.updateIndex(bean);
		}
		catch (IOException e)
		{
			log.error("", e);
		}
		catch (ParseException e)
		{
			log.error("", e);
		}
	}

	@Override
	public void preDelete(RelativeSearchWord bean)
	{
		super.preDelete(bean);
	}

	@Override
	public void afterDelete(RelativeSearchWord bean)
	{
		try
		{
			/** 删除内容后，删除索引 **/
			luceneContentSvc.deleteIndex(bean);
		}
		catch (IOException e)
		{
			log.error("", e);
		}
		catch (ParseException e)
		{
			log.error("", e);
		}
	}

}
