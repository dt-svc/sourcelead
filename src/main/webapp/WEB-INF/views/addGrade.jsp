<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script type="text/javascript">
$(document).ready(function(){
	getGradesList();
});
</script>

                                                                           
<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Grades List</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home </a>&raquo;<a href="${appPath}/orgRolesList"> Roles List </a> &raquo;  Grades List </div>
    </section>
    
    
    <div class="project_wrap">
    <div class="project_wrap_table backgroundWhite" id="gradesListGrid">
    
<div class="reportHead positionRelative clearfix">
      <ul class="pull-right list-unstyled list-inline">
			<li class="addcon-pop">
				<a href="javascript:void(0)" title="" class="newAdBtn mr5" onclick="showGradePopUp();">+ Grade</a>
            </li>      
	       <li class=""><input type="text" class="repSearchBar" placeholder='Search' id='gradesDataTableSearch'/><span class="repSearchIcon" onClick="gradeDataTableList();"></span></li>
        </ul>
      </div>     
              
          <div id="gradesList" class="filter_position positionRelative datatable_filter"></div> 
      </div> 
      </div>
    
  <div class="popupWrap" id="gradePopup" role="dialog">
    <div id="addGradeLoading"></div>
      <!-- Modal content-->
        <div class="popupHead">Add/Edit Grade <i class="closePop pull-right popClose" onclick="closeGradePopUp();"></i></div>
        
        <div class="popupContent">
        	<form:form class="clearfix"  id="addgrade_form"  name="addgrade_form" method="POST">
	      		<input type="hidden" id="uuid" name="uuid">
	      		<div class="form-group clearfix mb" id="gradeDiv">
		           <label class="">Grade <b class="valStar">*</b></label>
		           <div class="">
		                 <input type="text" id="gradeName" name="gradeName" class="form-control" onblur="validateGrade();" data-validation="required validate_Space validate_AlphaNumber validate_length length1-20" field-name="Grade"/>
		           </div>
	           </div>
	           
	           <div class="form-group clearfix mb" id="designationDiv">
		           <label class="">Designation</label>
		           <div class="">
		                 <input type="text" id="designation" name="designation" class="form-control" data-validation-optional="true" data-validation="validate_Space validate_AlphaNumber validate_length length1-100"/>
		           </div>
	           </div>
	           
	           <div class="form-group clearfix mb">
		           <label class="">Min Wage/Salary</label>
		            <div class="">
		          <div class="col-md-4 noPad dropdown-select drpdownCurrency">
						<select id="minSalaryRateType" name="minSalaryRateType" data-required-optional="true">
						<option ${SourceLeadWeb_currencyCode  == 'INR' ? 'selected' : ''} value="INR">INR</option>
						<option ${SourceLeadWeb_currencyCode  == 'AUD' ? 'selected' : ''} value="AUD">AUD</option>
						<option ${SourceLeadWeb_currencyCode  == 'USD' ? 'selected' : ''} value="USD">USD</option>
						<option ${SourceLeadWeb_currencyCode  == 'GBP' ? 'selected' : ''} value="GBP">GBP</option>
						<option ${SourceLeadWeb_currencyCode  == 'SGD' ? 'selected' : ''} value="SGD">SGD</option>
						<option ${SourceLeadWeb_currencyCode  == 'EUR' ? 'selected' : ''} value="EUR">EUR</option>
						</select>
				</div>
	
		          <div class="col-md-4 noPad inputRate" id="minSalDiv">
		                 <input type="text" id="minSalary" name="minSalary" class="form-control" data-validation-optional="true" data-validation="validate_Space validate_float validate_length length1-10" field-name="Min Wage/Salary" />
		           </div>
		           
		          <div class="col-md-4 noPad drpdownMonthly dropdown-select" data-validation-optional="true">
					<select id="minSalaryRatePer" name="minSalaryRatePer">
					<option value="Hourly">Hourly</option>
					<option value="Weekly">Weekly</option>
					<option value="Bi-weekly">Bi-weekly</option>
					<option value="Semi-Monthly">Semi-Monthly</option>
					<option value="Monthly">Monthly</option>
					<option value="Bi-Monthly">Bi-Monthly</option>
					<option value="Yearly" selected>Yearly</option>
					</select>
				</div>
				</div>
			</div>
			
			
			<div class="form-group clearfix minHeight40">
		           <label class="">Mid Wage/Salary</label>
		           <div class="">
		          <div class="col-md-4 noPad dropdown-select drpdownCurrency">
					<select id="midSalaryRateType" name="midSalaryRateType" data-validation-optional="true">
						<option ${SourceLeadWeb_currencyCode  == 'INR' ? 'selected' : ''} value="INR">INR</option>
						<option ${SourceLeadWeb_currencyCode  == 'AUD' ? 'selected' : ''} value="AUD">AUD</option>
						<option ${SourceLeadWeb_currencyCode  == 'USD' ? 'selected' : ''} value="USD">USD</option>
						<option ${SourceLeadWeb_currencyCode  == 'GBP' ? 'selected' : ''} value="GBP">GBP</option>
						<option ${SourceLeadWeb_currencyCode  == 'SGD' ? 'selected' : ''} value="SGD">SGD</option>
						<option ${SourceLeadWeb_currencyCode  == 'EUR' ? 'selected' : ''} value="EUR">EUR</option>
					</select>
				</div>
	
		          <div class="col-md-4 noPad inputRate" id="midSalDiv">
		                 <input type="text" id="midSalary" name="midSalary" class="form-control" data-validation-optional="true"  data-validation="validate_Space validate_float validate_length length1-10" field-name="Mid Wage/Salary"/>
		           </div>
		           
		          <div class="col-md-4 noPad drpdownMonthly dropdown-select" data-validation-optional="true">
					<select id="midSalaryRatePer" name="midSalaryRatePer">
					<option value="Hourly">Hourly</option>
					<option value="Weekly">Weekly</option>
					<option value="Bi-weekly">Bi-weekly</option>
					<option value="Semi-Monthly">Semi-Monthly</option>
					<option value="Monthly">Monthly</option>
					<option value="Bi-Monthly">Bi-Monthly</option>
					<option value="Yearly" selected>Yearly</option>
					</select>
				</div>
				</div>
			</div>
			
			
			<div class="form-group clearfix minHeight40">
		           <label class="">Max Wage/Salary</label>
		           <div class="">
		          <div class="col-md-4 noPad dropdown-select drpdownCurrency">
					<select id="maxSalaryRateType" name="maxSalaryRateType" data-validation-optional="true">
						<option ${SourceLeadWeb_currencyCode  == 'INR' ? 'selected' : ''} value="INR">INR</option>
						<option ${SourceLeadWeb_currencyCode  == 'AUD' ? 'selected' : ''} value="AUD">AUD</option>
						<option ${SourceLeadWeb_currencyCode  == 'USD' ? 'selected' : ''} value="USD">USD</option>
						<option ${SourceLeadWeb_currencyCode  == 'GBP' ? 'selected' : ''} value="GBP">GBP</option>
						<option ${SourceLeadWeb_currencyCode  == 'SGD' ? 'selected' : ''} value="SGD">SGD</option>
						<option ${SourceLeadWeb_currencyCode  == 'EUR' ? 'selected' : ''} value="EUR">EUR</option>
					</select>
				</div>
	
		          <div class="col-md-4 noPad inputRate" id="maxSalDiv">
		                 <input type="text" id="maxSalary" name="maxSalary" class="form-control" data-validation-optional="true" data-validation="validate_Space validate_float validate_length length1-10" field-name="Max Wage/Salary"/>
		           </div>
		           
		          <div class="col-md-4 noPad drpdownMonthly dropdown-select" data-validation-optional="true">
					<select id="maxSalaryRatePer" name="maxSalaryRatePer">
					<option value="Hourly">Hourly</option>
					<option value="Weekly">Weekly</option>
					<option value="Bi-weekly">Bi-weekly</option>
					<option value="Semi-Monthly">Semi-Monthly</option>
					<option value="Monthly">Monthly</option>
					<option value="Bi-Monthly">Bi-Monthly</option>
					<option value="Yearly" selected>Yearly</option>
					</select>
				</div>
				</div>
			</div>
			
			</form:form>
		</div>
		
		<div class="popupFoot text-right">
			<button type="submit" id="create_grade" onclick="createGrade();" class="slBtn md colorBtn">Add</button>					
			<input type="button" value="Cancel" class="slBtn md popClose" onclick="closeGradePopUp();">
		</div>	        
        	
        </div>
