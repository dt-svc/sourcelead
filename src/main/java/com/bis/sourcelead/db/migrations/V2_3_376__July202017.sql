


#3.Registration [Recruiter]
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'Individual Recruiter Invitation' and `ENTITY_TYPE` = 'Email'),
'supportLink','{{RecruiterInvitation.supportLink}}');

INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'Individual Recruiter Invitation' and `ENTITY_TYPE` = 'Email'),
'facebookLink','{{RecruiterInvitation.facebookLink}}');

INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'Individual Recruiter Invitation' and `ENTITY_TYPE` = 'Email'),
'linkedInLink','{{RecruiterInvitation.linkedInLink}}');

INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'Individual Recruiter Invitation' and `ENTITY_TYPE` = 'Email'),
'twitterLink','{{RecruiterInvitation.twitterLink}}');

INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'Individual Recruiter Invitation' and `ENTITY_TYPE` = 'Email'),
'googleLink','{{RecruiterInvitation.googleLink}}');


#REQUESTED_DOCUMENTS_SUBMITED_TO_USER_Email
#entity_type_Email
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('UploadedRequestForDocumentsToCandidates', 'Email');



#entity_type_Email
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('InvoiceClientPayableReminderEmail', 'Email');


SET SQL_SAFE_UPDATES = 0;	
UPDATE `sourcelead`.`class_field` SET `ENTITY_TYPE_ID`=(SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'UploadedRequestForDocumentsToCandidates' and `ENTITY_TYPE` = 'Email') WHERE DELIM_STR = '{{UploadedRequestForDocumentsToCandidates.userName}}' and FIELD_NAME='userName';


UPDATE `sourcelead`.`class_field` SET `ENTITY_TYPE_ID`=(SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'UploadedRequestForDocumentsToCandidates' and `ENTITY_TYPE` = 'Email') WHERE DELIM_STR = '{{UploadedRequestForDocumentsToCandidates.candidateName}}' and FIELD_NAME='candidateName';


# InvoiceClientPayableReminderEmail
UPDATE `sourcelead`.`class_field` SET `ENTITY_TYPE_ID`=(SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'InvoiceClientPayableReminderEmail' and `ENTITY_TYPE` = 'Email') WHERE DELIM_STR = '{{InvoiceClientPayableReminderEmail.orgName}}' and FIELD_NAME='orgName';


UPDATE `sourcelead`.`class_field` SET `ENTITY_TYPE_ID`=(SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'InvoiceClientPayableReminderEmail' and `ENTITY_TYPE` = 'Email') WHERE DELIM_STR = '{{InvoiceClientPayableReminderEmail.LoginUser}}' and FIELD_NAME='LoginUser';



UPDATE `sourcelead`.`class_field` SET `ENTITY_TYPE_ID`=(SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'InvoiceClientPayableReminderEmail' and `ENTITY_TYPE` = 'Email') WHERE DELIM_STR = '{{InvoiceClientPayableReminderEmail.invoiceID}}' and FIELD_NAME='invoiceID';

UPDATE `sourcelead`.`class_field` SET `ENTITY_TYPE_ID`=(SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'InvoiceClientPayableReminderEmail' and `ENTITY_TYPE` = 'Email') WHERE DELIM_STR = '{{InvoiceClientPayableReminderEmail.clientName}}' and FIELD_NAME='clientName';


UPDATE `sourcelead`.`class_field` SET `ENTITY_TYPE_ID`=(SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'InvoiceClientPayableReminderEmail' and `ENTITY_TYPE` = 'Email') WHERE DELIM_STR = '{{InvoiceClientPayableReminderEmail.grandTotal}}' and FIELD_NAME='grandTotal';



UPDATE `sourcelead`.`class_field` SET `ENTITY_TYPE_ID`=(SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'InvoiceClientPayableReminderEmail' and `ENTITY_TYPE` = 'Email') WHERE DELIM_STR = '{{InvoiceClientPayableReminderEmail.dueDate}}' and FIELD_NAME='dueDate';

UPDATE `sourcelead`.`class_field` SET `ENTITY_TYPE_ID`=(SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'InvoiceClientPayableReminderEmail' and `ENTITY_TYPE` = 'Email') WHERE DELIM_STR = '{{InvoiceClientPayableReminderEmail.acceptLink}}' and FIELD_NAME='acceptLink';


UPDATE `sourcelead`.`class_field` SET `ENTITY_TYPE_ID`=(SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'InvoiceClientPayableReminderEmail' and `ENTITY_TYPE` = 'Email') WHERE DELIM_STR = '{{InvoiceClientPayableReminderEmail.rejectLink}}' and FIELD_NAME='rejectLink';



