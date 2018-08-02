<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script src="/resources/js/jquery.maskedinput-1.3.min.js?staticTime=${staticTime}"></script>

<script>
$(document).ready(function(){
	hub();
// 	getUsersByDepartment();
});
</script>

<section class="content-wraper">
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2>Batches</h2>
    </div>
    <div class="pull-right brud-crum">Home &raquo; Batches</div>
  </section>
  <div class="timesheet_out">
    <div class="tablelist-wrap backgroundWhite">
      <div class="reportHead">
	<div class="pull-right">
		<ul class="list-unstyled list-inline clearfix">
		<li>
			<a onClick="addbatches()" class="newAdBtn mr5" id="createbatches">+ Create</a>
		</li>
			<li>
			<div class="dynamic_search mt10">
			<input type="text" placeholder="Search" id='dataTableSearch' />
			<i class="fa fa-search"></i>
			</div>              
			</li>
		</ul>
	</div>
</div>
<!-- <div class="dynamic_search">
			<input type="text" placeholder="Search" id='dataTableSearch' onclick="getAllDeductionType();"/>
			<i class="fa fa-search"></i>
		 </div> -->
      <div class="require_wrap_table datatable_filter">
      <div id="userGroupDiv"></div>
               
    <div class="clearfix"></div>
    </div>
  </div>
  <div id="popUp"></div>
      <div class="side_mask"><div class="proj_close"></div></div>
    <div class="clients_side_wrap">
      <div class="clients_side_wrap_inn">
			
		</div> 
   </div> 
   </div>
    </section>
    
    <!-- Content Area End -->
    
    <!--  Popups -->
    <div id="addUserGroupFormDiv"> </div>

<script>
function validateGroupId(){
	var groupId = $("#groupId").val();
	$.get(sourceleadUrl+"/validateUserGroupIdOrGroupName?"+tokenParamName+"="+tokenValue+"&groupId="+groupId,  function(result) {
			 if (result.response === "AWKWARD") {
                    location.href = "/errorPage";
                } else if (result.response === "SUCCESS") {
                	$('#groupIdDiv').find('p.jquery_form_error_message').remove();
                }  else {
					$('#groupIdDiv').find('p.jquery_form_error_message').remove();
      			  	$("#groupId").after("<p class='jquery_form_error_message'>"+groupId+" already exists..!!</span>");
                }
			
		});
}

function validateGroupName(){
	var groupName = $("#groupName").val();
	$.get(sourceleadUrl+"/validateUserGroupIdOrGroupName?"+tokenParamName+"="+tokenValue+"&groupName="+groupName,  function(result) {
			 if (result.response === "AWKWARD") {
                    location.href = "/errorPage";
                } else if (result.response === "SUCCESS") {
                	$('#groupNameDiv').find('p.jquery_form_error_message').remove();
                }  else {
					$('#groupNameDiv').find('p.jquery_form_error_message').remove();
      			  	$("#groupName").after("<p class='jquery_form_error_message'>"+groupName+" already exists..!!</span>");
                }
			
		});
}
</script>

<script type="text/javascript">
var config = {
	  '.chosen-select'           : {},
	  '.chosen-select-deselect'  : {allow_single_deselect:true},
	  '.chosen-select-no-single' : {disable_search_threshold:10},
	  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
	  '.chosen-select-width'     : {width:"95%"}
	}
	for (var selector in config) {
	  $(selector).chosen(config[selector]);
	}
	$('.chosen-select').each(function() {
		var $this = $(this);
		$this.next().css({'width': '100%'});

});
 		</script> 
