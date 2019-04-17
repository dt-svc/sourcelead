 <div class="clearfix"></div>
  <div class="timesheet_out" id="loansAdvancesDiv">

<div class="tablelist-wrap">


<div class="col-md-12">
<ul class="list-unstyled mt row">
<li class="col-md-3" title="Outstanding" onClick="outstandingFun(this)">
	<div class="cardModel-widget text-center cursorPointer currentActive" id="outstanding" onclick="EmployeeLoansAndAdvances('outstanding');">
		<h5 class="margin-top0">Outstanding</h5>
		<p class="text-muted mb0"><i class="fa fa-rupee paddingRight" id="outstandingAmount"></i></p>
	</div>
</li>
<li class="col-md-3"  title="Completed" onClick="completedFun(this)">
	<div class="cardModel-widget text-center cursorPointer" id="completed" onclick="EmployeeLoansAndAdvances('completed');">
				<h5 class="margin-top0">Completed</h5>
		<p class="text-muted mb0"><i class="fa fa-rupee paddingRight" id="completedAmount"></i></p>

	</div>
</li>
<li class="col-md-3" title="Request" onClick="requestFun(this)">
	<div class="cardModel-widget text-center cursorPointer" id="request" onclick="EmployeeLoansAndAdvances('requested');">
				<h5 class="margin-top0">Request</h5>
		<p class="text-muted mb0"><i class="fa fa-rupee paddingRight" id="requestAmount"></i></p>

	</div>
</li>
</ul>
</div>
<div class="clearfix"></div>
   
<div class="styleTables clearfix mt" id="outStadingTable">

<div class="candidate_wrap_table datatable_filter">
	<div id="outStadingTableDiv"></div>
</div>

</div>

<div class="styleTables clearfix mt" id="completedTable" style="display: none">

<div class="candidate_wrap_table datatable_filter">
</div>
           
</div>

<div class="styleTables clearfix mt" id="requestTable" style="display: none">

<div class="candidate_wrap_table datatable_filter">
</div>

</div>
    
</div>
</div>

 <!--  </section> -->
  
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
function outstandingFun(event){
	$("#outstanding").addClass("currentActive");
	$("#completed, #request").removeClass("currentActive");
}
function completedFun(event){
	$("#completed").addClass("currentActive");
	$("#outstanding, #request").removeClass("currentActive");
}
function requestFun(event){
	$("#request").addClass("currentActive");
	$("#outstanding, #completed").removeClass("currentActive");
}

</script>


