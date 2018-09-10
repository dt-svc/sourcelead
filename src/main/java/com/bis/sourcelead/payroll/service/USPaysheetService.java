package com.bis.sourcelead.payroll.service;

import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dto.GenerateUSPayrollDTO;
import com.bis.sourcelead.payroll.dto.PaysheetFrequencyDTO;
import com.bis.sourcelead.payroll.dto.USPaysheetDTO;
import com.bis.sourcelead.payroll.entity.PaysheetFrequency;

public interface USPaysheetService {

	ResponseList<USPaysheetDTO> generateUSPaysheet(OrgLocations orgLocation, GenerateUSPayrollDTO generateUSPayrollDTO);

	Response<PaysheetFrequencyDTO> submitPaysheet(OrgLocations orgLocation, PaysheetFrequencyDTO paysheetFrequencyDTO, User loginUser);

	PaysheetFrequency addPaysheetFrequency(PaysheetFrequency paySheetFreq);

	ResponseList<PaysheetFrequencyDTO> getPaySheetFrequencyListByOrgLocationId(OrgLocations orgLocation, PaysheetFrequencyDTO paysheetFrequencyDTO) throws Exception;

	PaysheetFrequency getPayFrequencyByUUID(String paySheetFrequencyUUID);

	PaysheetFrequencyDTO getPaySheetFrequencySummary(PaysheetFrequencyDTO paysheetFrequencyDTO, OrgLocations orgLocation);
}
