<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 

<script type="text/javascript">
		window.onload = function () {
				getHolidaysList();
		 };
</script>

 <!-- Content Area -->
  <section class="content-wraper" id="themeroller">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Holiday</h2>
        <div id=holidayListDiv></div>
      </div>
		<div class="pull-right brud-crum"><a href="dashboard_us.html">Home</a> &raquo; <a href="holiday.html">Leave Management </a> &raquo; Holiday</div>
    </section>
    
<div class="content-area content-new clearfix">

<div class="timesheet_out">
  <div class="clearfix">
    <div class="timesheet_head">
      <ul>
          <li>
              <a href="holiday.html" title="Add Holiday">
                <i class="fa fa-plus paddingRight"></i>Add Holiday
              </a>
          </li>
        
          <li class="dropdown">
            <span class="dropdown-toggle" id="menu1" data-toggle="dropdown">
                <i class="fa fa-filter"></i><span>Type</span><i class="glyphicon glyphicon-play"></i></span>|
          
            <div class="dropdown-menu bootstrap_dropdownMenu dropdownPadding" role="menu" aria-labelledby="menu1">
            <ul>
              <li>
                <input value="" name="resourcerequest" id="all" type="checkbox">
                <label for="all">Full Day</label>
              </li>
              <li>
                <input id="hotlist" name="resourcerequest" type="checkbox">
                <label for="hotlist">Half Day</label>
              </li>
              <li>
                <input id="Assigned" name="resourcerequest" type="checkbox">
                <label for="Assigned">SickLeave</label>
              </li>
            </ul>
          </div>
        </li>
        <div class="clearfix"></div>
      </ul>
      <div class="clearfix"></div>
      <div class="remove_search">
        <input placeholder="Search" type="text">
      </div>
    </div>
    <div class="require_wrap_table">
      <table>
        <thead>
          <tr>
            <th>Name</th>
            <th>Date</th>
            <th>Group</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
        <tr>
            <td>
                <div class="proj_show tbldata-dotdot-big">
                  <div class="pull-left tbldata-dotdot-me" title="Holiday Name">Holiday Name</div>
                  <i class="fa fa-binoculars pull-right"></i>
              </div>
            </td>
            <td>
                <div title="23/08/2017" class="tblminwidth-75">
                    23/08/2017
                </div>
            </td>
            <td>
                <div title="23/08/2017" class="tbldata-dotdot">
                    Group
                </div>
            </td>
            <td>
               <div class="tblminwidth-50">
                    <span title="Edit" class="paddingRight"><i class="fa fa-pencil text-primary cursorPointer"></i></span>
                    <span title="Delete" class="paddingLeft"><i class="fa fa-trash text-danger cursorPointer"></i></span>
                </div>
            </td>
          </tr>
          </tbody>
      </table>
    </div>
    <nav class="clearfix">
      <ul id="paging" class="pagination pull-right mr">
        <li><a href="#">Previous</a></li>
        <li><a href="#">2</a></li>
        <li><a href="#">3</a></li>
        <li class="disabled"><a href="#">6</a></li>
        <li data-page="last"><a href="#">Next</a></li>
      </ul>
      <div class="clearfix"></div>
    </nav>
  </div>
</div>

    </div>
    <!-- Content Area Ends--> 
  </section>
  <!-- Content Area Ends--> 

<script>

function getHolidaysList(){
	alert("hello111");
	$.ajax({
        type : "GET",
         url: sourceleadUrl+"/getHolidaysList?"+tokenParamName+"="+tokenValue+"",       
         success :function(data) {
        	 alert("hello222");
        	 prepareHolidayList(data);
        },
         
    }); 
	}
</script> 

<script>
  function prepareUsersList(data){
	  var json = JSON.parse(data);
  	var result = "";
  	for (var i=0; i<json.length; i++)
		{  
  		var report = json[i];
	        var result = "";
			result = result +" <table id='holidaysPagination'>";
			result = result +"<thead>";
			result = result +"<tr>";
			result = result +"<th>Holiday Name</th>";
			result = result +"<th>Date</th>";
			result = result +"<th>Status</th>";
			result = result +"</tr>";
			result = result +"</thead>";
			result = result +"<tbody>";
			
			 if(json !=''){
	        	  for (var i = 0; i < json.length; i++) { 
	        		  var report = json[i]; 
	        		  if(report.holidayName != null && report.holidayName != '' && report.holidayName != 'undefined'){
	        			  holidayName = report.holidayName;
	        		  }else{
	        			  holidayName = "--";
	        		  }
	        		  if(report.fromDate != null && report.fromDate != '' && report.fromDate != 'undefined'){
	        			  fromDate = report.fromDate;
	        		  }else{
	        			  fromDate = "--";
	        		  }
	        		  if(report.holidayStatus != null && report.holidayStatus != '' && report.holidayStatus != 'undefined'){
	        			  holidayStatus = report.holidayStatus;
	        		  }else{
	        			  holidayStatus = "--";
	        		  }
						 result = result +"<tr>";
						 result = result +"<td><div title="+holidayName+">"+holidayName+"</div></td>";
						 result = result +"<td><div title="+fromDate+">"+fromDate+"</div></td>";
						 result = result +"<td><div>"+holidayStatus+"</div></td>";
						 result = result +"</tr>"; 
	        	  }
			 }
 	 
	
	  result = result +" </tbody>";
	  result = result +" </table>";
		}
	  $("#holidayListDiv").empty();
   $("#holidayListDiv").append(result);
   holidaysPagination();}
	</script>	  
		  
<jsp:include page="../masterFooter.jsp" />
