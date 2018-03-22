package com.bis.sourcelead.dto;

import java.util.List;

import com.bis.sourcelead.entity.Employees;

public class ResidenceDTO extends CommonDTO {

		private String residence;
		
		private String country;
		
		private String startDate;
		
		private String endDate;

		public String getResidence() {
			return residence;
		}

		public void setResidence(String residence) {
			this.residence = residence;
		}

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		public String getStartDate() {
			return startDate;
		}

		public void setStartDate(String startDate) {
			this.startDate = startDate;
		}

		public String getEndDate() {
			return endDate;
		}

		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}
			
}
