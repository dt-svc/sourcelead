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
<style>
.disabledLink
{
	color: #333;
	text-decoration : none;
	pointer-events: none;
}
.form-control[disabled].selectdisabled {
background-color: #ddd; color: #333; border: 1px solid #ddd !important;
}
</style>
<c:set var ="passOnLoadCount" value = "${tabRecordCountMap['Passport']}" scope="request"/>
<c:set var ="visaOnLoadCount" value = "${tabRecordCountMap['Visa']}" scope="request"/>
<c:set var ="empOnLoadCount" value  =  "${tabRecordCountMap['Employers']}" scope="request"/>
<c:set var ="resiOnLoadCount" value = "${tabRecordCountMap['Residence']}" scope="request"/>
<c:set var ="eduOnLoadCount" value  = "${tabRecordCountMap['Education']}" scope="request"/>
<c:set var ="familyOnLoadCount" value  = "${tabRecordCountMap['Family']}" scope="request"/>
<c:set var ="taskOnLoadCount" value  = "${tabRecordCountMap['Tasks']}" scope="request"/>
<c:set var ="curUscisMyImmi" value = "${immigration.currUscisStatus}" scope="session"/>
 <section class="content-wraper content-wraperNew">
   
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>${immigration.generatedId}</h2>
        <!-- <span id='packageZipfile' class="cursorPointer"><i class="glyphicon glyphicon-download-alt paddingRight"></i>Download Package</span> -->
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
			<c:if test="${myImmigrationPage =='N'}">
				<div class="col-md-4">
	  				<p id='editImmigrationView' class="cursorPointer"><a href=/app/immigration/editImmigrationDetails?immigrationUUID=${immigration.immigrationUUID}>Edit</a></p>
	  			</div>
			</c:if>
  			<div class="col-md-2 clearfix mt10">
  				<p id='packageZipfile' class="cursorPointer"><i class="glyphicon glyphicon-download-alt mr10"></i>Download Package</p>
  			</div>
  			
  			<div class="col-md-2 immHeadactions clearfix">
  			
  			 <sec:authorize access="hasAnyRole('ROLE_EDIT_VIEW_PETITION')">
  			<select id="uscisStatusId" onchange="changeUscisStatusForImmigration($(this).val(),${immigration.currUscisStatusId});" <c:if test = "${myImmigrationPage =='Y'}">disabled</c:if> class="form-control selectdisabled selstyles-new">
  				<c:forEach var="item" items="${uscisStatusList}">
  					<option ${immigration.currUscisStatusId == item.id ? 'selected' : '' } value="${item.id}">${item.name}</option>
  				</c:forEach>
  			</select>
  			</sec:authorize>
  			</div>
  		</div>
		<div class="immigraHead">
			<ul class="immigraTabs">
              <li class="current"><a href="#detailsTab"><i class="fa fa-check-circle-o"></i>Details</a></li>
              <li><a href="#passportsTab"><i id ="passportfilled" class="fa fa-check-circle-o"></i>Passport</a></li>
              <li><a href="#visasTab"  id="visaInfoTab" class="${visaOnLoadCount==null?'disabledLink':''}"><i id ="visafilled" class="fa fa-check-circle-o"></i>Visa</a></li>
              <li><a href="#employersTab" id="employersInfoTab" class="${empOnLoadCount==null?'disabledLink':''}"><i id ="employersfilled" class="fa fa-check-circle-o"></i>Employers</a></li>
              <li><a href="#educationsTab" id="educationInfoTab" class="${eduOnLoadCount==null?'disabledLink':''}"><i id ="educationfilled" class="fa fa-check-circle-o"></i>Educations</a></li>
              <li><a href="#residencesTab" id="residenceInfoTab" class="${resiOnLoadCount==null?'disabledLink':''}"><i id ="residencefilled" class="fa fa-check-circle-o"></i>Residence</a></li>
              <li><a href="#familiesTab" class="${familyOnLoadCount==null?'disabledLink':''}"><i id ="familyfilled" class="fa fa-check-circle-o"></i>Family</a></li>
              <li><a href="#formsTab"><i id="allFormsfilled" class="fa fa-check-circle-o"></i>Forms</a></li>
              <li><a href="#docsTab"><i id="allDocsfilled" class="fa fa-check-circle-o"></i>Documents</a></li>
              <li><a href="#tasksTab"><i id="tasksInfoTab" class="${taskOnLoadCount==null?'fa fa-check-circle-o':''}"></i>Tasks</a></li>
              <li><a href="#notesTab">Notes</a></li>
              <li><a href="#activitiesTab" id= "#activitiesId">Timeline</a></li>
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
	  
	  <div id="tasksTab" class="immigraTabs_inn">
		<jsp:include page="taskDetailsTab.jsp" />
	  </div>

	</section>
    <div id="popUp"></div>
    
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
	$("#remove_immigration_pop").remove();
	$(".project_inn_mask").remove();
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
        }else if(tab=='#activitiesTab'){
        	getImmigrationActivies();
        }else if(tab=='#tasksTab'){
        	
        	getImmigrationTasks(immigrationId,"${myImmigrationPage}","");
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
<script>
$("#packageZipfile").click(function() {
	if(immigrationId == 0)
		return;
	$.post("downloadAllImmigrationDocsZip",{immigrationId: immigrationId}, function(data) { //  used for download all immigration doc files 
		if(data !== "" && data.indexOf("Documents Found") === -1)
			location.href = '/app/downloadFile.html?file='+encodeURI(data); // encodeURI used for encoding file path in request parameter.
		else if(data !== "" && data.indexOf("immigrationId Found") !== -1)
			alert(data);
		else
			alert("Unable to locate logs for this upload");
	});
});
</script>
<script>
	function goToSummaryNextTab(tabName) {
		if(tabName=="#visasTab"){
			if($('#passportTbl tr').length<=1){
				showWarningPopup("Passport");
			return;
			}
		}else if(tabName=="#residencesTab"){
			if($('#educationTbl tr').length<=1){
				showWarningPopup("Education");
			return;
			}
		}else if(tabName=="#educationsTab"){
			if($('#employerTbl tr ').length<=1){
				showWarningPopup("Employer");
			return;
			}
		}
		else if(tabName=="#familiesTab"){
			if($('#residenceTbl tr').length<=1){
				showWarningPopup("Residence");
			return;
			}
		}	
		else if(tabName=="#docsTab"){
			 var multiWarnMsg="Please Upload Below Required Forms!..." 
		    var missedForms="";
			$("table#formsTbl > tbody > tr").each(
					function(row, tr) {
						var formName = ($('td:eq(0)', this).text()); // first column form name text
						var required = ($('td:eq(1)', this).text());// second  column required column text
						var status = ($('td:eq(4)', this).text()); // fifth  column status column text

						if (required.toUpperCase() == 'YES'	&& status.toUpperCase() == 'REQUESTED') {
							if (missedForms == '') {
								missedForms = missedForms + formName;
							} else {
								missedForms = missedForms + ", " + formName;
							}

						}
						
			});
			
			if(missedForms!=''){  // if missed required forms upload
				multiWarnMsg = multiWarnMsg + '\n' + missedForms;
			    showWarningPopup("Form", multiWarnMsg);
			return;
			}
		}
		$("a[href='" + tabName + "']").removeClass("disabledLink");
		$("a[href='" + tabName + "']").click();
	}
	
	function goToSummaryTab(tabName) {
		$("a[href='" + tabName + "']").removeClass("disabledLink");
		$("a[href='" + tabName + "']").click();
	}
	function changeUscisStatusForImmigration(uscisStatusId,uscisOldId){
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/immigration/updateUscisTypeDropDown?templateId="+immigrationId+"&uscisId="+uscisStatusId+"&uscisOldId="+uscisOldId, 
		        success: function(data) {
		        	if(data == "success"){
		        		location.reload();
		        	}
		        }
		 });
	}
</script>
<script>

function showWarningPopup(tabName, multiWarnMsg){ // multiWarnMsg populated when validated form and docs required upload docs info.
	$(".project_inn_mask").remove();
	
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_immigration_pop' class='remove_user_pop remove_user_pop_a modal-body'>";
	 
	if (typeof multiWarnMsg != 'undefined' && multiWarnMsg != '') {
			result = result + " <b class='mt mb'>" + multiWarnMsg + "</b>";
	} 
	else {
		result = result + " <b class='mt mb'>Please Add " + tabName	+ " Details!...</b>";
	}
	   result = result + ' <div id="reasonForTemplateDiv"/>';
		result = result	+ "		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onClick='closePop()'></b>";
		result = result + "	</div>";
		$("#popUp").append(result);
		$("#remove_immigration_pop").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
	}

	function closePop() {
		$("#remove_immigration_pop").fadeOut(200);
		$(".project_inn_mask").fadeOut(200);
		$(".project_inn_mask").remove();
		$("#remove_immigration_pop").remove();
	}
</script>
<jsp:include page="../masterFooter.jsp" />
 
