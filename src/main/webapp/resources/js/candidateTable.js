function candidateListCheck(searchValue,universalCanddateSearchList,candidateKeyword,candidateIdSearchId){
	if(universalCanddateSearchList=="universalCanddateList")	 {
		$("#dataTableSearch").val(searchValue);
		candidateList();
	} else if(candidateKeyword == "candidateSearch"){
		candidateSideBar(candidateIdSearchId);
		candidateQuickViewProfile(candidateIdSearchId);
		candidateList();
		$("#upcandidateStatus").val('');
	} else {
		candidateList();
		$("#upcandidateStatus").val('');
	}
 }
 
  function candidateList(){ 	 
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
	  
	  
 	  var candTypeRadio = $('input[name=candidateType]');
 	  var candidateType = candTypeRadio.filter(':checked').val();
	  
	 	var upcandidateStatus =   $('input[name=upcandidateStatus]:checked').val();
		  
	 	  $("#candidateList").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	 	 
		
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
	  		var url = sourceleadUrl+"/searchCandidate?"+_csrfParam+"="+_csrfToken+"&candidateStatus="+candidateStatus+"&searchValue="+searchValue+"&updatedDateRange="+upcandidateStatus+"&isAssociate="+isAssociate+"&candidateType="+candidateType;
	  		url += "&"+$($('#candidatelist_form')[0]).serialize();
	  		prepareCandidateList(url);
			return true;
		 }else{ 
				return false;
		 } 
	} 

	$('body').on('blur', '#candidatelist_form', function() {
		$("#candidatelist_form").showHelpOnFocus();
		$("#candidatelist_form").validateOnBlur(false, validationSettings);
	}); 
	
  function prepareCandidateList(url){
 	     $("#candidateList").removeAttr("style");
     	 $("#candidateList").empty();
     	 var result = "";
     	  result = result +"<table id='candidatePagination' class='display' cellspacing='0' width='100%' >"; 
          result = result +"<thead>"; 
          result = result +"  <tr>"; 
          result = result +"    <th>&nbsp;</th>";
          result = result +"    <th>Generated Id</th>";
          result = result +"    <th>Name</th>"; 
          result = result +"    <th>Primary Skill</th>"; 
          result = result +"    <th>Employment Type</th>"; 
          result = result +"    <th>Email</th>"; 
          result = result +"    <th>Mobile Number</th>"; 
          result = result +"    <th>Location</th>"; 
          result = result +"    <th>Last Update</th>"; 
          result = result +"  </tr>"; 
          result = result +"</thead>";
          result = result +"<tbody>"; 
          result = result +" </tbody>";
          result = result +"</table>";
          $("#noCandidates").val('');
          $("#candidateList").append(result);
          cpagination(url); 
  }

function cpagination(url){ 
	var colIndex = 0;
	$.fn.dataTable.moment( 'DD-MM-YYYY' );
    $('#candidatePagination').DataTable( { 
    		"processing": true,
        "serverSide": true,
        "searching": false,
        "ajax": $.fn.dataTable.pipeline( {
            url: url,
            method: 'POST',
            pages: 10
        }),
        "columns": [
            { "data": "isAssociate" },
            { "data": "firstName"},
            { "data": "primarySkill" },
            { "data": "designationType"},
            { "data": "email" },
            { "data": "mobilePhone" },
            { "data": "country"},
            { "data": "updatedDate" }
        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	/*"className": "tbldata-dotdot_50",*/
	    	  	"data": "isAssociate",
	    	  	"sortable": false,
	    	  	"render": function(data, type, full, meta) {
	    	  		var result = "";
	    	  		var isFavourite = "";
	    	  		var isFavClass = "";
	    	  		if(full.isFavourite == '1' && isAssociate == "N"){
	            		isFavourite = "checked";
	            		isFavClass = "activestar-rating";
	            	} else if(full.isFavouriteAssociate == '1' && isAssociate == "Y") {
	            		isFavourite = "checked";
	            		isFavClass = "activestar-rating";
	            	}
	    	  		if(data == "Y" && isAssociate == 'N'){
		            	  result = result +"<i class='fa fa-black-tie pull-right mt10' title='Added as Associate'></i> ";  
		             }
	
		              result = result +"<div class='iconClass pull-left star-rating "+isFavClass+"' id='favourite"+full.candidateId+"'>";
		              if(associateFlag)
		            	  	result = result +"   <input type='checkbox' id='fav"+full.candidateId+"' "+isFavourite+" onclick='addFavourite("+full.candidateId+")'></input>";
		             
		              result = result +" <label></label>";
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
  	            	  		result = result +"<div>----</div>";
  	             	}
  	              return result;
              }
	          },{
            "targets": colIndex++,
            "data": "firstName",
            "render": function ( data, type, full, meta ) {
            		var result = "";
            		var lastName = "";
            		if(full.lastName != null && full.lastName != "" && full.lastName != 'undefined'){
	            		lastName = full.lastName;
	            	}else{
	            		lastName = "";
	            	}
            		if(name1Flag)
	                result = result +"  <td><div class='proj_show divmax-width' onclick='candidateQuickViewProfile("+full.candidateId+"); candidateSideBar("+full.candidateId+");'><span title='"+full.firstName+" "+lastName+"'>"+full.firstName+" "+lastName+"</span><i class='fa fa-binoculars'></i></div></td>";
	              
	              if(name2Flag)
	               result = result +"  <td><div class='proj_show' onclick='#'><span title='"+full.firstName+" "+lastName+"'>"+full.firstName+" "+lastName+"</span></div></td>";
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
  	            	  		result = result +"<div>----</div>";
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
   	             		 result = result +"<div>----</div>"; 
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
	    	  	"render": function(data, type, full, meta) {
	    	  		var mobilePhone = "";
	    	  		if(full.mobilePhone != null && full.mobilePhone != 'undefined' && full.mobilePhone != ""){
	         		       mobilePhone = full.mobilePhone;
		            	}else{
		            		mobilePhone = "----";
		            	}
	    	  		return "<div class='tbldata-dotdot-vsm'>"+mobilePhone+"</div>";
	    	  	}
	    	  }, {
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
		            		}
      	              return "<div><span title='"+location+"'>"+location+"</span></div>";
                  }
                }, {
                  "targets": colIndex++,
                  "data": "updatedDate",
                  "className": "tblminwidth-75",
                  "render": function ( data, type, opt, meta ) {
                  		var result = "";
                  		if(opt.candidateUpdateDateToString != null && opt.candidateUpdateDateToString != 'undefined' && opt.candidateUpdateDateToString != ""){
                  			result = "<div>"+opt.candidateUpdateDateToString+"</div>"; 
			            	}else{
			            		result = "<div>---</div>";
			            	}
      	              return result;
                  }
                } ],
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"aaSorting": [[7,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='resources/images/loading.gif' style='width: 160px; height: 160px;'/>"
	     }
    } );
    $("#candidatePagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#dataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
		candidateList();
    	   }
    	});
}