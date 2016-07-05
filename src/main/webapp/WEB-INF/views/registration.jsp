<!DOCTYPE html>

<html>
<head>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>::. Registration .::</title>
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
	<div class="regis_logo regis_logo_a"><img src="resources/images/logo-regis.png" alt=""/></div>
    <div class="regis_wrap regis_wrap_a">
    	<div class="signin_wrap" id="signin">
        	<h1>Registration</h1>
            <!-- <div class="regis_tabs">
            	<a id="pro-tab" href="javascript:void(0)" class="activetab">Professional</a> <a id="comp-tab" href="javascript:void(0)">Company</a>
                <div class="clearfix"></div>
            </div> -->
        <!-- <div id="profe-regis">    
            <div>
            <span>
            	<label>First Name</label>
                <input type="text" class="signin_fild" />
                <b></b>
                <p>Password incorrect</p>
            </span>
            <span>
            	<label>Last Name</label>
                <input type="text" class="signin_fild" />
                <b></b>
                <p>Password incorrect</p>
            </span>
            </div>
            
            <div>
            <span>
            	<label>Personal email</label>
                <input type="text" class="signin_fild" />
                <b></b>
                <p>User name incorrect.</p>
            </span>
            <span>
            	<label>Select Country</label>
                <input id="professional-autocomplete" type="text" class="signin_fild signin_fild_arrow" />
                <b></b>
                <p>Password incorrect</p>
            </span>
            </div>
            
            <div>
            <span>
            	<label>Password</label>
                <input type="password" class="signin_fild" />
                <b></b>
                <p>User name incorrect.</p>
            </span>
            <span>
            	<label>Confirm password</label>
                <input type="password" class="signin_fild" />
                <b></b>
                <p>Password incorrect</p>
            </span>
            </div>  
            
            <div class="clearfix"></div>
<div class="terms_text">By Signing Up you agree to our <a href="javascript:void(0)">Terms</a> and <a href="javascript:void(0)">Privacy Policy.</a></div>                      
            <input type="submit" value="Register" class="sign_btn" /><div class="for-pas-text">Already have an account? <a href="sign-in.html"> Sign In</a></div> 
           
        </div> -->
        
<div style="padding:20px 0px 0px 0px;">
       <form:form class="login-form clearfix"  id="companyRegistration_form"  name="companyRegistration_form" method="post" >
	            <div>
	            <span>
	            	<label>First Name</label>
	                <input type="text" name="firstName"  value="${firstName}" class="signin_fild" required />
	                <b></b>
	            </span>
	            <span>
	            	<label>Last Name</label>
	                <input type="text" name="lastName"  value="${lastName}" class="signin_fild" required />
	                <b></b>
	            </span>
	            </div>
	            
	            <div>
	            <span>
	            	<label>Company Name</label>
	                <input type="text" name="companyName"  value="${companyName}" class="signin_fild" required />
	                <b></b>
	            </span>
	            <div  id="companyWebsiteDiv">
	            <span>
	            	<label>Company website</label>
	                <input type="text" name="website"  value="${website}"   id="companyWebsite"   onblur="validateWebsite();"   class="signin_fild" required />
	                <b></b>
	            </span>
	            </div>
	            </div>
	            
	            <div>
	            <span>
	            	<label>Company email</label>
	                <input type="text" name="email" id="email"   value="${email}" class="signin_fild" required />
	                <b></b>
	            </span>
	            <span>
	            	<label>Select Country</label>
	                <input id="company-autocomplete" name="country"  value="${country}" type="text" class="signin_fild signin_fild_arrow" required />
	                <b></b>
	            </span>
	            </div>            
	            
	            <div id=passwordDiv>
	            <span>
	            	<label>Password</label>
	                <input type="password" name="password"  value="${password}" id="password" class="signin_fild" required  onkeydown="$(this).clear();" />
	                <b></b>
	            </span>
	            <span>
	            	<label>Confirm password</label>
	                <input type="password"  value="" id="confirmPassword" class="signin_fild"  onblur="$(this).checkPass();" required/>
	                <b></b>
	            </span>
	            </div>  
	            <div class="clearfix"></div>
		    <div class="terms_text">By Signing Up you agree to our <a href="javascript:void(0)">Terms</a> and <a href="javascript:void(0)">Privacy Policy.</a></div>                      
		            <input type="submit" value="Register" id="register_button" class="sign_btn" /><div class="for-pas-text">Already have an account? <a href="<c:url value='/login' />"> Sign In</a></div> 
		   
		   </form:form>        
        </div>            
                       
        </div>       
    </div>
</div>  
  
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

        $('#companyRegistration_form')
           .submit(function(e) {
        	    e.preventDefault(); 
        	    var frm = $('#companyRegistration_form').serialize();
        	    frm = "testvalue=testvalue&"+frm;
        		var con = JSON.stringify(frm);
                $.ajax({
            	      type: "GET",
            	      contentType : 'application/json; charset=utf-8',
            	      dataType : 'json',
                  	  url: "http://localhost:8080/sourcelead"+"/companyRegistration",
                  	  data:con
                  			  
                });
            
            });
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
function validateWebsite(){
      var website = document.getElementById("companyWebsite").value; 
     
     var sourceleadUrl ='${sourceleadUrl}';
     
     var hireWinLocationCode = '${hireWinLocationCode}';
	 $('input#companyWebsite').removeAttr( "record-exist record-exist-errormsg");
	 if(website != null && website != ''){

			 $("#register_button").attr("disabled", "disabled");
			 $.ajax({
				    type: "GET",
       	            url: sourceleadUrl+"/validateOrgWebsite?website="+website,
			        success: function(data) {
			        	alert("david"+data);
			        	 var result = "["+data+"]";
			        		alert("result"+result);
			        	if((result == '[true]')){
			        		
			        		alert("inside");
							  
			        	}
			        	else{
			        		
			        		alert("else");
			        		 
			        		
			        		
			        	} 
			        },
			        
			    }); 
	    }
    }
</script>




</html>