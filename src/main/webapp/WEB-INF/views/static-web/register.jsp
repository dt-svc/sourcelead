<!--   HTML   -->	  
<jsp:include page="../staticHeader.jsp" />   
<section class="logWrap">
	<div class="logWrapLeft rigRight"></div>	
    <div class="logWrapRight rigRight">
    	<div class="logIcon"><i class="fa fa-lock"></i></div>
    	<div class="logFilds">
        	<h3>Registration</h3>
            <span>I have an account <a href="/SignIn">Sign in now</a></span>
        	<div class="logFildInn">
            	<span><input type="text" class="textfild" placeholder="Username" /><i class="fa fa-user"></i><p>Error message here..</p></span>
                <span><input type="text" class="textfild" placeholder="Email" /><i class="fa fa-envelope"></i><p>Error message here..</p></span>
                <span><input type="text" class="textfild" placeholder="Phone" /><i class="fa fa-mobile"></i></span>
                <span><input type="password" class="textfild" placeholder="Password"><i class="fa fa-key"></i></span>
                <span><input type="password" class="textfild" placeholder="Confirm Password"><i class="fa fa-key"></i></span>
                <span><input type="button" value="Registration" class="logbtn"></span>
            </div>
        </div>
    </div>	
<div class="clear"></div>
</section>  
<jsp:include page="../staticFooter.jsp" />