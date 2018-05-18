package com.bis.sourcelead.service;

import java.util.List;

import org.json.JSONObject;

import com.bis.sourcelead.dto.SourceleadNotesDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.SourceleadNotes;
import com.bis.sourcelead.entity.User;

public interface SourceleadNotesService {

public SourceleadNotes storeComments(SourceleadNotesDTO sourceleadNotesDTO,User user,OrgLocations orgLocations) throws Exception;

public List<SourceleadNotes> getNotesListByOrglocationIdAndType(Long orgrelationId, Long orgLocationId) throws Exception;
	
public List<SourceleadNotes> getNotesListByRequirementId(Long requirementId,Long orgLocationId) throws Exception;

public List<SourceleadNotes> getNotesListByAssetId(Long assetId,Long orgLocationId) throws Exception;

public List<SourceleadNotes> getNotesListByInvoiceID(Long invoiceId,Long orgLocationId) throws Exception;

public List<SourceleadNotes> getNotesListByContractId(Long contractId,Long orgLocationId) throws Exception;

public List<SourceleadNotes> getNotesListByImmigrationId(Long immigrationId, Long orgLocationId) throws Exception;

}
