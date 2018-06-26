package com.ayida.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UrlPathHelper;

import static com.ayida.common.constant.Constants.POST;
import static com.ayida.common.constant.Constants.UTF8;
/**
 * HttpServletRequest 帮助类
 * 
 * @author John Hu
 *
 */
public class RequestUtils
{
	private static final Logger log = LoggerFactory
			.getLogger(RequestUtils.class);

	/**
	 * 获取用户的真实ip
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request)
	{
		String ip = request.getHeader("X-Real-IP");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip))
		{
			return ip;
		}
		// 当是代理服务器时，请求消息头会多 X-Forwarded-For ，记录请求ip，和目的主机ip
		ip = request.getHeader("X-Forwarded-For");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip))
		{
			// 多次反向代理会有多个IP值，第一个非 unknown 为真实IP
			int index = ip.lastIndexOf(",");
			if (-1 != index)
			{
				ip = ip.substring(0, index);
				return ip;
			}
		}
		ip = request.getRemoteAddr();
		// 当ip为0:0:0:0:0:0:0:1时，是访问使用了localhost,用127.0.0.1可以避免
		if ("0:0:0:0:0:0:0:1".equals(ip))
		{
			ip = "127.0.0.1";
		}
		return ip;
	}

	/**
	 * 获取请求参数
	 * 
	 * @param request
	 * @param param
	 * @return
	 */
	public static String getQueryParam(HttpServletRequest request, String param)
	{
		if (StringUtils.isBlank(param))
		{
			return null;
		}
		/** post请求时，直接request.getParameter() **/
		if (POST.equalsIgnoreCase(request.getMethod()))
		{
			return request.getParameter(param);
		}
		String s = request.getQueryString();
		if (StringUtils.isBlank(s))
		{
			return null;
		}
		try
		{
			s = URLDecoder.decode(s, "UTF-8");
		}
		catch (UnsupportedEncodingException e)
		{
			log.error("param decode failed,not supported character{" + UTF8
					+ "}", e);
		}
		String[] values = parseQueryString(s).get(param);
		if (null != values && values.length > 0)
		{
			return values[values.length - 1];
		}
		else
		{
			return null;
		}
	}

	public static Map<String, String[]> parseQueryString(String s)
	{
		String[] valArray = null;
		if (null == s)
		{
			throw new IllegalArgumentException("param can not be null");
		}
		Map<String, String[]> ht = new HashMap<String, String[]>();
		String[] st = s.split("&");
		for (String str : st)
		{
			int index = str.indexOf("=");
			if (-1 == index)
			{
				continue;
			}
			String key = str.substring(0, index);
			String value = str.substring(index + 1, str.length());
			if (ht.containsKey(key))
			{
				String[] oldVals = ht.get(key);
				valArray = new String[oldVals.length + 1];
				int i = 0;
				for (String os : oldVals)
				{
					valArray[i] = os;
					i++;
				}
				valArray[oldVals.length] = value;
			}
			else
			{
				valArray = new String[1];
				valArray[0] = value;
			}
			ht.put(key, valArray);
		}
		return ht;
	}
	
	/**
	 * 获取当前访问位置
	 * HttpServletRequest.getRequestURL+"?"+HttpServletRequest.getQueryString
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String getLocation(HttpServletRequest request)
	{
		UrlPathHelper helper = new UrlPathHelper();
		StringBuffer bf = request.getRequestURL();
		String uri = request.getRequestURI();
		String origUri = helper.getOriginatingRequestUri(request);
		bf.replace(bf.length() - uri.length(), bf.length(), origUri);
		String queryString = helper.getOriginatingQueryString(request);
		if (null != queryString) 
		{
			bf.append("?").append(queryString);
		}
		return bf.toString();
	}
}
