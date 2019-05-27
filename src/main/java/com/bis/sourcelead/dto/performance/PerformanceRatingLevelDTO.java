package com.bis.sourcelead.dto.performance;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.bis.sourcelead.annotations.Required;

public class PerformanceRatingLevelDTO implements Serializable{

	    private static final long serialVersionUID = 1L;
	    
	    private Long ratingLevelId;
	    
	    private BigInteger id;
	 
	    @Required(desc="name")
		private String name;
		
	    @Required(desc="description")
		private String description;
		
		private String uuid;
		
		private String createdDate;
		
		private String date;
		
		private String time;
		

		
		public Long getRatingLevelId() {
			return ratingLevelId;
		}

		public void setRatingLevelId(Long ratingLevelId) {
			this.ratingLevelId = ratingLevelId;
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

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
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
