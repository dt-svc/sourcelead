package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.AssociateSalary;

public interface AssociateSalaryDao {

	AssociateSalary addAssociateSal(AssociateSalary associateSalary);

	AssociateSalary findAssociateSalaryByUserId(Long userId);
}
