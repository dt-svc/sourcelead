<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script type="text/javascript">
		window.onload = function () {
			getRequestOrgsList();
		 };
	</script>

<section class="content-wraper">
        <section class="content-header clearfix">
          <div class="pull-left">
            <h2>Requested Companies</h2>
          </div>
          <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Requested Companies </div>
           <div class="clearfix"></div> 
           <div class="reportsCount req_Crowd req_crowd clearfix">
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
				
                <li id="filterDropDown"><i class="fa fa-filter"></i><span>Status</span><i class="glyphicon glyphicon-play" ></i>|
                	<div id="filter1ApprovePop" class="approvedPop approved_pop">
                    	<ul>
                    	    <li><input id="c6"  type="radio" name="requestedStatus" value ="All"  onclick="getRequestOrgsList();"><label for="c7">&nbsp;All</label></li>
                        	<li><input id="c7"  type="radio" name="requestedStatus" value ="Today"  onclick="getRequestOrgsList();"><label for="c7">&nbsp;Today</label></li>
							<li><input id="c9" type="radio" name="requestedStatus" value ="1 Week Ago"  onclick="getRequestOrgsList();"><label for="c9">&nbsp;1 Week Ago</label></li>
							<li><input id="c8" type="radio"  name="requestedStatus"   value ="2 Weeks Ago"  onclick="getRequestOrgsList();"><label for="c8">&nbsp;2 Weeks Ago</label></li>
               				<li><input id="c6" type="radio"  name="requestedStatus"  value ="1 month ago"  onclick="getRequestOrgsList();"><label for="c6">&nbsp;1 month ago</label></li>
               				<li><input id="c10" type="radio"  name="requestedStatus"   value ="3 Months Ago"  onclick="getRequestOrgsList();"><label for="c10">&nbsp;3 Months Ago</label></li>
               				
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
    $("#filterDropDown").click(function(){
		$(".approvedPop").slideToggle(200);
	});
    $("html").click(function() {
        $(".approvedPop").slideUp(200);
    });
    $("#filterDropDown, .approvedPop").click(function(e) {  
        e.stopPropagation();
    });
    
}); 

</script>


<script>
function getRequestOrgsList(){
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
		result = result +' <th>Location</th>';
	   	result = result +' <th>Email</th>';
	   	result = result +' <th>Phone No</th>';
	   	result = result +' <th>Modules</th>';
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
	 
	 var requestedStatus = [];
		$('input:radio[name=requestedStatus]:checked').each(function() 
	    {
			requestedStatus.push($(this).val());
	    });
		
	 searchValue = searchValue ? searchValue : "";
	 var url = sourceleadUrl+"/getRequestOrgsList?searchValue="+searchValue+"&requestedStatus="+requestedStatus;
	 requestOrgsListPagination(url,sourceleadUrl);
}

function requestOrgsListPagination(url,sourceleadUrl){
	
	var colIndex = 0;
    $('#reqOrgListTable').DataTable( { 
	  "processing": true,
      "serverSide": true,
      "searching": false,
      "ajax": $.fn.dataTable.pipeline( {
          url: url,
          method: 'GET',
          pages: 1
        }),
        "columns": [
            { "data": "companyName" },
            { "data": "requestedBy"},
            { "data": "country"},
            { "data": "email"},
            { "data": "phoneno" },
            { "data": "modules" },
        ],
         "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "companyName",
	    	  	"render": function(data, type, opt, meta) {
	    	  		  var result = "";
	    	  		  result = result + '<td data-title="Company Name"><div class="tbldata-dotdot" title="'+opt.companyName+'">'+opt.companyName+'</div></td>';
		             return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "requestedBy",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  result = result + '<td data-title="Requested By"><div class="tbldata-dotdot-sm" title="'+opt.requestedBy+'">'+opt.requestedBy+'</div></td>';
	              return result;
              }
          },{
	    	  "targets": colIndex++,
              "data": "country",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  result = result + '<td data-title="Country"><div class="tbldata-dotdot" title="'+opt.companyAddress+'">'+opt.companyAddress+'</div></td>';
	              return result;
              }
          },{
		    	  "targets": colIndex++,
	              "data": "email",
	              "render": function ( data, type, opt, meta ) {
	            	  var result = "";
	            	  
	            	    result = result + '<td data-title="Email"><div class="tbldata-dotdot-sm" title="'+opt.email+'">'+opt.email+'</div></td>';
	            		return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "phoneno",
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            		result = result + '<td><div class="tblminwidth-75">'+opt.phoneNumber+'</div></td>';
	            		return result;
	              }
		     }, {
		    	  "targets": colIndex++,
	              "data": "modules",
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    if(opt.requestedModules != null && opt.requestedModules != "" && opt.requestedModules != "undefined"){
		            	    result = result + '<td><div class="tblminwidth-75">'+opt.requestedModules+'</div></td>';
	            	    }else{
		            	    result = result + '<td><div class="tblminwidth-75">--</div></td>';
	            	    }
	            		return result;
	              }
		     } ], 
		     
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"aaSorting": [[1,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#reqOrgListTable").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#reqOrgDataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   requestedOrgsDataTableList();
    	   }
    	});
	
}
</script>
