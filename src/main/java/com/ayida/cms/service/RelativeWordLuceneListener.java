package com.ayida.cms.service;

import java.util.Map;

import com.ayida.cms.entity.hotword.RelativeSearchWord;

/**
 * 内容监听器
 * 
 * @author John Hu
 *
 */
public interface RelativeWordLuceneListener
{
	/**
	 * 保存之前执行
	 * 
	 * @param bean
	 */
	public void preSave(RelativeSearchWord bean);

	/**
	 * 保存之后执行，如创建索引
	 * 
	 * @param bean
	 */
	public void afterSave(RelativeSearchWord bean);

	/**
	 * 修改之前执行
	 * 
	 * @param bean
	 * @return 获取一些需要在afterChange中使用的值
	 */
	public Map<String, Object> preChange(RelativeSearchWord bean);

	/**
	 * 修改之后执行
	 * 
	 * @param bean
	 */
	public void afterChange(RelativeSearchWord bean, Map<String, Object> map);

	/**
	 * 删除之前执行
	 * 
	 * @param bean
	 */
	public void preDelete(RelativeSearchWord bean);

	/**
	 * 删除之后执行
	 * 
	 * @param bean
	 */
	public void afterDelete(RelativeSearchWord bean);
}
