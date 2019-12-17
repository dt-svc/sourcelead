<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<!-- these files for autocomplete with tagit -->

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%-- <jsp:include page="candidatesQuickView.jsp"></jsp:include>
 --%>
<style>
.addUser-org { overflow-x: auto; }
</style>

<%-- 
     @author : sarath kumar.
     this script is for display candidate education tab data.
     1.usage     : used at clientSideBar().
--%>


<script>
var atsUrl ='${SourceLeadWeb_atsUrl}';
function candidateLca(candidateId){
	 var candidateType = '${candidateType}';
	var result = "";
	if(window.candidateStatusGlobal == '0'){
		result = result  +'    <div class="project_tabs_inn_head addUser-org">  ';
	}else{
		result = result  +'    <div class="project_tabs_inn_head addUser-org">  ';
	}
	
	
	/* result  = result + '<div class="clearfix" style="height:35px;">';
	result  = result + '<div class="pull-left">';
	result  = result + '<ul class="list-unstyled list-inline">';
	result  = result + '<li class="cursorPointer" onClick="immigrationTabHandler(this);candidatePassport('+candidateId+');" value="passport">Passport</li><li value="visa" onClick="immigrationTabHandler(this);candidateLca('+candidateId+');" class="cursorPointer">Visa</li><li onClick="immigrationTabHandler(this)" class="cursorPointer" value="lca">LCA</li>';
	result  = result + '</ul>';
	result  = result + '</div>';
	result  = result + '<div class="clearfix"></div>'; */
	
	result  = result + '<div class="success_pop" id="selector4" style="display: none;">';
    result  = result + '<i class="fa fa-check-circle"></i><span id="eduMsgDiv"></span>';
    result  = result + '</div>';
/* 	if(window.candidateStatusGlobal == '0'){
		if(candidateType == "Candidates"){
			'<sec:authorize access="hasAnyRole(\'ROLE_ADD_CANDIDATE_EDUCATION\',\'ROLE_CROWDSOURCING_CANDIDATES\')">'; 	
			result = result  +'<a href="javascript:void(0)"  class="addUser-org" ></a> ';
			'</sec:authorize>';
		}else{
			'<sec:authorize access="hasRole(\'ROLE_ADD_ASSOCIATE_EDUCATION_DETAILS\')">'; 	
			result = result  +'<a href="javascript:void(0)"  class="addUser-org" ></a> ';
			'</sec:authorize>';
		}
		
	}else{
		if(candidateType == "Candidates"){
			'<sec:authorize access="hasAnyRole(\'ROLE_ADD_CANDIDATE_EDUCATION\',\'ROLE_CROWDSOURCING_CANDIDATES\')">';
			result = result  +'<span class="pad-sea padding_right"><a href="javascript:void(0)"  id="assignLca" class="addUser-org" ><i class="glyphicon glyphicon-plus"></i>Assign LCA</a></span>';
			'</sec:authorize>';
		}else{
			'<sec:authorize access="hasRole(\'ROLE_ADD_ASSOCIATE_EDUCATION_DETAILS\')">';
			result = result  +'<span class="pad-sea padding_right"><a href="javascript:void(0)"  id="assignLca" class="addUser-org" ><i class="glyphicon glyphicon-plus"></i>Assign LCA</a></span>';
			'</sec:authorize>';
		}
		
	} */
		result = result  +'     <div id="popUpDiv" class="edit-details col-md-4"> ';
		result = result  +'     </div>  ';
		result  = result + '   <div class="project_tabs_inn_table vendor_wrap_inn_table">';
		
		
		result  = result + '   <div id="candidateLcaList" class="datatable_filter"></div>';
		
		result  = result + '   </div>';
	result = result+'	    </div>';

	$("#tab-1").empty();
    $("#tab-2").empty(); 
    $("#tab-3").empty();  
    $("#tab-4").empty();  
    $("#tab-5").empty();  
    $("#tab-6").empty();  
    $("#tab-7").empty();
    $("#tab-8").empty();
    $("#tab-9").empty();
    $("#tab-4").empty();
    /* $("#tab-12").empty();
    $("#tab-12").append(result);   */
    $("#tab-12").show();
    $("#passportli").removeClass("current");
    $("#visali ").removeClass("current");
    $("#lcali").addClass("current");
    
    $("#immigrationCandidate").empty();
    $("#immigrationCandidate").append(result);
    candidateLcaList(candidateId);
    $("#assignLca").click(function(){
		$(".popMask").fadeIn(200);
		$("#popLcaView").fadeIn(100);
	});
  //Tab script
	//$(".tabcontent").hide();
	//immigrationTabHandler();
}
</script>

