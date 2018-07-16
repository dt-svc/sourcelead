package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.AssociatePayItem;
import com.bis.sourcelead.entity.AssociateSalary;
import com.bis.sourcelead.entity.AssociateSalaryHistory;

public interface AssociateSalaryDao {

	AssociateSalary addAssociateSal(AssociateSalary associateSalary);

	AssociateSalary findAssociateSalaryByUserId(Long userId);

	AssociateSalary findAssociateSalaryById(String id);

	AssociateSalaryHistory updaetAssociateSalHis(AssociateSalaryHistory associateSalHis);

	AssociatePayItem findPayItemByPayItemId(Long payItemId);

	AssociatePayItem saveUpdatedPayItem(AssociatePayItem associatePayItem);
}
