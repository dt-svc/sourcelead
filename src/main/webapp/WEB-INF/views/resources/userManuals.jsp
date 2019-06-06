<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link href="/resources/css/summernote-new.css" rel="stylesheet" type="text/css">
<script src="/resources/js/summernote.min.js"></script>

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
          <div class="pull-right brud-crum" id="level1Bred" style="display:block"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}" class="paddingLeft">Home</a> &raquo; Level-1 User Manuals </div>
          <div class="pull-right brud-crum" id="level2Bred" style="display:none"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}" class="paddingLeft">Home</a> &raquo; <a onclick="getUserManualList();" class="paddingLeft">Level-1</a> &raquo; Level-2 User Manuals </div>
          <div class="pull-right brud-crum" id="level3Bred" style="display:none"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}" class="paddingLeft">Home</a> &raquo;<a onclick="getUserManualList();" class="paddingLeft">Level-1</a> &raquo;<a id="level2cum" style="display:none" class="paddingLeft"></a> &raquo; Level-3 User Manuals </div>
       
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
	 document.getElementById("level1Bred").style.display="block";
	 document.getElementById("level2Bred").style.display="none";
	 document.getElementById("level3Bred").style.display="none";
	 
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
	result = result	+ '<a href="${SourceLeadWeb_sourceleadUrl}/addUserManual" class="btn mr mt10 anchorBlack"><i class="fa fa-plus mr"></i>Add user manual</a>';
	result = result	+ '</li>';
	
	result = result	+ '<li>';
	result = result	+ '<div class="remove_search">';
    result = result +" <input type='text' placeholder='Search' id='UserManualSearch'/>";
    result = result	+ '</div>';
    result = result	+ '</li>';
    result = result	+ '</ul>';
    
    result = result	+ '</div>';
    result = result	+ '<div class="project_tabs_inn_table project_tabs_inn_table_a widthAutoTD">';
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
		
		success : function(response) {
			if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
    		    showError(response.responseMessage);
    	  	} else if (response.response === "AWKWARD") {
    		    location.href = "/errorPage";
    		} else if (response.response === "SUCCESS") {
    			$("#userManualDelete").empty();
 			 	$(".project_inn_mask").fadeOut(200);
 			 	getUserManualList();
    		}else if (response.indexOf('Sign In') !== -1) {
    			location.replace(sourceleadUrl);
    		} else {
    		    showError("Operation failed, please try again");
    		}
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
	 document.getElementById("level1Bred").style.display="none";
	 document.getElementById("level2Bred").style.display="block";
	 document.getElementById("level3Bred").style.display="none";
	 level3Breadcrumbs(id)
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
	result = result	+ '<a href="${SourceLeadWeb_sourceleadUrl}/addUserManual" class="btn mr mt10 anchorBlack"><i class="fa fa-plus mr"></i>Add user manual</a>';
	result = result	+ '</li>';
	
	result = result	+ '<li>';
	result = result	+ '<div class="remove_search">';
    result = result +" <input type='text' placeholder='Search' id='UserManualTwoSearch'/>";
    result = result	+ '</div>';
    result = result	+ '</li>';
    result = result	+ '</ul>';
    
    result = result	+ '</div>';
    result = result	+ '<div class="project_tabs_inn_table project_tabs_inn_table_a widthAutoTD">';
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
		
		success : function(response) {
			
			if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
    		    showError(response.responseMessage);
    	  	} else if (response.response === "AWKWARD") {
    		    location.href = "/errorPage";
    		} else if (response.response === "SUCCESS") {
    			 $("#userManualDelete").empty();
 			 	$(".project_inn_mask").fadeOut(200);
 			 	getUserManualList();
    		}else if (response.indexOf('Sign In') !== -1) {
    			location.replace(sourceleadUrl);
    		} else {
    		    showError("Operation failed, please try again");
    		}
			
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
	 document.getElementById("level1Bred").style.display="none";
	 document.getElementById("level2Bred").style.display="none";
	 document.getElementById("level3Bred").style.display="block";
	 var searchValue = $("#UserManualThreeSearch").val();
	 searchValue = searchValue ? searchValue : "";
	 var url = sourceleadUrl	+"/getAllUseManualLevelThreeList?searchValue="+searchValue+"&id="+id;
		prepareLevelThreeList(url,id);
 }
 function level3Breadcrumbs(id){
	 document.getElementById("level2cum").style.display="inline-block";
	 var result = "";
	 result=result+'<a onclick="getAllLevelTwo('+id+');">Level-2</a>';
	 $("#level2cum").empty();  
	 $("#level2cum").append(result);  
 }
 </script>
 
 <script>
 function prepareLevelThreeList(url,id){
		 var result = "";
		result = result	+ '<div class="positionRelative table_head">';
		
		result = result	+ '<ul class="list-unstyled list-inline pull-right">';
		
		result = result	+ '<li>';
		result = result	+ '<a href="${SourceLeadWeb_sourceleadUrl}/addUserManual" class="btn mr mt10 anchorBlack"><i class="fa fa-plus mr"></i>Add user manual</a>';
		result = result	+ '</li>';
		
		result = result	+ '<li>';
		result = result	+ '<div class="remove_search">';
	    result = result +" <input type='text' placeholder='Search' id='UserManualThreeSearch'/>";
	    result = result	+ '</div>';
	    result = result	+ '</li>';
	    result = result	+ '</ul>';
	    
	    result = result	+ '</div>';
	    result = result	+ '<div class="project_tabs_inn_table project_tabs_inn_table_a widthAutoTD">';
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
		
		success : function(response) {
			
			if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
    		    showError(response.responseMessage);
    	  	} else if (response.response === "AWKWARD") {
    		    location.href = "/errorPage";
    		} else if (response.response === "SUCCESS") {
    			$("#userManualDelete").empty();
 			 	$(".project_inn_mask").fadeOut(200);
 			 	getUserManualList();
    		}else if (response.indexOf('Sign In') !== -1) {
    			location.replace(sourceleadUrl);
    		} else {
    		    showError("Operation failed, please try again");
    		}
			
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
			
			success : function(response) {
				if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
        		    showError(response.responseMessage);
        	  	} else if (response.response === "AWKWARD") {
        		    location.href = "/errorPage";
        		} else if (response.response === "SUCCESS") {
        			var title = "";
    				var description = "";
    				
    				/* for (var i = 0; i < response.response.length; i++) { 
                      	 var opt = response.response[i];  
                      	 alert(opt.title);
    				} */
    				if (response.object.title != null && response.object.title != '' && response.object.title != 'undefined') {
    						title = response.object.title;
    					} else {
    						title = "--";
    					}
    				
    			if (response.object.description != null && response.object.description != ''
    					&& response.object.description != 'undefined') {
    				description = response.object.description;
    			} else {
    				description = "----";
    			}
    			
    			if (response.object.updatedBy != null && response.object.updatedBy != '' && response.object.updatedBy != 'undefined') {
    				updatedBy = response.object.updatedBy;
    				} else {
    					updatedBy = "--";
    				}
    			if (response.object.updatedDate != null && response.object.updatedDate != '' && response.object.updatedDate != 'undefined') {
    				updatedDate = response.object.updatedDate;
    				} else {
    					updatedDate = "--";
    				}
    			if (response.object.createdBy != null && response.object.createdBy != '' && response.object.createdBy != 'undefined') {
    				createdBy = response.object.createdBy;
    				} else {
    					createdBy = "--";
    				}
    			if (response.object.createdDate != null && response.object.createdDate != '' && response.object.createdDate != 'undefined') {
    				createdDate = response.object.createdDate;
    				} else {
    					createdDate = "--";
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
    			result = result + '<span>'+createdBy+'</span>';
    			result = result + '<div class="clearfix"></div>';
    			result = result + '</div>';
    			
    			result = result + '<div class="clearfix"></div>';
    			
    			result = result	+ '<div class="project_details_cta" id="ProjectIdDiv">';
    			result = result + '<strong>CreatedDate:</strong>';
    			result = result + '<span>'+createdDate+'</span>';
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
				
        		else if (response.indexOf('Sign In') !== -1) {
        			location.replace(sourceleadUrl);
        		} else {
        		    showError("Operation failed, please try again");
        		}
			
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
			
			success : function(response) {
				
				if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
        		    showError(response.responseMessage);
        	  	} else if (response.response === "AWKWARD") {
        		    location.href = "/errorPage";
        		} else if (response.response === "SUCCESS") {
        			var title = "";
    				var description = "";
    				
    				
    				if (response.object.title != null && response.object.title != '' && response.object.title != 'undefined') {
    					title = response.object.title;
    					} else {
    				title = "--";
    			}
    			if (response.object.description != null && response.object.description != ''
    					&& response.object.description != 'undefined') {
    				description = response.object.description;
    			} else {
    				description = "----";
    			}
    			if (response.object.updatedBy != null && response.object.updatedBy != '' && response.object.updatedBy != 'undefined') {
    				updatedBy = response.object.updatedBy;
    				} else {
    					updatedBy = "--";
    				}
    			if (response.object.updatedDate != null && response.object.updatedDate != '' && response.object.updatedDate != 'undefined') {
    				updatedDate = response.object.updatedDate;
    				} else {
    					updatedDate = "--";
    				}
    			if (response.object.createdBy != null && response.object.createdBy != '' && response.object.createdBy != 'undefined') {
    				createdBy = response.object.createdBy;
    				} else {
    					createdBy = "--";
    				}
    			if (response.object.createdDate != null && response.object.createdDate != '' && response.object.createdDate != 'undefined') {
    				createdDate = response.object.createdDate;
    				} else {
    					createdDate = "--";
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
    			result = result + '<span>'+createdBy+'</span>';
    			result = result + '<div class="clearfix"></div>';
    			result = result + '</div>';
    			
    			result = result + '<div class="clearfix"></div>';
    			
    			result = result	+ '<div class="project_details_cta" id="ProjectIdDiv">';
    			result = result + '<strong>CreatedDate:</strong>';
    			result = result + '<span>'+createdDate+'</span>';
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
        		}else if (response.indexOf('Sign In') !== -1) {
        			location.replace(sourceleadUrl);
        		} else {
        		    showError("Operation failed, please try again");
        		}
				
				
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
			
			success : function(response) {
				
				if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
        		    showError(response.responseMessage);
        	  	} else if (response.response === "AWKWARD") {
        		    location.href = "/errorPage";
        		} else if (response.response === "SUCCESS") {
        			
        			
    				var title = "";
    				var description = "";
    				
    				if (response.object.title != null && response.object.title != '' && response.object.title != 'undefined') {
    					title = response.object.title;
    					} else {
    				title = "--";
    			}
    			if (response.object.description != null && response.object.description != '' && response.object.description != 'undefined') {
    				description = response.object.description;
    			} else {
    				description = "----";
    			}
    			if (response.object.updatedBy != null && response.object.updatedBy != '' && response.object.updatedBy != 'undefined') {
    				updatedBy = response.object.updatedBy;
    				} else {
    					updatedBy = "--";
    				}
    			if (response.object.updatedDate != null && response.object.updatedDate != '' && response.object.updatedDate != 'undefined') {
    				updatedDate = response.object.updatedDate;
    				} else {
    					updatedDate = "--";
    				}
    			if (response.object.createdBy != null && response.object.createdBy != '' && response.object.createdBy != 'undefined') {
    				createdBy = response.object.createdBy;
    				} else {
    					createdBy = "--";
    				}
    			if (response.object.createdDate != null && response.object.createdDate != '' && response.object.createdDate != 'undefined') {
    				createdDate = response.object.createdDate;
    				} else {
    					createdDate = "--";
    				}
    			if (response.object.urlName != null && response.object.urlName != '' && response.object.urlName != 'undefined') {
    				urlName = response.object.urlName;
    				} else {
    					urlName = "--";
    				}
    			if (response.object.publishOn != null && response.object.publishOn != '' && response.object.publishOn != 'undefined') {
    				publishOn = response.object.publishOn;
    				} else {
    					publishOn = "--";
    				}
    			
    			var result = "";
    			result = result	+ '<div class="project_tabs_inn_table clearfix mt10 mb">';
    			result = result + '<div class="clearfix">';
    			
    			result = result	+ '<div class="project_details_cta" id="ProjectIdDiv">';
    			result = result + '<strong>User Manual Title: </strong>';
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
    			
    			result = result	+ '<div class="project_details_cta" id="ProjectIdDiv">';
    			result = result + '<strong>Video URL Name: </strong>';
    			result = result + '<span>'+urlName+'</span>';
    			result = result + '<div class="clearfix"></div>';
    			result = result + '</div>';
    			
    			result = result + '<div class="clearfix"></div>';
    			
    			result = result	+ '<div class="project_details_cta" id="ProjectIdDiv">';
    			result = result + '<strong>CreatedBy:</strong>';
    			result = result + '<span>'+createdBy+'</span>';
    			result = result + '<div class="clearfix"></div>';
    			result = result + '</div>';
    			
    			result = result + '<div class="clearfix"></div>';
    			
    			result = result	+ '<div class="project_details_cta" id="ProjectIdDiv">';
    			result = result + '<strong>CreatedDate:</strong>';
    			result = result + '<span>'+createdDate+'</span>';
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
    			
    			result = result	+ '<div class="blogWrap" id="ProjectIdDiv">';
    			result = result + '<strong>Description:</strong>';
    			result = result + '<div class="clearfix"></div>';
    			result = result + '<div class="blogpreviewContent">'+description+'</div>';
    			result = result + '</div>';
    			
    			result = result + '<div class="clearfix"></div>';
    			
    			result = result	+ '<div class="project_details_cta" id="ProjectIdDiv">';
    			result = result + '<strong>attachments:</strong>';
    			if(undefined !== response.object.userManualAttachment){
    				for (var i=0; i<response.object.userManualAttachment.length; i++)
    				{
    				var att = response.object.userManualAttachment[i];
    				
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
        			
        		}else if (response.indexOf('Sign In') !== -1) {
        			location.replace(sourceleadUrl);
        		} else {
        		    showError("Operation failed, please try again");
        		}
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