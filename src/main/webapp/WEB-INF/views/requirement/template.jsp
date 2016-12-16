<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script> 
<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>


<link href="resources/css/jquery-ui.css" rel="stylesheet">
<link rel="stylesheet" href="resources/css/chosen.min.css">

<script src="resources/js/jquery-fun.js"></script> 
<script src="resources/js/chosen.jquery.min.js"></script> 

<script src="resources/js/bootstrap-select.min.js"></script>
<link rel="stylesheet" href="resources/css/bootstrap-select.min.css">

<script>
 window.onload = function() { 
	 requirementTemplateList();
};
</script>

<section class="content-wraper">
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2>Template List</h2>
    </div>
    <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Template List</div>
  </section>
  <div class="timesheet_out">
    <div class="vendor_wrap">
      <div class="timesheet_head">
        <ul>
          <sec:authorize access="hasAnyRole('ROLE_CREATE_REQUIREMENT_TEMPLATE')">
          	<li> <a href="<c:url value='/addTemplate'/>"><i class="fa fa-plus"></i>Add Template</a></li>
         </sec:authorize>
         
          <div class="clearfix"></div>
        </ul>
        <div class="clearfix"></div>
      </div>
      
      <!-- list requirement -->
      <div class="require_wrap_table">
     
          <div id="requirementTemplateList"></div>
      </div>
    </div>
   
   
    <!-- Project --> 
  </div>
</section>


<script>
function requirementTemplateList(){  
	
	  var sourceleadUrl ='${sourceleadUrl}';
	  $.ajax({
           type : "POST",
           url: sourceleadUrl+"/getRequirementTemplateListByOrgLocationsId?${_csrf.parameterName}=${_csrf.token}",    
            success :function(data) {
                 if(data != null && data != 'undefined' && data != "" && data != '[]'){
               		 if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }
               		 else if (data.indexOf('exception') >= 0) {
               			 location.replace(sourceleadUrl+"/errorPage");
               		 }
               		 else{
			    	     $("#requirementTemplateList").removeAttr("style");
	                	 $("#requirementTemplateList").empty();
	                	 
	                	 var result = "";
	                	 result = result +"<table id='requirementTemplatePagination' class='display' cellspacing='0' width='100%' >"; 
	                     result = result +"<thead>"; 
	                     result = result +"<tr>"; 
	                     result = result +"<th>Title</th>"; 
	                     result = result +"<th>Position Type</th>"; 
	                      result = result +"<th>Experience</th>";
	                     result = result +"<th>Skills</th>";
	                     result = result +"<th>Created by</th>";
	                    /*  result = result +"<th>Action</th>"; */
	                     result = result +"  </tr>"; 
	                     result = result +"</thead>";
	                     result = result +"<tbody>"; 
	                     var json = JSON.parse(data);
	                     for (var i = 0; i < json.length; i++) { 
	                    	 var opt = json[i]; 
	                    	 result = result +"<tr>"; 
	                    	 '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_REQUIREMENT_TEMPLATE\',\'ROLE_VIEW_DELETE_REQUIREMENT_TEMPLATE\')">'; 
		                     	result = result +'<td><div class="proj_show tbldata-dotdot"><a href="<c:url value="/requirementTemplateSummary?requirementId='+opt.id+'"/>" ><span class="pull-left" title="'+opt.jobTitle+'">'+opt.jobTitle+' &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i class="fa fa-binoculars"></i></span></a></div></td>';
		                     '</sec:authorize>';
		                     if(opt.jobType!=null && opt.jobType !='' && opt.jobType !='undefined'){
		                    	 result = result +"<td><div>"+opt.jobType+"</div></td>";
		                     }else{
		                    	 result = result +"<td><div>-----</div></td>";
		                     }
		                    
		                     if(opt.minExp!=null && opt.minExp !='' && opt.minExp !='undefined'){
		                    	 result = result +"<td><div>"+opt.minExp+"-"+opt.maxExp+"</div></td>";
		                     }else{
		                    	 result = result +"<td><div>-----</div></td>";
		                     }
		                     
		                     if(opt.skills!=null && opt.skills !='' && opt.skills !='undefined'){
		                    	 result = result +"<td><div title='"+opt.skills+"'>"+opt.skills+"</div></td>";
		                     }else{
		                    	 result = result +"<td><div>-----</div></td>";
		                     }
		                     
		                     if(opt.requirementCreatedDateToString!=null && opt.requirementCreatedDateToString !='' && opt.requirementCreatedDateToString !='undefined'){
		                    	 result = result +"<td><div>"+opt.requirementCreatedDateToString+"</div></td>";
		                     }else{
		                    	 result = result +"<td><div>-----</div></td>";
		                     }
		                    
		                    result = result +"</tr>";
	                     }
	                     result = result +" </tbody>";
	                     result = result +"</table>"; 
	                     $("#requirementTemplateList").append(result); 
			    	} 
                 }else{
                 	 $("#requirementTemplateList").removeAttr("style");
   	    	         var result ="";
  					 result = result+"		<div class='content-area clearfix'>"; 
  					 result = result+"			<div class='blank-pages'>";
  					 result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
  					 result = result+"			        <p>No Template Found</p>";
  					 result = result+"			 </div>";
  					 result = result+"		</div>"; 
  					$("#requirementTemplateList").empty();
  				    $("#requirementTemplateList").append(result); 
                  }
          },
            
       }); 
  } 
</script>

<%-- 
     @author : sarath kumar.
     this script is for prepare pagination to requirementList.
     1.usage     : used in candidateList() function.
--%>

<script>
function pagination(){ 
    $('#requirementTemplatePagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [2, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
            "sSearch": ""
       }
    } );
}
</script>



 <jsp:include page="../masterFooter.jsp" />
