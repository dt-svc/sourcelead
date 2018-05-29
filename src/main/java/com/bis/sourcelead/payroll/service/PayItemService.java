package com.bis.sourcelead.payroll.service;

import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dto.PayItemDTO;

public interface PayItemService {

	Response<PayItemDTO> addPayItem(OrgLocations orgLocation, PayItemDTO payItemDTO, User loginUser);

	ResponseList<PayItemDTO> getPayItemByLocationId(OrgLocations orgLocation, String associateEmpType);
}
