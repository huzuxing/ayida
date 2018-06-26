package com.ayida.cms.lucene;

import java.io.IOException;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;

public interface LuceneContentDAO
{
	public Integer index(final IndexWriter writer, Integer doctorId,
			Integer startId, Integer max) throws CorruptIndexException,
			IOException;
}
