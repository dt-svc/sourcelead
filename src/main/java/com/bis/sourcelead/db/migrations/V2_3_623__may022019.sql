SET SQL_SAFE_UPDATES = 0;
UPDATE `EMAIL_SCENARIO` SET `BODY`='Congratulations !! {{ForwardOfferMail.organizationName}} sent you an offer' WHERE `UNIQUE_SCENARIO_NAME`='Forward the Mail' and `ENTITY_TYPE`='Notification';
SET SQL_SAFE_UPDATES = 1;