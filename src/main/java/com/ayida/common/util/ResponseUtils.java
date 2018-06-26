package com.ayida.common.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author John Hu
 *
 */
public final class ResponseUtils
{
	private static final Logger log = LoggerFactory
			.getLogger(ResponseUtils.class);

	/**
	 * 响应文本字符串的render
	 * 
	 * @param response
	 * @param text
	 */
	public static void renderText(HttpServletResponse response, String text)
	{
		render(response, "text/plain;charset=UTF-8", text);
	}

	/**
	 * 返回json的render
	 * 
	 * @param response
	 * @param text
	 */
	public static void renderJson(HttpServletResponse response, String text)
	{
		render(response, "application/json;charset=UTF-8", text);
	}

	/**
	 * 返回xml的render
	 * 
	 * @param response
	 * @param text
	 */
	public static void renderXml(HttpServletResponse response, String text)
	{
		render(response, "text/xml;charset=UTF-8", text);
	}

	/**
	 * 向客户端响应对应的内容
	 * 
	 * @param response
	 * @param contentType
	 * @param text
	 */
	public static void render(HttpServletResponse response, String contentType,
			String text)
	{
		response.setContentType(contentType);
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "No-cache");
		response.setDateHeader("Expires", 0);
		PrintWriter out = null;
		try
		{
			out = response.getWriter();
			out.write(text);
			out.flush();
			out.close();
		}
		catch (IOException e)
		{
			log.error("render text failed:" + e.getMessage());
		}
	}
	
	/**
	 * 获取json返回类型的消息头
	 * @return
	 */
	public static HttpHeaders getJsonHttpHeaders()
	{
		HttpHeaders header = new HttpHeaders();
		header.set("Content-Type", "application/json;charset=UTF-8");
		return header;
	}
	
	/**
	 * 返回json
	 * @param obj
	 * @return
	 */
	public static String getJson(Object obj)
	{
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation() // 不导出实体中没有用@Expose注解的属性
				.enableComplexMapKeySerialization() // 支持Map的key为复杂对象的形式
				.serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss")// 时间转化为特定格式
				.setPrettyPrinting() // 对json结果格式化.
				.setVersion(1.0).create();
		return gson.toJson(obj);
	}
}
