// below details need to be dynamic as of now used the user details of 
//narasimha.seeral@businessintelli.com for india location 
var sourceleadUrl ="http://localhost:8080/app";//'${sourceleadUrl}'; // setting common user settings 
//var locationId = "5";//'${SourceLeadWeb_orgLocationId}'; //narasimha
var orgLocationId = "3";//'${SourceLeadWeb_orgLocationId}'; snigitha
var locationList ="dd-MM-yyyy";//'${SourceLeadWeb_orgLocations.dateFormat}';	
//var userId ="31";// '${SourceLeadWeb_user.id}'; Narasimha
var userId ="4";// '${SourceLeadWeb_user.id}'; snigitha
//var orgLocationCode = "txhlIN"; //'${SourceLeadWeb_locationCode}'; Narasimha
var orgLocationCode = "775BUS";// snigitha
var orgCode = '775B'
//var candiadteId = "9105";//'${SourceLeadWeb_candidateId}'; Narasimha
var candiadteId = '9077';// snigitha
var isGlobalAccess = "N";//'${SourceLeadWeb_isGlobalAccess}';
var loginUserRole ="ROLE_ADMIN";// '${SourceLeadWeb_loginUserRole}';
var orgATS = "Y";//'${SourceLeadWeb_ats}';
var orgVMS ="Y";// '${SourceLeadWeb_vms}';
//var approverUserId ="31";// '${SourceLeadWeb_user.id}'; Narasimha
var approverUserId = '4'; // snigitha
//var loginUser = "Narasimha Seerla";//'${SourceLeadWeb_userName}';
var loginUser = 'Snigitha Paruchuri';
var date = $("#eventCalendar").val();
// its an entity commented now. var timezone ='${SourceLeadWeb_orgLocations.timezone}'; 
var requirementId = "";//'${requirementId}';
var timezoneId = "246" 
/* if(timezone != null && timezone != '' && timezone != 'undefined'){
	timezoneId ="246" ;//'${SourceLeadWeb_orgLocations.timezone.id}';
} */
var country = "India";//'${SourceLeadWeb_orgLocations.country}';
var locationDate = locationList; // locationList is unique variable using for date format.
var tabAccessArray = '';
var roleActionsArray = '';


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
	 
	 var candidateTypeFilter = []
	 $('input:checkbox[name=candidateTypeFilter]:checked').each(function() 
	     {
		 candidateTypeFilter.push($(this).val());
		 });
	 
	 
 	 
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
	  		var url = sourceleadUrl+"/searchCandidate?candidateStatus="+candidateStatus+"&searchValue="+searchValue+"&updatedDateRange="+upcandidateStatus+"&isAssociate="+isAssociate+"&candidateType="+candidateType+"&employmnetTypes="+employmnetTypes+"&candidateFilterSkills="+candidateFilterSkills+"&availability="+availability+"&candidateTypeFilter="+candidateTypeFilter+"&createdByFilter="+createdByFilter;
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
var registerAdvFilterEvents=function(){
  function closeCandidateFilterPop(){
		$("#candidateFilterPopup").hide();
		$(".advancedsearch-mask").fadeOut();
	}
	function openCandidateFilterPop(){
		$("#candidateFilterPopup").toggle();
		 $(".advancedsearch-mask").fadeIn();
	}
	function resetCandidateFilter(){
		clearCandidateSearchKeys();
		$("ul#TypeFilter > li input:radio").prop("checked",false);
		$("ul#AllTypeFilter > li input:radio").prop("checked",false);
		$("ul#UpdatedFilter > li input:radio").prop("checked",false);
		$("ul#employmentFilter > li input:checkbox").prop("checked",false);
		$("ul#availabilityFilter > li input:checkbox").prop("checked",false);
		$("ul#skillsUL > li input:checkbox").prop("checked",false);
		$("ul#candidateTypeFilter > li input:checkbox").prop("checked",false);
		$("ul#createdByTypeFilter > li input:radio").prop("checked",false);
		
		
		$("#dataTableSearch").val('');
		document.getElementById("candidateTypeAll").checked = true;
		document.getElementById("candidateAll").checked = true;
		document.getElementById("candidateCheck").checked = true;
		document.getElementById("myCandidatesRadio").checked = true;
		candidateList();
	}
}

