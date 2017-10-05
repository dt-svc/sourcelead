<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script type="text/javascript" src="https://www.amcharts.com/lib/3/amcharts.js"></script>
<script type="text/javascript" src="https://www.amcharts.com/lib/3/serial.js"></script>
<script type="text/javascript" src="https://www.amcharts.com/lib/3/pie.js"></script> 

<%-- <jsp:include page="topLeaveTakers.jsp" /> --%>

<script type="text/javascript">
		 $(document).ready(function(){
			 getHolidaysList();
			 getEmployeeLeavesList();
			});
</script>


 <!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Leave Management</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Leave Management</div>
    </section>
    <div class="timesheet_out">
      <div class="clients_side_wrap_inn no_pad">
        <div class="project_side_wrap_inn_tabs" id="new-id">
             <div class="pull-right">
                          <a href="<c:url value='/applyLeave'/>" title="Apply Leave" class="btn btn-primary btn-sm zindex">Apply Leave</a>
                      </div>
          <ul class="project-tabs-menu projecttabs-custom">
            <li class="current"><a href="#tab-1">Self</a></li>
            <li><a href="#tab-2" onclick="getTopLeaveTakers();getLeaveTakersBarChat();">Employees</a></li>
            <div class="clearfix"></div>
          </ul>
           
          <div class="project_tabs projecttabs-custom"> 
            
            <!-- Details Tab -->
            <div id="tab-1" class="project_tabs_inn" style="display: block">
              <div class="clearfix">
                <div class="row">
                  <div class="clearfix"></div>
                  <div class="col-md-6">
                    <h4 class="leaves-heading">My Calendar</h4>
                    <div id="scheduleCalendar"></div>
                    <div class="clearfix"></div>
                    <ul class="scheduleAvailable">
                      <li><span class="bg-danger paddingRight"></span>Sick Leave</li>
                      <li><span class="bg-primary paddingRight"></span>Vacation</li>
                    </ul>
                      
                  </div>
                  <div class="col-md-6">
                    <h4 class="leaves-heading">My Time off balance</h4>
                    <div class="row time-balance">
                      <div class="col-md-3 mb">
                        <h4 class="text-center">Vacation</h4>
                        <div id="vacationDonut" class="amchartAnchor-Hide" style="width: 100%; height: 125px;"> </div>
                        <div class="text-center">
                          <h2>
                            <label class="small text-muted">Remaining:</label>
                            <label class="clearfix width100percnt mt5">120/80</label>
                          </h2>
                        </div>
                      </div>
                      <div class="col-md-3 mb">
                        <h4 class="text-center">Sick Leaves</h4>
                        <div id="sickDonut" class="amchartAnchor-Hide" style="width: 100%; height: 125px;"> </div>
                        <div class="text-center">
                          <h2>
                            <label class="small text-muted">Remaining:</label>
                            <label class="clearfix width100percnt mt5">120/80</label>
                          </h2>
                        </div>
                      </div>
                      <div class="col-md-3">
                        <h4 class="text-center">Casual Leaves</h4>
                        <div id="casualDonut" class="amchartAnchor-Hide" style="width: 100%; height: 125px;"></div>
                        <div class="text-center">
                          <h2>
                            <label class="small text-muted">Remaining:</label>
                            <label class="clearfix width100percnt mt5">120/80</label>
                          </h2>
                        </div>
                      </div>
                      <div class="col-md-3">
                        <h4 class="text-center">Other Leaves</h4>
                        <div id="otherDonut" class="amchartAnchor-Hide" style="width: 100%; height: 125px;"></div>
                        <div class="text-center">
                          <h2>
                            <label class="small text-muted">Remaining:</label>
                            <label class="clearfix width100percnt mt5">120/80</label>
                          </h2>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="clearfix"></div>
                    <div class="col-md-12 mt10"><hr></div>
                  <div class="col-md-10 col-md-offset-1 listof-holidays datatable_filter">
                        <div class="row">
                            <div class="col-md-5"><strong class="leaves-heading">List of Holidays</strong></div>
                            <div class="col-md-3 noPad dropdown-select">
                                <select class="form-control">
                                    <option selected>--Select Year--</option>
                                    <option>2020</option>
                                    <option>2019</option>
                                    <option>2018</option>
                                    <option>2017</option>
                                    <option>2016</option>
                                    <option>2015</option>
                                    <option>2014</option>
                                    <option>2013</option>
                                    <option>2012</option>
                                    <option>2011</option>
                                    <option>2010</option>
                                </select>
                            </div>
                            <div class="col-md-4 noPad">&nbsp</div>
                            </div>
                            <div id="holidayListDiv"></div>
                        
                    </div>
                </div>
              </div>
            </div>
            <div id="tab-2"  class="project_tabs_inn" style="display: none">
                <div class="row clearfix">
                    <div class="clearfix mb5">
                        <div class="col-md-6">
                            <h4 class="leaves-heading"><span class="pull-left">Employee Leaves</span></h4>

                        </div>
                        <div class="clearfix col-md-6 pull-right">
                        
							<ul class="col-md-5 empUlAli">
	                            <li id="filterDepartmentPop"><i class="fa fa-filter"></i> <span>Department</span><i class="fa fa-caret-down"></i>
	                            <div class="statusFiler-1">
	                            <ul>
		                            <c:forEach var="report" items="${departmentList}">
		                            	<li><input type="checkbox" name="department" value="${report.id}" onclick="getEmployeeLeavesList();"><label>${report.departmentName}</label></li>
		                            </c:forEach>
	                            </ul>
	                            </div>
	                            </li>
                            </ul> 
                        	
                        	<div class=" col-md-7 noPad"> 
                            <div class="form-inline leaves-heading">
                                <div class="pull-right">
                                <div class="form-group">
                            <input type="text" class="calendar form-control input-sm" id="leavesCalendar">
                                    </div>
                                <div class="form-group">
                            <button type="button" class="btn btn-primary btn-sm" title="Custom" onClick="customizeEmployee();">Custom</button>
                                </div>
                                    </div>
                                </div>
                                <div class="clearfix customizeEmployee" style="display: none;">
                                <div class="clearfix">
                                <div class="col-md-5 noPad mR5">
                                <input name="Date" placeholder="From" class="form-control calendar input-sm" type="text">
                                </div>
                                <div class="col-md-5 noPad mR5">
                                <input type="text" name="Date" placeholder="To" class="form-control calendar input-sm">
                                </div>
                                <button type="button" class="btn btn-primary btn-sm"><i class="fa fa-search"></i></button>
                                    </div>
                                </div>
                                
                                </div>
                                
                        </div>
                    </div>
                    <div class="clearfix ptagmrng-none">
                    	<div id="employeelistDiv"></div>
                      </div>
                </div>
                <div class="clearfix"><hr></div>
                <div class="row">
                  <div class="col-md-6">
                 
                 <div class="clearfix mt5">
                   <h4 class="heading col-md-4">Top leave Takers</h4>
                    <div class="timesheet_head clearfix col-md-8">
                          <div class="dropdown-select">
                            <div class="form-inline pull-right">
                            <div class="form-group">
                            <select class="form-control pull-right" name="reportYear" id="reportYear" onchange="getTopLeaveTakers();">
                              <option value="">Year</option>
                              <option value="2017">2017</option>
                              <option value="2018">2018</option>
                              <option value="2019">2019</option>
                              <option value="2020">2020</option>
                              <option value="2021">2021</option>
                            </select>
    </div>
    <div class="form-group">
    <select class="form-control pull-right" name="reportMonth" id="reportMonth" onchange="getTopLeaveTakers();">
                              <option value="">Month</option>
                              <option value="0">January</option>
                              <option value="1">February</option>
                              <option value="2">March</option>
                              <option value="3">April</option>
                              <option value="4">May</option>
                              <option value="5">June</option>
                              <option value="6">July</option>
                              <option value="7">August</option>
                              <option value="8">September</option>
                              <option value="9">October</option>
                              <option value="10">November</option>
                              <option value="11">December</option>
                            </select>
    </div>
    <button type="submit" class="btn btn-primary btn-sm" id="customLeaves" onClick="showCustom()">Custom</button>
  </div>
