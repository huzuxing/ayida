package com.ayida.common.constant;

import org.apache.lucene.util.Version;

/**
 * 常量类，定义为Abstract 类，防止实例化
 * 
 * @author John Hu
 *
 */
public abstract class Constants
{
	/**
	 * 全文检索索引路径
	 */
	public static final String LUCENE_PATH = "/WEB-INF/lucene";

	/**
	 * 分页默认页码
	 */
	public static final Integer DEFAULT_PAGENO = 1;

	/**
	 * 分页默认页容量
	 */
	public static final Integer DEFAULT_PAGESIZE = 5;

	/**
	 * post 请求
	 */
	public static final String POST = "POST";

	/**
	 * get 请求
	 */
	public static final String GET = "GET";

	/**
	 * UTF-8 字符集
	 */
	public static final String UTF8 = "UTF-8";
	
	/**
	 * 通用错误/失败状态码
	 */
	public static final Integer FAILD = 600;
	
	/**
	 * 通用成功状态码
	 */
	public static final Integer SUCCESS = 200;
	
	/**
	 * 无学历
	 */
	public static final Integer NONE = 0;
	
	/**
	 * 中专学历
	 */
	public static final Integer TEC_SEC_SCHOOL = 1;
	
	/**
	 * 高中学历
	 */
	public static final Integer SENIOR = 2;
	
	/**
	 * 大专学历
	 */
	public static final Integer COLLEGE = 3;
	
	/**
	 * 本科学历
	 */
	public static final Integer UNIVERSITY = 4;
	
	/**
	 * 研究生学历
	 */
	public static final Integer POSTGRADUATE = 5;
	
	/**
	 * 博士学历
	 */
	public static final Integer DOCTOR = 6;
	
	/**
	 * 一般医师---初级职称
	 */
	public static final Integer NORMAL_DOCTOR = 1;
	
	/**
	 *主治医师 ---中级职称
	 */
	public static final Integer ATTENDING_PHYSICIAN = 2;
	
	/**
	 * 副主任医师---副高职称
	 */
	public static final Integer ASSOCIATION_CHIEF_PHYSICIAN = 3;
	
	/**
	 * 主任医生---正高职称
	 */
	public static final Integer CHIEF_PHYSICIAN = 4;
	
	/**
	 * lucene version
	 */
	public static final Version LUCENE_VERSION = Version.LUCENE_4_10_1;
	
}
