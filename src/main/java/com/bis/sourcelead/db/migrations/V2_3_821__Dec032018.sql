ALTER TABLE `US_PAYROLL_SCHEDULE` ADD COLUMN `NOTIFY_BEFORE` VARCHAR(10) NULL DEFAULT NULL AFTER `STATUS`;

DROP TABLE `USPAY_SCHEDULE_NOTIFY_MEMBER`;


#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('USPayrollScheduleEmail', 'Email');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`TAB_ID`,`STATUS`,`DEFAULT_TEMPLATE`) 
	VALUES('USPayrollScheduleEmail', 'Email', 'USPayrollScheduleEmail', 'Mail should forward based on user-configured days before of Run payroll date.', 'Generate Payroll for [Pay_Period] frequency','org','Best Regards,\nSourcelead Team.', 'Dear [Payroll_Approver_Name],\nThis is to notify that your next Process Payroll is on [Run_Payroll_Date].\nPay Period : [Pay_Period]\nPay Frequency : [Pay_Frequency]\nPay Date : [Pay_Date]','N', 'SLADMIN', 'slead', 'sleadIN', 3,1,'Y');

INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'PayPeriod', '[Pay_Period]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'PayrollApproverName', '[Payroll_Approver_Name]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'RunPayrollDate', '[Run_Payroll_Date]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'PayFrequency', '[Pay_Frequency]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'PayDate', '[Pay_Date]');
