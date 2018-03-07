<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />


<!-- Content Area -->
<script>
 $(document).ready(function() {
	 usageReportList("ACTIVE");
// 	 getCountList();
	 getAllOrgs();
	 
 });
 
 </script>

  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Users Usage Report List</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Users Usage Report List</div>
    </section>
      <!-- Tabs html -->
      <div class="clearfix"></div>
<div class="clearfix">
      <div class="ReqRepHead clearfix mb reports-head">
    		      <div class="col-md-2 pull-left reqListBtn">
          <a href="user-usagereport.html" title="List View" id="GView" class="btn btn-default"><i class="fa fa-pie-chart"></i></a>
          <a href="user-usagereport-list.html" title="List View" id="LView" class="btn btn-default rAct"><i class="fa fa-list"></i></a>
    		      </div>
     		<!--<a href="jobsPipline-list.html" class="btn btn-primary">List</a>-->
       
        <div class="col-md-4 col-md-offset-1">
    <div class="dropdown-scroll">
		<select class="chosen-select form-control" multiple data-placeholder="Select Org" name="orgId" id="orgs">
		</select>
	</div>
        </div>
        <div class="col-md-2 dropdown-select">
        <div class="dropdown-select positionRelative">
        <select class="form-control" id="customDates">
          <option>Select Date</option>
          <option value="invoiceweekly">Last 7 days</option>
          <option value="invoice15Days">Last 15 days</option>
          <option value="invoice1Month">1 month</option>
          <option value="invoice3Month">3 months</option>
          <option value="invoices" selected>Last year</option>
          <option>Year to Date</option>
          <option value="custom-select">Custom</option>
        </select>
        <!-- Top Leave Takers Custom Date -->
    <div class="clearfix pull-lefet col-md-12 paddingRight customLeaves" id="custom-select" style="display: none; width: 346px;">
		<div class="pull-left">
		<div class="pull-left mr5" style="width: 140px">
		<input value="" name="Date" id="datepicker4" placeholder="From" class="form-control calendar input-sm" type="text">
		</div>
		<div class="pull-left mr5" style="width: 140px">
		<input value="" name="Date" id="datepicker2" placeholder="To" class="form-control calendar input-sm" type="text">
		</div>
		<button type="button" class="btn btn-primary btn-sm"><i class="fa fa-search"></i></button>
		</div>
    </div>
<!-- Top Leave Takers Custom Date --> 
      </div>

        </div>
        <div class="col-md-2">
        <button type="button" class="btn btn-primary btn-sm	mt2" onclick="usageReportList('ACTIVE')">Customize</button>
		</div>
		<div class="col-md-1">
          <div class="btn-group bootstrap_dropdownMenu dropdownPadding">
            <button type="button" class="btn btn-primary btn-sm" title="Actions"><i class="fa fa-download"></i></button>
            <button type="button" class="btn btn-primary dropdown-toggle btn-sm" data-toggle="dropdown"> <span class="caret"></span> </button>
            <ul class="dropdown-menu clearfix  dropdown-menu-right">
              <li role="Export" class="mt5 cursorPointer"><i class="fa fa-file-pdf-o paddingRight"></i>Export</li>
            </ul>
          </div>
        </div>
      </div>
    </div>
                 <div class="dashboard-widget">
          <div class="positionRelative"> 
