<!DOCTYPE html>


<html>
<head>
	
	<meta http-equiv="Pragma" content="private">
	<meta http-equiv="Cache-Control" content="private">
	<meta http-equiv="Expires" content="0">
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    
	<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@page session="true"%>

	<c:if test="${staticTime == null || staticTime == 0 }">
	    <c:set var='staticTime' value="<%=new java.util.Date().getTime() %>" scope="application"></c:set>
	 </c:if>
 

    <title>::. Registration .::</title>
    <link rel="icon" type="/resources/image/png" href="/resources/images/fav.png" />
	<link href="https://fonts.googleapis.com/css?family=Roboto:400,300italic,300,400italic,500,700,900" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="/resources/css/chosen.min.css?staticTime=${staticTime}">
    <link href="/resources/css/font-awesome.min.css?staticTime=${staticTime}" rel="stylesheet">
    <link href="/resources/css/bootstrap.min.css?staticTime=${staticTime}" rel="stylesheet">
    <link href="/resources/css/register-style.css?staticTime=${staticTime}" rel="stylesheet">
     <link href="/resources/css/crowdsourcing-main.css?staticTime=${staticTime}" rel="stylesheet" type="text/css">
     
</head>

<script type="text/javascript">
window.onload = function () {
	getOptionalServiceList();	
 };
</script>

<body>
<div class="regis_bg">
  <div class="container">
      
    <div class="outsoucing-registaion">
        <div class="pull-left mb"><img src="/resources/images/SourceLead-logo.png" alt="Sourcelead Logo" class="img-responsive" width="300"></div>
      <div class="clearfix"></div>
      <ul class="nav nav-tabs">
          <li class="active"><a data-toggle="tab" href="javascript:void(0)"><i class="fa fa-retweet"></i>&nbsp;&nbsp;Reset Credentials</a></li>
      </ul>
        <div class="regis_wrap_styles regiswrapStyles">
        <c:if test="${not empty msg}">
	        <div id="selector">
		         <span><font color="green">${msg}</font></span>
			</div>
        </c:if>
        <div class="tab-content">
        <div id="js" class="tab-pane fade in active">
            
            <form:form class="login-form clearfix"  id="resetCredentials_form"  name="resetCredentials_form" method="post" > 
            <div class="row">
                <div class="form-group clearfix">
	                <div class="col-xs-12 col-md-6" id="userNameDiv">
	                    <i class="fa fa-user-circle"></i>
	                    <label>User Name <span class="text-danger">*</span></label>
	                    <input type="text" id="userName" name="userName" onblur="validateUserName();" value='${userName}' data-validation="required" field-name="User Name">
	                    <b></b>
                </div>
                </div>
                
                <div class="form-group clearfix">
	                <div class="col-xs-12 col-md-6">
	                    <i class="fa fa-unlock-alt"></i>
	                    <label>Password <span class="text-danger">*</span></label>
	                    <input type="password" id="password" name="password" filed-name="Password" field-name="Password" data-validation="required validate_length length6-100" >
	                    <b></b>
	                </div>
	                <div class="col-xs-12 col-md-6" id="passwordDiv">
	                    <i class="fa fa-unlock-alt"></i>
	                    <label>Confirm Password <span class="text-danger">*</span></label>
	                    <input type="password" id="confirmPassword" name="confirmPassword" field-name="Confirm Password" onblur="$(this).checkPass();" data-validation="required validate_length length6-100" >
	                    <b></b>
	                </div>
                </div>
            </div>
            
            <div id="optionalServicesId"></div>
            
            
            <div class="clearfix mt10">
                <div class="pull-right">
                    <button type="submit" id="submit_button"  class="btn btn-primary btn_styles mr" title="Save">
                    <i class="glyphicon glyphicon-floppy-save"></i>Save</button>
                </div>
            </div>
            </form:form>
        </div>
      </div>
        </div>
      </div>
  </div>
</div>
<script src="/resources/js/jquery-1.11.3.min.js?staticTime=${staticTime}"></script> 
<script src="/resources/js/bootstrap.min.js?staticTime=${staticTime}"></script>
<script src="/resources/js/chosen.jquery.min.js?staticTime=${staticTime}"></script> 
<script src="/resources/js/formvalidator.js?staticTime=${staticTime}"></script>
</body>



