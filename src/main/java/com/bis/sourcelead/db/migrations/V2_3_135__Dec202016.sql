#Notification                        
                               
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ClienttoVendorAcceptence', 'Notification');
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES 
('Client to Vendor Acceptence', 'Notification', 'Client to Vendor Acceptence', 'Used mail for client to  vendor', 'Congratulations!! Offer from {{ClienttoVendorAcceptence.OrgnanizationName}}', 'sourcelead', '', 'Congratulations!! Offer from {{ClienttoVendorAcceptence.OrgnanizationName}}','N','SLADMIN', 'sourceLead.com', 'in.sourceLead.com');