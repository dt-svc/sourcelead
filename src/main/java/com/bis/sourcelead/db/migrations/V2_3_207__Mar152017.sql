SET SQL_SAFE_UPDATES = 0;

/*================================USERS================================*/
#inviteuser Email
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{InviteUser.FirstName}} {{InviteUser.LastName}},\n You have been invited to SourceLead as a {{InviteUser.Role}}.\nYou can simply login into SourceLead by clicking below link with following credentials\n\n Username : {{InviteUser.UserEmailId}} \n Password : {{InviteUser.Password}} \n\n {{InviteUser.LoginURL}}' WHERE `UNIQUE_SCENARIO_NAME`='Invite User' and `ENTITY_TYPE`='Email';


/*================================CLIENT INVITATION,ACCEPT,REJECT================================*/
#releaseMechanism_email
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'InviteClient' and `ENTITY_TYPE` = 'Email'),
'rejectURL', '{{inviteClient.rejectURL}}');


UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{inviteClient.FirstName}} {{inviteClient.LastName}},\nOur Organization {{inviteClient.vendorOrgName}} is using SourceLead Talent Acquisition Platform.We would like to invite your organization as our client.\nSourceLead is a Software as a Service talent acquisition platform that helps you attract, source, onboard and manage talent.\nOur platform provides the business insight needed to track the processes with in talent ecosystem.\n\nTo login into the system, click on the login button and use the following credentials.\n\nUsername : {{inviteClient.userName}}\nPassword : {{inviteClient.Password}}\n{{inviteClient.acceptURL}}{{inviteClient.rejectURL}}' WHERE `UNIQUE_SCENARIO_NAME`='Accept Client Invitation' and `ENTITY_TYPE`='Email' ;


/*acceptance mail from client*/
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'Accept Client' and `ENTITY_TYPE` = 'Email'), 
'signature', '{{acceptClient.signature}}');

UPDATE `EMAIL_SCENARIO` SET `SIGNATURE`='Best Regards,\n{{acceptClient.signature}}.', `BODY`='Dear {{acceptClient.FirstName}} {{acceptClient.LastName}},\n{{acceptClient.vendorFirstName}} {{acceptClient.vendorLastName}} has accepted your orgnazition as a Client in the \nLogin into the system for brief details {{acceptClient.loginURL}}.' WHERE `UNIQUE_SCENARIO_NAME`='Accept Client' and `ENTITY_TYPE`='Email';


/*client invitation reject*/
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{rejectClient.FirstName}} {{rejectClient.LastName}},\n{{rejectClient.vendorFirstName}} {{rejectClient.vendorLastName}} from {{rejectClient.vendorOrgName}} rejected your invitation as Client.\n You can rebuild relationship with {{rejectClient.vendorOrgName}} from ' WHERE `UNIQUE_SCENARIO_NAME`='Reject Client' and `ENTITY_TYPE`='Email';

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'RejectClient' and `ENTITY_TYPE` = 'Email'), 
'signature', '{{rejectClient.signature}}');

UPDATE `EMAIL_SCENARIO` SET `SIGNATURE`='Best Regards,\n{{rejectClient.signature}}.' WHERE `UNIQUE_SCENARIO_NAME`='Reject Client' and `ENTITY_TYPE`='Email';


/*================================VENDOR================================*/

 #vendor
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'InviteVendor' and `ENTITY_TYPE` = 'Email'),
'rejectURL', '{{inviteVendor.rejectURL}}');
 
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{inviteVendor.FirstName}} {{inviteVendor.LastName}},\nOur Organization {{inviteVendor.clientOrgName}} is using SourceLead Talent Acquisition Platform. We would like to invite your organization as our client.\nSourceLead is a Software as a Service talent acquisition platform that helps you attract, source, onboard and manage talent.\nOur platform provides the business insight needed to track the processes with in talent ecosystem.\n\nTo login into the system, click on the login button and use the following credentials.\n\nUsername : {{inviteVendor.userName}}\n  Password : {{inviteVendor.Password}}\n{{inviteVendor.acceptURL}}{{inviteVendor.rejectURL}}' WHERE `UNIQUE_SCENARIO_NAME`='Accept Vendor Invitation' and `ENTITY_TYPE`='Email';


#vendorAccept
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{acceptVendor.FirstName}} {{acceptVendor.LastName}},\n{{acceptVendor.clientFirstName}} {{acceptVendor.clientLastName}} has accepted your orgnazition as a Vendor in the \nLogin into the system for brief details {{AcceptVendor.loginURL}}'WHERE `UNIQUE_SCENARIO_NAME`='Accept Vendor' and `ENTITY_TYPE`='Email';

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'Accept Vendor' and `ENTITY_TYPE` = 'Email'), 
'signature', '{{acceptVendor.signature}}');
UPDATE `EMAIL_SCENARIO` SET `SIGNATURE`='Best Regards,\n{{acceptVendor.signature}}.' WHERE `UNIQUE_SCENARIO_NAME`='Accept Vendor' and `ENTITY_TYPE`='Email';

#vendor Reject
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'RejectVendor' and `ENTITY_TYPE` = 'Email'),'signature', '{{rejectVendor.signature}}');
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{rejectVendor.FirstName}} {{rejectVendor.LastName}},\n{{rejectVendor.clientFirstName}} {{rejectVendor.clientLastName}} from {{rejectVendor.clientOrgName}} rejected your invitation as Vendor.\n You can rebuild relationship with {{rejectVendor.clientOrgName}} from ' WHERE `UNIQUE_SCENARIO_NAME`='Reject Vendor' and `ENTITY_TYPE`='Email';
UPDATE `EMAIL_SCENARIO` SET `SIGNATURE`='Best Regards,\n{{rejectVendor.signature}}.' WHERE `UNIQUE_SCENARIO_NAME`='Reject Vendor' and `ENTITY_TYPE`='Email';
SET SQL_SAFE_UPDATES = 1;

