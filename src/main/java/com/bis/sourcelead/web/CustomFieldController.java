package com.bis.sourcelead.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.entity.SourceleadFormFields;
import com.bis.sourcelead.entity.SourceleadFormSections;
import com.bis.sourcelead.service.CustomFieldService;
import com.fasterxml.jackson.annotation.JsonFormat.Value;

@Controller
public class CustomFieldController {

	@Autowired
	private CustomFieldService customFieldService;
	
@RequestMapping (value="/customFields")
public ModelAndView CustomField() {
	ModelAndView model = new ModelAndView();
	List<SourceleadFormSections> sectionFeildName = customFieldService.getAllCustomFieldServices();
	model.addObject("sectionFeildName",sectionFeildName);
	model.setViewName("customSection");
	return model;
}
	
@RequestMapping (value="/getFormFieldsBysectionId")
public ModelAndView getFormFieldsBysectionId(@RequestParam (value="sectionFieldId") String sectionFieldId) {
	ModelAndView model = new ModelAndView();
	List<SourceleadFormFields> sectionFieldValue = customFieldService.getFormFieldsBysectionId(sectionFieldId);
	model.addObject("sectionFieldValue",sectionFieldValue);
	model.setViewName("customSectionFields");
	return model;
}

	
}
