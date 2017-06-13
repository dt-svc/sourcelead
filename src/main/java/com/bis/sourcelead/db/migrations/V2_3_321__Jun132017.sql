
CALL sourcelead.AlterTableAddColumn ('sourcelead','ON_BOARDING_TEMPLATE_DOCUMENTS','UPLOADED_DOC_FILE','MEDIUMBLOB NULL AFTER `FILE_NAME`');

CALL sourcelead.AlterTableAddColumn ('sourcelead','ON_BOARDING_TEMPLATE_DOCUMENTS','UPLOADED_DOC_FILE_CONTENT_TYPE','VARCHAR(500) NULL  AFTER `UPLOADED_DOC_FILE`');

CALL sourcelead.AlterTableAddColumn ('sourcelead','ON_BOARDING_TEMPLATE_DOCUMENTS','UPLOADED_DOC_FILE_NAME','TEXT NULL AFTER `UPLOADED_DOC_FILE_CONTENT_TYPE`');


UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `BODY`='Dear	{{TaskMechanism.assignedUser}},\n\nPriority:{{TaskMechanism.priority}}\nDate:{{TaskMechanism.date}}\nTime:{{TaskMechanism.time}}\nDescription:{{TaskMechanism.description}}\n' WHERE `ID`='93';
