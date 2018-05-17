SET SQL_SAFE_UPDATES = 0;
UPDATE `EMAIL_SCENARIO` SET `BODY`='{{OfferAccepted.OfferedApplicantName}} accepted the offer on behalf of {{OfferAccepted.OfferedRequirementName}}' WHERE `UNIQUE_SCENARIO_NAME`='Offer Accepted Notification' and `ENTITY_TYPE`='Notification';
UPDATE `EMAIL_SCENARIO` SET `BODY`='{{OfferRejected.OfferRejectedApplicantName}} rejected the offer for {{OfferRejected.RequirementName}}' WHERE `UNIQUE_SCENARIO_NAME`='Offer Rejected Notification' and `ENTITY_TYPE`='Notification';
SET SQL_SAFE_UPDATES = 1;