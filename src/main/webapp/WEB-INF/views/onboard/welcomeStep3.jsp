<!DOCTYPE html>
<html lang="en" manifest="/sourcelead.appcache">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="icon" type="image/png" href="resources/images/fav.png" />
<title>Welcome to Source Lead :: The Recruitment Application</title>
<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
<!-- Bootstrap -->
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<link href="resources/css/bootstrap-select.css" rel="stylesheet">
<link href="resources/css/jquery.dataTables.min.css" rel="stylesheet">
<link rel="stylesheet" href="resources/css/jquery-ui.css">
<link rel="stylesheet" href="resources/css/chosen.min.css">
<link href="resources/css/font-awesome.min.css" rel="stylesheet">
<link href="resources/css/sourcelead-main.css" rel="stylesheet" id="myStyleSheet">
<link href="resources/css/timesheet.css" rel="stylesheet">

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script src="resources/js/jquery-1.11.1.min.js"></script>
<script src="resources/js/formvalidator.js"></script>

<style type="text/css">
    .require_wrap_table.overflowVisible{
        overflow: visible !important;
    }
    </style>

</head>

<body>

<div class="wraper"> 

  
  <!--   Welcome Screens   -->
  
	<section class="welcoBg">
		<section class="welcoInn">
		  <div class="welcoHead">
				<div class="welLogo"><img src="images/w-logo.png" alt=""/></div>
			  <ul>
				  <li class="actLi"><span class="fill">1</span><b>Welcome</b><i class="line"></i></li>
				  <li class="actLi"><span class="fill">2</span><b>Step two</b><i class="line"></i></li>
				  <li class="actLi"><span>3</span><b>Step three</b><i class="line"></i></li>
				  <li><span>4</span><b>Finish</b><i class="line"></i></li>
				  <div class="clearfix"></div>
			  </ul>
			  <div class="clearfix"></div>
		  </div>
		  
		<div class="welCo1">

			<div class="step3Wrap">
			<h2>Employment Contract</h2>
				<div class="step3">
					<span><a href="images/BIS-pdf.pdf" target="_blank"  class="fa fa-file-excel-o"></a></span>
					<strong>
						<p>Document Name here...</p>
						<a href="images/BIS-pdf.pdf" download >Download</a><br>
						<label><input type="checkbox"> &nbsp; Checkbox accepted text..</label>
					</strong>
					<div class="clearfix"></div>
				</div>
				<div class="step3">
					<span><a href="images/BIS-pdf.pdf" target="_blank"  class="fa fa-file-pdf-o"></a></span>
					<strong>
						<p>Document Name here...</p>
						<a href="images/BIS-pdf.pdf" download >Download</a><br>
						<label><input type="checkbox"> &nbsp; Checkbox accepted text..</label>
					</strong>
					<div class="clearfix"></div>
				</div>
				<div class="clearfix"></div>			
			</div>	
			<div class="step3Wrap">
			<h2 class="padTop">Employment Forms</h2>
				<div class="step3">
					<span><a href="images/BIS-pdf.pdf" target="_blank"  class="fa fa-file-word-o"></a></span>
					<strong>
						<p>Document Name here...</p>
						<a href="images/BIS-pdf.pdf" download >Download</a><br>
						<label><input type="checkbox"> &nbsp; Checkbox accepted text..</label>
					</strong>
					<div class="clearfix"></div>
				</div>
				<div class="step3">
					<span><a href="images/BIS-pdf.pdf" target="_blank"  class="fa fa-file-excel-o"></a></span>
					<strong>
						<p>Document Name here...</p>
						<a href="images/BIS-pdf.pdf" download >Download</a><br>
						<label><input type="checkbox"> &nbsp; Checkbox accepted text..</label>
					</strong>
					<div class="clearfix"></div>
				</div>				
				<div class="step3">
					<span><a href="images/BIS-pdf.pdf" target="_blank"  class="fa fa-file-pdf-o"></a></span>
					<strong>
						<p>Document Name here...</p>
						<a href="images/BIS-pdf.pdf" download >Download</a><br>
						<label><input type="checkbox"> &nbsp; Checkbox accepted text..</label>
					</strong>
					<div class="clearfix"></div>
				</div>
				<div class="clearfix"></div>			
			</div>	
			<div class="step3Wrap">
			<h2 class="padTop">Polices</h2>
				<div class="step3">
					<span><a href="images/BIS-pdf.pdf" target="_blank"  class="fa fa-file-image-o"></a></span>
					<strong>
						<p>Document Name here...</p>
						<a href="images/BIS-pdf.pdf" download >Download</a><br>
						<label><input type="checkbox"> &nbsp; Checkbox accepted text..</label>
					</strong>
					<div class="clearfix"></div>
				</div>
				<div class="clearfix"></div>
			</div>							
	  
		  </div>		  
		  
<div class="welBtns">
	<a href="welcome-two.html">Prew</a><a class="Brig" href="welcome-four.html">Proceed to next step</a><div class="clearfix"></div>
</div>
		  
		</section>
	</section>  
  

  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script> 
  <script src="resources/js/jquery.dataTables.min.js"></script> 
  <script src="resources/js/moment.min.js"></script> 
  <script src="resources/js/jquery-ui.min.js"></script> 
  <script src="resources/js/bootstrap-select.min.js"></script> 
  <script src="resources/js/bootstrap.min.js"></script> 
  <script src="resources/js/masterHeaderAction.js"></script> 
  <script src="resources/js/chosen.jquery.min.js"></script> 
  <script type="text/javascript">
			var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
			for (var selector in config) {
			  $(selector).chosen(config[selector]);
			}
			$('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});

});
</script> 

</div>

</body>
</html>