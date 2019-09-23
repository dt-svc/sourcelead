#VendorProposeNewTime_Email
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('VendorProposeNewTime', 'Email');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUser', '{{VendorProposeNewTime.loginUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'interviewerName', '{{VendorProposeNewTime.interviewerName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'applicantName', '{{VendorProposeNewTime.applicantName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'interviewDate', '{{VendorProposeNewTime.interviewDate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'interviewTime', '{{VendorProposeNewTime.interviewTime}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementTitle', '{{VendorProposeNewTime.requirementTitle}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'proposeNewTimeList', '{{VendorProposeNewTime.proposeNewTimeList}}');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
('VendorProposeNewTime', 'Email', 'VendorProposeNewTime', 'Send mail to Vendor', ' Request for reschedule interview', 'sourcelead', 'Best Regards,\n{{VendorProposeNewTime.loginUser}}','Dear {{ProposeNewTime.interviewerName}},\n Please accept our sincere apologies as our applicant {{VendorProposeNewTime.applicantName}} unable to attend interview,the time you have scheduled. We request you to reschedule the interview based on applicant availability slots if possible. Applicant availability slots are below \n  Applicant Name : {{VendorProposeNewTime.applicantName}} \n  Requirement title : {{VendorProposeNewTime.requirementTitle}} \n Scheduled date :
{{ProposeNewTime.interviewDate}} \n Scheduled Time : {{ProposeNewTime.interviewTime}} .\n {{VendorProposeNewTime.proposeNewTimeList}} ', 'N', 'SLADMIN', 'com','in.com');


#VendorProposeNewTime_Notification
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('VendorProposeNewTime', 'Notification');


#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
('VendorProposeNewTime', 'Notification', 'VendorProposeNewTime', 'Vendor Propose New Time','org','Request for reschedule interview','N', 'SLADMIN', 'com', 'in.com');

