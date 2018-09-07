<script>
  function getUserGroupList(){
		var searchValue = $("#dataTableSearch").val();
		searchValue = searchValue ? searchValue : "";
		var url = sourceleadUrl+"/getUserGroupList?searchValue="+searchValue;
		getUserGroupListData(url); 
  }
</script>

<script>
function getUserGroupListData(url){
	    
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
        
        $("#payrollFrequencyDiv").hide();
        $("#draftFrequencyDiv").hide();
        $("#overViewDiv").hide();
  	 	$("#reviewPayrollDiv").hide();
        $("#userGroupDiv").show();
        $("#userGroupDiv").empty();
        $("#userGroupDiv").append(result);
        userGroupPaginationFun(url); 
        $("#overViewDiv").hide();
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
							result = result + ' <td><div class="tblminwidth-75"><a href="'+sourceleadUrl+'/numberOfEmployeesInGroup?groupUUID='+opt.groupUUID+'">'+opt.groupId+'</a></div></td>';
// 							result = result +'<div class="tblminwidth-100"><a href="'+sourceleadUrl+'/viewPaySheetFrequencyData?approverStatus='+opt.approverStatus+'&paySheetFrequencyUUID='+opt.paySheetFrequencyuuid+'&payPeriodNum='+opt.payPeriodNum+'">'+opt.payPeriodNum+'</a></div>'; 
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
                		result = result + '<td><div class="tbldata-dotdot_50"><i class="fa fa-pencil mr cursorPointer" onclick="addbatches(\''+opt.groupUUID+'\');"></i><i class="glyphicon glyphicon-trash" onclick="deleteUserGroup(\''+opt.groupUUID+'\');"></i>';
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