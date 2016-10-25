<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<link rel="stylesheet" href="resources/css/chosen.min.css">
<script src="resources/js/chosen.jquery.min.js"></script>

<link href="resources/css/jquery-ui.css" rel="stylesheet">
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<script src="resources/js/formvalidator.js"></script>
<link rel="stylesheet" href="resources/css/style.css" />

<link rel="stylesheet" href="resources/css/jquery.timepicker.css">
<script src="resources/js/jquery.timepicker.js"></script> 


<!-- For add New Interview -->
<script>
function addOffer(requirementId){
	
	var sourceleadUrl ='${sourceleadUrl}';
	var result = "";
    
	result= result + '	          <div class="add_inter">';
	
	result = result + '<form id="addOffer_form" name="addOffer_form" >';
	result = result + '<input type="hidden" id="maxOfferRowNum" name="maxOfferRowNum" value="1" />';
	result = result + '<input type="hidden" id="reqId" name="requirementId" value='+requirementId+'>';
	
	result= result + '	            <div class="add_inter_head"> <span>Add Offer</span><a href="javascript:void(0)" onClick="addOfferRow(this);"><i class="fa fa-plus"></i> Add row</a>';
	result= result + '	              <div class="clearfix"></div>';
	result= result + '	            </div>';
	
	result= result + '	            <table cellpadding="0" cellspacing="0" id="addOfferTbl">';
	result= result + '	              <tbody>';
	result= result + '	                <tr id="divTsRow0">';
	result= result + '	                  <th>Applicant</th>';
	result= result + '	                  <th>Date of Joining</th>';
	result= result + '	                  <th>Rate / Salary</th>';
	result= result + '	                  <th>Position Type</th>';
	result= result + '	                  <th>Attachment</th>';
	result = result + '<th class="action">Action</th>';
	result= result + '	                </tr>';
	
	result= result + '	              </tbody>';
	result= result + '	            </table>';
	
	result= result + '	          <div class="row pad2">';
	result= result + '	            <div class="col-md-12 pad1">';
	result = result +'				  <button type="submit" id="add_offer" class="btn btn-sm btn-primary mr">Submit</button>';
	result= result + '	              <button type="submit" class="btn btn-sm btn-danger" onclick="cancelAddOffer();" >Cancel</button>';
	result= result + '	            </div>';
	result= result + '	          </div>';
	
	result = result + '</form>';
	result= result + '	          </div>';
							
	$("#addOfferDiv").empty();
	$("#addOfferDiv").append(result);
	
	$(".Datepicker").datepicker();
	
	$(".add_offer_wrap").addClass("add_offer_wrap_active");
	$(".side_mask").fadeIn(200);
	$("body").css('overflow-y', 'hidden');
	
	addOfferRow();
	
}
</script>


