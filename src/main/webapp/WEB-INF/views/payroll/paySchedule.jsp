<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Content Area -->
  <section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Calendar Events</h2>
      </div>
      
      <div class="success_pop" id="selector2" style="display: nSemi-Monthlyone;">
     <i class="fa fa-check-circle"></i><span id="addPayrollScheduleMsgDiv"></span>
   </div>
      
<!--       <div class="pull-right brud-crum"> -->
<%-- 			<a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&raquo;<a --%>
<%-- 				href="<c:url value='${appPath}/payrollContribution'/>"> Payroll Contribution List</a> --%>
<!-- 		</div> -->
      
      <div class="pull-right brud-crum">
                <a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&raquo;<a
				href="<c:url value='${appPath}/payrollConfiguration'/>"> Payroll Configuration</a>&raquo; Calendar Events
       </div>
    </section>
    <div id="addPayrollScheduleLoading"></div>
    <form:form id="add_payroll_schedule" name="add_payroll_schedule" method="post">
    <div class="clearfix"></div>
    <div class="timesheet_out">
      <div class="tablelist-wrap clearfix">
        <div class="timesheet_head minHeight mt">
          
          
          <div class="col-md-3">
                <div class="dropdown-select">
                  <select id="datePickerDiv">
                    <option selected>Select Frequency</option>
                    <option value="Weekly">Weekly</option>
                    <option value="Bi-Weekly">Bi-Weekly</option>
                    <option value="Monthly">Monthly</option>
                    <option value="Semi-Monthly">Semi-Monthly</option>
                  </select>
                </div>
              </div> 
          
          <div class="clearfix"></div>
          <div class="remove_search hidden">
            <input placeholder="Search" type="text">
          </div>
        </div>
        
         <div class="clearfix mt customEvents-Calendar">
        	
        	<div class="col-md-3" id="datepicker" style="display:none;">
        	     <h5 class="text-center margin-top0">Weekly</h5>
        		<div class="calendarEvent"></div>
        	</div>
        	<div class="col-md-3" id="biWeeklyDatePicker" style="display:none;">
        	    <h5 class="text-center margin-top0">By Weekly</h5>
        		<div class="calendarEvent"></div>
        	</div>
        	<div class="col-md-3" id="monthlyDatePicker" style="display:none;">
        	  <h5 class="text-center margin-top0">Monthly</h5>
        		<div class="calendarEvent"></div>
        	</div>
        	<div class="col-md-3" id="semiMonthlyDatePicker" style="display:none;">
        	     <h5 class="text-center margin-top0">Semi Monthly</h5>
        		<div class="calendarEvent"></div>
        	</div>
        	
        	<div class="col-md-3" id="payPeriodDiv" style="display:none;">
        	     <h5 class="text-center margin-top0">Pay Period</h5>
         		<div class="clearfix"> 
         		<div id="payPeriodSpanId1" class="text-center clearfix label-success"></div>
       			<div id="payPeriodSpanId2" class="text-center clearfix"></div>
       			<div id="payPeriodSpanId3" class="text-center clearfix"></div>
       			<div id="payPeriodSpanId4" class="text-center clearfix"></div>
       		</div> 
        	</div>
        	
        </div> 
     
     <div class="form-footer clearfix mt">
        <div class="col-lg-12">
          <div class="pull-right" id="applyDiv" style="display:none;">
            <input value="Apply" id="applyPayrollConfiguration" class="btn btn-primary ml mr" onclick="savePayrollSchedule();" type="button">
<!--             <input value="Cancel" class="btn btn-default" onclick="" type="button"> -->
          </div>
        </div>
      </div>
        
      </div>
    </div>
     </form:form> 
  </section>
  <!-- Content Area Ends--> 
  my_select
  

