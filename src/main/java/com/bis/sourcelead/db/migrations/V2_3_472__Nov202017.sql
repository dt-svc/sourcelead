<<<<<<< HEAD
CALL sourcelead.AlterTableAddColumn ('sourcelead','USER_MAIL_BOX','MAIL_TYPE','INT NULL  AFTER `CAMPAIGN_TABLE_DATA`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','USER_MAIL_BOX','REQUIREMENT_ID','TEXT NULL  AFTER `CANDIDATE_ID`');


INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('JobListCampaign','Email');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'salutation', '{{JobListCampaign.salutation}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'body', '{{JobListCampaign.body}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'noOfJobs', '{{JobListCampaign.noOfJobs}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'signature', '{{JobListCampaign.signature}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'joblistTable', '{{JobListCampaign.joblistTable}}');

INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
											('JobListCampaign','Email', 'JobListCampaign', 'used for Joblist campaign', 'Job Campaign.','org', '{{JobListCampaign.signature}}', '{{JobListCampaign.salutation}},\n{{JobListCampaign.body}}\nNo.of Jobs : {{JobListCampaign.noOfJobs}}\n\n {{JobListCampaign.joblistTable}}','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');

											
=======
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'jobType', '{{notifyApplicantMailAboutRequirement.jobType}}');


INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'industryName', '{{notifyApplicantMailAboutRequirement.industryName}}');


INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'clientName', '{{notifyApplicantMailAboutRequirement.clientName}}');

INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'visaType', '{{notifyApplicantMailAboutRequirement.visaType}}');


set SQL_SAFE_UPDATES=0;
UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `BODY`='Dear {{notifyApplicantMailAboutRequirement.applicantName}},\n\nI came across a position I thought would interest you. Please take a moment to review this opportunity and let me know if you would like to learn more about this Position.\n\nPlease send your resume and a good time to talk if interested in learning more.\n\nTitle: {{notifyApplicantMailAboutRequirement.requirementName}}\nPrimary Skill(s): {{notifyApplicantMailAboutRequirement.skills}}\nLocation: {{notifyApplicantMailAboutRequirement.location}}\nExeperience: {{notifyApplicantMailAboutRequirement.experience}}\nClient Name: {{notifyApplicantMailAboutRequirement.clientName}}\nLength: {{notifyApplicantMailAboutRequirement.jobType}}\nRate: {{notifyApplicantMailAboutRequirement.requirementRate}}\nIndustry: {{notifyApplicantMailAboutRequirement.industryName}}\nWork Authorization: {{notifyApplicantMailAboutRequirement.visaType}}\n\nJob Description : {{notifyApplicantMailAboutRequirement.jobDescription}}.\n\nThank you in advance.\n ' WHERE `UNIQUE_SCENARIO_NAME`='notifyApplicantMailAboutRequirement' and `ENTITY_TYPE`='Email';
set SQL_SAFE_UPDATES=1;
>>>>>>> 790cb6fb3b0f35a67b8f0170d2ad39859c71e657
