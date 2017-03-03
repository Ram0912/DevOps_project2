package com.niit.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.model.ChatbizJob;

@Repository
public class ChatbizJobDaoImpl implements ChatbizJobDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	public void postJob(ChatbizJob chatbizjob) {
		Session session=sessionFactory.openSession();
		session.save(chatbizjob);
		session.flush();
		session.close();

	}

	
	public List<ChatbizJob> getAllJobs() {
		Session session=sessionFactory.openSession();
		Query query=session.createQuery("from Job");
		List<ChatbizJob> jobs=query.list();
		session.close();
		return jobs;
	}

	
	public ChatbizJob getJobDetail(int jobId) {
		Session session=sessionFactory.openSession();
		ChatbizJob job=(ChatbizJob)session.get(ChatbizJob.class, jobId);
		session.close();
		return job;
	}

}
