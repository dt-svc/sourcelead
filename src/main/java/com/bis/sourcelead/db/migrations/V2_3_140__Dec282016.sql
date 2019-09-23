
#User accept rate negotiation of the Requirement.

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('UserAcceptRateNegotiation','Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'applicantName', '{{UserAcceptRateNegotiation.applicantName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementName', '{{UserAcceptRateNegotiation.requirementName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'finalRate', '{{UserAcceptRateNegotiation.finalRate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'userName', '{{UserAcceptRateNegotiation.userName}}');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
											('UserAcceptRateNegotiation','Email', 'UserAcceptRateNegotiation', 'Used mail when user accepts rate', 'Rate Finalized for {{UserAcceptRateNegotiation.requirementName}}','org', 'Best Regards,\n{{UserAcceptRateNegotiation.userName}}', 'Dear {{UserAcceptRateNegotiation.applicantName}},\n\n We are the finalizing rate for the {{UserAcceptRateNegotiation.requirementName}}  after your acknowledgment.\n\n Finalized Rate  : {{UserAcceptRateNegotiation.finalRate}}\n','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');

											