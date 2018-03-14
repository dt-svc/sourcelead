<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link href="/resources/css/summernote-new.css" rel="stylesheet" type="text/css">
<script src="/resources/js/summernote.min.js"></script>

<style type="text/css">
.taskdesp .form-control {
height: auto !important;
}
.project_tabs_inn_table.project_tabs_inn_table_a table tr td:last-child
{
width: auto !important;
}
</style>

<script>
$(function(){
	getUserManualList();
});

</script>

<section class="content-wraper">
        <section class="content-header clearfix">
          <div class="pull-left">
            <h2>User Manuals List</h2>
          </div>
          <a href="${appPath}/addUserManual" class="pull-right btn btn-primary btn-sm mb">Add</a>
          <%-- <a href="${appPath}/applicantDeclineReports" title="Grid View" id="GView" class="btn btn-default " ><i class="fa fa-pie-chart"></i></a>
          --%> 
          <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; User Manuals </div>
        </section>
        
        <div class="content-area clearfix">
     	<div id="UserManualDivId" class="datatable_filter"> </div>
        </div>
        
 </section>

<script>
$('#summernote').summernote();
$('.note-editor').css({"min-height":"200px"});
$('.note-editable').css({"min-height":"200px"});
</script>
<script>
 function getUserManualList() {
	 var url = sourceleadUrl	+"/getAllUseManualLevel1List";
		prepareUserManualList(url);
	 
 }
 </script>
  <script>
 function prepareUserManualList(url){
	var result = "";
    result = result	+ '<div class="remove_search">';
    result = result +" <input type='text' placeholder='Search' id='UserManualSearch'/>";
	result = result	+ '</div><div class="project_tabs_inn_table project_tabs_inn_table_a">';
	result = result	+ '<div id="tasksLoadingImg"></div>';
	result = result + '<table id="UserManualPagination" class="display" cellspacing="0" width="100%">';
	result = result + '<thead>';
	result = result + '<tr">';
	result = result + '<th>Title</th>';
	result = result + '<th>Description</th>';
	result = result + '<th>Level</th>';
	//result = result + '<th>Status</th>';
	result = result + '<th>Action</th>';
	result = result + '</tr>';
	result = result + '</thead>';
	result = result + '</table>';
	result = result	+ '</div>';
	$("#UserManualDivId").empty();  
	$("#UserManualDivId").append(result);  
	userManualPagination(url);
}
function userManualPagination(url){
		var colIndex = 0;
		var levelName = "Level-1";
	    $('#UserManualPagination').DataTable( { 
		  "processing": true,
	      "serverSide": true,
	      "searching": false,
	      "ajax": $.fn.dataTable.pipeline( {
	          url: url,
	          method: 'POST',
	          pages: 10
	        }),
	        "columns": [
	            { "data": "title" },
	            { "data": "description"},
	            { "data": "level"},
	            { "data": "action" }
	        ],
	        "columnDefs": [ {
		    	  	"targets": colIndex++,
		    	  	"data": "title",
		    	  	"render": function(data, type, opt, meta) {
		    	  		var result = "";
		    	  		if(opt.title != null && opt.title != ""  && opt.title != "undefined"){
		    	  			manualtitle = opt.title + " - ";
						}else{
							manualtitle = "";
						}
		    	  		result = result	+ '<td><div class="tbldata-dotdot_50" onClick="getAllLevelTwo('+opt.id+');">'+manualtitle+ '</div></td>';
		    	  		return result;
		    	  	}
		      },{
		    	  "targets": colIndex++,
	              "data": "description",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	            	  var result = "";
	            	  if(opt.description != null && opt.description != ""  && opt.description != "undefined"){
		    	  			manualDesc = opt.description + " - ";
						}else{
							manualDesc = "";
						}
	            	  result = result	+ '<td><div class="tbldata-dotdot_80" title="'+manualDesc+'">'+manualDesc+ '</div></td>';
		              return result;
	              		
	              }
		          },{
	            "targets": colIndex++,
	            "data": "level",
	            "render": function ( data, type, opt, meta ) {
	            	
	            	var result = "";
	            	result = result	+ '<td><div class="tbldata-dotdot_50">level-1</div></td>';
	            	 return result;
	            }
	          }, /* {
	              "targets": colIndex++,
	              "data": "status",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	            	  if(opt.taskAssignedUsers != null && opt.taskAssignedUsers != "" && opt.taskAssignedUsers !="undefined"){
							taskAssignedUsers = opt.taskAssignedUsers;
						}else{
							taskAssignedUsers = "--"
						}
	            	  var result = "";
	            	  result = result	+ '<td><div class="tbldata-dotdot"><span >'+taskAssignedUsers+'</span ></div></td>';
	            	  return result;
	              }
	            }, */ {
	              "targets": colIndex++,
	              "data": "action",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	               
	              	var result = "";
	              	result = result	+ '<a class="glyphicon glyphicon-edit" title="Edit" href="'+sourceleadUrl+'/editUserManual?levelOneUUID='+opt.userManualUUID+'&levelName='+levelName+'" ></a> <a class="glyphicon glyphicon-trash text-danger ml" title="Delete" onclick="deleteUserManualOne('+opt.id+');"></a><a class="glyphicon glyphicon-trash text-danger ml" title="summary" onclick="userManualOneSummary('+opt.id+');"></a>';
	              	result = result	+ '</div></td>';
	  	              return result;
	              }
	        }],
		    	"scrollCollapse": false,
		    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
//	 	 //   	"aaSorting": [[1,'desc']],
		    	"bLengthChange": true,
		    	"bDestroy": true,
		    	"ordering": true,
		    	"oLanguage": {
		     		"sSearch": "",
		     		 "sZeroRecords": "", "sEmptyTable": "", 
		     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
		     }
	    } );
	    $("#UserManualPagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
	    $("input[type=search]").hide();
	    $("#UserManualSearch").keyup(function(e) {
	    	   if(e.which === 13) {
	    		  // getUserManualList();
	    	   }
	    	});
		
}


