<%-- 
     @author : David Sammeta.
     this script is for upDate Candidate Details .
     1.usage     :  used in candidatelist.jsp function  line 264
     2.parameter : candidateId .
--%> 
<script>
function candidateExperience(candidateId){
	
	var result = "";
	result = result  +'    <div class="project_tabs_inn_head addUser-org">                                                                                                    ';
	result = result  +'    	<a href="javascript:void(0)"  onClick="addExperience('+candidateId+');" class="addUser-org" ><i class="glyphicon glyphicon-plus"></i>Add Employer</a>                                                            ';
	result = result  +'     <div id="popUpDivEmp" class="edit-details col-md-4"> ';
	result = result  +'     </div>  ';
	result  = result + '   <div class="project_tabs_inn_table vendor_wrap_inn_table">';
	result  = result + '   <div id="candidateEmployersList" ></div>';
	result  = result + '   </div>';
	  

    $("#tab-1").empty();
    $("#tab-2").empty(); 
    $("#tab-3").empty();  
    $("#tab-4").empty();  
    $("#tab-5").empty();  
    $("#tab-6").empty();  
    $("#tab-3").append(result);  
	
    candidateEmployersList(candidateId);
    
}
</script>


 <%-- 
     @author : David Sammeta.
     this script is to Store  candidateEmployer.
     1.usage     :  used in addExperience function  line 187
     2.parameter : candidateId,csrf.token
     3.ajax url implementation in CandidateEmployersRestController.
--%> 
<script type="text/javascript">
function candidateEmployersStore(candidateId){
	
			var validationSettings = {
					errorMessagePosition : 'element',
					borderColorOnError : '',
					scrollToTopOnError : true,
					dateFormat : 'yyyy/mm/dd'
				};
			if ($('#employers_form').validate(false, validationSettings)) {
				//To disable addButton 
				 $("#candidateEmployersAdd").attr("disabled", "disabled");
			 var frm = $('#employers_form').serializeFormJSON();
			 var con = JSON.stringify(frm);
			     con = con.replace(/[{}]/g, "");
				 //It will escape all the special characters
			 var jsonData = encodeURIComponent(con);
			 var sourceleadUrl = '${sourceleadUrl}';
		   
			$.ajax({
					type : "POST",
					url : sourceleadUrl+"/storeCandidateEmployers?${_csrf.parameterName}=${_csrf.token}&json="+jsonData+"&candidateId="+candidateId,
					success : function(data) {
						
						if(data != null && data != 'undefined' && data != ""){
		               		if( data.indexOf('Sign In') >= 0){
					    		location.replace(sourceleadUrl);
					    	}
		               		else if (data.indexOf('exception') >= 0) {
		               			 location.replace(sourceleadUrl+"/errorPage");
		               		}
		               		else{
		               			 $("#popUpDivEmp").removeClass("edit-details-active");
		               			candidateEmployersList(candidateId);
					    	}
		                 } 
						
					},
			});
			return true;
			} else {
				return false;
			}
		} 
$('body').on('blur', '#employers_form', function() {
	$("#employers_form").showHelpOnFocus();
	$("#employers_form").validateOnBlur(false, validationSettings);
});
</script>            
   
  
  
<%-- 
     @author : David Sammeta.
     this script is for upDate Candidate Details .
     1.usage     :  used in candidateExperience  function  line 17
     2.parameter : candidateId .
--%> 
  
