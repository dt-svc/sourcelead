set SQL_SAFE_UPDATES=0;
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'clientOrgnanizationName', '{{ClienttoVendorAcceptence.clientOrgnanizationName}}');
UPDATE `EMAIL_SCENARIO` SET `SUBJECT`= 'Congratulations!! Offer from {{ClienttoVendorAcceptence.clientOrgnanizationName}}' WHERE `UNIQUE_SCENARIO_NAME`='Client to Vendor Acceptence' and `ENTITY_TYPE`='Email';

UPDATE `EMAIL_SCENARIO` SET `BODY`= 'Congratulations!! Offer from {{ClienttoVendorAcceptence.clientOrgnanizationName}}' WHERE `UNIQUE_SCENARIO_NAME`='Client to Vendor Acceptence' and `ENTITY_TYPE`='Notification';