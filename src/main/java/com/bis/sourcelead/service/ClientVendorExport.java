package com.bis.sourcelead.service;

import java.io.File;
import java.util.List;

import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.ProjectClients;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.Projects;

public interface ClientVendorExport {

	File getClientExportFile(List<OrgRelations> clientsList, String exportType, String string) throws Exception;

	File getClientContactsExportFile(List<OrgSpoc> vandorsContactList, String exportType, String orgName) throws Exception;

	File getVendorDetailsFile(List<OrgRelations> vandorsList, String orgName, String exportType) throws Exception;

	File getVendorContactDetailsFile(List<OrgSpoc> vandorsContactList, String orgName, String exportType)throws Exception;

    File getexportProjectEmployeeFile(List<ProjectMembers> employeeList,String exportType, Projects project,ProjectClients projectClients)throws Exception;
  
}
