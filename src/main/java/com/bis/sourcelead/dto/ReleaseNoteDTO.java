package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.Date;

public class ReleaseNoteDTO extends SearchDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private long id;
	
	private String releaseName;
	
	private String releaseDate;
	
	private String publishDate;

	private String releaseNotes;
	
	private String releaseDescrption;
	
	private String releaseShortDescription;
	
	private String releaseUUID;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public long getId() {
		return id;
	}

	public String getReleaseName() {
		return releaseName;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public String getReleaseNotes() {
		return releaseNotes;
	}

	public String getReleaseDescrption() {
		return releaseDescrption;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setReleaseName(String releaseName) {
		this.releaseName = releaseName;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public void setReleaseNotes(String releaseNotes) {
		this.releaseNotes = releaseNotes;
	}

	public void setReleaseDescrption(String releaseDescrption) {
		this.releaseDescrption = releaseDescrption;
	}

	public String getReleaseShortDescription() {
		return releaseShortDescription;
	}

	public String getReleaseUUID() {
		return releaseUUID;
	}

	public void setReleaseShortDescription(String releaseShortDescription) {
		this.releaseShortDescription = releaseShortDescription;
	}

	public void setReleaseUUID(String releaseUUID) {
		this.releaseUUID = releaseUUID;
	} 
	
}


