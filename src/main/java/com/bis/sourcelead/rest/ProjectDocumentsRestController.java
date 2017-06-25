package com.bis.sourcelead.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Produces;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.model.ProjectDocumentBO;
import com.bis.sourcelead.service.ProjectDocumentsService;

@RestController
@RequestMapping("/projectDocuments")
public class ProjectDocumentsRestController {

	@Autowired
	private ProjectDocumentsService projectDocumentsService;

	@RequestMapping(value ="/getProjectDocumentsById", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE )
	public List<ProjectDocumentBO> retrieveDocuments(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		long projectId = ServletRequestUtils.getRequiredLongParameter(request, "projectId");
		List<ProjectDocumentBO> projectDocs = projectDocumentsService.findDocumentsByProjectId(projectId);
		//List<Map<String, Object>> docList = new ArrayList<>();
		//ProjectDocumentBO projectDocumentBO = projectDocs.get(0);
//		if(projectDocumentBO != null){
//			response.setContentType(projectDocumentBO.getFileType());
//			response.setHeader("Content-Disposition","attachment; filename=\"" + projectDocumentBO.getFileName() +"\"");
//			FileCopyUtils.copy(projectDocumentBO.getFileContents(), response.getOutputStream());
//		}
		//docList = projectDocs.stream().map(p -> transformProjDoc(p)).collect(Collectors.toList());
		return projectDocs;
	}

	private Map<String, Object> transformProjDoc(final ProjectDocumentBO projDocBO) {
		Map<String, Object> map = new HashMap<>();
		if (projDocBO != null) {
			map.put("docId", projDocBO.getId());
			map.put("docName", projDocBO.getFileName());
		}
		return map;
	}

}
