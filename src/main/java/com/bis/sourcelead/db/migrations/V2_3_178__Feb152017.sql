
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('80', 'negotiationTable', '{{UserAcceptRateNegotiation.negotiationTable}}');
											
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{UserAcceptRateNegotiation.applicantName}},\n\n We are the finalizing rate for the {{UserAcceptRateNegotiation.requirementName}}  after your acknowledgment.\n\n Finalized Rate  : {{UserAcceptRateNegotiation.finalRate}}\n {{UserAcceptRateNegotiation.negotiationTable}}' WHERE `ID`='83';


INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('ApplicantAcceptRate','Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'applicantName', '{{ApplicantAcceptRate.applicantName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementName', '{{ApplicantAcceptRate.requirementName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'finalRate', '{{ApplicantAcceptRate.finalRate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'userName', '{{ApplicantAcceptRate.userName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'negotiationTable', '{{ApplicantAcceptRate.negotiationTable}}');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
											('ApplicantAcceptRate','Email', 'ApplicantAcceptRate', 'Used mail when Appplicant accepts rate', '{{ApplicantAcceptRate.applicantName}} accepted Rate for {{ApplicantAcceptRate.requirementName}}','org', 'Best Regards,\n{{ApplicantAcceptRate.applicantName}}', 'Dear {{ApplicantAcceptRate.userName}},\n\n {{ApplicantAcceptRate.applicantName}} accepted Rate for the {{UserAcceptRateNegotiation.requirementName}}.\n\n Finalized Rate  : {{UserAcceptRateNegotiation.finalRate}}\n{{ApplicantAcceptRate.negotiationTable}}','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');
