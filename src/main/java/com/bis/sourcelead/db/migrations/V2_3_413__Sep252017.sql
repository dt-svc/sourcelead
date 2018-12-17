SET SQL_SAFE_UPDATES = 0;

UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{MSAFinalizeEmail.receiverParty}},\n We are happy to inform you {{MSAFinalizeEmail.contractTitle}} Contract got finalised from {{MSAFinalizeEmail.duration}}' WHERE `UNIQUE_SCENARIO_NAME`='MSAFinalizeEmail';

SET SQL_SAFE_UPDATES = 1;