<!-- Top Leave Takers Custom Date -->
    <div class="clearfix pull-lefet col-md-6 paddingRight customLeaves" style="display: none;">
    <div class="pull-left">
    <div class="pull-left mr5" style="width: 140px">
    <input value="" name="reportFromDate" id="reportFromDate" placeholder="From" class="form-control calendar input-sm" type="text">
    </div>
    <div class="pull-left mr5" style="width: 140px">
    <input value="" name="reportEndDate" id="reportEndDate" placeholder="To" class="form-control calendar input-sm" type="text">
    </div>
    <button type="button" class="btn btn-primary btn-sm" onclick="getTopLeaveTakers();"><i class="fa fa-search"></i></button>
    </div>
    </div>
<!-- Top Leave Takers Custom Date -->    
                        </div>
                        </div>
                 </div>
                     
                      <div class="clearfix project_wrap_table mt5 datatable_filter">
                       <div id="topLeaveTakersDiv"></div>
                    </div>
                  </div>

                  <div class="col-md-6">
                    <div class="clearfix mt5">
                   <h4 class="heading col-md-4">Employee Leave Report</h4>
                    <div class="timesheet_head clearfix col-md-8">
                          <div class="dropdown-select">
                            <div class="form-inline pull-right">
                            <div class="form-group">
                            <select class="form-control pull-right" name="barChartYear" id="barChartYear">
                              <option value="">Year</option>
                              <option value="2017">2010</option>
                              <option value="2018">2011</option>
                              <option value="2019">2012</option>
                              <option value="2020">2013</option>
                              <option value="2021">2014</option>
                              <option value="2022">2015</option>
                            </select>
                            </div>
                            <div class="form-group">
                            <select class="form-control pull-right" name="barCharMonth" id="barCharMonth">
                              <option value="">Month</option>
                              <option value="0">January</option>
                              <option value="1">February</option>
                              <option value="2">March</option>
                              <option value="3">April</option>
                              <option value="4">May</option>
                              <option value="5">June</option>
                              <option value="6">July</option>
                              <option value="7">August</option>
                              <option value="8">September</option>
                              <option value="9">October</option>
                              <option value="10">November</option>
                              <option value="11">December</option>
                            </select>
    </div>
    <button type="submit" class="btn btn-primary btn-sm" id="showLeaveReport" onClick="showLeaveReport()">Custom</button>
  </div>
