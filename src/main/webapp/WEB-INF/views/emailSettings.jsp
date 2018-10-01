<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<link rel="stylesheet" href="/resources/css/bootstrap-multiselect.css?staticTime=${staticTime}" type="text/css">
<link rel="stylesheet" href="/resources/css/bootstrap3-wysihtml5.min.css?staticTime=${staticTime}" type="text/css">


<style>
.modal-backdrop{
display: none;
}

.nicEdit-main {
	overflow: auto !important;
}

.mt40{
 margin-top: 50px !important;
}
</style>


 <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Mail account setting</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;<a href="<c:url value='${appPath}/mailBox/mails'/>"> Mailbox</a>
			&raquo; Mail account settings</div>
    </section>
    <div class="content-area content-new clearfix">
      <div class="clearfix form-horizontal inner-content">
       <form:form id="email_settings_form" name="email_settings_form"  method="post" >
        <div class="row">
         
          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm mb">
            <div class="col-md-12"><h3> Setup email account for sending</h3></div>
            
            <div class="col-md-6 minHeight">
                <label>Email<span class="text-danger">&nbsp;*</span></label>
                <input class="form-control" value="" name="email" id="email" field-name="Email Id" data-validation="required validate_Space validate_length length2-100 validate_email" placeholder="Enter email" type="text"> 
                 
            </div>
            <div class="col-md-6 minHeight">
                <label>Password<span class="text-danger">&nbsp;*</span></label>
                <input class="form-control" value="" name="password"  field-name="Password" data-validation="required validate_Space" placeholder="Enter password" type="password"> 
            </div>
            
            <div class="col-md-6 mb">
                <h4>Receiving emails</h4>
                <p>Sever Type (IMAP Mail Server)</p>
                <div class="col-md-8 noPad minHeight">
                    <label>Incoming/Receiving Server<span class="text-danger">&nbsp;*</span></label>
                    <input class="form-control" value="${incomingServer}" name="recieveServerName" data-validation="required validate_Space" field-name="RecieveServerName" placeholder="example ${server}" type="text">                 
                </div>
                <div class="col-md-4 padding_right minHeight">
                    <label>Port<span class="text-danger">&nbsp;*</span></label>
                    <input class="form-control" value="${incomingPort}" name="recievePort" data-validation="required validate_Space"  field-name="recievePort" placeholder="123" type="text">                
                </div>                
            </div>
                            
            <div class="col-md-6 minHeight mb">
                <h4>Sending emails</h4>
                <p>Sever Type (SMTP Mail Server)</p>
                <div class="col-md-8 noPad minHeight">
                    <label>Outgoing/Sending Server<span class="text-danger">&nbsp;*</span></label>
                    <input class="form-control" value="${outgoingServer}" name="SendServerName" data-validation="required validate_Space" field-name="SendServer" placeholder="example ${server}" type="text">                 
                </div>
                <div class="col-md-4 padding_right minHeight">
                    <label>Port<span class="text-danger">&nbsp;*</span></label>
                    <input class="form-control" value="${outgoingPort}" name="sendPort" data-validation="required validate_Space" field-name="SendPort" placeholder="123" type="text">                
                </div>                
            </div> 

            <div class="col-md-6 minHeight">
                <label>User name<span class="text-danger">&nbsp;*</span><b class="small text-muted ml">This is usually same as your email</b></label>
                <input class="form-control" value="${username}" name="recieveEmail" id="recieveEmail" field-name="recieveName" data-validation="required validate_Space" id="" placeholder="User name" type="text"> 
            </div>
            <div class="col-md-6 minHeight">
                <label>User name<span class="text-danger">&nbsp;*</span><b class="small text-muted ml">This is usually same as your email</b></label>
                <input class="form-control" value="${username}" name="sendEmail" id="sendEmail" field-name="SendName" data-validation="required validate_Space" id="" placeholder="User name" type="text"> 
            </div>  
                            
            <div class="col-md-6 minHeight">
                <h4>Security Settings</h4>
                    <label>SSL (Connection protection)</label>
                <select class="form-control" name="recieveSSL" field-name="SSLName">
                    <option>Autodetect</option>
                   <!--  <option>Autodetect1</option> -->
                </select>            
            </div> 
                                                                   
            <div class="col-md-6 minHeight">
                <h4>Security Settings</h4>
                    <label>SSL (Connection protection)</label>
                <select class="form-control" name="sendSSL" field-name="SSLName">
                    <option>Autodetect</option>
                    <!-- <option>Autodetect1</option> -->
                </select>            
            </div>                              

          </div>
          
        </div>
        <!-- <div class="form-footer clearfix mt hidden">
          <div class="col-lg-12">
            <div class="pull-right">
              <input type="button" value="Save" class="btn btn-primary">
              <input type="button" value="Cancel" class="btn btn-default ml">
            </div>
          </div>
        </div> -->
        <div class="clearfix"></div>

      
      
          <footer class="form-footer">
              <div class="pull-right mr">
                  <button type="button" value="add" id="add_email_account"
							onclick="addEmailAccount();" class="btn btn-sm btn-primary">Add</button>
                  <button type="button" class="btn btn-sm btn-default" onClick="javascript:window.location.href = '<c:url value='${appPath}/mailBox/mails'/>'" ><i class="fa fa-times"></i>&nbsp;Cancel</button>
              </div>
          </footer>
            </form:form>
     </div>
    </div>
  </section>

<script type="text/javascript">
	$("#email").change(function() {
		if(jQueryFormUtils.validateEmail($("#email").val())) {
			$("#recieveEmail").val($("#email").val());
			$("#sendEmail").val($("#email").val());
		} else {
			$("#recieveEmail").val("");
			$("#sendEmail").val("");
		}
	});
	function addEmailAccount() {
		if ($('#email_settings_form').validate(false, validationSettings)){ 
			
		    var formData = getFormData("email_settings_form");
			$("#email_settings_form").attr("disabled", "disabled");
			$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
            $( "#email_settings_form" ).addClass('button').val('Processing..');
			$.post(sourceleadUrl+"/addEmailAccount?${_csrf.parameterName}=${_csrf.token}", formData, function(data) {
				location.replace(sourceleadUrl+"/mailBox/mails");
			});
			
	        return true;
		
	  }else{
		return false;
	  }
    }
 
   $('body').on('blur', '#email_settings_form', function() {
		$("#email_settings_form").showHelpOnFocus();
		$("#email_settings_form").validateOnBlur(false, validationSettings);  
   });
</script>
  
<jsp:include page="masterFooter.jsp" />