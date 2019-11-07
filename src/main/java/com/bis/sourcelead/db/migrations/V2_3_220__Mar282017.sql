SET SQL_SAFE_UPDATES = 0;

#Mail to Manager when HotList added before one month
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('HotListAlertMail', 'Email');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'managerName', '{{HotListAlertMail.managerName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'consultantsCount', '{{HotListAlertMail.consultantsCount}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'tableOfContent', '{{HotListAlertMail.tableOfContent}}');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
										('HotListAlertMail', 'Email', 'HotListAlertMail', 'HotList Alert Mail', '{{HotListAlertMail.consultantsCount}} consultants added to Hotlist', 'sourcelead', 'Best Regards \nSourceLead Team', 'Dear {{HotListAlertMail.managerName}},\n&nbsp;&nbsp;&nbsp; Following list of consultants added to Hotlist due to their projects are at Completion Stage \n{{HotListAlertMail.tableOfContent}}', 'N', 'SLADMIN', 'com', 'in.com');


#Notification to Manager when HotList added before one month
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('HotListAlertNotification', 'Notification');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
										('HotListAlertNotification', 'Notification', 'HotListAlertNotification', 'HotList Alert Notification','org','{{HotListAlertMail.consultantsCount}} consultants added to Hotlist','N', 'SLADMIN', 'com', 'in.com');

SET SQL_SAFE_UPDATES = 1;