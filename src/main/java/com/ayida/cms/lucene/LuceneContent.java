package com.ayida.cms.lucene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.NumericTokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ayida.cms.entity.hotword.RelativeSearchWord;
import com.ayida.common.constant.Constants;
import com.ayida.common.mybatis.Pager;
import com.ayida.common.util.LuceneUtils;

/**
 * @author John Hu
 *
 */
public class LuceneContent
{
	private static final Logger log = LoggerFactory
			.getLogger(LuceneContent.class);

	public static final String ID = "id";

	public static final String DOCTOR_NAME = "doctorName";

	public static final String DISEASE_NAME = "diseaseName";

	public static final String DOCTOR_NAMEINITIAL = "doctorNameInitial";

	public static final String DISEASE_NAMEINITIAL = "diseaseNameInitial";

	public static final String CITY_NAME = "cityName";

	public static final String HOSPITAL_BELONGINGS = "hospitalBelongings";

	public static final String PROFESSIONAL_TITLES = "professionalTitles";

	public static final String DEGREE_ID = "degreeId";

	public static final String[] QUERY_FIELD = {DOCTOR_NAME, DISEASE_NAME,
			DOCTOR_NAMEINITIAL, DISEASE_NAMEINITIAL};

	public static final BooleanClause.Occur[] QUERY_FLAGS = {
			BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD,
			BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD};

	/**
	 * 创建lucene格式的document
	 * 
	 * @param word
	 *            相关词对象
	 * @return
	 */
	@SuppressWarnings({"deprecation", "resource"})
	public static Document createDocument(RelativeSearchWord word)
	{
		if (null == word)
		{
			throw new IllegalArgumentException(
					"RelativeSearchWord can not be null in createDocument");
		}
		Document doc = new Document();
		doc.add(new Field(ID, word.getId().toString(), Store.YES,
				Index.NOT_ANALYZED));
		doc.add(new Field(DOCTOR_NAME, word.getDoctorName(), Store.YES,
				Index.ANALYZED));
		doc.add(new Field(DISEASE_NAME, word.getDiseaseName(), Store.YES,
				Index.ANALYZED));
		doc.add(new Field(DOCTOR_NAMEINITIAL, word.getDoctorNameInitial(),
				Store.YES, Index.ANALYZED));
		doc.add(new Field(DISEASE_NAMEINITIAL, word.getDiseaseNameInitial(),
				Store.YES, Index.ANALYZED));
		doc.add(new Field(CITY_NAME, word.getCityName(), Store.YES,
				Index.ANALYZED));
		doc.add(new Field(HOSPITAL_BELONGINGS, word.getHospitalBelongings(),
				Store.YES, Index.ANALYZED));
		doc.add(new Field(PROFESSIONAL_TITLES, new NumericTokenStream()
				.setIntValue(word.getProfessionalTitles())));
		doc.add(new Field(DEGREE_ID, word.getDegree().toString(), Store.YES,
				Index.NOT_ANALYZED));
		return doc;
	}

	/**
	 * 删除索引
	 * 
	 * @param doctorId
	 * @param writer
	 * @throws IOException
	 * @throws ParseException
	 */
	public static void delete(Integer id, IndexWriter writer)
			throws IOException, ParseException
	{
		// writer.deleteDocuments(createQuery(null, doctorId, null));
		// writer.deleteDocuments(new Term(ID, doctorId.toString()));
		// 调用LuceneUtils工具类删除索引
		LuceneUtils.deleteIndex(writer, ID, id.toString());
	}

