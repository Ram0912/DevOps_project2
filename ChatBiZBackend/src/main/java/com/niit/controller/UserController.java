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

import com.niit.dao.ChatbizFriendDAO;
import com.niit.dao.ChatbizUserDAO;
import com.niit.model.ChatbizUsers;

@RestController
public class UserController {
	
	private static final Logger log=LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	ChatbizUserDAO chatbizUserDAO;
	@Autowired
	ChatbizUsers chatbizUsers;
	@Autowired
	ChatbizFriendDAO chatbizFriendDAO;
	
	//for list
	@RequestMapping(value="/users", method=RequestMethod.GET)
	public ResponseEntity<List<ChatbizUsers>> listAllUsers(){
		log.debug("-->Calling method listAllUsers");
		List<ChatbizUsers> user=chatbizUserDAO.list();
		if(user.isEmpty()){
			return new ResponseEntity<List<ChatbizUsers>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<ChatbizUsers>>(user,HttpStatus.OK);
	}
	
	//to create users
	@RequestMapping(value="/createusers/", method=RequestMethod.POST)
	public ResponseEntity<ChatbizUsers> createusers(@RequestBody ChatbizUsers chatbizUsers){
		log.debug("-->Calling method createUsers");
		if(chatbizUserDAO.get(chatbizUsers.getUserid())==null){
			chatbizUserDAO.save(chatbizUsers);
			return new ResponseEntity<ChatbizUsers>(chatbizUsers,HttpStatus.OK);
		}
		log.debug("-->User already exist"+chatbizUsers.getUserid());
		return new ResponseEntity<ChatbizUsers>(chatbizUsers,HttpStatus.OK);
		}

	//to get user by user id
	@RequestMapping(value="/user/{userid}",method=RequestMethod.GET)
	public ResponseEntity<ChatbizUsers> getuser(@PathVariable("userid")String id)
	{
	log.debug("-->calling get user method");
	ChatbizUsers chatbizUsers=chatbizUserDAO.get(id);
	if(chatbizUsers==null)
	{
		log.debug("-->User does not exist");
		chatbizUsers = new ChatbizUsers();
		chatbizUsers.setErrorcode("404");
		chatbizUsers.setErrormessage("User not found");
		return new ResponseEntity<ChatbizUsers>(chatbizUsers,HttpStatus.NOT_FOUND);
	}
	log.debug("-->User exist");
	return new ResponseEntity<ChatbizUsers>(chatbizUsers,HttpStatus.OK);
	}
	
	//update user by user id
	@RequestMapping(value="/user/{userid}",method=RequestMethod.PUT)
	public ResponseEntity<ChatbizUsers> updateuser(@PathVariable("userid")String id)
	{
	log.debug("-->calling update method");
	if(chatbizUserDAO.get(id)==null)
	{
		log.debug("-->User does not exist");
		chatbizUsers = new ChatbizUsers();
		chatbizUsers.setErrorcode("404");
		chatbizUsers.setErrormessage("User not found");
		return new ResponseEntity<ChatbizUsers>(chatbizUsers,HttpStatus.NOT_FOUND);
	}
	chatbizUserDAO.update(chatbizUsers);
	log.debug("-->User updated successfully");
	return new ResponseEntity<ChatbizUsers>(chatbizUsers,HttpStatus.OK);
	
}
	
	//delete user
	@RequestMapping(value="/user/{userid}",method=RequestMethod.DELETE)
	public ResponseEntity<ChatbizUsers> deleteuser(@PathVariable("userid")String id)
	{
		log.debug("-->calling delete method");
		ChatbizUsers chatbizUsers=chatbizUserDAO.get(id);
		if(chatbizUsers==null)
		{
			log.debug("-->User does not exist");
			chatbizUsers = new ChatbizUsers();
			chatbizUsers.setErrorcode("404");
			chatbizUsers.setErrormessage("Blog not found");
			return new ResponseEntity<ChatbizUsers>(chatbizUsers,HttpStatus.NOT_FOUND);
		}
		chatbizUserDAO.delete(id);
		log.debug("-->User deleted successfully");
		return new ResponseEntity<ChatbizUsers>(chatbizUsers,HttpStatus.OK);
		}
	
	
	//authentication
	@RequestMapping(value="/user/authenticate",method=RequestMethod.POST)
	public ResponseEntity<ChatbizUsers> authenticateuser(@RequestBody ChatbizUsers chatbizUsers,HttpSession session)
	{
		log.debug("-->calling authenticate method");
		chatbizUsers=chatbizUserDAO.authenticate(chatbizUsers.getUserid(), chatbizUsers.getPassword());
		if(chatbizUsers==null)
		{
			log.debug("-->User does not exist");
			chatbizUsers = new ChatbizUsers();
			System.out.println("User does not exist");
			chatbizUsers.setErrorcode("404");
			chatbizUsers.setErrormessage("User does not exist");
	}
		else
		{
			chatbizUsers.setErrorcode("200");
			log.debug("-->User exist with above credentials");
			session.setAttribute("loggedInUser",chatbizUsers);
			session.setAttribute("loggedInUserId", chatbizUsers.getUserid());
			chatbizFriendDAO.setOnLine(chatbizUsers.getUserid());
			chatbizUserDAO.setOnLine(chatbizUsers.getUserid());
		}
		return new ResponseEntity<ChatbizUsers>(chatbizUsers,HttpStatus.OK);
	}
	
	
	
	//logout method
	@RequestMapping(value="/user/logout",method=RequestMethod.GET)
	public ResponseEntity<ChatbizUsers> logout(HttpSession session)
	{
		System.out.println("logout");
		ChatbizUsers loggedInUser = (ChatbizUsers) session.getAttribute("loggedInUser");
		chatbizUsers= chatbizUserDAO.authenticate(loggedInUser.getUserid(), loggedInUser.getPassword());
		chatbizFriendDAO.setOffLine(loggedInUser.getUserid());
		chatbizUserDAO.setOffLine(loggedInUser.getUserid());
		session.invalidate();
		return new ResponseEntity<ChatbizUsers>(chatbizUsers,HttpStatus.OK);
	}
}