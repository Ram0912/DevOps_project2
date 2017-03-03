package com.niit.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.dao.ChatbizBlogDAO;

import com.niit.model.ChatbizBlog;

@RestController
public class BlogController {
private static final Logger log=LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	ChatbizBlogDAO blogdao;
	@Autowired
	ChatbizBlog blog;
	
	@RequestMapping(value="/blogs", method=RequestMethod.GET)
	public ResponseEntity<List<ChatbizBlog>> listAllBlog(){
		log.debug("-->Calling method listAllUsers");
		List<ChatbizBlog> blog=blogdao.list();
		if(blog.isEmpty()){
			return new ResponseEntity<List<ChatbizBlog>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<ChatbizBlog>>(blog,HttpStatus.OK);
		
	}
	@RequestMapping(value="/blog/{id}",method=RequestMethod.GET)
	public ResponseEntity<ChatbizBlog> getuser(@PathVariable("id")String id)
	{
	log.debug("-->calling get method");
    ChatbizBlog blog=blogdao.get(id);
	if(blog==null)
	{
		blog = new ChatbizBlog();
		blog.setErrorcode("404");
		blog.setErrormessage("Blog not found");
		return new ResponseEntity<ChatbizBlog>(blog,HttpStatus.NOT_FOUND);
	}
	return new ResponseEntity<ChatbizBlog>(blog,HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/createblogs/", method=RequestMethod.POST)
	public ResponseEntity<ChatbizBlog> createblogs(@RequestBody ChatbizBlog blog,HttpSession session){
		log.debug("--> Calling the method createUsers");
		String loggedInUserid = (String) session.getAttribute("loggedInUserId");
		blog.setUserid(loggedInUserid);
	    blogdao.save(blog);
		return new ResponseEntity<ChatbizBlog>(blog,HttpStatus.OK);
		}
	
	@RequestMapping(value="/blog/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<ChatbizBlog> deleteuser(@PathVariable("id")String id)
	{
		log.debug("--> calling the delete method");
		ChatbizBlog blog=blogdao.get(id);
		if(blog==null)
		{
			log.debug("-->User does not exist");
			blog = new ChatbizBlog();
			blog.setErrorcode("404");
			blog.setErrormessage("Blog not found");
			return new ResponseEntity<ChatbizBlog>(blog,HttpStatus.NOT_FOUND);
		}
		blogdao.delete(id);
		log.debug("-->User deleted successfully");
		return new ResponseEntity<ChatbizBlog>(blog,HttpStatus.OK);
		}
	
}
