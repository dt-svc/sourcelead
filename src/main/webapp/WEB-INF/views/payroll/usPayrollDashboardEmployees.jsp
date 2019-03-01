

<div id="popUp"></div>

<script>
  function getUserGroupList(fromType){
		var searchValue = $("#dataTableSearch").val();
		searchValue = searchValue ? searchValue : "";
		var url = sourceleadUrl+"/getUserGroupList?searchValue="+searchValue;
		getUserGroupListData(url,fromType); 
  }
</script>

<script>
function getUserGroupListData(url,fromType){
	    
 	    var result = "";
 	    /* result = result +" <div class='modal-header'>";
 	    result = result +"</div>"; */
 	    result = result +"<table id='userGroupPagination' class='display' cellspacing='0' width='100%' >"; 
        result = result +"<thead>"; 
        result = result + '<tr>';
		result = result + '<th>Group Id</th>';
		result = result + '<th>No. of Associates</th>';
		result = result + '<th>Group Name</th>';
		result = result + '<th>Pay Frequency</th>';
		result = result + '<th>Action</th>';
		result = result + '</tr>';
        result = result +"</thead>";
        result = result +"<tbody>"; 
        result = result +" </tbody>";
        result = result +"</table>";
        
        $("#payrollFrequencyDiv").hide();
        $("#draftFrequencyDiv").hide();
        $("#overViewDiv").hide();
  	 	$("#reviewPayrollDiv").hide();
  	 	$("#historySearchDiv").hide();
  	    $("#searchDivForEmployee").show();
  	    $("#dashboardDiv").hide();
        $("#userGroupDiv").show();
        $("#userGroupDiv").empty();
        $("#userGroupDiv").append(result);
        userGroupPaginationFun(url,fromType); 
        $("#overViewDiv").hide();
}

</script>

<script>
function userGroupPaginationFun(url,fromType){
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
            { "data": "numOfAssociates"},
            { "data": "groupName"},
            { "data": "payFrequency" },
            { "data": "Action"}
        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "groupId",
	    	  	"sortable": true,
	    	  	"render": function(data, type, opt, meta) {
	    	  		        var result = "";
							result = result + ' <td><div class="tblminwidth-75">'+opt.groupId+'</div></td>';
// 							result = result +'<div class="tblminwidth-100"><a href="'+sourceleadUrl+'/viewPaySheetFrequencyData?approverStatus='+opt.approverStatus+'&paySheetFrequencyUUID='+opt.paySheetFrequencyuuid+'&payPeriodNum='+opt.payPeriodNum+'">'+opt.payPeriodNum+'</a></div>'; 
							return result;
	    	  	}
	      },{
	            "targets": colIndex++,
	            "data": "numOfAssociates",
	            "sortable": false,
	            "render": function ( data, type, opt, meta ) { 
	          			  var result = "";
						 result = result + ' <td><div  class="tblminwidth-100"><a href="'+sourceleadUrl+'/numberOfEmployeesInGroup?groupUUID='+opt.groupUUID+'">'+opt.totalEmpCount+'</a></div></td>';
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
	          },   {
              "targets": colIndex++,
              "data": "payFrequency",
              "sortable": true,
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	     result = result + '  <td><div  class="tblminwidth-100">'+opt.payFrequency+'</div></td>';
            	  return result;
              }
            }, {
                "targets": colIndex++,
                "data": "Action",
                "sortable": false,
                "render": function ( data, type, opt, meta ) {
                		var result = "";
                		result = result + '<td><div class="tbldata-dotdot_50"><i class="fa fa-pencil mr cursorPointer" onclick="addbatches(\''+opt.groupUUID+'\',\''+fromType+'\');"></i>';
//commented by rakshith                 		<i class="glyphicon glyphicon-trash cursorPointer" onclick="deleteUserGroup(\''+opt.groupUUID+'\');"></i>
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
    		   getUserGroupList();
    	   }
    	});
}
</script>



<script>

function deleteUserGroup(groupUUID){
	$("#popUp").empty();
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
        	 $("#popUp").empty();
        	 getUserGroupList();
         }
	});
}

function cancelDelete(){
	$("#remove_user_pop, .project_inn_mask").fadeOut();
	$("#popUp").empty();
}
</script>

<div id="addUserGroupFormDiv123" class="addUserGroupPopup" style="display:none">
 <jsp:include page="addUserGroup.jsp" />
</div>

<script>
function addbatches(groupUUID,fromType){
	if(groupUUID == null || groupUUID == '' || groupUUID == 'undefined'){
		groupUUID = "";
	}
	$.get(sourceleadUrl+"/addUserGroup?"+tokenParamName+"="+tokenValue+"&groupUUID="+groupUUID+"&fromType="+fromType,  function(result) {
		$("#addUserGroupFormDiv123").empty();
		$("#addUserGroupFormDiv123").append(result);
// 		getUsersByDepartment();
	});
	$(".addUserGroupPopup").show();
}
</script>
