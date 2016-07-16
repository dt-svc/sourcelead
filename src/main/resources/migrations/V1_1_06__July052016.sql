DROP TABLE IF EXISTS sourcelead.ENTITY_TYPE;
CREATE TABLE `sourcelead`.ENTITY_TYPE (
  `ID` BIGINT unsigned NOT NULL AUTO_INCREMENT,
  `ENTITY_NAME` varchar(255) DEFAULT NULL,
  `ENTITY_TYPE` varchar(50) NULL,
  PRIMARY KEY (`ID`),
  KEY `ID` (`ID`)
) ;


DROP TABLE IF EXISTS sourcelead.CLASS_FIELD;
CREATE TABLE `sourcelead`.CLASS_FIELD (
  `ID` BIGINT unsigned NOT NULL AUTO_INCREMENT ,
  `ENTITY_TYPE_ID` BIGINT unsigned DEFAULT NULL,
  `FIELD_NAME` varchar(100) DEFAULT NULL,
  `DELIM_STR` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `ENTITY_TYPE_ID` (`ENTITY_TYPE_ID`),
  KEY `ID` (`ID`),
  CONSTRAINT `class_field_ibfk_1` FOREIGN KEY (`ENTITY_TYPE_ID`) REFERENCES `ENTITY_TYPE` (`ID`)
) ;


DROP TABLE IF EXISTS sourcelead.EMAIL_SCENARIO;
CREATE TABLE `sourcelead`.EMAIL_SCENARIO (
  `ID` BIGINT unsigned NOT NULL AUTO_INCREMENT,
  `SCENARIO_NAME` varchar(150) DEFAULT NULL,
  `ENTITY_TYPE` varchar(50) NULL,
  `UNIQUE_SCENARIO_NAME` varchar(150) DEFAULT NULL,
  `DESCRIPTION` text,
  `SUBJECT` text,
  `TEMPLATE_TYPE` varchar(10) DEFAULT NULL,
  `SIGNATURE` text,
  `BODY` text,
  `CALL_TO_ACTION_TEXT` text,
  `BUTTON_TEXT` text,
  `IS_TEMPLATE_MODIFIED` varchar(3) DEFAULT NULL,
  `CREATED_DATE` date DEFAULT NULL,
  `CREATED_BY` varchar(8) DEFAULT NULL,
  `ORG_CODE` varchar(100) DEFAULT NULL,
  `LOCATION_CODE` varchar(100) DEFAULT NULL,
  `USE_LOGO` varchar(3) DEFAULT NULL,
  `DISCLAIMER_TEXT` text,
  PRIMARY KEY (`ID`),
  KEY `ORG_CODE` (`ORG_CODE`,`ID`)
) ;




INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('OrgRegistration','Email');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginURL', '{{Registration.LoginURL}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'firstName', '{{Registration.FirstName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`,`DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'lastName','{{Registration.LastName}}');

INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('ForgotPassword','Email');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`,`DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'firstName','{{ForgotPassword.FirstName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'lastName', '{{ForgotPassword.LastName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'verificationCode', '{{ForgotPassword.verificationCode}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'resetPasswordURL', '{{ForgotPassword.ResetPasswordURL}}');


INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('UpdatePassword','Email');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`,`DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'firstName','{{UpdatePassword.FirstName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'lastName', '{{UpdatePassword.LastName}}');

INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('InviteUser','Email');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'firstName', '{{InviteUser.FirstName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'lastName', '{{InviteUser.LastName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'administratorFirstName', '{{InviteUser.AdministratorFirstName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'administratorLastName', '{{InviteUser.AdministratorLastName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'role', '{{InviteUser.Role}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'userEmailId', '{{InviteUser.UserEmailId}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'password', '{{InviteUser.Password}}');


INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('ManagerAssignedToProject','Email');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'projectName', '{{ManagerAssignedToProject.ProjectName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'managerFirstName', '{{ManagerAssignedToProject.ManagerFirstName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'managerLastName', '{{ManagerAssignedToProject.ManagerLastName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'client name', '{{ManagerAssignedToProject.ClientName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'teamMembers', '{{ManagerAssignedToProject.TeamMembers}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'orgName', '{{ManagerAssignedToProject.OrgName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'userEmailId', '{{ManagerAssignedToProject.UserEmailId}}');


INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('TeamAssignedToProject','Email');

INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'employeeFirstName}', '{{TeamAssignedToProject.EmployeeFirstName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'employeeLastName', '{{TeamAssignedToProject.EmployeeLastName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'managerFirstName', '{{TeamAssignedToProject.ManagerFirstName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'managerLastName', '{{TeamAssignedToProject.ManagerLastName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'userEmailId', '{{TeamAssignedToProject.UserEmailId}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'projectName', '{{TeamAssignedToProject.ProjectName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'orgName', '{{TeamAssignedToProject.OrgName}}');

INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('Org Registration ','Email', 'Org Registration ', 'Used for Org Registration', 'Welcome to SourceLead\n', 'sourceLead', 'Best Regards\nCEO,\nSourceLead.', 'Welcome !!!\nHi {{Registration.FirstName}} {{Registration.LastName}},\nIt\'s great to see that you\'ve started accessing sourcelead\n{{Registration.LoginURL}}\nI hope you have a wonderful time with SourceLead. If you have any questions or comments, feel free to reach out to us at support@sourcelead.com','N', 'SLADMIN', 'slead', 'sleadIN');
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('Forgot Password','Email', 'Forgot Password', 'Used for Forgot password', 'Request to Reset your SourceLead Password', 'sourceLead', 'Best Regards,\nSourceLead.', 'Hi {{ForgotPassword.FirstName}} {{ForgotPassword.LastName}},\nWe received a request to reset your password for your SourceLead account.\nVerification Code : {{ForgotPassword.verificationCode}} \nTo reset your password process simply click the link below\n {{ForgotPassword.ResetPasswordURL}}\nIf you didn\'t ask to change your password ,don\'t worry! Your password is still safe and access the sourcelead with existing credentials.','N', 'SLADMIN', 'slead', 'sleadIN');
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES ('Update Password','Email', 'Update Password', 'Used for Update Password', 'Your SourceLead Password has been Reset\n', 'sourceLead', 'Best Regards\nSourceLead\n', 'Hi {{UpdatePassword.FirstName}} {{UpdatePassword.LastName}},\nThe Password of your Sourcelead  has been reset successfully.\nYou can now log in to your Sourcelead application with your new password. If you do not request for new password please contact support@sourcelead.com','N', 'SLADMIN', 'slead', 'sleadIN');
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES ('Invite User','Email', 'Invite User', 'Used for Invite User', '{{InviteUser.UserEmailId}} invited to SourceLead','org', 'Best Regards,\n{Administrator name},\nSourceLead.', 'Hi {{InviteUser.FirstName}} {{InviteUser.LastName}},\n{{InviteUser.AdministratorFirstName}} {{InviteUser.AdministratorLastName}} invited you to SourceLead as a {{InviteUser.Role}}.\n You can simply login into SourceLead by clicking below link with follow credentials\n Login  Username : {{InviteUser.UserEmailId}} will be the user name.\n Password : {{InviteUser.Password}}','N', 'SLADMIN', 'slead', 'sleadIN');
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES ('Manager Assigned To Project','Email', 'Manager Assigned To Project', 'Used for manager assigned to project', 'Manager for {{ManagerAssignedToProject.ProjectName}}','org', 'Best Regards,\n{{ManagerAssignedToProject.UserEmailId}},\n{{ManagerAssignedToProject.OrgName}}.', 'Hi {{ManagerAssignedToProject.ManagerFirstName}} {{ManagerAssignedToProject.ManagerLastName}},\n {{ManagerAssignedToProject.UserEmailId}} assigned as a project manager for the {{ManagerAssignedToProject.ProjectName}} to the {{ManagerAssignedToProject.ClientName}}.\n Team Members \n {{ManagerAssignedToProject.TeamMembers}}.','N', 'SLADMIN', 'slead', 'sleadIN');
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES ('Team Assigned To Project','Email', 'Team Assigned To Project', 'Used for Team assigned to project', 'New Project assigned','org', 'Best Regards,\n{{TeamAssignedToProject.UserEmailId}},\n {{TeamAssignedToProject.OrgName}}.', 'Hi {{TeamAssignedToProject.EmployeeFirstName}} {{TeamAssignedToProject.EmployeeLastName}},\n {{TeamAssignedToProject.UserEmailId}} assigned you as a team member for the {{TeamAssignedToProject.ProjectName}} and your project manager is {{TeamAssignedToProject.ManagerFirstName}} {{TeamAssignedToProject.ManagerLastName}}.','N','SLADMIN', 'slead', 'sleadIN');