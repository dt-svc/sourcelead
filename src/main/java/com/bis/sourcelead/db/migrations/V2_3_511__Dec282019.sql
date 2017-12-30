SET SQL_SAFE_UPDATES = 0;
UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `SUBJECT`='{{OffBoardingProcessApplicantTermination.offboardReason}} from {{OffBoardingProcessApplicantTermination.companyName}}' WHERE `UNIQUE_SCENARIO_NAME`='OffBoardingProcessApplicantTermination' and `ENTITY_TYPE`='Email';
UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `BODY`='{{OffBoardingProcessApplicantTermination.offboardReason}} from {{OffBoardingProcessApplicantTermination.companyName}}' WHERE `UNIQUE_SCENARIO_NAME`='OffBoardingProcessApplicantTermination' and `ENTITY_TYPE`='Notification';
SET SQL_SAFE_UPDATES = 1;