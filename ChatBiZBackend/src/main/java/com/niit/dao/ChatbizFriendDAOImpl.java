package com.niit.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;


import com.niit.model.ChatbizFriend;

@EnableTransactionManagement
@Repository("chatbizFriendDAO")
public class ChatbizFriendDAOImpl implements ChatbizFriendDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	
	public ChatbizFriendDAOImpl(SessionFactory sessionFactory)
	{
	this.sessionFactory = sessionFactory;
	}
	
	@Transactional
	public boolean save(ChatbizFriend chatbizFriendDAO)
	{
	try {
		// Session session = sessionFactory.getCurrentSession();
	//friend.setId(getMaxId()+1);
		sessionFactory.getCurrentSession().save(chatbizFriendDAO);
		return true;
	}
	catch(Exception e)
	{
		e.printStackTrace();
		return false;
	}
	}


@Transactional
public boolean update(ChatbizFriend chatbizFriendDAO)
{
try {

	sessionFactory.getCurrentSession().update(chatbizFriendDAO);
	return true;
}
catch(Exception e)
{
	e.printStackTrace();
	return false;
}
}



@Transactional
	public void delete(String userid,String friendid)
	{
		ChatbizFriend FriendToDelete = new ChatbizFriend();
		FriendToDelete.setUserid(userid);
		FriendToDelete.setFriendid(friendid);
			sessionFactory.getCurrentSession().delete(FriendToDelete);
		
	}


@Transactional
public List<ChatbizFriend> getmyfriends(String userid)
{
	String hql = "from Friend where userid= "+" '" +userid+ "' and status='"+"A'";
	Query query =sessionFactory.getCurrentSession().createQuery(hql);
	@SuppressWarnings("unchecked")
	List<ChatbizFriend> list = query.list();
	return list;
}

@Transactional
public List<ChatbizFriend> getNewFriendrequest(String userid)
{
	String hql = "from Friend where userid= "+" '" +userid+ "' and status='"+"N'";
	Query query =sessionFactory.getCurrentSession().createQuery(hql);
	@SuppressWarnings("unchecked")
	List<ChatbizFriend> list = query.list();
	return list;
}

@Transactional
public ChatbizFriend get(String userid,String friendid)
{
	String hql = "from Friend where userid= '" + userid + "' and " + " friendid ='" + friendid + "'";
	Query query =sessionFactory.getCurrentSession().createQuery(hql);
	@SuppressWarnings("unchecked")
	List<ChatbizFriend> list = (List<ChatbizFriend>) query.list();

	if (list != null && !list.isEmpty()) {
		return list.get(0);
	}

	return null ;
}

@Transactional
public void setOnLine(String userid)
{
	String hql ="update Friend SET is_online='Y' where userid= "+" '" +userid+ "'";
	Query query =sessionFactory.getCurrentSession().createQuery(hql);
	query.executeUpdate();
}

@Transactional
public void setOffLine(String userid)
{
	String hql ="update Friend SET is_online='N' where userid= "+" '" +userid+ "'";
	Query query =sessionFactory.getCurrentSession().createQuery(hql);
	query.executeUpdate();
	
}
}
