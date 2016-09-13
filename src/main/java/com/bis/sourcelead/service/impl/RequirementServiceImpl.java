package com.bis.sourcelead.service.impl;

import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.dao.RequirementDao;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.util.CommonUtil;

/**
 * 
 * @author prasad K
 * @date 13th sept 2016
 *
 */

@Service
public class RequirementServiceImpl implements RequirementService {

	private static final Logger logger = LoggerFactory.getLogger(RequirementServiceImpl.class);

	@Autowired
	RequirementDao requirementDao;

	@Autowired
	private CommonUtil commonUtil;
	
	
	
	/**
	 * @author prasad k
	 * @since  aug 26 2016
	 * @version 0.0.1
	 * 
	 * This method is used to store/save Requirement in Requirement table.
	 *  
	 *  
	 * 
	 * @param jsonObj This is the first paramter to storeRequirement method
	 * @param orgLocations This is the second paramter to storeRequirement method
	 * @param user This is the third paramter to storeRequirement method
	 * @param multiFile This is the fourth paramter to storeRequirement method
	 * 
	 * @return Requirement 
	 * 
	 * @throws NullPointer
	 */
	
	@Override
	public Requirement storeRequirement(JSONObject jsonObj,OrgLocations orgLocations,User user,MultipartFile multiFile) throws Exception {
		
		Requirement requirement = new Requirement();
		
		
	        
			if(orgLocations != null){
				requirement.setOrgLocationId(orgLocations);
			}
			
			if(jsonObj.has("jobTitle")){
				requirement.setJobTitile(jsonObj.getString("jobTitle"));
			}
			
			if(jsonObj.has("jobType")){
				requirement.setJobType(jsonObj.getString("jobType"));
			}
			
			if(jsonObj.has("country")){
			requirement.setCountry(jsonObj.getString("country"));
			}
			
			if(jsonObj.has("state")){
			requirement.setState(jsonObj.getString("state"));
			}
			
			if(jsonObj.has("city")){
			requirement.setCity(jsonObj.getString("city"));
			}
			
			if(jsonObj.has("description")){
			requirement.setDescription(jsonObj.getString("description"));
			}
			if(jsonObj.has("skills")){
			requirement.setSkiills(jsonObj.getString("skills"));
			}
			if(jsonObj.has("education")){
			requirement.setEducation(jsonObj.getString("education"));
			}
			
			requirement.setCreatedDate(commonUtil.currentDate());
			requirement.setCreatedBy(user.getUserCode());
			requirement.setUpdatedBy(user.getUserCode());
			requirement.setUpdatedDate(commonUtil.currentDate());
			
		Requirement savedRequirement = requirementDao.storeRequirement(requirement);
		return savedRequirement;
	}

	@Override
	public Requirement addRequirement(Requirement requirement) throws Exception {
		return requirementDao.storeRequirement(requirement);
	}

	/**
	 * @author sarath kumar
	 * @since  sep 13 2016
	 * @version 0.0.1
	 * 
	 * This method is used to get RequirementList by orgLocationId .
	 *  
	 * @param orgLocationId This is the first paramter to getRequirementListByOrgLocationID method
	 * 
	 * @return Requirement 
	 * 
	 * @throws NullPointer
	*/

	@Override
	public List<Requirement> getRequirementListByOrgLocationID(Long orgLocationId) throws Exception {
		return requirementDao.getRequirementListByOrgLocationID(orgLocationId);
	}


	
	
}
