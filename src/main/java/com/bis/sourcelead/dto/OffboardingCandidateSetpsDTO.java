package com.bis.sourcelead.dto;

import java.io.Serializable;

import com.bis.sourcelead.annotations.Required;

/**
 * @author Laxman
 *
 */
@SuppressWarnings("serial")
public class OffboardingCandidateSetpsDTO implements Serializable {
	
	@Required(desc="rating")
	private String rating;
	
	private String sellProperty;
	
	private String sublease;
	
	private String itemsStorage;
	
	private String accommodation;
	
	@Required(desc="surveyReason")
	private String surveyReason;
	
	private String surveyExplanation;

	public String getRating() {
		return rating;
	}

	public String getSellProperty() {
		return sellProperty;
	}

	public String getSublease() {
		return sublease;
	}

	public String getItemsStorage() {
		return itemsStorage;
	}

	public String getAccommodation() {
		return accommodation;
	}

	public String getSurveyReason() {
		return surveyReason;
	}

	public String getSurveyExplanation() {
		return surveyExplanation;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public void setSellProperty(String sellProperty) {
		this.sellProperty = sellProperty;
	}

	public void setSublease(String sublease) {
		this.sublease = sublease;
	}

	public void setItemsStorage(String itemsStorage) {
		this.itemsStorage = itemsStorage;
	}

	public void setAccommodation(String accommodation) {
		this.accommodation = accommodation;
	}

	public void setSurveyReason(String surveyReason) {
		this.surveyReason = surveyReason;
	}

	public void setSurveyExplanation(String surveyExplanation) {
		this.surveyExplanation = surveyExplanation;
	}
}