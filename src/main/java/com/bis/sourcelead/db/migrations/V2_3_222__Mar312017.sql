#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ApplicantAcceptMailToInterviewCreator', 'Email');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'InterviewCreatorName', '{{ApplicantAcceptMailToInterviewCreator.InterviewCreatorName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'ApplicantName', '{{ApplicantAcceptMailToInterviewCreator.ApplicantName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementName', '{{ApplicantAcceptMailToInterviewCreator.requirementName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'InterviewDate', '{{ApplicantAcceptMailToInterviewCreator.InterviewDate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'InterviewTime', '{{ApplicantAcceptMailToInterviewCreator.InterviewTime}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'InterviewType', '{{ApplicantAcceptMailToInterviewCreator.InterviewType}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'Details', '{{ApplicantAcceptMailToInterviewCreator.Details}}');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES 
('Applicant AcceptMailToInterviewCreator','Email', 'ApplicantAcceptMailToInterviewCreator', 'Used to accept mail to manager', '{{ApplicantAcceptMailToInterviewCreator.ApplicantName}} is attending for interview.','org', 'Best Regards,\nSourcelead Team.', 'Dear {{ApplicantAcceptMailToInterviewCreator.InterviewCreatorName}},\n\n{{ApplicantAcceptMailToInterviewCreator.ApplicantName}} attending for interview.\n Go through the brief details.\n\n Requirement Title :{{ApplicantAcceptMailToInterviewCreator.requirementName}}\nInterview Date :{{ApplicantAcceptMailToInterviewCreator.InterviewDate}}\nInterview Time :{{ApplicantAcceptMailToInterviewCreator.InterviewTime}}\nInterview Type :{{ApplicantAcceptMailToInterviewCreator.InterviewType}}\nDetails :{{ApplicantAcceptMailToInterviewCreator.Details}}','N', 'SLADMIN', 'slead', 'sleadIN');


INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ApplicantAcceptMailToInterviewCreator', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES('Applicant AcceptMailToInterviewCreator', 'Notification', 'ApplicantAcceptMailToInterviewCreator', 'Used to accept mail to manager', '{{ApplicantAcceptMailToInterviewCreator.ApplicantName}} is attending for interview.', 'sourcelead', '', '{{ApplicantAcceptMailToInterviewCreator.ApplicantName}} is attending for interview.','N','SLADMIN', 'com', 'in.com');



#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ApplicantRejectMailToInterviewCreator', 'Email');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'InterviewCreatorName', '{{ApplicantRejectMailToInterviewCreator.InterviewCreatorName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'ApplicantName', '{{ApplicantRejectMailToInterviewCreator.ApplicantName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementName', '{{ApplicantRejectMailToInterviewCreator.requirementName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'InterviewDate', '{{ApplicantRejectMailToInterviewCreator.InterviewDate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'InterviewTime', '{{ApplicantRejectMailToInterviewCreator.InterviewTime}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'InterviewType', '{{ApplicantRejectMailToInterviewCreator.InterviewType}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'Details', '{{ApplicantRejectMailToInterviewCreator.Details}}');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES 
('Applicant RejectMailToInterviewCreator','Email', 'ApplicantRejectMailToInterviewCreator', 'Used to Reject mail to manager', '{{ApplicantRejectMailToInterviewCreator.ApplicantName}} is not attending for interview.','org', 'Best Regards,\nSourcelead Team.', 'Dear {{ApplicantRejectMailToInterviewCreator.InterviewCreatorName}},\n\n{{ApplicantAcceptMailToInterviewCreator.ApplicantName}} is not attending for interview.\n Go through the brief details.\n\n Requirement Title :{{ApplicantRejectMailToInterviewCreator.requirementName}}\nInterview Date :{{ApplicantRejectMailToInterviewCreator.InterviewDate}}\nInterview Time :{{ApplicantRejectMailToInterviewCreator.InterviewTime}}\nInterview Type :{{ApplicantRejectMailToInterviewCreator.InterviewType}}\nDetails :{{ApplicantRejectMailToInterviewCreator.Details}}','N', 'SLADMIN', 'slead', 'sleadIN');



INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ApplicantRejectMailToInterviewCreator', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES('Applicant RejectMailToInterviewCreator', 'Notification', 'ApplicantRejectMailToInterviewCreator', 'Used to reject mail to manager', '{{ApplicantRejectMailToInterviewCreator.ApplicantName}} is not attending for interview.', 'sourcelead', '', '{{ApplicantAcceptMailToInterviewCreator.ApplicantName}} is not attending for interview.','N','SLADMIN', 'com', 'in.com');