function getSourceleadTags(){
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/getSourceleadTagsList?", 
		        success: function(json) {
		        	var output = "";
		        	for (var i=0; i<json.length; i++)
					{
			        		var opt = json[i];
			        		output = output + "<option value='" +opt+ "'>"+opt+"</option>";
			        		
					}
		        	$("#canTags").empty();
			        $("#canTags").append(output);
			        $('#canTags').trigger("chosen:updated");
		        },
		    }); 
}
function clearCandidateSearchKeys(){
	
    $("#anyOfKeyword").val('').trigger("chosen:updated");
    $("#allKeywords").val('').trigger("chosen:updated");
    $("#excludeKeyWord").val('').trigger("chosen:updated");
    $("#designation").val('').trigger("chosen:updated");
    $("#noticePeriod").val('').trigger("chosen:updated");
    $("#minExpYears").val('').trigger("chosen:updated");
    $("#maxExpYears").val('').trigger("chosen:updated");
  /*   $("#country").val('').trigger("chosen:updated"); */
    $("#state").val('').trigger("chosen:updated");
    $("#city").val('').trigger("chosen:updated");
    $("#education").val('').trigger("chosen:updated");
    $("#excludeEducation").val('').trigger("chosen:updated");
    $("#certification").val('').trigger("chosen:updated");
    $("#employers").val('').trigger("chosen:updated");
    $("#excludeEmployers").val('').trigger("chosen:updated");
    $("#applicantType").val('').trigger("chosen:updated");
    $("#candidateSkills").val('').trigger("chosen:updated");
    $("#candidateVisa").val('').trigger("chosen:updated");
    $("#primarySkill").val('').trigger("chosen:updated");
    
    $("#designationType").val('').trigger("chosen:updated");
    $('#candidatelist_form').find('p.jquery_form_error_message').remove();
    
     $("#anyOfKeyword").empty();
     $("#allKeywords").empty();
     $("#excludeKeyWord").empty();
     $("#designation").empty();
      $("#minRate").val(''); 
      $("#country").val(''); 
      $("#state").val('');
      $("#city").val('');
      
     $("#currency").val('');
     $('.selectpicker').selectpicker('refresh');
     
     $("#maxRate").val(''); 
     
     $("#rateType").val(''); 
     $('.selectpicker').selectpicker('refresh');
     
     $("#minExpYears").val(''); 
     $("#maxExpYears").val(''); 
     $('#expId').find('p.jquery_form_error_message').remove();
     
     $("#noticePeriod").val(''); 
     
     $("#country").val(''); 
     $("#state").val(''); 
     $("#city").val(''); 
     
     $("#designationType").val(''); 
     
     
     $("#education").val(''); 
     $("#excludeEducation").val('');
     $("#certification").val(''); 
     
     $("#employers").val(''); 
     $("#excludeEmployers").val(''); 
     $("#applicantType").val('');
     $("#candidateSkills").val('');
     $("#primarySkill").val('');
     $("#country").val(''); 
     $("#state").val(''); 
     $("#city").val(''); 
    
}
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
		 if(candidateId != null &&  candidateId !='undefined' && candidateId != ''){
			 $.ajax({
		           type : "GET",
		           url: sourceleadUrl+"/getCandidateSubmittedRequirementCount?candidateId="+candidateId,    
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
		    url: sourceleadUrl+"/getCandidateSubmittedJobsList?candidateId="+candidateId,
		     success :function(response) {
		    	    $("#candidateSubmittedJobsDiv").empty();  
		    	    $("#candidateSubmittedJobsDiv").append(response);
		    	    $(".modal.paymentaccount-modal").fadeIn();
		    	    $(".comMask").fadeIn();
				},
			});
}