<div class="col-md-12 mt">
  <ul class="list-unstyled list-inline col-md-offset-2">
    <li class="col-md-2">
      <div class="text-center well well-sm usageGrid" id="activeLi">
        <div class="clearfix"><i class="fa fa-user fa-2x text-primary"></i></div>
        <h4>Active</h4>
        <label class="views-count usersList" onClick="usageReportList('ACTIVE');usageactiveList();" id="activeCount"></label>
      </div>
    </li>
    <li class="col-md-2">
      <div class="text-center well well-sm" id="inactiveLi">
        <div class="clearfix"><i class="fa fa-user-times fa-2x text-danger"></i></div>
        <h4>Inactive</h4>
        <label class="views-count submited2client" onClick="usageReportList('INACTIVE');usageinactiveList();" id="inActiveCount"></label>
      </div>
    </li>
    <li class="col-md-2">
      <div class="text-center well well-sm" id="notloginLi">
        <div class="clearfix"><i class="fa fa-lock fa-2x text-warning"></i></div>
        <h4>Not Login</h4>
        <label class="views-count interview" onClick="usageReportList('NOT_LOGGEDIN');usagenotloginList();" id="notLoggedInCount"></label>
      </div>
    </li>
    <li class="col-md-2">
      <div class="text-center well well-sm" id="totalLi">
        <div class="clearfix"><i class="fa fa-users fa-2x text-info"></i></div>
        <h4>Total</h4>
        <label class="views-count rejected" id="totalUserCount"></label>
      </div>
    </li>
         <li class="col-md-2">
      <div class="text-center well well-sm" id="multipleLi">
        <div class="clearfix"><i class="fa fa-sliders fa-2x text-success"></i></div>
        <h4>Multiple Ips</h4>
        <label class="views-count offer" onClick="usagemultipleipslist()">50</label>
      </div>
    </li>
  </ul>
</div>
<div class="clearfix"></div>
<div class="timesheet_out clearfix userlistGrid" id="activeList">
  <div class="timesheet_head">
    <p class="pull-left mt ml"><strong>Active User List</strong></p>
    <ul class="pull-right">
      <li class="dropdown"> <span class="dropdown-toggle" id="menu1" data-toggle="dropdown" aria-expanded="false"> <i class="fa fa-filter"></i><span>Job Status</span><i class="glyphicon glyphicon-play"></i></span>|
        <div class="dropdown-menu bootstrap_dropdownMenu dropdownPadding" role="menu" aria-labelledby="menu1">
          <ul>
            <li class="checkBox">
              <input value="" name="resourcerequest" id="jobNew" type="checkbox">
              <label for="all">New</label>
            </li>
            <li class="checkBox">
              <input id="jobOpen" name="resourcerequest" type="checkbox">
              <label for="hotlist">Open</label>
            </li>
            <li class="checkBox">
              <input id="jobOnhold" name="resourcerequest" type="checkbox">
              <label for="Assigned">Onhold</label>
            </li>
            <li class="checkBox">
              <input id="jobFilledClosed" name="resourcerequest" type="checkbox">
              <label for="Assigned">Filled Closed</label>
            </li>
            <li class="checkBox">
              <input id="jobUnfilledClosed" name="resourcerequest" type="checkbox">
              <label for="Assigned">Unfilled Closed</label>
            </li>
            <li class="checkBox">
              <input id="jobClosed" name="resourcerequest" type="checkbox">
              <label for="Assigned">Closed</label>
            </li>
          </ul>
        </div>
      </li>
      
      
      <div class="clearfix"></div>
    </ul>
    <div class="clearfix"></div>
     
  </div>
  <div class="require_wrap_table datatable_filter">
  
  <div id="activeDiv"></div>
  
  </div>
  
  </div>
  
  </script>
<script type="text/javascript">
       function usageReportList(userType){
		var sourceleadUrl ='${sourceleadUrl}';
		// var tab = "previous";
// 		 var tab = '${tab}';
	    var orgId = $("#orgs").val();
	    if(orgId != null && orgId != '' && orgId != 'undefined'){
	    	orgId = $("#orgs").val();
	    }else{
	    	orgId = "";
	    }
		var searchValue = $("#dataTableSearch").val();
		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		searchValue = searchValue ? searchValue : "";
		var url = sourceleadUrl+"/userUsageReportList?searchValue="+searchValue+"&orgLocationId="+orgLocationId+"&userType="+userType+"&orgId="+orgId;
	    prepareUsageReportList(url, userType);
}
</script> 


