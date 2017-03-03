package com.niit.dao;

import java.util.List;

import org.springframework.stereotype.Repository;


import com.niit.model.ChatbizUsers;



@Repository
public interface ChatbizUserDAO {
	
	public boolean save(ChatbizUsers chatbizUsers);
	
	public boolean update(ChatbizUsers chatbizUsers);
		
		public boolean delete(String id);
		
		public void setOnLine(String userid);
		public void setOffLine(String userid);
		public List<ChatbizUsers> list();
				
		public ChatbizUsers authenticate(String username, String password);
		
		public ChatbizUsers get(String username);
		

	
			

}
