package com.bis.sourcelead.web.reportsController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.service.OrgRelationsService;

@Controller
@RequestMapping("/app")
public class ClientReportController {

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	OrgRelationsService orgRelationsService;
	
	@RequestMapping(value = "/clientReportSummary")
	public ModelAndView clientReportSummary(@RequestParam(value = "orgRelationUUID", required = false) String orgRelationUUID,@RequestParam(value = "invoiceType", required = false) String invoiceType,@RequestParam(value = "type", required = false) String type) throws Exception {
		ModelAndView model = new ModelAndView();
		
		OrgRelations orgRelations = orgRelationsService.getOrgRelationsByUUID(orgRelationUUID);
		if(orgRelations != null) {
			model.addObject("sourceleadUrl", sourceleadUrl);
			model.addObject("orgRelationUUID",orgRelations.getUUID());
			model.addObject("invoiceType",invoiceType);
			model.addObject("type",type);
			model.setViewName("reports/clientReportSummary");
		}else {
			model.setViewName("registration/persmissionDenied");
		}
		
		return model;
	}

}