<!-- Top Leave Takers Custom Date -->
    <div class="clearfix pull-lefet col-md-6 paddingRight showLeaveReport" style="display: none;">
    <div class="pull-left">
    <div class="pull-left mr5" style="width: 140px">
    <input value=""  placeholder="From" class="form-control calendar input-sm" type="text" id="barChartFromDate" name="barChartFromDate">
    </div>
    <div class="pull-left mr5" style="width: 140px">
    <input value=""  placeholder="To" class="form-control calendar input-sm" type="text" id="barChartToDate" name="barChartToDate">
    </div>
    <button type="button" class="btn btn-primary btn-sm"><i class="fa fa-search"></i></button>
    </div>
    </div>
<!-- Top Leave Takers Custom Date -->    
                        </div>
                        </div>
                 </div>
                      <div id="empLeavereport" class="amchartAnchor-Hide mt5" style="width: 100%; height: 250px;"></div>
                  
                    </div>
                    </div>
              
              </div>
              
          </div>
        </div>
      </div>
      
      <!-- Project --> 
    </div>
  </section>


<script type="text/javascript">
    //Chosen script
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
$(document).ready(function() {
$("#tab-1").show();
    $(".project-tabs-menu a").click(function(event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
        var tab = $(this).attr("href");
        $(".project_tabs_inn").not(tab).css("display", "none");
        $(tab).fadeIn();
    });
});
    // jQuery Ui Calendar
    $(function() {
    $( "#scheduleCalendar" ).datepicker({
	  changeMonth: true,
      changeYear: true,
        onSelect : function(selected) {
            var tet = $("<div></div>").text("Today No Events").appendTo("#ui-datepicker-div")
        }
    });
  });
</script> 


