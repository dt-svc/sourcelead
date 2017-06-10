<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 
 <jsp:include page="addExpenseEntries.jsp"></jsp:include>
 
 <script type="text/javascript">
		window.onload = function () {
			getClientBasedOnSelectedProject();
		 };
</script>
<div class="wraper"> 
  <!-- Content Area -->
  <section class="content-wraper" id="themeroller">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Add Expense</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; My Expenses &raquo; Add Expense</div>
    </section>
    <form:form id="add_expenses_form" name="add_expenses_form"  enctype='multipart/form-data'>
	<div class="content-area content-new clearfix">
         
          <div class="clearfix form-horizontal inner-content">
            <div class="row">
              <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
              
                <div class="form-group">
                  <label class="col-md-3">Expenses Id<span class="text-danger" >&nbsp;*</span></label>
                  <div class="col-md-9">
                    <input type="text" class="form-control" name="ExpenseAutoId" value="${expenses != null ? expenses.expenseId : ExpensesId}" readonly="readonly">
                    <input type="text" style="display: none;" class="form-control" name="expenseId" value="${expenses.id}">
                  </div>
                </div>  
                <div class="form-group">
                  <label class="col-md-3">Purpose</label>
                  <div class="col-md-9">
                    <input type="text" class="form-control" name="expensePurpose" value="${expenses.purpose}">
                  </div>
                </div>                                          
				<div class="form-group">
					<label class="col-md-3">Select Project <span class="text-danger">&nbsp;*</span></label>
					<div class="col-md-9">
						<select class="chosen-select form-control" onchange="getClientBasedOnSelectedProject();" id="projectMemberId" name="projectMemberId">
							<c:forEach var="projMembers" items="${projectMembersList}">
							       <option  ${expenses.projectMembers.id == projMembers.id ? 'selected' : '' } value="${projMembers.id}">${projMembers.projects.projectName}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-3">Client<span class="text-danger">&nbsp;*</span></label>
	                  <div class="col-md-9">
	                    <input type="text" class="form-control" name="clientNameExpense" value="" id="clientNameId" readonly="readonly">
	                  </div>
				</div>               
	            <div class="form-group">
	              <label class="col-md-3">Start & End date<span class="text-danger">&nbsp;*</span></label>
	              <div class="col-md-9" >
						<div class="row">
						  <div class="col-lg-6 evDaPa" id="dateDiv">
							  <input type="text" class="form-control calendar" data-validation="required" value="${expenses.startDateStr}" field-name="Start Date" name="startDateExpense" id="projectStartDateExpensesId" placeholder="Start Date" readonly="readonly">
						  </div>
						  <div class="col-lg-6">
							  <input type="text" class="form-control calendar" value="${expenses.endDateStr}" name="endDateExpense"  field-name="End Date" id="projectEndDateExpensesId" placeholder="End Date" readonly="readonly">
						  </div>					  
						</div>
	              </div>
	            </div>
               
		</div>
	</div>
</div>
<div class="expensHead">
    <input type="hidden"  name="maxRecCount" id="maxRecCount" value="1">
	<a href="#" class="btn btn-primary btn-sm AddRow" onclick='addExpenseRow();'><i class="fa fa-plus"></i>&nbsp; Add Row</a>
	<div class="clearfix"></div>
</div>
<div class="project_wrap_table expensTableView">
	<table id="categoryTable">
	    <thead>
			<tr id="divEntityTsRow0">
				<th>Exp Category</th>
				<th>Date</th>
				<th>Payment Mode</th>
				<th>Currency</th>
				<th>Amount</th>
				<th>Description</th>
				<th>Action</th>
			</tr>
		</thead>	
			<tbody>
		   </tbody>
	</table>
</div>
<div class="form-footer clearfix mt">
		  <div class="col-lg-12">
			<div class="pull-right">
			  <input type="button" value="Save" class="btn btn-sm btn-primary" onclick="saveAddExpensesData('draft');">
			  <input type="button" id ="submitForApprovalExpensesId"value="Sub for Approval" class="btn btn-sm btn-success ml10" onclick="saveAddExpensesData('submitted');">
			  <input type="button" value="Cancel" class="btn btn-sm btn-default ml10">
			</div>
		  </div>
		</div>
                
          
      <!-- Content Area Ends--> 
	  </div>
</form:form>
  </section>

