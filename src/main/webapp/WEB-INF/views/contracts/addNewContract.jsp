<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script src="resources/js/formvalidator.js"></script>
<!-- <script src="resources/js/chosen.jquery.min.js"></script> --> 


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
                  <select class="chosen-select form-control" id="contractType" name="contractType" onchange="contractTypeLabels();getContractTemplates();" data-validation="required" field-name="Contract Type">
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
              <label class="col-md-4">Select template<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8">
                  <select class="chosen-select form-control" id="template" name="template"  data-validation="required" field-name="Template">
                  </select>
              </div>
            </div>  
                     
            <div class="form-group">
              <label class="col-md-4">Contract title<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8">
                <input type="text" class="form-control" id="contractTitle" name="contractTitle" value="" data-validation="required validate_Space validate_length length1-250" field-name="Contract Title">
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
                   <!--  <option>Select Owner</option>
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
                  <select class="chosen-select form-control" id="approver" name="approver">
                  <option>Select Owner</option>
                  <c:forEach var="user" items="${userList}">
	                     <option value="${user.id}" ${user.id  == SourceLeadWeb_userId ? 'selected' : ''} >${user.firstName} ${user.lastName}</option>
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
            
        </div>
      </div>
      
      
<div class="row newContrBill">
          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
           <h2>Billing Details</h2>
            <div class="form-group">
              <label class="col-md-4">Billing Amount<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8">
					<div class="row">
					  <div class="col-lg-8">
					  	<input type="text" class="form-control" id="billingAmount" name="billingAmount" value="" placeholder="Enter Amount" data-validation="required validate_Space validate_float validate_length length1-50" field-name="Billing Amount">
					  </div>
					  <div class="col-lg-4">
						<select class="form-control selectpicker" id="billingPer" name="billingPer">
						  <option>India</option>
						  <option>USA</option>
						  <option>UK</option>
						</select>
					  </div>
					</div>
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-4">invoice frequency<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8">
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
              <div class="col-md-8">
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
           <!--  <div class="form-group">
              <label class="col-md-4">Payment frequency<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8">
              	<input type="text" class="form-control" name="Company Name" value="">
            </div>
            </div> -->
            <div class="form-group">
              <label class="col-md-4">Tax</label>
              <div class="col-md-8">
					<div class="row">
					  <div class="col-lg-4" id = "taxTypeDiv">
						<select class="form-control selectpicker" id="taxType"  name="taxType" onchange = "selectTaxType();clearSearchKey();">
						 <option value="Percentage">%</option>
						  <option value="INR">INR</option>
						  
						</select>
					  </div>					
					  <div class="col-lg-8" id = "taxDiv">
					  	<input type="text" class="form-control" id="tax" name="tax" value="" onchange = "selectTaxType();">
					  </div>
					 <!--  <span class="hidden">Enter Project Id</span> -->
					</div>
              </div>
		</div>      
            
        </div>
      </div>      
      
      <div class="form-footer clearfix contrFoot">
        <div class="col-lg-12">
          <div class="pull-right">
            <input type="button" value="Draft" id="draft_template" onclick="saveNewContract('draft'); " class="btn btn-primary">
            <input type="button" value="Save" class="btn btn-success ml10">
            <input type="button" value="Cancel" class="btn btn-danger ml10">
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
       			output = output + "<option value='" +opt.vendorUser.id+ "'>"+opt.spocName+"</option>";
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
	output = output + "<option value='" +opt.clientUser.id+ "'>"+opt.ownerName+"</option>";
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
	
 	if(contractType == "Client MSA"){
		document.getElementById("clientDiv").style.display="block";
		document.getElementById("clientContactDiv").style.display="block";
		document.getElementById("vendorDiv").style.display="none";
		document.getElementById("vendorContactDiv").style.display="none";
		}
		if(contractType == "Vendor MSA"){
		document.getElementById("clientDiv").style.display="none";
		document.getElementById("clientContactDiv").style.display="none";
		document.getElementById("vendorDiv").style.display="block";
		document.getElementById("vendorContactDiv").style.display="block";
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
if(contractType == "Client MSA"){
$('#orgRelationId').removeAttr('data-validation');
$('#orgRelationId').attr('data-validation-optional',"true");
$('#vendorContact').removeAttr('data-validation');
$('#vendorContact').attr('data-validation-optional',"true");
}
if(contractType == "Vendor MSA"){
$('#clientOrgRelationId').removeAttr('data-validation');
$('#clientOrgRelationId').attr('data-validation-optional',"true");
$('#clientContact').removeAttr('data-validation');
$('#clientContact').attr('data-validation-optional',"true");
}
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




<!-- Footer Area -->
<jsp:include page="../masterFooter.jsp" />
<!-- Footer Area -->