	/**
	 * @param queries
	 * @param id
	 * @param analyzer
	 * @return
	 * @throws ParseException
	 */
	public static Query createQuery(String query, Integer id, String cityName,
			String hospital_P, String hospital_S, boolean viceSenior,
			Analyzer analyzer) throws ParseException
	{
		List<String> more = new ArrayList<String>(2);
		BooleanQuery bq = new BooleanQuery();
		Query q;
		if (StringUtils.isNotBlank(query))
		{
			q = MultiFieldQueryParser.parse(query, QUERY_FIELD, QUERY_FLAGS,
					analyzer);
			bq.add(q, BooleanClause.Occur.MUST);
		}
		if (StringUtils.isNotBlank(cityName))
		{
			q = new TermQuery(new Term(CITY_NAME, cityName));
			bq.add(q, BooleanClause.Occur.MUST);
		}
		if (StringUtils.isNotBlank(hospital_P))
		{
			more.add(hospital_P);
		}
		if (StringUtils.isNotBlank(hospital_S))
		{
			more.add(hospital_S);
		}
		int size = more.size();
		if (size > 0)
		{
			String[] belongings = new String[size];
			if (1 == size)
			{
				q = MultiFieldQueryParser.parse(more.get(0), new String[]{
						HOSPITAL_BELONGINGS, HOSPITAL_BELONGINGS},
						new BooleanClause.Occur[]{BooleanClause.Occur.SHOULD,
								BooleanClause.Occur.SHOULD}, analyzer);
				bq.add(q, BooleanClause.Occur.MUST);
			}
			else
			{
				q = MultiFieldQueryParser.parse(more.toArray(belongings),
						new String[]{HOSPITAL_BELONGINGS, HOSPITAL_BELONGINGS},
						new BooleanClause.Occur[]{BooleanClause.Occur.SHOULD,
								BooleanClause.Occur.SHOULD}, analyzer);
				bq.add(q, BooleanClause.Occur.MUST);
			}
		}
		if (viceSenior)
		{
			q = NumericRangeQuery.newIntRange(PROFESSIONAL_TITLES,
					Constants.ASSOCIATION_CHIEF_PHYSICIAN,
					Constants.CHIEF_PHYSICIAN, true, true);
			bq.add(q, BooleanClause.Occur.MUST);
		}
		if (null != id)
		{
			q = new TermQuery(new Term(ID, id.toString()));
			bq.add(q, BooleanClause.Occur.MUST);
		}
		return bq;
	}
	/**
	 * lucene分页查询 获得的是ID集合
	 * 
	 * @param searcher
	 * @param topDocs
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws IOException
	 */
	public static Pager<Integer> getPagerList(IndexSearcher searcher,
			TopDocs topDocs, Integer pageNo, Integer pageSize)
			throws IOException
	{
		List<Integer> list = new ArrayList<Integer>();
		ScoreDoc[] scores = topDocs.scoreDocs;
		Integer endIndex = pageNo * pageSize;
		int len = scores.length;
		if (endIndex > len)
			endIndex = len;
		try
		{
			for (int i = (pageNo - 1) * pageSize; i < endIndex; i++)
			{
				Document d = searcher.doc(scores[i].doc);
				list.add(Integer.valueOf(d.getField(ID).stringValue()));
			}
		}
		catch (NumberFormatException e)
		{
			log.error("not number format:" + e.getMessage());
			throw new IllegalArgumentException();
		}
		Pager<Integer> page = new Pager<Integer>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setTotalCount(topDocs.totalHits);
		page.setResults(list);
		return page;
	}

	/**
	 * 获取id集合，便于从数据库查询信息
	 * 
	 * @param searcher
	 * @param topDocs
	 * @param first
	 * @param max
	 * @return
	 * @throws IOException
	 */
	public static List<Integer> getResultIdsList(IndexSearcher searcher,
			TopDocs topDocs, Integer first, Integer max) throws IOException
	{
		/** 声明一个指定大小的ArrayList **/
		List<Integer> idsList = new ArrayList<Integer>(max);
		ScoreDoc[] docs = topDocs.scoreDocs;
		if (null == first || first < 0)
		{
			first = 0;
		}
		if (null == max || max < 0)
		{
			max = 0;
		}
		int last = first + max;
		int len = docs.length;
		if (last > len)
		{
			last = len;
		}
		/** 这里将Integer.valueOf方法try-catch，规范 **/
		try
		{
			for (int i = 0; i < last; i++)
			{
				Document doc = searcher.doc(docs[i].doc);
				idsList.add(Integer.valueOf(doc.getField(ID).stringValue()));
			}
		}
		catch (NumberFormatException e)
		{
			log.error("Integer.valuerOf(doc.getField(ID).stringValue():error number String):"
					+ e.getMessage());
			throw new RuntimeException(e);
		}
		return idsList;
	}
}
