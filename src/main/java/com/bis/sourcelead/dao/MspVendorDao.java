package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.dto.MspVendorDTO;
import com.bis.sourcelead.entity.MspVendor;
import com.bis.sourcelead.entity.MspVendorHistory;
import com.bis.sourcelead.entity.OrgRelations;

public interface MspVendorDao {
	
	/**
	 * @author Laxman
	 * @param mspVendor
	 * @return
	 * @throws Exception
	 */
	
	MspVendor saveMspVendor(MspVendor mspVendor) throws Exception;
	
	MspVendor getMspVendorListByOrgLocationIdAndMspRelationId(Long orgLcoationId,Long orgRelationId) throws Exception;

	List<MspVendorDTO> getMspClientlistByOrgLocationId(Long mspOrgLocationId)throws Exception;
	
	List<OrgRelations> selectedMSPVendorListByOrgLocationId(MspVendorDTO  mspVendorDTO)throws Exception;

	MspVendorHistory saveMspVendorHistory(MspVendorHistory mspVendorHistory)throws Exception;

    MspVendorHistory getMspVendorHistoryDetailsByMSPRelationIdAndOrgRelationId(Long mpsRealtionId,Long orgRelationId)throws Exception;
	
	List<MspVendorHistory> mspVendorHistoryListByMspRelationIdMspOrgLocationId(Long mspRelationId,Long mspOrgLocationId) throws Exception;
	
}
