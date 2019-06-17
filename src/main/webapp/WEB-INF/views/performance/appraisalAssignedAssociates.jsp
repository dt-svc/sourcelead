<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

  
  <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Assigned Associates <span id="templateName"></span></h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; 
      <a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/settings'/>">Settings</a> &raquo;
      <a href="${SourceLeadWeb_sourceleadUrl}/performanceManagementConfiguration">Performance Management</a> &raquo;<a onclick="performanceManagement.viewAppraisalCycle();"> Appraisal Cycle List</a> &raquo; Assigned Associates List <span id = "fname"></a> </div>
</section>
  
  <div class="content-area content-new clearfix backWhite">
 <div id="appraisalAssignedAssociatesLoading"></div>
         <div class="clearfix">
         <div class="positionRelative reportHead clearfix"> 
            <ul class="pull-right list-unstyled list-inline">
		    <li id="appraisalAssignedAssociatesListSearch"><input type="text" class="repSearchBar"  id='appraisalAssignedAssociatesDataTableSearch'/><span class="repSearchIcon" onClick="getAppraisalAssignedAssociatesList('${uuid}');"></span></li>
        	</ul>
         </div>
      
          
        <div class="clearfix"></div>  
          
          
          <div class="candidate_wrap_table datatable_filter positionRelative">
		    <div class="project_tabs_inn_table vendor_wrap_inn_table dataFilterNew pdtop">
		    	<div id="appraisalAssignedAssociatesList" class="datatable_filter"></div>
			</div>	  
		
          </div>
        
 </div>
 </div>

 
 <script>
 function getAppraisalAssignedAssociatesList(templateUUId){
	   $("#appraisalAssignedAssociatesLoading").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	   $("#templateName").html('(${templateName})')
	   
	    var result = "";
	 	result = result + '<table id="appraisalAssignedAssociatesListTable" class="display mt10" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result + '<th>Associate Id</th>';
	  	result = result + '<th>Associate Name</th>';
	  	result = result + '<th>Email</th>';
	  	result = result + '<th>Role</th>';
	  	result = result + '<th>Reporting Manager</th>';
	  	result = result + '<th>Action</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
	    result = result +"</table>";
	    $("#appraisalAssignedAssociatesList").empty();
	    $("#appraisalAssignedAssociatesList").append(result);
	    appraisalAssignedAssociatesTableList(templateUUId);
     
 }
 </script>
 
  <script>
	function appraisalAssignedAssociatesTableList(templateUUId){
		 var sourceleadUrl = '${SourceLeadWeb_sourceleadUrl}';
		 var searchValue = $("#appraisalAssignedAssociatesDataTableSearch").val();
		 searchValue = searchValue ? searchValue : "";
		 var url = sourceleadUrl+"/getAppraisalAssignedAssociatesList?appraisalTemplateUUId="+templateUUId+"&searchValue="+searchValue;
		  appraisalAssignedAssociatesPagination(url,templateUUId);
		 $("#appraisalAssignedAssociatesLoading").removeAttr("style");
	}
</script>

<script>
function appraisalAssignedAssociatesPagination(url,templateUUId){
	
	var colIndex = 0;
    $('#appraisalAssignedAssociatesListTable').DataTable( { 
      "processing": true,
      "serverSide": true,
      "searching": false,
      "ajax": $.fn.dataTable.pipeline( {
    	  url: url,
          method: 'GET',
          pages: 1
        }),
        "columns": [
            { "data": "associateId" },
            { "data": "associateName"},
            { "data": "associateEmail"},
            { "data": "associateRole"},
            { "data": "reportingManager"},
            { "data": "actions"}
        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "associateId",
	    	  	"sortable": false,
	    	  	"render": function(data, type, opt, meta) {
	    	  		 var result = "";
                	 result = result +"<td>";
	    	  	  	 result = result +"<div class='width300px UserTextLeft tabImg clearfix'>";
	    	  		 result = result +"<img class='m-t-xs pull-left' onerror='predefineAssociateImage(this);' src='"+opt.userImgUrl+"'>";
	    	  		 result = result +"<div class='pull-left' title='"+opt.associateId+"'>"+opt.associateId+"</div>";
	    	  		 result = result +"</div>";
	    	  		 result = result +"</td>";
	    	  		 
		             return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "associateName",
              "sortable": false,
              "render": function ( data, type, opt, meta ) {
            	     var result = "";
	    	  	   	 result = result +"<td>";
             	     result = result +"<div class='width300px'>";
             	     result = result +"<div class='tbldata-dotdot-big pull-left' title='"+opt.associateName+"'>"+opt.associateName+"</div>";
             	     result = result +"</div>";
             	     result = result +"</td>";
		             return result;
              		
              }
	      
          },{
		    	  "targets": colIndex++,
	              "data": "associateEmail",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	            	  var result = "";
	            	     result = result +"<td>";
	            	     result = result +"<div class=''>";
	             	     result = result +"<div class='tbldata-dotdot-me pull-left' title='"+opt.associateEmail+"'>"+opt.associateEmail+"</div>";
	             	     result = result +"</div>";
	             	     result = result +"</td>";
			             return result;
	              }
		     }, {
		    	  "targets": colIndex++,
	              "data": "associateRole",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	            	     var result = "";
	            	     result = result +"<td>";
	             	     result = result +"<div class='tbldata-dotdot-sm' title='"+opt.associateRole+"'>"+opt.associateRole+"</div>";
	             	     result = result +"</td>";
			             return result;
	              }
		     }, {
		    	  "targets": colIndex++,
	              "data": "reportingManager",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	            	     var result = "";
	            	     result = result +"<td>";
	             	     result = result +"<div class='tbldata-dotdot-sm' title='"+opt.reportingManager+"'>"+opt.reportingManager+"</div>";
	             	     result = result +"</td>";
			             return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "actions",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    if(opt.daysDifference < 0){
	            	    	 result = result	+ '<td><i class="fa fa-trash cursorPointer text-danger ml10" onclick="deleteAssignedAssociate(\''+opt.uuid+'\');"></i></a></td>';
	            	    }else{
	            	    	 result = result	+ '<td>--</td>';
	            	    }
	            	   
	            		return result;
	              }
		     }],
		     
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"aaSorting": [[0,'desc']],
	    	"ordering": false,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#appraisalAssignedAssociatesListTable").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#appraisalAssignedAssociatesDataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
   		    $('#appraisalAssignedAssociatesDataTableSearch').parent().find('p.jquery_form_error_message').remove();
   	    	var searchData = $('#appraisalAssignedAssociatesDataTableSearch').val();
   	    	
   	 	if(searchData.endsWith("-") || searchData.endsWith("\\") || searchData.endsWith("//") || searchData.endsWith("%")){
	    		$("#appraisalAssignedAssociatesDataTableSearch").after("<p class='jquery_form_error_message'><span><font color='red'>Enter valid Search Data</font></span>");
	    	}else{
	    		appraisalAssignedAssociatesTableList(templateUUId);
	    	}
   	    	$('#appraisalAssignedAssociatesDataTableSearch').val(searchData);
    	   }
    	});
}
</script>

<script>
	function predefineAssociateImage(imageId){
		$(imageId).attr('src','/resources/images/userprofileImg.jpg');
	}
</script>
