function getPayGroupList(id){
	$.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getPayGroupList?"+tokenParamName+"="+tokenValue                 , 
	        success: function(json) {
	        var output = "";
	        output = output + "<option value=''>select</option>";
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
	$.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getPayPeriodByGroupId?"+tokenParamName+"="+tokenValue+"&groupId="+groupId               , 
	        success: function(json) {
	        var output = "";
	        output = output + "<option value=''>Pay Period</option>";
			for (var i=0; i<json.data.length; i++)
			{
				var opt = json.data[i];
				output = output + "<option value='"+opt.freqStartDate+"#"+opt.freqEndDate+"'>"+opt.freqStartDate+" "+opt.freqEndDate+"</option>";
			}
			$("#"+payPeriodId1).empty();
			$("#"+payPeriodId1).append(output);
			$('#'+payPeriodId1).trigger("chosen:updated"); 
	        },
	    });	
}

function getDepartments(id){
	$.ajax({
    	type: "GET",
        url: sourceleadUrl+"/getDepartmentList?&departmentStatus=1", 
        success: function(json) {
        var output = "";
        output = output + "<option value=''>select</option>";
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
