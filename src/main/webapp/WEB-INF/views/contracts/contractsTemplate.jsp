<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script src="resources/js/formvalidator.js"></script>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!-- <script src="resources/js/chosen.jquery.min.js"></script> --> 


<!-- these files for autocomplete with tagit -->
<link href="resources/css/jquery-tagit.css" rel="stylesheet">
<!-- end files for autocomplete with tagit -->

<!-- Content Area -->
<section class="content-wraper">
<section class="content-header clearfix">
  <div class="pull-left">
    <h2>Contract Template</h2>
  </div>
  <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Contract Template List</div>
</section>
	
	
<script type="text/javascript">
$(document).ready(function(){
   getTemplateList();
});
</script>
	
	
<div class="content-area content-new clearfix">
 <div class="project_wrap">
	<div class="timesheet_head">
        	<ul>
        		<sec:authorize access="hasAnyRole('ROLE_ADD_CONTRACT_TEMPLATE')">
				<li><a href="<c:url value="/addContractTemplate" />"><i class="fa fa-plus"></i>Add Template</a></li>	
				</sec:authorize>
                <div class="clearfix"></div>
            </ul>
            <div class="clearfix"></div>
                         
            
        </div>
    <div class="project_wrap_table">
    <div id="templateList"></div>
	</div>
    
</div>
      
<script type="text/javascript">
function getTemplateList(){
		var sourceleadUrl ='${sourceleadUrl}';
		 $.ajax({
	       type : "GET",
	        url: sourceleadUrl+"/getAllTemplatesByOrgLocationId?${_csrf.parameterName}=${_csrf.token}",       
	        success :function(data) {
	       	 preparedTemplateList(data);
	       },
	   }); 
}
</script>
<!-- Content Area Ends-->


<script>
function preparedTemplateList(data){
	var sourceleadUrl ='${sourceleadUrl}';
	var result = '';
	result = result +  '<table id="contractTemplateTable" class="display" cellspacing="0" width="100%" >';
	result = result + '	<thead>';
	result = result + '   <tr>';
	result = result + '     <th>Template Title</th>';
	result = result + '    <th>Template Type</th>';
	result = result + '    <th>Description</th>';
	result = result + '    <th>Actions</th>';
// 	result = result + '    <th>Document</th>';
	result = result + '   </tr>';
	result = result + '	</thead>';
	result = result + '	<tbody>';
	var json = JSON.parse(data);
    for (var i = 0; i < json.length; i++) {
    	var report= json[i];
    	result = result + '<tr id="row'+report.id+'">';
        result = result + '<td>';
        if(report.templateName != null && report.templateName != '' && report.templateName !='undefined'){
           	result = result + '<div><span>'+report.templateName+'</span></div>';
     		} else{
    		result = result + '<div><span>---</span></div>';
    		    }
        result = result + '</td>';
    	result = result + '<td>';
        if(report.templateType != null && report.templateType != '' && report.templateType !='undefined'){
       	result = result + '<div><span>'+report.templateType+'</span></div>';
 		} else{
		result = result + '<div><span>---</span></div>';
		    }
        result = result + '</td>';
        result = result + '<td>';
        if(report.description != null && report.description != '' && report.description !='undefined'){
         result = result + '<div><span>'+report.description+'</span></div>';
        }
        else{
		 result = result + '<div><span>---</span></div>';
		    }
         result = result + '</td>';
         result = result + '<td>';
        /*  result = result + '<div><span onclick="previewPDF('+report.id+')">previews/edit</span></div>'; */
         '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_CONTRACT_TEMPLATE\')">';
         result = result + '<div class="tbldata-dotdot-vsm"><a href="#" title="Preview" class="mr"><i class="fa fa-eye" onclick="previewPDF(\''+report.templateUUID+'\',\''+report.templateName+'\')"></i></a> <a href=\"'+sourceleadUrl+'/editTemplate?templateId='+report.id+'\" title="Edit"><i class="fa fa-edit"></i></a></div>';
         '</sec:authorize>';
         result = result + '</td>';
         /* result = result + '<td>';
         result = result + '<div><a class="glyphicon glyphicon-paperclip pinCo" href="" onclick="downloadPdfTemplate('+report.id+')"></a></div>';
         result = result + '</td>'; */
        result = result + '</tr>';
    }
	result = result + '	</tbody>';
	result = result + '</table>';
	$("#templateList").empty();
    $("#templateList").append(result);
    listTemplatePagination();	
	
}

</script>


<script>
function listTemplatePagination(){
	$('#contractTemplateTable').DataTable({ 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
}
</script>

<script type="text/javascript">
function previewPDF(id,tempName) {
	var sourceleadUrl ='${sourceleadUrl}';
	window.open(sourceleadUrl+'/testController/'+id+'/'+tempName+'','_newtab');
}</script>

 <script>
function downloadPdfTemplate(templateId){
	var sourceleadUrl ='${sourceleadUrl}';
	window.location.href = sourceleadUrl+"/downloadContractsTemplate?${_csrf.parameterName}=${_csrf.token}&templateId="+templateId;
	/* window.location.href = sourceleadUrl+"/downloadContractsTemplate&templateId="+templateId; */
}

</script> 
<!-- Footer Area -->
<%-- <jsp:include page="../masterFooter.jsp" /> --%>
<!-- Footer Area -->




