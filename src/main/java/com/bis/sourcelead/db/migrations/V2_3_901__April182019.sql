ALTER TABLE `CANDIDATE` ADD COLUMN `SSN_NUMBER` VARCHAR(20) NULL DEFAULT NULL AFTER `PAN_NUMBER`;
			
INSERT INTO `sourcelead`.`sourcelead_form_fields` (`SECTION_ID`, `LABEL_NAME`, `FIELD_NAME`, `FIELD_TYPE`, `IS_FIELD_REQUIRED`, `STATUS`, `IS_IMPORT_FIELD`) VALUES ('8', 'Pan Number', 'panNumber', 'TextBox', '0', '1', '1');
			
INSERT INTO `sourcelead`.`sourcelead_form_fields` (`SECTION_ID`, `LABEL_NAME`, `FIELD_NAME`, `FIELD_TYPE`, `IS_FIELD_REQUIRED`, `STATUS`, `IS_IMPORT_FIELD`) VALUES ('8', 'Aadhar Number', 'aadharNumber', 'TextBox', '0', '1', '1');
			
INSERT INTO `sourcelead`.`sourcelead_form_fields` (`SECTION_ID`, `LABEL_NAME`, `FIELD_NAME`, `FIELD_TYPE`, `IS_FIELD_REQUIRED`, `STATUS`, `IS_IMPORT_FIELD`) VALUES ('8', 'SSN Number', 'ssnNumber', 'TextBox', '0', '1', '1');