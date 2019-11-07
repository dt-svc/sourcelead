set SQL_SAFE_UPDATES=0;
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{ActivateUserEmail.registerName}},\n\nThank you for registering with us. To verify your Sourcelead account, kindly click on the verification link below.\n\n {{ActivateUserEmail.verifyLink}}' WHERE `UNIQUE_SCENARIO_NAME`='ActivateUserEmail' and `ENTITY_TYPE`='Email';
set SQL_SAFE_UPDATES=1;