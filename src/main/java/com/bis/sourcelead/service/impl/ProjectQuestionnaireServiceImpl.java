package com.bis.sourcelead.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ProjectQuestionnaireDao;
import com.bis.sourcelead.dao.ProjectsDao;
import com.bis.sourcelead.dto.ProjectAdditionalDetailsDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectQuestionnaire;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.service.ProjectQuestionnaireService;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class ProjectQuestionnaireServiceImpl implements ProjectQuestionnaireService{
	
	@Autowired
	ProjectQuestionnaireDao projectQuestionnaireDao;
	
	@Autowired
	ProjectsDao projectsDao;
	
    protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	 }

	@Override
	public void saveProjectQuestionnaireDetails(ProjectAdditionalDetailsDTO projectAdditionalDetailsDTO,Long projectId, User user,
			OrgLocations orgLocations) throws Exception {
		
		ProjectQuestionnaire questionnarie = null;
		questionnarie = getProjectQuestionnaireByProjectId(projectId);
		
		if(questionnarie == null){
			questionnarie = new ProjectQuestionnaire();
			questionnarie.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
		}
		
		Projects projects = projectsDao.getProjectById(projectId);
		questionnarie.setProjectId(projects);
		
		if(!isNullOrEmpty(projectAdditionalDetailsDTO.getProjectLocation())){
			questionnarie.setProjectLocation(projectAdditionalDetailsDTO.getProjectLocation());
		}else{
			questionnarie.setProjectLocation(null);
		}
		
		if(!isNullOrEmpty(projectAdditionalDetailsDTO.getProjectLocation())){
			questionnarie.setIndustry(projectAdditionalDetailsDTO.getIndustry());
			if(projectAdditionalDetailsDTO.getIndustry().equalsIgnoreCase("Others")){
				questionnarie.setIndustryOtherType(projectAdditionalDetailsDTO.getOtherIndustry());
			}else{
				questionnarie.setIndustryOtherType(null);
			}
		}else{
			questionnarie.setIndustry(null);
			questionnarie.setIndustryOtherType(null);
		}
		
		if(!isNullOrEmpty(projectAdditionalDetailsDTO.getScope())){
			questionnarie.setProjectScope(projectAdditionalDetailsDTO.getScope());
			if(projectAdditionalDetailsDTO.getScope().equalsIgnoreCase("Others")){
				questionnarie.setProjectOtherScope(projectAdditionalDetailsDTO.getOtherScope());
			}else{
				questionnarie.setProjectOtherScope(null);
			}
		}else{
			questionnarie.setProjectScope(null);
			questionnarie.setProjectOtherScope(null);
		}
		
	   if(projectAdditionalDetailsDTO.getCategory() != null && projectAdditionalDetailsDTO.getCategory().size() > 0){
	    	StringBuilder categoryBuilder = new StringBuilder();
	    	for(String category : projectAdditionalDetailsDTO.getCategory()){
	    		categoryBuilder.append(category).append(",");
	    	}
	    	String categoryStr = categoryBuilder.toString();
	    	
	    	questionnarie.setEmploymentCategory(categoryBuilder.substring(0, categoryStr.length()-1)); 
	    }else{
	    	questionnarie.setEmploymentCategory(null);
	    }
	   
	   if(!isNullOrEmpty(projectAdditionalDetailsDTO.getTeamSize())){
		   questionnarie.setTeamSize(Integer.parseInt(projectAdditionalDetailsDTO.getTeamSize()));
	   }
	   
	   
	   if(projectAdditionalDetailsDTO.getFrontEnd() != null && projectAdditionalDetailsDTO.getFrontEnd().size() > 0){
	    	StringBuilder frontEndBuilder = new StringBuilder();
	    	for(String frontEnd : projectAdditionalDetailsDTO.getFrontEnd()){
	    		frontEndBuilder.append(frontEnd).append(",");
	    	}
	    	String frontEndStr = frontEndBuilder.toString();
	    	questionnarie.setFrontEnd(frontEndBuilder.substring(0, frontEndStr.length()-1)); 
	    }else{
	    	questionnarie.setFrontEnd(null);
	    }
	   
	   
	   if(projectAdditionalDetailsDTO.getBackEnd() != null && projectAdditionalDetailsDTO.getBackEnd().size() > 0){
	    	StringBuilder backEndBuilder = new StringBuilder();
	    	for(String backEnd : projectAdditionalDetailsDTO.getBackEnd()){
	    		backEndBuilder.append(backEnd).append(",");
	    	}
	    	String backEndStr = backEndBuilder.toString();
	    	questionnarie.setBackEnd(backEndBuilder.substring(0, backEndStr.length()-1)); 
	    }else{
	    	questionnarie.setBackEnd(null);
	    }
	   
	   if(projectAdditionalDetailsDTO.getProjectDatabase() != null && projectAdditionalDetailsDTO.getProjectDatabase().size() > 0){
	    	StringBuilder dataBaseBuilder = new StringBuilder();
	    	for(String database : projectAdditionalDetailsDTO.getProjectDatabase()){
	    		dataBaseBuilder.append(database).append(",");
	    	}
	    	String dataBaseStr = dataBaseBuilder.toString();
	    	questionnarie.setDataBase(dataBaseBuilder.substring(0, dataBaseStr.length()-1)); 
	    }else{
	    	questionnarie.setDataBase(null);
	    }
	   
	   

	   if(projectAdditionalDetailsDTO.getOtherTechnologies() != null && projectAdditionalDetailsDTO.getOtherTechnologies().size() > 0){
	    	StringBuilder otherTechBuilder = new StringBuilder();
	    	for(String otherTech : projectAdditionalDetailsDTO.getOtherTechnologies()){
	    		otherTechBuilder.append(otherTech).append(",");
	    	}
	    	String otherTechStr = otherTechBuilder.toString();
	    	questionnarie.setOtherTechnologies(otherTechBuilder.substring(0, otherTechStr.length()-1)); 
	    }else{
	    	questionnarie.setOtherTechnologies(null);
	    }
	   
	   
	   if(projectAdditionalDetailsDTO.getTestManagement() != null && projectAdditionalDetailsDTO.getTestManagement().size() > 0){
	    	StringBuilder testManagementBuilder = new StringBuilder();
	    	for(String teamManagement : projectAdditionalDetailsDTO.getTestManagement()){
	    		testManagementBuilder.append(teamManagement).append(",");
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
	   

	   if(projectAdditionalDetailsDTO.getAgileMethodologies() != null && projectAdditionalDetailsDTO.getAgileMethodologies().size() > 0){
	    	StringBuilder agileMethodologiesBuilder = new StringBuilder();
	    	for(String agileMethod : projectAdditionalDetailsDTO.getAgileMethodologies()){
	    		agileMethodologiesBuilder.append(agileMethod).append(",");
	    	}
	    	String ageilMethodsStr = agileMethodologiesBuilder.toString();
	    	questionnarie.setAgileMethodologies(agileMethodologiesBuilder.substring(0, ageilMethodsStr.length()-1)); 
	    }else{
	    	questionnarie.setAgileMethodologies(null);
	    }
	   questionnarie.setOrgLocationId(orgLocations);
	   questionnarie.setCreatedBy(user.getUserCode());
	   questionnarie.setCreatedDate(new Date());
	   questionnarie.setUpdatedBy(user.getUserCode());
	   questionnarie.setUpdatedDate(new Date());
	   
	   saveProjectQuestionnaire(questionnarie);
	   
	   projects.setIsProjectAdditionalDetailsFilled(Constants.YES);
	   if(projectAdditionalDetailsDTO.getMailToManagers().equalsIgnoreCase(Constants.YES)){
		   projects.setIsMailToProjectManager(Status.Y);
	   }else{
		   projects.setIsMailToProjectManager(Status.N);
	   }
	   if(projectAdditionalDetailsDTO.getMailToTeamMembers().equalsIgnoreCase(Constants.YES)){
		   projects.setIsMailToTeamMembers(Status.Y);
	   }else{
		   projects.setIsMailToTeamMembers(Status.N);
	   }
	   
	   if(projectAdditionalDetailsDTO.getSaveType().equalsIgnoreCase("Finish")){
		   projects.setProjectMode("Finished");
	   }
	   
	   projectsDao.saveProject(projects);
	
	}

	@Override
	public ProjectQuestionnaire saveProjectQuestionnaire(ProjectQuestionnaire projectQuestionnaire) throws Exception {
		return projectQuestionnaireDao.saveProjectQuestionnaire(projectQuestionnaire);
	}

	@Override
	public ProjectQuestionnaire getProjectQuestionnaireByProjectId(Long projectId) throws Exception {
		return projectQuestionnaireDao.getProjectQuestionnaireByProjectId(projectId);
	}

}
