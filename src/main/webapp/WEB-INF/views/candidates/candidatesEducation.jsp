<script src="resources/js/chosen.jquery.min.js"></script> 
<script src="resources/js/bootstrap-select.min.js"></script>
<link rel="stylesheet" href="resources/css/bootstrap-select.min.css">
<link rel="stylesheet" href="resources/css/chosen.min.css">

<!-- these files for autocomplete with tagit -->

<script src="resources/js/csc.js" type="text/javascript" charset="utf-8"></script>
<link href="resources/css/jquery-tagit.css" rel="stylesheet">
<script src="resources/js/tag-it.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/tag-it.min.js" type="text/javascript" charset="utf-8"></script>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- 
     @author : sarath kumar.
     this script is for display candidate education tab data.
     1.usage     : used at clientSideBar().
--%>
<script>
function candidateEducation(candidateId){
	
	var result = "";
	result = result  +'    <div class="project_tabs_inn_head addUser-org">  ';
	result =result +'<div id = "msgDiv1"></div>';
	if(window.candidateStatusGlobal == '0'){
		result = result  +'    	<a href="javascript:void(0)"  class="addUser-org" ><i class="glyphicon glyphicon-plus"></i>Add Education</a>                                                            ';
	}else{
		result = result  +'    	<span class="pad-sea"><a href="javascript:void(0)"  onClick="addEducation('+candidateId+');" class="addUser-org" ><i class="glyphicon glyphicon-plus"></i>Add Education</a></span>                                                            ';
	}
		result = result  +'     <div id="popUpDiv" class="edit-details col-md-4"> ';
		result = result  +'     </div>  ';
		result  = result + '   <div class="project_tabs_inn_table vendor_wrap_inn_table">';
		result  = result + '   <div id="candidateEducationList" ></div>';
		result  = result + '   </div>';
	result = result+'	    </div>';
	  

    $("#tab-1").empty();
    $("#tab-2").empty(); 
    $("#tab-3").empty();  
    $("#tab-4").empty();  
    $("#tab-5").empty();  
    $("#tab-6").empty();  
    $("#tab-4").append(result);  
	
    candidateEducationList(candidateId);
    
}
</script>

<%-- 
     @author : sarath kumar.
     this script is for get candidateEducationList.
     1.usage     : used in candidateEducation().
     2.parameter : csrf token,candidateId .
     3.ajax url implementation in CandidateListRestController.
