CALL sourcelead.AlterTableAddColumn ('sourcelead','USER','IS_MAIL_SENT','VARCHAR(3) NULL AFTER `STATUS`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','PROJECTS','IS_PRIMARY','INT(2) NULL AFTER `STATUS`');

INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('4', 'loginURL', '{{InviteUser.LoginURL}}');

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `SIGNATURE`='Best Regards,\n{{InviteUser.AdministratorFirstName}} {{InviteUser.AdministratorLastName}}',
`SUBJECT`='{{InviteUser.FirstName}} {{InviteUser.LastName}} invited to SourceLead' ,
`BODY`='Hi {{InviteUser.FirstName}} {{InviteUser.LastName}},\n\n{{InviteUser.AdministratorFirstName}} {{InviteUser.AdministratorLastName}} invited you to SourceLead as a {{InviteUser.Role}}.\n\nYou can simply login into SourceLead by clicking below link with follow credentials\n\n{{InviteUser.LoginURL}}\n\nLogin Username : {{InviteUser.UserEmailId}}\n\nPassword : {{InviteUser.Password}}' WHERE  `ID`=4;