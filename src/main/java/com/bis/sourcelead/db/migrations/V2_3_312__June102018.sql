set SQL_SAFE_UPDATES=0;
UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='{{InviteUser.FirstName}} {{InviteUser.LastName}} Invited to SourceLead' WHERE `UNIQUE_SCENARIO_NAME`='Invite User' and `ENTITY_TYPE`='Email';

UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{InviteUser.FirstName}} {{InviteUser.LastName}},\nYou have been invited to SourceLead as a {{InviteUser.Role}}.\nYou can simply login into SourceLead by clicking below link with following credentials\n\nUsername : {{InviteUser.UserEmailId}} \nPassword : {{InviteUser.Password}}\n\n {{InviteUser.LoginURL}}' WHERE `UNIQUE_SCENARIO_NAME`='Invite User' and `ENTITY_TYPE`='Email';
set SQL_SAFE_UPDATES=1;