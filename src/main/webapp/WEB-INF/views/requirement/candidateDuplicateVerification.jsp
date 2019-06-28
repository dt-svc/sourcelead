<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/formvalidator.js?staticTime=${staticTime}"></script>
  <!-- Header Area -->
  <script type="text/javascript">
var atsUrl = '${SourceLeadWeb_atsUrl}';
var sourceleadUrl = '${SourceLeadWeb_sourceleadUrl}';
</script>

<script>
	$(document).ready(function() {
		getDuplicateConfigData();
	});
</script>
  <section class="content-wraper">
    <section class="content-header clearfix">
       <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; Submission List</div>
    </section>
    
    <!-- checkbox div -->
    <form name="duplicate_check_form" id="duplicate_check_form">
    <input type="hidden" id="configId" name="duplicateConfigID"/>
    <div id="configLoading"></div>
        <div class="col-md-12 mb">
          <div class="checkbox-new mt">
            <input id="email" class="checkbox-custom" name="email" type="checkbox"  checked value="Yes" disabled="true" >
            <label for="email" class="checkbox-custom-label"></label>
            <span class="ml10">Email</span> </div>
            <div class="clearfix"></div>
        </div>

        <div class="col-md-12 mb">
          <div class="checkbox-new mt">
            <input id="phoneNumber" class="checkbox-custom" name="phoneNumber" type="checkbox" value="No">
            <label for="phoneNumber" class="checkbox-custom-label"></label>
            <span class="ml10">Phone Number</span> </div>
            <div class="clearfix"></div>
        </div>
      <c:if test="${loginCountryName == 'India' }">
        <div class="col-md-12 mb">
          <div class="checkbox-new mt">
            <input id="panNumber" class="checkbox-custom" name="panNumber" type="checkbox" value="No">
            <label for="panNumber" class="checkbox-custom-label"></label>
            <span class="ml10">Pan Number</span> </div>
            <div class="clearfix"></div>
        </div>
        </c:if>
      <c:if test="${loginCountryName == 'United States' }">
        <div class="col-md-12 mb">
          <div class="checkbox-new mt">
            <input id=ssnNumber class="checkbox-custom" name="ssnNumber" type="checkbox" value="No" >
            <label for="ssnNumber" class="checkbox-custom-label"></label>
            <span class="ml10">SSN Number</span> </div>
            <div class="clearfix"></div>
        </div>
       	</c:if>
 <c:if test="${loginCountryName == 'India' }">
        <div class="col-md-12 mb">
          <div class="checkbox-new mt">
            <input id="aadharNumber" class="checkbox-custom" name="aadharNumber" type="checkbox" value="No">
            <label for="aadharNumber" class="checkbox-custom-label"></label>
            <span class="ml10">Aadhaar Number</span> </div>
            <div class="clearfix"></div>
        </div>
        </c:if>


        <div class="clearfix mt col-md-10 mb">
          <div class="pull-right">
            <button type="button" class="btn btn-primary mr" onclick="saveDuplicateCheckConfig();">Save</button>
            <button type="button" class="btn btn-default" onclick="cancelDuplicateCheckConfig();">Cancel</button>
          </div>
        </div>
        </div>
         </form>
        <!-- checkbox div ends --> 
</section>

<script>
 function saveDuplicateCheckConfig(){
	 var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
	 var email 		= $('#email').val();
	 var phoneNumber=$('#phoneNumber').val();
	 var panNumber	=$('#panNumber').val();
	 var id = $('#configId').val();
	if($('#ssnNumber').val()== undefined || $('#ssnNumber').val()== null){
	  var ssnNumber 	='No';
	  }else{
		  var ssnNumber = $('#ssnNumber').val(); 
	  }
	 var aadharNumber =$('#aadharNumber').val();
	 $("#configLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
     $.ajax({
 	      type: "POST",
       	  url:atsUrl+"/saveDuplicateCheckConfig?email="+email+"&phoneNumber="+phoneNumber+"&panNumber="+panNumber+"&ssnNumber="+ssnNumber+"&aadharNumber="+aadharNumber+"&id="+id,
         	success: function(json) {
         		location.replace(sourceleadUrl+"/settings")
	        },  
     });
	 
 }
 
 </script>
   <script>
   function getDuplicateConfigData(){
	     $.ajax({
	 	      type: "GET",
	       	  url:atsUrl+"/getDuplicateConfigData",
	         	success: function(data) {
	         		$("#configId").val(data.id);
        		displayAlreadyDuplicateConfigData(data);
		        },  
	     });   
   }
   </script>
   <script>
 function displayAlreadyDuplicateConfigData(data){
	 if(data.email == true){
		 $("#email").prop("checked", true);
	 }else{
		 $("#email").prop("checked", true);
	 }
		 if(data.mobilePhone == true){
			 $("#phoneNumber").prop("checked", true);
			 $("#phoneNumber").val('Yes');
		 }else{
			 $("#phoneNumber").prop("checked", false);
		 }
		 
		 if(data.panNumber == true){
			 $("#panNumber").prop("checked", true);
			 $("#panNumber").val('Yes');
		 }else{
			 $("#panNumber").prop("checked", false);
		 }
		 
		 if(data.ssnNumber == true){
			 $("#ssnNumber").prop("checked", true);
			 $("#ssnNumber").val('Yes');
		 }else{
			 $("#ssnNumber").prop("checked", false);
		 }
		 
		 if(data.aadharNumber == true){
			 $("#aadharNumber").prop("checked", true);
			 $("#aadharNumber").val('Yes');
		 }else{
			 $("#aadharNumber").prop("checked", false);
		 }
 }

 </script>
 <<script type="text/javascript">
 function cancelDuplicateCheckConfig(){
	 location.replace(sourceleadUrl+"/settings")
 }
</script>
  <script>
   $('#email').change(function(){
   $(this).val(this.checked ? "Yes" : "No");
   });	
   
   $('#phoneNumber').change(function(){
	   $(this).val(this.checked ? "Yes" : "No");
   });	 
	 
   $('#panNumber').change(function(){
     $(this).val(this.checked ? "Yes" : "No");
	 });	   
     
   $('#ssnNumber').change(function(){
      $(this).val(this.checked ? "Yes" : "No");
      });		   
	 
   $('#aadharNumber').change(function(){
	 $(this).val(this.checked ? "Yes" : "No");
	  });	
  </script> 
<jsp:include page="../masterFooter.jsp" />
