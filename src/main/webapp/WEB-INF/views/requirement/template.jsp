<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<script>
 window.onload = function() { 
	 requirementTemplateList();
};
</script>
<section class="content-wraper">
  <section class="content-header clearfix">
   <c:if test="${!empty msg}">
	        <div class="success_pop" id="selector_template"  style="display: block;">
		         <i class="fa fa-check-circle"></i><span>${msg}</span>
			</div>
	     </c:if>
    <div class="pull-left"><h2>Job Templates</h2></div>
	<div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;  <a href="<c:url value='${appPath}/settings'/>">Settings</a>&raquo; <a href="<c:url value='${appPath}/orgTemplates'/>"> Templates</a> &raquo; Job Templates</div>
  </section>
  
  <div class="overflow_Auto vendor_wrap">
    <div class="clearfix">
    
	 <div class="reportHead reportHeadDataTab clearfix">
	    <ul class="pull-right list-unstyled list-inline">
          <sec:authorize access="hasAnyRole('ROLE_CREATE_REQUIREMENT_TEMPLATE')">
          	<li> <a href="<c:url value='${appPath}/addTemplate'/>" class="newAdBtn">+ Job Template</a></li>
         </sec:authorize>	    
		</ul>
	</div>	    
      
      <div class="project_tabs_inn_table">
          <div id="requirementTemplateList" class="datatable_filter dataSeaTop"></div>
      </div>
    </div>
   
  </div>
</section>

<script>
  $("#selector_template").fadeIn(200);
  $('#selector_template').delay(2000).fadeOut('slow');
</script>   
<script>
function requirementTemplateList(){  
	
	  var sourceleadUrl ='${sourceleadUrl}';
	  $.ajax({
           type : "POST",
           url: sourceleadUrl+"/getRequirementTemplateListByOrgLocationsId?"+tokenParamName+"="+tokenValue+"",    
            success :function(data) {
                 /* if(data != null && data != 'undefined' && data != "" && data != '[]'){ */
               		
			    	     $("#requirementTemplateList").removeAttr("style");
	                	 $("#requirementTemplateList").empty();
	                	 
	                	 var result = "";
	                	 result = result +"<table id='temppagination' class='display' cellspacing='0' width='100%' >"; 
	                     result = result +"<thead>"; 
	                     result = result +"<tr>"; 
	                     result = result +"<th>Title</th>"; 
	                     result = result +"<th>Employment Type</th>"; 
	                      result = result +"<th>Experience</th>";
	                     result = result +"<th>Skills</th>";
	                     result = result +"<th>Created by</th>";
	                      result = result +"<th>Action</th>"; 
	                     result = result +"  </tr>"; 
	                     result = result +"</thead>";
	                     result = result +"<tbody>"; 
	                     var json = JSON.parse(data);
	                     for (var i = 0; i < json.length; i++) { 
	                    	 var opt = json[i]; 
	                    	 result = result +"<tr>"; 
	                    	 result = result +"<td>";
	                    	 '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_REQUIREMENT_TEMPLATE\',\'ROLE_VIEW_DELETE_REQUIREMENT_TEMPLATE\')">'; 
		                     	result = result +'<div class="proj_show tbldata-dotdot"><a href="<c:url value="/app/requirementTemplateSummary?requirementId='+opt.id+'"/>"><span class="pull-left" title="'+opt.jobTitle+'">'+opt.jobTitle+'</span><i class="fa fa-binoculars"></i></div></a>';
		                     '</sec:authorize>';
		                     '<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_EDIT_REQUIREMENT_TEMPLATE\',\'ROLE_VIEW_DELETE_REQUIREMENT_TEMPLATE\')">'; 
		                     	result = result +'<div class="tbldata-dotdot"><span class="pull-left" title="'+opt.jobTitle+'">'+opt.jobTitle+'</span></div>';
		                     '</sec:authorize>';
		                     result = result +"</td>";
		                     if(null != opt.jobType && opt.jobType !='' && opt.jobType !='undefined'){
		                    	 if(opt.jobType=='Other'){
		                    		   if(null != opt.otherJobType && opt.otherJobType !='' && opt.otherJobType !='undefined'){
		                    		 result = result +"<td><div><span title="+opt.otherJobType+">"+opt.otherJobType+"</span></div></td>";
		                    		   }
		                    		   else
		                    			   {
		                    			   result = result +"<td><div>-----</div></td>";
		                    			   }
		                    	 }else{
		                    		 result = result +"<td><div>"+opt.jobType+"</div></td>";
		                    	 }
		                     }else{
		                    	 result = result +"<td><div>-----</div></td>";
		                     }
		                     
		                     
		                    if(opt.maxExp != null && opt.maxExp !='' && opt.maxExp !='undefined'){
		                    	 maxExp = opt.maxExp;
		                     }else{
		                    	 maxExp =''; 
		                     }
		                    
		                    if(opt.maxExp != null && opt.maxExp !='' && opt.maxExp !='undefined' && opt.minExp!=null && opt.minExp !='' && opt.minExp !='undefined'){
		                    	maxExp = "-"+maxExp;
		                    }
		                     
		                     if(opt.minExp!=null && opt.minExp !='' && opt.minExp !='undefined'){
		                    	 minExp = opt.minExp
		                     }else{
		                    	 minExp='';
		                     } 
		                     
		                     
		                     result = result +"<td><div>"+minExp+""+maxExp+"</div></td>";
		                     
		                     if(opt.skills!=null && opt.skills !='' && opt.skills !='undefined'){
		                    	 result = result +"<td><div><span title='"+opt.skills+"'>"+opt.skills+"</span></div></td>";
		                     }else{
		                    	 result = result +"<td><div>-----</div></td>";
		                     }
		                     
		                     if(opt.createdByName!=null && opt.createdByName !='' && opt.createdByName !='undefined'){
		                    	 result = result +"<td><div><span title="+opt.createdByName+">"+opt.createdByName+"</span></div></td>";
		                     }else{
		                    	 result = result +"<td><div>-----</div></td>";
		                     }
		                     result = result +"<td><div>";
		                     '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_DELETE_REQUIREMENT_TEMPLATE\')">'; 
		                     	result = result +"<a id='edit-user' href='#' title='Delete' onClick='deleteRequirementTemplate("+opt.id+");'><i class='glyphicon glyphicon-trash'></i></a>";
		                     '</sec:authorize>';
		                     result = result +"</div></td>";
		                    result = result +"</tr>";
	                     }
	                     result = result +" </tbody>";
	                     result = result +"</table>"; 
	                     $("#requirementTemplateList").append(result); 
	                     temppagination();
			    	
                 /* } else{
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
                  } */
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
function temppagination(){ 
    $('#temppagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    } );
}
</script>

<script>
function deleteRequirementTemplate(requirementId){
	var sourceleadUrl ='${sourceleadUrl}';
	var message = confirm("You are choosing to delete the Requirement Template");
	if (message == true) {
		$.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/terminateRequirementTemplate?"+tokenParamName+"="+tokenValue+"&requirementId="+requirementId, 	   
   		    success: function(data) {
   		    	if( data.indexOf('Sign In') >= 0){
		    		location.replace(sourceleadUrl);
		    	 }
        		 else if (data.indexOf('exception') >= 0) {
        			 location.replace(sourceleadUrl+"/errorPage");
        		 }
        		 else{
        			 location.replace(sourceleadUrl+"/template");
        			 
        		 }
   		    }
	     });
	}
}
</script>

 <jsp:include page="../masterFooter.jsp" />
