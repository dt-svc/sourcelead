<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script src="/resources/js/formvalidator.js"></script>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<!-- Content Area -->
<section class="content-wraper">
<section class="content-header clearfix">
  <div class="pull-left">
      <!-- <h2>Submission Templates</h2> -->
      <h2 class="pull-left">Contract Templates</h2>
    </div>
  <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;  <a href="<c:url value='${appPath}/settings'/>">Settings</a>&raquo; <a href="<c:url value='${appPath}/orgTemplates'/>"> Templates </a> &raquo; Contract Template List</div>
</section>
	
	
<script type="text/javascript">
$(document).ready(function(){
   getTemplateList();
});
</script>
	
 <div class="project_wrap">
 
 <div class="reportHead  clearfix">
      <ul class="pull-right list-unstyled list-inline">
			
        	<sec:authorize access="hasAnyRole('ROLE_ADD_CONTRACT_TEMPLATE')">
				<li><a href="<c:url value="/app/addContractTemplate" />" class="newAdBtn mr5">+ Template</a></li>	
			</sec:authorize>      
      
        	<li id="filterContractTypePop"><a href="javascript:void(0)" class="filtIcon" title="Filters"></a>
	           	<div class="statusFiler-1">
					<ul class="list-unstyled">
						<li><input id="c1" type="checkbox" name="templateType" value="Client MSA" onclick="getTemplateList();"><label for="c1">Client MSA</label></li>
						<li><input id="c2" type="checkbox" name="templateType" value="Vendor MSA" onclick="getTemplateList();"><label for="c2">Vendor MSA</label></li>
						<li><input id="c3" type="checkbox" name="templateType" value="Client SOW" onclick="getTemplateList();"><label for="c3">Client SOW</label></li>
						<li><input id="c4" type="checkbox" name="templateType" value="Vendor SOW" onclick="getTemplateList();"><label for="c4">Vendor SOW</label></li>
						<li><input id="c5" type="checkbox" name="templateType" value="NDA" onclick="getTemplateList();"><label for="c5">NDA</label></li>
						<li><input id="c6" type="checkbox" name="templateType" value="NSA" onclick="getTemplateList();"><label for="c6">NSA</label></li>
	                	<li><input id="c7" type="checkbox" name="templateType" value="W-4 Form" onclick="getTemplateList();"><label for="c7">W-4 Form</label></li>               
                        <li><input id="c8" type="checkbox" name="templateType" value="I-9 Form" onclick="getTemplateList();"><label for="c8">I-9 Form</label></li> 
                        <li><input id="c9" type="checkbox" name="templateType" value="Full Time Offer" onclick="getTemplateList();"><label for="c9">Full Time Offer</label></li> 
 
					</ul>
				</div>	                	
			</li>

			<li class=""><input type="text" class="repSearchBar" placeholder='Search' id='contractTemplateTableSearch'/><span class="repSearchIcon" onClick=""></span></li>
		
        </ul>
        
      </div>
      
    <div class="project_wrap_table">
    <div id="templateList" class="datatable_filter"></div>
	</div>
    
</div>
</section>
      
 <script type="text/javascript">
function getTemplateList(){
		var templateType = [];
		$('input:checkbox[name=templateType]:checked').each(function() 
	    {
			templateType.push($(this).val());
	    });
		var sourceleadUrl ='${sourceleadUrl}';
		var searchValue = $("#contractTemplateTableSearch").val();
		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		searchValue = searchValue ? searchValue : "";
		var url = sourceleadUrl+"/getAllTemplatesByOrgLocationId?${_csrf.parameterName}=${_csrf.token}&templateType="+templateType+"&orgLocationId="+orgLocationId+"&searchValue="+searchValue;
		/* url += "&"+$($('#payroll_list_form')[0]).serialize(); */
	    prepareTemplateList(url,sourceleadUrl);
}
</script>      

