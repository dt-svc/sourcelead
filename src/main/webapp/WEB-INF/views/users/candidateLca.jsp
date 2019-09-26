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
	result = result  +'                    <div class="col-md-6 clearDate" onClick="clearStartDt();">                                                                                            ';
	result = result  +'                      <input type="text" name="startDate" class="form-control input-sm" field-name="startDate" id="startDate" onclick="educationStartDate();" data-validation="required" class="form-control input-sm" data-validation-optional="true" value="" placeholder="Start Date" id="edu-startDate" readonly>                           ';
	result = result  +'                      <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>                                           ';
	result = result  +'                    </div>                                                                                                                             ';
	result = result  +'                    <div class="col-md-6 clearDate" onClick="clearEndDt();">                                                                                            ';
	result = result  +'                      <input type="text" name="endDate" class="form-control input-sm" field-name="endDate" id="endDate" onclick="educationStartDate();" data-validation="required" class="form-control input-sm" data-validation-optional="true" value="" placeholder="End Date" id="edu-endDate" readonly>                               ';
	result = result  +'                      <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>                                           ';
	result = result  +'                    </div>                                                                                                                             ';
	result = result  +'                  </div>                                                                                                                               ';
	result = result  +'                                                                                                                                                       ';
	result  = result + '                 <div class="clearfix mb10">';
	result = result  +'	                     <div class="col-md-6 dropdown-select positionRelative">';
	result = result  +'	                     <select class="newSelect"  name="gradeType" id="scoreEducationType" onchange="educationSelectScoreType();clearSearchKeyEducation();">';
	result = result  +' 					   <option value="" selected="selected">Score Type</option>';
	result = result  +'	                       <option value="Percentage">Percentage</option>';
	result = result  +'	                       <option value="CGPA">CGPA</option>';
	result = result  +'	                       <option value="GRADE">GRADE</option>';
	result = result  +'	                      </select>';
	result = result  +'	                   </div>';
	result  = result + '                   <div class="col-md-6" positionRelative paddingLeft id="candidateScoreDiv">';
	result  = result + '                     <input type="text" class="form-control input-sm" value="" name="grade" placeholder="Score" id="candidateEducationScore" onkeyup="educationSelectScoreType();educationscoreAndScoreType();"';
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
	  var finalDateFormatEduSave = getJSDateFormat(locationDate);
	  
	$('#startDate').datepicker({
	      dateFormat: finalDateFormatEduSave,
	      changeMonth: true,
	  	  changeYear: true, 
	      onSelect: function (selected) {
	            //var dt = new Date(selected);
	            var dt = $(this).datepicker('getDate', '+1d');
	            dt.setDate(dt.getDate() + 1);
	            $("#endDate").datepicker("option", "minDate", dt);
	        }
	  });
	  
	  $('#endDate').datepicker({
	      dateFormat: finalDateFormatEduSave,
	      changeMonth: true,
	  	  changeYear: true, 
	        onSelect: function (selected) {
	            //var dt = new Date(selected);
	            var dt = $(this).datepicker('getDate', '-1d');
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
	 /* $('html').click(function(){
		$(".edit-details").removeClass("edit-details-active");
	   });	 */
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
		url : atsUrl+ "/getCandidateEducationBycandidateIdandEducationId?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId+"&candidateEducationId="+candidateEducationId,
		success : function(response) {
			if (response && response.response === "INVALID_DATA") {
			    showError(response.responseMessage);
			} else if (response.response === "AWKWARD") {
			    location.href = "/errorPage";
			} else if (response.response === "SUCCESS") {
				//var json = JSON.parse(data);
				var json = response.object;
				var specialization = "";
				var grade = "";
				var institute = "";
				var address  = "";
				
				if(json.specialization !=null && json.specialization !='undefined' && json.specialization !='') {
					specialization = json.specialization;
				}
				
				if(json.grade !=null && json.grade !='undefined' && json.grade !='') {
					grade = json.grade;
				}
				
				if(json.institute !=null && json.institute !='undefined' && json.institute !=''){
					institute = json.institute;
				}
				
				if(json.address !=null && json.address !='undefined' && json.address !=''){
					address = json.address;
				}
				if (json != null && json != 'undefined' && json != "") {
//						if (json.indexOf('Sign In') >= 0) {
//							location.replace(sourceleadUrl);
//						} else if (json.indexOf('exception') >= 0) {
//							location.replace(sourceleadUrl + "/errorPage");
//						} else {
						var result = "";
						result = result  +'<!-- Popup Div -->';
						result = result  +'          <form role="form"  id="education_form" name="educatio(n_form" method="post" > ';
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
result = result  +'                      <input type="text" name="startDate" class="form-control input-sm" field-name="startDate" id="startDateEdit" onclick="educationStartDate();" data-validation="required" class="form-control input-sm" data-validation-optional="true" value="'+ json.startDateStr +'" placeholder="Start Date" id="edu-startDate" readonly>                           ';
result = result  +'                      <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>                                           ';
							}else{
result = result  +'					  <input type="text" name="startDate" class="form-control input-sm" field-name="startDate" id="startDateEdit" onclick="educationStartDate();" data-validation="required" class="form-control input-sm" data-validation-optional="true" value="" placeholder="Start Date" id="edu-startDate" readonly>                           ';
result = result  +'                      <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>';
							}
								
result = result  +'                    </div>                                                                                                                             ';
result = result  +'                    <div class="col-md-6 positionRelative">                                                                                            ';
							if(json.endDateStr !=null && json.endDateStr !='undefined' && json.endDateStr !=''){
result = result  +'                      <input type="text" name="endDate" class="form-control input-sm" field-name="endDate" id="endDateEdit"  onclick="educationStartDate();" data-validation="required" class="form-control input-sm" data-validation-optional="true" value="'+ json.endDateStr +'" placeholder="End Date" id="edu-endDate" readonly>                               ';
result = result  +'                      <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>                                           ';
							}
							else{
result = result  +'                      <input type="text" name="endDate" class="form-control input-sm" field-name="endDate" id="endDateEdit" onclick="educationStartDate();" data-validation="required" class="form-control input-sm" data-validation-optional="true" value="" placeholder="End Date" id="edu-endDate" readonly>                               ';
result = result  +'                      <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>                                           ';
							}
result = result  +'                    </div>    ';
result = result  +'                  </div>                                                                                                                               ';
result = result  +'                                                                                                                                                       ';

result  = result + '                 <div class="clearfix mb10">';
result = result  +'	                     <div class="col-md-6 dropdown-select positionRelative paddingRight">';
result = result  +'	                     <select class="newSelect" value="'+ json.gradeType +'" name="gradeType" id="scoreEducationType" onchange="educationSelectScoreType();clearSearchKeyEducation();">';
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
result  = result + '                   <div class="col-md-6" positionRelative paddingLeft id="candidateScoreDiv">';
result  = result + '                     <input type="text" class="form-control input-sm" value="'+grade+'" name="grade" placeholder="Score" id="candidateEducationScore" onkeyup="educationSelectScoreType();educationscoreAndScoreType();"';
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

 var eduStartDate = $("#startDateEdit").val();
var eduEndDate = $("#endDateEdit").val(); 

var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 

 
 var finalDateFormatEduEditOneSave = '';
 
 // written common method in slead-common.js for date formats start
  var eduStartDateEdit = getJSDate(eduStartDate,locationDate);
  var eduEndDateEdit = getJSDate(eduEndDate,locationDate);
  
  var finalDateFormatEduUpdate = getJSDateFormat(locationDate);
  //end

$('#startDateEdit').datepicker({
      dateFormat: finalDateFormatEduUpdate,
      changeMonth: true,
  	  changeYear: true, 
  	 maxDate: eduEndDateEdit,
      onSelect: function (selected) {
            //var dt = new Date(selected);
            var dt = $(this).datepicker('getDate', '+1d');
            dt.setDate(dt.getDate() + 1);
            $("#endDateEdit").datepicker("option", "minDate", dt);
        }
  });
  
  $('#endDateEdit').datepicker({
      dateFormat: finalDateFormatEduUpdate,
      changeMonth: true,
  	  changeYear: true, 
  	  minDate: eduStartDateEdit,
      onSelect: function (selected) {
            //var dt = new Date(selected);
            var dt = $(this).datepicker('getDate', '-1d');
            dt.setDate(dt.getDate() - 1);
            $("#startDateEdit").datepicker("option", "maxDate", dt);
        }
  });

/* $(".addUser-org").click(function(){
	 $(".edit-details").toggleClass("edit-details-active");
 }); */
$(".edit-details").toggleClass("edit-details-active");
 $(".close-div").click(function(){ 
	 $(this).parent("div.edit-details").removeClass("edit-details-active");
	 });
 $(".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div, .edit-details").click(function(e){
	 e.stopPropagation();
 });
 /* $('html').click(function(){
	$(".edit-details").removeClass("edit-details-active");
   });	 */
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
//					}a	
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
	var scoreType = document.getElementById("scoreEducationType").value; 
	 if(scoreType != null && scoreType != ""){
		 $('#candidateEducationScore').removeAttr('data-validation-optional');
		 $('#candidateEducationScore').attr("data-validation","required"); 
	 }else{
		 $('#candidateEducationScore').removeAttr('data-validation');
		 $('#candidateEducationScore').attr("data-validation-optional","true"); 
	 }
	  
	  if ($('#education_form').validate(false, validationSettings)) {
		  if($("#candEdu").length > 0 && $("#candEdu_chosen").length > 0){
		     var formData = getFormData("education_form");
			 $("#education_form").attr("disabled", "disabled");
			 $("#save_button").attr("disabled", "disabled");
			
			 $.post(atsUrl+"/storeCandidateEducation?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId, formData, function(result) {
				 if (result && result.response === "INVALID_DATA") {
					    $("#update_candidate").removeAttr("disabled");
					    $("#update_candidate").removeAttr('button').val('Save');
					    showError(result.responseMessage);
					} else if (result.response === "AWKWARD") {
					    location.href = "/errorPage";
					} else if (result.response === "SUCCESS") {
						var data = result.object;
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
		    			// for quick view bar update
		    			$('#quickPerVal').attr('aria-valuenow', data).css('width',data+'%');
		      			 if(data<50){
		          			   $("#quickPerVal").removeClass('progress-bar-success');
		          			   $("#quickPerVal").addClass("progress-bar-danger");
		          			 }
		          			 if(data>=50){
		          				 $("#quickPerVal").removeClass('progress-bar-danger');
		          				 $("#quickPerVal").addClass("progress-bar-success");
		          			 }

		    			$("#quickDisVal").empty();
		    			$("#quickDisVal").append(data+'%');
		    			
		       		    $("#popUpDiv").removeClass("edit-details-active");
		    			$("#selector4").removeAttr("style");
		    			$("#selector4").css({ display: "block" });
						$("#eduMsgDiv").html("Success!! New Education added");
		       			candidateLcaList(candidateId);
		       			eduMsgFadeinFunction();
		       			qveducationSubmission(candidateId);
		       			setTimeout(function(){
				 			candidateList();					 			
				 		},100);
			        } else if (result.indexOf('Sign In') !== -1) {
						location.replace(sourceleadUrl);
					} else {
					    showError("Operation failed, please try again");
					}
			 });
			 return true;
		} else {
			showError("Education is missing");
		}
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
	$("#remove_user_pop, .project_inn_mask").fadeOut();
}
</script>

<div id="deleteEduPopUp"></div> 

<script type="text/javascript">
function removeEducation(candidateEducationId, candidateId){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>You are choosing to delete the Education</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='deleteSelectedEducation("+candidateEducationId+","+candidateId+");'><input class='u_can_color' type='button'  value='Cancel' onClick='deleteEduPopup();'></b>";
	result = result +"	</div>";
	$("#deleteEduPopUp").empty();
	$("#deleteEduPopUp").append(result);
	$("#remove_user_pop").slideDown(200);
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
          url: atsUrl+"/removeCandidateEducationBycandidateIdandcandidateEducationId?"+tokenParamName+"="+tokenValue+"&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId+"&candidateId="+candidateId+"&candidateEducationId="+candidateEducationId,        
          success :function(result) {
        	  if (result && result.response === "INVALID_DATA") {
      		    showError(result.responseMessage);
      		} else if (result.response === "AWKWARD") {
      		    location.href = "/errorPage";
      		} else if (result.response === "SUCCESS") {
      			var data = result.object;
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
      			
      			$('#quickPerVal').attr('aria-valuenow', data).css('width',data+'%');
    			 if(data<50){
         			   $("#quickPerVal").removeClass('progress-bar-success');
         			   $("#quickPerVal").addClass("progress-bar-danger");
         			 }
         			 if(data>=50){
         				 $("#quickPerVal").removeClass('progress-bar-danger');
         				 $("#quickPerVal").addClass("progress-bar-success");
         			 }

  				$("#quickDisVal").empty();
  				$("#quickDisVal").append(data+'%');
      			
      			
      			 	 $("#selector4").removeAttr("style");
       			 $("#selector4").css({ display: "block" });
       			 $("#eduMsgDiv").html("Education deleted");
        			 $("#popUpDiv").removeClass("edit-details-active");
        			$("#remove_user_pop, .project_inn_mask").fadeOut();
        			 candidateLcaList(candidateId);
        			eduMsgFadeinFunction();
        			 setTimeout(function(){
			 			candidateList();					 			
			 	    },100);
      		} else if (result.indexOf('Sign In') !== -1) {
      			location.replace(sourceleadUrl);
      		} else {
      		    showError("Operation failed, please try again");
      		}
			 
        },
          
     }); 
	 
}
</script> 

 


<script type="text/javascript">
function educationSelectScoreType(){
	var str = document.getElementById("candidateEducationScore").value;
	if (str == 'a' || str == 'b' || str == 'c' || str == 'd' || str == 'e') {
		var strToUpper = str.toUpperCase();
		$('#candidateEducationScore').val(strToUpper);
	}
	 
	 var selectedText = document.getElementById("scoreEducationType").value;
	 var canScore = document.getElementById("candidateEducationScore").value;
	 
	 $('#candidateScoreDiv').parent().find('p.jquery_form_error_message').remove(); 
	 if(selectedText =='Percentage'){
		  var floatNum = /^(\d{0,2}(\.\d{0,2})?|100)$/
		  if(canScore.match(floatNum)){
			  $("#save_button").removeAttr("disabled");
			   return true;
		  }		  
		 else{
				$("input#candidateEducationScore").parent().after("<p class='jquery_form_error_message'><span>Enter your Valid Percentage</span>");
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
				 $("input#candidateEducationScore").parent().after("<p class='jquery_form_error_message'><span>Enter your Valid CGPA value</span>");
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
				$("input#candidateEducationScore").parent().after("<p class='jquery_form_error_message'> Enter your Valid Grade </p>");
				 $("#save_button").attr("disabled", "disabled");
	    		return false;
	    		
			}
   		}
		 }
	  
</script>


<script type="text/javascript">
function educationscoreAndScoreType(){
	
	 var candidateScore = document.getElementById("candidateEducationScore").value;
	 var selectedText = document.getElementById("scoreEducationType").value;
	 var floatNum = /^(\d{0,2}(\.\d{0,2})?|100)$/
	 var cgpanum = /^(([\d][\.])?([\d])|10)?$/
	 var grades = /^([A-E][-+]{0,2})?$/
	 if(candidateScore.match(floatNum)){
		 $("#scoreEducationType").val('Percentage'); 
		 $('#candidateScoreDiv').parent().find('p.jquery_form_error_message').remove();
		 $("#save_button").removeAttr("disabled");
	 }
	 if(candidateScore.match(grades)){
		 $('#candidateScoreDiv').parent().find('p.jquery_form_error_message').remove();
		 $("#scoreEducationType").val('GRADE'); 
		 $("#save_button").removeAttr("disabled");
	 }
	 if(candidateScore.match(cgpanum)){
		 $('#candidateScoreDiv').parent().find('p.jquery_form_error_message').remove();
		 $("#scoreEducationType").val('CGPA'); 
		 $("#save_button").removeAttr("disabled");
	 }
	 if((selectedText == '') && (!candidateScore.match(floatNum)) && (!candidateScore.match( cgpanum)) && (!candidateScore.match(grades))){
		 $("input#candidateEducationScore").parent().after("<p class='jquery_form_error_message'> Enter your Valid Score </p>");
	 }
	 if(selectedText == '' && candidateScore == ''){
		 $('#candidateScoreDiv').parent().find('p.jquery_form_error_message').remove(); 
	 }
	 if(candidateScore == ''){
		 $("#scoreEducationType").val('');
	 }
}
</script>



<%-- 
     @author : shivayogi , Ajith.
     this script is to flush candidate value when score type is changed.
--%>
<script>
function clearSearchKeyEducation() {
	   $('#candidateEducationScore').val('');
	   $('#candidateScoreDiv').parent().find('p.jquery_form_error_message').remove();

}
</script>       

<script type="text/javascript">
	function updateCandidateEducation(candidateId, candidateEducationId) {
		var scoreType = document.getElementById("scoreEducationType").value; 
		 if(scoreType != null && scoreType != ""){
			 $('#candidateEducationScore').removeAttr('data-validation-optional');
			 $('#candidateEducationScore').attr("data-validation","required"); 
		 }else{
			 $('#candidateEducationScore').removeAttr('data-validation');
			 $('#candidateEducationScore').attr("data-validation-optional","true"); 
		 }
		 
		if ($('#education_form').validate(false,
				validationSettings)) {
			
             var formData = getFormData('education_form');
			 $("#education_form").attr("disabled", "disabled");
			 $("#save_button").attr("disabled", "disabled");	
			
			 
			 $.ajax({
						type : "POST",
						data : formData,
						
						url : atsUrl+"/upDateCandidateEducationByCandidateIdAndCandidateEducationId?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,
						success : function(result) {
							if (result && result.response === "INVALID_DATA") {
							    showError(result.responseMessage);
							} else if (result.response === "AWKWARD") {
							    location.href = "/errorPage";
							} else if (result.response === "SUCCESS") {
								closePopup();
		            			$("#selector4").removeAttr("style");
		            			$("#selector4").css({ display: "block" });
		            			$("#eduMsgDiv").html("Education details updated");
							candidateLcaList(candidateId)
							eduMsgFadeinFunction();
							qveducationSubmission(candidateId);
							setTimeout(function(){
					 			candidateList();					 			
					 		},100);
							} else if (result.indexOf('Sign In') !== -1) {
								location.replace(sourceleadUrl);
							} else {
							    showError("Operation failed, please try again");
							}
						},
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
        url: atsUrl+"/validateEduname?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId+"&name="+name, 
        success: function(response) {
        	$('#education_form').find('p.jquery_form_error_message').remove();
        	if (response && response.response === "INVALID_DATA") {
        	    showError(response.responseMessage);
        	} else if (response.response === "AWKWARD") {
        	    location.href = "/errorPage";
        	} else if (response.response === "SUCCESS") {
        		if(response.object == true) {
          			$('#nameIdDiv').find('p.jquery_form_error_message').remove();
  				   	$("#candEdu").after("<p class='jquery_form_error_message'>"+name+" is Already Existed</span>");
  				  	$("#save_button").attr('disabled','disabled');
          		} else{
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
function clearStartDt(){
	$("#startDate").val("");
	};
function clearEndDt(){
	$("#endDate").val("");
	};
</script>
<div class="clearfix">
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
</div>
<script>
	function closeLcapopup(){
		$(".popMask").fadeOut(200);
		$("#popLcaView").fadeOut(100);
		$("#lcaSave_Form")[0].reset();
		/* $("#lcaId").val(''); */
		$('#popLcaView').find('p.jquery_form_error_message').remove();
	}
	</script>
