
UPDATE `sourcelead`.`email_scenario` SET `BODY`='You has invited as vendor from {{InviteVendor.clientOrgName}}' WHERE `ID`='37';
UPDATE `sourcelead`.`email_scenario` SET `BODY`='You has invited as Client from {{inviteClient.vendorOrgName}}' WHERE `ID`='38';

CALL sourcelead.AlterTableAddColumn ('sourcelead','REQUIREMENT_INTERVIEW','INTERVIEW_SUBMISSION_TYPE','VARCHAR(250) NULL  AFTER `SUBMISSION_ID`');
