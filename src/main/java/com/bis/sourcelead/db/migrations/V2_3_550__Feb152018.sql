
set SQL_SAFE_UPDATES=0;
INSERT INTO `sourcelead`.`LOOKUP_DATA` ( `NAME`, `DESCRIPTION`, `CATEGORY`, `LOCATION`) VALUES ( 'USC', 'Permanent Residence', 'VisaCategory', 'United States');
INSERT INTO `sourcelead`.`LOOKUP_DATA` ( `NAME`, `DESCRIPTION`, `CATEGORY`, `LOCATION`) VALUES ( 'TN', 'Canadians or Mexicans', 'VisaCategory', 'United States');
INSERT INTO `sourcelead`.`LOOKUP_DATA` ( `NAME`, `DESCRIPTION`, `CATEGORY`, `LOCATION`) VALUES ( 'E3', 'Australian', 'VisaCategory', 'United States');
set SQL_SAFE_UPDATES=1;

SET SQL_SAFE_UPDATES = 0;
UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `SUBJECT`='Opening Job position for  {{ShareRecomendedApplicant.jobName}}' WHERE `ID`='316';
set SQL_SAFE_UPDATES=1;