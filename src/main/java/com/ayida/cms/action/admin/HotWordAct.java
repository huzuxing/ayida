package com.ayida.cms.action.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ayida.cms.entity.hotword.HotWord;
import com.ayida.cms.service.HotWordService;
import com.ayida.common.mybatis.Pager;
import com.ayida.common.util.PageUtils;
import com.ayida.core.web.util.FrontUtils;

@Controller
@RequestMapping(value = "/admin/hotword/")
public class HotWordAct
{
	private static final String reg = " ";

	@Autowired
	private HotWordService hotWordService;

	@RequestMapping(value = "list.do", method = RequestMethod.GET)
	public String list(HttpServletRequest request, Model model, Integer pageNo,
			Integer pageSize)
	{
		Pager<HotWord> page = new Pager<HotWord>();
		page.setPageNo(PageUtils.cpn(pageNo));
		page.setPageSize(PageUtils.cps(pageSize));
		List<HotWord> words = hotWordService.getPagerList(page);
		FrontUtils.frontData(request, model);
		model.addAttribute("words", words);
		return "admin/hotword/list";
	}

	@RequestMapping(value = "add.do", method = RequestMethod.GET)
	public String add(HttpServletRequest request, Model model)
	{
		FrontUtils.frontData(request, model);
		return "admin/hotword/add";
	}

	@RequestMapping(value = "save.do", method = RequestMethod.POST)
	public String save(HttpServletRequest request, String name, String childName)
	{
		String[] childs = childName.split(reg);
		HotWord bean = hotWordService.save(name, null);
		for (String child : childs)
		{
			hotWordService.save(child, bean.getId());
		}
		return "redirect:list.do";
	}

	@RequestMapping(value = "update.do", method = RequestMethod.GET)
	public String goUpdate(HttpServletRequest request, Model model, Integer id)
	{
		HotWord word = hotWordService.findById(id);
		List<HotWord> childs = hotWordService.getChildList(id);
		FrontUtils.frontData(request, model);
		model.addAttribute("word", word);
		model.addAttribute("childs", childs);
		return "admin/hotword/update";
	}

	@RequestMapping(value = "update.do", method = RequestMethod.POST)
	public String update(HttpServletRequest request, Integer id, String name,
			Integer parentId, String childName)
	{
		HotWord word = hotWordService.findById(id);
		if (null != word)
		{
			word.setName(name);
			word.setParentId(parentId);
			hotWordService.update(word);
			hotWordService.deleteChild(word.getId());
		}
		String[] childs = childName.split(reg);
		for(String child : childs)
		{
			hotWordService.save(child, word.getId());
		}
		return "redirect:list.do";
	}
}
