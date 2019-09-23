
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{ApplicantNegotiateRequirement.initiatorUserName}}, \n\n  {{ApplicantNegotiateRequirement.applicantName}} requested new rate for the {{ApplicantNegotiateRequirement.requirementName}},Please find out the details,acknowledge for rate . \n{{ApplicantNegotiateRequirement.negotiationTable}} \n' WHERE `ID`='73';


INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('70', 'negotiationTable', '{{ApplicantNegotiateRequirement.negotiationTable}}');



#User rate negotiation of the Requirement.

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('UserRateNegotiation','Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'applicantName', '{{UserRateNegotiation.applicantName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementName', '{{UserRateNegotiation.requirementName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'organizationName', '{{UserRateNegotiation.organizationName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'userName', '{{UserRateNegotiation.userName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'negotiationTable', '{{UserRateNegotiation.negotiationTable}}');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
											('UserRateNegotiation','Email', 'UserRateNegotiation', 'Used mail from user to Applicant  ', 'New Rate proposed by {{UserRateNegotiation.userName}}','org', 'Best Regards,\n{{UserRateNegotiation.userName}}', 'Dear {{UserRateNegotiation.applicantName}},\n\n {{UserRateNegotiation.userName}} proposed new rate for the Position {{UserRateNegotiation.requirementName}} at {{UserRateNegotiation.organizationName}}\n {{UserRateNegotiation.negotiationTable}}','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');

											