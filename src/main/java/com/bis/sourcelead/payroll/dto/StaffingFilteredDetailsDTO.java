package com.bis.sourcelead.payroll.dto;

import java.util.List;
import java.util.Map;

public class StaffingFilteredDetailsDTO {
	private List<String> clients;
	private List<Map<String,String>> hlcc;
	private List<String> jobs;
	private String rating;
	private List<StaffingFilteredReviewsDTO> reviews;
	private String vendorId;
	private String vendorName;
	public List<String> getClients() {
		return clients;
	}
	public void setClients(List<String> clients) {
		this.clients = clients;
	}
	public List<Map<String, String>> getHlcc() {
		return hlcc;
	}
	public void setHlcc(List<Map<String, String>> hlcc) {
		this.hlcc = hlcc;
	}
	public List<String> getJobs() {
		return jobs;
	}
	public void setJobs(List<String> jobs) {
		this.jobs = jobs;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public List<StaffingFilteredReviewsDTO> getReviews() {
		return reviews;
	}
	public void setReviews(List<StaffingFilteredReviewsDTO> reviews) {
		this.reviews = reviews;
	}
	public String getVendorId() {
		return vendorId;
	}
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	
}
