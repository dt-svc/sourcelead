SET SQL_SAFE_UPDATES = 0;	
UPDATE `EMAIL_SCENARIO` SET `BODY`= 'Dear {{UserRateNegotiation.applicantName}},\n\n{{UserRateNegotiation.userName}} Proposed new rate for the position {{UserRateNegotiation.requirementName}} with {{UserRateNegotiation.organizationName}}\n {{UserRateNegotiation.negotiationTable}}\n\n\nPlease login to SourceLead to check more details\n' WHERE `UNIQUE_SCENARIO_NAME`='UserRateNegotiation' and `ENTITY_TYPE`='Email';
SET SQL_SAFE_UPDATES = 1;	
