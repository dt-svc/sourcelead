package com.bis.sourcelead.web;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.dto.OrgJobBoardAccountDTO;
import com.bis.sourcelead.dto.OrgJobBoardAccountUserDTO;
import com.bis.sourcelead.dto.OrgJobBoardDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.JobBoardConfigurationService;
import com.bis.sourcelead.service.TeamService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Raja Gopal Reddy P
 */
@Controller
@RequestMapping("/app")
public class JobBoardController {

    @Value("${sourceleadUrl}")
    private String sourceleadUrl;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private TeamService teamService;

    @Autowired
    private JobBoardConfigurationService jobBoardConfigurationService;

    @RequestMapping("/job-boards")
    public String jobBoardConfiguration(HttpServletRequest request, ModelMap map, boolean error) {
        Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
        if (org == null) {
            map.addAttribute("error", "Session Timedout");
        } else {
            map.addAttribute("jobBoardList", jobBoardConfigurationService.getOrgJobBoardList(org.getId(), false));
        }
        if (error) {
            map.addAttribute("error", "No job boards enabled");
        }
        map.addAttribute("sourceleadUrl", sourceleadUrl);
        return "jobBoard/configuration";
    }

    @RequestMapping(value = "/save-job-board", method = RequestMethod.POST)
    @ResponseBody
    public Response<Long> saveJobBoard(HttpServletRequest request, OrgJobBoardDTO orgJobBoardDTO) {
        Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
        Response<Long> response = new Response<>();
        if (org == null) {
            response.setResponse(Result.SESSION_TIMEDOUT.name());
            response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
        } else {
            orgJobBoardDTO.setOrgId(org.getId());
            orgJobBoardDTO.setActive(true);
            long i = jobBoardConfigurationService.saveOrgJobBoard(orgJobBoardDTO);
            if (i >= 1) {
                response.setResponse(Result.SUCCESS.name());
                response.setResponseMessage(Result.SUCCESS.getValue());
                response.setObject(i);
            } else {
                response.setResponse(Result.FAILED.name());
                response.setResponseMessage(Result.FAILED.getValue());
            }
        }
        return response;
    }

    @RequestMapping("/change-job-board-satatus")
    @ResponseBody
    public Response changeJobBoardStatus(HttpServletRequest request, long orgJobBoardId) {
        Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
        Response<Long> response = new Response<>();
        if (org == null) {
            response.setResponse(Result.SESSION_TIMEDOUT.name());
            response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
        } else {
            int i = jobBoardConfigurationService.changeJobBoardStatus(orgJobBoardId);
            if (i == 1) {
                response.setResponse(Result.SUCCESS.name());
                response.setResponseMessage(Result.SUCCESS.getValue());
            } else {
                response.setResponse(Result.FAILED.name());
                response.setResponseMessage(Result.FAILED.getValue());
            }
        }
        return response;
    }