function deleteUserManualOne(userManualId){ 
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>"; 
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p style='text-align: center;'>Do you want to delete a task ?</p>";
	result = result + ' <div id="reasonForProjectDiv"/>';
	result = result +'		<b href="javascript:void(0)"><input id="confirm-id" type="button" value="Delete" onclick="deleteUserManualOne('+userManualId+')"><input class="u_can_color" type="button"  value="Cancel" onClick="cancelTermination();"></b>';
	result = result +"	</div>";
	$("#UserManualDelete").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}

function deleteUserManualOne(userManualId){
	var sourceleadUrl = '${sourceleadUrl}';
	$.ajax({
		type : "GET",
		url : sourceleadUrl+ "/deleteUserManualOne?${_csrf.parameterName}=${_csrf.token}&userManualId="+userManualId,
		
		success : function(json) {
			    $("#UserManualDelete").empty();
			 	$(".project_inn_mask").fadeOut(200);
			 	projectTask(projectId,projectStatus);
		}
	});
}

 </script>
 <script>
 function getAllLevelTwo(id){
	 var url = sourceleadUrl	+"/getAllUseManualLevelTwoList?id="+id;
		prepareLevelTwoList(url);
 }
 </script>
 
 <script>
 function prepareLevelTwoList(url){
	var result = "";
    result = result	+ '<div class="remove_search">';
    result = result +" <input type='text' placeholder='Search' id='UserManualTwoSearch'/>";
	result = result	+ '</div><div class="project_tabs_inn_table project_tabs_inn_table_a">';
	result = result	+ '<div id="tasksLoadingImg"></div>';
	result = result + '<table id="UserManualTwoPagination" class="display" cellspacing="0" width="100%">';
	result = result + '<thead>';
	result = result + '<tr">';
	result = result + '<th>Title</th>';
	result = result + '<th>Description</th>';
	result = result + '<th>Level</th>';
	//result = result + '<th>Status</th>';
	result = result + '<th>Action</th>';
	result = result + '</tr>';
	result = result + '</thead>';
	result = result + '</table>';
	result = result	+ '</div>';
	$("#UserManualDivId").empty();  
	$("#UserManualDivId").append(result);  
	userManualLevel2Pagination(url);
}
function userManualLevel2Pagination(url){
		var colIndex = 0;
		var levelName="Level-2"
	    $('#UserManualTwoPagination').DataTable( { 
		  "processing": true,
	      "serverSide": true,
	      "searching": false,
	      "ajax": $.fn.dataTable.pipeline( {
	          url: url,
	          method: 'POST',
	          pages: 10
	        }),
	        "columns": [
	            { "data": "title" },
	            { "data": "description"},
	            { "data": "level"},
	            { "data": "action" }
	        ],
	        "columnDefs": [ {
		    	  	"targets": colIndex++,
		    	  	"data": "title",
		    	  	"render": function(data, type, opt, meta) {
		    	  		var result = "";
		    	  		if(opt.title != null && opt.title != ""  && opt.title != "undefined"){
		    	  			manualtitle = opt.title + " - ";
						}else{
							manualtitle = "";
						}
		    	  		result = result	+ '<td><div class="tbldata-dotdot_50" onClick="getAllLevelThree('+opt.id+');">'+manualtitle+ '</div></td>';
		    	  		return result;
		    	  	}
		      },{
		    	  "targets": colIndex++,
	              "data": "description",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	            	  var result = "";
	            	  if(opt.description != null && opt.description != ""  && opt.description != "undefined"){
		    	  			manualDesc = opt.description + " - ";
						}else{
							manualDesc = "";
						}
	            	  result = result	+ '<td><div class="tbldata-dotdot_80" title="'+manualDesc+'">'+manualDesc+ '</div></td>';
		              return result;
	              		
	              }
		          },{
	            "targets": colIndex++,
	            "data": "level",
	            "render": function ( data, type, opt, meta ) {
	            	
	            	var result = "";
	            	result = result	+ '<td><div class="tbldata-dotdot_50">level-2</div></td>';
	            	 return result;
	            }
	          }, /* {
	              "targets": colIndex++,
	              "data": "status",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	            	  if(opt.taskAssignedUsers != null && opt.taskAssignedUsers != "" && opt.taskAssignedUsers !="undefined"){
							taskAssignedUsers = opt.taskAssignedUsers;
						}else{
							taskAssignedUsers = "--"
						}
	            	  var result = "";
	            	  result = result	+ '<td><div class="tbldata-dotdot"><span >'+taskAssignedUsers+'</span ></div></td>';
	            	  return result;
	              }
	            }, */ {
	              "targets": colIndex++,
	              "data": "action",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	               
	              	var result = "";
	              	result = result	+ '<a class="glyphicon glyphicon-edit" title="Edit" href="'+sourceleadUrl+'/editUserManual?levelOneUUID='+opt.userManualUUID+'&levelName='+levelName+'" ></a> <a class="glyphicon glyphicon-trash text-danger ml" title="Delete" onclick="deleteUserManualTwo('+opt.id+');"></a><a class="glyphicon glyphicon-edit" title="summary" onclick="userManualTwoSummary('+opt.id+');"></a>';
	              	result = result	+ '</div></td>';
	  	              return result;
	              }
	        }],
		    	"scrollCollapse": false,
		    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
//	 	 //   	"aaSorting": [[1,'desc']],
		    	"bLengthChange": true,
		    	"bDestroy": true,
		    	"ordering": true,
		    	"oLanguage": {
		     		"sSearch": "",
		     		 "sZeroRecords": "", "sEmptyTable": "", 
		     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
		     }
	    } );
	    $("#UserManualTwoPagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
	    $("input[type=search]").hide();
	    $("#UserManualTwoSearch").keyup(function(e) {
	    	   if(e.which === 13) {
	    		  // getUserManualList();
	    	   }
	    	});
		
}


