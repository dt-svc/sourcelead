package com.bis.sourcelead.reportsDTO;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bis.sourcelead.entity.Asset;

public class AssetGrowthDTO {

	private String categoryIds;
	
	private String dateSelection;
	
	private String fromDate;
	
	private String listFromDate;
	
	private String toDate;

	private String fromtype;

	private String year;
	
	private String searchValue;
	
	private BigInteger categoryId;
	
	private Date purchaseDate;
	
	private String catgoryName;
	
	private BigInteger assestId;
	
	private String slNo;
	
	private int assetCount;
	
	private int categoryCount;
	
	private String monthNames;
	
	private int monthNumber;

	private int noOfDays;
	
	private String gridType;
	
	private BigDecimal cost;
	
	private double costDoble;
	
	
	Map<String,Long> categoryNameMap;

	public String getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(String categoryIds) {
		this.categoryIds = categoryIds;
	}

	public String getDateSelection() {
		return dateSelection;
	}

	public void setDateSelection(String dateSelection) {
		this.dateSelection = dateSelection;
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

	public String getFromtype() {
		return fromtype;
	}

	public void setFromtype(String fromtype) {
		this.fromtype = fromtype;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public Map<String, Long> getCategoryNameMap() {
		return categoryNameMap;
	}

	public void setCategoryNameMap(Map<String, Long> categoryNameMap) {
		this.categoryNameMap = categoryNameMap;
	}

	public BigInteger getCategoryId() {
		return categoryId;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public String getCatgoryName() {
		return catgoryName;
	}

	public BigInteger getAssestId() {
		return assestId;
	}

	public String getSlNo() {
		return slNo;
	}
 

	public void setCategoryId(BigInteger categoryId) {
		this.categoryId = categoryId;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public void setCatgoryName(String catgoryName) {
		this.catgoryName = catgoryName;
	}

	public void setAssestId(BigInteger assestId) {
		this.assestId = assestId;
	}

	public void setSlNo(String slNo) {
		this.slNo = slNo;
	}

	public int getAssetCount() {
		return assetCount;
	}

	public int getCategoryCount() {
		return categoryCount;
	}

	public void setAssetCount(int assetCount) {
		this.assetCount = assetCount;
	}

	public void setCategoryCount(int categoryCount) {
		this.categoryCount = categoryCount;
	}

	public String getMonthNames() {
		return monthNames;
	}

	public void setMonthNames(String monthNames) {
		this.monthNames = monthNames;
	}

	public int getMonthNumber() {
		return monthNumber;
	}

	public void setMonthNumber(int monthNumber) {
		this.monthNumber = monthNumber;
	}

	public int getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(int noOfDays) {
		this.noOfDays = noOfDays;
	}

	public String getGridType() {
		return gridType;
	}

	public void setGridType(String gridType) {
		this.gridType = gridType;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public double getCostDoble() {
		return costDoble;
	}

	public void setCostDoble(double costDoble) {
		this.costDoble = costDoble;
	}

	public String getListFromDate() {
		return listFromDate;
	}

	public void setListFromDate(String listFromDate) {
		this.listFromDate = listFromDate;
	}
}
