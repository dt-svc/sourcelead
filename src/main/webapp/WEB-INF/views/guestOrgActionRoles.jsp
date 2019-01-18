<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script type="text/javascript">
window.onload = function () {
	getGuestRolesList();
 };
</script>

<style type="text/css">
.dataTables_wrapper .dataTables_filter input {
background-color: #f5f5f5;
}
.role_header {
padding-right: 254px; padding-top: 10px;
}
</style>

<section class="content-wraper">
<div id="listLoading"></div>
        <section class="content-header clearfix">
          <div class="pull-left">
            <h2>Roles List</h2>
          </div>
           <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>  &raquo; <a href="<c:url value='${appPath}/goToSettings'/>">Settings</a>&raquo;Guest Action Roles</div>
        </section>

        <div class="timesheet_out">
			<div class="project_wrap">
			
			    <div class="project_wrap_table datatable_filter">
			    				<table id="rolesList" class="display" cellspacing="0" width="100%">
								</table>
				</div>	   
					
			</div>
	   </div>	
	   
 </section>
 
 
 
 <jsp:include page="masterFooter.jsp" />	


<script>
function getGuestRolesList(){
	
	var sourceleadUrl ='${sourceleadUrl}';
	$("#listLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	
	 $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/getGuestOrgEnrolledList?${_csrf.parameterName}=${_csrf.token}", 	   
   		    success: function(json) {
   	        	var result = "";
   	         	result = result +"<table id='rolePagination' class='display' cellspacing='0' width='100%' >"; 
   		    	result = result +'<thead>';
   		    	result = result +'	<tr>';
   		    	result = result +'	<th>Role Name</th>';
   		    	result = result +'     <th>Number Of Actions</th>';
   		    	result = result +'     <th>Action</th>';
   		    	result = result +'    </tr>';
   		    	result = result +'</thead>	';
   		    	result = result +'<tbody>';
   		    	for (var i=0; i<json.length; i++)
   				{   
   		    		var report = json[i];
   		    		
   		    		result = result +'<tr>';
   		    		
   		    		result = result +'<td><div class="tbldata-dotdot-vsm" title="'+report.displayName+'">'+report.displayName+'</div></td>';
   		    		result = result +'<td ><div class="tbldata-dotdot-vsm" title="'+report.actionsCount+'">'+report.actionsCount+'</div></td>';
   		    		result = result +'<td>';
   		    		result = result +'<div class="tbldata-dotdot-vsm">';
   		    		result = result +'<label class="label label-success">';
   		    		result = result +'<a class="anchorWhite" href="<c:url value="/app/guestRoleActions/'+report.uuid+'"/>""><i class="fa fa-key"></i>&nbsp;&nbsp;Configure</a>';
   		    		result = result +'</label>';
   		    		result = result +'</div>';
   		    		result = result +'</td>';
   		    		
                    result = result +'</tr>';
   				}
                result = result +'  </tbody>';
                result = result +'</div>';
                $("#rolesList").empty();  
                $("#listLoading").removeAttr("style");
   	    	    $("#rolesList").append(result);
   		    }
	 }); 
	
	
}	
</script> 