package com.ayida.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ayida.cms.lucene.LuceneContentSvcImpl;
import com.ayida.common.constant.Constants;

public class PageUtils
{
	private static Logger log = LoggerFactory
			.getLogger(LuceneContentSvcImpl.class);

	/**
	 * 对分页参数校验
	 * 
	 * @param pageNo
	 *            ：当页码为null时，返回默认的页码，否则返回当前页码
	 * @return
	 */
	public static Integer cpn(Integer pageNo)
	{
		return (pageNo == null) ? Constants.DEFAULT_PAGENO : pageNo;
	}

	/**
	 * 分页参数校验
	 * 
	 * @param pageSize
	 *            ：当页容量为null时，返回默认页容量，否则返回当前页容量
	 * @return
	 */
	public static Integer cps(Integer pageSize)
	{
		return (pageSize == null) ? Constants.DEFAULT_PAGESIZE : pageSize;
	}

	/**
	 * 处理lucene查询字符串中的关键字 lucene 中保留了如下特殊字符：+ - && || ! ( ) { } [ ] ^ " ~ * ? :
	 * 
	 * @param query
	 *            ：所以对输入的关键词做相应的处理
	 * @return
	 */
	public static String parseKeyWords(String query)
	{
		char c = '\\';
		int cIndex = query.indexOf(c);
		if (-1 != cIndex && 0 == cIndex)
		{
			query = query.substring(1);
		}
		if (-1 != cIndex && cIndex == query.length() - 1)
		{
			query = query.substring(0, cIndex);
		}
		try
		{
			String regex = "[\\+\\-\\&\\|\\!\\(\\)\\{\\}\\[\\]\\^\\~\\*\\?\\:\\\\]";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(query);
			String resource = null;
			while (m.find())
			{
				resource = m.group();
				query = query.replaceAll("\\" + resource, "\\\\" + resource);
			}
			query.replaceAll("And", "and").replaceAll("Or", "or")
					.replace("Not", "not").replace("[", "［").replace("]", "］");
		}
		catch (Exception e)
		{
			log.error("key words handle error:" + e.getMessage());
		}
		return query;
	}

}
