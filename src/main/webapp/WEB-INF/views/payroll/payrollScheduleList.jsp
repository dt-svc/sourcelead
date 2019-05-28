<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>



<script>
$(document).ready(function(){
	getPayrollScheduleList();
});

</script>

<!-- Content Area -->
  <section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Pay Schedule</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard'/>" title="Home">Home</a>&raquo;
       <a href="<c:url value='${appPath}/settings'/>">Settings</a>&raquo;
       <a href="<c:url value='${appPath}/payrollConfiguration'/>"> Payroll Configuration</a>&raquo;Pay Schedule List
		</div>
    </section>
    <div class="contentareaInner-styles clearfix">
      <div class="timesheet_out">
        <div class="clearfix">
          <div class="positionRelative timesheet_head clearfix"> 
         <!--  <span class="pull-left refreIcon"><a href="javascript:void(0)" class="refeIcon" title="Refresh" onclick="resetFilter();"></a></span> -->
            <ul class="pull-right list-unstyled list-inline">
              <li><a class="newAdBtn mr5" href="<c:url value='${appPath}/paySchedule'/>"  title="Add">+ Add</a></li>
            </ul> 
          </div>
          <div class="require_wrap_table tableFirstSecond datatable_filter clearfix overflow_inherint width100percnt">
           <div id="payrollScheduleDIV"></div>
          </div>
        </div>
      </div>
      
      <!-- Content Area Ends--> 
    </div>
  </section>
 
 <script>
  function getPayrollScheduleList(){
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/findAllPayrollScheduleList?"+tokenParamName+"="+tokenValue, 
		        success: function(data) {
		        	
		        	var result = "";
		        	result = result + "<table id='scheduleListTable' class='display' cellspacing='0' width='100%' >";
		        	result = result + "<thead>";
		        	result = result + "<tr>";
		        	result = result + "<th>Pay Frequency</th>";
		        	result = result + "<th>Pay Period Start Date</th>";
		        	result = result + "<th>Calender</th>";
		 /*        	result = result + "<th>PayCheckDate</th>";
		        	result = result + "<th>Run Pay Date</th>"; */
		        	/* result = result + "<th>Action</th>"; */
		        	result = result + "</tr>";
		        	result = result + "</thead>";
		        	result = result + "<tbody>";
		        	
		        	for(var i=0;i<data.length;i++){
		        		var opt = data[i];
		        		result = result + "<tr>";
			        	result = result + "<td><div class='tblminwidth-75'>"+opt.payFrequency+"</div></td>";
			        	if(opt.payPeriodStartDate != null && opt.payPeriodStartDate != '' && opt.payPeriodStartDate != 'undefined'){
			        		result = result + "<td><div class='tblminwidth-75'>"+opt.payPeriodStartDate+"</div></td>";
			        	}else{
			        		result = result + "<td><div class='tblminwidth-75'>"+opt.nextPayMonth+"</div></td>";
			        	}
			        	
			        	result = result + ' <td>';
		        		 result = result + ' <div class="tblminwidth-50">';
		        		 result = result +' <span title="List" class="paddingRight"><a href="<c:url value="/app/payrollScheduleCalender?payScheduleUUID='+opt.payrollScheduleUUID+'"/>">view</a></span> ';
		        		 result = result + '  </div>';
		        		 result = result + ' </td>';
			        	//result = result + "<td><div class='tblminwidth-75'><a onClick='calendarPop(\""+opt.payFrequency+"\" , \""+opt.payPeriodStartDate+"\",\""+opt.payCheckDate+"\",\""+opt.runPayDate+"\");'>view</a></div></td>";
			        	/* result = result + "<td><div class='tblminwidth-75'><a onClick='calendarPop(\""+opt.payFrequency+"\" , \""+opt.payPeriodStartDate+"\",\""+opt.payCheckDate+"\",\""+opt.runPayDate+"\");'>"+opt.payPeriodStartDate+"</a></div></td>";
			        	result = result + "<td><div class='tblminwidth-75'>"+opt.payCheckDate+"</div></td>";
			        	result = result + "<td><div class='tblminwidth-75'>"+opt.runPayDate+"</div></td>"; */
			        	/* result = result + '<td><div class="tblminwidth-50"><a href="<c:url value="/app/payScheduleCalEdit?payScheduleUUID='+opt.payrollScheduleUUID+'"/>"><i class="fa fa-pencil"></i></a></div></td>'; */
			        	result = result + "</tr>";
		        	}
		        	
		                result = result + "</tbody>";
		                result = result + " </table>"; 
		                $("#payrollScheduleDIV").empty();
		                $("#payrollScheduleDIV").append(result);
		                payschedulePagination();
				
		        },
		    });
  }
  </script>
  
  <script>
