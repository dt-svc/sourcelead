set SQL_SAFE_UPDATES=0;
UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='{{InviteUser.loginUser}} Invites you to join {{InviteUser.orgName}} on SourceLead a Global Talent Harvesting Platform' WHERE `UNIQUE_SCENARIO_NAME`='Invite User' and `ENTITY_TYPE`='Email';
set SQL_SAFE_UPDATES=1;