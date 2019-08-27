<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/formvalidator.js"></script>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/formvalidator.js"></script>


<section class="content-wraper">
<div id="onboardingLoading"></div>
    
   <section class="content-header clearfix">
    <c:if test="${!empty msg}">
        <div class="success_pop" id="selector"  style="display: block;">
	         <i class="fa fa-check-circle"></i><span>${msg}</span>
		</div>
     </c:if>
         <div class="pull-left"><h2 class="pull-left">Onboard Templates</h2></div>
         <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; <a href="${SourceLeadWeb_sourceleadUrl}/orgTemplates'/>"> Templates</a> &raquo; Onboard Templates List</div>
		<div class="clearfix"></div>
   </section>
    
<div class="project_wrap">
	 <div class="reportHead reportHeadDataTab clearfix">
	    <ul class="pull-right list-unstyled list-inline">
		 <sec:authorize access="hasRole('ROLE_CREATE_ON_BOARD_TEMPLATE')"> 
		 	<li><a href="<c:url value='${SourceLeadWeb_atsUrl}/addNewOnboardTemplate'/>" class="newAdBtn">+ Template</a></li>
	 	 </sec:authorize>			
		</ul>
	</div>	

    <div class="clients_wrap_table datatable_scroll">
	    <div id="onboardingTemplatesList" class="datatable_filter dataSeaTop"></div>
	</div>	
	   
</div>
	
</section>
    
<script> 
$(document).ready(function(){
	onboardingTemplatesList();
});
</script>
<script>
  $("#selector").fadeIn(200);
  $('#selector').delay(2000).fadeOut('slow');
	      
</script>

<script>
function onboardingTemplatesList(){  
	  var sourceleadUrl ='${sourceleadUrl}';
	  var atsUrl ='${SourceLeadWeb_atsUrl}';
	  
	  $("#onboardingLoading").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	  $.ajax({
           type : "POST",
           url: sourceleadUrl+"/getOnboardingTemplatesList?${_csrf.parameterName}=${_csrf.token}",    
            success :function(response) {
            	if (response.response === "AWKWARD") {
            	    location.href = "/errorPage";
            	} else if (response.response === "SUCCESS") {
	            	 $("#onboardingLoading").removeAttr("style");
	               	 $("#onboardingTemplatesList").empty();
	               	 var result = "";
	               	 result = result +"<table id='onboardingPagination' class='display' cellspacing='0' width='99.9%' >"; 
	                 result = result +"<thead>"; 
	                 result = result +"  <tr>"; 
	                 result = result +"    <th>Template Name</th>";
	                 result = result +"    <th>Description</th>";
	                 result = result +"    <th>Created Date</th>";
	                 result = result +"    <th>Created By</th>"; 
	                 result = result +"    <th>Status</th>";
	                 '<sec:authorize access="hasRole(\'ROLE_VIEW_EDIT_ON_BOARD_TEMPLATE\')">'
	                		 result = result +"<th>Actions</th>";
	                 '</sec:authorize>';
	                 result = result +"  </tr>"; 
	                 result = result +"</thead>";
	                 result = result +"<tbody>"; 
	                 var json = JSON.parse(response.object);
	               	 for (var i = 0; i < json.length; i++) { 
	                   	 var opt = json[i]; 
	                   	 result = result +"<tr>";
	                   		 result = result +"<td><div class='tbldata-dotdot-sm'><span title='"+opt.templateName+"'>"+opt.templateName+"</span></div></td>";
	                   		 result = result +"<td><div><span title='"+opt.description+"'>"+opt.description+"</span></div></td>";
	                   		 result = result +"<td><div><span>"+opt.templateCreatedDate+"</span></div></td>";
	                   		 result = result +"<td><div><span>"+opt.createdUser+"</span></div></td>";
	                   		 result = result +"<td><div><span>"+opt.templateStatus+"</span></div></td>";
	                   		 '<sec:authorize access="hasRole(\'ROLE_VIEW_EDIT_ON_BOARD_TEMPLATE\')">'
			                    	 result = result +"<td><div class='tblicons'><a href='"+atsUrl+"/viewOnboardTemplateByTemplateUUID?templateUUID="+opt.templateUUID+"' title='View' class='mr10'><i class='fa fa-eye'></i></a></div></td>"; 
			                     '</sec:authorize>';
		                     result = result +"</tr>";
		                     result = result +"</tr>";
	                  }
	                  result = result +" </tbody>";
	                  result = result +"</table>"; 
	                  $("#onboardingLoading").removeAttr("style");
	                  $("#onboardingTemplatesList").append(result); 
	                  onboardingPagenation(); 
            	} else if (response.indexOf('Sign In') !== -1) {
            		location.replace(sourceleadUrl);
            	} else {
            	    showError("Operation failed, please try again");
            	}
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