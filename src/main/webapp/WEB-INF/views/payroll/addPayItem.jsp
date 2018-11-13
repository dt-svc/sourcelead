<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script>
$(document).ready(function(){
 /*   var empType = '${payItem.allowEmployeementType}';
   if(empType != null && empType != '' && empType != 'undefined'){
	   $("#allowEmployeementType").val(empType);
   } */
});
</script>
<!-- Content Area -->
  <section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Pay Item</h2>
      </div>
      <div class="pull-right brud-crum">
                <a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&raquo;<a
				href="<c:url value='${appPath}/payrollConfiguration'/>"> Payroll Configuration</a>&raquo; Pay Item
      </div>
    </section>
    
    <div class="contentareaInner-styles clearfix">
    <form:form id="add_pay_item_form"  name="add_pay_item_form" method="post">
      <div class="clearfix minHeightcol5">
        <div class="clearfix row">
        
         <div class="col-md-5">
              <label class="clearfix">Name <span class="text-danger paddingLeft">*</span></label>
              <div class="clearfix" id="payItemNameDiv">
                <input type="text" class="form-control" value="${payItem.payItemName}" name="payItemName" id="payItemName" onblur="validateItemName();" data-validation="required" field-name="Pay Item Name">
                <input type="hidden" id="payItemId" name="id" value="${payItem.id}"/>
                <input type="hidden" id="payItemUUID" name="payItemUUID" value="${payItem.payItemUuid}"/>
              </div>
         </div>
            
          <div class="col-md-5">
              <label class="clearfix">Schedule <span class="text-danger paddingLeft">*</span></label>
              <div class="clearfix">
                <select class="form-control sel" name="paySchedule" id="paySchedule" data-validation="required" field-name="Schedule">
					    <option value="">--Select Schedule-</option>
						<option ${payItem.paySchedule == 'EVERY_PAY' ? 'selected' : ''} value="EVERY_PAY">Every Pay</option>
						<option ${payItem.paySchedule == 'MONTHLY' ? 'selected' : ''} value="MONTHLY">Monthly</option>
						<option ${payItem.paySchedule == 'QUARTERLY' ? 'selected' : ''} value="QUARTERLY">Quarterly</option>
						<option ${payItem.paySchedule == 'ANNUALLY' ? 'selected' : ''} value="ANNUALLY">Annually</option>
					</select>
              </div>
           </div>	
           
            <div class="col-md-5">
              <label class="clearfix">Allow Employment Type <span class="text-danger paddingLeft">*</span></label>
              <div class="clearfix">
                <select class="form-control sel chosen-select" multiple data-placeholder="Select Employee Type" name="allowEmployeementType" id="allowEmployeementType" data-validation="required" field-name="Employment Type">
					<c:forEach var="empList" items="${empList}">
	               		<c:set var = "NUM" value = "${0}"/>
		               		<c:forEach var="payItem" items="${payItem.empType}">
							    <c:if test="${payItem eq empList}">
							    	<c:set var = "NUM" value = "${1}"/>
							       <option selected value="${empList}">${empList}</option>
							    </c:if>
							</c:forEach>
						<c:if test="${NUM == 0}">
							<option  value="${empList}">${empList}</option>
						</c:if>
                	</c:forEach>
				</select>
              </div>
           </div>  
        <div class="col-md-5">
              <label class="clearfix">Description</label>
              <div class="clearfix">
               <textarea rows="2" class="form-control" name="description"  data-validation="validate_length length1-5000"
							   id="description">${payItem.description}</textarea>
            </div>
        </div>


       
       <div class="col-md-5">
						<div class="checkbox-new pull-left">
							<c:if test="${!empty payItem.allowOverTime}">
								<input id="override" class="checkbox-custom" checked value="YES" name="allowOverTime" type="checkbox" id="allowOverTime">
								<label for="override" class="checkbox-custom-label"><span class="ml10">Allow Override</span></label>
		              		</c:if>
		              		<c:if test="${empty payItem.allowOverTime}">
			              		<input id="override" class="checkbox-custom" value="YES" name="allowOverTime" type="checkbox" id="allowOverTime">
								<label for="override" class="checkbox-custom-label"><span class="ml10">Allow Override</span></label>
		              		</c:if>
						</div>
                    </div>
        
      </div>
</div>
	<div class="clearfix footerstyelsNew">
        <div class="col-lg-12">
          <div class="pull-right">
            <input value="Save" class="btn btn-success btnSuccess" id="save_payitem_button" type="button" onclick="addPayItem();">
            <input value="Cancel" class="btn btn-defualt ml10" onclick="window.location='${appPath}/viewPayItem';" type="button">
          </div>
        </div>
      </div>      
      </form:form>
      </div>
      </section>
<jsp:include page="../masterFooter.jsp" />




<script type="text/javascript">
 function addPayItem() {
	 if ($('#add_pay_item_form').validate(false, validationSettings)){ 
			   /*  var formData = getFormData("add_pay_item_form"); */
			   var formData = getFormData1('add_pay_item_form');
				$("#save_payitem_button").attr("disabled", "disabled");
				$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	            $( "#save_payitem_button" ).addClass('button').val('Processing..');
	            $.ajax({
					type : "POST",
	        	    data: formData,
	        	    processData: false,
	  		        contentType:false, 
					url : sourceleadUrl+"/createPayItem?"+tokenParamName+"="+tokenValue+"",
					success : function(result) {
						 if (result && result.response === "INVALID_DATA") {
			                    $("#save_payitem_button").removeAttr("disabled");
			                    $("#save_payitem_button").removeAttr('button').val('Save');
			                    showError(result.responseMessage);
			                } else if (result.response === "AWKWARD") {
			                    location.href = "/errorPage";
			                } else if (result.response === "SUCCESS") {
			                	 location.replace(sourceleadUrl+ "/viewPayItem");
			                } else if (result.indexOf('Sign In') !== -1) {
								location.replace(sourceleadUrl);
							} else {
			                    $("#save_payitem_button").removeAttr("disabled");
			                    $("#save_payitem_button").addClass('button').val('Save');
			                    showError("Operation failed, please try again");
			                }
						
					},
				});
		
	        return true;
	  }else{
		return false;
	  }
    }
 
   $('body').on('blur', '#add_pay_item_form', function() {
		$("#add_pay_item_form").showHelpOnFocus();
		$("#add_pay_item_form").validateOnBlur(false, validationSettings);  
   });
  
   function validateItemName(){
		var payItemName = $("#payItemName").val();
		var payItemId = $("#payItemId").val();
		$.get(sourceleadUrl+"/validatePayItemName?"+tokenParamName+"="+tokenValue+"&payItemName="+payItemName+"&payItemId="+payItemId,  function(result) {
				 if (result.response === "AWKWARD") {
	                    location.href = "/errorPage";
	                } else if (result.response === "SUCCESS") {
	                	$('#save_payitem_button').removeAttr("disabled");
	                	$('#payItemNameDiv').find('p.jquery_form_error_message').remove();
	                }  else {
						$('#payItemNameDiv').find('p.jquery_form_error_message').remove();
	      			  	$("#payItemName").after("<p class='jquery_form_error_message'>"+payItemName+" already exists..!!</span>");
	      			  	$('#save_payitem_button').attr("disabled",true);
	                }
				
			});
	}
</script> 

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
				})
				$(document).ready(function(){
					$("#cd").click(function(){
						$(".clientDetails").toggleClass('hidden', 500);
					});
	
	});
 		</script>