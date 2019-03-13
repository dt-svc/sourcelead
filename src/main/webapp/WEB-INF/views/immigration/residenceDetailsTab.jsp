<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <c:set var ="residenceListCount" value ="${fn:length(candidatesResidenceList)}"/>
<div class="immigraTabsHead">
			<c:choose>
				    <c:when test="${myImmigrationPage == 'N'}">
				        <a href="javascript:void(0)" id="addResi"><i class="glyphicon glyphicon-plus"></i> Add Residence</a>
				    </c:when>
				    <c:otherwise>
				        <c:choose>
						    <c:when test="${curUscisMyImmi == 'Request to fill' || curUscisMyImmi == 'Request for modifications'}">
						        <a href="javascript:void(0)" id="addResi"><i class="glyphicon glyphicon-plus"></i> Add Residence</a>
						    </c:when>
						    <c:otherwise>
						    </c:otherwise>
						</c:choose>
				    </c:otherwise>
				</c:choose>
		  	<span><input class="immigraTabSerach" type="text" id="residenceSearch" placeholder="Location/Country"></span>
		</div>
		
	  	<div class="immigraTable">
		  <table id="residenceTbl">
			<tbody>
			  <tr>
				<th>Location</th>
				<th>Country</th>
				<th>From</th>
				<th>To</th>
				<c:choose>
				    <c:when test="${myImmigrationPage == 'N'}">
				        <th>Action</th>
						<th>&nbsp;</th>
				    </c:when>
				    <c:otherwise>
				        <c:choose>
						    <c:when test="${curUscisMyImmi == 'Request to fill' || curUscisMyImmi == 'Request for modifications'}">
						        <th>Action</th>
								<th>&nbsp;</th>
						    </c:when>
						    <c:otherwise>
						    </c:otherwise>
						</c:choose>
				    </c:otherwise>
				</c:choose>
			  </tr>
			  <c:forEach var="residitems" items="${candidatesResidenceList}" varStatus="rows"> 
			  
			  <tr>
				<td><div class="tbldata-dotdot-big" title = "${residitems.location}">${!empty residitems.location ? residitems.location : '---'}</div></td>
				<td><div class="tbldata-dotdot-vsm" title = "${residitems.country}">${!empty residitems.country ? residitems.country : '---'}</div></td>
				<td><div class="tbldata-dotdot-vsm" title = "${residitems.startDateStr}">${!empty residitems.startDateStr ? residitems.startDateStr : '---'}</div></td>				
				<td><div class="tbldata-dotdot-vsm" title = "${residitems.endDateStr}">${!empty residitems.endDateStr ? residitems.endDateStr : '---'}</div></td>
				<c:choose>
				    <c:when test="${myImmigrationPage == 'N'}">
				        <c:choose>
						<c:when test = "${residitems.isCurrAddress =='Y'}">
						<td><div><i id="edit" onclick="immigration.editResidenceDEtailsByRowId(${residitems.id},'${residitems.isCurrAddress}');"
						class="glyphicon glyphicon-pencil cursorPointer"></i></div></td>
						</c:when>
						<c:otherwise>
						<td><div><i id="edit" onclick="immigration.editResidenceDEtailsByRowId(${residitems.id});"
						class="glyphicon glyphicon-pencil cursorPointer"></i><i id="delete" 
						 onclick="deleteImmigrationRecord('immigration.deleteResidenceDetailsById',${residitems.id});" 
						 class="glyphicon glyphicon-trash ml10 cursorPointer"></i></div></td>
						</c:otherwise>
						</c:choose>
				    </c:when>
				    <c:otherwise>
				        <c:choose>
						    <c:when test="${curUscisMyImmi == 'Request to fill' || curUscisMyImmi == 'Request for modifications'}">
						        <c:choose>
									<c:when test = "${residitems.isCurrAddress =='Y'}">
									<td><div><i id="edit" onclick="editResidenceDEtailsByRowId(${residitems.id},'${residitems.isCurrAddress}');"
									class="glyphicon glyphicon-pencil cursorPointer"></i></div></td>
									</c:when>
									<c:otherwise>
									<td><div><i id="edit" onclick="immigration.editResidenceDEtailsByRowId(${residitems.id});"
									class="glyphicon glyphicon-pencil cursorPointer"></i><i id="delete" 
									 onclick="deleteImmigrationRecord('immigration.deleteResidenceDetailsById',${residitems.id});" 
									 class="glyphicon glyphicon-trash ml10 cursorPointer"></i></div></td>
									</c:otherwise>
								</c:choose>
						    </c:when>
						    <c:otherwise>
						    </c:otherwise>
						</c:choose>
				    </c:otherwise>
				</c:choose>
				
				<!-- <td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
						</span>
					</div>
				</td> -->
			  </tr>  			  			  
			   
 
			  </c:forEach> 
			 <c:if test="${empty candidatesResidenceList}">
				<tr>
				 <td colspan="5" valign="top" class="dataTables_empty"></td>
				</tr>
		     </c:if>						
			</tbody>
		  </table>
		</div>
		<!-- 
		<div class="immigraPagi clearfix">
			<div class="immiPagiText">Showing 1 to 6 of 6 entries</div>
			  <ul class="pagination">
				<li><a href="#"> << </a></li>
				<li><a href="#">2</a></li>
				<li><a href="#">3</a></li>
				<li class="disabled"><a href="#">6</a></li>
				<li data-page="last"><a href="#"> >> </a></li>
			  </ul>
		</div> -->
		
		<!-- popcode start -->
		<form id ="residenceDetails_form" name = "residenceDetails_form" method = "Post">
		<div class="popUpOut" id="popResiView">
		<input type="hidden" id= "residenceId" name = "residenceId">
		<input type="hidden" id= "isCurrResi" value="N" name = "isCurrResi">
		<div class="popupHead">
			<h2>Add Residence</h2><a href="javascript:void(0)" class="fa fa-close popCan" onclick="refreshResidency();"></a>
		</div>
		
		<div class="popupMid clearfix">

          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Location<span class="text-danger">&nbsp;*</span></label>  
              	<div class="col-md-8 padding_right">
              		<input type="text" name= "location" id= "location" field-name= "location" placeholder="Location" onclick="initLocationAuto('location','country','state','city','zipcode','','','','streetAddress');" data-validation="required validate_Space validate_length length2-100" class="form-control" /> 
              		   <i class="fa fa-refresh btn btn-primary refresh-icon" title="Refresh" onClick="refreshGoogleApiLocation('location','country','state','city','zipcode','streetAddress');"></i>         		
				</div>
			</div>	
			<div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">StreetAddress</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" name= "streetAddress" id= "streetAddress" field-name= "streetAddress" placeholder="StreetAddress" class="form-control" /> 
              		             		
				</div>
			</div>
			
			<div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">City</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" name= "city" id= "city" field-name= "city" placeholder="City" class="form-control"/> 
              		             		
				</div>
			</div>
					
			<div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">State</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" name= "state" id= "state" field-name= "state" placeholder="State" class="form-control" /> 
              		             		
				</div>
			</div>
			
						          
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Country</label>
              	<div class="col-md-8 padding_right">
				 <input class="form-control" id="country" name="country" placeholder="Country" value="${loginCountryName}" type="text" ></input>

				</div>
			</div>
			
			<div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Zipcode</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" name= "zipcode" id= "zipcode" field-name= "zipcode" placeholder="Zipcode"  class="form-control" /> 
              		             		
				</div>
			</div>
			
           <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Start Date<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              	<div class="input-group errorInptgroup">
              		<input type="text" name = "residenceStartDate" id = "residenceStartDate" field-name = "Start Date" placeholder="Start Date" readonly="readonly" data-validation="required" class="form-control calendar"/>
				   	<span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer" onclick="clearDateValue('residenceStartDate');"></i></span>
				</div>
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix" id="resiEnddatediv">
				<label class="col-md-4 padding_left">End Date<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              	 <div class="input-group errorInptgroup">
              		<input type="text" name = "residenceEndDate" id = "residenceEndDate" field-Name = "End Date" placeholder="End Date" readonly="readonly" data-validation="required" class="form-control calendar" />
				  	<span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer" onclick="clearDateValue('residenceEndDate');"></i></span>
				</div>
			</div>	 								
		</div>
		<div class="clearfix"></div>
		</div>	
		<div class="immiPopFoot mt clearfix">
            <input value="Save" class="" type="button" id="residence_SaveButon" onclick="immigration.saveResidenceDetails('save');">
            <input value="Cancel" class="popCan" type="button" onclick="refreshResidency();">
          </div>		
        
       
	<!-- popcode code End -->
	</div>
	<div class="immFoot clearfix">
			<div class="pull-right text-right">
				<input type="button" value="Back" onclick="goToSummaryTab('#educationsTab');" class="btn btn-default btn-sm">
             	<!-- <input type="button" value="Skip" onclick="goToSummaryTab('#educationsTab');" class="btn btn-default btn-sm">  -->
              	<input type="button" value="Continue" onclick="goToSummaryNextTab('#familiesTab');" class="btn btn-primary btn-sm ml10">
            </div>
		</div>
		</form>
		
		    <script type="text/javascript">
		 var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		  var finalDateFormatEduSave = getJSDateFormat(locationDate);
		  
		$('#residenceStartDate').datepicker({
		      dateFormat: finalDateFormatEduSave,
		      changeMonth: true,
		  	  changeYear: true, 
		      onSelect: function (selected) {
		            //var dt = new Date(selected);
		            var dt = $(this).datepicker('getDate', '+1d');
		            dt.setDate(dt.getDate() + 1);
		            $("#residenceEndDate").datepicker("option", "minDate", dt);
		        }
		  });
		  
		  $('#residenceEndDate').datepicker({
		      dateFormat: finalDateFormatEduSave,
		      changeMonth: true,
		  	  changeYear: true, 
		        onSelect: function (selected) {
		            //var dt = new Date(selected);
		            var dt = $(this).datepicker('getDate', '-1d');
		            dt.setDate(dt.getDate() - 1);
		            $("#residenceStartDate").datepicker("option", "maxDate", dt);
		        }
		  });
		
</script>  
		
		
		<script>
			 // Add addResi Div  
			$("#addResi").click(function(){
				$(".popMask").fadeIn(200);
				$("#popResiView").fadeIn(100);
			});	
			 function refreshResidency(){
				 $(".popMask").fadeOut(200);
				$("#popResiView").fadeOut(100); 
				$("#residenceDetails_form")[0].reset();
				$("#residenceId").val('');
				$('#popResiView').find('p.jquery_form_error_message').remove();
				$("#residence_SaveButon").removeAttr("disabled");
			   }
			 
				$("#residenceSearch").keyup(function (e) {
			        if (e.which === 13) {
			        	immigration.getCandidateResidenceList(candidateId);
			        }
			    });
		</script>
		
<script>
  var residenceListCount = '<c:out value ="${residenceListCount}"/>';
  var initCount  = '<c:out value ="${requestScope.resiOnLoadCount}"/>';
 
  if(residenceListCount>0 || initCount!= ""){
        $('#residencefilled').show() // show and hides circle icon.
    }
	else {
        $('#residencefilled').hide()
	}
</script>