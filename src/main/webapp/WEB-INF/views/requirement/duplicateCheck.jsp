<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
  <!-- Header Area -->
  <script type="text/javascript">
var atsUrl = '${SourceLeadWeb_atsUrl}';
var sourceleadUrl = '${SourceLeadWeb_sourceleadUrl}';
</script>
  <section class="content-wraper">
    <section class="content-header clearfix">
       <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; Submission List</div>
    </section>
    
    <!-- checkbox div -->
    <form name="duplicate_check_form" id="duplicate_check_form">
    <div id="configLoading"></div>
        <div class="col-md-12 mb">
          <div class="checkbox-new mt">
            <input id="email" class="checkbox-custom" name="email" type="checkbox" checked value="Yes">
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

        <div class="col-md-12 mb">
          <div class="checkbox-new mt">
            <input id="panNumber" class="checkbox-custom" name="panNumber" type="checkbox" value="No">
            <label for="panNumber" class="checkbox-custom-label"></label>
            <span class="ml10">Pan Number</span> </div>
            <div class="clearfix"></div>
        </div>
      <c:if test="${loginCountryName == 'United States' }">
        <div class="col-md-12 mb">
          <div class="checkbox-new mt">
            <input id=ssnNumber class="checkbox-custom" name="ssnNumber" type="checkbox" value="No" >
            <label for="ssnNumber" class="checkbox-custom-label"></label>
            <span class="ml10">SSN Number</span> </div>
            <div class="clearfix"></div>
        </div>
       	</c:if>

        <div class="col-md-12 mb">
          <div class="checkbox-new mt">
            <input id="aadharNumber" class="checkbox-custom" name="aadharNumber" type="checkbox" value="No">
            <label for="aadharNumber" class="checkbox-custom-label"></label>
            <span class="ml10">Aadhaar Number</span> </div>
            <div class="clearfix"></div>
        </div>


        <div class="clearfix mt col-md-10 mb">
          <div class="pull-right">
            <button type="buton" class="btn btn-primary mr" onclick="saveDuplicateCheckConfig();">Save</button>
            <button type="buton" class="btn btn-default">Cancel</button>
          </div>
        </div>
        </div>
         </form>
        <!-- checkbox div ends --> 
</section>

<script>
 function saveDuplicateCheckConfig(){
	 var sourceleadUrl ='${sourceleadUrl}';
	 var email 		= $('#email').val();
	 var phoneNumber=$('#phoneNumber').val();
	 var panNumber	=$('#panNumber').val();
	if($('#ssnNumber').val()== undefined || $('#ssnNumber').val()== null){
	  var ssnNumber 	='N';
	  }else{
		  var ssnNumber = $('#ssnNumber').val(); 
	  }
	 var aadharNumber =$('#aadharNumber').val();
	 $("#configLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
     $.ajax({
 	      type: "POST",
       	  url:atsUrl+"/saveDuplicateCheckConfig?email="+email+"&phoneNumber="+phoneNumber+"&panNumber="+panNumber+"&ssnNumber="+ssnNumber+"&aadharNumber="+aadharNumber,
         	success: function(json) {
         		location.reload();
	        },  
     });
	 
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
