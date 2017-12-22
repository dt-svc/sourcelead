package com.bis.sourcelead.rest.reportsRestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.dto.LeaveDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.reports.service.FinanceService;
import com.bis.sourcelead.reportsDTO.APAgingDTO;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
@RequestMapping("/app")
public class FinanceReportRestController {
	private static final Logger logger = LoggerFactory.getLogger(FinanceReportRestController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	FinanceService financeService;
	
	/**
	 * Returns A/P Aging list JSP
	 * @param request
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/apAgingReport")
	public ModelAndView clientReports(HttpServletRequest request) throws Exception
	{
		 ModelAndView model = new ModelAndView();
		 model.setViewName("reports/apAgingReport");
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 return model;
	}
	
	/**
	 *  Returns A/P Aging hraph JSP
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/apAgingGraphReport")
	public ModelAndView apAgingGraphReport(HttpServletRequest request) throws Exception
	{
		 ModelAndView model = new ModelAndView();
		 model.setViewName("reports/apAgingGraphReport");
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 return model;
	}
	
	
	/**
	 *  Get A/P aging report
	 * @param request
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = {"/vendorapAging","/rest/vendorapAging"}, method = RequestMethod.POST)
	public @ResponseBody ResponseList<APAgingDTO> vendorapAging(APAgingDTO aPAgingDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		ResponseList<APAgingDTO> responseList = financeService.getApAgingListByLocation(orgLocation,aPAgingDTO);
		return responseList;
	}

}
