package com.ayida.core.security.mvc;

/**
 * 据对路径提供者
 * 
 * @author John Hu
 *
 */
public interface RealPathResolver
{
	/**
	 * 获取绝对路径
	 * 
	 * @param path
	 * @return
	 */
	public String get(String path);
}