<script>
function prepareUsageReportList(url, userType){
        var result = "";
 	    result = result +' <div class="modal-header">';
 	    result = result +' <div class="remove_search">';
 	    result = result +' <input type="text" placeholder="Search" id="dataTableSearch"/>';
 	    result = result +'</div>';
 	    result = result +'</div>';
        result = result +'<table id="activePagination" class="display" cellspacing="0" width="100%" >'; 
        result = result +'<thead>'; 
        result = result + '<tr>';
		result = result + '<th>Users</th>';
		result = result + '<th>Email</th>';
		result = result + '<th>User Type</th>';
		result = result + '<th>Org</th>';
		result = result + '<th>No.of Activities</th>';
		result = result + '<th>Multiple Ips</th>';
		result = result + '<th>Created On</th>';
		result = result + '<th>Last Activity On</th>';
		result = result + '<th>Action</th>';
		result = result + '</tr>';
        result = result +'</thead>';
        result = result +'<tbody>'; 
        result = result +' </tbody>';
        result = result +'</table>';
        $("#activeDiv").empty();
        $("#activeDiv").append(result);
        activePagination(url, userType);
}
</script>

<script>
function activePagination(url, userType){
	var colIndex = 0;
    $('#activePagination').DataTable( { 
    		"processing": true,
        "serverSide": true,
        "searching": false,
        "ajax": $.fn.dataTable.pipeline( {
            url: url,
            method: 'POST',
            pages: 10
        }),
        "columns": [
            { "data": "users" },
            { "data": "email"},
            { "data": "userType"},
            { "data": "org" },
            { "data": "noOfActivities"},
            { "data": "createdOn" },
            { "data": "lastActivityOn" },
            { "data": "action"},
            
        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "users",
	    	  	"render": function(data, type, opt, meta) {
	    	  		  var result = "";
	    	  		 if(opt.userName !=null && opt.userName !='' && opt.userName !='undefined'){
							result = result + ' <td><div><span>'+opt.userName+'</span></div></td>';
						}else{
							result = result + ' <td><div>--</div></td>';
						}
		             return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "email",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  if(opt.userEmail !=null && opt.userEmail !='' && opt.userEmail !='undefined'){
            		  result = result + ' <td><div><span>'+opt.userEmail+'</span></div></td>';
					}else{
						result = result + ' <td><div>--</div></td>';
					}
            	  return result;
              		
              }
	          },{
            "targets": colIndex++,
            "data": "userType",
            "render": function ( data, type, opt, meta ) {
            	var result = "";
            	
   				if(opt.userType != null && opt.userType != '' && opt.userType !='undefined'){
   					result = result + '<td><div><span>'+opt.userType+' </span></div></td>';
            	 }else{
            		 result = result + '<td><div><span>---</span></div></td>';
            	 }
   				return result;
            }
          }, {
              "targets": colIndex++,
              "data": "org",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  
            	  result = result +'<td>';
 		        	result = result +'<div class="positionRelative">';
	   		         for(var k=0; k<1; k++){
	   		        	result = result + '<p class="tbldata-dotdot-me pull-left">'+opt.enrolledOrgs[k].orgName+'</p>';
	   		         }
 		    	
	   		        result = result +'<div class="show_Empdetails showjoblist">';
 		        	result = result +'<i class="fa fa-ellipsis-v"></i>';

 		        	result = result +'<div class="emailvalues emailvaluesNew">';
 		        	
					for(var k=0; k<opt.enrolledOrgs.length; k++){
						result = result +'<p><i class="fa fa-map-marker"></i>'+opt.enrolledOrgs[k].orgName+'</p>';
	   		        	
 		    	 	}
 		        	result = result +'</div>';
 		        	result = result +'</div>';
 

		        	result = result +'</div>';
		        	result = result +'</td>';
		        	
            	  return result;
              }
            }, {
                "targets": colIndex++,
                "data": "noOfActivities",
                "render": function ( data, type, opt, meta ) {
                		var result = "";
                		if(opt.noOfActivities !=null && opt.noOfActivities !='' && opt.noOfActivities !='undefined'){
							result = result + ' <td><div><span>'+opt.noOfActivities+'</span></div></td>';
						}else{
							result = result + ' <td><div><span>---</span></div></td>';
						}
    	              return result;
                }
          },{
              "targets": colIndex++,
              "data": "multipleIps",
              "render": function ( data, type, opt, meta ) {
              		var result = "";
              		if(opt.multipleIps !=null && opt.multipleIps !='' && opt.multipleIps !='undefined'){
							result = result + ' <td><div><span>'+opt.multipleIps+'</span></div></td>';
						}else{
							result = result + ' <td><div><span>---</span></div></td>';
						}
  	              return result;
              }
        },
          {
	    	  	"targets": colIndex++,
	    	  	"data": "createdOn",
	    	  	"render": function(data, type, opt, meta) {
	    	  		var result = "";
	    	  		if(opt.createdOn !=null && opt.createdOn !='' && opt.createdOn !='undefined'){
	    	  			result = result + ' <td><div><span>'+opt.createdOn+'</span></div></td>';
					}else{
						result = result + ' <td><div>--</div></td>';
					}
  	              return result;
	    	  	}
	    	  },{
	    	  	"targets": colIndex++,
	    	  	"data": "lastActivityOn",
	    	  	"render": function(data, type, opt, meta) {
	    	  		var result = "";
	    	  		if(opt.lastActivityOn != null && opt.lastActivityOn !='' && opt.lastActivityOn != 'undefined'){
	    	  			result = result + ' <td><div><span>'+opt.lastActivityOn+'</span></div></td>';
	    	  		}else{
	    	  			result = result + ' <td><div><span>---</span></div></td>';
	    	  		}
	    	  		return result;
	    	  	}
	    	  }, {
                  "targets": colIndex++,
                  "data": "action",
                  "render": function ( data, type, opt, meta ) {
                	  var result = "";
  	    	  				result = result + ' <td>';
  	    	  		 if(userType == 'ACTIVE'){
  	    	  			result = result + ' <div class="tblminwidth-75" title="Closed">';
    	  		    	result = result + '<label class="switch mt10">';
    	  		     	result = result + '<input type="checkbox" onclick="blockActiveUser('+opt.userRoleId+','+opt.locationId+');" checked>';
    	  		    	result = result + '<span class="slider round"></span> </label>';
    	  		    	result = result + ' </div>';
  	    	  		    
	                  }else{
	                	  result = result + '<span> --- </span>';
	                  }
    	  		        result = result + ' </td>';
	                  
  	    	  		return result;
                  }
                }],
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
// 	    	"aaSorting": [[3,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#activePagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#dataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   usageReportList(userType);
    	   }
    	});
    getCountList();
}

