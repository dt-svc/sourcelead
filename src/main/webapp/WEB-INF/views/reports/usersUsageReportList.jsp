<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="/resources/amcharts/amcharts.js"></script>
<script src="/resources/amcharts/serial.js"></script>
<script src="/resources/amcharts/plugins/export/export.min.js"></script>
<link rel="stylesheet" href="/resources/amcharts/plugins/export/export.css" type="text/css" media="all" />

<!-- Content Area -->
<script>
 $(document).ready(function() {
     usageReportList("ACTIVE");
//      getCountList();
     getAllOrgs();
    
 });
 
 </script>
<input type="hidden" id="emptyUserUsageReportsId" name="emptyUserUsageReportsId"/>
<input type="hidden" id="userTypeId" name="userTypeId"/>
<input type="hidden" id="lastActivityCountIdHidden" name="lastActivityCountIdHidden" value="---"/>
<input type="hidden" id="lastActivityIpCountIdHidden" name="lastActivityIpCountIdHidden" value="---"/>
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Usage Report(s)</h2>
      </div>
      
  <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&raquo; <a href="<c:url value='${appPath}/reportsDashboard'/>">Reports	Dashboard</a> &raquo; Usage Report(s)</div>

      
<!--       <div class="pull-right brud-crum">Home &raquo; Users Usage Report List</div> -->
    </section>
      <!-- Tabs html -->
      <div class="clearfix"></div>
<div class="clearfix">
      <div class="ReqRepHead clearfix mb reports-head">
                  <div class="col-md-2 pull-left reqListBtn">
          <a  title="Graph View" id="GView" class="btn btn-default" onclick="usageReportsGraph();"><i class="fa fa-pie-chart"></i></a>
          <a  title="List View" id="LView" class="btn btn-default rAct" onclick="usageReportsShowList();"><i class="fa fa-list"></i></a>
                  </div>
             <!--<a href="jobsPipline-list.html" class="btn btn-primary">List</a>-->
       
        <div class="col-md-4 col-md-offset-2">
    <div class="dropdown-scroll">
    <sec:authorize access="hasRole('ROLE_ENROLL_ORG')">
        <select class="chosen-select form-control" multiple data-placeholder="Select Org" name="orgId" id="orgs">
        </select>
        </sec:authorize>
        
        <sec:authorize access="!hasRole('ROLE_ENROLL_ORG')">
        <input type="hidden" name="orgId" id="orgs" value="${SourceLeadWeb_orgLocations.org.id}">
        </sec:authorize>
    </div>
        </div>
        
          <div class="col-md-2">
        
        <sec:authorize access="hasRole('ROLE_ENROLL_ORG')">
        <button type="button" class="btn btn-primary mt2  pull-left mr" id="reportCustomize" onclick="usageReportList('ACTIVE');">Customize</button>
        </sec:authorize>
        
        </div>
         <div class="col-md-2 dropdown-select">
        <div class="dropdown-select positionRelative">
        <select class="form-control" id="customGraphDates" style= display:none onchange="showCustomFieldsGraph();">
<!--           <option>Select Date</option> -->
          <option value="Current_Year">Current Year</option>
          <option value="Last_Year">Last year</option>
          <option value="Custom">Custom</option>
        </select>
        <!-- Top Leave Takers Custom Date -->
        
    <div class="clearfix pull-lefet col-md-12 paddingRight customLeaves" id="custom-select" style="display: none; width: 346px;" >
        <div class="pull-left">
        <div class="pull-left mr5" style="width: 140px" id="custFromDate1Div">
        <input value="" name="Date" id="custFromDate1" placeholder="From" class="form-control calendar input-sm" type="text">
        </div>
        <div class="pull-left mr5" style="width: 140px" id="custToDate1Div">
        <input value="" name="Date" id="custToDate1" placeholder="To" class="form-control calendar input-sm" type="text">
        </div>
        <button type="button" class="btn btn-primary btn-sm" onclick="validateCustDatesGraph();"><i class="fa fa-search"></i></button>
        </div>
    </div>
<!-- Top Leave Takers Custom Date  -->
      </div>

        </div>
     
<!--         <div class="col-md-1"> -->
<!--           <div class="btn-group bootstrap_dropdownMenu dropdownPadding"> -->
<!--             <button type="button" class="btn btn-primary btn-sm" title="Actions"><i class="fa fa-download"></i></button> -->
<!--             <button type="button" class="btn btn-primary dropdown-toggle btn-sm" data-toggle="dropdown"> <span class="caret"></span> </button> -->
<!--             <ul class="dropdown-menu clearfix  dropdown-menu-right"> -->
<!--               <li role="Export" class="mt5 cursorPointer" id="mypop"><i class="fa fa-file-pdf-o paddingRight"></i>Export</li> -->
<!--             </ul> -->
<!--           </div> -->
<!--         </div> -->
                      
      </div>
    </div>
                 <div class="dashboard-widget" >
                 
                 <div id="userUsageReportsListSection">
                 <div class="positionRelative">
