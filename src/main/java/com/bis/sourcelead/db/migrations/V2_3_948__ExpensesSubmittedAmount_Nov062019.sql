SET SQL_SAFE_UPDATES = 0;
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear [Employee_User],\nWe are pleased to inform you that your proposal for expenses submitted on [Submit_For_Approval_Date] has been approved.\nExpense id : [Expense_Id]\nProject : [Project_Name]\nStart Date : [Start_Date]\nEnd Date : [End_Date]\nSubmitted Amount : [Submitted_Amount]\nApproved Amount: [Amount]\nFor brief details click on following link [Login_Link]' WHERE `UNIQUE_SCENARIO_NAME`='ExpensesApprovalManagerEmailToEmployee' and `ENTITY_TYPE`='Email';
SET SQL_SAFE_UPDATES = 1;
