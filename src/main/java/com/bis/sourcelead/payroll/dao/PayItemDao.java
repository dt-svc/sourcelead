package com.bis.sourcelead.payroll.dao;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.entity.PayItem;

public interface PayItemDao {

	PayItem addPayItem(PayItem payItem);

	ResponseList<PayItem>  getPayItemByLocationId(OrgLocations orgLocation);
}
