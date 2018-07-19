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
      <div class="pull-right brud-crum">Home &raquo; Calendar Events</div>
    </section>
    <div class="clearfix"></div>
    <div class="timesheet_out">
      <div class="tablelist-wrap clearfix">
        <div class="timesheet_head minHeight">
          <div class="col-md-7 mt">
            <div class="row">
              <div class="col-md-3">
                <div class="dropdown-select">
                  <select>
                    <option selected>Select Group</option>
                    <option>Group-1</option>
                    <option>Group-2</option>
                    <option>Group-3</option>
                  </select>
                </div>
              </div>
              <div class="col-md-4 paddingLeft paddingRight">
                <div class="dropdown-select">
                  <select>
                    <option selected>Select Frequency</option>
                    <option>01/02/2018</option>
                    <option>01/02/2018</option>
                    <option>09/02/2018 - 15/01/2018</option>
                  </select>
                </div>
              </div>
              <div class="col-md-3 paddingRight">
                <input name="" class="form-control calendar" placeholder="Check Date" type="text" id="checkDate">
              </div>
              <div class="col-md-2"> <a href="run-payroll.html" class="btn btn-success btnSuccess btn-sm anchorWhite">Run Payroll</a> </div>
            </div>
          </div>
          <div class="clearfix"></div>
          <div class="remove_search">
            <input placeholder="Search" type="text">
          </div>
        </div>
        
        <div class="clearfix mt customEvents-Calendar">
        	
        	<div class="col-md-3">
        	<h5 class="text-center margin-top0">Weekly</h5>
        		<div class="calendarEvent" id="calendar-oneweek"></div>
        	</div>
        	<div class="col-md-3">
        	    <h5 class="text-center margin-top0">By Weekly</h5>
        		<div class="calendarEvent" id="calendar-twoweek"></div>
        	</div>
        	<div class="col-md-3">
        	  <h5 class="text-center margin-top0">Monthly</h5>
        		<div class="calendarEvent" id="calendar-threeweek"></div>
        	</div>
        	<div class="col-md-3">
        	     <h5 class="text-center margin-top0">Semi Monthly</h5>
        		<div class="calendarEvent" id="calendar-onemonth"></div>
        	</div>
        	
        </div>
        
      </div>
    </div>
  </section>
  <!-- Content Area Ends--> 
  
  
  <script type="text/javascript">
$(function() {
	$("#calendar-oneweek").datepicker();
	$("#calendar-twoweek").datepicker();
	$("#calendar-threeweek").datepicker();
	$("#calendar-onemonth").datepicker();
	$("#checkDate").datepicker();
})
	
	
</script>
<jsp:include page="../masterFooter.jsp" />


 		