<script>
function EmployeeLoansAndAdvances(type){
	$.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getEmpOutstandingLoansAdvances?"+tokenParamName+"="+tokenValue+"&type="+type, 
	        success: function(data) {
         	var result = "";
	        result = result +'<table id="outstandingLoansTableDiv" class="display" cellspacing="0" width="100%" >';
	        result = result +'<thead>';
	        result = result +'<tr>';
	        if(type == "outstanding"){
	        	result = result +'<th>Name</th>';
		        result = result +'<th>Type</th>';
		        result = result +'<th>Total Amount</th>';
		        result = result +'<th>Paid Amount</th>';
		        result = result +'<th>Due Amount</th>';
		        result = result +'<th>Next Due Date</th>';
		        result = result +'<th>No. of Schedule</th>';
// 		        result = result +'<th>Action</th>';
	        }else if(type == "completed"){
	        	result = result +'<th>Name</th>';
		        result = result +'<th>Type</th>';
		        result = result +'<th>Total Amount</th>';
		        result = result +'<th>Last Paid On</th>';
		        result = result +'<th>No. of Schedule</th>';
// 		        result = result +'<th>Action</th>';
	        }else if(type == "requested"){
	        	result = result +'<th>Name</th>';
		        result = result +'<th>Type</th>';
		        result = result +'<th>Total Amount</th>';
		        result = result +'<th>Requested To</th>';
		        result = result +'<th>Requested On</th>';
		        result = result +'<th>Status</th>';
	        }
	        
	        result = result +'</tr>';
	        result = result +'</thead>';
	        result = result +'<tbody>';
	       
 	        if(data != null && data.length >0){
	        	for(var i = 0; i < data.length; i++){
	        	var json = data[i];
	        	result = result +'<tr>';
	 	        result = result +'<td>';
	 	        result = result +'<div class="tblminwidth-100 text-center">';
	 	        result = result +'<div class="clearfix">'+json.name+'</div>';
	 	        result = result +'</div>';
	 	        result = result +'</td>';
	 	        result = result +'<td>';
	 	        result = result +'<div class="tblminwidth-100 text-center">';
	 	        result = result +'<div class="clearfix">'+json.loanAndAdvType+'</div>';
	 	        result = result +'</div>';
	 	        result = result +'</td>';
	 	        result = result +'<td>';
	 	        result = result +'	<div class="tblminwidth-125">';
	 	        result = result +'<div class="clearfix">$ '+json.loanAmount+'</div>';
	 	        result = result +'</div>';
	 	        result = result +'</td>';
	 	        
	 	        
	 	        if(type == "outstanding"){
	 	        	result = result +'<td>';
	 	 	        result = result +'<div class="tblminwidth-125">';
	 	 	        if(json.paidAmount != null){
	 	 	        	result = result +'<div>$ '+json.paidAmount+'</div>';	
	 	 	        }else{
	 	 	        	result = result +'<div>--</div>';
	 	 	        }
	 	 	        result = result +'</div>';
	 	 	        result = result +'</td>';
	 	 	        result = result +'<td>';
	 	 	        result = result +'<div class="tblminwidth-50">';
	 	 	        result = result +'<div>$ '+json.dueAmount+'</div>';
	 	 	        result = result +'</div>';
	 	 	        result = result +'</td>';
	 	 	        
	 	 	      	result = result +'<td>';
	 	 	      	result = result +'<div class="tblminwidth-50">';
	 	 	        result = result +'<div>'+json.nextDueDate+'</div>';
	 	 	        result = result +'</div>';
	 	 	        result = result +'</td>';
	 	 	        
	 	        } else if(type == "completed"){
	 	           result = result +'<td>';
	 	 	        result = result +'<div class="tblminwidth-50">';
	 	 	        if(json.lastPaidOnMaxDate != null){
	 	 	        	result = result +'<div>'+json.lastPaidOnMaxDate+'</div>';
	 	 	        }else{
	 	 	        	result = result +'<div>--</div>';
	 	 	        }
	 	 	        result = result +'</div>';
	 	 	        result = result +'</td>';
	 	        } else if(type == "requested"){
	 	        	result = result +'<td>';
		 	        result = result +'<div class="tblminwidth-100 text-center">';
		 	        result = result +'<div class="clearfix">'+json.approverUser+'</div>';
		 	        result = result +'</div>';
		 	        result = result +'</td>';
		 	        result = result +'<td>';
		 	        result = result +'<div class="tblminwidth-100 text-center">';
		 	        result = result +'<div class="clearfix">'+json.createdDate+'</div>';
		 	        result = result +'</div>';
		 	        result = result +'</td>';
		 	        result = result +'<td>';
		 	        result = result +'	<div class="tblminwidth-125">';
		 	        if(json.status == 1){
		 	        	result = result +'<div class="clearfix">Active</div>';
		 	        }else{
		 	        	result = result +'<div class="clearfix">InActive</div>';
		 	        }
		 	        result = result +'</div>';
		 	        result = result +'</td>';
	 	        }
	 	        
	 	       if(type == "outstanding" ||  type == "completed"){
	 	    	    result = result +'<td>';
		 	        result = result +'<div class="tblminwidth-50">';
		 	        result = result +'<div>'+json.noOfSchedules+'</div>';
		 	        result = result +'</div>';
		 	        result = result +'</td>';
		 	        /* result = result +'<td>';
		 	        result = result +'<div class="tblminwidth-30">';
		 	        result = result +'<a href="javascript:void(0)" class="anchorBlack"><i class="fa fa-eye"></i></a>';
		 	        result = result +'</div>';
		 	        result = result +'</td>'; */
	 	         }
	 	       
	 	            result = result +'</tr>';
	 	           
	        	}
	        }  
	        result = result +'</tbody>';
	        result = result +'</table>';	
	        $("#outStadingTableDiv").empty();
	        $("#outStadingTableDiv").append(result);
	        outstandingLoansPagination(); 
	        
			
	        }, 
	    });
	
	    
}
</script>

<script>
function outstandingLoansPagination(){ 
    $('#outstandingLoansTableDiv').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    });
    $("#outstandingLoansTableDiv").wrap("<div class='overflow_Auto width100percnt'></div>");
    $('.dataTables_filter input').attr("placeholder", "Search");
}
</script>

<script>
function getLoansCountList(){
	$.ajax({
    	type: "GET",
        url: sourceleadUrl+"/getLoansAndAdvancesCounts?"+tokenParamName+"="+tokenValue, 
        success: function(data) {
        	$("#outstandingAmount").html(data.Outstanding);
        	$("#completedAmount").html(data.Completed);
        	$("#requestAmount").html(data.Requested);
        }
	});
}
</script>


