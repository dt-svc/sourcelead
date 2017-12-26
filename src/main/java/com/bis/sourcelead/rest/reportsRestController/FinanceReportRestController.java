package com.bis.sourcelead.rest.reportsRestController;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.reports.service.FinanceService;
import com.bis.sourcelead.reportsDTO.APAgingDTO;
import com.bis.sourcelead.reportsDTO.AparagingDate;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
@RequestMapping("/app")
public class FinanceReportRestController {
	private static final Logger logger = LoggerFactory.getLogger(FinanceReportRestController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	FinanceService financeService;
	
	@Autowired
	OrgRelationsService orgRelationsService;
	
	@Autowired
	CommonUtil commonUtil;
	
	/**
	 * Returns A/P Aging list JSP
	 * @param request
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/apAgingReport")
	public ModelAndView apAgingReport(HttpServletRequest request) throws Exception
	{
		 ModelAndView model = new ModelAndView();
		 model.setViewName("reports/apAgingReport");
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 return model;
	}
	
	
	@RequestMapping(value = "/arAgingReport")
	public ModelAndView arAgingReport(HttpServletRequest request) throws Exception
	{
		 ModelAndView model = new ModelAndView();
		 model.setViewName("reports/arAgingReport");
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
		 
		 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			List<OrgRelations> vendorList = orgRelationsService.getAllVendorsListByOrgLocationId(orgLocations.getId());
			if(null != vendorList){
				for (OrgRelations orgRelations : vendorList) {
					String orgName = "";
					if(orgRelations.getResponderLocationId().getId().longValue() == orgLocations.getId().longValue()){
						orgName = orgRelations.getLocationId().getOrgLocationName();
					}
					
					if(orgRelations.getLocationId().getId().longValue() == orgLocations.getId().longValue()){
						orgName = orgRelations.getResponderLocationId().getOrgLocationName();
					}
					orgRelations.setOrgName(orgName);
				}
			}
			
		 model.addObject("vendorList", vendorList);
		 model.setViewName("reports/apAgingGraphReport");
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 return model;
	}
	
	
	@RequestMapping(value = "/arAgingGraphReport")
	public ModelAndView arAgingGraphReport(HttpServletRequest request) throws Exception
	{
		 ModelAndView model = new ModelAndView();
		 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 List<OrgRelations> clientList = orgRelationsService.getAllClientsByOrgLocationId(orgLocations.getId());
			if(null != clientList){
				for (OrgRelations orgRelations : clientList) {
					String orgName = "";
					if(orgRelations.getResponderLocationId().getId().longValue() == orgLocations.getId().longValue()){
						orgName = orgRelations.getLocationId().getOrgLocationName();
					}if(orgRelations.getLocationId().getId().longValue() == orgLocations.getId().longValue()){
						orgName = orgRelations.getResponderLocationId().getOrgLocationName();
					}
					orgRelations.setOrgName(orgName);
				}
			}
		 model.addObject("clientList", clientList);
		 model.setViewName("reports/arAgingGraphReport");
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 return model;
	}
	/**
	 *  Get A/P aging Vendor report
	 * @param request
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = {"/vendorapAging","/rest/vendorapAging"}, method = RequestMethod.POST)
	public @ResponseBody ResponseList<APAgingDTO> vendorapAging(APAgingDTO aPAgingDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ResponseList<APAgingDTO> responseList = new ResponseList<>();
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		APAgingDTO apagingdto = financeService.getApAgingListByLocation(orgLocation,aPAgingDTO);
		responseList.setData(apagingdto.getApaAgingList());
		responseList.setRecordsTotal(apagingdto.getRecordsTotal());
		responseList.setRecordsFiltered(apagingdto.getRecordsFiltered());
		return responseList;
	}

	
	/**
	 *  Get A/P aging bill report
	 * @param request
	 * @return ResponseList<APAgingDTO>
	 * @throws Exception
	 */

	@RequestMapping(value = {"/apagingBill","/rest/apagingBill"}, method = RequestMethod.POST)
	public @ResponseBody ResponseList<APAgingDTO> apagingBill(APAgingDTO aPAgingDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		ResponseList<APAgingDTO> responseList = financeService.getApAgingBillListByLocation(orgLocation,aPAgingDTO);
		return responseList;
	}
	
	
	/**
	 *  Get A/P aging Vendor Bill Graph report
	 * @param request
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = {"/vendorapAgingBillGraph","/rest/vendorapAgingBillGraph"}, method = RequestMethod.POST)
	public @ResponseBody APAgingDTO vendorapAgingBillGraph(APAgingDTO aPAgingDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ResponseList<APAgingDTO> responseList = new ResponseList<>();
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		APAgingDTO  apagingdto= financeService.getApAgingListByLocation(orgLocation,aPAgingDTO);
		responseList.setData(apagingdto.getApaAgingList());
		responseList.setRecordsTotal(apagingdto.getRecordsTotal());
		responseList.setRecordsFiltered(apagingdto.getRecordsFiltered());
		
		APAgingDTO apagingDto = new APAgingDTO();
		List<AparagingDate> aparagingDate = new ArrayList<>();
		List<APAgingDTO> apagingDtoList = responseList.getData(); 
		 double tenDaysToatl = 0;
		 double fifteenDaysTotal = 0;
		 double thirtyDaysTotal = 0;
		 double sixtyDaysTotal = 0;
		 double nintyDaysTotal = 0;
		 double oneeightyDaysTotal = 0;
		 double threeSixtyFiveDaysTotal = 0;
		 double above365DaysTotal = 0;
		 double total = 0;
		for(APAgingDTO ap : apagingDtoList) {
			tenDaysToatl = tenDaysToatl + ap.getTenDays();
			fifteenDaysTotal = fifteenDaysTotal + ap.getFifteenDays();
			thirtyDaysTotal = thirtyDaysTotal + ap.getThirtyDays();
			sixtyDaysTotal = sixtyDaysTotal + ap.getSixtyDays();
			nintyDaysTotal = nintyDaysTotal + ap.getNintyDays();
			oneeightyDaysTotal = oneeightyDaysTotal + ap.getOneeightyDays();
			threeSixtyFiveDaysTotal = threeSixtyFiveDaysTotal + ap.getThreeSixtyFiveDays();
			above365DaysTotal = above365DaysTotal + ap.getAbovethreeSixtyFiveDays();
		}
		total = tenDaysToatl + fifteenDaysTotal + thirtyDaysTotal + sixtyDaysTotal + nintyDaysTotal + oneeightyDaysTotal + threeSixtyFiveDaysTotal + above365DaysTotal; 
		if(apagingdto.getAbove365Dates() !=null && apagingdto.getAbove365Dates().size() >0) {
			 Map<Integer,List<Double>> map = new HashMap<>();
			 for(AparagingDate aparagingdate : apagingdto.getAbove365Dates()) {
				 Calendar cal = Calendar.getInstance(); 
				 cal.setTime(aparagingdate.getDate());
				 int year = cal.get(Calendar.YEAR);
	    	   if(map.containsKey(year)) {
	    	   List<Double> amountList = map.get(year);
	    	   amountList.add(aparagingdate.getAmount());
	    	   map.put(year, amountList);
	    	   }else {
	    	   List<Double> amountList = new ArrayList<>();
	    	   amountList.add(aparagingdate.getAmount());
	    	   map.put(year, amountList);
	    	   }
	       }
			 for (Map.Entry<Integer,List<Double>> entry : map.entrySet()) {
				 AparagingDate apar = new AparagingDate();
				 apar.setYear(entry.getKey());
				 double amount = entry.getValue().stream().mapToDouble(am->am.doubleValue()).sum();
				 apar.setAmount(amount);
				 aparagingDate.add(apar);
				}
		}
		
		
		apagingDto.setTenDaysToatl(tenDaysToatl);
		apagingDto.setFifteenDaysTotal(fifteenDaysTotal);
		apagingDto.setThirtyDaysTotal(thirtyDaysTotal);
		apagingDto.setSixtyDaysTotal(sixtyDaysTotal);
		apagingDto.setNintyDaysTotal(oneeightyDaysTotal);
		apagingDto.setOneeightyDaysTotal(oneeightyDaysTotal);
		apagingDto.setThreeSixtyFiveDays(threeSixtyFiveDaysTotal);
		apagingDto.setAbove365DaysTotal(above365DaysTotal);
		apagingDto.setAbove365Dates(aparagingDate);
		apagingDto.setTotal(total);
		return apagingDto;
	}
}
