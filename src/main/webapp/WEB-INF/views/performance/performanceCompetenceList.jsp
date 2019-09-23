<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<style>
.competencePaddingRight{
padding: 0px 240px 8px 0px;
}
</style>


<section class="content-header clearfix">
      <div class="pull-left">
        <h2>Performance Competence</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; 
      <a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/settings'/>">Settings</a> &raquo;
      <a href="${SourceLeadWeb_sourceleadUrl}/performanceManagementConfiguration">Performance Management</a> &raquo; Performance Competence <span id = "fname"></a> </div>
</section>

<div class="clearfix">
           <ul class="project-tabs-menu" id="tab_bar">
            
	            <li class="current" id="competence">
	            	<a href="#"  onclick="getCompetenceList();" >
	              		Competence 
	              	</a> 
	            </li>
	            
	             <li id="tagCompetence">
	            	<a href="#"  onclick="getTagCompetenceList();">
	              		Tag Competence 
	              	</a> 
	            </li>
          </ul>
 </div>
 
 
<div class="content-area content-new clearfix backWhite">
 
 <div id="comptenceClassId">
      <ul class="pull-right list-unstyled list-inline">
      
            <li>
				<a class="action newAdBtn mr5" onclick="getActions();" href="javascript:void(0)">+ Add <i class="fa fa-caret-down"></i></a>
				<div class="action-pop">
					<span> 
				          <a onclick="performanceManagement.addNewCompetence();" title="Create offboard">Add Competence</a>
				          <a onclick="performanceManagement.tagPerformanceCompetence();" title="Create onboard">Tag Competence</a> 
					</span>
				</div>
			</li>
		  <li id="competenceListSearch"><input type="text" class="repSearchBar"  id='competenceDataTableSearch'/><span class="repSearchIcon" onClick="competenceDataTableList();"></span></li>
        </ul>
      </div>
          
        <div class="clearfix"></div>  
          
          
          <div class="candidate_wrap_table datatable_filter positionRelative" style='position:relative; top:-25px;'>
	          <div id="competenceLoading"></div>
	         
		    <div class="project_tabs_inn_table vendor_wrap_inn_table dataFilterNew pdtop">
		    	<div id="competenceList" class="datatable_filter"></div>
			</div>	  
			
		    <div class="project_wrap_table datatable_filter dataSeaTop mt10"  style='position:relative; top:-5px;'>
	    	<table id="competenceTaggingList" class="display" cellspacing="0" width="100%"></table>
		   </div>
		
          </div>
        
      </div>
      
      <jsp:include page="../performance/addNewPerformanceCompetence.jsp" />  
 
 
 <script>
 function getCompetenceList(){
	 $("#comptenceClassId").removeClass();
	 $("#comptenceClassId").addClass("positionRelative reportHead clearfix");
	 var sourceleadUrl = '${SourceLeadWeb_sourceleadUrl}';
	 var locationCode = '${SourceLeadWeb_locationCode}';
	 
	 var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  competenceListDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  competenceListDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  competenceListDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  competenceListDate = 'YYYY-DD-MM'; 
	  }
	  
	 $("#competenceLoading").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	  var result = "";

	 	result = result + '<table id="competenceListTable" class="display mt10" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result +'	<th>Competence Name</th>';
	  	result = result +'     <th>Created Date<br>(<small class="custom-small">'+competenceListDate+'</small>)</th>';
	  	result = result +'      <th>Description</th>';
	  	result = result +'       <th>Actions</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
	    result = result +"</table>";
	    $("#competenceTaggingList").empty();
	    $("#competenceList").empty();
	    $("#competenceList").append(result);
	    $("#tagCompetence").removeClass("current");
	    $("#competence").addClass("current");
	    $("#competenceListSearch").show();
	    competenceDataTableList();
	   
 }
 </script>
 
<script>
	function competenceDataTableList(){
		 var sourceleadUrl = '${SourceLeadWeb_sourceleadUrl}';
		 var searchValue = $("#competenceDataTableSearch").val();
		 searchValue = searchValue ? searchValue : "";
		 var url = sourceleadUrl+"/getPerformanceCompetenceList?searchValue="+searchValue;
		 competencePagination(url);
		 $("#competenceLoading").removeAttr("style");
	}
</script>


<script>
function competencePagination(url){
	var colIndex = 0;
    $('#competenceListTable').DataTable( { 
      "processing": true,
      "serverSide": true,
      "searching": false,
      "ajax": $.fn.dataTable.pipeline( {
    	  url: url,
          method: 'GET',
          pages: 1
        }),
        "columns": [
            { "data": "competenciesName" },
            { "data": "createdDate"},
            { "data": "description"},
            { "data": "actions"}
        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "competenciesName",
	    	  	"render": function(data, type, opt, meta) {
	    	  		  var result = "";
	    	  	   	 result = result +"<td>";
                	 result = result +"<div class='width300px'>";
                	 result = result +"<div class='tbldata-dotdot-big pull-left' title='"+opt.name+"'>"+opt.name+"</div>";
                	 result = result +"</td>";
		             return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "createdDate",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
             	  result = result +"<td><div><span>"+opt.createdDate+"</span></div></td>";
	              return result;
              		
              }
	      
	          },{
		    	  "targets": colIndex++,
	              "data": "description",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	            	  var result = "";
	            	  result = result +"<td><div class='tbldata-dotdot-sm' title='"+opt.description+"'><span>"+opt.description+"</span></div></td>";
	            	  return result;
	              }
		     }, {
		    	  "targets": colIndex++,
	              "data": "actions",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    result = result	+ '<td><div class="tblminwidth-50 pull-left ml"><div class="pull-left"><a><i class="fa fa-pencil text-success" onclick="performanceManagement.editCompetence(\''+opt.competenceId+'\');"></i></a></div> <div class="pull-left mt2 ml5"><a><i class="fa fa-trash text-danger" onclick="performanceManagement.deleteCompetence(\''+opt.competenceId+'\');"></i></a></div></div></td>';
	            		return result;
	              }
		     }],
		     
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"aaSorting": [[1,'desc']],
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#competenceListTable").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#competenceDataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
   		    $('#competenceDataTableSearch').parent().find('p.jquery_form_error_message').remove();
   	    	var searchData = $('#competenceDataTableSearch').val();
   	    	
   	 	if(searchData.endsWith("-") || searchData.endsWith("\\") || searchData.endsWith("//") || searchData.endsWith("%")){
	    		$("#competenceDataTableSearch").after("<p class='jquery_form_error_message'><span><font color='red'>Enter valid Search Data</font></span>");
	    	}else{
	    		competenceDataTableList();
	    	}
   	    	$('#competenceDataTableSearch').val(searchData);
    	   }
    	});
}

