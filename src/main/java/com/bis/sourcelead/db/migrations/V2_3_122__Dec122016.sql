#Applicant receive Email about requirement.

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('ApplicantReceiveMailAboutRequirement','Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'applicantName', '{{ApplicantReceiveMailAboutRequirement.applicantName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementName', '{{ApplicantReceiveMailAboutRequirement.requirementName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'organizationName', '{{ApplicantReceiveMailAboutRequirement.organizationName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'location', '{{ApplicantReceiveMailAboutRequirement.location}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'experience', '{{ApplicantReceiveMailAboutRequirement.experience}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'skills', '{{ApplicantReceiveMailAboutRequirement.skills}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'jobDescription', '{{ApplicantReceiveMailAboutRequirement.jobDescription}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementRate', '{{ApplicantReceiveMailAboutRequirement.requirementRate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUserName', '{{ApplicantReceiveMailAboutRequirement.loginUserName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'acceptLink', '{{ApplicantReceiveMailAboutRequirement.acceptLink}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'rejectLink', '{{ApplicantReceiveMailAboutRequirement.rejectLink}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'negotiateRateLink', '{{ApplicantReceiveMailAboutRequirement.negotiateRateLink}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'attachmentLink', '{{ApplicantReceiveMailAboutRequirement.attachmentLink}}');




INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
											('ApplicantReceiveMailAboutRequirement','Email', 'ApplicantReceiveMailAboutRequirement', 'Used mail from login user to Applicant  ', 'Job Opening || {{ApplicantReceiveMailAboutRequirement.requirementName}} - {{ApplicantReceiveMailAboutRequirement.organizationName}}','org', 'Best Regards,\n{{ApplicantReceiveMailAboutRequirement.loginUserName}},\n{{ApplicantReceiveMailAboutRequirement.organizationName}}', 'Dear {{ApplicantReceiveMailAboutRequirement.applicantName}},\n\n We have opening for the {{ApplicantReceiveMailAboutRequirement.requirementName}}, \n\n Exeperience : {{ApplicantReceiveMailAboutRequirement.experience}} \n\n Location : {{ApplicantReceiveMailAboutRequirement.location}} \n\n Rate :{{ApplicantReceiveMailAboutRequirement.requirementRate}}\n\n Skills  : {{ApplicantReceiveMailAboutRequirement.skills}} \n\n Job Description :  {{ApplicantReceiveMailAboutRequirement.jobDescription}} .\n\n  If interested , please click on the link accept. Your acceptance will assist us for further process of recruitment for this position. \n\n {{ApplicantReceiveMailAboutRequirement.acceptLink}} {{ApplicantReceiveMailAboutRequirement.rejectLink}} {{ApplicantReceiveMailAboutRequirement.negotiateRateLink}} {{ApplicantReceiveMailAboutRequirement.attachmentLink}} \n\n  As these position is urgent, we would appreciate a quick response from your end. \n','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');


											
#Applicant Acceptence Email.

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('ApplicantAcceptRequirement','Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'location', '{{ApplicantAcceptRequirement.location}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'experience', '{{ApplicantAcceptRequirement.experience}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'skills', '{{ApplicantAcceptRequirement.skills}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'jobDescription', '{{ApplicantAcceptRequirement.jobDescription}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'applicantName', '{{ApplicantAcceptRequirement.applicantName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'initiatorUserName', '{{ApplicantAcceptRequirement.initiatorUserName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementName', '{{ApplicantAcceptRequirement.requirementName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementRate', '{{ApplicantAcceptRequirement.requirementRate}}');



INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
											('ApplicantAcceptRequirement','Email', 'ApplicantAcceptRequirement', 'Used mail   from  Applicant  to login user', '{{ApplicantAcceptRequirement.applicantName}} interested for position {{ApplicantAcceptRequirement.requirementName}}','org', 'Best Regards,\n{{ApplicantAcceptRequirement.applicantName}}', 'Dear {{ApplicantAcceptRequirement.initiatorUserName}},\n\n {{ApplicantAcceptRequirement.applicantName}} interested for the position {{ApplicantAcceptRequirement.requirementName}} \n\n Exeperience : {{ApplicantAcceptRequirement.experience}} \n\n Location : {{ApplicantAcceptRequirement.location}} \n\n Rate :{{ApplicantAcceptRequirement.requirementRate}}\n\n Skills  : {{ApplicantAcceptRequirement.skills}} \n\n Job Description :  {{ApplicantAcceptRequirement.jobDescription}} .\n\n  I am waiting for the further process of recruitment. \n','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');


											
#Applicant Acceptence Notification.

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ApplicantAcceptRequirement', 'Notification');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'applicantName', '{{ApplicantAcceptRequirement.applicantName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementName', '{{ApplicantAcceptRequirement.requirementName}}');



INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
                                          ('ApplicantAcceptRequirement','Notification','ApplicantAcceptRequirement', 'Used mail   from  Applicant  to login user', '{{ApplicantAcceptRequirement.applicantName}} interested for position {{ApplicantAcceptRequirement.requirementName}}', 'sourcelead', '', '{{ApplicantAcceptRequirement.applicantName}} interested for position {{ApplicantAcceptRequirement.requirementName}}','N','SLADMIN', 'sourcelead', 'sourceleadIN');

                                          
                                          
#Applicant Rejectance Email.


INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('ApplicantRejectanceRequirement','Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'location', '{{ApplicantRejectanceRequirement.location}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'experience', '{{ApplicantRejectanceRequirement.experience}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'skills', '{{ApplicantRejectanceRequirement.skills}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'jobDescription', '{{ApplicantRejectanceRequirement.jobDescription}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'applicantName', '{{ApplicantRejectanceRequirement.applicantName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'initiatorUserName', '{{ApplicantRejectanceRequirement.initiatorUserName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementName', '{{ApplicantRejectanceRequirement.requirementName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementRate', '{{ApplicantRejectanceRequirement.requirementRate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'rejectReason', '{{ApplicantRejectanceRequirement.rejectReason}}');



INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
											('ApplicantRejectanceRequirement','Email', 'ApplicantRejectanceRequirement', 'Used mail   from  Applicant  to login user', '{{ApplicantRejectanceRequirement.applicantName}} not interested for position {{ApplicantRejectanceRequirement.requirementName}}','org', 'Best Regards,\n{{ApplicantRejectanceRequirement.applicantName}}', 'Dear {{ApplicantRejectanceRequirement.initiatorUserName}},\n\n  {{ApplicantRejectanceRequirement.applicantName}} not interested for the position {{ApplicantRejectanceRequirement.requirementName}} \n\n Reason : {{ApplicantRejectanceRequirement.rejectReason}} \n\n Exeperience : {{ApplicantRejectanceRequirement.experience}} \n\n Location : {{ApplicantRejectanceRequirement.location}} \n\n Rate :{{ApplicantRejectanceRequirement.requirementRate}}\n\n Skills  : {{ApplicantRejectanceRequirement.skills}} \n\n Job Description :  {{ApplicantRejectanceRequirement.jobDescription}} .\n','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');

											
											
											
#Applicant Rejectance Notification.

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ApplicantRejectanceRequirement', 'Notification');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'applicantName', '{{ApplicantRejectanceRequirement.applicantName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementName', '{{ApplicantRejectanceRequirement.requirementName}}');



INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
                                          ('ApplicantRejectanceRequirement','Notification','ApplicantRejectanceRequirement', 'Used mail   from  Applicant  to login user', '{{ApplicantRejectanceRequirement.applicantName}} not  interested for the position {{ApplicantRejectanceRequirement.requirementName}}', 'sourcelead', '', '{{ApplicantRejectanceRequirement.applicantName}} not interested for position {{ApplicantRejectanceRequirement.requirementName}}','N','SLADMIN', 'sourcelead', 'sourceleadIN');

                                          
                                          
                                          
#Applicant Negotiate Email.


INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('ApplicantNegotiateRequirement','Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementName', '{{ApplicantNegotiateRequirement.requirementName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'applicantName', '{{ApplicantNegotiateRequirement.applicantName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'initiatorUserName', '{{ApplicantNegotiateRequirement.initiatorUserName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementRate', '{{ApplicantNegotiateRequirement.requirementRate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'applicantRate', '{{ApplicantNegotiateRequirement.applicantRate}}');


INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
											('ApplicantNegotiateRequirement','Email', 'ApplicantNegotiateRequirement', 'Used mail   from  Applicant  to login user', 'Rate request from {{ApplicantNegotiateRequirement.applicantName}}','org', 'Best Regards,\n{{ApplicantNegotiateRequirement.applicantName}}', 'Dear {{ApplicantNegotiateRequirement.initiatorUserName}}, {{ApplicantNegotiateRequirement.applicantName}} interested for position {{ApplicantNegotiateRequirement.requirementName}}, but want negotiate rate . \n\n Requirement Rate : {{ApplicantNegotiateRequirement.requirementRate}} \n\n Applicant Rate : {{ApplicantNegotiateRequirement.applicantRate}} .\n','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');

											
											
											
#Applicant Negotiate Notification.

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ApplicantNegotiateRequirement', 'Notification');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'applicantName', '{{ApplicantNegotiateRequirement.applicantName}}');



INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
                                          ('ApplicantNegotiateRequirement','Notification','ApplicantNegotiateRequirement', 'Used mail   from  Applicant  to login user', 'Rate request from {{ApplicantNegotiateRequirement.applicantName}}', 'sourcelead', '', 'Rate request from {{ApplicantNegotiateRequirement.applicantName}}','N','SLADMIN', 'sourcelead', 'sourceleadIN');

        
		