<div class="col-md-12 mt">
  <ul class="list-unstyled list-inline col-md-offset-2">
    <li class="col-md-2">
      <div class="text-center well well-sm usageGrid" id="activeLi">
        <div class="clearfix"><i class="fa fa-user fa-2x text-primary"></i></div>
        <h5>Active</h5>
        <label class="views-count usersList" onClick="usageReportList('ACTIVE');usageactiveList();" id="activeCount"></label>
<!--       <input type="text" value='ACTIVE' id="activeCount" class="form-control"> -->
      </div>
    </li>
    <li class="col-md-2">
      <div class="text-center well well-sm" id="inactiveLi">
        <div class="clearfix"><i class="fa fa-user-times fa-2x text-danger"></i></div>
        <h5>Inactive</h5>
        <label class="views-count submited2client" onClick="usageReportList('INACTIVE');usageinactiveList();" id="inActiveCount"></label>
      </div>
    </li>
    <li class="col-md-2">
      <div class="text-center well well-sm" id="notloginLi">
        <div class="clearfix"><i class="fa fa-lock fa-2x text-warning"></i></div>
        <h5>Not Logged In</h5>
        <label class="views-count interview" onClick="usageReportList('NOT_LOGGEDIN');usagenotloginList();" id="notLoggedInCount"></label>
      </div>
    </li>
    <li class="col-md-2">
      <div class="text-center well well-sm" id="totalLi">
        <div class="clearfix"><i class="fa fa-users fa-2x text-info"></i></div>
        <h5>Total</h5>
        <label class="view-count" id="totalUserCount"></label>
      </div>
    </li>
         <li class="col-md-2">
      <div class="text-center well well-sm" id="multipleLi">
        <div class="clearfix"><i class="fa fa-sliders fa-2x text-success"></i></div>
        <h5>IP Details</h5>
        <label class="views-count offer" onClick="usagemultipleipslist('MULTIPLE_IP')" id="multipleIpsCount"></label>
      </div>
    </li>
  </ul>
</div>
<div class="clearfix"></div>
<div class="timesheet_out clearfix userlistGrid" id="activeList">
  <div class="timesheet_head">
    <p class="pull-left mt ml"><strong><b id="listNamediv">Active User List</b></strong></p>
    <ul>
        <!-- <li>
    <div title="Export" class="mrPOP mt10 pull-right" id="mypop"><i class="glyphicon glyphicon-export"></i>
                     <div id="exportPop" class="ExpPop" style="left: 0px; display: none;">
                       <ul>
                         <li onclick="exportUserUsageReportPdf();"><i class="fa fa-file-pdf-o"></i>&nbsp;&nbsp;PDF</li>
                 <li onclick="exportUserUsageReportExcel();"><i class="fa fa-file-excel-o text-primary"></i>&nbsp;&nbsp;Excel</li>
                </ul>
              </div>
            </div>
    </li> -->
    <li>
    <div class="col-md-12 dropdown-select">
        <div class="dropdown-select positionRelative">
        <select class="form-control" id="customDates" onchange="showCustomFields();">
<!--           <option>Select Date</option> -->
          <option value="Last_7_days" selected>Last 7 days</option>
          <option value="Last_15_days">Last 15 days</option>
          <option value="1_month">1 month</option>
          <option value="3_months">3 months</option>
          <option value="Last_Year">Last year</option>
          <option value="Custom">Custom</option>
        </select>
        <!-- Top Leave Takers Custom Date -->
    <div class="clearfix pull-lefet col-md-12 paddingRight customLeaves" id="custom-selects" style="display: none; width: 346px;" >
        <div class="pull-left">
        <div class="pull-left mr5" style="width: 140px" id="custFromDateDiv">
        <input value="" name="Date" id="custFromDate" placeholder="From" class="form-control calendar input-sm margin-top0" type="text">
        </div>
        <div class="pull-left mr5" style="width: 140px" id="custToDateDiv">
        <input value="" name="Date" id="custToDate" placeholder="To" class="form-control calendar input-sm margin-top0" type="text">
        </div>
        <button type="button" class="btn btn-primary btn-sm" onclick="validateCustDatesList();"><i class="fa fa-search"></i></button>
        </div>
    </div>
<!-- Top Leave Takers Custom Date -->
      </div>
        </div>
    </li>
    </ul>
    <div class="clearfix"></div>
     
  </div>
  <div class="require_wrap_table datatable_filter">
  <div id="activeDiv"></div>
 
  </div>
  
  <div class="require_wrap_table overflow_inherint">
    <div id="multipleIpsDiv"></div>
  
  </div>
 
  </div>
 
  <div id="popUp"></div>
 

 
 
    
