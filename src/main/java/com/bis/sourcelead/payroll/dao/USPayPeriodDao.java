package com.bis.sourcelead.payroll.dao;

import com.bis.sourcelead.payroll.entity.USPayPeriod;

public interface USPayPeriodDao {

	USPayPeriod getPayPeriodNumber(Long payFrequencyId);

	USPayPeriod savePayPeriodNumber(USPayPeriod uSPayPeriod);
}
