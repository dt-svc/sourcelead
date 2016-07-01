// Sing in Error
$(".sign_btn").click(function(){
	$(".signin_wrap p").slideDown(100);	
});

// forgot password
$(document).ready(function() {
	$("#forgot-pass").hide();
    $("#fp-click").click(function(){
		$("#signin").hide();
		$("#forgot-pass").show();
	});
    $(".cancel_for").click(function(){
		$("#signin").show();
		$("#forgot-pass").hide();
	});	
});

// registration tabs
//$(document).ready(function() {
	//$("#comp-regis").hide();
    //$("#pro-tab").click(function(){
		//$("#pro-tab").addClass("activetab");
		//$("#comp-tab").removeClass("activetab");
		//$("#comp-regis").hide();
		//$("#profe-regis").show();
	//});
   // $("#comp-tab").click(function(){
		//$("#pro-tab").removeClass("activetab");
		//$("#comp-tab").addClass("activetab");
		//$("#comp-regis").show();
		//$("#profe-regis").hide();
	//});	
//});	


 
//$(function(){
//  var currencies = [
//    { value: 'Australia'},
//    { value: 'France'},
//    { value: 'India'},
//    { value: 'Singapore'},
//    { value: 'United Kingdom'},
//    { value: 'United States'},
//  ];
//  
//  // setup autocomplete function pulling from currencies[] array
//  $('#professional-autocomplete, #company-autocomplete').autocomplete({
//    lookup: currencies,
//    onSelect: function (suggestion) {}	
//  }).focus(function () {  
//          $("#professional-autocomplete").autocomplete("ssss");
//  });
//   
//});



 $(function() {
            var availableTutorials = [
               "Australia",
               "France",
               "India",
               "Singapore",
			   "United Kingdom",
			   "United States",
            ];
            $( "#professional-autocomplete, #company-autocomplete" ).autocomplete({
               source: availableTutorials,
               minLength: 0,   
            }).focus(function () {
                $(this).autocomplete("search");
           });

         });
 
 


