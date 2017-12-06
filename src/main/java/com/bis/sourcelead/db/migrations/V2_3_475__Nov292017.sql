
ALTER TABLE `sourcelead`.`USER_ROLE` 
ADD COLUMN `URL_SENTTIME` VARCHAR(10) NULL DEFAULT NULL AFTER `IS_MAIL_SENT`;
ADD COLUMN `LOGIN_SECURITY` VARCHAR(10) NULL DEFAULT NULL AFTER `URL_SENTTIME`;

ALTER TABLE `sourcelead`.`USER` 
ADD COLUMN `VERIFICATION_UUID` VARCHAR(45) NULL DEFAULT NULL AFTER `FILE_URL`,
ADD COLUMN `IS_VERIFIED` VARCHAR(10) NULL DEFAULT NULL AFTER `VERIFICATION_UUID`,
ADD COLUMN `URL_SENTTIME` DATETIME NULL DEFAULT NULL AFTER `IS_VERIFIED`;



ALTER TABLE `sourcelead`.`ORG_LOCATIONS` 
ADD COLUMN `LOGIN_SECURITY` VARCHAR(10) NULL DEFAULT 'N' AFTER `ORG_LOCATION_BILLING_ADDRESS`;


ALTER TABLE `sourcelead`.`USER_LOGIN_LOGOUT_SESSION` 
ADD COLUMN `IS_AUTHENTICATED` VARCHAR(10) NULL DEFAULT 'N' AFTER `LOGOUTIME`;

ALTER TABLE `sourcelead`.`USER` 
ADD COLUMN `LOGIN_SECURITY` VARCHAR(10) NULL DEFAULT 'N' AFTER `URL_SENTTIME`;



#Activate User EMail
#entity_type
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ActivateUserEmail', 'Email');

#CLASS_FIELD
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'registerName', '{{ActivateUserEmail.registerName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'verifyLink', '{{ActivateUserEmail.verifyLink}}');

#email_scenario
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('ActivateUserEmail', 'Email', 'ActivateUserEmail','Used to activate the Account.', 'Sourcelead – Account Activation','sourcelead','Best Regards,\nSourcelead Team.','Dear {{ActivateUserEmail.registerName}},\n\nThank you for registering with us. To verify your Sourcelead account, kindly click on the verify registration link below.\n\n {{ActivateUserEmail.verifyLink}}', 'N', 'SLADMIN', 'sourceLead.com','in.sourceLead.com');



#Login OTP EMail
#entity_type
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('LoginOTPEmail', 'Email');

#CLASS_FIELD
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'userName', '{{LoginOTPEmail.userName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'token', '{{LoginOTPEmail.token}}');

#email_scenario
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('LoginOTPEmail', 'Email', 'LoginOTPEmail','Used to send OTP to User.', 'Sourcelead – Login Alert','sourcelead','Best Regards,\nSourcelead Team.','Dear {{LoginOTPEmail.userName}},\n\nHere is your One Time Password to login into the system {{LoginOTPEmail.token}}', 'N', 'SLADMIN', 'sourceLead.com','in.sourceLead.com');

