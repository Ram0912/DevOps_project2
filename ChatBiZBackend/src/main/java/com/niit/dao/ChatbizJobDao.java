package com.niit.dao;


import java.util.List;

import com.niit.model.ChatbizJob;

public interface ChatbizJobDao {
	
	void postJob(ChatbizJob chatbizjob);
	List<ChatbizJob> getAllJobs();
	ChatbizJob getJobDetail(int jobId);

}
