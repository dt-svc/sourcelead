package com.bis.sourcelead.service.impl.performance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.OrgLocationsDao;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.dao.performance.PerformanceGoalsCommentsDao;
import com.bis.sourcelead.dao.performance.PerformanceGoalsDao;
import com.bis.sourcelead.dto.performance.PerformanceGoalsCommentsDTO;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.performance.PerformanceGoals;
import com.bis.sourcelead.entity.performance.PerformanceGoalsComments;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.performance.PerformanceGoalsCommentsService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;

@Service
public class PerformanceGoalsCommentsServiceImpl implements PerformanceGoalsCommentsService{

	@Autowired
	PerformanceGoalsCommentsDao performanceGoalsCommentsDao;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	OrgLocationsDao orgLocationsDao;
	
	@Autowired
	PerformanceGoalsDao performanceGoalsDao;
	
	@Autowired
	EmailScenarioService emailScenarioService;
		
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	@Override
	public PerformanceGoalsComments savePerformanceGoalsComments(PerformanceGoalsComments performanceGoalsComments)
			throws Exception {
		return performanceGoalsCommentsDao.savePerformanceGoalsComments(performanceGoalsComments);
	}

	@Override
	public void storePerformanceGoalsComments(PerformanceGoalsCommentsDTO performanceGoalsCommentsDTO,Long orgLocationId)
			throws Exception {
		
		PerformanceGoalsComments comments = new PerformanceGoalsComments();
		PerformanceGoals goals = performanceGoalsDao.getPerformanceGoalsById(performanceGoalsCommentsDTO.getGoalId());
		comments.setGoalId(goals);
		comments.setComment(performanceGoalsCommentsDTO.getGoalsComment());
		comments.setCreatedUserName(performanceGoalsCommentsDTO.getCreatedUserName());
		User loginUser = userDao.getUserById(performanceGoalsCommentsDTO.getCreatedUserId());
		comments.setUserId(loginUser);
		comments.setCreatedDate(commonUtil.currentDate());
		savePerformanceGoalsComments(comments);
		
		if(StringUtils.isNotBlank(performanceGoalsCommentsDTO.getMentionedUserIds())) {
			OrgLocations orgLocations = orgLocationsDao.getOrgLocations(orgLocationId);
			String[] commentedUsers = performanceGoalsCommentsDTO.getMentionedUserIds().split(",");
			 for(int i=0; i<commentedUsers.length;i++) {
				 User commentedUser = userDao.getUserById(Long.valueOf(commentedUsers[i]));
				 sendMailToGoalCommentUsers(performanceGoalsCommentsDTO,goals,loginUser,commentedUser,orgLocations);
			 }
		}
		
		
		
	}

	private void sendMailToGoalCommentUsers(PerformanceGoalsCommentsDTO performanceGoalsCommentsDTO,PerformanceGoals goals, User loginUser,
			User commentedUser, OrgLocations orgLocations) {
		
		try {
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(loginUser.getFirstName());
		emailMessage.setLastName(loginUser.getLastName());
		emailMessage.setToEmail(commentedUser.getEmail());
		
		emailMessage.setFromEmail(loginUser.getEmail());
		emailMessage.setCreatedBy(loginUser.getUserCode());
		emailMessage.setFromName(loginUser.getFullName());
		emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		globalVarsMap.put("assignMemberName", commentedUser.getFullName());
		globalVarsMap.put("loginUser", loginUser.getFullName());
		globalVarsMap.put("goalName", goals.getGoalName());
		
		int index = performanceGoalsCommentsDTO.getGoalsComment().lastIndexOf(">");
		String comments = performanceGoalsCommentsDTO.getGoalsComment().substring(index + 1, performanceGoalsCommentsDTO.getGoalsComment().length());
		globalVarsMap.put("goalComment", comments);
		
		commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, orgLocations.getOrg(), orgLocations, Constants.ADD_GOAL_COMMENT_EMAIL,new ArrayList<EmailAttachment>());
		
		EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.ADD_GOAL_COMMENT_NOTIFICATION, Constants.ENTITY_TYPE_NOTIFICATION);
		String notificationBody = emailScenerioUtil.buildTemplateBody(notificationScenario.getSubject(), globalVarsMap,notificationScenario);
		notificationScenario.setBody(notificationBody);
		notificationScenario.setOrgCode(orgLocations.getOrg().getOrgCode());
		String actionLink = "";
		if(commentedUser != null) {
			emailScenerioUtil.sendNotification(actionLink, loginUser, commentedUser, orgLocations, notificationScenario, new HashMap<String, String>());
		}		
	
		}catch (Exception e) {
			e.printStackTrace();
		}
		        
	}

	@Override
	public List<PerformanceGoalsCommentsDTO> getPerformanceGoalsCommentsList(Long goalId, String dateFormat) throws Exception {
		return performanceGoalsCommentsDao.getPerformanceGoalsCommentsList(goalId,dateFormat);
	}

	

}
