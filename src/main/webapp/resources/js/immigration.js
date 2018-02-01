var immigration  = new Immigration();
function Immigration(){
	this.savePageOneContinue = function (status){
		$("#newPetitionTemplateDiv").empty();
		if(status == 'one'){
			$("#newPetitionTemplateDiv").load("/app/loadNewPetitionPage?status=one");
		}else if(status == 'two'){
			$("#newPetitionTemplateDiv").load("/app/loadNewPetitionPage?status=two");
		}else if(status == 'three'){
			$("#newPetitionTemplateDiv").load("/app/loadNewPetitionPage?status=three");
		}else if(status == 'four'){
			$("#newPetitionTemplateDiv").load("/app/loadNewPetitionPage?status=four");
		}else if(status == 'five'){
			$("#newPetitionTemplateDiv").load("/app/loadNewPetitionPage?status=five");
		}else if(status == 'six'){
			$("#newPetitionTemplateDiv").load("/app/loadNewPetitionPage?status=six");
		}else if(status == 'seven'){
			$("#newPetitionTemplateDiv").load("/app/loadNewPetitionPage?status=seven");
		}
	}
	
	this.savePageAndContinuePetitionTemplate = function (status){
		$("#addNewPetitionTemplateDiv").empty();
		if(status == 'one'){
			$("#addNewPetitionTemplateDiv").load("/app/loadAddPetitionTemplatePage?status=one");
		}else if(status == 'two'){
			$("#addNewPetitionTemplateDiv").load("/app/loadAddPetitionTemplatePage?status=two");
		}else if(status == 'three'){
			$("#addNewPetitionTemplateDiv").load("/app/loadAddPetitionTemplatePage?status=three");
		}else if(status == 'four'){
			$("#addNewPetitionTemplateDiv").load("/app/loadAddPetitionTemplatePage?status=four");
		}
	}
}
/*var savePageOneContinue = function (){
	  $("#newPetitionTemplateDiv").empty();
	  $("#newPetitionTemplateDiv").load("/app/loadNewPetitionPageOne");
}*/