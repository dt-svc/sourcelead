package com.bis.sourcelead.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.dao.SourceleadFormSectionsDao;
import com.bis.sourcelead.entity.SourceleadActions;

@Controller
@RequestMapping (value="/app")
public class CustomFieldController {

@Autowired
private SourceleadFormSectionsDao SourceleadFormSectionsDao;
	
@Value("${sourceleadUrl}")
private String sourceleadUrl;

@RequestMapping (value="/customFields")
public ModelAndView CustomField() throws Exception {
	ModelAndView model = new ModelAndView();
	List<SourceleadActions> entityList = SourceleadFormSectionsDao.getAllSourceleadActions();
	model.addObject("Sourcelead_Entities",entityList);
	model.addObject("sourceleadUrl",sourceleadUrl);
	model.setViewName("customForms/customForm");
	return model;
}
	
}
