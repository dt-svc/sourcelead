SET SQL_SAFE_UPDATES = 0;	
UPDATE `sourcelead`.`CLASS_FIELD` SET `ENTITY_TYPE_ID`=(SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'AcceptClient' and `ENTITY_TYPE` = 'Email') WHERE DELIM_STR = '{{acceptClient.signature}}' and FIELD_NAME='signature';

UPDATE `sourcelead`.`CLASS_FIELD` SET `ENTITY_TYPE_ID`=(SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'AcceptVendor' and `ENTITY_TYPE` = 'Email') WHERE DELIM_STR = '{{acceptVendor.signature}}' and FIELD_NAME='signature';

