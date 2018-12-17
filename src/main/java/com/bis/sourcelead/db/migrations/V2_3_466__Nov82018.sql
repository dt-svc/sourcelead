INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'InviteUser' and `ENTITY_TYPE` = 'Email'),
'loginUser', '{{InviteUser.loginUser}}');

#userTermination
set SQL_SAFE_UPDATES=0;
UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='You have been relieved from {{UserTerminatedEmail.companyName}}', `BODY`='Dear {{UserTerminatedEmail.applicantName}},\n\nWe regret to inform you that your employment has been terminated and you are relieved from all your duties from {{UserTerminatedEmail.companyName}} effective from {{UserTerminatedEmail.date}}.\n\nPlease Complete the Exit Interview in order to complete offboarding process whenever you can.\n\nHowever, you can still use SourceLead to find your next Job, refer your friends and earn money using\nour referral program. please update your profile with your personal email ID to continue using\nSourceLead Application.\n\nFor more information please click here.\n\n{{UserTerminatedEmail.loginURL}}' WHERE `UNIQUE_SCENARIO_NAME`='UserTerminatedEmail' and `ENTITY_TYPE`='Email';

#userInvitation
UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='{{InviteUser.loginUser}} Invites you to join ({{InviteUser.orgName}}) on SourceLead a Global Talent Harvesting Platform' WHERE `UNIQUE_SCENARIO_NAME`='Invite User' and `ENTITY_TYPE`='Email';
set SQL_SAFE_UPDATES=1;