function deleteUserManualTwo(userManualId){ 
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>"; 
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p style='text-align: center;'>Do you want to delete a task ?</p>";
	result = result + ' <div id="reasonForProjectDiv"/>';
	result = result +'		<b href="javascript:void(0)"><input id="confirm-id" type="button" value="Delete" onclick="deleteUserManualTwo('+userManualId+')"><input class="u_can_color" type="button"  value="Cancel" onClick="cancelTermination();"></b>';
	result = result +"	</div>";
	$("#UserManualDelete").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}

function deleteUserManualTwo(userManualId){
	var sourceleadUrl = '${sourceleadUrl}';
	$.ajax({
		type : "GET",
		url : sourceleadUrl+ "/deleteUserManualTwo?${_csrf.parameterName}=${_csrf.token}&userManualId="+userManualId,
		
		success : function(json) {
			    $("#UserManualDelete").empty();
			 	$(".project_inn_mask").fadeOut(200);
			 	projectTask(projectId,projectStatus);
		}
	});
}




 </script>
 
  <script>
 function getAllLevelThree(id){
	 var url = sourceleadUrl	+"/getAllUseManualLevelThreeList?id="+id;
		prepareLevelThreeList(url);
 }
 </script>
 
 <script>
 function prepareLevelThreeList(url){
	var result = "";
    result = result	+ '<div class="remove_search">';
    result = result +" <input type='text' placeholder='Search' id='UserManualThreeSearch'/>";
	result = result	+ '</div><div class="project_tabs_inn_table project_tabs_inn_table_a">';
	result = result	+ '<div id="tasksLoadingImg"></div>';
	result = result	+ '<div id="UserManualDelete"></div>';
	result = result + '<table id="UserManualThreePagination" class="display" cellspacing="0" width="100%">';
	result = result + '<thead>';
	result = result + '<tr">';
	result = result + '<th>Title</th>';
	result = result + '<th>Description</th>';
	result = result + '<th>Level</th>';
	//result = result + '<th>Status</th>';
	result = result + '<th>Action</th>';
	result = result + '</tr>';
	result = result + '</thead>';
	result = result + '</table>';
	result = result	+ '</div>';
	$("#UserManualDivId").empty();  
	$("#UserManualDivId").append(result);  
	UserManualThreePagination(url);
}
function UserManualThreePagination(url){
		var colIndex = 0;
		var levelName="Level-3"
	    $('#UserManualThreePagination').DataTable( { 
		  "processing": true,
	      "serverSide": true,
	      "searching": false,
	      "ajax": $.fn.dataTable.pipeline( {
	          url: url,
	          method: 'POST',
	          pages: 10
	        }),
	        "columns": [
	            { "data": "title" },
	            { "data": "description"},
	            { "data": "level"},
	            { "data": "action" }
	        ],
	        "columnDefs": [ {
		    	  	"targets": colIndex++,
		    	  	"data": "title",
		    	  	"render": function(data, type, opt, meta) {
		    	  		var result = "";
		    	  		if(opt.title != null && opt.title != ""  && opt.title != "undefined"){
		    	  			manualtitle = opt.title + " - ";
						}else{
							manualtitle = "";
						}
		    	  		result = result	+ '<td><div class="tbldata-dotdot_50" onClick="getAllLevelThree('+opt.id+');">'+manualtitle+ '</div></td>';
		    	  		return result;
		    	  	}
		      },{
		    	  "targets": colIndex++,
	              "data": "description",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	            	  var result = "";
	            	  if(opt.description != null && opt.description != ""  && opt.description != "undefined"){
		    	  			manualDesc = opt.description + " - ";
						}else{
							manualDesc = "";
						}
	            	  result = result	+ '<td><div class="tbldata-dotdot_80" title="'+manualDesc+'">'+manualDesc+ '</div></td>';
		              return result;
	              		
	              }
		          },{
	            "targets": colIndex++,
	            "data": "level",
	            "render": function ( data, type, opt, meta ) {
	            	
	            	var result = "";
	            	result = result	+ '<td><div class="tbldata-dotdot_50">level-3</div></td>';
	            	 return result;
	            }
	          }, /* {
	              "targets": colIndex++,
	              "data": "status",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	            	  if(opt.taskAssignedUsers != null && opt.taskAssignedUsers != "" && opt.taskAssignedUsers !="undefined"){
							taskAssignedUsers = opt.taskAssignedUsers;
						}else{
							taskAssignedUsers = "--"
						}
	            	  var result = "";
	            	  result = result	+ '<td><div class="tbldata-dotdot"><span >'+taskAssignedUsers+'</span ></div></td>';
	            	  return result;
	              }
	            }, */ {
	              "targets": colIndex++,
	              "data": "action",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	               
	              	var result = "";
	              	result = result	+ '<a class="glyphicon glyphicon-edit" title="Edit" href="'+sourceleadUrl+'/editUserManual?levelOneUUID='+opt.userManualUUID+'&levelName='+levelName+'" ></a> <a class="glyphicon glyphicon-trash text-danger ml" title="Delete" onclick="deleteUserManualThree('+opt.id+');"></a><a class="glyphicon glyphicon-edit" title="summary" onclick="userManualOneSummary('+opt.id+');"></a>';
	              	result = result	+ '</div></td>';
	  	              return result;
	              }
	        }],
		    	"scrollCollapse": false,
		    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
//	 	 //   	"aaSorting": [[1,'desc']],
		    	"bLengthChange": true,
		    	"bDestroy": true,
		    	"ordering": true,
		    	"oLanguage": {
		     		"sSearch": "",
		     		 "sZeroRecords": "", "sEmptyTable": "", 
		     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
		     }
	    } );
	    $("#UserManualThreePagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
	    $("input[type=search]").hide();
	    $("#UserManualThreeSearch").keyup(function(e) {
	    	   if(e.which === 13) {
	    		   getAllLevelThree();
	    	   }
	    	});
		
}


