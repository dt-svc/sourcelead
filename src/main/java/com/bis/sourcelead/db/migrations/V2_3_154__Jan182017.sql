UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{InviteUser.FirstName}} {{InviteUser.LastName}},\n\n {{InviteUser.AdministratorFirstName}} {{InviteUser.AdministratorLastName}} invited you to SourceLead as a {{InviteUser.Role}}.\n\n You can simply login into SourceLead by clicking below link with follow credentials..\n\nUsername : {{InviteUser.UserEmailId}}\n\n Password : {{InviteUser.Password}}\n\n {{InviteUser.LoginURL}}' WHERE `ID`='4';

UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{inviteVendor.FirstName}} {{inviteVendor.LastName}},\n\n {{inviteVendor.clientOrgName}} invited you as a Vendor through Sourcelead platform. To login into the system, click on the login button and use the following credentials.\n\n  Username : {{inviteVendor.userName}}\n  Password : {{inviteVendor.Password}}\n\n{{inviteVendor.acceptURL}}' WHERE `ID`='35';

UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{inviteClient.FirstName}} {{inviteClient.LastName}},\n\n {{inviteClient.vendorOrgName}} invited you as a Vendor through Sourcelead platform. To login into the system, click on the login button and use the following credentials.\n\n  Username : {{inviteClient.userName}}\n  Password : {{inviteClient.Password}}\n\n {{inviteClient.acceptURL}}' WHERE `ID`='36';

UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{ApprovalMechanism.approverName}},\n\n Approve the requirement {{ApprovalMechanism.requirementName}}, The brief requirement details are below \n\n  Requirement Name : {{ApprovalMechanism.requirementName}} \n Requirement Location :{{ApprovalMechanism.requirementLocation}}\n Requirement Rate  : {{ApprovalMechanism.requirementRate}}\n Client :  {{ApprovalMechanism.client}}.\n\n For clear details login into the system. \n {{ApprovalMechanism.link}}\n ' WHERE `ID`='46';


 