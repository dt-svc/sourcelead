package com.bis.sourcelead.dto.performance;

import java.io.Serializable;
import java.math.BigInteger;

import com.bis.sourcelead.annotations.Required;

public class PerformanceRatingScaleDTO implements Serializable {
	 private static final long serialVersionUID = 1L;
	    
	    private Long ratingScaleId;
	    
	    private BigInteger id;
	 
	    @Required(desc="ratingName")
		private String ratingName;
	    
	    @Required(desc="ratingLevelId")
		private String ratingLevelId;
	    
	    @Required(desc="ratingScaleValue")
		private Integer ratingScaleValue;
	    
	    @Required(desc="shortCode")
		private String shortCode;
	    
		
		private String description;
		
		private String uuid;
		
		private String createdDate;
		
		private String date;
		
		private String time;
		
		private String levelName;
		
		private BigInteger levelId;
		
		
		

		public BigInteger getLevelId() {
			return levelId;
		}

		public void setLevelId(BigInteger levelId) {
			this.levelId = levelId;
		}

		public String getLevelName() {
			return levelName;
		}

		public void setLevelName(String levelName) {
			this.levelName = levelName;
		}

		public Long getRatingScaleId() {
			return ratingScaleId;
		}

		public void setRatingScaleId(Long ratingScaleId) {
			this.ratingScaleId = ratingScaleId;
		}

		public BigInteger getId() {
			return id;
		}

		public void setId(BigInteger id) {
			this.id = id;
		}

		public String getRatingName() {
			return ratingName;
		}

		public void setRatingName(String ratingName) {
			this.ratingName = ratingName;
		}

		public String getRatingLevelId() {
			return ratingLevelId;
		}

		public void setRatingLevelId(String ratingLevelId) {
			this.ratingLevelId = ratingLevelId;
		}

		public Integer getRatingScaleValue() {
			return ratingScaleValue;
		}

		public void setRatingScaleValue(Integer ratingScaleValue) {
			this.ratingScaleValue = ratingScaleValue;
		}

		public String getShortCode() {
			return shortCode;
		}

		public void setShortCode(String shortCode) {
			this.shortCode = shortCode;
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

		public String getCreatedDate() {
			return createdDate;
		}

		public void setCreatedDate(String createdDate) {
			this.createdDate = createdDate;
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
		
		

}
