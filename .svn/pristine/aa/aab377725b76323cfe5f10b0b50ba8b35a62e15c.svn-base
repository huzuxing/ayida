package com.ayida.cms.lucene;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ayida.cms.dao.RelativeSearchWordDAO;
import com.ayida.cms.entity.hotword.RelativeSearchWord;

@Repository(value = "luceneContentDao")
public class LuceneContentDAOImpl implements LuceneContentDAO
{
	@Autowired
	private RelativeSearchWordDAO relativeWordDao;

	/**
	 * (non-Javadoc) 创建索引DAO 思路： 1、查询所有的所需要的信息 2、将查询出来的信息创建成索引文件
	 * 
	 * @see com.ayida.cms.lucene.LuceneContentDAO#index(org.apache.lucene.index.
	 *      IndexWriter, java.lang.Integer, java.lang.Integer,
	 *      java.lang.Integer)
	 */
	@Override
	public Integer index(IndexWriter writer, Integer id, Integer startId,
			Integer max) throws CorruptIndexException, IOException
	{
		List<RelativeSearchWord> words = relativeWordDao.findAll();
		int count = 0;
		RelativeSearchWord word = null;
		for (RelativeSearchWord w : words)
		{
			word = w;
			writer.addDocument(LuceneContent.createDocument(word));
			++count;
		}
		if (count < max || null == word)
		{
			// 当实际数量小于指定数量，或者没有任何数据，代表生成结束
			return null;
		}
		else
		{
			// 返回最后一个id
			return word.getId();
		}
	}
}
