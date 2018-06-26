package com.ayida.test;

import java.util.Set;

import org.apache.ibatis.type.Alias;

@Alias("Author")
public class Author
{
	private Integer id;
	
	private String authorName;
	
	public String getAuthorName()
	{
		return authorName;
	}

	public void setAuthorName(String authorName)
	{
		this.authorName = authorName;
	}

	private Set<Blog> blogs;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Set<Blog> getBlogs()
	{
		return blogs;
	}

	public void setBlogs(Set<Blog> blogs)
	{
		this.blogs = blogs;
	}
}
