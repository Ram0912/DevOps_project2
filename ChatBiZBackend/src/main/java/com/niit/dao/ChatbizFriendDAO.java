package com.niit.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.niit.model.ChatbizFriend;

@Repository
public interface ChatbizFriendDAO {
	
	public boolean save(ChatbizFriend chatbizFriend);
	public boolean update(ChatbizFriend chatbizFriend);
	public void delete(String userid,String friendid);
	public void setOnLine(String userid);
	public void setOffLine(String userid);
	public ChatbizFriend get(String userid,String friendid);
	public List<ChatbizFriend> getmyfriends(String userid);
	public List<ChatbizFriend> getNewFriendrequest(String userid);
	

}
