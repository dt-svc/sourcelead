
#Email to project manager who is assigned to project
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('MailToProjectManager', 'Email');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUserFirstName', '{{MailToProjectManager.LoginUserFirstName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUserLastName', '{{MailToProjectManager.LoginUserLastName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'managerFirstName', '{{MailToProjectManager.ManagerFirstName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'managerLastName', '{{MailToProjectManager.ManagerLastName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'teamMembers', '{{MailToProjectManager.TeamMembers}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'projectName', '{{MailToProjectManager.ProjectName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'orgName', '{{MailToProjectManager.OrgName}}');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES ('Mail To Project Manager', 'Email', 'Mail To Project Manager', 'Used for Mail To Project Manager', 'Manager for {{MailToProjectManager.ProjectName}}', 'org', 'Best Regards,\n{{MailToProjectManager.LoginUserFirstName}} {{MailToProjectManager.LoginUserLastName}},\n{{MailToProjectManager.OrgName}}.', 'Dear {{MailToProjectManager.ManagerFirstName}} {{MailToProjectManager.ManagerLastName}},\n {{MailToProjectManager.LoginUserFirstName}} {{MailToProjectManager.LoginUserLastName}} assigned you as a project manager for the {{MailToProjectManager.ProjectName}}.\nTeam Members\n{{MailToProjectManager.TeamMembers}}','N','SLADMIN', 'com', 'in.com');

