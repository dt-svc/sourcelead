<!DOCTYPE html>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:if test="${staticTime == null || staticTime == 0 }">
    <c:set var='staticTime' value="<%=new java.util.Date().getTime() %>" scope="application"></c:set>
 </c:if>
 
<link rel="icon" type="image/png" href="/resources/images/fav.png" />
<title>Welcome to Source Lead :: The Recruitment Application</title>
<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
<!-- Bootstrap -->

<link href="/resources/css/bootstrap.min.css?staticTime=${staticTime}" rel="stylesheet">
<link href="/resources/css/bootstrap-select.css?staticTime=${staticTime}" rel="stylesheet">
<link href="/resources/css/jquery.dataTables.min.css?staticTime=${staticTime}" rel="stylesheet">
<link rel="stylesheet" href="/resources/css/jquery-ui.css">
<link rel="stylesheet" href="/resources/css/chosen.min.css?staticTime=${staticTime}">
<link href="/resources/css/font-awesome.min.css?staticTime=${staticTime}" rel="stylesheet">
<link href="/resources/css/sourcelead-main.css?staticTime=${staticTime}" rel="stylesheet" id="myStyleSheet">
<link href="/resources/css/timesheet.css?staticTime=${staticTime}" rel="stylesheet">


<script src="/resources/js/jquery-1.11.1.min.js?staticTime=${staticTime}"></script>
<script src="/resources/js/formvalidator.js?staticTime=${staticTime}"></script>
<script src="https://maps.google.com/maps/api/js?libraries=places&v=3&key=${googleMapsLocationApiKey}" type="text/javascript"></script>

<script type="text/javascript">
		window.onload = function () {
			initLocationAuto('onBoardLocatin','onBoardCountry','onBoardState','onBoardCity','onBoardZipCode')
			getOnBoardCandidateCountry('${SourceLeadWeb_candidateOnBoarding.country}');
			getOnBoardCandidateStates('${SourceLeadWeb_candidateOnBoarding.country}','${SourceLeadWeb_candidateOnBoarding.state}');
			getOnBoardCandidateCities('${SourceLeadWeb_candidateOnBoarding.country}','${SourceLeadWeb_candidateOnBoarding.state}','${SourceLeadWeb_candidateOnBoarding.city}');
		};
</script>

<script type="text/javascript">
 var sourceleadUrl ='${sourceleadUrl}';
</script>



<div class="wraper"> 
  <!--   Welcome Screens   -->
  
	<section class="welcoBg">
		<section class="welcoInn">
		  <div class="welcoHead">
				
		<c:if test="${!empty SourceLeadWeb_userSpocRolesList}">
		<div class="offOrg">
             <a href="javascript:void(0)" title="Spoc Links" id="spoclinks" onclick="JavaScript: navtoggle(this);"> <i class="fa fa-globe"></i> </a>
	              <div id="spockLinksList" style="display: none;">
	              <ul class="list-unstyled">
	              <c:forEach var="links" items="${SourceLeadWeb_userSpocRolesList}">
		              <li>
			              <a href="<c:url value='${sourceleadUrl}/dashboard/${links.orgLocations.id}'/>" class="sopcSwith">
				                <div class="clearfix">
				                   <div class="pull-left image-parent positionRelative">
				                   <div class="imageParent org-imagelink ${links.orgLocations.id == SourceLeadWeb_orgLocationId ? 'active-comp' : ''}">
				                     <img onerror='predefineOrgImage(this);' data-id="orgLogoImage" src="${links.orgLocations.org.fileUrl}">
				                     </div>
				                   </div>
				                   <div class="pull-left mt5">
				                     <p class="tbldata-dotdot"><i class="fa fa-building"></i>&nbsp;${links.orgLocations.org.orgName}</p>
				                     <p class="tbldata-dotdot"><i class="fa fa-map-marker"></i>&nbsp;${!empty links.orgLocations.city ? links.orgLocations.city : ""} ${links.orgLocations.country}</p>
				                     <p><i class="fa fa-user"></i>&nbsp;${links.roles.displayName}</p>
				                   </div>
				                </div>
			                </a>
		              </li>
	              </c:forEach>
	              </ul>
	            </div>
	            </div>
          </c:if>
				
				<c:if test="${!empty SourceLeadWeb_org.fileContentType}">
					<div class="welLogo">
					<img class="logo" data-id="orgLogoImage" src="${SourceLeadWeb_org.fileUrl}" title="${SourceLeadWeb_orgName}" />
					</div>
				</c:if>
				
				<c:if test="${empty SourceLeadWeb_org.fileContentType}">
					<div class="welLogoText">
					<span  title="${SourceLeadWeb_orgName}">${SourceLeadWeb_orgName}</span>
					</div>
				</c:if>
				
				<div class="welSiO" onclick="logoutFormSubmit();" title="Sign Out"><i class="fa fa-sign-out"></i></div>
			  <ul>
				  <li class="actLi"><span class="fill">1</span><b>Welcome</b><i class="line"></i></li>
				  <li class="actLi"><span class="fill">2</span><b>Step two</b><i class="line"></i></li>
				  <li class="actLi"><span class="fill">3</span><b>Step three</b><i class="line"></i></li>
				  <li class="actLi"><span>4</span><b>Step four</b><i class="line"></i></li>
				  <li><span>5</span><b>Step five</b><i class="line"></i></li>
				  <li><span>6</span><b>Finish</b><i class="line"></i></li>
				  <div class="clearfix"></div>
			  </ul>
			  <div class="clearfix"></div>
		  </div>
		  
		  

