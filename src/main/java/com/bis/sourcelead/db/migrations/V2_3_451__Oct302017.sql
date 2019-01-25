#applicant offer reject Mail
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ApplicantRejectOffer', 'Email');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'applicantName', '{{ApplicantRejectOffer.applicantName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'orgUser', '{{ApplicantRejectOffer.orgUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'jobTitle', '{{ApplicantRejectOffer.jobTitle}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'reason', '{{ApplicantRejectOffer.reason}}');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('ApplicantRejectOffer', 'Email', 'ApplicantRejectOffer','Used when applicant reject offer', '{{ApplicantRejectOffer.applicantName}} rejected the offer.','sourcelead','Thank You,\n{{ApplicantAcceptOffer.applicantName}}','Dear {{ApplicantRejectOffer.orgUser}},\n\nI regret to inform you that  I am rejecting Your Offer due to {{ApplicantRejectOffer.reason}} for the following position.\nJob ID/ Title /Project Name : {{ApplicantAcceptOffer.jobTitle}}', 'N', 'SLADMIN', 'com','in.com');

#applicant offer accept notification
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ApplicantRejectOffer', 'Notification');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
('ApplicantRejectOffer', 'Notification', 'ApplicantRejectOffer', 'Used when applicant reject offer','org','{{ApplicantRejectOffer.applicantName}} rejected the offer for {{ApplicantAcceptOffer.jobTitle}}','N', 'SLADMIN', 'com', 'in.com');
