UPDATE `sourcelead`.`LOOKUP_DATA` SET `NAME`='S Corporation', `DESCRIPTION`='S Corporation' WHERE `ID`='1080';
INSERT INTO `sourcelead`.`LOOKUP_DATA` ( `NAME`, `DESCRIPTION`, `CATEGORY`) VALUES ( 'Sole Proprietorship', 'Sole Proprietorship', 'Company Onboard Business type');
INSERT INTO `sourcelead`.`LOOKUP_DATA` ( `NAME`, `DESCRIPTION`, `CATEGORY`) VALUES ( 'Private Limited', 'Private Limited', 'Company Onboard Business type');
INSERT INTO `sourcelead`.`LOOKUP_DATA` ( `NAME`, `DESCRIPTION`, `CATEGORY`) VALUES ( 'Public Limited', 'Public Limited', 'Company Onboard Business type');


CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG_LOCATIONS','PO_EMAIL','VARCHAR(500) NULL');
CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG_LOCATIONS','PAYMENT_REMITTANCE_EMIL','VARCHAR(500) NULL');