<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="immigraTabsHead">			  	
		  	<a href="javascript:void(0)" id="addPass"><i class="glyphicon glyphicon-plus"></i> Add Passport</a> 
		  	<span><input class="immigraTabSerach" type="text"></span>
		</div>
		
	  	<div class="immigraTable">
		  <table>
			<tbody>
			  <tr>
				<th>Passport Id</th>
				<th>Country</th>
				<th>Valid From</th>
				<th>Valid To</th>
				<th>&nbsp;</th>
			  </tr>
			  <c:forEach var="passportdetails" items="${PassportList}" varStatus="rows">
			  <tr>
				<td><div class="tbldata-dotdot-big">${passportdetails.idNumber}</div></td>
				<td><div class="tbldata-dotdot-me">India</div></td>
				<td><div class="tbldata-dotdot-me">${passportdetails.validFromDate}</div></td>
				<td><div class="tbldata-dotdot-me">${passportdetails.validToDate}</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>immMenuClick
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr>  
			  </c:forEach>			 				
			</tbody>
		  </table>
		</div>
		
		
		<!-- <div class="immigraPagi clearfix">
			<div class="immiPagiText">Showing 1 to 6 of 6 entries</div>
			  <ul class="pagination">
				<li><a href="#"> << </a></li>
				<li><a href="#">2</a></li>
				<li><a href="#">3</a></li>
				<li class="disabled"><a href="#">6</a></li>
				<li data-page="last"><a href="#"> >> </a></li>
			  </ul>
		</div> -->
		<!--popup code start -->
		<form id="CandidatePassportform" name="CandidatePassportform" method="post"> 
		<input type="hidden" id="Passport" name="Passport" value="Passport" >
		<div class="popUpOut" id="popPassView">
		
		<div class="popupHead">
			<h2>Add Passport</h2><a href="javascript:void(0)" class="fa fa-close popCan" onClick="popFun();"></a>
		</div>
		
		<div class="popupMid clearfix">
        
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Passport Id<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" name = "idNumber" id = "idNumber" field-name = "Passport Id" class="form-control" data-validation = "required"/>
				</div>
			</div>				          
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Country</label>
              	<div class="col-md-8 padding_right">
					<select class="chosen-select form-control">
					  <option>Select Country</option>
					  <option>India</option>
					  <option>USA</option>
					  <option>UK</option>
					</select>
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Valid From</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control calendar" value="" name = "validFrom" id = "validFrom" field-name = "Valid From"/>
				    <div class="clearDate" onClick="clearCandidateCertStartDate();"></div>
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Valid To</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control calendar" value="" name = "validTo" id = "validTo" field-name = "Valid To" />
				<div class="clearDate" onClick="clearCandidateCertEndDate();"></div>
				</div>
			</div>									
		</div>
		
		
		<div class="immiPopFoot">
            <input value="Save" class="" type="button" id="passport_save_id" onclick="immigration.savePassportVisaDetails('Passport')">
            <input value="Cancel" class="popCan" type="button" onClick="popFun();">
          </div>		
	</div><!--popcode end  -->
		</form>
		
		<script>
		 // Add addPass Div  
			$("#addPass").click(function(){
				$(".popMask").fadeIn(200);
				$("#popPassView").fadeIn(100);
			});	
			function popFun() {
				$(".popMask").fadeOut(200);
				$("#popPassView").fadeOut(200);
			}
		</script>
		
		
		