<div id="choicepopUp"></div>    
 </section>
 
 
 <script>
 function showGradePopUp() {
	    $(".popupWrap").fadeIn(100);
	    $(".mask").show();
	}
 
 function closeGradePopUp() {
	    
	    $('#gradeName').val('');
	    $('#designation').val('');
	   
	    $('#minSalary').val('');
	    $('#minSalaryRateType').val('${SourceLeadWeb_currencyCode}');
	    $('#minSalaryRatePer').val('Yearly');
	    
	    $('#midSalary').val('');
	    $('#midSalaryRateType').val('${SourceLeadWeb_currencyCode}');
	    $('#midSalaryRatePer').val('Yearly');
	    
	    $('#maxSalary').val('');
	    $('#maxSalaryRateType').val('${SourceLeadWeb_currencyCode}');
	    $('#maxSalaryRatePer').val('Yearly');
	    
	    $('#uuid').val(''); 
	    
	    $('#gradeDiv').find('p.jquery_form_error_message').remove();
	    $('#designationDiv').find('p.jquery_form_error_message').remove();
	    $('#minSalDiv').find('p.jquery_form_error_message').remove();
	    $('#midSalDiv').find('p.jquery_form_error_message').remove();
	    $('#maxSalDiv').find('p.jquery_form_error_message').remove();
	    
	    $(".popupWrap").fadeOut(100);
	    $(".mask").hide();
		
	}
 </script>
 
 
 <script type="text/javascript">
 function createGrade() {
	   if ($('#addgrade_form').validate(false, validationSettings)){ 
		
		    var formData = getFormData("addgrade_form");
		    $("#addGradeLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
            var sourceleadUrl = '${sourceleadUrl}';
            
		    $.ajax({
 		    	type: "POST",
 		    	data: formData,
 		    	 url: sourceleadUrl+"/addNewGrade?${_csrf.parameterName}=${_csrf.token}", 
 		    			 success: function(result) {
 		        	
 		    				if (result.response === "AWKWARD") {
				        	    location.href = "/errorPage";
				        	} else if (result.response === "SUCCESS") {
				        		if(result.object === "DataMissing") {
				        			$("#addGradeLoading").removeAttr("style");
									showError("Invalid data");
								}else{
									$("#addGradeLoading").removeAttr("style");
									closeGradePopUp()
									gradeDataTableList();
									
								}
				        	} else if (result.indexOf('Sign In') !== -1) {
				        		location.replace(sourceleadUrl);
				        	} else {
				        		$("#addGradeLoading").removeAttr("style");
				        	    showError("Operation failed, please try again");
				        	}
 		        	
 			        
 		        },
 		    });
            
	        return true;
		
	  }else{
		return false;
	  }
    }
 
   $('body').on('blur', '#addgrade_form', function() {
		$("#addgrade_form").showHelpOnFocus();
		$("#addgrade_form").validateOnBlur(false, validationSettings);  
   });
  
 
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
 
 <script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };

        
