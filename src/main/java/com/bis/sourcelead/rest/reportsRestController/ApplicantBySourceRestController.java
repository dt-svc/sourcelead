package com.bis.sourcelead.rest.reportsRestController;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.reportsDTO.ApplicantsBySourceDTO;

@RestController
@RequestMapping("/app")
public class ApplicantBySourceRestController {
	
	@RequestMapping(value = "/getApplicantBySourceDetails", method = RequestMethod.GET)
	public List<ApplicantsBySourceDTO> getApplicantBySourceDetails(HttpServletRequest request) throws Exception {
		
		
		List<ApplicantsBySourceDTO> applicantBySourceList = new ArrayList<>();
		
		
		return applicantBySourceList;
	}

}
