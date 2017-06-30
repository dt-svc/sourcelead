 CALL sourcelead.AlterTableAddColumn ('sourcelead','CANDIDATE','LOCATION','VARCHAR(100) NULL AFTER `ADDRESS`');
 CALL sourcelead.AlterTableAddColumn ('sourcelead','CANDIDATE','ZIPCODE','VARCHAR(20) NULL AFTER `LOCATION`');
 
 CALL sourcelead.AlterTableAddColumn ('sourcelead','CANDIDATE','LATITUDE','VARCHAR(50) NULL AFTER `ZIPCODE`');
 CALL sourcelead.AlterTableAddColumn ('sourcelead','CANDIDATE','LONGITUDE','VARCHAR(50) NULL AFTER `LATITUDE`');
 
 

