#Approval Mechanism Email

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('ShareRecomendedApplicant','Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'jobName', '{{ShareRecomendedApplicant.jobName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'jobLocation', '{{ShareRecomendedApplicant.jobLocation}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'jobRate', '{{ShareRecomendedApplicant.jobRate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUserName', '{{ShareRecomendedApplicant.loginUserName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'viewJobLink', '{{ShareRecomendedApplicant.viewJobLink}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'jobDescription', '{{ShareRecomendedApplicant.jobDescription}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'jobExperience', '{{ShareRecomendedApplicant.jobExperience}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginOrgName', '{{ShareRecomendedApplicant.loginOrgName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'candidateName', '{{ShareRecomendedApplicant.candidateName}}');



INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
('Share Recomended Applicant','Email', 'Share Recomended Applicant', 'Used mail from Share Recomended Applicant  ', 'Opening Job position for  {{ShareRecomendedApplicant.jobName}} - {{ShareRecomendedApplicant.jobLocation}}','org', 'Best Regards,\n{{ShareRecomendedApplicant.loginUserName}},\n{{ShareRecomendedApplicant.loginOrgName}}', 'Dear {{ShareRecomendedApplicant.candidateName}},\n\n We have an excellent opportunity that matches your technical skills and expertise. Please go through the job details and apply if it suits your profile and career aspirations.\n\n Job Name : {{ShareRecomendedApplicant.jobName}} \n\n Job Location : {{ShareRecomendedApplicant.jobLocation}} \n\n Experience : {{ShareRecomendedApplicant.jobExperience}} \n\n Rate : {{ShareRecomendedApplicant.jobRate}} \n\n Job Description : {{ShareRecomendedApplicant.jobDescription}} \n\n  {{ShareRecomendedApplicant.viewJobLink}} \n','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');