<!-- <div class="timesheet_out clearfix userlistGrid" id="multipleList" style="display: none">
  <div class="timesheet_head">
    <p class="pull-left mt ml"><strong>Multiple IPS User List</strong></p>
    <div class="clearfix"></div>
     
  </div>
   <div class="require_wrap_table datatable_filter">
   
   <div id="multipleIpsDiv"></div>
   
   </div>
   
   </div> -->
   

          </div>
                 </div>
                 
          <div id="useractiveGraph" style="width: 100%; height: 300px; display:none"></div>
        </div>
      <!-- Tabs html -->
      
      
      
  </section>
 
 <script>
 function usageReportsGraph(){
     $('#reportCustomize').removeAttr("onclick");
     $('#reportCustomize').attr("onclick", "usageReportsGraph()");
     $("#custom-select").hide();
     $('#custFromDate1').removeAttr('data-validation');
     $('#custToDate1').attr('data-validation',"required");
     $("#customGraphDates").show();
     $("#customDates").hide();
     $("#GView").addClass("rAct");
     $("#LView").removeClass("rAct");
     $("#useractiveGraph").show();
     $("#activeDiv").hide();
     $("#userUsageReportsListSection").hide();
    
     var orgLocationId = '${SourceLeadWeb_orgLocationId}';
        var fromDate = $("#custFromDate1").val();
        var toDate = $("#custToDate1").val();
        var sourceleadUrl ='${sourceleadUrl}';
        var customGraphDates = $("#customGraphDates").val();
        var orgId = $("#orgs").val();
        if(orgId != null && orgId != '' && orgId != 'undefined'){
            orgId = $("#orgs").val();
        }else{
            orgId = "";
        }
        var url = sourceleadUrl+"/userUsageGraph?locationId="+orgLocationId+"&customDates="+customGraphDates+"&fromDate="+fromDate+"&toDate="+toDate+"&orgId="+orgId;
        $.get(url, function(data) {
         prepareAmchart(data);
        });
 }
 
 function usageReportsShowList(){
     $("#custom-selects").hide();
     $('#reportCustomize').removeAttr("onclick");
     $('#reportCustomize').attr("onclick", "usageReportList(\'ACTIVE\')");
     $('#custFromDate').removeAttr('data-validation');
     $('#custToDate').attr('data-validation',"required");
     $("#customGraphDates").hide();
     $("#customDates").show();
     $("#LView").addClass("rAct");
     $("#GView").removeClass("rAct");
     $("#useractiveGraph").hide();
     $("#activeDiv").show();
     $("#userUsageReportsListSection").show();
 }
 </script>
 
  <script type="text/javascript">
       function usageReportList(userType){
           $("#emptyUserUsageReportsId").val(0);
           $("#userTypeId").val(userType);
            var fromDate = $("#custFromDate").val();
            var toDate = $("#custToDate").val();
            var sourceleadUrl ='${sourceleadUrl}';
            var orgId = $("#orgs").val();
            if(orgId != null && orgId != '' && orgId != 'undefined'){
                orgId = $("#orgs").val();
            }else{
                orgId = "";
            }
            var customDates = $("#customDates").val();
            var searchValue = $("#dataTableSearch").val();
            var orgLocationId = '${SourceLeadWeb_orgLocationId}';
            searchValue = searchValue ? searchValue : "";
            var url = sourceleadUrl+"/userUsageReportList?searchValue="+searchValue+"&orgLocationId="+orgLocationId+"&userType="+userType+"&orgId="+orgId+"&customDates="+customDates+"&fromDate="+fromDate+"&toDate="+toDate;
            prepareUsageReportList(url, userType);
}
</script>


<script>
function prepareUsageReportList(url, userType){
        var result = "";
         result = result +' <div class="clearfix">';
         result = result +' <div class="remove_search mt">';
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
//         result = result + '<th>Action</th>';
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
//             { "data": "action"},
            
        ],
        "columnDefs": [ {
                  "targets": colIndex++,
                  "data": "users",
                  "render": function(data, type, opt, meta) {
                      $("#emptyUserUsageReportsId").val(1);
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
                        if(userType != "NOT_LOGGEDIN"){
                            result = result + ' <td><div class="tblminwidth-50"><a id="'+opt.userId+'countId" data-id="'+opt.userId+'" class="viewActiveCount"><i class="fa fa-spinner fa-pulse"></i></a></div><div class="tblminwidth-50"  style="display:none;"><span title="100"></span></div></td>';
                        
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
                      if(userType != "NOT_LOGGEDIN"){
                      if(opt.multipleIps !=null && opt.multipleIps !='' && opt.multipleIps !='undefined'){
                            result = result + ' <td><div><span>'+opt.multipleIps+'</span></div></td>';
                        }else{
                            result = result + ' <td><div><span>---</span></div></td>';
                        }
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
                      var lastActivityHiddenCount = $("#lastActivityCountIdHidden").val();
                      if(userType != "NOT_LOGGEDIN"){
                      /* if(opt.lastActivity != null && opt.lastActivity !='' && opt.lastActivity != 'undefined'){
                          result = result + ' <td><div><span>--</span></div></td>';
                      }else  */if(lastActivityHiddenCount != null && lastActivityHiddenCount !='' && lastActivityHiddenCount != 'undefined'){
                    	  result = result + ' <td><div id="'+opt.userId+'lastActivityCountId"><span>'+opt.lastActivity+'</span></div></td>';
                      }else{
                    	  result = result + ' <td><div><span>---</span></div></td>';
                      }
                      }else{
                          result = result + ' <td><div><span>---</span></div></td>';
                      } 
                      return result;
                  }
              }/* , {
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
                } */],
            "scrollCollapse": false,
            "lengthMenu": [[10, 15, 20], [10, 15, 20]],
//             "aaSorting": [[3,'desc']],
            "bLengthChange": true,
            "bDestroy": true,
            "ordering": true,
            "oLanguage": {
                 "sSearch": "",
                  "sZeroRecords": "", "sEmptyTable": "",
                  "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
         },
	      drawCallback: function(){
	    	 $('.viewActiveCount').each(function() {
	    		 var id = this.id;
	    		 var localId = $("#"+id).attr('data-id');
	    		 if(localId != null &&  localId !='undefined' && localId != ''){
	    			 viewCount(localId);
	    		 }
	    			
	       }); 
	   	   } 
         
    } );
    $("#activePagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>");
    $("input[type=search]").hide();
    $("#dataTableSearch").keyup(function(e) {
           if(e.which === 13) {
               usageReportList(userType);
           }
        });
    
    if(userType == "ACTIVE"){
        $("#listNamediv").html("Active Users List");
    }else if(userType == "INACTIVE"){
        $("#listNamediv").html("Inactive Users List");
    }else if(userType == "NOT_LOGGEDIN"){
        $("#listNamediv").html("Not Logged In Users List");
    }
    getCountList();
    
    
}

