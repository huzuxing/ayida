package com.ayida.core.security.mvc;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

@Component
public class ServletContextRealPathResolver
		implements
			RealPathResolver,
			ServletContextAware
{
	private ServletContext context;

	@Override
	public String get(String path)
	{
		String realPath = context.getRealPath(path);
		// tomcat8获取不了绝对路径，以"/"获取
		if (null == realPath)
		{
			realPath = context.getRealPath("/") + path;
		}
		return realPath;
	}

	@Override
	public void setServletContext(ServletContext arg0)
	{
		this.context = arg0;
	}

}
