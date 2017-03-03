package com.niit.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.niit.model.ChatbizBlog;


@Repository
public interface ChatbizBlogDAO {
public boolean save(ChatbizBlog chatbizBlog);
	
	public boolean update(ChatbizBlog chatbizBlog);
		
		public boolean delete(String id);
		
		
		public List<ChatbizBlog> list();
				
		
		
		public ChatbizBlog get(String id);
}
