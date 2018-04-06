#MSA Finalize email
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('MSAFinalizeEmail', 'Email');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUser', '{{MSAFinalizeEmail.loginUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'receiverParty', '{{MSAFinalizeEmail.receiverParty}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'contractTitle', '{{MSAFinalizeEmail.contractTitle}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'duration', '{{MSAFinalizeEmail.duration}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'billingAmount', '{{MSAFinalizeEmail.billingAmount}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'billingCycle', '{{MSAFinalizeEmail.billingCycle}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'paymentFrequency', '{{MSAFinalizeEmail.paymentFrequency}}');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
			('MSAFinalizeEmail','Email', 'MSAFinalizeEmail', 'used to notify receiver party about finalizing msa contract', '{{MSAFinalizeEmail.contractTitle}} Contract got finalised.','org', 'Best Regards,\n{{MSAFinalizeEmail.loginUser}}', 'Dear {{MSAFinalizeEmail.receiverParty}},\nWe are happy to inform you {{MSAFinalizeEmail.contractTitle}} Contract got finalised. The brief details of the contract are following\nDuration : {{MSAFinalizeEmail.duration}}\nBilling Amount : {{MSAFinalizeEmail.billingAmount}}\nBilling Cycle : {{MSAFinalizeEmail.billingCycle}}\nPayment Frequency : {{MSAFinalizeEmail.paymentFrequency}}','N', 'SLADMIN', 'slead', 'sleadIN');
			

			
#SOW Finalize email
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('SOWFinalizeEmail', 'Email');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUser', '{{SOWFinalizeEmail.loginUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'receiverParty', '{{SOWFinalizeEmail.receiverParty}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'contractTitle', '{{SOWFinalizeEmail.contractTitle}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'Job', '{{SOWFinalizeEmail.Job}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'Associate', '{{SOWFinalizeEmail.Associate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'duration', '{{SOWFinalizeEmail.duration}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'billingAmount', '{{SOWFinalizeEmail.billingAmount}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'billingCycle', '{{SOWFinalizeEmail.billingCycle}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'paymentFrequency', '{{SOWFinalizeEmail.paymentFrequency}}');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
			('SOWFinalizeEmail','Email', 'SOWFinalizeEmail', 'used to notify receiver party about finalizing sow contract', '{{SOWFinalizeEmail.contractTitle}} Contract got finalised.','org', 'Best Regards,\n{{SOWFinalizeEmail.loginUser}}', 'Dear {{SOWFinalizeEmail.receiverParty}},\nWe are happy to inform you {{SOWFinalizeEmail.contractTitle}} Contract got finalised. The brief details of the contract are following\nDuration : {{SOWFinalizeEmail.duration}}\nJob : {{SOWFinalizeEmail.Job}}\nAssociate : {{SOWFinalizeEmail.Associate}}\nBilling Amount : {{SOWFinalizeEmail.billingAmount}}\nBilling Cycle : {{SOWFinalizeEmail.billingCycle}}\nPayment Frequency : {{SOWFinalizeEmail.paymentFrequency}}','N', 'SLADMIN', 'slead', 'sleadIN');


#NDA/NSA Finalize email
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('NDAOrNSAFinalizeEmail', 'Email');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUser', '{{NDAOrNSAFinalizeEmail.loginUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'receiverParty', '{{NDAOrNSAFinalizeEmail.receiverParty}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'contractTitle', '{{NDAOrNSAFinalizeEmail.contractTitle}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'duration', '{{NDAOrNSAFinalizeEmail.duration}}');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
			('NDAOrNSAFinalizeEmail','Email', 'NDAOrNSAFinalizeEmail', 'used to notify receiver party about finalizing sow contract', '{{NDAOrNSAFinalizeEmail.contractTitle}} Contract got finalised.','org', 'Best Regards,\n{{NDAOrNSAFinalizeEmail.loginUser}}', 'Dear {{NDAOrNSAFinalizeEmail.receiverParty}},\nWe are happy to inform you {{NDAOrNSAFinalizeEmail.contractTitle}} Contract got finalised. The brief details of the contract are following\nDuration : {{NDAOrNSAFinalizeEmail.duration}}','N', 'SLADMIN', 'slead', 'sleadIN');

			