<script>
	function saveAddExpensesData(status) {
		if ($('#add_expenses_form').validate(false, validationSettings)){
			var frm = $('#add_expenses_form').serializeFormJSON();
			var jsonData = JSON.stringify(frm);
			var form = $('#add_expenses_form')[0];
      		var formData = new FormData(form); 
      		formData.append("json",jsonData);
			var sourceleadUrl = '${sourceleadUrl}';
			$( "#submitForApprovalExpensesId" ).addClass('button').val('Processing..');
			window.location.replace(sourceleadUrl+'/expenses');
     		$.ajax({
       	        type : "POST",
        	    data: formData,
        	    contentType : 'application/json; charset=utf-8',
        	    enctype: 'multipart/form-data',
    	        processData: false,
    	        contentType:false,
        	    dataType : 'json',
				url : sourceleadUrl+"/storeAddExpensesData?${_csrf.parameterName}=${_csrf.token}&status="+status,
				success : function(data) {
					$( "#submitForApprovalExpensesId" ).addClass('button').val('Sub for Approval');
				},
			});
			return true;
		}else{
			return false;
		}
	}
</script>

<script>
	var validationSettings = {
		errorMessagePosition : 'element',
		borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd'
	};
</script>
<script>
	$(function() {
		$("#datepicker,#datepicker2").datepicker();
		$("#datepicker5").datepicker();
	});
</script>
<script type="text/javascript">
	var config = {
		'.chosen-select' : {},
		'.chosen-select-deselect' : {
			allow_single_deselect : true
		},
		'.chosen-select-no-single' : {
			disable_search_threshold : 10
		},
		'.chosen-select-no-results' : {
			no_results_text : 'Oops, nothing found!'
		},
		'.chosen-select-width' : {
			width : "95%"
		}
	}
	for ( var selector in config) {
		$(selector).chosen(config[selector]);
	}
	$('.chosen-select').each(function() {
		var $this = $(this);
		$this.next().css({
			'width' : '100%'
		});
	});
</script>


<script>
function getClientBasedOnSelectedProject(){
	var sourceleadUrl ='${sourceleadUrl}';
	var projectMemberId = document.getElementById("projectMemberId").value;
	$.ajax({
	      type : "GET",
	       url: sourceleadUrl+"/getClientByProjectMemberId?projectMemberId="+projectMemberId,       
	       success :function(data) {
	    	   if(data != null && data !='undefined' && data !=''){
	    	   var json = JSON.parse(data);
		    	  if(json.expensesClientName != null && json.expensesClientName !='undefined' && json.expensesClientName !=''){
		    	   		$('#clientNameId').val(json.expensesClientName);
		    	   }else{
		    	   		$('#clientNameId').val('');   
		    	   }
		    	  if(json.startDateStr != null && json.startDateStr !='undefined' && json.startDateStr !='' && json.endDateStr != null && json.endDateStr !='undefined' && json.endDateStr !=''){
		    		  	var projectStartDate = json.startDateStr;
		    			var projectEndDate = json.endDateStr;
		    			expensesDatePickerStartDate(projectStartDate,projectEndDate);
		    	  }else{
					  var projectStartDate = json.startDateStr;
					  var projectEndDate = json.endDateStr;
					  expensesDatePickerStartDate(projectStartDate,projectEndDate);
		    	  }		
	    	   }
	       },
	});
}
</script>

<script>
	function expensesDatePickerStartDate(projectStartDate, projectEndDate) {
		$("#projectStartDateExpensesId").datepicker("destroy");
		$("#projectEndDateExpensesId").datepicker("destroy");
		$('#projectStartDateExpensesId').datepicker({
					dateFormat : 'dd-mm-yy',
					changeMonth : true,
					changeYear : true,
					minDate : projectStartDate,
					maxDate : projectEndDate,
					onSelect : function(selected) {
						$("#projectEndDateExpensesId").datepicker("option","minDate", selected);
					}
				});
		$('#projectEndDateExpensesId').datepicker({
					dateFormat : 'dd-mm-yy',
					changeMonth : true,
					changeYear : true,
					minDate : projectStartDate,
					maxDate : projectEndDate,
					onSelect : function(selected) {
						$("#projectStartDateExpensesId").datepicker("option","maxDate", selected);
					}
				});
	}
</script>

<script>
function expensesDateCheck(){
	$('#dateError').remove();
	var startDate = document.getElementById("projectStartDateExpensesId").value;
	var endDate = document.getElementById("projectEndDateExpensesId").value;
	if(startDate != null && startDate != "" && startDate != 'undefined'){
		loadCategoryDiv(startDate,endDate,null);
	}else{
		$("#dateDiv").parent().after("<p id='dateError' class='jquery_form_error_message'> Project Start Date is mandatory </p>");
		$('#addExpenseEntity').modal({
		   backdrop: 'static',
		   keyboard: false
		});
	}
}

</script>
<jsp:include page="../masterFooter.jsp" />
