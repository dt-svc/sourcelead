#Applicant_Authorization_Acceptence_Email
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ApplicantAuthorizationManagerAcceptance', 'Email');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'applicantName', '{{ApplicantAuthorizationManagerAcceptance.applicantName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUser', '{{ApplicantAuthorizationManagerAcceptance.loginUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirmentName', '{{ApplicantAuthorizationManagerAcceptance.requirmentName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirmentLocation', '{{ApplicantAuthorizationManagerAcceptance.requirmentLocation}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginOrg', '{{ApplicantAuthorizationManagerAcceptance.loginOrg}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'assignedUser', '{{ApplicantAuthorizationManagerAcceptance.assignedUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'applicantRate', '{{ApplicantAuthorizationManagerAcceptance.applicantRate}}');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('ApplicantAuthorizationManagerAcceptance', 'Email', 'ApplicantAuthorizationManagerAcceptance', 'Send mail to Applicant Authorization', 'Your request have accepted for the {{ApplicantAuthorizationManagerAcceptance.applicantName}} to {{ApplicantAuthorizationManagerAcceptance.requirmentName}}', 'sourcelead', 'Best Regards,\n{{ApplicantAuthorizationManagerAcceptance.loginUser}}\n{{ApplicantAuthorizationManagerAcceptance.loginOrg}}','Dear {{ApplicantAuthorizationManagerAcceptance.assignedUser}},\n\nI have accepted the {{ApplicantAuthorizationManagerAcceptance.applicantName}} for {{ApplicantAuthorizationManagerAcceptance.requirmentName}}.\nYou can go for further process.\nThe brief details are below\nRequiment Location: {{ApplicantAuthorizationManagerAcceptance.requirmentLocation}}\nApplicant Rate:  {{ApplicantAuthorizationManagerAcceptance.applicantRate}}', 'N', 'SLADMIN', 'com','in.com');


#Applicant_Authorization_Acceptence_Notification
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ApplicantAuthorizationManagerAcceptance', 'Notification');


#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('ApplicantAuthorizationManagerAcceptance', 'Notification', 'ApplicantAuthorizationManagerAcceptance', 'Send mail to Applicant Authorization','org','Your Request have accepted for the {{ApplicantAuthorizationManagerAcceptance.applicantName}} to {{ApplicantAuthorizationManagerAcceptance.requirmentName}} ','N', 'SLADMIN', 'com', 'in.com');

