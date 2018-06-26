package com.ayida.cms.action.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ayida.cms.entity.doctor.Doctor;
import com.ayida.cms.entity.doctor.DoctorExt;
import com.ayida.cms.entity.hotword.RelativeSearchWord;
import com.ayida.cms.service.DoctorService;
import com.ayida.cms.service.RelativeSearchWordService;

@Controller
@RequestMapping(value = "/doctor/")
public class DoctorAct
{
	@Autowired
	private DoctorService doctorService;

	@Autowired
	private RelativeSearchWordService relativeService;

	private static final Logger log = LoggerFactory.getLogger(DoctorAct.class);
	
	private static final String ADD = "admin/doctor/add";

	private static final String LIST = "admin/doctor/list";

	private static final String UPDATE = "admin/doctor/update";

	@RequestMapping(value = "list.do", method = RequestMethod.GET)
	public String list(HttpServletRequest request, Integer pageNo,
			Integer pageSize, Model model)
	{
		// List<Doctor> doctors = doctorService.getDoctorPagerList(pager);
		List<Doctor> doctors = doctorService.getAllDoctors();
		model.addAttribute("doctors", doctors);
		return LIST;
	}

	@RequestMapping(value = "add.do", method = RequestMethod.GET)
	public String add(HttpServletRequest request, Model model)
	{
		return ADD;
	}

	@RequestMapping(value = "save.do", method = RequestMethod.POST)
	public String save(HttpServletRequest request,
			HttpServletResponse response, Doctor doctor, DoctorExt doctorExt,
			Model model)
	{
		Doctor doct = doctorService.save(doctor);
		/** 将doctor保存后，顺便保存到联词表里，便于前端搜索和联词效果 **/
		//relativeService.save(doct.getId(), doct.getName());
		return "redirect:list.do";
	}

	@RequestMapping(value = "update.do", method = RequestMethod.GET)
	public String update(HttpServletRequest request, Integer id, Model model)
	{
		Doctor doctor = doctorService.findById(id);
		model.addAttribute("doctor", doctor);
		return UPDATE;
	}

	@RequestMapping(value = "update.do", method = RequestMethod.POST)
	public String update(HttpServletRequest request, Doctor doctor,
			DoctorExt doctorExt, Model model)
	{
		doctorService.updateBean(doctor);
		RelativeSearchWord word = new RelativeSearchWord();
		//word.setName(doctor.getName());
		relativeService.update(word);
		return "redirect:list.do";
	}

	@RequestMapping(value = "delete.do", method = RequestMethod.GET)
	public String delete(HttpServletRequest request, Integer id, Model model)
	{
		Doctor doc = doctorService.findById(id);
		if (null != doc)
		{
			doctorService.deleteById(doc.getId());
		}
		RelativeSearchWord bean = relativeService.findById(doc.getId());
		if (null != bean)
		{
			relativeService.delete(bean);
		}
		return "redirect:list.do";
	}
}
