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

<script type="text/javascript">
function validate_fileupload(fileName)
{

    var allowed_extensions = new Array("ppt", "pptx", "pps", "ppsx", "doc", "docx", "pdf", "rtf", "odt", "xls", "xlsx", "csv", "zip", "txt", "jpg","jpeg","bmp","png","gif","eps","tiff","TGA","psd","svg","msg","wpd","wps");
    var file_extension = fileName.split('.').pop(); 

    for(var i = 0; i <= allowed_extensions.length; i++)
    {
        if(allowed_extensions[i] != null && allowed_extensions[i] != '' && allowed_extensions[i] != 'undefined' && allowed_extensions[i]==file_extension.toLowerCase())
        {
            return true; // valid file extension
        }
    }

    return false;
}
</script>
 <script type="text/javascript">
 function validateUploadFile(id)
	{
		 var selectedFile = $('#'+id+'docFile').val();
		  if(validate_fileupload(selectedFile)){
			   var docSize = $('#'+id+'docFile')[0].files[0].size;
		       $("#"+id+"docFileuploadFileGroup").find('p.jquery_form_error_message').remove();
		      if(docSize > 2097152){
		    	  $('#'+id+'docFileuploadFileGroup').find('p.jquery_form_error_message').remove();
		    	  $('#'+id+'docFileuploadFileGroup').find('i').after("<p class='jquery_form_error_message'>Limit is exceeded than 2MB </span>");
				  $('#'+id+'docFile').attr('record-exist','yes');
				  $('#'+id+'docFile').attr('record-exist-errorMsg','Limit is exceeded than 2MB'); 
		      }else{
		    	  $('input#'+id+'docFile').removeAttr( "record-exist record-exist-errormsg");
	   		       $('#'+id+'docFileuploadFileGroup').find('p.jquery_form_error_message').remove(); 
		      }
		  }
		  else{
			      $('#'+id+'docFileuploadFileGroup').find('p.jquery_form_error_message').remove();
			      $('#'+id+'docFileuploadFileGroup').find('i').after("<p class='jquery_form_error_message'>Invalid Format</span>");
				  $('#'+id+'docFile').attr('record-exist','yes');
				  $('#'+id+'docFile').attr('record-exist-errorMsg','Invalid Format'); 
		  }
		
	 }
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
                  </div>
                </div>  
                <div class="form-group">
                  <label class="col-md-3">Purpose</label>
                  <div class="col-md-9">
                    <input type="text" class="form-control" name="expensePurpose">
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
<div class="expensHead" id="expenseAddRowId">
	<a href="javascript:void(0)" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#addExpenseEntity" onclick="expensesDateCheck();"><i class="fa fa-plus"></i>&nbsp; Row</a>
	<div class="clearfix"></div>
</div>


<div class="modal fade" id="addExpenseEntity" role="dialog"></div>

<div class="project_wrap_table expensTable">
	<table id="entriesTable">
		<thead>
			<tr>
				<th>Exp Category</th>
				<th>Date</th>
				<th>Issued By</th>
				<th>Currency</th>
				<th>Amount</th>
				<th>Description</th>
				<th>Action</th>
			</tr>
		</thead>
		<tbody id="divBody"> </tbody>
	</table>
</div>

<input type="text" style="display: none;" name="maxRecCount" id="maxRecCount" value="">
                
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
<div id="divAttachmentTd1" style="display: none; float: left;">
	   <div id='REPLACEROWIDdocFileuploadFileGroup'>
 			<input type='file' name='REPLACEROWIDdocFile' id='REPLACEROWIDdocFile' data-validation="required"  field-name="Attachment" class="timesheetAttachmentValidation" onchange='validateUploadFile(REPLACEROWID)'> 
 			<i class="fa fa-times" onclick="removeAttachmentRow(REPLACEROWID);"></i>
 		</div>
 </div>
  </section>
  <!-- Content Area Ends--> 
  <!--   Error Message   -->
  <div class="vendor_wrap_mask"></div>
  <div class="vendor_error">
    <textarea placeholder="Enter message"></textarea>
    <a class="close_color" href="javascript:void(0)">Yes</a><a href="javascript:void(0)">No</a> </div>
  <!--   Error Message   --> 
</div>

<script>
	function saveAddExpensesData(status) {
		$("#maxRecCount").val(recCount);
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
	// Error pop
	$(document).ready(function() {
		$(".glyphicon-trash").click(function() {
			$(".vendor_error").slideToggle(200);
			$(".vendor_wrap_mask").fadeIn(200);
		});
		$(".vendor_error a").click(function() {
			$(".vendor_error").slideUp(200);
			$(".vendor_wrap_mask").fadeOut(200);
		});
	});
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
						var dt = toDate('#projectStartDateExpensesId');
						dt.setDate(minDate.getDate() + 1);
						$("#projectEndDateExpensesId").datepicker("option","minDate", dt);
					}
				});
		$('#projectEndDateExpensesId').datepicker({
					dateFormat : 'dd-mm-yy',
					changeMonth : true,
					changeYear : true,
					minDate : projectStartDate,
					maxDate : projectEndDate,
					onSelect : function(selected) {
						var dt = toDate('#projectEndDateExpensesId');
						dt.setDate(dt.getDate() - 1);
						$("#projectStartDateExpensesId").datepicker("option","minDate", dt);
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
