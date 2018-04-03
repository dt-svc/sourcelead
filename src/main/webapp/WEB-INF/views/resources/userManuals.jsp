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
          <div class="pull-left" id="usermanualList" style="display:block">
            <h2>User Manuals List</h2>
          </div>
          <div class="pull-left" id="level1summary" style="display:none">
            <h2>Level One Summary</h2>
          </div>
          <div class="pull-left" id="level2summary" style="display:none">
            <h2>Level Two Summary</h2>
          </div>
          <div class="pull-left" id="level3summary" style="display:none">
            <h2>Level Three Summary</h2>
          </div>
          <%-- <a href="${appPath}/addUserManual" class="pull-right btn btn-primary btn-sm mb">Add</a> --%>
          <%-- <a href="${appPath}/applicantDeclineReports" title="Grid View" id="GView" class="btn btn-default " ><i class="fa fa-pie-chart"></i></a>
          --%> 
          <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; User Manuals </div>
        </section>
        
        <div class="content-area clearfix">
     	<div id="UserManualDivId" class="datatable_filter"> </div>
     	 <div id="userManualDelete" class="datatable_filter"> </div>
     	
        </div>
        
 </section>
 

 

<script>
$('#summernote').summernote();
$('.note-editor').css({"min-height":"200px"});
$('.note-editable').css({"min-height":"200px"});
</script>


<!--  UserManualone dynamic list -->


<script>
 function getUserManualList() {
	 
	 var searchValue = $("#UserManualSearch").val();
	 searchValue = searchValue ? searchValue : "";
	 var url = sourceleadUrl	+"/getAllUseManualLevel1List?searchValue="+searchValue;
		prepareUserManualList(url);
	 
 }
 </script>
  <script>
 function prepareUserManualList(url){
	var result = "";
	result = result	+ '<div class="positionRelative table_head">';
	
	result = result	+ '<ul class="list-unstyled list-inline pull-right">';
	
	result = result	+ '<li>';
	result = result	+ '<a href="${appPath}/addUserManual" class="btn mr mt10 anchorBlack"><i class="fa fa-plus mr"></i>Add user manual</a>';
	result = result	+ '</li>';
	
	result = result	+ '<li>';
	result = result	+ '<div class="remove_search">';
    result = result +" <input type='text' placeholder='Search' id='UserManualSearch'/>";
    result = result	+ '</div>';
    result = result	+ '</li>';
    result = result	+ '</ul>';
    
    result = result	+ '</div>';
    result = result	+ '<div class="project_tabs_inn_table project_tabs_inn_table_a">';
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
		    	  			manualtitle = opt.title ;
						}else{
							manualtitle = "";
						}
		    	  		result = result	+ '<td><div class="tbldata-dotdot-me" title="'+manualtitle+'" onClick="getAllLevelTwo('+opt.id+');"><a href="javascript:void(0)">'+manualtitle+'</a></div></td>';
		    	  		return result;
		    	  	}
		      },{
		    	  "targets": colIndex++,
	              "data": "description",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	            	  var result = "";
	            	  if(opt.description != null && opt.description != ""  && opt.description != "undefined"){
	            		  result = result	+ '<td><div class="tbldata-dotdot-me" title="'+opt.description+'">'+opt.description+ '</div></td>';
						}else{
							result = result	+ '<td><div class="tbldata-dotdot-me" >---</div></td>';
						}
	            	  
		              return result;
	              		
	              }
		          },{
	            "targets": colIndex++,
	            "data": "level",
	            "sortable": false,
	            "render": function ( data, type, opt, meta ) {
	            	
	            	var result = "";
	            	result = result	+ '<td><div class="tbldata-dotdot-100">level-1</div></td>';
	            	 return result;
	            }
	          },  {
	              "targets": colIndex++,
	              "data": "action",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	               
	              	var result = "";
	              	result = result	+ '<a class="fa fa-pencil" title="Edit" href="'+sourceleadUrl+'/editUserManual?levelOneUUID='+opt.userManualUUID+'&levelName='+levelName+'" ></a> <a class="glyphicon glyphicon-trash text-danger ml" title="Delete" onclick="deleteUserManualOne('+opt.id+');"></a><a class="fa fa-eye text-blue ml" title="summary" onclick="userManualOneSummary('+opt.id+');"></a>';
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
 	    		   getUserManualList();
	    	   }
	    	});
		
}


