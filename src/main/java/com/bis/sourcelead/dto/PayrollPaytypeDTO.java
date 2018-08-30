package com.bis.sourcelead.dto;

import java.io.Serializable;

import com.bis.sourcelead.annotations.Required;

public class PayrollPaytypeDTO implements Serializable{
	
	 private static final long serialVersionUID = 1L;
	 
	 	private Long id;
	 
	 	@Required(desc="name")
		private String name;
		
		private String description;
		
	    private String orgLocationId;
	    
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getOrgLocationId() {
			return orgLocationId;
		}

		public void setOrgLocationId(String orgLocationId) {
			this.orgLocationId = orgLocationId;
		}
	
}
