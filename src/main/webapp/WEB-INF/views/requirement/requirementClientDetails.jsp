<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>















<script type="text/javascript">
	function saveClient() {
		var validationSettings = {
			errorMessagePosition : 'element',
			borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd'
		};
		if ($('#clientDetails_form').validate(false, validationSettings)) {
			var frm = $('#clientDetails_form').serializeFormJSON();
			var orgLocationId = document.getElementById("orgLocationId").value;
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, "");
			  //It will escape all the special characters
	        var jsonData = encodeURIComponent(con);
			var sourceleadUrl = '${sourceleadUrl}';
			$("#save_client").attr("disabled", "disabled");
			/* $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;'); */
	 		$.ajax({
						type : "GET",
						url : sourceleadUrl
								+ "/saveClientFromProject?json="+jsonData+"&orgLocationId="+orgLocationId+"&responderType=Client",
						success : function(data) {
							var json = JSON.parse(data);
							var output = "";
							output = output
									+ "<option value=''>-- Select Client --</option>";
							for (var i = 0; i < json.length; i++) {
								var opt = json[i];
								
								output = output + "<option value='" +opt.id+","+opt.responderLocationId.id+ "'>"
										+ opt.orgName + "</option>";
							}
							$("#addClientId").empty();
							$("#addClientId").append(output);
							$('#addClientId').trigger("chosen:updated");
							$('#addClient').modal('hide');
							 

						},
					});
	 		$("#orgName").val('');
	 		$("#webSite").val('');
	 		$("#firstName").val('');
	 		$("#lastName").val('');
	 		$("#email").val('');
	 		$("#roleType option:selected").removeAttr("selected");
			return true;
		} else {
			return false;
		}
	}
	$('body').on('blur', '#clientDetails_form', function() {
		$("#clientDetails_form").showHelpOnFocus();
		$("#clientDetails_form").validateOnBlur(false, validationSettings);
	});
</script>

<script type="text/javascript">
function getContactList(local){
		var valueArray = local.value.split(",");
        clientContactList(valueArray[0],valueArray[1]);
}
</script>
​
<script type="text/javascript">
	function clientContactList(orgRelationID,clientLocationId) {
		var sourceleadUrl = '${sourceleadUrl}';
		$.ajax({
	    	type: "POST",
	    	url : sourceleadUrl+"/getOrgSpocsList?${_csrf.parameterName}=${_csrf.token}&orgRelationId="+orgRelationID, 
	        success: function(data) {
	        	var json = JSON.parse(data);
				var output = "";
				output = output
						+ "<option value=''>-- Select Client --</option>";
				for (var i = 0; i < json.length; i++) {
					var opt = json[i];
					
					output = output + "<option value='" +opt.id+","+opt.responderLocationId.id+ "'>"
							+opt.spocName+ "</option>";
				}
				$("#selectContact").empty();
				$("#selectContact").append(output);
				$('#selectContact').trigger("chosen:updated");
	        }
		});
	}
</script>


<script type="text/javascript">
	function saveClientContact() {
		var validationSettings = {
			errorMessagePosition : 'element',
			borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd'
		};
		if ($('#clientContacts_form').validate(false, validationSettings)) {
			var frm = $('#clientContacts_form').serializeFormJSON();
			var orgLocationId = document.getElementById("orgLocationId").value;
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, "");
			  //It will escape all the special characters
	        var jsonData = encodeURIComponent(con);
			var sourceleadUrl = '${sourceleadUrl}';
			$("#save_clientContact").attr("disabled", "disabled");
			/* $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;'); */
	 		$.ajax({
						type : "GET",
						url : sourceleadUrl
								+ "/saveClientFromProject?json="+jsonData+"&orgLocationId="+orgLocationId+"&responderType=Client",
						success : function(data) {
							var json = JSON.parse(data);
							var output = "";
							output = output
									+ "<option value=''>-- Select Client --</option>";
							for (var i = 0; i < json.length; i++) {
								var opt = json[i];
								
								output = output + "<option value='" +opt.id+","+opt.responderLocationId.id+ "'>"
										+ opt.orgName + "</option>";
							}
							$("#addClientId").empty();
							$("#addClientId").append(output);
							$('#addClientId').trigger("chosen:updated");
							$('#addClient').modal('hide');
							 

						},
					});
	 		$("#orgName").val('');
	 		$("#webSite").val('');
	 		$("#firstName").val('');
	 		$("#lastName").val('');
	 		$("#email").val('');
	 		$("#roleType option:selected").removeAttr("selected");
			return true;
		} else {
			return false;
		}
	}
	$('body').on('blur', '#clientContacts_form', function() {
		$("#clientContacts_form").showHelpOnFocus();
		$("#clientContacts_form").validateOnBlur(false, validationSettings);
	});
</script>


</body>
</html>