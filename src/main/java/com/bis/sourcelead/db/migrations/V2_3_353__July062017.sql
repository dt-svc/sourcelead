SET SQL_SAFE_UPDATES = 0;

#forgot password
#updated password

UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='Reset Your SourceLead Account Password', `BODY`='Dear {{ForgotPassword.FirstName}} {{ForgotPassword.LastName}},\n\nSourceLead just received a request to reset your password. In order to reset your password, please click the URL below.\n\nVerification Code : {{ForgotPassword.verificationCode}}\n{{ForgotPassword.ResetPasswordURL}}\nIf you did not request your password to be reset please ignore this email and your password will stay as it is.' WHERE `UNIQUE_SCENARIO_NAME`='Forgot Password' and `ENTITY_TYPE`='Email';
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{UpdatePassword.FirstName}} {{UpdatePassword.LastName}},\n\nThe Password of your Sourcelead has been reset successfully.\nYou can now log in to your Sourcelead application with your new password.\nIf you did not request for new password please contact support@com' WHERE `UNIQUE_SCENARIO_NAME`='Update Password' and `ENTITY_TYPE`='Email';

#added classfields fr invite client mail scenario

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUsername', '{{inviteClient.loginUsername}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'supportLink', '{{inviteClient.supportLink}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'facebookLink', '{{inviteClient.facebookLink}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'linkedInLink', '{{inviteClient.linkedInLink}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'twitterLink', '{{inviteClient.twitterLink}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'googleLink', '{{inviteClient.googleLink}}');

#added classfields fr invite vendor mail scenario

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUsername', '{{inviteVendor.loginUsername}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'supportLink', '{{inviteVendor.supportLink}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'facebookLink', '{{inviteVendor.facebookLink}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'linkedInLink', '{{inviteVendor.linkedInLink}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'twitterLink', '{{inviteVendor.twitterLink}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'googleLink', '{{inviteVendor.googleLink}}');

#added classfields fr accept client mail scenario

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'invitedOrg', '{{acceptClient.invitedOrg}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'faqLink', '{{acceptClient.faqLink}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'facebookLink', '{{acceptClient.facebookLink}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'linkedInLink', '{{acceptClient.linkedInLink}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'twitterLink', '{{acceptClient.twitterLink}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'googleLink', '{{acceptClient.googleLink}}');

#added classfields fr accept vendor mail scenario

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'invitedOrg', '{{acceptVendor.invitedOrg}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'faqLink', '{{acceptVendor.faqLink}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'facebookLink', '{{acceptVendor.facebookLink}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'linkedInLink', '{{acceptVendor.linkedInLink}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'twitterLink', '{{acceptVendor.twitterLink}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'googleLink', '{{acceptVendor.googleLink}}');

#added classfields fr editing owner client & vendor contact mail scenario

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'changedOwnerOrg', '{{EditSpocContact.changedOwnerOrg}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'changedOwnerName', '{{EditSpocContact.changedOwnerName}}');

#updated body & notification fr above scenarios

UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='{{inviteClient.loginUsername}} @{{inviteClient.vendorOrgName}} invites you to join SourceLead a Global Talent Harvesting Platform', `BODY`='Dear {{inviteClient.FirstName}} {{inviteClient.LastName}},\n\n{{inviteClient.loginUsername}} @{{inviteClient.vendorOrgName}} is using SourceLead a Global Talent Harvesting Platform to Manage Human\nResource for their company.\n\nThis email is being sent to you because {{inviteClient.loginUsername}} @{{inviteClient.vendorOrgName}} is using SourceLead to Manage their\nresource including but not limited to Project management,Applicaiton Tracking,Time Tracking,Billing,\nCrowd Sourcing etc.
\nSourceLead makes it easy for teams to work more efficiently by Managing the Resource. Over 8,000 remote\nteams uses it daily.\n\nPlease find your user id and temporary password below to login to the sourcelead platform and setup your\norganization.\nUsername : {{inviteClient.userName}}\nPassword  : {{inviteClient.Password}}\n{{inviteClient.acceptURL}}\nMore Technical Questions?\nSee:{{inviteClient.supportLink}}\n\nNeed Support?\nWe have video tutorials, live chat, and a great support site to help if you get stuck.\n\n',`SIGNATURE`='Team SourceLead\nWelcome aboard, great to have you!\n{{inviteClient.facebookLink}}\t{{inviteClient.linkedInLink}}\t{{inviteClient.twitterLink}}\t{{inviteClient.googleLink}}' WHERE `UNIQUE_SCENARIO_NAME`='Accept Client Invitation' and `ENTITY_TYPE`='Email';

UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='{{inviteVendor.loginUsername}} @{{inviteVendor.clientOrgName}} invites you to join SourceLead a Global Talent Harvesting Platform', `BODY`='Dear {{inviteVendor.FirstName}} {{inviteVendor.LastName}},\n\n{{inviteClient.loginUsername}} @{{inviteVendor.clientOrgName}} is using SourceLead a Global Talent Harvesting Platform to Manage Human\nResource for their company.\n\nThis email is being sent to you because {{inviteClient.loginUsername}} @{{inviteVendor.clientOrgName}} is using SourceLead to Manage their\nresource including but not limited to Project management,Applicaiton Tracking,Time Tracking,Billing,\nCrowd Sourcing etc.
\nSourceLead makes it easy for teams to work more efficiently by Managing the Resource. Over 8,000 remote\nteams uses it daily.\n\nPlease find your user id and temporary password below to login to the sourcelead platform and setup your\norganization\nUsername : {{inviteVendor.userName}}\nPassword  : {{inviteVendor.Password}}\n{{inviteVendor.acceptURL}}\nMore Technical Questions?\nSee:{{inviteVendor.supportLink}}\n\nNeed Support?\nWe have video tutorials, live chat, and a great support site to help if you get stuck.\n\n',`SIGNATURE`='Team SourceLead\nWelcome aboard, great to have you!\n{{inviteVendor.facebookLink}}\t{{inviteVendor.linkedInLink}}\t{{inviteVendor.twitterLink}}\t{{inviteVendor.googleLink}}' WHERE `UNIQUE_SCENARIO_NAME`='Accept Vendor Invitation' and `ENTITY_TYPE`='Email';

UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='{{acceptClient.vendorFirstName}} {{acceptClient.vendorLastName}} @{{acceptClient.vendorOrgName}} has accepted your invitation.', `BODY`='Dear {{acceptClient.FirstName}} {{acceptClient.LastName}} @{{acceptClient.invitedOrg}},\n\n{{acceptClient.vendorFirstName}} {{acceptClient.vendorLastName}} @{{acceptClient.vendorOrgName}} has accepted your invitation to join SourceLead a Global\nTalent Harvesting Platform to Manage Human Resource.\n\nThere are many ways your association can help each other\nfor more details please {{acceptClient.faqLink}}\n\nNeed Support?\nWe have video tutorials,live chat, and a great support site to help if you get stuck.\n\n',`SIGNATURE`='Team SourceLead\nWelcome aboard, great to have you!\n{{acceptClient.facebookLink}}\t{{acceptClient.linkedInLink}}\t{{acceptClient.twitterLink}}\t{{acceptClient.googleLink}}' WHERE `UNIQUE_SCENARIO_NAME`='Accept Client' and `ENTITY_TYPE`='Email';

UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='{{acceptVendor.clientFirstName}} {{acceptVendor.clientLastName}} @{{acceptVendor.clientOrgName}} has accepted your invitation.', `BODY`='Dear {{acceptVendor.FirstName}} {{acceptVendor.LastName}} @{{acceptVendor.invitedOrg}},\n\n{{acceptVendor.clientFirstName}} {{acceptVendor.clientLastName}} @{{acceptVendor.clientOrgName}} has accepted your invitation to join SourceLead a Global\nTalent Harvesting Platform to Manage Human Resource.\n\nThere are many ways your association can help each other\nfor more details please {{acceptVendor.faqLink}}\n\nNeed Support?\nWe have video tutorials,live chat, and a great support site to help if you get stuck.\n\n',`SIGNATURE`='Team SourceLead\nWelcome aboard, great to have you!\n{{acceptVendor.facebookLink}}\t{{acceptVendor.linkedInLink}}\t{{acceptVendor.twitterLink}}\t{{acceptVendor.googleLink}}' WHERE `UNIQUE_SCENARIO_NAME`='Accept Vendor' and `ENTITY_TYPE`='Email';

UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='Alert !! This is to Notify that {{EditSpocContact.LoginUser}} @{{EditSpocContact.LoginOrgName}} changed the Point of contact for the {{EditSpocContact.changedOwnerOrg}} to {{EditSpocContact.changedOwnerName}}.', `BODY`='Dear {{EditSpocContact.ClientFirstName}} {{EditSpocContact.ClientLastName}} @{{EditSpocContact.changedOwnerOrg}},\n\n{{EditSpocContact.LoginUser}} @{{EditSpocContact.LoginOrgName}} has changed the point of contact from {{EditSpocContact.changedOwnerName}} to {{EditSpocContact.OwnerName}}( {{EditSpocContact.ownerEmail}} ).So your future communication will be done through this contact.\nIf you do not wish to change this please ignore this email. if you want to approve this change please click Confirm.',`SIGNATURE`='Thank you\nSource Lead Support' WHERE `UNIQUE_SCENARIO_NAME`='Edit SpocContact' and `ENTITY_TYPE`='Email';

UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='Point of contact Updated for {{EditSpocContact.changedOwnerOrg}}', `BODY`='Point of contact Updated for {{EditSpocContact.changedOwnerOrg}}' WHERE `UNIQUE_SCENARIO_NAME`='Edit SpocContact' and `ENTITY_TYPE`='Notification';


SET SQL_SAFE_UPDATES = 1;