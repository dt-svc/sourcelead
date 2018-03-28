<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="immigraTabsHead">
		  	<a href="javascript:void(0)" id="addResi"><i class="glyphicon glyphicon-plus"></i> Add Residence</a> 
		  	<span><input class="immigraTabSerach" type="text"></span>
		</div>
		
	  	<div class="immigraTable">
		  <table>
			<tbody>
			  <tr>
				<th>Location</th>
				<th>Country</th>
				<th>From</th>
				<th>To</th>
				<th>Action</th>
				<th>&nbsp;</th>
			  </tr>
			  <c:forEach var="residitems" items="${candidatesResidenceList}" varStatus="rows"> 
			  
			  <tr>
				<td><div class="tbldata-dotdot-big">${residitems.location}</div></td>
				<td><div class="tbldata-dotdot-vsm">${residitems.country}</div></td>
				<td><div class="tbldata-dotdot-vsm">yyyy</div></td>
				<td><div class="tbldata-dotdot-vsm">llll</div></td>
				
				<td><div><i id="edit" onclick="editResidenceDEtailsByRowId(${residitems.id});"
				class="glyphicon glyphicon-pencil"></i><i id="delete" 
				 onclick="deleteResidenceDetailsById(${residitems.id});" 
				 class="glyphicon glyphicon-trash ml10"></i></div></td>
				
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
		<div class="popupHead">
			<h2>Add Residence</h2><a href="javascript:void(0)" class="fa fa-close popCan" onclick="refreshResidency();"></a>
		</div>
		
		<div class="popupMid clearfix">

          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Location<span class="text-danger">&nbsp;*</span></label>  
              	<div class="col-md-8 padding_right">
              		<input type="text" name= "location" id= "location" field-name= "location" onclick="initLocationAuto('location','country','state','city','zipcode','','','','streetAddress');" data-validation="required validate_Space validate_length length2-100" class="form-control" /> 
              		   <i class="fa fa-refresh btn btn-primary refresh-icon" title="Refresh" onClick="refreshGoogleApiLocation('location','country','state','city','zipcode','streetAddress');"></i>         		
				</div>
			</div>	
			<div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">StreetAddress</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" name= "streetAddress" id= "streetAddress" field-name= "streetAddress" class="form-control" /> 
              		             		
				</div>
			</div>
			
			<div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">City</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" name= "city" id= "city" field-name= "city" class="form-control"/> 
              		             		
				</div>
			</div>
					
			<div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">State</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" name= "state" id= "state" field-name= "state"  class="form-control" /> 
              		             		
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
              		<input type="text" name= "zipcode" id= "zipcode" field-name= "zipcode"  class="form-control" /> 
              		             		
				</div>
			</div>
			
           <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Start Date<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" name = "residenceStartDate" id = "residenceStartDate" field-name = "Start Date"  class="form-control calendar"/>
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">End Date<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" name = "residenceEndDate" id = "residenceEndDate" field-Name = "End Date"  class="form-control calendar" />
				</div>
			</div>	 								
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" class="" type="button" id="save_Buton" onclick="saveResidenceDetails('save');">
            <input value="Cancel" class="popCan" type="button" onclick="refreshResidency();">
          </div>		
        
       
	</div>	<!-- popcode code End -->
		</form>
		
		 <!--   <script type="text/javascript">
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
		
</script>   -->
		
		
		<script>
			 // Add addResi Div  
			$("#addResi").click(function(){
				$(".popMask").fadeIn(200);
				$("#popResiView").fadeIn(100);
			});	
			 function refreshResidency(){
				 $(".popMask").fadeOut(200);
				$("#popResiView").fadeOut(100); 
			 }
		</script>
		
		
		
		