package com.bis.sourcelead.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.LetterHeadDao;
import com.bis.sourcelead.dto.LetterHeadDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.AssetCategory;
import com.bis.sourcelead.entity.LetterHead;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.service.LetterHeadService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@RequestMapping("/app")
public class LetterHeadRestController {
	
	
@Value("${static.path}")
private String staticPath;
	
@Autowired
private LetterHeadService letterHeadService;

@Autowired
private LetterHeadDao letterHeadDao;
	
private static final Logger logger = LoggerFactory.getLogger(LetterHeadRestController.class);
	
@RequestMapping(value= "/saveLetterheadData", method = RequestMethod.POST)
public @ResponseBody String saveLetterheadData(LetterHeadDTO letterHeadDTO,HttpServletRequest request,HttpServletResponse response)
		throws Exception {
	
	OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	String status = "failure";
	LetterHead letterHead = letterHeadService.storeLetterHeadData(letterHeadDTO,orgLocation);
	if(letterHead != null) {
		status = "success";
	}
	return status;
}

@RequestMapping(value = "/validateletterHeadName",  method = RequestMethod.GET)
public @ResponseBody Boolean validateletterHeadName(@RequestParam(value="letterHeadName", required=false) String letterHeadName, 
		@RequestParam(value="letterHeadId", required=false) Long letterHeadId,HttpServletRequest request) throws Exception {
       OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
      
       boolean flag = false;
       
       LetterHead letterHead = null;
       if(letterHeadId != null){
    	   letterHead = letterHeadService.validateNameInEdit(letterHeadName,letterHeadId,orgLocation.getId());
		}
       
       if(letterHead == null){
		   flag = letterHeadService.validateLetterHeadName(letterHeadName,orgLocation);
       }
       return flag;
      
    }

@RequestMapping(value = "/getAllLetterHeadList", method = RequestMethod.GET)
public ResponseList<LetterHeadDTO> getAllLetterHeadList(LetterHeadDTO letterHeadDTO, HttpServletRequest request,HttpServletResponse response) throws Exception {
	
	
	OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	
	String[] tableColumns = {"letterHeadName", "letterHeadDescription", ""};
     int sortColumnIndex = 0; //by default sorting using updated date
	String sortColumnOrder = "desc"; //by default descending order
	if(letterHeadDTO.getOrder() != null && !letterHeadDTO.getOrder().isEmpty()) {
		sortColumnIndex = Integer.parseInt(letterHeadDTO.getOrder().get(0).get("column"));
		sortColumnOrder = letterHeadDTO.getOrder().get(0).get("dir");
	}
	letterHeadDTO.setSortColumn(tableColumns[sortColumnIndex]);
	letterHeadDTO.setSortOrder(sortColumnOrder);
	
	
	return letterHeadService.getAllLetterHeadList(letterHeadDTO,orgLocation.getId());
} 


@RequestMapping(value = "/deleteLetterhead", method = RequestMethod.POST)
public @ResponseBody  void deleteEmployementTemplate(@RequestParam("letterHeadUUID") String letterHeadUUID,HttpServletRequest request, HttpServletResponse response) throws Exception
{
	LetterHead letter = letterHeadService.getLetterheadByUUID(letterHeadUUID);
	letter.setStatus(Constants.MEMBER_IN_ACTIVE);
	letterHeadDao.updateLetterhead(letter);
}


@RequestMapping(value = "/previewLetterHead/{letterHeadUUID}", method = RequestMethod.GET)
public @ResponseBody  void previewLetterHead(@PathVariable("letterHeadUUID") String letterHeadUUID,HttpServletRequest request,HttpServletResponse response) throws Exception
{
	
	try {
		LetterHead letterHead = letterHeadService.getLetterheadByUUID(letterHeadUUID);
		letterHeadService.letterHeadData(letterHead, response);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	
	
}
}