</script>
 
 
  <script>
        //Usage User show hide script
        function usageactiveList(){
//             $("#inactiveList").hide();
            $("#activeDiv").show();
//             $("#notloginList").hide();
            $("#multipleIpsDiv").hide();
//             $("#totalList").hide();
			$("#mypop").show();
            $("#activeLi").addClass("usageGrid");
            $("#inactiveLi").removeClass("usageGrid");
            $("#notloginLi").removeClass("usageGrid");
            $("#multipleLi").removeClass("usageGrid");
            $("#totalLi").removeClass("usageGrid");
        }
        function usageinactiveList(){
//             $("#inactiveList").show();
            $("#activeDiv").show();
//             $("#notloginList").hide();
            $("#multipleIpsDiv").hide();
//             $("#totalList").hide();
            $("#activeLi").removeClass("usageGrid");
            $("#mypop").show();
            $("#inactiveLi").addClass("usageGrid");
            $("#notloginLi").removeClass("usageGrid");
            $("#multipleLi").removeClass("usageGrid");
            $("#totalLi").removeClass("usageGrid");
        }
        function usagenotloginList(){
//             $("#inactiveList").hide();
            $("#activeDiv").show();
//                $("#notloginList").show();
               $("#multipleIpsDiv").hide();
//             $("#totalList").hide();
			$("#mypop").show();
            $("#activeLi").removeClass("usageGrid");
            $("#inactiveLi").removeClass("usageGrid");
            $("#notloginLi").addClass("usageGrid");
            $("#multipleLi").removeClass("usageGrid");
            $("#totalLi").removeClass("usageGrid");
        }
        function usagemultipleipslist(userType){
            $("#userTypeId").val(userType);
            $("#inactiveList").hide();
            $("#activeDiv").hide();
            $("#notloginList").hide();
            $("#multipleIpsDiv").show();
            $("#totalList").hide();
            $("#mypop").hide();
            $("#activeLi").removeClass("usageGrid");
            $("#inactiveLi").removeClass("usageGrid");
            $("#notloginLi").removeClass("usageGrid");
            $("#multipleLi").addClass("usageGrid");
            $("#totalLi").removeClass("usageGrid");
            
         var fromDate = $("#custFromDate").val();
         var toDate = $("#custToDate").val();
         var customDates = $("#customDates").val();
         var sourceleadUrl ='${sourceleadUrl}';
            var searchValue = $("#dataaTableSearch").val();
            var orgLocationId = '${SourceLeadWeb_orgLocationId}';
           var orgId = $("#orgs").val();
        if(orgId != null && orgId != '' && orgId != 'undefined'){
            orgId = $("#orgs").val();
        }else{
            orgId = "";
        }
            searchValue = searchValue ? searchValue : "";
           
            var url = sourceleadUrl+"/multipleIpsList?searchValue="+searchValue+"&fromDate="+fromDate+"&toDate="+toDate+"&customDates="+customDates+"&orgId="+orgId;
            prepareMultipleIpsList(url);
        }
        
        function prepareMultipleIpsList(url){
        var result= "";
            result = result +' <div class="clearfix">';
            result = result +' <div class="remove_search mt">';
            result = result +' <input type="text" placeholder="Search" id="dataaTableSearch"/>';
            result = result +'</div>';
            result = result +'</div>';
            result = result +'<table id="multipleIpsPagination" class="display" cellspacing="0" width="100%" >';
            result = result +'<thead>';
            result = result + '<tr>';
            result = result + '<th>IP Address</th>';
            result = result + '<th>No. of Users</th>';
            result = result + '<th>Location</th>';
            result = result + '<th>No.of Activities</th>';
            result = result + '</tr>';
            result = result +'</thead>';
            result = result +'<tbody>';
            result = result +' </tbody>';
            result = result +'</table>';
            $("#multipleIpsDiv").empty();
            $("#multipleIpsDiv").append(result);
            multipleIpsPagination(url);
           $("#listNamediv").html("Ip List").css({"position":"relative","top":"-12px"});
        }
        
        function multipleIpsPagination(url){
            var colIndex = 0;
            $('#multipleIpsPagination').DataTable( {
                    "processing": true,
                "serverSide": true,
                "searching": false,
                "dom": 'Bfrtip',
            	"buttons": [ 'pageLength', 'excel', 'pdf'	], 
                "ajax": $.fn.dataTable.pipeline( {
                    url: url,
                    method: 'POST',
                    pages: 10
                }),
                "columns": [
                    { "data": "ipAddress" },
                    { "data": "noOfUsers"},
                    { "data": "location"},
                    { "data": "noOfActivities"},
                    
                ],
                "columnDefs": [ {
                          "targets": colIndex++,
                          "data": "ipAddress",
                          "render": function(data, type, opt, meta) {
                                var result = "";
                              $("#emptyUserUsageReportsId").val(1);
                               if(opt.ipAddress !=null && opt.ipAddress !='' && opt.ipAddress !='undefined'){
                                    result = result + ' <td><div><span>'+opt.ipAddress+'</span></div></td>';
                                }else{
                                    result = result + ' <td><div>--</div></td>';
                                }
                             return result;
                          }
                  },{
                      "targets": colIndex++,
                      "data": "noOfUsers",
                      "render": function ( data, type, opt, meta ) {
                          var result = "";
//                           if(opt.noOfUsers !=null && opt.noOfUsers !='' && opt.noOfUsers !='undefined'){
                              result = result + ' <td><div> <a id="'+opt.ipAddress+'ipUsersCountId" data-id="'+opt.ipAddress+'" class="ipDetailsCount" class="cursorPointer" onClick="showIp(\''+opt.ipAddress+'\');"><i class="fa fa-spinner fa-pulse"></i></a></div></td>';
//                             }else{
//                                 result = result + ' <td><div>--</div></td>';
//                             }
                          return result;
                              
                      }
                      },{
                    "targets": colIndex++,
                    "data": "location",
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
                        "data": "noOfActivities",
                        "render": function ( data, type, opt, meta ) {
                                var result = "";
//                                 var lastActivityIpHiddenCount = $("#lastActivityIpCountIdHidden").val();
//                                 if(opt.noOfActivities !=null && opt.noOfActivities !='' && opt.noOfActivities !='undefined'){
//                                     result = result + ' <td><div><span>'+opt.noOfActivities+'</span></div></td>';
                                    result = result + ' <td><div class="tblminwidth-50"><a id="'+opt.ipAddress+'ipCountId" data-id="'+opt.ipAddress+'" class="ipDetailsCount"><i class="fa fa-spinner fa-pulse"></i></i></a></div><div class="tblminwidth-50"  style="display:none;"><span title="100"></span></div></td>';
//                                    }else{
//                                     result = result + ' <td><div><span>---</span></div></td>';
//                                    }
                              return result;
                        }
                  }],
                  
                  "scrollCollapse": false,
                  "lengthMenu": [[10, 15, 20], [10, 15, 20]],
//                   "aaSorting": [[3,'desc']],
                  "bLengthChange": true,
                  "bDestroy": true,
                  "ordering": true,
                  "oLanguage": {
                       "sSearch": "",
                        "sZeroRecords": "", "sEmptyTable": "",
                        "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
               },
            
	            drawCallback: function(){
	   	    	 $('.ipDetailsCount').each(function() {
	   	    		 var localId = $(this).attr('data-id').replace(/\,/g, '');
		   	    		 if(localId != null &&  localId !='undefined' && localId != ''){
		   	    			 ipCount(localId);
		   	    		 }
   	    			
   	       }); 
   	   	   }
            
          } );
            
//             $("#multipleIpsDiv").find('a.buttons-excel').prop("title", "Export to Excel");
//         	$("#multipleIpsDiv").find('a.buttons-pdf').prop("title", "Export to PDF");
        	
          $("#multipleIpsPagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>");
          $("input[type=search]").hide();
          $("#dataaTableSearch").keyup(function(e) {
                 if(e.which === 13) {
                   prepareMultipleIpsList(url);
//                      usageReportList(userType);
                 }
              });
          
      }
        
        function usagetotalList(){
            $("#inactiveList").hide();
            $("#activeList").hide();
            $("#notloginList").hide();
            $("#multipleList").hide();
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
             $("#multipleIpsCount").html(data.multipleIps);
            
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
        if(data == "success"){
           usageReportList("ACTIVE");
        }
    });
 
}
</script>

