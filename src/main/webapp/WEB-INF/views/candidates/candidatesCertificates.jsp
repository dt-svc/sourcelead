<script>
function candidateCertificate(candidateId){
	 
	var result = "";
	result  = result + '   <div class="project_tabs_inn_head">                                                                          ';
	result  = result + '   	<a href="#" onClick="addCertification();" class="addUser-org"><i class="glyphicon glyphicon-plus"></i>Add Certification</a> ';
	result  = result + '     <!-- Popup Div -->                                                                                         ';
	result  = result + '         <div class="edit-details col-md-4"> <i class="fa fa-close close-div"></i>                                ';
	result  = result + '         <form id="Candidate_certification_form" name="Candidate_certification_form" method="post">        ';
	result  = result + '               <div class="form-horizontal clearfix">';
	result  = result + '                 <div class="clearfix mb10">';
	result  = result + '                   <div class="col-md-12" id="candidatename">';
	result  = result + '                     <input type="text" class="form-control input-sm" value="" name="name"  placeholder="Name *" ';
	result  = result + '                      data-validation="required validate_Space validate_length length2-100" field-name="Name" >';
	result  = result + '                   </div>';
	result  = result + '                 </div>';
	result  = result + '                 <div class="clearfix mb10">';
	result  = result + '                   <div class="col-md-12">';
	result  = result + '                     <input type="text" class="form-control input-sm" value="" name="provider" placeholder="Provider *"';
	result  = result + '                      data-validation="required validate_Space validate_length length2-100" field-name="Provider">';
	result  = result + '                   </div>';
	result  = result + '                 </div>';
	result  = result + '                 <div class="clearfix mb10">';
	result  = result + '                   <div class="col-md-12">';
	result  = result + '                     <input type="text" class="form-control input-sm" value="" name="score" placeholder="Score" ';
	result  = result + '                     data-validation="required" field-name="score">';
	result  = result + '                   </div>';
	result  = result + '                 </div>';
	result  = result + '                 <div class="clearfix mb10">';
	result  = result + '                   <div class="col-md-12">';
	result  = result + '                     <textarea class="form-control" rows="1" name ="description"  placeholder="Description"';
	result  = result + '                     data-validation="required"></textarea>';
	result  = result + '                   </div>';
	result  = result + '                 </div>';
	result  = result + '                 <div class="clearfix mb10">';
	result  = result + '                   <div class="col-md-6 positionRelative">';
	result  = result + '                     <input type="text" class="form-control input-sm"  value="" name ="validFrom" placeholder="Valid From" ';
	result  = result + '                     data-validation="required" field-name="Valid From">';
	result  = result + '                     <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>';
	result  = result + '                   </div>';
	result  = result + '                   <div class="col-md-6 positionRelative">';
	result  = result + '                     <input type="text" class="form-control input-sm" value="" name="validTo" placeholder="Validate To" ';
	result  = result + '                     data-validation="required" field-name="Valid To" >';
	result  = result + '                     <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>';
	result  = result + '                   </div>';
	result  = result + '                 </div>';
	result  = result + '                 <div class="clearfix mr">';
	result  = result + '                   <button type="submit" class="btn btn-sm btn-primary pull-right"  onclick="storeCertificate('+candidateId+');"  >Add</button>';
	result  = result + '                 </div>';
	result  = result + '               </div>';
	result  = result + '              </form>';
	result  = result + '             </div>';
	result  = result + '   </div>';
	
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




<!-- this script is for saving candidate certification  -->

<script>
function storeCertificate(candidateId){
	var validationSettings = {
			errorMessagePosition : 'element',
			borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd'
		};
	if ($('#Candidate_certification_form').validate(false, validationSettings)) {
	     var frm = $('#Candidate_certification_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, "");
		 var jsonData = encodeURIComponent(con);
		 var sourceleadUrl = '${sourceleadUrl}';
		 $("#Candidate_certification_form").attr("disabled", "disabled");
		
	 $.ajax({  
          type : "POST",
          url: sourceleadUrl+"/storeCandidateCertifications?${_csrf.parameterName}=${_csrf.token}&json="+jsonData+"&candidateId="+candidateId,      
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

$('body').on('blur', '#Candidate_certification_form', function() {
	$("#Candidate_certification_form").showHelpOnFocus();
	$("#Candidate_certification_form").validateOnBlur(false, validationSettings);
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


<!-- this script is for serializeFormJSON  End-->

<script>
function candidateCertificateList(candidateId){
	 var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({  
          type : "POST",
          url: sourceleadUrl+"/getCandidateCertificationListByCandidateIdAndOrgLocationID?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId,        
          success :function(data) {
               if(data != null && data != 'undefined' && data != ""){
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
	                    	 result  = result + '         <tr>';
	                    	 result  = result + '           <td><div><span>'+opt.name+'</span></div></td>';
	                    	 result  = result + '           <td><div><span>'+opt.provider+'</span></div></td>';
	                    	 result  = result + '           <td><div><span>'+opt.score+'</span></div></td>';
	                    	 result  = result + '           <td><div><span>'+opt.scoreType+'</span></div></td>';
	                    	 result  = result + '           <td><div><span>'+opt.validFrom+'</span></div></td>';
	                    	 result  = result + '           <td><div><span>'+opt.validTo+'</span></div></td>';
	                    	 result  = result + '           <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>';
	                    	 result  = result + '         </tr>';
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
               }
        },
          
     }); 
}
</script>
<script>
function addCertification() {	
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



       
            
            
