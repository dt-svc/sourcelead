#OffBoarding_ProcessApplicantTerminationMailToReportingManager_Email
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('OffBoardingProcessMailToReportingManager', 'Email');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'applicantName', '{{OffBoardingProcessMailToReportingManager.applicantName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUser', '{{OffBoardingProcessMailToReportingManager.loginUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'companyName', '{{OffBoardingProcessMailToReportingManager.companyName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginLink', '{{OffBoardingProcessMailToReportingManager.loginLink}}');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('OffBoardingProcessMailToReportingManager', 'Email', 'OffBoardingProcessMailToReportingManager', 'Send mail to OffBoardingProcessMailToReportingManager', 
'{{OffBoardingProcessMailToReportingManager.loginUser}} has been Removed from {{OffBoardingProcessMailToReportingManager.companyName}}','sourcelead','Best Regards,\n{{OffBoardingProcessMailToReportingManager.loginUser}}',
'Dear {{OffBoardingProcessMailToReportingManager.applicantName}},\n\nWe regret to inform you that {{OffBoardingProcessMailToReportingManager.loginUser}} with {{OffBoardingProcessMailToReportingManager.companyName}} has been Removed.,\nPlease assign is task to another Manager.\nPlease click here to login for further process.\n\n{{OffBoardingProcessMailToReportingManager.loginLink}}', 'N', 'SLADMIN', 'com','in.com');


#OffBoarding_ProcessApplicantTerminationMailToReportingManager_Notification
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('OffBoardingProcessMailToReportingManager', 'Notification');


#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('OffBoardingProcessMailToReportingManager', 'Notification', 'OffBoardingProcessMailToReportingManager', 'Send mail to OffBoardingProcessMailToReportingManager','org','{{OffBoardingProcessMailToReportingManager.loginUser}} has been Removed from {{OffBoardingProcessMailToReportingManager.companyName}}','N', 'SLADMIN', 'com', 'in.com');


#OffBoardingApplicantDealayProcess_Email
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('OffBoardingApplicantDealayProcess', 'Email');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'candidateName', '{{OffBoardingApplicantDealayProcess.candidateName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'companyName', '{{OffBoardingApplicantDealayProcess.companyName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginLink', '{{OffBoardingApplicantDealayProcess.loginLink}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'ManagerName', '{{OffBoardingApplicantDealayProcess.ManagerName}}');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('OffBoardingApplicantDealayProcess', 'Email', 'OffBoardingApplicantDealayProcess', 'Send mail to OffBoardingApplicantDealayProcess', 
'Alert Off boarding process delayed for {{OffBoardingApplicantDealayProcess.candidateName}}','sourcelead','Best Regards,\n{{OffBoardingApplicantDealayProcess.companyName}}',
'Dear {{OffBoardingApplicantDealayProcess.ManagerName}},\n\nOff-boarding process for {{OffBoardingApplicantDealayProcess.candidateName}} has been delayed. \nTo view more details please Login\n\n{{OffBoardingApplicantDealayProcess.loginLink}}', 'N', 'SLADMIN', 'com','in.com');




CALL AlterTableAddColumn ('USER_ROLE','IS_MAIL_SENT','VARCHAR(10) NULL');

SET SQL_SAFE_UPDATES=0;
UPDATE `USER_ROLE` SET `IS_MAIL_SENT`='Y' ;
SET SQL_SAFE_UPDATES=1;





