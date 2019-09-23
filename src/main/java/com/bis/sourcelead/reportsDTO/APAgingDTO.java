package com.bis.sourcelead.reportsDTO;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.dto.SearchDTO;

public class APAgingDTO extends SearchDTO {

	private double tenDays;
	private double fifteenDays;
	private double thirtyDays;
	private double sixtyDays;
	private double nintyDays;
	private double oneeightyDays;
	private double threeSixtyFiveDays;
	private double abovethreeSixtyFiveDays;
	private double previousYearsTotal;
	private String apagingType;
	private String orgName;
	private double total;
	private String referId;
	private String billDate;
	private String dueDate;
	private double dueAmount;
	private double totalAmount;
	private double tenDaysToatl;
	private double fifteenDaysTotal;
	private double thirtyDaysTotal;
	private double sixtyDaysTotal;
	private double nintyDaysTotal;
	private double oneeightyDaysTotal;
	private double threeSixtyFiveDaysTotal;
	private double above365DaysTotal;
	private String orgIds;
	private String filterType;
	private String fromDate;
	private String toDate;
	private  int recordsTotal;
	private  int recordsFiltered; 
	List<APAgingDTO> apaAgingList;
	List<AparagingDate> above365Dates;
	

	public double getPreviousYearsTotal() {
		return previousYearsTotal;
	}

	public void setPreviousYearsTotal(double previousYearsTotal) {
		this.previousYearsTotal = previousYearsTotal;
	}

	public double getTenDays() {
		return tenDays;
	}

	public void setTenDays(double tenDays) {
		this.tenDays = tenDays;
	}

	public double getFifteenDays() {
		return fifteenDays;
	}

	public void setFifteenDays(double fifteenDays) {
		this.fifteenDays = fifteenDays;
	}

	public double getThirtyDays() {
		return thirtyDays;
	}

	public void setThirtyDays(double thirtyDays) {
		this.thirtyDays = thirtyDays;
	}

	public double getSixtyDays() {
		return sixtyDays;
	}

	public void setSixtyDays(double sixtyDays) {
		this.sixtyDays = sixtyDays;
	}

	public double getNintyDays() {
		return nintyDays;
	}

	public void setNintyDays(double nintyDays) {
		this.nintyDays = nintyDays;
	}

	public double getOneeightyDays() {
		return oneeightyDays;
	}

	public void setOneeightyDays(double oneeightyDays) {
		this.oneeightyDays = oneeightyDays;
	}

	public double getThreeSixtyFiveDays() {
		return threeSixtyFiveDays;
	}

	public void setThreeSixtyFiveDays(double threeSixtyFiveDays) {
		this.threeSixtyFiveDays = threeSixtyFiveDays;
	}

	

	public double getAbovethreeSixtyFiveDays() {
		return abovethreeSixtyFiveDays;
	}

	public void setAbovethreeSixtyFiveDays(double abovethreeSixtyFiveDays) {
		this.abovethreeSixtyFiveDays = abovethreeSixtyFiveDays;
	}

	public String getApagingType() {
		return apagingType;
	}

	public void setApagingType(String apagingType) {
		this.apagingType = apagingType;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String getReferId() {
		return referId;
	}

	public void setReferId(String referId) {
		this.referId = referId;
	}

	public String getBillDate() {
		return billDate;
	}

	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public double getDueAmount() {
		return dueAmount;
	}

	public void setDueAmount(double dueAmount) {
		this.dueAmount = dueAmount;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public double getTenDaysToatl() {
		return tenDaysToatl;
	}

	public void setTenDaysToatl(double tenDaysToatl) {
		this.tenDaysToatl = tenDaysToatl;
	}

	public double getFifteenDaysTotal() {
		return fifteenDaysTotal;
	}

	public void setFifteenDaysTotal(double fifteenDaysTotal) {
		this.fifteenDaysTotal = fifteenDaysTotal;
	}

	public double getThirtyDaysTotal() {
		return thirtyDaysTotal;
	}

	public void setThirtyDaysTotal(double thirtyDaysTotal) {
		this.thirtyDaysTotal = thirtyDaysTotal;
	}

	public double getSixtyDaysTotal() {
		return sixtyDaysTotal;
	}

	public void setSixtyDaysTotal(double sixtyDaysTotal) {
		this.sixtyDaysTotal = sixtyDaysTotal;
	}

	public double getNintyDaysTotal() {
		return nintyDaysTotal;
	}

	public void setNintyDaysTotal(double nintyDaysTotal) {
		this.nintyDaysTotal = nintyDaysTotal;
	}

	public double getOneeightyDaysTotal() {
		return oneeightyDaysTotal;
	}

	public void setOneeightyDaysTotal(double oneeightyDaysTotal) {
		this.oneeightyDaysTotal = oneeightyDaysTotal;
	}

	public double getThreeSixtyFiveDaysTotal() {
		return threeSixtyFiveDaysTotal;
	}

	public void setThreeSixtyFiveDaysTotal(double threeSixtyFiveDaysTotal) {
		this.threeSixtyFiveDaysTotal = threeSixtyFiveDaysTotal;
	}

	public double getAbove365DaysTotal() {
		return above365DaysTotal;
	}

	public void setAbove365DaysTotal(double above365DaysTotal) {
		this.above365DaysTotal = above365DaysTotal;
	}

	public String getOrgIds() {
		return orgIds;
	}

	public void setOrgIds(String orgIds) {
		this.orgIds = orgIds;
	}

	public String getFilterType() {
		return filterType;
	}

	public void setFilterType(String filterType) {
		this.filterType = filterType;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public int getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(int recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public int getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(int recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public List<APAgingDTO> getApaAgingList() {
		return apaAgingList;
	}

	public void setApaAgingList(List<APAgingDTO> apaAgingList) {
		this.apaAgingList = apaAgingList;
	}

	public List<AparagingDate> getAbove365Dates() {
		return above365Dates;
	}

	public void setAbove365Dates(List<AparagingDate> above365Dates) {
		this.above365Dates = above365Dates;
	}

	
	

}
