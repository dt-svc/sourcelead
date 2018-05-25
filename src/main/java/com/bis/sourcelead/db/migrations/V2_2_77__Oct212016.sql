CALL AlterTableRemoveColumn('sourcelead','REQUIREMENT_INTERVIEW','COUNTRY');
CALL AlterTableRemoveColumn('sourcelead','REQUIREMENT_INTERVIEW','STATE');
CALL AlterTableRemoveColumn('sourcelead','REQUIREMENT_INTERVIEW','CITY');

CALL AlterTableAddColumn ('sourcelead','REQUIREMENT_INTERVIEW','LOCATION','TEXT NULL AFTER `END_TIME`');