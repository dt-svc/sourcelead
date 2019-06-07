<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 
 <script type="text/javascript">
 var pmsUrl ='${SourceLeadWeb_pmsUrl}';
 var atsUrl ='${SourceLeadWeb_atsUrl}';
 var value =[];
 var conTempIndex = null;
    $(document).ready(function(){
    		$(".add-contribution-row").click(function(){
    		var i = $("#index").val(); 
        	var contributionType = $("#contriType").val();
        	var contributionTypeValue = $('#contriType :selected').text();
        	var contributionAmount = $("#contriAmt").val();
        	if(!value.includes(contributionTypeValue)){
        		value.push(contributionTypeValue);
        	if(contributionType != null && contributionType != '' && contributionType != 'undefined' && contributionAmount != null && contributionAmount != '' && contributionAmount != 'undefined'){
                  var contributionMethod = $("#contricontributionMethod").val();
                  var markuptr = "";
                  if(conTempIndex >= 0 && conTempIndex != null){
                	  markuptr = '<tr id="row'+conTempIndex+'"><td><input type="hidden" id="contributionType" name="contributionDTOList["+i+"].contributionType" value="'+contributionType+'"/><span id="conTypeVal'+conTempIndex+'">'+ contributionTypeValue + '</span></td><td><input type="hidden" id="contributionMethod" name="contributionDTOList['+i+'].contributionMethod" value="'+contributionMethod+'" />' + contributionMethod + '</td><td><input type="hidden" id="contributionAmount" name="contributionDTOList['+i+'].contributionAmount" value="'+contributionAmount+'"/>' + contributionAmount + '</td><td><a onclick="deleteRowCont(\''+conTempIndex+'\');"><i class="fa fa-remove text-danger"></i></a></td></tr>';
                  }else{
                	  markuptr = '<tr id="row'+i+'"><td><input type="hidden" id="contributionType" name="contributionDTOList['+i+'].contributionType" value="'+contributionType+'"/><span id="conTypeVal'+i+'">'+ contributionTypeValue + '</span></td><td><input type="hidden" id="contributionMethod" name="contributionDTOList['+i+'].contributionMethod" value="'+contributionMethod+'" />'+ contributionMethod+'</td><td><input type="hidden" id="contributionAmount" name="contributionDTOList['+i+'].contributionAmount" value="'+contributionAmount+'"/>' + contributionAmount + '</td><td><a onclick="deleteRowCont(\''+i+'\');"><i class="fa fa-remove text-danger"></i></a></td></tr>';
                	  i++;
                	  conTempIndex = null;
                  }
                  
                  $("#contribuionTrAdd").append(markuptr);
                  $("#index").val(i);	
        	} 
        	} 
        });
        
        // Find and remove selected table rows
        /* $(".delete-contribution-row").click(function(){
            $("#contribuionTrAdd").find('input[name="contributio-record"]').each(function(){
            	if($(this).is(":checked")){
                    $(this).parents("tr").remove();
                }
            });
        }); */
    });    

function deleteRowCont(rowId){
	var conSeleValue = $("#conTypeVal"+rowId).html();
	$("#row"+rowId).remove();
	var deleted= value.indexOf(conSeleValue);
	conTempIndex = deleted;
	value.splice(deleted,1);
	
	/* var i = $("#index").val(); 
	$("#contribuionTrAdd").find('input[name="contributio-record"]').each(function(){
    	if($(this).is(":checked")){
            $(this).parents("tr").remove();
            i--;
            $("#index").val(i);	
            var deleted = value.indexOf(contributionTypeValue);
            value.splice(deleted,1);
        }
    }); */
}

</script>

<script>
$(document).ready(function(){
	getContributionType(); 
});    
</script>

<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Contribution</h2>
      </div>
      <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo;<a href="${SourceLeadWeb_atsUrl}/userSummaryDetails?tabType=contribution&userCode=${userCode}">Contribution List</a> &raquo; Contribution </div>
    </section>
    
     <div class="content-area content-new clearfix">

