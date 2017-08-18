
DELETE FROM `sourcelead`.`module_actions` WHERE `ACTION_ID`='15';
DELETE FROM `sourcelead`.`module_actions` WHERE `ACTION_ID`='37';

DELETE FROM `sourcelead`.`sourcelead_actions` WHERE `ID`='15';
UPDATE `sourcelead`.`sourcelead_actions` SET `ACTION_NAME`='Import Client & Contact' WHERE `ID`='10';

DELETE FROM `sourcelead`.`sourcelead_actions` WHERE `ID`='37';
UPDATE `sourcelead`.`sourcelead_actions` SET `ACTION_NAME`='Import Vendor & Contact' WHERE `ID`='32';
