
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('InviteVendor','Email');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'firstName', '{{InviteVendor.FirstName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'lastName', '{{InviteVendor.LastName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'clientOrgName', '{{InviteVendor.ClientOrgName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'clientFirstName', '{{InviteVendor.ClientFirstName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'clientLastName', '{{InviteVendor.ClientLastName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'vendorEmailId', '{{InviteVendor.VendorEmailId}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'password', '{{InviteVendor.Password}}');


INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES ('Invite Vendor','Email', 'Invite Vendor', 'Used for Invite Vendor', '{{InviteVendor.ClientOrgName}} invited you as Vendor','org', 'Best Regards,\n{{InviteVendor.ClientFirstName}} {{InviteVendor.ClientLastName}},\n{{InviteVendor.ClientOrgName}}.', 'Dear {{InviteVendor.FirstName}} {{InviteVendor.LastName}},\n\n{{InviteVendor.ClientFirstName}} {{InviteVendor.ClientLastName}} have invited you as Vendor for the {{InviteVendor.ClientOrgName}}. So login into the system and accept the invitation.\n\n  Username : {{InviteVendor.VendorEmailId}}\n  Password : {{InviteVendor.Password}}','N', 'SLADMIN', 'slead', 'sleadIN');


INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('InviteClient','Email');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'firstName', '{{InviteClient.FirstName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'lastName', '{{InviteClient.LastName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'vendorOrgName', '{{InviteClient.VendorOrgName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'vendorFirstName', '{{InviteClient.VendorFirstName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'vendorLastName', '{{InviteClient.VendorLastName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'clientEmailId', '{{InviteClient.ClientEmailId}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'password', '{{InviteClient.Password}}');


INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES ('Invite Client','Email', 'Invite Client', 'Used for Invite Client', '{{InviteClient.VendorOrgName}} invited you as Client','org', 'Best Regards,\n{{InviteClient.VendorFirstName}} {{InviteClient.VendorLastName}},\n{{InviteClient.VendorOrgName}}.', 'Dear {{InviteClient.FirstName}} {{InviteClient.LastName}},\n\n{{InviteClient.VendorFirstName}} {{InviteClient.VendorLastName}} have invited you as Client for the {{InviteClient.VendorOrgName}}. So login into the system and accept the invitation.\n\n  Username : {{InviteClient.ClientEmailId}}\n  Password : {{InviteClient.Password}}','N', 'SLADMIN', 'slead', 'sleadIN');