<script>
function showCustomFields(){
    var frequency = $("#customDates").val();
    var userType = $("#userTypeId").val();
    if(frequency == "Custom"){
        $("#custom-selects").show();
         $('#custFromDate').attr('data-validation',"required");
         $('#custToDate').attr('data-validation',"required");
    }else{
        $("#custom-selects").hide();
        $('#custFromDate').removeAttr('data-validation');
        $('#custToDate').attr('data-validation',"required");
        $('#custFromDate').val("");
        $('#custToDate').val("");
        usageReportList(userType);
//         usagemultipleipslist();
    }
    
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

      $( "#custFromDate" ).datepicker({
           dateFormat : finalDateFormatEndDate,
            changeMonth: true,
            changeYear: true,
            maxDate: new Date() ,
            onSelect : function(selected) {
            var dt = $(this).datepicker('getDate', '+1d');
            dt.setDate(dt.getDate());
            $("#custToDate").datepicker("option", "minDate", dt);
             removeStartValid();
            }
      
      });
      
      $( "#custToDate" ).datepicker({
           dateFormat : finalDateFormatEndDate,
            changeMonth: true,
            changeYear: true,
          maxDate: new Date() ,
      onSelect : function(selected) {
            var dt = $(this).datepicker('getDate', '+1d');
            dt.setDate(dt.getDate());
            $("#custFromDate").datepicker("option", "maxDate", dt);
             removeEndValid();
            }});
}

