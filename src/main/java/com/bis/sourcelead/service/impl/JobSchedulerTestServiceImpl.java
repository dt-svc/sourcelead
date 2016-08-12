package com.bis.sourcelead.service.impl;

import org.springframework.stereotype.Service;

import com.bis.sourcelead.service.JobSchedulerTestService;

@Service
public class JobSchedulerTestServiceImpl implements JobSchedulerTestService {

	@Override
	public String schedulerTest(String arg) {
		String status = "Success";
		System.out.println("Calling from Job Scheduler Test Service Implemenation "+arg);
		return status;
	}

	@Override
	public String notiFicationSchedularTest(String arg) {
		String status = "Success";
		return status;
	}

}
