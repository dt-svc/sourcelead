
CALL AlterTableAddColumn ('CANDIDATE','TERMINATE_REASON','VARCHAR(500) NULL AFTER `STATUS`');
CALL AlterTableAddColumn ('CANDIDATE_SKILLS','STATUS','VARCHAR(20) NULL AFTER `SCALE`');









INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('AcceptVendorInvitation', 'Notification');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'vendorOrgName', '{{inviteVendor.vendorOrgName}}');

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('AcceptClientInvitation', 'Notification');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'clientOrgName', '{{inviteClient.clientOrgName}}');

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('AcceptVendorInvitation', 'Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'AcceptVendorInvitation', '{{inviteVendor.acceptVendorInvitation}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'firstName', '{{inviteVendor.firstName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'lastName', '{{inviteVendor.lastName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'clientFirstName', '{{inviteVendor.clientFirstName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'clientLastName', '{{inviteVendor.clientLastName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'vendorOrgName', '{{inviteVendor.vendorOrgName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'userName', '{{inviteVendor.userName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'password', '{{inviteVendor.password}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'acceptURL', '{{inviteVendor.acceptURL}}');


INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('AcceptClientInvitation', 'Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'AcceptClientInvitation', '{{inviteClient.acceptClientInvitation}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'firstName', '{{inviteClient.firstName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'lastName', '{{inviteClient.lastName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'vendorFirstName', '{{inviteClient.vendorFirstName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'vendorLastName', '{{inviteClient.vendorLastName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'clientOrgName', '{{inviteClient.clientOrgName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'userName', '{{inviteClient.userName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'password', '{{inviteClient.password}}');


INSERT INTO `CLASS_FIELD` ( `ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('28', 'acceptURL', '{{inviteClient.acceptURL}}');



INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES ('Invite Vendor','Email', 'Accept Vendor Invitation', 'Accept Vendor Invitation', '{{inviteVendor.clientOrgName}} invited you as Vendor','org', 'Best Regards,\n{{inviteVendor.clientFirstName}} {{inviteVendor.clientLastName}},\n{{inviteVendor.clientOrgName}}.', 'Dear {{inviteVendor.FirstName}} {{inviteVendor.LastName}},\n\n{{inviteVendor.clientFirstName}} {{inviteVendor.clientLastName}} has invited you as Vendor for the {{inviteVendor.vendorOrgName}}. So login into the system and accept the invitation.\n\n  Username : {{inviteVendor.userName}}\n  Password : {{inviteVendor.Password}} \n\n{{inviteVendor.acceptURL}}','N', 'SLADMIN', 'slead', 'sleadIN');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES ('Invite Client','Email', 'Accept Client Invitation', 'Accept Client Invitation', '{{inviteClient.vendorOrgName}} invited you as Client','org', 'Best Regards,\n{{inviteClient.vendorFirstName}} {{inviteClient.vendorLastName}},\n{{inviteClient.vendorOrgName}}.', 'Dear {{inviteClient.FirstName}} {{inviteClient.LastName}},\n\n{{inviteClient.vendorFirstName}} {{inviteClient.vendorLastName}} has invited you as Client for the {{inviteClient.clientOrgName}}. So login into the system and accept the invitation.\n\n  Username : {{inviteClient.userName}}\n  Password : {{inviteClient.Password}} \n\n{{inviteClient.acceptURL}}','N', 'SLADMIN', 'slead', 'sleadIN');





INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES ('Accept Vendor Invitation','Notification', 'Accept Vendor Invitation', 'Accept Vendor Invitation', 'org',  'You has invited as vendor form {{inviteVendor.vendorOrgName}}','N', 'SLADMIN', 'slead', 'sleadIN');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES ('Accept Client Notification','Notification', 'Accept Client Invitation', 'Accept Client Invitation', 'org',  'You has invited as Client form {{inviteClient.clientOrgName}}','N', 'SLADMIN', 'slead', 'sleadIN');



CALL AlterTableAddColumn ('NOTIFICATIONS','USER_NOTIFICATION_STATUS','VARCHAR(25) NULL');

