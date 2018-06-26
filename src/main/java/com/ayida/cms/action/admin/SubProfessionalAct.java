package com.ayida.cms.action.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ayida.cms.entity.doctor.SubProfessional;
import com.ayida.cms.entity.hotword.RelativeSearchWord;
import com.ayida.cms.service.RelativeSearchWordService;
import com.ayida.cms.service.SubProfessionalService;

public class SubProfessionalAct
{
	@Autowired
	private SubProfessionalService subProfessionalService;

	@Autowired
	private RelativeSearchWordService relativeService;

	private static final Logger log = LoggerFactory.getLogger(DoctorAct.class);
	
	private static final String ADD = "admin/subprofessional/add";
	
	private static final String LIST = "admin/subprofessional/list";
	
	private static final String UPDATE = "admin/subprofessional/update";

	@RequestMapping(value = "list.do", method = RequestMethod.GET)
	public String list(HttpServletRequest request, Integer pageNo,
			Integer pageSize, Model model)
	{
		List<SubProfessional> SubProfessionals = subProfessionalService
				.getAllSubProfessional();
		model.addAttribute("professionals", SubProfessionals);
		return LIST;
	}

	@RequestMapping(value = "add.do", method = RequestMethod.GET)
	public String add(HttpServletRequest request, Model model)
	{
		return ADD;
	}

	@RequestMapping(value = "save.do", method = RequestMethod.POST)
	public String save(HttpServletRequest request,
			HttpServletResponse response, SubProfessional bean, Model model)
	{
		SubProfessional entity = subProfessionalService.save(bean);
		/** 将disease保存后，顺便保存到联词表里，便于前端搜索和联词效果 **/
		//relativeService.save(entity.getId(), entity.getName());
		return "redirect:list.do";
	}

	@RequestMapping(value = "update.do", method = RequestMethod.GET)
	public String update(HttpServletRequest request, Integer id, Model model)
	{
		SubProfessional bean = subProfessionalService.findById(id);
		model.addAttribute("subprofessional", bean);
		return UPDATE;
	}

	@RequestMapping(value = "update.do", method = RequestMethod.POST)
	public String update(HttpServletRequest request, SubProfessional bean,
			Model model)
	{
		subProfessionalService.updateBean(bean);
		RelativeSearchWord word = new RelativeSearchWord();
		//word.setName(bean.getName());
		relativeService.update(word);
		return "redirect:list.do";
	}

	@RequestMapping(value = "delete.do", method = RequestMethod.GET)
	public String delete(HttpServletRequest request, Integer id, Model model)
	{
		SubProfessional bean = subProfessionalService.findById(id);
		if (null != bean)
		{
			subProfessionalService.deleteById(bean.getId());
		}
		RelativeSearchWord entity = relativeService.findById(bean.getId());
		if (null != entity)
		{
			relativeService.delete(entity);
		}
		return "redirect:list.do";
	}
}
