<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script src="resources/js/formvalidator.js"></script>
<!-- <script src="resources/js/chosen.jquery.min.js"></script> --> 


<style type="text/css">
.chosen-container {
    width: 100% !important;
}
.fullWid {width: 100% !important;}
</style>

<!-- these files for autocomplete with tagit -->
<link href="resources/css/jquery-tagit.css" rel="stylesheet">
<!-- end files for autocomplete with tagit -->

<!-- Content Area -->
 <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>New Contract</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; New Contract</div>
    </section>
    <div class="content-area content-new clearfix">
     <div id="loading"></div>
     <form:form id="newcontract_form" name="newcontract_form" method="post">
      <div class="clearfix form-horizontal">
       
        <div class="row newContrWrap">
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
           
           <h2>Contract Details</h2>
            <div class="form-group">
              <label class="col-md-4">Contract Type<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8">
                  <select class="chosen-select form-control" id="contractType" name="contractType" onchange="contractTypeLabels();getContractTemplates();flush();" data-validation="required" field-name="Contract Type">
                    <option value=''>Contract Type</option>
                    <option>Client MSA</option>
                    <option>Vendor MSA</option>
                    <option>Client SOW</option>
                    <option>Vendor SOW</option>
                    <option>NDA</option>
                    <option>NCA</option>
                  </select>
            </div>
            </div>				
			</div>
          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
          	
          	<div class="form-group">
              <label class="col-md-4">Contract title<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8" id="titleDiv">
                <input type="text" class="form-control" id="contractTitle" name="contractTitle" value="" data-validation="required validate_Space validate_length length1-250" field-name="Contract Title" onblur="validateContractTitle();">
              </div>
            </div>
                       
            <div class="form-group">
              <label class="col-md-4">Select template<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8">
                  <select class="chosen-select form-control" id="template" name="template"  data-validation="required" field-name="Template">
                  </select>
              </div>
            </div>  
            
            <div class="form-group" id="requirementDiv" style="display: none;">
              <label class="col-md-4">Select Requirement<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8">
                  <select class="form-control" id="requirementsList" name="requirementsList" onchange="submissionOffers();" field-name="Requirement">
                  </select>
              </div>
            </div>
            
            <div class="form-group" id="offeredSubmissionDiv" style="display: none;">
              <label class="col-md-4">Select Applicant<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8">
                  <select class="form-control" id="submissionOffersList" name="submissionOffersList" field-name="Applicant">
                  </select>
              </div>
            </div>
                     
             
          <div class="form-group">
              <label class="col-md-4">Owner<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8">
                  <select class="chosen-select form-control" id="owner" name="owner" data-validation="required" field-name="Owner">
                  <option value=''>Select Owner</option>
                  <c:forEach var="user" items="${userList}">
	                     <option value="${user.id}" ${user.id  == SourceLeadWeb_userId ? 'selected' : ''} >${user.firstName} ${user.lastName}</option>
                  </c:forEach>
                   <!--  <option>Select Owner</option>ER
                    <option>Owner-1</option>
                    <option>Owner-2</option>
                    <option>Owner-3</option> -->
                  </select>
              </div>
          </div>             
            
             <div class="form-group">
              <label class="col-md-4">Start & End date<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8">
					<div class="row">
					  <div class="col-lg-6">
						<div id="startDateDiv">
						  <input type="text" class="form-control calendar" id="startDate" name="startDate" placeholder="Start date" data-validation="required" field-name="Start Date" readonly="readonly">
						</div>
					  </div>
					  <div class="col-lg-6">
						<div id="endDateDiv">
						  <input type="text" class="form-control calendar" id="endDate" name="endDate" placeholder="End date" readonly="readonly">
						</div>
					  </div>
					</div>
              </div>
            </div>
          
         <div class="form-group" id="vendorDiv" style="display: none;">
              <label class="col-md-4">Vendor<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8">
                  <select class="chosen-select form-control" id="orgRelationId" name="orgRelationId" onchange="getVendorContacts();" data-validation="required" field-name="Vendor Name">
                    <option value=''>Select Vendor</option>
                   <c:forEach var="orgrelations" items="${orgRelationsList}">
                         <option value="${orgrelations.id}">${orgrelations.orgName}</option>
                  </c:forEach>
                  
                   <!--  <option>Vendor-1</option>
                    <option>Vendor-2</option>
                    <option>Vendor-3</option> -->
                  </select>
              </div>
          </div>                      
                      
            <div class="form-group" id="vendorContactDiv"  style="display: none;">
              <label class="col-md-4">Vendor Contact<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8">
                  <select class="chosen-select form-control" id="vendorContact" name="vendorContact" data-validation="required" field-name="Vendor Contact">
                  </select>
              </div>
            </div>
            
            
             <div class="form-group" id="clientDiv" style="display: none;">
              <label class="col-md-4">Client<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8">
                  <select class="chosen-select form-control" id="clientOrgRelationId" name="clientOrgRelationId" onchange="getClientContacts();" data-validation="required" field-name="Vendor Name">
                    <option value=''>Select Client</option>
                   <c:forEach var="orgrelations" items="${clientList}">
                         <option value="${orgrelations.id}">${orgrelations.orgName}</option>
                  </c:forEach>
                  </select>
              </div>
          </div>                      
                      
            <div class="form-group" id="clientContactDiv" style="display: none;">
              <label class="col-md-4">Client Contact<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8">
                  <select class="chosen-select form-control" id="clientContact" name="clientContact" data-validation="required" field-name="Vendor Contact">
                  </select>
              </div>
            </div>
        
            
          <div class="form-group">
              <label class="col-md-4">Approver</label>
              <div class="col-md-8">
                  <select class="chosen-select form-control" id="approver" name="approver" onchange="changeSaveButton();">
                  <option value=''>--Select Owner--</option>
                  <c:forEach var="user" items="${userList}">
                   <%-- to Enable login user as approver ${user.id  == SourceLeadWeb_userId ? 'selected' : ''} --%>
	                     <option value="${user.id}">${user.firstName} ${user.lastName}</option>
                  </c:forEach>
                  </select>
              </div>
          </div> 
                       
            <div class="form-group">
              <label class="col-md-4">Description</label>
              <div class="col-md-8">
					<textarea class="form-control" rows="3" id="description" name="description" data-validation-optional="true" data-validation="validate_Space validate_length length1-250"></textarea>
              </div>
            </div>    
            
            <div class="form-group">
              <label class="col-md-4">State of Jurisdiction</label>
              <div class="col-md-8">
                <input type="text" class="form-control" id="jurisdiction" name="jurisdiction" field-name="State Jurisdiction">
              </div>
            </div>         
            
        </div>
      </div>
      
      