UPDATE `sourcelead`.`class_field` SET `ENTITY_TYPE_ID`=(SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'InvoiceClientPayableReminderEmail' and `ENTITY_TYPE` = 'Email') WHERE DELIM_STR = '{{InvoiceClientPayableReminderEmail.attachement}}' and FIELD_NAME='attachement';



# Professional Invitation	
UPDATE `sourcelead`.`class_field` SET `ENTITY_TYPE_ID`=(SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'ProfessionalInvitation' and `ENTITY_TYPE` = 'Email') WHERE DELIM_STR = '{{ProfessionalInvitation.supportLink}}' and FIELD_NAME='supportLink';

UPDATE `sourcelead`.`class_field` SET `ENTITY_TYPE_ID`=(SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'ProfessionalInvitation' and `ENTITY_TYPE` = 'Email') WHERE DELIM_STR = '{{ProfessionalInvitation.facebookLink}}' and FIELD_NAME='facebookLink';

UPDATE `sourcelead`.`class_field` SET `ENTITY_TYPE_ID`=(SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'ProfessionalInvitation' and `ENTITY_TYPE` = 'Email') WHERE DELIM_STR = '{{ProfessionalInvitation.linkedInLink}}' and FIELD_NAME='linkedInLink';

UPDATE `sourcelead`.`class_field` SET `ENTITY_TYPE_ID`=(SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'ProfessionalInvitation' and `ENTITY_TYPE` = 'Email') WHERE DELIM_STR = '{{ProfessionalInvitation.twitterLink}}' and FIELD_NAME='twitterLink';

UPDATE `sourcelead`.`class_field` SET `ENTITY_TYPE_ID`=(SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'ProfessionalInvitation' and `ENTITY_TYPE` = 'Email') WHERE DELIM_STR = '{{ProfessionalInvitation.googleLink}}' and FIELD_NAME='googleLink';


# Refer Requirment To Professional

UPDATE `sourcelead`.`class_field` SET `ENTITY_TYPE_ID`=(SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'Refer Requirment To Professional' and `ENTITY_TYPE` = 'Email') WHERE DELIM_STR = '{{ReferRequirement.linkedInLink}}' and FIELD_NAME='supportLink';

UPDATE `sourcelead`.`class_field` SET `ENTITY_TYPE_ID`=(SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'Refer Requirment To Professional' and `ENTITY_TYPE` = 'Email') WHERE DELIM_STR = '{{ReferRequirement.twitterLink}}' and FIELD_NAME='twitterLink';

UPDATE `sourcelead`.`class_field` SET `ENTITY_TYPE_ID`=(SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'Refer Requirment To Professional' and `ENTITY_TYPE` = 'Email') WHERE DELIM_STR = '{{ReferRequirement.googleLink}}' and FIELD_NAME='googleLink';

UPDATE `sourcelead`.`class_field` SET `ENTITY_TYPE_ID`=(SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'Refer Requirment To Professional' and `ENTITY_TYPE` = 'Email') WHERE DELIM_STR = '{{ReferRequirement.jobLocation}}' and FIELD_NAME='jobLocation';

UPDATE `sourcelead`.`class_field` SET `ENTITY_TYPE_ID`=(SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'Refer Requirment To Professional' and `ENTITY_TYPE` = 'Email') WHERE DELIM_STR = '{{ReferRequirement.jobDescription}}' and FIELD_NAME='jobDescription';


UPDATE `sourcelead`.`class_field` SET `ENTITY_TYPE_ID`=(SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'Refer Requirment To Professional' and `ENTITY_TYPE` = 'Email') WHERE DELIM_STR = '{{ReferRequirement.professionalReg}}' and FIELD_NAME='professionalReg';

UPDATE `sourcelead`.`class_field` SET `ENTITY_TYPE_ID`=(SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'Refer Requirment To Professional' and `ENTITY_TYPE` = 'Email') WHERE DELIM_STR = '{{ReferRequirement.referedProfessionalFirstName}}' and FIELD_NAME='referedProfessionalFirstName';

UPDATE `sourcelead`.`class_field` SET `ENTITY_TYPE_ID`=(SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'Refer Requirment To Professional' and `ENTITY_TYPE` = 'Email') WHERE DELIM_STR = '{{ReferRequirement.supportLink}}' and FIELD_NAME='supportLink';

UPDATE `sourcelead`.`class_field` SET `ENTITY_TYPE_ID`=(SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'Refer Requirment To Professional' and `ENTITY_TYPE` = 'Email') WHERE DELIM_STR = '{{ReferRequirement.facebookLink}}' and FIELD_NAME='facebookLink';

UPDATE `sourcelead`.`class_field` SET `ENTITY_TYPE_ID`=(SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'Refer Requirment To Professional' and `ENTITY_TYPE` = 'Email') WHERE DELIM_STR = '{{ReferRequirement.linkedInLink}}' and FIELD_NAME='linkedInLink';

SET SQL_SAFE_UPDATES = 1;	