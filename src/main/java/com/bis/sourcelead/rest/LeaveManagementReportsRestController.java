package com.bis.sourcelead.rest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.dao.LeaveDao;
import com.bis.sourcelead.reportsDTO.LeaveBalanceReportDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.UserDTO;
import com.bis.sourcelead.entity.LeaveReportView;
import com.bis.sourcelead.entity.LeaveType;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.AssociateBalanceLeaveService;
import com.bis.sourcelead.service.LeaveService;
import com.bis.sourcelead.service.LeaveTypeService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@RequestMapping("/app")
public class LeaveManagementReportsRestController {
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Value("${static.path}")
	private String staticPath;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	TimeZoneService timeZoneService;
	@Autowired
	LeaveService leaveService;
	
	@Autowired
	LeaveTypeService leaveTypeService;
	
	@Autowired
	UserService userService;
	@Autowired
	AssociateBalanceLeaveService associateBalanceLeaveService;
	
	@RequestMapping(value = "/daywiseLeaveReport")
	public ModelAndView approvedLeavesJsp(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("reports/daywiseLeaveReport");
		return model;
	}
	
	@RequestMapping(value = "/leaveBalancesReport")
	public ModelAndView leaveBalances(HttpServletRequest request,
			HttpServletResponse response) {
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		List<String> leaveTypeNames = leaveService.getLeaveTypeNamesByOrgLocationId(orgLocation.getId());
		model.addObject("leaveTypeNames", leaveTypeNames);
		model.setViewName("reports/leaveBalancesReports");
		return model;
	}
	
	 
	
	@RequestMapping(value = "/getDaywiseLeaveReportList", method = RequestMethod.GET)
	@ResponseBody
	public ResponseList<LeaveReportView> getDaywiseLeaveReportList(@RequestParam(value="fromDate", required=false) String fromDate,@RequestParam(value="searchValue", required=false) String searchValue,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
//		ResponseList<LeaveReportView> leaveReportsList =  new ResponseList<>();
		ResponseList<LeaveReportView> leaveReportsList = leaveService.getLeaveReportsOrglocationIdAndFromToDate(orgLocation.getOrg().getId(),orgLocation.getId(),fromDate,searchValue);
		leaveReportsList.getData().stream().forEach(u -> {
    		   u.setFromDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), u.getFromDate()));
    		   u.setToDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), u.getToDate()));
    	   });
//    	   leaveReportsList.setData(leaveList);
		
		
		return leaveReportsList;
}
	
	@RequestMapping(value="/getLeaveBalanceReportList",method = RequestMethod.GET)
	@ResponseBody
	public ResponseList<LeaveBalanceReportDTO> getLeaveBalanceReportList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ResponseList<LeaveBalanceReportDTO> resList = new ResponseList<>();
		LeaveBalanceReportDTO leaveBalanceReportDto = new LeaveBalanceReportDTO();
		List<LeaveBalanceReportDTO> list = new ArrayList<>();
		try {
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<LeaveType> leaveTypeList = leaveTypeService.getLeaveTypeListByLocationIdAndStatus(String.valueOf(orgLocation.getId()));
		List<String> leaveTypeNames = leaveService.getLeaveTypeNamesByOrgLocationId(orgLocation.getId());
		List<LeaveBalanceReportDTO> users =  userService.getUserNameAndTypeByOrgLocationId(orgLocation.getId());
		Map<String,Float> map = new HashMap<>();
		for(LeaveBalanceReportDTO ltDto : users) {
			for(LeaveType lt : leaveTypeList) {
				float leaveTypeBalance = associateBalanceLeaveService.getTotalLeavesCountByUserIdAndLeaveTypeId(lt.getId(),ltDto.getUserId().longValue(),orgLocation.getId());
				map.put(lt.getName(), leaveTypeBalance);
			}
		}
		leaveBalanceReportDto.setLeaveTypeNames(leaveTypeNames);
		leaveBalanceReportDto.setMap(map);
		if(users != null) {
		list.add(leaveBalanceReportDto);
		list.addAll(users);
		resList.setData(list);
		resList.setResponse(Result.SUCCESS.name());
		resList.setResponseMessage(Result.SUCCESS.getValue());
	} else {
		resList.setResponse(Result.SESSION_TIMEDOUT.name());
		resList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
	}

} catch (Exception e) {
	resList.setResponse(Result.AWKWARD.name());
	resList.setResponseMessage(Result.AWKWARD.getValue());
	//logger.error(ExceptionUtils.getFullStackTrace(e));
}	
		return resList;
	}
}
