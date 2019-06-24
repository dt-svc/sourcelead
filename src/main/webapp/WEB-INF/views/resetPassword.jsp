<!DOCTYPE html>
<html lang="en">

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
   
    <title>::. Reset Password .::</title>
    <link rel="icon" type="/resources/image/png" href="/resources/images/fav.png" />
	<link href="https://fonts.googleapis.com/css?family=Roboto:400,300italic,300,400italic,500,700,900" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css">
    <link href="/resources/css/bootstrap.min.css" rel="stylesheet?staticTime=${staticTime}">
    <link href="/resources/css/register-style.css?staticTime=${staticTime}" rel="stylesheet">
     <link href="/resources/css/jquery-ui.css?staticTime=${staticTime}" rel="stylesheet">
     
     
     
</head>

<body>
  
  
  
<div class="regis_bg">
	<div class="regis_logo"><img src="/resources/images/SourceLead-logo.png" alt=""/></div>
    <div class="regis_wrap">
     <div id="loading"></div> 
    	<div class="signin_wrap reset_wrap_a" id="signin">
        	<h1>Reset Password</h1>
        	<c:if test="${not empty msg}">
	        <div id="selector">
		         <span><font color="green">${msg}</font></span>
			</div>
           </c:if>
             <form:form class="login-form clearfix"  id="resetPassword_form"  name="resetPassword_form" method="post" >
            <div id=passwordDiv>
            <span>
            	<label>Verification Code</label>
                <input type="text" class="signin_fild" name="verificationCode" data-validation="required" field-name="Verification Code" id="verificationCode" value="${verificationCode}" />
                <b></b>
            </span>
            <div class="clearfix"></div>
            <div class="col-xs-12 col-md-12 mb10 posiRela">
             <span class="positionRelative">
            	<label>Password</label>
                <input type="password" class="signin_fild" name="password" field-name="Password" pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d.*)(?=.*\W.*)[a-zA-Z0-9\S]{8,}$" data-validation="required validate_length length6-100" title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters" value="${password}" id="password" onkeydown="$(this).clear();" onkeyup="passwordValidation('password','fp_letter','fp_capital','fp_number','fp_special','fp_length');" />
                <b></b>
                <i class="fa fa-eye showPassword" onclick="showPassword();"></i>
            </span>  
            
            			<div id="fp_message" class="passHint" style="left:-310px; box-shadow: 1px 1px 1px 2px #ddd; top:-10px;">
				  			<p  id="fp_letter" class="invalid">At least one lowercase letter</p>
				  			<p  id="fp_capital" class="invalid">At least one uppercase letter</p>
				  			<p  id="fp_number" class="invalid">At least one number</p>
				  			<p  id="fp_special" class="invalid">At least one special character</p>
				  			<p  id="fp_length" class="invalid">Minimum 8 characters required</p>
						</div> 
            </div>
           
            <div class="clearfix"></div>
            <span class="positionRelative">
            	<label>Confirm Password</label>
                <input type="password" class="signin_fild" value="" data-validation="required" field-name="Confirm Password" id="confirmPassword" onblur="$(this).checkPass();" />
                <b></b>
                  <i class="fa fa-eye showPassword" onclick="showConfirmPassword();"></i>
            </span>
            <div class="clearfix"></div>
            </div>
            <input type="submit" value="Save" id="submit_button" onclick="checkPasswords();" class="sign_btn" />
            </form:form>
        </div>
    </div>
</div>  
<input id="autocomplete"/>&nbsp;<input id="submit" type="submit"/>
  
	  <script src="/resources/js/jquery-1.11.1.min.js?staticTime=${staticTime}"></script>
    <script src="/resources/js/jquery-ui.js?staticTime=${staticTime}"></script>
    <script src="/resources/js/bootstrap.min.js?staticTime=${staticTime}"></script>
    <script src="/resources/js/jquery-fun.js?staticTime=${staticTime}"></script> 
    <script src="/resources/js/formvalidator.js?staticTime=${staticTime}"></script>
    
    
    
</body>

<script>
function showPassword(){
	var passwordInput = document.getElementById('password');
	if(passwordInput.type === "password"){
		passwordInput.type = "text"
	}else{
		passwordInput.type = "password"
	}
	
	
}
</script>

