CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG_SPOC','RESPONDER_LOCATION_ID','BIGINT NULL AFTER `STATUS`');
ALTER TABLE `sourcelead`.`USER` ADD FOREIGN KEY(REPORTING_MANAGER_ID) REFERENCES ORG_LOCATIONS(ID);

CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG_SPOC','INITIATOR_LOCATION_ID','BIGINT NULL AFTER `STATUS`');
ALTER TABLE `sourcelead`.`USER` ADD FOREIGN KEY(REPORTING_MANAGER_ID) REFERENCES ORG_LOCATIONS(ID);

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `BODY`='Dear {{InviteClient.FirstName}} {{InviteClient.LastName}},\n\n{{InviteClient.VendorFirstName}} {{InviteClient.VendorLastName}} have invited you as Client for the {{InviteClient.ClientOrgName}}.\n\nSo login into the system and accept the invitation.\n\n{{InviteClient.LoginURL}}\n\n Username : {{InviteClient.VendorEmailId}}\n Password : {{InviteClient.Password}}' WHERE `ID`='34';


UPDATE `sourcelead`.`ACTION_ROLES` SET `IS_NOTIFY`='Y' WHERE `ID`='54';
UPDATE `sourcelead`.`ACTION_ROLES` SET `IS_NOTIFY`='Y' WHERE `ID`='62';
