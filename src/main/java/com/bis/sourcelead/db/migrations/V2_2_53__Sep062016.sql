CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG_SPOC','RESPONDER_LOCATION_ID','BIGINT NULL AFTER `STATUS`');
ALTER TABLE `sourcelead`.`USER` ADD FOREIGN KEY(REPORTING_MANAGER_ID) REFERENCES ORG_LOCATIONS(ID);

CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG_SPOC','INITIATOR_LOCATION_ID','BIGINT NULL AFTER `STATUS`');
ALTER TABLE `sourcelead`.`USER` ADD FOREIGN KEY(REPORTING_MANAGER_ID) REFERENCES ORG_LOCATIONS(ID);

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `BODY`='Dear {{InviteClient.FirstName}} {{InviteClient.LastName}},\n\n{{InviteClient.VendorFirstName}} {{InviteClient.VendorLastName}} have invited you as Client for the {{InviteClient.ClientOrgName}}.\n\nSo login into the system and accept the invitation.\n\n{{InviteClient.LoginURL}}\n\n Username : {{InviteClient.VendorEmailId}}\n Password : {{InviteClient.Password}}' WHERE `ID`='34';


UPDATE `sourcelead`.`ACTION_ROLES` SET `IS_NOTIFY`='Y' WHERE `ID`='54';
UPDATE `sourcelead`.`ACTION_ROLES` SET `IS_NOTIFY`='Y' WHERE `ID`='62';

CALL sourcelead.AlterTableAddColumn ('sourcelead','NOTIFICATIONS','UUID','VARCHAR(50) NULL');



CALL sourcelead.AlterTableAddColumn ('sourcelead','countries','currency','VARCHAR(45) NULL AFTER `Code2`');

UPDATE `sourcelead`.`countries` SET `currency`='AUD' WHERE `ID`='15';
UPDATE `sourcelead`.`countries` SET `currency`='INR' WHERE `ID`='100';
UPDATE `sourcelead`.`countries` SET `currency`='USD' WHERE `ID`='224';
UPDATE `sourcelead`.`countries` SET `currency`='GBP' WHERE `ID`='77';
UPDATE `sourcelead`.`countries` SET `currency`='SGD' WHERE `ID`='187';
UPDATE `sourcelead`.`countries` SET `currency`='EUR' WHERE `ID`='73';

