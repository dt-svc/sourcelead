set SQL_SAFE_UPDATES=0;

UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{MailToTeamMembers.EmployeeFirstName}},
\n{{MailToTeamMembers.LoginUserFirstName}} assigned you as a team member for the Project\n\n The brief details of the project are following\n Project name : {{MailToTeamMembers.ProjectName}} \n Start Date :{{MailToTeamMembers.userStartDate}}\n Project manager :{{MailToTeamMembers.ManagerFirstName}}' WHERE `UNIQUE_SCENARIO_NAME`='Mail To Team Members' and `ENTITY_TYPE`='Email';

set SQL_SAFE_UPDATES=1;