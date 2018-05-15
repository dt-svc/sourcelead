package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.entity.AccountDeductionsDetails;

public class RegistrationDTO extends SearchDTO implements Serializable {
 
private static final long serialVersionUID = 1L;

private String email;

public String getEmail()
{
	return email;
}

public void setEmail(String email)
{
	this.email = email;
}


}
