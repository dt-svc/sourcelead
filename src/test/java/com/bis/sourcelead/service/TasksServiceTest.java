package com.bis.sourcelead.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import com.bis.sourcelead.SecurityConfig;
import com.bis.sourcelead.WebAppConfig;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Tasks;
import com.bis.sourcelead.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebAppConfig.class, SecurityConfig.class}, loader = AnnotationConfigWebContextLoader.class)
public class TasksServiceTest {

	@Autowired
	TaskService taskService;
	private static final Logger logger = LoggerFactory.getLogger(TasksServiceTest.class);

	@Test
	public void getTaskByScheduledTime() throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String currentTime = dateFormat.format(date);
		DateFormat dateFormat1 = new SimpleDateFormat("HH:mm");
		String time = dateFormat1.format(date);
		final Date dateObj = dateFormat1.parse(time);
		String taskTime = new SimpleDateFormat("KK:mm a").format(dateObj);
		List<Tasks> taskDetails = taskService.getTaskByScheduledTime(currentTime);
		for (Tasks task : taskDetails) {
			if (taskTime.equalsIgnoreCase(task.getTaskTime())) {
				taskService.mailToUserTask(task);
			}

		}
	}
}
