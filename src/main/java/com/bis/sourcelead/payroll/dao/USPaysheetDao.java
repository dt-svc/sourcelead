package com.bis.sourcelead.payroll.dao;

import java.util.List;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dto.PaysheetFrequencyDTO;
import com.bis.sourcelead.payroll.entity.PaysheetFrequency;

public interface USPaysheetDao {

	PaysheetFrequency addPaysheetFrequency(PaysheetFrequency paysheetFrequency);

	ResponseList<PaysheetFrequency> getPaySheetFrequencyListByOrgLocationId(OrgLocations orgLocation,PaysheetFrequencyDTO paysheetFrequencyDTO) throws Exception;

	PaysheetFrequency getPayFrequencyByUUID(String paySheetFrequencyUUID);
}
