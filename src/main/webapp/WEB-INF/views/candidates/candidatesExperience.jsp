<script>
function candidateExperience(){  
	var result ="";
	result = result  +'		 <div class="project_tabs_inn_head">                                                                            ';                          
	result = result  +'	     <a href="#" onClick="addExperience();" class="addUser-org">                                                                               ';
	result = result  +'	           <i class="glyphicon glyphicon-plus"></i>Add Employer\'s                                                    ';
	result = result  +'	     </a>                                                                                                           ';
	result = result  +'	     	<!-- Popup Div -->                                                                                          ';
	result = result  +'	       <div class="edit-details col-md-4"> <i class="fa fa-close close-div"></i>                            ';
	result = result  +'	         <form:form id="employers_form" name="employers_form" method="post">';
	result = result  +'	             <div class="form-horizontal clearfix">                                                                 ';
	result = result  +'	               <div class="clearfix mb10">                                                                          ';
	result = result  +'	                 <div class="col-md-12">                                                                            ';
	result = result  +'	                  <input type="text" class="form-control"  placeholder="Employer *" name="employerName" data-validation="required validate_Space validate_length length2-100" field-name="Employer Name" value=""> <span class="hidden">Employer Name</span>';
	result = result  +'	                 </div>                                                                                             ';
	result = result  +'	               </div>                                                                                               ';
	result = result  +'	               <div class="clearfix mb10">                                                                          ';
	result = result  +'	                 <div class="col-md-12 checkbox">                                                                   ';
	result = result  +'	                   <label>                                                                                          ';
	result = result  +'	                  <label> <input type="checkbox" id="isCurrentPosition" value="Y" name="isCurrentPosition"> Is Current Position</label>               ';
	result = result  +'	                 </div>                                                                                             ';
	result = result  +'	               </div>                                                                                               ';
	result = result  +'	               <div class="clearfix mb10">                                                                          ';
	result = result  +'	                 <div class="col-md-12">                                                                            ';
	result = result  +'	                    <input type="text" class="form-control"  placeholder="Employer Designation" name="employerDesignation" data-validation="required validate_Space validate_length length2-100" field-name="Employer Designation" value=""> <span class="hidden">Employer Designation</span>';
	result = result  +'	                 </div>                                                                                             ';
	result = result  +'	               </div>                                                                                               ';
	result = result  +'	               <div class="clearfix mb10">                                                                          ';
	result = result  +'	                 <div class="col-md-12">                                                                            ';
	result = result  +'	                  <input type="text" class="form-control input-sm" placeholder="Address" name="employerAddress" data-validation="required validate_Space validate_length length2-100" field-name="Address" value=""> <span 	class="hidden">Address</span>';
	result = result  +'	                 </div>                                                                                             ';
	result = result  +'	               </div>                                                                                               ';
	result = result  +'	               <div class="clearfix mb10">                                                                          ';
	result = result  +'	                 <p class="ml">Start Date</p>';
	result = result  +'	                 <div class="col-md-6">                                                                             ';
	result = result  +'	                   <select class="chosen-select">                                                                   ';
	result = result  +'	                       <option value="Select Month">Select Month</option>                                           ';
	result = result  +'	                       <option value="January">January</option>                                                     ';
	result = result  +'	                       <option value="February">February</option>                                                   ';
	result = result  +'	                       <option value="March">March</option>                                                         ';
	result = result  +'	                       <option value="April">April</option>                                                         ';
	result = result  +'	                       <option value="April">May</option>                                                           ';
	result = result  +'	                       <option value="April">June</option>                                                          ';
	result = result  +'	                       <option value="April">Jully</option>                                                         ';
	result = result  +'	                       <option value="April">August</option>                                                        ';
	result = result  +'	                       <option value="April">September</option>                                                     ';
	result = result  +'	                       <option value="April">October</option>                                                       ';
	result = result  +'	                       <option value="April">November</option>                                                      ';
	result = result  +'	                       <option value="April">December</option>                                                      ';
	result = result  +'	                   </select>                                                                                        ';
	result = result  +'	                   </div>                                                                                           ';
	result = result  +'	                   <div class="col-md-6">                                                                           ';
	result = result  +'	                   <input type="text" max class="form-control" value="" id="startDate" name="startDate"   placeholder="Enter Year"  field-name="Start Date" data-validation="required" >';
	result = result  +'	                 </div>                                                                                             ';
	result = result  +'	                 </div>                                                                                             ';
	result = result  +'	                 <div class="clearfix mb10 endDate">                                                                ';
	result = result  +'	                 <p class="ml">End Date</p>                                                                         ';
	result = result  +'	                 <div class="col-md-6">                                                                             ';
	result = result  +'	                   <select class="chosen-select">                                                                   ';
	result = result  +'	                       <option value="Select Month">Select Month</option>                                           ';
	result = result  +'	                       <option value="January">January</option>                                                     ';
	result = result  +'	                       <option value="February">February</option>                                                   ';
	result = result  +'	                       <option value="March">March</option>                                                         ';
	result = result  +'	                       <option value="April">April</option>                                                         ';
	result = result  +'	                       <option value="April">May</option>                                                           ';
	result = result  +'	                       <option value="April">June</option>                                                          ';
	result = result  +'	                       <option value="April">Jully</option>                                                         ';
	result = result  +'	                       <option value="April">August</option>                                                        ';
	result = result  +'	                       <option value="April">September</option>                                                     ';
	result = result  +'	                       <option value="April">October</option>                                                       ';
	result = result  +'	                       <option value="April">November</option>                                                      ';
	result = result  +'	                       <option value="April">December</option>                                                      ';
	result = result  +'	                   </select>                                                                                        ';
	result = result  +'	                   </div>                                                                                           ';
	result = result  +'	                   <div class="col-md-6">                                                                           ';
	result = result  +'	                   <input type="text" max class="form-control" value="" id="endDate" name="endDate" field-name="End Date"  placeholder="Enter Year" data-validation="required" >';
	result = result  +'	                 </div>                                                                                             ';
	result = result  +'	                 </div>                                                                                             ';
	result = result  +'	               <div class="clearfix mr">                                                                            ';
	result = result  +'	                 <button type="submit"  onClick ="candidateEmployersStore();"class="btn btn-sm btn-primary pull-right">Add</button>                       ';
	result = result  +'	               </div>                                                                                               ';
	result = result  +'	             </div>                                                                                                 ';
	result = result  +'	        </form:form>                                                                                  ';
	result = result  +'	           </div>                                                                                                   ';
	result = result  +'			<!-- Popup Div -->                                                                                          ';
	result = result  +'	 </div>                                                                                                             ';
	result = result  +'	 <div class="project_tabs_inn_table vendor_wrap_inn_table">                                                         ';
	result = result  +'	   <table>                                                                                                          ';
	result = result  +'	     <tbody>                                                                                                        ';
	result = result  +'	       <tr>                                                                                                         ';
	result = result  +'	         <th>Employer</th>                                                                                          ';
	result = result  +'	         <th>Designation</th>                                                                                       ';
	result = result  +'	         <th>Address</th>                                                                                           ';
	result = result  +'	         <th>Start date</th>                                                                                        ';
	result = result  +'	         <th>End date</th>                                                                                          ';
	result = result  +'	         <th>Action</th>                                                                                            ';
	result = result  +'	       </tr>                                                                                                        ';
	result = result  +'	       <tr>                                                                                                         ';
	result = result  +'	         <td><div><span>TCS Pvt Ltd</span></div></td>                                                               ';
	result = result  +'	         <td><div><span>Web developer</span></div></td>                                                             ';
	result = result  +'	         <td><div><span>Hyderabad Hyderabad</span></div></td>                                                       ';
	result = result  +'	         <td><div><span>30/08/2016</span></div></td>                                                                ';
	result = result  +'	         <td><div><span>30/09/2016</span></div></td>                                                                ';
	result = result  +'	         <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>  ';
	result = result  +'	       </tr>                                                                                                        ';
	result = result  +'	     </tbody>                                                                                                       ';
	result = result  +'	   </table>                                                                                                         ';
	result = result  +'	 </div>                                                                                                             ';
	result = result  +'	 <nav>                                                                                                              ';
	result = result  +'	   <ul id="pagingdg" class="pagination">                                                                            ';
	result = result  +'	     <li><a href="#">Previous</a></li>                                                                              ';
	result = result  +'	     <li><a href="#">2</a></li>                                                                                     ';
	result = result  +'	     <li><a href="#">3</a></li>                                                                                     ';
	result = result  +'	     <li class="disabled"><a href="#">6</a></li>                                                                    ';
	result = result  +'	     <li data-page="last"><a href="#">Next</a></li>                                                                 ';
	result = result  +'	   </ul>                                                                                                            ';
	result = result  +'	   <div class="clearfix"></div>                                                                                     ';
	result = result  +'	 </nav>                                                                                                             ';
	
    $("#tab-1").empty();
    $("#tab-2").empty(); 
    $("#tab-3").empty();  
    $("#tab-4").empty();  
    $("#tab-5").empty();  
    $("#tab-6").empty();  
    $("#tab-3").append(result);  
	
}

