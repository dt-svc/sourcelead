package com.bis.sourcelead.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.SourceleadNotesDTO;
import com.bis.sourcelead.entity.Asset;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.SourceleadBlogs;
import com.bis.sourcelead.entity.SourceleadNotes;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.AssetService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.ContractsTemplateService;
import com.bis.sourcelead.service.InvoiceService;
import com.bis.sourcelead.service.OrgLocationService;
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
@RequestMapping("/app")
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
	private UserService userService;
	
	@Autowired
	private OrgLocationService orgLocationService;
	
	@Autowired
	private SourceleadNotesService sourceleadNotesService;
	
	@Autowired
	private ContractsTemplateService contractsTemplateService;
	
	@Autowired
	private AssetService assetService;
	
	
	@RequestMapping(value = "/storeNotes", method = RequestMethod.POST)
	public @ResponseBody Response<String> storeNotes(SourceleadNotesDTO sourceleadNotesDTO,
			@RequestParam("userId") Long userId,
			@RequestParam("locationCode") String locationCode) throws Exception
	{
		
			Response<String> response = new Response<>();
			User user =  userService.getUserById(userId);
			OrgLocations orgLocation = orgLocationService.getOrgLocationsByLocationCode(locationCode);
			try {
				if (user != null & orgLocation != null) {
	
					if (Validator.validate(sourceleadNotesDTO)) {
						sourceleadNotesService.storeComments(sourceleadNotesDTO, user,orgLocation);
						response.setResponse(Result.SUCCESS.name());
						response.setResponseMessage(Result.SUCCESS.getValue());
					} else {
						response.setResponse(Result.INVALID_DATA.name());
						response.setResponseMessage(Result.INVALID_DATA.getValue());
					}
	
				} else {
					response.setResponse(Result.SESSION_TIMEDOUT.name());
					response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
				}
			} catch (Exception e) {
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
			}
		return response;
	}
	
	
	@RequestMapping(value = "/getNotes", method = RequestMethod.POST)
	public @ResponseBody String getNotes(@RequestParam(value = "orgRelationId", required = false) Long orgRelationId,HttpServletRequest request) throws Exception
	{
		String notesList;
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<SourceleadNotes> list = sourceleadNotesService.getNotesListByOrglocationIdAndType(orgRelationId,orgLocation.getId());
		notesList = commonUtil.toJSON(list);
		return notesList;
	}
	@RequestMapping(value = "/getNotesInvoice", method = RequestMethod.POST)
	public @ResponseBody String getNotesInvoice(@RequestParam(value = "invoiceUUID", required = false) String invoiceUUID,HttpServletRequest request) throws Exception
	{
		String notesList;
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Invoice invoice = invoiceService.getInvoicetByInvoiceUUID(invoiceUUID);
		List<SourceleadNotes> list = sourceleadNotesService.getNotesListByInvoiceID(invoice.getId(),orgLocation.getId());
		notesList = commonUtil.toJSON(list);
		return notesList;
	}
	
	
	@RequestMapping(value = "/getContractNotes", method = RequestMethod.POST)
	public @ResponseBody String getContractNotes(@RequestParam(value = "contractUUID", required = false) String contractUUID,HttpServletRequest request) throws Exception
	{
		String notesList;
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Contracts contracts = contractsTemplateService.getContractByContractUUID(contractUUID);
		List<SourceleadNotes> list = sourceleadNotesService.getNotesListByContractId(contracts.getId(),orgLocation.getId());
		notesList = commonUtil.toJSON(list);
		return notesList;
	}
	
	
	@RequestMapping(value = "/getRequirementNotes", method = RequestMethod.POST)
	public @ResponseBody ResponseList<SourceleadNotes> getRequirementNotes(@RequestParam(value = "requirementId", required = false) Long requirementId,HttpServletRequest request) throws Exception
	{
		ResponseList<SourceleadNotes> response = new ResponseList<>();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if(user != null){
			try {
					OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
					List<SourceleadNotes> list = sourceleadNotesService.getNotesListByRequirementId(requirementId,orgLocation.getId());
					response.setData(list);
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				}catch(Exception e) {
					response.setResponse(Result.AWKWARD.name());
					response.setResponseMessage(Result.AWKWARD.getValue());
			}
		}else {
			response.setResponse(Result.SESSION_TIMEDOUT.name());
			response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}
		return response;
	}
	
	@RequestMapping(value = "/getAssetNotes", method = RequestMethod.POST)
	public @ResponseBody String getAssetNotes(@RequestParam(value = "assetUUID", required = false) String assetUUID,HttpServletRequest request) throws Exception
	{
		String notesList;
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Asset asset = assetService.getAssetByUUID(assetUUID);
		List<SourceleadNotes> list = sourceleadNotesService.getNotesListByAssetId(asset.getId(),orgLocation.getId());
		notesList = commonUtil.toJSON(list);
		return notesList;
	}
}