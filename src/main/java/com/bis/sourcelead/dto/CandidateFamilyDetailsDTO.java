package com.bis.sourcelead.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.bis.sourcelead.annotations.Email;
import com.bis.sourcelead.annotations.Phone;
import com.bis.sourcelead.annotations.Required;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.User;

public class CandidateFamilyDetailsDTO extends SearchDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
   
    private Long id; // added to populate the value in edit mode.
    
	private Long candidateId;
	
	@Required(desc = "first name")
	private String firstName;
	
	private String middleName;
	
	@Required(desc = "last name")
	private String lastName;
	
	@Required(desc = "date of birth")
	private String dateOfBirth;
	
	private String relation;
	
	private String placeOfBirth;
	
	private String nationality;
	
	@Required(desc = "marriage place", dependsOn = "relationType", parentValue = "Spouse" )
	private String marriagePlace;
	
	@Required(desc = "marraige date", dependsOn = "relationType", parentValue = "Spouse")
	private String marraigeDate;
	
	private String marriageEndDate;
	
	private String currentAddress;

	private String status;

	private String createdDate;

	private String createdBy;

	private String updatedDate;

	private String updatedBy;
	
	private String relationType;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getMarriagePlace() {
		return marriagePlace;
	}

	public void setMarriagePlace(String marriagePlace) {
		this.marriagePlace = marriagePlace;
	}

	public String getMarraigeDate() {
		return marraigeDate;
	}

	public void setMarraigeDate(String marraigeDate) {
		this.marraigeDate = marraigeDate;
	}

	public String getMarriageEndDate() {
		return marriageEndDate;
	}

	public void setMarriageEndDate(String marriageEndDate) {
		this.marriageEndDate = marriageEndDate;
	}

	public String getCurrentAddress() {
		return currentAddress;
	}

	public void setCurrentAddress(String currentAddress) {
		this.currentAddress = currentAddress;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getRelationType() {
		return relationType;
	}

	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}
    	
}