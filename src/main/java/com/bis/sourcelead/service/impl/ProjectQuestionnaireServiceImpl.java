package com.bis.sourcelead.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.ProjectQuestionnaireDao;
import com.bis.sourcelead.dto.ProjectAdditionalDetailsDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectQuestionnaire;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.ProjectQuestionnaireService;

@Service
public class ProjectQuestionnaireServiceImpl implements ProjectQuestionnaireService{
	
	@Autowired
	ProjectQuestionnaireDao projectQuestionnaireDao;
	
    protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	 }

	@Override
	public void saveProjectQuestionnaireDetails(ProjectAdditionalDetailsDTO projectAdditionalDetailsDTO,Long projectId, User user,
			OrgLocations orgLocations) throws Exception {
		
		ProjectQuestionnaire questionnarie = new ProjectQuestionnaire();
		Projects projects = new Projects();
		projects.setId(projectId);
		questionnarie.setProjectId(projects);
		
		if(!isNullOrEmpty(projectAdditionalDetailsDTO.getProjectLocation())){
			questionnarie.setProjectLocation(projectAdditionalDetailsDTO.getProjectLocation());
		}else{
			questionnarie.setProjectLocation(null);
		}
		
		if(!isNullOrEmpty(projectAdditionalDetailsDTO.getProjectLocation())){
			questionnarie.setIndustry(projectAdditionalDetailsDTO.getIndustry());
		}else{
			questionnarie.setIndustry(null);
		}
		
		if(!isNullOrEmpty(projectAdditionalDetailsDTO.getScope())){
			questionnarie.setProjectScope(projectAdditionalDetailsDTO.getScope());
		}else{
			questionnarie.setProjectScope(null);
		}
		
	   if(projectAdditionalDetailsDTO.getCategory() != null && projectAdditionalDetailsDTO.getCategory().length > 0){
	    	StringBuilder categoryBuilder = new StringBuilder();
	    	for(int i=0; i<projectAdditionalDetailsDTO.getCategory().length; i++){
	    		categoryBuilder.append(projectAdditionalDetailsDTO.getCategory()[i]).append(",");
	    	}
	    	String categoryStr = categoryBuilder.toString();
	    	questionnarie.setEmploymentCategory(categoryBuilder.substring(0, categoryStr.length()-1)); 
	    }else{
	    	questionnarie.setEmploymentCategory(null);
	    }
	   
	   if(projectAdditionalDetailsDTO.getTimeSize() != null){
		   questionnarie.setTeamSize(projectAdditionalDetailsDTO.getTimeSize());
	   }
	   
	   
	   if(projectAdditionalDetailsDTO.getFrontEnd() != null && projectAdditionalDetailsDTO.getFrontEnd().length > 0){
	    	StringBuilder frontEndBuilder = new StringBuilder();
	    	for(int i=0; i<projectAdditionalDetailsDTO.getFrontEnd().length; i++){
	    		frontEndBuilder.append(projectAdditionalDetailsDTO.getFrontEnd()[i]).append(",");
	    	}
	    	String frontEndStr = frontEndBuilder.toString();
	    	questionnarie.setFrontEnd(frontEndBuilder.substring(0, frontEndStr.length()-1)); 
	    }else{
	    	questionnarie.setFrontEnd(null);
	    }
	   
	   
	   if(projectAdditionalDetailsDTO.getBackEnd() != null && projectAdditionalDetailsDTO.getBackEnd().length > 0){
	    	StringBuilder backEndBuilder = new StringBuilder();
	    	for(int i=0; i<projectAdditionalDetailsDTO.getBackEnd().length; i++){
	    		backEndBuilder.append(projectAdditionalDetailsDTO.getBackEnd()[i]).append(",");
	    	}
	    	String backEndStr = backEndBuilder.toString();
	    	questionnarie.setBackEnd(backEndBuilder.substring(0, backEndStr.length()-1)); 
	    }else{
	    	questionnarie.setBackEnd(null);
	    }
	   
	   if(projectAdditionalDetailsDTO.getDatabase() != null && projectAdditionalDetailsDTO.getDatabase().length > 0){
	    	StringBuilder dataBaseBuilder = new StringBuilder();
	    	for(int i=0; i<projectAdditionalDetailsDTO.getDatabase().length; i++){
	    		dataBaseBuilder.append(projectAdditionalDetailsDTO.getDatabase()[i]).append(",");
	    	}
	    	String dataBaseStr = dataBaseBuilder.toString();
	    	questionnarie.setDataBase(dataBaseBuilder.substring(0, dataBaseStr.length()-1)); 
	    }else{
	    	questionnarie.setDataBase(null);
	    }
	   
	   

	   if(projectAdditionalDetailsDTO.getOtherTechnologies() != null && projectAdditionalDetailsDTO.getOtherTechnologies().length > 0){
	    	StringBuilder otherTechBuilder = new StringBuilder();
	    	for(int i=0; i<projectAdditionalDetailsDTO.getOtherTechnologies().length; i++){
	    		otherTechBuilder.append(projectAdditionalDetailsDTO.getOtherTechnologies()[i]).append(",");
	    	}
	    	String otherTechStr = otherTechBuilder.toString();
	    	questionnarie.setOtherTechnologies(otherTechBuilder.substring(0, otherTechStr.length()-1)); 
	    }else{
	    	questionnarie.setOtherTechnologies(null);
	    }
	   
	   
	   if(projectAdditionalDetailsDTO.getTestManagement() != null && projectAdditionalDetailsDTO.getTestManagement().length > 0){
	    	StringBuilder testManagementBuilder = new StringBuilder();
	    	for(int i=0; i<projectAdditionalDetailsDTO.getTestManagement().length; i++){
	    		testManagementBuilder.append(projectAdditionalDetailsDTO.getTestManagement()[i]).append(",");
	    	}
	    	String testManagementStr = testManagementBuilder.toString();
	    	questionnarie.setTestManagement(testManagementBuilder.substring(0, testManagementStr.length()-1)); 
	    }else{
	    	questionnarie.setTestManagement(null);
	    }
		
	   if(!isNullOrEmpty(projectAdditionalDetailsDTO.getExecutionModel())){
		   questionnarie.setExecutionModel(projectAdditionalDetailsDTO.getExecutionModel());
	   }else{
		   questionnarie.setExecutionModel(null);
	   }
	   
	   saveProjectQuestionnaire(questionnarie);
	
	}

	@Override
	public ProjectQuestionnaire saveProjectQuestionnaire(ProjectQuestionnaire projectQuestionnaire) throws Exception {
		return projectQuestionnaireDao.saveProjectQuestionnaire(projectQuestionnaire);
	}

}
