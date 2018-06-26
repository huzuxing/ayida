package com.ayida.common.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet Filter implementation class ProcessTimeFilter, process time filter
 */
@WebFilter("/ProcessTimeFilter")
public class ProcessTimeFilter implements Filter
{
	private static final Logger log = LoggerFactory
			.getLogger(ProcessTimeFilter.class);

	private static String STRAT_TIME = "_start_time";
	/**
	 * Default constructor.
	 */
	public ProcessTimeFilter()
	{
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy()
	{
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse response,
			FilterChain chain) throws IOException, ServletException
	{
		HttpServletRequest request = (HttpServletRequest) req;
		long time = System.currentTimeMillis();
		request.setAttribute(STRAT_TIME, time);
		time = System.currentTimeMillis();
		log.debug("process in {} ms: {}", time, request.getRequestURI());
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException
	{
	}
}
