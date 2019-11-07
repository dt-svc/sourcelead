#HolidayRemainder Email
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('HolidayRemainder', 'Email');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'UserName', '{{HolidayRemainder.UserName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'companyName', '{{HolidayRemainder.companyName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'date', '{{HolidayRemainder.date}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'holidayname', '{{HolidayRemainder.holidayname}}');


#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('HolidayRemainder', 'Email', 'HolidayRemainder','This mails should forward to the user about holiday information', 'Holiday on {{HolidayRemainder.date}}',
 
'sourcelead','Best Regards,\nSourcelead Team','Dear {{HolidayRemainder.UserName}},\n\nThis is to inform that there will be holiday on {{HolidayRemainder.date}} due to {{HolidayRemainder.holidayname}}', 'N', 'SLADMIN', 'com','in.com');


