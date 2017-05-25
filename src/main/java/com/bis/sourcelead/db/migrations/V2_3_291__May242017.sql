CALL sourcelead.AlterTableAddColumn ('sourcelead','INVOICE','CURRENCY_CODE','VARCHAR(10) NULL  AFTER `TOTAL`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','INVOICE_PAYMENT','CURRENCY_CODE','VARCHAR(10) NULL  AFTER `PAYMENT_AMOUNT`');

set SQL_SAFE_UPDATES=0;
UPDATE `sourcelead`.`SOURCELEAD_ACTIONS` SET `ACTION_NAME`='Add Associates Into Hotlist' WHERE `ID`='184';
UPDATE `sourcelead`.`SOURCELEAD_ACTIONS` SET `ACTION_NAME`='View & Edit Profile Associate' WHERE `ID`='163';
UPDATE `sourcelead`.`SOURCELEAD_ACTIONS` SET `ACTION_NAME`='View & Edit Profile Candidate' WHERE `ID`='75';

