var immigration  = new Immigration();
var immiModulePath = "/immigration";
function Immigration(){
	/*this.savePageOneContinue = function (status){
		$("#newPetitionTemplateDiv").empty();
		if(status == 'one'){
			$("#newPetitionTemplateDiv").load("/app/immigration/loadNewPetitionPage?status=one");
		}else if(status == 'two'){
			$("#newPetitionTemplateDiv").load("/app/immigration/loadNewPetitionPage?status=two");
		}else if(status == 'three'){
			$("#newPetitionTemplateDiv").load("/app/immigration/loadNewPetitionPage?status=three");
		}else if(status == 'four'){
			$("#newPetitionTemplateDiv").load("/app/immigration/loadNewPetitionPage?status=four");
		}else if(status == 'five'){
			$("#newPetitionTemplateDiv").load("/app/immigration/loadNewPetitionPage?status=five");
		}else if(status == 'six'){
			$("#newPetitionTemplateDiv").load("/app/immigration/loadNewPetitionPage?status=six");
		}else if(status == 'seven'){
			$("#newPetitionTemplateDiv").load("/app/immigration/loadNewPetitionPage?status=seven");
		}
	}*/
	
	/*this.savePageAndContinuePetitionTemplate = function (status){
		$("#addNewPetitionTemplateDiv").empty();
		if(status == 'one'){
			$("#addNewPetitionTemplateDiv").load("/app/immigration/addPetitionTemplate?status=one");
		}else if(status == 'two'){
			$("#addNewPetitionTemplateDiv").load("/app/immigration/addPetitionTemplate?status=two");
		}else if(status == 'three'){
			$("#addNewPetitionTemplateDiv").load("/app/immigration/addPetitionTemplate?status=three");
		}else if(status == 'four'){
			$("#addNewPetitionTemplateDiv").load("/app/immigration/addPetitionTemplate?status=four");
		}
	}*/
	this.savePetitionTemplateAndContinue = function(){
		var formData = getFormData("savePetitionTemplateDetails");
		$.ajax({
		       url: sourceleadUrl+immiModulePath+"/savePetitionTemplateDetails",
		       type : "POST",
		       data: formData,
		       success: function(data) {
		    	   console.log("inside success");
		    	   alert("hi");
		    	   alert(data);
		    	   if(data == 'success'){
			    	   $("#addNewPetitionTemplateDiv").empty();
			    	   $("#addNewPetitionTemplateDiv").load(sourceleadUrl+immiModulePath+"/addPetitionTemplate?status=docDetails");
		    	   }
		       }
		});
	}
}
/*var savePageOneContinue = function (){
	  $("#newPetitionTemplateDiv").empty();
	  $("#newPetitionTemplateDiv").load("/app/loadNewPetitionPageOne");
}*/