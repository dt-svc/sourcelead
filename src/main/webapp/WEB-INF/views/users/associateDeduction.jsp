  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 
<script type="text/javascript">
var value = [];
var tempIndex = null;
$(document).ready(function(){
	
	$(".add-row").click(function(){
		var i = $("#index").val();
		var deductionType = $("#dedType").val();
		var deductionTypeValue = $('#dedType :selected').text();
		var amount = $("#amt").val();
		if(!value.includes(deductionTypeValue)){
			value.push(deductionTypeValue);
			if(deductionType != null && deductionType != '' && deductionType != 'undefined' && amount != null && amount != '' && amount != 'undefined'){
				var method = $("#mthd").val();
				var markup = "";
				if(tempIndex >= 0 && tempIndex != null){
					markup = '<tr id="row'+tempIndex+'"><td><input type="hidden" id="deductionType" name="deductionDTOList['+tempIndex+'].deductionType" value="'+deductionType+'"/><span id="dedTypeVal'+tempIndex+'"> '+deductionTypeValue+'</span></td><td><input type="hidden" id="method" name="deductionDTOList['+tempIndex+'].method" value="'+method+'" />'+ method +'</td><td><input type="hidden" id="amount" name="deductionDTOList['+tempIndex+'].amount" value="'+amount+'"/>' + amount + '</td><td><a onclick="deleteRow(\''+tempIndex+'\');"><i class="fa fa-remove text-danger"></i></a></td></tr>';
				}else{
					markup = '<tr id="row'+i+'"><td><input type="hidden" id="deductionType" name="deductionDTOList['+i+'].deductionType" value="'+deductionType+'"/> <span id="dedTypeVal'+i+'">'+deductionTypeValue+'</span></td><td><input type="hidden" id="method" name="deductionDTOList['+i+'].method" value="'+method+'" />'+ method +'</td><td><input type="hidden" id="amount" name="deductionDTOList['+i+'].amount" value="'+amount+'"/>' + amount + '</td><td><a onclick="deleteRow(\''+i+'\');"><i class="fa fa-remove text-danger"></i></a></td></tr>';
					i++;
					tempIndex = null;
				}
				// $("#deductionTrAdd").empty();
				$("#deductionTrAdd").append(markup);
				$("#index").val(i);
			}
		}
	
	});
	// Find and remove selected table rows
	/* $(".delete-row").click(function(){
	$("#deductionTrAdd").find('input[name="record"]').each(function(){
	if($(this).is(":checked")){
	$(this).parents("tr").remove();
	}
	});
	}); */

});

function deleteRow(rowId){
	var dedValue = $("#dedTypeVal"+rowId).html();
	$("#row"+rowId).remove();
	var deleted= value.indexOf(dedValue);
	tempIndex = deleted;
	value.splice(deleted,1);
	
	
	/* var i = $("#index").val();
	$("#deductionTrAdd").find('input[name="record"]').each(function(){
		if($(this).is(":checked")){
			$(this).parents("tr").remove();
			i--;
			$("#index").val(i);
			var deleted= value.indexOf(deductionTypeValue);
			tempIndex = deleted;
			value.splice(deleted,1);
		}
	}); */
}

</script>


<script>
$(document).ready(function(){
getDeductionType(); 
});    
</script>

  <input type="hidden" name="index" id="index" value='0'>
   <div id="tab-4" class="tabsMenu_tabs_inn clearfix">
          <form:form id="associate_deduction_form"  name="associate_deduction_form" method="post">
          	<div class="clearfix row">
              
               <h4 class="col-md-12 clearfix">Deduction</h4>
                
               <div class="minHeightcol5">
               <div class="col-md-5">
               <label class="clearfix">Type</label>
                      <select class="sel" id="dedType" name="dedType" data-validation="required" field-name="Deduction Type" onchange="getDeductionAmount();resetValues();">
                      </select>
                  </div>
                  
                  <div class="col-md-5">
                    <label class="clearfix">Amount</label>
                    <div class="clearfix">
                      <input type="text" class="form-control" id="amt" name="amt" data-validation="required validate_float" field-name="Amount">
                    </div>
                  </div>
                  </div>
                  <div class="col-md-5" >
                    <label class="clearfix">Method</label>
                    <div class="clearfix">
                      <input type="text" class="form-control" id="mthd" name="mthd" value="Fixed" readonly>
                    </div>
                  </div>
                  <div class="clearfix"></div>
                  <div class="col-md-10">
                  	<button type="button" class="btn btn-success btnSuccess pull-right add-row" onclick="validateForm();"><i class="fa fa-plus paddingRight"></i>Add</button>
                  </div>
                <div class="clearfix"></div>
                
                <div class="col-md-10">
               <div class="styleTables mt">
                <table>
                	<thead>
                		<tr>
<!--                 		    <th>Select</th> -->
                			<th>Type</th>
                			<th>Method</th>
                			<th>Amount</th>
                			<th>Action</th>
                		</tr>
                	</thead>
                	<tbody id="deductionTrAdd">
                		<!-- <tr>
                			<td>Type</td>
                			<td>Fixed</td>
                			<td><div class="">$ 80000</div></td>
                		</tr> -->
                	</tbody>
                </table>
				</div>
               </div>
               
                </div>
               					<div class="clearfix"></div>
                  
                  <div class="clearfix footerstyelsNew">
                 <div class="col-md-6">
                 <input type="button" value="Previous" onclick="shiftToTaxTab();" class="btn btn-default">
			     </div>
                  <div class="col-md-6">
                   <div class="pull-right">
                   <input value="Skip" class="btn btn-default mr10" onclick="skipAssociateDeductionJsp();" type="button">
                    <input value="Save & Continue" class="btn btn-success btnSuccess" type="button" id="associate_deduction_save_button" onclick="saveAssociateDeduction();">
                    <input value="Cancel" class="btn btn-default ml10" onclick="javascript:window.location.href = '/app/usersList'" type="button">
                    </div>
                  </div>
                </div>