<div class="welCo1">	
 <form:form class="login-form clearfix"  id="onboardCandidatePfEsi_form"  name="onboardCandidatePfEsi_form" method="post" >
 <div id="loading"></div>
<h5> PF & ESI Details </h5>
<div class="form-horizontal padBot">
<input type="hidden" id="id" name="id" value="${associatePfEsi.id}">

			<div class="clearfix row">
              <div class="col-md-12 mb">
                <div class="clearfix row">
                  <div class="col-md-6 mb checkbox-new">
                  <c:if test="${associatePfEsi.eligibleForPf eq 'Y'}">
                  		<input id="pf" class="checkbox-custom" checked name="eligibleForPf" type="checkbox"   onClick="pfFunction()">
                  </c:if>
                   <c:if test="${empty associatePfEsi.eligibleForPf}">
                      	<input id="pf" class="checkbox-custom" name="eligibleForPf" type="checkbox"   onClick="pfFunction()">
                  </c:if>
					<label for="pf" class="checkbox-custom-label"><span class="ml10">Eligible for PF</span></label>
                    <div>
                    </div>
                  </div>
                  <div class="clearfix"></div>
                  <div class="clearfix" id="pfEligible" style="display: none;">
                   <div class="col-md-5" id="uanError">
                    <label>UAN Number <span class="text-danger paddingLeft">*</span></label>
                    <div>
					<input type="text" class="form-control" name="uanNumber" id="pfUan"  value="${associatePfEsi.uanNumber}" field-name="PF UAN">
                    </div>
                  </div>
                  
                  <div class="col-md-5" id="pfError">
                    <label>PF Number <span class="text-danger paddingLeft">*</span></label>
                    <div>
                       <input type="text" class="form-control" name="pfNumber"  id="pfNumber" value="${associatePfEsi.pfNumber}" field-name="PF Number">
                    </div>
                  </div>
                  <div class="col-md-5">
                    <label>Enrolled Date </label>
                    <div>
                   <input type="text" class="form-control calendar" name="enroleDate"  value="${enrolledDate}" id="enrolledDate" >
                    </div>
                  </div>
                  </div>
                  <div class="clearfix"></div>
                   <div class="col-md-6 checkbox-new">
                   <c:if test="${associatePfEsi.eligibleForEsi eq 'Y'}">
                             <input id="esi" class="checkbox-custom"  checked name="eligibleForEsi" type="checkbox"  onClick="esiFunction()">
                   </c:if>
                   <c:if test="${empty associatePfEsi.eligibleForEsi}">
                   			<input id="esi" class="checkbox-custom" name="eligibleForEsi" type="checkbox"  onClick="esiFunction()">
                   </c:if>
					<label for="esi" class="checkbox-custom-label"><span class="ml10">Eligible for ESI</span></label>
                    <div>
                    </div>
                  </div>
                  <div class="clearfix"></div>
                  
                  
                  <div class="col-md-5 mt10" id="esiEligible" style="display: none;">
                    <label>ESI Number</label>
                    <div>
                       <input type="text" class="form-control" name="esinumber" value="${associatePfEsi.esinumber}" id="esiNumber" data-validation="validate_int validate_length length1-17" data-validation-optional="true" field-name="ESI Number">
                    </div>
                  </div>
					<div class="clearfix"></div>
                </div>
              </div>
		</div>
		
		<div class="clearfix"></div>
		
		<div class="col-md-6 mb checkbox-new noPad">
                   <c:if test="${associatePfEsi.nominee eq 'Y'}">
                             <input class="checkbox-custom" id="nomineeShow" name="nominee" type="checkbox" onClick="nomineeDetails()">
                   </c:if>
                   <c:if test="${empty associatePfEsi.nominee}">
                   			<input class="checkbox-custom" id="nomineeShow" name="nominee" type="checkbox" onClick="nomineeDetails()">
                   </c:if>
					<label for="nomineeShow" class="checkbox-custom-label" onClick="nomineeDetails()"><span class="ml10">Nominee</span></label>
                    <div>
                    </div>
        </div>
		