</script> 
 
 
 <script>
 function getGradesList(){
	 
	 $("#gradesList").empty();
	 
	  var sourceleadUrl ='${sourceleadUrl}';
	  $("#gradesLoading").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	  
	    var result = "";
	 	
	 	result = result + '<table id="gradesListTable" class="display" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result +'	    <th>Grade Name</th>';
		result = result +'	    <th>Designation</th>';
	  	result = result +'      <th>Min Wage/Salary</th>';
	  	result = result +'      <th>Mid Wage/Salary</th>';
	  	result = result +'      <th>Max Wage/Salary</th>';
	  	result = result +'       <th>Actions</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
	    result = result +"</table>";
	    $("#gradesList").empty();
	    $("#gradesList").append(result);
	    
	    gradeDataTableList();
 }
 </script>
 
 <script>
function gradeDataTableList(){
	 var sourceleadUrl ='${sourceleadUrl}';
	 var searchValue = $("#gradesDataTableSearch").val();
	 searchValue = searchValue ? searchValue : "";
	 var url = sourceleadUrl+"/getAssociatesGradesList?searchValue="+searchValue;
	 gradesPagination(url,sourceleadUrl);
	 $("#gradesLoading").removeAttr("style");
}
</script>



<script>
function gradesPagination(url,sourceleadUrl){
	var colIndex = 0;
    $('#gradesListTable').DataTable( { 
	  "processing": true,
      "serverSide": true,
      "searching": false,
      "ajax": $.fn.dataTable.pipeline( {
          url: url,
          method: 'POST',
          pages: 1
        }),
        "columns": [
            { "data": "gradeName" },
            { "data": "designation"},
            { "data": "minSalary"},
            { "data": "midSalary" },
            { "data": "maxSalary" },
            { "data": "actions"}
        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "gradeName",
	    	  	"render": function(data, type, opt, meta) {
	    	  		  var result = "";
	    	  	   	 result = result +"<td>";
                	 result = result +"<div class='width300px'>";
                	 result = result +"<div class='tbldata-dotdot-big pull-left' title='"+opt.gradeName+"'>"+opt.gradeName+"</div>";
                	 result = result +"<div>";
                	 result = result +"</td>";
		             return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "designation",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  var designation = '';
             	 
             	 if(opt.designation != null && opt.designation != '' && opt.designation != 'undefined'){
             		designation = opt.designation;
             	 }else{
             		designation = "----";
             	 }
            	  
             	 result = result +"<td><div><span>"+designation+"</span></div></td>";
	             return result;
              		
              }
	      
	          },{
		    	  "targets": colIndex++,
	              "data": "minSalary",
	              "render": function ( data, type, opt, meta ) {
	            	  var result = "";
	            	  var minSalary = "";
	            	  
	            	  if(opt.minSalary != null && opt.minSalary != '' && opt.minSalary != 'undefined'){
	            		  minSalary = opt.minSalaryRateType+" "+opt.minSalary+"/"+opt.minSalaryRatePer;
	               	  }else{
	               		minSalary = "----";
	               	  }
	            	  
	            	  result = result +"<td><div><span>"+minSalary+"</span></div></td>";
	            	  return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "midSalary",
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	     var midSalary = "";
		            	  
		            	  if(opt.midSalary != null && opt.midSalary != '' && opt.midSalary != 'undefined'){
		            		  midSalary = opt.midSalaryRateType+" "+opt.midSalary+"/"+opt.midSalaryRatePer;
		               	  }else{
		               		midSalary = "----";
		               	  }
		            	  
		            	  result = result +"<td><div><span>"+midSalary+"</span></div></td>";
	            		 return result;
	              }
		     }, {
		    	  "targets": colIndex++,
	              "data": "maxSalary",
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    var maxSalary = "";
		            	  
		            	  if(opt.maxSalary != null && opt.maxSalary != '' && opt.maxSalary != 'undefined'){
		            		  maxSalary = opt.maxSalaryRateType+" "+opt.maxSalary+"/"+opt.maxSalaryRatePer;
		               	  }else{
		               		maxSalary = "----";
		               	  }
		            	  
		            	  result = result +"<td><div><span>"+maxSalary+"</span></div></td>";
	            		return result;
	              }
		     }, {
		    	  "targets": colIndex++,
	              "data": "actions",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    result = result	+ '<td>';
	            	    result = result	+ '<div class="tblicons">';
	            	    result = result	+ '	<i class="glyphicon glyphicon-pencil paddingRight" title="Edit" onclick="editGrade(\''+opt.uuid+'\');"></i>';
	            	    result = result	+ '	<i class="glyphicon glyphicon-trash paddingLeft" title="Delete" onclick="deleteGrade(\''+opt.uuid+'\',\''+opt.gradeName+'\');"></i>';
	            	    result = result	+ ' </div>';
	            	    result = result	+ '</td>';
	            	    return result;
	              }
		     }],
		     
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#gradesListTable").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#gradesDataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
   		    $('#gradesDataTableSearch').parent().find('p.jquery_form_error_message').remove();
   	    	var searchData = $('#gradesDataTableSearch').val();
   	    	var regex =  new RegExp(/[\\-]/g);
   	    	if(regex.test(searchData) == false){
   	    		gradeDataTableList();
   	    	}else{
   	    		$("#gradesDataTableSearch").after("<p class='jquery_form_error_message'><span><font color='red'>Enter valid Search Data</font></span>");
   	    	}
   	    	$('#gradesDataTableSearch').val(searchData);
    	   }
    	});
}