function removeStartValid(){
    $('#custFromDateDiv').find('p.jquery_form_error_message').remove();
}
function removeEndValid(){
    $('#custToDateDiv').find('p.jquery_form_error_message').remove();
}

function validateCustDatesList(){
    var userType = $("#userTypeId").val();
    var frequency = $("#customDates").val();
    var fromDate = $("#custFromDate").val();
    var toDate = $("#custToDate").val();
    if(frequency == "Custom"){
        if(fromDate == ''){
            $('#custFromDateDiv').find('p.jquery_form_error_message').remove();
            $("input#custFromDate").after( "<p class='jquery_form_error_message'>FromDate is required.</span>");
        }
        if(toDate == ''){
            $('#custToDateDiv').find('p.jquery_form_error_message').remove();
            $("input#custToDate").after( "<p class='jquery_form_error_message'>ToDate is required.</span>");
        }
        if((fromDate != '' && fromDate != 'undefined' && fromDate != null) && (toDate != '' && toDate != 'undefined' && toDate != null)){
            $('#custFromDateDiv').find('p.jquery_form_error_message').remove();
            $('#custToDateDiv').find('p.jquery_form_error_message').remove();
            if(userType == "MULTIPLE_IP"){
                usagemultipleipslist();
            }else{
                usageReportList(userType);
                $("#custom-selects").hide();
            }
        }
    }else{
        $('#custFromDateDiv').find('p.jquery_form_error_message').remove();
        $('#custToDateDiv').find('p.jquery_form_error_message').remove();
        usageReportList(userType);
        usagemultipleipslist()
    }
}
</script>

<script>
$('#mypop').click(function(){
        $("#exportPop").toggle();
        $("#filter2ApprovedPop, #filter1ApprovedPop, .action-pop, .approved_pop").slideUp();
 });
 $("html").click(function(){
    $("#exportPop, .ExpPop").hide(200);
 });
 $(".billing_dashboard, .nav-tabs").click(function(){
    $("#exportPop, .ExpPop").hide(200);
 });
 
 $("#exportPop, #mypop, .nav-tabs, .billing_dashboard").click(function(e) {  
     e.stopPropagation();
 });
</script>


<script>
function exportUserUsageReportPdf(){
        var emptyExport = $("#emptyUserUsageReportsId").val();
        var customDates = $('#customDates').val();
        
        var fromDate = $("#custFromDate").val();
        var toDate = $("#custToDate").val();
        var userType = $("#userTypeId").val();
        
        if(emptyExport > 0){
            window.location.href = sourceleadUrl+"/getUserUsageReportExportList?"+tokenParamName+"="+tokenValue+"&customDates="+customDates+"&fromDate="+fromDate+"&toDate="+toDate+"&userType="+userType+"&exportType=pdf";
        }else{
             var result = "";
                result = result +"    <div class='project_inn_mask'></div>";
                result = result +"       <div id='empty_receivable_pop' class='remove_user_pop remove_user_pop_a'>";
                result = result +"        <p>User Usage Report List is Empty</p>";
                result = result +"        <div>&nbsp;&nbsp;&nbsp;</div>";
                result = result +"        <b href='javascript:void(0)'><input type='button' value='Ok' onclick='cancelExport();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelExport();'></b>";
                result = result +"    </div>";
                $("#popUp").append(result);
                $("#empty_receivable_pop").slideDown(200);
                $(".project_inn_mask").fadeIn(200);
        }
    
}
</script>

<script>
function exportUserUsageReportExcel(){
        var emptyExport = $('#emptyUserUsageReportsId').val();
        var customDates = $('#customDates').val();
        
        var fromDate = $("#custFromDate").val();
        var toDate = $("#custToDate").val();
        var userType = $("#userTypeId").val();
        
        if(emptyExport > 0){
            window.location.href = sourceleadUrl+"/getUserUsageReportExportList?"+tokenParamName+"="+tokenValue+"&customDates="+customDates+"&fromDate="+fromDate+"&toDate="+toDate+"&userType="+userType+"&exportType=xls";
        }else{    
                var result = "";
                result = result +"    <div class='project_inn_mask'></div>";
                result = result +"       <div id='empty_requirement_pop' class='remove_user_pop remove_user_pop_a'>";
                result = result +"        <p>Receivables List is Empty</p>";
                result = result +"        <div>&nbsp;&nbsp;&nbsp;</div>";
                result = result +"        <b href='javascript:void(0)'><input type='button' value='Ok' onclick='cancelExport();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelExport();'></b>";
                result = result +"    </div>";
                $("#popUpp").append(result);
                $("#empty_requirement_pop").slideDown(200);
                $(".project_inn_mask").fadeIn(200);
  }
}
</script>
<div class="sideMask"></div>
<!-- Modal -->
  <div class="modal multipleipsList" id="multipleipsList" style="display:none">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" onClick="closeIp();">&times;</button>
          <h4 class="modal-title">IPS User List</h4>
        </div>
        <div class="modal-body">
        <div class="require_wrap_table datatable_filter">
          <div id="multipleIpsUsersDiv"></div>
          </div>
          <!-- <table class="table table-bordered">
          <thead>
              <tr>
              <th>Name</th><th>Email</th><th>Phone</th><th>Company</th>
              </tr>
          </thead>
          <tbody>
          <tr>
              <td>Name</td><td>Email</td><td>Phone</td><td>Company</td>
          </tr>
          </tbody>
          </table> -->
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal" onClick="closeIp();">Close</button>
        </div>
      </div>
    </div>
  </div>

