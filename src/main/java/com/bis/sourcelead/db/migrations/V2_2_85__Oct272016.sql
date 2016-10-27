UPDATE `sourcelead`.`CLASS_FIELD` SET `DELIM_STR`='{{SubmitToClient.LoginUser}}' WHERE `ID`='209';
UPDATE `sourcelead`.`CLASS_FIELD` SET `DELIM_STR`='{{SubmitToClient.LoginOrgName}}' WHERE `ID`='210';
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('57', 'loginURL', '{{SubmitToClient.LoginURL}}');

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `BODY`='Hi {{SubmitToClient.ClientFirstName}} {{SubmitToClient.ClientLastName}},\n\n {{SubmitToClient.LoginUser}}({{SubmitToClient.LoginOrgName}}) submitted the applicants for the {{SubmitToClient.RequirementTitle}}.\n Total no.of submitted applicants : {{SubmitToClient.TotalNoOfApplicants}}\n\n {{SubmitToClient.LoginURL}}' WHERE `ID`='57';
