<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script src="resources/js/formvalidator.js"></script>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<style type="text/css">
.statusFiler-1 {
width: 150px;
}
.statusFiler-1 ul{
padding: 5px;
}
.statusFiler-1 li label {
padding-left: 5px;
position: relative;
top: -2px;
}
.statusFiler-1 li {
padding: 2px 10px !important;
margin: 0 !important;
}
</style>

<!-- Content Area -->
<section class="content-wraper">
<section class="content-header clearfix">
  <div class="pull-left">
      <!-- <h2>Submission Templates</h2> -->
      <h2 class="pull-left">Contract Templates</h2>
				<div class=" pull-left reportsH">
			
			<div class="RePop openTemplates">
				
			</div>
		</div>
    </div>
  <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='/orgTemplates'/>"> Templates</a> &raquo; Contract Template List</div>
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
        	
        	<li id="filterContractTypePop"><i class="fa fa-filter"></i><span>Type</span><i class="glyphicon glyphicon-play"></i>
	           	<div class="statusFiler-1">
					<ul class="list-unstyled">
						<li><input id="c1" type="checkbox" name="templateType" value="Client MSA" onclick="getTemplateList();"><label for="c1">Client MSA</label></li>
						<li><input id="c2" type="checkbox" name="templateType" value="Vendor MSA" onclick="getTemplateList();"><label for="c2">Vendor MSA</label></li>
						<li><input id="c3" type="checkbox" name="templateType" value="Client SOW" onclick="getTemplateList();"><label for="c3">Client SOW</label></li>
						<li><input id="c4" type="checkbox" name="templateType" value="Vendor SOW" onclick="getTemplateList();"><label for="c4">Vendor SOW</label></li>
						<li><input id="c5" type="checkbox" name="templateType" value="NDA" onclick="getTemplateList();"><label for="c5">NDA</label></li>
						<li><input id="c6" type="checkbox" name="templateType" value="NSA" onclick="getTemplateList();"><label for="c6">NSA</label></li>
					</ul>
				</div>	                	
			</li>
			
        		<sec:authorize access="hasAnyRole('ROLE_ADD_CONTRACT_TEMPLATE')">
				<li><a href="<c:url value="/addContractTemplate" />"><i class="fa fa-plus"></i>Add Template</a></li>	
				</sec:authorize>
                <div class="clearfix"></div>
            </ul>
            <div class="clearfix"></div>
                         
            
        </div>
    <div class="project_wrap_table">
    <div id="templateList" class="datatable_filter"></div>
	</div>
    
</div>
      
<script type="text/javascript">
function getTemplateList(){
	
	var templateType = [];
	$('input:checkbox[name=templateType]:checked').each(function() 
    {
		templateType.push($(this).val());
    });
	
		var sourceleadUrl ='${sourceleadUrl}';
		 $.ajax({
	       type : "GET",
	        url: sourceleadUrl+"/getAllTemplatesByOrgLocationId?${_csrf.parameterName}=${_csrf.token}&templateType="+templateType,       
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
$("#filterContractTypePop").click(function(){
	$(".statusFiler-1").toggle();
	//$(".statusFiler-2, .statusFiler-3").hide();
});

$("html").click(function() {
    $(".statusFiler-1").hide();
});	
$("#filterContractTypePop, .statusFiler-1, .approved_pop").click(function(e) {
    e.stopPropagation();
});	
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




