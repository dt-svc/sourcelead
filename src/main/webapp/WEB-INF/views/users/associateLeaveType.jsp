<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 
<!--  <script>
 $(document).ready(function(){
	 getAllLeaveType();
});

</script>  -->
  
 
 <!--  Tab-2 Banking Starts -->
 <form:form id="associate_leave_form" name="associate_leave_form" method="post">
 <div id="loading"></div>
            <div id="tab-2" class="tabsMenu_tabs_inn">
            <div class="project_tabs_inn_table">
            	<div class="clearfix"></div>
            	
					<div class="row">
						<div class="col-md-10">
						<div id="leaveDiv"></div>
							<!-- <div class="mb row">
								<label  class="col-md-3"><th>Sick Leave</th></label>
								<div class="col-md-2">
									<input type="text" value="" class="form-control">
								</div>
								<div class="col-md-1">
									<i class="fa fa-pencil cursorPointer mt5"></i>
								</div>
							</div>
							
							<div class="mb row">
								<label  class="col-md-3"><th>Casual Type</th></label>
								<div class="col-md-2">
									<input type="text" value="" class="form-control">
								</div>
								<div class="col-md-1">
									<i class="fa fa-pencil cursorPointer mt5"></i>
								</div>
							</div> -->
							
						</div>
					</div>
            </div>
            
            <div class="clearfix footerstyelsNew">
                 <div class="col-md-6">
                 <a href="javascript:void(0)" class="anchorBlack">Skip</a>
			     </div>
                  <div class="col-md-6">
                   <div class="pull-right">
                    <input value="Save &amp; Continue" class="btn btn-success btnSuccess" id="save_assoc_leave" type="button" onclick="saveAssociateleave();">
                    <input value="Cancel" class="btn btn-default ml10" type="button">
                    </div>
                  </div>
                </div>
            
          </div>
          </form:form>
          <!--  Tab-2 Banking Starts -->
          
          
 <script>
 function getAllLeaveType(){
	alert("Hi");
	 var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	 var userId = $("#associateId").val();   
	 $.ajax({
                type: "GET",
                url: sourceleadUrl+"/getPieChartForLeaveStatus?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&userId="+userId,    
                success: function(json) {
                	var result = "";
                	 for (var i = 0; i < json.length; i++) { 
               		  var opt = json[i];
               		result = result + '<div class="mb row">';
               		result = result + '<input type = "hidden" value='+opt.id+' id="'+i+'leaveTypeId" name="totalLeavesDto['+i+'].leaveTypeId" />';
               		result = result + '<label  class="col-md-3"><th>'+opt.name+'</th></label>';
               		result = result + '<div class="col-md-2">';
               		result = result + '<input type="text" value='+opt.noOfDays+' class="form-control" id="'+i+'noOfLeaves" name="totalLeavesDto['+i+'].noOfLeaves">';
               		result = result + '</div>';
               		result = result + '<div class="col-md-1">';
               		result = result + '<i class="fa fa-pencil cursorPointer mt5"></i>';
               		result = result + '</div>';
               		result = result + '</div>';
               	  }
                	$("#leaveDiv").empty();
                	$("#leaveDiv").append(result);
                },
            });
 }
 
 </script>
 
 
 
  <script type="text/javascript">
 function saveAssociateleave() {
	 var userId = $("#associateId").val();
			   var formData = $($('#associate_leave_form')[0]).serialize();
				$("#save_assoc_leave").attr("disabled", "disabled");
				$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	            $( "#save_assoc_leave" ).addClass('button').val('Processing..');
	            $.ajax({
					type : "POST",
	        	    data: formData,
					url : sourceleadUrl+"/addAssociateLeave?"+tokenParamName+"="+tokenValue+"&userId="+userId,
					success : function(result) {
						 if (result && result.response === "INVALID_DATA") {
			                    $("#save_assoc_leave").removeAttr("disabled");
			                    $("#save_assoc_leave").removeAttr('button').val('Save');
			                    showError(result.responseMessage);
			                } else if (result.response === "AWKWARD") {
			                    location.href = "/errorPage";
			                } else if (result.response === "SUCCESS") {
								/* var associateBanking = document.getElementById("associateBanking");
								document.getElementById("associateBanking").removeAttribute("class"); 
								associateBanking.href="#tab-2"; 
								$("#associateBanking").click(); */
			                	location.replace(sourceleadUrl+"/usersList");
			                } else if (result.indexOf('Sign In') !== -1) {
								location.replace(sourceleadUrl);
							} else {
			                    $("#save_assoc_leave").removeAttr("disabled");
			                    $("#save_assoc_leave").addClass('button').val('Save');
			                    showError("Operation failed, please try again");
			                }
						
					},
				});
	 
    }
 
 
 
</script>