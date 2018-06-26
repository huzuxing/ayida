package com.ayida.cms.action.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ayida.cms.entity.disease.Disease;
import com.ayida.cms.entity.hotword.RelativeSearchWord;
import com.ayida.cms.service.DiseaseService;
import com.ayida.cms.service.RelativeSearchWordService;

@Controller
@RequestMapping(value = "/disease/")
public class DiseaseAct
{
	@Autowired
	private DiseaseService diseaseService;

	private static final Logger log = LoggerFactory.getLogger(DoctorAct.class);

	private static final String ADD = "admin/disease/add";

	private static final String LIST = "admin/disease/list";

	private static final String UPDATE = "admin/disease/update";

	@RequestMapping(value = "list.do", method = RequestMethod.GET)
	public String list(HttpServletRequest request, Integer pageNo,
			Integer pageSize, Model model, String name)
	{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", name);
		List<Disease> diseases = diseaseService.getDiseasePagerList(pageNo, pageSize, params);
		model.addAttribute("diseases", diseases);
		return LIST;
	}

	@RequestMapping(value = "add.do", method = RequestMethod.GET)
	public String add(HttpServletRequest request, Model model)
	{
		return ADD;
	}

	@RequestMapping(value = "save.do", method = RequestMethod.POST)
	public String save(HttpServletRequest request,
			HttpServletResponse response, Disease bean, Model model)
	{
		Disease entity = diseaseService.save(bean);
		return "redirect:list.do";
	}

	@RequestMapping(value = "update.do", method = RequestMethod.GET)
	public String update(HttpServletRequest request, Integer id, Model model)
	{
		Disease bean = diseaseService.findById(id);
		model.addAttribute("disease", bean);
		return UPDATE;
	}

	@RequestMapping(value = "update.do", method = RequestMethod.POST)
	public String update(HttpServletRequest request, Disease bean, Model model)
	{
		diseaseService.updateBean(bean);
		return "redirect:list.do";
	}

	@RequestMapping(value = "delete.do", method = RequestMethod.GET)
	public String delete(HttpServletRequest request, Integer id, Model model)
	{
		Disease bean = diseaseService.findById(id);
		if (null != bean)
		{
			diseaseService.deleteById(bean.getId());
		}
		return "redirect:list.do";
	}
}
