CALL sourcelead.AlterTableAddColumn ('sourcelead','CONTRACTS','FILE_URL','VARCHAR(2000) NULL AFTER `FILE_NAME`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','CONTRACT_REQUEST_DOCUMENTS','FILE_URL','VARCHAR(2000) NULL AFTER `FILE_NAME`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','CONTRACT_REQUEST_DOCUMENTS','DOC_FILE_NAME','VARCHAR(2000) NULL AFTER `FILE_URL`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','INVOICE_DOCUMENTS','FILE_URL','VARCHAR(2000) NULL AFTER `UPDATED_BY`');