function candidateQuickViewProfile(candidateId){
	  var skillsList = '${skillsList}'; 
	  $.ajax({
      type : "POST",
       url: sourceleadUrl+"/getCandidateById?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,        
       success :function(result) {  
      	 var json = result.object;
    	     if(result.response === "SESSION_TIMEDOUT") {
					location.replace(sourceleadUrl);
				} else if (result.response === "AWKWARD") {
					location.href = "/errorPage";
				} else if (result.response === "SUCCESS") {
            		if(json != null){  
							var result ="";
							
							if(json.country != null && json.country != '' && json.country != 'undefined'){
	         			 		country =json.country;
	             			 	}else{
	             			 		country = '';
	             			 	}
	         			 	
	         			 	if(json.state != null && json.state != '' && json.state != 'undefined'){
	         			 		state =json.state+',';
	             			 	}else{
	             			 		state = '';
	             			 	}
	         			 	
	         			 	if(json.city != null && json.city != '' && json.city != 'undefined'){
	         			 		city =json.city+',';
	             			 	}else{
	             			 		city = '';
	             			 	}	
	         			 	
	         			 	if(json.lastName != null && json.lastName != '' && json.lastName != 'undefined'){
	         			 		lastName =json.lastName;
	             			 	}else{
	             			 		lastName = '';
	             			 	}
	         			 	if(json.middleName != null && json.middleName !='' && json.middleName != 'undefined'){
	         			 		middleName = json.middleName;
	         			 	}else{
	         			 		middleName = "";
	         			 	}
	         			 	if(json.mobilePhone != null && json.mobilePhone != '' && json.mobilePhone != 'undefined'){
	         			 		mobilePhone =json.mobilePhone;
	             			 	}else{
	             			 		mobilePhone = '';
	             			 	}
	         			 	if(json.designationType != null && json.designationType != '' && json.designationType != 'undefined'){
	         			 		designationType =json.designationType+',';
	             			 	}else{
	             			 		designationType = '';
	             			 	}
	         			 	
	         			 	if(json.expectedRate != null && json.expectedRate != '' && json.expectedRate != 'undefined'){
	         			 		expectedRate = json.expectedRate;
	         			 		expectedRateCurrencey = '/'+json.expectedRateCurrencey;
	         			 	}else{
	         			 		expectedRate = '---';
	         			 		expectedRateCurrencey = '';
	         			 	}
	         			 	
	         			 	if(json.rateType != null && json.rateType != '' && json.rateType != 'undefined'){
	         			 		rateType = json.rateType;
	         			 	}else{
	         			 		rateType = '';
	         			 	}
	         			 	if(json.skillsName != null && json.skillsName != 'undefined' && json.skillsName != ''){
				               var skill = json.skillsName;
	         			 		var qvSkills = "";
	         			 		var res = [];
	         			 		res = skill.split(',');
	         			 		var minSkills = res.slice(1,20);
	         			 		var totalSkills = res.length;
	         			 		if(totalSkills > 20){
	         			 			var remainingSkillsCount = totalSkills - 20;
	         			 		}
	         			 		
				                qvSkills = "<ul>";
				                for(i = 0; i<=minSkills.length ; i++){
				                		if(res[i] != null && res[i] != '' && res[i] != 'undefined'){
					                		qvSkills += "<li>" + res[i] + "</li>";
					                	}
				                		if(totalSkills > 20 && i == minSkills.length){
				                			qvSkills += "<li style='background-color: #d0d0d0;'><i class='fa fa-plus'></i> " + remainingSkillsCount + " more</li>";
				                		}
				                }
				                qvSkills += "</ul>"; 
	         			 	}else{
	         			 		qvSkills = "";
	         			 	}
							
//					<!-- resumeView Starts -->
					result = result+'  <div class="resumeView">                                                                                                                                    ';
					result = result+'                                                                                                                                                              ';
					result = result+'      <!-- Candiate Header -->                                                                                                                                ';
					result = result+'      <div class="row">';
					result = result+'          <div class="resume-header clearfix">                                                                                                                ';
					result = result+'        <div class="col-md-7">                                                                                                                                ';
					result = result+'            <div class="media">                                                                                                                               ';
					result = result+'                <div class="media-body">                                                                                                                      ';
					result = result+'                    <h4 class="media-heading text-warning">'+json.firstName+' '+middleName+' '+lastName+'<small> '+designationType+'</small></h4>';
					if(json.summary != null && json.summary != '' && json.summary != 'undifined'){
						
						result = result+'      <div class="trimTxt">';
						result = result+'<p title="'+json.summary+'"><i class="fa fa-quote-left text-muted"></i>'+json.summary+'</p>';
						result = result+'      </div>';
					}else{
						result = result+'<p>---</p>';
					}
					
					result = result+'                </div>                                                                                                                                        ';
					result = result+'                <div class="media-right">                                                                                                                     ';
					if(json.fileUrl != null && json.fileUrl !='' && json.fileUrl !="undefined"){
					result = result+'<img data-id="profileImage" id="userImage" src="'+json.fileUrl+'" alt="Profile Image" class="media-object" style="width:65px">';
					}else{
					result = result+'<img data-id="profileImage" id="userImage" src="/resources/images/userprofileImg.jpg" alt="Profile Image" class="media-object" style="width:65px">';
					} 
					result = result+'  <div class="mb mt5 UsProBar">';                                                                                                                                        
						result = result+' <div class="progress nomr">  ';
					if(json.profilePercentage>=50){ 
						result = result+'   <div class="progress-bar  progress-bar-success  ';
					}
					else{
						result = result+'   <div class="progress-bar  progress-bar-danger  ';
					}
					result = result+' progress-bar-striped active" role="progressbar" id="quickPerVal" aria-valuenow="'+json.profilePercentage+'" aria-valuemin="0" aria-valuemax="100" style="width:'+json.profilePercentage+'%">  ';
					result = result+'     <p id="quickDisVal">'+json.profilePercentage+'%</p>                                                                                                                                                                      ';
					result = result+'   </div>  ';
					result = result+' </div>                 ';                                                                                                                                                      
					result = result+' </div> ';
					result = result+'                </div>                                                                                                                                        ';
					
					result = result+'            </div>                                                                                                                                            ';
					result = result+'        </div>                                                                                                                                                ';
					result = result+'          <div class="col-md-5">                                                                                                                              ';
					result = result+'              <div class="pull-right">                                                                                                                        ';
					result = result+'                  <ul class="list-unstyled">                                                                                                                  ';
					result = result+'                      <li><i class="fa fa fa-envelope paddingRight text-warning" title="Email"></i>'+json.email+'';
					result = result+'                      </li>                                                                                                                                   ';
					result = result+'                      <li><i class="fa fa-phone paddingRight text-warning" title="Mobile"></i>'+mobilePhone+'</li>                                              ';
					result = result+'                      <li><i class="fa fa-map-marker paddingRight text-warning" title="Location"></i>'+city+''+state+''+country+'</li>';
					result = result+'                    <li><i class="fa fa-usd paddingRight text-warning" title="Rate"></i>'+expectedRate+' '+rateType+''+expectedRateCurrencey+'</li>';
					result = result+'                  </ul>                                                                                                                                       ';
					result = result+'              </div>                                                                                                                                          ';
					result = result+'          </div>                                                                                                                                              ';
					result = result+'              <hr>                                                                                                                                            ';
					result = result+'              </div>                                                                                                                                          ';
					result = result+'      </div>                                                                                                                                                  ';
					result = result+'        <!-- Candiate Header -->                                                                                                                              ';
					result = result+'                                                                                                                                                              ';
					result = result+'      <!-- Work Experience and Skills -->                                                                                                                     ';
					result = result+'      <div class="row">                                                                                                                                       ';
					result = result+'                                                                                                                                                              ';
					result = result+'          <!-- Work Experience and Education BLock -->                                                                                                        ';
					result = result+'          <div class="col-md-6 paddingLeft">                                                                                                                              ';
					result = result+'              <div class="row">                                                                                                                               ';
					//Employee Div
					result = result + '			<div id="qvemployeeSubmission"></div> '; 
					//Education Div
					result = result + '			<div id="qveducationSubmission"></div> ';
					result = result+'              </div>                                                                                                                                          ';
					result = result+'          </div>                                                                                                                                              ';
					result = result+'          <!-- Work Experience and Education BLock -->                                                                                                        ';
                  result = result+'                                                                                                                                                              ';
					result = result+'          <!-- Skills, Achievements &amp; Certificates BLock -->                                                                                              ';
					result = result+'          <div class="col-md-5 col-md-offset-1 paddingLeft">                                                                                                              ';
					result = result+'              <div class="row">                                                                                                                               ';
					if(json.skillsName != null && json.skillsName != 'undefined' && json.skillsName != ''){
						result = result+'              <div class="col-md-12">                                                                                                                         ';
						result = result+'                  <h4>Skills</h4>                                                                                                           ';
						result = result+'                  <div class="skillsView row">                                                                                                                ';
						result = result+'                  <div class="panel panel-default">                                                                                                                ';
						result = result+'                  <div class="panel-body" id="skillsView"> '+qvSkills+'';
						result = result+'                  </div>';
						result = result+'                  </div>';
						result = result+'                  </div>';
						result = result+'               </div>';
					}
					//Certification Div
					result = result + '<div id="qvcertificationSubmission"></div> ';
					
					result = result+'              </div>                                                                                                                                          ';
					result = result+'          </div>                                                                                                                                              ';
					result = result+'           <!-- Skills, Achievements &amp; Certificates BLock -->                                                                                             ';
					result = result+'      </div>                                                                                                                                                  ';
					result = result+'      <!-- Work Experience and Skills -->                                                                                                                     ';
					result = result+'  </div>                                                                                                                                                      ';
//						      <!-- resumeView -->
						
					$("#candidateQuickViewDiv").empty();
				    $("#candidateQuickViewDiv").append(result); 
				    
				    qvemployeeSubmission(candidateId);
				    qveducationSubmission(candidateId);
					qwikCertificates(candidateId);
					candidateSideBar(candidateId); 
            	}else{
     	    	         var result ="";
    		    	     result = result+"   <div class='row'>"; 
    					 result = result+"	<div class='col-lg-12'>"; 
    					 result = result+"		<div class='content-area clearfix'>"; 
    					 result = result+"			<div class='blank-pages'>";
    					 result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
    					 result = result+"			        <p>No Candidates Found</p>";
    					 result = result+"			 </div>";
    					 result = result+"		</div>"; 
    					 result = result+"	</div>"; 
    					 result = result+"  </div> "; 
    					$("#candidateQuickViewDiv").empty();
    				    $("#candidateQuickViewDiv").append(result); 
                    }
				} else if (result.indexOf('Sign In') !== -1) {
					location.replace(sourceleadUrl);
				} else {
					showError("Operation failed, please try again");
				}
             },
               
          }); 
}
	function qvemployeeSubmission(candidateId) {
		$.ajax({
					type : "POST",
					url : sourceleadUrl	+ "/getCandidatesEmployersListByCandidateIdAndOrgLocationID?&candidateId=" + candidateId,
					success : function(data) {
						if (data && data.response === "INVALID_DATA") {
						    $("#update_candidate").removeAttr("disabled");
						    $("#update_candidate").removeAttr('button').val('Save');
						    showError(result.responseMessage);
						} else if (data.response === "AWKWARD") {
						    location.href = "/errorPage";
						} else if (data.response === "SUCCESS") {
							if(data.data.length > 0){
								
							
							$("#qvemployeeSubmission").empty();
							var result = "";
							result = result +'<div class="col-md-12">';
							result = result +'<h4>Work Experience</h4>';
							for (var i = 0; i < data.data.length; i++) {
								if(i < 4) {
									var opt = data.data[i];
									if (opt.name != null && opt.name != '' && opt.name != 'undefined') {
										name = opt.name;
									} else {
										name = " ";
									}
									if (opt.designation != null	&& opt.designation!='' && opt.designation != 'undefined') {
										designation = opt.designation;
									} else {
										designation = " "; 
									}
									if (opt.startYear != null && opt.startYear != '' && opt.startYear != 'undefined') {
										startYear=   opt.startYear;
									} else{
										startYear = " ";
									}
									if (opt.endYear != null	&& opt.endYear != '' && opt.endYear != 'undefined') {
										endYear = opt.endYear;  
									} else {
										endYear = " ";
									}
									if (opt.address != null && opt.address != '' && opt.address != 'undefined') {
										address=   opt.address;
									} else {
										address = " ";
									}
									result = result +'<div class="resume-widget">';
									if(opt.startYear != null && opt.startYear != '' && opt.startYear != 'undefined' && opt.endYear != null	&& opt.endYear != '' && opt.endYear != 'undefined'){
										result = result +'  <p><small>'+startYear +' To '+endYear+'</small>';
								    } else if(opt.startYear != null && opt.startYear != '' && opt.startYear != 'undefined') {
								    	result = result +'  <p><small>'+startYear +'</small>';
								    } else if(opt.endYear != null	&& opt.endYear != '' && opt.endYear != 'undefined') {
								    	result = result +'  <p><small>'+endYear+'</small>';
								    }
									result = result +'  <small class="pull-right address" title="'+address+'">'+address+'</small></p>';
									result = result +'  <h4>'+designation+'</h4>';
									result = result +'  <h5>'+name+'</h5>';
									result = result +'</div>';
								}
							}
							result = result +'</div>';
							$("#qvemployeeSubmission").append(result);
							} else {
							 	var result = '';
				        	  	result = result +'<div class="col-md-12" style="display:none">';
				        	  	result = result +' <h4>Work Experience</h4>';
				        	  	result = result +' <div>---</div>';
				        	  	result = result +'</div>';
				        	  	$("#qvemployeeSubmission").empty();
				        	  	$("#qvemployeeSubmission").append(result);
							}
						} else if (data.indexOf('Sign In') !== -1) {
							/* location.replace(sourceleadUrl); */
						} else {
						    showError("Operation failed, please try again");
						}
					},
				});
	}