#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ApplicantTentativeMailToInterviewCreator', 'Email');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'InterviewCreatorName', '{{ApplicantTentativeMailToInterviewCreator.InterviewCreatorName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'ApplicantName', '{{ApplicantTentativeMailToInterviewCreator.ApplicantName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementName', '{{ApplicantTentativeMailToInterviewCreator.requirementName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'InterviewDate', '{{ApplicantTentativeMailToInterviewCreator.InterviewDate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'InterviewTime', '{{ApplicantTentativeMailToInterviewCreator.InterviewTime}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'InterviewType', '{{ApplicantTentativeMailToInterviewCreator.InterviewType}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'Details', '{{ApplicantTentativeMailToInterviewCreator.Details}}');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES 
('Applicant TentativeMailToInterviewCreator','Email', 'ApplicantTentativeMailToInterviewCreator', 'applicant tentative mail to manager', '{{ApplicantTentativeMailToInterviewCreator.ApplicantName}} may attend for interview.','org', 'Best Regards,\nSourcelead Team.', 'Dear {{ApplicantTentativeMailToInterviewCreator.InterviewCreatorName}},\n\n{{ApplicantAcceptMailToInterviewCreator.ApplicantName}} may attend for interview.\n Go through the brief details.\n\n Requirement Title :{{ApplicantTentativeMailToInterviewCreator.requirementName}}\nInterview Date :{{ApplicantTentativeMailToInterviewCreator.InterviewDate}}\nInterview Time :{{ApplicantTentativeMailToInterviewCreator.InterviewTime}}\nInterview Type :{{ApplicantTentativeMailToInterviewCreator.InterviewType}}\nDetails :{{ApplicantTentativeMailToInterviewCreator.Details}}','N', 'SLADMIN', 'slead', 'sleadIN');



INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ApplicantTentativeMailToInterviewCreator', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES('Applicant TentativeMailToInterviewCreator', 'Notification', 'ApplicantTentativeMailToInterviewCreator', 'Used for tentative mail to manager', '{{ApplicantTentativeMailToInterviewCreator.ApplicantName}} may attend for interview.', 'sourcelead', '', '{{ApplicantTentativeMailToInterviewCreator.ApplicantName}} may attend for interview.','N','SLADMIN', 'com', 'in.com');





#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('VendorApplicantAcceptMailToInterviewCreator', 'Email');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'InterviewCreatorName', '{{VendorApplicantAcceptMailToInterviewCreator.InterviewCreatorName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'ApplicantName', '{{VendorApplicantAcceptMailToInterviewCreator.ApplicantName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementName', '{{VendorApplicantAcceptMailToInterviewCreator.requirementName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'InterviewDate', '{{VendorApplicantAcceptMailToInterviewCreator.InterviewDate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'InterviewTime', '{{VendorApplicantAcceptMailToInterviewCreator.InterviewTime}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'InterviewType', '{{VendorApplicantAcceptMailToInterviewCreator.InterviewType}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'Details', '{{VendorApplicantAcceptMailToInterviewCreator.Details}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'LoginorgUserLocation', '{{VendorApplicantAcceptMailToInterviewCreator.LoginorgUserLocation}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'LoginorgUserName', '{{VendorApplicantAcceptMailToInterviewCreator.LoginorgUserName}}');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES 
('Vendor Applicant AcceptMailToInterviewCreator','Email', 'VendorApplicantAcceptMailToInterviewCreator', 'Vendor Applicant Accept Mail To InterviewCreator', '{{VendorApplicantAcceptMailToInterviewCreator.ApplicantName}} is ready for attending interview.','org', 'Best Regards,\n{{VendorApplicantAcceptMailToInterviewCreator.LoginorgUserName}}\n{{VendorApplicantAcceptMailToInterviewCreator.LoginorgUserLocation}}.', 'Dear {{VendorApplicantAcceptMailToInterviewCreator.InterviewCreatorName}},\n\n{{ApplicantAcceptMailToInterviewCreator.ApplicantName}} is attending for interview.\n Go through the brief details.\n\n Requirement Title :{{VendorApplicantAcceptMailToInterviewCreator.requirementName}}\nInterview Date :{{VendorApplicantAcceptMailToInterviewCreator.InterviewDate}}\nInterview Time :{{VendorApplicantAcceptMailToInterviewCreator.InterviewTime}}\nInterview Type :{{VendorApplicantAcceptMailToInterviewCreator.InterviewType}}\nDetails :{{VendorApplicantAcceptMailToInterviewCreator.Details}}','N', 'SLADMIN', 'slead', 'sleadIN');




INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('VendorApplicantAcceptMailToInterviewCreator', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES('Vendor Applicant AcceptMailToInterviewCreator', 'Notification', 'VendorApplicantAcceptMailToInterviewCreator', 'Used to accept mail to vendor', '{{VendorApplicantAcceptMailToInterviewCreator.ApplicantName}} is ready for attending interview.', 'sourcelead', '', '{{VendorApplicantAcceptMailToInterviewCreator.ApplicantName}} is ready for attending interview.','N','SLADMIN', 'com', 'in.com');





#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('VendorApplicantRejectMailToInterviewCreator', 'Email');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'InterviewCreatorName', '{{VendorApplicantRejectMailToInterviewCreator.InterviewCreatorName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'ApplicantName', '{{VendorApplicantRejectMailToInterviewCreator.ApplicantName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementName', '{{VendorApplicantRejectMailToInterviewCreator.requirementName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'InterviewDate', '{{VendorApplicantRejectMailToInterviewCreator.InterviewDate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'InterviewTime', '{{VendorApplicantRejectMailToInterviewCreator.InterviewTime}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'InterviewType', '{{VendorApplicantRejectMailToInterviewCreator.InterviewType}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'Details', '{{VendorApplicantRejectMailToInterviewCreator.Details}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'LoginorgUserLocation', '{{VendorApplicantRejectMailToInterviewCreator.LoginorgUserLocation}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'LoginorgUserName', '{{VendorApplicantRejectMailToInterviewCreator.LoginorgUserName}}');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES 
('Vendor Applicant RejectMailToInterviewCreator','Email', 'VendorApplicantRejectMailToInterviewCreator', 'Vendor Applicant Reject Mail To InterviewCreator', '{{VendorApplicantRejectMailToInterviewCreator.ApplicantName}} is not  attending for interview.','org', 'Best Regards,\n{{VendorApplicantRejectMailToInterviewCreator.LoginorgUserName}}\n{{VendorApplicantRejectMailToInterviewCreator.LoginorgUserLocation}}.', 'Dear {{VendorApplicantRejectMailToInterviewCreator.InterviewCreatorName}},\n\nWe are sorry to say {{ApplicantAcceptMailToInterviewCreator.ApplicantName}} is not attending for interview.\n Go through the brief details.\n\n Requirement Title :{{VendorApplicantRejectMailToInterviewCreator.requirementName}}\nInterview Date :{{VendorApplicantRejectMailToInterviewCreator.InterviewDate}}\nInterview Time :{{VendorApplicantRejectMailToInterviewCreator.InterviewTime}}\nInterview Type :{{VendorApplicantRejectMailToInterviewCreator.InterviewType}}\nDetails :{{VendorApplicantRejectMailToInterviewCreator.Details}}','N', 'SLADMIN', 'slead', 'sleadIN');


INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('VendorApplicantRejectMailToInterviewCreator', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES('Vendor Applicant RejectMailToInterviewCreator', 'Notification', 'VendorApplicantRejectMailToInterviewCreator', 'Used to reject mail to vendor', '{{VendorApplicantRejectMailToInterviewCreator.ApplicantName}} is not  attending for interview.', 'sourcelead', '', '{{VendorApplicantRejectMailToInterviewCreator.ApplicantName}} is not  attending for interview.','N','SLADMIN', 'com', 'in.com');



#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('VendorApplicantTentativeMailToInterviewCreator', 'Email');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'InterviewCreatorName', '{{VendorApplicantTentativeMailToInterviewCreator.InterviewCreatorName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'ApplicantName', '{{VendorApplicantTentativeMailToInterviewCreator.ApplicantName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementName', '{{VendorApplicantTentativeMailToInterviewCreator.requirementName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'InterviewDate', '{{VendorApplicantTentativeMailToInterviewCreator.InterviewDate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'InterviewTime', '{{VendorApplicantTentativeMailToInterviewCreator.InterviewTime}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'InterviewType', '{{VendorApplicantTentativeMailToInterviewCreator.InterviewType}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'Details', '{{VendorApplicantTentativeMailToInterviewCreator.Details}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'LoginorgUserLocation', '{{VendorApplicantTentativeMailToInterviewCreator.LoginorgUserLocation}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'LoginorgUserName', '{{VendorApplicantTentativeMailToInterviewCreator.LoginorgUserName}}');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES 
('Vendor Applicant TentativeMailToInterviewCreator','Email', 'VendorApplicantTentativeMailToInterviewCreator', 'Vendor Applicant Tentative Mail To InterviewCreator', '{{VendorApplicantTentativeMailToInterviewCreator.ApplicantName}} may attend for interview.','org', 'Best Regards,\n{{VendorApplicantTentativeMailToInterviewCreator.LoginorgUserName}}\n{{VendorApplicantTentativeMailToInterviewCreator.LoginorgUserLocation}}.', 'Dear {{VendorApplicantTentativeMailToInterviewCreator.InterviewCreatorName}},\n\n{{ApplicantAcceptMailToInterviewCreator.ApplicantName}} may attend for interview.\n Go through the brief details.\n\n Requirement Title :{{VendorApplicantTentativeMailToInterviewCreator.requirementName}}\nInterview Date :{{VendorApplicantTentativeMailToInterviewCreator.InterviewDate}}\nInterview Time :{{VendorApplicantTentativeMailToInterviewCreator.InterviewTime}}\nInterview Type :{{VendorApplicantTentativeMailToInterviewCreator.InterviewType}}\nDetails :{{VendorApplicantTentativeMailToInterviewCreator.Details}}','N', 'SLADMIN', 'slead', 'sleadIN');




INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('VendorApplicantTentativeMailToInterviewCreator', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES('Vendor Applicant TentativeMailToInterviewCreator', 'Notification', 'VendorApplicantRejectMailToInterviewCreator', 'Used for tentative mail to vendor', '{{VendorApplicantTentativeMailToInterviewCreator.ApplicantName}} may attend for interview.', 'sourcelead', '', '{{VendorApplicantTentativeMailToInterviewCreator.ApplicantName}} may attend for interview.','N','SLADMIN', 'com', 'in.com');