<script>
function addExperience(candidateId) {	
	
	var result = "";
	result = result  +'      <!-- Popup Div --> ';   
	result = result  +'	         <form id="employers_form" name="employers_form" method="post">';
	result = result  +'	             <div class="form-horizontal clearfix">';
	result = result  +'	               <div class="clearfix mb10">';
	result = result  +'	                 <div class="col-md-12">';
	result = result  +'	                  <input type="text" class="form-control"  placeholder="Employer *" name="name" data-validation="required validate_Space validate_length length2-100" field-name="Employer Name" value=""> <span class="hidden">Employer Name</span>';
	result = result  +'	                 </div>';
	result = result  +'	               </div>';
	result = result  +'	               <div class="clearfix mb10">';
	result = result  +'	                 <div class="col-md-12 checkbox">';
	result = result  +'	                   <label>';
	result = result  +'	                  <label> <input type="checkbox" id="isCurrentPosition" value="Y" onClick ="iscurrentPosition();"name="isCurrentPosition"> Is Current Position</label>';
	result = result  +'	                 </div>';
	result = result  +'	               </div>';
	result = result  +'	               <div class="clearfix mb10">';
	result = result  +'	                 <div class="col-md-12">';
	result = result  +'	                    <input type="text" class="form-control"  placeholder="Employer Designation" name="designation" data-validation="required validate_Space validate_length length2-100" field-name="Employer Designation" value=""> <span class="hidden">Employer Designation</span>';
	result = result  +'	                 </div>';
	result = result  +'	               </div>';
	result = result  +'	               <div class="clearfix mb10">';
	result = result  +'	                 <div class="col-md-12">';
	result = result  +'	                  <input type="text" class="form-control input-sm" placeholder="Address" name="address" data-validation="required validate_Space validate_length length2-250" field-name="Address" value=""> <span 	class="hidden">Address</span>';
	result = result  +'	                 </div>';
	result = result  +'	               </div>';
	result = result  +'	               <div class="clearfix mb10">';
	result = result  +'	                 <p class="ml">Start Date</p>';
	result = result  +'	                 <div class="col-md-6">';
	result = result  +'	                   <select class="chosen-select"  name="startMonth" id="startMonth">';
	result = result  +'	                       <option value="Select Month ">Select Month</option>';
	result = result  +'	                       <option value="January">January</option>';
	result = result  +'	                       <option value="February">February</option>';
	result = result  +'	                       <option value="March">March</option>';
	result = result  +'	                       <option value="April">April</option>';
	result = result  +'	                       <option value="April">May</option>';
	result = result  +'	                       <option value="April">June</option>';
	result = result  +'	                       <option value="April">Jully</option>';
	result = result  +'	                       <option value="April">August</option>';
	result = result  +'	                       <option value="April">September</option>';
	result = result  +'	                       <option value="April">October</option>';
	result = result  +'	                       <option value="April">November</option>';
	result = result  +'	                       <option value="April">December</option>';
	result = result  +'	                   </select>';
	result = result  +'	                   </div>';
	result = result  +'	                   <div class="col-md-6">';
	result = result  +'	                   <input type="text" max class="form-control" value="" id="startYear" name="startYear" onclick="validYear();"  placeholder="Enter Year"  field-name="Start Date" data-validation="required" >';
	result = result  +'	                 </div>';
	result = result  +'	                 </div>';
	result = result  +'	                 <div id="endDateDiv">';
	result = result  +'	                 <div class="clearfix mb10 endDate >';
	result = result  +'	                 <p class="ml">End Date</p>';
	result = result  +'	                 <div class="col-md-6 id = "endMR"">';
	result = result  +'	                    <select class="chosen-select" class="required" name="endMonth" id="endMonth">';
	result = result  +'	                       <option value="Select Month">Select Month</option>';
	result = result  +'	                       <option value="January">January</option>';
	result = result  +'	                       <option value="February">February</option>';
	result = result  +'	                       <option value="March">March</option>';
	result = result  +'	                       <option value="April">April</option>';
	result = result  +'	                       <option value="April">May</option>';
	result = result  +'	                       <option value="April">June</option> ';
	result = result  +'	                       <option value="April">Jully</option>';
	result = result  +'	                       <option value="April">August</option>';
	result = result  +'	                       <option value="April">September</option>';
	result = result  +'	                       <option value="April">October</option>';
	result = result  +'	                       <option value="April">November</option>';
	result = result  +'	                       <option value="April">December</option>';
	result = result  +'	                   </select>';
	result = result  +'	                   </div>';
	result = result  +'	                   <div class="col-md-6" >';
	result = result  +'	                   <input type="text" max class="form-control" value="" id="endYear" name="mobileOffer" field-name="End Year"  placeholder="Enter Year" data-validation="validate_Space validate_int validate_length length1-2" >';
	result = result  +'	                 </div>';
	result = result  +'	                 </div> ';
	result = result  +'	                 </div> ';
	result = result  +'	               <div class="clearfix mr">';
	result = result  +'	                 <button type="button" id ="candidateEmployersAdd" onClick ="candidateEmployersStore('+candidateId+');"class="btn btn-sm btn-primary pull-right">Add</button>';
	result = result  +'	               </div>';
	result = result  +'	             </div>';
	result = result  +'	        </form> ';
	$("#popUpDivEmp").empty();
	$("#popUpDivEmp").append(result);
	
	
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
 
 
<%-- 
     @author : David Sammeta.
     this script is for serializeFormJSON.
     1.usage     :  used in candidateEmployersStore function  line 57
--%> 
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
     @author : David Sammeta.
     this script is getting candidateEmployers List .
     1.usage     :  used in candidateExperience  function  line 34
     2.parameter : candidateId ,csrf.token.
     3.ajax url implementation in CandidateListRestController.
--%> 
<script>
function candidateEmployersList(candidateId){
	 var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({  
          type : "POST",
          url: sourceleadUrl+"/getCandidatesEmployersListByCandidateIdAndOrgLocationID?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId,        
          success :function(data) {
               if(data != null && data != 'undefined' && data != "" && data != '[]'){
             		 if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }
             		 else if (data.indexOf('exception') >= 0) {
             			 location.replace(sourceleadUrl+"/errorPage");
             		 }
             		 else{
	                	 $("#candidateEmployersList").empty();
	                	 var result = "";
	                	 result = result +"<table id='candidateCertificatePagination' class='display' cellspacing='0' width='100%' >"; 
	                     result = result +"<thead>"; 
	                     result = result +"  <tr>"; 
	                     result  = result + '           <th>Employer</th>';
	                 	 result  = result + '           <th>Designation</th>';
	                 	 result  = result + '           <th>Address</th>';
	                 	 result  = result + '           <th>Start date</th>';
	                 	 result  = result + '           <th>End date</th>';
	                 	 result  = result + '           <th>Action</th>';
	                     result = result +"  </tr>"; 
	                     result = result +"</thead>";
	                     result = result +"<tbody>"; 
	                     var json = JSON.parse(data);
	                     for (var i = 0; i < json.length; i++) { 
	                    	 var opt = json[i];  
	                    	 result  = result + '         <tr>';
	                    	 if(opt.name != null && opt.name != '' && opt.name != 'undefined'){
	                    	 result  = result + '<td><div><span>'+opt.name+'</span></div></td>';
	                    	 }else{
	                    	   result  = result + '<td><div><span>--</span></div></td>';
	                    	 }
	                    	 if(opt.designation != null && opt.designation && opt.designation != 'undefined'){
	                    	 result  = result + '<td><div><span>'+opt.designation+'</span></div></td>';
	                    	 }else{
	                    		 result  = result + '<td><div><span>--</span></div></td>';
	                    	 }
	                    	 if(opt.address != null && opt.address && opt.address != 'undefined'){
	                    		 result  = result + '<td><div><span>'+opt.address+'</span></div></td>';
	                    	 }else{
	                    		 result  = result + '<td><div><span>--</span></div></td>';
	                    	 }
	                    	 
	                    	 if(opt.startYear!=null && opt.startYear!='' && opt.startYear != 'undefined'){
	         					result = result  +'<td><div><span>'+opt.startMonth+' -'+opt.startYear+'</span></div></td>';     
	         					}else{
	         						result = result  +'<td><div><span>--</span></div></td>'; 
	         					}
	                    	 
	                    	 if(opt.endYear!=null && opt.endYear!='' && opt.endYear!='undefined'){
		         					result = result  +' <td><div><span>'+opt.endMonth+'-'+opt.endYear+'</span></div></td>';     
		         				}else{
		         					result = result  +'<td><div><span>--</span></div></td>';
		         				}
	                    	 result  = result + '<td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"  onClick ="removeEmployer('+opt.id+','+candidateId+');"></i></div></td>';
	                    	 result  = result + '</tr>';
	                      } 
	                     result = result +" </tbody>";
	                     result = result +"</table>";
	                     $("#candidateEmployersList").append(result); 
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
					$("#candidateEmployersList").empty();
				    $("#candidateEmployersList").append(result); 
               }
        },
          
     }); 
}
</script>


<%-- 
     @author : David Sammeta.
     this script is getting removing Employer in the  List .
     1.usage     :  used in candidateEmployersList  function  line 286
     2.parameter : candidateId ,csrf.token,candidateemployerId
     3.ajax url implementation in CandidateListRestController.
--%> 
<script>

function removeEmployer(candidateemployerId,candidateId){
	
	 var sourceleadUrl ='${sourceleadUrl}';
	 
	 $.ajax({  
          type : "POST",
          url: sourceleadUrl+"/removeCandidateEmployerBycandidateIdandCandidateEmployerID?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId+"&candidateemployerId="+candidateemployerId,        
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
             			candidateEmployersList(candidateId);
			    	}
               } 
        },
          
     }); 
}
</script>


<%-- 
     @author : David Sammeta.
     this script is for onclick function iscurrentPosition  addExperience  .
     1.usage     :  used in addExperience  function  line 124
--%> 
<script>
function iscurrentPosition(){
	
	  var bk =document.getElementById("isCurrentPosition").checked;
			 if( bk) {
				 document.getElementById("endDateDiv").style.display = 'none';
				 $('#endYear').removeAttr('data-validation');
				 $('#endMonth').removeAttr('data-validation');
			 }
	       else if(document.getElementById("endDateDiv").style.display == 'none'){
	    	   document.getElementById("endDateDiv").style.display = 'block';
	    	   $("#endYear").attr("data-validation", "required");
	    	   $("#endMonth").attr("data-validation", "required");
	       }  
}
</script>
             
