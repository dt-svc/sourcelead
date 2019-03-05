package com.bis.sourcelead.rest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

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
import com.bis.sourcelead.dto.TicketPriorityDTO;
import com.bis.sourcelead.dto.TicketStatusDTO;
import com.bis.sourcelead.dto.TicketTypeDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.ticketManagement.service.TicketManagementService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@RequestMapping("/app")
public class TicketManagementRestController {

	@Value("${static.path}")
	private String staticPath;

	@Autowired
	private TicketManagementService ticketManagementService;

	@Autowired
	CommonUtil commonUtil;

	@RequestMapping(value = "/dumpTicketManagementData")
	@ResponseBody
	public String dumpTicketManagementPreDefinedValuesToAllOrgLocations(HttpServletRequest request) {
		int count = 0;
		try {
			System.out.println("-------- Stated Dump of pre-defined Ticket Management values to all OrgLocations --------");
			count = ticketManagementService.dumpTicketManagementPreDefinedValuesToAllOrgLocations();
			System.out.println("-------- Completed Dump of pre-defined Ticket Management values to all OrgLocations --------");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Added ticket management related data to " + count + " orgLocations";
	}

	@RequestMapping(value = {"/getTicketTypeList", "/rest/getTicketTypeList"}, method = RequestMethod.POST)
	@ResponseBody
	public ResponseList<TicketTypeDTO> getTicketTypeList(HttpServletRequest request) {
		ResponseList<TicketTypeDTO> responseList = new ResponseList<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				boolean forMobile = request.getRequestURI().contains("rest");
				OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				responseList.setData(ticketManagementService.getTicketTypeListByOrgLocationId(loginOrgLocation.getId(), forMobile ? 1 : -1));
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
	public Response<Boolean> saveTicketType(HttpServletRequest request, TicketTypeDTO ticketTypeDTO) {
		Response<Boolean> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			if (user != null) {
				response.setObject(ticketManagementService.saveTicketType(ticketTypeDTO, user.getId(), loginOrgLocation));
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
	public ResponseList<TicketCategoryDTO> getTicketCategoryList(HttpServletRequest request) {
		ResponseList<TicketCategoryDTO> responseList = new ResponseList<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				boolean forMobile = request.getRequestURI().contains("rest");
				OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				responseList.setData(ticketManagementService.getTicketCategoryListByOrgLocationId(loginOrgLocation.getId(), forMobile ? 1 : -1));
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
	public Response<Boolean> saveTicketCategory(HttpServletRequest request, TicketCategoryDTO ticketCategoryDTO) {
		Response<Boolean> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			if (user != null) {
				response.setObject(ticketManagementService.saveTicketCategory(ticketCategoryDTO, user.getId(), loginOrgLocation));
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
	public ResponseList<TicketPriorityDTO> getTicketPriorityList(HttpServletRequest request) {
		ResponseList<TicketPriorityDTO> responseList = new ResponseList<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				boolean forMobile = request.getRequestURI().contains("rest");
				OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				responseList.setData(ticketManagementService.getTicketPriorityListByOrgLocationId(loginOrgLocation.getId(), forMobile ? 1 : -1));
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
	public Response<Boolean> saveTicketPriority(HttpServletRequest request, TicketPriorityDTO ticketPriorityDTO) {
		Response<Boolean> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			if (user != null) {
				ticketPriorityDTO.setStaticPath(staticPath);
				response.setObject(ticketManagementService.saveTicketPriority(ticketPriorityDTO, user.getId(), loginOrgLocation));
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

	/*
	 * @RequestMapping(value ="/deleteTicketPriorityDataById", method =
	 * RequestMethod.POST)
	 * 
	 * @ResponseBody public Response<Boolean>
	 * deleteTicketPriorityDataById(HttpServletRequest request, long
	 * ticketPriorityId) throws Exception { Response<Boolean> response = new
	 * Response<>(); try { User user = (User)
	 * SourceLeadSessionManager.getSessionAttribute(request, "user"); if (user !=
	 * null) {
	 * response.setObject(ticketManagementService.deleteTicketPriorityDataById(
	 * ticketPriorityId)); response.setResponse(Result.SUCCESS.name());
	 * response.setResponseMessage(Result.SUCCESS.getValue()); } else {
	 * response.setResponse(Result.SESSION_TIMEDOUT.name());
	 * response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue()); } } catch
	 * (Exception e) { e.printStackTrace();
	 * response.setResponse(Result.AWKWARD.name());
	 * response.setResponseMessage(Result.AWKWARD.getValue()); } return response; }
	 */

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
	public ResponseList<TicketStatusDTO> getTicketStatusList(HttpServletRequest request) {
		ResponseList<TicketStatusDTO> responseList = new ResponseList<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				boolean forMobile = request.getRequestURI().contains("rest");
				OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				responseList.setData(ticketManagementService.getTicketStatusListByOrgLocationId(loginOrgLocation.getId(), forMobile ? 1 : -1));
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
	public Response<Boolean> saveTicketStatus(HttpServletRequest request, TicketStatusDTO ticketStatusDTO) {
		Response<Boolean> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			if (user != null) {
				response.setObject(ticketManagementService.saveTicketStatus(ticketStatusDTO, user.getId(), loginOrgLocation));
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

	/*
	 * @RequestMapping(value ="/deleteTicketStatusDataById", method =
	 * RequestMethod.POST)
	 * 
	 * @ResponseBody public Response<Boolean>
	 * deleteTicketStatusDataById(HttpServletRequest request, long ticketStatusId)
	 * throws Exception { Response<Boolean> response = new Response<>(); try { User
	 * user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	 * if (user != null) {
	 * response.setObject(ticketManagementService.deleteTicketStatusDataById(
	 * ticketStatusId)); response.setResponse(Result.SUCCESS.name());
	 * response.setResponseMessage(Result.SUCCESS.getValue()); } else {
	 * response.setResponse(Result.SESSION_TIMEDOUT.name());
	 * response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue()); } } catch
	 * (Exception e) { e.printStackTrace();
	 * response.setResponse(Result.AWKWARD.name());
	 * response.setResponseMessage(Result.AWKWARD.getValue()); } return response; }
	 */

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
	public ResponseList<TicketDTO> getTicketManagementList(HttpServletRequest request) {
		ResponseList<TicketDTO> responseList = new ResponseList<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				responseList.setData(ticketManagementService.getTicketManagementListByOrgLocationId(loginOrgLocation.getId(), user.getId(), loginOrgLocation.getDateFormat()));
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

	@RequestMapping(value = { "/saveTicket", "/rest/saveTicket" }, method = RequestMethod.POST)
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

	@RequestMapping(value = "/rest/ticketSummary")
	public Response<TicketDTO> getTicketSummary(HttpServletRequest request, @RequestParam(defaultValue = "") String uuid) {
		Response<TicketDTO> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			if (user != null) {
				response.setObject(ticketManagementService.getTicketSummaryByUUID(uuid, user.getId(), loginOrgLocation.getId(), loginOrgLocation.getDateFormat()));
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
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		TicketDTO ticketDTO = ticketManagementService.getTicketSummaryByUUID(uuid, user.getId(), orgLocations.getId(), orgLocations.getDateFormat());
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
				responseList.setData(ticketManagementService.getTicketCommentListByTicketId(ticketId, user.getId(), loginOrgLocation.getId()));
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
}
