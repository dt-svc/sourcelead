#Accept Client.

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('AcceptClient','Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'firstName', '{{AcceptClient.firstName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'lastName', '{{AcceptClient.lastName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'vendorFirstName', '{{AcceptClient.vendorFirstName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'vendorLastName', '{{AcceptClient.vendorLastName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'vendorOrgName', '{{AcceptClient.vendorOrgName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginURL', '{{AcceptClient.loginURL}}');


INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES 
('Accept Client','Email', 'Accept Client', 'Accept Client Invitation', '{{acceptClient.vendorOrgName}} accepted your invitation as client','org', 'Best Regards,\n{{acceptClient.vendorFirstName}} {{acceptClient.vendorLastName}},\n{{acceptClient.vendorOrgName}}.', 
'Dear {{acceptClient.FirstName}} {{acceptClient.LastName}}, {{acceptClient.vendorFirstName}} {{acceptClient.vendorLastName}}accepted your invitation as Client.Login into the system for brief details.\n {{acceptClient.loginURL}}','N', 'SLADMIN', 'slead', 'sleadIN');

#Accept Vendor.

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('AcceptVendor','Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'firstName', '{{AcceptVendor.firstName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'lastName', '{{AcceptVendor.lastName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'clientFirstName', '{{AcceptVendor.clientFirstName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'clientLastName', '{{AcceptVendor.clientLastName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'clientOrgName', '{{AcceptVendor.clientOrgName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginURL', '{{AcceptVendor.loginURL}}');


INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES 
('Accept Vendor','Email', 'Accept Vendor', 'Accept Vendor Invitation', '{{acceptVendor.clientOrgName}} accepted your invitation as vendor','org', 'Best Regards,\n{{acceptVendor.clientFirstName}} {{acceptVendor.clientLastName}},\n{{acceptVendor.clientOrgName}}.', 
'Dear {{acceptVendor.FirstName}} {{acceptVendor.LastName}}, {{acceptVendor.clientFirstName}} {{acceptVendor.clientLastName}}accepted your invitation as Vendor.Login into the system for brief details.\n {{AcceptVendor.loginURL}}','N', 'SLADMIN', 'slead', 'sleadIN');

#Reject Client.

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('RejectClient','Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'firstName', '{{RejectClient.firstName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'lastName', '{{RejectClient.lastName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'vendorFirstName', '{{RejectClient.vendorFirstName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'vendorLastName', '{{RejectClient.vendorLastName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'vendorOrgName', '{{RejectClient.vendorOrgName}}');


INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES 
('Reject Client','Email', 'Reject Client', 'Reject Client Invitation', '{{rejectClient.vendorOrgName}} rejected your invitation as client','org', 'Best Regards,\n{{rejectClient.vendorFirstName}} {{rejectClient.vendorLastName}},\n{{rejectClient.vendorOrgName}}.', 
'Dear {{rejectClient.FirstName}} {{rejectClient.LastName}}, {{rejectClient.vendorFirstName}} {{rejectClient.vendorLastName}} rejected your invitation as Client.','N', 'SLADMIN', 'slead', 'sleadIN');

#Reject Vendor.

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('RejectVendor','Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'firstName', '{{RejectVendor.firstName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'lastName', '{{RejectVendor.lastName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'clientFirstName', '{{RejectVendor.clientFirstName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'clientLastName', '{{RejectVendor.clientLastName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'clientOrgName', '{{RejectVendor.clientOrgName}}');


INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES 
('Reject Vendor','Email', 'Reject Vendor', 'Reject Vendor Invitation', '{{rejectVendor.clientOrgName}} rejected your invitation as Vendor','org', 'Best Regards,\n{{rejectVendor.clientFirstName}} {{rejectVendor.clientLastName}},\n{{rejectVendor.clientOrgName}}.', 
'Dear {{rejectVendor.FirstName}} {{rejectVendor.LastName}}, {{rejectVendor.clientFirstName}} {{rejectVendor.clientLastName}} rejected your invitation as Vendor.','N', 'SLADMIN', 'slead', 'sleadIN');

