package com.bis.sourcelead.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.PaymentAccountDTO;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateOnBoarding;
import com.bis.sourcelead.entity.OnBoardingTemplate;
import com.bis.sourcelead.entity.OnBoardingTemplateDocuments;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequirementOffers;
import com.bis.sourcelead.entity.Tasks;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dto.AssociateBankDataDTO;
import com.bis.sourcelead.payroll.dto.AssociatePfEsiDetailsDTO;
import com.bis.sourcelead.payroll.dto.AssociateTaxDTO;
import com.bis.sourcelead.payroll.entity.AssociatePfEsiDetails;
import com.bis.sourcelead.payroll.service.AssociateBankDataService;
import com.bis.sourcelead.payroll.service.AssociatePfEsiService;
import com.bis.sourcelead.payroll.service.AssociateTaxService;
import com.bis.sourcelead.service.CandidateOffBoardingService;
import com.bis.sourcelead.service.CandidateOnBoardingService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.OnBoardingTemplateDocumentsService;
import com.bis.sourcelead.service.OnBoardingTemplateService;
import com.bis.sourcelead.service.OnboardTasksService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.RequirementOffersService;
import com.bis.sourcelead.service.TaskService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
@RequestMapping("/app")
public class CandidateOnBoardController {

	private static final Logger logger = LoggerFactory.getLogger(CandidateOnBoardController.class);
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	OnBoardingTemplateService onBoardingTemplateService;
	
	@Autowired
	CandidateOnBoardingService candidateOnBoardingService;
	
	@Autowired
	OnBoardingTemplateDocumentsService onBoardingTemplateDocumentsService;
	
	@Autowired
	CandidateService CandidateService;
	
	@Autowired
	OnboardTasksService onboardTasksService;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	RequirementOffersService requirementOffersService;
	
	@Autowired
	CandidateOffBoardingService candidateOffBoardingService;
	
	@Autowired
	AssociateBankDataService associateBankDataService;
	
	@Autowired
	AssociateTaxService associateTaxService;
	
	@Autowired
	AssociatePfEsiService associatePfEsiService;
	
	@Autowired
	 private OrgService orgService;
	
	@Autowired
	private CandidateService candidateService;
	
	@Value("${GoogleMaps_Location_API_KEY}")
	private String googleMapsLocationApiKey;
	
	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
	
	@RequestMapping(value = "/welcomeToOnboard")
 	public ModelAndView welcomeToOnboard() {
 			ModelAndView model = new ModelAndView();
 			model.addObject("sourceleadUrl", sourceleadUrl);
 			model.setViewName("onboard/welcome");
 			return model;
 	}
	
