DROP TABLE IF EXISTS sourcelead.ENTITY_TYPE;
CREATE TABLE `sourcelead`.ENTITY_TYPE (
  `ID` BIGINT unsigned NOT NULL AUTO_INCREMENT,
  `ENTITY_NAME` varchar(255) DEFAULT NULL,
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




INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`) VALUES ('OrgRegistration');
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`) VALUES ('ForgotPassword');
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`) VALUES ('UpdatePassword');
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`) VALUES ('InviteUser');
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`) VALUES ('ManagerAssignedToProject');
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`) VALUES ('TeamAssignedToProject');


INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('1', 'firstName', '{{Registration.FirstName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`,`DELIM_STR`) VALUES ('1', 'lastName','{{Registration.LastName}}');

INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`,`DELIM_STR`) VALUES ('2', 'firstName','{{ForgotPassword.FirstName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('2', 'lastName', '{{ForgotPassword.LastName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('2', 'userEmail', '{{ForgotPassword.userEmail}}');

INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`,`DELIM_STR`) VALUES ('3', 'firstName','{{UpdatePassword.FirstName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('3', 'lastName', '{{UpdatePassword.LastName}}');


INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('4', 'firstName', '{{InviteUser.FirstName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('4', 'lastName', '{{InviteUser.LastName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('4', 'administratorFirstName', '{{InviteUser.AdministratorFirstName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('4', 'administratorLastName', '{{InviteUser.AdministratorLastName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('4', 'role', '{{InviteUser.Role}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('4', 'userEmailId', '{{InviteUser.UserEmailId}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('4', 'password', '{{InviteUser.Password}}');

INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('5', 'projectName', '{{ManagerAssignedToProject.ProjectName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('5', 'managerFirstName', '{{ManagerAssignedToProject.ManagerFirstName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('5', 'managerLastName', '{{ManagerAssignedToProject.ManagerLastName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('5', 'client name', '{{ManagerAssignedToProject.ClientName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('5', 'teamMembers', '{{ManagerAssignedToProject.TeamMembers}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('5', 'orgName', '{{ManagerAssignedToProject.OrgName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('5', 'userEmailId', '{{ManagerAssignedToProject.UserEmailId}}');

INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('6', 'employeeFirstName}', '{{TeamAssignedToProject.EmployeeFirstName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('6', 'employeeLastName', '{{TeamAssignedToProject.EmployeeLastName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('6', 'managerFirstName', '{{TeamAssignedToProject.ManagerFirstName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('6', 'managerLastName', '{{TeamAssignedToProject.ManagerLastName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('6', 'userEmailId', '{{TeamAssignedToProject.UserEmailId}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('6', 'projectName', '{{TeamAssignedToProject.ProjectName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('6', 'orgName', '{{TeamAssignedToProject.OrgName}}');


INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('Org Registration ', 'Org Registration ', 'Used for Org Registration', 'Welcome to SourceLead\n', 'sourceLead', 'Best Regards\nCEO,\nSourceLead.', 'Welcome !!!\nHi {{Registration.FirstName}} {{Registration.LastName}},\nIt\'s great to see that you\'ve started accessing sourcelead\nYour Sourcelead URL : \nI hope you have a wonderful time with SourceLead. If you have any questions or comments, feel free to reach out to us at support@sourcelead.com','N', 'SLADMIN', 'sourceLead.com', 'in.sourceLead.com');
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('Forgot Password', 'Forgot Password', 'Used for Forgot password', 'Request to Reset your SourceLead Password', 'sourceLead', 'Best Regards,\nSourceLead.', 'Hi {{ForgotPassword.FirstName}} {{ForgotPassword.LastName}},\nWe received a request to reset your password for your SourceLead account.\nUsername : “{{ForgotPassword.userEmail}} “\nTo reset your password process simply click the link below\nReset Password\nIf you didn’t ask to change your password ,don’t worry! Your password is still safe and access the sourcelead with existing credentials.','N', 'SLADMIN', 'sourceLead.com', 'in.sourceLead.com');
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES ('Update Password', 'Update Password', 'Used for Update Password', 'Your SourceLead Password has been Reset\n', 'sourceLead', 'Best Regards\nSourceLead\n', 'Hi {{UpdatePassword.FirstName}} {{UpdatePassword.LastName}},\nThe Password of your Sourcelead  has been reset successfully.\nYou can now log in to your Sourcelead application with your new password. If you do not request for new password please contact support@sourcelead.com','N', 'SLADMIN', 'sourceLead.com', 'in.sourceLead.com');
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES ('Invite User', 'Invite User', 'Used for Invite User', '{{InviteUser.UserEmailId}} invited to SourceLead', 'sourceLead', 'Best Regards,\n{Administrator name},\nSourceLead.', 'Hi {{InviteUser.FirstName}} {{InviteUser.LastName}},\n{{InviteUser.AdministratorFirstName}} {{InviteUser.AdministratorLastName}} invited you to SourceLead as a {{InviteUser.Role}}.\n You can simply login into SourceLead by clicking below link with follow credentials\n Login  Username : {{InviteUser.UserEmailId}} will be the user name.\n Password : {{InviteUser.Password}}','N', 'SLADMIN', 'sourceLead.com', 'in.sourceLead.com');
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES ('Manager Assigned To Project', 'Manager Assigned To Project', 'Used for manager assigned to project', 'Manager for {{ManagerAssignedToProject.ProjectName}}', 'sourceLead', 'Best Regards,\n{{ManagerAssignedToProject.UserEmailId}},\n{{ManagerAssignedToProject.OrgName}}.', 'Hi {{ManagerAssignedToProject.ManagerFirstName}} {{ManagerAssignedToProject.ManagerLastName}},\n {{ManagerAssignedToProject.UserEmailId}} assigned as a project manager for the {{ManagerAssignedToProject.ProjectName}} to the {{ManagerAssignedToProject.ClientName}}.\n Team Members \n {{ManagerAssignedToProject.TeamMembers}}.','N', 'SLADMIN', 'sourceLead.com', 'in.sourceLead.com');
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES ('Team Assigned To Project', 'Team Assigned To Project', 'Used for Team assigned to project', 'New Project assigned', 'sourceLead', 'Best Regards,\n{{TeamAssignedToProject.UserEmailId}},\n {{TeamAssignedToProject.OrgName}}.', 'Hi {{TeamAssignedToProject.EmployeeFirstName}} {{TeamAssignedToProject.EmployeeLastName}},\n {{TeamAssignedToProject.UserEmailId}} assigned you as a team member for the {{TeamAssignedToProject.ProjectName}} and your project manager is {{TeamAssignedToProject.ManagerFirstName}} {{TeamAssignedToProject.ManagerLastName}}.','N','SLADMIN', 'sourceLead.com', 'in.sourceLead.com');