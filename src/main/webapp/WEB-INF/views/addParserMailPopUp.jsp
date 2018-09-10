<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- -email parser popup statrs here  -->
  <form id="email_parser_form"  name="email_parser_form" method="post">
<div class="clearfix">
		<div class="popupHead">Email Parsing<i class="closePop pull-right popClose" onclick="closeEmailNotifications();"></i></div>
			<div class="popupContent">		
			<div class="clearfix mb">
                <label class="noPad">Email <b class="valStar">*</b></label>
                <input class="form-control" value="" name="emailId" id="emailId"  field-name="Email Id" data-validation="required validate_Space validate_length length2-100 validate_email" placeholder="Enter email" type="text">
            </div>
          <div class="clearfix mb">
             <label class="noPad">Password <b class="valStar">*</b></label>
         <input class="form-control" value="" name="EmailPassword" id="passwordId"   field-name="Password" data-validation="required validate_Space" placeholder="Enter password" type="password">
          </div>
          
          <div class="clearfix mb">
             <label class="noPad">POP3 Mail Server name <b class="valStar">*</b></label>
            <input class="form-control" value="" id="serverName" name="serverName" data-validation="required validate_Space"  field-name="Receive Server" placeholder="e.g.secure.emailsrvr.com" type="text">
          </div>
          
          <div class="clearfix mb">
             <label class="noPad">Port <b class="valStar">*</b></label>
           <input class="form-control" value="" id="PortId" name="portNumber" data-validation="required validate_Space" field-name="Receive Port" placeholder="123" type="text">
          </div>
          
          <div class="clearfix mb">
             <label class="noPad">Encryption Type<b class="valStar">*</b></label>
            <select id="encryptId" name="encryptType" field-name="Encryption Type" data-validation="required" class="sel">
				<option value='' selected>Select</option>
				<option value='TLS'>TLS</option>
			</select>
          </div>
          
          <div class="clearfix mb">
             <label class="noPad">Type<b class="valStar">*</b></label>
           <select id="" name="usageType" field-name="Type" data-validation="required" class="sel">
				<option value=''  selected>Select</option>
				<option value='job_parsing' >Job Parsing</option>
			</select>
          </div>
          
           <div class="clearfix bg-info">
           <label class="modal-body"><strong class="text-danger paddingRight">Note:</strong>To configure the Gmail account, enable the access here : <a href="https://www.google.com/settings/security/lesssecureapps" target="_blank">Click here</a></label>
           </div>
          
			</div>
			<div class="popupFoot text-right">
				<input type="hidden" name="id" id="id" value="">
					<button type="button" id="save_title" class="slBtn md colorBtn" onclick="saveEmailParsers();">Save</button>
					<button type="button" class="slBtn md popClose" onclick="closeEmailNotifications();">Cancel</button>
			</div>
		</div>
	</form>	
		<script>
		
function saveEmailParsers(){
	if ($('#email_parser_form').validate(false, validationSettings)) {
		
			//$("#save").attr("disabled", "disabled");
			//$("#save_title").addClass('button').val('Processing..');
		//var formData = $($('#user_manuals_form')[0]).serialize();
		var formData = getFormData1("email_parser_form");
		$( "#save" ).addClass('button').val('Processing..');
		$("#ConfigLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:10000;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	    
		$.ajax({
			 type : "POST",
			 data: formData,
		      processData: false,
		      contentType:false, 
			  url : sourceleadUrl+"/saveEmailParser?"+tokenParamName+"="+tokenValue,
			  	success : function(result) {
			  		if(result=="success"){
			  			$("#ConfigLoading").removeAttr("style");
			  			alert("Mail configured Successfully!")
			  			closeEmailNotifications();
			  			getParserEmailList();
			  		}else if(result=="failure"){
			  			$("#ConfigLoading").removeAttr("style");
			  			closeEmailNotifications();
			  			alert("configured mail is wrong.please update the right email")
			  			//$("#msgConfigDiv").html("Configured Mail is wrong.Please update the correct Mail");
			  		}
		    		
			},
		});
		
	}else {
		return false;
	}

}

</script>
<script>
$(document).ready(function () {
	getParserEmailList();
});

</script>
<script>
function getParserEmailList(){
	$.ajax({
	   	type: "POST",
	    url: sourceleadUrl+"/getConfigParserMailList?${_csrf.parameterName}=${_csrf.token}", 	   
		    success: function(response) {
		    	var result = "";
		    	
		    	result  = result + '	 <div class="clearfix project_wrap_table project_tabs_inn_table_a">';
		    	result  = result + '	<table>								';
		    	result  = result + '<thead>									';
		    	result  = result + '<tr>									';
		    	result  = result + '<th>Email</th>							';
		    	result  = result + '<th>Type</th>				';
		    	result  = result + '<th>Actions</th>						';
		    	result  = result + '</tr>									';
		    	result  = result + '</thead>								';
		    	result  = result + '<tbody>									';
		    	 for (var i = 0; i < response.data.length; i++) { 
					 var mailrow = response.data[i];
				result  = result + '	 <tr>                                                                                                ';
     			result  = result + '	<td><div class="tbldata-dotdot">'+mailrow.emailId+'</div></td>                                ';
     			result  = result + '	<td><div class="tbldata-dotdot">'+mailrow.usageType+'</div></td>                                ';
     			result  = result + '	<td>                                                                                                 ';
     			result  = result + '	<div class="tbldata-dotdot">                                                                        ';
     			result  = result + '	<ul class="list-unstyled list-inline noMar">                                                               ';
     			result  = result + '	<li>                                                                                                 ';
				result  = result + '	<label class="switch" style="position:relative; top:5px;">                                                                          ';
				result  = result + '	<input onclick="changeDefaultemailConfig(this, '+mailrow.id+')" type="checkbox" '+(mailrow.status =="Y" ? 'checked' : '')+'/>                                                                    ';
				result  = result + '	 <span class="slider round"></span>                                                                  ';
				result  = result + '	</label>                                                                                             ';
     			result  = result + '	</li>                                                                                                ';
     			/* result  = result + '	<li><a class="fa fa-pencil pull-left ml" title="Edit" onclick="editEmailPopUp(\''+mailrow.email_UUID+'\');"></a> </li>';
     			result  = result + '	<li><a class="glyphicon glyphicon-trash text-danger" title="Delete" onclick="deleteFromMail('+mailrow.id+');"></a></li>'; */
     			result  = result + '	</ul>                                                                                                ';
     			result  = result + '	</div>                                                                                               ';
     			result  = result + '	</td>                                                                                                ';
     			result  = result + '	</tr>                                                                                                ';
		    	 }
		    	
		    	 $("#emailparsingList").empty();
	        	 $("#emailparsingList").append(result);
	        	 
		    },
     });
	
}
</script>