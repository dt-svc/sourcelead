set SQL_SAFE_UPDATES=0;

UPDATE `SOURCELEAD_ACTIONS` SET `ACTION_NAME`='Export Employees', `ACTION_ROLE`='ROLE_EXPORT_EMPLOYEE' WHERE `ID`='93';

UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='{{InviteUser.loginUser}} invites you to join ({{InviteUser.orgName}}) on SourceLead a Global Talent Harvesting Platform',`BODY`='Dear {{InviteUser.FirstName}},\n\nWelcome to {{InviteUser.orgName}}, I am delighted to Introduce you to our Platform to Manage Day to day Operations.  You are invited to be a {{InviteUser.Role}} for our company operations, please take a moment to update your information and start your operations using the product.\n\nSourceLead makes it easy for teams to work more efficiently by Managing the Resource.  Over 8,000 remote teams use it daily.\n\nPlease find your user id and temporary password below to login to the sourcelead platform and setup your profile.\n\nUsername : {{InviteUser.UserEmailId}} \nPassword  : {{InviteUser.Password}}\n\n {{InviteUser.LoginURL}}',`SIGNATURE`='Best Regards,\n{{InviteUser.loginUser}}' WHERE `UNIQUE_SCENARIO_NAME`='Invite User' and `ENTITY_TYPE`='Email';

set SQL_SAFE_UPDATES=1;

