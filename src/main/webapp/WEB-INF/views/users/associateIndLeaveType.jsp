<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 
 <!--  Tab-2 Banking Starts -->
 <form:form id="associate_leave_form" name="associate_leave_form" method="post">
            <div id="tab-6" class="tabsMenu_tabs_inn clearfix">
            <div class="project_tabs_inn_table">
            	<div class="clearfix"></div>
            	
					<div class="row">
						<div class="col-md-10">
						<div id="IndLeaveTypeDiv"></div>
						</div>
					</div>
            </div>
            
            <div class="clearfix footerstyelsNew">
			      <div class="col-md-6">
                 <input type="button" value="Previous" onclick="shiftLoanAndAdvTab();" class="btn btn-default">
			     </div>
			     <div class="col-md-6">
                   <div class="pull-right">
                    <input value="Skip" class="btn btn-default mr10" onclick="skipToSalaryTab();" type="button">
                    </div>
                  </div>
                </div>
            
          </div>
          </form:form>
          <!--  Tab-2 Banking Starts -->
          
<script>
	function shiftLoanAndAdvTab(){
		 var indLoanAndAdvancesDetails = document.getElementById("indLoanAndAdvancesDetails");
		 document.getElementById("indLoanAndAdvancesDetails").removeAttribute("class");
		 indLoanAndAdvancesDetails.href="#tab-4";
		 $("#indLoanAndAdvancesDetails").click();
	}
</script> 


<script>
function skipToSalaryTab(){
	var indSalaryDetails = document.getElementById("indSalaryDetails");
	 document.getElementById("indSalaryDetails").removeAttribute("class");
	 indSalaryDetails.href="#tab-5";
	 $("#indSalaryDetails").click();
}
</script>

          
 <script>
 function getIndAssociateLeaveTypeDetails(){
	 var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	 var userId = $("#associateId").val();   
	 $.ajax({
                type: "GET",
                url: sourceleadUrl+"/getAssociateBalanceLeavesByUserId?"+tokenParamName+"="+tokenValue+"&userId="+userId,    
                success: function(json) {
                	var data = json.data;
                	if(data.length >0 && data !=null && json !='' && data != 'undefined'){
                		var result = "";
                		result = result + '<div class="project_tabs_inn_table">';
                		result = result + '<div class="clearfix"></div>';
                		for(var i=0;i<data.length;i++){
                  		var opt = data[i];
                  		result = result + '<div class="clearfix mt mb">';
                 		result = result + '<div class="col-md-10">';
                 		result = result + '<div class="mb row">';
                 		result = result + '<input type="hidden" value='+opt.assoBalLeaveUUID+' />';
                 		result = result + '<label  class="col-md-3"><th>'+opt.leaveTypeName+'</th></label>';
                 		result = result + '<div class="col-md-2">';
                 		result = result + '<input type="text" value='+opt.noofLeaves+' id="'+i+'noofLeaves" name="'+i+'noofLeaves" class="form-control" onblur="editAssBalLeave(\''+opt.assoBalLeaveUUID+'\','+i+','+userId+')">';
                 		result = result + '<div id="'+i+'errorDiv" style="color:red;"></div>';
                 		result = result + '</div>';
                 		result = result + '<div class="col-md-1 paddingLeft">';
                 		result = result + '<i class="fa fa-pencil cursorPointer mt5" ></i>';
                 		result = result + '</div>';
                 		result = result + '</div>';
                 		result = result + '</div>';
                  	   } 
                		
                		result = result + '</div>';
                		result = result + '</div>';
                		
                		$("#IndLeaveTypeDiv").empty();
                       	$("#IndLeaveTypeDiv").append(result);
                	}
                },
            });
 }
 
 </script>
 
 <script>
function editAssBalLeave(assoBalLeaveUUID,rowId,userId){
	var totalLeaves = $("#"+rowId+"noofLeaves").val();
	if(totalLeaves != '' && totalLeaves != 'undefined' && totalLeaves != null){
		$("#"+rowId+"errorDiv").text("");
		$.ajax({
	        type : "PUT",
	         url: sourceleadUrl+"/editAssBalLeave?"+tokenParamName+"="+tokenValue+"&assoBalLeaveUUID="+assoBalLeaveUUID+"&totalLeaves="+totalLeaves,        
	         success :function(json) { },
	         }); 
	}else{
		$("#"+rowId+"errorDiv").text("Total Leave Required");
	}
 	 
}

</script>
 

