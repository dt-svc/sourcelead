
INSERT INTO `sourcelead`.`ROLES` (`ID`, `ROLE_NAME`) VALUES ('3', 'ROLE_APPROVER');

ALTER TABLE `sourcelead`.`EMPLOYEE`
ADD COLUMN `EMPLOYEE_ID` VARCHAR(20) NULL AFTER `USER_ID`;


ALTER TABLE `sourcelead`.`EMPLOYEE`
ADD COLUMN `RATE` VARCHAR(20) NULL AFTER `UPDATED_BY`,
ADD COLUMN `RATE_PER` VARCHAR(20) NULL AFTER `RATE`,
ADD COLUMN `DAILY_HOURS` VARCHAR(20) NULL AFTER `RATE_PER`;


CALL sourcelead.AlterTableAddColumn ('sourcelead','USER','IS_MAIL_SENT','VARCHAR(3) NULL AFTER `STATUS`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','PROJECTS','IS_PRIMARY','INT(2) NULL AFTER `STATUS`');

INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('4', 'loginURL', '{{InviteUser.LoginURL}}');

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `SIGNATURE`='Best Regards,\n{{InviteUser.AdministratorFirstName}} {{InviteUser.AdministratorLastName}}',
`SUBJECT`='{{InviteUser.FirstName}} {{InviteUser.LastName}} invited to SourceLead' ,
`BODY`='Hi {{InviteUser.FirstName}} {{InviteUser.LastName}},\n\n{{InviteUser.AdministratorFirstName}} {{InviteUser.AdministratorLastName}} invited you to SourceLead as a {{InviteUser.Role}}.\n\nYou can simply login into SourceLead by clicking below link with follow credentials\n\n{{InviteUser.LoginURL}}\n\nLogin Username : {{InviteUser.UserEmailId}}\n\nPassword : {{InviteUser.Password}}' WHERE  `ID`=4;