function deleteUserManualThree(userManualId){ 
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>"; 
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p style='text-align: center;'>Do you want to delete a task ?</p>";
	result = result + ' <div id="reasonForProjectDiv"/>';
	result = result +'		<b href="javascript:void(0)"><input id="confirm-id" type="button" value="Delete" onclick="deleteUserManualThree('+userManualId+')"><input class="u_can_color" type="button"  value="Cancel" onClick="cancelTermination();"></b>';
	result = result +"	</div>";
	$("#UserManualDelete").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}

function deleteUserManualThree(userManualId){
	var sourceleadUrl = '${sourceleadUrl}';
	$.ajax({
		type : "GET",
		url : sourceleadUrl+ "/deleteUserManualThree?${_csrf.parameterName}=${_csrf.token}&userManualId="+userManualId,
		
		success : function(json) {
			    $("#UserManualDelete").empty();
			 	$(".project_inn_mask").fadeOut(200);
			 	projectTask(projectId,projectStatus);
		}
	});
}



 </script>
 <script>
  
 function userManualOneSummary(userManualId){
	 alert("hello")
		var sourceleadUrl = '${sourceleadUrl}';
		$.ajax({
			type : "POST",
			url : sourceleadUrl+ "/userManualOneSummary?${_csrf.parameterName}=${_csrf.token}&userManualId="+userManualId,
			
			success : function(json) {
				alert(json)
				var title = "";
				var description = "";
				
				
				if (json.title != null && json.title != '' && json.title != 'undefined') {
					alert("hello")
					title = json.title;
					} else {
				title = "--";
			}
			if (json.description != null && json.description != ''
					&& json.description != 'undefined') {
				description = json.description;
			} else {
				description = "----";
			}
			
			var result = "";
			result = result	+ '<div class="project_tabs_inn_table">';
			result = result + '<div class="project_details">';
			
			result = result	+ '<div class="project_details_cta" id="ProjectIdDiv">';
			result = result + '<strong>User Manual Title:</strong>';
			result = result + '<span>'+title+'</span>';
			result = result + '<div class="clearfix"></div>';
			result = result + '</div>';
			
			result = result	+ '<div class="project_details_cta" id="ProjectIdDiv">';
			result = result + '<strong>Description:</strong>';
			result = result + '<span>'+description+'</span>';
			result = result + '<div class="clearfix"></div>';
			result = result + '</div>';
			
			result = result	+ '<div class="project_details_cta" id="ProjectIdDiv">';
			result = result + '<strong>CreatedBy:</strong>';
			result = result + '<span>'+json.createdBy+'</span>';
			result = result + '<div class="clearfix"></div>';
			result = result + '</div>';
			
			result = result	+ '<div class="project_details_cta" id="ProjectIdDiv">';
			result = result + '<strong>CreatedDate:</strong>';
			result = result + '<span>'+json.createdDate+'</span>';
			result = result + '<div class="clearfix"></div>';
			result = result + '</div>';
			
			result = result	+ '<div class="project_details_cta" id="ProjectIdDiv">';
			result = result + '<strong>UpdatedBy:</strong>';
			result = result + '<span>'+json.updatedBy+'</span>';
			result = result + '<div class="clearfix"></div>';
			result = result + '</div>';
			
			result = result	+ '<div class="project_details_cta" id="ProjectIdDiv">';
			result = result + '<strong>UpdatedDate:</strong>';
			result = result + '<span>'+json.updatedDate+'</span>';
			result = result + '<div class="clearfix"></div>';
			result = result + '</div>';
			
			result = result + '</div>';
			result = result + '</div>';
			
			$("#UserManualDivId").empty();  
			$("#UserManualDivId").append(result);  
			
			}
		});
	}
 
 </script>
 
  <script>
  
 function userManualTwoSummary(userManualId){
	 alert("hello")
		var sourceleadUrl = '${sourceleadUrl}';
		$.ajax({
			type : "POST",
			url : sourceleadUrl+ "/userManualTwoSummary?${_csrf.parameterName}=${_csrf.token}&userManualId="+userManualId,
			
			success : function(json) {
				alert(json)
				var title = "";
				var description = "";
				
				
				if (json.title != null && json.title != '' && json.title != 'undefined') {
					alert("hello")
					title = json.title;
					} else {
				title = "--";
			}
			if (json.description != null && json.description != ''
					&& json.description != 'undefined') {
				description = json.description;
			} else {
				description = "----";
			}
			
			var result = "";
			result = result	+ '<div class="project_tabs_inn_table">';
			result = result + '<div class="project_details">';
			
			result = result	+ '<div class="project_details_cta" id="ProjectIdDiv">';
			result = result + '<strong>User Manual Title:</strong>';
			result = result + '<span>'+title+'</span>';
			result = result + '<div class="clearfix"></div>';
			result = result + '</div>';
			
			result = result	+ '<div class="project_details_cta" id="ProjectIdDiv">';
			result = result + '<strong>Description:</strong>';
			result = result + '<span>'+description+'</span>';
			result = result + '<div class="clearfix"></div>';
			result = result + '</div>';
			
			result = result	+ '<div class="project_details_cta" id="ProjectIdDiv">';
			result = result + '<strong>CreatedBy:</strong>';
			result = result + '<span>'+json.createdBy+'</span>';
			result = result + '<div class="clearfix"></div>';
			result = result + '</div>';
			
			result = result	+ '<div class="project_details_cta" id="ProjectIdDiv">';
			result = result + '<strong>CreatedDate:</strong>';
			result = result + '<span>'+json.createdDate+'</span>';
			result = result + '<div class="clearfix"></div>';
			result = result + '</div>';
			
			result = result	+ '<div class="project_details_cta" id="ProjectIdDiv">';
			result = result + '<strong>UpdatedBy:</strong>';
			result = result + '<span>'+json.updatedBy+'</span>';
			result = result + '<div class="clearfix"></div>';
			result = result + '</div>';
			
			result = result	+ '<div class="project_details_cta" id="ProjectIdDiv">';
			result = result + '<strong>UpdatedDate:</strong>';
			result = result + '<span>'+json.updatedDate+'</span>';
			result = result + '<div class="clearfix"></div>';
			result = result + '</div>';
			
			result = result + '</div>';
			result = result + '</div>';
			
			$("#UserManualDivId").empty();  
			$("#UserManualDivId").append(result);  
			
			}
		});
	}
 
 </script>
 
  <script>
  
 function userManualOneSummary(userManualId){
	 alert("hello")
		var sourceleadUrl = '${sourceleadUrl}';
		$.ajax({
			type : "POST",
			url : sourceleadUrl+ "/userManualOneSummary?${_csrf.parameterName}=${_csrf.token}&userManualId="+userManualId,
			
			success : function(json) {
				alert(json)
				var title = "";
				var description = "";
				
				
				if (json.title != null && json.title != '' && json.title != 'undefined') {
					alert("hello")
					title = json.title;
					} else {
				title = "--";
			}
			if (json.description != null && json.description != ''
					&& json.description != 'undefined') {
				description = json.description;
			} else {
				description = "----";
			}
			
			var result = "";
			result = result	+ '<div class="project_tabs_inn_table">';
			result = result + '<div class="project_details">';
			
			result = result	+ '<div class="project_details_cta" id="ProjectIdDiv">';
			result = result + '<strong>User Manual Title:</strong>';
			result = result + '<span>'+title+'</span>';
			result = result + '<div class="clearfix"></div>';
			result = result + '</div>';
			
			result = result	+ '<div class="project_details_cta" id="ProjectIdDiv">';
			result = result + '<strong>Description:</strong>';
			result = result + '<span>'+description+'</span>';
			result = result + '<div class="clearfix"></div>';
			result = result + '</div>';
			
			result = result	+ '<div class="project_details_cta" id="ProjectIdDiv">';
			result = result + '<strong>CreatedBy:</strong>';
			result = result + '<span>'+json.createdBy+'</span>';
			result = result + '<div class="clearfix"></div>';
			result = result + '</div>';
			
			result = result	+ '<div class="project_details_cta" id="ProjectIdDiv">';
			result = result + '<strong>CreatedDate:</strong>';
			result = result + '<span>'+json.createdDate+'</span>';
			result = result + '<div class="clearfix"></div>';
			result = result + '</div>';
			
			result = result	+ '<div class="project_details_cta" id="ProjectIdDiv">';
			result = result + '<strong>UpdatedBy:</strong>';
			result = result + '<span>'+json.updatedBy+'</span>';
			result = result + '<div class="clearfix"></div>';
			result = result + '</div>';
			
			result = result	+ '<div class="project_details_cta" id="ProjectIdDiv">';
			result = result + '<strong>UpdatedDate:</strong>';
			result = result + '<span>'+json.updatedDate+'</span>';
			result = result + '<div class="clearfix"></div>';
			result = result + '</div>';
			
			result = result + '</div>';
			result = result + '</div>';
			
			$("#UserManualDivId").empty();  
			$("#UserManualDivId").append(result);  
			
			}
		});
	}
 
 </script>
 

<jsp:include page="../masterFooter.jsp" />