<!-- Show success message after form submission script -->
<script>
	function eduMsgFadeinFunction() {
		$("#selector4").fadeIn(200);
        $('#selector4').delay(2000).fadeOut('slow');
	}
</script> 
<!-- End -->


<%-- 
     @author : sarath kumar.
     this script is for get candidateLcaList.
     1.usage     : used in candidateEducation().
     2.parameter : csrf token,candidateId .
     3.ajax url implementation in CandidateListRestController.
--%>
<script>
function candidateLcaList(candidateId){
	var candidateType = '${candidateType}';
	 var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  canLcaDatePattern = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  canLcaDatePattern = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  canLcaDatePattern = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  canLcaDatePattern = 'YYYY-DD-MM'; 
	  }
	 $.ajax({  
          type : "GET",
          url: atsUrl+"/getAssociateLcaDetails?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,        
          success :function(response) {
        	  	if (response && response.response === "INVALID_DATA") {
        		    showError(response.responseMessage);
        		} else if (response && response.response === "INVALID_USER") {
        		    showError(response.responseMessage);
        		} else if (response.response === "AWKWARD") {
        		    location.href = "/errorPage";
        		} else if (response.response === "SUCCESS") {
        			$("#candidateLcaList").empty();
               	 var result = "";
               	 result = result +"<table id='candidateLcaPagination' class='display' cellspacing='0' width='100%' >"; 
                    result = result +"<thead>"; 
                    result = result +"  <tr>"; 
                    result  = result + '           <th>LCA Number</th>';
                	 result  = result + '           <th>LCA Title</th>';
                	 result  = result + '           <th>Location</th>';	
                	 result  = result + '           <th>Prevailing Wage</th>';
                	 result  = result + '           <th>Valid From <br>(<small class="custom-small">'+canLcaDatePattern+'</small>)</th>';
                	 result  = result + '           <th>Valid To <br>(<small class="custom-small">'+canLcaDatePattern+'</small>)</th>';
                	 result  = result + '           <th>LCA Status</th>';
                	 result  = result + '           <th>Status</th>';
                    result = result +"  </tr>"; 
                    result = result +"</thead>";
                    result = result +"<tbody>"; 
                    
                    for (var i = 0; i < response.data.length; i++) { 
                   	 var opt = response.data[i];  
                   	 result  = result + '         <tr>';
                   	 if(opt.lcaNo != null && opt.lcaNo != '' && opt.lcaNo != 'undefined'){
                   	 result  = result + '<td><div><span title = "'+opt.lcaNo+'">'+opt.lcaNo+'</span></div></td>';
                   	    }else{
                   	    	 result  = result + ' <td><div><span>----</span></div></td>';
                   	    }
                   	 if(opt.lcaTitle != null && opt.lcaTitle != '' && opt.lcaTitle != 'undefined'){
                   	 result  = result + ' <td><div><span title="'+opt.lcaTitle+'">'+opt.lcaTitle+'</span></div></td>';
                   	 }else{
                   		 result  = result + ' <td><div><span>----</span></div></td>';
                   	 }
                   	 if(opt.location != null && opt.location != '' && opt.location != 'undefined'){
                       	 result  = result + ' <td><div><span title="'+opt.location+'">'+opt.location+'</span></div></td>';
                      }else{
                       	  result  = result + ' <td><div><span>----</span></div></td>';
                      }
                   	 if(opt.prevailingWage != null && opt.prevailingWage != '' && opt.prevailingWage != 'undefined'){
                       	 result  = result + ' <td><div><span title="'+opt.prevailingWage+'">'+opt.prevailingWage+'</span></div></td>';
                      }else{
                       	  result  = result + ' <td><div><span>----</span></div></td>';
                      }
                   	 if(opt.startDate != null && opt.startDate != '' && opt.startDate != 'undefined'){
                   	 result  = result + ' <td><div><span>'+opt.startDate+'</span></div></td>';
                   	 }else{
                   		 result  = result + ' <td><div><span>----</span></div></td>';
                   	 }
                   	 if(opt.endDate != null && opt.endDate != '' && opt.endDate != 'undefined'){ 
                   	 result  = result + '<td><div><span>'+opt.endDate+'</span></div></td>';
                   	 }else{
                   		 result  = result + '<td><div><span>----</span></div></td>';
                   	 }
                   	 if(opt.lcaStatus != null && opt.lcaStatus != '' && opt.lcaStatus  != 'undefined'){
                   	 result  = result + '<td><div><span>'+opt.lcaStatus+'</span></div></td>';
                   	 }else{
                   		 result  = result + '<td><div><span>----</span></div></td>'; 
                   	 }
                   	if(opt.status != null && opt.status != '' && opt.status  != 'undefined'){
                      	 result  = result + '<td><div><span>'+(opt.status == 1?"Active":"In-Active")+'</span></div></td>';
                      	 }else{
                      		 result  = result + '<td><div><span>----</span></div></td>'; 
                      	 }
                   	 result  = result + '</tr>';
                     } 
                    result = result +" </tbody>";
                    result = result +"</table>";
                    $("#candidateLcaList").append(result); 
                   lcaPagination();
        		} else if (response.indexOf('Sign In') !== -1) {
        			location.replace(sourceleadUrl);
        		} else {
        		    showError("Operation failed, please try again");
        		}
        },
     }); 
}
</script>

