UPDATE `ACTION_ROLES` SET `IS_NOTIFY`='Y' WHERE `ID`='41';

UPDATE `EMAIL_SCENARIO` SET `BODY`='You has invited as vendor from {{inviteVendor.vendorOrgName}}' WHERE `ID`='37';
UPDATE `EMAIL_SCENARIO` SET `BODY`='You has invited as Client from {{inviteClient.clientOrgName}}' WHERE `ID`='38';


