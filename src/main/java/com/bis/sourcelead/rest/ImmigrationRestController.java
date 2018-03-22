package com.bis.sourcelead.rest;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.CandidateEmployerDTO;
import com.bis.sourcelead.dto.CandidateResumeDTO;
import com.bis.sourcelead.dto.ImmigrationDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateDocDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateFormDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateListDTO;
import com.bis.sourcelead.dto.ResponseList;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.Immigration;
import com.bis.sourcelead.entity.ImmigrationTemplate;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.immigration.service.ImmigrationCommonService;
import com.bis.sourcelead.immigration.service.ImmigrationTemplateService;
import com.bis.sourcelead.service.CandidateResumesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@RequestMapping("/app/immigration")
public class ImmigrationRestController {

    private static final Logger logger = LoggerFactory.getLogger(ImmigrationRestController.class);

    @Value("${sourceleadUrl}")
    private String sourceleadUrl;

    @Value("${sourceleadLoginUrl}")
    private String sourceleadLoginUrl;

    @Autowired
    private UserService userService;

    @Value("${static.path}")
    private String staticPath;

    @Autowired
    private OrgLocationService orgLocationService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private LookUpDataService lookUpDataService;

    @Autowired
    private ImmigrationTemplateService immigrationTemplateService;

    @Autowired
    private ImmigrationCommonService immigrationCommonService;

    @Autowired
    private CandidateService candidateService;
    
    @Autowired
	CandidateResumesService candidateResumesService;

	@Autowired
	private CommonUtil commonUtil;

    @Autowired
    private RequirementService requirementService;

    // ====================immigration view startviewImmigration if you don't
    // required comment below code start here==============================
    @RequestMapping(value = {"/viewImmigrationList"})
    public ModelAndView loadImmigrationList(HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        model.addObject("sourceleadUrl", sourceleadUrl);
        User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
        model.setViewName("/immigration/immigrationList");
        return model;
    }

    @RequestMapping(value = {"/loadNewPetition"})
    public ModelAndView loadNewPetition(HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        model.addObject("sourceleadUrl", sourceleadUrl);
        User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
        model.setViewName("/immigration/newPetition");
        return model;
    }

