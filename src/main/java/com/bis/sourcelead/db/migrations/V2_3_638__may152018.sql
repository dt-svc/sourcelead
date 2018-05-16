SET SQL_SAFE_UPDATES = 0;
#qa

UPDATE `EMAIL_SCENARIO` SET `BODY`='[ShortList_Applicant_Count] applicants got shortlisted for the [Requirement_Name]' WHERE `ID`='144';

INSERT INTO `CLASS_FIELD` (`FIELD_NAME`, `EMAIL_SCENARIO_ID`, `DELIMITER_STR`) VALUES ('ShortListApplicantCount', '144', '[ShortList_Applicant_Count]');

INSERT INTO `CLASS_FIELD` (`FIELD_NAME`, `EMAIL_SCENARIO_ID`, `DELIMITER_STR`) VALUES ('RequirementName', '144', '[Requirement_Name]');

/*
#prod

UPDATE `EMAIL_SCENARIO` SET `BODY`='[ShortList_Applicant_Count] applicants got shortlisted for the [Requirement_Name]' WHERE `ID`='145';

INSERT INTO `CLASS_FIELD` (`FIELD_NAME`, `EMAIL_SCENARIO_ID`, `DELIMITER_STR`) VALUES ('ShortListApplicantCount', '145', '[ShortList_Applicant_Count]');

INSERT INTO `CLASS_FIELD` (`FIELD_NAME`, `EMAIL_SCENARIO_ID`, `DELIMITER_STR`) VALUES ('RequirementName', '145', '[Requirement_Name]');

SET SQL_SAFE_UPDATES = 1;

*/