function deleteUserManualOne(userManualId){ 
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>"; 
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p style='text-align: center;'>Reference with level One sub levels will be delete.Do you want to delete ?</p>";
	result = result + ' <div id="reasonForProjectDiv"/>';
	result = result +'		<b href="javascript:void(0)"><input id="confirm-id" type="button" value="Delete" onclick="deleteUserManualonee('+userManualId+')"><input class="u_can_color" type="button"  value="Cancel" onClick="cancelTermination();"></b>';
	result = result +"	</div>";
	$("#userManualDelete").append(result);
	$("#remove_user_pop").fadeIn(200);
	$(".project_inn_mask").fadeIn(200);
}


function deleteUserManualonee(userManualId){
	var sourceleadUrl = '${sourceleadUrl}';
	$.ajax({
		type : "GET",
		url : sourceleadUrl+ "/deleteUserManualOne?${_csrf.parameterName}=${_csrf.token}&userManualId="+userManualId,
		
		success : function(json) {
			    $("#userManualDelete").empty();
			 	$(".project_inn_mask").fadeOut(200);
			 	getUserManualList();
		}
	});
}

function cancelTermination(){
	$("#userManualDelete").empty();
	 $(".project_inn_mask").fadeOut(200);
}
 </script>
 
 <!--  UserManualTwo dynamic list -->
 
 <script>
 function getAllLevelTwo(id){
	 var searchValue = $("#UserManualTwoSearch").val();
	 searchValue = searchValue ? searchValue : "";
	 var url = sourceleadUrl	+"/getAllUseManualLevelTwoList?searchValue="+searchValue+"&id="+id;
		prepareLevelTwoList(url,id);
 }
 </script>
 
 <script>
 function prepareLevelTwoList(url,id){
	var result = "";
	
	result = result	+ '<div class="positionRelative table_head">';
	
	result = result	+ '<ul class="list-unstyled list-inline pull-right">';
	
	result = result	+ '<li>';
	result = result	+ '<a href="${appPath}/addUserManual" class="btn mr mt10 anchorBlack"><i class="fa fa-plus mr"></i>Add user manual</a>';
	result = result	+ '</li>';
	
	result = result	+ '<li>';
	result = result	+ '<div class="remove_search">';
    result = result +" <input type='text' placeholder='Search' id='UserManualTwoSearch'/>";
    result = result	+ '</div>';
    result = result	+ '</li>';
    result = result	+ '</ul>';
    
    result = result	+ '</div>';
    result = result	+ '<div class="project_tabs_inn_table project_tabs_inn_table_a">';
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
	userManualLevel2Pagination(url,id);
}
function userManualLevel2Pagination(url,id){
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
		    	  			manualtitle = opt.title ;
						}else{
							manualtitle = "";
						}
		    	  		result = result	+ '<td><div class="tbldata-dotdot-me" title="'+manualtitle+'" onClick="getAllLevelThree('+opt.id+');"><a href="javascript:void(0)">'+manualtitle+ '</a></div></td>';
		    	  		return result;
		    	  	}
		      },{
		    	  "targets": colIndex++,
	              "data": "description",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	            	  var result = "";
	            	  if(opt.description != null && opt.description != ""  && opt.description != "undefined"){
	            		  result = result	+ '<td><div class="tbldata-dotdot-me" title="'+opt.description+'">'+opt.description+ '</div></td>';
						}else{
							result = result	+ '<td><div class="tbldata-dotdot-me" >---</div></td>';
						}
		              return result;
	              		
	              }
		          },{
	            "targets": colIndex++,
	            "data": "level",
	            "sortable": false,
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
	              	result = result	+ '<a class="glyphicon glyphicon-edit" title="Edit" href="'+sourceleadUrl+'/editUserManual?levelOneUUID='+opt.userManualUUID+'&levelName='+levelName+'" ></a> <a class="glyphicon glyphicon-trash text-danger ml" title="Delete" onClick="deleteUserManualTwo('+opt.id+');"></a><a class="fa fa-eye text-blue ml" title="summary" onclick="userManualTwoSummary('+opt.id+');"></a>';
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
	    		   getAllLevelTwo(id);
	    	   }
	    	});
		
}


