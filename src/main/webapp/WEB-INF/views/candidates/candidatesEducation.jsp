<script>
function candidateEducation(candidateId){
	
	var result = "";
	result = result  +'    <div class="project_tabs_inn_head addUser-org">                                                                                                    ';
		result = result  +'    	<a href="javascript:void(0)"  onClick="addEducation('+candidateId+');" class="addUser-org" ><i class="glyphicon glyphicon-plus"></i>Add Education</a>                                                            ';
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


<script>
function candidateEducationList(candidateId){
	 var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({  
          type : "POST",
          url: sourceleadUrl+"/getCandidateEducationListByCandidateId?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId,        
          success :function(data) {
               if(data != null && data != 'undefined' && data != ""){
             		 if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }
             		 else if (data.indexOf('exception') >= 0) {
             			 location.replace(sourceleadUrl+"/errorPage");
             		 }
             		 else{
	                	 $("#candidateEducationList").empty();
	                	 var result = "";
	                	 result = result +"<table id='candidateEducationPagination' class='display' cellspacing='0' width='100%' >"; 
	                     result = result +"<thead>"; 
	                     result = result +"  <tr>"; 
	                     result  = result + '           <th>Education</th>';
	                 	 result  = result + '           <th>Specilization</th>';
	                 	 result  = result + '           <th>Start date</th>';
	                 	 result  = result + '           <th>End date</th>';
	                 	 result  = result + '           <th>Grade %</th>';
	                 	 result  = result + '           <th>Institute/University</th>';
	                 	 result  = result + '           <th>Address</th>';
	                 	 result  = result + '           <th>Action</th>';
	                     result = result +"  </tr>"; 
	                     result = result +"</thead>";
	                     result = result +"<tbody>"; 
	                     var json = JSON.parse(data);
	                     for (var i = 0; i < json.length; i++) { 
	                    	 var opt = json[i];  
	                    	 result  = result + '         <tr>';
	                    	 result  = result + '           <td><div><span>'+opt.name+'</span></div></td>';
	                    	 result  = result + '           <td><div><span>'+opt.specialization+'</span></div></td>';
	                    	 result  = result + '           <td><div><span>'+opt.startDateStr+'</span></div></td>';
	                    	 result  = result + '           <td><div><span>'+opt.endDateStr+'</span></div></td>';
	                    	 result  = result + '           <td><div><span>'+opt.grade+'</span></div></td>';
	                    	 result  = result + '           <td><div><span>'+opt.institute+'</span></div></td>';
	                    	 result  = result + '           <td><div><span>'+opt.address+'</span></div></td>';
	                    	 result  = result + '           <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash" onClick ="removeEducation('+opt.id+','+candidateId+');"></i></div></td>';	                    	 result  = result + '         </tr>';
	                      } 
	                     result = result +" </tbody>";
	                     result = result +"</table>";
	                     $("#candidateEducationList").append(result); 
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
					$("#candidateEducationList").empty();
				    $("#candidateEducationList").append(result); 
               }
        },
          
     }); 
}
</script>

<script>
function pagination(){  
    $('#candidateEducationPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 15, 20, -1], [5, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    } );
}
</script>

<script>
function closePopup(){
	 $("#popUpDiv").removeClass("edit-details-active");
}
</script>
<script>
function addEducation(candidateId) {	
	
	var result = "";
	result = result  +'      <!-- Popup Div -->                                                                                                                               ';
	result = result  +'          <form role="form"  id="education_form" name="education_form" method="post" >                                                                                                                           ';
	result = result  +'          <i class="fa fa-close close-div"  onClick="closePopup();"></i>                                                                                                        ';
	result = result  +'                <div class="form-horizontal clearfix">                                                                                                 ';
	result = result  +'                                                                                                                                                       ';
	result = result  +'                  <div class="clearfix mb10">                                                                                                          ';
	result = result  +'                    <div class="col-md-12">                                                                                                            ';
	result = result  +'                      <input type="text" name="name" field-name="name" data-validation="required validate_Space validate_length length2-100" class="form-control input-sm" value="" placeholder="Education *" required>                                    ';
	result = result  +'                    </div>                                                                                                                             ';
	result = result  +'                  </div>                                                                                                                               ';
	result = result  +'                                                                                                                                                       ';
	result = result  +'                  <div class="clearfix mb10">                                                                                                          ';
	result = result  +'                    <div class="col-md-12">                                                                                                            ';
	result = result  +'                      <input type="text" name="specialization" field-name="specialization" data-validation="required validate_Space validate_length length2-100" class="form-control input-sm" value="" placeholder="Specialization">                                          ';
	result = result  +'                    </div>                                                                                                                             ';
	result = result  +'                  </div>                                                                                                                               ';
	result = result  +'                                                                                                                                                       ';
	result = result  +'                  <div class="clearfix mb10">                                                                                                          ';
	result = result  +'                    <div class="col-md-6 positionRelative">                                                                                            ';
	result = result  +'                      <input type="text" name="startDate" class="form-control input-sm" field-name="startDate" id="startDate" onclick="educationStartDate();" data-validation="required" class="form-control input-sm" value="" placeholder="Start Date" id="edu-startDate" readonly>                           ';
	result = result  +'                      <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>                                           ';
	result = result  +'                    </div>                                                                                                                             ';
	result = result  +'                    <div class="col-md-6 positionRelative">                                                                                            ';
	result = result  +'                      <input type="text" name="endDate" class="form-control input-sm" field-name="endDate" id="endDate" data-validation="required" class="form-control input-sm" value="" placeholder="End Date" id="edu-endDate" readonly>                               ';
	result = result  +'                      <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>                                           ';
	result = result  +'                    </div>                                                                                                                             ';
	result = result  +'                  </div>                                                                                                                               ';
	result = result  +'                                                                                                                                                       ';
	result = result  +'                  <div class="clearfix mb10">                                                                                                          ';
	result = result  +'                    <div class="col-md-12">                                                                                                            ';
	result = result  +'                      <input type="text" name="grade" field-name="grade" data-validation="required validate_Space validate_length length2-100" class="form-control input-sm" value="" placeholder="Grade">                                                   ';
	result = result  +'                    </div>                                                                                                                             ';
	result = result  +'                  </div>                                                                                                                               ';
	result = result  +'                                                                                                                                                       ';
	result = result  +'                  <div class="clearfix mb10">                                                                                                          ';
	result = result  +'                    <div class="col-md-12">                                                                                                            ';
	result = result  +'                      <input type="text" name="institute" field-name="institute" data-validation="required validate_Space validate_length length2-100" class="form-control input-sm" value="" placeholder="Institute">                                               ';
	result = result  +'                    </div>                                                                                                                             ';
	result = result  +'                  </div>                                                                                                                               ';
	result = result  +'                                                                                                                                                       ';
	result = result  +'                  <div class="clearfix mb10">                                                                                                          ';
	result = result  +'                    <div class="col-md-12">                                                                                                            ';
	result = result  +'                      <input type="text" field-name="institute" data-validation="required validate_Space validate_length length2-100" name="address" class="form-control input-sm" value="" placeholder="Address">                                                 ';
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
	 $('html').click(function(){
		$(".edit-details").removeClass("edit-details-active");
	   });	
	   
	}
</script>

<script>
function candidateEducationStore(candidateId){
	  var validationSettings = {
				errorMessagePosition : 'element',
				borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd'
			};
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



   <!-- this script is for removing the Education  starts --> 
      
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
			    	}
             		else if (data.indexOf('exception') >= 0) {
             			 location.replace(sourceleadUrl+"/errorPage");
             		}
             		else{
             			 $("#popUpDivEmp").removeClass("edit-details-active");
             			candidateEducationList(candidateId);
			    	}
               } 
        },
          
     }); 
}
</script> 

       <!-- this script is for removing the Education  Ends -->       
            

 