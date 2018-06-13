package com.bis.sourcelead.reports.dao;

import java.util.List;

import com.bis.sourcelead.entity.AssociateSalaryHistory;

public interface AssociateSalaryHistoryDao {

	List<AssociateSalaryHistory> findAssociateSalHistory(Long associateSalid);
}
