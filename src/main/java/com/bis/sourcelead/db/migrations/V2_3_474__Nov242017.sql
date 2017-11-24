
ALTER TABLE `sourcelead`.`asset` 
DROP COLUMN `ATTACHMENT`;

ALTER TABLE `sourcelead`.`asset` 
DROP COLUMN `SHIFT_A_ATTACHMENT`;

ALTER TABLE `sourcelead`.`asset` 
DROP COLUMN `SHIFT_B_ATTACHMENT`;

ALTER TABLE `sourcelead`.`bo_uploaded_files` 
DROP COLUMN `DOC_FILE`;

ALTER TABLE `sourcelead`.`candidate` 
DROP COLUMN `DOC_FILE`;

ALTER TABLE `sourcelead`.`candidate_documents` 
DROP COLUMN `DOC_FILE`;

ALTER TABLE `sourcelead`.`candidate_documents_history` 
DROP COLUMN `DOC_FILE`;

ALTER TABLE `sourcelead`.`candidate_history` 
DROP COLUMN `DOC_FILE`;

ALTER TABLE `sourcelead`.`candidate_resumes` 
DROP COLUMN `DOC_FILE`;

ALTER TABLE `sourcelead`.`candidate_resumes_history` 
DROP COLUMN `DOC_FILE`;

ALTER TABLE `sourcelead`.`contract_request_documents` 
DROP COLUMN `DOC_FILE`;

ALTER TABLE `sourcelead`.`contracts` 
DROP COLUMN `DOC_FILE`;

ALTER TABLE `sourcelead`.`expenses_entries_documents` 
DROP COLUMN `DOC_FILE`;

ALTER TABLE `sourcelead`.`invoice` 
DROP COLUMN `DOC_FILE`;

ALTER TABLE `sourcelead`.`invoice_documents` 
DROP COLUMN `DOC_FILE`;

ALTER TABLE `sourcelead`.`invoice_payment` 
DROP COLUMN `DOC_FILE`;