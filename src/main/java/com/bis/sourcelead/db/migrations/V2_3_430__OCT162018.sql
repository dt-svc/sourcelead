#OffBoarding_ProcessApplicantTerminationMailToReportingManager_Email
#entity_type
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('OffBoardingProcessMailToReportingManager', 'Email');

#CLASS_FIELD
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'applicantName', '{{OffBoardingProcessMailToReportingManager.applicantName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginUser', '{{OffBoardingProcessMailToReportingManager.loginUser}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'companyName', '{{OffBoardingProcessMailToReportingManager.companyName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginLink', '{{OffBoardingProcessMailToReportingManager.loginLink}}');

#email_scenario
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('OffBoardingProcessMailToReportingManager', 'Email', 'OffBoardingProcessMailToReportingManager', 'Send mail to OffBoardingProcessMailToReportingManager', 
'{{OffBoardingProcessMailToReportingManager.loginUser}} has been Removed from {{OffBoardingProcessMailToReportingManager.companyName}}','sourcelead','Best Regards,\n{{OffBoardingProcessMailToReportingManager.loginUser}}',
'Dear {{OffBoardingProcessMailToReportingManager.applicantName}},\n\nWe regret to inform you that {{OffBoardingProcessMailToReportingManager.loginUser}} with {{OffBoardingProcessMailToReportingManager.companyName}} has been Removed.,\nPlease assign is task to another Manager.\nPlease click here to login for further process.\n\n{{OffBoardingProcessMailToReportingManager.loginLink}}', 'N', 'SLADMIN', 'sourceLead.com','in.sourceLead.com');


#OffBoarding_ProcessApplicantTerminationMailToReportingManager_Notification
#entity_type
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('OffBoardingProcessMailToReportingManager', 'Notification');


#email_scenario
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('OffBoardingProcessMailToReportingManager', 'Notification', 'OffBoardingProcessMailToReportingManager', 'Send mail to OffBoardingProcessMailToReportingManager','org','{{OffBoardingProcessMailToReportingManager.loginUser}} has been Removed from {{OffBoardingProcessMailToReportingManager.companyName}}','N', 'SLADMIN', 'sourceLead.com', 'in.sourceLead.com');

