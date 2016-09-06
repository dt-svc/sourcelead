<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<link href="resources/css/jquery-ui.css" rel="stylesheet">
<style>
.clsDatePicker {
	z-index: 100000 !important;
}
</style>
<script>
function closeCertificatePopup(){
	 $("#popUpDivEmp").removeClass("edit-details-active");
}
</script>

<script>
function candidateCertificate(candidateId){
	 
	var result = "";
	result  = result + '   <div class="project_tabs_inn_head">                                                                          ';
	result  = result + '   	<a href="javascript:void(0)" id="newCandidate" onClick="addCertification('+candidateId+');flushCandiadate();" class="addUser-org"><i class="glyphicon glyphicon-plus"></i>Add Certification</a> ';
	
	result = result  +'     <div id="popUpDivCertificates" > ';
	result = result  +'     </div>  ';
	
	result  = result + '   <div class="project_tabs_inn_table vendor_wrap_inn_table">';
	result  = result + '   <div id="candidateCertificateList" ></div>';
	result  = result + '   </div>';

    $("#tab-1").empty();
    $("#tab-2").empty(); 
    $("#tab-3").empty();  
    $("#tab-4").empty();  
    $("#tab-5").empty();  
    $("#tab-6").empty();  
    $("#tab-5").append(result);
    
    candidateCertificateList(candidateId);
    
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

<!-- this script is for saving candidate certification  -->

<script>
function storeCertificate(candidateId){
	
	
		if ($('#Candidate_certification_form').validate(false,validationSettings)) {
			var frm = $('#Candidate_certification_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, "");
			var jsonData = encodeURIComponent(con);
			var sourceleadUrl = '${sourceleadUrl}';
			$("#Candidate_certification_form").attr("disabled", "disabled");
				$.ajax({
						type : "POST",
						url : sourceleadUrl
								+ "/storeCandidateCertifications?${_csrf.parameterName}=${_csrf.token}&json="
								+ jsonData + "&candidateId=" + candidateId,
						success : function(data) {
							if (data != null && data != 'undefined'
									&& data != "") {
								if (data.indexOf('Sign In') >= 0) {
									location.replace(sourceleadUrl);
								} else if (data.indexOf('exception') >= 0) {
									location.replace(sourceleadUrl+ "/errorPage");
								}
								else{
				               			$("#popUpDivEmp").removeClass("edit-details-active");
				               			candidateCertificateList(candidateId);
							    	}
							   }
						},
					});
			return true;
		} else {
			return false;
		}
	}
	$('body').on('blur','#Candidate_certification_form',
			function() {
				$("#Candidate_certification_form").showHelpOnFocus();
				$("#Candidate_certification_form").validateOnBlur(false,validationSettings);
			});
</script>


<!-- this script is for serializeFormJSON -->
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


<!-- this script is for serializeFormJSON  End-->

<script>
function candidateCertificateList(candidateId){
	 var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({  
          type : "POST",
          url: sourceleadUrl+"/getCandidateCertificationListByCandidateIdAndOrgLocationID?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId,        
          success :function(data) {
               if(data != null && data != 'undefined' && data != "" && data != '[]'){
             		 if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }
             		 else if (data.indexOf('exception') >= 0) {
             			 location.replace(sourceleadUrl+"/errorPage");
             		 }
             		 else{
	                	 $("#candidateCertificateList").empty();
	                	 var result = "";
	                	 result = result +"<table id='candidateCertificatePagination' class='display' cellspacing='0' width='100%' >"; 
	                     result = result +"<thead>"; 
	                     result = result +"  <tr>"; 
	                     result  = result +'           <th>Name</th>';
	                 	result  = result + '           <th>Provider</th>';
	                 	result  = result + '           <th>Score</th>';
	                 	result  = result + '           <th>Score Type</th>';
	                 	result  = result + '           <th>Description</th>';
	                 	result  = result + '           <th>Valid from</th>';
	                 	result  = result + '           <th>Valid to</th>';
	                 	result  = result + '           <th>Action</th>';
	                     result = result +"  </tr>"; 
	                     result = result +"</thead>";
	                     result = result +"<tbody>"; 
	                      var json = JSON.parse(data);
	                     for (var i = 0; i < json.length; i++) { 
	                    	 var opt = json[i];  
	                    	 var score = "";
	                    	 var scoreType ="";
	                    	 var description ="";
	                    	 var candidatevalidFromDateToString ="";
	                    	 var candidatevalidToDateToString ="";
				            	if(opt.score != null && opt.score != 'undefined' && opt.score != ""){
				            		score = opt.score;
				            	}else{
				            		score = "NO DATA";
				            	}
				            	if(opt.scoreType != null && opt.scoreType != 'undefined' && opt.scoreType != ""){
				            		scoreType = opt.scoreType;
				            	}else{
				            		scoreType = "NO DATA";
				            	}
				            	if(opt.description != null && opt.description != 'undefined' && opt.description != ""){
				            		description = opt.description;
				            	}else{
				            		description = "NO DATA";
				            	}
				            	if(opt.candidatevalidFromDateToString != null && opt.candidatevalidFromDateToString != 'undefined' && opt.candidatevalidFromDateToString != ""){
				            		candidatevalidFromDateToString = opt.candidatevalidFromDateToString;
				            	}else{
				            		candidatevalidFromDateToString = "NO DATA";
				            	}
				            	if(opt.candidatevalidToDateToString != null && opt.candidatevalidToDateToString != 'undefined' && opt.candidatevalidToDateToString != ""){
				            		candidatevalidToDateToString = opt.candidatevalidToDateToString;
				            	}else{
				            		candidatevalidToDateToString = "NO DATA";
				            	}
	                    	 result  = result + '         <tr>';
	                    	 result  = result + '           <td><div><span>'+opt.name+'</span></div></td>';
	                    	 result  = result + '           <td><div><span>'+opt.provider+'</span></div></td>';
	                    	 result  = result + '           <td><div><span>'+score+'</span></div></td>';
	                    	 result  = result + '           <td><div><span>'+scoreType+'</span></div></td>';
	                    	 result  = result + '           <td><div><span>'+description+'</span></div></td>';
	                    	 result  = result + '           <td><div><span>'+candidatevalidFromDateToString+'</span></div></td>';
	                    	 result  = result + '           <td><div><span>'+candidatevalidToDateToString+'</span></div></td>';
	                    	 result  = result + '           <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash" onClick ="removeCertification('+opt.id+','+candidateId+');"></i></div></td>';	                    	 result  = result + '         </tr>';
	                      } 
	                     result = result +" </tbody>";
	                     result = result +"</table>";
	                     $("#candidateCertificateList").append(result); 
	                     pagination(); 
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
					$("#candidateCertificateList").empty();
				    $("#candidateCertificateList").append(result);
				    
				    $("#popUpDivEmp").empty();
					$("#popUpDivEmp").append(result);
               }
        },
          
     }); 
}
</script>
<script>
function addCertification(candidateId) {	
	
	 	var result = '';
	 	result  = result + '     <!-- Popup Div -->                                                                                         ';
		result  = result + '         <div  id="popUpDivEmp" class="edit-details col-md-4"> <i class="fa fa-close close-div"></i>                                ';
		result  = result + '         <form id="Candidate_certification_form" name="Candidate_certification_form" method="post">        ';
		result = result+'	           <i class="fa fa-close close-div" onClick="closeCertificatePopup();"></i>';
		result  = result + '               <div class="form-horizontal clearfix">';
		result  = result + '                 <div class="clearfix mb10">';
		result  = result + '                   <div class="col-md-12" id="candidatenameDiv">';
		result  = result + '                     <input type="text" class="form-control input-sm" value="" name="name"  placeholder="Name *" id="candidateName" ';
		result  = result + '                      data-validation="required validate_Space validate_length length2-100" field-name="Name" >';
		result  = result + '                   </div>';
		result  = result + '                 </div>';
		result  = result + '                 <div class="clearfix mb10">';
		result  = result + '                   <div class="col-md-12">';
		result  = result + '                     <input type="text" class="form-control input-sm" value="" name="provider" placeholder="Provider *" id="candidateProvider"';
		result  = result + '                      data-validation="required validate_Space validate_length length2-100" field-name="Provider">';
		result  = result + '                   </div>';
		result  = result + '                 </div>';
		
		result  = result + '                 <div class="clearfix mb10">';
		result  = result + '                   <div class="col-md-8" positionRelative>';
		result  = result + '                     <input type="text" class="form-control input-sm" value="" name="score" placeholder="Score" id="candidateScore"';
		result  = result + '                     data-validation-optional="true" data-validation="required validate_Alphabet validate_length length1-5" field-name="score">';
		result  = result + '                 	 </div>';
		result = result  +'	                     <div class="col-md-4" positionRelative>';
		result = result  +'	                     <select class="chosen-select"  name="scoreType" id="scoreType" onClick="selectScoreType();">';
		result = result  +' 					   <option value="" disabled="disabled" selected="selected">Score Type</option>';
		result = result  +'	                       <option value="Percentage">%</option>';
		result = result  +'	                       <option value="CGPA">CGPA</option>';
		result = result  +'	                       <option value="GRADE">GRADE</option>';
		result = result  +'	                      </select>';
		result = result  +'	                   </div>';
		result = result  +'	                   </div>';
		
		result  = result + '                 <div class="clearfix mb10">';
		result  = result + '                   <div class="col-md-12">';
		result  = result + '                     <textarea class="form-control" rows="1" name ="description"  placeholder="Description" id="candidateDescription"';
		result  = result + '                     data-validation-optional="true" data-validation="required"></textarea>';
		result  = result + '                   </div>';
		result  = result + '                 </div>';
		result  = result + '                 <div class="clearfix mb10">';
		result  = result + '                   <div class="col-md-6 positionRelative">';
		result  = result + '                     <input type="text" class="form-control input-sm"  value="" name ="validFrom" placeholder="Valid From" ';
		result  = result + '                      data-validation-optional="true" data-validation="required" field-name="Valid From" id="StartDate" readonly>';
		result  = result + '                     <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>';
		result  = result + '                   </div>';
		result  = result + '                   <div class="col-md-6 positionRelative">';
		result  = result + '                     <input type="text" class="form-control input-sm" value="" name="validTo" placeholder="Validate To" ';
		result  = result + '                     data-validation-optional="true" data-validation="required" field-name="Valid To" id="EndDate" readonly>';
		result  = result + '                     <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>';
		result  = result + '                   </div>';
		result  = result + '                 </div>';
		result  = result + '                 <div class="clearfix mr">';
		result  = result + '                   <button type="button" class="btn btn-sm btn-primary pull-right" id="addCandidate"  onclick="storeCertificate('+candidateId+');"  >Add</button>';
		result  = result + '                 </div>';
		result  = result + '               </div>';
		result  = result + '              </form>';
		result  = result + '             </div>';
		result  = result + '   </div>';
		$("#popUpDivCertificates").empty();
		$("#popUpDivCertificates").append(result);
		
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
		
		
		/*  $(".addUser-org").click(function(){
			 $(".edit-details").toggleClass("edit-details-active");
		 });  */
		/* $(".edit-details").toggleClass("edit-details-active");
		 $(".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div, .edit-details").click(function(e){
			 e.stopPropagation();
		 }); */
	 /* $('html').click(function(){
		$(".edit-details").removeClass("edit-details-active");
	   });	 */

	  $('#StartDate').datepicker({
	      dateFormat: 'yy-mm-dd',
	      onSelect: function (selected) {
	            var dt = new Date(selected);
	            dt.setDate(dt.getDate() + 1);
	            $("#EndDate").datepicker("option", "minDate", dt);
	        }
	  });
	  
	  $('#EndDate').datepicker({
	      dateFormat: 'yy-mm-dd',
	        onSelect: function (selected) {
	            var dt = new Date(selected);
	            dt.setDate(dt.getDate() - 1);
	            $("#StartDate").datepicker("option", "maxDate", dt);
	        }
	  });
	}
</script>

     <!-- this script is for removing the Certification  starts --> 
      
<script>
function removeCertification(candidateCertificationId,candidateId){
	
	 var sourceleadUrl ='${sourceleadUrl}';
	 
	 $.ajax({  
          type : "POST",
          url: sourceleadUrl+"/removeCandidateCertificateBycandidateIdandcandidateCertificateId?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId+"&candidateCertificationId="+candidateCertificationId,        
          success :function(data) {

				if(data != null && data != 'undefined' && data != ""){
             		if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	}
             		else if (data.indexOf('exception') >= 0) {
             			 location.replace(sourceleadUrl+"/errorPage");
             		}
             		else{
             			 $("#popUpDivEmp").removeClass("edit-details-active");
             			candidateCertificateList(candidateId);
			    	}
               } 
        },
          
     }); 
}
</script> 
 <!-- this script is for removing the Certification  Ends -->       
            
<script type="text/javascript">
	function flushCandiadate() {
		$("#addCandidate").removeAttr("disabled");
		$('#candidateName').val('');
		$('#candidateProvider').val('');
		$('#candidateScore').val('');
		$('#candidateDescription').val('');
		$('#StartDate').val('');
		$('#EndDate').val('');
		$(".error").removeClass("error");
		$('#newCandidate').find('p.jquery_form_error_message').remove();
	}
</script>