function qveducationSubmission(candidateId){
	 $.ajax({  
        type : "GET",
        url: sourceleadUrl+"/getCandidateEducationListByCandidateId?&candidateId="+candidateId,        
      		  success :function(data) {
								if(data && data.response === "INVALID_DATA") {
     								showError(data.responseMessage);
     							} else if (data.response === "AWKWARD") {
     	    						location.href = "/errorPage";
					       		} else if (data.response === "SUCCESS") {
					       			$("#qveducationSubmission").empty();
				                	 var result = "";
										result = result +'            <div class="col-md-12">';
										result = result +'              <h4>Education</h4>';
					                     for (var i = 0; i < data.data.length; i++) {
					                    	 if(i < 3) {
						                    	 var opt = data.data[i];  
						                    	 if(opt.name != null && opt.name != '' && opt.name != 'undefined'){
					                    		        EducationName = opt.name;
						                    	    }else{
						                    	    	EducationName = " ";
						                    	    }
							                     if(opt.specialization != null && opt.specialization != '' && opt.specialization != 'undefined'){
							                    	 specialization= '(' + opt.specialization +')';
						                    	  }else{
						                    		  specialization= " ";
						                       	 }
							                     if(opt.institute != null && opt.institute != '' && opt.institute != 'undefined'){
							                    	 institute = opt.institute;
						                    	 }else{
						                    		 institute = " ";
						                    	 }
							                     if(opt.grade != null && opt.grade != '' && opt.grade  != 'undefined'){
							                    	 Educationgrade= opt.grade + ' , ';
						                    	 }else{
						                    		 Educationgrade = ""; 
						                    	 }
						                    	 if(opt.gradeType != null && opt.gradeType != '' && opt.grgradeTypeade  != 'undefined'){
						                    		 EducationgradeType  = opt.gradeType + ' - ';
								                 }else{
								                	 EducationgradeType = " "; 
								                 }
						                    	 if(opt.address != null && opt.address != '' && opt.address != 'undefined'){
						                    		 Educationaddress = ' , ' +opt.address;
						                    	 }else{
						                    		 Educationaddress = " ";
						                    	 }
						                    	 if(opt.startDateStr != null && opt.startDateStr != '' && opt.startDateStr != 'undefined'){
						                    		 EducationstartDateStr = opt.startDateStr;
						                    	 }else{
						                    		 EducationstartDateStr = " ";
						                    	 }
						                    	 if(opt.endDateStr != null && opt.endDateStr != '' && opt.endDateStr != 'undefined'){ 
						                    		 EducationendDateStr = opt.endDateStr;
						                    	 }else{
						                    		 EducationendDateStr = " ";
						                    	 }
						                    	 
													result = result +'<div class="resume-widget">';
													 if(opt.startDateStr != null && opt.startDateStr != '' && opt.startDateStr != 'undefined' && opt.endDateStr != null && opt.endDateStr != '' && opt.endDateStr != 'undefined'){
														 result = result +'<p><small>'+EducationstartDateStr+' To '+EducationendDateStr+'</small></p>';
								                    	    }else if(opt.startDateStr != null && opt.startDateStr != '' && opt.startDateStr != 'undefined'){
								                    	    	result = result +'<p><small>'+EducationstartDateStr+'</small></p>';
								                    	    }else if(opt.endDateStr != null && opt.endDateStr != '' && opt.endDateStr != 'undefined'){
								                    	    	result = result +'<p><small>'+EducationendDateStr+'</small></p>';
								                    	    }
//			 										result = result +'<p><small>'+EducationstartDateStr+' '+EducationendDateStr+'</small></p>';
													result = result +'<h5>'+EducationName+' '+specialization+'</h5>';
													result = result +'</div>';
					                     	}
					                     }
										
										result = result +'        </div>';
				                     $("#qveducationSubmission").append(result); 
				                     
				                     
				                     /* var result = '';
				               	  result = result +'            <div class="col-md-12 " style="display:none">';
				               	  result = result +' <h4>Education</h4>';
				               	  result = result +' <div>---</div>';
				               	  result = result +'            </div>';
				               	  $("#qveducationSubmission").empty();
				               	  $("#qveducationSubmission").append(result); */
					       		} else if (data.indexOf('Sign In') !== -1) {
      							location.replace(sourceleadUrl);
					        	} else {
					        	    showError("Operation failed, please try again");
					        	}
      },
   }); 
}
function qwikCertificates(candidateId){
	 $.ajax({  
        type : "POST",
        url: sourceleadUrl+"/getCandidateCertificationListByCandidateIdAndOrgLocationID?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,        
        success :function(response) {
      	  	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
      		    showError(response.responseMessage);
      		} else if (response.response === "AWKWARD") {
      		    location.href = "/errorPage";
      		} else if (response.response === "SUCCESS") {
      			if(response.data.length>0) {
      				 $("#qvcertificationSubmission").empty();
	                	 var result = "";
	                	 result = result +'<div class="col-md-12">';
						 result = result +' <h4>Certifications</h4>';
//							var json = JSON.parse(data);
							for (var i = 0; i < response.data.length; i++) {
								if(i < 3) {
		                    	var opt = response.data[i];
								result = result +'<div class="resume-widget resumewidget">';
								
								if(opt.candidatevalidFromDateToString != null && opt.candidatevalidFromDateToString != 'undefined' && opt.candidatevalidFromDateToString != "" && opt.candidatevalidToDateToString != null && opt.candidatevalidToDateToString != 'undefined' && opt.candidatevalidToDateToString != ""){
									result = result +'  <p><small>'+opt.candidatevalidFromDateToString+' To '+opt.candidatevalidToDateToString+'</small></p>';
								}else if(opt.candidatevalidFromDateToString != null && opt.candidatevalidFromDateToString != '' && opt.candidatevalidFromDateToString !='undefined'){
									result = result +'  <p><small>'+opt.candidatevalidFromDateToString+'</small></p>';
								}else if(opt.candidatevalidToDateToString != null && opt.candidatevalidToDateToString != '' && opt.candidatevalidToDateToString !='undefined'){
									result = result +'  <p><small>'+opt.candidatevalidToDateToString+'</small></p>';
								}
								
								if(opt.name != null && opt.name != 'undefined' && opt.name != ""){
									name = opt.name;
								}else{
									name = '';
								}
								
								if(opt.scoreType == 'Percentage'){
									result = result +' <h4>'+name+' ('+opt.score+' %) </h4>';
								}else{
									result = result +' <h4>'+name+' ('+opt.score+' - '+opt.scoreType+') </h4>';
								}
								
								/* result = result +'<div class="description">';
								result = result +'  <p>'+opt.provider+'</p>';
								result = result +'</div>';   */
								result = result +'                  </div>';
							} 
      	  				}
							result = result +'        </div>';
							$("#qvcertificationSubmission").append(result); 
      			} else {
      				 var result = '';
      	        	  result = result +'            <div class="col-md-12 " style="display:none">';
      	        	  result = result +' <h4>Certifications</h4>';
      	        	  result = result +' <div>---</div>';
      	        	  result = result +'            </div>';
      	        	  $("#qvcertificationSubmission").empty();
      	        	  $("#qvcertificationSubmission").append(result); 
      			}
      		} else if (response.indexOf('Sign In') !== -1) {
      			location.replace(sourceleadUrl);
      		} else {
      		    showError("Operation failed, please try again");
      		}
      },
   });
}