<div class="row newContrBill">
          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm" id="billingDiv" >
           <h2>Billing Details</h2>
           
           <div class="form-group" id="hoursPerWeekDiv" style="display: none;">
              <label class="col-md-4">Hours Per Week<span class="text-danger">&nbsp;*</span></label>
	              <div class="col-md-8">
				  		<input type="text" class="form-control" id="hoursPerWeek" name="hoursPerWeek" value="" placeholder="Enter Hours" field-name="Hours Per Week">
	              </div>
            </div>
            
            <div class="form-group" id="overTimeHoursDiv" style="display: none;">
              <label class="col-md-4">Overtime Hours<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8">
					  	<input type="text" class="form-control" id="overTimeHours" name="overTimeHours" value="" placeholder="Enter Hours" field-name="Overtime Hours">
              	</div> 
            </div>
            
            <div class="form-group" id="totalHoursEstimatedDiv" style="display: none;">
              <label class="col-md-4">Total Hours Estimated<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8">
					  	<input type="text" class="form-control" id="totalHoursEstimated" name="totalHoursEstimated" value="" placeholder="Enter Total Estimated Hours" field-name="Total Estimated Hours">
              	</div>
            </div>
           
           <div class="form-group" id="overtimeRateDiv" style="display: none;">
              <label class="col-md-4">Overtime Rate<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8">
              		<div class="row">
					  <div class="col-lg-8">
					  	<input type="text" class="form-control" id="overtimeRate" name="overtimeRate" value="" placeholder="Overtime Rate" field-name="Overtime Rate">
					  </div>
					  <div class="col-lg-4">
						<select class="form-control selectpicker" id="overtimeRateType" name="overtimeRateType" field-name="Overtime Rate Type">
						  <option value="">Select Type</option>
						  <option value="FLAT">Flat</option>
						  <option value="PERCENTAGE">Percentage</option>
						</select>
					  </div>
					</div>
              	</div>
            </div>
           
            <div class="form-group">
              <!-- <label class="col-md-4">Billing Amount<span class="text-danger">&nbsp;*</span></label> -->
                <label class="col-md-4">Billing Amount<span class="text-danger">&nbsp;*</span></label>
                   <div class="clearfix col-md-8" id="errBilling">
                      <div class="col-md-5 paddingLeft">
                        <div class="btn-group btn_Block">
                          <button type="button" class="btn btn-sm btn-primary" id="percentageBtn" value="percentage" onClick="getRewardType('Percentage')">%</button>
                          <button type="button" class="btn btn-sm btn-default" id="flatBtn" value="flat" onClick="getRewardType('Flat')">Flat</button>
                          <input type="hidden" name="rewardType" id="rewardType" value=""/>
                        </div>
                      </div>
                      <div class="col-md-7 paddingLeft" id="percentageReward">
                        <div class="form-group positionRelative iconHolder percentage fullWid" >
                          <input type="text" class="form-control input-sm" field-name="Percentage" id="percentageRewardValue" name="percentageReward" onblur="getPercentageValue()" placeholder="Percentage">
                          <i class="fa fa-percent"></i> 
                        </div>
                        </div>
                        <div class="col-md-7 paddingLeft" id="flatReward"  style="display: none">
                        <div class="row flat">
                          <div class="form-group col-md-8 paddingRight positionRelative iconHolder" >
                            <input type="text" class="form-control input-sm" field-name="Flat" id="flatRewardValue"  name="flatReward" placeholder="Flat">
                            <i class="fa fa-money" id="flatRewardIcon"></i> </div>
                          <div class="form-group select-field col-md-4 paddingLeft">
                            <select class="form-control input-sm" id="flatRateType" name="flatRateType"  style="display: none">
                              <option  ${SourceLeadWeb_currencyCode == 'INR' ? 'selected' : ''}><i class="fa fa-rupee" title="INR"></i>INR</option>
                              <option  ${SourceLeadWeb_currencyCode == 'USD' ? 'selected' : ''}><i class="fa fa-dollar" title="USD"></i>USD</option>
                              <option  ${SourceLeadWeb_currencyCode == 'AUD' ? 'selected' : ''}><i class="fa fa-aud" title="AUD"></i>AUD</option>
                              <option  ${SourceLeadWeb_currencyCode == 'EUR' ? 'selected' : ''}><i class="fa fa-eur" title="Eur"></i>EUR</option>
                              <option  ${SourceLeadWeb_currencyCode == 'SGD' ? 'selected' : ''}><i class="fa fa-sgd" title="SGD"></i>SGD</option>
                              <option  ${SourceLeadWeb_currencyCode == 'GBP' ? 'selected' : ''}><i class="fa fa-gdp" title="GDP"></i>GBP</option>
                            </select>
                          </div>
                        </div>
                      </div>
                    </div>
            </div>
            <div class="form-group">
              <label class="col-md-4">invoice frequency<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8" id="errInvoice">
                  <select class="chosen-select form-control" id="invoiceFrequency" name="invoiceFrequency" data-validation="required" field-name="Invoice Frequency">
                    <option value="">Select frequency</option>
                    <option value="Weekly">Weekly</option>
                    <option value="Bi-Weekly">Bi-Weekly</option>
                    <option value="Semi-Monthly">Semi-Monthly</option> 
                    <option value="Monthly">Monthly</option>
                  </select>
              </div>
            </div>
            
              <div class="form-group">
              <label class="col-md-4">Payment frequency<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8" id="errPayment">
                  <select class="chosen-select form-control" id="paymentFrequency" name="paymentFrequency" data-validation="required" field-name="Payment Frequency">
                    <option value="">Select frequency</option>
                    <option value="10 days">10 days</option>
                    <option value="15 days">15 days</option>
                    <option value="30 days">30 days</option>
                    <option value="45 days">45 days</option>
                     <option value="60 days">60 days</option>
                  </select>
              </div>
            </div>
            
            <div class="form-group">
              <!-- <label class="col-md-4">Billing Amount<span class="text-danger">&nbsp;*</span></label> -->
                <label class="col-md-4">Tax<span class="text-danger">&nbsp;*</span></label>
                   <div class="clearfix col-md-8" id="errTax">
                      <div class="col-md-5 paddingLeft">
                        <div class="btn-group btn_Block">
                          <button type="button" class="btn btn-sm btn-primary" id="taxPercentageBtn" value="percentage" onClick="getTaxRewardType('Percentage')">%</button>
                          <button type="button" class="btn btn-sm btn-default" id="taxflatBtn" value="flat" onClick="getTaxRewardType('Flat')">Flat</button>
                          <input type="hidden" name="taxRewardType" id="taxRewardType" value=""/>
                        </div>
                      </div>
                      <div class="col-md-7 paddingLeft" id="taxPercentageReward">
                        <div class="form-group positionRelative iconHolder percentage fullWid" > 
                          <input type="text" class="form-control input-sm" field-name="Percentage" id="taxPercentageRewardValue" name="taxPercentageReward" onblur="getTaxPercentageValue()" placeholder="Percentage">
                          <i class="fa fa-percent"></i> 
                        </div>
                        </div>
                        <div class="col-md-7 paddingLeft" id="taxFlatReward"  style="display: none">
                        <div class="row flat">
                          <div class="form-group col-md-8 paddingRight positionRelative iconHolder" >
                            <input type="text" class="form-control input-sm" field-name="Flat" id="taxFlatRewardValue"  name="taxFlatReward" placeholder="Flat">
                            <i class="fa fa-money" id="taxFlatRewardIcon"></i> </div>
                          <div class="form-group select-field col-md-4 paddingLeft"> 
                            <select class="form-control input-sm" id="taxFlatRateType" name="taxFlatRateType"  style="display: none">
                              <option  ${SourceLeadWeb_currencyCode == 'INR' ? 'selected' : ''}><i class="fa fa-rupee" title="INR"></i>INR</option>
                              <option  ${SourceLeadWeb_currencyCode == 'USD' ? 'selected' : ''}><i class="fa fa-dollar" title="USD"></i>USD</option>
                              <option  ${SourceLeadWeb_currencyCode == 'AUD' ? 'selected' : ''}><i class="fa fa-aud" title="AUD"></i>AUD</option>
                              <option  ${SourceLeadWeb_currencyCode == 'EUR' ? 'selected' : ''}><i class="fa fa-eur" title="Eur"></i>EUR</option>
                              <option  ${SourceLeadWeb_currencyCode == 'SGD' ? 'selected' : ''}><i class="fa fa-sgd" title="SGD"></i>SGD</option>
                              <option  ${SourceLeadWeb_currencyCode == 'GBP' ? 'selected' : ''}><i class="fa fa-gdp" title="GDP"></i>GBP</option>
                            </select>
                          </div>
                        </div>
                      </div>
                    </div>
            </div>
        </div>
        <!-- billing end -->
      </div>      
      
      <div class="form-footer clearfix contrFoot">
        <div class="col-lg-12">
          <div class="pull-right mt">
            <input type="button" value="Save Draft" id="draft_template" onclick="saveNewContract('draft');" class="btn btn-primary pull-left">
            <input type="button" value="Save" id="save_template" onclick="saveNewContract('pending');" class="btn btn-success ml10 pull-left">

            <input type="button" value="SubmitToApprover" id="save_approver_template" onclick="saveNewContract('pending');" class="btn btn-success ml10 pull-left" style="display:none">
            <input type="button" value="Cancel" class="btn btn-danger ml10 pull-left" id="cancel_contract" onclick="cancelContract();">
          </div>
        </div>
      </div>
      
      <!-- Content Area Ends--> 
    </div>
    </form:form>
  </section>


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
    <script>
    $("#uasrch").selectpicker('refresh');
    </script>