<script>
function getHolidaysList(){
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	var userId = '${SourceLeadWeb_userId}';
	$.get(sourceleadUrl+"/getHolidaysList?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&userId="+userId+"", function(data) {
		var result = "";				        	
				result = result + '<table id="viewHolidaysTable" class="display table table-bordered" cellspacing="0" width="100%" >';
				result = result + '<thead>';
				result = result + '<tr>';
				result = result +'<th>S.No</th>';
				result = result +'<th>Name</th>';
				result = result +'<th>Date</th>';
				result = result + '<th>Day of the Week</th>';
				result = result + '</tr>';
				result = result + '</thead>	';
				result = result + '<tbody>';
				if(data !=null && data !=''){
					var j = 1;
					 for (var i=0; i<data.length; i++)
						{
			        		 var opt = data[i];
			        		 result = result + ' <tr class="text-danger">';
			        		 result = result + ' <td>'+j+'</td>';
			        		 if(opt.holidayName !=null && opt.holidayName !='' && opt.holidayName !='undefined'){
			        			 result = result + '<td title="'+opt.holidayName+'">'+opt.holidayName+'</td>'; 
			        		 }else{
			        			 result = result + '<td>---</td>';
			        		 }
			        		 result = result + ' <td title="'+opt.fromDate+'">'+opt.fromDate+'</td>';
			        		 result = result + ' <td title="'+opt.dayOfTheWeek+'">'+opt.dayOfTheWeek+'</td>';
			        		 result = result + '  </tr>';
			        		 j++;
						}
				}
	        	
				result = result + '  </tbody>';
				result = result + ' </table>';
				$("#holidayListDiv").empty();
				$("#holidayListDiv").append(result);
				viewHolidaysTable();
		}); 
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
}
</script>

<script>
$("#filterDepartmentPop").click(function(){
	$(".statusFiler-1").toggle();
	//$(".statusFiler-2, .statusFiler-3").hide();
});

$("html").click(function() {
    $(".statusFiler-1").hide();
});	
$("#filterDepartmentPop, .statusFiler-1").click(function(e) {
    e.stopPropagation();
});	
</script>

