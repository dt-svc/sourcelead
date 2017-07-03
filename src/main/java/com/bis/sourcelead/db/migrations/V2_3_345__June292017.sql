 CALL sourcelead.AlterTableAddColumn ('sourcelead','CANDIDATE','LOCATION','VARCHAR(100) NULL AFTER `ADDRESS`');
 CALL sourcelead.AlterTableAddColumn ('sourcelead','CANDIDATE','ZIPCODE','VARCHAR(20) NULL AFTER `LOCATION`');
 
 CALL sourcelead.AlterTableAddColumn ('sourcelead','CANDIDATE','LATITUDE','VARCHAR(50) NULL AFTER `ZIPCODE`');
 CALL sourcelead.AlterTableAddColumn ('sourcelead','CANDIDATE','LONGITUDE','VARCHAR(50) NULL AFTER `LATITUDE`');
 
 

 CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG_LOCATIONS','LOCATION','VARCHAR(100) NULL AFTER `ZIP_CODE`');
 
 CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG_LOCATIONS','LATITUDE','VARCHAR(50) NULL AFTER `LOCATION`');
 CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG_LOCATIONS','LONGITUDE','VARCHAR(50) NULL AFTER `LATITUDE`');