<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div id="emailNotifications" class="tab-pane fade"> 
          <!-- Tab for Profile setting-area ends -->
          <div class="row">
            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12"> 
              <!-- Tab for Profile setting-area -->
              <div class="accountsettings-tab">
                <div class="tab-content panel-body">
                  <div class="clearfix">
					<div class="clearfix">
					<h4 class="pull-left">
						Email
					</h4>
						<a href="javascript:void(0)" class="btn btn-primary pull-right" onClick="addEmail();"><i class="fa fa-plus paddingRight"></i>Add Email</a>
					</div>
                    <div class="clearfix styleTables">
                    	<table>
                    		<thead>
                    			<tr>
                    				<th>Email</th>
                    				<th>Selected Actions</th>
                    				<th>Actions</th>
                    			</tr>
                    		</thead>
                    		<tbody>
                    			<tr>
                    				<td><div class="tbldata-dotdot">Rajkumarsantoshi@gmail.com</div></td>
                    				<td><div class="tbldata-dotdot">Jobs, timesheet, Interview</div></td>
                    				<td>
                    					<div class="tblminwidth-100">
                    						<ul class="list-unstyled list-inline">
                    						<li>
												<label class="switch mt10">
												  <input type="checkbox" checked>
												  <span class="slider round"></span>
												</label>
                    						</li>
                    						<li><i class="fa fa-pencil pull-left ml" style="position: relative;top: -10px;"></i></li>
                    						<li><i class="fa fa-trash pull-left text-danger" style="position: relative;top: -10px;"></i></li>
                    					</ul>
                    					</div>
                    				</td>
                    			</tr>
                    		</tbody>
                    	</table>
                    </div>
                  </div>
                  
                </div>
              </div>
            </div>
          </div>
          <!-- Tab for Profile setting-area ends --> 
        </div>
        
        <!-- Tab for Profile setting-area starts Ends --> 
<!-- Content Area Ends-->
<!-- Add email notificatons -->
<div class="comMask"></div>
<div class="modal fade in emailforNotifications" id="emailforNotifications">
	<div class="modal-dialog">
		<div class="modal-content clearfix">
			<div class="modal-header">
				<a href="javascript:void(0)" class="close" onClick="closeEmailNotifications()">&times;</a>
				<h4 class="modal-title">Add Emails</h4>
			</div>
			<div class="modal-body">
			<div class="clearfix mb">
					<label class="clearfix">Enter Email</label>
					<div class="clearfix">
					<div class="input-group errorInptgroup">
						<input type="text" class="form-control" id="email" value="" onblur="verifyEmails();">
						 <span id="emailStatusIconDiv" class="input-group-addon">
	                    	<i class="fa fa-check text-muted" style="color:#f6f6f6"></i>
	                  	</span>
					</div>
					</div>
			</div>
			<div class="clearfix mb">
					<label class="clearfix">Choose Actions</label>
					<div class="clearfix">
						<select class="chosen-select form-control" data-placeholder="Select Actions" multiple>
							<option>Jobs</option>
							<option>Jobs</option>
							<option>Jobs</option>
							</select>
					</div>
				</div>
				<div class="clearfix row">
		<label class="col-md-3">Make Default</label>
			<div class="col-md-5">
				<label class="switch">
				  <input checked="" type="checkbox">
				  <span class="slider round"></span>
				</label>
		  	</div>
				</div>
				<div class="clearfix"></div>
			</div>
			<div class="modal-footer clearfix">
				<div>
					<button type="button" class="btn btn-success btnSuccess mr" onClick="closeEmailNotifications()">Save</button>
					<button type="button" class="btn btn-Default">Cancel</button>
				</div>
			</div>
		</div>
	</div>
</div>

<script src="js/jquery-1.11.3.min.js"></script> 
<script src="js/bootstrap.min.js"></script> 
<script src="js/masterHeaderAction.js"></script> 
<script src="js/bootstrap-colorpicker.min.js"></script>
 <script src="js/chosen.jquery.min.js"></script> 
  <script type="text/javascript">
			var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
			for (var selector in config) {
			  $(selector).chosen(config[selector]);
			}
			$('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
				});
 		</script>
    <script>
    //Color Picker
    $(function() {
        $('#cp2, #cp3').colorpicker();
    });
		function addSignatoryDiv() {
		$("#addSignatoryDiv").toggle();
		}
	function addEmail() {
		alert("hi");
	}
//Email Notifications Script
function addEmail() {
	$(".comMask").fadeIn();
	$(".emailforNotifications").fadeIn();
}
function closeEmailNotifications(){
	$(".comMask").fadeOut();
	$(".emailforNotifications").fadeOut();
}
</script>

<script type="text/javascript">
function verifyEmails() {
	var email = document.getElementById("email").value;
	if(email != null && email != ''){
	      validateEmailForExistenceUniversally(email, '#email');
	}else{
		 $("#emailStatusIconDiv").find("i").remove();
   		 $("#emailStatusIconDiv").append('<i class="fa fa-check text-muted" style="color:#f6f6f6"></i>');
	}
}

</script>