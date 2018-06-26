package com.ayida.cms.lucene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.SortField.Type;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.ayida.cms.entity.doctor.Doctor;
import com.ayida.cms.entity.hotword.RelativeSearchWord;
import com.ayida.cms.service.DoctorService;
import com.ayida.common.constant.Constants;
import com.ayida.common.mybatis.Pager;
import com.ayida.common.util.LuceneUtils;
import com.ayida.core.security.mvc.RealPathResolver;

import static com.ayida.cms.lucene.LuceneContent.DEGREE_ID;
;

@Service(value = "luceneContentSvc")
@Transactional
public class LuceneContentSvcImpl implements LuceneContentSvc
{
	private static Logger log = LoggerFactory
			.getLogger(LuceneContentSvcImpl.class);

	@Autowired
	private RealPathResolver realPathResolver;
	@Autowired
	private DoctorService doctorService;
	@Autowired
	private LuceneContentDAO luceneContentDao;

	@Transactional(readOnly = true)
	public Integer createIndex(Integer id, Integer startId, Integer max)
			throws IOException, ParseException
	{
		String path = realPathResolver.get(Constants.LUCENE_PATH);
		Directory dir = LuceneUtils.getDirectory(path);
		return createIndex(id, startId, max, dir);
	}

	@Transactional(readOnly = true)
	public Integer createIndex(Integer id, Integer startId, Integer max,
			Directory dir) throws IOException, ParseException
	{
		/** 检验索引目录是否存在 **/
		boolean exist = LuceneUtils.isIndexExist(dir);
		IndexWriter writer = new IndexWriter(dir, new IndexWriterConfig(
				Constants.LUCENE_VERSION, new IKAnalyzer()));
		try
		{
			if (exist)
			{
				LuceneContent.delete(id, writer);
			}
			Integer lastId = luceneContentDao.index(writer, id, startId, max);
			return lastId;
		}
		finally
		{
			LuceneUtils.closeIndexWriter(writer);
		}
	}

	@Transactional(readOnly = true)
	public void createIndex(RelativeSearchWord content, Directory dir)
			throws IOException
	{
		IndexWriter writer = new IndexWriter(dir, new IndexWriterConfig(
				Constants.LUCENE_VERSION, new IKAnalyzer()));
		try
		{
			writer.addDocument(LuceneContent.createDocument(content));
		}
		finally
		{
			LuceneUtils.closeIndexWriter(writer);
		}
	}

	@Transactional(readOnly = true)
	public void createIndex(RelativeSearchWord content) throws IOException
	{
		String path = realPathResolver.get(Constants.LUCENE_PATH);
		Directory dir = LuceneUtils.getDirectory(path);
		createIndex(content, dir);
	}

	@Transactional(readOnly = true)
	public void deleteIndex(Integer id) throws IOException, ParseException
	{
		String path = realPathResolver.get(Constants.LUCENE_PATH);
		Directory dir = LuceneUtils.getDirectory(path);
		deleteIndex(id, dir);
	}

	@Transactional(readOnly = true)
	public void deleteIndex(Integer id, Directory dir) throws IOException,
			ParseException
	{
		boolean exist = LuceneUtils.isIndexExist(dir);
		if (exist)
		{
			IndexWriterConfig config = new IndexWriterConfig(
					Constants.LUCENE_VERSION, new IKAnalyzer());
			IndexWriter writer = new IndexWriter(dir, config);
			try
			{
				LuceneContent.delete(id, writer);
			}
			catch (Exception e)
			{
				log.error("%s delete index document failed...");
			}
			finally
			{
				LuceneUtils.closeIndexWriter(writer);
			}
		}
	}

	@Override
	public void updateIndex(RelativeSearchWord content) throws IOException,
			ParseException
	{
		String path = realPathResolver.get(Constants.LUCENE_PATH);
		Directory dir = LuceneUtils.getDirectory(path);
		updateIndex(content, dir);
	}

