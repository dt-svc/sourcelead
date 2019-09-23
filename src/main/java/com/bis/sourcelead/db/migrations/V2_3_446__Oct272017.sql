
ALTER TABLE `PROJECT_BUDGET` 
CHANGE COLUMN `ESTIMATED_BUDGET_VALUE` `ESTIMATED_BUDGET_VALUE` DOUBLE NULL DEFAULT NULL ;

ALTER TABLE `PROJECT_BUDGET` 
CHANGE COLUMN `ESTIMATED_HOURS` `ESTIMATED_HOURS` DOUBLE NULL DEFAULT NULL ;

set SQL_SAFE_UPDATES=0;
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{inviteClient.FirstName}} {{inviteClient.LastName}},\n\n{{inviteClient.loginUsername}} @{{inviteClient.vendorOrgName}} is using SourceLead a Global Talent Harvesting Platform to Manage Human Resource for their company.\n\nThis email is being sent to you because {{inviteClient.loginUsername}} @{{inviteClient.vendorOrgName}} is using SourceLead to Manage their resource including Project management,Applicaiton Tracking,Time Tracking,Billing,Crowd Sourcing etc.
\nSourceLead makes it easy for teams to work more efficiently by Managing the Resource.\nOver 8,000 remote teams uses it daily.\n\nPlease find your user id and temporary password below to login to the sourcelead platform and setup your organization.\n\nUsername : {{inviteClient.userName}}\nPassword  : {{inviteClient.Password}}\n{{inviteClient.acceptURL}}\n\nNeed Support?\nWe have video tutorials, live chat, and a great support site to help if you get stuck.\n\n' WHERE `UNIQUE_SCENARIO_NAME`='Accept Client Invitation' and `ENTITY_TYPE`='Email';

# vendor invitation body change

UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{inviteVendor.FirstName}} {{inviteVendor.LastName}},\n\n{{inviteClient.loginUsername}} @{{inviteVendor.clientOrgName}} is using SourceLead a Global Talent Harvesting Platform to Manage Human Resource for their company.\n\nThis email is being sent to you because {{inviteClient.loginUsername}} @{{inviteVendor.clientOrgName}} is using SourceLead to Manage their resource including Project management,Applicaiton Tracking,Time Tracking,Billing,Crowd Sourcing etc.
\nSourceLead makes it easy for teams to work more efficiently by Managing the Resource.\nOver 8,000 remote teams uses it daily.\n\nPlease find your user id and temporary password below to login to the sourcelead platform and setup your organization\n\nUsername : {{inviteVendor.userName}}\nPassword  : {{inviteVendor.Password}}\n{{inviteVendor.acceptURL}}\n\nNeed Support?\nWe have video tutorials, live chat, and a great support site to help if you get stuck.\n\n' WHERE `UNIQUE_SCENARIO_NAME`='Accept Vendor Invitation' and `ENTITY_TYPE`='Email';


UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{inviteClient.FirstName}} {{inviteClient.LastName}},\n\n{{inviteClient.loginUsername}} @{{inviteClient.vendorOrgName}} is using SourceLead a Global Talent Harvesting Platform to Manage Human\nResource for their company.\n\nThis email is being sent to you because {{inviteClient.loginUsername}} @{{inviteClient.vendorOrgName}} is using SourceLead to Manage their\nresource including but not limited to Project management,Applicaiton Tracking,Time Tracking,Billing,\nCrowd Sourcing etc.
\nSourceLead makes it easy for teams to work more efficiently by Managing the Resource.\nOver 8,000 remote teams uses it daily.\n\nPlease find your user id and temporary password below to login to the sourcelead platform and setup your organization.\n\nUsername : {{inviteClient.userName}}\nPassword  : {{inviteClient.Password}}\n{{inviteClient.acceptURL}}\n\nNeed Support?\nWe have video tutorials, live chat, and a great support site to help if you get stuck.\n\n' WHERE `UNIQUE_SCENARIO_NAME`='Accept Client Invitation' and `ENTITY_TYPE`='Email';

# vendor invitation body change
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{inviteVendor.FirstName}} {{inviteVendor.LastName}},\n\n{{inviteClient.loginUsername}} @{{inviteVendor.clientOrgName}} is using SourceLead a Global Talent Harvesting Platform to Manage Human\nResource for their company.\n\nThis email is being sent to you because {{inviteClient.loginUsername}} @{{inviteVendor.clientOrgName}} is using SourceLead to Manage their\nresource including but not limited to Project management,Applicaiton Tracking,Time Tracking,Billing,\nCrowd Sourcing etc.
\nSourceLead makes it easy for teams to work more efficiently by Managing the Resource.\nOver 8,000 remote teams uses it daily.\n\nPlease find your user id and temporary password below to login to the sourcelead platform and setup your organization\n\nUsername : {{inviteVendor.userName}}\nPassword  : {{inviteVendor.Password}}\n{{inviteVendor.acceptURL}}\n\nNeed Support?\nWe have video tutorials, live chat, and a great support site to help if you get stuck.\n\n' WHERE `UNIQUE_SCENARIO_NAME`='Accept Vendor Invitation' and `ENTITY_TYPE`='Email';

set SQL_SAFE_UPDATES=1;