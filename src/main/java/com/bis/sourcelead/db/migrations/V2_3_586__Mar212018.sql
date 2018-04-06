SET SQL_SAFE_UPDATES=0;
UPDATE `EMAIL_SCENARIO` SET `BODY`='{{InviteVendor.clientOrgName}} invited you as Vendor' WHERE `UNIQUE_SCENARIO_NAME`='Accept Vendor Invitation';
UPDATE `EMAIL_SCENARIO` SET `BODY`='{{inviteClient.vendorOrgName}} invited you as Client' WHERE `UNIQUE_SCENARIO_NAME`='Accept Client Invitation';
SET SQL_SAFE_UPDATES=1;