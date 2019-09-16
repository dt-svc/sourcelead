package com.bis.sourcelead.rest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.TicketCategoryDTO;
import com.bis.sourcelead.dto.TicketCommentDTO;
import com.bis.sourcelead.dto.TicketDTO;
import com.bis.sourcelead.dto.UserDTO;
import com.bis.sourcelead.dto.TicketPriorityDTO;
import com.bis.sourcelead.dto.TicketStatusDTO;
import com.bis.sourcelead.dto.TicketStatusHistoryDTO;
import com.bis.sourcelead.dto.TicketTypeDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Ticket;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.ticketManagement.service.TicketManagementService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.util.dynamicReport.DynamicReport;

@RestController
@RequestMapping("/app")
public class TicketManagementRestController {

	@Value("${static.path}")
	private String staticPath;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private TicketManagementService ticketManagementService;
	
	@Autowired
	private DynamicReport dynamicReport;

	@Autowired
	CommonUtil commonUtil;

	@RequestMapping(value = "/dumpTicketManagementData")
	@ResponseBody
	public String dumpTicketManagementPreDefinedValuesToAllOrgLocations(HttpServletRequest request, @RequestParam(defaultValue="0") long orgLocationId) {
		int count = 0;
		try {
			if(request.getSession().getAttribute("dumpTicketManagementData") == null || (!(Boolean) request.getSession().getAttribute("dumpTicketManagementData"))) {
				request.getSession().setAttribute("dumpTicketManagementData", true);
				System.out.println("-------- Stated Dump of pre-defined Ticket Management values to all OrgLocations --------");
				count = ticketManagementService.dumpTicketManagementPreDefinedValuesToAllOrgLocations(orgLocationId);
				System.out.println("-------- Completed Dump of pre-defined Ticket Management values to all OrgLocations --------");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Added ticket management related data to " + count + " orgLocations";
	}

	@RequestMapping(value = {"/getTicketTypeList", "/rest/getTicketTypeList"}, method = RequestMethod.POST)
	@ResponseBody
	public ResponseList<TicketTypeDTO> getTicketTypeList(HttpServletRequest request, @RequestParam(defaultValue = "") String searchValue,TicketTypeDTO ticketTypeSorting) {
	ResponseList<TicketTypeDTO> responseList = new ResponseList<>();
	try {
	User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	if (user != null) {
	boolean forMobile = request.getRequestURI().contains("rest");
	OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");

	String[] tableColumns = { "id", "name ", "description"};// should be

	// entity names
	int sortColumnIndex = 1; // by default sorting using invoice date
	String sortColumnOrder = "asc"; // by default descending order
	if (ticketTypeSorting.getOrder() != null && !ticketTypeSorting.getOrder().isEmpty()) {
		sortColumnIndex = Integer.parseInt(ticketTypeSorting.getOrder().get(0).get("column"));
		sortColumnOrder = ticketTypeSorting.getOrder().get(0).get("dir");
	}
	ticketTypeSorting.setSortColumn(tableColumns[sortColumnIndex]);
	ticketTypeSorting.setSortOrder(sortColumnOrder);

	List<TicketTypeDTO> ticketTypeDTOList = ticketManagementService.getTicketTypeListByOrgLocationId(loginOrgLocation.getId(), forMobile ? 1 : -1,searchValue,ticketTypeSorting);
	responseList.setData(ticketTypeDTOList);
	responseList.setRecordsTotal(ticketTypeDTOList.size());
	responseList.setRecordsFiltered(ticketTypeDTOList.size());
	responseList.setResponse(Result.SUCCESS.name());
	responseList.setResponseMessage(Result.SUCCESS.getValue());
	} else {
	responseList.setResponse(Result.SESSION_TIMEDOUT.name());
	responseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
	}
	} catch (Exception e) {
	e.printStackTrace();
	responseList.setResponse(Result.AWKWARD.name());
	responseList.setResponseMessage(Result.AWKWARD.getValue());
	}
	return responseList;
	}
	@RequestMapping(value = "/saveTicketType", method = RequestMethod.POST)
	@ResponseBody
	public Response<Long> saveTicketType(HttpServletRequest request, TicketTypeDTO ticketTypeDTO) {
		Response<Long> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			if (user != null) {
				long result = ticketManagementService.saveTicketType(ticketTypeDTO, user.getId(), loginOrgLocation);
				if(result == -1) {
					response.setResponse(Result.DUPLICATE_NAME.name());
					response.setResponseMessage(Result.DUPLICATE_NAME.getValue());
				} else if(result > 0) {
					response.setObject(result);
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				} else {
					response.setResponse(Result.FAILED.name());
					response.setResponseMessage(Result.FAILED.getValue());
				}
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}

	@RequestMapping(value = "/getTicketTypeDataById", method = RequestMethod.POST)
	@ResponseBody
	public Response<TicketTypeDTO> getTicketTypeDataById(HttpServletRequest request, long ticketTypeId) throws Exception {
		Response<TicketTypeDTO> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			if (user != null) {
				response.setObject(ticketManagementService.getTicketTypeDataById(ticketTypeId, user.getId(), loginOrgLocation));
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}

	/*
	 * @RequestMapping(value ="/deleteTicketTypeDataById", method =
	 * RequestMethod.POST)
	 * 
	 * @ResponseBody public Response<Boolean>
	 * deleteTicketTypeDataById(HttpServletRequest request, long ticketTypeId)
	 * throws Exception { Response<Boolean> response = new Response<>(); try { User
	 * user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	 * if (user != null) {
	 * response.setObject(ticketManagementService.deleteTicketTypeDataById(
	 * ticketTypeId)); response.setResponse(Result.SUCCESS.name());
	 * response.setResponseMessage(Result.SUCCESS.getValue()); } else {
	 * response.setResponse(Result.SESSION_TIMEDOUT.name());
	 * response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue()); } } catch
	 * (Exception e) { e.printStackTrace();
	 * response.setResponse(Result.AWKWARD.name());
	 * response.setResponseMessage(Result.AWKWARD.getValue()); } return response; }
	 */

	@RequestMapping(value = "/changeTicketTypeStatusDataById", method = RequestMethod.POST)
	@ResponseBody
	public Response<Boolean> changeStatusTicketTypeDataById(HttpServletRequest request,
			@RequestParam(defaultValue = "0") long id, int status) throws Exception {
		Response<Boolean> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			if (user != null) {
				response.setObject(ticketManagementService.changeStatusTicketTypeDataById(id, status, orgLocation));
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}

	@RequestMapping(value = {"/getTicketCategoryList", "/rest/getTicketCategoryList"}, method = RequestMethod.POST)
	@ResponseBody
	public ResponseList<TicketCategoryDTO> getTicketCategoryList(HttpServletRequest request, @RequestParam(defaultValue = "") String searchValue,TicketCategoryDTO ticketCategorySorting) {
		ResponseList<TicketCategoryDTO> responseList = new ResponseList<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				boolean forMobile = request.getRequestURI().contains("rest");
				OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				String[] tableColumns = { "id", "name ", "description" ,"assignTo"};// should be

				// entity names
				int sortColumnIndex = 1; // by default sorting using name
				String sortColumnOrder = "asc"; // by default descending order
				if (ticketCategorySorting.getOrder() != null && !ticketCategorySorting.getOrder().isEmpty()) {
					sortColumnIndex = Integer.parseInt(ticketCategorySorting.getOrder().get(0).get("column"));
					sortColumnOrder = ticketCategorySorting.getOrder().get(0).get("dir");
				}
				ticketCategorySorting.setSortColumn(tableColumns[sortColumnIndex]);
				ticketCategorySorting.setSortOrder(sortColumnOrder);

				List<TicketCategoryDTO> ticketCategoryDTOList = ticketManagementService.getTicketCategoryListByOrgLocationId(loginOrgLocation.getId(), forMobile ? 1 : -1,searchValue,ticketCategorySorting); 
				responseList.setData(ticketCategoryDTOList);
				responseList.setRecordsTotal(ticketCategoryDTOList.size());
				responseList.setRecordsFiltered(ticketCategoryDTOList.size());
				responseList.setResponse(Result.SUCCESS.name());
				responseList.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				responseList.setResponse(Result.SESSION_TIMEDOUT.name());
				responseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseList.setResponse(Result.AWKWARD.name());
			responseList.setResponseMessage(Result.AWKWARD.getValue());
		}
		return responseList;
	}

	@RequestMapping(value = "/saveTicketCategory", method = RequestMethod.POST)
	@ResponseBody
	public Response<Long> saveTicketCategory(HttpServletRequest request, TicketCategoryDTO ticketCategoryDTO) {
		Response<Long> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			if (user != null) {
				long result = ticketManagementService.saveTicketCategory(ticketCategoryDTO, user, user.getId(), loginOrgLocation);
				if(result == -1) {
					response.setResponse(Result.DUPLICATE_NAME.name());
					response.setResponseMessage(Result.DUPLICATE_NAME.getValue());
				} else if(result > 0) {
					response.setObject(result);
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				} else {
					response.setResponse(Result.FAILED.name());
					response.setResponseMessage(Result.FAILED.getValue());
				}
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}

	@RequestMapping(value = "/getTicketCategoryDataById", method = RequestMethod.POST)
	@ResponseBody
	public Response<TicketCategoryDTO> getTicketCategoryDataById(HttpServletRequest request, long ticketCategoryId,
			OrgLocations OrgLocations) throws Exception {
		Response<TicketCategoryDTO> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				response.setObject(ticketManagementService.getTicketCategoryDataById(ticketCategoryId, OrgLocations));
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}

	/*
	 * @RequestMapping(value ="/deleteTicketCategoryDataById", method =
	 * RequestMethod.POST)
	 * 
	 * @ResponseBody public Response<Boolean>
	 * deleteTicketCategoryDataById(HttpServletRequest request, long
	 * ticketCategoryId) throws Exception { Response<Boolean> response = new
	 * Response<>(); try { User user = (User)
	 * SourceLeadSessionManager.getSessionAttribute(request, "user"); if (user !=
	 * null) {
	 * response.setObject(ticketManagementService.deleteTicketCategoryDataById(
	 * ticketCategoryId)); response.setResponse(Result.SUCCESS.name());
	 * response.setResponseMessage(Result.SUCCESS.getValue()); } else {
	 * response.setResponse(Result.SESSION_TIMEDOUT.name());
	 * response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue()); } } catch
	 * (Exception e) { e.printStackTrace();
	 * response.setResponse(Result.AWKWARD.name());
	 * response.setResponseMessage(Result.AWKWARD.getValue()); } return response; }
	 */

	@RequestMapping(value = "/changeTicketCategoryStatusDataById", method = RequestMethod.POST)
	@ResponseBody
	public Response<Boolean> changeTicketCategoryStatusDataById(HttpServletRequest request,
			@RequestParam(defaultValue = "0") long id, int status) throws Exception {
		Response<Boolean> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			if (user != null) {
				response.setObject(ticketManagementService.changeTicketCategoryStatusDataById(id, status, orgLocation));
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}

	@RequestMapping(value = {"/getTicketPriorityList", "/rest/getTicketPriorityList"}, method = RequestMethod.POST)
	@ResponseBody
	public ResponseList<TicketPriorityDTO> getTicketPriorityList(HttpServletRequest request, @RequestParam(defaultValue = "") String searchValue,TicketPriorityDTO ticketPrioritySorting) {
		ResponseList<TicketPriorityDTO> responseList = new ResponseList<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				boolean forMobile = request.getRequestURI().contains("rest");
				OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				String[] tableColumns = {"id","icon", "name","description" };// should be

				// entity names
				int sortColumnIndex = 2; // by default sorting using name
				String sortColumnOrder = "asc"; // by default descending order
				if (ticketPrioritySorting.getOrder() != null && !ticketPrioritySorting.getOrder().isEmpty()) {
					sortColumnIndex = Integer.parseInt(ticketPrioritySorting.getOrder().get(0).get("column"));
					sortColumnOrder = ticketPrioritySorting.getOrder().get(0).get("dir");
				}
				ticketPrioritySorting.setSortColumn(tableColumns[sortColumnIndex]);
				ticketPrioritySorting.setSortOrder(sortColumnOrder);

				List<TicketPriorityDTO> ticketPriorityDTOList = ticketManagementService.getTicketPriorityListByOrgLocationId(loginOrgLocation.getId(), forMobile ? 1 : -1, searchValue,ticketPrioritySorting);
				responseList.setData(ticketPriorityDTOList);
				responseList.setRecordsTotal(ticketPriorityDTOList.size());
				responseList.setRecordsFiltered(ticketPriorityDTOList.size());
				responseList.setResponse(Result.SUCCESS.name());
				responseList.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				responseList.setResponse(Result.SESSION_TIMEDOUT.name());
				responseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseList.setResponse(Result.AWKWARD.name());
			responseList.setResponseMessage(Result.AWKWARD.getValue());
		}
		return responseList;
	}

	@RequestMapping(value = "/saveTicketPriority", method = RequestMethod.POST)
	@ResponseBody
	public Response<Long> saveTicketPriority(HttpServletRequest request, TicketPriorityDTO ticketPriorityDTO) {
		Response<Long> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			if (user != null) {
				long result = ticketManagementService.saveTicketPriority(ticketPriorityDTO, user.getId(), loginOrgLocation);
				if(result == -1) {
					response.setResponse(Result.DUPLICATE_NAME.name());
					response.setResponseMessage(Result.DUPLICATE_NAME.getValue());
				} else if(result > 0) {
					response.setObject(result);
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				} else {
					response.setResponse(Result.FAILED.name());
					response.setResponseMessage(Result.FAILED.getValue());
				}
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}

	@RequestMapping(value = "/getTicketPriorityDataById", method = RequestMethod.POST)
	@ResponseBody
	public Response<TicketPriorityDTO> getTicketPriorityDataById(HttpServletRequest request, long ticketPriorityId)
			throws Exception {
		Response<TicketPriorityDTO> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			if (user != null) {
				response.setObject(ticketManagementService.getTicketPriorityDataById(ticketPriorityId, loginOrgLocation));
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}

	@RequestMapping(value = "/changeTicketPriorityStatusDataById", method = RequestMethod.POST)
	@ResponseBody
	public Response<Boolean> changeTicketPriorityStatusDataById(HttpServletRequest request,
			@RequestParam(defaultValue = "0") long id, int status) throws Exception {
		Response<Boolean> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			if (user != null) {
				response.setObject(ticketManagementService.changeTicketPriorityStatusDataById(id, status, orgLocation));
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}

	@RequestMapping(value = {"/getTicketStatusList", "/rest/getTicketStatusList"}, method = RequestMethod.POST)
	@ResponseBody
	public ResponseList<TicketStatusDTO> getTicketStatusList(HttpServletRequest request, @RequestParam(defaultValue = "") String searchValue,TicketStatusDTO ticketStatusSorting) {
		ResponseList<TicketStatusDTO> responseList = new ResponseList<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				boolean forMobile = request.getRequestURI().contains("rest");
				OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				String[] tableColumns = { "id", "name", "description", "comment"};// should be

				// entity names
				int sortColumnIndex = 1; // by default sorting using invoice date
				String sortColumnOrder = "asc"; // by default descending order
				if (ticketStatusSorting.getOrder() != null && !ticketStatusSorting.getOrder().isEmpty()) {
					sortColumnIndex = Integer.parseInt(ticketStatusSorting.getOrder().get(0).get("column"));
					sortColumnOrder = ticketStatusSorting.getOrder().get(0).get("dir");
				}
				ticketStatusSorting.setSortColumn(tableColumns[sortColumnIndex]);
				ticketStatusSorting.setSortOrder(sortColumnOrder);
				List<TicketStatusDTO> ticketStatusDTOList = ticketManagementService.getTicketStatusListByOrgLocationId(loginOrgLocation.getId(), forMobile ? 1 : -1, searchValue,ticketStatusSorting); 
				responseList.setData(ticketStatusDTOList);
				responseList.setRecordsTotal(ticketStatusDTOList.size());
				responseList.setRecordsFiltered(ticketStatusDTOList.size());
				responseList.setResponse(Result.SUCCESS.name());
				responseList.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				responseList.setResponse(Result.SESSION_TIMEDOUT.name());
				responseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseList.setResponse(Result.AWKWARD.name());
			responseList.setResponseMessage(Result.AWKWARD.getValue());
		}
		return responseList;
	}

	@RequestMapping(value = "/saveTicketStatus", method = RequestMethod.POST)
	@ResponseBody
	public Response<Long> saveTicketStatus(HttpServletRequest request, TicketStatusDTO ticketStatusDTO) {
		Response<Long> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			if (user != null) {
				long result = ticketManagementService.saveTicketStatus(ticketStatusDTO, user.getId(), loginOrgLocation);
				if(result == -1) {
					response.setResponse(Result.DUPLICATE_NAME.name());
					response.setResponseMessage(Result.DUPLICATE_NAME.getValue());
				} else if(result > 0) {
					response.setObject(result);
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				} else {
					response.setResponse(Result.FAILED.name());
					response.setResponseMessage(Result.FAILED.getValue());
				}
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}

	@RequestMapping(value = "/getTicketStatusDataById", method = RequestMethod.POST)
	@ResponseBody
	public Response<TicketStatusDTO> getTicketStatusDataById(HttpServletRequest request, long ticketStatusId)
			throws Exception {
		Response<TicketStatusDTO> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			if (user != null) {
				response.setObject(ticketManagementService.getTicketStatusDataById(ticketStatusId, loginOrgLocation));
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}

	@RequestMapping(value = {"/changeTicketStatusType", "/rest/changeTicketStatusType"}, method = RequestMethod.POST)
	@ResponseBody
	public Response<Boolean> changeTicketStatusType(HttpServletRequest request, TicketStatusHistoryDTO ticketStatusHistoryDTO) {
		Response<Boolean> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			if (user != null) {
				response = ticketManagementService.changeTicketStatus(ticketStatusHistoryDTO, user, orgLocation);
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}
	
	@RequestMapping(value = "/changeTicketStatusDataById", method = RequestMethod.POST)
	@ResponseBody
	public Response<Boolean> changeTicketStatusDataById(HttpServletRequest request,
			@RequestParam(defaultValue = "0") long id, int status) throws Exception {
		Response<Boolean> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			if (user != null) {
				response.setObject(ticketManagementService.changeTicketStatusDataById(id, status, orgLocation));
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}

	@RequestMapping(value = { "/getTicketList", "/rest/getTicketList" }, method = RequestMethod.POST)
	@ResponseBody
	public ResponseList<TicketDTO> getTicketManagementList(HttpServletRequest request, @RequestParam(defaultValue = "") String searchValue) {
		ResponseList<TicketDTO> responseList = new ResponseList<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				String userRole = null;
				if(request.getRequestURI().contains("rest")) {
					UserRole loginUserRole = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), loginOrgLocation.getId());
					if(loginUserRole != null) {
						userRole = loginUserRole.getRoles().getRoleName();
					}
				} else 
					userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
				List<TicketDTO> ticketDTOList = ticketManagementService.getTicketManagementListByOrgLocationId(loginOrgLocation.getId(), user.getId(), 1, loginOrgLocation.getDateFormat(), userRole, searchValue);
				responseList.setData(ticketDTOList);
				responseList.setRecordsTotal(ticketDTOList.size());
				responseList.setRecordsFiltered(ticketDTOList.size());
				responseList.setResponse(Result.SUCCESS.name());
				responseList.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				responseList.setResponse(Result.SESSION_TIMEDOUT.name());
				responseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseList.setResponse(Result.AWKWARD.name());
			responseList.setResponseMessage(Result.AWKWARD.getValue());
		}
		return responseList;
	}

	@RequestMapping(value = {"/saveTicket"}, method = RequestMethod.POST)
	@ResponseBody
	public Response<Boolean> saveTicket(HttpServletRequest request, TicketDTO ticketDTO) {
		Response<Boolean> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			if (user != null) {
				response.setObject(ticketManagementService.saveTicket(ticketDTO, user, loginOrgLocation));
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}
	
	@RequestMapping(value = {"/rest/saveTicket"}, method = RequestMethod.POST)
	@ResponseBody
	public Response<Boolean> saveTicketForMobile(HttpServletRequest request, @RequestBody TicketDTO ticketDTO) {
		return saveTicket(request, ticketDTO);
	}
	
	@RequestMapping(value = "/getTicketDataById", method = RequestMethod.POST)
	@ResponseBody
	public Response<TicketDTO> getTicketDataById(HttpServletRequest request, long ticketId)
			throws Exception {
		Response<TicketDTO> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			if (user != null) {
				response.setObject(ticketManagementService.getTicketDataById(ticketId, loginOrgLocation.getDateFormat()));
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}
	
	@RequestMapping(value="/changeTicketManagementStatus",method = RequestMethod.POST)
	public Response<Boolean> deleteTicket(HttpServletRequest request, @RequestParam(defaultValue = "0") long ticketId, @RequestParam(defaultValue = "1") int status) {
		Response<Boolean> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				response.setObject(ticketManagementService.changeTicketManagementStatus(ticketId, status));
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}

	@RequestMapping(value = {"/rest/changeTicketManagementStatus"}, method = RequestMethod.POST)
	@ResponseBody
	public Response<Boolean> deleteTicketForMobile(HttpServletRequest request, @RequestParam(defaultValue = "0") long ticketId, @RequestParam(defaultValue = "1") int status) {
		return deleteTicket(request, ticketId, status);
	}


	@RequestMapping(value = "/rest/ticketSummary")
	public Response<TicketDTO> getTicketSummary(HttpServletRequest request, @RequestParam(defaultValue = "") String uuid) {
		Response<TicketDTO> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			if (user != null) {
				response.setObject(ticketManagementService.getTicketSummaryByUUID(uuid, loginOrgLocation.getId(), loginOrgLocation.getDateFormat(), true));
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}

	@RequestMapping(value = "getTicketManagementDescription", method = RequestMethod.POST)
	public TicketDTO getTicketManagementDescription(@RequestParam(value = "uuid", required = false) String uuid, HttpServletRequest request) throws Exception {
		TicketDTO ticketDTO = new TicketDTO();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if (user != null) {
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			ticketDTO = ticketManagementService.getTicketSummaryByUUID(uuid, orgLocations.getId(), orgLocations.getDateFormat(), false);
		}
		return ticketDTO;
	}

	@RequestMapping(value = { "/getTicketCommentList", "/rest/getTicketCommentList" }, method = RequestMethod.POST)
	@ResponseBody
	public ResponseList<TicketCommentDTO> getTicketCommentList(HttpServletRequest request,
			@RequestParam(defaultValue = "0") long ticketId) {
		ResponseList<TicketCommentDTO> responseList = new ResponseList<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				responseList.setData(ticketManagementService.getTicketCommentListByTicketId(ticketId, user.getId(), loginOrgLocation.getId(), loginOrgLocation.getDateFormat()));
				responseList.setResponse(Result.SUCCESS.name());
				responseList.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				responseList.setResponse(Result.SESSION_TIMEDOUT.name());
				responseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseList.setResponse(Result.AWKWARD.name());
			responseList.setResponseMessage(Result.AWKWARD.getValue());
		}
		return responseList;
	}
	
	@RequestMapping(value = {"/saveTicketComment", "/rest/saveTicketComment"}, method = RequestMethod.POST)
	@ResponseBody
	public Response<Boolean> saveTicketComment(HttpServletRequest request, TicketCommentDTO ticketCommentDTO) {
		Response<Boolean> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				response.setObject(ticketManagementService.saveTicketComment(ticketCommentDTO, user, loginOrgLocation));
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}
	
	@RequestMapping(value = "/getTicketComment", method = RequestMethod.POST)
	@ResponseBody
	public Response<TicketCommentDTO> getTicketComment(HttpServletRequest request, @RequestParam(defaultValue = "0") long ticketCommentId) {
		Response<TicketCommentDTO> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				response.setObject(ticketManagementService.getTicketComment(ticketCommentId, loginOrgLocation.getDateFormat()));
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}
	
	@RequestMapping(value = "/deleteTicketComment", method = RequestMethod.POST)
	@ResponseBody
	public Response<Boolean> deleteTicketComment(HttpServletRequest request, @RequestParam(defaultValue = "0") long ticketCommentId) {
		Response<Boolean> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				response.setObject(ticketManagementService.deleteTicketComment(ticketCommentId));
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}
	
	@RequestMapping(value = {"/validateTicketId", "/rest/validateTicketId"}, method = RequestMethod.POST)
	public @ResponseBody Boolean validateTicketId(@RequestParam(value = "generatedId", required = false) String generatedId,
			@RequestParam(value = "ticketId", required = false) Long ticketId, HttpServletRequest request) throws Exception {
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				Ticket ticket = null;
				if (ticketId != null) {
					ticket = ticketManagementService.getTicketSnoAndTicketId(generatedId, ticketId, orgLocation.getId());
				}
				if (ticket == null) {
					return ticketManagementService.validateTicketId(generatedId, orgLocation.getId());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@RequestMapping(value = "/getCategoryAssignToListByTicketCategoryId", method = RequestMethod.POST)
	@ResponseBody
	public ResponseList<UserDTO> getAssignToListByCategoryId(HttpServletRequest request,@RequestParam(defaultValue = "0") long ticketCategoryId) {
		ResponseList<UserDTO> responseList = new ResponseList<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				responseList.setData(ticketManagementService.getCategoryAssignToListByTicketCategoryId(ticketCategoryId, user.getId(), loginOrgLocation.getId()));
				responseList.setResponse(Result.SUCCESS.name());
				responseList.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				responseList.setResponse(Result.SESSION_TIMEDOUT.name());
				responseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseList.setResponse(Result.AWKWARD.name());
			responseList.setResponseMessage(Result.AWKWARD.getValue());
		}
		return responseList;
	}
	
	@RequestMapping(value = {"/getUserListByLoginOrgLocationId", "/rest/getUserListByLoginOrgLocationId"}, method = RequestMethod.POST)
	@ResponseBody
	public ResponseList<User> getAllUsersByOrgLocationId(HttpServletRequest request) {
		ResponseList<User> responseList = new ResponseList<>(); 
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if(user != null) {
				OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				responseList.setData(userService.getAllUsersByOrgLocationCode(orgLocations.getId()));
				responseList.setResponse(Result.SUCCESS.name());
				responseList.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				responseList.setResponse(Result.SESSION_TIMEDOUT.name());
				responseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseList.setResponse(Result.AWKWARD.name());
			responseList.setResponseMessage(Result.AWKWARD.getValue());
		}
		return responseList;
	}
	
	@RequestMapping(value = "/getTicketManagementExportListByOrgLocationId")
	public void getTicketManagementExportListByOrgLocationId(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="exportType", required=false) String exportType) {
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if(user != null) {
				ResponseList<TicketDTO> responseList = new ResponseList<>(); 
				String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
				OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				responseList.setData(ticketManagementService.getTicketManagementListByOrgLocationId(loginOrgLocation.getId(), user.getId(), 1, loginOrgLocation.getDateFormat(), userRole, null));
				File file = null;
				if (exportType.equalsIgnoreCase("pdf")) {
					file = dynamicReport.ticketManagementPdfExport(responseList, loginOrgLocation);
				} else {
					file = dynamicReport.ticketManagementExcelExport(responseList, loginOrgLocation);
				}
				
				String mimeType = URLConnection.guessContentTypeFromName(file.getName());
				if (mimeType == null) {
					mimeType = "application/octet-stream";
				}
				response.setContentType(mimeType);
				StringBuilder fileNameSB = new StringBuilder();
				response.setHeader("Content-Disposition", fileNameSB.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
				response.setContentLength((int) file.length());
				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
				FileCopyUtils.copy(inputStream, response.getOutputStream());
				inputStream.close();
				response.getOutputStream().close();
				response.getOutputStream().flush();	
				if(file!= null){
					file.delete();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
