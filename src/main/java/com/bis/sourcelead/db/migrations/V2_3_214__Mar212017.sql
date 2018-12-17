SET SQL_SAFE_UPDATES = 0;
  /*================================USER EMAIL SUBJECT EDITED================================*/ 
   
UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='{{InviteUser.FirstName}} {{InviteUser.LastName}} Invited You to SourceLead' WHERE `UNIQUE_SCENARIO_NAME`='Invite User' and `ENTITY_TYPE`='Email';

/*================================CLIENT ACCEPT MAIL EDITED================================*/

UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{inviteClient.FirstName}} {{inviteClient.LastName}},\nOur Organization {{inviteClient.vendorOrgName}} is using SourceLead Talent Acquisition Platform.We would like to invite your organization as our client.\nSourceLead is a Software as a Service talent acquisition platform that helps you attract, source, onboard and manage talent.\nOur platform provides the business insight needed to track the processes with in talent ecosystem.\n\nTo login into the system, click on the login button and use the following credentials.\n\nUsername : {{inviteClient.userName}}\nPassword : {{inviteClient.Password}}\n{{inviteClient.acceptURL}}' WHERE `UNIQUE_SCENARIO_NAME`='Accept Client Invitation' and `ENTITY_TYPE`='Email' ;

/*================================VENDOR ACCEPT MAIL EDITED================================*/

UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{inviteVendor.FirstName}} {{inviteVendor.LastName}},\nOur Organization {{inviteVendor.clientOrgName}} is using SourceLead Talent Acquisition Platform. We would like to invite your organization as our vendor.\nSourceLead is a Software as a Service talent acquisition platform that helps you attract, source, onboard and manage talent.\nOur platform provides the business insight needed to track the processes with in talent ecosystem.\n\nTo login into the system, click on the login button and use the following credentials.\n\nUsername : {{inviteVendor.userName}}\n  Password : {{inviteVendor.Password}}\n{{inviteVendor.acceptURL}}' WHERE `UNIQUE_SCENARIO_NAME`='Accept Vendor Invitation' and `ENTITY_TYPE`='Email';

SET SQL_SAFE_UPDATES = 1;