<script>
	$(function() {

		$('#startDate').datepicker({
            dateFormat: 'dd-mm-yy',
            changeMonth: true,
              changeYear: true,
            onSelect: function (selected) {
                  var dt = toDate("#startDate");
                  dt.setDate(dt.getDate() + 1);
                  $("#endDate").datepicker("option", "minDate", dt);
              }
        });
        
        $('#endDate').datepicker({
            dateFormat: 'dd-mm-yy',
            changeMonth: true,
              changeYear: true,
              onSelect: function (selected) {
                  var dt = toDate("#endDate");
                  dt.setDate(dt.getDate() - 1);
                  $("#startDate").datepicker("option", "maxDate", dt);
              }
        });
});
</script>

<script>
function toDate(selector) {
    var from = $(selector).val().split("-");
    return new Date(from[2], from[1] - 1, from[0]);
}
</script>


<!-- <script type="text/javascript"> 
	function addValidClass(){
		 var StartDate = document.getElementById("startDate").value;
		 if(StartDate){
			 $('#startDateDiv').find('p.jquery_form_error_message').remove(); 
		 }
		 
		 var EndDate = document.getElementById("endDate").value;
		 if(EndDate){
			 $('#endDateDiv').find('p.jquery_form_error_message').remove(); 
		 }
}
</script> -->

