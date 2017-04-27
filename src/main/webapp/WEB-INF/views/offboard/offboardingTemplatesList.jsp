<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="resources/js/formvalidator.js"></script>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script src="resources/js/formvalidator.js"></script>


<section class="content-wraper">
    <div id="offboardingLoading"></div>
    <section class="content-header clearfix">
          <div class="pull-left">
            <h2>Off-board Templates List</h2>
          </div>
    </section>
    
    <div class="timesheet_out">
			<div class="project_wrap">
			<div class="timesheet_head">
			<ul>
			<li><a href="<c:url value='/addNewOffboardTemplate'/>"><i class="fa fa-plus"></i>Add Template</a></li>
			</ul>
			     
            </div>
			
			    <div class="clients_wrap_table datatable_scroll">
				    <div id="offboardingTemplatesList"></div>
				</div>	   
					
			</div>
	   </div>	
        
</section>
    
<script> 
$(document).ready(function(){
	offboardingTemplatesList();
});
</script>


<script>
function offboardingTemplatesList(){  
	  var sourceleadUrl ='${sourceleadUrl}';
	  $("#onboardingLoading").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	  $.ajax({
           type : "POST",
           url: sourceleadUrl+"/getOffboardingTemplatesList?${_csrf.parameterName}=${_csrf.token}",    
            success :function(data) {
               		
			    	     $("#offboardingLoading").removeAttr("style");
	                	 $("#offboardingTemplatesList").empty();
	                	 var result = "";
	                	 result = result +"<table id='offboardingPagination' class='display' cellspacing='0' width='99.9%' >"; 
	                     result = result +"<thead>"; 
	                     result = result +"  <tr>"; 
	                     result = result +"    <th>Template Name</th>";
	                     result = result +"    <th>Description</th>";
	                     result = result +"    <th>Created Date</th>";
	                     result = result +"    <th>Created By</th>";
	                     result = result +"    <th>Status</th>";
	                     result = result +"<th>Actions</th>";
	                     result = result +"  </tr>"; 
	                     result = result +"</thead>";
	                     result = result +"<tbody>"; 
	                     
	                     var json = JSON.parse(data);
	                     
	                    	 for (var i = 0; i < json.length; i++) { 
	                    	 var opt = json[i]; 
	                    	 result = result +"<tr>";
	                    	 
	                    		 result = result +"<td><div class='tbldata-dotdot-sm'><span title='"+opt.templateName+"'>"+opt.templateName+"</span></div></td>";
	                    		
	                    		 result = result +"<td><div><span title='"+opt.description+"'>"+opt.description+"</span></div></td>";
	                    		 
	                    		 result = result +"<td><div><span>"+opt.templateCreatedDate+"</span></div></td>";
	                    		 
	                    		 result = result +"<td><div><span>"+opt.createdUser+"</span></div></td>";
	                    		 
	                    		 result = result +"<td><div><span>"+opt.templateStatus+"</span></div></td>";
	                    		 
			                     result = result +"<td><div class=''><a href='"+sourceleadUrl+"/viewOffboardTemplateByTemplateUUID?templateUUID="+opt.templateUUID+"'>View</a></div></td>"; 

		                     
		                     result = result +"</tr>";
		                     result = result +"</tr>";
	                     }
	                     result = result +" </tbody>";
	                     result = result +"</table>"; 
	                     $("#onboardingLoading").removeAttr("style");
	                     $("#offboardingTemplatesList").append(result); 
	                     
	                     offboardingPagenation(); 
                    
          },
            
       }); 
  } 
</script>   

<script>
function offboardingPagenation(){ 
	 $('#offboardingPagination').DataTable( { 
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20, -1], [2, 15, 20, "All"]],
	    	"bLengthChange": false,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	            "sSearch": "",
	            "sZeroRecords": "", "sEmptyTable": ""
	       }
	    } );
}
</script>


<jsp:include page="../masterFooter.jsp" />