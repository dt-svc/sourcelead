package com.bis.sourcelead;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.DashBoardSocketCountDTO;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.TimeSheetSubmissionCount;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.timesheet.service.TimesheetSubmissionsService;
import com.bis.sourcelead.util.CommonUtil;

@ServerEndpoint("/app/ws/dashBoardDynamicCount")
public class DashBoardSocketCount {
	
	private UserService userService = BeanUtil.getBean(UserService.class);
	private TimesheetSubmissionsService timesheetSubmissionsService = BeanUtil.getBean(TimesheetSubmissionsService.class);
	private OrgLocationService orglocationService = BeanUtil.getBean(OrgLocationService.class);
	private CandidateService candidateService = BeanUtil.getBean(CandidateService.class);
	private CommonUtil commonUtil = BeanUtil.getBean(CommonUtil.class);
	
	private static ConcurrentHashMap<String, Session> sessionMap = new ConcurrentHashMap<>();
	private Thread rateThread;
	private volatile boolean runnable = true;
	
	public void initThread(final DashBoardSocketCountDTO dashBoardSocketCountDTO) 
	 {
		rateThread = new Thread() {
			public void run() {
				while (runnable) { 
					if (dashBoardSocketCountDTO.getSession().isOpen()) {
						triggerCount(dashBoardSocketCountDTO);
						try {
							sleep(10000);
						} catch (InterruptedException e) {
						}
					} else {
						rateThread.interrupt();
					}
					
				}
			};
		};
		rateThread.start();
	}

	@OnMessage
	public void  onMessage(Session session, String msg) {
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
		String isGlobalAccess = session.getRequestParameterMap().get("isGlobalAccess").get(0);
		Iterator<String> it = sessionMap.keySet().iterator();
		while(it.hasNext()) {
			if(it.next().equals(userId)) {
				sessionMap.get(userId).close();
				sessionMap.remove(userId);
			}
		}
		DashBoardSocketCountDTO dashBoardCountDto = getDetails(userId,orgLocationCode,candiadteId,timesheetType,isGlobalAccess);
		dashBoardCountDto.setSession(session);
		sessionMap.put(userId, session);
		/*System.out.println("New session opened: " + session.getId());*/
		initThread(dashBoardCountDto);
	}

	private DashBoardSocketCountDTO getDetails(String userId, String orgLocationCode, String candiadteId, String timesheetType,String isGlobalAccess) throws NumberFormatException, Exception {
		User user = userService.getUserById(Long.parseLong(userId));
		OrgLocations loginOrgLocations = orglocationService.getOrgLocationsByLocationCode(orgLocationCode);
		Candidate candidate = candidateService.getCandidateByCandidateId(Long.parseLong(candiadteId));
		DashBoardSocketCountDTO dto = new DashBoardSocketCountDTO();
		dto.setUserId(user.getId().toString());
		dto.setCandidateId(candidate.getMasterCandidateId().getId().toString());
		dto.setOrgLocationId(loginOrgLocations.getId().toString());
		dto.setTimesheetType(timesheetType);
		dto.setIsGlobalAccess(isGlobalAccess);
		return dto;
	}

	@OnError
	public void error(Session session, Throwable t) throws IOException {
		String userId = session.getRequestParameterMap().get("userId").get(0);
		sessionMap.remove(userId);
		runnable = false;
		/*System.err.println("Error on session " + session.getId());*/
	}

	@OnClose
	public void closedConnection(Session session) {
		String userId = session.getRequestParameterMap().get("userId").get(0);
		Iterator<String> it = sessionMap.keySet().iterator();
		while(it.hasNext()) {
			if(it.next().equals(userId)) {
				try {
					sessionMap.get(userId).close();
				} catch (IOException e) {}
				sessionMap.remove(userId);
			}
		}
		runnable = false;
		/*System.out.println("session closed: " + session.getId());*/
	}

	private void triggerCount(DashBoardSocketCountDTO dashBoardSocketCountDTO) {
		try {
			TimeSheetSubmissionCount timeSheetSubmissionCount = new TimeSheetSubmissionCount();
			
			if(dashBoardSocketCountDTO.getSession() != null && dashBoardSocketCountDTO.getSession().isOpen()){
					String timesheetCountType = Constants.ALL_RECORDS;
					if("employes".equalsIgnoreCase(dashBoardSocketCountDTO.getTimesheetType())){
						timeSheetSubmissionCount = timesheetSubmissionsService.getTimeSheetSubmissionsCountByUserIdAndOrgLocationIdForApprover(Long.parseLong(dashBoardSocketCountDTO.getUserId()), Long.parseLong(dashBoardSocketCountDTO.getOrgLocationId()),Long.parseLong(dashBoardSocketCountDTO.getOrgLocationId()),timesheetCountType,dashBoardSocketCountDTO.getIsGlobalAccess(),false);
					}else if("myTimesheets".equalsIgnoreCase(dashBoardSocketCountDTO.getTimesheetType())){
						timeSheetSubmissionCount = timesheetSubmissionsService.getTimeSheetSubmissionsCountByMasterCandidateIdAndOrgLocationId(Long.parseLong(dashBoardSocketCountDTO.getCandidateId()), Long.parseLong(dashBoardSocketCountDTO.getOrgLocationId()),Long.parseLong(dashBoardSocketCountDTO.getOrgLocationId()),timesheetCountType);
					}
					if(timeSheetSubmissionCount != null && dashBoardSocketCountDTO.getSession().isOpen()) {
						dashBoardSocketCountDTO.getSession().getBasicRemote().sendText(commonUtil.toJSON(timeSheetSubmissionCount));
					}
					
					
					
				} else {
					/*System.err.println("Closed session: " + dashBoardSocketCountDTO.getSession().getId());*/
					sessionMap.remove(dashBoardSocketCountDTO.getUserId());
				}
			
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}
}