function payschedulePagination(){ 
    $('#scheduleListTable').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    });
    $("#scheduleListTable").wrap("<div class='overflow_Auto width100percnt'></div>")
}
</script>
  
  <div class="mask"></div>
<div class="popupWrap" id="modalTasktype">
      <div class="popupHead"><span>Schedule Calendar</span><i class="closePop pull-right popClose"></i></div>
        <form id="#" name="" action="" method="post">
        <div class="popupContent row">
			<div class="col-md-1 noPad">
				<a title="Prev" id="prev" style="margin-top: 125px; position: absolute;" class="anchor-black fa fa-angle-double-left fa-2x">
				</a>
			</div>
			<div class="col-md-10 noPad">
				<div id="eventCalendar" class="clearfix width100percnt"></div>
			</div>
			<div class="col-md-1 noPad text-right">
				<a title="Next" class="fa fa-angle-double-right fa-2x anchor-black" id="next" style="margin-top: 125px; position: absolute; right: 0;"></a>
			</div>
        </div>
			<div class="clearfix"></div>
        <div class="popupFoot text-right">
          <button type="button" class="slBtn md popClose" onclick="flushInviteCompany();"> Cancel</button>
        </div>
        
        <div>
</div></form>
  </div>
  
  
  
  
  
  
  
  
  
<script>

	/* var SelectedDates = {};
	 SelectedDates[new Date('10/10/2018')] = new Date('10/10/2018'); 
	 SelectedDates[new Date('10/11/2018')] = new Date('10/11/2018');
	 SelectedDates[new Date('10/12/2018')] = new Date('10/12/2018');
	 SelectedDates[new Date('10/13/2018')] = new Date('10/13/2018');
	 SelectedDates[new Date('10/14/2018')] = new Date('10/14/2018');
	 SelectedDates[new Date('10/15/2018')] = new Date('10/15/2018');
	 SelectedDates[new Date('10/16/2018')] = new Date('10/16/2018');
	 
	 SelectedDates[new Date('10/17/2018')] = new Date('10/17/2018'); 
	 SelectedDates[new Date('10/18/2018')] = new Date('10/18/2018');
	 SelectedDates[new Date('10/19/2018')] = new Date('10/19/2018');
	 SelectedDates[new Date('10/20/2018')] = new Date('10/20/2018');
	 SelectedDates[new Date('10/21/2018')] = new Date('10/21/2018');
	 SelectedDates[new Date('10/22/2018')] = new Date('10/22/2018');
	 SelectedDates[new Date('10/23/2018')] = new Date('10/23/2018');
	 
	 SelectedDates[new Date('10/24/2018')] = new Date('10/24/2018'); 
	 SelectedDates[new Date('10/25/2018')] = new Date('10/25/2018');
	 SelectedDates[new Date('10/26/2018')] = new Date('10/26/2018');
	 SelectedDates[new Date('10/27/2018')] = new Date('10/27/2018');
	 SelectedDates[new Date('10/28/2018')] = new Date('10/28/2018');
	 SelectedDates[new Date('10/29/2018')] = new Date('10/29/2018');
	 SelectedDates[new Date('10/30/2018')] = new Date('10/30/2018'); */
	 
function calendarPop(payFrequency,payPeriodStartDate,payCheckDate,runPayDate){
	  $.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/findScheduleDates?"+tokenParamName+"="+tokenValue+"&payFrequency="+payFrequency+"&payPeriodStartDate="+payPeriodStartDate
	        		+"&payCheckDate="+payCheckDate+"&runPayDate="+runPayDate, 
	        success: function(data) {
			
	        },
	    });
	 

	$(".mask, #modalTasktype").fadeIn();
	openDate();
}
function openDate(){
	$(".popClose").click(function() {
		$(".mask, #modalTasktype").fadeOut();
	});
	  /* $(function() { */
	    $("#eventCalendar").datepicker({
	    	   changeMonth: true,
		       changeYear: true,
			    beforeShowDay: function(date) {
			  	  var hlText = SelectedDates[date];
			  	 /*  j++;
			  	  if(j<=6){
			  		 alert(j) */
			  		return (hlText)?[true, "Highlighted", '']: [true, '', ''];  
			  	  /* }else{
			  		return (hlText)?[true, "Hil", '']: [true, '', '']; 
			  	  } */
			  		
			        
			  	}
	      /* changeMonth: true, changeYear: true */
	      
	      
	    });
	  /* }); */
		$('#next, #prev').on('click', function(e) {
	    $('.ui-datepicker-'+e.target.id).trigger("click");
	});
}
</script>
<jsp:include page="../masterFooter.jsp" />


 		