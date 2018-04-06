#6.Refer Job[Professional]:
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'Professional Invitation' and `ENTITY_TYPE` = 'Email'),
'supportLink','{{ReferRequirement.supportLink}}');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'Professional Invitation' and `ENTITY_TYPE` = 'Email'),
'facebookLink','{{ReferRequirement.facebookLink}}');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'Professional Invitation' and `ENTITY_TYPE` = 'Email'),
'linkedInLink','{{ReferRequirement.linkedInLink}}');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'Professional Invitation' and `ENTITY_TYPE` = 'Email'),
'twitterLink','{{ReferRequirement.twitterLink}}');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'Professional Invitation' and `ENTITY_TYPE` = 'Email'),
'googleLink','{{ReferRequirement.googleLink}}');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'Professional Invitation' and `ENTITY_TYPE` = 'Email'),
'googleLink','{{ReferRequirement.googleLink}}');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'Professional Invitation' and `ENTITY_TYPE` = 'Email'),
'jobLocation','{{ReferRequirement.jobLocation}}');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'Professional Invitation' and `ENTITY_TYPE` = 'Email'),
'jobDescription','{{ReferRequirement.jobDescription}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'Professional Invitation' and `ENTITY_TYPE` = 'Email'),
'professionalReg','{{ReferRequirement.professionalReg}}');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'Professional Invitation' and `ENTITY_TYPE` = 'Email'),
'referedProfessionalFirstName','{{ReferRequirement.referedProfessionalFirstName}}');

UPDATE `EMAIL_SCENARIO` SET `BODY`= ' Dear {{ReferRequirement.professinalName}}\n\n I would like you to review the following Job\nJob ID and Title:{{ReferRequirement.requirementTitle}}\nJob Description:{{ReferRequirement.jobDescription}}\nJob Location:{{ReferRequirement.jobLocation}}\nFor more details please click here {{ReferRequirement.registerLink}}\n\nSourceLead is a leading Global Talent harvesting application can help you grow professionaly and also earn by referring your friends and family to the eligible jobs.\nFor more information please find the FAQ\nif you would like to register please click here {{ReferRequirement.professionalReg}}\nMore Technical Questions?\nSee : {{ReferRequirement.supportLink}}\nNeed Support?\nWe have video tutorials , live chat, and a great support site to help if you get stuck.\n\n ', `SUBJECT`='{{ReferRequirement.referedProfessionalFirstName}} refered you to the job {{ReferRequirement.requirementTitle}}',`SIGNATURE`='Team SourceLead\nWelcome aboard, great to have you!\n{{RecruiterInvitation.facebookLink}}\t{{RecruiterInvitation.linkedInLink}}\t{{RecruiterInvitation.twitterLink}}\t{{RecruiterInvitation.googleLink}}' WHERE `UNIQUE_SCENARIO_NAME`='Refer Requirment To Professional' and `ENTITY_TYPE`='Email';


#2.Registration[Professional]:

UPDATE `EMAIL_SCENARIO` SET `BODY`=' Dear {{ProfessionalInvitation.professionalName}}\n\nWelcome to SourceLead a Global Talent Harvesting Platform to Manage Human Resource.\n\nThis email is being sent to you because you have registered with \n\nSourceLead makes it easy for teams to work more efficiently by Automating the process of Human resource management.\nIt has many more benefits for professionals like you.Over 8,000 remote teams uses it daily.\n\nUser Name:{{ProfessionalInvitation.loginUserName}}\nYour Sourcelead URL:{{ProfessionalInvitation.resetLink}}\n\n\nMore Technical Questions?\nSee:{{ProfessionalInvitation.supportLink}}\n\nNeed Support?\nWe have video tutorials, live chat, and a great support site to help if you get stuck.\n\n',`SIGNATURE`='Team SourceLead\nWelcome aboard, great to have you!\n{{ProfessionalInvitation.facebookLink}}\t{{ProfessionalInvitation.linkedInLink}}\t{{ProfessionalInvitation.twitterLink}}\t{{ProfessionalInvitation.googleLink}}' WHERE `UNIQUE_SCENARIO_NAME`='Professional Invitation' and `ENTITY_TYPE`='Email';


#2.Contract for Approve:

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'approverUpdatedContract' and `ENTITY_TYPE` = 'Email'),
'loginLink','{{approverUpdatedContract.loginLink}}');

UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{approverUpdatedContract.ApproverName}}\n\nYour Approval is required to complete the Contract {{approverUpdatedContract.ContractTitle}}\n\nPlease find the contract details below\nTitle :{{approverUpdatedContract.ContractTitle}}\nOwner : {{approverUpdatedContract.OwnerName}}\nStart Date : {{approverUpdatedContract.StartDate}}\nEnd Date : {{approverUpdatedContract.EndDate}}\n\nPlease login to SourceLead to find more details\n\n{{approverUpdatedContract.loginLink}}' WHERE `UNIQUE_SCENARIO_NAME`='approverUpdatedContract' and `ENTITY_TYPE`='Email';

 UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='Alert Approval Requested for Contract {{approverUpdatedContract.ContractTitle}}  '  WHERE `UNIQUE_SCENARIO_NAME`='approverUpdatedContract' and `ENTITY_TYPE`='Email';
 
 
 
 #3.Approved Contract:
 
 INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'approveContract' and `ENTITY_TYPE` = 'Email'),