function deleteUserManualTwo(userManualId){ 
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>"; 
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p style='text-align: center;'>Reference with level two Sub level will be delete.Do you want to delete?</p>";
	result = result + ' <div id="reasonForProjectDiv"/>';
	result = result +'		<b href="javascript:void(0)"><input id="confirm-id" type="button" value="Delete" onclick="deleteUserManualTwoo('+userManualId+')"><input class="u_can_color" type="button"  value="Cancel" onClick="cancelTermination();"></b>';
	result = result +"	</div>";
	$("#userManualDelete").append(result);
	$("#remove_user_pop").slideDown(200);
}

function deleteUserManualTwoo(userManualId){
	var sourceleadUrl = '${sourceleadUrl}';
	$.ajax({
		type : "GET",
		url : sourceleadUrl+ "/deleteUserManualTwo?${_csrf.parameterName}=${_csrf.token}&userManualId="+userManualId,
		
		success : function(json) {
			    $("#userManualDelete").empty();
			 	$(".project_inn_mask").fadeOut(200);
			 	getUserManualList();
		}
	});
}

function cancelTermination(){
	$("#userManualDelete").empty();
	 $(".project_inn_mask").fadeOut(200);
}


 </script>
 
 <!--  UserManualThree dynamic list -->
 
  <script>
 function getAllLevelThree(id){
	 var searchValue = $("#UserManualThreeSearch").val();
	 searchValue = searchValue ? searchValue : "";
	 var url = sourceleadUrl	+"/getAllUseManualLevelThreeList?searchValue="+searchValue+"&id="+id;
		prepareLevelThreeList(url,id);
 }
 </script>
 
 <script>
 function prepareLevelThreeList(url,id){
	 
	 
	 
		 var result = "";
		result = result	+ '<div class="positionRelative table_head">';
		
		result = result	+ '<ul class="list-unstyled list-inline pull-right">';
		
		result = result	+ '<li>';
		result = result	+ '<a href="${appPath}/addUserManual" class="btn mr mt10 anchorBlack"><i class="fa fa-plus mr"></i>Add user manual</a>';
		result = result	+ '</li>';
		
		result = result	+ '<li>';
		result = result	+ '<div class="remove_search">';
	    result = result +" <input type='text' placeholder='Search' id='UserManualThreeSearch'/>";
	    result = result	+ '</div>';
	    result = result	+ '</li>';
	    result = result	+ '</ul>';
	    
	    result = result	+ '</div>';
	    result = result	+ '<div class="project_tabs_inn_table project_tabs_inn_table_a">';
		result = result	+ '<div id="tasksLoadingImg"></div>';
		
   
		result = result + '<table id="UserManualThreePagination" class="display" cellspacing="0" width="100%">';
		result = result + '<thead>';
		result = result + '<tr">';
		result = result + '<th>Title</th>';
		result = result + '<th>Description</th>';
		result = result + '<th>Level</th>';
		result = result + '<th>Status</th>';
		result = result + '<th>Action</th>';
		result = result + '</tr>';
		result = result + '</thead>';
		result = result + '</table>';
		result = result	+ '</div>';
		$("#UserManualDivId").empty();  
		$("#UserManualDivId").append(result);  
		UserManualThreePagination(url,id);
}
function UserManualThreePagination(url,id){
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
	            { "data": "status"},
	            { "data": "action" }
	        ],
	        "columnDefs": [ {
		    	  	"targets": colIndex++,
		    	  	"data": "title",
		    	  	"render": function(data, type, opt, meta) {
		    	  		var result = "";
		    	  		if(opt.title != null && opt.title != ""  && opt.title != "undefined"){
		    	  			manualtitle = opt.title ;
						}else{
							manualtitle = "";
						}
		    	  		result = result	+ '<td><div class="tbldata-dotdot-me" title="'+manualtitle+'">'+manualtitle+ '</div></td>';
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
	            	  result = result	+ '<td><div class="tbldata-dotdot-me" title="'+manualDesc+'">'+manualDesc+ '</div></td>';
		              return result;
	              		
	              }
		          },{
	            "targets": colIndex++,
	            "data": "level",
	            "sortable": false,
	            "render": function ( data, type, opt, meta ) {
	            	
	            	var result = "";
	            	result = result	+ '<td><div class="tbldata-dotdot_50">level-3</div></td>';
	            	 return result;
	            }
	          }, {
	              "targets": colIndex++,
	              "data": "status",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	            	  var result = "";
	            	  if(opt.status != null && opt.status != "" && opt.status !="undefined" && opt.status=="publish" ){
	            		  result = result	+ '<td><div class="tbldata-dotdot"><span ><a href="javascript:void(0)" onclick="userManualThreePublish('+opt.id+','+id+');">'+opt.status+'</span ></a></div></td>';
						}else{
							result = result	+ '<td><div class="tbldata-dotdot"><span >'+opt.status+'</span ></div></td>';
						}
	            	  
	            	  return result;
	              }
	            },  {
	              "targets": colIndex++,
	              "data": "action",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	               
	              	var result = "";
	              	result = result	+ '<a class="glyphicon glyphicon-edit" title="Edit" href="'+sourceleadUrl+'/editUserManual?levelOneUUID='+opt.userManualUUID+'&levelName='+levelName+'" ></a> <a class="glyphicon glyphicon-trash text-danger ml" title="Delete" onclick="deleteUserManualThree('+opt.id+');"></a> <a class="fa fa-eye text-blue ml" title="summary" onclick="userManualThreeSummary('+opt.id+');"></a>';
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
	    		   getAllLevelThree(id);
	    	   }
	    	});
		
}


