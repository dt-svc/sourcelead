
SET SQL_SAFE_UPDATES=0;
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'managersName', '{{MailToTeamMembers.managersName}}');

UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{MailToTeamMembers.EmployeeFirstName}},
\n{{MailToTeamMembers.LoginUserFirstName}} assigned you as a team member for the Project\n\n The brief details of the project are following\n Project name : {{MailToTeamMembers.ProjectName}} \n Start Date :{{MailToTeamMembers.userStartDate}}\n End Date :{{MailToTeamMembers.projectEndDate}}\n Project manager :{{MailToTeamMembers.managersName}}' WHERE `UNIQUE_SCENARIO_NAME`='Mail To Team Members' and `ENTITY_TYPE`='Email';
SET SQL_SAFE_UPDATES=1;

