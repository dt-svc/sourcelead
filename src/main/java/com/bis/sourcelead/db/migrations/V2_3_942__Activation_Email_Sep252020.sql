SET SQL_SAFE_UPDATES=0;

UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear [Register_Name],\n
Thank you for registering with us. To verify your Sourcelead account, kindly click on the verification link below.\n
[Verify_Link] \n
Note: The above link would be active for next 24:00 Hrs only. Do get back to Admin to re-activate.\n' WHERE ENTITY_TYPE = 'Email' and UNIQUE_SCENARIO_NAME = 'ActivateUserEmail';

UPDATE ASSET SET WORKING_STATUS = replace(WORKING_STATUS,'Working','Assigned');

SET SQL_SAFE_UPDATES=1;