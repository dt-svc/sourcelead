package com.bis.sourcelead.dto.performance;

import java.io.Serializable;

import com.bis.sourcelead.annotations.Required;

public class PerformanceCompetenciesDTO implements Serializable{

	    private static final long serialVersionUID = 1L;
	 
	    @Required(desc="name")
		private String name;
		
	    @Required(desc="description")
		private String description;
		
		private String uuid;

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

		public String getUuid() {
			return uuid;
		}

		public void setUuid(String uuid) {
			this.uuid = uuid;
		}
}
