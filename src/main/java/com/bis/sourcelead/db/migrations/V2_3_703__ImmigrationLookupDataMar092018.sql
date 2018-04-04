
INSERT INTO `sourcelead`.`LOOKUP_DATA` (`NAME`,`DESCRIPTION`,`CATEGORY`) VALUES ('Request to fill','Request to fill','USCISStatus');
INSERT INTO `sourcelead`.`LOOKUP_DATA` (`NAME`,`DESCRIPTION`,`CATEGORY`) VALUES ('Filled','Filled','USCISStatus');
INSERT INTO `sourcelead`.`LOOKUP_DATA` (`NAME`,`DESCRIPTION`,`CATEGORY`) VALUES ('Reviewed & Send to attorney','Reviewed & Send to attorney','USCISStatus');
INSERT INTO `sourcelead`.`LOOKUP_DATA` (`NAME`,`DESCRIPTION`,`CATEGORY`) VALUES ('Request for modifications','Request for modifications','USCISStatus');
INSERT INTO `sourcelead`.`LOOKUP_DATA` (`NAME`,`DESCRIPTION`,`CATEGORY`) VALUES ('Reviewed & Submitted','Reviewed & Submitted','USCISStatus');
INSERT INTO `sourcelead`.`LOOKUP_DATA` (`NAME`,`DESCRIPTION`,`CATEGORY`) VALUES ('Case Received & Emailed','Case Received & Emailed','USCISStatus');
INSERT INTO `sourcelead`.`LOOKUP_DATA` (`NAME`,`DESCRIPTION`,`CATEGORY`) VALUES ('Case Received','Case Received','USCISStatus');
INSERT INTO `sourcelead`.`LOOKUP_DATA` (`NAME`,`DESCRIPTION`,`CATEGORY`) VALUES ('Request for Additional Evidence mailed','Request for Additional Evidence mailed','USCISStatus');
INSERT INTO `sourcelead`.`LOOKUP_DATA` (`NAME`,`DESCRIPTION`,`CATEGORY`) VALUES ('Response for Request for Evidence Received','Response for Request for Evidence Received','USCISStatus');
INSERT INTO `sourcelead`.`LOOKUP_DATA` (`NAME`,`DESCRIPTION`,`CATEGORY`) VALUES ('Case Approved And Decision Emailed','Case Approved And Decision Emailed','USCISStatus');
INSERT INTO `sourcelead`.`LOOKUP_DATA` (`NAME`,`DESCRIPTION`,`CATEGORY`) VALUES ('Case Approved','Case Approved','USCISStatus');
INSERT INTO `sourcelead`.`LOOKUP_DATA` (`NAME`,`DESCRIPTION`,`CATEGORY`) VALUES ('Decision Mailed','Decision Mailed','USCISStatus');
INSERT INTO `sourcelead`.`LOOKUP_DATA` (`NAME`,`DESCRIPTION`,`CATEGORY`) VALUES ('Invalid Receipt Number','Invalid Receipt Number','USCISStatus');
INSERT INTO `sourcelead`.`LOOKUP_DATA` (`NAME`,`DESCRIPTION`,`CATEGORY`) VALUES ('Name was updated','Name was updated','USCISStatus');
INSERT INTO `sourcelead`.`LOOKUP_DATA` (`NAME`,`DESCRIPTION`,`CATEGORY`) VALUES ('Fees will be Refunded','Fees will be Refunded','USCISStatus');
INSERT INTO `sourcelead`.`LOOKUP_DATA` (`NAME`,`DESCRIPTION`,`CATEGORY`) VALUES ('Completed','Completed','USCISStatus');
INSERT INTO `sourcelead`.`LOOKUP_DATA` (`NAME`,`DESCRIPTION`,`CATEGORY`) VALUES ('Rejected','Rejected','USCISStatus');



