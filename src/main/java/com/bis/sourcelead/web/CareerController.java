package com.bis.sourcelead.web;

import com.bis.sourcelead.dto.RequirementDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.elk.index.RequirementIndex;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.RequirementService;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@PropertySource("${propertyLocation:app.properties}")
public class CareerController {

    @Autowired
    private OrgService orgService;

    @Autowired
    private OrgLocationService orgLocationService;

    @Autowired
    private LookUpDataService lookUpDataService;

    @Autowired
    private RequirementService requirementService;

    @Value("${sourceleadUrl}")
    private String sourceleadUrl;

    @RequestMapping("/careers-page.html")
    public String cph(HttpServletRequest request, String key) {
    	request.getSession().setAttribute("sourceleadUrl", sourceleadUrl.replace("/app", ""));
    	request.setAttribute("key", key);
        return "static-web/careers-test";
    }

    @RequestMapping(value = "/ws/job-list-page", method = RequestMethod.GET)
    public String jobListPage(HttpServletRequest request, String key, ModelMap map) {
        if (key == null || key.isEmpty()) {
            return "forward:/ws/careers-error?msg=Key is required";
        }
        try {
            Org org = orgService.getOrgByCareerPageKey(key);
            if (org == null) {
                return "forward:/ws/careers-error?msg=Invalid Key";
            } else if (!org.getCareerPageKeyActive()) {
                return "forward:/ws/careers-error?msg=Key is not active";
            } else {
                request.getSession().setAttribute("sourceleadUrl", sourceleadUrl.replace("/app", ""));
                map.addAttribute("locations", orgLocationService.getAllOrgLocationsByOrgId(org.getId()));
                return "careers/jobList";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "forward:/ws/careers-error?msg=Operation failed, please try again";
    }

    @ResponseBody
    @RequestMapping(value = "/ws/jobs", method = RequestMethod.POST)
    public ResponseList<RequirementDTO> requirementList(HttpServletRequest request, String key,
            String search, @RequestParam(defaultValue = "0") Long location,
            String employmentType,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        ResponseList<RequirementDTO> response = new ResponseList<>();
        if (key == null || key.isEmpty()) {
            response.setError("Key is required");
        } else {
            try {
                Org org = orgService.getOrgByCareerPageKey(key);
                if (org == null) {
                    response.setError("Invalid Key");
                } else if (!org.getCareerPageKeyActive()) {
                    response.setError("Key is not active");
                } else {
                    response = requirementService.getRequirementList(org.getId(), search, location, employmentType, pageNo, pageSize);
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.setError("Operation failed, please try again");
            }
        }
        return response;
    }

    @RequestMapping(value = "/ws/careers-error", method = RequestMethod.GET)
    public String jobListPageError(HttpServletRequest request, ModelMap map, String msg) {
        map.addAttribute("msg", msg);
        return "careers/error";
    }

    @RequestMapping("/ws/job-list")
    @ResponseBody
    public ResponseList<RequirementIndex> jobList(int orgId, List<Integer> locationIds, String search, String key) {
        ResponseList<RequirementIndex> response = new ResponseList<>();
        List<RequirementIndex> requirementList = new ArrayList<RequirementIndex>();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

        return response;
    }
}
