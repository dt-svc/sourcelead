<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 

<script type="text/javascript">
		 $(document).ready(function(){
			 getHolidaysList();
			});
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
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	var userId = '${SourceLeadWeb_userId}';
	$.get(sourceleadUrl+"/getHolidaysList?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&userId="+userId+"", function(data) {
		if(data !=null){
       	 var result = "";				        	
				result = result + '<table id="viewHolidaysTable" class="display" cellspacing="0" width="100%" >';
				result = result + '<thead>';
				result = result + '<tr>';
				result = result +'<th>Holiday Name</th>';
				result = result +'<th>From Date</th>';
				result = result +'<th>To Date</th>';
				result = result + '<th>Description</th>';
				result = result + '<th>Action</th>';
				result = result + '</tr>';
				result = result + '</thead>	';
				result = result + '<tbody>';
				
				if(data !=null && data !=''){
					 for (var i=0; i<data.length; i++)
						{
			        		 var opt = data[i];
			        		 result = result + ' <tr>';
			        		 result = result + ' <td>';
			        		 result = result + ' <div class="proj_show tbldata-dotdot-big">';
			        		 if(opt.holidayName !=null && opt.holidayName !='' && opt.holidayName !='undefined'){
			        			 result = result + '     <div class="pull-left tbldata-dotdot-me" title="">'+opt.holidayName+'</div>'; 
			        		 }else{
			        			 result = result + '     <div class="pull-left tbldata-dotdot-me" title="">--</div>';
			        		 }
			        		 result = result + '  </div>';
			        		 result = result + ' </td>';
			        		 result = result + ' <td>';
			        		 result = result + '   <div title="From Date" class="tblminwidth-75">'+opt.fromDate+'</div>';
			        		 result = result + ' </td>';
			        		 result = result + ' <td>';
			        		 result = result + '   <div title="To Date" class="tbldata-dotdot-100">'+opt.toDate+'</div>';
			        		 result = result + '</td>';
			        		 result = result + ' <td>';
			        		 if(opt.description !=null && opt.description != 'undefined' && opt.description != ''){
			        			 result = result + '     <div title="2" class="tblminwidth-75">'+opt.description+'</div>';	 
			        		 }else{
			        			 result = result + '     <div title="2" class="tblminwidth-75">--</div>';	
			        		 }
			        		 
			        		 result = result + '  </td>';
			        		 result = result + ' <td>';
			        		 result = result + ' <div class="tblminwidth-50">';
			        		 result = result + '      <span title="Edit" class="paddingRight"><a href="'+sourceleadUrl+'""/editHoliday?optionId=\''+opt.id+'\"><i class="fa fa-pencil text-primary cursorPointer"></i></a></span>';
			        		 result = result + '     <span title="Delete" class="paddingLeft"><i class="fa fa-trash text-danger cursorPointer" onclick="deleteholiday(\''+opt.id+'\')"></i></span>';
			        		 result = result + '  </div>';
			        		 result = result + ' </td>';
			        		 result = result + '  </tr>';
						}
				}
	        	
				result = result + '  </tbody>';
				result = result + ' </table>';
				$("#viewOptinsDiv").empty();
				$("#viewOptinsDiv").append(result);
				$("#loading").removeAttr("style");
				viewHolidaysTable();
        }
		}); 
 }
</script> 
<script>
function deleteHoliday(holidayId){
	var reason = document.getElementById("reason").value;
	var reason1 = document.getElementById("reason").value;
	 reason = reason.replace(/\s/g, "")
	if(reason.match(/^[^\s-](.*)*$/) != null){
		$("#deleteLeaveHolidayDiv").empty(); 
		$.ajax({
		   	type: "POST",
		   	url: sourceleadUrl+"/deleteHoliday?"+tokenParamName+"="+tokenValue+"&holidayId="+holidayId+"&reason="+reason,	    	   
		    success: function(data) {
	   			 $("#selector1").removeAttr("style");
	  			 $("#selector1").css({ display: "block" });
	  			 $("#msgDiv").html("Holiday removed");
	  			 $("#popUp").empty();
	 			 $(".project_inn_mask").fadeOut(200);
	             msgFadein();
	             if(data == true){
	 				location.replace(sourceleadUrl+ "/viewHoliday");
	 			}
		    }
	   });
	}else{
		if(reason.length == 0 && reason1){
			$("#deleteLeaveHolidayDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Must not Start With Space</font>") ;
		}else{
			$("#deleteLeaveHolidayDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Reason is required</font>") ; 
		}
	}
}
</script>
<script>
function deleteholiday(holidayId){
	 $(".project_inn_mask").remove();
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<textarea id='reason' class='form-control' field-name='Reject Reason'  data-validation='required validate_Space validate_Description validate_length length2-250' name='reason' value='' placeholder='Reason' rows='2' onkeypress='clientDashboardRemoveSpacesForRejectReason();'></textarea>";
	result = result + ' <div id="deleteLeaveHolidayDiv"/>';
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Delete' onclick='deleteHoliday(\""+holidayId+"\");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelRejectPopupClient();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200); 
}
</script>

<script>
function viewHolidaysTable(){ 
    $('#viewHolidaysTable').DataTable( { 
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
    $("#viewHolidaysTable").wrap("<div class='overflow_Auto width100percnt'></div>")
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
	
<script>
   function clientDashboardRemoveSpacesForRejectReason() {
	    var output = document.getElementById("reason").value;
	    output = output.replace(/\s/g, "") 
	    if(output.length != 0){
	    $("#reasonForSpoc").removeAttr("data-validation");
	    }
	}
</script>		  
		  
<jsp:include page="../masterFooter.jsp" />
