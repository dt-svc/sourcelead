<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>



<script>
$(document).ready(function(){
	getPayrollScheduleList();
});

</script>


<style>
.Highlighted a {
	background-color : #9ACD32 !important;
	font-weight:bold !important;
	font-size: 12pt;
}
</style>

<!-- Content Area -->
  <section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Pay Schedule</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Payhead List &raquo; Pay Schedule</div>
    </section>
    <div class="contentareaInner-styles clearfix">
      <div class="timesheet_out">
        <div class="clearfix">
          <div class="positionRelative reportHead clearfix"> <span class="pull-left refreIcon"><a href="javascript:void(0)" class="refeIcon" title="Refresh" onclick="resetFilter();"></a></span>
            <ul class="pull-right list-unstyled list-inline">
              <li><a class="newAdBtn mr5" href="<c:url value='${appPath}/paySchedule'/>"  title="Add">+ Add</a></li>
              <li><a href="javascript:void(0)" class="filtIcon" title="Filters" onclick="openPop();"></a></li>
              <li><a href="javascript:void(0)" class="overIcon" title="Actions" onclick="newActions();"></a>
                <div class="newActPop" style=""> <span><a href="javascript:void(0)" onclick="exportToPdf();" title="PDF">Export PDF</a> <a href="javascript:void(0)" onclick="exportUserData();" title="Excel">Export Excel</a> </span> </div>
              </li>
              <li class="">
                <input class="repSearchBar" placeholder="Search" id="dynamicUserDataTableSearch" type="text">
                <span class="repSearchIcon" onclick="getDynamciUserList();"></span></li>
            </ul>
          </div>
          <div class="require_wrap_table tableFirstSecond clearfix">
           <div id="payrollScheduleDIV"></div>
          </div>
        <!--   <nav>
            <ul id="paging" class="pagination">
              <li><a href="#">Previous</a></li>
              <li><a href="#">2</a></li>
              <li><a href="#">3</a></li>
              <li><a href="#">4</a></li>
              <li><a href="#">5</a></li>
              <li class="disabled"><a href="#">6</a></li>
              <li data-page="last"><a href="#">Next</a></li>
            </ul>
            <div class="clearfix"></div>
          </nav> -->
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
		        	result = result + "<table>";
		        	result = result + "<thead>";
		        	result = result + "<tr>";
		        	result = result + "<th>Pay Frequency</th>";
		        	result = result + "<th>Pay Period Start Date</th>";
		        	result = result + "<th>PayCheckDate</th>";
		        	result = result + "<th>Run Pay Date</th>";
		        	result = result + "<th>Action</th>";
		        	result = result + "</tr>";
		        	result = result + "</thead>";
		        	result = result + "<tbody>";
		        	
		        	for(var i=0;i<data.length;i++){
		        		var opt = data[i];
		        		result = result + "<tr>";
			        	result = result + "<td><div class='tblminwidth-75'>"+opt.payFrequency+"</div></td>";
			        	result = result + "<td><div class='tblminwidth-75'><a onClick='calendarPop(\""+opt.payFrequency+"\" , \""+opt.payPeriodStartDate+"\");'>"+opt.payPeriodStartDate+"</a></div></td>";
			        	result = result + "<td><div class='tblminwidth-75'>"+opt.payCheckDate+"</div></td>";
			        	result = result + "<td><div class='tblminwidth-75'>"+opt.runPayDate+"</div></td>";
			        	result = result + " <td><div class='tblminwidth-50'><a href='payschedule.html'><i class='fa fa-pencil'></i></a></div></td>";
			        	result = result + "</tr>";
		        	}
		        	
		                result = result + "</tbody>";
		                result = result + " </table>"; 
		                $("#payrollScheduleDIV").empty();
		                $("#payrollScheduleDIV").append(result);
				
		        },
		    });
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

	var SelectedDates = {};
	SelectedDates[new Date('10/30/2018')] = new Date('10/30/2018');
	SelectedDates[new Date('11/11/2018')] = new Date('11/11/2018');
	SelectedDates[new Date('11/19/2018')] = new Date('11/19/2018');
function calendarPop(payFrequency,payPeriodStartDate){
	 alert("Hi");
	  $.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/findScheduleDates?"+tokenParamName+"="+tokenValue+"&payFrequency="+payFrequency+"&payPeriodStartDate="+payPeriodStartDate, 
	        success: function(data) {
			
	        },
	    });
	 

	$(".mask, #modalTasktype").fadeIn();
}
$(".popClose").click(function() {
	$(".mask, #modalTasktype").fadeOut();
});
  $(function() {
    $("#eventCalendar").datepicker({
    	   changeMonth: true,
	       changeYear: true,
		    beforeShowDay: function(date) {
		  	  var hlText = SelectedDates[date];
		        return (hlText)?[true, "Highlighted", '']: [true, '', ''];
		  	}
      /* changeMonth: true, changeYear: true */
      
      
    });
  });
	$('#next, #prev').on('click', function(e) {
    $('.ui-datepicker-'+e.target.id).trigger("click");
});
</script>
<jsp:include page="../masterFooter.jsp" />


 		