<script>
function getVendorContacts(){
	 var orgRelationId = $("#orgRelationId").val();
	var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
      type : "GET",
       url: sourceleadUrl+"/getVendorContactsByOrgRelationId?${_csrf.parameterName}=${_csrf.token}&orgRelationId="+orgRelationId,       
       success :function(data) {
    	var json = JSON.parse(data);
       	var output = "";
       	output = output + "<option value=''>Select Vendor Contact</option>";
       	for (var i=0; i<json.length; i++)
			{
	        var opt = json[i];
	            // need to use in edit
       			//output = output + "<option value='" +opt.vendorUser.id+ "' selected>"+opt.spocName+"</option>";
       			output = output + "<option value='" +opt.tempOwnerId+ "'>"+opt.spocName+"</option>";
			}
       	    $("#vendorContact").empty();
	        $("#vendorContact").append(output);
	        $('#vendorContact').trigger("chosen:updated");
      },
  });  
	
}

function getClientContacts(){
	var orgRelationId = $("#clientOrgRelationId").val();
	var sourceleadUrl ='${sourceleadUrl}';
	$.ajax({
	type : "GET",
	url: sourceleadUrl+"/getVendorContactsByOrgRelationId?${_csrf.parameterName}=${_csrf.token}&orgRelationId="+orgRelationId,
	success :function(data) {
	var json = JSON.parse(data);
	var output = "";
	output = output + "<option value=''>Select Client Contact</option>";
	for (var i=0; i<json.length; i++)
	{
	var opt = json[i];
	// need to use in edit
	//output = output + "<option value='" +opt.vendorUser.id+ "' selected>"+opt.spocName+"</option>";
	output = output + "<option value='" +opt.spocUserId+ "'>"+opt.ownerName+"</option>";
	}
	$("#clientContact").empty();
	$("#clientContact").append(output);
	$('#clientContact').trigger("chosen:updated");
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
function clearSearchKey() {
	   $('#tax').val('');
	   $('#taxDiv').parent().find('p.jquery_form_error_message').remove();

}
</script>  
<script type="text/javascript">
function selectTaxType(){
	 var taxType = document.getElementById("taxType").value;
	 
	 var tax = document.getElementById("tax").value;
	 $('#taxDiv').parent().find('p.jquery_form_error_message').remove(); 
	 if(taxType =='Percentage'){
		  var floatNum = /^(\d{0,2}(\.\d{0,2})?|100)$/
		  if(tax.match(floatNum)){
			  $("#draft_template").removeAttr("disabled");  
			  $("#save_template").removeAttr("disabled"); 
			  return true;
		  }		  
		 else{
				$("input#tax").parent().after("<p class='jquery_form_error_message'><span>Enter your Valid Percentage</span>");
				 $("#draft_template").attr("disabled", "disabled");
				 $("#save_template").attr("disabled", "disabled");
				return false; 
		 	 }
	  }
	 
		 }
	  
</script>


<script>
function getContractTemplates(){
	var contractType = $("#contractType").val();
	
	if(contractType == ""){
		$("#clientOrgRelationId option:selected").removeAttr("selected");
		$('#clientOrgRelationId').trigger("chosen:updated");
		document.getElementById("clientDiv").style.display="none";
		
		$("#clientContact option:selected").removeAttr("selected");
		$('#clientContact').trigger("chosen:updated");
		document.getElementById("clientContactDiv").style.display="none";
		
		$("#orgRelationId option:selected").removeAttr("selected");
		$('#orgRelationId').trigger("chosen:updated");
		document.getElementById("vendorDiv").style.display="none";
		
		$("#vendorContact option:selected").removeAttr("selected");
		$('#vendorContact').trigger("chosen:updated");
		document.getElementById("billingDiv").style.display="block";
		document.getElementById("vendorContactDiv").style.display="none";
	}
 	if(contractType == "Client MSA"){
 		document.getElementById("billingDiv").style.display="block";
		document.getElementById("clientDiv").style.display="block";
		document.getElementById("clientContactDiv").style.display="block";
		document.getElementById("vendorDiv").style.display="none";
		document.getElementById("vendorContactDiv").style.display="none";
	}
	if(contractType == "Vendor MSA"){
		document.getElementById("billingDiv").style.display="block";
		document.getElementById("clientDiv").style.display="none";
		document.getElementById("clientContactDiv").style.display="none";
		document.getElementById("vendorDiv").style.display="block";
		document.getElementById("vendorContactDiv").style.display="block";
	}
	if(contractType == "Client SOW"){
		document.getElementById("billingDiv").style.display="block";
		document.getElementById("clientDiv").style.display="block";
		document.getElementById("clientContactDiv").style.display="block";
		document.getElementById("vendorDiv").style.display="none";
		document.getElementById("vendorContactDiv").style.display="none";
	}
	if(contractType == "Vendor SOW"){
		document.getElementById("clientDiv").style.display="none";
		document.getElementById("clientContactDiv").style.display="none";
		document.getElementById("billingDiv").style.display="block";
		document.getElementById("vendorDiv").style.display="block";
		document.getElementById("vendorContactDiv").style.display="block";
	}
	if(contractType == "NDA" || contractType == "NCA"){
		document.getElementById("clientDiv").style.display="none";
		document.getElementById("clientContactDiv").style.display="none";
		document.getElementById("vendorDiv").style.display="none";
		document.getElementById("vendorContactDiv").style.display="none";
		document.getElementById("billingDiv").style.display="none";
	}
		 
 	var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
     type : "GET",
      url: sourceleadUrl+"/getContractsTemplatesByContractType?${_csrf.parameterName}=${_csrf.token}&contractType="+contractType,       
      success :function(data) {
    	var json = JSON.parse(data);
      	var output = "";
      	output = output + "<option>Select Vendor Contact</option>";
      	for (var i=0; i<json.length; i++)
			{
	        var opt = json[i];
	            // need to use in edit
      			//output = output + "<option value='" +opt.vendorUser.id+ "' selected>"+opt.spocName+"</option>";
      			output = output + "<option value='" +opt.id+ "'>"+opt.templateName+"</option>";
			}
      	    $("#template").empty();
	        $("#template").append(output);
	        $('#template').trigger("chosen:updated"); 
     },
 }); 
	
}

</script>

<script>
function contractTypeLabels(){
	var contractType = $("#contractType").val();
	if(contractType == ""){
		$('#orgRelationId').removeAttr('data-validation');
		$('#orgRelationId').attr('data-validation-optional',"true");
		$('#vendorContact').removeAttr('data-validation');
		$('#vendorContact').attr('data-validation-optional',"true");
		
		hideCommonSOWFields();
	}
	if(contractType == "Client MSA"){
		$('#orgRelationId').removeAttr('data-validation');
		$('#orgRelationId').attr('data-validation-optional',"true");
		$('#vendorContact').removeAttr('data-validation');
		$('#vendorContact').attr('data-validation-optional',"true");
		
		hideCommonSOWFields();
	}
	if(contractType == "Vendor MSA"){
		$('#clientOrgRelationId').removeAttr('data-validation');
		$('#clientOrgRelationId').attr('data-validation-optional',"true");
		$('#clientContact').removeAttr('data-validation');
		$('#clientContact').attr('data-validation-optional',"true");
		
		hideCommonSOWFields();
	}
	if(contractType == "Client SOW"){
		$('#orgRelationId').removeAttr('data-validation');
		$('#orgRelationId').attr('data-validation-optional',"true");
		$('#vendorContact').removeAttr('data-validation');
		$('#vendorContact').attr('data-validation-optional',"true");
		hideCommonSOWFields();
		showCommonSOWFields();
		getRequirements("Client SOW");
	}
	if(contractType == "Vendor SOW"){
		$('#clientOrgRelationId').removeAttr('data-validation');
		$('#clientOrgRelationId').attr('data-validation-optional',"true");
		$('#clientContact').removeAttr('data-validation');
		$('#clientContact').attr('data-validation-optional',"true");
		hideCommonSOWFields();
		showCommonSOWFields();
		getRequirements("Vendor SOW");
	}
	if(contractType == "NDA" || contractType == "NSA"){

		$('#percentageRewardValue').removeAttr('data-validation');
		$('#flatRewardValue').removeAttr('data-validation');
		$('#invoiceFrequency').removeAttr('data-validation');
		$('#paymentFrequency').removeAttr('data-validation');
		$('#taxPercentageRewardValue').removeAttr('data-validation');
		$('#taxFlatRewardValue').removeAttr('data-validation');
		
		
		$('#percentageRewardValue').attr('data-validation-optional',"true");
		$('#flatRewardValue').attr('data-validation-optional',"true");
		$('#invoiceFrequency').attr('data-validation-optional',"true");
		$('#paymentFrequency').attr('data-validation-optional',"true");
		$('#taxPercentageRewardValue').attr('data-validation-optional',"true");
		$('#taxFlatRewardValue').attr('data-validation-optional',"true");
		
		
		$('#clientOrgRelationId').removeAttr('data-validation');
		$('#clientOrgRelationId').attr('data-validation-optional',"true");
		$('#clientContact').removeAttr('data-validation');
		$('#clientContact').attr('data-validation-optional',"true");
		
		$('#orgRelationId').removeAttr('data-validation');
		$('#orgRelationId').attr('data-validation-optional',"true");
		$('#vendorContact').removeAttr('data-validation');
		$('#vendorContact').attr('data-validation-optional',"true");
		
		
		
		$('#requirementsList').removeAttr('data-validation');
		$('#requirementsList').attr('data-validation-optional',"true");
		$('#requirementDiv').find('p.jquery_form_error_message').remove();
		document.getElementById("requirementDiv").style.display="none";
		
		$('#submissionOffersList').removeAttr('data-validation');
		$('#submissionOffersList').attr('data-validation-optional',"true");
		$('#offeredSubmissionDiv').find('p.jquery_form_error_message').remove();
		document.getElementById("offeredSubmissionDiv").style.display="none";
		
		$('#hoursPerWeek').removeAttr('data-validation');
		$('#hoursPerWeek').attr('data-validation-optional',"true");
		$('#hoursPerWeek').val("");
		$('#hoursPerWeekDiv').find('p.jquery_form_error_message').remove();
		document.getElementById("hoursPerWeekDiv").style.display="none";
		
		$('#overTimeHours').removeAttr('data-validation');
		$('#overTimeHours').attr('data-validation-optional',"true");
		$('#overTimeHours').val("");
		$('#overTimeHoursDiv').find('p.jquery_form_error_message').remove();
		document.getElementById("overTimeHoursDiv").style.display="none";
		
		$('#totalHoursEstimated').removeAttr('data-validation');
		$('#totalHoursEstimated').attr('data-validation-optional',"true");
		$('#totalHoursEstimated').val("");
		$('#totalHoursEstimatedDiv').find('p.jquery_form_error_message').remove();
		document.getElementById("totalHoursEstimatedDiv").style.display="none";
		
		$('#overtimeRate').removeAttr('data-validation');
		$('#overtimeRate').attr('data-validation-optional',"true");
		$('#overtimeRate').val("");
		$('#overtimeRateType').removeAttr('data-validation');
		$('#overtimeRateType').attr('data-validation-optional',"true");
		$('#overtimeRateDiv').find('p.jquery_form_error_message').remove();
		
	}
}
</script>

<script>
function showCommonSOWFields(){
	document.getElementById("hoursPerWeekDiv").style.display="block";
    $('#hoursPerWeek').removeAttr('data-validation-optional');
	$('#hoursPerWeek').attr("data-validation","required");
	
	document.getElementById("overTimeHoursDiv").style.display="block";
    $('#overTimeHours').removeAttr('data-validation-optional');
	$('#overTimeHours').attr("data-validation","required");
	
	document.getElementById("totalHoursEstimatedDiv").style.display="block";
    $('#totalHoursEstimated').removeAttr('data-validation-optional');
	$('#totalHoursEstimated').attr("data-validation","required");
	
	document.getElementById("overtimeRateDiv").style.display="block";
    $('#overtimeRate').removeAttr('data-validation-optional');
	$('#overtimeRate').attr("data-validation","required");
	$('#overtimeRateType').removeAttr('data-validation-optional');
	$('#overtimeRateType').attr("data-validation","required");
}
</script>

<script>
function hideCommonSOWFields(){
	$("#requirementsList option:selected").removeAttr("selected");
	$('#requirementsList').trigger("chosen:updated");
	$('#requirementsList').removeAttr('data-validation');
	$('#requirementsList').attr('data-validation-optional',"true");
	$('#requirementDiv').find('p.jquery_form_error_message').remove();
	document.getElementById("requirementDiv").style.display="none";
	
	$("#submissionOffersList option:selected").removeAttr("selected");
	$('#submissionOffersList').trigger("chosen:updated");
	$('#submissionOffersList').removeAttr('data-validation');
	$('#submissionOffersList').attr('data-validation-optional',"true");
	$('#offeredSubmissionDiv').find('p.jquery_form_error_message').remove();
	document.getElementById("offeredSubmissionDiv").style.display="none";
	
	$('#hoursPerWeek').removeAttr('data-validation');
	$('#hoursPerWeek').attr('data-validation-optional',"true");
	$('#hoursPerWeek').val("");
	$('#hoursPerWeekDiv').find('p.jquery_form_error_message').remove();
	document.getElementById("hoursPerWeekDiv").style.display="none";
	
	$('#overTimeHours').removeAttr('data-validation');
	$('#overTimeHours').attr('data-validation-optional',"true");
	$('#overTimeHours').val("");
	$('#overTimeHoursDiv').find('p.jquery_form_error_message').remove();
	document.getElementById("overTimeHoursDiv").style.display="none";
	
	$('#totalHoursEstimated').removeAttr('data-validation');
	$('#totalHoursEstimated').attr('data-validation-optional',"true");
	$('#totalHoursEstimated').val("");
	$('#totalHoursEstimatedDiv').find('p.jquery_form_error_message').remove();
	document.getElementById("totalHoursEstimatedDiv").style.display="none";
	
	$('#overtimeRate').removeAttr('data-validation');
	$('#overtimeRate').attr('data-validation-optional',"true");
	$('#overtimeRate').val("");
	$("#overtimeRateType option:selected").removeAttr("selected");
	$('#overtimeRateType').trigger("chosen:updated");
	$('#overtimeRateType').removeAttr('data-validation');
	$('#overtimeRateType').attr('data-validation-optional',"true");
	$('#overtimeRateDiv').find('p.jquery_form_error_message').remove();
	document.getElementById("overtimeRateDiv").style.display="none";
}
</script>

<script type="text/javascript">
	function saveNewContract(saveType) {
		if ($('#newcontract_form').validate(false, validationSettings)) {
			var frm = $('#newcontract_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, "");
	        var jsonData = encodeURIComponent(con);
			var sourceleadUrl = '${sourceleadUrl}';
			$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	 		$.ajax({
						type : "POST",
						url : sourceleadUrl+ "/saveNewContract?${_csrf.parameterName}=${_csrf.token}&json="+jsonData+"&saveType="+saveType,
						success : function(result) {
							location.replace(sourceleadUrl+"/contractListbyStatus");
						},
					}); 
			return true;
		} else {
			return false;
		}
	}
	$('body').on('blur', '#newcontract_form', function() {
		$("#newcontract_form").showHelpOnFocus();
		$("#newcontract_form").validateOnBlur(false, validationSettings);
	});
</script>


<script>
	function getRequirements(sowType){
		var sourceleadUrl ='${sourceleadUrl}';
		$.ajax({
	        type : "GET",
	        url: sourceleadUrl+"/getRequirementsForSOWByOrgLocationId?sowType="+sowType,    
	         success :function(data) {
	              if(data != null && data != 'undefined' && data != "" && data != '[]'){
	            	  var json = JSON.parse(data);
	            	  var result = "";
	            	  result = result + '<option value="">--- Select Requirement ---</option>';
	                  for (var i = 0; i < json.length; i++) {
	                 	 var opt = json[i]; 
	                 	 result = result +  '<option value="'+opt.id+'"> '+opt.jobTitle+'</option>';
	                  }
	                  $("#requirementsList").empty();
	                  $("#requirementsList").append(result);
	                  $("#requirementsList").chosen();
	              	  $("#requirementsList").trigger('chosen:update');
	              }
	              document.getElementById("requirementDiv").style.display="block";
	              $('#requirementsList').removeAttr('data-validation-optional');
	  			  $('#requirementsList').attr("data-validation","required");
	       },
	    });
	}
		
	function submissionOffers(){
		var sourceleadUrl ='${sourceleadUrl}';
		var requirementId = $("#requirementsList").val();
		var sowType = $("#contractType").val();
		$.ajax({
	        type : "GET",
	        url: sourceleadUrl+"/getSubmissionsForSOWByRequirementId?requirementId="+requirementId+"&sowType="+sowType,    
	         success :function(data) {
	              if(data != null && data != 'undefined' && data != "" && data != '[]'){
	            	  var json = JSON.parse(data);
	            	  var result = "";
	            	  result = result + '<option value="">--- Select Candidate ---</option>';
	                  for (var i = 0; i < json.length; i++) {
	                 	 var opt = json[i]; 
	                 	 result = result +  '<option value="'+opt.id+'"> '+opt.candidateName+'</option>';
	                  }
	                  $("#submissionOffersList").empty();
	                  $("#submissionOffersList").append(result);
	                  $("#submissionOffersList").chosen();
	              	  $("#submissionOffersList").trigger('chosen:update');
	              }
	              document.getElementById("offeredSubmissionDiv").style.display="block";
	              $('#submissionOffersList').removeAttr('data-validation-optional');
	  			  $('#submissionOffersList').attr("data-validation","required");
	       },
	    });

	}
</script>


<script type="text/javascript">
function getRewardType(rewardType){
	
	 $("#rewardType").val(rewardType);
	 
	 $("#percentageRewardValue").val('');
	 $("#flatRewardValue").val('');
	 
	 $('#percentageReward').find('p.jquery_form_error_message').remove();
	 $('#flatReward').find('p.jquery_form_error_message').remove();
	 
	if(rewardType == 'Percentage'){
		document.getElementById("percentageReward").style.display="";
		document.getElementById("flatReward").style.display="none";
		document.getElementById("flatRateType").style.display="none";
		
		document.getElementById("percentageBtn").className = 'btn btn-sm btn-primary active'
	    document.getElementById("flatBtn").className = 'btn btn-sm btn-primary'
    	$('#percentageRewardValue').attr("data-validation","required validate_float");  
		$('#percentageRewardValue').removeAttr('data-validation-optional');
	    $('#flatRewardValue').removeAttr('data-validation');
	    $('#flatRewardValue').attr("data-validation-optional","true"); 
	    	
	    
	}
	
	if(rewardType == 'Flat'){
		/* document.getElementById("crowd_rate").style.display=""; */
		document.getElementById("flatReward").style.display="";
		document.getElementById("flatRateType").style.display="";
		document.getElementById("percentageReward").style.display="none";
		
		document.getElementById("percentageBtn").className = 'btn btn-sm btn-primary'
		document.getElementById("flatBtn").className = 'btn btn-sm btn-primary active'
		$('#flatRewardValue').attr("data-validation","required validate_float");
		$('#flatRewardValue').removeAttr('data-validation-optional');
		$('#percentageRewardValue').removeAttr('data-validation');
		$('#percentageRewardValue').attr("data-validation-optional","true"); 
		
	}
}
</script>

<script>
function getPercentageValue(){
	var percentageValue = document.getElementById("percentageRewardValue").value;
	if(percentageValue > 100 || percentageValue < 0){
		/* $("#releaseToCWBtn").attr("disabled", "disabled"); */
	    $('#percentageReward').find('p.jquery_form_error_message').remove(); 
	   
	    if(percentageValue < 0){
	    	$("input#percentageRewardValue").after("<p class='jquery_form_error_message'>Percentage value should not in negitive</span>");
	    }else{
	    	$("input#percentageRewardValue").after("<p class='jquery_form_error_message'>Percentage value not more than 100</span>");
	    }
	    
		$('input#percentageRewardValue').attr('record-exist','yes');
		 if(percentageValue < 0){
			 $('input#percentageRewardValue').attr('record-exist-errorMsg','Percentage value should not in negitive'); 
		 }else{
			 $('input#percentageRewardValue').attr('record-exist-errorMsg','Percentage value not more than 100'); 
		 }
		
	}else{
		 /* $("#releaseToCWBtn").removeAttr("disabled"); */
		 $('input#percentageRewardValue').removeAttr( "record-exist record-exist-errormsg");
		 $('#percentageReward').find('p.jquery_form_error_message').remove(); 
		 $( "#releaseToCWBtn" ).removeAttr("style");
		 $( "#releaseToCWBtn" ).click(function() {
		 if ($(this).validate(false, validationSettings)){
		 }
		});
	}
	
}
</script>


<script type="text/javascript">
function getTaxRewardType(rewardType){
	
	 $("#taxRewardType").val(rewardType);
	 
	 $("#taxPercentageRewardValue").val('');
	 $("#taxFlatRewardValue").val('');
	 
	 $('#taxPercentageReward').find('p.jquery_form_error_message').remove();
	 $('#taxFlatReward').find('p.jquery_form_error_message').remove();
	 
	if(rewardType == 'Percentage'){
		document.getElementById("taxPercentageReward").style.display="";
		document.getElementById("taxFlatReward").style.display="none";
		document.getElementById("taxFlatRateType").style.display="none";
		
		document.getElementById("taxPercentageBtn").className = 'btn btn-sm btn-primary active'
	    document.getElementById("taxFlatBtn").className = 'btn btn-sm btn-primary'
    	$('#taxPercentageRewardValue').attr("data-validation","required validate_float");  
		$('#taxPercentageRewardValue').removeAttr('data-validation-optional');
	    $('#taxFlatRewardValue').removeAttr('data-validation');
	    $('#taxFlatRewardValue').attr("data-validation-optional","true"); 
	    	
	    
	}
	
	if(rewardType == 'Flat'){
		/* document.getElementById("crowd_rate").style.display=""; */
		document.getElementById("taxFlatReward").style.display="";
		document.getElementById("taxFlatRateType").style.display="";
		document.getElementById("taxPercentageReward").style.display="none";
		
		document.getElementById("taxPercentageBtn").className = 'btn btn-sm btn-primary'
		document.getElementById("taxFlatBtn").className = 'btn btn-sm btn-primary active'
		$('#taxFlatRewardValue').attr("data-validation","required validate_float");
		$('#taxFlatRewardValue').removeAttr('data-validation-optional');
		$('#taxPercentageRewardValue').removeAttr('data-validation');
		$('#taxPercentageRewardValue').attr("data-validation-optional","true"); 
		
	}
}
</script>

<script>
function getTaxPercentageValue(){
	var percentageValue = document.getElementById("taxPercentageRewardValue").value;
	if(percentageValue > 100 || percentageValue < 0){
		/* $("#releaseToCWBtn").attr("disabled", "disabled"); */
	    $('#taxPercentageReward').find('p.jquery_form_error_message').remove(); 
	   
	    if(percentageValue < 0){
	    	$("input#taxPercentageRewardValue").after("<p class='jquery_form_error_message'>Percentage value should not in negitive</span>");
	    }else{
	    	$("input#taxPpercentageRewardValue").after("<p class='jquery_form_error_message'>Percentage value not more than 100</span>");
	    }
	    
		$('input#taxPercentageRewardValue').attr('record-exist','yes');
		 if(percentageValue < 0){
			 $('input#taxPercentageRewardValue').attr('record-exist-errorMsg','Percentage value should not in negitive'); 
		 }else{
			 $('input#taxPercentageRewardValue').attr('record-exist-errorMsg','Percentage value not more than 100'); 
		 }
		
	}else{
		 /* $("#releaseToCWBtn").removeAttr("disabled"); */
		 $('input#taxPercentageRewardValue').removeAttr( "record-exist record-exist-errormsg");
		 $('#taxPercentageReward').find('p.jquery_form_error_message').remove(); 
		/*  $( "#releaseToCWBtn" ).removeAttr("style");
		 $( "#releaseToCWBtn" ).click(function() {
		 if ($(this).validate(false, validationSettings)){
		 }
		}); */
	}
	
}
</script>
<script>
function changeSaveButton(){
	 var approverId = $("#approver").val();
	if(approverId !='' && approverId != null){
		document.getElementById("save_approver_template").style.display="block";
		document.getElementById("save_template").style.display="none";
	}else{
		document.getElementById("save_approver_template").style.display="none";
		document.getElementById("save_template").style.display="block";
	}
}
</script>

<script>
function flush(){
	document.getElementById("contractTitle").value="";
//document.getElementById("owner").value="";
	document.getElementById("template").value="";
	document.getElementById("requirementsList").value="";
	document.getElementById("submissionOffersList").value="";
	document.getElementById("startDate").value="";
	document.getElementById("endDate").value="";
	
	document.getElementById("orgRelationId").value="";
	
	document.getElementById("vendorContact").value="";
	document.getElementById("clientOrgRelationId").value="";
	document.getElementById("clientContact").value="";
	document.getElementById("approver").value="";
	document.getElementById("description").value="";
	
	document.getElementById("hoursPerWeek").value="";
	document.getElementById("overTimeHours").value="";
	document.getElementById("totalHoursEstimated").value="";
	document.getElementById("overtimeRate").value="";
	document.getElementById("overtimeRateType").value="";
	
	document.getElementById("invoiceFrequency").value="";
	document.getElementById("taxFlatRateType").value="";
	document.getElementById("submissionOffersList").value="";
	
	
	
	$("newcontract_form").validate().resetForm();
}
</script>

<script>
function cancelContract(){
	var sourceleadUrl = '${sourceleadUrl}';
	location.replace(sourceleadUrl+"/contractListbyStatus");
}

</script>

<script>
function validateContractTitle(){
	var contractTitle = $('#contractTitle').val();
	var contractType = $('#contractType').val();
	 var sourceleadUrl ='${sourceleadUrl}';
	 if(contractTitle != null && contractTitle != "" && contractType != null && contractType != ""){
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/validateContractTitle?${_csrf.parameterName}=${_csrf.token}&contractTitle="+contractTitle+"&contractType="+contractType, 
		        success: function(data) {
		        	if(data == 'true'){
						  $("input#contractTitle").after("<p class='jquery_form_error_message'>"+contractTitle+" is Already Existed</span>");
						  $("#save_template").attr("disabled", "disabled");
		        	}
		        	else{
		        		 $('input#contractTitle').removeAttr( "record-exist record-exist-errormsg");
		        		 $('#titleDiv').find('p.jquery_form_error_message').remove(); 
		        		 $("#save_template" ).addClass('button').val('Save Template');
                        $("#save_template").removeAttr("disabled");
		        	}
		        	}
		        });
	 }
	
}

</script>

<!-- Footer Area -->
<jsp:include page="../masterFooter.jsp" />
<!-- Footer Area -->


