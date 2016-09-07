package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.OrgSpoc;

public interface OrgSpocDao {


    OrgSpoc addOrgSpoc(OrgSpoc orgSpoc)throws Exception;

    
    /**
	 * @author Prasad Salina 
	 * To get all the spocs
	 */
    List<OrgSpoc> getAllSpocsByOrgRelationId(Long orgRelationId, Long userId, List<Long> userIds,Long orgLocationId) throws Exception;
    
    /**To get OrgSpoc by ID
	 * @author Prasad Salina
	 * @param Id
	 * @return
	 * @throws Exception
	 */
	OrgSpoc getOrgSpocById(Long Id) throws Exception;
	
	/**To validate spoc email id while create new spoc
	 * @author Prasad Salina
	 * @param email
	 * @param orgRelationId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	OrgSpoc validateSpacEmailIdByOwnerUserIdAndOrgRelationId(String email, Long orgRelationId, Long userId) throws Exception;


	OrgSpoc getSpocAndOwnerDetailsById(Long spocId, Long ownerId);


	List<Long> getOwnerIdbySpocId(Long spocUserId, Long ownerId);
	
	/**To get Spoc user
	 * @author Prasad Salina
	 * @param spocUserId
	 * @return
	 */
	OrgSpoc getSpocUserByUserId(Long spocUserId) throws Exception;
	
}
