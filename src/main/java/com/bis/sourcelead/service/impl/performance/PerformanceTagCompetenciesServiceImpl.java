package com.bis.sourcelead.service.impl.performance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.RolesDao;
import com.bis.sourcelead.dao.performance.PerformanceTagCompetenciesDao;
import com.bis.sourcelead.dto.performance.PerformanceTagCompetenciesDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.performance.PerformanceTagCompetencies;
import com.bis.sourcelead.service.performance.PerformanceTagCompetenciesService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class PerformanceTagCompetenciesServiceImpl  implements PerformanceTagCompetenciesService{
	
	@Autowired
	PerformanceTagCompetenciesDao performanceTagCompetenciesDao;
	
	@Autowired
	RolesDao rolesDao;
	
	@Autowired
	CommonUtil commonUtil;

	@Override
	public PerformanceTagCompetencies savePerformanceTagCompetencies(
			PerformanceTagCompetencies performanceTagCompetencies) throws Exception {
		return performanceTagCompetenciesDao.savePerformanceTagCompetencies(performanceTagCompetencies);
	}

	@Override
	public PerformanceTagCompetencies storePerformanceTagCompetencies(
			PerformanceTagCompetenciesDTO performanceTagCompetenciesDTO, Long orgLocationId, Long userId)
			throws Exception {
		
		OrgLocations orgLocations = new OrgLocations();
		orgLocations.setId(orgLocationId);
		User user = new User();
		user.setId(userId);
		PerformanceTagCompetencies tagCometencies = null;
		if(performanceTagCompetenciesDTO.getTagCompetenceId() != null) {
			tagCometencies = performanceTagCompetenciesDao.getPerformanceTagCompetenciesById(performanceTagCompetenciesDTO.getTagCompetenceId());
			tagCometencies.setUpdatedBy(user);
			tagCometencies.setUpdatedDate(new Date());
		}else {
			tagCometencies = new PerformanceTagCompetencies();
			tagCometencies.setCreatedBy(user);
			tagCometencies.setCreatedDate(new Date());
			tagCometencies.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
			tagCometencies.setOrgLocationId(orgLocations);
			tagCometencies.setStatus(Constants.RECORD_ACTIVE);
		}
		
		tagCometencies.setCompetencies(performanceTagCompetenciesDTO.getPerformanceCompetence());
		tagCometencies.setRoles(performanceTagCompetenciesDTO.getCompetenceRole());
		tagCometencies.setCompetenciesTaggingJsonString(performanceTagCompetenciesDTO.getJsonCompetenceString());
		savePerformanceTagCompetencies(tagCometencies);
		
		return tagCometencies;
	}

	@Override
	public PerformanceTagCompetencies getPerformanceTagCompetenciesById(Long id) throws Exception {
		return performanceTagCompetenciesDao.getPerformanceTagCompetenciesById(id);
	}

	@Override
	public List<PerformanceTagCompetenciesDTO> getPerformanceTagCompetenciesList(Long orgLocationId,String dateFormat) throws Exception {
		
		List<PerformanceTagCompetenciesDTO> tatCompetenciesDTOList = new ArrayList<>();
		List<PerformanceTagCompetencies> tagCompetenciesList =  performanceTagCompetenciesDao.getPerformanceTagCompetenciesList(orgLocationId);
		if(tagCompetenciesList != null) {
			tagCompetenciesList.forEach(tagCompetencies -> {
				PerformanceTagCompetenciesDTO taCompetenciesDTO = new PerformanceTagCompetenciesDTO();
				taCompetenciesDTO.setTagCompetenceId(tagCompetencies.getId());
				if(tagCompetencies.getRoles() != null) {
					String roleIds[] = tagCompetencies.getRoles().split(",");
					List<String> rolesList = new ArrayList<String>();
					rolesList = Arrays.asList(roleIds);
					try {
						List<String> rolesNamesList = rolesDao.getRolesNamesListByRolesId(rolesList);
						StringBuilder sb = new StringBuilder();
						for (String s : rolesNamesList)
						{
						    sb.append(s).append(",");
						}
						String roleNames = sb.deleteCharAt(sb.length()-1).toString(); 
						taCompetenciesDTO.setCompetenceRoleNames(roleNames);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				if(tagCompetencies.getCompetencies() != null) {
					String competenciesIds[] = tagCompetencies.getCompetencies().split(",");
					taCompetenciesDTO.setCompetenceCount(competenciesIds.length);
				}
				taCompetenciesDTO.setUuid(tagCompetencies.getUuid());
				
				if(tagCompetencies.getCompetenciesTaggingJsonString() != null) {
					Integer totalWeightage = 0;
					JSONArray jsonArray;
					try {
						jsonArray = new JSONArray(tagCompetencies.getCompetenciesTaggingJsonString());
						for (int i = 0; i < jsonArray.length(); i++) {
							 JSONParser parser = new JSONParser();
						    JSONObject jsonObjVal = (JSONObject) parser.parse(jsonArray.get(i).toString());
						    for (Object okey : jsonObjVal.keySet()) {
						    	 String keyStr = (String) okey;
						    	 if(keyStr.equalsIgnoreCase("weightage")) {
						    		 String keyvalue = jsonObjVal.get(keyStr).toString();
						    		 totalWeightage = totalWeightage+Integer.parseInt(keyvalue);
						    	 }
						    	 
						    }
						    
						}
					} catch (JSONException e) {
						e.printStackTrace();
					} catch (ParseException e) {
						e.printStackTrace();
					}
				taCompetenciesDTO.setTagCompetenceId(Long.valueOf(tagCompetencies.getId()));
				taCompetenciesDTO.setJsonCompetenceString(tagCompetencies.getCompetenciesTaggingJsonString());
				taCompetenciesDTO.setTotalWeightage(totalWeightage);
				taCompetenciesDTO.setCreatedDate(commonUtil.dateStrFromOrgDateFormatter(dateFormat,tagCompetencies.getCreatedDate()));
				}
				
				tatCompetenciesDTOList.add(taCompetenciesDTO);
				
			});
		}
		
		return tatCompetenciesDTOList;
	}

}
