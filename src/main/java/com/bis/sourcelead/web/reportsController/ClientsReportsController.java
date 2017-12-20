package com.bis.sourcelead.web.reportsController;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.reports.service.ClientReportService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
@RequestMapping("/app")
public class ClientsReportsController {
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	@Autowired
	ClientReportService clientReportService;
	
	@RequestMapping(value = "/clientReports")
	public ModelAndView clientReports(HttpServletRequest request) throws Exception
	{
		OrgLocations location = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		ModelAndView model = new ModelAndView();
		Map<String, Integer> activeAndInactiveCount = clientReportService.getAllClientsCountByOrgLocationIdAndUserId(location.getId(), user.getId());
		int activeClientsCount = activeAndInactiveCount.get("activeClient");
		int inActiveClientsCount = activeAndInactiveCount.get("inActiveClient");
		int totalClientsCount = activeClientsCount + inActiveClientsCount;
		model.addObject("activeClientsCount", activeClientsCount);
		model.addObject("inActiveClientsCount", inActiveClientsCount);
		model.addObject("totalClientsCount", totalClientsCount);
		Integer totalSpocCount = clientReportService.getAllClientsSpocCountByOrgLocationIdAndUserId(location.getId(), user.getId());
		model.setViewName("reports/clientReports");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
}
