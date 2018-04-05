<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<!-- these files for autocomplete with tagit -->

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<style>
.addUser-org {
    overflow-x: auto;
}
</style>

<%-- 
     @author : sarath kumar.
     this script is for display candidate education tab data.
     1.usage     : used at clientSideBar().
--%>
<script>
function professionalCandidateEducation(candidateId){
	
	var result = "";
	result = result  +'    <div class="project_tabs_inn_head addUser-org">  ';
	result  = result + '<div class="success_pop" id="selector4" style="display: none;">';
    result  = result + '<i class="fa fa-check-circle"></i><span id="eduMsgDiv"></span>';
    result  = result + '</div>';
	if(window.candidateStatusGlobal == '0'){
		
		result = result  +'<a href="javascript:void(0)"  class="addUser-org" ><i class="glyphicon glyphicon-plus"></i>Add Education</a> ';
	}else{
		result = result  +'<span class="pad-sea"><a href="javascript:void(0)"  onClick="addEducation('+candidateId+');" class="addUser-org" ><i class="glyphicon glyphicon-plus"></i>Add Education</a></span>';
	}
		result = result  +'     <div id="popUpDiv" class="edit-details col-md-4"> ';
		result = result  +'     </div>  ';
		result  = result + '   <div class="project_tabs_inn_table vendor_wrap_inn_table">';
		
		
		result  = result + '   <div id="candidateEducationList" class="datatable_filter"></div>';
		
		result  = result + '   </div>';
	result = result+'	    </div>';
	  

    $("#tab-1").empty();
    $("#tab-2").empty(); 
    $("#tab-3").empty();  
    $("#tab-4").empty();  
    $("#tab-5").empty();  
    $("#tab-4").append(result);  
	
    candidateEducationList(candidateId);
    
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
     this script is for get candidateEducationList.
     1.usage     : used in candidateEducation().
     2.parameter : csrf token,candidateId .
     3.ajax url implementation in CandidateListRestController.
--%>
<script>
function candidateEducationList(candidateId){
	var candidateType = '${candidateType}';
	 
	 var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  candidateEducationListDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  candidateEducationListDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  candidateEducationListDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  candidateEducationListDate = 'YYYY-DD-MM'; 
	  }
	
	 
	 $.ajax({  
          type : "GET",
          url: sourceleadUrl+"/getCandidateEducationListByCandidateId?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId,        
          success :function(response) {
        	  	if (response && response.response === "INVALID_DATA") {
        		    showError(response.responseMessage);
        		} else if (response && response.response === "INVALID_USER") {
        		    showError(response.responseMessage);
        		} else if (response.response === "AWKWARD") {
        		    location.href = "/errorPage";
        		} else if (response.response === "SUCCESS") {
        			$("#candidateEducationList").empty();
               	 	var result = "";
               	 	result = result +"<table id='candidateEducationPagination' class='display' cellspacing='0' width='100%' >"; 
                    result = result +"<thead>"; 
                    result = result +"  <tr>"; 
                    result  = result + '           <th>Education</th>';
                	 result  = result + '           <th>Specialization</th>';
                	 result  = result + '           <th>Start date<br>(<small class="custom-small">'+candidateEducationListDate+'</small>)</th>';
                	 result  = result + '           <th>End date<br>(<small class="custom-small">'+candidateEducationListDate+'</small>)</th>';
                	 result  = result + '           <th>Score</th>';
                	 result  = result + '           <th>Score Type</th>';
                	 result  = result + '           <th>Institute/University</th>';
                	 result  = result + '           <th>Address</th>';
                	 result  = result + '           <th>Action</th>';
                    result = result +"  </tr>"; 
                    result = result +"</thead>";
                    result = result +"<tbody>"; 
                    
                    for (var i = 0; i < response.data.length; i++) { 
                   	 var opt = response.data[i];
                   	 result  = result + '<tr>';
                   	 if(opt.name != null && opt.name != '' && opt.name != 'undefined'){
                   	 result  = result + '<td><div><span title = "'+opt.name+'">'+opt.name+'</span></div></td>';
                   	    }else{
                   	    	 result  = result + ' <td><div><span>----</span></div></td>';
                   	    }
                   	 if(opt.specialization != null && opt.specialization != '' && opt.specialization != 'undefined'){
                   	 result  = result + ' <td><div><span title="'+opt.specialization+'">'+opt.specialization+'</span></div></td>';
                   	 }else{
                   		 result  = result + ' <td><div><span>----</span></div></td>';
                   	 }
                   	 if(opt.startDateStr != null && opt.startDateStr != '' && opt.startDateStr != 'undefined'){
                   	 result  = result + ' <td><div><span>'+opt.startDateStr+'</span></div></td>';
                   	 }else{
                   		 result  = result + ' <td><div><span>----</span></div></td>';
                   	 }
                   	 if(opt.endDateStr != null && opt.endDateStr != '' && opt.endDateStr != 'undefined'){ 
                   	 result  = result + '<td><div><span>'+opt.endDateStr+'</span></div></td>';
                   	 }else{
                   		 result  = result + '<td><div><span>----</span></div></td>';
                   	 }
                   	 if(opt.grade != null && opt.grade != '' && opt.grade  != 'undefined'){
                   	 result  = result + '<td><div><span>'+opt.grade+'</span></div></td>';
                   	 }else{
                   		 result  = result + '<td><div><span>----</span></div></td>'; 
                   	 }
                   	 if(opt.gradeType != null && opt.gradeType != '' && opt.gradeType  != 'undefined'){
		                 result  = result + '<td><div><span>'+opt.gradeType+'</span></div></td>';
		                 }else{
		                 result  = result + '<td><div><span>----</span></div></td>'; 
		                 }
                   	 if(opt.institute != null && opt.institute != '' && opt.institute != 'undefined'){
                   	 result  = result + '<td><div><span title="'+opt.institute+'">'+opt.institute+'</span></div></td>';
                   	 }else{
                   		 result  = result + '<td><div><span>----</span></div></td>';
                   	 }
                   	 if(opt.address != null && opt.address != '' && opt.address != 'undefined'){
                   	 result  = result + ' <td><div><span title="'+opt.address+'">'+opt.address+'</span></div></td>';
                   	 }else{
                   		 result  = result + ' <td><div><span>----</span></div></td>';
                   	 }
                   	 if(opt.id != null && opt.id != '' && opt.id != 'undefined'){
                   		 if(window.candidateStatusGlobal == '0'){
                   			 result = result+' <td><div class="tblicons"><a class="glyphicon glyphicon-pencil" href="javascript:void(0)"></a>';
                   		 }
                   		 else{
                   			 if(candidateType == "Candidates"){
                   				 result = result+' <td><div class="tblicons tblminwidth-75"><a class="glyphicon glyphicon-pencil" href="javascript:void(0)" onClick ="EditEducation('+ candidateId+ ','+ opt.id+ ');" title="Edit"></a>';
                   			 }else{
                   				 result = result+' <td><div class="tblicons tblminwidth-75"><a class="glyphicon glyphicon-pencil" href="javascript:void(0)" onClick ="EditEducation('+ candidateId+ ','+ opt.id+ ');" title="Edit"></a>';
                   			 }
                   				 
                   		 }
                   		 if(window.candidateStatusGlobal == '0'){
								result = result+'<i class="glyphicon glyphicon-trash"></i></div></td>';''
                   		 }else{
                   			 if(candidateType == "Candidates"){
                   				 result = result+'<i class="glyphicon glyphicon-trash" onclick="removeEducation('+opt.id+','+candidateId+');" title="Remove"></i></div></td>';	 
                   			 }else{
                   				 result = result+' <i class="glyphicon glyphicon-trash" onclick="removeEducation('+opt.id+','+candidateId+');" title="Remove"></i></div></td>';
                   			 }
                   			  
                   		 }
                   	}
                   	 result  = result + '</tr>';
                     } 
                    result = result +" </tbody>";
                    result = result +"</table>";
                    $("#candidateEducationList").append(result); 
                    educationPagination();
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
     1.usage     : used in candidateEducationList().
--%>

<script>
function educationPagination(){  
    $('#candidateEducationPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"aoColumnDefs": [{
	           'bSortable': false,
	           'aTargets': 8
			}],
    	"oLanguage": {
     		"sSearch": "",
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

<script>
function closePopup(){
	 $("#popUpDiv").removeClass("edit-details-active");
}
</script>

<%-- 
     @author : sarath kumar.
     this script is for shown popUp.
     1.usage   : used in candidateEducation() function.
--%>
<script>
function addEducation(candidateId) {	
	
	var result = "";
	result = result  +'<!-- Popup Div -->';
	result = result  +'          <form role="form"  id="education_form" name="education_form" method="post" > ';
	result = result  +'          <i class="fa fa-close close-div"  onClick="closePopup();"></i> ';
	result = result  +'                <div class="form-horizontal clearfix"> ';
	result = result  +'   ';
	result = result  +'                  <div class="clearfix mb10 col-md-12" id="nameIdDiv"> ';
	result = result  +'						<select label=" " class="chosen-select form-control" field-name="Education" data-placeholder = "Select Education" name="name" id="candEdu" data-validation=" required" onchange="validateEduname('+candidateId+');" onkeyup = "validateEduname('+candidateId+');">';
	
											 <c:forEach var="edu" items="${educationsList}">
    result = result  +'	                        <option value="" disabled selected></option>';										 
	result = result + ' 						<option value="${edu.name}">${edu.name}</option>';
	                				    	 </c:forEach> 
	                				    	
	                				    	 
	                				    	 
    result = result + '						</select>';
	result = result  +'                  </div>                                                                                                                               ';
	result = result  +'                                                                                                                                                       ';
	result = result  +'                  <div class="clearfix mb10">                                                                                                          ';
	result = result  +'                    <div class="col-md-12">                                                                                                            ';
	result = result  +'                      <input type="text" name="specialization" field-name="specialization" data-validation="required validate_Space validate_length length2-100" data-validation-optional="true" class="form-control input-sm" value="" placeholder="Specialization">                                          ';
	result = result  +'                    </div>                                                                                                                             ';
	result = result  +'                  </div>                                                                                                                               ';
	result = result  +'                                                                                                                                                       ';
	result = result  +'                  <div class="clearfix mb10">                                                                                                          ';
	result = result  +'                    <div class="col-md-6">                                                                                            ';
	result  = result + '                   <div>';
	result = result  +'                      <input type="text" name="startDate" class="form-control input-sm" field-name="startDate" id="startDate" onclick="educationStartDate();" data-validation="required" class="form-control input-sm" data-validation-optional="true" value="" placeholder="Start Date" id="edu-startDate" readonly>                           ';
	result = result  +'                      <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>                                           ';
	result  = result + '					<div class="clearDate" onClick="clearProfCanEduStartDate();"></div>';
	result  = result + '                   </div>';

	result = result  +'                    </div>                                                                                                                             ';
	result = result  +'                    <div class="col-md-6">                                                                                            ';
	result  = result + '                   <div>';
	result = result  +'                      <input type="text" name="endDate" class="form-control input-sm" field-name="endDate" id="endDate" onclick="educationStartDate();" data-validation="required" class="form-control input-sm" data-validation-optional="true" value="" placeholder="End Date" id="edu-endDate" readonly>                               ';
	result = result  +'                      <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>                                           ';
	result  = result + '					<div class="clearDate" onClick="clearProfCanEduEndDate();"></div>';
	result  = result + '                   </div>';
	result = result  +'                    </div>                                                                                                                             ';
	result = result  +'                  </div>                                                                                                                               ';
	result = result  +'                                                                                                                                                       ';
	
	result  = result + '                 <div class="clearfix mb10">';
	result = result  +'	                     <div class="col-md-5 dropdown-select positionRelative paddingRight">';
	result = result  +'	                     <select class="newSelect"  name="gradeType" id="scoreType" onchange="eduSelectScoreType();clearSearchKey();">';
	result = result  +' 					   <option value="" selected="selected">Score Type</option>';
	result = result  +'	                       <option value="Percentage">Percentage</option>';
	result = result  +'	                       <option value="CGPA">CGPA</option>';
	result = result  +'	                       <option value="GRADE">GRADE</option>';
	result = result  +'	                      </select>';
	result = result  +'	                   </div>';
	result  = result + '                   <div class="col-md-7" positionRelative id="candidateScoreDiv">';
	result  = result + '                     <input type="text" class="form-control" value="" name="grade" placeholder="Score" id="candidateScore" onkeyup="eduSelectScoreType();eduScoreAndScoreType();"';
	result  = result + '                     data-validation-optional="true" data-validation="required validate_length length1-3" field-name="score">';
	result  = result + '                 	 </div>';
	result = result  +'	                   </div>';
	
	result = result  +'                                                                                                                                                       ';
	result = result  +'                  <div class="clearfix mb10">                                                                                                          ';
	result = result  +'                    <div class="col-md-12">                                                                                                            ';
	result = result  +'                      <input type="text" name="institute" field-name="institute" data-validation="required validate_Space validate_length length2-100" data-validation-optional="true" class="form-control input-sm" value="" placeholder="Institute">                                               ';
	result = result  +'                    </div>                                                                                                                             ';
	result = result  +'                  </div>                                                                                                                               ';
	result = result  +'                                                                                                                                                       ';
	result = result  +'                  <div class="clearfix mb10">                                                                                                          ';
	result = result  +'                    <div class="col-md-12">                                                                                                            ';
	result = result  +'                      <input type="text" field-name="Address" data-validation="required validate_Space validate_length length2-250" data-validation-optional="true" name="address" class="form-control input-sm" value="" placeholder="Address">                                                 ';
	result = result  +'                    </div>                                                                                                                             ';
	result = result  +'                  </div>                                                                                                                               ';
	result = result  +'                                                                                                                                                       ';
	result = result  +'                  <div class="clearfix mr">                                                                                                            ';
    result = result +'<input type="button" value="Add" name="btnAddPr" id="save_button" class="btn btn-sm btn-primary pull-right" onClick ="candidateEducationStore('+candidateId+');" />'; 
	/* result = result  +'                  <input onClick ="candidateEmployersStore('+candidateId+');" type="submit" value="Add" class="btn btn-sm btn-primary pull-right"/>                                                       '; */
	result = result  +'                  </div>                                                                                                                               ';
	result = result  +'                </div>                                                                                                                                 ';
	result = result  +'                </form>                                                                                                                                ';
	$("#popUpDiv").empty();
	$("#popUpDiv").append(result);
	
	function educationStartDate(){
	}
	
	var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	  var finalDateFormat1 = getJSDateFormat(locationDate);
	  
	  $('#startDate').datepicker({
	      dateFormat: finalDateFormat1,
	      changeMonth: true,
	  	  changeYear: true, 
	      onSelect: function (selected) {
	            var dt =  $(this).datepicker('getDate', '+1d'); 
	            dt.setDate(dt.getDate() + 1);
	            $("#endDate").datepicker("option", "minDate", dt);
	        }
	  });
	  
	  $('#endDate').datepicker({
	      dateFormat: finalDateFormat1,
	      changeMonth: true,
	  	  changeYear: true, 
	        onSelect: function (selected) {
	            var dt =  $(this).datepicker('getDate', '-1d'); 
	            dt.setDate(dt.getDate() - 1);
	            $("#startDate").datepicker("option", "maxDate", dt);
	        }
	  });
	$(".edit-details").toggleClass("edit-details-active");
	 $(".close-div").click(function(){ 
		 $(this).parent("div.edit-details").removeClass("edit-details-active");
		 });
	 $(".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div, .edit-details").click(function(e){
		 e.stopPropagation();
	 });
	 
	 <%-- Eductions and Skills Autocomplete Scripts Starts Here --%>
	 var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
	//Adding Customized Educations to the list
     var selectone, chosenone;
     selectone = $("#candEdu");
     selectone.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add New Education:' });
     chosenone = selectone.data('chosen');
     chosenone.search_field.on('keyup', function(e1)
	    {
	    	var newEducation = this.value.replace(",","");
	        if ((e1.which == 13 || e1.which == 188) && (newEducation != null  && newEducation != '' && newEducation != 'undefined'))
	        {
	        	var par = this.parent;
	            var option = $("<option>").val(newEducation).text(newEducation);
	            selectone.append(option);
	            selectone.find(option).prop('selected', true);
	          //to doesn't allow duplicates
             $("#candEdu option").each(function(){
                   $(this).siblings("[value='"+ this.value+"']").remove();
             });
             //end
     		$("#candEdu").trigger("chosen:updated");
     		
	        }
	    }); 
	    //Adding Customized Educations to the list
	 
			for (var selector in config) {
			  $(selector).chosen(config[selector]);
			}
			$('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
				})
		<%-- Eductions and Skills Autocomplete Scripts Ends Here --%>
	}
</script>

<script>
function EditEducation(candidateId,candidateEducationId) {
	$.ajax({	
		type : "POST",
		url : sourceleadUrl+ "/getCandidateEducationBycandidateIdandEducationId?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId+"&candidateEducationId="+candidateEducationId,
		success : function(response) {
			if (response && response.response === "INVALID_DATA") {
			    showError(response.responseMessage);
			} else if (response.response === "AWKWARD") {
			    location.href = "/errorPage";
			} else if (response.response === "SUCCESS") {
				var json = response.object;
				var specialization = "";
				var grade = "";
				var institute = "";
				var address  = "";
				if(json.specialization !=null && json.specialization !='undefined' && json.specialization !=''){
					specialization = json.specialization;
				}
				
				if(json.grade !=null && json.grade !='undefined' && json.grade !=''){
					grade = json.grade;
				}
				
				if(json.institute !=null && json.institute !='undefined' && json.institute !=''){
					institute = json.institute;
				}
				
				if(json.address !=null && json.address !='undefined' && json.address !=''){
					address = json.address;
				}
				
				if (json != null && json != 'undefined' && json != "") {
					/* if (data.indexOf('Sign In') >= 0) {
						location.replace(sourceleadUrl);
					} else if (data.indexOf('exception') >= 0) {
						location.replace(sourceleadUrl + "/errorPage");
					} else { */	
var result = "";
result = result  +'<!-- Popup Div -->';
result = result  +'          <form role="form"  id="education_form" name="education_form" method="post" > ';
result = result  +'          <i class="fa fa-close close-div"  onClick="closePopup();"></i> ';
result = result  +'                <div class="form-horizontal clearfix"> ';
result = result  +'   ';
result = result  +'                  <div class="clearfix mb10"> ';
result = result  +'                    <div class="col-md-12">                                                                                                            ';
result = result + "<select  class='chosen-select form-control' name='name' id='candEdu' data-validation='validate_Space' data-validation-optional='true' onchange='validateEduname("+candidateId+");'onkeyup = 'validateEduname("+candidateId+");'>";
					
			    	 $.each(json.allAndCandidateEducations, function( i, eduName){
						  var existed = 0;
			                if(json.name != null && json.name != 'undefined' && json.name != ''){
				                var res = [];
				                res = json.name.split(",");
				                res.forEach(function(item) {
				                	if(eduName == item){
				                		existed = 1;
				                		result = result + ' <option selected value="'+eduName+'">'+eduName+'</option>';
				                	}
				                });
			                }
			                if(existed == 0){
			                	result = result + ' <option value="'+eduName+'">'+eduName+'</option>';
			                }
						  
						});
result = result + "</select>";
result = result  +'                  </div>                                                                                                                               ';
result = result  +'                  </div>                                                                                                                               ';
result = result  +'                                                                                                                                                       ';
result = result  +'                  <div class="clearfix mb10">                                                                                                          ';
result = result  +'                    <div class="col-md-12">                                                                                                            ';
result = result  +'                      <input type="text" name="specialization"  field-name="specialization" data-validation="required validate_Space validate_length length2-100" data-validation-optional="true" class="form-control input-sm" value="'+specialization+'" placeholder="Specialization">                                          ';
result = result  +'                    </div>                                                                                                                             ';
result = result  +'                  </div>                                                                                                                               ';
result = result  +'                                                                                                                                                       ';
result = result  +'                  <div class="clearfix mb10">                                                                                                          ';
result = result  +'                    <div class="col-md-6 positionRelative">                                                                                            ';
							if(json.startDateStr !=null && json.startDateStr !='undefined' && json.startDateStr !=''){

result  = result + '                   <div>';
result = result  +'                      <input type="text" name="startDate" class="form-control input-sm" field-name="startDate" id="startDateEdit" onclick="educationStartDate();" data-validation="required" class="form-control input-sm" data-validation-optional="true" value="'+ json.startDateStr +'" placeholder="Start Date" id="edu-startDate" readonly>                           ';
result = result  +'                      <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>                                           ';
result  = result + '					<div class="clearDate" onClick="editProfCandEduStartDate();"></div>';
result  = result + '                   </div>';

							}else{
result  = result + '                   <div>';
result = result  +'					  <input type="text" name="startDate" class="form-control input-sm" field-name="startDate" id="startDateEdit" onclick="educationStartDate();" data-validation="required" class="form-control input-sm" data-validation-optional="true" value="" placeholder="Start Date" id="edu-startDate" readonly>                           ';
result = result  +'                      <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>';
result  = result + '					<div class="clearDate" onClick="editProfCandEduStartDate();"></div>';
result  = result + '                   </div>';

							}
								
result = result  +'                    </div>                                                                                                                             ';
result = result  +'                    <div class="col-md-6 positionRelative">                                                                                            ';
							if(json.endDateStr !=null && json.endDateStr !='undefined' && json.endDateStr !=''){
						
result  = result + '                   <div>';					
result = result  +'                      <input type="text" name="endDate" class="form-control input-sm" field-name="endDate" id="endDateEdit"  onclick="educationStartDate();" data-validation="required" class="form-control input-sm" data-validation-optional="true" value="'+ json.endDateStr +'" placeholder="End Date" id="edu-endDate" readonly>                               ';
result = result  +'                      <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>                                           ';
result  = result + '					<div class="clearDate" onClick="editProfCandEduEndDate();"></div>';
result  = result + '                   </div>';				
							
							}
							else{
result  = result + '                   <div>';									
result = result  +'                      <input type="text" name="endDate" class="form-control input-sm" field-name="endDate" id="endDateEdit" onclick="educationStartDate();" data-validation="required" class="form-control input-sm" data-validation-optional="true" value="" placeholder="End Date" id="edu-endDate" readonly>                               ';
result = result  +'                      <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>                                           ';
result  = result + '					<div class="clearDate" onClick="editProfCandEduEndDate();"></div>';
result  = result + '                   </div>';						
							}
result = result  +'                    </div>    ';
result = result  +'                  </div>                                                                                                                               ';
result = result  +'                                                                                                                                                       ';

result  = result + '                 <div class="clearfix mb10">';
result = result  +'	                     <div class="col-md-4" positionRelative>';
result = result  +'	                     <select class="newSelect" value="'+ json.gradeType +'" name="gradeType" id="scoreType" onchange="eduSelectScoreType();clearSearchKey();">';
result = result  +' 					   <option value="" selected="selected">Score Type</option>';
											if (json.gradeType == "Percentage") {
												result = result + '<option selected>Percentage</option>';
											} else {
												result = result	+ '<option>Percentage</option>';
											}
											if (json.gradeType == "CGPA") {
												result = result	+ '<option selected>CGPA</option>';
											} else {
												result = result	+ '<option>CGPA</option>';
											}
											if (json.gradeType == "GRADE") {
												result = result	+ '<option selected>GRADE</option>';
											} else {
												result = result + '<option>GRADE</option>';
											}
result = result  +'	                      </select>';
result = result  +'	                   </div>';
result  = result + '                   <div class="col-md-8" positionRelative id="candidateScoreDiv">';
result  = result + '                     <input type="text" class="form-control input-sm" value="'+grade+'" name="grade" placeholder="Score" id="candidateScore" onkeyup="eduSelectScoreType();eduScoreAndScoreType();"';
result  = result + '                     data-validation-optional="true" data-validation="required validate_length length1-3" field-name="score">';
result  = result + '                 	 </div>';
result = result  +'	                   </div>';

result = result  +'                                                                                                                                                       ';
result = result  +'                  <div class="clearfix mb10">                                                                                                          ';
result = result  +'                    <div class="col-md-12">                                                                                                            ';
result = result  +'                      <input type="text" name="institute" field-name="institute" data-validation="required validate_Space validate_length length2-100" data-validation-optional="true" class="form-control input-sm" value="'+institute+'" placeholder="Institute">                                               ';
result = result  +'                    </div>                                                                                                                             ';
result = result  +'                  </div>                                                                                                                               ';
result = result  +'                                                                                                                                                       ';
result = result  +'                  <div class="clearfix mb10">                                                                                                          ';
result = result  +'                    <div class="col-md-12">                                                                                                            ';
result = result  +'                      <input type="text" field-name="institute" data-validation="required validate_Space validate_length length2-250" data-validation-optional="true" name="address" class="form-control input-sm" value="'+address+'" placeholder="Address">                                                 ';
result = result  +'                    </div>                                                                                                                             ';
result = result  +'                  </div>                                                                                                                               ';
result = result  +'     ';

result = result  +'                  <div class="clearfix mr"> ';

result = result	+ '	                   <input type="hidden"  value="'+candidateEducationId+'" id="candidateEducationId"  name="candidateEduId" >';
result = result	+ '	                   <input type="hidden"  value="'+candidateId+'" id="candidateId" >';

result  = result + '<button type="button"  id="save_button"  onClick ="updateCandidateEducation('+candidateId+');" class="btn btn-sm btn-primary pull-right">Update</button>';
result = result  +'                  </div>';

result = result  +'                </div>   ';
result = result  +'                </form>                                                                                                                                ';
$("#popUpDiv").empty();
$("#popUpDiv").append(result);

function educationStartDate(){
}

 var proEduStartDate = $("#startDateEdit").val();
var proEduEndDate = $("#endDateEdit").val(); 

var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 

 // written common method in slead-common.js for date formats start
  var eduStartDateEdit = getJSDate(proEduStartDate,locationDate);
  var eduEndDateEdit = getJSDate(proEduEndDate,locationDate);
  var finalDateFormat3 = getJSDateFormat(locationDate);
  //end
  
  $('#startDateEdit').datepicker({
      dateFormat: finalDateFormat3,
      changeMonth: true,
  	  changeYear: true,
  	  maxDate: eduEndDateEdit,
      onSelect: function (selected) {
            var dt = $(this).datepicker('getDate', '+1d');
            dt.setDate(dt.getDate() + 1);
            $("#endDateEdit").datepicker("option", "minDate", dt);
        }
  });
  
  $('#endDateEdit').datepicker({
      dateFormat: finalDateFormat3,
      changeMonth: true,
  	  changeYear: true,
  	  minDate: eduStartDateEdit,
        onSelect: function (selected) {
            var dt = $(this).datepicker('getDate', '-1d');
            dt.setDate(dt.getDate() - 1);
            $("#startDateEdit").datepicker("option", "maxDate", dt);
        }
  });


$(".edit-details").toggleClass("edit-details-active");
 $(".close-div").click(function(){ 
	 $(this).parent("div.edit-details").removeClass("edit-details-active");
	 });
 $(".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div, .edit-details").click(function(e){
	 e.stopPropagation();
 });
 
 <%-- Eductions and Skills Autocomplete Scripts Starts Here --%>
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
	<%-- Eductions and Skills Autocomplete Scripts Ends Here --%>
	
}
//				}		
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
<script>
function candidateEducationStore(candidateId){
	var scoreType = document.getElementById("scoreType").value; 
	 if(scoreType != null && scoreType != ""){
		 $('#candidateScore').removeAttr('data-validation-optional');
		 $('#candidateScore').attr("data-validation","required"); 
	 }else{
		 $('#candidateScore').removeAttr('data-validation');
		 $('#candidateScore').attr("data-validation-optional","true"); 
	 }
	  
	  if ($('#education_form').validate(false, validationSettings)) {
		  
		  var formData = getFormData("education_form");
			 $("#education_form").attr("disabled", "disabled");
			 $("#save_button").attr("disabled", "disabled");
			 $.post(sourceleadUrl+"/storeCandidateEducation?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId, formData, function(response) {
				 	if (response && response.response === "INVALID_DATA") {
					    showError(response.responseMessage);
					} else if (response && response.response === "INVALID_USER") {
					    showError(response.responseMessage);
					} else if (response.response === "AWKWARD") {
					    location.href = "/errorPage";
					} else if (response.response === "SUCCESS") {
						var data = response.object;
						closePopup();
						 $('#perVal').attr('aria-valuenow', data).css('width',data+'%');
		    			 if(data<50){
		    			   $("#perVal").removeClass('progress-bar-success');
		    			   $("#perVal").addClass("progress-bar-danger");
		    			 }
		    			 if(data>=50){
		    				 $("#perVal").removeClass("progress-bar-danger");
		    				 $("#perVal").addClass("progress-bar-success");
		    			 }
		    			 $("#disVal").empty();
		    			$("#disVal").append(data+'%');
		       		    $("#popUpDiv").removeClass("edit-details-active");
		    			$("#selector4").removeAttr("style");
		    			$("#selector4").css({ display: "block" });
						$("#eduMsgDiv").html("Success!! New Education added");
		       			candidateEducationList(candidateId);
		       			eduMsgFadeinFunction();
		       			qveducationSubmission(candidateId);
					} else if (response.indexOf('Sign In') !== -1) {
						location.replace(sourceleadUrl);
					} else {
					    showError("Operation failed, please try again");
					}
		        });
			return true; 
		} else {
			return false;
		}
} 
$('body').on('blur', '#education_form', function() {
	$("#education_form").showHelpOnFocus();
	$("#education_form").validateOnBlur(false, validationSettings);
});


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



  
  
  <script type="text/javascript">
function deleteEduPopup(){
		$("#remove_user_Edu_pop, .project_inn_mask").fadeOut();
}
</script>

<div id="deleteEduPopUp"></div> 

<script type="text/javascript">
function removeEducation(candidateEducationId, candidateId){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_Edu_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>You are choosing to delete the Education</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='deleteSelectedEducation("+candidateEducationId+","+candidateId+");'><input class='u_can_color' type='button'  value='Cancel' onClick='deleteEduPopup();'></b>";
	result = result +"	</div>";
	$("#deleteEduPopUp").empty();
	$("#deleteEduPopUp").append(result);
	$("#remove_user_Edu_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);

}
</script>
   
<%-- 
     @author : david.
     this script is for remove candidateEducation.
--%>

      
<script>
function deleteSelectedEducation(candidateEducationId,candidateId){

	var loginUserId = '${SourceLeadWeb_userId}';
    var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	 $.ajax({  
          type : "POST",
          url: sourceleadUrl+"/removeCandidateEducationBycandidateIdandcandidateEducationId?${_csrf.parameterName}=${_csrf.token}&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId+"&candidateId="+candidateId+"&candidateEducationId="+candidateEducationId,        
          success :function(response) {
        	  if (response.response === "AWKWARD") {
                  location.href = "/errorPage";
              } else if (response.response === "SUCCESS") {
                  var data = response.object;
                  $('#perVal').attr('aria-valuenow', data).css('width',data+'%');
                  if(data<50){
                   /* $("#perVal").removeClass("progress-bar-success"); */
                    $("#perVal").removeClass('progress-bar-success')
                    $("#perVal").addClass("progress-bar-danger");
                  }
                  if(data>=50){
                      $("#perVal").removeClass('progress-bar-danger');
                      $("#perVal").addClass("progress-bar-success");
                  }
                  $("#disVal").empty();
                 $("#disVal").append(data+'%');
                    $("#selector4").removeAttr("style");
                $("#selector4").css({ display: "block" });
                $("#eduMsgDiv").html("Education deleted");
                 $("#popUpDiv").removeClass("edit-details-active");
                $("#remove_user_Edu_pop, .project_inn_mask").fadeOut();
                 candidateEducationList(candidateId);
                eduMsgFadeinFunction();           
              } else if (response.indexOf('Sign In') !== -1) {
                  location.replace(sourceleadUrl);
              } else {
                  showError("Operation failed, please try again");
              }				
        },
          
     }); 
	 
}
</script> 

 <!-- this script is for removing the Education  Ends -->       
 
 <%-- 
     @author : shivayogi , Ajith.
     this script is to display candidate score validation based on scoreType.
--%>
<script type="text/javascript">
function eduSelectScoreType(){
	var str = document.getElementById("candidateScore").value;
	if (str == 'a' || str == 'b' || str == 'c' || str == 'd' || str == 'e') {
		var strToUpper = str.toUpperCase();
		$('#candidateScore').val(strToUpper);
	}
	 var selectedText = document.getElementById("scoreType").value;
	 var canScore = document.getElementById("candidateScore").value;
	 $('#candidateScoreDiv').parent().find('p.jquery_form_error_message').remove(); 
	 if(selectedText =='Percentage'){
		  var floatNum = /^(\d{0,2}(\.\d{0,2})?|100)$/
		  if(canScore.match(floatNum)){
			  $("#save_button").removeAttr("disabled");
			   return true;
		  }		  
		 else{
				$("input#candidateScore").parent().after("<p class='jquery_form_error_message'><span>Enter your Percentage</span>");
				 $("#save_button").attr("disabled", "disabled");
				return false; 
		 	 }
	  }
	 else if(selectedText =='CGPA'){
		  var cgpanum = /^(([\d][\.])?([\d])|10)?$/
			  if(canScore.match(cgpanum)){
				  $("#save_button").removeAttr("disabled");
				   return true;
			  }		  
			 else{
				 $("input#candidateScore").parent().after("<p class='jquery_form_error_message'><span>Enter your CGPA value</span>");
				 $("#save_button").attr("disabled", "disabled");
			return false; 
			 }
	  } 
	 else if(selectedText =='GRADE'){
		 var grades = /^([A-E][-+]{0,2})?$/
			 if (canScore.match(grades)) {
				 $("#save_button").removeAttr("disabled");
				 return true;
			}else{
				$("input#candidateScore").parent().after("<p class='jquery_form_error_message'> Enter  a Valid Grade </p>");
				 $("#save_button").attr("disabled", "disabled");
	    		return false;
	    		
			}
   		}
		 }
	  
</script>

<script type="text/javascript">
function eduScoreAndScoreType(){
	 var candidateScore = document.getElementById("candidateScore").value;
	 var selectedText = document.getElementById("scoreType").value;
	 var floatNum = /^(\d{0,2}(\.\d{0,2})?|100)$/
	 var cgpanum = /^(([\d][\.])?([\d])|10)?$/
	 var grades = /^([A-E][-+]{0,2})?$/
	 if(candidateScore.match(floatNum)){
		 $("#scoreType").val('Percentage'); 
		 $('#candidateScoreDiv').parent().find('p.jquery_form_error_message').remove();
		 $("#save_button").removeAttr("disabled");
	 }
	 if(candidateScore.match(grades)){
		 $("#scoreType").val('GRADE'); 
	 }
	 if(candidateScore.match(cgpanum)){
		 $("#scoreType").val('CGPA'); 
	 }
	 if((selectedText == '') && (!candidateScore.match(floatNum)) && (!candidateScore.match( cgpanum)) && (!candidateScore.match(grades))){
		 $("input#candidateScore").parent().after("<p class='jquery_form_error_message'> Enter your Valid Score </p>");
	 }
	 if(selectedText == '' && candidateScore == ''){
		 $('#candidateScoreDiv').parent().find('p.jquery_form_error_message').remove(); 
	 }
	 if(candidateScore == ''){
		 $("#scoreType").val('');
	 }
}
</script>

<%-- 
     @author : shivayogi , Ajith.
     this script is to flush candidate value when score type is changed.
--%>
<script>
function clearSearchKey() {
	   $('#candidateScore').val('');
	   $('#candidateScoreDiv').parent().find('p.jquery_form_error_message').remove();

}
</script>       

<script type="text/javascript">
	function updateCandidateEducation(candidateId, candidateEducationId) {
		var scoreType = document.getElementById("scoreType").value; 
		 if(scoreType != null && scoreType != ""){
			 $('#candidateScore').removeAttr('data-validation-optional');
			 $('#candidateScore').attr("data-validation","required"); 
		 }else{
			 $('#candidateScore').removeAttr('data-validation');
			 $('#candidateScore').attr("data-validation-optional","true"); 
		 }
		if ($('#education_form').validate(false,
				validationSettings)) {

			var formData = getFormData("education_form");
			 $("#education_form").attr("disabled", "disabled");
			 $("#save_button").attr("disabled", "disabled");
			 
			 $.post(sourceleadUrl+"/upDateCandidateEducationByCandidateIdAndCandidateEducationId?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId, formData, function(data) {
				 closePopup();
				 $("#selector4").removeAttr("style");
     			$("#selector4").css({ display: "block" });
     			$("#eduMsgDiv").html("Education details updated");
				 candidateEducationList(candidateId);
				 eduMsgFadeinFunction();
				 qveducationSubmission(candidateId);
		        });

			
			return true;
		} else {
			return false;
		}
		$('body').on('blur','#education_form',
				function() {
					$("#education_form").showHelpOnFocus();
					$("#education_form").validateOnBlur(false,
							validationSettings);
				});
	}
</script>



<script type="text/javascript">
 function validateEduname(candidateId) {
 	var name = document.getElementById("candEdu").value; 
	$('#nameIdDiv').find('p.jquery_form_error_message').remove();
	$.ajax({
		type: "POST",
	    url: sourceleadUrl+"/validateEduname?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId+"&name="+name, 
	    success: function(response) {
	    	if (response && response.response === "INVALID_DATA") {
	    	    showError(response.responseMessage);
	    	} else if (response.response === "AWKWARD") {
	    	    location.href = "/errorPage";
	    	} else if (response.response === "SUCCESS") {
	    		if(respose.object == true){
		        	$('#nameIdDiv').find('p.jquery_form_error_message').remove();
	        		 /* document.getElementById("name").setAttribute('record-exist','yes');
					 document.getElementById("name").setAttribute('record-exist-errorMsg',name+' is Already Existed'); 
					  */
					$("#candEdu").after("<p class='jquery_form_error_message'>"+name+" is Already Existed</span>");
					$("#save_button").attr('disabled','disabled');
					 // return false;
		        } else {
		        	$('#nameIdDiv').removeAttr( "record-exist record-exist-errormsg");
		        	$('#nameIdDiv').find('p.jquery_form_error_message').remove(); 
	                $("#save_button").removeAttr("disabled");
		        }
	    	} else if (response.indexOf('Sign In') !== -1) {
	    		location.replace(sourceleadUrl);
	    	} else {
	    	    showError("Operation failed, please try again");
	    	}
		},
	});
 }
 </script>     
 
 <!-- script for converting dateformat in datepicker -->
<script>
function toDate(selector) {
    var from = $(selector).val().split("-");
    return new Date(from[2], from[1] - 1, from[0]);
}
</script>
<!-- script for dateformat in datepicker End -->

<script>
function clearProfCanEduStartDate(){
	$("#startDate").val("");
	};
function clearProfCanEduEndDate(){
	$("#endDate").val("");
	};
</script>

<script>
function editProfCandEduStartDate(){
	$("#startDateEdit").val("");
	};
 function editProfCandEduEndDate(){
	$("#endDateEdit").val("");
	}; 
</script> 