package com.bis.sourcelead.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "TABS")
public class Tabs implements Serializable{
	
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	
	@Column(name = "TAB_NAME")
	private String tabName;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TAB_ID", nullable = false)
	private Tabs tabs;
	
	
    @Column(name = "SUB_TAB_ORDER", nullable = false)
	private Long subTabOrder;
    
    @Column(name = "TAB_ORDER", nullable = false)
   	private Long tabOrder;
    
    
    @Column(name = "STATUS", nullable = false)
   	private Integer status;
    
    @Column(name = "TAB_ICON")
	private String tabIcon;
	
    
    

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTabName() {
		return tabName;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}

	public Tabs getTabs() {
		return tabs;
	}

	public void setTabs(Tabs tabs) {
		this.tabs = tabs;
	}

	public Long getSubTabOrder() {
		return subTabOrder;
	}

	public void setSubTabOrder(Long subTabOrder) {
		this.subTabOrder = subTabOrder;
	}

	public Long getTabOrder() {
		return tabOrder;
	}

	public void setTabOrder(Long tabOrder) {
		this.tabOrder = tabOrder;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTabIcon() {
		return tabIcon;
	}

	public void setTabIcon(String tabIcon) {
		this.tabIcon = tabIcon;
	}
    
    

}
