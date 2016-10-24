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
	result = result + '<input type="hidden" id="maxOfferRowNum" name="maxOfferRowNum" value="2" />';
	
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
	result= result + '	                </tr>';
	
	
	result= result + '	                <tr id="divTsRow0">';
	result= result + '	                  <td><select multiple class="chosen-select" id="1applicant"  name="1applicant">';
	result= result + '	                      <option>India</option>';
	result= result + '	                      <option>USA</option>';
	result= result + '	                      <option>UK</option>';
	result= result + '	                      <option>Jaipan</option>';
	result= result + '	                      <option>China</option>';
	result= result + '	                      <option>Austrlia</option>';
	result= result + '	                    </select></td>';
	result= result + '	                  <td><div class="positionRelative date">';
	result= result + '	                      <input type="text" value="" id="1dateOfJoining"  name="1dateOfJoining"  class="form-control input-sm Datepicker" placeholder="Date">';
	result= result + '	                      <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>';
	result= result + '	                      </div>';
	result= result + '	                  </td>';
	
	result= result + '	                  <td class="clearfix">';
	result= result + '	                  	  <div class="positionRelative float">';
	result= result + '	                          <select class="form-control" id="1PaymentType"  name="1PaymentType">';
	result= result + '	                          <option>Rupee</option>';
	result= result + '	                          <option>Dollar</option>';
	result= result + '	                          </select>';
	result= result + '	                      </div>';
	result= result + '	                      <div class="positionRelative float"><input type="text" value="" class="form-control input-sm" id="1Amount"  name="1Amount" ></div>';
	result= result + '	                  	  <div class="positionRelative float">';
	result= result + '	                          <select class="form-control" id="1PaymentMode"  name="1PaymentMode">';
	result= result + '	                          <option>Yearly</option>';
	result= result + '	                          <option>Monthly</option>';
	result= result + '	                          </select>';
	result= result + '	                      </div>';
	result= result + '					  </td>   ';               
	result= result + '	                  <td><select class="form-control" id="1PositionType"  name="1PositionType">';
	result= result + '	                      <option>Fulltime</option>';
	result= result + '	                      <option>Parttime</option>';
	result= result + '	                      <option>C2C</option>';
	result= result + '	                    </select>';
	result= result + '	                  </td>';
	result= result + '					  <td>';
	result= result + '						  <div class="input-group">';
	result= result + '	                      	<label class="input-group-btn"> <span class="btn btn-primary file-btn"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File';
	result= result + '	                        	<input type="file" style="display: none;" multiple id="uploadBtn"  class="upload"></span>';
	result= result + '							</label>';
	result= result + '							<input type="text" id="1OfferAttachment"  name="1OfferAttachment" class="form-control file1" readonly id="uploadFile" placeholder="Files are not selected...">';
	result= result + '	                     </div>    ';              
	result= result + '	                  </td>';
	result= result + '	                </tr>';  
	
	result= result + '	              </tbody>';
	result= result + '	            </table>';
	
	result= result + '	          <div class="row pad2">';
	result= result + '	            <div class="col-md-12 pad1">';
	result = result +'				  <button type="submit" id="add_offer" class="btn btn-sm btn-primary mr">Submit</button>';
	/* result= result + '	              <button type="submit" class="btn btn-sm btn-primary mr">Submit</button>'; */
	result= result + '	              <button type="submit" class="btn btn-sm btn-danger">Cancel</button>';
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
	
	$(".chosen-select").chosen('destroy');
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
			
         
		$(".chosen-select").chosen();
		});
	
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
	    <select multiple class="chosen-select" id="REPLACEROWIDapplicant"  name="REPLACEROWIDapplicant" field-name="Applicant" data-validation="required">
	                      <option>India</option>
	                      <option>USA</option>
	                      <option>UK</option>
	                      <option>Jaipan</option>
	                      <option>China</option>
	                      <option>Austrlia</option>
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
		
		
		$(".add_offer_wrap").addClass("add_offer_wrap_active");
		$(".side_mask").fadeIn(200);
		$("body").css('overflow-y', 'hidden');
		
		$(".chosen-select").chosen('destroy');
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
				
	         
			$(".chosen-select").chosen();
			});
		
}		
</script>

<script type="text/javascript">
	function removeInterviewRow(rowId) {
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
function cancelAddInterviewDiv(){
	 $(".add_inter_wrap").removeClass("add_inter_wrap_active");
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
 		
 		alert("con"+con);
 		
   	    $.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/saveOffer?${_csrf.parameterName}=${_csrf.token}&jsonData="+jsonData,
	        success: function(result) {
	        	    $(".add_inter_wrap").removeClass("add_inter_wrap_active");
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
<!--  
<script type="text/javascript">
 function getShortListCandidates(rowId) {
	// var existingInterviewers = $("#"+rowId+"location").val();
	 var existingInterviewers = document.getElementById("1applicant").value;
	// alert("existingInterviewers"+existingInterviewers);
	 var sourceleadUrl ='${sourceleadUrl}';
	 var requirementId = $("#reqId").val();
     
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getAllShortListCandidates?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId, 
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
 function getInterviewers(rowId) {
	 
	 var sourceleadUrl ='${sourceleadUrl}';
	  $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getAllInterviewers?${_csrf.parameterName}=${_csrf.token}", 
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	var output = "";
		        	
		        	for (var i=0; i<json.length; i++)
					{
			        		var opt = json[i];
			        		output = output + "<option value='" +opt.id+ "'>"+opt.firstName+" "+opt.lastName+"</option>";
					}
		        	$("#"+rowId+"interviewer").empty();
		        	$("#"+rowId+"interviewer").append(output);
		        	$("#"+rowId+"interviewer").trigger("chosen:updated");
			       
		        },
		    });
	
 }
</script>  
 -->
<jsp:include page="../masterFooter.jsp" />