<script type="text/javascript">
function cancelExport(){
    $("#popUp").empty();
    $(".project_inn_mask").fadeOut(200);
}
function showIp(ipAddress){
    $(".sideMask").fadeIn(200);
    $(".multipleipsList").fadeIn(200);
    
//     var fromDate = $("#custFromDate").val();
//      var toDate = $("#custToDate").val();
//      var customDates = $("#customDates").val();
    var sourceleadUrl ='${sourceleadUrl}';
       var searchValue = $("#dataaTableSearch").val();
       var orgLocationId = '${SourceLeadWeb_orgLocationId}';
       searchValue = searchValue ? searchValue : "";
      var orgId = $("#orgs").val();
    if(orgId != null && orgId != '' && orgId != 'undefined'){
        orgId = $("#orgs").val();
    }else{
        orgId = "";
    }
       var url = sourceleadUrl+"/multipleIpsUsersList?searchValue="+searchValue+"&ipAddress="+ipAddress+"&orgId="+orgId;
       prepareMultipleIpsUsersList(url);
}

function prepareMultipleIpsUsersList(url){
    
     var result= "";
        result = result +' <div class="modal-header">';
        result = result +' <div class="remove_search">';
        result = result +' <input type="text" placeholder="Search" id="dataa1TableSearch"/>';
        result = result +'</div>';
        result = result +'</div>';
        result = result +'<table id="multipleIpsUsersPagination" class="display" cellspacing="0" width="100%" >';
        result = result +'<thead>';
        result = result + '<tr>';
        result = result + '<th>Name</th>';
        result = result + '<th>Email</th>';
        result = result + '<th>Phone</th>';
//         result = result + '<th>Company Name</th>';
        result = result + '</tr>';
        result = result +'</thead>';
        result = result +'<tbody>';
        result = result +' </tbody>';
        result = result +'</table>';
        $("#multipleIpsUsersDiv").empty();
        $("#multipleIpsUsersDiv").append(result);
       multipleIpsUsersPagination(url);
    
}

function multipleIpsUsersPagination(url){
    var colIndex = 0;
    $('#multipleIpsUsersPagination').DataTable( {
            "processing": true,
        "serverSide": true,
        "searching": false,
        "ajax": $.fn.dataTable.pipeline( {
            url: url,
            method: 'POST',
            pages: 10
        }),
        "columns": [
            { "data": "userName" },
             { "data": "userEmail"},
            { "data": "mobilePhone"},
//             { "data": "companyName"},
            
        ],
        "columnDefs": [ {
                  "targets": colIndex++,
                  "data": "userName",
                  "render": function(data, type, opt, meta) {
                        var result = "";
                       if(opt.userName !=null && opt.userName !='' && opt.userName !='undefined'){
                            result = result + ' <td><div><span>'+opt.userName+'</span></div></td>';
                        }else{
                            result = result + ' <td><div>--</div></td>';
                        }
                     return result;
                  }
          },
           {
              "targets": colIndex++,
              "data": "userEmail",
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
            "data": "mobilePhone",
            "render": function ( data, type, opt, meta ) {
                var result = "";
                   if(opt.mobilePhone != null && opt.mobilePhone != '' && opt.mobilePhone !='undefined'){
                       result = result + '<td><div><span>'+opt.mobilePhone+' </span></div></td>';
                 }else{
                     result = result + '<td><div><span>---</span></div></td>';
                 }
                   return result;
            }
          }],
          
          "scrollCollapse": false,
            "lengthMenu": [[10, 15, 20], [10, 15, 20]],
//             "aaSorting": [[3,'desc']],
            "bLengthChange": true,
            "bDestroy": true,
            "ordering": true,
            "oLanguage": {
                 "sSearch": "",
                  "sZeroRecords": "", "sEmptyTable": "",
                  "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
         }
    } );
    $("#multipleIpsUsersPagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>");
    $("input[type=search]").hide();
    $("#dataa1TableSearch").keyup(function(e) {
           if(e.which === 13) {
               prepareMultipleIpsUsersList(url);
           }
        });
}

    


function closeIp(){
    $(".sideMask").fadeOut(200);
    $(".multipleipsList").fadeOut(200);
}
</script>  