<script>
function prepareTemplateList(url,sourceleadUrl){
	 $("#templateList").removeAttr("style");
 	 $("#templateList").empty();
 	 var result = "";
 	 
/*  	result = result +" <div class='clearfix'>";
 	result = result +" <div class='remove_search'>";
 	result = result +" <input type='text' placeholder='Search' id='contractTemplateTableSearch'/>";
 	result = result +"</div>";
 	result = result +"</div>"; */
 	
 	result = result +"<table id='contractTemplatePagination' class='display' cellspacing='0' width='100%' >"; 
    result = result + '	<thead>';
	result = result + '   <tr>';
	result = result + '     <th>Template Title</th>';
	result = result + '    <th>Template Type</th>';
	result = result + '    <th>Letter Head</th>';
	result = result + '    <th>Description</th>';
 	result = result + '    <th>Actions</th>';
//  result = result + '    <th>Document</th>';
 	result = result + '   </tr>';
 	result = result + '	</thead>';
        result = result +"</table>";
      $("#templateList").append(result);
      contractTemplatePagination(url,sourceleadUrl); 
}
</script>

<script>
function contractTemplatePagination(url,sourceleadUrl){
	
	var colIndex = 0;
	/*$.fn.dataTable.moment( 'DD-MM-YYYY' );*/
    $('#contractTemplatePagination').DataTable( { 
    		"processing": true,
        "serverSide": true,
        "searching": false,
        "ajax": $.fn.dataTable.pipeline( {
            url: url,
            method: 'POST',
            pages: 10
        }),
        "columns": [
            { "data": "templateName" },
            { "data": "templateType"},
            { "data": "letterHead"},
            { "data": "description"},
            { "data": "action"}
            
        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "templateName",
	    	  	"render": function(data, type, report, meta) {
	    	  		  var result = "";
	    	  		if(report.templateName != null && report.templateName != '' && report.templateName !='undefined'){
	    	                   result = result + '<div><span title="'+report.templateName+'">'+report.templateName+'</span></div>';
	    	     		} else{
	    	    		       result = result + '<div><span>---</span></div>';
	    	    		    }
		             return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "templateType",
              "render": function ( data, type, report, meta ) {
            	  var result = "";
            	  if(report.templateType != null && report.templateType != '' && report.templateType !='undefined'){
            	       	       result = result + '<div><span title="'+report.templateType+'">'+report.templateType+'</span></div>';
            	 		} else{
            			       result = result + '<div><span>---</span></div>';
            			    }
	              return result;
              		
              }
	          },{
		    	  "targets": colIndex++,
	              "data": "letterHead",
	              "render": function ( data, type, report, meta ) {
	            	  var result = "";
	            	  if(report.letterHead != null && report.letterHead != '' && report.letterHead !='undefined'){
	            	       	       result = result + '<div><span title="'+report.letterHead.letterHeadName+'">'+report.letterHead.letterHeadName+'</span></div>';
	            	 		} else{
	            			       result = result + '<div><span>---</span></div>';
	            			    }
		              return result;
	              		
	              }
		          },{
             "targets": colIndex++,
             "data": "description",
             "render": function ( data, type, report, meta ) {
             	var result = "";
             	if(report.description != null && report.description != '' && report.description !='undefined'){
                         result = result + '<div><span title="'+report.description+'">'+report.description+'</span></div>';
                    }else{
            		        result = result + '<div><span>---</span></div>';
            		    }
             	
             	
             	return result;
             }
 	          
          },   {
             "targets": colIndex++,
             "data": "action",
             "render": function ( data, type, report, meta ) {
             	var result = "";
             	'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_CONTRACT_TEMPLATE\')">';
             	result = result + '<div class="tblicons"><a href="#" title="Preview" class="mr10"><i class="fa fa-eye" onclick="previewPDF(\''+report.templateUUID+'\',\''+report.templateName+'\');" ></i></a> <a href="'+sourceleadUrl+'/editTemplate?templateId='+report.id+'" title="Edit"><i class="fa fa-pencil"></i></a></div>';
             	'</sec:authorize>';
             	return result;
                }
             	
             }], 
          
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20,], [10, 15, 20,]],
	    	"aaSorting": [[0,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>" 
 	     }
    } );
    $("#contractTemplatePagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#contractTemplateTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   getTemplateList();
    	   }
    	});
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
// 	window.open(sourceleadUrl+'/testController/'+id+'/'+tempName+'','_newtab');
	window.open(sourceleadUrl+'/previewContractTemplate/'+id+'/'+tempName+'','_newtab');
}</script>

 <script>
function downloadPdfTemplate(templateId){
	var sourceleadUrl ='${sourceleadUrl}';
	window.location.href = sourceleadUrl+"/downloadContractsTemplate?${_csrf.parameterName}=${_csrf.token}&templateId="+templateId;
	/* window.location.href = sourceleadUrl+"/downloadContractsTemplate&templateId="+templateId; */
}

</script> 
