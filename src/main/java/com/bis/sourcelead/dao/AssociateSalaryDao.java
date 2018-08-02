package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.AssociatePayItem;
import com.bis.sourcelead.entity.AssociateSalary;
import com.bis.sourcelead.entity.AssociateSalaryHistory;
import com.bis.sourcelead.entity.OrgLocations;

public interface AssociateSalaryDao {

	AssociateSalary addAssociateSal(AssociateSalary associateSalary);

	AssociateSalary findAssociateSalaryByUserId(Long userId);

	AssociateSalary findAssociateSalaryById(String id);

	AssociateSalaryHistory updaetAssociateSalHis(AssociateSalaryHistory associateSalHis);

	AssociatePayItem findPayItemByPayItemId(Long payItemId);

	AssociatePayItem saveUpdatedPayItem(AssociatePayItem associatePayItem);

	List<AssociateSalary> getAssociateSalaryListByGroupId(OrgLocations orgLocation, long groupId);
}