	@Override
	public void updateIndex(RelativeSearchWord content, Directory dir)
			throws IOException, ParseException
	{
		boolean exist = LuceneUtils.isIndexExist(dir);
		IndexWriter writer = new IndexWriter(dir, new IndexWriterConfig(
				Constants.LUCENE_VERSION, new IKAnalyzer()));
		try
		{
			if (exist)
			{
				LuceneContent.delete(content.getId(), writer);
			}
			writer.addDocument(LuceneContent.createDocument(content));
		}
		finally
		{
			LuceneUtils.closeIndexWriter(writer);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ayida.cms.lucene.LuceneContentSvc#searchList(org.apache.lucene.store
	 * .Directory, java.lang.String, java.lang.Integer, java.lang.Integer,
	 * java.lang.Integer)
	 */
	@Transactional(readOnly = true)
	public List<Doctor> searchList(Directory dir, String query,
			boolean smartSort, Integer id, String cityName, String hospital_P,
			String hospital_S, boolean viceSenior, boolean degree,
			Integer first, Integer max) throws CorruptIndexException,
			IOException, ParseException
	{
		IndexReader reader = DirectoryReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		try
		{
			Query querySearch = LuceneContent.createQuery(query, id,
					hospital_P, hospital_S, cityName, viceSenior,
					new StandardAnalyzer());
			if (null == first || first < 0)
			{
				first = 0;
			}
			if (null == max || max < 0)
			{
				max = 0;
			}
			TopDocs topDocs = searcher.search(querySearch, first + max);
			/** 获取查询到的ID集合 **/
			List<Integer> ids = LuceneContent.getResultIdsList(searcher,
					topDocs, first, max);
			/** 指定大小的ArrayList **/
			List<Doctor> doctorList = new ArrayList<Doctor>(ids.size());
			/** 遍历ID集合，并查询数据库，将查询到的信息放到List中返回 **/
			Map<String, Object> map = new HashMap<String, Object>();
			ids.forEach(dId -> {
				map.put("id", dId);
				/** 将直接查询到的医生装进list **/
				doctorList.addAll(doctorService.findDoctorBySearching(map));
			});
			return doctorList;
		}
		finally
		{
			/** 最后显示地关闭reader **/
			LuceneUtils.closeIndexReader(reader);
		}
	}

	@Override
	public void deleteIndex(RelativeSearchWord content) throws IOException,
			ParseException
	{

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ayida.cms.lucene.LuceneContentSvc#getPagerList(java.lang.String,
	 * java.lang.String, java.lang.Integer, java.lang.String, java.lang.Integer,
	 * java.lang.Integer)
	 */
	@Transactional(readOnly = true)
	public Pager<?> getPagerList(String path, String query, boolean smartSort,
			Integer id, String cityName, String hospital_P, String hospital_S,
			boolean viceSenior, boolean degree, Integer pageNo, Integer pageSize)
			throws CorruptIndexException, IOException, ParseException
	{
		return getPagerList(LuceneUtils.getDirectory(path), query, smartSort,
				id, cityName, hospital_P, hospital_S, viceSenior, degree,
				pageNo, pageSize);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ayida.cms.lucene.LuceneContentSvc#getPagerList(org.apache.lucene.
	 * store.Directory, java.lang.String, java.lang.Integer, java.lang.String,
	 * java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("deprecation")
	@Transactional(readOnly = true)
	public Pager<?> getPagerList(Directory dir, String query,
			boolean smartSort, Integer id, String cityName, String hospital_P,
			String hospital_S, boolean viceSenior, boolean degree,
			Integer pageNo, Integer pageSize) throws CorruptIndexException,
			IOException, ParseException
	{
		IndexReader reader = DirectoryReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		try
		{
			Analyzer analyzer = new IKAnalyzer(true);
			Query querySearch = LuceneContent.createQuery(query, null,
					cityName, hospital_P, hospital_S, viceSenior, analyzer);
			TopDocs docs = null;
			/** 增加排序，以后可以自定义排序 **/
			if (degree)
			{
				Sort sort = new Sort();
				// 学历降序排序
				SortField degreeField = new SortField(DEGREE_ID, Type.BYTE,
						true);
				sort.setSort(degreeField);
				docs = searcher.search(querySearch, pageNo * pageSize, sort);
			}
			else
			{
				docs = searcher.search(querySearch, pageNo * pageSize);
			}
			Pager<Integer> p = LuceneContent.getPagerList(searcher, docs,
					pageNo, pageSize);
			List<Integer> list = p.getResults();
			List<Doctor> doctors = new ArrayList<Doctor>();
			/** 根据ID查询Doctor **/
			list.forEach(dId -> {
				doctors.add(doctorService.findById(dId));
			});
			/** 将分页数据封装进Pager对象中并返回 **/
			Pager<Doctor> page = new Pager<Doctor>();
			page.setPageNo(p.getPageNo());
			page.setPageSize(p.getPageSize());
			page.setTotalCount(p.getTotalCount());
			page.setParams(p.getParams());
			page.setResults(doctors);
			return page;
		}
		catch (ParseException e)
		{
			log.error("query failed in getPagerList:" + e.getMessage());
		}
		return null;
	}

	@Override
	public List<Doctor> searchList(String path, String query,
			boolean smartSort, Integer id, String cityName, String hospital_P,
			String hospital_S, boolean viceSenior, boolean degree,
			Integer pageNo, Integer pageSize) throws CorruptIndexException,
			IOException, ParseException
	{
		// TODO Auto-generated method stub
		return null;
	}

}
