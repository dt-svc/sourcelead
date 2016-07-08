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
/*
$(document).ready(function() {
	$("#comp-regis").hide();
    $("#pro-tab").click(function(){
		$("#pro-tab").addClass("activetab");
		$("#comp-tab").removeClass("activetab");
		$("#comp-regis").hide();
		$("#profe-regis").show();
	});
    $("#comp-tab").click(function(){
		$("#pro-tab").removeClass("activetab");
		$("#comp-tab").addClass("activetab");
		$("#comp-regis").show();
		$("#profe-regis").hide();
	});	
});*/	

// timesheet comment
$(document).ready(function() {
    $(".timesheet_comm i").click(function(){
		$("#teskcomment").slideToggle(200);
	});
    $("html").click(function() {
        $("#teskcomment").slideUp(200);
    });
    $(".timesheet_comm i, #teskcomment").click(function(e) {
        e.stopPropagation();
    });	
});	

// Approved Popup
$(document).ready(function() {
    $("#appro-pop").click(function(){
		$(".approved_pop").slideToggle(200);
	});
    $("html").click(function() {
        $(".approved_pop").slideUp(200);
    });
    $(".approved_pop, #appro-pop").click(function(e) {
        e.stopPropagation();
    });	
});

// Export Popup
$(document).ready(function() {
    $("#export-pop").click(function(){
		$(".export_pop").slideToggle(200);
	});
    $("html").click(function() {
        $(".export_pop").slideUp(200);
    });
    $(".export_pop, #export-pop").click(function(e) {
        e.stopPropagation();
    });	
});	
	


 
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
 
 
 $(function() {
	    $('.time_cont_res').hide();
	    $('#accordion h2:first').addClass('res-active').next().slideDown('slow');
	    $('#accordion h2').click(function() {
	        if($(this).next().is(':hidden')) {
	            $('#accordion h2').removeClass('res-active').next().slideUp('slow');
	            $(this).toggleClass('res-active').next().slideDown('slow');
			}
		});
	});

