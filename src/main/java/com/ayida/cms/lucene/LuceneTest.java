package com.ayida.cms.lucene;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Paths;












import java.util.Random;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.ayida.common.util.LuceneUtils;



public class LuceneTest
{
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException, ParseException
	{
		String text = "Lucene 胡祖兴 是 一个医生，但那是不可能的，没有医院要他";
		Analyzer analyzer = new IKAnalyzer();
		//Directory doc = FSDirectory.open(Paths.get("D:\\lucene"));
		//boolean exist = DirectoryReader.indexExists(doc);
//		StringReader reader1 = new StringReader(text);
//		TokenStream s = analyzer.tokenStream("test", reader1);
//		while(s.incrementToken()) 
//		{
//			System.out.println(s.toString());
//		}
		Directory doc = LuceneUtils.getDirectory("D:\\lucene");
		IndexWriterConfig config = LuceneUtils.getIndexWriterConfig(analyzer);
		IndexWriter writer = LuceneUtils.getIndexWriter(doc, config);
		Document docc = new Document();
		//docc.add(new Field("textname", text, Store.YES, Index.ANALYZED));
		docc.add(new Field("textname", "成都市", Store.YES, Index.ANALYZED));
		writer.addDocument(docc);
		LuceneUtils.closeIndexWriter(writer);
		
		//search
		IndexReader reader = LuceneUtils.getIndexReader(doc);
		System.out.println("count = " + reader.numDocs() + "max = " + reader.maxDoc());
		IndexSearcher searcher = LuceneUtils.getIndexSearcher(reader);
		Query query = new TermQuery(new Term("textname", "成都市"));
		QueryParser parser = new QueryParser("textname", analyzer);
		Query q = parser.parse("要他");
		ScoreDoc[] hits = searcher.search(query, null, 1000).scoreDocs;
		//TopDocs tops = searcher.search(q, 10);
		//System.out.println("总记录数：" + tops.totalHits + "==" + hits.length);
 		for (int i = 0; i < hits.length ; i++)
 		{
 			Document hitDoc = searcher.doc(hits[i].doc);
 			System.out.println(hitDoc.get("textname"));
 		}
 		//reader.close();
 		//doc.close();
 		
	}
}
