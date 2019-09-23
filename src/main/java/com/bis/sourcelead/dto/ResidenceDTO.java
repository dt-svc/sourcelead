package com.bis.sourcelead.dto;

import java.util.List;

import com.bis.sourcelead.annotations.Required;
import com.bis.sourcelead.entity.Employees;
/**
 * @author SanjeevReddy Indurthi
 **/
public class ResidenceDTO extends CommonDTO {
	
	     private Long residenceId;
	     
	    @Required(desc="location")
		private String location;
	    
		private String streetAddress;
		
		private String city;
		
		private String state;
		
		private String country;
		
		private String zipcode;
		
		private String isCurrResi;
		
		@Required(desc="residence start date")
		private String residenceStartDate;
		
		@Required(desc="residence end date",dependsOn = "isCurrResi", parentValue = "N")
		private String residenceEndDate;

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}

		public String getStreetAddress() {
			return streetAddress;
		}

		public void setStreetAddress(String streetAddress) {
			this.streetAddress = streetAddress;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		public String getZipcode() {
			return zipcode;
		}

		public void setZipcode(String zipcode) {
			this.zipcode = zipcode;
		}

		public String getResidenceStartDate() {
			return residenceStartDate;
		}

		public void setResidenceStartDate(String residenceStartDate) {
			this.residenceStartDate = residenceStartDate;
		}

		public String getResidenceEndDate() {
			return residenceEndDate;
		}

		public void setResidenceEndDate(String residenceEndDate) {
			this.residenceEndDate = residenceEndDate;
		}

		public Long getResidenceId() {
			return residenceId;
		}

		public void setResidenceId(Long residenceId) {
			this.residenceId = residenceId;
		}

		public String getIsCurrResi() {
			return isCurrResi;
		}

		public void setIsCurrResi(String isCurrResi) {
			this.isCurrResi = isCurrResi;
		}

		

		
			
}
