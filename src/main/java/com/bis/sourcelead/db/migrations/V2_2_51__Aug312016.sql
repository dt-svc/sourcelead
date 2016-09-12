
CALL sourcelead.AlterTableAddColumn ('sourcelead','CANDIDATE_EDUCATION','STATUS','VARCHAR(10) NULL AFTER `ADDRESS`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','CANDIDATE_CERTIFICATIONS','STATUS',' VARCHAR(10) NULL AFTER `DESCRIPTION`');



INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('33', 'loginURL', '{{InviteVendor.LoginURL}}');

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `BODY`='Dear {{InviteVendor.FirstName}} {{InviteVendor.LastName}},\n\n{{InviteVendor.ClientFirstName}} {{InviteVendor.ClientLastName}} have invited you as Vendor for the {{InviteVendor.ClientOrgName}}.\n\nSo login into the system and accept the invitation.\n\n{{InviteVendor.LoginURL}}\n\n Username : {{InviteVendor.VendorEmailId}}\n Password : {{InviteVendor.Password}}' WHERE `ID`='33';

INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('34', 'loginURL', '{{InviteClient.LoginURL}}');

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `BODY`='Dear {{InviteClient.FirstName}} {{InviteClient.LastName}},\n\n{{InviteClient.ClientFirstName}} {{InviteClient.ClientLastName}} have invited you as Client for the {{InviteClient.ClientOrgName}}.\n\nSo login into the system and accept the invitation.\n\n{{InviteClient.LoginURL}}\n\n Username : {{InviteClient.VendorEmailId}}\n Password : {{InviteClient.Password}}' WHERE `ID`='34';