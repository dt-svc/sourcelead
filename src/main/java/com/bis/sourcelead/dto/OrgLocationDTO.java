package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class OrgLocationDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String orgName;
	
	private String locationCode;
	
	private String orgCode;
	
	private Long orgId;
	
	private Long orgLocationId;
	
	private Long candidateId;
	
	private String orgImageUrl;
	
	private String country;
	
	private String state;
	
	private String city;
	
	private String orgLocationAddress;
	
	private String website;
	
	private String isPrimaryOrg;
	
	private String userType;
	
	List<String> menuList;
	
	//List<String> roleActionsList;
	
	List<String> menuIconsList;
	
	private Long vendorOrgLocationId;
	
	private Long vendorOrgRelationId;
	
	private Long vendorOnboardId;
	
	

	public String getOrgName()
	{
		return orgName;
	}

	public void setOrgName(String orgName)
	{
		this.orgName = orgName;
	}

	public String getLocationCode()
	{
		return locationCode;
	}

	public void setLocationCode(String locationCode)
	{
		this.locationCode = locationCode;
	}

	public String getOrgCode()
	{
		return orgCode;
	}

	public void setOrgCode(String orgCode)
	{
		this.orgCode = orgCode;
	}

	public String getOrgImageUrl()
	{
		return orgImageUrl;
	}

	public void setOrgImageUrl(String orgImageUrl)
	{
		this.orgImageUrl = orgImageUrl;
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

	public String getOrgLocationAddress()
	{
		return orgLocationAddress;
	}

	public void setOrgLocationAddress(String orgLocationAddress)
	{
		this.orgLocationAddress = orgLocationAddress;
	}

	public String getWebsite()
	{
		return website;
	}

	public void setWebsite(String website)
	{
		this.website = website;
	}

	public String getIsPrimaryOrg()
	{
		return isPrimaryOrg;
	}

	public void setIsPrimaryOrg(String isPrimaryOrg)
	{
		this.isPrimaryOrg = isPrimaryOrg;
	}

	
	public String getUserType()
	{
		return userType;
	}

	public void setUserType(String userType)
	{
		this.userType = userType;
	}

	public List<String> getMenuList()
	{
		return menuList;
	}

	public void setMenuList(List<String> menuList)
	{
		this.menuList = menuList;
	}

/*	public List<String> getRoleActionsList()
	{
		return roleActionsList;
	}

	public void setRoleActionsList(List<String> roleActionsList)
	{
		this.roleActionsList = roleActionsList;
	}
*/
	public List<String> getMenuIconsList() {
		return menuIconsList;
	}

	public void setMenuIconsList(List<String> menuIconsList) {
		this.menuIconsList = menuIconsList;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(Long orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public Long getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}

	public Long getVendorOrgLocationId() {
		return vendorOrgLocationId;
	}

	public void setVendorOrgLocationId(Long vendorOrgLocationId) {
		this.vendorOrgLocationId = vendorOrgLocationId;
	}

	public Long getVendorOrgRelationId() {
		return vendorOrgRelationId;
	}

	public void setVendorOrgRelationId(Long vendorOrgRelationId) {
		this.vendorOrgRelationId = vendorOrgRelationId;
	}

	public Long getVendorOnboardId() {
		return vendorOnboardId;
	}

	public void setVendorOnboardId(Long vendorOnboardId) {
		this.vendorOnboardId = vendorOnboardId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	
}
