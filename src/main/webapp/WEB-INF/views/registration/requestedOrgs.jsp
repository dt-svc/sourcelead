<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style>
.reportsCount.req_Crowd ul li {
    width: 25% !important;
}
</style>

<script type="text/javascript">
		window.onload = function () {
			getRequestedOrgsList();
		 };
	</script>

<section class="content-wraper">
        <section class="content-header clearfix">
          <div class="pull-left">
            <h2>Requested Companies</h2>
          </div>
          <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Requested Companies </div>
           <div class="clearfix"></div> 
		      <div class="reportsCount req_Crowd clearfix">
		          <ul class="clearfix">
		          <li id="crowd"><label class="mr10"><span><i class="fa fa-registered"></i></span><b id="crowdActive"><input class="myCheckbox valid" type="radio"  name="sourceleadOrgType" id="sourceleadOrg" onClick="javascript:window.location.href = '<c:url value='${sourceleadUrl}/enrolledOrgsList'/>'" >&nbsp;Registered Companies</label></b></li>
		          <li id="crowd"><label class="mr10"><span><i class="fa fa-cubes"></i></span><b id="savedActive"><input class="myCheckbox valid" type="radio" checked name="sourceleadOrgType" id="sourceleadOrg" onClick="javascript:window.location.href = '<c:url value='${sourceleadUrl}/requestedOrgs'/>'" >&nbsp;Requested Companies</label></b></li>
		          <li id="crowd"><label class="mr10"><span><i class="fa fa-laptop"></i></span><b id="savedActive"><input class="myCheckbox valid" type="radio" name="sourceleadOrgType" id="sourceleadOrg" onClick="javascript:window.location.href = '<c:url value='${sourceleadUrl}/demoRequests'/>'">&nbsp;Demo Requests</label></b></li>
		          </ul>
		      </div> 
        </section>
     
     <div class="clearfix"></div>     
         <div class="timesheet_out timesheet_background">
			
			<div class="timesheet_head topfilters">
        	<ul>
				
                <li id="filter1ApprovePop"><i class="fa fa-filter"></i><span>Status</span><i class="glyphicon glyphicon-play" ></i>|
                	<div id="filter1ApprovePop" class="approved_pop">
                    	<ul>
                        	<li><input id="c7"  type="radio" name="requestedStatus" value ="Today"  onclick="filter();"><label for="c7">Today</label></li>
							<li><input id="c9" type="radio" name="requestedStatus" value ="1 Week Ago"  onclick="filter();"><label for="c9">1 Week Ago</label></li>
							<li><input id="c8" type="radio"  name="requestedStatus"   value ="2 Weeks Ago"  onclick="filter();"><label for="c8">2 Weeks Ago</label></li>
               				<li><input id="c6" type="radio"  name="requestedStatus"  value ="1 month ago"  onclick="filter();"><label for="c6">1 month ago</label></li>
               				<li><input id="c10" type="radio"  name="requestedStatus"   value ="3 Months Ago"  onclick="filter();"><label for="c10">3 Months Ago</label></li>
               				
                        </ul>
                    </div>                	
                </li>
	             
                <div class="clearfix"></div>
            </ul>
            <div class="clearfix"></div>
        </div>
			
			<div class="project_tabs_inn_table vendor_wrap_inn_table dataFilterNew pdtop">
		    	<div id="requestedOrgsFilterList" class="datatable_filter"></div>
			</div>	
        </div>
 </section>

<jsp:include page="../masterFooter.jsp" />

<script>
$(document).ready(function() {
    $("#filter1ApprovePop").click(function(){
		$("#filter1ApprovedPop").slideToggle(200);
	});
    $("html").click(function() {
        $("#filter1ApprovedPop").slideUp(200);
    });
    $("#filter1ApprovedPop, #filter1ApprovePop").click(function(e) {  
        e.stopPropagation();
    });	
    
}); 

</script>


<script>
function getRequestedOrgsList(){
	var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	 if(locationList.match("yyyy-MM-dd")){
		  projectListDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  projectListDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  projectListDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  projectListDate = 'YYYY-DD-MM'; 
	  }
	  
	  var result = "";
	 	result = result +" <div class='remove_search'>";
	 	result = result +" <input type='text' placeholder='Search' id='reqOrgDataTableSearch'/>";
	 	result = result +"</div>";
	 	
	 	result = result + '<table id="reqOrgListTable" class="display" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
	   	result = result +' <th>Company Name</th>'; 
	   	result = result +' <th>Requested By</th>';
	   	result = result +' <th>Email</th>';
	   	result = result +' <th>Phone No</th>';
	   	result = result +' <th>Modules</th>';
	   	result = result +' <th>Demo Date/Time</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
	    result = result +"</table>";
	    $("#requestedOrgsFilterList").empty();
	    $("#requestedOrgsFilterList").append(result);
	    
	    requestedOrgsDataTableList();
}


function requestedOrgsDataTableList(){
	
	 var sourceleadUrl ='${sourceleadUrl}';
	 var searchValue = $("#reqOrgDataTableSearch").val();
	 searchValue = searchValue ? searchValue : "";
	 var url = sourceleadUrl+"/getRequestedOrgsList?searchValue="+searchValue;
	 projectsPagination(url,sourceleadUrl);
}


</script>
