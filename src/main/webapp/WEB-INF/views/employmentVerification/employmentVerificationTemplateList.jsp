<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script>
$(document).ready(function(){
	getEmploymentVerificationList();
});
</script>

 <!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Employment Verfication List</h2>
      </div>
      <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; <a href="${SourceLeadWeb_sourceleadUrl}/orgTemplates"> Templates</a> &raquo;  Employment Verfication List</div>
    </section>

<div class="project_wrap">
             
	 <div class="reportHead clearfix">
	    <ul class="pull-right list-unstyled list-inline">
         	<li><a href="${SourceLeadWeb_sourceleadUrl}/addEmploymentVerificationTemplate" title="Add Template" class="newAdBtn mr5">Add</a></li>
         	<li><input type="text" class="repSearchBar" placeholder='Search' id='employmentTemplateTableSearch'/><span class="repSearchIcon" onClick=""></span></li>
		</ul>
	</div>	
	
	<div class="candidate_wrap_table datatable_filter">
		<div id="templateList"></div>
	</div>
	
</div>     
      
  </section>
  
  
  <script>
  function getEmploymentVerificationList(){
		var searchValue = $("#employmentTemplateTableSearch").val();
		searchValue = searchValue ? searchValue : "";
		var url = sourceleadUrl+"/getAllEmploymentVerificationListByOrgLocationId?${_csrf.parameterName}=${_csrf.token}&searchValue="+searchValue;
		prepareEmploymentVerificationTemplateList(url);
  }
  </script>
  
  <script>
function prepareEmploymentVerificationTemplateList(url){
	 $("#templateList").removeAttr("style");
 	 $("#templateList").empty();
 	 var result = "";
 	 
/*  	result = result +" <div class='clearfix'>";
 	result = result +" <div class='remove_search'>";
 	result = result +" <input type='text' placeholder='Search' id='employmentTemplateTableSearch'/>";
 	result = result +"</div>";
 	result = result +"</div>"; */
 	
 	result = result +"<table id='employmentTemplatePagination' class='display' cellspacing='0' width='100%' >"; 
    result = result + '	<thead>';
	result = result + '   <tr>';
	result = result + '     <th>Name/Title</th>';
	result = result + '    <th>Description</th>';
	result = result + '    <th>Signatory</th>';
 	result = result + '    <th>Created by</th>';
 	result = result + '    <th>Action</th>';
 	result = result + '   </tr>';
 	result = result + '	</thead>';
    result = result +"</table>";
    $("#templateList").append(result);
    employmentTemplatePagination(url); 
}
</script>

<script>
function employmentTemplatePagination(url){
	
	var colIndex = 0;
    $('#employmentTemplatePagination').DataTable( { 
        "processing": true,
        "serverSide": true,
        "searching": false,
        "ajax": $.fn.dataTable.pipeline( {
            url: url,
            method: 'GET',
            pages: 10
        }),
        "columns": [
            { "data": "templateName" },
            { "data": "description"},
            { "data": "signatory"},
            { "data": "createdBy"},
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
	    	  "sortable": false,
              "data": "description",
              "render": function ( data, type, report, meta ) {
            	  var result = "";
            	  if(report.description != null && report.description != '' && report.description !='undefined'){
	                   result = result + '<div><span title="'+report.description+'">'+report.description+'</span></div>';
     			  } else{
    		       result = result + '<div><span>---</span></div>';
    		      }
	              return result;
              		
              }
	          },{
             "targets": colIndex++,
             "data": "signatory",
             "render": function ( data, type, report, meta ) {
             	var result = "";
             	if(report.signatoryFullName != null && report.signatoryFullName != '' && report.signatoryFullName !='undefined'){
	                   result = result + '<div><span title="'+report.signatoryFullName+'">'+report.signatoryFullName+'</span></div>';
  			  } else{
 		       result = result + '<div><span>---</span></div>';
 		      }
             	return result;
             }
 	          
          }, {
              "targets": colIndex++,
              "data": "createdBy",
              "render": function ( data, type, report, meta ) {
              	var result = "";
              	if(report.createdBy != null && report.createdBy != '' && report.createdBy !='undefined'){
	                   result = result + '<div><span title="'+report.createdBy+'">'+report.createdBy+'</span></div>';
			  } else{
		       result = result + '<div><span>---</span></div>';
		      }
              	return result;
              }
  	          
           }, {
             "targets": colIndex++,
             "data": "action",
             "sortable": false,
             "render": function ( data, type, report, meta ) {
             	var result = "";
				result = result + '    	<td>                                                                                                          '; 
				result = result + '       <div class="tblminwidth-75">                                                                                '; 
				result = result + '       		<ul class="list-unstyled list-inline actionIcons clearfix ml5">                                       '; 
				result = result + '       			<li><a href="javascript:void(0)" title="View"><i class="fa fa-eye textView" onclick="previewEmpTemplate(\''+report.templateUUID+'\',\''+report.templateName+'\');"></i></a></li>         '; 
				result = result + '       			<li><a href="'+sourceleadUrl+'/editEmpTemplate?templateUUID='+report.templateUUID+'" title="Edit"><i class="fa fa-pencil textEdit"></i></a></li>      '; 
				result = result + '       			<li><a href="javascript:void(0)" title="Delete"><i class="fa fa-trash textDelete" onclick="deleteEmpTemplate(\''+report.templateUUID+'\');"></i></a></li>   '; 
				result = result + '       		</ul>                                                                                                 '; 
				result = result + '       </div>                                                                                                      '; 
				result = result + '     </td>  																										  '; 
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
    $("#employmentTemplatePagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#employmentTemplateTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   getEmploymentVerificationList();
    	   }
    	});
}
</script>


<script>
function previewEmpTemplate(templateUUID,templateName){
	window.open(sourceleadUrl+'/previewEmpTemplate/'+templateUUID+'/'+templateName+'','_newtab');
}
</script>

<script>
function deleteEmpTemplate(templateUUID){
	 $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/deleteEmployementTemplate?"+tokenParamName+"="+tokenValue+"&templateUUID="+templateUUID,
		    success: function(data) {
// 			 	location.replace(sourceleadUrl+"/getAllEmploymentVerificationListByOrgLocationId");
			 	getEmploymentVerificationList();
		    }
	     });
}
</script>

<!-- Footer Area -->
<jsp:include page="../masterFooter.jsp" />
<!-- Footer Area -->

