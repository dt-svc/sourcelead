INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES (147, 'managerName', '[Manager_Name]');
SET SQL_SAFE_UPDATES = 0;
UPDATE `EMAIL_SCENARIO` SET `BODY`= ' Dear [Manager_Name],\n\n[Requirement_Title] has been updated by [Login_User]\nPlease find the modifications\nJob Title: [Requirement_Title]\nClient : [Client_Contactname]\nJob Location : [Requirement_Location]\nJob Rate : [Requirement_Rate]\nExperience : [Experience]\nStatus : [Status]\nPlease login to SourceLead to find more details' WHERE `UNIQUE_SCENARIO_NAME`='updatedRequirementEmailToManager' and `ENTITY_TYPE`='Email';
UPDATE`EMAIL_SCENARIO` SET `TEMPLATE_TYPE`='sourcelead' WHERE `ID`='334';
SET SQL_SAFE_UPDATES = 1;