	@RequestMapping(value = "/onboardingCandidateStep2")
	public ModelAndView onboardingCandidateStep2(HttpServletRequest request) throws Exception{
		ModelAndView model = new ModelAndView();
		 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request,"user");
		CandidateOnBoarding candidateOnBoarding = candidateOnBoardingService.getCandidateOnBoardingByUserIdAndLocationId(user.getId(), orgLocations.getId());
		if(candidateOnBoarding != null){
			model.addObject("candidateOnBoarding",candidateOnBoarding);
		}
		model.addObject("googleMapsLocationApiKey", googleMapsLocationApiKey);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("onboard/welcomeStep2");
		return model;
	}
	
	@RequestMapping(value = "/onboardingCandidateBankingDetails")
	public ModelAndView onboardingCandidateBankingDetails(HttpServletRequest request) throws Exception{
		ModelAndView model = new ModelAndView();
		 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request,"user");
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 model.addObject("userId", user.getId());
		 Candidate candidate = CandidateService.getCandidateByConsultantUserId(user.getId(), orgLocations.getId());
		 model.addObject("candidateId", candidate.getId());
		 List<PaymentAccountDTO> paymentList  = orgService.getPaymentAccountListByCandidateId(candidate.getId(), false);
		 model.addObject("paymentList", paymentList);
		 model.addObject("paymentListSize", paymentList.size());
		 model.addObject("googleMapsLocationApiKey", googleMapsLocationApiKey);
		 if(StringUtils.isNotBlank(orgLocations.getCountry()) && orgLocations.getCountry().equalsIgnoreCase("India")) {
			 model.setViewName("onboard/onboardingCandidateBankingDetails");
		}else {
			 model.setViewName("onboard/onboardingCandidateUSABankingDetails");
		}
		 model.addObject("orgLocationId", orgLocations.getId());
		return model;
	}
	
	@RequestMapping(value = "/onboardingCandidateTaxDetails")
	public ModelAndView onboardingCandidateTaxDetails(HttpServletRequest request) throws Exception{
		ModelAndView model = new ModelAndView();
		 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request,"user");
		 if(StringUtils.isNotBlank(orgLocations.getCountry()) && orgLocations.getCountry().equalsIgnoreCase("India")) {
			 Candidate candidate = CandidateService.getCandidateByConsultantUserId(user.getId(), orgLocations.getId());
			 AssociatePfEsiDetails aped = associatePfEsiService.findAssociatePfEsiByCandidateId(candidate.getId(), orgLocations);
			 if(aped != null) {
				 if(aped.getEnroleDate() != null) {
					 model.addObject("enrolledDate",commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), aped.getEnroleDate()));
				 }
				 model.addObject("associatePfEsi",aped);
			 }
			 model.setViewName("onboard/onboardingCandidatePfAndEsiDetails");
		 }else {
			 Candidate candidate = candidateService.getCandidateByConsultantUserId(user.getId(), orgLocations.getId());
			 AssociateTaxDTO taxDeatails = associateTaxService.findAssociateTaxSummaryByCandidateId(candidate.getId(), orgLocations);
				if(taxDeatails != null) {
					model.addObject("taxDeatails",taxDeatails);
				}
				model.setViewName("onboard/onboardingCandidateTaxDetails");
		 }
		model.addObject("googleMapsLocationApiKey", googleMapsLocationApiKey);
		model.addObject("userId", user.getId());
		model.addObject("sourceleadUrl", sourceleadUrl);
		
		return model;
	}
	
	
	
	@RequestMapping(value = "/onboardingCandidateStep3")
	public ModelAndView onboardingCandidateStep3(HttpServletRequest request) throws Exception {
		
		 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request,"user");
		 
		ModelAndView model = new ModelAndView();
		
		CandidateOnBoarding candidateOnBoarding = candidateOnBoardingService.getCandidateOnBoardingByUserIdAndLocationId(user.getId(), orgLocations.getId());
		
		List<OnBoardingTemplateDocuments> contractDocsList = onBoardingTemplateDocumentsService.getOnBoardingTemplateDocumentsByCandidateOnBoardingId(candidateOnBoarding.getId(), Constants.ONBOARDING_CONTRACTS);
		model.addObject("contractDocsList", contractDocsList);
		model.addObject("contractDocsListSize", contractDocsList.size());
		
		List<OnBoardingTemplateDocuments> formsDocsList = onBoardingTemplateDocumentsService.getOnBoardingTemplateDocumentsByCandidateOnBoardingId(candidateOnBoarding.getId(), Constants.ONBOARDING_FORMS);
		model.addObject("formsDocsList", formsDocsList);
		model.addObject("formsDocsListSize", formsDocsList.size());
		
		List<OnBoardingTemplateDocuments> policiesDocsList = onBoardingTemplateDocumentsService.getOnBoardingTemplateDocumentsByCandidateOnBoardingId(candidateOnBoarding.getId(), Constants.ONBOARDING_POLICIES);
		model.addObject("policiesDocsList", policiesDocsList);
		model.addObject("policiesDocsListSize", policiesDocsList.size());
		
		
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("candidateOnboardId", candidateOnBoarding.getId());
		model.setViewName("onboard/welcomeStep3");
		return model;
	}
	
	
	
	
	@RequestMapping(value = "/onboardingCandidateStep4")
	public ModelAndView onboardingCandidateStep4(HttpServletRequest request) throws Exception {
		
		 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request,"user");
		 
		ModelAndView model = new ModelAndView();
		
		CandidateOnBoarding candidateOnBoarding = candidateOnBoardingService.getCandidateOnBoardingByUserIdAndLocationId(user.getId(), orgLocations.getId());
		//SimpleDateFormat simpleDateFormat = orgLocations.getLocFullFormDateFormatter();
		List<Tasks> tasksList = taskService.getTasskByAssignedUserIdAndOrgLocationIdAndOnboardingId(orgLocations.getId(),user.getId(),candidateOnBoarding.getId());
		List<Tasks> taskList = new ArrayList<Tasks>();
		if(null != tasksList && tasksList.size() > 0){
			for(Tasks tasks : tasksList){
				tasks.setTaskDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),tasks.getTaskDate()));
				taskList.add(tasks);
			}
			
		}
		
		model.addObject("tasksList", taskList);
		model.addObject("tasksListSize", taskList.size());
		
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("onboard/welcomeStep4");
		return model;
	}
	
	
	
	
	
	
	@RequestMapping(value = "/candidateOnBoardingDetails")
	public ModelAndView candidateOnBoardingDetails(@RequestParam(value="candidateUUId", required=false) String candidateUUId,
			@RequestParam(value="offerUUID", required=false) String offerUUID,HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView();
		
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.OnAndOffboard);
		
		Candidate candidate = CandidateService.getCandidateByUUID(candidateUUId);
		RequirementOffers offer = requirementOffersService.getRequirementOffersByUUID(offerUUID);
		
		if(candidate != null && offer != null){
			request.getSession().setAttribute("sessionData",sessionData);
			model.addObject("sourceleadUrl", sourceleadUrl);
			model.addObject("candidateId", candidate.getId());
			model.addObject("offerId", offer.getId());
			model.addObject("candidateEmail", candidate.getEmail());
			
			StringBuilder candidateName = new StringBuilder();
			String name = null;
			if(!isNullOrEmpty(candidate.getLastName())){
				name = candidateName.append(candidate.getFirstName()).append(" ").append(candidate.getLastName()).toString();
			}else{
				name = candidateName.append(candidate.getFirstName()).toString();
			}
			
			model.addObject("candidateName", name);
			model.addObject("jobTitle", offer.getSubmissionId().getRequirement().getJobTitle());
			
			model.setViewName("onboard/candidateOnBoardCreation");
		}else{
			model.setViewName("registration/persmissionDenied");
		}
		
		return model;
	}
	
	@PreAuthorize("hasAnyRole('ROLE_CREATE_ON_BOARD_TEMPLATE','ROLE_VIEW_EDIT_ON_BOARD_TEMPLATE')")
	@RequestMapping(value = "/onboardingTemplatesList")
 	public ModelAndView onboardingTemplatesList(HttpServletRequest request) {
		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		    ModelAndView model = new ModelAndView();
 			commonUtil.highLightMenu(Constants.OrgSettings, request);
 			
 			model.setViewName("onboard/onboardingTemplatesList");
 			model.addObject("sourceleadUrl", sourceleadUrl);
 			String msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "OnboardCreateMsg"); 
 			if(!isNullOrEmpty(msg)){ 
 			model.addObject("msg", msg); 
 			SourceLeadSessionManager.removeSessionAttribute(request, "OnboardCreateMsg"); 
 			}
 			return model;
 	}
	
	@PreAuthorize("hasRole('ROLE_CREATE_ON_BOARD_TEMPLATE')")
	@RequestMapping(value = "/addNewOnboardTemplate")
 	public ModelAndView addNewOnboardTemplate(HttpServletRequest request) {
		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		    ModelAndView model = new ModelAndView();
 			commonUtil.highLightMenu(Constants.OrgSettings, request);
 			
 			model.setViewName("onboard/addNewOnboardTemplate");
 			model.addObject("sourceleadUrl", sourceleadUrl);
 			return model;
 	}
	
	@PreAuthorize("hasRole('ROLE_VIEW_EDIT_ON_BOARD_TEMPLATE')")
	@RequestMapping(value = "/viewOnboardTemplateByTemplateUUID")
 	public ModelAndView viewOnboardTemplateByTemplateUUID(@RequestParam(value="templateUUID", required=false) String templateUUID,
 			HttpServletRequest request) throws Exception {
		   
		    ModelAndView model = new ModelAndView();
 			commonUtil.highLightMenu(Constants.OrgSettings, request);
 			
 			OnBoardingTemplate template = onBoardingTemplateService.getOnBoardingTemplateByTemplateUUID(templateUUID);
 			
 			model.setViewName("onboard/addNewOnboardTemplate");
 			model.addObject("sourceleadUrl", sourceleadUrl);
 			model.addObject("templateId", template.getId());
 			return model;
 	}
	
	
	@PreAuthorize("hasAnyRole('ROLE_CREATE_ON_BOARD_USER','ROLE_VIEW_EDIT_ON_BOARD_USER','ROLE_CREATE_OFF_BOARD_USER','ROLE_VIEW_EDIT_OFF_BOARD_USER')")
	@RequestMapping(value = "/candidateOnBoardingList")
	public ModelAndView candidateOnBoardingList(HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView();
		
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.OnAndOffboard);
		
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		
		Integer onboardCount = candidateOnBoardingService.getCandidateOnBoardingListCountByOrgLocationId(orgLocations.getId());
		
		Integer offboardCount = candidateOffBoardingService.getCandidateOffBoardingListCountByOrgLocationId(orgLocations.getId());
		
		request.getSession().setAttribute("sessionData",sessionData);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("onboardCount", onboardCount);
		model.addObject("offboardCount", offboardCount);
		model.setViewName("onboard/candidateOnBoardingList");
		return model;
	}
	
	
	@PreAuthorize("hasRole('ROLE_CREATE_ON_BOARD_USER')")
	@RequestMapping(value = "/candidateOnBoardCreation")
	public ModelAndView candidateOnBoardCreation(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.OnAndOffboard);
		
		request.getSession().setAttribute("sessionData",sessionData);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("onboard/candidateOnBoardCreation");
		return model;
	}
	