<script>
function getEmployeeLeavesList(){
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	var userId = '${SourceLeadWeb_userId}';
	
	var department = [];
	$('input:checkbox[name=department]:checked').each(function() 
    {
		department.push($(this).val());
    });
	$.get(sourceleadUrl+"/getEmployeeLeavesList?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&userId="+userId+"&department="+department, function(data) {
		var result = "";	
		for (var i=0; i<data.length; i++){
			var opt = data[i];
			result += '<div class="col-md-3">';
	        result += '<div class="media media-custom mb">';
	        result += '    <div class="media-left">';
	        result += '        <div class="empimg-parent">';
	        if(opt.empProfileImageURL != null && opt.empProfileImageURL != '' && opt.empProfileImageURL != 'undefined'){
	        	result += '          <img onerror="predefineProfileImage(this);" src="'+opt.empProfileImageURL+'"  class="">';
	        }else{
	        	result += '          <img onerror="predefineProfileImage(this);" src=""  class="">';
	        }
	        result += '        </div>';
	        result += '    </div>';
	        result += '    <div class="media-body text-left">';
	        if(opt.empName != null && opt.empName != '' && opt.empName != 'undefined'){
	        	result += '      <h5 class="media-heading tbldata-dotdot">'+opt.empName+'</h5>';
	        }
	        
	        if(opt.empType != null && opt.empType != '' && opt.empType != 'undefined'){
	        	result += '      <p class="small text-muted">'+opt.empType+'</p>';
	        }else{
	        	result += '      <p class="small text-muted">NA</p>';
	        }
	        result += '        <ul class="list-unstyled list-inline mrng0 mt5 empLeave-li">';
	        if(opt.isSunday == "Y" && opt.isSunday != null && opt.isSunday != '' && opt.isSunday != 'undefined'){
	        	if(opt.isSundayHalfDay == "Y" && opt.isSundayHalfDay != null && opt.isSundayHalfDay != '' && opt.isSundayHalfDay != 'undefined'){
	        		result += '            <li title="Date" class="halfday">S</li>';
	        	}else{
	        		result += '            <li title="Date" class="fullday">S</li>';
	        	}
	        }else{
	        	result += '            <li title="Date">S</li>';
	        }
	        
	        if(opt.isMonday == "Y" && opt.isMonday != null && opt.isMonday != '' && opt.isMonday != 'undefined'){
	        	if(opt.isMondayHalfDay == "Y" && opt.isMondayHalfDay != null && opt.isMondayHalfDay != '' && opt.isMondayHalfDay != 'undefined'){
	        		result += '            <li title="Date" class="halfday">M</li>';
	        	}else{
	        		result += '            <li title="Date" class="fullday">M</li>';
	        	}
	        }else{
	        	result += '            <li title="Date">M</li>';
	        }
	        
	        if(opt.isTuesday == "Y" && opt.isTuesday != null && opt.isTuesday != '' && opt.isTuesday != 'undefined'){
	        	if(opt.isTuesdayHalfDay == "Y" && opt.isTuesdayHalfDay != null && opt.isTuesdayHalfDay != '' && opt.isTuesdayHalfDay != 'undefined'){
	        		result += '            <li title="Date" class="halfday">T</li>';
	        	}else{
	        		result += '            <li title="Date" class="fullday">T</li>';
	        	}
	        }else{
	        	result += '            <li title="Date">T</li>';
	        }
	        
	        if(opt.isWednesday == "Y" && opt.isWednesday != null && opt.isWednesday != '' && opt.isWednesday != 'undefined'){
	        	if(opt.isWednesdayHalfDay == "Y" && opt.isWednesdayHalfDay != null && opt.isWednesdayHalfDay != '' && opt.isWednesdayHalfDay != 'undefined'){
	        		result += '            <li title="Date" class="halfday">W</li>';
	        	}else{
	        		result += '            <li title="Date" class="fullday">W</li>';
	        	}
	        }else{
	        	result += '            <li title="Date">W</li>';
	        }
	        
	        if(opt.isThursday == "Y" && opt.isThursday != null && opt.isThursday != '' && opt.isThursday != 'undefined'){
	        	if(opt.isThursdayHalfDay == "Y" && opt.isThursdayHalfDay != null && opt.isThursdayHalfDay != '' && opt.isThursdayHalfDay != 'undefined'){
	        		result += '            <li title="Date" class="halfday">T</li>';
	        	}else{
	        		result += '            <li title="Date" class="fullday">T</li>';
	        	}
	        }else{
	        	result += '            <li title="Date">T</li>';
	        }
	        
	        if(opt.isFriday == "Y" && opt.isFriday != null && opt.isFriday != '' && opt.isFriday != 'undefined'){
	        	if(opt.isFridayHalfDay == "Y" && opt.isFridayHalfDay != null && opt.isFridayHalfDay != '' && opt.isFridayHalfDay != 'undefined'){
	        		result += '            <li title="Date" class="halfday">F</li>';
	        	}else{
	        		result += '            <li title="Date" class="fullday">F</li>';
	        	}
	        }else{
	        	result += '            <li title="Date">F</li>';
	        }
	        
	        if(opt.isSaturday == "Y" && opt.isSaturday != null && opt.isSaturday != '' && opt.isSaturday != 'undefined'){
	        	if(opt.isSaturdayHalfDay == "Y" && opt.isSaturdayHalfDay != null && opt.isSaturdayHalfDay != '' && opt.isSaturdayHalfDay != 'undefined'){
	        		result += '            <li title="Date" class="halfday">S</li>';
	        	}else{
	        		result += '            <li title="Date" class="fullday">S</li>';
	        	}
	        }else{
	        	result += '            <li title="Date">S</li>';
	        }
	        
	        result += '        </ul>';
	        result += '    </div>';
	        result += '</div>';
	        result += '</div>';
	        $("#employeelistDiv").empty();
	        $("#employeelistDiv").append(result);
		}
		
	}); 
	
}
</script>



