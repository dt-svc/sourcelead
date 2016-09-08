INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ID`, `ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('35', 'AcceptVendorInvitation', 'Notification');
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ID`, `ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('36', 'AcceptClientInvitation', 'Notification');
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ID`, `ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('37', 'AcceptVendorInvitation', 'Email');
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ID`, `ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('38', 'AcceptClientInvitation', 'Email');



INSERT INTO `sourcelead`.`CLASS_FIELD` (`ID`, `ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('129', '35', 'vendorOrgName', '{{inviteVendor.vendorOrgName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ID`, `ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('130', '36', 'clientOrgName', '{{inviteClient.clientOrgName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ID`, `ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('131', '37', 'AcceptVendorInvitation', '{{inviteVendor.acceptVendorInvitation}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ID`, `ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('132', '38', 'AcceptClientInvitation', '{{inviteClient.acceptClientInvitation}}');



INSERT INTO `sourcelead`.`CLASS_FIELD` (`ID`, `ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('133', '37', 'firstName', '{{inviteVendor.firstName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ID`, `ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('134', '37', 'lastName', '{{inviteVendor.lastName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ID`, `ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('135', '37', 'clientFirstName', '{{inviteVendor.clientFirstName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ID`, `ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('136', '37', 'clientLastName', '{{inviteVendor.clientLastName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ID`, `ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('137', '37', 'vendorOrgName', '{{inviteVendor.vendorOrgName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ID`, `ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('138', '37', 'userName', '{{inviteVendor.userName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ID`, `ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('139', '37', 'password', '{{inviteVendor.password}}');


INSERT INTO `sourcelead`.`CLASS_FIELD` (`ID`, `ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('140', '38', 'firstName', '{{inviteClient.firstName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ID`, `ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('141', '38', 'lastName', '{{inviteClient.lastName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ID`, `ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('142', '38', 'vendorFirstName', '{{inviteClient.vendorFirstName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ID`, `ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('143', '38', 'vendorLastName', '{{inviteClient.vendorLastName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ID`, `ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('144', '38', 'clientOrgName', '{{inviteClient.clientOrgName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ID`, `ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('145', '38', 'userName', '{{inviteClient.userName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ID`, `ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('146', '38', 'password', '{{inviteClient.password}}');

INSERT INTO `sourcelead`.`CLASS_FIELD` (`ID`, `ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('147', '37', 'acceptURL', '{{inviteVendor.acceptURL}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ID`, `ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('148', '28', 'acceptURL', '{{inviteClient.acceptURL}}');



INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES ('Invite Vendor','Email', 'Accept Vendor Invitation', 'Accept Vendor Invitation', '{{inviteVendor.clientOrgName}} invited you as Vendor','org', 'Best Regards,\n{{inviteVendor.clientFirstName}} {{inviteVendor.clientLastName}},\n{{inviteVendor.clientOrgName}}.', 'Dear {{inviteVendor.FirstName}} {{inviteVendor.LastName}},\n\n{{inviteVendor.clientFirstName}} {{inviteVendor.clientLastName}} has invited you as Vendor for the {{inviteVendor.vendorOrgName}}. So login into the system and accept the invitation.\n\n  Username : {{inviteVendor.userName}}\n  Password : {{inviteVendor.Password}} \n\n{{inviteVendor.acceptURL}}','N', 'SLADMIN', 'slead', 'sleadIN');

INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES ('Invite Client','Email', 'Accept Client Invitation', 'Accept Client Invitation', '{{inviteClient.vendorOrgName}} invited you as Client','org', 'Best Regards,\n{{inviteClient.vendorFirstName}} {{inviteClient.vendorLastName}},\n{{inviteClient.vendorOrgName}}.', 'Dear {{inviteClient.FirstName}} {{inviteClient.LastName}},\n\n{{inviteClient.vendorFirstName}} {{inviteClient.vendorLastName}} has invited you as Client for the {{inviteClient.clientOrgName}}. So login into the system and accept the invitation.\n\n  Username : {{inviteClient.userName}}\n  Password : {{inviteClient.Password}} \n\n{{inviteClient.acceptURL}}','N', 'SLADMIN', 'slead', 'sleadIN');





INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES ('Accept Vendor Invitation','Notification', 'Accept Vendor Invitation', 'Accept Vendor Invitation', 'org',  'You has invited as vendor form {{inviteVendor.vendorOrgName}}','N', 'SLADMIN', 'slead', 'sleadIN');

INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES ('Accept Client Notification','Notification', 'Accept Client Invitation', 'Accept Client Invitation', 'org',  'You has invited as Client form {{inviteClient.clientOrgName}}','N', 'SLADMIN', 'slead', 'sleadIN');



CALL sourcelead.AlterTableAddColumn ('sourcelead','NOTIFICATIONS','USER_NOTIFICATION_STATUS','VARCHAR(25) NULL');

