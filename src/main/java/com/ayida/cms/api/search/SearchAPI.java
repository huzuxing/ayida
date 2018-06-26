package com.ayida.cms.api.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryparser.classic.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ayida.cms.entity.doctor.Doctor;
import com.ayida.cms.entity.hotword.HotWord;
import com.ayida.cms.entity.hotword.SearchWord;
import com.ayida.cms.lucene.LuceneContentSvc;
import com.ayida.cms.lucene.LuceneContentSvcImpl;
import com.ayida.cms.service.HotWordService;
import com.ayida.cms.service.SearchWordCache;
import com.ayida.cms.service.SearchWordService;
import com.ayida.common.constant.Constants;
import com.ayida.common.mybatis.Pager;
import com.ayida.common.util.PageUtils;
import com.ayida.common.util.RequestUtils;
import com.ayida.common.util.ResponseUtils;
import com.ayida.core.security.mvc.RealPathResolver;
import com.ayida.core.web.util.FrontUtils;

@Controller
@RequestMapping(value = "/api/search/")
public class SearchAPI
{
	private static Logger log = LoggerFactory
			.getLogger(LuceneContentSvcImpl.class);

	@Autowired
	private SearchWordCache searchWordCache;

	@Autowired
	private RealPathResolver realPathResolver;

	@Autowired
	private LuceneContentSvc luceneContentSvc;

	@Autowired
	private SearchWordService searchWordService;

	@Autowired
	private HotWordService hotWordService;

	/**
	 * 获取热词接口
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "hit.jspx", method = RequestMethod.POST)
	public ResponseEntity<String> getHotWord(HttpServletRequest request,
			HttpServletResponse response)
	{
		Pager<HotWord> page = new Pager<HotWord>();
		page.setPageNo(1);
		page.setPageSize(100);
		List<HotWord> list = hotWordService.getPagerList(page);
		return new ResponseEntity<String>(ResponseUtils.getJson(list),
				ResponseUtils.getJsonHttpHeaders(), HttpStatus.OK);
	}

	/**
	 * @param request
	 * @param response
	 * @param String
	 *            query 搜索关键词
	 * @param boolean smartSort 是否智能排序
	 * @param String
	 *            near 离我最近
	 * @param String
	 *            cityName 城市名
	 * @param String
	 *            hospital_P 专科医院
	 * @param String
	 *            hospital_S 三甲医院
	 * @param boolean viceSenior 副高以上
	 * @param boolean degree 学历优先
	 * @param Integer
	 *            pageNo 页码
	 * @param Integer
	 *            pageSize 页容量
	 * @return
	 */
	@RequestMapping(value = "web/search*.jspx", method = RequestMethod.GET)
	public String webSearch(HttpServletRequest request, Model model,
			HttpServletResponse response, String query, boolean smartSort,
			String near, String cityName, String hospital_P, String hospital_S,
			boolean viceSenior, boolean degree, Integer pageNo, Integer pageSize)
	{
		query = RequestUtils.getQueryParam(request, "query");
		String parseQ = PageUtils.parseKeyWords(query);
		Pager<Doctor> page = getDoctorList(parseQ, smartSort, cityName,
				hospital_P, hospital_S, viceSenior, degree, pageNo, pageSize);
		addAttribute(model, page, query, cityName, hospital_P, hospital_S,
				viceSenior, degree);
		FrontUtils.frontData(request, model);
		return "front/search/search_result";
	}
	private void addAttribute(Model model, Pager<Doctor> page, String query,
			String cityName, String hospital_P, String hospital_S,
			boolean viceSenior, boolean degree)
	{
		model.addAttribute("page", page);
		model.addAttribute("query", query);
		model.addAttribute("cityName", cityName);
		model.addAttribute("hospital_P", hospital_P);
		model.addAttribute("hospital_S", hospital_S);
		model.addAttribute("viceSenior", viceSenior);
		model.addAttribute("degree", degree);
	}

	/**
	 * @param request
	 * @param response
	 * @param String
	 *            query 搜索关键词
	 * @param boolean smartSort 是否智能排序
	 * @param String
	 *            near 离我最近
	 * @param String
	 *            cityName 城市名
	 * @param String
	 *            hospital_P 专科医院
	 * @param String
	 *            hospital_S 三甲医院
	 * @param boolean viceSenior 副高以上
	 * @param boolean degree 学历优先
	 * @param Integer
	 *            pageNo 页码
	 * @param Integer
	 *            pageSize 页容量
	 * @return
	 */
	@RequestMapping(value = "search.jspx", method = RequestMethod.POST)
	public ResponseEntity<String> search(HttpServletRequest request,
			HttpServletResponse response, String query, boolean smartSort,
			String near, String cityName, String hospital_P, String hospital_S,
			boolean viceSenior, boolean degree, Integer pageNo, Integer pageSize)
	{
		query = RequestUtils.getQueryParam(request, "query");
		String parseQ = PageUtils.parseKeyWords(query);
		Pager<Doctor> page = getDoctorList(parseQ, smartSort, cityName,
				hospital_P, hospital_S, viceSenior, degree, pageNo, pageSize);
		return new ResponseEntity<String>(ResponseUtils.getJson(page),
				ResponseUtils.getJsonHttpHeaders(), HttpStatus.OK);
	}

	/**
	 * 索搜框输入---联词效果
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "ajaxSearch.jspx", method = RequestMethod.GET)
	public void ajaxSearch(HttpServletRequest request,
			HttpServletResponse response)
	{
		String name = RequestUtils.getQueryParam(request, "term");
		List<SearchWord> list = searchWordService.findListByName(name);
		List<String> names = new ArrayList<String>();
		list.forEach(word -> names.add(word.getName()));
		/** 向客户端发送json数据 **/
		ResponseUtils.renderJson(response, ResponseUtils.getJson(names));
	}

	@SuppressWarnings("unchecked")
	private Pager<Doctor> getDoctorList(String query, boolean smartSort,
			String cityName, String hospital_P, String hospital_S,
			boolean viceSenior, boolean degree, Integer pageNo, Integer pageSize)
	{
		String path = realPathResolver.get(Constants.LUCENE_PATH);
		Pager<Doctor> page = null;
		try
		{
			page = (Pager<Doctor>) luceneContentSvc.getPagerList(path, query,
					smartSort, null, cityName, hospital_P, hospital_S,
					viceSenior, degree, PageUtils.cpn(pageNo),
					PageUtils.cps(pageSize));

		}
		catch (CorruptIndexException e)
		{
			log.error("CorruptIndexException:" + e.getMessage());
		}
		catch (IOException e)
		{
			log.error("IOException:" + e.getMessage());
		}
		catch (ParseException e)
		{
			log.error("ParserException:" + e.getMessage());
		}
		/** 缓存搜索词 **/
		searchWordCache.cacheWord(query);
		return page;
	}

}
