package com.bis.sourcelead.constants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * 
 * @author Rajashekar
 * @date 6th July 2016
 *
 */

public class Constants {
	public static final String[] FILE_HEADER_MAPPING = { "Employee ID", "First Name", "Last Name", "Email id",
			"Phone Number", "Rate", "Rate Per", "Date of Join", "Role", "Invoice Frequency", "Day Hours" };

	public static final String EMPLOYEE_ID = "Employee ID";
	public static final String FIRST_NAME = "First Name";
	public static final String LAST_NAME = "Last Name";
	public static final String EMAIL_ID = "Email id";
	public static final String PHONE_NUMBER = "Phone Number";
	public static final String RATE = "Rate";
	public static final String RATEPER = "Rate Per";
	public static final String DATEOFJOIN = "Date of Join";
	public static final String ROLE = "Role";
	public static final String INVOICE_FREQUENCY = "Invoice Frequency";
	public static final String DAY_HOURS = "Day Hours";
	public static final String FAILED_REASON = "Reason";
	   
	public static transient DateFormat DMY = new SimpleDateFormat("dd-MM-yyyy");
	public static transient DateFormat DF = new SimpleDateFormat("yyyy/MM/dd");
	public static DateFormat DF_STD = new SimpleDateFormat("yyyy/MM/dd");
	public static DateFormat DF_YMD = new SimpleDateFormat("yyyy-MM-dd");
	public static DateFormat DF_STD_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
	
	public static DateFormat DF_DMY = new SimpleDateFormat("dd-MM-yyyy");
	
	public static String MAIL_TEAM_MEMBERS = "Mail To Team Members";
	public static String INVITE_USER = "Invite User";
	
	public static String ENTITY_TYPE_EMAIL = "Email";
	
	public static TimeZone TIMEZONE_CENTRAL = TimeZone.getTimeZone("America/Chicago");
}
