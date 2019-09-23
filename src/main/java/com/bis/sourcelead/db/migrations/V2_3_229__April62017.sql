#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('interviewRescheduleMailToApplicant', 'Email');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'applicantName', '{{interviewRescheduleMailToApplicant.applicantName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUser', '{{interviewRescheduleMailToApplicant.loginUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementTitle', '{{interviewRescheduleMailToApplicant.requirementTitle}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginOrgName', '{{interviewRescheduleMailToApplicant.loginOrgName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'interviewDate', '{{interviewRescheduleMailToApplicant.interviewDate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'interviewTime', '{{interviewRescheduleMailToApplicant.interviewTime}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'interviewType', '{{interviewRescheduleMailToApplicant.interviewType}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'acceptURL', '{{interviewRescheduleMailToApplicant.acceptURL}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'rejectURL', '{{interviewRescheduleMailToApplicant.rejectURL}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'proposeNewTime', '{{interviewRescheduleMailToApplicant.proposeNewTime}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'comment', '{{interviewRescheduleMailToApplicant.comment}}');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES('interview Reschedule MailTo Applicant','Email', 'interviewRescheduleMailToApplicant', 'notify to applicant about interview reschedule', 'Interview got rescheduled for the {{interviewRescheduleMailToApplicant.requirementTitle}}\n','org', '\nBest Regards,\n{{interviewRescheduleMailToApplicant.loginUser}}\n{{interviewRescheduleMailToApplicant.loginOrgName}}.', 'Dear {{interviewRescheduleMailToApplicant.applicantName}},\nInterview got rescheduled for the {{interviewRescheduleMailToApplicant.requirementTitle}}.\nThe rescheduled interview details are follwing\nDate :{{interviewRescheduleMailToApplicant.interviewDate}}\nTime :{{interviewRescheduleMailToApplicant.interviewTime}}\nType :{{interviewRescheduleMailToApplicant.interviewType}}\nComment :{{interviewRescheduleMailToApplicant.comment}}\n{{interviewRescheduleMailToApplicant.acceptURL}}{{interviewRescheduleMailToApplicant.rejectURL}}{{interviewRescheduleMailToApplicant.proposeNewTime}}\n\n','N', 'SLADMIN', 'slead', 'sleadIN');




