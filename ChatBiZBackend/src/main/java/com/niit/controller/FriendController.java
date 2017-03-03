package com.niit.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.dao.ChatbizFriendDAO;

import com.niit.model.ChatbizFriend;
import com.niit.model.ChatbizUsers;

@RestController
public class FriendController {
	
	@Autowired
	ChatbizFriendDAO friendDAO;
	@Autowired
	ChatbizFriend friend;
	
	
	private static final Logger log=LoggerFactory.getLogger(ChatbizFriend.class);
	@RequestMapping(value="/myfriends", method=RequestMethod.GET)
	public ResponseEntity<List<ChatbizFriend>> listAllFriend(HttpSession session){
		log.debug("-->Calling method to listAllFriends");
		ChatbizUsers loggedInUser = (ChatbizUsers) session.getAttribute("loggedInUser");
		List<ChatbizFriend> myfriends = friendDAO.getmyfriends(loggedInUser.getUserid());
		return new ResponseEntity<List<ChatbizFriend>> (myfriends,HttpStatus.OK);
		
	}
		
	@RequestMapping(value="/addfriend/{friendid}", method=RequestMethod.GET)
	public ResponseEntity<ChatbizFriend> sendFriendRequest(@PathVariable("friendid")String friendid,HttpSession session,ChatbizUsers userdetails)
	{
		log.debug("-->Calling method send friend request");
		String loggedInUserid = (String) session.getAttribute("loggedInUserId");
		friend.setUserid(loggedInUserid);
		friend.setFriendid(friendid);
		friend.setStatus("N");
		friendDAO.setOnLine(userdetails.getUserid());
		friendDAO.save(friend);
		return new ResponseEntity<ChatbizFriend>(friend,HttpStatus.OK);
	}
	
	@RequestMapping(value="/unfriend/{friendid}", method=RequestMethod.GET)
	public ResponseEntity<ChatbizFriend> unfriend(@PathVariable("friendid")String friendid,HttpSession session)
	{
		log.debug("-->Calling method to send the friend request");
		ChatbizUsers loggedInUser = (ChatbizUsers) session.getAttribute("loggedInUser");	
		friend.setUserid(loggedInUser.getUserid());
		friend.setFriendid(friendid);
		friend.setStatus("U");
		friendDAO.update(friend);
		return new ResponseEntity<ChatbizFriend>(friend,HttpStatus.OK);
	}
	
	@RequestMapping(value="/rejectFriend/{friendid}", method=RequestMethod.GET)
	public ResponseEntity<ChatbizFriend> rejectFriend(@PathVariable("friendid")String friendid,HttpSession session)
	{
		log.debug("-->Calling method to reject friend request");
		ChatbizUsers loggedInUser = (ChatbizUsers) session.getAttribute("loggedInUser");	
		friend.setUserid(loggedInUser.getUserid());
		friend.setFriendid(friendid);
		friend.setStatus("R");
		friendDAO.update(friend);
		return new ResponseEntity<ChatbizFriend>(friend,HttpStatus.OK);
	}
	
	@RequestMapping(value="/getmyfriendRequest", method=RequestMethod.GET)
	public ResponseEntity<ChatbizFriend> getFriendRequest(HttpSession session){
		log.debug("-->Calling method to listAllFriends");
		String loggedInUserid = (String) session.getAttribute("loggedInUserId");

		friendDAO.getNewFriendrequest(loggedInUserid);
		return new ResponseEntity<ChatbizFriend> (friend,HttpStatus.OK);
	
}
	@RequestMapping(value="/acceptFriend/{friendid}", method=RequestMethod.GET)
	public ResponseEntity<ChatbizFriend> acceptFriend(@PathVariable("friendid")String friendid,HttpSession session,ChatbizUsers userdetails)
	{
		log.debug("-->Calling method to accept friend request");
		String loggedInUserId = (String) session.getAttribute("loggedInUserId");
		friend.setUserid(loggedInUserId);
		friend.setFriendid(friendid);
		friend.setStatus("A");
		friendDAO.setOnLine(userdetails.getUserid());
		friendDAO.update(friend);
		//updateRequest(friendid,"A",session);
		return new ResponseEntity<ChatbizFriend> (friend,HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/myfriends/{id}", method=RequestMethod.GET)
	public ResponseEntity<List<ChatbizFriend>> getmyFriendsTemp(@PathVariable("id")String id){
		List<ChatbizFriend> myfriends = friendDAO.getmyfriends(id);
		return new ResponseEntity<List<ChatbizFriend>> (myfriends,HttpStatus.OK);
	}
	}