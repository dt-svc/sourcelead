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
      <h2 class="pull-left">Offer Letter Templates</h2>
    </div>
    
    <div class="pull-right brud-crum">
			<a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a>
			&raquo;  <a href="<c:url value='${appPath}/settings'/>">Settings</a>&raquo;
			         <a href="${SourceLeadWeb_sourceleadUrl}/orgTemplates"> Templates
				</a> &raquo; Offer Letter Template List
		</div>
  
</section>
	
	
<script type="text/javascript">
$(document).ready(function(){
   getOfferLetterTemplateList();
});
</script>
	
 <div class="project_wrap">
 
 <div class="reportHead  clearfix">
      <ul class="pull-right list-unstyled list-inline">
			
			<li><a href="${SourceLeadWeb_sourceleadUrl}/addOfferLetterTemplate" class="newAdBtn mr5">+ Template</a></li>	
			<li class=""><input type="text" class="repSearchBar" placeholder='Search' id='offerLetterTemplateTableSearch'/><span class="repSearchIcon" onClick=""></span></li>
		
        </ul>
        
      </div>
      
    <div class="project_wrap_table">
    <div id="templateList" class="datatable_filter"></div>
	</div>
    
</div>
</section>
      
 <script type="text/javascript">
function getOfferLetterTemplateList(){
		var sourceleadUrl ='${sourceleadUrl}';
		var searchValue = $("#offerLetterTemplateTableSearch").val();
		searchValue = searchValue ? searchValue : "";
		var url = sourceleadUrl+"/getAllOfferLetterTemplatesByOrgLocationId?${_csrf.parameterName}=${_csrf.token}&searchValue="+searchValue;
	    prepareTemplateList(url);
	    
}
</script>      

<script>
function prepareTemplateList(url){
 	 var result = "";
	 	result = result +"<table id='offerLetterTemplatePagination' class='display' cellspacing='0' width='100%' >"; 
	    result = result + '	<thead>';
		result = result + '   <tr>';
		result = result + '     <th>Template Title</th>';
		result = result + '    <th>Description</th>';
		result = result + '    <th>Letter Head</th>';
	 	result = result + '    <th>Actions</th>';
	 	result = result + '   </tr>';
	 	result = result + '	</thead>';
        result = result +"</table>";
 	    $("#templateList").empty();
        $("#templateList").append(result);
	offerLetterTemplatePagination(url); 
	}
</script>

<script>
function offerLetterTemplatePagination(url){
	var sourceleadUrl ='${sourceleadUrl}';
	var colIndex = 0;
    $('#offerLetterTemplatePagination').DataTable( { 
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
            { "data": "templateType"},
            { "data": "action"}
            
        ],
        "columnDefs": [{
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
 	          
          },{
	    	  "targets": colIndex++,
              "data": "letterHead",
              "render": function ( data, type, report, meta ) {
            	  var result = "";
            	  if(report.letterHeadId != null && report.letterHeadId != '' && report.letterHeadId !='undefined'){
       	       	       result = result + '<div><span title="'+report.letterHeadId.letterHeadName+'">'+report.letterHeadId.letterHeadName+'</span></div>';
            	  } else{
       			       result = result + '<div><span>---</span></div>';
      			    }
	              return result;
              		
              }
	        },{
	              "targets": colIndex++,
	              "data": "action",
	              "render": function ( data, type, report, meta ) {
	              	var result = "";
	              	result = result + '<div class="tblicons">';
	              	result = result + ' <a href="#" title="Preview" class="mr10"><i class="fa fa-eye" onclick="previewPDF(\''+report.templateUUID+'\',\''+report.templateName+'\');" ></i></a> ';
	              	result = result + ' <a href="'+sourceleadUrl+'/addOfferLetterTemplate?templateUUID='+report.templateUUID+'" title="Edit"><i class="fa fa-pencil"></i></a>';
	              	result = result + ' <a href="javascript:void(0)" title="Delete"><i class="fa fa-trash textDelete" onclick="deleteOfferTemplate(\''+report.templateUUID+'\');"></i></a>';
	              	result = result + '</div>';
	              	return result;
	                 }
	              	
	              }  
        
       ], 
        
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
    $("#offerLetterTemplatePagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#offerLetterTemplateTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   getOfferLetterTemplateList();
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
<script>
function deleteOfferTemplate(templateUUID){
	var sourceleadUrl ='${sourceleadUrl}';
	if(templateUUID != ''){
	$.ajax({
		type : "POST",
		url : sourceleadUrl	+ "/deleteOfferLetterTemplateByTemplateUUID?&templateUUID="+templateUUID,
		success : function(data) {
			location.reload();
		},
	});
}
}


</script>

<script type="text/javascript">
function previewPDF(templateUUID,tempName) {
	var sourceleadUrl ='${sourceleadUrl}';
		window.open(sourceleadUrl+'/offerLetterTempleateView/'+templateUUID+'/'+tempName+'','_newtab');
}
</script>

 <script>
function downloadPdfTemplate(templateId){
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	window.location.href = vmsUrl+"/downloadContractsTemplate?${_csrf.parameterName}=${_csrf.token}&templateId="+templateId;
	/* window.location.href = sourceleadUrl+"/downloadContractsTemplate&templateId="+templateId; */
}

</script> 
