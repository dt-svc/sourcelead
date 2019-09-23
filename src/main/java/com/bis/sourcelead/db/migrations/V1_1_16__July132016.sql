CALL AlterTableAddColumn ('TIMESHEET_SUBMISSIONS','SUBMISSION_APPROVALSTATUS','VARCHAR(50) NULL AFTER `SUBMISSION_STATUS`');
CALL AlterTableAddColumn ('TIMESHEET_SUBMISSIONS','LEVEL_OF_APPROVER','INT NULL AFTER `SUBMISSION_APPROVALSTATUS`');
CALL AlterTableAddColumn ('TIMESHEET_SUBMISSIONS','APPROVER_USER_ID','BIGINT NULL AFTER `SUBMISSION_APPROVALSTATUS`');
ALTER TABLE `TIMESHEET_SUBMISSIONS` ADD FOREIGN KEY(APPROVER_USER_ID) REFERENCES USER(id);

INSERT INTO `ROLES` (`ID`, `ROLE_NAME`) VALUES ('3', 'ROLE_APPROVER');

#ALTER TABLE `EMPLOYEE`
#ADD COLUMN `EMPLOYEE_ID` VARCHAR(20) NULL AFTER `USER_ID`;


#ALTER TABLE `EMPLOYEE`
#ADD COLUMN `RATE` VARCHAR(20) NULL AFTER `UPDATED_BY`,
#ADD COLUMN `RATE_PER` VARCHAR(20) NULL AFTER `RATE`,
#ADD COLUMN `DAILY_HOURS` VARCHAR(20) NULL AFTER `RATE_PER`;

#INSERT INTO `ROLES` (`ID`, `ROLE_NAME`) VALUES ('3', 'ROLE_APPROVER');


CALL AlterTableAddColumn ('EMPLOYEE','EMPLOYEE_ID','VARCHAR(20) NULL AFTER `USER_ID`');

CALL AlterTableAddColumn ('EMPLOYEE','RATE','VARCHAR(20) NULL AFTER `UPDATED_BY`');

CALL AlterTableAddColumn ('EMPLOYEE','RATE_PER','VARCHAR(20) NULL AFTER `RATE`');

CALL AlterTableAddColumn ('EMPLOYEE','DAILY_HOURS','VARCHAR(20) NULL AFTER `RATE_PER`');

CALL AlterTableAddColumn ('USER','IS_MAIL_SENT','VARCHAR(3) NULL AFTER `STATUS`');
CALL AlterTableAddColumn ('PROJECTS','IS_PRIMARY','INT(2) NULL AFTER `STATUS`');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('4', 'loginURL', '{{InviteUser.LoginURL}}');

UPDATE `EMAIL_SCENARIO` SET `SIGNATURE`='Best Regards,\n{{InviteUser.AdministratorFirstName}} {{InviteUser.AdministratorLastName}}',
`SUBJECT`='{{InviteUser.FirstName}} {{InviteUser.LastName}} invited to SourceLead' ,
`BODY`='Hi {{InviteUser.FirstName}} {{InviteUser.LastName}},\n\n{{InviteUser.AdministratorFirstName}} {{InviteUser.AdministratorLastName}} invited you to SourceLead as a {{InviteUser.Role}}.\n\nYou can simply login into SourceLead by clicking below link with follow credentials\n\n{{InviteUser.LoginURL}}\n\nLogin Username : {{InviteUser.UserEmailId}}\n\nPassword : {{InviteUser.Password}}' WHERE  `ID`=4;