INSERT INTO `sourcelead`.`LOOKUP_DATA` (`NAME`,`DESCRIPTION`,`CATEGORY`) VALUES ('Level 1','Level 1','EmploymentLevel');
INSERT INTO `sourcelead`.`LOOKUP_DATA` (`NAME`,`DESCRIPTION`,`CATEGORY`) VALUES ('Level 2','Level 2','EmploymentLevel');
INSERT INTO `sourcelead`.`LOOKUP_DATA` (`NAME`,`DESCRIPTION`,`CATEGORY`) VALUES ('Level 3','Level 3','EmploymentLevel');
INSERT INTO `sourcelead`.`LOOKUP_DATA` (`NAME`,`DESCRIPTION`,`CATEGORY`) VALUES ('Level 4','Level 4','EmploymentLevel');

INSERT INTO `sourcelead`.`LOOKUP_DATA` (`NAME`,`DESCRIPTION`,`CATEGORY`) VALUES ('H1B Filing (Cap/H1) or H1 Transfer','H1B Filing (Cap/H1) or H1 Transfer','PetitionType');
INSERT INTO `sourcelead`.`LOOKUP_DATA` (`NAME`,`DESCRIPTION`,`CATEGORY`) VALUES ('H1B Filing (Amendment)','H1B Filing (Amendment)','PetitionType');
INSERT INTO `sourcelead`.`LOOKUP_DATA` (`NAME`,`DESCRIPTION`,`CATEGORY`) VALUES ('H1B Filing (Extension)','H1B Filing (Extension)','PetitionType');
INSERT INTO `sourcelead`.`LOOKUP_DATA` (`NAME`,`DESCRIPTION`,`CATEGORY`) VALUES ('Green Card','Green Card','PetitionType');

INSERT INTO `sourcelead`.`LOOKUP_DATA` (`NAME`,`DESCRIPTION`,`CATEGORY`) VALUES ('C2C','C2C','EmploymentType');
INSERT INTO `sourcelead`.`LOOKUP_DATA` (`NAME`,`DESCRIPTION`,`CATEGORY`) VALUES ('W2','W2','EmploymentType');
INSERT INTO `sourcelead`.`LOOKUP_DATA` (`NAME`,`DESCRIPTION`,`CATEGORY`) VALUES ('Full Time','Full Time','EmploymentType');
INSERT INTO `sourcelead`.`LOOKUP_DATA` (`NAME`,`DESCRIPTION`,`CATEGORY`) VALUES ('Part Time','Part Time','EmploymentType');
INSERT INTO `sourcelead`.`LOOKUP_DATA` (`NAME`,`DESCRIPTION`,`CATEGORY`) VALUES ('1099','1099','EmploymentType');
INSERT INTO `sourcelead`.`LOOKUP_DATA` (`NAME`,`DESCRIPTION`,`CATEGORY`) VALUES ('C2C-1099','C2C-1099','EmploymentType');
INSERT INTO `sourcelead`.`LOOKUP_DATA` (`NAME`,`DESCRIPTION`,`CATEGORY`) VALUES ('C2H','C2H','EmploymentType');
INSERT INTO `sourcelead`.`LOOKUP_DATA` (`NAME`,`DESCRIPTION`,`CATEGORY`) VALUES ('C2H-1099','C2H-1099','EmploymentType');


CALL sourcelead.AlterTableAddColumn ('sourcelead','IMMIGRATION','ORG_LOCATION_ID','BIGINT NULL AFTER `ORG_SPOC_ID`');
ALTER TABLE sourcelead.`IMMIGRATION` ADD CONSTRAINT `ORG_LOCATION_ID_FK8` FOREIGN KEY (`ORG_LOCATION_ID`) REFERENCES `sourcelead`.`ORG_LOCATIONS` (`ID`);

set sql_safe_updates=0;
UPDATE `sourcelead`.`LOOKUP_DATA` SET `LOCATION`='United States' WHERE `CATEGORY`='PetitionType';
set sql_safe_updates=1;