set SQL_SAFE_UPDATES=0;
UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='Request for Leave to Approve'  WHERE `UNIQUE_SCENARIO_NAME`='LeaveEmailToApprovers' and `ENTITY_TYPE`='Email';
set SQL_SAFE_UPDATES=1;



