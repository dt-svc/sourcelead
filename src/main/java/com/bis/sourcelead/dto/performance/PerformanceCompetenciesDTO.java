package com.bis.sourcelead.dto.performance;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.bis.sourcelead.annotations.Required;

public class PerformanceCompetenciesDTO implements Serializable{

	    private static final long serialVersionUID = 1L;
	    
	    private Long competenceId;
	    
	    private BigInteger id;
	 
	    @Required(desc="name")
		private String name;
		
	    @Required(desc="description")
		private String description;
		
		private String uuid;
		
		private String createdDate;
		
		private int start;
		
		private int length;
		
		private String searchValue;
		
		private String sortColumn;
		
		private String sortOrder;
		
		private List<Map<String, String>> order;
		

		public Long getCompetenceId() {
			return competenceId;
		}

		public void setCompetenceId(Long competenceId) {
			this.competenceId = competenceId;
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

		public String getUuid() {
			return uuid;
		}

		public void setUuid(String uuid) {
			this.uuid = uuid;
		}

		public int getStart() {
			return start;
		}

		public void setStart(int start) {
			this.start = start;
		}

		public int getLength() {
			return length;
		}

		public void setLength(int length) {
			this.length = length;
		}

		public String getSearchValue() {
			return searchValue;
		}

		public void setSearchValue(String searchValue) {
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

		public List<Map<String, String>> getOrder() {
			return order;
		}

		public void setOrder(List<Map<String, String>> order) {
			this.order = order;
		}

		public String getCreatedDate() {
			return createdDate;
		}

		public void setCreatedDate(String createdDate) {
			this.createdDate = createdDate;
		}

		public BigInteger getId() {
			return id;
		}

		public void setId(BigInteger id) {
			this.id = id;
		}
}