<!--         <div class="clearfix row">  -->
<!--         	<a href="javascript:void(0)" id="nomineeShow" class="col-md-12" title="Update Nominee Details">Nominee</a> -->
<!--          </div> -->

			<div class="clearfix">
					<div class="clearfix mb" id="nomineeDetaDiv" style="display: none;" >
		               <div class="pull-right">
		                 <input value="Add" id="addButton" class="btn btn-success btnSuccess" type="button" onclick="addNomineeDetails();">
		               </div>
		             </div>
		             <div id="nomineeDeta" style="display: none;" class="require_wrap_table">
		             </div>
			</div> 


<!--          <div class="clerfix project_wrap_table"> -->
         
         
 <script>
    function getNomineeDetails(){
    	 var userId = '${SourceLeadWeb_user.id}';
//     	var empId = $("#empId").val();
//     	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
    	$("#loading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
//     		  var sourceleadUrl ='${sourceleadUrl}';
    				  $.ajax({
    				    	type: "GET",
    				        url: sourceleadUrl+"/getNomineeDetails?${_csrf.parameterName}=${_csrf.token}&userId="+userId,
    				        success: function(json) {
    						 var result = "";				        	
    						result = result + '<table id="incometable" class="display" cellspacing="0" width="100%" >';
    						result = result + '<thead>';
    						result = result + '<tr>';
    						result = result + '<th>Name</th>';
    						result = result + '<th>Relation</th>';
    						result = result + '<th>Nomination For</th>';
    						result = result + '<th>Nomination %</th>';
    						result = result + '<th>Action</th>';
    						result = result + '</tr>';
    						result = result + '</thead>	';
    						result = result + '<tbody>';
    						 if(json != null && json != ''){
    							 if(json.data.length >= 4){
    								 $("#addButton").hide();
    							 }
    							 for (var i=0; i<5; i++)
    	    						{
    	    			        		 var opt = json.data[i];
    	    			        		 if(opt != null && opt != '' && opt != 'undefined'){
    	    			        			 result = result + ' <tr>';
     	    			        		    if(opt.name !=null && opt.name !='' && opt.name !='undefined'){
     	    			        		    	result = result + '<td><div class="tblminwidth-75">'+opt.name+'</div></td>';
     	    			        		    }else{
     	    			        		    	result = result + '<td><div class="tblminwidth-75">---</div></td>';
     	    			        		    }
     	    			        		    
     	    			        		    if(opt.relation !=null && opt.relation !='' && opt.relation !='undefined'){
     	    			        		    	result = result + '<td><div>'+opt.relation+'</div></td>';
     	    			        		    }else{
     	    			        		    	result = result + '<td><div>---</div></td>';
     	    			        		    }
     	    			        		    
     	    			        		    if(opt.nominationFor !=null && opt.nominationFor !='' && opt.nominationFor !='undefined'){
     	    									result = result + ' <td><div class="tblminwidth-75">'+opt.nominationFor+'</div></td>';
     	    								}else{
     	    									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
     	    								}
     	    			        		    if(opt.nominationPercentage !=null && opt.nominationPercentage !='' && opt.nominationPercentage !='undefined'){
     	    									result = result + ' <td><div class="tblminwidth-75">'+opt.nominationPercentage+'</div></td>';
     	    								}else{
     	    									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
     	    								}
     	    								
     	    			        		    result = result + ' <td><div class="tblminwidth-75"><ul class="list-inline list-unstyled mb0"><li><i class="cursorPointer fa fa-trash" onclick="deleteNominee('+opt.id+','+opt.candidateId+');"></i></li></div></td>';
     	    								
     	    								result = result + ' </tr>';
    	    			        		 }
    	    						}
    						} 
    			        	 
    						result = result + '  </tbody>';
    						result = result + ' </table>';
							$("#nomineeDeta").show();
							$("#nomineeDetaDiv").show();
							
    						$("#nomineeDeta").empty();
    						$("#nomineeDeta").append(result);
    						$("#loading").removeAttr("style");
    				        },
    				        
    				    });
    }
    </script>
    
         
<!--                   <table> -->
                    <!-- <thead>
                      <tr>
                        <th>Name</th>
                        <th>Relation</th>
                        <th>Nomination For</th>
                        <th>Nomination %</th>
                        <th>Action</th>
                      </tr>
                    </thead> -->
					  <!-- <tbody>
				<tr>
				<td><div class="ExpShow"><i class="fa fa-plus-square-o" aria-hidden="true"></i>Category 1</div></td>
				<td><div>20/09/2017</div></td>
				<td><div>Cash</div></td>
				<td><div>USD</div></td>
				<td><div>5055</div></td>				
			</tr>
			<tr class="ExpDe">
				<td colspan="6">
					<div class="expDetails">
						<ul>
							<li><strong>Exp Category :</strong><span> Category 1</span><div class="clearfix"></div></li>
							<li><strong>Date :</strong><span> 20/09/2017</span><div class="clearfix"></div></li>
							<li><strong>Payment Mode :</strong><span> Cash</span><div class="clearfix"></div></li>
							<li><strong>Currency :</strong><span> USD</span><div class="clearfix"></div></li>
							<li><strong>Amount :</strong><span> 20,000</span><div class="clearfix"></div></li>
							<li><strong>Purpose :</strong><span> Purpose text</span><div class="clearfix"></div></li>
							<li><strong>Description :</strong><p> Description Description Description Description</p>
							<div class="clearfix"></div>
							</li>
							<div class="clearfix"></div>
						</ul>
					</div>
				</td>			
			</tr>
						  
					  </tbody> -->
<!--                   </table> -->
<!--                 </div> -->
<!--                 <div class="clearfix"></div> -->
         
         
</div>
</form:form>


 <div class="clearfix"></div>
                <div class="clearfix mt minHeightcol5 row" id="nomineeDetails" style="display: none;">
                <form:form class="login-form clearfix"  id="nomineeDetails_form"  name="nomineeDetails_form" method="post" >
                  <div class="col-md-12">
                  <h5>Family Details</h5>
                  </div>
                  <div class="clearfix"></div>
                  <div class="col-md-5">
                    <label>Name<span class="text-danger paddingLeft">*</span></label>
                    <div>
                      <input type="text" id="name" class="form-control" name="name" value="" field-name="Name" data-validation="required validate_length length1-250 validate_Alphabet">
                    </div>
                  </div>
                  <div class="col-md-5">
                    <label>Date of Birth<span class="text-danger paddingLeft">*</span></label>
                    <div>
                      <input type="text" id="dateOfBirth" class="form-control calendar" name="dateOfBirth" value="" field-name="Date of Birth" data-validation="required">
                    </div>
                  </div>
                  <div class="col-md-5">
                    <label>Gender</label>
                    <div>
                      <select class="sel" id="gender" name="gender">
                        <option value="">Select</option>
                        <option value="Male">Male</option>
                        <option value="Female">Female</option>
                        <option value="Transgender">Transgender</option>
                      </select>
                    </div>
                  </div>
                  <div class="col-md-5">
                    <label>Blood Group</label>
                    <div>
                      <select class="sel" id="bloodGroup" name="bloodGroup">
                        <option selected>Select</option>
                        <option value="A+">A+</option>
                        <option value="B+">B+</option>
                        <option value="AB+">AB+</option>
                        <option value="A-">A-</option>
                        <option value="B-">B-</option>
                        <option value="AB-">AB-</option>
                        <option value="O+">O+</option>
                        <option value="O-">O-</option>
                      </select>
                    </div>
                  </div>
                  <div class="col-md-5">
                    <label>Relation<span class="text-danger paddingLeft">*</span></label>
                    <div>
                      <select class="sel" id="relation" name="relation" field-name="Relation" data-validation="required">
                        <option value="">Select</option>
                        <option value="Father">Father</option>
                        <option value="Mother">Mother</option>
                        <option value="Brother">Brother</option>
                        <option value="Sister">Sister</option>
                        <option value="Husband">Husband</option>
                        <option value="Wife">Wife</option>
                        <option value="Son">Son</option>
                        <option value="Daughter">Daughter</option>
                      </select>
                    </div>
                  </div>
                  <div class="col-md-5">
                    <label>Nomination For</label>
                    <div>
                      <select class="sel" name="nominationFor">
                        <option value="">Select</option>
                        <option value="PF">PF</option>
                        <option value="ESI">ESI</option>
                      </select>
                    </div>
                  </div>
                  <div class="col-md-5">
                    <label>Nomination %<span class="text-danger paddingLeft">*</span></label>
                    <div>
                      <input type="number" id="nomination" class="form-control" name="nominationPercentage" field-name="Nomination Percentage" onInput="return check(event,value)" min="0" max="100" step="0.01" data-validation="required">
                    </div>
                  </div>
                  <div class="col-md-5">
                    <label>Address</label>
                    <div>
                      <textarea rows="2" id="address" class="form-control" name="address" data-validation="validate_Space validate_length length1-500" data-validation-optional = "true"></textarea>
                    </div>
                  </div>
                  <div class="col-md-5">
                    <div class="row">
                      <div class="col-md-4 mb checkbox-new">
                        <label>&nbsp;</label>
                        <div class="clearfix">
                          <input id="illness" class="checkbox-custom" name="illness" type="checkbox" onClick="illnessFunction()">
                          <label for="illness" class="checkbox-custom-label"><span class="ml10">Illness</span></label>
                        </div>
                      </div>
                      <div class="col-md-4 mb checkbox-new">
                        <label>&nbsp;</label>
                        <div class="clearfix">
                          <input id="minor" class="checkbox-custom" name="minor" type="checkbox" onClick="minorFunction()">
                          <label for="minor" class="checkbox-custom-label"><span class="ml10">Minor</span></label>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="clearfix mt"></div>
                  <div class="clearfix" id="illnessMinor" style="display: none;">
                  <h4 class="col-md-12 margin-top0 ml">Illness</h4>
                    <div class="col-md-5">
                      <label>Guardian Name<span class="text-danger paddingLeft">*</span></label>
                      <div>
                        <input type="text" id="illnessGuardianName" class="form-control" name="illnessGuardianName" field-name="Guardian Name" data-validation="required">
                      </div>
                    </div>
                    <div class="col-md-5">
                      <label>Relation<span class="text-danger paddingLeft">*</span></label>
                      <div>
                        <select class="sel" id="illnessGuardianRelationName" name="illnessGuardianRelationName" field-name="Relation" data-validation="required">
                          <option value="">Select</option>
                            <option value="Father">Father</option>
	                        <option value="Mother">Mother</option>
	                        <option value="Brother">Brother</option>
	                        <option value="Sister">Sister</option>
	                        <option value="Husband">Husband</option>
	                        <option value="Wife">Wife</option>
	                        <option value="Son">Son</option>
	                        <option value="Daughter">Daughter</option>
                        </select>
                      </div>
                    </div>
                    <div class="col-md-5">
                      <label>Address</label>
                      <div>
                        <textarea rows="2" id="illnessGuardianAddress" class="form-control" name="illnessGuardianAddress"></textarea>
                      </div>
                    </div>
                    <div class="col-md-5">
                      <label>Phone Number</label>
                      <div>
                        <input type="text" id="illnessGuardianPhoneNumber" class="form-control" name="illnessGuardianPhoneNumber">
                      </div>
                    </div>
                    <div class="clearfix mt"></div>
                    <div class="col-md-5 mt">
                      <label>Email</label>
                      <div>
                        <input type="text" id="illnessGuardianEmail" class="form-control" name="illnessGuardianEmail">
                      </div>
                    </div>
                    <div class="clearfix"></div>
                  </div>
                  <!--  Minor Div -->
                  <div class="clearfix" id="minorDiv" style="display: none;">
                  <h4 class="col-md-12 margin-top0 ml">Minor</h4>
                    <div class="col-md-5">
                      <label>Guardian Name<span class="text-danger paddingLeft">*</span></label>
                      <div>
                        <input type="text" id="minorGuardianName" class="form-control" name="minorGuardianName" field-name="Guardian Name" data-validation="required">
                      </div>
                    </div>
                    <div class="col-md-5">
                      <label>Relation<span class="text-danger paddingLeft">*</span></label>
                      <div>
                        <select class="sel" id="minorGuardianRelationName" name="minorGuardianRelationName" field-name="Relation" data-validation="required">
                          <option value="">Select</option>
                          	<option value="Father">Father</option>
	                        <option value="Mother">Mother</option>
	                        <option value="Brother">Brother</option>
	                        <option value="Sister">Sister</option>
	                        <option value="Husband">Husband</option>
	                        <option value="Wife">Wife</option>
	                        <option value="Son">Son</option>
	                        <option value="Daughter">Daughter</option>
                        </select>
                      </div>
                    </div>
                    <div class="col-md-5">
                      <label>Address</label>
                      <div>
                        <textarea rows="2" id="minorGuardianAddress" class="form-control" name="minorGuardianAddress"></textarea>
                      </div>
                    </div>
                    <div class="col-md-5">
                      <label>Phone Number</label>
                      <div>
                        <input type="text" id="minorGuardianPhoneNumber" class="form-control" name="minorGuardianPhoneNumber">
                      </div>
                    </div>
                    <div class="clearfix mt"></div>
                    <div class="col-md-5 mt">
                      <label>Email</label>
                      <div>
                        <input type="text" id="minorGuardianEmail" class="form-control" name="minorGuardianEmail">
                      </div>
                    </div>
                    <div class="clearfix"></div>
                  </div>
                  
                  
                  <div class="clearfix footerstyelsNew">
			             <div class="col-md-6 col-md-offset-6">
			               <div class="pull-right">
			                 <input value="save" id="saveButton" class="btn btn-success btnSuccess" type="button" onclick="saveNomineeDetails();">
			               </div>
			             </div>
			     </div> 
                </form:form>  
                </div>
</div>
  

<div class="welBtns">
	<input type="button"  value="Previous" onClick="javascript:window.location.href = '<c:url value='${sourceleadUrl}/onboardingCandidateBankingDetails'/>'" >
	<input type="button"  value="Proceed to next step" id="save_button"  onclick="saveOnboardingCandidatePfEsiDetails();" class="Brig">
	<div class="clearfix"></div>
</div>
		  
		</section>
	</section>  
  </div>
  <!--   Welcome Screens   -->

  <!-- jQuery (necessary for Bootstrap's JavaScript plugins) --> 
  
  				<div class="location-alert alert alert-danger alert-dismissable zInPop" id="locationAlert">
    <a href="#" class="loc-alertclose close">&times;</a>
    <strong>Alert !</strong> <span>Your are not able to Save Required data is Missing.</span>
  </div>
  
  
  
    
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script> 
  <script src="/resources/js/jquery.dataTables.min.js?staticTime=${staticTime}"></script> 
  <script src="/resources/js/moment.min.js?staticTime=${staticTime}"></script> 
  <script src="/resources/js/bootstrap-select.min.js?staticTime=${staticTime}"></script> 
  <script src="/resources/js/bootstrap.min.js?staticTime=${staticTime}"></script> 
  <script src="/resources/js/masterHeaderAction.js?staticTime=${staticTime}"></script> 
  <script src="/resources/js/chosen.jquery.min.js?staticTime=${staticTime}"></script> 
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
			  $(".loc-alertclose").click(function(){
					 $(".location-alert").fadeOut();
				})		 
</script> 

<script src="/resources/js/formvalidator.js"></script>
<script src="../resources/js/jquery-ui.js"></script>
<script>
$(function() {
	var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	  var finalDateFormatProjectSave = '';
	  if(locationDate.match("yyyy-MM-dd")){
		  finalDateFormatProjectSave = 'yy-mm-dd';
	  }if(locationDate.match("dd-MM-yyyy")){
		  finalDateFormatProjectSave = 'dd-mm-yy'; 
	  }if(locationDate.match("MM-dd-yyyy")){
		  finalDateFormatProjectSave = 'mm-dd-yy'; 
	  }if(locationDate.match("yyyy-dd-MM")){
		  finalDateFormatProjectSave = 'yy-dd-mm'; 
	  }
	  $('#enrolledDate').datepicker({
			dateFormat : finalDateFormatProjectSave,
			changeMonth: true,
			changeYear: true, 
			 
		});
});
</script>
 
<script>
 function saveOnboardingCandidatePfEsiDetails(){
	 var validationSettings = {
		        errorMessagePosition : 'element',
		       borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd' 
	         }; 
	 
	 if ($('#onboardCandidatePfEsi_form').validate(false, validationSettings)){ 
	        var formData = getFormData("onboardCandidatePfEsi_form");
	        $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	         $.ajax({
		    	type: "POST",
		    	data: formData,
		    	 url: sourceleadUrl+"/saveOnboardingCandidatePfEsiDetails?${_csrf.parameterName}=${_csrf.token}",
		        success: function(response) {
		        	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
      	    		    showError(response.responseMessage);
      	    		    $("#loading").removeAttr('style');
      	    		}
		        	else if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		location.replace(sourceleadUrl+"/onboardingCandidateStep3");  
		        	} else if (response.indexOf('Sign In') !== -1) {
		        		location.replace(sourceleadUrl);
		        	} else {
		        	    showError("Operation failed, please try again");
		        	}
		        },
		    });  
	        return true;
	  } else {
		return false;
	  }
 }
 
 $('body').on('blur', '#onboardCandidatePfEsi_form', function() {
		$("#onboardCandidatePfEsi_form").showHelpOnFocus();
		$("#onboardCandidatePfEsi_form").validateOnBlur(false, validationSettings);  
	});
 </script>
 
 <script>
 function saveNomineeDetails(){
	 illnessFunction();
	 minorFunction();
	 var userId = '${SourceLeadWeb_user.id}';
	 if ($('#nomineeDetails_form').validate(false, validationSettings)){ 
	        var formData = getFormData("nomineeDetails_form");
	        $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	         $.ajax({
		    	type: "POST",
		    	data: formData,
		    	 url: sourceleadUrl+"/saveNomineeDetails?${_csrf.parameterName}=${_csrf.token}&userId="+userId,
		        success: function(response) {
		        	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
      	    		    showError(response.responseMessage);
      	    		    $("#loading").removeAttr('style');
      	    		}
		        	else if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		//location.replace(sourceleadUrl+"/onboardingCandidateTaxDetails");  
		        		getNomineeDetails();
		        		$("#nomineeDetails").hide();
		        	} else if (response.indexOf('Sign In') !== -1) {
		        		location.replace(sourceleadUrl);
		        	} else {
		        	    showError("Operation failed, please try again");
		        	}
		        },
		    });  
	        return true;
	  } else {
		return false;
	  }
 }
 
 $('body').on('blur', '#nomineeDetails_form', function() {
		$("#nomineeDetails_form").showHelpOnFocus();
		$("#nomineeDetails_form").validateOnBlur(false, validationSettings);  
	});
 </script>
 
 <script type="text/javascript">
