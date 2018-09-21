function getPayGroupList(id){
	 $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getPayGroupList?"+tokenParamName+"="+tokenValue                 , 
	        success: function(json) {
	        var output = "";
	        output = output + "<option value=''>Select Group</option>";
			for (var i=0; i<json.length; i++)
			{
				var opt = json[i];
				output = output + "<option value='" +opt.id+ "'>"+opt.groupName+"</option>";
			}
			$("#"+id).empty();
			$("#"+id).append(output);
			$('#'+id).trigger("chosen:updated"); 
	        },
	    });
}


function getPayPeriodByGroupId(payGroupid,payPeriodId1,payPeriodId2){
	var groupId = $("#"+payGroupid).val();
	$("#groupId").val(groupId);
	$.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getPayPeriodByGroupId?"+tokenParamName+"="+tokenValue+"&groupId="+groupId               , 
	        success: function(json) {
	        var output = "";
	        output = output + "<option value=''>Select Pay Period</option>";
			for (var i=0; i<json.data.length; i++)
			{
				var opt = json.data[i];
				output = output + "<option value='" +opt.payPeriodNumber+ "'>"+opt.freqStartDate+" - "+opt.freqEndDate+"</option>";
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
}

function getDepartments(id){
	$.ajax({
    	type: "GET",
        url: sourceleadUrl+"/getDepartmentList?&departmentStatus=1", 
        success: function(json) {
        var output = "";
        output = output + "<option value=''>Select Department</option>";
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
