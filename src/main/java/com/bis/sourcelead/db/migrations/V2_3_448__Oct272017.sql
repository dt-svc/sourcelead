#applicant offer accept Mail
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ApplicantAcceptOffer', 'Email');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'applicantName', '{{ApplicantAcceptOffer.applicantName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'orgUser', '{{ApplicantAcceptOffer.orgUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'jobTitle', '{{ApplicantAcceptOffer.jobTitle}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'joinDate', '{{ApplicantAcceptOffer.joinDate}}');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('ApplicantAcceptOffer', 'Email', 'ApplicantAcceptOffer','Used when applicant accept offer', '{{ApplicantAcceptOffer.applicantName}} accepted the offer.','sourcelead','Thank You,\n{{ApplicantAcceptOffer.applicantName}}','Dear {{ApplicantAcceptOffer.orgUser}},\n\nGreat News {{ApplicantAcceptOffer.applicantName}} accepted Your Offer. \nPlease find the details below\nJob ID/ Title /Project Name : {{ApplicantAcceptOffer.jobTitle}}\nJoin Date :  {{ApplicantAcceptOffer.joinDate}}\nPlease start the onboarding process', 'N', 'SLADMIN', 'com','in.com');

#applicant offer accept notification
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ApplicantAcceptOffer', 'Notification');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
('ApplicantAcceptOffer', 'Notification', 'ApplicantAcceptOffer', 'Used when applicant accept offer','org','{{ApplicantAcceptOffer.applicantName}} accepted the offer for {{ApplicantAcceptOffer.jobTitle}}','N', 'SLADMIN', 'com', 'in.com');
