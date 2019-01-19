package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class SearchDTO extends CommonDTO implements Serializable {
	
	private List<String> anyOfKeyword;
	private List<String> allKeywords;
	private List<String> excludeKeyWord;
	private String country;
	private String state;
	private String city;
	private String updatedOn;
	private String updatedDateRange;
	private int start;
	private int length;
	private String searchValue;
	private Map<String, String> search;
	private List<Map<String, String>> order;
	private String sortColumn = "id";
	private String sortOrder = "desc";
	private String searchCountry;
	private String searchState;
	private String searchCity;
	
	public List<String> getAnyOfKeyword()
	{
		return anyOfKeyword;
	}
	
	public void setAnyOfKeyword(List<String> anyOfKeyword)
	{
		this.anyOfKeyword = anyOfKeyword;
	}
	
	public List<String> getAllKeywords()
	{
		return allKeywords;
	}
	
	public void setAllKeywords(List<String> allKeywords)
	{
		this.allKeywords = allKeywords;
	}
	
	public List<String> getExcludeKeyWord()
	{
		return excludeKeyWord;
	}
	
	public void setExcludeKeyWord(List<String> excludeKeyWord)
	{
		this.excludeKeyWord = excludeKeyWord;
	}
	
	public String getCountry()
	{
		return country;
	}
	
	public void setCountry(String country)
	{
		this.country = country;
	}
	
	public String getState()
	{
		return state;
	}
	
	public void setState(String state)
	{
		this.state = state;
	}
	
	public String getCity()
	{
		return city;
	}
	
	public void setCity(String city)
	{
		this.city = city;
	}
	
	public String getUpdatedOn()
	{
		return updatedOn;
	}
	
	public void setUpdatedOn(String updatedOn)
	{
		this.updatedOn = updatedOn;
	}
	
	public String getUpdatedDateRange()
	{
		return updatedDateRange;
	}
	
	public void setUpdatedDateRange(String updatedDateRange)
	{
		this.updatedDateRange = updatedDateRange;
	}
	
	public int getStart()
	{
		return start;
	}
	
	public void setStart(int start)
	{
		this.start = start;
	}
	
	public int getLength()
	{
		return length;
	}
	
	public void setLength(int length)
	{
		this.length = length;
	}
	
	public Map<String, String> getSearch()
	{
		return search;
	}
	
	public void setSearch(Map<String, String> search)
	{
		this.search = search;
	}
	
	public List<Map<String, String>> getOrder()
	{
		return order;
	}
	
	public void setOrder(List<Map<String, String>> order)
	{
		this.order = order;
	}
	
	public String getSearchValue()
	{
		return searchValue;
	}
	
	public void setSearchValue(String searchValue)
	{
		this.searchValue = searchValue;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getSearchCountry() {
		return searchCountry;
	}

	public void setSearchCountry(String searchCountry) {
		this.searchCountry = searchCountry;
	}

	public String getSearchState() {
		return searchState;
	}

	public void setSearchState(String searchState) {
		this.searchState = searchState;
	}

	public String getSearchCity() {
		return searchCity;
	}

	public void setSearchCity(String searchCity) {
		this.searchCity = searchCity;
	}
}
