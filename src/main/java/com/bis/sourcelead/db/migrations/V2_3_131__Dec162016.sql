#vendor negotiate to the Requirement Rate.

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('ClientRateNegotiation','Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUser', '{{ClientRateNegotiation.loginUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'clientContact', '{{ClientRateNegotiation.clientContact}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementName', '{{ClientRateNegotiation.requirementName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginOrgName', '{{ClientRateNegotiation.loginOrgName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'candidateName', '{{ClientRateNegotiation.candidateName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'negotiationTable', '{{ClientRateNegotiation.negotiationTable}}');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
											('ClientRateNegotiation','Email', 'ClientRateNegotiation', 'Used mail from vendor to Client. ', 'New rate request from {{ClientRateNegotiation.vendorContact}} for {{ClientRateNegotiation.requirementName}}.','org', 'Best Regards,\n{{ClientRateNegotiation.loginUser}}\n{{ClientRateNegotiation.loginOrgName}}.', 'Dear {{ClientRateNegotiation.clientContact}},\n\n {{ClientRateNegotiation.loginOrgName}} requested new rate for the {{ClientRateNegotiation.requirementName}} for {{ClientRateNegotiation.candidateName}}.\n     Please find out the details,if you acknowledge with it click on the accept link.\n {{UserRateNegotiation.negotiationTable}}','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');
									
