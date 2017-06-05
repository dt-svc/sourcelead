<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 
 
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
    
	<div class="content-area content-new clearfix">
         
          <div class="clearfix form-horizontal inner-content">
            <div class="row">
              <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
              
                <div class="form-group">
                  <label class="col-md-3">Expenses Id<span class="text-danger" >&nbsp;*</span></label>
                  <div class="col-md-9">
                    <input type="text" class="form-control" name="First Name" value="${ExpensesId}" readonly="readonly">
                  </div>
                </div>  
                
                <div class="form-group">
                  <label class="col-md-3">Purpose</label>
                  <div class="col-md-9">
                    <input type="text" class="form-control" name="First Name" value="">
                  </div>
                </div>                                          

			<div class="form-group">
				<label class="col-md-3">Select Project <span class="text-danger">&nbsp;*</span></label>
				<div class="col-md-9">
					<select class="chosen-select form-control" onchange="getClientBasedOnSelectedProject();" id="projectIdByMemberId">
					<c:forEach var="projMembers" items="${projectMembersList}">
					       <option  selected value="${projMembers.projects.id}">${projMembers.projects.projectName}</option>
					</c:forEach>
					
					  <!-- <option>Select Project</option>
					  <option>Project-1</option>
					  <option>Project-2</option>
					  <option>Project-3</option> -->
					</select>
				</div>
			  </div>
               
			<div class="form-group">
				<label class="col-md-3">Client<span class="text-danger">&nbsp;*</span></label>
                  <div class="col-md-9">
                    <input type="text" class="form-control" name="First Name" value="" id="clientNameId" readonly="readonly">
                  </div>
			  </div>               
                
            <div class="form-group">
              <label class="col-md-3">Start & End date<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-9">
					<div class="row">
					  <div class="col-lg-6 evDaPa">
						  <input type="text" class="form-control calendar" value="" name="Date" id="projectStartDateExpensesId" placeholder="Start date" readonly="readonly">
					  </div>
					  <div class="col-lg-6">
						  <input type="text" class="form-control calendar" value="" name="Date" id="projectEndDateExpensesId" placeholder="End date" readonly="readonly">
					  </div>					  
					</div>
              </div>
            </div>
               
		</div>
	</div>
			</div>
  
<div class="expensHead">
	<a href="#" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#addTask" onclick="loadCategoryDiv();"><i class="fa fa-plus"></i>&nbsp; Row</a>
	<div class="clearfix"></div>
</div>

<jsp:include page="addExpenseEntries.jsp"></jsp:include>


                
<div class="form-footer clearfix mt">
		  <div class="col-lg-12">
			<div class="pull-right">
			  <input type="button" value="Save" class="btn btn-sm btn-primary">
			  <input type="button" value="Sub for Approval" class="btn btn-sm btn-success ml10">
			  <input type="button" value="Cancel" class="btn btn-sm btn-default ml10">
			</div>
		  </div>
		</div>
          
      <!-- Content Area Ends--> 
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
// Error pop
$(document).ready(function() {
    $(".glyphicon-trash").click(function(){
		$(".vendor_error").slideToggle(200);
		$(".vendor_wrap_mask").fadeIn(200);
	});
	$(".vendor_error a").click(function(){
		$(".vendor_error").slideUp(200);
		$(".vendor_wrap_mask").fadeOut(200);
	});
});	
</script>    

     <script>
  $( function() {
    $( "#datepicker,#datepicker2" ).datepicker();
	$("#datepicker5" ).datepicker();
  } );
  </script>
  

  <script type="text/javascript">
			var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
			for (var selector in config) {
			  $(selector).chosen(config[selector]);
			}
			$('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
				});
 		</script>


<script>
function getClientBasedOnSelectedProject(){
	var sourceleadUrl ='${sourceleadUrl}';
	var projectId = document.getElementById("projectIdByMemberId").value;
	$.ajax({
	      type : "GET",
	       url: sourceleadUrl+"/getClientByProjectId?projectId="+projectId,       
	       success :function(data) {
	    	   if(data != null && data !='undefined' && data !=''){
	    	   var json = JSON.parse(data);
		    	  if(json.expensesClientName != null && json.expensesClientName !='undefined' && json.expensesClientName !=''){
		    	   		$('#clientNameId').val(json.expensesClientName);
		    	   }else{
		    	   		$('#clientNameId').val('');   
		    	   }
		    	  if(json.startDateStr != null && json.startDateStr !='undefined' && json.startDateStr !='' && json.endDateStr != null && json.endDateStr !='undefined' && json.endDateStr !=''){
		    		  $('#projectStartDateExpensesId').val(json.startDateStr);
		    		  $('#projectEndDateExpensesId').val(json.endDateStr);
		    		  	var projectStartDate = json.startDateStr;
		    			var projectEndDate = json.endDateStr;
		    			expensesDatePickerStartDate(projectStartDate,projectEndDate);
		    	  }else{
					  $('#projectStartDateExpensesId').val(json.startDateStr);
					  $('#projectEndDateExpensesId').val(json.endDateStr);
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
	function expensesDatePickerStartDate(projectStartDate,projectEndDate){
		$("#projectStartDateExpensesId").datepicker("destroy");
		$("#projectEndDateExpensesId").datepicker("destroy");
		$('#projectStartDateExpensesId').datepicker({
			dateFormat : 'dd-mm-yy',
			changeMonth: true,
			changeYear: true, 
			minDate:projectStartDate,
			maxDate:projectEndDate,
			onSelect : function(selected) {
				var dt = toDate('#projectStartDateExpensesId');
				dt.setDate(minDate.getDate() + 1);
				$("#projectEndDateExpensesId").datepicker("option", "minDate", dt);
			}
		});
		$('#projectEndDateExpensesId').datepicker({
			dateFormat : 'dd-mm-yy',
			changeMonth: true,
			changeYear: true,
			minDate:projectStartDate,
			maxDate:projectEndDate,
			onSelect : function(selected) {
				var dt = toDate('#projectEndDateExpensesId');
				dt.setDate(dt.getDate() - 1);
				$("#projectStartDateExpensesId").datepicker("option", "minDate", dt);
			}
		});
	}
</script>




 
<jsp:include page="../masterFooter.jsp" />