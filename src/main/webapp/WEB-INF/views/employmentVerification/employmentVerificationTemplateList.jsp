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
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Employment Verfication List</div>
    </section>
    <div class="content-area content-new clearfix">
      <div class="clearfix form-horizontal">

<div class="project_wrap">
                 <div class="timesheet_head minHeight40">
					<ul>
					<li> <a href="<c:url value="/app/addEmploymentVerificationTemplate"/>" title="Add Template"> <i class="fa fa-plus paddingRight"></i>Add</a> </li>
					<div class="clearfix"></div>
					</ul>
					<div class="clearfix"></div>
                 </div>
	
	<div class="candidate_wrap_table datatable_filter">
	                  <div id="templateList"></div>
                  </div>
	
</div>     
      
      <!-- Content Area Ends--> 
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
 	result = result +" <div class='clearfix'>";
 	result = result +" <div class='remove_search'>";
 	result = result +" <input type='text' placeholder='Search' id='employmentTemplateTableSearch'/>";
 	result = result +"</div>";
 	result = result +"</div>";
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
            method: 'POST',
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
		             return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "description",
              "render": function ( data, type, report, meta ) {
            	  var result = "";
	              return result;
              		
              }
	          },{
             "targets": colIndex++,
             "data": "signatory",
             "render": function ( data, type, report, meta ) {
             	var result = "";
             	
             	return result;
             }
 	          
          }, {
              "targets": colIndex++,
              "data": "createdBy",
              "render": function ( data, type, report, meta ) {
              	var result = "";
              	
              	return result;
              }
  	          
           }, {
             "targets": colIndex++,
             "data": "action",
             "render": function ( data, type, report, meta ) {
             	var result = "";
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

<!-- Footer Area -->
<jsp:include page="../masterFooter.jsp" />
<!-- Footer Area -->

