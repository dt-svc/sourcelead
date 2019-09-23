
set SQL_SAFE_UPDATES=0;
INSERT INTO `LOOKUP_DATA` ( `NAME`, `DESCRIPTION`, `CATEGORY`, `LOCATION`) VALUES ( 'USC', 'Permanent Residence', 'VisaCategory', 'United States');
INSERT INTO `LOOKUP_DATA` ( `NAME`, `DESCRIPTION`, `CATEGORY`, `LOCATION`) VALUES ( 'TN', 'Canadians or Mexicans', 'VisaCategory', 'United States');
INSERT INTO `LOOKUP_DATA` ( `NAME`, `DESCRIPTION`, `CATEGORY`, `LOCATION`) VALUES ( 'E3', 'Australian', 'VisaCategory', 'United States');
set SQL_SAFE_UPDATES=1;