<script type="text/javascript">
function getLeaveTakersBarChat(){
	//empLeavereport
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	var userId = '${SourceLeadWeb_userId}';
	var barChartYear ="";
	var barCharMonth = "";
	var barChartFromDate = "";
	var barChartToDate "";
	if($("#barChartYear").val() !=null && $("#barChartYear").val() != 'undefined' && $("#barChartYear").val() != ''){
		barChartYear = $("#barChartYear").val();
	}
	if($("#barCharMonth").val() !=null && $("#barCharMonth").val() != 'undefined' && $("#barCharMonth").val() != ''){
		barCharMonth = $("#barCharMonth").val();	
	}
	if($("#barChartFromDate").val() !=null && $("#barChartFromDate").val() != 'undefined' && $("#barChartFromDate").val() != ''){
		var barChartFromDate = $("#barChartFromDate").val();	
	}
	if($("#barChartToDate").val() !=null && $("#barChartToDate").val() != 'undefined' && $("#barChartToDate").val() != ''){
		var barChartToDate = $("#barChartToDate").val();	
	}

		$.ajax({
		   	type: "POST",
		   	var url = sourceleadUrl+"/getTopLeavetakers?orgLocationId="+orgLocationId+"&userId="+userId+"&barChartYear="+barChartYear+"&barCharMonth="+barCharMonth+"&barChartFromDate="+barChartFromDate+"&barChartToDate="+barChartToDate;	    	   
		    success: function(data) {
	   });
}
</script>

<script>
    /*------My Timeoff Balance Piecharts------*/
    //vacationDonut
        AmCharts.makeChart("vacationDonut",
				{
					"type": "pie",
					"balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b></span>",
                    //"balloonText": "",
					"innerRadius": "60%",
                    "minRadius":0,
                    "pieY": "50%",
					"pullOutRadius": 0,
					"radius": "45%",
					"startAngle": 0,
					"colors": [
						"#337ab7",
						"#ddd"
					],
					"labelsEnabled": false,
					"marginBottom": 0,
					"marginTop": 0,
					"titleField": "category",
					"valueField": "column-1",
					"allLabels": [],
					"balloon": {
						"fixedPosition": false
					},
					"titles": [],
					"dataProvider": [
						{
							"category": "Vacation",
							"column-1": "80"
						},
						{
							"category": "Pending Leaves",
							"column-1": "20"
						}
					]
				}
			);
        //sickDonut
        AmCharts.makeChart("sickDonut",
				{
					"type": "pie",
					"balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b></span>",
                    //"balloonText": "",
					"innerRadius": "60%",
                    "minRadius":0,
                    "pieY": "50%",
					"pullOutRadius": 0,
					"radius": "45%",
					"startAngle": 0,
					"colors": [
						"#e53935",
						"#ddd"
					],
					"labelsEnabled": false,
					"marginBottom": 0,
					"marginTop": 0,
					"titleField": "category",
					"valueField": "column-1",
					"allLabels": [],
					"balloon": {
						"fixedPosition": false
					},
					"titles": [],
					"dataProvider": [
						{
							"category": "Sick Leaves",
							"column-1": "80"
						},
						{
							"category": "Pending Leaves",
							"column-1": "20"
						}
					]
				}
			);
        //casualDonut
                AmCharts.makeChart("casualDonut",
				{
					"type": "pie",
					"balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b></span>",
                    //"balloonText": "",
					"innerRadius": "60%",
                    "minRadius":0,
                    "pieY": "50%",
					"pullOutRadius": 0,
					"radius": "45%",
					"startAngle": 0,
					"colors": [
						"#ffa726",
						"#ddd"
					],
					"labelsEnabled": false,
					"marginBottom": 0,
					"marginTop": 0,
					"titleField": "category",
					"valueField": "column-1",
					"allLabels": [],
					"balloon": {
						"fixedPosition": false
					},
					"titles": [],
					"dataProvider": [
						{
							"category": "Casual Leaves",
							"column-1": "80"
						},
						{
							"category": "Pending Leaves",
							"column-1": "20"
						}
					]
				}
			);
                        AmCharts.makeChart("otherDonut",
				{
					"type": "pie",
					"balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b></span>",
                    //"balloonText": "",
					"innerRadius": "60%",
                    "minRadius":0,
                    "pieY": "50%",
					"pullOutRadius": 0,
					"radius": "45%",
					"startAngle": 0,
					"colors": [
						"#5c6bc0",
						"#ddd"
					],
					"labelsEnabled": false,
					"marginBottom": 0,
					"marginTop": 0,
					"titleField": "category",
					"valueField": "column-1",
					"allLabels": [],
					"balloon": {
						"fixedPosition": false
					},
					"titles": [],
					"dataProvider": [
						{
							"category": "Other Leaves",
							"column-1": "80"
						},
						{
							"category": "Pending Leaves",
							"column-1": "20"
						}
					]
				}
			);
    //Custom buttons
    function showCustom(){
        $(".customLeaves").toggle();
    }
    function showLeaveReport(){
        $(".showLeaveReport").toggle();
    }
    //jQuery UI Calendar
    $("#leavesCalendar").datepicker();
    </script>
    
		<script>
                //Employee LeaveReport Amchart
			AmCharts.makeChart("empLeavereport",
				{
					"type": "serial",
					"categoryField": "category",
					"columnSpacing": 20,
					"columnWidth": 0.5,
					"autoMarginOffset": 20,
					"marginBottom": 5,
					"marginLeft": 0,
					"marginRight": 5,
					"marginTop": 10,
					"minMarginBottom": 0,
					"minMarginLeft": 0,
					"minMarginRight": 0,
					"minMarginTop": 0,
					"startDuration": 1,
					"autoDisplay": true,
					"touchClickDuration": 10,
					"categoryAxis": {
						"gridPosition": "start",
						"titleFontSize": 0
					},
					"trendLines": [],
					"graphs": [
						{
							"balloonText": "[[category]] [[value]]",
							"colorField": "color",
							"fillAlphas": 1,
							"id": "AmGraph-1",
							"lineColorField": "color",
							"title": "graph 1",
							"type": "column",
							"valueField": "column-1"
						}
					],
					"guides": [],
					"valueAxes": [],
					"allLabels": [],
					"balloon": {
						"offsetY": 4,
						"pointerWidth": 10,
						"verticalPadding": 5
					},
					"titles": [],
					"dataProvider": [
						{
							"category": "Annual Leave",
							"column-1": "12",
							"color": "#64b5f6"
						},
						{
							"category": "Sick Leave",
							"column-1": "6",
							"color": "#ef5350"
						},
						{
							"category": "Casual Leave",
							"column-1": "12",
							"color": "#66bb6a "
						},
						{
							"category": "Maternity Leave",
							"column-1": "6",
							"color": "#3949ab"
						},
						{
							"category": "Personal Leave",
							"column-1": "8",
							"color": "#ffa726"
						}
					]
				}
			);
		</script>
    <script>
    function customizeEmployee(){
        $(".customizeEmployee").fadeToggle();
    }
    </script>
    
    
    
 <script>
	function getTopLeaveTakers() {
		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		var userId = '${SourceLeadWeb_userId}';
		var reportYear = "";
		var reportMonth = "";
		var reportFromDate = "";
		if($("#reportYear").val() !=null && $("#reportYear").val() != 'undefined' && $("#reportYear").val() != ''){
			reportYear = $("#reportYear").val();
		}
		if($("#reportMonth").val() !=null && $("#reportMonth").val() != 'undefined' && $("#reportMonth").val() != ''){
			reportMonth = $("#reportMonth").val();	
		}
		if($("#reportFromDate").val() !=null && $("#reportFromDate").val() != 'undefined' && $("#reportFromDate").val() != ''){
			var reportFromDate = $("#reportFromDate").val();	
		}
		if($("#reportEndDate").val() !=null && $("#reportEndDate").val() != 'undefined' && $("#reportEndDate").val() != ''){
			var reportEndDate = $("#reportEndDate").val();	
		}

		var searchValue = $("#dataTableSearch").val();
  		searchValue = searchValue ? searchValue : "";
  		var url = sourceleadUrl+"/getTopLeavetakers?orgLocationId="+orgLocationId+"&searchValue="+searchValue+"&userId="+userId+"&reportYear="+reportYear+"&reportMonth="+reportMonth+"&reportFromDate="+reportFromDate+"&reportEndDate="+reportEndDate;
  		prepareTopLeaveTakersList(url); 
	}
	
</script>	

<script>	
	function prepareTopLeaveTakersList(url) {
		$("#topLeaveTakersDiv").empty();
		var result = "";
		result = result + "<table id='topLeaveTakersTable' class='display' cellspacing='0' width='100%' >";
		result = result + "<thead>";
		result = result + "  <tr>";
		result = result + "    <th>Employee Name</th>";
		result = result + "    <th>Number Of Days</th>";
		result = result + "    <th>Status</th>";
		result = result + "  </tr>";
		result = result + "</thead>";
		result = result + "<tbody>";
		result = result + " </tbody>";
		result = result + "</table>";
		$("#topLeaveTakersDiv").append(result);
		topLeaveTakersPagination(url);
	}

</script>


<script>
function topLeaveTakersPagination(url){
	var colIndex = 0;
		 $('#topLeaveTakersTable').DataTable( { 
	    	"processing": true,
	        "serverSide": true,
	        "searching": false,
	        "ajax": $.fn.dataTable.pipeline( {
	            url: url,
	            method: 'POST',
	            pages: 10
	        }),
	        "columns": [
	            { "data": "empName" },
	            { "data": "noOfDays"},
	            { "data": "status" }
	        ],
	        "columnDefs": [ {
		    	  	"targets": colIndex++,
		    	  	"data": "empName",
		    	  	"render": function(data, type, report, meta) {
		    	  		var result = "";
		    	  		result = result + '<td>';
		    	  		result = result + '<div class="tbldata-dotdot-big" title="">';
		    	  		result = result + '<a href="javascript:void(0)">'+report.empName+'</a>';
		    	  		result = result + '</div>';
		    	  		result = result + '</td>';
		    	  		return  result; 
		    	  	}
		      },{
	            "targets": colIndex++,
	            "data": "noOfDays",
	            "render": function ( data, type, report, meta ) {
	            	    var result = "";
	            	    result = result + '<td>';
	            	    result = result + '<div class="tbldata-dotdot-100">'+report.noOfDays+'</div>';
	            	    result = result + '</td>';
		              return result;
	            }
	          }, {
	              "targets": colIndex++,
	              "data": "status",
	              "render": function ( data, type, report, meta ) {
	            	   var result = "";
	            	   result = result + '<td><div class="tblminwidth-75">'+report.status+'</div></td>';
	  	              return result; 
	              }
	            }],
		    	"scrollCollapse": false,
		    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
		    	"aaSorting": [[0,'desc']],
		    	"bLengthChange": true,
		    	"bDestroy": true,
		    	"ordering": true,
		    	"oLanguage": {
		     		"sSearch": "",
		     		 "sZeroRecords": "", "sEmptyTable": ""
		     		
		     }
	    } );
	    $("#topLeaveTakersTable").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
	    $("input[type=search]").hide();
	   /*  $("#dataTableSearch").keyup(function(e) {
	    	   if(e.which === 13) {
	    		   getClientList();
	    	   }
	    	}); */
}
</script>


<script>
	$(function() {
		 var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		  var finalDateFormatEndDate = '';
		  if(locationDate.match("yyyy-MM-dd")){
			  finalDateFormatEndDate = 'yy-mm-dd';
		  }if(locationDate.match("dd-MM-yyyy")){
			  finalDateFormatEndDate = 'dd-mm-yy'; 
		  }if(locationDate.match("MM-dd-yyyy")){
			  finalDateFormatEndDate = 'mm-dd-yy'; 
		  }if(locationDate.match("yyyy-dd-MM")){
			  finalDateFormatEndDate = 'yy-dd-mm'; 
		  }
		$('#reportFromDate').datepicker({
			dateFormat : finalDateFormatEndDate,
			changeMonth: true,
			changeYear: true, 
			onSelect : function(selected) {
			var dt = $(this).datepicker('getDate', '+1d');
			dt.setDate(dt.getDate());
			$("#toDate").datepicker("option", "minDate", dt);
			removeStartValid();
			}
		});
		
	  $('#reportEndDate').datepicker({
			dateFormat : finalDateFormatEndDate,
			changeMonth: true,
			changeYear: true, 
			onSelect : function(selected) {
			var dt = $(this).datepicker('getDate', '-1d');
			dt.setDate(dt.getDate());
			$("#fromdate").datepicker("option", "maxDate", dt);
			removeEndValid();
			validateDate();
			}
		});
		
	});
</script>




<jsp:include page="../masterFooter.jsp" />