package com.niit.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.niit.model.ChatbizBlog;


@EnableTransactionManagement
@Repository("chatbizBlogDAO")
public class ChatbizBlogDAOImpl implements ChatbizBlogDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	
	public ChatbizBlogDAOImpl(SessionFactory sessionFactory)
	{
	this.sessionFactory = sessionFactory;
	}
	
@Transactional
	public boolean save(ChatbizBlog chatbizBlog)
	{
	try {
		// Session session = sessionFactory.getCurrentSession();
	
		sessionFactory.getCurrentSession().save(chatbizBlog);
		return true;
	}
	catch(Exception e)
	{
		e.printStackTrace();
		return false;
	}
	}


@Transactional
public boolean update(ChatbizBlog chatbizBlog)
{
try {

	sessionFactory.getCurrentSession().update(chatbizBlog);
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
		
		
		ChatbizBlog BlogToDelete = new ChatbizBlog();
			BlogToDelete.setId(id);
			sessionFactory.getCurrentSession().delete(BlogToDelete);
		
		return true;
	}
	catch(Exception e)
	{
		
		e.printStackTrace();
		return false;
	}
	}

@Transactional
public ChatbizBlog get(String id)
{
	String hql = "from Blog where id= "+" '" +id+ "'";
	Query query =sessionFactory.getCurrentSession().createQuery(hql);
	@SuppressWarnings("unchecked")
	List<ChatbizBlog> list = query.list();
	if(list == null || list.isEmpty())
	{
		return null;
	}
	else
	{
		return list.get(0);
	}
}



@SuppressWarnings("unchecked")
@Transactional
public List<ChatbizBlog> list()
{
	String hql = "from Blog";
	Query query =sessionFactory.getCurrentSession().createQuery(hql);
	return query.list();
}

}
