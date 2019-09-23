

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('61', 'negotiationLink', '{{ForwardOfferMail.negotiationLink}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('61', 'requirementTitle', '{{ForwardOfferMail.requirementTitle}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('61', 'finalRate', '{{ForwardOfferMail.finalRate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('61', 'positionType', '{{ForwardOfferMail.positionType}}');

UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{ForwardOfferMail.applicantName}},\n\n  We are pleased to offer for a {{ForwardOfferMail.requirementTitle}} at {{ForwardOfferMail.organizationName}}.\n  We trust that your experience and skills will be a valuable asset to our Organization.\n\n  Rate :{{ForwardOfferMail.finalRate}}\n\n  Date of Join:{{ForwardOfferMail.dateofJoin}}\n\n  Position Type:{{ForwardOfferMail.positionType}}\n\n\n        Confirm the offer by clicking on the following link \n  {{ForwardOfferMail.acceptLink}} {{ForwardOfferMail.rejectLink}} {{ForwardOfferMail.negotiationLink}}\n\n  {{ForwardOfferMail.attachment}}' WHERE `ID`='62';

