


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



#2.Registration[Professional]
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'ProfessionalInvitation' and `ENTITY_TYPE` = 'Email'),
'supportLink','{{ProfessionalInvitation.supportLink}}');

INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'ProfessionalInvitation' and `ENTITY_TYPE` = 'Email'),
'facebookLink','{{ProfessionalInvitation.facebookLink}}');

INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'ProfessionalInvitation' and `ENTITY_TYPE` = 'Email'),
'linkedInLink','{{ProfessionalInvitation.linkedInLink}}');

INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'ProfessionalInvitation' and `ENTITY_TYPE` = 'Email'),
'twitterLink','{{ProfessionalInvitation.twitterLink}}');

INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'ProfessionalInvitation' and `ENTITY_TYPE` = 'Email'),
'googleLink','{{ProfessionalInvitation.googleLink}}');