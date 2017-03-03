package com.niit.controller;


import java.util.Date;
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

import com.niit.dao.ChatbizJobDao;
import com.niit.model.ChatbizJob;
import com.niit.model.ChatbizUsers;


@RestController
public class JobController {
	
	Logger logger=LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ChatbizJobDao jobDao;
	
	
	
	@RequestMapping(value="/postJob",method=RequestMethod.POST)
	public ResponseEntity<?> postJob(@RequestBody ChatbizJob job,HttpSession session){
		ChatbizUsers user=(ChatbizUsers)session.getAttribute("user");
		if(user==null){
			Error error=new Error("Unauthorized user.. login using valid credentials");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);//401
		}
		else{
					job.setPostedOn(new Date());
					jobDao.postJob(job);
				return new ResponseEntity<Void>(HttpStatus.OK);
			
	}
}
	
	@RequestMapping(value="/getAllJobs",method=RequestMethod.GET)
	public ResponseEntity<?> getAllJobs(HttpSession session){
    	ChatbizUsers user=(ChatbizUsers)session.getAttribute("user");
    	if(user==null){
    		System.out.println("USER is null");
    		Error error=new Error("Unauthorized user.. login using valid credentials");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);//401
    	}
    	System.out.println("USER OBJECT " + user.getRole());
    	List<ChatbizJob> jobs=jobDao.getAllJobs();
    	return new ResponseEntity<List<ChatbizJob>>(jobs,HttpStatus.OK);
    }
	
	
	@RequestMapping(value="/getJobDetail/{jobId}",method=RequestMethod.GET)
    public ResponseEntity<?> getJobDetail(@PathVariable(value="jobId")int jobId,
    		HttpSession session){
    	ChatbizUsers user=(ChatbizUsers)session.getAttribute("user");
    	if(user==null){
    		System.out.println("USER is null");
    		Error error=new Error("Unauthorized user.. login using valid credentials");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);//401
    	}
    	logger.debug("JobId "+ jobId);
    	ChatbizJob jobDetail=jobDao.getJobDetail(jobId);
    	return new ResponseEntity<ChatbizJob>(jobDetail,HttpStatus.OK);
    }
}