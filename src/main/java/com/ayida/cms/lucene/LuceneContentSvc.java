package com.ayida.cms.lucene;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.Directory;

import com.ayida.cms.entity.doctor.Doctor;
import com.ayida.cms.entity.hotword.RelativeSearchWord;
import com.ayida.common.mybatis.Pager;
public interface LuceneContentSvc
{
	public Integer createIndex(Integer id, Integer startId, Integer max)
			throws IOException, ParseException;

	public Integer createIndex(Integer id, Integer startId, Integer max,
			Directory dir) throws IOException, ParseException;

	public void createIndex(RelativeSearchWord content, Directory dir)
			throws IOException;

	public void createIndex(RelativeSearchWord content) throws IOException;

	public void deleteIndex(RelativeSearchWord content) throws IOException,
			ParseException;

	public void deleteIndex(Integer id, Directory dir) throws IOException,
			ParseException;

	public void updateIndex(RelativeSearchWord content) throws IOException,
			ParseException;

	public void updateIndex(RelativeSearchWord content, Directory dir)
			throws IOException, ParseException;

	public Pager<?> getPagerList(String path, String query, boolean smartSort, Integer id,
			String cityName, String hospital_P, String hospital_S,boolean viceSenior, 
			boolean degree, Integer pageNo, Integer pageSize) throws CorruptIndexException,
			IOException, ParseException;
	
	public Pager<?> getPagerList(Directory dir, String query, boolean smartSort, Integer id,
			String cityName, String hospital_P, String hospital_S,boolean viceSenior, 
			boolean degree, Integer pageNo, Integer pageSize) throws CorruptIndexException,
			IOException, ParseException;

	public List<Doctor> searchList(String path, String query, boolean smartSort, Integer id,
			String cityName, String hospital_P, String hospital_S,boolean viceSenior, 
			boolean degree, Integer pageNo, Integer pageSize) throws CorruptIndexException,
			IOException, ParseException;

	public List<Doctor> searchList(Directory dir, String query, boolean smartSort, Integer id,
			String cityName, String hospital_P, String hospital_S,boolean viceSenior, 
			boolean degree, Integer first, Integer max)
			throws CorruptIndexException, IOException, ParseException;

}