(function ($) {
   $.fn.serializeFormJSON = function () {

       var o = {};
       var a = this.serializeArray();
       $.each(a, function () {
           if (o[this.name]) {
               if (!o[this.name].push) {
                   o[this.name] = [o[this.name]];
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
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };

        
</script>  

<script>
		function logoutFormSubmit() {
			document.getElementById("logoutForm").submit();
		}
</script>
 
<script>
  function navtoggle(elementRef){
	  
	 if('${SourceLeadWeb_userSpocRolesListCount}' < 6){
		 document.getElementById("spockLinksList").removeAttribute("class")
		 document.getElementById('spockLinksList').className = 'spoclinks-area'
	     document.getElementById("spockLinksList").style.display="block";
	 }else{
		 document.getElementById("spockLinksList").removeAttribute("class")
		 document.getElementById('spockLinksList').className = 'spoclinks-area spoclinksMore'
		 document.getElementById("spockLinksList").style.display="block";
	 }
  }
  $('html').click(function(){
	  $('#spockLinksList').hide();
  });
  
  $(".offOrg").click(function( event ) {
	  event.stopPropagation();
	});
  </script>
  
 <script>
	function predefineOrgImage(imageId){
		$(imageId).attr('src','/resources/images/office-image.jpg');
	}
</script>

<c:url var="logoutUrl" value="/logout"/>
<form action="${logoutUrl}" method="post" id="logoutForm">
 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<form action="${logoutUrl}" method="post" id="logoutForm">
 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
<script>
//Pf Function
function pfFunction() {
    var checkBox = document.getElementById("pf");
    var pfel = document.getElementById("pfEligible");
    var val = checkBox.checked;
    var dbVal = '${associatePfEsi.eligibleForPf}';
    if (checkBox.checked == true){
        pfel.style.display = "block";
        $("#pfUan").attr("data-validation", "required validate_int validate_length length1-12");
        $("#pfNumber").attr("data-validation", "required validate_AlphaNumber validate_length length1-19");
        $("#pf").val('pf');
    } else {
       pfel.style.display = "none";
    	$('#pfUan').removeAttr('data-validation');
    	$('#pfNumber').removeAttr('data-validation');
    	$("#uanError").find("p.jquery_form_error_message").remove();
    	$("#pfError").find("p.jquery_form_error_message").remove();
    	$("#pfUan").val('');
    	$("#pfNumber").val('');
    	$("#enrolledDate").val('');
    }
}
		//ESI Function
function esiFunction() {
    var checkBox = document.getElementById("esi");
    var esiEl = document.getElementById("esiEligible");
    if (checkBox.checked == true){
       esiEl.style.display = "block";
       $("#esi").val('esi');
    } else {
       esiEl.style.display = "none";
       $("#esiNumber").val('');
    }
}
</script>
<script type="text/javascript">
$(document).ready(function(){
	pfFunction();
	esiFunction();
});
</script>

<script>
$("#illness").click(function() {
	$("#illnessMinor").toggle();
});
$("#nomineeShow").click(function() {
	$("#nomineeDetails").toggle();
});
$("#minor").click(function() {
	$("#minorDiv").toggle();
});
</script>
<script>
$(function() {
    $(".ExpShow").click(function(){
		$(this).parent().parent().next().slideToggle(100);
		$(this).find("i").toggleClass("closExp");
	});
});	
</script>

<script>

	//to set from date to current month
	$('#dateOfBirth').datepicker({
	dateFormat : 'dd-mm-yy',
	changeMonth: true,
	changeYear: true,
	});

</script>

<script> 
function deleteNominee(nomineeId,candidateId){ 
    $.ajax({
  	  type: "POST",
      url: sourceleadUrl+"/deleteNominee?&id="+nomineeId+"&candidateId="+candidateId, 	  
 	  success: function(data) { 
 		 getNomineeDetails();
	  }, 
    });
	   
 } 
 </script> 

<script>
function addNomineeDetails(){
	$("#nomineeDetails").show();
	$("#name").val('');
    $("#dateOfBirth").val('');
	$("#gender").val('');
	$("#bloodGroup").val('');
	$("#relation").val('');
	$("#nominationFor").val('');
	$("#nomination").val('');
	$("#address").val(data.address);
	$("#illnessGuardianName").val('');
	$("#illnessGuardianRelationName").val('');
	$("#illnessGuardianAddress").val('');
	$("#illnessGuardianPhoneNumber").val('');
	$("#illnessGuardianEmail").val('');
	$("#minorGuardianName").val('');
	$("#minorGuardianRelationName").val('');
	$("#minorGuardianPhoneNumber").val('');
	$("#minorGuardianEmail").val('');
	
}
</script>


<script>
function illnessFunction(){
	var checkBox = document.getElementById("illness");
	if (checkBox.checked == true){
		$("#illness").show();
		$('#illnessGuardianName').attr('data-validation', "required validate_length length1-250");
		$('#illnessGuardianRelationName').attr('data-validation', "required");
		$('#illnessGuardianPhoneNumber').attr('data-validation-optional', "true");
		$('#illnessGuardianPhoneNumber').attr('data-validation', "validate_space validate_length length7-10");
	    } else {
		 $("#illness").hide();
		 $('#illnessGuardianName').removeAttr('data-validation', "required validate_length length1-250");
	     $('#illnessGuardianRelationName').removeAttr('data-validation', "required");
	     $('#illnessGuardianPhoneNumber').removeAttr('data-validation-optional', "true");
		 $('#illnessGuardianPhoneNumber').removeAttr('data-validation', "validate_space validate_length length7-10");
 }
	
}
</script>

<script>
function minorFunction(){
	var checkBox = document.getElementById("minor");
	if (checkBox.checked == true){
		$("#minor").show();
		$('#minorGuardianName').attr('data-validation', "required validate_length length1-250");
		$('#minorGuardianRelationName').attr('data-validation', "required");
		$('#minorGuardianPhoneNumber').attr('data-validation-optional', "true");
		$('#minorGuardianPhoneNumber').attr('data-validation', "validate_space validate_length length7-10");
	    } else {
		 $("#minor").hide();
		 $('#minorGuardianName').removeAttr('data-validation', "required validate_length length1-250");
	     $('#minorGuardianRelationName').removeAttr('data-validation', "required");
	     $('#minorGuardianPhoneNumber').removeAttr('data-validation-optional', "true");
		 $('#minorGuardianPhoneNumber').removeAttr('data-validation', "validate_space validate_length length7-10");
 }
	
}
</script>

<script>
 check = function (e,value){
		if (!e.target.validity.valid) {
   	e.target.value = value.substring(0,value.length - 1);
     return false;
   }
 	var idx = value.indexOf('.');
   if (idx >= 0) {
     if (value.length - idx > 3 ) {
       e.target.value = value.substring(0,value.length - 1);
       return false;
     }
   }
   return true;
 }
 
</script>

<script src="/resources/js/slead-common.js?staticTime=${staticTime}"></script>
