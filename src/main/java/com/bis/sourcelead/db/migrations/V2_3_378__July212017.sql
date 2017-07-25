SET SQL_SAFE_UPDATES = 0;	
UPDATE `sourcelead`.`CLASS_FIELD` SET `ENTITY_TYPE_ID`=(SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'AcceptClient' and `ENTITY_TYPE` = 'Email') WHERE DELIM_STR = '{{acceptClient.signature}}' and FIELD_NAME='signature';

UPDATE `sourcelead`.`CLASS_FIELD` SET `ENTITY_TYPE_ID`=(SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'AcceptVendor' and `ENTITY_TYPE` = 'Email') WHERE DELIM_STR = '{{acceptVendor.signature}}' and FIELD_NAME='signature';

DELETE FROM `sourcelead`.`CLASS_FIELD` WHERE  `ID`=993;
DELETE FROM `sourcelead`.`CLASS_FIELD` WHERE  `ID`=992;
DELETE FROM `sourcelead`.`CLASS_FIELD` WHERE  `ID`=991;
DELETE FROM `sourcelead`.`CLASS_FIELD` WHERE  `ID`=990;
DELETE FROM `sourcelead`.`CLASS_FIELD` WHERE  `ID`=989;
SET SQL_SAFE_UPDATES = 1;	

