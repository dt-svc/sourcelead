

<div class="project_tabs_inn_table vendor_wrap_inn_table datatable_filter">
<div id="associateBankData"></div>
</div>



<script>
function associateBankData(){  
	var userId = '${SourceLeadWeb_user.id}';
	$.ajax({
           type : "GET",
           url: sourceleadUrl+"/findAssociateBankDataByUserId?"+tokenParamName+"="+tokenValue+"&userId="+userId,    
            success :function(json) {
            	  var result = "";
     	    	 result += '<table id="associateBankDataList" class="display" cellspacing="0" width="100%" >';
     	         result += '<thead>';
     	         result += '  <tr>';
     	         result += '    <th>Account Type</th>';
     	         result += '    <th>Bank Name</th>';
     	         result += '    <th>Account Number</th>';
     	         result += '    <th>Routing Number</th>';
     	      	result += '    <th>Active</th>';
     	         result += '  </tr>';
     	         result += '</thead>';
     	         result += '<tbody>';
     	    	 for(i=0;i < json.length;i++){
     	    		 var report = json[i];
     	    		 result += '<tr>';
     	    		 if(report.accountType != null){
     	    			result += '<td>'+report.accountType+'</td>';
					 }else{
						 result += '<td>--</td>';
					 }
     	    		if(report.bankName != null){
     	    			result += '<td>'+report.bankName+'</td>';
					 }else{
						 result += '<td>--</td>';
					 }
     	    		if(report.accountNumber != null){
     	    			result += '<td>'+report.accountNumber+'</td>';
					 }else{
						 result += '<td>--</td>';
					 }
     	    		if(report.routingNumber != null){
     	    			result += '<td>'+report.routingNumber+'</td>';
					 }else{
						 result += '<td>--</td>';
					 }
     	    		 
     		        result += '<td>';
					result += '<label class="switch">';
					if(report.defaultAccount == true){
						result += '<input type="checkbox" checked class="toggle-button" id="defaultAccount'+report.id+'" name="defaultAccount" onclick="changeActiveAccount('+report.id+','+report.candidate.id+','+userId+')">';
					}else{
						result += '<input type="checkbox" class="toggle-button" id="defaultAccount'+report.id+'" name="defaultAccount" onclick="changeActiveAccount('+report.id+','+report.candidate.id+','+userId+')">';
					}
					result += '<div class="slider round"></div>';
					result += '</label>';
					result += '</div>';
					result += '</td>'; 
					     result += '  </tr>';
     	    	 }
     	    	
     	         result += '  </tbody>';
         		 result += '</table> ';
         		 
         		$("#associateBankDetails").hide();
                $('#associateBankData').show();
                $("#associateBankData").empty();
                $("#associateBankData").append(result);
                $("#loading").hide();
                assoBankDatapagination();
          },
            
       }); 
  } 
</script>

<script>

function assoBankDatapagination(){
		$('#associateBankDataList').DataTable({ 
			"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"searching": false,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": ""
	     	}
	    });
		$("#associateBankDataList").wrap("<div class='overflow_Auto require_wrap_table' style='width:100%;'></div>");  

	}
</script>

<!-- <script>
function associateBankData(){  
	var userId = '${SourceLeadWeb_user.id}';
	 $.ajax({
           type : "GET",
           url: sourceleadUrl+"/findAssociateBankDataByUserId?"+tokenParamName+"="+tokenValue+"&userId="+userId,    
            success :function(json) {
            	if(json != null && json !='' && json !='undefined'){
            		var result = "";
                	result = result + '<div id="editTest" class="col-md-12">';
//                 	result = result + '<a onclick="editAssociateBankDataFun('+userId+');" class="anchor-black pull-right" target="_self" title="Edit"><i class="fa fa-pencil paddingRight"></i>Edit</a>';
                	result = result + '</div>';
                	result = result + ' <div id="test" class="user_details_cta user_detailsCta">';
                	result = result + '<div class="project_details_cta"> <strong>Account Type:</strong>';
                	//result = result + ' <input type="text" value='+json.accountType+' placeholder="Project  class="project_detfield">';
                	if(json.accountType != null && json.accountType != '' && json.accountType != 'undefined')
                		result = result + '  <span>'+json.accountType+'</span>';
                	else
                		result = result + '  <span>---</span>';
                	result = result + '  <div class="clearfix"></div>';
                	result = result + ' </div>';
                	result = result + ' <div class="project_details_cta"> <strong>Bank Name:</strong>';
                	if(json.bankName != null && json.bankName != '' && json.bankName != 'undefined')
                		result = result + '  <span>'+json.bankName+'</span>';
                	else
                		result = result + '  <span>---</span>';
                    result = result + '<div class="clearfix"></div>';
                    result = result + '</div>';
                    result = result + '<div class="project_details_cta"> <strong>Account Number:</strong>';
                    if(json.accountNumber != null && json.accountNumber != '' && json.accountNumber != 'undefined')
                		result = result + '  <span>'+json.accountNumber+'</span>';
                	else
                		result = result + '  <span>---</span>';
                    result = result + '<div class="clearfix"></div>';
                    result = result + '</div>';
                    result = result + '<div class="project_details_cta"> <strong>Routing Number:</strong>';
                    if(json.routingNumber != null && json.routingNumber != '' && json.routingNumber != 'undefined')
                		result = result + '  <span>'+json.routingNumber+'</span>';
                	else
                		result = result + '  <span>---</span>';
                    result = result + '<div class="clearfix"></div>';
                    result = result + '</div>';
                    result = result + '</div>';
                    $("#associateBankDetails").hide();
                    $('#associateBankData').show();
                    $("#associateBankData").empty();
                    $("#associateBankData").append(result);
                    $("#loading").hide();
              }else{
           	   $("#myBankDetails").show();
            }
           },
            	
            
       });  
  } 
</script> -->