<script>
// project side bar
$(document).ready(function() {
    $(".proj_show").click(function(){
		$(".clients_side_wrap").addClass("clients_side_wrap_active");
		$(".side_mask").fadeIn(200);
		$("body").css('overflow-y', 'hidden');
	});
    $(".proj_close, .side_mask").click(function(){
		$(".clients_side_wrap").removeClass("clients_side_wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
	});	
});
function closeBatch() {
	$(".popupWrap, .mask").fadeOut();
	 document.getElementById("add_user_group_form").reset(); 
	 $('#add_user_group_form').find('p.jquery_form_error_message').remove();
	 $("#userId").empty();
}
 
</script> 
<script>
function addbatches(groupUUID){
	if(groupUUID == null || groupUUID == '' || groupUUID == 'undefined'){
		groupUUID = "";
	}
	$.get(sourceleadUrl+"/addUserGroup?"+tokenParamName+"="+tokenValue+"&groupUUID="+groupUUID,  function(result) {
		$("#addUserGroupFormDiv").empty();
		$("#addUserGroupFormDiv").append(result);
// 		getUsersByDepartment();
		$(".popupWrap, .mask").fadeIn();
	});
}

function deleteUserGroup(groupUUID){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Do you want to delete this User Group</p>";
	result = result + ' <div id="userGroupDeleteDiv"/>';
	result = result +"		<b><input id='confirm-id' type='button' value='Ok' onclick='confirmDeleteUserGroup(\""+groupUUID+"\");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelDelete();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
	
function confirmDeleteUserGroup(groupUUID){	
	$.get(sourceleadUrl+"/deleteUserGroupByUUID?"+tokenParamName+"="+tokenValue+"&groupUUID="+groupUUID,  function(result) {
		 if (result.response === "AWKWARD") {
             location.href = "/errorPage";
         } else if (result.response === "SUCCESS") {
        	 $("#remove_user_pop, .project_inn_mask").fadeOut();
        	 hub();
         }
	});
}

function cancelDelete(){
	$("#remove_user_pop, .project_inn_mask").fadeOut();
}
</script>

<script>
 function getUsersByDepartment(){
	var department = $("#department").val();
	if(groupUUID == null || groupUUID == '' || groupUUID == 'undefined'){
		groupUUID = "";
	}
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/getUsersByDepartment?"+tokenParamName+"="+tokenValue+"&departmentId="+department                  , 
		        success: function(json) {
		       var output = "";
				for (var i=0; i<json.length; i++)
				{
				    var opt = json[i];
				    if(opt.lastName != null && opt.lastName != '' && opt.lastName != 'undefined'){
				    	output = output + "<option value='" +opt.userId+ "'>"+opt.firstName+" "+opt.lastName+" ("+opt.displayName+")</option>";
				    }else{
				    	output = output + "<option value='" +opt.userId+ "'>"+opt.firstName+" ("+opt.displayName+")</option>";
				    }
				}
				$("#assocByDept").empty();
				$("#assocByDept").append(output);
				$('#assocByDept').trigger("chosen:updated");
				$(".popupWrap, .mask").fadeIn();
		        },
		    });
	 
} 
</script>


<script>
  function hub(){
	     var sourceleadUrl ='${sourceleadUrl}';
		var searchValue = $("#dataTableSearch").val();
		searchValue = searchValue ? searchValue : "";
		var url = sourceleadUrl+"/getUserGroupList?searchValue="+searchValue;
		getUserGroupList(url); 
  }
</script>

<script>
function getUserGroupList(url){
	    $("#userGroupDiv").empty();
 	    var result = "";
 	    /* result = result +" <div class='modal-header'>";
 	    result = result +"</div>"; */
 	    result = result +"<table id='userGroupPagination' class='display' cellspacing='0' width='100%' >"; 
        result = result +"<thead>"; 
        result = result + '<tr>';
		result = result + '<th>Group Id</th>';
		result = result + '<th>Group Name</th>';
// 		result = result + '<th>No.Of Associates</th>';
		result = result + '<th>Pay Frequency</th>';
		result = result + '<th>Action</th>';
		result = result + '</tr>';
        result = result +"</thead>";
        result = result +"<tbody>"; 
        result = result +" </tbody>";
        result = result +"</table>";
        $("#userGroupDiv").append(result);
        userGroupPaginationFun(url); 
}

</script>

<script>
function userGroupPaginationFun(url){
	var colIndex = 0;
    $('#userGroupPagination').DataTable( { 
    	"processing": true,
        "serverSide": true,
        "searching": false,
        "ajax": $.fn.dataTable.pipeline( {
            url: url,
            method: 'POST',
            pages: 10
        }),
        "columns": [
            { "data": "groupId" },
            { "data": "groupName"},
//             { "data": "numOfAssociates"},
            { "data": "payFrequency" },
            { "data": "Action"}
        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "groupId",
	    	  	"sortable": true,
	    	  	"render": function(data, type, opt, meta) {
	    	  		        var result = "";
							result = result + ' <td><div class="tblminwidth-75"><span>'+opt.groupId+'</span></div></td>';
		                     return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "groupName",
              "sortable": true,
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
					result = result + '  <td><div class="tbldata-dotdot-verybig">'+opt.groupName+'</div></td>';
	              return result;
              		
              }
	          },/* {
            "targets": colIndex++,
            "data": "numOfAssociates",
            "sortable": false,
            "render": function ( data, type, opt, meta ) { 
          			  var result = "";
					 result = result + ' <td><div  class="tblminwidth-100">'+opt.numOfAssociates+'</div></td>';
            return result;
            }
          }, */ {
              "targets": colIndex++,
              "data": "payFrequency",
              "sortable": true,
              "render": function ( data, type, opt, meta ) {
            	  return ' <td><div  class="tblminwidth-100">'+opt.payFrequency+'</div></td>';
              }
            }, {
                "targets": colIndex++,
                "data": "Action",
                "sortable": false,
                "render": function ( data, type, opt, meta ) {
                		var result = "";
                		result = result + '<td><div class="tbldata-dotdot_50"><i class="fa fa-pencil mr" onclick="addbatches(\''+opt.groupUUID+'\');"></i><i class="glyphicon glyphicon-trash" onclick="deleteUserGroup(\''+opt.groupUUID+'\');"></i>';
                		result = result + '</div>';
                		result = result + '</td>';
    	              return result;
                }
          } ],
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"aaSorting": [[3,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#userGroupPagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#dataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   hub();
    	   }
    	});
}
</script>

<jsp:include page="../masterFooter.jsp" />

