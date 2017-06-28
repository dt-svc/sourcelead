package com.bis.sourcelead.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.SourceleadNotes;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.InvoiceService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.SourceleadNotesService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
/**
 * 
 * @author Ajith
 * Handers all Notes actions
 *
 */

@RestController
@PropertySource("${propertyLocation:app.properties}")
public class SourceleadNotesRestController {
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	OrgSpocService orgSpocService;
	
	@Autowired
	RequirementService requirementService;
	
	@Autowired
	OrgRelationsService orgRelationsService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	CandidateService candidateService;
	
	@Autowired
	InvoiceService invoiceService;
	
	@Autowired
	UserService userService;
	@Autowired
	SourceleadNotesService sourceleadNotesService;
	
	
	
	@RequestMapping(value = "/storeNotes", method = RequestMethod.POST)
	public @ResponseBody String storeNotes(@RequestParam(value = "json", required = false) String json, HttpServletRequest request) throws Exception
	{
		String jsonc = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(jsonc);
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		sourceleadNotesService.storeComments(jsonObj, user);
		return "success";
	}
	
	
	@RequestMapping(value = "/getNotes", method = RequestMethod.POST)
	public @ResponseBody String getNotes(@RequestParam(value = "orgRelationId", required = false) Long orgRelationId) throws Exception
	{
		String notesList;
		List<SourceleadNotes> list = sourceleadNotesService.getNotesListByOrglocationIdAndType(orgRelationId);
		notesList = commonUtil.toJSON(list);
		return notesList;
	}
	@RequestMapping(value = "/getNotesInvoice", method = RequestMethod.POST)
	public @ResponseBody String getNotesInvoice(@RequestParam(value = "invoiceUUID", required = false) String invoiceUUID) throws Exception
	{
		String notesList;
			Invoice invoice = invoiceService.getInvoicetByInvoiceUUID(invoiceUUID);
		List<SourceleadNotes> list = sourceleadNotesService.getNotesListByInvoiceID(invoice.getId());
		notesList = commonUtil.toJSON(list);
		return notesList;
	}
}