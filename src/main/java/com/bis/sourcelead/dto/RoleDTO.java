package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.math.BigInteger;

public class RoleDTO implements Serializable {
	private Long id;
	private String displayName;
	private Integer actionsCount;
	private Long hierarchyId;
	private String hierarchyName;
	private Long roleId;
	private String uuid;
	private BigInteger rolesId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Integer getActionsCount() {
		return actionsCount;
	}

	public void setActionsCount(Integer actionsCount) {
		this.actionsCount = actionsCount;
	}

	public Long getHierarchyId() {
		return hierarchyId;
	}

	public void setHierarchyId(Long hierarchyId) {
		this.hierarchyId = hierarchyId;
	}

	public String getHierarchyName() {
		return hierarchyName;
	}

	public void setHierarchyName(String hierarchyName) {
		this.hierarchyName = hierarchyName;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public BigInteger getRolesId() {
		return rolesId;
	}

	public void setRolesId(BigInteger rolesId) {
		this.rolesId = rolesId;
	}

}
