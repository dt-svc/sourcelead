<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="icon" type="image/png" href="/resources/images/fav.png" />
<title>Welcome to Source Lead</title>
<link rel="stylesheet" href="/resources/css/cropper.min.css" >
<link rel="stylesheet" href="/resources/css/style.css">
<link rel="stylesheet" href="/resources/css/chosen.min.css">
<link rel="stylesheet" href="/resources/css/dashboard_3118.css" type="text/css" media="all">
<link rel="stylesheet" href="/resources/css/chosen.min.css">
<link href="/resources/css/sourcelead-main.css" rel="stylesheet">
<link rel="stylesheet" href="/resources/css/timesheet.css" type="text/css">
<!--  scripts are added here  -->
<script src="/resources/js/slead-common.js"></script>
<script src="/resources/js/requirement.js"></script>
<script src="/resources/js/masterSideNav.js"></script>
<script src="/resources/js/masterHeader.js"></script>
<script src="/resources/js/masterHeaderAction.js"></script>
<script src="/resources/js/jquery-1.11.1.min.js"></script>
<script src="/resources/js/jquery-ui.js"></script>
<script src="/resources/js/bootstrap-select.min.js"></script>
<script src="/resources/js/formvalidator.js"></script>
<script src="/resources/js/chosen.jquery.min.js"></script>
<script src="/resources/js/sourcelead-app.js"></script>
<script src="/resources/js/jquery.dataTables.min.js"></script>
<script src="/resources/js/jquery.timepicker.js"></script>
<script  src="/resources/js/jobs/js/controls.js"></script>
<script src="/resources/js/cropper.min.js"></script> 
<script src="/resources/js/submissionFormBuilder.js"></script> 
<script src="/resources/js/submissionForm-html-generator.js"></script> 

</head>
<%-- 
     @author : David Raju.<!
     this script is to get filter popup
     1.usage     : onclick on requirementList() ; 
     
--%>
<script>

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
 <script type="text/javascript">
 var hideColumns = [];
 var table = "";
 $(document).ready(function () {
	 getLoginUserTabsList();
	 getLoginUserRolesActionsList();
	 $('body').load("/resources/requirementListTemplate.html");
     $('label.toggle-vis').on('click', function (e) {
         e.preventDefault();
         // Get the column API object
         var column = table.column($(this).attr('data-column'));
         $(this).toggleClass("active");
         // Toggle the visibility
         column.visible(!column.visible());
     });
});
</script>
<script>
// toggle events are not worked properly thats why kept in jsp.
function getActions(){
	$(".action-pop").slideToggle(200);
	$(".newActPop").slideUp(200);
}	
$("html").click(function(){
	$(".action-pop").slideUp(200);
});
$(".action, .action-pop").click(function(e){
 	e.stopPropagation();
});	
function newActions(){
	$(".newActPop").slideToggle(200);
	$(".action-pop").slideUp(200);
}
$("html").click(function(){
	$(".newActPop").slideUp(200);
});
$(".overIcon, .newActPop").click(function(e){
 	e.stopPropagation();
});	
</script>	
<script>
//var sourceleadUrl= '${sourceleadUrl}';
var userId ="4";//'${SourceLeadWeb_userId}'; // need to change later


<script>
$(document).ready(function() {
	getcites('apiLocation','city','recommendation');
	
});
</script>