    @RequestMapping(value = {"/loadNewPetitionPage"})
    public ModelAndView loagNewPetitionPageView(HttpServletRequest request,
            @RequestParam(value = "forward", required = false) String forward) {
        ModelAndView model = new ModelAndView();
        model.addObject("sourceleadUrl", sourceleadUrl);
        if (forward.equalsIgnoreCase("petitionDetails")) {
            model.setViewName("/immigration/petitionDetails");
            OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
                    "orgLocations");
            List<ImmigrationTemplate> templateList = immigrationCommonService.getImmigrationTemplateList(orgLocations);
            System.out.println("templateList size in controller is-->" + templateList.size());

            model.addObject("templateList", templateList);
        } else if (forward.equalsIgnoreCase("two")) {
            model.setViewName("/immigration/newPetitionPage2");
        } else if (forward.equalsIgnoreCase("three")) {
            model.setViewName("/immigration/newPetitionPage3");
        } else if (forward.equalsIgnoreCase("four")) {
            model.setViewName("/immigration/newPetitionPage4");
        } else if (forward.equalsIgnoreCase("five")) {
            model.setViewName("/immigration/newPetitionPage5");
        } else if (forward.equalsIgnoreCase("six")) {
            model.setViewName("/immigration/newPetitionPage6");
        } else if (forward.equalsIgnoreCase("seven")) {
            model.setViewName("/immigration/newPetitionPage7");
        }
        return model;
    }

    @RequestMapping(value = {"getAllActiveCandidatesByLocation"}, method = RequestMethod.GET)
    public @ResponseBody
    List<Candidate> getAllActiveCandidatesByLocation(HttpServletRequest request) {
        List<Candidate> candidateList = null;
        try {
            OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
                    "orgLocations");
            candidateList = candidateService.getAllActiveCandidatesByLocation(orgLocations.getId());
            System.out.println(
                    "candidateList size in getAllActiveCandidatesByLocation controller is-->" + candidateList.size());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // Gson gson=new Gson();
        // gsondata=gson.toJson(candidateList);//.replace("\\", "\\\\");
        return candidateList;
    }

    @RequestMapping(value = {"/getPetitionTemplateEmployers"})
    public List<CandidateEmployerDTO> getPetitionTemplateEmployers(HttpServletRequest request) {
        Candidate candidate = (Candidate) SourceLeadSessionManager.getSessionAttribute(request, "candidate");
        List<CandidateEmployerDTO> caEmployerDTOs = immigrationTemplateService
                .getPetitionTemplateEmployers(candidate.getId());
        return caEmployerDTOs;
    }

    @RequestMapping(value = {"/getEmploymentTypeList"})
    public List<LookUpData> getEmploymentTypeList(HttpServletRequest request) {
        List<LookUpData> caEmployerDTOs = immigrationTemplateService
                .getLookupDataByType(Constants.EMPLOYMENT_TYPE_PETITION);
        return caEmployerDTOs;
    }

    @RequestMapping(value = {"/viewImmigration"})
    public ModelAndView viewImmigration(HttpServletRequest request
    		, @RequestParam(value = "templateId", required = false) String templateId
    		, @RequestParam(value = "candidateId", required = false) String candidateId
    		 , @RequestParam(value = "immigrationId", required = false) String immigrationId) {
        ModelAndView model = new ModelAndView();
        model.addObject("immigrationId", immigrationId);
        model.addObject("candidateId", candidateId);
        model.addObject("templateId", templateId);
        
        ImmigrationDTO immigrationDTO = immigrationCommonService.getImmigrationViewList(immigrationId);
        model.addObject("immigration", immigrationDTO);
        model.addObject("sourceleadUrl", sourceleadUrl);
        model.setViewName("/immigration/immigrationView");
        return model;
    }

    // ============= immigration view End. if you don't required comment above code
    // end here============================
    @RequestMapping(value = {"/gotoTemplatePreviousPage"})
    public ModelAndView loagNewPetitionPageOne(HttpServletRequest request,
            @RequestParam(value = "status", required = false) String status) {
        ModelAndView model = new ModelAndView();
        model.addObject("sourceleadUrl", sourceleadUrl);
        try {
            if (status.equalsIgnoreCase("templateDetails")) {
                ImmigrationTemplateDTO dto = (ImmigrationTemplateDTO) request.getSession().getAttribute("templateDTO");
                model.addObject("templateDTO", dto);
                model.setViewName("/immigration/petitionTemplateDetails");
            }
            if (status.equalsIgnoreCase("tempDetails")) {
                model.setViewName("/immigration/petitionTemplateDetails");
            } else if (status.equalsIgnoreCase("docDetails")) {
                List<LookUpData> educationsList = null;
                educationsList = lookUpDataService.getLookUpDataByCategory(Constants.DOCUMENT_TYPE); // for populating
                // dropdown
                // values in UI.
                /*
                 * List<ImmigrationTemplateDTO> templateDTO =
                 * (List<ImmigrationTemplateDTO>)request.getSession().getAttribute("templateDTO"
                 * ); model.addObject("templateDTO", );
                 */
                model.addObject("educationsList", educationsList);
                model.setViewName("/immigration/petitionTemplateDocDetails");
            } else if (status.equalsIgnoreCase("formDetails")) {
                System.out.println(" inside formDetails controller --->");
                model.setViewName("/immigration/petitionTemplateFormDetails");
            } else if (status.equalsIgnoreCase("taskList")) {
                model.setViewName("/immigration/immigrationTemplateTaskList");
            }
        } catch (Exception e) {
            logger.error("Exception occured in controller while navigating-->");
            e.printStackTrace();
        }
        return model;
    }

    @RequestMapping(value = {"/addNewPetitionTemplate"})
    public ModelAndView loadAddPetitionTemplate(HttpServletRequest request) throws ServletRequestBindingException {
        ModelAndView model = new ModelAndView();
        model.addObject("sourceleadUrl", sourceleadUrl);
        User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
        request.getSession().removeAttribute("templateDTO");
        request.getSession().removeAttribute("templateDocList");
        request.getSession().removeAttribute("templateFormsList");
        model.setViewName("/immigration/addPetitionTemplate");
        Long templateId = ServletRequestUtils.getLongParameter(request, "templateId");// sending parameter edit mode.
        if (templateId != null) { // below code is used for edit template name, docs, forms when opened in edit
            // mode.
            try {
                System.out.println(" Edit mode templateId templateId -->" + templateId);
                ImmigrationTemplateDTO templateDTO = immigrationTemplateService.getPetitionTemplateDetails(templateId);
                List<ImmigrationTemplateDocDTO> docsList = immigrationTemplateService
                        .getImmigrationTemplateDocsListById(templateId);
                logger.debug(
                        " Edit mode templateId templateId -->" + templateId + ", docsList size is " + docsList.size());
                List<ImmigrationTemplateFormDTO> formsList = immigrationTemplateService
                        .getImmigrationTemplateformsListById(templateId);
                logger.debug(
                        " Edit mode templateId templateId -->" + templateId + ", formsList size i " + formsList.size());
                // templateDTO.setImmigrationDocList(docsList);
                request.getSession().setAttribute("templateDTO", templateDTO);
                request.getSession().setAttribute("templateDocList", docsList);
                request.getSession().setAttribute("templateFormsList", formsList);
            } catch (Exception e) {
                logger.error(" Exception caught in edit mode in controller -->" + templateId);
                e.printStackTrace();
            }
        }
        return model;
    }

    @RequestMapping(value = {"/viewImmigrationTemplateList"})
    public ModelAndView loadViewPetitionTemplate(HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        model.addObject("sourceleadUrl", sourceleadUrl);
        User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
        model.setViewName("/immigration/immigrationTemplateList");
        /*
         * try { // List<ImmigrationTemplateView> templateList=
         * immigrationTemplateService.getAllTemplateDetails();
         * System.out.println("template list size is "+templateList.size());
         * model.addObject("templateList", templateList); } catch (Exception e) {
         * logger.error("Exception occured while getting template list-->"+e.getMessage(
         * )); e.printStackTrace(); }
         */
        return model;
    }

    @RequestMapping(value = "/getImmigrationTemplateList")
    public @ResponseBody
    ResponseList<ImmigrationTemplateListDTO> getAlltemplateList(
            ImmigrationTemplateListDTO templateListDTO,
            @RequestParam(value = "searchValue", required = false) String searchValue, HttpServletRequest request) {
        ResponseList<ImmigrationTemplateListDTO> templateListDTOList = null;
        try {
            String[] tableColumns = {"name", "description", "docsCount", "formsCount", "tasksCount", "status", "id"};// should be
            // entity names
            int sortColumnIndex = 0; // by default sorting using updated date
            String sortColumnOrder = "desc"; // by default ascending order
            if (templateListDTO.getOrder() != null && !templateListDTO.getOrder().isEmpty()) {
                sortColumnIndex = Integer.parseInt(templateListDTO.getOrder().get(0).get("column"));
                sortColumnOrder = templateListDTO.getOrder().get(0).get("dir"); // sorting order 'dir' means direction
                // of the sorting.
            }
            templateListDTO.setSortColumn(tableColumns[sortColumnIndex]);
            templateListDTO.setSortOrder(sortColumnOrder);
            System.out.println("searchValue in controller ---> " + searchValue);

            OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
                    "orgLocations");

            templateListDTOList = immigrationTemplateService.getAllTemplateDetails(templateListDTO, searchValue,
                    orgLocations.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (templateListDTOList.getRecordsTotal() > 0) {
            System.out.println("After getAlltemplateList in controller ---> "
                    + templateListDTOList.getData().get(0).getDocsCount());
        }
        return templateListDTOList;
    }

    @RequestMapping(value = "/deleteImmigrationTemplateDetatils")
    public String deleteTemplateDetailsById(@RequestParam(value = "deleteId", required = true) Long deleteId,
            HttpServletRequest request) {
        try {
            immigrationTemplateService.deleteAllPetitionTemplateDetailsById(deleteId);// only soft delete operation in
            // Database.
            // System.out.println("inside delete controller method calling ----->>>>>>>:
            // "+deleteId);
            logger.debug("inside delete controller method calling ----->>>>>>>: " + deleteId);
        } catch (Exception e) {
            logger.error(" Exception while deleting template deitails in controller -->" + deleteId);
            e.printStackTrace();
        }
        return "success";
    }

    @RequestMapping(value = {"/addPetitionTemplate"})
    public ModelAndView addPetitionTemplatePage(HttpServletRequest request,
            @RequestParam(value = "forwardTo", required = false) String forwardTo) {
        ModelAndView model = new ModelAndView();
        try {
            model.addObject("sourceleadUrl", sourceleadUrl);
            if (forwardTo.equalsIgnoreCase("tempDetails")) {
                model.setViewName("/immigration/petitionTemplateDetails");
            } else if (forwardTo.equalsIgnoreCase("docDetails")) {
                List<LookUpData> educationsList = null;
                educationsList = lookUpDataService.getLookUpDataByCategory(Constants.DOCUMENT_TYPE); // for populating
                // dropdown
                // values in UI.
                model.addObject("educationsList", educationsList);
                model.setViewName("/immigration/petitionTemplateDocDetails");
            } else if (forwardTo.equalsIgnoreCase("formDetails")) {
                System.out.println(" inside formDetails controller --->");
                model.setViewName("/immigration/petitionTemplateFormDetails");
            } else if (forwardTo.equalsIgnoreCase("taskList")) {
                model.setViewName("/immigration/immigrationTemplateTaskList");
            }

        } catch (Exception e) {
            logger.error("Exception occured in controller while navigating-->");
            e.printStackTrace();
        }

        return model;
    }

    @RequestMapping(value = {
        "/savePetitionTemplateDetails"}, method = RequestMethod.POST, produces = "application/json")
    public String savePetitionTemplateDetails(ImmigrationTemplateDTO templateDTO, HttpServletRequest request,
            @RequestParam(value = "templateId", required = false) String templateId) {
        ModelAndView model = new ModelAndView();
        model.addObject("sourceleadUrl", sourceleadUrl);
        Long immigrationId = null;
        try {
            User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
            OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
                    "orgLocations");

            immigrationId = immigrationTemplateService.savePetitionTemplateDetails(templateDTO, user, orgLocations,
                    templateId);
        } catch (Exception e) {
            logger.error("Exception occured while saving Petition Template Deatils" + e.getMessage());
            e.printStackTrace();
        }
        request.getSession().setAttribute("templateDTO", templateDTO);
        return "{status:'success', id:" + immigrationId + "}";
    }

    @RequestMapping(value = {"/savePetitionTemplateDocDetails"}, method = RequestMethod.POST)
    public String savePetitionTemplateDocDetails(ImmigrationTemplateDTO templateDTO, HttpServletRequest request,
            @RequestParam(value = "templateId", required = false) String templateId) {
        ModelAndView model = new ModelAndView();
        model.addObject("sourceleadUrl", sourceleadUrl);
        List<ImmigrationTemplateDocDTO> templateDocList = null;
        try {
            User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
            templateDocList = immigrationTemplateService.savePetitionTemplateDocDetails(templateDTO, user);
        } catch (Exception e) {
            logger.error("Exception occured while saving Petition Template Doc Details" + e.getMessage());
            e.printStackTrace();
        }
        request.getSession().setAttribute("templateDocList", templateDocList);
        return "success";
    }

    @RequestMapping(value = {"/savePetitionTemplateFormDetails"}, method = RequestMethod.POST)
    public String savePetitionTemplateFormDetails(ImmigrationTemplateDTO templateDTO, HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        List<ImmigrationTemplateFormDTO> templateFormsList = null;
        model.addObject("sourceleadUrl", sourceleadUrl);
        templateDTO.setStaticPath(staticPath);
        User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
        templateFormsList = immigrationTemplateService.savePetitionTemplateFormDetails(templateDTO, user);
        request.getSession().setAttribute("templateFormsList", templateFormsList);
        return "success";
    }

    @RequestMapping(value = {"/savePetitionTemplateTaskDetails"}, method = RequestMethod.POST)
    public String savePetitionTemplateTaskDetails(ImmigrationTemplateDTO templateDTO, HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        model.addObject("sourceleadUrl", sourceleadUrl);
        try {
            User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
            immigrationTemplateService.savePetitionTemplateTaskDetails(templateDTO, user);
            System.out.println("Template Task Details saved succesfully --> ");
        } catch (Exception e) {
            logger.error("Exception occured while saving Template Task Details" + e.getMessage());
            e.printStackTrace();
        }
        return "success";
    }

    @RequestMapping(value = {"/getTemplateTaskList"}, method = RequestMethod.POST)
    public @ResponseBody
    ResponseList<ImmigrationTemplateDTO> getTemplateTaskList(ImmigrationTemplateDTO templateDTO,
            HttpServletRequest request, @RequestParam(value = "templateId", required = false) String templateId,
            @RequestParam(value = "searchValue", required = false) String searchValue) throws Exception {
        ResponseList<ImmigrationTemplateDTO> immiTemplateList = new ResponseList<>();
        String[] tableColumns = {"taskName", "assignedTo", "priority", "taskRepeat"};
        int sortColumnIndex = 3; // by default sorting using updated date
        String sortColumnOrder = "desc"; // by default descending order
        templateDTO.setTemplateId(Long.parseLong(templateId));
        if (templateDTO.getOrder() != null && !templateDTO.getOrder().isEmpty()) {
            sortColumnIndex = Integer.parseInt(templateDTO.getOrder().get(0).get("column"));
            sortColumnOrder = templateDTO.getOrder().get(0).get("dir");
        }
        templateDTO.setSortColumn(tableColumns[sortColumnIndex]);
        templateDTO.setSortOrder(sortColumnOrder);
        immiTemplateList = immigrationTemplateService.getTemplateTaskList(templateDTO, searchValue);
        return immiTemplateList;
    }

    @RequestMapping(value = {"/editImmigrationTaskList"})
    public ImmigrationTemplateDTO editImmigrationTaskList(HttpServletRequest request,
            @RequestParam(value = "id", required = false) String id) throws NumberFormatException, Exception {
        ImmigrationTemplateDTO templateDTO = immigrationTemplateService.editImmigrationTaskList(id);
        return templateDTO;
    }

    @RequestMapping(value = {"/deleteImmigrationTaskList"})
    public void deleteImmigrationTaskList(HttpServletRequest request,
            @RequestParam(value = "id", required = false) String id) {
        immigrationTemplateService.deleteImmigrationTaskList(id);
    }

    @RequestMapping(value = {"/savePetitionTemplateDetailsValues"})
    public String savePetitionTemplateDetailsValues(HttpServletRequest request,
            @RequestParam(value = "templateId", required = false) String templateId) {
        int value = immigrationTemplateService.savePetitionTemplateDetailsValues(templateId);
        String message = "failed";
        if (value != 0) {
            message = "success";
            request.getSession().setAttribute("success", message);
        }
        return message;
    }

    @RequestMapping(value = {"/getPetitionTypeDropDown"})
    public List<LookUpData> getPetitionTypeDropDown(HttpServletRequest request) {
        List<LookUpData> caEmployerDTOs = immigrationTemplateService.getLookupDataByType(Constants.PETITION_TYPE);
        return caEmployerDTOs;
    }

    @RequestMapping(value = {"/getUscisTypeDropDown"})
    public List<LookUpData> getUscisTypeDropDown(HttpServletRequest request) {
        List<LookUpData> caEmployerDTOs = immigrationTemplateService.getLookupDataByType(Constants.USCIS_STATUS);
        return caEmployerDTOs;
    }

    @RequestMapping(value = {"/getEmploymentLevelDropDown"})
    public List<LookUpData> getEmploymentLevelDropDown(HttpServletRequest request) {
        List<LookUpData> caEmployerDTOs = immigrationTemplateService.getLookupDataByType(Constants.EMPLOYMENT_LEVEL);
        return caEmployerDTOs;
    }

    @RequestMapping(value = {"/getJobTitleDropDown"})
    public List<Requirement> getJobTitleDropDown(HttpServletRequest request) throws Exception {
        OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
                "orgLocations");
        List<Requirement> jobList = requirementService.getAllRequirementsByOrglocationId(orgLocations.getId());
        return jobList;
    }

    @RequestMapping(value = {"/savePetitionDetailsPage"}, method = RequestMethod.POST)
    public String  savePetitionDetailsPage(ImmigrationDTO immigrationDTO, HttpServletRequest request) {
        User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
        OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
        immigrationDTO.setStaticPath(staticPath);
		System.out.println("Candidate ID ----> :  "+immigrationDTO.getCandidateId());
		Immigration immigration = null;
        try {
        	immigration = immigrationCommonService.savePetitionDetails(immigrationDTO, user, orgLocations);
        } catch (Exception e) {
            logger.error("Exception caugth in savePetitionDetailsPage controller" + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("immigration.getImmigrationTemplateId()"+immigration.getImmigrationTemplateId().getId());
        return "{result:'success',id:'"+immigration.getId()+"',candidateId:'"+immigration.getCandidate().getId()+"',immTemplateId:'"+immigration.getImmigrationTemplateId().getId()+"'}";
    }

    @RequestMapping(value = "/validateTemplateName", method = RequestMethod.POST)
    @ResponseBody
    public boolean validateTemplateName(HttpServletRequest request,
            @RequestParam(value = "templateName") String templateName,
            @RequestParam(value = "templateId", defaultValue = "0") Long templateId) {
        OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
        return immigrationTemplateService.validateTemplateNameExistance(templateName, templateId);
    }
    
    
    @RequestMapping(value = {"/getCandidatesResumeDetailsById"})
    public @ResponseBody CandidateResumeDTO getCandidatesResumeDetailsById(@RequestParam(value = "candidateId", required = false) Long candidateId, HttpServletRequest request) throws Exception {
        OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
                "orgLocations");
		Integer status = Constants.CANDIDATE_ACTIVE;
		CandidateResumeDTO candidateResumeDTO  =new CandidateResumeDTO();
		if (candidateId != null) {
			List<CandidateResumes> candidateResumesList = candidateResumesService.getCandidateResumesListByCandidateIdAndStatus(candidateId, status);
			if(candidateResumesList != null && candidateResumesList.size()>0) {
				CandidateResumes candidateResume = candidateResumesList.get(0);
	             candidateResumeDTO = new CandidateResumeDTO();
				candidateResumeDTO.setId(candidateResume.getId());
				candidateResumeDTO.setTitle(candidateResume.getTitle());
				candidateResumeDTO.setDescription(candidateResume.getDescription());
				candidateResumeDTO.setCreatedBy(candidateResume.getCreatedBy());
				candidateResumeDTO.setFileContentType(candidateResume.getFileContentType());
				candidateResumeDTO.setFileName(candidateResume.getFileName());
				candidateResumeDTO.setFileURL(candidateResume.getFileURL());
			}
		}
		return candidateResumeDTO;
    }
}
