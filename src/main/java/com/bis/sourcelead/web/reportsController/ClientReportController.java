package com.bis.sourcelead.web.reportsController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;

@Controller
@RequestMapping("/app")
public class ClientReportController {

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	private OrgSpocService orgSpocService;
	
	@Autowired
	private OrgRelationsService orgRelationsService;
	
	@RequestMapping(value = "/clientReportSummary")
	public ModelAndView clientReportSummary(@RequestParam(value = "orgRelationUUID", required = false) String orgRelationUUID,@RequestParam(value = "orgSpocUUID", required = false) String orgSpocUUID,@RequestParam(value = "invoiceType", required = false) String invoiceType,@RequestParam(value = "type", required = false) String type) throws Exception {
		ModelAndView model = new ModelAndView();
		
		OrgRelations orgRelations = null;
		OrgSpoc spoc = null;
		if(orgRelationUUID != null) {
			 orgRelations = orgRelationsService.getOrgRelationsByUUID(orgRelationUUID);
			 model.addObject("orgRelationUUID",orgRelations.getUUID());
		}else if(orgSpocUUID != null){
			spoc = orgSpocService.getOrgSpocByUUID(orgSpocUUID);
			model.addObject("orgSpocUUID",orgSpocUUID);
		}else{
			model.setViewName("registration/persmissionDenied");
		}
		
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("invoiceType",invoiceType);
		model.addObject("type",type);
		model.setViewName("reports/clientReportSummary");
		
		return model;
	}

}