<div id="loading"></div>
  <input type="hidden" name="index" id="index" value='0'>
  <div  class="clearfix col-md-12">
          <form:form id="associate_contribution_form"  name="associate_contribution_form" contributionMethod="post">
          	<div class="clearfix row">
              
               <h4 class="col-md-12 clearfix">Contribution</h4>
                
               <div class="minHeightcol5">
               <div class="col-md-5">
               <label class="clearfix">Type</label>
                      <select class="sel" id="contriType" name="contriType" data-validation="required" field-name="Contribution Type" onchange="getContributionAmount(); resValues();" onblur="validateDuplicateContribution();">
                      </select>
                  </div>
                  
                  <div class="col-md-5">
                    <label class="clearfix">Amount</label>
                    <div class="clearfix">
                      <input type="text" class="form-control" id="contriAmt" name="contriAmt" data-validation="required validate_float" field-name="Amount">
                    </div>
                  </div>
                  </div>
                  <div class="col-md-5" >
                    <label class="clearfix">Method</label>
                    <div class="clearfix">
                      <input type="text" class="form-control" id="contricontributionMethod" name="contricontributionMethod" value="Fixed" readonly>
                    </div>
                  </div>
                  <div class="clearfix"></div>
                  <div class="col-md-10">
                  	<button type="button" id="contributionAddButtonId" class="btn btn-success btnSuccess pull-right add-contribution-row" onclick="validateContributionForm();"><i class="fa fa-plus paddingRight"></i>Add</button>
                  </div>
                <div class="clearfix"></div>
                
                <div class="col-md-10">
               <div class="styleTables mt">
                <table id="test">
                	<thead>
                		<tr>
<!--                 		    <th>Select</th> -->
                			<th>Type</th>
                			<th>Method</th>
                			<th>Amount</th>
                			<th>Action</th>
                		</tr>
                	</thead>
                	<tbody id="contribuionTrAdd">
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
			     </div> 
                  <div class="col-md-12">
                   <div class="pull-right">
                    <input value="Save" class="btn btn-success btnSuccess" type="button" id="contribution_save_button" onclick="saveAssociateContribution();">
                    <input value="Cancel" class="btn btn-default ml10" onclick="javascript:window.location.href = '${SourceLeadWeb_atsUrl}/userSummaryDetails?tabType=contribution&userCode=${userCode}'" type="button">
                    </div>
                  </div>
                </div>
<!--                  <button type="button" class="delete-contribution-row">Delete Row</button> -->
                 </form:form>
                 </div>
                 
                 </div>
 </section>
          	
          
          
     
 <script type="text/javascript">
