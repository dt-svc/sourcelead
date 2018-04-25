<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script src="/resources/js/immigration.js"></script>
 <section class="content-wraper content-wraperNew">
   
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>${immigration.generatedId}</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="/app/immigration/viewImmigrationList">Immigrations List</a> &raquo; Immigration Summery</div>
      <input type="hidden" id = "candidateID" value = "${candidateId}">
		<input type="hidden" id = "immigrationID" value = "${immigrationId}">
		<input type="hidden" id = "templateID" value = "${templateId}">
      
    </section>
    
	<section class="immigraTabsWrap">
	
  	<div class="immHeadWrap">
  		<div class="immHead clearfix">
  			<div class="col-md-4">
  				<h2>${immigration.firstName}&nbsp;${immigration.lastName}</h2>
  				<h3>${immigration.email}</h3>
  			</div>
  			<div class="col-md-4">
				<p><strong>Visa Type</strong>: ${immigration.petitionType}</p>
				<p><strong>Attorney</strong>: ${immigration.attorneyName}</p>
  			</div>
  			<div class="col-md-4 immHeadactions clearfix">
  				<a href="javascript:void(0)">
  					<i class="fa fa-caret-down"></i>
  					<ul>
  						<li>Status 1</li>
  						<li>Status 2</li>
  						<li>Status 3</li>
  						<li>Status 4</li>
  						<li>Status 5</li>
  					</ul>
  				</a>  			
  				<span>${immigration.currUscisStatus}</span>
  			</div>
  		</div>
	
		<div class="immigraHead">
			<ul class="immigraTabs">
              <li class="current"><a href="#detailsTab"><i class="fa fa-check-circle-o"></i>Details</a></li>
              <li><a href="#passportsTab">Passport</a></li>
              <li><a href="#visasTab">Visa</a></li>
              <li><a href="#employersTab">Employers</a></li>
              <li><a href="#residencesTab">Residence</a></li>
              <li><a href="#educationsTab">Educations</a></li>
              <li><a href="#familiesTab">Family</a></li>
              <li><a href="#formsTab">Forms</a></li>
              <li><a href="#docsTab">Documents</a></li>
              <li><a href="#notesTab">Notes</a></li>
              <li><a href="#activitiesTab">Activities</a></li>
              <div class="clearfix"></div>
            </ul>
		</div>	
		
		</div>
		
	
	
  
	  <div id="detailsTab" class="immigraTabs_inn">
		 	<jsp:include page="petitionDetailsTab.jsp" />	  		  		  
		
	  </div>
	  
	  <div id="docsTab" class="immigraTabs_inn">
		<jsp:include page="documentDetailsTab.jsp" />	
		
	  </div>
	  
	  <div id="employersTab" class="immigraTabs_inn">
		<jsp:include page="employerDetailsTab.jsp" />	
	  </div>
	  
	  <div id="residencesTab" class="immigraTabs_inn">
		<jsp:include page="residenceDetailsTab.jsp" />
	  </div>	 
	   
	  <div id="educationsTab" class="immigraTabs_inn">
		<jsp:include page="educationDetailsTab.jsp" />
	  </div>
	  
	  <div id="familiesTab" class="immigraTabs_inn">
		<jsp:include page="familyDetailsTab.jsp" />
	  </div>	
	  
	  <div id="notesTab" class="immigraTabs_inn">
		<jsp:include page="noteDetailsTab.jsp" />
	  </div>  
	  
	  <div id="activitiesTab" class="immigraTabs_inn">
		<jsp:include page="activityDetailsTab.jsp" />
	  </div> 
	   
	   <div id="visasTab" class="immigraTabs_inn">
		<jsp:include page="visaDetailsTab.jsp" />
	  </div>
	  	  
	  <div id="passportsTab" class="immigraTabs_inn">
		<jsp:include page="passportDetailsTab.jsp" />
	  </div>	  
	  
	   <div id="formsTab" class="immigraTabs_inn">
		<jsp:include page="formDetailsTab.jsp" />
	  </div>

	</section>
    
    
  </section>
  <!-- Content Area -->

   
<!-- Footer Area --> 

<!--   Popup Code  -->
<div class="popMask"></div>

<script>
$(document).ready(function() {
	$(".popCan").click(function(){
		$(".popMask").fadeOut(200);
		$("#docuView, #emploView, #popUpVisa, #popEduView, #popResiView, #docuImportView, #popNoteView, #popPassView, #viewFormpop, #uploadFormPop").fadeOut(100);
	});
});	
</script>

<!--   Family Popup's  -->
<script>
//update the javascript variables
immigrationId = '<c:out value ="${immigrationId}"/>'
candidateId = '<c:out value ="${candidateId}"/>'
templateId = '<c:out value ="${templateId}"/>'

var addfamilyPopupEventListener = function() {
	$("body").click(function(){ // used for family popup events.
		$(".immiSubMenu").hide();
	});
	
	$(".immMenuClick").click(function(){
		$(".immiSubMenu").show();
	});	
	
	$(".immMenuClick, .immiSubMenu").click(function(event) {
	  event.stopImmediatePropagation();
	});	
  }	
</script>

<script>
// project side bar tabs
$(document).ready(function() {
	$("#detailsTab").show()
	addfamilyPopupEventListener();
    $(".immigraTabs a").click(function(event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
        var tab = $(this).attr("href");
        $(".immigraTabs_inn").not(tab).css("display", "none");
        if(tab=='#deatilsTab'){
        	immigration.displayPetitionDetails(immigrationId);
        }else if(tab=='#familiesTab'){
        	immigration.displayFamilyDetails(candidateId);
        }else if(tab=='#educationsTab'){
        getCandidateEducationList(candidateId);
        } else if(tab=='#employersTab'){
        getCandidateEmployerList(candidateId);
        }else if(tab=='#residencesTab'){
        	getCandidateResidenceList(candidateId);
        }else if(tab=='#passportsTab'){
        immigration.displayPassportDetails(candidateId,'Passport');
        }else if(tab=='#visasTab'){
        immigration.displayVisaDetails(candidateId,'Visa');
        }else if(tab=='#docsTab'){
        	displayImmigrationCandidateDocs(candidateId,templateId,immigrationId,"");
        }else if(tab =='#notesTab'){
        	getNotesListByImmigration(candidateId);
        } else if(tab=='#formsTab'){
        	immigration.displayImmigrationCandidateForms(candidateId,templateId,immigrationId,"");
        	console.log("immigrationId --->"+immigrationId)
        }
		
       
        $(tab).fadeIn();
    });
	
	
});	
	</script>

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
	})
	$(document).ready(function(){
	$("#cd").click(function(){
	$(".clientDetails").toggleClass('hidden', 500);
	});

	});
</script> 
    <!-- Script for Linked Invoice pop -->
    <script>
        $(".sendReminder").click(function(){
        $('.modal.modal_invoice').fadeIn(200);
        $('.side_mask').fadeIn(200);
        });
        
        $(".close, .cancel").click(function(){
        $('.modal.modal_invoice').fadeOut(200);
        $('.side_mask').fadeOut(200);
        });
    </script>
    <script>
	// use below function to clear all the date values.
   function clearDateValue(id){
	   $("#"+id).val(""); 
   }
   </script>
   
<script>
$(document).ready(function(){
	$(".immHeadactions i").click(function(){
		$(".immHeadactions ul").slideToggle(0);
	});
});	
</script>
   
    <!-- Script for Linked Invoice pop -->
<jsp:include page="../masterFooter.jsp" />
 
