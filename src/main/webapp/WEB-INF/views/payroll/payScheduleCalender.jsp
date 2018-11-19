<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>



<style>
.Highlighted {
	background-color : #DCDCDC !important;
	font-weight:bold !important;
	font-size: 12pt; border: 1px solid #fff !important;
}
.CheckDateHighlighted {
	background-color : 	#F0E68C !important;
	font-weight:bold !important;
	font-size: 12pt; border: 1px solid #fff !important;
}
.RunDateHighlighted {
	background-color : #0000FF !important;
	font-weight:bold !important;
	font-size: 12pt; border: 1px solid #fff !important;
}
.Highlighted a.ui-state-default {
 color: #fff !important;
}

#eventCalendar .ui-widget.ui-widget-content, #scheduleCalendar .ui-widget.ui-widget-content {
    border: 1px solid #ededed !important;
    box-shadow: none;
    width: 60% !important;
    border-radius: 0;
    background: #fff !important;
    padding: 10px;
}

/* .Hil a { */
/* 	background-color : #DC143C !important; */
/* 	font-weight:bold !important; */
/* 	font-size: 12pt; */
/* } */



</style>


<script>
$(document).ready(function(){
	$("#calenderViewDIV").hide();
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
		
		
		<div class="clearfix">
		<div id="calenderViewDIV" class="mb mt"></div>
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
						 callDates(data);
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
    	"ordering": false,
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
	$("#calenderViewDIV").hide();
	$(this).addClass("rAct");
	$(".calendarviewPaydays").removeClass("rAct");
});
$(".calendarviewPaydays").click(function(){
	$("#payperiodListview").hide();
	$("#payperiodCalendarview").toggle();
	$("#calenderViewDIV").show();
	$(this).addClass("rAct");
	$(".listviewPaydays").removeClass("rAct");
	
	
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


function callDates(data){
        var result = "";
        for(var i=0;i<data.length;i++){
        	var opt = data[i];
        	result = result + '<div class="col-md-3 mb">';
        	result = result + '<div class="clearfix">';
        	result = result + '<p class="text-primary">Pay Period</p>';
        	result = result + '<h4 class="text-primary">'+opt.dateFormat+'</h4>';
        	result = result + '</div>';
        	result = result + '<div id="eventCalendar'+i+'" class="eventCalendar customEvents-Calendar"></div>';
        	result = result + '</div>';
        }
		$("#calenderViewDIV").empty();
		$("#calenderViewDIV").append(result);
		
         for(var i=0;i<data.length;i++){
        	 var opt = data[i];
        	 var eventDates = {};
        	 
        	 var dates = getDates(new Date(''+opt.startDate+''), new Date(''+opt.endDate+''));
        	 
        	 // Preparing eventDates Array
        	 for (var k = 0; k < dates.length; k++) {
        		  eventDates[ new Date(''+dates[k]+'')] = new Date(''+dates[k]+'');
        		} 
             var dateToday = new Date(''+opt.startDate+'');  // current date calender dates
        	  $("#eventCalendar"+i).datepicker({
        		  changeMonth: true,
        	      changeYear: true,
        	      defaultDate: dateToday,
        		    beforeShowDay: function(date) {
        		  	  var hlText = eventDates[date];
        		  	return (hlText)?[true, "Highlighted", '']: [true, '', ''];    
        		  	}
        	    })
        	  $("#eventCalendar"+i).removeClass('ui-state-active');
        	  $("#eventCalendar"+i).find("a.ui-state-active").removeClass('ui-state-active'); 
         }
         
         for(var j=0;j<data.length;j++){
        	 var opt = data[j];
        	 var checkEventDates = {};
        	/*  var dates = getDates(new Date(''+opt.startDate+''), new Date(''+opt.endDate+''));
        	 
        	 // Preparing eventDates Array
        	 for (var k = 0; k < dates.length; k++) { */
        		 checkEventDates[ new Date('10/15/2018')] = new Date('10/15/2018');
        		/* }  */
             var todayDate = new Date('10/15/2018');  // current date calender dates
        	  $("#eventCalendar"+j).datepicker({
        		  changeMonth: true,
        	      changeYear: true,
        	      defaultDate: todayDate,
        		    beforeShowDay: function(date) {
        		  	  var hlText = checkEventDates[date];
        		  	return (hlText)?[true, "CheckDateHighlighted", '']:[true, '', ''];    
        		  	}
        	    })
        	  $("#eventCalendar"+j).removeClass('ui-state-active');
        	  $("#eventCalendar"+j).find("a.ui-state-active").removeClass('ui-state-active'); 
         }
   
    
}
</script>

<script>
var getDates = function(startDate, endDate) {
	  var dates = [],
	      currentDate = startDate,
	      addDays = function(days) {
	        var date = new Date(this.valueOf());
	        date.setDate(date.getDate() + days);
	        return date;
	      };
	  while (currentDate <= endDate) {
	    dates.push(currentDate);
	    currentDate = addDays.call(currentDate, 1);
	  }
	  return dates;
	};
</script>


<jsp:include page="../masterFooter.jsp" />


 		