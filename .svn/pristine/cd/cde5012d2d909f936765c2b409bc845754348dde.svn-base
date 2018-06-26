package com.ayida.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/test.jspx")
public class TestAct
{	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<String> test()
	{
		Author author = authorDao.findById(1);
		System.out.println(author.getAuthorName());
		for(Blog b : author.getBlogs())
		{
			System.out.println(b.getBlogName());
		}
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}
	
	@Autowired
	private AuthorDAO authorDao;
}
