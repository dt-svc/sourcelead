#Client negotiate to the Requirement Rate.

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('VendorRateNegotiation','Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUser', '{{VendorRateNegotiation.loginUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'vendorContact', '{{VendorRateNegotiation.vendorContact}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementName', '{{VendorRateNegotiation.requirementName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginOrgName', '{{VendorRateNegotiation.loginOrgName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'candidateName', '{{VendorRateNegotiation.candidateName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'negotiationTable', '{{VendorRateNegotiation.negotiationTable}}');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
											('VendorRateNegotiation','Email', 'VendorRateNegotiation', 'Used mail from Client to Vendor. ', 'New rate request from {{VendorRateNegotiation.loginUser}} for {{VendorRateNegotiation.requirementName}}.','org', 'Best Regards,\n{{VendorRateNegotiation.loginUser}}\n{{VendorRateNegotiation.loginOrgName}}.', 'Dear {{VendorRateNegotiation.vendorContact}},\n\n {{VendorRateNegotiation.loginOrgName}} requested new rate for the {{VendorRateNegotiation.requirementName}} for {{VendorRateNegotiation.candidateName}}.\n     Please find out the details,if you acknowledge with it click on the accept link.\n {{VendorRateNegotiation.negotiationTable}}','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');
				
											
UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='New rate request from {{ClientRateNegotiation.loginUser}} for {{ClientRateNegotiation.requirementName}}.' WHERE `ID`='80';