<script>
function showCustomFieldsGraph(){
    var frequency = $("#customGraphDates").val();
    var userType = $("#userTypeId").val();
    if(frequency == "Custom"){
        $("#custom-select").show();
         $('#custFromDate1').attr('data-validation',"required");
         $('#custToDate1').attr('data-validation',"required");
    }else{
        $("#custom-select").hide();
        $('#custFromDate1').removeAttr('data-validation');
        $('#custToDate1').attr('data-validation',"required");
        usageReportsGraph();
    }
    
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

    $( "#custFromDate1" ).datepicker({
         dateFormat : finalDateFormatEndDate,
          changeMonth: true,
          changeYear: true,
          maxDate: new Date() ,
          onSelect : function(selected) {
          var dt = $(this).datepicker('getDate', '+1d');
          dt.setDate(dt.getDate());
          $("#custToDate1").datepicker("option", "minDate", dt);
           removeStartValid();
          }
    
    });
    
    $( "#custToDate1" ).datepicker({
         dateFormat : finalDateFormatEndDate,
          changeMonth: true,
          changeYear: true,
        maxDate: new Date() ,
    onSelect : function(selected) {
          var dt = $(this).datepicker('getDate', '+1d');
          dt.setDate(dt.getDate());
          $("#custFromDate1").datepicker("option", "maxDate", dt);
           removeEndValid();
          }});
}

function removeStartValid(){
  $('#custFromDate1Div').find('p.jquery_form_error_message').remove();
}
function removeEndValid(){
  $('#custToDate1Div').find('p.jquery_form_error_message').remove();
}

function validateCustDatesGraph(){
    var userType = $("#userTypeId").val();
  var frequency = $("#customGraphDates").val();
  var fromDate = $("#custFromDate1").val();
  var toDate = $("#custToDate1").val();
  if(frequency == "Custom"){
      if(fromDate == ''){
          $('#custFromDate1Div').find('p.jquery_form_error_message').remove();
          $("input#custFromDate1").after( "<p class='jquery_form_error_message'>FromDate is required.</span>");
      }
      if(toDate == ''){
          $('#custToDate1Div').find('p.jquery_form_error_message').remove();
          $("input#custToDate1").after( "<p class='jquery_form_error_message'>ToDate is required.</span>");
      }
      if((fromDate != '' && fromDate != 'undefined' && fromDate != null) && (toDate != '' && toDate != 'undefined' && toDate != null)){
          $('#custFromDate1Div').find('p.jquery_form_error_message').remove();
          $('#custToDate1Div').find('p.jquery_form_error_message').remove();
          if(userType == "MULTIPLE_IP"){
              usagemultipleipslist();
          }else{
              usageReportsGraph();
          }
      }
  }else{
      $('#custFromDate1Div').find('p.jquery_form_error_message').remove();
      $('#custToDate1Div').find('p.jquery_form_error_message').remove();
      usageReportsGraph();
//       usagemultipleipslist()
  }
}
    
    </script>
    
  <script>
    function viewCount(userId){
        var orgLocationId = '${SourceLeadWeb_orgLocationId}';
        var userType = $("#userTypeId").val();
        var orgId = $("#orgs").val();
        var customDates = $("#customDates").val();
        var fromDate = $("#custFromDate").val();
        var toDate = $("#custToDate1").val();
//         var url = sourceleadUrl+"/viewCountsList?orgLocationId="+orgLocationId+"&userType="+userType+"&orgId="+orgId+"&customDates="+customDates+"&fromDate="+fromDate+"&toDate="+toDate;
        $.ajax({
            type : "GET",
             url: sourceleadUrl+"/viewCountsList?userId="+userId+"&userType="+userType+"&orgId="+orgId+"&customDates="+customDates+"&fromDate="+fromDate+"&toDate="+toDate,    
             success :function(data) {
           	  
           	  $("#"+userId+"countId").html(data.noOfActivities);
           	  $("#lastActivityCountIdHidden").val(data.lastActivity);
           	  $("#"+userId+"lastActivityCountId").empty();
           	  $("#"+userId+"lastActivityCountId").html(data.lastActivity)
            },
             
        }); 
        
        
    }
</script>

<script>
function ipCount(ipAddress){
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	var orgId = $("#orgs").val();
    var customDates = $("#customDates").val();
    var fromDate = $("#custFromDate").val();
    var toDate = $("#custToDate1").val();
	$.ajax({
        type : "GET",
	    url : sourceleadUrl+"/ipCountDetails?ipAddress="+ipAddress+"&fromDate="+fromDate+"&toDate="+toDate+"&customDates="+customDates+"&orgId="+orgId,
	    success :function(data) {
// 	    	$("#"+ipAddress+"ipCountId").html(data.noOfActivities);
	    	document.getElementById(""+ipAddress+"ipCountId").innerHTML = data.noOfActivities
	    	document.getElementById(""+ipAddress+"ipUsersCountId").innerHTML = data.totalNoOfUsers
	    },
	
	 });
	
}
</script>

<jsp:include page="userUsageReportsGraph.jsp" />
<jsp:include page="../masterFooter.jsp" />