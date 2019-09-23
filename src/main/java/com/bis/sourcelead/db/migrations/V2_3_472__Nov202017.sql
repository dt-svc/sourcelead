
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'jobType', '{{notifyApplicantMailAboutRequirement.jobType}}');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'industryName', '{{notifyApplicantMailAboutRequirement.industryName}}');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'clientName', '{{notifyApplicantMailAboutRequirement.clientName}}');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'visaType', '{{notifyApplicantMailAboutRequirement.visaType}}');

set SQL_SAFE_UPDATES=0;

UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{notifyApplicantMailAboutRequirement.applicantName}},\n\nI came across a position I thought would interest you. Please take a moment to review this opportunity and let me know if you would like to learn more about this Position.\n\nPlease send your resume and a good time to talk if interested in learning more.\n\nTitle: {{notifyApplicantMailAboutRequirement.requirementName}}\nPrimary Skill(s): {{notifyApplicantMailAboutRequirement.skills}}\nLocation: {{notifyApplicantMailAboutRequirement.location}}\nExeperience: {{notifyApplicantMailAboutRequirement.experience}}\nClient Name: {{notifyApplicantMailAboutRequirement.clientName}}\nLength: {{notifyApplicantMailAboutRequirement.jobType}}\nRate: {{notifyApplicantMailAboutRequirement.requirementRate}}\nIndustry: {{notifyApplicantMailAboutRequirement.industryName}}\nWork Authorization: {{notifyApplicantMailAboutRequirement.visaType}}\n\nJob Description : {{notifyApplicantMailAboutRequirement.jobDescription}}.\n\nThank you in advance.\n ' WHERE `UNIQUE_SCENARIO_NAME`='notifyApplicantMailAboutRequirement' and `ENTITY_TYPE`='Email';

set SQL_SAFE_UPDATES=1;

