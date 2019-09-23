<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<script>
function assocBalanceLeaveSummary(userId){
	$.ajax({
        type : "GET",
         url: sourceleadUrl+"/getAssociateBalanceLeavesByUserId?"+tokenParamName+"="+tokenValue+"&userId="+userId,        
         success :function(json) {  
        	    var data = json.data;
        	    var result = "";
          		result = result + '<div class="project_tabs_inn_table">';
        	    if(data != null && data != "" && data != "undefined"){
          		result = result + '<div class="clearfix"></div>';
          		for(var i=0;i<data.length;i++){
            		var opt = data[i];
            		result = result + '<div class="clearfix mt mb">';
           		result = result + '<div class="col-md-10">';
           		result = result + '<div class="mb row">';
           		result = result + '<input type="hidden" value='+opt.assoBalLeaveUUID+' />';
           		result = result + '<label  class="col-md-3"><th>'+opt.leaveTypeName+'</th></label>';
           		result = result + '<div class="col-md-2">';
           		result = result + '<input type="text" value='+opt.noofLeaves+' id="'+i+'noofLeaves" readonly name="'+i+'noofLeaves" class="form-control"  onblur="editAssBalLeave(this,\''+opt.assoBalLeaveUUID+'\','+i+','+userId+')">';
           		result = result + '<div id="'+i+'errorDiv" style="color:red;"></div>';
           		result = result + '</div>';
           		result = result + '<div class="col-md-1 paddingLeft">';
           		result = result + '<i class="fa fa-pencil cursorPointer mt5" onclick="editBalLeave(\''+i+'noofLeaves\');"></i>';
           		result = result + '</div>';
           		result = result + '<label  class="col-md-3 mt10"><th>Applied Leaves : '+opt.appliedLeaveCount+' / '+opt.balanceLeaveCounts+'</th></label>';
           		result = result + '</div>';
           		result = result + '</div>';
            	   } 
          		result = result + '</div>';
          		
        	  }else{
        		  result = result + '<div class="dataTables_empty"></div>'
        	  }
        	    result = result + '</div>';
          		$("#tab-17").empty(); 
          		$("#tab-18").empty();  
          	    $("#tab-19").empty();
          	    $("#tab-20").empty();
          	    $("#candidateSideDiv").empty();  
          	    $("#tab-29").empty();  
          	  	$("#tab-25").empty();
          	    $("#tab-29").append(result);
        	    
        	    
                 },
              }); 
}

function editBalLeave(typeId){
	$("#"+typeId+"").removeAttr("readonly");
}
</script>

<script>
function editAssBalLeave(ele, assoBalLeaveUUID,rowId,userId){
	var value = $(ele).val();
 	var splitValue = value.split(".");
 	var flag = true;
 	if(splitValue.length === 2 && splitValue[1].length > 2) {
		flag = false;
 	}
 	
	var totalLeaves = $("#"+rowId+"noofLeaves").val();
	var valid = !isNaN(totalLeaves) && flag;
	if(totalLeaves != "" && totalLeaves != null && totalLeaves != 'undefined' && valid ){
		$("#"+rowId+"errorDiv").text("");
		$.ajax({
	        type : "PUT",
	         url: sourceleadUrl+"/editAssBalLeave?"+tokenParamName+"="+tokenValue+"&assoBalLeaveUUID="+assoBalLeaveUUID+"&totalLeaves="+totalLeaves,        
	         success :function(json) {  
	        	 assocBalanceLeaveSummary(userId);
	        	 $('#'+rowId+'').attr("readonly",true);
	                 },
	              });  
	}else{
		if(totalLeaves == "" || totalLeaves == null || totalLeaves == 'undefined'){
			$("#"+rowId+"errorDiv").text("Total Leave Required");
		}else if(!flag){
			$("#"+rowId+"errorDiv").text("Only 2 Decimals are allowed");
		} else {
			$("#"+rowId+"errorDiv").text("Incorrect float value");
		}
		
	}
 	 
}

</script>


