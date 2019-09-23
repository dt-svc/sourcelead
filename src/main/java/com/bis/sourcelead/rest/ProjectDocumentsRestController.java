package com.bis.sourcelead.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.model.ProjectDocumentBO;
import com.bis.sourcelead.service.ProjectDocumentsService;

@RestController
@RequestMapping("/app/projectDocuments")
public class ProjectDocumentsRestController {

	@Autowired
	private ProjectDocumentsService projectDocumentsService;

	@RequestMapping(value ="/getProjectDocumentsById", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
	public List<ProjectDocumentBO> retrieveDocuments(@RequestParam("projectId") long projectId) throws Exception{
		List<ProjectDocumentBO> projectDocs = projectDocumentsService.findDocumentsByProjectId(projectId);
		return projectDocs;
	}

	

}
