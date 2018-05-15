SET SQL_SAFE_UPDATES = 0;
UPDATE `EMAIL_SCENARIO` SET `BODY`='[Offered_Applicant_Name] accepted the offer on behalf of [Offered_Requirement_Name]' WHERE `UNIQUE_SCENARIO_NAME`='Offer Accepted Notification' and `ENTITY_TYPE`='Notification';
UPDATE `EMAIL_SCENARIO` SET `BODY`='[Offer_Rejected_Applicant_Name] rejected the offer for [Requirement_Name]'  WHERE `UNIQUE_SCENARIO_NAME`='Offer Rejected Notification' and `ENTITY_TYPE`='Notification';
SET SQL_SAFE_UPDATES = 1;