</script>

<script>
function candidateEmployersStore(){
	  var validationSettings = {
				errorMessagePosition : 'element',
				borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd'
			};
	  if ($('#employers_form').validate(false, validationSettings)) {
		  
			 var frm = $('#employers_form').serializeFormJSON();
			 var con = JSON.stringify(frm);
			 con = con.replace(/[{}]/g, "");
			
		     var jsonData = encodeURIComponent(con);
			 var sourceleadUrl = '${sourceleadUrl}';
			 $("#employers_form").attr("disabled", "disabled");

			$.ajax({
					type : "POST",
					url : sourceleadUrl
							+ "/storeCandidateEmployers?${_csrf.parameterName}=${_csrf.token}&json="+jsonData,
							 success :function(data) {
				                 if(data != null && data != 'undefined' && data != ""){
				               		if( data.indexOf('Sign In') >= 0){
							    		location.replace(sourceleadUrl);
							    	}
				               		else if (data.indexOf('exception') >= 0) {
				               			 location.replace(sourceleadUrl+"/errorPage");
				               		}
				               		else{
							    		 $("#timesheetApprList").removeAttr("style");
					                	 $("#timesheetApprList").empty();
					                     $("#timesheetApprList").append(data); 
					                     pagination();
							    	}
				                 }else{
				                	 $("#timesheetApprList").removeAttr("style");
				  	    	         var result ="";
						    	     result = result+"   <div class='row'>"; 
									 result = result+"	<div class='col-lg-12'>"; 
									 result = result+"		<div class='content-area clearfix'>"; 
									 result = result+"			<div class='blank-pages'>";
									 result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
									 result = result+"			        <p>No Timesheets Found</p>";
									 result = result+"			 </div>";
									 result = result+"		</div>"; 
									 result = result+"	</div>"; 
									 result = result+"  </div> "; 
									$("#timesheetApprList").empty();
								    $("#timesheetApprList").append(result);
				                 }
				               
				          },
					});
			return true; 
		} else {
			alert("veda");
			return false;
		}
} 
	  $('body').on('blur', '#employers_form', function() {
			$("#employers_form").showHelpOnFocus();
			$("#employers_form").validateOnBlur(false, validationSettings);
		});
</script>        
          
<script>
function addExperience() {	
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

            





             
