package com.bis.sourcelead.payroll.service;

import com.bis.sourcelead.dto.AssociateSalaryDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.AssociatePayItem;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dto.PayItemDTO;
import com.bis.sourcelead.payroll.entity.PayItem;

public interface PayItemService {

	Response<PayItemDTO> addPayItem(OrgLocations orgLocation, PayItemDTO payItemDTO, User loginUser);

	ResponseList<PayItemDTO> getPayItemByLocationId(OrgLocations orgLocation);

	PayItem getPayItemByUUID(String payItemUUID);

	Response<PayItem> deletePayItemByUUID(PayItemDTO payItemDTO, User user);

	Response<PayItem> validatePayItemName(PayItemDTO payItemDTO, OrgLocations orgLocation);

	ResponseList<PayItemDTO> getPayItemByLocationId(OrgLocations orgLocation, String associateEmpType);

	AssociatePayItem savePayItemsFromSummary(AssociateSalaryDTO associateSalaryDTO, OrgLocations orgLocation, User user);

}