</script>


<script>
function validateGrade(){
	var gradeName =  $('#gradeName').val();
	var uuid =  $('#uuid').val();
	 var sourceleadUrl = '${sourceleadUrl}';
	 if(gradeName){
		 $("#create_grade").attr("disabled", "disabled");
		 $("#addGradeLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		 
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/validateGradeName?"+tokenParamName+"="+tokenValue+"&gradeName="+gradeName+"&uuid="+uuid, 
		        success: function(data) {
		        	
		        	
		        	if(data == "false"){
		        		  $('#gradeDiv').find('p.jquery_form_error_message').remove();
						  $("input#gradeName").after("<p class='jquery_form_error_message'>"+gradeName+" is Already Existed</span>");
						  document.getElementById("gradeName").setAttribute('record-exist','yes');
						  document.getElementById("gradeName").setAttribute('record-exist-errorMsg',gradeName+' is Already Existed');
						  $("#addGradeLoading").removeAttr("style");
		        	}
		        	else{
		        		 $('input#gradeName').removeAttr( "record-exist record-exist-errormsg");
		        		 $('#gradeDiv').find('p.jquery_form_error_message').remove(); 
                         $("#create_grade").removeAttr("disabled");
                         $("#addGradeLoading").removeAttr("style");
		        	}
		        },
		    });
	 }
	
}
</script>


