#OffBoarding_ProcessApplicantTermination_Notification
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('OffBoardingProcessApplicantTermination', 'Notification');


#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('OffBoardingProcessApplicantTermination', 'Notification', 'OffBoardingProcessApplicantTermination', 'Send mail to OffBoardingProcessApplicantTermination','org','Termination from {{OffBoardingProcessApplicantTermination.companyName}}','N', 'SLADMIN', 'com', 'in.com');


SET SQL_SAFE_UPDATES = 0;

UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='{{ModifiedRequirementStatusMailtoVendorContact.requirementTitle}} is {{ModifiedRequirementStatusMailtoVendorContact.requirementStatus}} by {{ModifiedRequirementStatusMailtoVendorContact.requirementManagerName}} ({{ModifiedRequirementStatusMailtoVendorContact.logInOrgName}})',`BODY`='Dear {{ModifiedRequirementStatusMailtoVendorContact.vendorContactName}},\n{{ModifiedRequirementStatusMailtoVendorContact.requirementTitle}} has {{ModifiedRequirementStatusMailtoVendorContact.requirementStatus}} by {{ModifiedRequirementStatusMailtoVendorContact.requirementManagerName}} ({{ModifiedRequirementStatusMailtoVendorContact.logInOrgName}}). The brief requirement details are below \nRequirement Title : {{ModifiedRequirementStatusMailtoVendorContact.requirementTitle}} \nClient : {{ModifiedRequirementStatusMailtoVendorContact.requirementClientName}} \nRequirement Location : {{ModifiedRequirementStatusMailtoVendorContact.requirementLocation}} \nRequirement Rate : {{ModifiedRequirementStatusMailtoVendorContact.requirementRate}} \nExperience : {{ModifiedRequirementStatusMailtoVendorContact.reqExp}} \nStatus : {{ModifiedRequirementStatusMailtoVendorContact.requirementStatus}} \nFor more details login into the system \n\n {{Registration.LoginURL}}' WHERE `UNIQUE_SCENARIO_NAME`='ModifiedRequirementStatusMailtoVendorContact' and `ENTITY_TYPE`='Email';
UPDATE `EMAIL_SCENARIO` SET `BODY`='{{ModifiedRequirementStatusMailtoVendorContact.requirementTitle}} is {{ModifiedRequirementStatusMailtoVendorContact.requirementStatus}} by {{ModifiedRequirementStatusMailtoVendorContact.requirementManagerName}} {{ModifiedRequirementStatusMailtoVendorContact.logInOrgName}}' WHERE `UNIQUE_SCENARIO_NAME`='ModifiedRequirementStatusNotificationtoVendorContact' and `ENTITY_TYPE`='Notification';
SET SQL_SAFE_UPDATES = 1;