</script>
  
  
    
<div class="timesheet_out clearfix userlistGrid" id="multipleList" style="display: none">
  <div class="timesheet_head">
    <p class="pull-left mt ml"><strong>Multiple IPS User List</strong></p>
    <ul class="pull-right">
      <li class="dropdown"> <span class="dropdown-toggle" id="menu1" data-toggle="dropdown" aria-expanded="false"> <i class="fa fa-filter"></i><span>Job Status</span><i class="glyphicon glyphicon-play"></i></span>|
        <div class="dropdown-menu bootstrap_dropdownMenu dropdownPadding" role="menu" aria-labelledby="menu1">
          <ul>
            <li class="checkBox">
              <input value="" name="resourcerequest" id="jobNew" type="checkbox">
              <label for="all">New</label>
            </li>
            <li class="checkBox">
              <input id="jobOpen" name="resourcerequest" type="checkbox">
              <label for="hotlist">Open</label>
            </li>
            <li class="checkBox">
              <input id="jobOnhold" name="resourcerequest" type="checkbox">
              <label for="Assigned">Onhold</label>
            </li>
            <li class="checkBox">
              <input id="jobFilledClosed" name="resourcerequest" type="checkbox">
              <label for="Assigned">Filled Closed</label>
            </li>
            <li class="checkBox">
              <input id="jobUnfilledClosed" name="resourcerequest" type="checkbox">
              <label for="Assigned">Unfilled Closed</label>
            </li>
            <li class="checkBox">
              <input id="jobClosed" name="resourcerequest" type="checkbox">
              <label for="Assigned">Closed</label>
            </li>
          </ul>
        </div>
      </li>
      <div class="clearfix"></div>
    </ul>
    <div class="clearfix"></div>
     
  </div>
  <div class="require_wrap_table">
    <table>
      <thead>
        <tr>
          <th>IP Address</th>
          <th>No. of Users</th>
          <th>Location</th>
          <th>No. of Activities</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td><div class="tbldata-dotdot-200" title="192.168.14.290"><a href="activeactivities-usersusage.html">192.168.14.290</a></div></td>
          <td><div title="20" class="tblminwidth-75">20</div></td>
          <td><div title="Hyderabad" class="tblminwidth-75">Hyderabad</div></td>
          <td><div title="100" class="tblminwidth-75">100</div></td>
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
<div class="timesheet_out clearfix userlistGrid" id="totalList" style="display: none">
  <div class="timesheet_head">
    <p class="pull-left mt ml"><strong>Total User List</strong></p>
    <ul class="pull-right">
      <li class="dropdown"> <span class="dropdown-toggle" id="menu1" data-toggle="dropdown" aria-expanded="false"> <i class="fa fa-filter"></i><span>Job Status</span><i class="glyphicon glyphicon-play"></i></span>|
        <div class="dropdown-menu bootstrap_dropdownMenu dropdownPadding" role="menu" aria-labelledby="menu1">
          <ul>
            <li class="checkBox">
              <input value="" name="resourcerequest" id="jobNew" type="checkbox">
              <label for="all">New</label>
            </li>
            <li class="checkBox">
              <input id="jobOpen" name="resourcerequest" type="checkbox">
              <label for="hotlist">Open</label>
            </li>
            <li class="checkBox">
              <input id="jobOnhold" name="resourcerequest" type="checkbox">
              <label for="Assigned">Onhold</label>
            </li>
            <li class="checkBox">
              <input id="jobFilledClosed" name="resourcerequest" type="checkbox">
              <label for="Assigned">Filled Closed</label>
            </li>
            <li class="checkBox">
              <input id="jobUnfilledClosed" name="resourcerequest" type="checkbox">
              <label for="Assigned">Unfilled Closed</label>
            </li>
            <li class="checkBox">
              <input id="jobClosed" name="resourcerequest" type="checkbox">
              <label for="Assigned">Closed</label>
            </li>
          </ul>
        </div>
      </li>
      <div class="clearfix"></div>
    </ul>
    <div class="clearfix"></div>
     
  </div>
  <div class="require_wrap_table">
    <table>
      <thead>
        <tr>
          <th>Users</th>
          <th>Email</th>
          <th>User Type</th>
          <th>Org</th>
          <th>No. of Activities</th>
          <th>Created On</th>
          <th>Last Activity On</th>
          <th>Action</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td><div class="tbldata-dotdot" title="BIS10041JavaDeveloper">BIS10041JavaDeveloper </div></td>
          <td><div title="TCS, Dalls" class="tbldata-dotdot-vsm">businessintelli@info.com</div></td>
          <td><div title="TCS, Dalls" class="tbldata-dotdot-vsm">Org</div></td>
          <td><div title="TCS, Dalls" class="tbldata-dotdot-vsm">Tata Consultancy Services</div></td>
          <td><div title="TCS, Dalls" class="tblminwidth-75">100</div></td>
          <td><div title="TCS, Dalls" class="tblminwidth-75">23/07/2017</div></td>
          <td><div title="TCS, Dalls" class="tblminwidth-75">23/07/2017</div></td>
          <td><div class="tblminwidth-75" title="Closed">
              <label class="switch mt10">
                <input type="checkbox" checked>
                <span class="slider round"></span> </label>
            </div></td>
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
      <!-- Tabs html --> 
  </section>
  
  <script>
        //Usage User show hide script
        function usageactiveList(){
//             $("#inactiveList").hide();
//             $("#activeList").toggle();
//             $("#notloginList").hide();
//             $("#multipleList").hide();
//             $("#totalList").hide();
            $("#activeLi").addClass("usageGrid");
            $("#inactiveLi").removeClass("usageGrid");
            $("#notloginLi").removeClass("usageGrid");
            $("#multipleLi").removeClass("usageGrid");
            $("#totalLi").removeClass("usageGrid");
        }
        function usageinactiveList(){
            $("#inactiveList").toggle();
//             $("#activeList").hide();
//             $("#notloginList").hide();
//             $("#multipleList").hide();
//             $("#totalList").hide();
            $("#activeLi").removeClass("usageGrid");
            $("#inactiveLi").addClass("usageGrid");
            $("#notloginLi").removeClass("usageGrid");
            $("#multipleLi").removeClass("usageGrid");
            $("#totalLi").removeClass("usageGrid");
        }
        function usagenotloginList(){
//             $("#inactiveList").hide();
//             $("#activeList").hide();
            $("#notloginList").toggle();
//             $("#multipleList").hide();
//             $("#totalList").hide();

            $("#activeLi").removeClass("usageGrid");
            $("#inactiveLi").removeClass("usageGrid");
            $("#notloginLi").addClass("usageGrid");
            $("#multipleLi").removeClass("usageGrid");
            $("#totalLi").removeClass("usageGrid");
        }
        function usagemultipleipslist(){
//             $("#inactiveList").hide();
//             $("#activeList").hide();
//             $("#notloginList").hide();
            $("#multipleList").toggle();
//             $("#totalList").hide();

            $("#activeLi").removeClass("usageGrid");
            $("#inactiveLi").removeClass("usageGrid");
            $("#notloginLi").removeClass("usageGrid");
            $("#multipleLi").addClass("usageGrid");
            $("#totalLi").removeClass("usageGrid");
        }
        function usagetotalList(){
//             $("#inactiveList").hide();
//             $("#activeList").hide();
//             $("#notloginList").hide();
//             $("#multipleList").hide();
            $("#totalList").toggle();

            $("#activeLi").removeClass("usageGrid");
            $("#inactiveLi").removeClass("usageGrid");
            $("#notloginLi").removeClass("usageGrid");
            $("#multipleLi").removeClass("usageGrid");
            $("#totalLi").addClass("usageGrid");
        }
        
        //Usage org show hide script
        function orgactiveList(){
            $("#orgactiveList").toggle();
//             $("#orgguestList").hide();
//             $("#orginactiveList").hide();
//             $("#orgtotalList").hide();
            
            $("#orgactiveLi").addClass("usageGrid");
            $("#orgguestLi").removeClass("usageGrid");
            $("#orginactiveLi").removeClass("usageGrid");
            $("#orgtotalLi").removeClass("usageGrid");
        }
        function orgguestList(){
//             $("#orgactiveList").hide();
            $("#orgguestList").toggle();
//             $("#orginactiveList").hide();
//             $("#orgtotalList").hide();
            
            $("#orgactiveLi").removeClass("usageGrid");
            $("#orgguestLi").addClass("usageGrid");
            $("#orginactiveLi").removeClass("usageGrid");
            $("#orgtotalLi").removeClass("usageGrid");
        }
        function orginactiveList(){
//             $("#orgactiveList").hide();
//             $("#orgguestList").hide();
            $("#orginactiveList").toggle();
//             $("#orgtotalList").hide();
            
            $("#orgactiveLi").removeClass("usageGrid");
            $("#orgguestLi").removeClass("usageGrid");
            $("#orginactiveLi").addClass("usageGrid");
            $("#orgtotalLi").removeClass("usageGrid");
        }
        function orgtotalList(){
//             $("#orgactiveList").hide();
//             $("#orgguestList").hide();
//             $("#orginactiveList").hide();
            $("#orgtotalList").toggle();
            
            $("#orgactiveLi").removeClass("usageGrid");
            $("#orgguestLi").removeClass("usageGrid");
            $("#orginactiveLi").removeClass("usageGrid");
            $("#orgtotalLi").addClass("usageGrid");

        }
        
        
        //Usage Ticket show hide script
        function ticketclosedList(){
            $("#ticketclosedList").toggle();
//             $("#inprogressticketList").hide();
//             $("#notstartedticketList").hide();
//             $("#totalticketList").hide();
            
            $("#ticketclosedLi").addClass("usageGrid");
            $("#inprogressticketLi").removeClass("usageGrid");
            $("#notstartedticketLi").removeClass("usageGrid");
            $("#totalticketLi").removeClass("usageGrid");
        }
        function ticketinprogresList(){
//             $("#ticketclosedList").hide();
            $("#inprogressticketList").toggle();
//             $("#notstartedticketList").hide();
//             $("#totalticketList").hide();
            
            $("#ticketclosedLi").removeClass("usageGrid");
            $("#inprogressticketLi").addClass("usageGrid");
            $("#notstartedticketLi").removeClass("usageGrid");
            $("#totalticketLi").removeClass("usageGrid");
        }
        function ticketnotstartedList(){
//             $("#ticketclosedList").hide();
//             $("#inprogressticketList").hide();
            $("#notstartedticketList").toggle();
//             $("#totalticketList").hide();
            
            $("#ticketclosedLi").removeClass("usageGrid");
            $("#inprogressticketLi").removeClass("usageGrid");
            $("#notstartedticketLi").addClass("usageGrid");
            $("#totalticketLi").removeClass("usageGrid");
        }
        function tickettotalList(){
//             $("#ticketclosedList").hide();
//             $("#inprogressticketList").hide();
//             $("#notstartedticketList").hide();
            $("#totalticketList").toggle();
            
            $("#ticketclosedLi").removeClass("usageGrid");
            $("#inprogressticketLi").removeClass("usageGrid");
            $("#notstartedticketLi").removeClass("usageGrid");
            $("#totalticketLi").addClass("usageGrid");
        }
   </script>
   
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
    $('#priority').trigger('chosen:updated');