<script>
function editGrade(uuid){
	showGradePopUp();
    var sourceleadUrl = '${sourceleadUrl}';
    $("#addGradeLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
    $.ajax({
	    	type: "POST",
	    	 url: sourceleadUrl+"/getGradeDetails?${_csrf.parameterName}=${_csrf.token}&uuid="+uuid, 
	    			 success: function(result) {
	    				    $('#gradeName').val(result.gradeName);
	    				    
	    				    if(result.gradeName != null && result.gradeName != '' && result.gradeName != 'undefined'){
	    				    	 $('#designation').val(result.designation);
	    				    }
	    				    
	    				    if(result.minSalary != null && result.minSalary != '' && result.minSalary != 'undefined'){
	    				    	 $('#minSalary').val(result.minSalary);
	    				    	 $("select[name=minSalaryRateType]").val(result.minSalaryRateType);
	    				    	 $("select[name=minSalaryRatePer]").val(result.minSalaryRatePer);
	    				    }
	    				   
	    				   
	    				    if(result.midSalary != null && result.midSalary != '' && result.midSalary != 'undefined'){
	    				    	 $('#midSalary').val(result.midSalary);
	    				    	 $("select[name=midSalaryRateType]").val(result.midSalaryRateType);
	    				    	 $("select[name=midSalaryRatePer]").val(result.midSalaryRatePer);
	    				    }
	    				    
	    				    
	    				    if(result.maxSalary != null && result.maxSalary != '' && result.maxSalary != 'undefined'){
	    				    	 $('#maxSalary').val(result.maxSalary);
	    				    	 $("select[name=maxSalaryRateType]").val(result.maxSalaryRateType);
	    				    	 $("select[name=maxSalaryRatePer]").val(result.maxSalaryRatePer);
	    				    }
	    				    
	    				    $('#uuid').val(result.uuid); 
	    				 
	    				 $("#addGradeLoading").removeAttr("style");
		        
	        },
	    });
	
}
</script>


<script>
function deleteGrade(uuid,gradeName){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Are your sure to delete <strong>"+gradeName+"</strong> ?</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +'		<b href="javascript:void(0)"><input id="confirm-id" type="button" value="Ok" onclick="deleteAssociateGrade(\''+uuid+'\');"><input class="u_can_color" type="button"  value="Cancel" onClick="cancel();"></b>';
	result = result +"	</div>";
	$("#choicepopUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}


function cancel(){
	$("#choicepopUp").empty();
	$(".project_inn_mask").fadeOut(200);
}

function deleteAssociateGrade(uuid){
	$("#confirm-id").attr("disabled", "disabled");
	var sourceleadUrl = '${sourceleadUrl}';
	 $.ajax({
	    	type: "POST",
	    	 url: sourceleadUrl+"/deleteGradeDetails?${_csrf.parameterName}=${_csrf.token}&uuid="+uuid, 
	    			 success: function(result) {
	    				  
	    				    $("#choicepopUp").empty();
	    					$(".project_inn_mask").fadeOut(200);
	    					gradeDataTableList();
	        },
	    });
}

</script>
 
 
 <jsp:include page="masterFooter.jsp" />
    