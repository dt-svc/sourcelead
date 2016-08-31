package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.OrgSpoc;

public interface OrgSpocDao {


    OrgSpoc addOrgSpoc(OrgSpoc orgSpoc)throws Exception;

    
    /**
	 * @author Prasad Salina 
	 * To get all the spocs
	 */
    List<OrgSpoc> getAllSpocsByOrgRelationId(Long orgRelationId, Long userId, List<Long> userIds) throws Exception;
    
    /**To get OrgSpoc by ID
	 * @author Prasad Salina
	 * @param Id
	 * @return
	 * @throws Exception
	 */
	OrgSpoc getOrgSpocById(Long Id) throws Exception;


}
