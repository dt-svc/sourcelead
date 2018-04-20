package com.bis.sourcelead.dto;

import com.bis.sourcelead.annotations.Required;

public class JobAlertDTO {

	 private String savedJobAlertId;
	 
	 @Required(desc="alertTitle")
	 private String alertTitle;
	 
	 @Required(desc="email", dependsOn = "tabType", parentValue = "openjobsForm")
	 private String email;
	 
	 private String country;
	 
	 private String state;
	 
	 private String city;
	 
	 private String location;
	 
	 private String zipcode;
	 
	 private String experience;
	 
	 private String rateCurrency;
	 
	 private String rate;
	 
	 private String rateType;
	 
	 private String industry;
	 
	 @Required(desc="skills")
	 private String skills;
	 
	 @Required(desc="duration")
	 private String duration;
	 
	 private String tabType;

	public String getSavedJobAlertId()
	{
		return savedJobAlertId;
	}

	public void setSavedJobAlertId(String savedJobAlertId)
	{
		this.savedJobAlertId = savedJobAlertId;
	}

	public String getAlertTitle()
	{
		return alertTitle;
	}

	public void setAlertTitle(String alertTitle)
	{
		this.alertTitle = alertTitle;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getCountry()
	{
		return country;
	}

	public void setCountry(String country)
	{
		this.country = country;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	public String getZipcode()
	{
		return zipcode;
	}

	public void setZipcode(String zipcode)
	{
		this.zipcode = zipcode;
	}

	public String getExperience()
	{
		return experience;
	}

	public void setExperience(String experience)
	{
		this.experience = experience;
	}

	public String getRateCurrency()
	{
		return rateCurrency;
	}

	public void setRateCurrency(String rateCurrency)
	{
		this.rateCurrency = rateCurrency;
	}

	public String getRate()
	{
		return rate;
	}

	public void setRate(String rate)
	{
		this.rate = rate;
	}

	public String getRateType()
	{
		return rateType;
	}

	public void setRateType(String rateType)
	{
		this.rateType = rateType;
	}

	public String getIndustry()
	{
		return industry;
	}

	public void setIndustry(String industry)
	{
		this.industry = industry;
	}

	public String getSkills()
	{
		return skills;
	}

	public void setSkills(String skills)
	{
		this.skills = skills;
	}

	public String getDuration()
	{
		return duration;
	}

	public void setDuration(String duration)
	{
		this.duration = duration;
	}

	public String getTabType()
	{
		return tabType;
	}

	public void setTabType(String tabType)
	{
		this.tabType = tabType;
	}
	 
}