'ApproverUser','{{approveContract.ApproverUser}}');
				 
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'approveContract' and `ENTITY_TYPE` = 'Email'),
'OwnerName','{{approveContract.OwnerName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'approveContract' and `ENTITY_TYPE` = 'Email'),
'StartDate','{{approveContract.StartDate}}');
	INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'approveContract' and `ENTITY_TYPE` = 'Email'),
'EndDate','{{approveContract.EndDate}}');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'approveContract' and `ENTITY_TYPE` = 'Email'),
'loginLink','{{approveContract.loginLink}}');
									 
									 
			 
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'approveContract' and `ENTITY_TYPE` = 'Email'),
'clientContact','{{approveContract.clientContact}}');
									 
									 
UPDATE `EMAIL_SCENARIO` SET `BODY`=' Dear {{approveContract.contractCreator}}\n\nThis is to notify that the Contract {{approveContract.contractTitle}} has been approved by {{approveContract.ApproverUser}}\n\nPlease find the contract details below\nTitle :{{approveContract.ContractTitle}}\nOwner : {{approveContract.OwnerName}}\nStart Date : {{approveContract.StartDate}}\nEnd Date : {{approveContract.EndDate}}\nContact : {{approveContract.clientContact}}\n\nPlease login to SourceLead to find more details\n\n{{approveContract.loginLink}}' WHERE `UNIQUE_SCENARIO_NAME`='Approve Contract' and `ENTITY_TYPE`='Email';	
									  
									  
									  	 
 UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='Contract {{approveContract.contractTitle}} has been approved.  '  WHERE `UNIQUE_SCENARIO_NAME`='Approve Contract' and `ENTITY_TYPE`='Email';
									 
									 
									 
									 
 #4.Rejected Contract:
									 
									 
 INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'rejectContract' and `ENTITY_TYPE` = 'Email'),
'ApproverUser','{{rejectContract.ApproverUser}}');	

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'rejectContract' and `ENTITY_TYPE` = 'Email'),
'rejectReason','{{rejectContract.rejectReason}}');	

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'rejectContract' and `ENTITY_TYPE` = 'Email'),
'clientContact','{{rejectContract.clientContact}}');	 


INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'rejectContract' and `ENTITY_TYPE` = 'Email'),
'loginLink','{{rejectContract.loginLink}}');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'rejectContract' and `ENTITY_TYPE` = 'Email'),
'OwnerName','{{rejectContract.OwnerName}}');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'rejectContract' and `ENTITY_TYPE` = 'Email'),
'StartDate','{{rejectContract.StartDate}}');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'rejectContract' and `ENTITY_TYPE` = 'Email'),
'EndDate','{{rejectContract.EndDate}}');

UPDATE `EMAIL_SCENARIO` SET `BODY`=' Dear {{rejectContract.contractCreator}}\n\nThis is to notify that the Contract {{rejectContract.contractTitle}} has been rejected by {{rejectContract.ApproverUser}}\n\nPlease find the contract details below\nTitle :{{rejectContract.ContractTitle}}\nOwner : {{rejectContract.OwnerName}}\nStart Date : {{rejectContract.StartDate}}\nEnd Date : {{rejectContract.EndDate}}\nContact : {{rejectContract.clientContact}} \nReason for denial:{{rejectContract.rejectReason}}\n\nPlease login to SourceLead to find more details\n\n{{rejectContract.loginLink}}' WHERE `UNIQUE_SCENARIO_NAME`='Reject Contract' and `ENTITY_TYPE`='Email';					 
									 	 
UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='Contract {{rejectContract.contractTitle}} has been Denied.  '  WHERE `UNIQUE_SCENARIO_NAME`='Reject Contract' and `ENTITY_TYPE`='Email';


#5.Contracts Follow-up:

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'contractExpiryRemainder' and `ENTITY_TYPE` = 'Email'),
'clientContact','{{contractExpiryRemainder.clientContact}}');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'contractExpiryRemainder' and `ENTITY_TYPE` = 'Email'),
'expiringdays','{{contractExpiryRemainder.expiringdays}}');


UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{contractExpiryRemainder.contractOwner}},\nThis is to notify that the Contract {{contractExpiryRemainder.contractTitle}} contract will be expiring in {{contractExpiryRemainder.expiringdays}} day/days.\n\nPlease find the details below\nTitle :{{contractExpiryRemainder.ContractTitle}}\nOwner : {{contractExpiryRemainder.contractOwner}}\nStart Date : {{contractExpiryRemainder.contractStartDate}}\nEnd Date : {{contractExpiryRemainder.contractEndDate}}\nContact : {{contractExpiryRemainder.clientContact}}\nReceiver Party : {{contractExpiryRemainder.receiverParty}}\nStatus : {{contractExpiryRemainder.status}}\nContract Type : {{contractExpiryRemainder.contractType}}\n\nTo extend the contract and resign please log in to Source Lead to find more details\n\n{{contractExpiryRemainder.loginUrl}}' WHERE `UNIQUE_SCENARIO_NAME`='contractExpiryRemainder' and `ENTITY_TYPE`='Email';	
			
			
UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='Alert Contract expiring in {{contractExpiryRemainder.expiringdays}} days'  WHERE `UNIQUE_SCENARIO_NAME`='contractExpiryRemainder' and `ENTITY_TYPE`='Email';