function validateContributionForm(){
	  if ($('#associate_contribution_form').validate(false, validationSettings)){  
		  validateDuplicateContribution();
		 /*  saveAssociateContribution(); */
	       return true; 
	   }else{
		return false;
	  } 
}
$('body').on('blur', '#associate_contribution_form', function() {
	$("#associate_contribution_form").showHelpOnFocus();
	$("#associate_contribution_form").validateOnBlur(false, validationSettings);  
});  
</script>   
  <div id="contributionPopup"></div>
 <script>
 function saveAssociateContribution(){
	 var userCode = '${userCode}';
	 var userId = '${userId}';  
	 var saveType = '${saveType}';
	 var contriType = $("#contriType").val();
	 var contributionType = $("#contributionType").val();
	 if(contributionType != null && contributionType != '' && contributionType != 'undefined' && contriType != null && contriType != '' && contriType != 'undefined'){
		 var formData = getFormData("associate_contribution_form"); 
			$("#contribution_save_button").attr("disabled", "disabled");
			$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
      $( "#contribution_save_button" ).addClass('button').val('Processing..');
      
      $.ajax({
				type : "POST",
  	    data: formData,
				url : pmsUrl+"/saveAssociateContribution?"+tokenParamName+"="+tokenValue+"&userId="+userId+"&saveType="+saveType,
				success : function(result) {
					 if (result && result.response === "INVALID_DATA") {
		                    $("#contribution_save_button").removeAttr("disabled");
		                    $("#contribution_save_button").removeAttr('button').val('Save');
		                    showError(result.responseMessage);
		                } else if (result.response === "AWKWARD") {
		                    location.href = "/errorPage";
		                } else if (result.response === "SUCCESS") {
		                	location.replace(atsUrl+"/userSummaryDetails?tabType=contribution&userCode="+userCode);
		                	/* $("#associateId").val(result.object.userId);
							var associateContribution = document.getElementById("associateContribution");
							document.getElementById("associateContribution").removeAttribute("class"); 
							associateDeduction.href="#tab-5"; 
							$("#associateContribution").click(); */
		                } else if (result.indexOf('Sign In') !== -1) {
							location.replace(pmsUrl);
						} else {
		                    $("#contribution_save_button").removeAttr("disabled");
		                    $("#contribution_save_button").addClass('button').val('Save');
		                    showError("Operation failed, please try again");
		                }
						 
				},
			}); 
		     
	 }else{
		 $("#contributionPopup").empty();
			var result = "";
			result = result +"	<div class='project_inn_mask'></div>";
			result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
			result = result +"		<p>Please add atleast one contribution type to save</p>";
			result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
			result = result +"		<div class='clearfix'><input class='u_can_color' type='button'  value='Ok' onClick='contributionPopup();' style='width:100%;'></div>";
			result = result +"	</div>";
			$("#contributionPopup").append(result);
			$("#remove_user_pop").slideDown(200);
			$(".project_inn_mask").fadeIn(200); 
	 }
		
   
 }
 </script>

<script type="text/javascript">
function contributionPopup(){
	$("#remove_user_pop, .project_inn_mask").fadeOut();
}
</script>

 <script>
 function getContributionType(){
	 $.ajax({
			type : "POST",
			url: pmsUrl+"/getAllContributionType?"+tokenParamName+"="+tokenValue+"",
			success :function(data) {
			var json = data;
			var output = "";
			output = output + "<option value=''>Select Contribution Type</option>";
			for (var i=0; i<json.length; i++)
			{
			var opt = json[i];
			/* if(orgRelationId == opt.id){ */
				/* var selected = 'selected'; */
			/* 	output = output + "<option value='" +opt.id+ "'"+selected+">"+opt.orgName+"</option>";
			}else{ */
				output = output + "<option value='" +opt.contributionId+ "'>"+opt.contributionType+"</option>";
			/* } */
			
			}
			$("#contriType").empty();
			$("#contriType").append(output);
			$('#contriType').trigger("chosen:updated");
			
		},
		});
 }
 </script>
 
 <script>
 function getContributionAmount(){
	 var contributionId = $("#contriType").val();
	 $.ajax({
			type : "POST",
			url: pmsUrl+"/getContributionAmount?"+tokenParamName+"="+tokenValue+"&contributionId="+contributionId,
			success :function(data) {
				$("#contricontributionMethod").val(data.payrollContributionMethod);
				$("#contriAmt").val(data.contributionAmount); 
		},
		});
 }
 </script>
 
 <script>
function resValues(){
	$("#contriAmt").val('');
	$("#contricontributionMethod").val('');
}
</script>
 
 <script>
 function validateDuplicateContribution(){
	 var contriType = $("#contriType").val();
	 var userId = '${userId}'; 
	 $.ajax({
			type : "GET",
			url: pmsUrl+"/validateContribution?"+tokenParamName+"="+tokenValue+"&contriType="+contriType+"&userId="+userId,
			success :function(data) {
				if(data == true){
					$("#contriType").after("<p class='jquery_form_error_message'>This type is Already Existed</span>");
					document.getElementById("contribution_save_button").disabled = true;
					document.getElementById("contributionAddButtonId").disabled = true;
				}else{
					$('#contriType').find('p.jquery_form_error_message').remove();
					document.getElementById("contribution_save_button").disabled = false;
					document.getElementById("contributionAddButtonId").disabled = false;
				}
				
		 },
		});
 }
 </script>
 