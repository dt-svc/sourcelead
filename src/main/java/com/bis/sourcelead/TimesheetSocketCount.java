package com.bis.sourcelead;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.TimeSheetSubmissionCount;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;

@ServerEndpoint("/ratesrv")
public class TimesheetSocketCount {
	
	private UserService userService = BeanUtil.getBean(UserService.class);
	
	private TimesheetSubmissionsService timesheetSubmissionsService = BeanUtil.getBean(TimesheetSubmissionsService.class);
	
	private OrgLocationService orglocationService = BeanUtil.getBean(OrgLocationService.class);
	
	private CandidateService candidateService = BeanUtil.getBean(CandidateService.class);
	
	private CommonUtil commonUtil = BeanUtil.getBean(CommonUtil.class);
	
	private Map<String, Session> sessionMap = new HashMap<>();
	
	private Map<String, Map<String,String>> userDetailsMap = new HashMap<>();
	
	private Thread rateThread;
	 {
		rateThread = new Thread() {
			public void run() {
				while (true) {
					if (userDetailsMap != null)
						triggerCount();
					try {
						sleep(2000);
					} catch (InterruptedException e) {
					}
				}
			};
		};
		rateThread.start();
	}

	@OnMessage
	public void onMessage(Session session, String msg) {
		try {
			System.out.println("received msg " + msg + " from " + session.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@OnOpen
	public void open(Session session) throws NumberFormatException, Exception {
		String userId = session.getRequestParameterMap().get("userId").get(0);
		String orgLocationCode = session.getRequestParameterMap().get("orgLocationCode").get(0);
		String timesheetType = session.getRequestParameterMap().get("timesheetType").get(0);
		String candiadteId = session.getRequestParameterMap().get("candiadteId").get(0);
		Map<String, String> sessionIds = getDetails(userId,orgLocationCode,candiadteId,timesheetType);
		userDetailsMap.remove(userId);
		sessionMap.remove(userId);
		userDetailsMap.put(userId, sessionIds);
		sessionMap.put(userId, session);
		System.out.println("New session opened: " + session.getId());
	}

	private Map<String,String> getDetails(String userId, String orgLocationCode, String candiadteId, String timesheetType) throws NumberFormatException, Exception {
		Map<String,String> sessionIds = new HashMap<>();
		User user = userService.getUserById(Long.parseLong(userId));
		OrgLocations loginOrgLocations = orglocationService.getOrgLocationsByLocationCode(orgLocationCode);
		Candidate candidate = candidateService.getCandidateByCandidateId(Long.parseLong(candiadteId));
		sessionIds.put("userId", user.getId().toString());
		sessionIds.put("orgLocationId", loginOrgLocations.getId().toString());
		sessionIds.put("candiadteId", candidate.getMasterCandidateId().getId().toString());
		sessionIds.put("timesheetType", timesheetType);
		return sessionIds;
	}

	@OnError
	public void error(Session session, Throwable t) {
		String userId = session.getRequestParameterMap().get("userId").get(0);
		sessionMap.remove(userId);
		System.err.println("Error on session " + session.getId());
	}

	@OnClose
	public void closedConnection(Session session) {
		String userId = session.getRequestParameterMap().get("userId").get(0);
		String orgLocationCode = session.getRequestParameterMap().get("orgLocationCode").get(0);
		String timesheetType = session.getRequestParameterMap().get("timesheetType").get(0);
		String candiadteId = session.getRequestParameterMap().get("candiadteId").get(0);
		sessionMap.remove(userId);
		sessionMap.remove(orgLocationCode);
		sessionMap.remove(timesheetType);
		sessionMap.remove(candiadteId);
		System.out.println("session closed: " + session.getId());
	}

	private void triggerCount() {
		try {
			/* Send the new rate to all open WebSocket sessions*/ 
			Iterator<String> i = userDetailsMap.keySet().iterator();
			TimeSheetSubmissionCount timeSheetSubmissionCount = new TimeSheetSubmissionCount();
			while (i.hasNext()) {
				String key = i.next();
				Session session = sessionMap.get(key);
				Map<String,String> umap = userDetailsMap.get(key);
				if (!session.isOpen()) {
					System.err.println("Closed session: " + session.getId());
					sessionMap.remove(key);
				} else {
					
					String timesheetCountType = Constants.ALL_RECORDS;
					if("employes".equalsIgnoreCase(umap.get("timesheetType"))){
						timeSheetSubmissionCount = timesheetSubmissionsService.getTimeSheetSubmissionsCountByUserIdAndOrgLocationIdForApprover(Long.parseLong(umap.get("userId")), Long.parseLong(umap.get("orgLocationId")),timesheetCountType );
					}else if("myTimesheets".equalsIgnoreCase(umap.get("timesheetType"))){
						timeSheetSubmissionCount = timesheetSubmissionsService.getTimeSheetSubmissionsCountByMasterCandidateIdAndOrgLocationId(Long.parseLong(umap.get("candiadteId")), Long.parseLong(umap.get("orgLocationId")),timesheetCountType);
					}
					session.getBasicRemote().sendText(commonUtil.toJSON(timeSheetSubmissionCount));
				}
			}
			//System.out.println("Sending " + timeSheetSubmissionCount.getApprovedTimesheetSubmissionCount() + " to " + sessionMap.size() + " clients");
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}
}
