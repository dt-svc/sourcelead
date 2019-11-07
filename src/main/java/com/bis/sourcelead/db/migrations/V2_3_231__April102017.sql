#notification for client,vendor rate negotiation

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ClientRateNegotiation', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
			('ClientRateNegotiation', 'Notification', 'ClientRateNegotiation', 'notification from vendor to client', 'New Rate request for the {{ClientRateNegotiation.candidateName}} to {{ClientRateNegotiation.requirementName}}', 'sourcelead', '', 'New request for the {{ClientRateNegotiation.candidateName}} to {{ClientRateNegotiation.requirementName}}','N','SLADMIN', 'com', 'in.com');

#notification for client acceptance rate negotiation

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ClientAcceptRateNegotiation', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
			('ClientAcceptRateNegotiation', 'Notification', 'ClientAcceptRateNegotiation', 'notify when Client Accepts rate', 'Requested Rate accepted for {{ClientAcceptRateNegotiation.applicantName}} to {{ClientAcceptRateNegotiation.requirementName}}', 'sourcelead', '', 'Requested Rate accepted for {{ClientAcceptRateNegotiation.applicantName}} to {{ClientAcceptRateNegotiation.requirementName}}','N','SLADMIN', 'com', 'in.com');

#notification for vendor acceptance rate negotiation

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('VendorAcceptRateNegotiation', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
			('VendorAcceptRateNegotiation', 'Notification', 'VendorAcceptRateNegotiation', 'notify when vendor Accepts rate', 'Requested Rate accepted for {{VendorAcceptRateNegotiation.applicantName}} to {{VendorAcceptRateNegotiation.requirementName}}', 'sourcelead', '', 'Requested Rate accepted for {{ClientAcceptRateNegotiation.applicantName}} to {{ClientAcceptRateNegotiation.requirementName}}','N','SLADMIN', 'com', 'in.com');

#notification for client rejectance rate negotiation

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ClientRejectRateNegotiation', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
			('ClientRejectRateNegotiation', 'Notification', 'ClientRejectRateNegotiation', 'notify when client Rejects rate', 'Requested Rate rejected for {{ClientRejectRateNegotiation.applicantName}} to {{ClientRejectRateNegotiation.requirementName}}', 'sourcelead', '', 'Requested Rate rejected for {{ClientRejectRateNegotiation.applicantName}} to {{ClientRejectRateNegotiation.requirementName}}','N','SLADMIN', 'com', 'in.com');

#notification for vendor rejectance rate negotiation

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('VendorRejectRateNegotiation', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
			('VendorRejectRateNegotiation', 'Notification', 'VendorRejectRateNegotiation', 'notify when vendor Rejects rate', 'Requested Rate rejected for {{VendorRejectRateNegotiation.applicantName}} to {{VendorRejectRateNegotiation.requirementName}}', 'sourcelead', '', 'Requested Rate rejected for {{VendorRejectRateNegotiation.applicantName}} to {{VendorRejectRateNegotiation.requirementName}}','N','SLADMIN', 'com', 'in.com');