</script>

<script type="text/javascript">
       function getCountList(){
		var sourceleadUrl ='${sourceleadUrl}';
		var orgId = $("#orgs").val();
	    if(orgId != null && orgId != '' && orgId != 'undefined'){
	    	orgId = $("#orgs").val();
	    }else{
	    	orgId = "";
	    }
		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		$.get(sourceleadUrl+"/getCountList?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&orgId="+orgId, function(data) {
			 $("#activeCount").html(data.activeCount);
			 $("#inActiveCount").html(data.inactiveCount);
	    	 $("#notLoggedInCount").html(data.notLoggedinCount);
	    	 
	    	 var totalCount = data.activeCount + data.inactiveCount + data.notLoggedinCount;
	    	 
	    	 $("#totalUserCount").html(totalCount);
		}); 
}
</script>

<script type="text/javascript">
       function getAllOrgs(){
		var sourceleadUrl ='${sourceleadUrl}';
		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		$.get(sourceleadUrl+"/getAllOrgs?"+tokenParamName+"="+tokenValue, function(data) {
			
			var output = "";
	           for(var i=0; i<data.length;i++){
	              var opt = data[i];
	                output = output + "<option value='" +opt.id+ "'>"+opt.orgName+"</option>";
	           }
	           $("#orgs").empty();
	          $("#orgs").append(output);
	         $("#orgs").trigger("chosen:updated");
			
		}); 
}
</script> 

<script>
function blockActiveUser(userRoleId,locationId){
	var sourceleadUrl ='${sourceleadUrl}';
	$.get(sourceleadUrl+"/blockActiveUser?"+tokenParamName+"="+tokenValue+"&orgLocationId="+locationId+"&userRoleId="+userRoleId,function(data) {
		alert("hai");
		if(data == "success"){
		   usageReportList("ACTIVE");
		}
	});
  
}
</script>
  
  
<jsp:include page="../masterFooter.jsp" />