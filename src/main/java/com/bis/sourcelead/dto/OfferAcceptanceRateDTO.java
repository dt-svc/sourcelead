package com.bis.sourcelead.dto;

import java.io.Serializable;

public class OfferAcceptanceRateDTO implements Serializable {

	/**
	 * @author Mahaveer Manoj
	 */
	private static final long serialVersionUID = 1L;
	private String offerDate;
	private int offerCount;
	private int acceptedCount;
	private int rejectedCount;
	private String selectedClients;

	public String getOfferDate() {
		return offerDate;
	}

	public void setOfferDate(String offerDate) {
		this.offerDate = offerDate;
	}

	public int getOfferCount() {
		return offerCount;
	}

	public void setOfferCount(int offerCount) {
		this.offerCount = offerCount;
	}

	public int getAcceptedCount() {
		return acceptedCount;
	}

	public void setAcceptedCount(int acceptedCount) {
		this.acceptedCount = acceptedCount;
	}

	public int getRejectedCount() {
		return rejectedCount;
	}

	public void setRejectedCount(int rejectedCount) {
		this.rejectedCount = rejectedCount;
	}

	public String getSelectedClients() {
		return selectedClients;
	}

	public void setSelectedClients(String selectedClients) {
		this.selectedClients = selectedClients;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
