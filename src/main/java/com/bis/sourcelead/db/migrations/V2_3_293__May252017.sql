CALL AlterTableAddColumn ('INVOICE','EXCHANGE_RATE','VARCHAR(50) NULL  AFTER `CURRENCY_CODE`');

CALL AlterTableAddColumn ('INVOICE','CONVERT_AMOUNT','VARCHAR(50) NULL  AFTER `EXCHANGE_RATE`');

CALL AlterTableAddColumn ('INVOICE','CONVERT_CURRENCY_CODE','VARCHAR(50) NULL  AFTER `CONVERT_AMOUNT`');

set SQL_SAFE_UPDATES=0;
UPDATE `EMAIL_SCENARIO` SET `BODY`= 'Welcome !!!\n Dear {{ProfessionalInvitation.professionalName}}\n\n It is great to see that you have started accessing \n User Name : {{ProfessionalInvitation.loginUserName}}\n Your Sourcelead URL : {{ProfessionalInvitation.resetLink}}\n  I hope you will  have a wonderful time with  If you have any questions or comments, feel free to reach out to us at info@com' WHERE `UNIQUE_SCENARIO_NAME`='Professional Invitation' and `ENTITY_TYPE`='Email';

set SQL_SAFE_UPDATES=0;
UPDATE `EMAIL_SCENARIO` SET `BODY`= 'Welcome !!!\n Dear {{RecruiterInvitation.recruiterName}}\n\n It is great to see that you have started accessing \n User Name : {{RecruiterInvitation.loginUserName}}\n Your Sourcelead URL : {{RecruiterInvitation.resetLink}}\n  I hope you will  have a wonderful time with  If you have any questions or comments, feel free to reach out to us at info@com' WHERE `UNIQUE_SCENARIO_NAME`='Individual Recruiter Invitation' and `ENTITY_TYPE`='Email';