<script type="text/javascript">
function getOptionalServiceList(){
	var sourceleadUrl ='${sourceleadUrl}';
$.ajax({
      type: "POST",
  	  url: sourceleadUrl+"/ws/getOptionalValueList?${_csrf.parameterName}=${_csrf.token}",
    	success: function(data) {
		    var json = JSON.parse(data);
		    var result = '';		
		    result = result + '	<div class="form-group clearfix empCheckbox">';
		    result = result + ' <div class="row">';
		    result = result + ' <div class="clearfix">';   
		    result = result + ' <p class="ml"><strong>Would like to</strong></p>';
		    for (var i=0; i<json.length; i++)
			{ 
		    var report = json[i];
			result = result + ' <span class="col-md-4">';
			result = result + ' <input type="checkbox" value="'+report.id+'" name="Wouldliketo"><i>'+report.registerName+'</i>';
			result = result + ' </span>';
			}
		    result = result + ' </div>';
		    result = result + ' </div>';
		    result = result + '	</div>';
		    $("#optionalServicesId").empty();
		    $("#optionalServicesId").append(result);		
   },  
});   

}
</script>




<script type="text/javascript">
var validationSettings = {
        errorMessagePosition : 'element',
       borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd' 
   };  
         $('body').on('submit', '#resetCredentials_form', function(e){
        	  if ($(this).validate(false, validationSettings)){
        		e.preventDefault();
            	var frm = $('#resetCredentials_form').serializeFormJSON();
			    
			    var con = JSON.stringify(frm);
		        con = con.replace(/[{}]/g, "");
		        
		        var jsonData = encodeURIComponent(con);
			    
         		var sourceleadUrl ='${sourceleadUrl}';
         	 	$("#submit_button").attr("disabled", "disabled");
         	 	
         	 	var requirementUUID = '${requirementUUID}';
         	 	var referUUID = '${referUUID}';
         		
            	 $.ajax({
          	      type: "POST",
          	      contentType : 'application/json; charset=utf-8',
          	      dataType : 'json',
                	  url: sourceleadUrl+"/ws/resetCredentials?${_csrf.parameterName}=${_csrf.token}&json="+jsonData,
                			  
                  	success: function(result) {
			        	if((result == 'success')){
			        		if(requirementUUID != null && requirementUUID != '' && requirementUUID != 'undefined' && 
			        				referUUID != null && referUUID != '' && referUUID != 'undefined'){
			        			location.replace(sourceleadUrl+"/login?requirementUUID="+requirementUUID+"&referUUID="+referUUID);  
			        		}else{
			        			location.replace(sourceleadUrl+"/login");  
			        		}
			        		
			        	}
			        	
			        },  
              });   
                
            	    return true;
            }
            else{
            	return false;	
               }
            });
         $('#resetCredentials_form').showHelpOnFocus();
            
     
	</script>
	
	
	
	
	
<script type="text/javascript">
function validateUserName(){
	
    var userName = document.getElementById("userName").value;
    var sourceleadUrl ='${sourceleadUrl}';
		if(userName != null && userName != ''){
			$("#submit_button").attr("disabled", "disabled");
		$.ajax({
		   	type: "GET",
		       url: sourceleadUrl+"/validateCrowdSourcingUserName?${_csrf.parameterName}=${_csrf.token}&userName="+userName,
		       success: function(data) {
		    		if(data != null && data != ''){
					     if(data == 'Success'){
					    	 $("#submit_button").removeAttr("disabled");
							 $('input#userName').removeAttr( "record-exist record-exist-errormsg");
			        		 $('#userNameDiv').find('p.jquery_form_error_message').remove(); 
							 $( "#submit_button" ).removeAttr("style");
							 $( "#submit_button" ).click(function() {
							 if ($(this).validate(false, validationSettings)){
							 }
							});
					     } 
					     
				       	else{
				       		$('#userNameDiv').find('p.jquery_form_error_message').remove(); 
				    	    $("input#userName").after("<p class='jquery_form_error_message'>Invalid User Name</span>");
		    				$('input#userName').attr('record-exist','yes');
		    				$('input#userName').attr('record-exist-errorMsg','Invalid User Name'); 
				       	}
			       	}
		    	   
		      },
		       
		}); 
	   }
}
</script>


