CALL sourcelead.AlterTableAddColumn ('sourcelead','USER','FILE_NAME','VARCHAR(250) NULL AFTER `RECRUITER_REGISTER_COUNTRY`');
				 
CALL sourcelead.AlterTableAddColumn ('sourcelead','USER','FILE_URL','VARCHAR(4000) NULL AFTER `FILE_NAME`');