function lineclamp() {
	   var lineheight = parseFloat($('div.trimTxt p').css('line-height'));
	   alert(lineheight)
		var articleheight = $('div.trimTxt').height(); 
		alert(articleheight)
		var calc = parseInt(articleheight/lineheight);
		alert(calc)
		$("div.trimTxt p").css({
	  	"-webkit-box-orient": "" + calc + ""
		});
	}


function getLoginUserTabsList() {

	$.ajax({
		type : "GET",
		url : sourceleadUrl + "/getLoginUserTabAccessList?locationId="+orgLocationId+"&userId="+userId,
		success : function(data) {
			tabAccessArray = data;
			$("#masterSideNav").html(masterSideNav.masterSideNavPanel());
		},

	});
}

function getLoginUserRolesActionsList(){
$.ajax({
		type : "GET",
		url : sourceleadUrl + "/getLoginUserRoleActionsList?locationId="
				+ orgLocationId + "&userId=" + userId,
		success : function(data) {
			roleActionsArray = data;
			$("#masterHeaderNav").html(masterHeader.masterHeaderPanel());
			function closeCandidateFilterPop(){
				$("#candidateFilterPopup").hide();
				$(".advancedsearch-mask").fadeOut();
			}
			function openCandidateFilterPop(){
				$("#candidateFilterPopup").toggle();
				 $(".advancedsearch-mask").fadeIn();
			}
			function resetCandidateFilter(){
				clearCandidateSearchKeys();
				$("ul#TypeFilter > li input:radio").prop("checked",false);
				$("ul#AllTypeFilter > li input:radio").prop("checked",false);
				$("ul#UpdatedFilter > li input:radio").prop("checked",false);
				$("ul#employmentFilter > li input:checkbox").prop("checked",false);
				$("ul#availabilityFilter > li input:checkbox").prop("checked",false);
				$("ul#skillsUL > li input:checkbox").prop("checked",false);
				$("ul#candidateTypeFilter > li input:checkbox").prop("checked",false);
				$("ul#createdByTypeFilter > li input:radio").prop("checked",false);
				
				
				$("#dataTableSearch").val('');
				document.getElementById("candidateTypeAll").checked = true;
				document.getElementById("candidateAll").checked = true;
				document.getElementById("candidateCheck").checked = true;
				document.getElementById("myCandidatesRadio").checked = true;
				candidateList();
			}
		},

	});
}