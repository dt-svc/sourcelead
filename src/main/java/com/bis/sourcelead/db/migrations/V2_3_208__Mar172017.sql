CALL AlterTableAddColumn ('CANDIDATE','IS_PROFILE_PUBLIC','VARCHAR(10) NULL');

SET SQL_SAFE_UPDATES = 0;
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{addNewContact.contactName}}\n\nI am a user of Sourcelead which is a new recruitment platform to find requirements and I am glad to invite to join in SourceLead, I thought you might be interested. Sourcelead is a referral driven platform which lets me find requirement for myself  or for my friends and connections. \nPlease click on below link to get register  \n\nRegistration Link : {{addNewContact.registerLink}}' WHERE `UNIQUE_SCENARIO_NAME`='Add New User Contact';
SET SQL_SAFE_UPDATES = 1;