package com.bis.sourcelead.service;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.User;

/**
 * @author Prasad K
 *
*/
public interface RequirementService {
	
	/**
	 * @param jsonObj
	 * @param orgLocations
	 * @param user
	 * @param multiFile
	 * @return
	 * @throws Exception
	 */
	public Requirement storeRequirement(JSONObject jsonObj,OrgLocations orgLocations,User user,MultipartFile multiFile) throws Exception;

	
	/**
	 * @param Requirement
	 * @return
	 * @throws Exception
	 */
	public Requirement addRequirement(Requirement requirement) throws Exception;
	
	
}