<%-- 
     @author : sarath kumar.
     this script is for prepare pagination.
     1.usage     : used in candidateLcaList().
--%>

<script>
function lcaPagination(){  
    $('#candidateLcaPagination').DataTable( { 
    	"searching": false,
   	 	"paging":   false,
        "ordering": false,
        "info":     false,
      	"oLanguage": {
     	    "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
}
</script>


<%-- 
     @author : sarath kumar.
     this script is for remove popUp.
     1.usage   : used in addEducation() function.
--%>


<%-- 
     @author : sarath kumar.
     this script is for shown popUp.
     1.usage   : used in candidateEducation() function.
--%>




<%-- 
     @author : sarath kumar.
     this script is for store candidateEducationstore .
     1.usage   : used in candidateEducation() function.
--%>
<script>
var validationSettings = {
		errorMessagePosition : 'element',
		borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd'
	};
</script>



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


<%-- <div class="clearfix">
<!-- Add LCA From Immigration Summary Starts Here (pop Up) -->

		<!-- popup code start -->
		<form id="lcaSave_Form" name = "lcaSave_Form" method = "post">
		<div class="popUpOut" id="popLcaView">
		<!--hidden id for edit -->
		<!-- <div class="popupHead">
			<h2 class="pull-left mt">Assign LCA</h2><a href="javascript:void(0)" class="fa fa-close popCan mt mr" onclick="closeLcapopup();"></a>
		</div> -->
		
		<div class="popupMid clearfix">

          
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">LCA No<span class="text-danger paddingLeft">*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" name="lcaNo"  id="lcaNumberAuto" class="form-control"  placeholder="Autofill Enabled For 2 Chars" field-name="LCA NO" data-validation="required" >
				</div>
			</div>				          
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Start Date<span class="text-danger paddingLeft">*</span></label>
              	<div class="col-md-8 padding_right">
              	<div class="input-group errorInptgroup" id="lcaStartDateDiv">
				<input type="text" name="startDate" id="lcaStartDate" readonly='true'  class="form-control calendar" placeholder="Start Date" field-name="Start Date" data-validation="required">
				 <span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer" onclick="clearLcaDateValue('startDate');"></i></span>
          	</div>
			</div>	
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">End Date<span class="text-danger paddingLeft">*</span></label>
              	<div class="col-md-8 padding_right">
              	<div class="input-group errorInptgroup" id="lcaEndDateDiv">
				<input type="text" id="lcaEndDate" name="endDate"  readonly='true' class="form-control calendar" placeholder="End Date" field-name="End Date" data-validation="required">
				<span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer" onclick="clearLcaDateValue('endDate');"></i></span>
			</div>
				</div>
			</div>
        <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Location<span class="text-danger">&nbsp;*</span></label>  
              	<div class="col-md-8 padding_right">
              		<input type="text" name= "location" id= "locationlca" field-name= "location" placeholder="Location" onclick="initLocationAuto('location','country','state','city','zipcode','','','','streetAddress');" data-validation="required validate_Space validate_length length2-100" class="form-control" disabled="disabled"/> 
              		   <i class="fa fa-refresh btn btn-primary refresh-icon" title="Refresh" onClick="refreshGoogleApiLocation('locationlca','country','state','city','zipcode','streetAddress');" ></i>         		
				</div>
			</div>	
			
			
			<div class="col-sm-12 noPad mt clearfix">
            <label class="col-md-4 padding_left">Prevailing  Wage <span class="text-danger paddingLeft">*</span> </label>
            <div class="col-md-8 padding_right">
            <div class="input-group er ratetype">
                <div class="input-group-btn">
                   <select class="form-control tblminwidth-75 sel" id="privilingWageCurrency" name="privilingWageCurrency" data-style="btn-primary" >
						<option value="INR" ${privilingWageCurrency == 'INR' ? 'selected' : ''}>INR</option>
						<option value="AUD" ${privilingWageCurrency == 'AUD' ? 'selected' : ''}>AUD</option>
						<option value="USD" ${privilingWageCurrency == 'USD' ? 'selected' : ''}>USD</option>
						<option value="GBP" ${privilingWageCurrency == 'GBP' ? 'selected' : ''}>GBP</option>
						<option value="SGD" ${privilingWageCurrency == 'SGD' ? 'selected' : ''}>SGD</option>
						<option value="EUR" ${privilingWageCurrency == 'EUR' ? 'selected' : ''}>EUR</option> 
					</select>
                </div>
                <input type="text" name="prevailingWage" id="prevailingWage" value="${lcaDTO.prevailingWage}" class="form-control" value="" field-name="Prevailing Wage"
								data-validation="required validate_Space validate_float validate_length length1-8" 
							    placeholder="Wage Or Rate" >
                <div class="input-group-btn ratetype">
                 <select class="form-control tblminwidth-75 sel" id="privilingWageRateType" name="privilingWageRateType" field-name="Rate Per" data-style="btn-primary" >
						 <option  value="">Rate Per</option>
						<option ${lcaDTO.privilingWageRateType == 'Hourly' ? 'selected' : ''} value="Hourly">Hourly</option>
						<option ${lcaDTO.privilingWageRateType == 'Weekly' ? 'selected' : ''} value="Weekly">Weekly</option>
						<option ${lcaDTO.privilingWageRateType == 'Bi-Weekly' ? 'selected' : ''} value="Bi-Weekly">Bi-Weekly</option>
						<option ${lcaDTO.privilingWageRateType == 'Semi-Monthly' ? 'selected' : ''} value="Semi-Monthly">Semi-Monthly</option>
						<option ${lcaDTO.privilingWageRateType == 'Monthly' ? 'selected' : ''} value="Monthly">Monthly</option>
						<option ${lcaDTO.privilingWageRateType == 'Bi-Monthly' ? 'selected' : ''} value="Bi-Monthly">Bi-Monthly</option>
						<option ${lcaDTO.privilingWageRateType == 'Yearly' ? 'selected' : ''} value="Yearly">Yearly</option> 
					</select>
                </div>
              </div>
            
          	</div>
          </div>
          
          <div class="clearfix"></div>
		  <div class="col-md-4 col-md-offset-4">
		   	<div class="mt5 clearfix">
			   <input id="multipleCheckBox123" title="Is Current" name="multiple"  type="checkbox" value = "Y" >
			   <label class="padding_left">Is Current</label>
			</div>
		   </div>
									
		</div>
		
		<div class="clearfix"></div>
		<div class="immiPopFoot mt clearfix">
            <input value="Save" id= "lcaSave_Button" class="" type="button" onclick ="immigration.saveLcaFromImmigration();">
            <input value="Cancel" class="popCan" type="button" onclick="closeLcapopup();">
          </div>		
	</div>
	</div><!-- popup code end -->
	</form>
</div> --%>
<script>
	function closeLcapopup(){
		$(".popMask").fadeOut(200);
		$("#popLcaView").fadeOut(100);
		$("#lcaSave_Form")[0].reset();
		/* $("#lcaId").val(''); */
		$('#popLcaView').find('p.jquery_form_error_message').remove();
	}
	</script>