//	@PreAuthorize("hasRole('ROLE_CREATE_ON_BOARD_USER')")
	@RequestMapping(value = "/viewOnboardCandidateDetails")
 	public ModelAndView viewOnboardCandidateDetails(@RequestParam(value="onboardUUID", required=false) String onboardUUID,
 			HttpServletRequest request) throws Exception {
		   
		    ModelAndView model = new ModelAndView();
 			
		    SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
			if(sessionData == null)
			{
				sessionData = new SessionMaintainanceData();
			}
			sessionData.setNav(Constants.OnAndOffboard);
 			
 			CandidateOnBoarding onBoarding = candidateOnBoardingService.getCandidateOnBoardingByUUID(onboardUUID);
 			
 			model.setViewName("onboard/candidateOnBoardCreation");
 			model.addObject("sourceleadUrl", sourceleadUrl);
 			model.addObject("onBoardingId", onBoarding.getId());
 			model.addObject("onBoardingUUID", onBoarding.getOnboardCandidateUUID());
 			return model;
 	}
	
	
	@PreAuthorize("hasRole('ROLE_VIEW_EDIT_ON_BOARD_USER')")
    @RequestMapping(value = "/viewOnboardCandidateSummary")
     public ModelAndView viewOnboardCandidateSummary(@RequestParam(value="onboardUUID", required=false) String onboardUUID,
                     HttpServletRequest request) throws Exception {
               
                ModelAndView model = new ModelAndView();
                     
                SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
                    if(sessionData == null)
                    {
                            sessionData = new SessionMaintainanceData();
                    }
                    sessionData.setNav(Constants.OnAndOffboard);
                     
                     
                     model.setViewName("onboard/viewOnboardCandidateSummary");
                     model.addObject("sourceleadUrl", sourceleadUrl);
                     model.addObject("onBoardingUUID", onboardUUID);
                     return model;
     }

}
