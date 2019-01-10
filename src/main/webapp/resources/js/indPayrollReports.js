function getIndPayGroupList(id,customEnable){
	$.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getPayGroupList?"+tokenParamName+"="+tokenValue                 , 
	        success: function(json) {
	        var output = "";
	        output = output + "<option value=''>select group</option>";
			for (var i=0; i<json.length; i++)
			{
				var opt = json[i];
				output = output + "<option value='" +opt.id+ "'>"+opt.groupName+"</option>";
			}
			if(customEnable == "Y"){
				output = output + "<option value='custom'>Custom</option>";
			}
			$("#"+id).empty();
			$("#"+id).append(output);
			$('#'+id).trigger("chosen:updated"); 
	        },
	    });
}


function getPayPeriodByGroupId(payGroupid,payPeriodId1,payPeriodId2,customDivId){
	var groupId = $("#"+payGroupid).val();
	$("#groupId").val(groupId);
	if(groupId != "custom"){
		$("#"+customDivId).hide();
		$("#"+payPeriodId1+"Div").show();
		$.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getIndPayPeriodByGroupId?"+tokenParamName+"="+tokenValue+"&groupId="+groupId               , 
	        success: function(json) {
	        var output = "";
	        output = output + "<option value=''>Pay Period</option>";
			for (var i=0; i<json.length; i++)
			{
				var opt = json[i];
				output = output + "<option value='"+opt.frequencyStartDateStr+"#"+opt.frequencyEndDateStr+"'>"+opt.frequencyStartDateStr+" To "+opt.frequencyEndDateStr+"</option>";
			}
			$("#"+payPeriodId1).empty();
			$("#"+payPeriodId1).append(output);
			$('#'+payPeriodId1).trigger("chosen:updated");
			if(payPeriodId2 != null && payPeriodId2 != '' && payPeriodId2 != 'undefined'){
				$("#"+payPeriodId2).empty();
				$("#"+payPeriodId2).append(output);
				$("#"+payPeriodId2).trigger("chosen:updated");
			}
			
	        },
	    });
	}else{
		$("#"+customDivId).show();
		$("#"+payPeriodId1+"Div").hide();
		 $("#freqEndDate").val("");
		 $("#freqStartDate").val("");
	}
}

function getDepartments(id){
	$.ajax({
    	type: "GET",
        url: sourceleadUrl+"/getDepartmentList?&departmentStatus=1", 
        success: function(json) {
        var output = "";
//        output = output + "<option value=''>Select Department</option>";
		for (var i=0; i<json.data.length; i++)
		{
			var opt = json.data[i];
			output = output + "<option value='" +opt.id+ "'>"+opt.departmentName+"</option>";
		}
		$("#"+id).empty();
		$("#"+id).append(output);
		$('#'+id).trigger("chosen:updated"); 
        },
    });	
}

function getFreqdates(freqSDId,fredEDId){
	var payPeriod = $("#payPeriod").val();
	var str = payPeriod.toString();
	var dates = str.split("#");
	$("#"+freqSDId).val(dates[0]);
	$("#"+fredEDId).val(dates[1]);
}


function getEmployeesBasedOnDepartment(id){
	
	var departmentId = $("#payrollDepartment").val();
	$.ajax({
    	type: "GET",
        url: sourceleadUrl+"/getEmployeesBasedOnDepartment?&departmentId="+departmentId, 
        success: function(json) {
        var output = "";
		for (var i=0; i<json.length; i++)
		{
			var opt = json[i];
			output = output + "<option value='" +opt.id+ "'>"+opt.fullName+"</option>";
		}
		$("#"+id).empty();
		$("#"+id).append(output);
		$('#'+id).trigger("chosen:updated"); 
        },
    });	
	
}
