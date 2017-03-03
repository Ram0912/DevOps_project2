package com.niit.dao;

import java.util.List;

import org.hibernate.Query;

import org.hibernate.SessionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.niit.model.ChatbizUsers;

@EnableTransactionManagement
@Repository("chatbizUserDAO")
public class ChatbizUserDAOImpl implements ChatbizUserDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	public ChatbizUserDAOImpl(SessionFactory sessionFactory)
	{
	this.sessionFactory = sessionFactory;
	}
	
@Transactional
	public boolean save(ChatbizUsers chatbizUser)
	{
	try {
		// Session session = sessionFactory.getCurrentSession();
	
		sessionFactory.getCurrentSession().save(chatbizUser);
		return true;
	}
	catch(Exception e)
	{
		e.printStackTrace();
		return false;
	}
	}


@Transactional
public boolean update(ChatbizUsers chatbizUser)
{
try {

	sessionFactory.getCurrentSession().update(chatbizUser);
	return true;
}
catch(Exception e)
{
	e.printStackTrace();
	return false;
}
}



@Transactional
	public boolean delete(String id)
	{
	try {
		
		
			ChatbizUsers CategoryToDelete = new ChatbizUsers();
			CategoryToDelete.setUserid(id);
			sessionFactory.getCurrentSession().delete(CategoryToDelete);
		
		return true;
	}
	catch(Exception e)
	{
		
		e.printStackTrace();
		return false;
	}
	}

@Transactional
public ChatbizUsers get(String userid)
{
	String hql = "from ChatbizUsers where userid= "+" '" +userid+ "'";
	Query query =sessionFactory.getCurrentSession().createQuery(hql);
	@SuppressWarnings("unchecked")
	List<ChatbizUsers> list = query.list();
	if(list == null || list.isEmpty())
	{
		return null;
	}
	else
	{
		return list.get(0);
	}
}

@Transactional
public ChatbizUsers authenticate(String userid, String password) {
	System.out.println("DAO IMPLEMENTATION..");
	String hql = "from ChatbizUsers where userid= '" + userid + "' and " + " password ='" + password + "'";
	Query query = sessionFactory.getCurrentSession().createQuery(hql);

	
	List<ChatbizUsers> list = (List<ChatbizUsers>) query.list();

	if (list != null && !list.isEmpty()) {
		return list.get(0);
	}

	return null ;
}

@SuppressWarnings("unchecked")
@Transactional
public List<ChatbizUsers> list()
{
	String hql = "from ChatbizUsers";
	Query query =sessionFactory.getCurrentSession().createQuery(hql);
	return query.list();
}

@Transactional
public void setOnLine(String userid)
{
	String hql ="update ChatbizUsers SET is_online='Y' where userid= "+" '" +userid+ "'";
	Query query =sessionFactory.getCurrentSession().createQuery(hql);
	query.executeUpdate();
}

@Transactional
public void setOffLine(String userid)
{
	String hql ="update ChatbizUsers SET is_online='N' where userid= "+" '" +userid+ "'";
	Query query =sessionFactory.getCurrentSession().createQuery(hql);
	query.executeUpdate();
	
}
}








