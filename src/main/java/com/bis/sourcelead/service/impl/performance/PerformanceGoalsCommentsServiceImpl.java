package com.bis.sourcelead.service.impl.performance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.performance.PerformanceGoalsCommentsDao;
import com.bis.sourcelead.dto.performance.PerformanceGoalsCommentsDTO;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.performance.PerformanceGoals;
import com.bis.sourcelead.entity.performance.PerformanceGoalsComments;
import com.bis.sourcelead.service.performance.PerformanceGoalsCommentsService;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class PerformanceGoalsCommentsServiceImpl implements PerformanceGoalsCommentsService{

	@Autowired
	PerformanceGoalsCommentsDao performanceGoalsCommentsDao;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Override
	public PerformanceGoalsComments savePerformanceGoalsComments(PerformanceGoalsComments performanceGoalsComments)
			throws Exception {
		return performanceGoalsCommentsDao.savePerformanceGoalsComments(performanceGoalsComments);
	}

	@Override
	public void storePerformanceGoalsComments(PerformanceGoalsCommentsDTO performanceGoalsCommentsDTO)
			throws Exception {
		
		PerformanceGoalsComments comments = new PerformanceGoalsComments();
		
		PerformanceGoals goals = new PerformanceGoals();
		goals.setId(performanceGoalsCommentsDTO.getGoalId());
		comments.setGoalId(goals);
		comments.setComment(performanceGoalsCommentsDTO.getGoalsComment());
		comments.setCreatedUserName(performanceGoalsCommentsDTO.getCreatedUserName());
		User createdUser = new User();
		createdUser.setId(performanceGoalsCommentsDTO.getCreatedUserId());
		comments.setUserId(createdUser);
		comments.setCreatedDate(commonUtil.currentDate());
		savePerformanceGoalsComments(comments);
		
	}

	@Override
	public List<PerformanceGoalsCommentsDTO> getPerformanceGoalsCommentsList(Long goalId, String dateFormat) throws Exception {
		return performanceGoalsCommentsDao.getPerformanceGoalsCommentsList(goalId,dateFormat);
	}

	

}