    @RequestMapping("/manage-job-board-accounts")
    public String jobBoardAccounts(HttpServletRequest request, ModelMap map) {
        Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
        OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
        if (org == null) {
            map.addAttribute("error", "Session Timedout");
        } else {
            List<OrgJobBoardDTO> jobBoardList = jobBoardConfigurationService.getOrgJobBoardList(org.getId(), false);
            if (jobBoardList.isEmpty()) {
                return "redirect:/app/job-boards?error=true";
            } else {
                map.addAttribute("jobBoardList", jobBoardList);
                try {
                    map.addAttribute("userList", userService.getAllUsersByOrgLocationCode(orgLocation.getId()));
                    map.addAttribute("teamList", teamService.getTeamListByOrgLocationID(orgLocation.getId()));
                } catch (Exception ex) {
                    Logger.getLogger(JobBoardController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        map.addAttribute("sourceleadUrl", sourceleadUrl);
        return "jobBoard/accounts";
    }

    @RequestMapping(value = "/org-job-board-accounts", method = RequestMethod.POST)
    @ResponseBody
    public ResponseList<OrgJobBoardAccountDTO> orgJobBoardAccounts(HttpServletRequest request, long jobBoardId, String search) {
        Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
        ResponseList<OrgJobBoardAccountDTO> response = new ResponseList<>();
        if (org == null) {
            response.setResponse(Result.SESSION_TIMEDOUT.name());
            response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
        } else {
            List<OrgJobBoardAccountDTO> list = jobBoardConfigurationService.getOrgJobBoardAccountList(org.getId(), jobBoardId, search, 0, 0);
            response.setData(list);
            response.setResponse(Result.SUCCESS.name());
            response.setResponseMessage(Result.SUCCESS.getValue());
        }
        return response;
    }
    
    @RequestMapping(value = "/org-job-board-account-user-list", method = RequestMethod.POST)
    @ResponseBody
    public ResponseList<OrgJobBoardAccountUserDTO> orgJobBoardAccountUserList(HttpServletRequest request, long orgJobBoardAccountId) {
        Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
        ResponseList<OrgJobBoardAccountUserDTO> response = new ResponseList<>();
        if (org == null) {
            response.setResponse(Result.SESSION_TIMEDOUT.name());
            response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
        } else {
            List<OrgJobBoardAccountUserDTO> list = jobBoardConfigurationService.getOrgJobBoardAccountUserList(orgJobBoardAccountId);
            response.setData(list);
            response.setResponse(Result.SUCCESS.name());
            response.setResponseMessage(Result.SUCCESS.getValue());
        }
        return response;
    }

    @RequestMapping(value = "/save-job-board-account", method = RequestMethod.POST)
    @ResponseBody
    public Response<Long> saveJobBoardAccount(HttpServletRequest request, OrgJobBoardAccountDTO orgJobBoardAccountDTO) {
        Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
        Response<Long> response = new Response<>();
        if (org == null) {
            response.setResponse(Result.SESSION_TIMEDOUT.name());
            response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
        } else {
            if (Validator.validate(orgJobBoardAccountDTO)) {
                orgJobBoardAccountDTO.setOrgId(org.getId());
                long i = jobBoardConfigurationService.saveOrgJobBoardAccount(orgJobBoardAccountDTO);
                if (i >= 1) {
                    response.setResponse(Result.SUCCESS.name());
                    response.setResponseMessage(Result.SUCCESS.getValue());
                    response.setObject(i);
                } else {
                    response.setResponse(Result.FAILED.name());
                    response.setResponseMessage(Result.FAILED.getValue());
                }
            } else {
                response.setResponse(Result.INVALID_DATA.name());
                response.setResponseMessage(Result.INVALID_DATA.getValue());
            }
        }
        return response;
    }
    
    @RequestMapping(value = "/save-job-board-account-access", method = RequestMethod.POST)
    @ResponseBody
    public Response saveJobBoardAccountAccess(HttpServletRequest request, long orgJobBoardAccountId, boolean allFlag, 
            @RequestParam(name = "userList[]", required = false) ArrayList<Long> userList, @RequestParam(name = "teamList[]", required = false) ArrayList<Long> teamList) {
        Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
        Response response = new Response<>();
        if (org == null) {
            response.setResponse(Result.SESSION_TIMEDOUT.name());
            response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
        } else {
//            if (Validator.validate(orgJobBoardAccountDTO)) {
//                OrgJobBoardAccountUserDTO.setOrgId(org.getId());
                long i = jobBoardConfigurationService.updateJobBoardAccountUserInfo(orgJobBoardAccountId, allFlag, userList, teamList);
                if (i >= 1) {
                    response.setResponse(Result.SUCCESS.name());
                    response.setResponseMessage(Result.SUCCESS.getValue());
                } else {
                    response.setResponse(Result.FAILED.name());
                    response.setResponseMessage(Result.FAILED.getValue());
                }
//            } else {
//                response.setResponse(Result.INVALID_DATA.name());
//                response.setResponseMessage(Result.INVALID_DATA.getValue());
//            }
        }
        return response;
    }
    
    @RequestMapping("/change-job-board-account-satatus")
    @ResponseBody
    public Response changeJobBoardAccountStatus(HttpServletRequest request, long orgJobBoardAccountId) {
        Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
        Response<Long> response = new Response<>();
        if (org == null) {
            response.setResponse(Result.SESSION_TIMEDOUT.name());
            response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
        } else {
            int i = jobBoardConfigurationService.changeJobBoardAccountStatus(orgJobBoardAccountId);
            if (i == 1) {
                response.setResponse(Result.SUCCESS.name());
                response.setResponseMessage(Result.SUCCESS.getValue());
            } else {
                response.setResponse(Result.FAILED.name());
                response.setResponseMessage(Result.FAILED.getValue());
            }
        }
        return response;
    }
}
