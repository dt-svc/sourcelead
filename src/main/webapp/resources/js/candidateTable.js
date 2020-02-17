function candidateListCheck(searchValue,universalCanddateSearchList,candidateKeyword,candidateIdSearchId){
	if(universalCanddateSearchList=="universalCanddateList")	 {
		$("#dataTableSearch").val(searchValue);
		candidateList();
	} else if(candidateKeyword == "candidateSearch"){
		candidateSideBar(candidateIdSearchId);
		candidateQuickViewProfile(candidateIdSearchId);
		candidateList();
		$("#upcandidateStatus").val('');
	} else if(candidateKeyword == "requestedDocumentNotification"){
		candidateSideBar(candidateIdSearchId);
		candidateQuickViewProfile(candidateIdSearchId);
		candidateList();
		$("#upcandidateStatus").val('');
	} else {
		candidateList();
		$("#upcandidateStatus").val('');
	}
 }
 
  function candidateList(type){
	  var candFilterRadio = $('input[name=candidateStatus]');
 	  var candidateStatus = candFilterRadio.filter(':checked').val();
	  
 	  if(candidateStatus == 1){
           $("#canDiv").text("Active Candidates");
       }else if(candidateStatus == 0){
     	  $("#canDiv").text("In Active Candidates");
       }else if(candidateStatus == 2){
     	  $("#canDiv").text("All Candidates");
       }else{
           $("#canDiv").removeAttr("onClick");
       }
	  
 	 var employmnetTypes = [];
 	 $('input:checkbox[name=employmentType]:checked').each(function() 
      {
 		employmnetTypes.push($(this).val());
 	 });
 	 
 	var candidateFilterSkills = [];
	 $('input:checkbox[name=candidateFilterSkills]:checked').each(function() 
     {
		 candidateFilterSkills.push($(this).val());
	 });
	 
	 
	 var availability = []
	 $('input:checkbox[name=availability]:checked').each(function() 
	     {
	       availability.push($(this).val());
		 });
	 
	  var candidateTypeRadio = $('input[name=candidateTypeFilter]');
	  var candidateTypeFilter = candidateTypeRadio.filter(':checked').val();
	 
	 
 	 
 	  var candTypeRadio = $('input[name=candidateType]');
 	  var candidateType = candTypeRadio.filter(':checked').val();
 	  
 	 var createdByRadio = $('input[name=createdByFilter]');
	  var createdByFilter = createdByRadio.filter(':checked').val();
	  
	 	var upcandidateStatus =   $('input[name=upcandidateStatus]:checked').val();
		  
	 	  $("#candidateList").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	 	 
		
		var minRate = $('#minRate').val(); 
		var maxRate = $('#maxRate').val();
		 if((minRate != null && minRate != "" && minRate !='undefined') || (maxRate != null && maxRate != "" && maxRate !='undefined')){
			 $('#rateType').removeAttr('data-validation-optional');
			 $('#rateType').attr("data-validation","required"); 
			 
			 $('#currency').removeAttr('data-validation-optional');
			 $('#currency').attr("data-validation","required"); 
		 }else{
			 $('#rateType').removeAttr('data-validation');
			 $('#rateType').attr("data-validation-optional","true"); 
			 
			 $('#currency').removeAttr('data-validation');
			 $('#currency').attr("data-validation-optional","true"); 
		 }
		var validationSettings = {
				errorMessagePosition : 'element',
				borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd'
			};
		
		 if ($('#candidatelist_form').validate(false, validationSettings)){ 
			
			var frm = $('#candidatelist_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, ""); 
		    var jsonData = encodeURIComponent(con);
			var form = $('#candidatelist_form')[0];
	  		var formData = new FormData(form); 
	  		var searchValue = $("#dataTableSearch").val();
	  		searchValue = searchValue ? searchValue : "";
	  	//skip % (30%) in profile percentage	
	  		if(searchValue.endsWith('%')){
	  			searchValue=searchValue.split('%')[0];
	  		}
	  		
	  		var url = atsUrl+"/searchCandidate?"+_csrfParam+"="+_csrfToken+"&candidateStatus="+candidateStatus+"&searchValue="+searchValue+"&updatedDateRange="+upcandidateStatus+"&isAssociate="+isAssociate+"&candidateType="+candidateType+"&employmnetTypes="+employmnetTypes+"&candidateFilterSkills="+candidateFilterSkills+"&availability="+availability+"&candidateTypeFilter="+candidateTypeFilter+"&createdByFilter="+createdByFilter;
	  		url += "&"+$($('#candidatelist_form')[0]).serialize();
	  		if(type == 'gMarketPlace'){
	  			callMarketPlaceCandidates(url)
	  		}else{
	  			prepareCandidateList(url);
	  		}
			return true;
		 }else{ 
				return false;
		 } 
	} 

	$('body').on('blur', '#candidatelist_form', function() {
		$("#candidatelist_form").showHelpOnFocus();
		$("#candidatelist_form").validateOnBlur(false, validationSettings);
	});
	
	function callMarketPlaceCandidates(urlGlobal){
		$.ajax({
			type : "POST",
			url : urlGlobal,
			success : function(object) {
				var candidateIdsArray = [];
				if(object.data != null && object.data.length != 0){
					for(var i=0;i<object.data.length;i++){
						var objectArray = object.data[i]
						candidateIdsArray.push(objectArray.candidateId)
					}
				}
				getGloblaMarketPlaceCandidates(globalMarketDivType, candidateIdsArray);
				candidateIdsArray = [];
			}
		});
	}
	
  function prepareCandidateList(url){
 	     $("#candidateList").removeAttr("style");
     	 $("#candidateList").empty();
     	  var result = "";
     	  result = result +"<table id='candidatePagination' class='display' cellspacing='0' width='100%' >"; 
          result = result +"<thead>"; 
          result = result +"  <tr>"; 
  	      result = result +'<th class="text-center"><input type="checkbox" id="sharedcheckbox" onclick="toggleShareCandidates(this);"/></th>'; 
          result = result +"    <th>&nbsp;</th>";
          result = result +"    <th>Generated ID</th>";
          result = result +' <div class="tbldata-dotdot-vsm"> <th>Name</th></div>'; 
          result = result +"    <th>Primary Skill</th>"; 
          result = result +"    <th>Employment Type</th>"; 
          result = result +"    <th>Email</th>"; 
          result = result +"    <th>Mobile Number</th>";
          result = result +"    <th>Location</th>";
          result = result +"    <th>Last Update</th>"; 
          result = result +"    <th>Profile %</th>"
          result = result +"    <th>No Of Jobs</th>"; 
          result = result +"    <th>Created By</th>"; 
          result = result +"  </tr>"; 
          result = result +"</thead>";
          result = result +"<tbody>"; 
          result = result +" </tbody>";
          result = result +"</table>";
          $("#noCandidates").val(1);
          $("#candidateList").append(result);
          cpagination(url); 
          shareCandidates = {};
  }
  
  
  function candidateSearchHighlight(){
 	 setTimeout(function () {
  		var body = $( table.table().body() );
 	        body.unhighlight();
 	        body.highlight( $("#dataTableSearch").val() );   
 			
 			}, 1000); 
  }
 
 	$(document).on( 'init.dt.dth', function (e, settings, json) {
     table = new $.fn.dataTable.Api( settings );
     var body = $( table.table().body() );
     if ( $( table.table().node() ).hasClass( 'searchHighlight' ) || settings.oInit.searchHighlight ||  $.fn.dataTable.defaults.searchHighlight ) {
 	
 	}
 });

function cpagination(url){ 
	var colIndex = 0;
	/*$.fn.dataTable.moment( 'DD-MM-YYYY' );*/
    $('#candidatePagination').DataTable( { 
    		"processing": true,
        "serverSide": true,
        "searching": false,
        "searchHighlight": true,
        "ajax": $.fn.dataTable.pipeline( {
            url: url,
            method: 'POST',
            pages: 10
        }),
        "columns": [
        	{ "data": "id" },
            { "data": "isAssociate" },
            { "data": "generatedId"},
            { "data": "firstName"},
            { "data": "primarySkill" },
            { "data": "designationType"},
            { "data": "email" },
            { "data": "mobilePhone" },
            { "data": "country"},
            { "data": "updatedDate" },
            { "data": "profilePercentage" },
            { "data": "noOfJobs"},
            { "data": "createdBy" }
        ],
        "columnDefs": [ {
    	  	"targets": colIndex++,
    	  	"sortable": false,
    	  	"render": function(data, type, report, meta) {
    	  		 var result = "";
    	  		 var shareflag = false;
	        		 for(var e in shareCandidates) {
   	     	    	   if(!e || !shareCandidates[e] || shareCandidates[e] === "")
   	     				   continue;
   	     	    	   if(e === report.candidateId+"") {
   	     	    		    if(!$("#sharedcheckbox").is(":checked"))
   	     	    		 	checkOrUncheckParent("candidatePagination",shareCandidates, "sharedcheckbox"); 
   	     	    		    shareflag = true;
   	     	    	   	break;
   	     	    	   }
   	        		 }
	        		 if(!shareflag)
   	        			$("#sharedcheckbox").removeAttr('checked');
	        			result = result +'  <td><input'+(shareflag ? " checked='checked'" : "")+' class="contactscheckbox" name="sharedcheckbox"  type="checkbox" id="'+report.candidateId+'" data-id="'+report.candidateId+'" value="'+report.candidateId+'" onclick="appendIdsToMarketPlace(this);" data-uuid="'+report.candidateUUID+'" onchange="checkShareCandidates(this)"/></td>';
    	  		 return result; 
    	  	}
        	},{
	    	  	"targets": colIndex++,
	    	  	//"className": "tbldata-dotdot_50",
	    	  	"data": "isAssociate",
	    	  	"sortable": false,
	    	  	"render": function(data, type, full, meta) {
	    	  		var result = "";
	    	  		var isFavourite = "";
	    	  		var isFavClass = "";
	    	  		var title = "title = 'Add as Favourite'";
	    	  		if(full.isFavourite == '1' && isAssociate == "N"){
	            		isFavourite = "checked";
	            		isFavClass = "activestar-rating";
	            		title = "title = 'Change to un Favourite'";
	            	} else if(full.isFavouriteAssociate == '1' && isAssociate == "Y") {
	            		isFavourite = "checked";
	            		isFavClass = "activestar-rating";
	            	}
	    	  		result = result +"<div class='tblminwidth-75 pull-left'>";
	    	  		if(data == "Y" && isAssociate == 'N'){
	    	  			result = result +"&nbsp;<i class='fa fa-black-tie pull-right mt10' title='Added as Associate'></i> "; 
	    	  			if(full.isSharedInMarketplace == 'Y'){
	  	    	  		  result = result +"&nbsp;<i class='fa fa-address-book-o pull-right mt10 mr10' title='Shared in Marketplace'></i> "; 
	  	    	  		}
		             }
	    	  		else if(full.isSharedInMarketplace == 'Y'){
		    	  		  result = result +"&nbsp;<i class='fa fa-address-book-o pull-right mt10 mr25' title='Shared in Marketplace'></i> "; 
		    	  	}
	    	  		
	    	  		
		              result = result +"<div class='iconClass pull-left star-rating "+isFavClass+"' id='favourite"+full.candidateId+"'>";
		              if(associateFlag)
		            	  	result = result +"   <input type='checkbox' id='fav"+full.candidateId+"' "+isFavourite+" "+title+" onclick='addFavourite("+full.candidateId+")'></input>";
		             
		              result = result +" <label></label>";
		              result = result +"</div>";
		              result = result +"</div>";
		              return  result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "generatedId",
              "render": function ( data, type, full, meta ) {
            	  
              		var result = "";
              		if(full.generatedId != null && full.generatedId != '' && full.generatedId != "undefined"){
  	            	  		result = result +"<div><span title='"+full.generatedId+"'>"+full.generatedId+"</span></div>";
  	              	}else{
  	            	  		result = result +"<div>--</div>";
  	             	}
  	              return result;
              }
	          },{
            "targets": colIndex++,
            "data": "firstName",
            "render": function ( data, type, full, meta ) {
            		var result = "";
            		var lastName = "";
            		var middleName = "";
            		if(full.lastName != null && full.lastName != "" && full.lastName != 'undefined'){
	            		lastName = full.lastName;
	            	}else{
	            		lastName = "";
	            	}
            		if(full.middleName != null && full.middleName != "" && full.middleName != 'undefined'){
	            		middleName = full.middleName;
	            	}else{
	            		middleName = "";
	            	}
            		if(name1Flag)
	                result = result +"  <td><div class='proj_show divmax-width' onclick='candidateQuickViewProfile("+full.candidateId+");'><span title='"+full.firstName+" "+middleName+"  "+lastName+"'>"+full.firstName+" "+middleName+"  "+lastName+"</span><i class='fa fa-binoculars'></i></div></td>";
	              
	              if(name2Flag)
	               result = result +"  <td><div class='proj_show' onclick='#'><span title='"+full.firstName+" "+middleName+" "+lastName+"'>"+full.firstName+" "+middleName+" "+lastName+"</span></div></td>";
	              return result;
            }
          }, {
              "targets": colIndex++,
              "data": "primarySkill",
              "render": function ( data, type, full, meta ) {
              		var result = "";
              		if(full.primarySkill != null && full.primarySkill != '' && full.primarySkill != "undefined"){
  	            	  		result = result +"<div><span title='"+full.primarySkill+"'>"+full.primarySkill+"</span></div>";
  	              	}else{
  	            	  		result = result +"<div>--</div>";
  	             	}
  	              return result;
              }
            }, {
                "targets": colIndex++,
                "data": "designationType",
                "render": function ( data, type, opt, meta ) {
                		var result = "";
                		if(opt.designationType != null && opt.designationType != '' && opt.designationType != "undefined")
   	             	 {
   	            	  if(opt.designationOtherType !=null && opt.designationOtherType !='' && opt.designationOtherType !="undefined"){
   	                   result = result +"<div>"+opt.designationOtherType+"</div>";
   	             	 }else{
   	             		result = result +"<div>"+opt.designationType+"</div>"; 
   	             	 }
   	             	 }else{
   	             		 result = result +"<div>--</div>"; 
   	             	 }
    	              return result;
                }
          },{
	    	  	"targets": colIndex++,
	    	  	"data": "email",
	    	  	"render": function(data, type, opt, meta) {
	    	  		return "<div ><span title='"+opt.email+"'>"+opt.email+"</span></div>";
	    	  	}
	    	  },{
	    	  	"targets": colIndex++,
	    	  	"data": "mobilePhone",
	    	  	"sortable": false,
	    	  	"render": function(data, type, full, meta) {
	    	  		var mobilePhone = "";
	    	  		if(full.mobilePhone != null && full.mobilePhone != 'undefined' && full.mobilePhone != ""){
	         		       mobilePhone = full.mobilePhone;
		            	}else{
		            		mobilePhone = "--";
		            	}
	    	  		return "<div class='tbldata-dotdot-vsm'>"+mobilePhone+"</div>";
	    	  	}
	    	  },{
                  "targets": colIndex++,
                  "data": "country",
                  "render": function ( data, type, opt, meta ) {
                  		var location = "";
                  		if(opt.country != null && opt.country != "" && opt.country != 'undefined'){
		            			location = opt.country;
			            		if(opt.state != null && opt.state != "" && opt.state != 'undefined'){
			            			 location = opt.state+", "+location;
				            	}
			            		if(opt.city != null && opt.city != "" &&  opt.city != 'undefined'){
			            			 location = opt.city+", "+location;
				            	}
		            		}else{
		            			location = "--";
		            		}
      	              return "<div><span title='"+location+"'>"+location+"</span></div>";
                  }
                },{
                  "targets": colIndex++,
                  "data": "updatedDate",
                  "className": "tblminwidth-75",
                  "render": function ( data, type, opt, meta ) {
                  		var result = "";
                  		if(opt.candidateUpdateDateToString != null && opt.candidateUpdateDateToString != 'undefined' && opt.candidateUpdateDateToString != ""){
                  			result = "<div>"+opt.candidateUpdateDateToString+"</div>"; 
			            	}else{
			            		result = "<div>--</div>";
			            	}
      	              return result;
                  }
                },{
		    	  	"targets": colIndex++,
		    	  	"data": "profilePercentage",
		    	  	"sortable": false,
		    	  	"render": function(data, type, opt, meta) {
			            	
		    	  		return "<div class='profilePercentageClass"+opt.candidateId+"'><div id='"+opt.candidateId+"profilePercentage'><i class='fa fa-spinner fa-pulse'></i></div></div>";
		    	  		
		    	  	}
		    	  }, {
	    	    	  	"targets": colIndex++,
	    	    	  	"data": "noOfJobs",
	    	    		"sortable": false,
	    	    	  	"render": function(data, type, opt, meta) {
	    	    	  	 return "<div class='tbldata-dotdot-vsm submittedRequirementCount' id='"+opt.candidateId+"'><a href='#' onclick='getSubmittedJobsList("+opt.candidateId+")'><div id='"+opt.candidateId+"submittedRequirementCount'><i class='fa fa-spinner fa-pulse'></i></div></div></a></div>";
	    	    	  		
	    	    	  	}
    	    	  },{
                    "targets": colIndex++,
                    "data": "createdBy",
                    "className": "tblminwidth-75",
                    "render": function ( data, type, opt, meta ) {
                    		var result = "";
                    		if(opt.createdBy != null && opt.createdBy != 'undefined' && opt.createdBy != ""){
                    			return "<div><span title='"+opt.createdBy+"'>"+opt.createdBy+"</span></div>";
  			            	}else{
  			            		result = "<div>--</div>";
  			            	}
        	              return result;
                    }
                  } ],
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"aaSorting": [[9,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     },
	     drawCallback: function(){
   	      $('a.paginate_button', this.api().table().container()).on('click', function(){
   	    	candidateSearchHighlight();
	         }); 
   	   getCandidateSubmittedJobsCount();
	   }
    } );
    $("#candidatePagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#dataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
		candidateList();
		candidateSearchHighlight();
    	   }
    	});
}


function getCandidateSubmittedJobsCount(){
	 $('.submittedRequirementCount').each(function() {
		 var candidateId = this.id;
		  var candidateTypeRadio = $('input[name=candidateTypeFilter]');
		  var candidateTypeFilter = candidateTypeRadio.filter(':checked').val();
		 if(candidateId != null &&  candidateId !='undefined' && candidateId != ''){
			 $.ajax({
		           type : "GET",
		           url: atsUrl+"/getCandidateSubmittedRequirementCount?candidateId="+candidateId+"&candidateType="+candidateTypeFilter,    
		            success :function(data) {
		            	  $("#"+candidateId+"submittedRequirementCount").empty();
		             	  $("#"+candidateId+"submittedRequirementCount").html(data.submittedJobsCount);
		             	  
		             	  $("#"+candidateId+"profilePercentage").empty();
		             		
		             	      if(parseInt(data.profilePercentage) >= 50){
		             	    	 $(".profilePercentageClass"+candidateId).addClass("tbldata-dotdot-vsm text-green");
			    	  			}else{
			    	  				 $(".profilePercentageClass"+candidateId).addClass("tbldata-dotdot-vsm colorRed");
			    	  			}
		             	  $("#"+candidateId+"profilePercentage").html(data.profilePercentage+ "%");
		             	  
		             	 
		            }
			    });
		 }
			
   });  
}

function getSubmittedJobsList(candidateId){
	$.ajax({
		  type: "GET",
		    url: atsUrl+"/getCandidateSubmittedJobsList?candidateId="+candidateId,
		     success :function(response) {
		    	    $("#candidateSubmittedJobsDiv").empty();  
		    	    $("#candidateSubmittedJobsDiv").append(response);
		    	    $(".modal.paymentaccount-modal").fadeIn();
		    	    $(".comMask").fadeIn();
				},
			});
}