</script>



<script>
function getTagCompetenceList(){
	 $("#comptenceClassId").removeClass();
	 $("#comptenceClassId").addClass("positionRelative reportHead competencePaddingRight clearfix");
	 $("#competenceListSearch").hide(); 
	 $("#competence").removeClass("current");
	    $("#tagCompetence").addClass("current");
	$("#competenceLoading").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	$.ajax({
		  type: "GET",
		    url: sourceleadUrl+"/getTagCompetenceList?",
		     success :function(response) {
		    	 
		    	 	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
    	    		    showError(response.responseMessage);
    	    		    $("#competenceLoading").removeAttr('style');
    	    		}
		        	else if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		
		        	  var dateFormat = '${SourceLeadWeb_orgLocations.dateFormat}';
		       		  if(dateFormat.match("yyyy-MM-dd")){
			   			  uploadedDateStr = 'YYYY-MM-DD';
			   		  }if(dateFormat.match("dd-MM-yyyy")){
			   			uploadedDateStr = 'DD-MM-YYYY'; 
			   		  }if(dateFormat.match("MM-dd-yyyy")){
			   			uploadedDateStr = 'MM-DD-YYYY'; 
			   		  }if(dateFormat.match("yyyy-dd-MM")){
			   			uploadedDateStr = 'YYYY-DD-MM'; 
			   		  }
		        		
		        	   var result = "";
 		        	   result = result + '   <div class="clearfix"></div>';
 			           result = result + '   </div>';
 			           result = result + '   <div class="">';
 			           result = result + '     <table id="tagCompetenceiesPegination" class="display" class="display mt10" cellspacing="0" width="100%">';
 			           result = result + '       <thead>';
 			           result = result + '         <tr>';
 			           result = result + '           <th>Roles</th>';
 			           result = result + '           <th>Competenceies</th>';
 			           result = result + '           <th>Weightage</th>';
 			           result = result + '           <th>Date<br>(<small class="custom-small">'+uploadedDateStr+'</small>)</th>';
 			           result = result + '           <th>Action</th>';
 			           result = result + '         </tr>';
 			           result = result + '       </thead>';
 			           result = result + '       <tbody>';
 			           
		        		if(response.data.length > 0){
		        			for (var i=0; i<response.data.length; i++) {
		    	        		var report = response.data[i];
		    	        		
		    			        		    
		    			        		    result = result + '<tr>';
		    			        		    result = result + '<td><div class="tbldata-dotdot-me" title="'+report.competenceRoleNames+'">'+report.competenceRoleNames+'</div></td>';
		    			        		    result = result + '<td><div class="tbldata-dotdot-me">'+report.competenceCount+'</div></td>';
		    			        		    result = result + '<td><div class="tbldata-dotdot-me">'+report.totalWeightage+'</div></td>';
		    			        		    result = result + '<td><div class="tbldata-dotdot-me">'+report.createdDate+'</div></td>';
		    			        		    result = result+'<td>';
		    			        		    result = result+'<div class="tblminwidth-50 pull-left ml">'; 
		    			        		    result = result	+'<div class="pull-left"><a><i class="fa fa-pencil text-success" onclick="performanceManagement.tagPerformanceCompetence(\''+report.tagCompetenceId+'\');"></i></a></div>';
		    			        		    result = result	+'<div class="pull-left mt2 ml5"><a><i class="fa fa-trash text-danger" onclick="performanceManagement.deleteCompetence(\''+report.tagCompetenceId+'\');"></i></a></div>';
		    			        		    result = result+'</div></td>';
		    			        		   
		    			        		    result = result + '</tr>';
		    					          
		    		  				
		    				}
		        			  
		        		}else{
		        			 result = result + '<tr class="odd"><td valign="top" colspan="4" class="dataTables_empty"></td></tr>';
			    		}
		        		
		        		   result = result + '</tbody>';
				           result = result + '</table>';
				           result = result + '</div>';
				           result = result + '</div>';
				           result = result + '<div id="choicepopUp"></div>';
				            $("#competenceLoading").removeAttr('style');
				            $("#competenceList").empty();
				            $("#competenceTaggingList").empty();
			    			$("#competenceTaggingList").append(result);
			    			tagCompetenceiesPegination();
		        		  
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
function tagCompetenceiesPegination(){ 
	$('#tagCompetenceiesPegination').DataTable( { 
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
	function getActions(){
		$(".action-pop").slideToggle(200);
		$(".newActPop").slideUp(200);
	}	
	$("html").click(function(){
		$(".action-pop").slideUp(200);
	});
	$(".action, .action-pop").click(function(e){
	 	e.stopPropagation();
	});
</script>
 
 