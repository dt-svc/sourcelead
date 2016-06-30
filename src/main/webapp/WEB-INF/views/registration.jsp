<!DOCTYPE html>

<html>
<head>

<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>::. Registration .::</title>
    <link rel="icon" type="resources/image/png" href="resources/images/fav.png" />
	<link href="https://fonts.googleapis.com/css?family=Roboto:400,300italic,300,400italic,500,700,900" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css">
    <link href="resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="resources/css/register-style.css" rel="stylesheet">
    
</head>

<body>
<div class="regis_bg">
	<div class="regis_logo regis_logo_a"><img src="resources/images/logo-regis.png" alt=""/></div>
    <div class="regis_wrap regis_wrap_a">
    	<div class="signin_wrap" id="signin">
        	<h1>Registration</h1>
            <div class="regis_tabs">
            	<a id="pro-tab" href="javascript:void(0)" class="activetab">Professional</a> <a id="comp-tab" href="javascript:void(0)">Company</a>
                <div class="clearfix"></div>
            </div>
        <div id="profe-regis">    
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
           
        </div>
        
<div id="comp-regis">    
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
            	<label>Company Name</label>
                <input type="text" class="signin_fild" />
                <b></b>
                <p>User name incorrect.</p>
            </span>
            <span>
            	<label>Company website</label>
                <input type="text" class="signin_fild" />
                <b></b>
                <p>Password incorrect</p>
            </span>
            </div>
            
            <div>
            <span>
            	<label>Company email</label>
                <input type="text" class="signin_fild" />
                <b></b>
                <p>User name incorrect.</p>
            </span>
            <span>
            	<label>Select Country</label>
                <input id="company-autocomplete" type="text" class="signin_fild signin_fild_arrow" />
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
            <input type="submit" value="Register" class="sign_btn" /><div class="for-pas-text">Already have an account? <a href="<c:url value='/login' />"> Sign In</a></div> 
           
        </div>            
                       
        </div>       
    </div>
</div>  
  
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="resources/js/bootstrap.min.js"></script>
    <script src="resources/js/jquery.autocomplete.min.js"></script>
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











</html>