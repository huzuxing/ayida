package com.ayida.cms.action.front;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ayida.cms.entity.hotword.HotWord;
import com.ayida.cms.service.HotWordService;
import com.ayida.common.mybatis.Pager;
import com.ayida.core.web.util.FrontUtils;
import com.ayida.core.web.util.URLHelper;

@Controller
@RequestMapping(value = "/")
public class IndexAct
{
	@Autowired
	private HotWordService hotWordService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model)
	{
		FrontUtils.frontData(request, model);
		/** 带有其他路径则是非法路径 **/
		String uri = URLHelper.getURI(request);
		if (StringUtils.isNotBlank(uri) && !"/".equals(uri))
			return "common/pageNotFound";
		Pager<HotWord> page = new Pager<HotWord>();
		page.setPageNo(1);
		page.setPageSize(1000);
		List<HotWord> words = hotWordService.getPagerList(page);
		List<HotWord> hits = hotWordService.getPagerList(page);
		model.addAttribute("words", words);
		model.addAttribute("hits", hits);
		return "index/index";
	}

	@RequestMapping(value = "/index.html", method = RequestMethod.GET)
	public String showIndex(HttpServletRequest request,
			HttpServletResponse response, Model model)
	{
		return index(request, response, model);
	}
}
