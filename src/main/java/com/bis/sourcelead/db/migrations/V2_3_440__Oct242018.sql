set SQL_SAFE_UPDATES=0;
#user_Invitation_Email
UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='{{InviteUser.FirstName}} Invites you to join ({{InviteUser.orgName}}) on SourceLead a Global Talent Harvesting Platform', `BODY`='Dear {{InviteUser.FirstName}},\n\nWelcome to {{InviteUser.orgName}}, I am delighted to Introduce you to our Platform to Manage Day to day Operations.  You are invited to be a manager for our company operations, please take a moment to update your information and start your operations using the product.\n\nSourceLead makes it easy for teams to work more efficiently by Managing the Resource.  Over 8,000 remote teams use it daily.\n\nPlease find your user id and temporary password below to login to the sourcelead platform and setup your profile.\n\nUsername : {{InviteUser.UserEmailId}} \nPassword  : {{InviteUser.Password}}\n\n {{InviteUser.LoginURL}}' WHERE `UNIQUE_SCENARIO_NAME`='Invite User' and `ENTITY_TYPE`='Email';


UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='{{rejectVendor.clientFirstName}} {{rejectVendor.clientLastName}} rejected your invitation as ({{rejectVendor.clientOrgName}}) Vendor', `BODY`='Dear {{rejectVendor.FirstName}} {{rejectVendor.LastName}},\n{{rejectVendor.clientFirstName}} {{rejectVendor.clientLastName}} from {{rejectVendor.clientOrgName}} rejected your invitation as Vendor.\nYou can rebuild relationship with {{rejectVendor.clientOrgName}} from ' WHERE `UNIQUE_SCENARIO_NAME`='Reject Vendor' and `ENTITY_TYPE`='Email';

UPDATE `EMAIL_SCENARIO` SET `SIGNATURE`='Best Regards \n{{UserRoleChanged.LoginUser}}', `BODY`='Dear {{UserRoleChanged.FirstName}},\nYour Role has been updated from {{UserRoleChanged.ExistingRole}} to {{UserRoleChanged.CurrentRole}}.\nfor  brief details login into system \n {{UserRoleChanged.Loginlink}}' WHERE `UNIQUE_SCENARIO_NAME`='UserRoleChanged' and `ENTITY_TYPE`='Email';


INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`,`DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'userName','{{UpdatePassword.userName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`,`DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'updatedDate','{{UpdatePassword.updatedDate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`,`DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'forgotPasswordLink','{{UpdatePassword.forgotPasswordLink}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`,`DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'supportLink','{{UpdatePassword.supportLink}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`,`DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'sourceleadLink','{{UpdatePassword.sourceleadLink}}');

UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{UpdatePassword.FirstName}} {{UpdatePassword.LastName}},\n\nThis is to notify you that the password for your SourceLead account {{UpdatePassword.userName}}(User Name) was recently changed ({{UpdatePassword.updatedDate}} UTC).\n\n
Dont recognize this activity\nReset your account password using the {{UpdatePassword.forgotPasswordLink}} option.\n\nSend an email to {{UpdatePassword.supportLink}} if you need any further clarification.', `SIGNATURE`='Thank you\nSourcelead Team\n{{UpdatePassword.sourceleadLink}}'  WHERE `UNIQUE_SCENARIO_NAME`='Update Password' and `ENTITY_TYPE`='Email';
set SQL_SAFE_UPDATES=1;