function deleteUserManualThree(userManualId){ 
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>"; 
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p style='text-align: center;'>Do you want to delete this level?</p>";
	result = result + ' <div id="reasonForProjectDiv"/>';
	result = result +'		<b href="javascript:void(0)"><input id="confirm-id" type="button" value="Delete" onclick="deleteUserManualThre('+userManualId+')"><input class="u_can_color" type="button"  value="Cancel" onClick="cancelTermination();"></b>';
	result = result +"	</div>";
	$("#userManualDelete").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}

function deleteUserManualThre(userManualId){
	var sourceleadUrl = '${sourceleadUrl}';
	$.ajax({
		type : "GET",
		url : sourceleadUrl+ "/deleteUserManualThree?${_csrf.parameterName}=${_csrf.token}&userManualId="+userManualId,
		
		success : function(json) {
			    $("#userManualDelete").empty();
			 	$(".project_inn_mask").fadeOut(200);
			 	getUserManualList();
		}
	});
}

function cancelTermination(){
	$("#userManualDelete").empty();
	 $(".project_inn_mask").fadeOut(200);
}

 </script>
 
 <!--  UserManualone Summary -->
 
 <script>
  
 function userManualOneSummary(userManualId){
		var sourceleadUrl = '${sourceleadUrl}';
		$.ajax({
			type : "POST",
			url : sourceleadUrl+ "/userManualOneSummary?${_csrf.parameterName}=${_csrf.token}&userManualId="+userManualId,
			
			success : function(json) {
				var title = "";
				var description = "";
				
				
				if (json.title != null && json.title != '' && json.title != 'undefined') {
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
			
			if (json.updatedBy != null && json.updatedBy != '' && json.updatedBy != 'undefined') {
				updatedBy = json.updatedBy;
				} else {
					updatedBy = "--";
				}
			if (json.updatedDate != null && json.updatedDate != '' && json.updatedDate != 'undefined') {
				updatedDate = json.updatedDate;
				} else {
					updatedDate = "--";
				}
			
			var result = "";
			result = result	+ '<div class="project_tabs_inn_table">';
			result = result + '<div class="project_details">';
			
			result = result	+ '<div class="project_details_cta" id="ProjectIdDiv">';
			result = result + '<strong>User Manual Title:</strong>';
			result = result + '<span>'+title+'</span>';
			result = result + '<div class="clearfix"></div>';
			result = result + '</div>';
			
			result = result + '<div class="clearfix"></div>';
			
			result = result	+ '<div class="project_details_cta" id="ProjectIdDiv">';
			result = result + '<strong>CreatedBy:</strong>';
			result = result + '<span>'+json.createdBy+'</span>';
			result = result + '<div class="clearfix"></div>';
			result = result + '</div>';
			
			result = result + '<div class="clearfix"></div>';
			
			result = result	+ '<div class="project_details_cta" id="ProjectIdDiv">';
			result = result + '<strong>CreatedDate:</strong>';
			result = result + '<span>'+json.createdDate+'</span>';
			result = result + '<div class="clearfix"></div>';
			result = result + '</div>';
			
			result = result + '<div class="clearfix"></div>';
			
			result = result	+ '<div class="project_details_cta" id="ProjectIdDiv">';
			result = result + '<strong>UpdatedBy:</strong>';
			result = result + '<span>'+updatedBy+'</span>';
			result = result + '<div class="clearfix"></div>';
			result = result + '</div>';
			
			result = result + '<div class="clearfix"></div>';
			
			result = result	+ '<div class="project_details_cta" id="ProjectIdDiv">';
			result = result + '<strong>UpdatedDate:</strong>';
			result = result + '<span>'+updatedDate+'</span>';
			result = result + '<div class="clearfix"></div>';
			result = result + '</div>';
			
			result = result + '<div class="clearfix"></div>';
			
			result = result	+ '<div class="" id="ProjectIdDiv">';
			result = result + '<strong>Description:</strong>';
			result = result + '<span>'+description+'</span>';
			result = result + '<div class="clearfix"></div>';
			result = result + '</div>';
			
			result = result + '</div>';
			result = result + '</div>';
			
			$("#UserManualDivId").empty();  
			$("#UserManualDivId").append(result); 
			
			 document.getElementById("level1summary").style.display="block";
			 document.getElementById("usermanualList").style.display="none";
			 document.getElementById("level2summary").style.display="none";
			 document.getElementById("level3summary").style.display="none";
			
			}
		});
	}
 
 </script>
 
  <!--  UserManualTwo Summary -->
 
  <script>
  
 function userManualTwoSummary(userManualId){
		var sourceleadUrl = '${sourceleadUrl}';
		$.ajax({
			type : "POST",
			url : sourceleadUrl+ "/userManualTwoSummary?${_csrf.parameterName}=${_csrf.token}&userManualId="+userManualId,
			
			success : function(json) {
				var title = "";
				var description = "";
				
				
				if (json.title != null && json.title != '' && json.title != 'undefined') {
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
			if (json.updatedBy != null && json.updatedBy != '' && json.updatedBy != 'undefined') {
				updatedBy = json.updatedBy;
				} else {
					updatedBy = "--";
				}
			if (json.updatedDate != null && json.updatedDate != '' && json.updatedDate != 'undefined') {
				updatedDate = json.updatedBy;
				} else {
					updatedDate = "--";
				}
			
			
			var result = "";
			result = result	+ '<div class="project_tabs_inn_table">';
			result = result + '<div class="project_details">';
			
			result = result	+ '<div class="project_details_cta" id="ProjectIdDiv">';
			result = result + '<strong>User Manual Title:</strong>';
			result = result + '<span>'+title+'</span>';
			result = result + '<div class="clearfix"></div>';
			result = result + '</div>';
			
			result = result + '<div class="clearfix"></div>';
			
			result = result	+ '<div class="project_details_cta" id="ProjectIdDiv">';
			result = result + '<strong>CreatedBy:</strong>';
			result = result + '<span>'+json.createdBy+'</span>';
			result = result + '<div class="clearfix"></div>';
			result = result + '</div>';
			
			result = result + '<div class="clearfix"></div>';
			
			result = result	+ '<div class="project_details_cta" id="ProjectIdDiv">';
			result = result + '<strong>CreatedDate:</strong>';
			result = result + '<span>'+json.createdDate+'</span>';
			result = result + '<div class="clearfix"></div>';
			result = result + '</div>';
			
			result = result + '<div class="clearfix"></div>';
			
			result = result	+ '<div class="project_details_cta" id="ProjectIdDiv">';
			result = result + '<strong>UpdatedBy:</strong>';
			result = result + '<span>'+updatedBy+'</span>';
			result = result + '<div class="clearfix"></div>';
			result = result + '</div>';
			
			result = result + '<div class="clearfix"></div>';
			
			result = result	+ '<div class="project_details_cta" id="ProjectIdDiv">';
			result = result + '<strong>UpdatedDate:</strong>';
			result = result + '<span>'+updatedDate+'</span>';
			result = result + '<div class="clearfix"></div>';
			result = result + '</div>';
			
			result = result + '<div class="clearfix"></div>';
			
			result = result	+ '<div class="" id="ProjectIdDiv">';
			result = result + '<strong>Description:</strong>';
			result = result + '<span>'+description+'</span>';
			result = result + '<div class="clearfix"></div>';
			result = result + '</div>';
			
			
			
			$("#UserManualDivId").empty();  
			$("#UserManualDivId").append(result); 
			
			 document.getElementById("level3summary").style.display="none";
			 document.getElementById("level2summary").style.display="block";
			 document.getElementById("level1summary").style.display="none";
			 document.getElementById("usermanualList").style.display="none";
				
			
			}
		});
	}
 
 </script>
 
 
  <!--  UserManualThree Summary -->
 
  <script>
  
 function userManualThreeSummary(userManualId){
		var sourceleadUrl = '${sourceleadUrl}';
		$.ajax({
			type : "POST",
			url : sourceleadUrl+ "/userManualThreeSummary?${_csrf.parameterName}=${_csrf.token}&userManualId="+userManualId,
			
			success : function(json) {
				var title = "";
				var description = "";
				
				if (json.title != null && json.title != '' && json.title != 'undefined') {
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
			if (json.updatedBy != null && json.updatedBy != '' && json.updatedBy != 'undefined') {
				updatedBy = json.updatedBy;
				} else {
					updatedBy = "--";
				}
			if (json.updatedDate != null && json.updatedDate != '' && json.updatedDate != 'undefined') {
				updatedDate = json.updatedBy;
				} else {
					updatedDate = "--";
				}
			if (json.urlName != null && json.urlName != '' && json.urlName != 'undefined') {
				urlName = json.urlName;
				} else {
					urlName = "--";
				}
			if (json.publishOn != null && json.publishOn != '' && json.publishOn != 'undefined') {
				publishOn = json.publishOn;
				} else {
					publishOn = "--";
				}
			
			var result = "";
			result = result	+ '<div class="project_tabs_inn_table">';
			result = result + '<div class="project_details">';
			
			result = result	+ '<div class="project_details_cta" id="ProjectIdDiv">';
			result = result + '<strong>User Manual Title:</strong>';
			result = result + '<span>'+title+'</span>';
			result = result + '<div class="clearfix"></div>';
			result = result + '</div>';
			
			/* result = result + '<div class="clearfix"></div>';
			
			result = result	+ '<div class="project_details_cta" id="ProjectIdDiv">';
			result = result + '<strong>PublishedOn:</strong>';
			result = result + '<span>'+publishOn+'</span>';
			result = result + '<div class="clearfix"></div>';
			result = result + '</div>'; */
			
			result = result + '<div class="clearfix"></div>';
			
			result = result	+ '<div class="" id="ProjectIdDiv">';
			result = result + '<strong>Video URL Name:</strong>';
			result = result + '<span>'+urlName+'</span>';
			result = result + '<div class="clearfix"></div>';
			result = result + '</div>';
			
			result = result + '<div class="clearfix"></div>';
			
			result = result	+ '<div class="project_details_cta" id="ProjectIdDiv">';
			result = result + '<strong>CreatedBy:</strong>';
			result = result + '<span>'+json.createdBy+'</span>';
			result = result + '<div class="clearfix"></div>';
			result = result + '</div>';
			
			result = result + '<div class="clearfix"></div>';
			
			result = result	+ '<div class="project_details_cta" id="ProjectIdDiv">';
			result = result + '<strong>CreatedDate:</strong>';
			result = result + '<span>'+json.createdDate+'</span>';
			result = result + '<div class="clearfix"></div>';
			result = result + '</div>';
			
			result = result + '<div class="clearfix"></div>';
			
			result = result	+ '<div class="project_details_cta" id="ProjectIdDiv">';
			result = result + '<strong>UpdatedBy:</strong>';
			result = result + '<span>'+updatedBy+'</span>';
			result = result + '<div class="clearfix"></div>';
			result = result + '</div>';
			
			result = result + '<div class="clearfix"></div>';
			
			result = result	+ '<div class="project_details_cta" id="ProjectIdDiv">';
			result = result + '<strong>UpdatedDate:</strong>';
			result = result + '<span>'+updatedDate+'</span>';
			result = result + '<div class="clearfix"></div>';
			result = result + '</div>';
			
			result = result + '<div class="clearfix"></div>';
			
			result = result	+ '<div class="" id="ProjectIdDiv">';
			result = result + '<strong>Description:</strong>';
			result = result + '<span>'+description+'</span>';
			result = result + '<div class="clearfix"></div>';
			result = result + '</div>';
			
			result = result + '<div class="clearfix"></div>';
			
			result = result	+ '<div class="project_details_cta" id="ProjectIdDiv">';
			result = result + '<strong>attachments:</strong>';
			if(undefined !== json.userManualAttachment){
				for (var i=0; i<json.userManualAttachment.length; i++)
				{
				var att = json.userManualAttachment[i];
				
				result = result+' <div class="clearfix"></div>';
				result = result+' <div class="clearfix mt">';
				result = result+'<div>';
				result = result+' <div class="timesheet_week_comm invoiceDocs invoiceDocsInput">';
				result = result+'<span class="pull-left">';
				result = result+'<strong>';
				result = result+'<i class="downloadIcon-new" style="position:absolute; top:13px; font-size:15px; left:10px;"><a download class="glyphicon glyphicon-save" href="'+att.fileUrl+'" title="download"></a></i>';
				result = result+'<input type="button" title="'+att.fileName+'" value="'+att.fileName+'" style="cursor:initial !important">';
				result = result+'<a href="javascript:void(0)" class="fa fa-eye pull-left paddingLeft ml5 mt anchorBlack" title="view attachment" onclick="viewFile(\''+att.fileUrl+'\',\''+att.fileName+'\');"></a>';
				
				/* result = result+'	            <td><div class="tblminwidth-125"><a download class="glyphicon glyphicon-download-alt" href=\"'+opt.fileURL+'\" title="Download"></a>';
           		result = result+' <a class="fa fa-eye" href="javascript:void(0)" onClick="viewFile(\''+opt.fileURL+'\',\''+opt.fileName+'\',\''+opt.fileContentType+'\');"title="preview"></a>';
				 */
           		result = result+'</strong>';
				result = result+'</span>';
				result = result+' </div>';
				result = result+'</div>';
				result = result+' </div>';

				}
			}
			  
			result = result + '<div class="clearfix"></div>';
			result = result + '</div>';
			
			
			
			result = result + '</div>';
			result = result + '</div>';
			
			$("#UserManualDivId").empty();  
			$("#UserManualDivId").append(result); 
			document.getElementById("level3summary").style.display="block";
			 document.getElementById("level2summary").style.display="none";
			document.getElementById("level1summary").style.display="none";
			 document.getElementById("usermanualList").style.display="none";
			 
			
			}
		});
	}
 
 </script>
 
 <script>
 function userManualThreePublish(id,userManaualTwoId){
	 var sourceleadUrl = '${sourceleadUrl}';
		$.ajax({
			type : "POST",
			url : sourceleadUrl+ "/publishUserManualThree?${_csrf.parameterName}=${_csrf.token}&userManualId="+id,
			
			success : function(data) {
				getAllLevelThree(userManaualTwoId);
			}
		});	
	 
 }
 
 </script>
 

<jsp:include page="../masterFooter.jsp" />