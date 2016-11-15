<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="resources/js/jquery-fun.js"></script> 
<script src="resources/js/formvalidator.js"></script>

<script type="text/javascript">
window.onload = function () {
	getRolesList();
 };
</script>



<section class="content-wraper">
        <section class="content-header clearfix">
          <div class="pull-left">
            <h2>Roles List</h2>
          </div>
        </section>

        
	   <div class="timesheet_out">
			<div class="project_wrap">
			    <div class="project_wrap_table roles_list">
			    				<table id="rolesList" class="display" cellspacing="0" width="100%">
								</table>
				</div>	   
					
			</div>
	   </div>		
 </section>
 
 
 
 <jsp:include page="masterFooter.jsp" />	
 
 

<script>
function getRolesList(){
	
	var sourceleadUrl ='${sourceleadUrl}';
	
	 $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/getOrgEnrolledList?${_csrf.parameterName}=${_csrf.token}", 	   
   		    success: function(data) {
   		    	var json = JSON.parse(data);
   	        	var result = "";
   		    	result = result +'<thead>';
   		    	result = result +'	<tr>';
   		    	result = result +'	<th>Role</th>';
   		    	result = result +'    <th>Level</th>'; 
   		    	result = result +'     <th>Number Of Actions</th>';
   		    	result = result +'     <th>Action</th>';
   		    	result = result +'    </tr>';
   		    	result = result +'</thead>	';
   		    	result = result +'<tbody>';
   		    	for (var i=0; i<json.length; i++)
   				{   
   		    		var report = json[i];
   		    		
   		    		result = result +'<tr>';
   		    		
   		    		result = result +'<td><div class="tbldata-dotdot-me" title="'+report.displayName+'">'+report.displayName+'</div></td>';
   		    		result = result +'<td><div class="tbldata-dotdot-sm" title="'+report.hierarchyName+'">'+report.hierarchyName+'</div></td>';
   		    		result = result +'<td><div class="tbldata-dotdot-vsm" title="'+report.actionsCount+'">'+report.actionsCount+'</div></td>';
   		    		result = result +'<td>';
   		    		result = result +'<div>';
   		    		result = result +'<label class="label label-success">';
   		    		result = result +'<a href="<c:url value="/roleActions/'+report.id+'"/>""><i class="fa fa-key"></i>&nbsp;&nbsp;Confiqure</a>';
   		    		result = result +'</label>';
   		    		result = result +'</div>';
   		    		result = result +'</td>';
   		    		/* result = result +'</div></td>'; */
   		    		
                    result = result +'</tr>';
   				}
                result = result +'  </tbody>';
                result = result +' </table>';
                result = result +'</div>';
                $("#rolesList").empty();  
   	    	    $("#rolesList").append(result);
   	    	    pagination();	
   		    }
	 }); 
	
	
}	
</script> 



<script>
function pagination(){  
    $('#rolesList').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
     		"sSearch": ""
     	}
		
    } );
}
</script>