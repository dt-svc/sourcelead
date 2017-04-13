<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script src="resources/js/formvalidator.js"></script>
<!-- <script src="resources/js/chosen.jquery.min.js"></script> --> 


<!-- these files for autocomplete with tagit -->
<link href="resources/css/jquery-tagit.css" rel="stylesheet">
<!-- end files for autocomplete with tagit -->

<!-- Content Area -->
<section class="content-wraper">
<section class="content-header clearfix">
  <div class="pull-left">
    <h2>Add Contract Template</h2>
  </div>
  <div class="pull-right brud-crum">Home &raquo; Add Contract Template</div>
</section>

<section class="addcontrHead">
	<a href="#" class="active">Template</a>
	<a href="#">Preview</a>
</section>
	
<div class="content-area content-new clearfix">
      <div class="clearfix form-horizontal">
       
        <div class="row newContrWrap">
          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
           
            <div class="form-group">
              <label class="col-md-4">Template name<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8">
                <input type="text" class="form-control" name="Company Name" value="">
              </div>
            </div>
            
            <div class="form-group">
              <label class="col-md-4">Template Type<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8">
                  <select class="chosen-select form-control" id="contractType" onchange="contractTemplateType();">
                    <option>Client MSA</option>
                    <option>Vendor MSA</option>
                    <option>Client SOW</option>
                    <option>Vendor SOW</option>
                    <option>NDA</option>
                    <option>NSA</option>
                  </select>
            </div>
            </div>
                       
             
          <div class="form-group">
              <label class="col-md-4">Letter head</label>
              <div class="col-md-8">
                  <select class="chosen-select form-control">
                    <option>Letter head</option>
                    <option>Letter head-1</option>
                    <option>Letter head-2</option>
                    <option>Letter head-3</option>
                  </select>
              </div>
          </div>             
                   
            <div class="form-group">
              <label class="col-md-4">Description</label>
              <div class="col-md-8">
					<textarea class="form-control" rows="3" name="Project Description"></textarea>
              </div>
            </div>            
            
        </div>
      </div>
      
<div class="row newContrWrap">
	<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
		<div class="col-md-9">
			<textarea id="txtEditor" class="form-control newContrtextarea"></textarea>
		</div>  
		
		
		<div class="col-md-3 contrCus">
			<h3>Custom Files</h3>
			<ul id="clientmsa" style="display:block">
				<li><i></i>Client Name</li>
				<li><i></i>Client Contact</li>
				<li><i></i>Client location</li>
			</ul>
			
			<ul id="vendormsa" style="display:none">
				<li><i></i>Vendor Name</li>
				<li><i></i>Vendor Contact</li>
				<li><i></i>Vendor location</li>
			</ul>
			
			<ul id="clientsow" style="display:none">
				<li><i></i>Client Name</li>
				<li><i></i>Client Contact</li>
				<li><i></i>Client Location</li>
				<li><i></i>Requirement Title</li>
				<li><i></i>Associate Name</li>
				<li><i></i>Offer Rate</li>
			</ul>
			
			<ul id="vendorsow" style="display:none">
				<li><i></i>Vendor Name</li>
				<li><i></i>Vendor Contact</li>
				<li><i></i>Vendor Location</li>
				<li><i></i>Requirement Title</li>
				<li><i></i>Associate Name</li>
				<li><i></i>Offer Rate</li>
			</ul>
			<ul id="nda" style="display:none">
				<li><i></i>Vendor Name</li>
				<li><i></i>Vendor Contact</li>
				<li><i></i>Vendor Location</li>
			</ul>
			<ul id="nsa" style="display:none">
				<li><i></i>Vendor Name</li>
				<li><i></i>Vendor Contact</li>
				<li><i></i>Vendor Location</li>
			</ul>
		</div>  		      
	</div>
</div>      

      <div class="form-footer clearfix contrFoot">
        <div class="col-lg-12">
          <div class="pull-right">
            <input type="button" value="Back" class="btn btn-danger">
			<a href="add-contract-template-preview.html"><input type="button" value="Next" class="btn btn-primary ml10"></a>
          </div>
        </div>
      </div>

<!-- Content Area Ends-->

 <script src="js/editor.js"></script>
    <link href="css/editor.css" type="text/css" rel="stylesheet"/>
		<script>
			$(document).ready(function() {
				$("#txtEditor").Editor();
			});
</script>   


<script type="text/javascript">
function contractTemplateType(){
	
	var contractType = $("#contractType").val();
	if(contractType == "Client MSA"){
		document.getElementById("vendormsa").style.display="none";
		document.getElementById("clientsow").style.display="none";
		document.getElementById("vendorsow").style.display="none";
		document.getElementById("nda").style.display="none";
		document.getElementById("nsa").style.display="none";
		document.getElementById("clientmsa").style.display="block";
	}
	if(contractType == "Vendor MSA"){
		document.getElementById("clientmsa").style.display="none";
		document.getElementById("clientsow").style.display="none";
		document.getElementById("vendorsow").style.display="none";
		document.getElementById("nda").style.display="none";
		document.getElementById("nsa").style.display="none";
		document.getElementById("vendormsa").style.display="block";
	}
	if(contractType == "Client SOW"){
		document.getElementById("clientmsa").style.display="none";
		document.getElementById("vendormsa").style.display="none";
		document.getElementById("vendorsow").style.display="none";
		document.getElementById("nda").style.display="none";
		document.getElementById("nsa").style.display="none";
		document.getElementById("clientsow").style.display="block";
	}
	
	if(contractType == "Vendor SOW"){
		document.getElementById("clientmsa").style.display="none";
		document.getElementById("vendormsa").style.display="none";
		document.getElementById("clientsow").style.display="none";
		document.getElementById("nda").style.display="none";
		document.getElementById("nsa").style.display="none";
		document.getElementById("vendorsow").style.display="block";
	}
	if(contractType == "NDA"){
		document.getElementById("clientmsa").style.display="none";
		document.getElementById("vendormsa").style.display="none";
		document.getElementById("clientsow").style.display="none";
		document.getElementById("vendorsow").style.display="none";
		document.getElementById("nsa").style.display="none";
		document.getElementById("nda").style.display="block";
	}
	if(contractType == "NSA"){
		document.getElementById("clientmsa").style.display="none";
		document.getElementById("vendormsa").style.display="none";
		document.getElementById("clientsow").style.display="none";
		document.getElementById("vendorsow").style.display="none";
		document.getElementById("nda").style.display="none";
		document.getElementById("nsa").style.display="block";
	}
}

</script>
<textarea id="txt" rows="15" cols="70">There is some text here.</textarea>
<input type="button" id="btn" value="OK" />
<script>
jQuery("#btn").on('click', function() {
    var $txt = jQuery("#txt");
    var caretPos = $txt[0].selectionStart;
    var textAreaTxt = $txt.val();
    var txtToAdd = "stuff";
    $txt.val(textAreaTxt.substring(0, caretPos) + txtToAdd + textAreaTxt.substring(caretPos) );
});
</script>


<!-- Footer Area -->
<jsp:include page="../masterFooter.jsp" />
<!-- Footer Area -->