<script>
    $(".addOffer_close, .side_mask").click(function() {
    	$(".add_offer_wrap").removeClass("add_offer_wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
	});	
</script>

<script>
function loadRemiderDiv(){
	$(".reminder-div").slideToggle(100);
}
</script>



<div id="divOfferTd1" style="display: none; float: left;">
       <select multiple class="chosen-select blminwidth-50" id="REPLACEROWIDapplicant" name="REPLACEROWIDapplicant" field-name="Applicant" data-validation="required" >
			
	  </select>
</div>


<div id="divOfferTd3" style="display: none; float: left;">
   	 <div class="positionRelative float">
           <select class="form-control" id="REPLACEROWIDPaymentType" name="REPLACEROWIDPaymentType"  >
	           <option>Rupee</option>
	           <option>Dollar</option>
           </select>
     </div>
     <div class="positionRelative float">
     	  <input type="text" value="" class="form-control input-sm" id="REPLACEROWIDAmount" name="REPLACEROWIDpaymentTypAmount" >
     </div>
   	 <div class="positionRelative float">
           <select class="form-control" id="REPLACEROWIDPaymentMode" name="REPLACEROWIDPaymentMode">
           <option>Yearly</option>
           <option>Monthly</option>
           </select>
     </div>
</div> 

<div id="divOfferTd4" style="display: none; float: left;">
 	<select class="form-control" id="REPLACEROWIDPositionType" name="REPLACEROWIDPositionType">
	                      <option>Fulltime</option>
	                      <option>Parttime</option>
	                      <option>C2C</option>
	</select>
</div>	              
<div id="divOfferTd5" style="display: none; float: left;">
	<div class="input-group">
                  	<label class="input-group-btn"> <span class="btn btn-primary file-btn"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
                    	<input type="file" style="display: none;" multiple id="uploadBtn"  class="upload"></span>
		</label>
		<input type="text" class="form-control file1" readonly id="uploadFile" placeholder="Files are not selected..." id="REPLACEROWIDOfferAttachment" name="REPLACEROWIDOfferAttachment" >
     </div>                 
</div>

 <div id="divOfferTd6" style="display: none; float: left;">
	 <div>
         <a href="#" onclick="removeOfferRow(REPLACEROWID);"><i class="glyphicon glyphicon-trash deme"></i></a>
    </div>
</div> 
	

<script type="text/javascript">
	function addOfferRow() {
		var checkMaxTsRowNum = $("#maxOfferRowNum").val();
		var tbl = document.getElementById("addOfferTbl");
		var maxRowNum = parseInt($("input[name = 'maxOfferRowNum']").val());
		$("input[name = 'maxOfferRowNum']").val(maxRowNum + 1);
		
		var row = tbl.insertRow(maxRowNum);
		row.setAttribute("id", "divTsRow" + maxRowNum);
		
		var cell0 = row.insertCell(0);
		var divTsTd1Str = document.getElementById("divOfferTd1").innerHTML;
		divTsTd1Str = divTsTd1Str.replace(/REPLACEROWID/g, (maxRowNum));
		cell0.innerHTML = divTsTd1Str;
		
		var cell1 = row.insertCell(1);
		cell1.innerHTML = "<div class='positionRelative date'><input class='form-control input-sm Datepicker' placeholder='Date' name='"+maxRowNum+"joiningDate' id='"+maxRowNum+"joiningDate'  readonly='readonly'  type='text' data-validation='required' field-name='Joining Date' > <span class='input-group-addon input-groupAddon'><i class='fa fa-calendar'></i></span> </div>";
		$(".Datepicker").datepicker(); 
				
		var cell2 = row.insertCell(2);
		var divTsTd3Str = document.getElementById("divOfferTd3").innerHTML;
		divTsTd3Str = divTsTd3Str.replace(/REPLACEROWID/g, (maxRowNum));
		cell2.innerHTML = divTsTd3Str; 
		
		var cell3 = row.insertCell(3);
		var divTsTd4Str = document.getElementById("divOfferTd4").innerHTML;
		divTsTd4Str = divTsTd4Str.replace(/REPLACEROWID/g, (maxRowNum));
		cell3.innerHTML = divTsTd4Str; 
			
		
		var cell4 = row.insertCell(4);
		var divTsTd5Str = document.getElementById("divOfferTd5").innerHTML;
		divTsTd5Str = divTsTd5Str.replace(/REPLACEROWID/g, (maxRowNum));
		divTsTd5Str = divTsTd5Str.replace(/REPLACEROWID/g, (maxRowNum));
		cell4.innerHTML = divTsTd5Str; 
		
		var cell5 = row.insertCell(5);
		var divTsTd6Str = document.getElementById("divOfferTd6").innerHTML;
		divTsTd6Str = divTsTd6Str.replace(/REPLACEROWID/g, (maxRowNum));
		cell5.innerHTML = divTsTd6Str;
		
		
		
		$(".add_offer_wrap").addClass("add_offer_wrap_active");
		$(".side_mask").fadeIn(200);
		$("body").css('overflow-y', 'hidden');
		
		
		getShortListAndSubmittedAndInterviewedCandidates(maxRowNum);
		

	    $("#"+maxRowNum+"applicant").chosen();
		
	    $("#"+maxRowNum+"applicant").on('change', function (e) {
	    	  getUpdatedSubmittedAndShortListedAndInterviewedCandidates();
	    }); 
		
}		
</script>

<script type="text/javascript">
	function removeOfferRow(rowId) {
		var rowNum = $("#divTsRow"+rowId).val();
		var maxRowNum = parseInt(rowNum);
		 document.getElementById("divTsRow"+rowId).style.display = 'none';
		 document.getElementById("divTsRow"+rowId).value= '';
		 var  rId = "divTsRow"+rowId;
		  var r = document.getElementById("addOfferTbl").rows[rId];
          var count = 2*(r.cells.length);
		    for(i=0;i<=count;i++){
		        r.deleteCell(0);
		    } 
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
	(function($) {
		$.fn.serializeFormJSON = function() {
			var o = {};
			var a = this.serializeArray();
			$.each(a, function() {
				if (o[this.name]) {
					if (!o[this.name].push) {
						o[this.name] = [ o[this.name] ];
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
function cancelAddOffer(){
	 $(".add_offer_wrap").removeClass("add_offer_wrap_active");
	 $(".side_mask").fadeOut(200);
 	 $("body").css('overflow-y', 'auto');
}

</script>  


<script type="text/javascript">
   	$('body').on('submit', '#addOffer_form', function(e){  
	 if ($(this).validate(false, validationSettings)){
        e.preventDefault();
        var frm = $('#addOffer_form').serializeFormJSON();
        var con = JSON.stringify(frm);
        con = con.replace(/[{}]/g, "");
        var jsonData = encodeURIComponent(con);
        var sourceleadUrl ='${sourceleadUrl}'; 
       
        $("#add_offer").attr("disabled", "disabled");
 		$("#add_offer" ).addClass('button').val('Processing..'); 
 		
 		
   	    $.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/saveOffer?${_csrf.parameterName}=${_csrf.token}&jsonData="+jsonData,
	        success: function(result) {
	        		
	        	    $(".add_inter_wrap").removeClass("add_offer_wrap_active");
	        		$(".side_mask").fadeOut(200);
	        		$("body").css('overflow-y', 'auto');
	        },
	    });   
        return true;
      }
      else{
      	return false;	
         } 
    });
    	
    $('#addOffer_form').showHelpOnFocus();
    $('#addOffer_form').validateOnBlur(false, validationSettings);  
  
  </script>
 
<script type="text/javascript">
 function getShortListAndSubmittedAndInterviewedCandidates(rowId) { 
	 var existingApplicantsIds = [];    
	 for(i=rowId ; i > 0; i--){
		 var applicant = $("#"+(i)+"applicant").val();
		 if(applicant != null && applicant != '' && applicant != 'undefined'){
			 existingApplicantsIds.push(applicant);
		 }
	 }
	
	 var sourceleadUrl ='${sourceleadUrl}';
	 var requirementId = $("#reqId").val();
     
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getAllSubmittedAndShortListAndInterviewededCandidates?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId+"&existingApplicantsIds="+existingApplicantsIds, 
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	var output = "";
		        	
		        	for (var i=0; i<json.length; i++)
					{
			        		var opt = json[i];
			        		output = output + "<option value='" +opt.id+ "'>"+opt.candidateName+"</option>";
					}
		        	$("#"+rowId+"applicant").empty();
		        	$("#"+rowId+"applicant").append(output);
		        	$("#"+rowId+"applicant").trigger("chosen:updated");
			       
		        },
		    });
	
 }
</script> 

<script type="text/javascript">
 function getUpdatedSubmittedAndShortListedAndInterviewedCandidates() { 
	 var rowId = $("#maxOfferRowNum").val();
	 var existingApplicantsIds = [];    
	 for(i=rowId ; i > 0; i--){
		 var applicant = $("#"+(i)+"applicant").val();
		 if(applicant != null && applicant != '' && applicant != 'undefined'){
			 existingApplicantsIds.push(applicant);
		 }
	 }
	 var sourceleadUrl ='${sourceleadUrl}';
	 var requirementId = $("#reqId").val();
     
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getAllSubmittedAndShortListAndInterviewededCandidates?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId+"&existingApplicantsIds="+existingApplicantsIds, 
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	var output = "";
		        	
		        	for (var i=0; i<json.length; i++)
					{
			        		var opt = json[i];
			        		output = output + "<option value='" +opt.id+ "'>"+opt.candidateName+"</option>";
					}
		        	for(var i=rowId-1 ; i > 0; i--){
		        		 var internalOutput = "";
		        		 var applicantIdList= $("#"+(i)+"applicant").val();
		        		 var arr = jQuery.makeArray(applicantIdList);
                         for(var j = 0; j < arr.length; j++) {
                        	 var label = $("#"+(i)+"applicant option[value='"+arr[j]+"']").text();
                        	 internalOutput = internalOutput+"<option value='" +arr[j]+ "' selected>"+label+"</option>";
                         }
		        		$("#"+(i)+"applicant").empty();
		        		$("#"+(i)+"applicant").append(internalOutput);
			        	$("#"+(i)+"applicant").append(output);
			        	$("#"+(i)+"applicant").trigger("chosen:updated");
		       	    }
			       
		        },
		    });
 }
</script>    
  
  
<jsp:include page="../masterFooter.jsp" />