<script>
function showConfirmPassword(){
	var confPasswordInput = document.getElementById('confirmPassword');
	if(confPasswordInput.type === "password"){
		confPasswordInput.type = "text"
	}else{
		confPasswordInput.type = "password"
	}
	
}
</script>

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
			 document.getElementById('submit_button').disabled = false;
	    }
	    else{ 
		        $('#passwordDiv').find('span.jquery_form_error_message').remove();
		        document.getElementById("confirmPassword").setAttribute('class','error signin_fild');
		        $("input#confirmPassword").after("<span class='jquery_form_error_message'> <font color='red'>Those passwords didn't matched.Try again.</font></span>");
				document.getElementById("confirmPassword").setAttribute('pass-exist','yes');
				document.getElementById("confirmPassword").setAttribute('pass-exist-errorMsg', ' Passwords Not Matched'); 
				$("input#confirmPassword").removeAttr("style");
				document.getElementById('submit_button').disabled = true;
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
	function checkPasswords() {   
		var pass1 = document.getElementById('password');  
	    var pass2 = document.getElementById('confirmPassword');
	    if(pass1.value == pass2.value){
	    	 $('input#confirmPassword').removeAttr( "class pass-exist pass-exist-errormsg");
			 $('#passwordDiv').find('span.jquery_form_error_message').remove(); 
			 document.getElementById("confirmPassword").setAttribute('class','valid signin_fild');
			 $("input#confirmPassword").removeAttr("style");
			 document.getElementById('submit_button').disabled = false;
	    }
	    else{ 
		        $('#passwordDiv').find('span.jquery_form_error_message').remove();
		        document.getElementById("confirmPassword").setAttribute('class','error signin_fild');
		        $("input#confirmPassword").after("<span class='jquery_form_error_message'> <font color='red'>Passwords Not Matched</font></span>");
				document.getElementById("confirmPassword").setAttribute('pass-exist','yes');
				document.getElementById("confirmPassword").setAttribute('pass-exist-errorMsg', ' Passwords Not Matched'); 
				$("input#confirmPassword").removeAttr("style");
				document.getElementById('submit_button').disabled = true;
	    }
   }
	
 
</script>


<script type="text/javascript">
var validationSettings = {
        errorMessagePosition : 'element',
       borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd' 
   };  
         $('body').on('submit', '#resetPassword_form', function(e){
        	  if ($(this).validate(false, validationSettings)){
        		e.preventDefault();
        		var form = $('#resetPassword_form').serializeFormJSON();
          		var sourceleadUrl ='${sourceleadUrl}';
          		 $("#submit_button").attr("disabled", "disabled");
          		 $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;'); 
                  $.ajax({
              	      type: "POST",
              	      data:JSON.stringify(form),
              	      contentType : 'application/json; charset=utf-8',
              	      dataType : 'json',
                    	  url:"app/ws/verifyResetPassword?${_csrf.parameterName}=${_csrf.token}",
                      	success: function(json) {
                      		if(json.resetResponse == 'Invalid'){
  	                   			 $('#passwordDiv').find('span.jquery_form_error_message').remove();
  	             		         document.getElementById("verificationCode").setAttribute('class','error signin_fild');
  	             		         $("input#verificationCode").after("<span class='jquery_form_error_message'> <font color='red'>Invalid Verification Code</font></span>");
  	             				 document.getElementById("verificationCode").setAttribute('pass-exist','yes');
  	             				 document.getElementById("verificationCode").setAttribute('pass-exist-errorMsg', ' Invalid Verification Code'); 
  	             				 $("input#verificationCode").removeAttr("style");
  			    				 $("#submit_button").removeAttr("disabled")
  			    				 $("#loading").removeAttr("style");
      			        	}else{
      			        		location.replace("/login"); 
      			        	}
  			        },  
                  });
                
            	    return true;
            }
            else{
            	return false;	
               }
            });
         $('#resetPassword_form').showHelpOnFocus();
            
     
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

<script>
$(document).ready(function(){
	$(".posiRela input").focus(function(){
		$(".passHint").show();
	});
	$(".posiRela input").blur(function(){
		$(".passHint").hide();
	});	
});
</script>

<script>
function passwordValidation(idValue,fp_letter,fp_capital,fp_number,fp_special,fp_length){
	var myInput = $("#"+idValue).val();
	 
	if(myInput.match(/[a-z]/g)){
		$("#"+fp_letter).removeClass("invalid");
		$("#"+fp_letter).addClass("actTe");
	}else{
		$("#"+fp_letter).removeClass("actTe");
		$("#"+fp_letter).addClass("invalid");
	} 
	if(myInput.match(/[A-Z]/g)){
		$("#"+fp_capital).removeClass("invalid");
		$("#"+fp_capital).addClass("actTe");
	}else{
		$("#"+fp_capital).removeClass("actTe");
		$("#"+fp_capital).addClass("invalid");
	}
	if(myInput.match(/[0-9]/g)){
		$("#"+fp_number).removeClass("invalid");
		$("#"+fp_number).addClass("actTe");
	}else{
		$("#"+fp_number).removeClass("actTe");
		$("#"+fp_number).addClass("invalid");
	}
	if(myInput.match(/[$&+,:;=?@#|'<>.^*()%!-]/g)){
		$("#"+fp_special).removeClass("invalid");
		$("#"+fp_special).addClass("actTe");
	}else{
		$("#"+fp_special).removeClass("actTe");
		$("#"+fp_special).addClass("invalid");
	}
	if(myInput.length >= 8){
		$("#"+fp_length).removeClass("invalid");
		$("#"+fp_length).addClass("actTe");
	}else{
		$("#"+fp_length).removeClass("actTe");
		$("#"+fp_length).addClass("invalid");
	}
	
}


</script>

<script>
  $('#selector').delay(2000).fadeOut('slow');
</script> 

</html>