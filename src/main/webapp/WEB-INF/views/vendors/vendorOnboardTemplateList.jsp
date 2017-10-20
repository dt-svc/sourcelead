<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="resources/js/formvalidator.js"></script>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script src="resources/js/formvalidator.js"></script>


<section class="content-wraper">
    <div id="onboardingLoading"></div>
    <section class="content-header clearfix">
          <div class="pull-left">
      <!-- <h2>Submission Templates</h2> -->
      <h2 class="pull-left">Vendor Onboard Templates</h2>
				<div class=" pull-left reportsH">
			
			<div class="RePop openTemplates">
				
			</div>
		</div>
    </div>
          <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='/orgTemplates'/>"> Templates</a> &raquo; Vendor Onboard Templates List</div>
 <div class="clearfix"></div>
    </section>
    
    <div class="timesheet_out">
			<div class="project_wrap">
			<div class="timesheet_head">
			<ul>
			 <sec:authorize access="hasRole('ROLE_CREATE_ON_BOARD_TEMPLATE')"> 
			 	<li><a href="<c:url value='/addNewVendorOnboardTemplate'/>"><i class="fa fa-plus"></i>Add Template</a></li>
		 	 </sec:authorize>
			</ul>
			     
            </div>
			
			    <div class="clients_wrap_table datatable_scroll">
				    <div id="onboardingTemplatesList" class="datatable_filter"></div>
				</div>	   
					
			</div>
	   </div>	
        
</section>
    
<script> 
$(document).ready(function(){
	onboardingTemplatesList();
});
</script>


<script>
function onboardingTemplatesList(){  
	  $("#onboardingLoading").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	  $.ajax({
           type : "POST",
           url: sourceleadUrl+"/getVendorOnboardingTemplatesList?${_csrf.parameterName}=${_csrf.token}",    
            success :function(json) {
               		
			    	     $("#onboardingLoading").removeAttr("style");
	                	 $("#onboardingTemplatesList").empty();
	                	 var result = "";
	                	 result = result +"<table id='onboardingPagination' class='display' cellspacing='0' width='99.9%' >"; 
	                     result = result +"<thead>"; 
	                     result = result +"  <tr>"; 
	                     result = result +"    <th>Template Name</th>";
	                     result = result +"    <th>Description</th>";
	                     result = result +"    <th>Documents</th>";
	                     result = result +"    <th>Tasks</th>";
	                     result = result +"    <th>Created Date</th>";
	                     result = result +"    <th>Created By</th>"; 
	                     result = result +"    <th>Status</th>";
	                     result = result +"    <th>Action</th>";
	                    
	                     '<sec:authorize access="hasRole(\'ROLE_VIEW_EDIT_ON_BOARD_TEMPLATE\')">'
	                    		 result = result +"<th>Actions</th>";
	                     '</sec:authorize>';
	                     
	                     result = result +"  </tr>"; 
	                     result = result +"</thead>";
	                     result = result +"<tbody>"; 
	                     
	                     
	                    	 for (var i = 0; i < json.length; i++) { 
	                    	 var opt = json[i]; 
	                    	 result = result +"<tr id='template"+opt.id+"'>";
	                    	 
	                    		 result = result +"<td><div class='tbldata-dotdot-sm'><span title='"+opt.templateName+"'>"+opt.templateName+"</span></div></td>";
	                    		
	                    		 if(opt.description != null && opt.description != '' && opt.description != 'undefineda'){
	                    			 result = result +"<td><div><span title='"+opt.description+"'>"+opt.description+"</span></div></td>";
	                    		 }else{
	                    			 result = result +"<td><div><span>--------</span></div></td>";
	                    		 }
	                    		 
	                    		 result = result +"<td><div><span>"+opt.documentsCount+"</span></div></td>";
	                    		 
	                    		 result = result +"<td><div><span>"+opt.tasksCount+"</span></div></td>";
	                    		
	                    		 result = result +"<td><div><span>"+opt.templateCreatedDate+"</span></div></td>";
	                    		 
	                    		 result = result +"<td><div><span>"+opt.createdUser+"</span></div></td>";
	                    		 
	                    		 result = result +"<td><div><span>"+opt.templateStatus+"</span></div></td>";
	                    		 
		                    	 result = result +"<td>";
		                    	 result = result +"<div class=''>";
		                    	 result = result +"<a href='"+sourceleadUrl+"/viewVendorOnboardTemplateByTemplateUUID?templateUUID="+opt.templateUUID+"'>Edit</a> ";
		                    	
		                    	 if(opt.isDefaultTemplate != 'Y'){
		                    		 result = result +'| <a href="javascript:void(0)" onclick="deleteOnboadTemplate(\''+opt.templateUUID+'\','+opt.id+');">Delete</a>';
		                    	 }
		                    	 result = result +"</div></td>"; 
		                     
		                     result = result +"</tr>";
		                     result = result +"</tr>";
	                     }
	                     result = result +" </tbody>";
	                     result = result +"</table>"; 
	                     $("#onboardingLoading").removeAttr("style");
	                     $("#onboardingTemplatesList").append(result); 
	                     
	                     onboardingPagenation(); 
                    
          },
            
       }); 
  } 
</script>   


<script>
function deleteOnboadTemplate(templateUUID,id){
	  $("#onboardingLoading").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	  
	  $.ajax({
          type : "POST",
          url: sourceleadUrl+"/deleteVendorOnboardTemplateByTemplateUUID?${_csrf.parameterName}=${_csrf.token}&templateUUID="+templateUUID,    
           success :function(data) {
        	   
    			document.getElementById("template"+id).remove();
    			 $("#onboardingLoading").removeAttr("style");
           },
           
      }); 
  }
</script>

<script>
function onboardingPagenation(){ 
//	 $.fn.dataTable.moment( 'DD-MM-YYYY' );
	 $('#onboardingPagination').DataTable( { 
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


<jsp:include page="../masterFooter.jsp" />