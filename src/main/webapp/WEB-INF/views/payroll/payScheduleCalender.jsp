<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<style>
.Highlighted a {
	background-color : #9ACD32 !important;
	font-weight:bold !important;
	font-size: 12pt;
}
</style>

<script>
$(document).ready(function(){
	var payrollScheduleUUID = "${payScheduleUUID}";
	calendarView(payrollScheduleUUID);
});

</script>


 <!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Upcomming pay period and paydays</h2>
      </div>
     <!--  <div class="pull-right brud-crum">Home &raquo; Payhead List &raquo; Upcomming pay period and paydays</div> -->
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home &raquo;</a> <a href="<c:url value='${appPath}/payrollScheduleList'/>">Pay Schedule List &raquo;</a> </div>
      
    </section>
    
	<div class="timesheet_out clearfix">
		<div class="positionRelative reportHead clearfix">
			<div class="clearfix mb10 mt10">
				<div class="pull-left reqListBtn ml10">
					<span class="listviewPaydays rAct" title="Graphical View"><i class="fa fa-list"></i></span>
					<!-- <span class="calendarviewPaydays" title="List View"><i class="fa fa-calendar"></i></span> -->
				</div>
				</div>
			</div>
		<div class="clearfix"></div>
		
		<div id="payperiodListview" style="display: block;">
			<div class="require_wrap_table tableFirstSecond datatable_filter clearfix">
			<div id="calenderDiv"></div>
			  </div>
		</div>
		
		<div id="payperiodCalendarview" class="customEvents-Calendar" style="display: none;">
			<div class="clearfix mb mt">
			<div class="col-md-12">
					<div class="col-md-3 mb">
						<div class="clearfix">
							<p class="text-primary">Pay Period</p>
							<h4 class="text-primary">December 19-25, 2018</h4>
						</div>
						<div id="eventCalendar" class="clearfix width100percnt"></div>
					</div>
			</div>
				<!-- <div class="col-md-3 mb">
					<div id="period1"></div>
					<div class="clearfix"></div>
				</div>
				<div class="col-md-3 mb">
					<div id="period2"></div>
					<div class="clearfix"></div>
				</div>
				<div class="col-md-3 mb">
					<div id="period3"></div>
					<div class="clearfix"></div>
				</div>
				<div class="col-md-3 mb">
					<div id="period4"></div>
					<div class="clearfix"></div>
				</div> -->
			</div>
		</div>
		
	  </div>
	  
  </section>
  
  
  <script>
  function calendarView(payrollScheduleUUID){
		  $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/findScheduleDates?"+tokenParamName+"="+tokenValue+"&payrollScheduleUUID="+payrollScheduleUUID, 
		        success: function(data) {
		        var result = '';
		        	result = result + '<table id="calenderTable" class="display" cellspacing="0" width="100%" >';
		        	result = result + '<thead>';
		        	result = result + '<tr>';
		        	result = result + '<th>Pay Period</th>';
		        	result = result + '<th>Pay Check Date</th>';
		        	result = result + '<th>Run Pay Date</th>';
		        	result = result + '</tr>';
		        	result = result + '</thead>';
		        	result = result + '<tbody>';
		        	for(var i=0;i<data.length;i++){
		        		var opt = data[i];
		        		result = result + '<tr>';
		        		result = result + '<td> <div class="tblminwidth-75">'+opt.payPeriod+'</div></td>';
		        		result = result + ' <td><div class="tblminwidth-75">'+opt.payCheckDate+'</div></td>';
		        		result = result + '  <td> <div class="tblminwidth-50">'+opt.payRunDate+'</div></td>';
		        		result = result + '</tr>';
		        	}
						rsult = result + '</tbody>';
						rsult = result + '</table>';
						$("#calenderDiv").empty();
						$("#calenderDiv").append(result);
						calenderPagination();
		        },
		    });
	}
  </script>
  
    <script>
function calenderPagination(){ 
    $('#calenderTable').DataTable( { 
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
    $("#calenderTable").wrap("<div class='overflow_Auto width100percnt'></div>")
}
</script>
  
  <script>
$(".listviewPaydays").click(function(){
	$("#payperiodListview").show();
	$("#payperiodCalendarview").hide();
	$(this).addClass("rAct");
	$(".calendarviewPaydays").removeClass("rAct");
});
$(".calendarviewPaydays").click(function(){
	$("#payperiodListview").hide();
	$("#payperiodCalendarview").toggle();
	$(this).addClass("rAct");
	$(".listviewPaydays").removeClass("rAct");
	
	var dates = {}
	dates[new Date('11/10/2018')] = new Date('11/10/2018'); 
	 alert(dates);
	 openDates(dates);
	
});
</script>
<script>
  $(function() {
          $("#period1, #period2, #period3, #period4").datepicker({
    });
  });
$('#next, #prev').on('click', function(e) {
    $('.ui-datepicker-'+e.target.id).trigger("click");
});
	</script>
	
	
	
<script>

/* var j=0; */
	
	/*  SelectedDates[new Date('10/13/2018')] = new Date('10/13/2018');
	 SelectedDates[new Date('10/14/2018')] = new Date('10/14/2018');
	 SelectedDates[new Date('10/15/2018')] = new Date('10/15/2018');
	 SelectedDates[new Date('10/16/2018')] = new Date('10/16/2018'); */
	 
	/*  SelectedDates[new Date('10/17/2018')] = new Date('10/17/2018'); 
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
	 

	/* 
	  function openDates(){
		 alert("hi");
		 var SelectedDates = {};
		 SelectedDates[new Date('11/10/2018')] = new Date('11/10/2018'); 
		 SelectedDates[new Date('11/11/2018')] = new Date('11/11/2018');
		 SelectedDates[new Date('11/12/2018')] = new Date('11/12/2018');
		 
	    $("#eventCalendar").datepicker({
			    beforeShowDay: function(date) {
			  	  var hlText = SelectedDates[date];
			  		return (hlText)?[true, "Highlighted", '']: [true, '', ''];  
			  		
			        
			  	}
	      
	    });
	  }  */
	 
	
		function openDates(dates) {
		  alert(dates);
		    $("#eventCalendar").datepicker({
		    	beforeShowDay: function(date) {
				  	  var hlText = dates[date];
				        return (hlText)?[true, "Highlighted", '']: [true, '', ''];
				  	}
		    });
		}
</script>
<jsp:include page="../masterFooter.jsp" />


 		