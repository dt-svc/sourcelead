CALL AlterTableAddColumn ('ORG_SPOC','RESPONDER_LOCATION_ID','BIGINT NULL AFTER `STATUS`');
ALTER TABLE `ORG_SPOC` ADD FOREIGN KEY(RESPONDER_LOCATION_ID) REFERENCES ORG_LOCATIONS(ID);

CALL AlterTableAddColumn ('ORG_SPOC','INITIATOR_LOCATION_ID','BIGINT NULL AFTER `STATUS`');
ALTER TABLE `ORG_SPOC` ADD FOREIGN KEY(RESPONDER_LOCATION_ID) REFERENCES ORG_LOCATIONS(ID);

UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{InviteClient.FirstName}} {{InviteClient.LastName}},\n\n{{InviteClient.VendorFirstName}} {{InviteClient.VendorLastName}} have invited you as Client for the {{InviteClient.ClientOrgName}}.\n\nSo login into the system and accept the invitation.\n\n{{InviteClient.LoginURL}}\n\n Username : {{InviteClient.VendorEmailId}}\n Password : {{InviteClient.Password}}' WHERE `ID`='34';


UPDATE `ACTION_ROLES` SET `IS_NOTIFY`='Y' WHERE `ID`='54';
UPDATE `ACTION_ROLES` SET `IS_NOTIFY`='Y' WHERE `ID`='62';

CALL AlterTableAddColumn ('NOTIFICATIONS','UUID','VARCHAR(50) NULL');



CALL AlterTableAddColumn ('countries','currency','VARCHAR(45) NULL AFTER `Code2`');

UPDATE `countries` SET `currency`='AUD' WHERE `ID`='15';
UPDATE `countries` SET `currency`='INR' WHERE `ID`='100';
UPDATE `countries` SET `currency`='USD' WHERE `ID`='224';
UPDATE `countries` SET `currency`='GBP' WHERE `ID`='77';
UPDATE `countries` SET `currency`='SGD' WHERE `ID`='187';
UPDATE `countries` SET `currency`='EUR' WHERE `ID`='73';

