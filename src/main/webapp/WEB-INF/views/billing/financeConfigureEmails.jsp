<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<link rel="stylesheet" href="/resources/css/bootstrap-clockpicker.min.css">
<script src="/resources/js/bootstrap-clockpicker.min.js"></script> 

<!-- Content Area -->
<section class="content-wraper">
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2>Finance</h2>
    </div>
    <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Finance</div>
  </section>
  <form:form id="financeConfigueEmail_form" name="financeConfigueEmail_form" method="post">
  <div class="content-area content-new clearfix" style="min-height: 50px;">
    <div class="col-xs-12 col-sm-7 col-md-7 mt mb">
		<div class="clearfix">
      <div class="row">
        <div class="col-md-4 textBold paddingRight">Applicants Ready for Invoice</div>
        <div class="col-md-8">
          <div class="checkbox-new">
            <input id="arfi" class="checkbox-custom" name="contractExpiry" value="Y" type="checkbox">
            <label for="arfi" class="checkbox-custom-label"></label>
          </div>
        </div>
        <div class="clearfix"></div>
      </div>
      <div class="clearfix mt" id="arfiDiv" style="display:none;">
        <div class="clearfix form-horizontal">
          <div class="row">
            <div class="clerfix"></div>
            <div class="col-xs-12 col-sm-12 col-md-12 minHeight50 mb">
              <label class="col-md-3 paddingRight">Send<sup>1st</sup> Reminder</label>
              <div class="col-md-9">
                
				  <div class="row">
				  
			   <div class="col-md-6">
                  <input type="text" class="form-control" name="firstReminder" id="firstReminder" value="">
				</div>
				 
				  <div class="clockpicker col-md-4">
					<input type="text" class="form-control timeicon" name="remindertime" id="remindertime" value="">
				  </div>
					  
				  <div title="Add Reminder" onClick="arfiFun();" class="col-md-2">
					  <div class="mt5">
						  <i class="fa fa-plus cursorPointer text-primary" title="Add"></i>
					  </div>
				   </div>

				 </div>
              </div>
            </div>
			  
			 <div class="col-xs-12 col-sm-12 col-md-12 mb minHeight50" id="arfireminder" style="display:none">
              <label class="col-md-3 paddingRight">Send<sup>2nd</sup> Reminder</label>
              <div class="col-md-9">
                
				  <div class="row">
				  
			   <div class="col-md-6">
                  <input type="text" class="form-control" name="secondReminder" id="secondReminder" value="">
				</div>
				 
				  <div class="clockpicker col-md-4">
					<input type="text" class="form-control timeicon" name="remindertime" value="">
				  </div>
					  
				  <div title="Add Reminder" class="col-md-2">
					  <div class="mt5">
						  <i class="fa fa-plus cursorPointer text-primary mr" title="Add" onClick="arfiFun();"></i>
						  <i class="fa fa-times cursorPointer colorRed" title="Remove" onClick="arfiFun();"></i>
					  </div>
				   </div>

				 </div>
              </div>
            </div> 
			  
			  
            

            <!-- <div class="col-xs-12 col-sm-12 col-md-12 minHeight50 mb">
              <label class="col-md-3">Reminder To</label>
              <div class="col-md-9">
                <select class="form-control chosen-select" data-placeholder="Select" multiple>
                  <option>Reporting Manager</option>
                  <option>Project Manager</option>
                  <option>Team Leader</option>
                </select>
              </div>
            </div> -->
            <div class="col-xs-12 col-sm-12 col-md-12 minHeight50 mb">
              <label class="col-md-3">Notify To</label>
              <div class="col-md-9">
                <select class="form-control chosen-select" data-placeholder="Select" multiple >
                  <option>Roles 1</option>
                  <option>Roles 2</option>
                  <option>Roles 3</option>
                </select>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="clearfix mt"></div>
			</div>
	  </div>
		<div class="clearfix"></div>
	  <div class="form-footer clearfix mt">
      <div class="col-lg-12">
        <div class="pull-right">
          <input type="button" value="Save" class="btn btn-primary" onclick="saveFinanceConfigureEmail();">
          <input type="hidden" id="orgLocationId" value='${orgLocationId}' />
          <input type="button" value="Cancel" class="btn btn-default ml10">
        </div>
      </div>
    </div>
  </div>
  </form:form>
</section>


<script type="text/javascript">
$('.clockpicker').clockpicker({
    placement: 'bottom',
    align: 'left',
    donetext: 'Done',
	//autoclose: true,
    twelvehour: true
});
//reminderFun
function arfiFun() {
	document.getElementById("arfireminder").style.display="block";
}
$("#arfi").click(function() {
	$("#arfiDiv").toggle();
});
</script>


<script>
	function saveFinanceConfigureEmail(){
		alert("inside the function");
			if ($('#financeConfigueEmail_form').validate(false, validationSettings)) {
				alert("inside the if condition of function saveFinanceConfigureEmail");
				var orgLocationId = document.getElementById("orgLocationId").value;
				alert("after the orgLocationId : "+orgLocationId);
			  	var formData = getFormData('financeConfigueEmail_form');
			  	alert("after the formData variable : "+formData);
				$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
				alert("before the calling restcontroller value");
				$.post(sourceleadUrl+"/saveFinanceConfigureEmail?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&responderType=Client", formData, function(data) {
					alert("inside the calling value function of ajax");
					location.replace(sourceleadUrl+ "/emailreminderConfigurationDashboard");
				});
				return true;
			} else {
				return false;
			}
		
	}
</script>


<jsp:include page="../masterFooter.jsp" />