<script>
$("#datePickerDiv").change(function() {
	var dateFrequency = $("#datePickerDiv").val();
	 $("#applyDiv").css({display:"block"});                    
	     disablingApplyButton(dateFrequency);
	  var finalDateFormatEduUpdate = '';
	  if(locationDate.match("yyyy-MM-dd")){
	  	  finalDateFormatEduUpdate = 'yy-mm-dd';
	  }if(locationDate.match("dd-MM-yyyy")){
	  	  finalDateFormatEduUpdate = 'dd-mm-yy'; 
	  }if(locationDate.match("MM-dd-yyyy")){
	  	  finalDateFormatEduUpdate = 'mm-dd-yy'; 
	  }if(locationDate.match("yyyy-dd-MM")){
	  	  finalDateFormatEduUpdate = 'yy-dd-mm'; 
	  }
	
	if(dateFrequency == "Weekly"){
		 $("#payPeriodSpanId1").empty();
		 $("#payPeriodSpanId2").empty();
		 $("#payPeriodSpanId3").empty();
		 $("#payPeriodSpanId4").empty();
		 
		$("#datepicker").datepicker({
			dateFormat : finalDateFormatEduUpdate,
		    onSelect: function(selected){
		    	
				var selectedDate = $(this).datepicker( 'getDate' );
		    	calculateWeeklyFrequency(selected,dateFrequency);
		    	
		    }
		  })
		  $("#datepicker").show();
		  $("#payPeriodDiv").show();
		  $("#biWeeklyDatePicker").hide();
		  $("#monthlyDatePicker").hide();
		  $("#semiMonthlyDatePicker").hide();
	}else if(dateFrequency == "Bi-Weekly"){
		 $("#payPeriodSpanId1").empty();
		 $("#payPeriodSpanId2").empty();
		 $("#payPeriodSpanId3").empty();
		 $("#payPeriodSpanId4").empty();
		
		$("#biWeeklyDatePicker").datepicker({
			dateFormat : finalDateFormatEduUpdate,
		    onSelect: function(selected){
		    	
		    	var selectedDate = $(this).datepicker( 'getDate' );
		    	calculateWeeklyFrequency(selected,dateFrequency);
		    }
		          
		  })	
		  $("#biWeeklyDatePicker").show();
		  $("#payPeriodDiv").show();
		  $("#datepicker").hide();
		  $("#monthlyDatePicker").hide();
		  $("#semiMonthlyDatePicker").hide();
	}else if(dateFrequency == "Monthly"){
		 $("#payPeriodSpanId1").empty();
		 $("#payPeriodSpanId2").empty();
		 $("#payPeriodSpanId3").empty();
		 $("#payPeriodSpanId4").empty();
		 
		$("#monthlyDatePicker").datepicker({
			dateFormat : finalDateFormatEduUpdate,
		    onSelect: function(selected){
		    	
		    	var selectedDate = $(this).datepicker( 'getDate' );
		    	calculateWeeklyFrequency(selected,dateFrequency);
		    }
		
		  })	
		  $("#monthlyDatePicker").show();
		  $("#payPeriodDiv").show();
		  $("#datepicker").hide();
		  $("#biWeeklyDatePicker").hide();
		  $("#semiMonthlyDatePicker").hide();
	}else if(dateFrequency == "Semi-Monthly"){
		$("#payPeriodSpanId1").empty();
		 $("#payPeriodSpanId2").empty();
		 $("#payPeriodSpanId3").empty();
		 $("#payPeriodSpanId4").empty();
		 
		$("#semiMonthlyDatePicker").datepicker({
			dateFormat : finalDateFormatEduUpdate,
		    onSelect: function(selected){
		    	
		    	var selectedDate = $(this).datepicker( 'getDate' );
		    	calculateWeeklyFrequency(selected,dateFrequency);
		    }
		
		  })	
		  $("#semiMonthlyDatePicker").show();
		  $("#payPeriodDiv").show();
		  $("#datepicker").hide();
		  $("#biWeeklyDatePicker").hide();
		  $("#monthlyDatePicker").hide();
	}
	
	  
});

function calculateWeeklyFrequency(selectedDate,dateFrequency){
	 $.ajax({
		type : "GET",
		url : sourceleadUrl+ "/getWeeklyFrequenciesBySelectedDate?&selectedDate="+selectedDate+"&dateFrequency="+dateFrequency,
		success : function(response) {
		   for(i=0 ;i<response.data.length ;i++){
			   var j = i+1;
			   var opt = response.data[i];
			     $("#payPeriodSpanId"+j).empty();
			     $("#payPeriodSpanId"+j).append(opt);
		  } 
		     
		}
      });
}

 function disablingApplyButton(dateFrequency){
	$.ajax({
		type : "GET",
		url : sourceleadUrl+ "/getDisableApplyFrequencies?&dateFrequency="+dateFrequency,
		success : function(response) {
			if(response == true){
				document.getElementById("applyPayrollConfiguration").disabled = true;
			}else{
				document.getElementById("applyPayrollConfiguration").disabled = false;
			}
		}
	
	});
} 

</script>

<script>
function savePayrollSchedule(){
                   var payScheduleDate = "";
                   var payScheduleType = $("#datePickerDiv").val();
                   if(payScheduleType == "Weekly"){
                	   payScheduleDate = $("#datepicker").val();
                   }else if(payScheduleType == "Bi-Weekly"){
                	   payScheduleDate = $("#biWeeklyDatePicker").val();
                   }else if(payScheduleType == "Monthly"){
                	   payScheduleDate = $("#monthlyDatePicker").val();
                   }else if(payScheduleType == "Semi Monthly"){
                	   payScheduleDate = $("#semiMonthlyDatePicker").val();
                   }
	        	  $("#addPayrollScheduleLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
                   $.ajax({
           			type : "POST",
           			url : sourceleadUrl+ "/savePayrollSchedule?&payScheduleType="+payScheduleType+"&payScheduleDate="+payScheduleDate,
           			success : function(data) {
           			  $("#addPayrollScheduleLoading").removeAttr("style");
                      $("#selector2").removeAttr("style");
		     		  $("#selector2").css({ display: "block" });
		     		  $("#addPayrollScheduleMsgDiv").html("Added Successfully");
		     		   addPayrollScheduleMsgFadein();
           			}
                   });
                   
                   
}
</script>

<script>
function addPayrollScheduleMsgFadein() {
	$("#selector2").fadeIn(200);
    $("#selector2").delay(2000).fadeOut(200);
}
</script>

<jsp:include page="../masterFooter.jsp" />


 		