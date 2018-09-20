package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.BulkClientDetails;
import com.bis.sourcelead.entity.User;

/**
 * @author RajaShekar Chelpuri
 *
 */
public interface ClientImportDao {
	
	public BulkClientDetails saveClientVendorExcelData(BulkClientDetails bulkClientDetails) throws Exception;

	public List<BulkClientDetails> getbulkClientDetailsByOrgLocationId(Long orgLocationId, String responderType, User user);

	public BulkClientDetails isRecordExist(BulkClientDetails bulkClientVendors, Long orgLocationId, String responderType) throws Exception;

	public BulkClientDetails getbulkClientByrecordIdAndOrgLocationId(long recordId, Long orgLocationId, String responderType)throws Exception;

	public void updateBulkImportedClients() throws Exception;

	
}
