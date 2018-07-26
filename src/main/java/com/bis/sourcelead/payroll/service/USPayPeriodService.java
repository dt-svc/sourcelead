package com.bis.sourcelead.payroll.service;

import com.bis.sourcelead.payroll.entity.USPayPeriod;

public interface USPayPeriodService {

	USPayPeriod getPayPeriodNumber(Long payFrequencyId);

	USPayPeriod savePayPeriodNumber(USPayPeriod uSPayPeriod);
}
