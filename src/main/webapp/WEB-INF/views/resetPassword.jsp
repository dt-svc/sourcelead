<!DOCTYPE html>
<html lang="en">

<head>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>::. Reset Password .::</title>
    <link rel="icon" type="resources/image/png" href="resources/images/fav.png" />
	<link href="https://fonts.googleapis.com/css?family=Roboto:400,300italic,300,400italic,500,700,900" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css">
    <link href="resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="resources/css/register-style.css" rel="stylesheet">
     <link href="resources/css/jquery-ui.css" rel="stylesheet">
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@page session="true"%>
</head>

<body>
  
<div class="regis_bg">
	<div class="regis_logo"><img src="resources/images/logo-regis.png" alt=""/></div>
    <div class="regis_wrap">
    	<div class="signin_wrap reset_wrap_a" id="signin">
        	<h1>Reset Password</h1>
             <form:form class="login-form clearfix"  id="resetPassword_form"  name="resetPassword_form" method="post" >
            <div id=passwordDiv>
            <span>
            	<label>Verification Code</label>
                <input type="text" class="signin_fild" name="verificationCode" id="verificationCode" value="${verificationCode}" required/>
                <b></b>
            </span>
            <span>
            	<label>Password</label>
                <input type="Password" class="signin_fild" name="password"  value="${password}" id="password" onkeydown="$(this).clear();" required/>
                <b></b>
            </span>
            <span>
            	<label>Confirm Password</label>
                <input type="Password" class="signin_fild" value="" id="confirmPassword" onblur="$(this).checkPass();" required/>
                <b></b>
            </span>
            </div>
            <input type="submit" value="Save" id="submit_button" class="sign_btn" />
            </form:form>
        </div>
    </div>
</div>  
<input id="autocomplete"/>&nbsp;<input id="submit" type="submit"/>
  
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="http://code.jquery.com/ui/1.11.3/jquery-ui.js"></script>
    <script src="resources/js/bootstrap.min.js"></script>
    <script src="resources/js/jquery-fun.js"></script> 
    
    
</body>

<script>
$(function () {
     applyInputSlide();
});

function applyInputSlide() {
    $(".signin_wrap span input").each(function (i) {
        if ($(this).val().trim() !== "")
            $(this).parent(".signin_wrap span").find('label').addClass('test');
    });
    $(".signin_wrap span input").on('focus', function () {
        $(this).parent(".signin_wrap span").find('label').addClass('test');
    });
    $(".signin_wrap span input").on('blur', function () {
        if ($(this).val().trim() === "")
            $(this).parent(".signin_wrap span").find('label').removeClass('test');
    });	    
}
</script>


<script type="text/javascript">
(function($) {
	$.fn.checkPass = function() {   
		var pass1 = document.getElementById('password');  
	    var pass2 = document.getElementById('confirmPassword');
	    if(pass1.value == pass2.value){
	    	 $('input#confirmPassword').removeAttr( "class pass-exist pass-exist-errormsg");
			 $('#passwordDiv').find('span.jquery_form_error_message').remove(); 
			 document.getElementById("confirmPassword").setAttribute('class','valid signin_fild');
			 $("input#confirmPassword").removeAttr("style");
			 document.getElementById('register_button').disabled = false;
	    }
	    else{ 
		        $('#passwordDiv').find('span.jquery_form_error_message').remove();
		        document.getElementById("confirmPassword").setAttribute('class','error signin_fild');
		        $("input#confirmPassword").after("<span class='jquery_form_error_message'> <font color='red'>Passwords Not Matched</font></span>");
				document.getElementById("confirmPassword").setAttribute('pass-exist','yes');
				document.getElementById("confirmPassword").setAttribute('pass-exist-errorMsg', ' Passwords Not Matched'); 
				$("input#confirmPassword").removeAttr("style");
				document.getElementById('register_button').disabled = true;
	    }
   }
	
    $.fn.clear = function() {   
         $('#confirmMessage').empty(); 
    	 $('#confirmPassword').val('');
    	
	 return false;
    }
})(jQuery);
</script>



<script type="text/javascript">
        $('#resetPassword_form')
           .submit(function(e) {
        	    e.preventDefault(); 
        	    var form = $('#resetPassword_form').serialize();
        	    form = "testvalue=testvalue&"+form;
        		var con = JSON.stringify(form);
        		var sourceleadUrl ='${sourceleadUrl}';
        		 $("#submit_button").attr("disabled", "disabled");
        		 $( "#submit_button" ).addClass('button').val('Processing..');
                $.ajax({
            	      type: "GET",
            	      contentType : 'application/json; charset=utf-8',
            	      dataType : 'json',
                  	  url: sourceleadUrl+"/verifyResetPassword?${_csrf.parameterName}=${_csrf.token}",
                  	  data:con,
                    	success: function(result) {
                    		if((result == 'Invalid')){
	                   			 $('#passwordDiv').find('span.jquery_form_error_message').remove();
	             		         document.getElementById("verificationCode").setAttribute('class','error signin_fild');
	             		         $("input#verificationCode").after("<span class='jquery_form_error_message'> <font color='red'>Invalid Verification Code</font></span>");
	             				 document.getElementById("verificationCode").setAttribute('pass-exist','yes');
	             				 document.getElementById("verificationCode").setAttribute('pass-exist-errorMsg', ' Invalid Verification Code'); 
	             				 $("input#verificationCode").removeAttr("style");
    			        	}else{
								                   			     
    			        		location.replace(sourceleadUrl+"/login"); 
    			        	}
			        },  
                });
            });
</script>
   
</html>