--%>
<script>
function candidateEducationList(candidateId){
	 var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({  
          type : "POST",
          url: sourceleadUrl+"/getCandidateEducationListByCandidateId?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId,        
          success :function(data) {
               if(data != null && data != 'undefined' && data != "" && data != '[]'){
             		 if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }
             		 else if (data.indexOf('exception') >= 0) {
             			 location.replace(sourceleadUrl+"/errorPage");
             		 }
             		 else{
             			var json = JSON.parse(data);
             			for (var i = 0; i < 1; i++) {
                            var val= json[i];
                            var opt = "";
                                    if(val.msg != null && val.msg != '' && val.msg != 'undefined'){
                                            opt  = opt + '<div class="success_pop" id="selector2" style="display: block;">';
                                            opt  = opt + '<i class="fa fa-check-circle"></i><span>'+val.msg+'</span>';
                                            opt  = opt + '</div>';
                                            }
                                    $("#msgDiv1").empty();
                                    $("#msgDiv1").append(opt);
                            }
             			 
	                	 $("#candidateEducationList").empty();
	                	 var result = "";
	                	 result = result +"<table id='candidateEducationPagination' class='display' cellspacing='0' width='100%' >"; 
	                     result = result +"<thead>"; 
	                     result = result +"  <tr>"; 
	                     result  = result + '           <th>Education</th>';
	                 	 result  = result + '           <th>Specialization</th>';
	                 	 result  = result + '           <th>Start date</th>';
	                 	 result  = result + '           <th>End date</th>';
	                 	 result  = result + '           <th>score</th>';
	                 	 result  = result + '           <th>score Type</th>';
	                 	 result  = result + '           <th>Institute/University</th>';
	                 	 result  = result + '           <th>Address</th>';
	                 	 result  = result + '           <th>Action</th>';
	                     result = result +"  </tr>"; 
	                     result = result +"</thead>";
	                     result = result +"<tbody>"; 
	                     
	                     for (var i = 0; i < json.length; i++) { 
	                    	 var opt = json[i];  
	                    	 result  = result + '         <tr>';
	                    	 if(opt.name != null && opt.name != '' && opt.name != 'undefined'){
	                    	 result  = result + '<td><div><span>'+opt.name+'</span></div></td>';
	                    	    }else{
	                    	    	 result  = result + ' <td><div><span>----</span></div></td>';
	                    	    }
	                    	 if(opt.specialization != null && opt.specialization != '' && opt.specialization != 'undefined'){
	                    	 result  = result + ' <td><div><span>'+opt.specialization+'</span></div></td>';
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
	                    	 if(opt.gradeType != null && opt.gradeType != '' && opt.grgradeTypeade  != 'undefined'){
			                 result  = result + '<td><div><span>'+opt.gradeType+'</span></div></td>';
			                 }else{
			                 result  = result + '<td><div><span>----</span></div></td>'; 
			                 }
	                    	 if(opt.institute != null && opt.institute != '' && opt.institute != 'undefined'){
	                    	 result  = result + '<td><div><span>'+opt.institute+'</span></div></td>';
	                    	 }else{
	                    		 result  = result + '<td><div><span>----</span></div></td>';
	                    	 }
	                    	 if(opt.address != null && opt.address != '' && opt.address != 'undefined'){
	                    	 result  = result + ' <td><div><span>'+opt.address+'</span></div></td>';
	                    	 }else{
	                    		 result  = result + ' <td><div><span>----</span></div></td>';
	                    	 }
	                    	 if(opt.id != null && opt.id != '' && opt.id != 'undefined'){
	                    		 if(window.candidateStatusGlobal == '0'){
	                    			 result = result+' <td><div class="tblicons"><a class="glyphicon glyphicon-pencil" href="javascript:void(0)"></a>';
	                    		 }
	                    		 else{
	                    			 result = result+' <td><div class="tblicons"><a class="glyphicon glyphicon-pencil" href="javascript:void(0)" onClick ="EditEducation('+ candidateId+ ','+ opt.id+ ');"></a>';	 
	                    		 }
	                    		 if(window.candidateStatusGlobal == '0'){
									result = result+'<i class="glyphicon glyphicon-trash"></i></div></td>';
	                    		 }else{
	                    			 result = result+'<i class="glyphicon glyphicon-trash" onclick="removeEducation('+opt.id+','+candidateId+');"></i></div></td>'; 
	                    		 }
	                    	}
	                    	 result  = result + '</tr>';
	                      } 
	                     result = result +" </tbody>";
	                     result = result +"</table>";
	                     $("#candidateEducationList").append(result); 
	                     educationPagination(); 
	                     msgFadein1(); 
			    	} 
               }else{
	    	         var result ="";
		    	     result = result+"   <div class='row'>"; 
					 result = result+"	<div class='col-lg-12'>"; 
					 result = result+"		<div class='content-area clearfix'>"; 
					 result = result+"			<div class='blank-pages'>";
					 result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
					 result = result+"			        <p>No CandidatesCertifications Found</p>";
					 result = result+"			 </div>";
					 result = result+"		</div>"; 
					 result = result+"	</div>"; 
					 result = result+"  </div> "; 
					$("#candidateEducationList").empty();
				    $("#candidateEducationList").append(result); 
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
    	"lengthMenu": [[5, 15, 20, -1], [5, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
     		"sSearch": ""
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
	result = result  +'                  <div class="clearfix mb10" id="nameIdDiv"> ';
	result = result  +'						<select label=" " class="chosen-select form-control" field-name="Education " name="name" id="name" data-validation=" required" onchange="validateEduname('+candidateId+');">';
	
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
	result = result  +'                    <div class="col-md-6 positionRelative">                                                                                            ';
	result = result  +'                      <input type="text" name="startDate" class="form-control input-sm" field-name="startDate" id="startDate" onclick="educationStartDate();" data-validation="required" class="form-control input-sm" data-validation-optional="true" value="" placeholder="Start Date" id="edu-startDate" readonly>                           ';
	result = result  +'                      <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>                                           ';
	result = result  +'                    </div>                                                                                                                             ';
	result = result  +'                    <div class="col-md-6 positionRelative">                                                                                            ';
	result = result  +'                      <input type="text" name="endDate" class="form-control input-sm" field-name="endDate" id="endDate" onclick="educationStartDate();" data-validation="required" class="form-control input-sm" data-validation-optional="true" value="" placeholder="End Date" id="edu-endDate" readonly>                               ';
	result = result  +'                      <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>                                           ';
	result = result  +'                    </div>                                                                                                                             ';
	result = result  +'                  </div>                                                                                                                               ';
	result = result  +'                                                                                                                                                       ';
	
	result  = result + '                 <div class="clearfix mb10">';
	result = result  +'	                     <div class="col-md-4" positionRelative>';
	result = result  +'	                     <select class="chosen-select"  name="gradeType" id="scoreType" onchange="selectScoreType();clearSearchKey();">';
	result = result  +' 					   <option value="" selected="selected">Score Type</option>';
	result = result  +'	                       <option value="Percentage">%</option>';
	result = result  +'	                       <option value="CGPA">CGPA</option>';
	result = result  +'	                       <option value="GRADE">GRADE</option>';
	result = result  +'	                      </select>';
	result = result  +'	                   </div>';
	result  = result + '                   <div class="col-md-8" positionRelative id="candidateScoreDiv">';
	result  = result + '                     <input type="text" class="form-control input-sm" value="" name="grade" placeholder="Score" id="candidateScore" onkeyup="selectScoreType();"';
	result  = result + '                     data-validation-optional="true" data-validation="required validate_length length1-5" field-name="score">';
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
	result = result  +'                      <input type="text" field-name="institute" data-validation="required validate_Space validate_length length2-100" data-validation-optional="true" name="address" class="form-control input-sm" value="" placeholder="Address">                                                 ';
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
	
	$('#startDate').datepicker({
	      dateFormat: 'yy-mm-dd',
	      onSelect: function (selected) {
	            var dt = new Date(selected);
	            dt.setDate(dt.getDate() + 1);
	            $("#endDate").datepicker("option", "minDate", dt);
	        }
	  });
	  
	  $('#endDate').datepicker({
	      dateFormat: 'yy-mm-dd',
	        onSelect: function (selected) {
	            var dt = new Date(selected);
	            dt.setDate(dt.getDate() - 1);
	            $("#startDate").datepicker("option", "maxDate", dt);
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
</script>




<script>
function EditEducation(candidateId,candidateEducationId) {
	var sourceleadUrl = '${sourceleadUrl}';
	$.ajax({	type : "POST",
				url : sourceleadUrl+ "/getCandidateEducationBycandidateIdandEducationId?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId+"&candidateEducationId="+candidateEducationId,
				success : function(data) {
					var json = JSON.parse(data);
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
					
					if (data != null && data != 'undefined' && data != "") {
						if (data.indexOf('Sign In') >= 0) {
							location.replace(sourceleadUrl);
						} else if (data.indexOf('exception') >= 0) {
							location.replace(sourceleadUrl + "/errorPage");
						} else {	
	var result = "";
	result = result  +'<!-- Popup Div -->';
	result = result  +'          <form role="form"  id="education_form" name="education_form" method="post" > ';
	result = result  +'          <i class="fa fa-close close-div"  onClick="closePopup();"></i> ';
	result = result  +'                <div class="form-horizontal clearfix"> ';
	result = result  +'   ';
	result = result  +'                  <div class="clearfix mb10"> ';
	result = result + "<select  class='chosen-select form-control' name='name' id='name' data-validation='validate_Space' data-validation-optional='true' onchange='validateEduname("+candidateId+");'>";
	                 for (var j = 0; j < json.educationList.length; j++) {
							var education = json.educationList[j];
							   if(education.name == json.name){
								   result = result+'<option value=\"'+education.name+'\" selected >'+education.name+'</option>';
							  }else{
								   result = result+'<option value=\"'+education.name+'\" >'+education.name+'</option>';
			   		 	      } 
						}
    result = result + "</select>";
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
	result = result  +'	                     <div class="col-md-4" positionRelative>';
	result = result  +'	                     <select class="chosen-select" value="'+ json.gradeType +'" name="gradeType" id="scoreType" onchange="selectScoreType();clearSearchKey();">';
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
	result  = result + '                     <input type="text" class="form-control input-sm" value="'+grade+'" name="grade" placeholder="Score" id="candidateScore" onkeyup="selectScoreType();"';
	result  = result + '                     data-validation-optional="true" data-validation="required validate_length length1-5" field-name="score">';
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
	result = result  +'                      <input type="text" field-name="institute" data-validation="required validate_Space validate_length length2-100" data-validation-optional="true" name="address" class="form-control input-sm" value="'+address+'" placeholder="Address">                                                 ';
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
	
	$('#startDateEdit').datepicker({
	      dateFormat: 'yy-mm-dd',
	      onSelect: function (selected) {
	            var dt = new Date(selected);
	            dt.setDate(dt.getDate() + 1);
	            $("#endDateEdit").datepicker("option", "minDate", dt);
	        }
	  });
	  
	  $('#endDateEdit').datepicker({
	      dateFormat: 'yy-mm-dd',
	        onSelect: function (selected) {
	            var dt = new Date(selected);
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
		  
			 var frm = $('#education_form').serializeFormJSON();
			 var con = JSON.stringify(frm);
			 con = con.replace(/[{}]/g, "");
		     var jsonData = encodeURIComponent(con);
			 var sourceleadUrl = '${sourceleadUrl}';
			 $("#education_form").attr("disabled", "disabled");
			
			$.ajax({
					type : "POST",
					url : sourceleadUrl+"/storeCandidateEducation?${_csrf.parameterName}=${_csrf.token}&json="+con+"&candidateId="+candidateId,
					success :function(data) {  
				                 if(data != null && data != 'undefined' && data != ""){
				               		if( data.indexOf('Sign In') >= 0){
							    		location.replace(sourceleadUrl);
							    	}
				               		else if (data.indexOf('exception') >= 0) {
				               			 location.replace(sourceleadUrl+"/errorPage");
				               		}
				               		else{
				               			 $("#popUpDiv").removeClass("edit-details-active");
				               			 candidateEducationList(candidateId);
							    	}
				                 }   
				      }, 
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



   
<%-- 
     @author : david.
     this script is for remove candidateEducation.
--%>

      
<script>
function removeEducation(candidateEducationId,candidateId){
 var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({  
          type : "POST",
          url: sourceleadUrl+"/removeCandidateEducationBycandidateIdandcandidateEducationId?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId+"&candidateEducationId="+candidateEducationId,        
          success :function(data) {

				if(data != null && data != 'undefined' && data != ""){
             		if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	}else if (data.indexOf('exception') >= 0) {
             			 location.replace(sourceleadUrl+"/errorPage");
             		}else{
             			 $("#popUpDiv").removeClass("edit-details-active");
             			candidateEducationList(candidateId);
			    	}
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
function selectScoreType(){
	
	 var selectedText = document.getElementById("scoreType").value;
	 var canScore = document.getElementById("candidateScore").value;
	 $('#candidateScoreDiv').parent().find('p.jquery_form_error_message').remove(); 
	 if(selectedText =='Percentage'){
		  var floatNum = /^(\d{0,2}(\.\d{0,2})?|100)$/
		  if(canScore.match(floatNum)){
			  $("#addCandidate").removeAttr("disabled");
			   return true;
		  }		  
		 else{
				$("input#candidateScore").parent().after("<p class='jquery_form_error_message'><span>Enter your Percentage</span>");
				 $("#addCandidate").attr("disabled", "disabled");
				return false; 
		 	 }
	  }
	 else if(selectedText =='CGPA'){
		  var cgpanum = /^(([\d][\.])?([\d])|10)?$/
			  if(canScore.match(cgpanum)){
				  $("#addCandidate").removeAttr("disabled");
				   return true;
			  }		  
			 else{
				 $("input#candidateScore").parent().after("<p class='jquery_form_error_message'><span>Enter your CGPA value</span>");
				 $("#addCandidate").attr("disabled", "disabled");
			return false; 
			 }
	  } 
	 else if(selectedText =='GRADE'){
		 var grades = /^([A-E][-+]{0,2})?$/
			 if (canScore.match(grades)) {
				 $("#addCandidate").removeAttr("disabled");
				 return true;
			}else{
				$("input#candidateScore").parent().after("<p class='jquery_form_error_message'> Enter  a Valid Grade </p>");
				 $("#addCandidate").attr("disabled", "disabled");
	    		return false;
	    		
			}
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
		/* var validationSettings = {
			errorMessagePosition : 'element',
			borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd'
		}; */
		
		
		if ($('#education_form').validate(false,
				validationSettings)) {

			var frm = $('#education_form').serializeFormJSON();

			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, "");
			var jsonData = encodeURIComponent(con);
			//It will escape all the special characters
			var sourceleadUrl = '${sourceleadUrl}';

			$.ajax({
						type : "POST",
						url : sourceleadUrl
								+ "/upDateCandidateEducationByCandidateIdAndCandidateEducationId?${_csrf.parameterName}=${_csrf.token}&json="+ jsonData + "&candidateId=" + candidateId ,
						success : function(result) {
							closePopup();
							candidateEducationList(candidateId)
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
	  var name = document.getElementById("name").value; 
 	  $('#nameIdDiv').find('p.jquery_form_error_message').remove();
	 var sourceleadUrl ='${sourceleadUrl}';
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/validateEduname?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId+"&name="+name, 
		        success: function(data) {
		        	if(data == true){
		        		  $('#nameIdDiv').find('p.jquery_form_error_message').remove();
		        		  /* document.getElementById("name").setAttribute('record-exist','yes');
						  document.getElementById("name").setAttribute('record-exist-errorMsg',name+' is Already Existed'); 
						   */
						   $("#name").after("<p class='jquery_form_error_message'>"+name+" is Already Existed</span>");
						  $("#save_button").attr('disabled','disabled');
						 // return false;
		        	}
		        	else{
		        		 $('#nameIdDiv').removeAttr( "record-exist record-exist-errormsg");
		        		 $('#nameIdDiv').find('p.jquery_form_error_message').remove(); 
                         $("#save_button").removeAttr("disabled");
		        	}
		        },
		    });
 }
 </script>     
 
 <script>
       function msgFadein1() {
           setTimeout(function(){
                   $('#selector2').fadeOut('slow'); }, 1000);
       }
</script>  