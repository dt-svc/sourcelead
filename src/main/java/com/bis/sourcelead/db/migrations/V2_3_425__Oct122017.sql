SET SQL_SAFE_UPDATES = 0;
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{OffBoardingProcessApplicantTermination.applicantName}},\n\nThis is to inform you that you have been terminated from your services with {{OffBoardingProcessApplicantTermination.companyName}}.\nWe Thank you for your time and service at {{OffBoardingProcessApplicantTermination.companyName}}.\n\nIf you have future questions or concerns please Contact Human resources.\n\nPlease click here to complete the exit formalities.\n\n{{OffBoardingProcessApplicantTermination.loginLink}}'  WHERE `UNIQUE_SCENARIO_NAME`='OffBoardingProcessApplicantTermination' and `ENTITY_TYPE`='Email';

UPDATE `SOURCELEAD_ACTIONS` SET `ACTION_NAME`='Create  Onboard' WHERE `ID`='190';
UPDATE `SOURCELEAD_ACTIONS` SET `ACTION_NAME`='Create  Offboard' WHERE `ID`='191';
SET SQL_SAFE_UPDATES = 1;