<!--                  <button type="button" class="delete-row">Delete Row</button> -->
                 </form:form>
            </div>
          	
     
<script type="text/javascript">
function validateForm(){
	  if ($('#associate_deduction_form').validate(false, validationSettings)){  
		 /*  saveAssociateDeduction(); */
	       return true; 
	   }else{
		return false;
	  } 
}
$('body').on('blur', '#associate_deduction_form', function() {
	$("#associate_deduction_form").showHelpOnFocus();
	$("#associate_deduction_form").validateOnBlur(false, validationSettings);  
});  
</script>   
 <div id="deductionPopup"></div>
 <script>
 function saveAssociateDeduction(){
	 var userId = $("#associateId").val();  
	 var saveType = "New";
	 var dedType = $("#dedType").val();
	 var deductionType = $("#deductionType").val();
	 if(deductionType != null && deductionType != '' && deductionType != "undefined" && dedType != null && dedType != '' && dedType != "undefined"){
		var formData = getFormData("associate_deduction_form");
		$("#associate_deduction_save_button").attr("disabled", "disabled");
		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
     $( "#associate_deduction_save_button" ).addClass('button').val('Processing..');
     $.ajax({
			type : "POST",
 	    data: formData,
			url : sourceleadUrl+"/saveAssociateDeduction?"+tokenParamName+"="+tokenValue+"&userId="+userId+"&saveType="+saveType,
			success : function(result) {
				 if (result && result.response === "INVALID_DATA") {
	                    $("#associate_deduction_save_button").removeAttr("disabled");
	                    $("#associate_deduction_save_button").removeAttr('button').val('Save');
	                    showError(result.responseMessage);
	                } else if (result.response === "AWKWARD") {
	                    location.href = "/errorPage";
	                } else if (result.response === "SUCCESS") {
	                	/* $("#associateId").val(result.object.userId); */
						var associateContribution = document.getElementById("associateContribution");
						document.getElementById("associateContribution").removeAttribute("class"); 
						associateContribution.href="#tab-5"; 
						$("#associateContribution").click();
						$("#associate_deduction_save_button").removeAttr("disabled");
						$("#associate_deduction_save_button").addClass('button').val('Save & Continue');
	                } else if (result.indexOf('Sign In') !== -1) {
						location.replace(sourceleadUrl);
					} else {
	                    $("#associate_deduction_save_button").removeAttr("disabled");
	                    $("#associate_deduction_save_button").addClass('button').val('Save');
	                    showError("Operation failed, please try again");
	                }
					 
			},
		});	
	}else{
		$("#deductionPopup").empty();
		var result = "";
		result = result +"	<div class='project_inn_mask'></div>";
		result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
		result = result +"		<p>Please add atleast one deduction type to save</p>";
		result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
		result = result +"		<b href='javascript:void(0)'><input class='u_can_color' type='button'  value='Ok' onClick='deductionPopup();' style='width:100%'></b>";
		result = result +"	</div>";
		$("#deductionPopup").append(result);
		$("#remove_user_pop").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
	}
		  
   
 }
 </script>
 
 <script>
function resetValues(){
	$('#amt').val('');
	$('#mthd').val('');
}
</script>
 
 <script>
function shiftToTaxTab(){
	var associates = document.getElementById("associateTax");
	document.getElementById("associateTax").removeAttribute("class"); 
	associates.href="#tab-3"; 
	$("#loading").removeAttr('style');
	if($("#allowEmployeeToFill").is(':checked')){
	    $("#associate_tax_button" ).addClass('button').val('Proceed To Next Step');
	}
	
    $("#associate_tax_button").removeAttr("disabled");
    $("#associateTax").click();
}
</script>
 
 
<script type="text/javascript">
function deductionPopup(){
	$("#remove_user_pop, .project_inn_mask").fadeOut();
}
</script>

 <script>
 function getDeductionType(){
	 $.ajax({
			type : "POST",
			url: sourceleadUrl+"/getAllDeductionType?"+tokenParamName+"="+tokenValue+"",
			success :function(data) {
			var json = data.data;
			var output = "";
			output = output + "<option value=''>Select Deduction Type</option>";
			for (var i=0; i<json.length; i++)
			{
			var opt = json[i];
			/* if(orgRelationId == opt.id){ */
				/* var selected = 'selected'; */
			/* 	output = output + "<option value='" +opt.id+ "'"+selected+">"+opt.orgName+"</option>";
			}else{ */
				output = output + "<option value='" +opt.id+ "'>"+opt.payrollDeductionType+"</option>";
			/* } */
			
			}
			$("#dedType").empty();
			$("#dedType").append(output);
			$('#dedType').trigger("chosen:updated");
			
		},
		});
 }
 </script>
 
 <script>
 function getDeductionAmount(){
	 var dedType = $("#dedType").val();
	 $.ajax({
			type : "POST",
			url: sourceleadUrl+"/getDeductionAmount?"+tokenParamName+"="+tokenValue+"&dedType="+dedType,
			success :function(data) {
				$("#mthd").val(data.payrollDeductionMethod);
				$("#amt").val(data.deductionAmount); 
		},
		});
 }
 </script>
 
 <script>
 function skipAssociateDeductionJsp(){
	    var associateContribution = document.getElementById("associateContribution");
		document.getElementById("associateContribution").removeAttribute("class"); 
		associateContribution.href="#tab-5"; 
		$("#associateContribution").click();
 }
 </script>