<script type="text/javascript">
function validateResetUserName(){
	
	var userName = document.getElementById("userName").value;
    var resetUserName = document.getElementById("resetUserName").value;
    var sourceleadUrl ='${sourceleadUrl}';
		if((resetUserName != null && resetUserName != '') && (userName != null && userName != '')){
			$("#submit_button").attr("disabled", "disabled");
		$.ajax({
		   	type: "GET",
		       url: sourceleadUrl+"/validateCrowdSourcingResetUserName?${_csrf.parameterName}=${_csrf.token}&userName="+userName+"&resetUserName="+resetUserName,
		       success: function(data) {
		    		if(data != null && data != ''){
					     if(data == 'Success'){
					    	 $("#submit_button").removeAttr("disabled");
							 $('input#resetUserName').removeAttr( "record-exist record-exist-errormsg");
			        		 $('#resetUserNameDiv').find('p.jquery_form_error_message').remove(); 
							 $( "#submit_button" ).removeAttr("style");
							 $( "#submit_button" ).click(function() {
							 if ($(this).validate(false, validationSettings)){
							 }
							});
					     } 
					     
				       	else{
				       		$('#resetUserNameDiv').find('p.jquery_form_error_message').remove(); 
				    	    $("input#resetUserName").after("<p class='jquery_form_error_message'>User Name Already Exists</span>");
		    				$('input#resetUserName').attr('record-exist','yes');
		    				$('input#resetUserName').attr('record-exist-errorMsg','User Name Already Exists'); 
				       	}
			       	}
		    	   
		      },
		       
		}); 
	   }
}
</script>

<script type="text/javascript">
(function($) {
	$.fn.checkPass = function() {   
		var pass1 = document.getElementById('password');  
	    var pass2 = document.getElementById('confirmPassword');
	    $("#submit_button").attr("disabled", "disabled");
	    
	    if(pass1.value == pass2.value){
			 $("#submit_button").removeAttr("disabled");
			 $('input#confirmPassword').removeAttr( "record-exist record-exist-errormsg");
    		 $('#passwordDiv').find('p.jquery_form_error_message').remove(); 
			 $( "#submit_button" ).removeAttr("style");
			 $( "#submit_button" ).click(function() {
			 if ($(this).validate(false, validationSettings)){
			 }
			});
	    }
	    else{ 
		    $('#passwordDiv').find('p.jquery_form_error_message').remove(); 
    	    $("input#confirmPassword").after("<p class='jquery_form_error_message'> Passwords not matched</span>");
			$('input#confirmPassword').attr('record-exist','yes');
			$('input#confirmPassword').attr('record-exist-errorMsg','Passwords Not Matched'); 
	    }
   }
	
    $.fn.clear = function() {   
    	 $('#confirmPassword').val('');
    	
	 return false;
    }
})(jQuery);
</script>

<script>
  $('#selector').delay(2000).fadeOut('slow');
</script> 


<script>
$(function () {
     applyInputSlide();
});

function applyInputSlide() {
    $(".regis_wrap_styles div input").each(function (i) {
        if ($(this).val().trim() !== "")
            $(this).parent(".regis_wrap_styles div").find('label').addClass('test');
    });
    $(".regis_wrap_styles div input").on('focus', function () {
        $(this).parent(".regis_wrap_styles div").find('label').addClass('test');
    });
    $(".regis_wrap_styles div input").on('blur', function () {
        if ($(this).val().trim() === "")
            $(this).parent(".regis_wrap_styles div").find('label').removeClass('test');
    });	    
}
</script>

<script type="text/javascript">
(function ($) {
   $.fn.serializeFormJSON = function () {

       var o = {};
       var a = this.serializeArray();
       $.each(a, function () {
           if (o[this.name]) {
               if (!o[this.name].push) {
                   o[this.name] = [o[this.name]];
               }
               o[this.name].push(this.value || '');
           } else {
               o[this.name] = this.value || '';
           }
       });
       return o;
   };
})(jQuery);
</script>
</html>