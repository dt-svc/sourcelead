
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="/resources/js/immigration.js?staticTime=${staticTime}"></script>
<c:set var = "lcaDTO" scope = "page" value = '<%= session.getAttribute("lcaDTO")%>'/>
<c:set var="docRowIndex" value="0" scope="page" /><!-- used for dynamic row index  rowindex starts from 0-->
<c:set var="rowIndexPlempt" value="0" scope="page" />
<!-- Content Area -->
<section class="content-wraper">
  <section class="content-header clearfix">
      <div class="pull-left positionRelative"><h2>Add LCA</h2>
    </div>
    <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;<a href="/app/immigration/viewImmigrationList">Petition List</a>&raquo;  <a href="/app/immigration/viewLCAList">LCA List</a>&raquo; Add LCA</div>
  </section>
  <div class="content-area content-new clearfix">
  
  <form id="lcaDetailsForm" name="lcaDetailsForm">
            <c:if test="${empty lcaDTO}">
			<input type = "hidden" id="editMode" name = "editMode" value = "false">
			</c:if>
			<c:if test="${!empty lcaDTO}">
			<input type = "hidden" id="editMode" name = "editMode" value = "true">
			</c:if>
			
  	<input type="hidden" id="lcaId" name="lcaId" value="${lcaDTO.lcaId}">
  	<input type="hidden" id="visaClsfId" name="visaClsfId" value="${lcaDTO.visaClsfId}">
    <div class="row clearfix mt" >
         
         <div class="col-xs-12 col-sm-6 col-md-6 minHeight50 mb" id="errLcaNoDiv">
            <label class="col-md-4">LCA No<span class="text-danger paddingLeft">*</span></label>
            <div class="col-md-8">
                <input type="text" name="lcaNo"  id="lcaNo" class="form-control" value="${lcaDTO.lcaNo}" placeholder="LCA Number" field-name="LCA NO"
								data-validation="required validate_LcaNum validate_length length1-30" onkeyup="validateLcaNumber();" onblur="validateLcaNumber();">
          	</div>
        </div>
          
           <div class="col-xs-12 col-sm-6 col-md-6 minHeight50 mb" id="errLcaTitleDiv">
            <label class="col-md-4">LCA Title<span class="text-danger paddingLeft">*</span></label>
            <div class="col-md-8">
                <input type="text" name="lcaTitle" id="lcaTitle" class="form-control" value="${lcaDTO.lcaTitle}" placeholder="LCA Title" field-name="LCA Title"
								data-validation="required validate_AlphaNumber validate_length length1-250" onkeyup="validateLcaTitle();" onblur="validateLcaTitle();">
          	</div>
          </div>
           <div class="col-xs-12 col-sm-6 col-md-6 minHeight50 mb">
            <label class="col-md-4">LCA Status<span class="text-danger paddingLeft">*</span></label>
            <div class="col-md-8">
               <select class="form-control sel" name="lcaStatus" id="lcaStatus" field-name="LCA Status" data-validation="required">
                <option value=""> --Select--</option>
                <option ${lcaDTO.lcaStatus == 'Initiated' ? 'selected': ''} value='Initiated'>Initiated</option>
                <option ${lcaDTO.lcaStatus == 'In progress' ? 'selected': ''} value='In progress'>In progress</option>
                <option ${lcaDTO.lcaStatus == 'Certified' ? 'selected': ''} value='Certified'>Certified</option>
                <option ${lcaDTO.lcaStatus == 'Denied' ? 'selected': ''}  value='Denied'>Denied</option>
                <option ${lcaDTO.lcaStatus == 'Withdrawn' ? 'selected': ''} value='Withdrawn'>Withdrawn</option>
                <option ${lcaDTO.lcaStatus == 'Voided' ? 'selected': ''} value='Voided'>Voided</option>
                <option ${lcaDTO.lcaStatus == 'Invalidated' ? 'selected': ''} value='Invalidated'>Invalidated</option>
                <option ${lcaDTO.lcaStatus == 'Rejected' ? 'selected': ''} value='Rejected'>Rejected</option>
                <option ${lcaDTO.lcaStatus == 'Expired' ? 'selected': ''} value='Expired'>Expired</option>
              </select>
          	</div>
          </div>
           <div class="col-xs-12 col-sm-6 col-md-6 minHeight50 mb">
            <label class="col-md-4">Status<span class="text-danger paddingLeft">*</span></label>
            <div class="col-md-8">
               <select class="form-control sel" name="status" id="status" field-name="Status" data-validation="required">
                 <option value=""> --Select-- </option>
                <option ${lcaDTO.status == '1' ? 'selected': ''} value='Active'>Active</option>
                <option ${lcaDTO.status == '0' ? 'selected': ''} value='In-active'>In-active</option>
              </select>
          	</div>
          </div>
           <div class="form-group col-xs-12 col-sm-6 col-md-6 minHeight50 mb">
            <label class="col-md-4">Start Date<span class="text-danger paddingLeft">*</span></label>
            <div class="col-md-8">
             <div class="input-group errorInptgroup" id="lcaStartDateDiv">
				<input type="text" name="startDate" id="startDate" readonly='true' value="${lcaDTO.startDate}" class="form-control calendar" placeholder="Start Date" field-name="Start Date" data-validation="required">
				 <span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer" onclick="clearLcaDateValue('startDate');"></i></span>
          	</div>
          	</div>
          </div>
          
           <div class="form-group col-xs-12 col-sm-6 col-md-6 minHeight50 mb">
            <label class="col-md-4">End Date<span class="text-danger paddingLeft">*</span></label>
            <div class="col-md-8">
             <div class="input-group errorInptgroup" id="lcaEndDateDiv">
				<input type="text" id="endDate" name="endDate" value="${lcaDTO.endDate}" readonly='true' class="form-control calendar" placeholder="End Date" field-name="End Date" data-validation="required">
				<span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer" onclick="clearLcaDateValue('endDate');"></i></span>
			</div>
          	</div>
          </div>
           
          <div class="col-xs-12 col-sm-6 col-md-6 minHeight50 mb">
            <label class="col-md-4 paddingRight">No.of Positions<span class="text-danger paddingLeft">*</span></label>
            <div class="col-md-8">
				<input type="text" name="noOfPositions" id="noOfPositions" value="${lcaDTO.noOfPositions}" class="form-control" placeholder="No Of Positions" field-name="No.of Positions" data-validation="required validate_intFromOne validate_length length1-3">
          	</div>
          </div>
          
		  <div class="clearfix"></div>
          <div class="col-xs-12 col-sm-12 col-md-12 mb">
          <h5 class="col-xs-12 col-sm-12 col-md-12">Visa Classifications</h5>
          </div>
          <div class="clearfix"></div>
          
          <div class="clearfix col-xs-12 col-sm-6 col-md-4 mb">
          	<div class="col-md-7">
          		<div class="pull-left">
					<input id="newEmployment" class="checkbox-custom fmcheckbox" name="newEmployment" value="${lcaDTO.newEmployment}" type="checkbox" ${lcaDTO.newEmployment == 1 ? 'checked' : '' }>
					<label for="newEmployment" class="checkbox-custom-label fmcheckbox-lable" onclick="newEmployment();"></label>
					<span class="ml5 mt2 pull-left">New Employment</span>
				</div>
          	</div>
          <div class="col-md-5 padding_left">
          		<input type="text"  name="newEmploymentCount" id="newEmploymentCount" value="${lcaDTO.newEmploymentCount}" class="form-control input-sm" 
          		onkeyup="validateNumber(this);"  field-name="Count" placeholder="Enter Count" readonly>
          	</div>

          </div>
          
          <div class="clearfix col-xs-12 col-sm-6 col-md-4 mb">
          	<div class="col-md-8">
          		<div class="pull-left">
					<input id="concurrentEmployment" class="checkbox-custom fmcheckbox" name="concurrentEmployment" value="${lcaDTO.concurrentEmployment}" type="checkbox" ${lcaDTO.concurrentEmployment == 1 ? 'checked' : '' }>
					<label for="concurrentEmployment" class="checkbox-custom-label fmcheckbox-lable" onclick="concurrentEmployment()"></label>
					<span class="ml5 mt2 pull-left">Concurrent Employment</span>
				</div>
          	</div>
          	<div class="col-md-4 padding_left">
          		<input type="text" name="concurrentEmploymentCount" value="${lcaDTO.concurrentEmploymentCount}" id="concurrentEmploymentCount" class="form-control input-sm" 
          		onkeyup="validateNumber(this);" field-name="Count"  placeholder="Enter Count" readonly>
          	</div>

          </div>
          <div class="clearfix col-xs-12 col-sm-6 col-md-4 mb">
          	<div class="col-md-7">
          		<div class="pull-left">
					<input id="extention" class="checkbox-custom fmcheckbox" name="extention"  value="${lcaDTO.extention}" type="checkbox" ${lcaDTO.extention == 1 ? 'checked' : '' }>
					<label for="extention" class="checkbox-custom-label fmcheckbox-lable" onclick="extention();"></label>
					<span class="ml5 mt2 pull-left">Extension</span>
				</div>
          	</div>
          	<div class="col-md-5 padding_left">
          		<input type="text" name="extentionCount" id="extentionCount" value="${lcaDTO.extentionCount}" class="form-control input-sm" 
          		onkeyup="validateNumber(this);" field-name="Count" placeholder="Enter Count" readonly>
          	</div>

          </div>
          <div class="clearfix"></div>
          <div class="clearfix col-xs-12 col-sm-6 col-md-4 mb">
          	<div class="col-md-7">
          		<div class="pull-left">
					<input id="changeEmployer" class="checkbox-custom fmcheckbox" name="changeInEmployer" value="${lcaDTO.changeInEmployer}" type="checkbox" ${lcaDTO.changeInEmployer == 1 ? 'checked' : '' }>
					<label for="changeEmployer" class="checkbox-custom-label fmcheckbox-lable" onclick="changEmployer()"></label>
					<span class="ml5 mt2 pull-left">Change in Employer</span>
				</div>
          	</div>
          	<div class="col-md-5 padding_left">
          		<input type="text" name="changeInEmployerCount" id="changeInEmployerCount" value="${lcaDTO.changeInEmployerCount}" class="form-control input-sm" 
          		onkeyup="validateNumber(this);" field-name="Count" placeholder="Enter Count" readonly>
          	</div>
          </div>
          <div class="clearfix col-xs-12 col-sm-6 col-md-4 mb">
          	<div class="col-md-8">
          		<div class="pull-left">
					<input id="changeEmployerPrevious" class="checkbox-custom fmcheckbox" name="changeInPreApproEmployment" value="${lcaDTO.changeInPreApproEmployment}" type="checkbox" ${lcaDTO.changeInPreApproEmployment == 1 ? 'checked' : '' }>
					<label for="changeEmployerPrevious" class="checkbox-custom-label fmcheckbox-lable" onclick="approvedEmployment();"></label>
					<span class="ml5 pull-left">Change in Previous<br> Approved Employment</span>
				</div>
          	</div>
          	<div class="col-md-4 padding_left">
          		<input type="text" name="changeInPreApproEmplmtCount" id="changeEmployerPreviousCount" value="${lcaDTO.changeInPreApproEmplmtCount}" class="form-control input-sm" 
          		onkeyup="validateNumber(this);" field-name="Count" placeholder="Enter Count" readonly>
          	</div>
          </div>
          <div class="clearfix col-xs-12 col-sm-6 col-md-4 mb">
          	<div class="col-md-7">
          		<div class="pull-left">
					<input id="amendment" class="checkbox-custom fmcheckbox" name="amendment" value="${lcaDTO.amendment}" type="checkbox" ${lcaDTO.amendment == 1 ? 'checked' : '' }>
					<label for="amendment" class="checkbox-custom-label fmcheckbox-lable" onclick="amendment()"></label>
					<span class="ml5 mt2 pull-left">Amendment</span>
				</div>
          	</div>
          	<div class="col-md-5 padding_left">
          		<input type="text" name="amendmentCount" id="amendmentCount" value="${lcaDTO.amendmentCount}" class="form-control input-sm" 
          		onkeyup="validateNumber(this);" field-name="Count" placeholder="Enter Count" readonly>
          	</div>

          </div>
          <div class="clearfix"></div>
          <div class="col-xs-12 col-sm-6 col-md-6 minHeight50 mb mt">
            <label class="col-md-4">Job Title<span class="text-danger paddingLeft">*</span></label>
            <div class="col-md-8">
				<input type="text" name="jobTitle" id="jobTitle" value="${lcaDTO.jobTitle}" class="form-control" placeholder="Job Title" field-name="Job Title" data-validation="required validate_length length1-250">
          	</div>
          </div>
          
          <div class="col-xs-12 col-sm-6 col-md-6 minHeight50 mb mt">
            <label class="col-md-4">SOC Code<span class="text-danger paddingLeft">*</span></label>
            <div class="col-md-8">
				<input type="text" name="socCode" id="socCode" value="${lcaDTO.socCode}" class="form-control" placeholder="SOC Code" field-name="SOC Code" data-validation="required">
          	</div>
          </div>
          
          <div class="col-xs-12 col-sm-6 col-md-6 minHeight50 mb">
            <label class="col-md-4 paddingRight">SOC Title<span class="text-danger paddingLeft">*</span></label>
            <div class="col-md-8">
				<input type="text" name="socOccupationTitle" value="${lcaDTO.socOccupationTitle}" id="socOccupationTitle" class="form-control" placeholder="SOC Occupation Title" field-name="SOC Occupation Title" data-validation="required">
          	</div>
          </div>
          
          <div class="col-xs-12 col-sm-6 col-md-6 minHeight50 mb">
            <label class="col-md-4 paddingRight">Offered Wage</label>
            <div class="col-md-8 ">
            <div class="input-group er ratetype">
                <div class="input-group-btn">
                  <select class="form-control selectpicker" id="offeredWageCurrency" name="offeredWageCurrency" data-style="btn-primary">
										<option value="INR" ${offeredWageCurrency == 'INR' ? 'selected' : ''}>INR</option>
										<option value="AUD" ${offeredWageCurrency == 'AUD' ? 'selected' : ''}>AUD</option>
										<option value="USD" ${offeredWageCurrency == 'USD' ? 'selected' : ''}>USD</option>
										<option value="GBP" ${offeredWageCurrency == 'GBP' ? 'selected' : ''}>GBP</option>
										<option value="SGD" ${offeredWageCurrency == 'SGD' ? 'selected' : ''}>SGD</option>
										<option value="EUR" ${offeredWageCurrency == 'EUR' ? 'selected' : ''}>EUR</option> 
									</select>
                </div>
                <input type="text" id="offeredWage" name="offeredWage" value="${lcaDTO.offeredWage}" class="form-control" value="" field-name="Offered Wage"
								data-validation="validate_Space validate_float validate_length length1-8" data-validation-optional="true"
								 placeholder="Wage Or Rate">
                <div class="input-group-btn ratetype">
                 <select class="selectpicker form-control" id="offeredWageRateType" name="offeredWageRateType" field-name="Rate Per" data-style="btn-primary">
						 <option value="">Rate Per</option>
						<option ${lcaDTO.offeredWageRateType == 'Hourly' ? 'selected' : ''} value="Hourly">Hourly</option>
						<option ${lcaDTO.offeredWageRateType == 'Weekly' ? 'selected' : ''} value="Weekly">Weekly</option>
						<option ${lcaDTO.offeredWageRateType == 'Bi-Weekly' ? 'selected' : ''} value="Bi-Weekly">Bi-Weekly</option>
						<option ${lcaDTO.offeredWageRateType == 'Semi-Monthly' ? 'selected' : ''} value="Semi-Monthly">Semi-Monthly</option>
						<option ${lcaDTO.offeredWageRateType == 'Monthly' ? 'selected' : ''} value="Monthly">Monthly</option>
						<option ${lcaDTO.offeredWageRateType == 'Bi-Monthly' ? 'selected' : ''} value="Bi-Monthly">Bi-Monthly</option>
						<option ${lcaDTO.offeredWageRateType == 'Yearly' ? 'selected' : ''} value="Yearly">Yearly</option> 
					</select>
                </div>
              </div>
            
          	</div>
          </div>
          
          <div class="clearfix"></div>
          <div class="col-xs-12 col-sm-12 col-md-12 mb">
          <h5 class="col-xs-8 col-sm-8 col-md-8">Place of Employment <span class="text-danger paddingLeft">*</span></h5>
          <c:if test="${empty lcaDTO.lcaPlaceOfEmploymentList}">
          <div class="col-xs-4 col-sm-4 col-md-4">
         		<div class="pull-right">
          		<button type="button" class="btn btn-primary btn-sm pull-right"  id="placeEmployMntId" onclick="appendDynamicPlaceofEmploymentDiv();"><i class="fa fa-plus paddingRight"></i>Add</button>
          		<div class="clearfix"></div>
          		</div>
          </div>
          </c:if>
          <c:if test="${!empty lcaDTO.lcaPlaceOfEmploymentList}">
          <div class="col-xs-4 col-sm-4 col-md-4">
         		<div class="pull-right">
          		<button type="button" class="btn btn-primary btn-sm pull-right"  id="placeEmployMntId" onclick="appendDynamicPlaceofEmploymentDiv();"><i class="fa fa-plus paddingRight"></i>Add</button>
          		<div class="clearfix"></div>
          		</div>
          </div>
          </c:if>
          </div>
          <div class="clearfix"></div>
          
         <div class="clearfix">
          <c:if test="${empty lcaDTO.lcaPlaceOfEmploymentList}">
          <input type="hidden" name="lcaPlaceOfEmploymentList[0].plsempmntId" value="">
	          <div class="col-xs-12 col-sm-6 col-md-6 minHeight50 mb">
	              <label class="col-md-4">Location<span class="text-danger paddingLeft">*</span></label>
	              <div class="col-md-8">
	                  <div class="positionRelative">
									<input class="form-control positionRelative"
										id="location" name="lcaPlaceOfEmploymentList[0].location" value="${lcaDTO.lcaPlaceOfEmploymentList[0].location}" onclick="initLocationAuto('location','country','state','city','zipCode','','','','addressLineOne','addressLine2');"
										placeholder="Enter Location" type="text" field-name="Location" data-validation="required"></input> <i
										class="fa fa-refresh btn btn-primary refresh-icon"
										title="Refresh"
										onClick="refreshGoogleApiLocation('location','country','state','city','zipCode','addressLineOne','addressLine2');"></i>
						</div>
	              </div>
	            </div>
         
				<div class="col-xs-12 col-sm-6 col-md-6 minHeight50 mb">
					<label class="col-md-4">Address Line 1</label>
					<div class="col-md-8">
					<input type="text" name="lcaPlaceOfEmploymentList[0].addressLine1" id="addressLineOne"  value="${lcaDTO.lcaPlaceOfEmploymentList[0].addressLine1}" class="form-control" placeholder="Address Line 1" field-name="Address Line1"
								data-validation="validate_Space validate_length length1-250"
								data-validation-optional="true">
					</div>
				</div>
				
				<div class="col-xs-12 col-sm-6 col-md-6 minHeight50 mb">
					<label class="col-md-4">Address Line 2</label>
					<div class="col-md-8">
					<input type="text" name="lcaPlaceOfEmploymentList[0].addressLine2" id="addressLine2" value="${lcaDTO.lcaPlaceOfEmploymentList[0].addressLine2}" class="form-control" placeholder="Address Line 2" field-name="Address Line2"
								data-validation="validate_Space validate_length length1-250"
								data-validation-optional="true">
					</div>
				</div>
				
				<div class="col-xs-12 col-sm-6 col-md-6 minHeight50 mb">
					<label class="col-md-4">City<span class="text-danger paddingLeft">*</span></label>
					<div class="col-md-8">
					<input type="text" name="lcaPlaceOfEmploymentList[0].city" id="city" value="${lcaDTO.lcaPlaceOfEmploymentList[0].city}" class="form-control" placeholder="City" field-name="City"
								data-validation="required validate_length length2-100">
					</div>
				</div>
				
				<div class="col-xs-12 col-sm-6 col-md-6 minHeight50 mb">
					<label class="col-md-4">State<span class="text-danger paddingLeft">*</span></label>
					<div class="col-md-8">
					<input type="text" name="lcaPlaceOfEmploymentList[0].state" id="state" value="${lcaDTO.lcaPlaceOfEmploymentList[0].state}" class="form-control" placeholder="State" field-name="State"
								data-validation="required validate_length length2-100">
					</div>
				</div>
				
				<div class="col-xs-12 col-sm-6 col-md-6 minHeight50 mb">
					<label class="col-md-4">Country<span class="text-danger paddingLeft">*</span></label>
					<div class="col-md-8">
					<input type="text" name="lcaPlaceOfEmploymentList[0].country" id="country" value="${lcaDTO.lcaPlaceOfEmploymentList[0].country == null ? ''  : lcaDTO.lcaPlaceOfEmploymentList[0].country}"  class="form-control" placeholder="Country" field-name="Country"
								data-validation="required validate_length length2-100">
					</div>
				</div>
				
				
				<div class="col-xs-12 col-sm-6 col-md-6 minHeight50 mb">
					<label class="col-md-4">Zip Code</label>
					<div class="col-md-8">
					<input type="text" name="lcaPlaceOfEmploymentList[0].zipCode" id="zipCode" value="${lcaDTO.lcaPlaceOfEmploymentList[0].zipCode}" class="form-control" placeholder="Zip Code"
					field-name="Zip Code" data-validation="validate_int validate_length length2-8" data-validation-optional="true">
					</div>
				</div>
				</c:if>
				
		 <c:if test="${!empty lcaDTO.lcaPlaceOfEmploymentList}">
		  
          <c:forEach var='lcaPlaceOfEmployment' items="${lcaDTO.lcaPlaceOfEmploymentList}" varStatus="rows">
          
          	<c:set var="rowIndexPlempt" value="${rows.count-1}" scope="page"/>
          	<div id ='dynamicPlsofEmpmtDiv${rows.count-1}'>
          		<input type="hidden" name="lcaPlaceOfEmploymentList[${rows.count-1}].plsempmntId" id="plsempmntId${rows.count-1}" value="${lcaDTO.lcaPlaceOfEmploymentList[rows.count-1].plsempmntId}">
          	<c:if test = "${rows.count >1}">
          	 <div class="clearfix col-md-12 mb">
				<div class='pull-right col-md-6'>
				 <button type='button' class='btn btn-default pull-right' onclick='removeDynamicPlsofEmpmtDiv(${rows.count-1})'>
				 <i class ='fa fa-remove text-danger paddingRight'></i>Remove</button>
				</div>
			</div>
			</c:if>
          	<!-- rows start -->
          	
          	 <div class="col-xs-12 col-sm-6 col-md-6 minHeight50 mb">
	              <label class="col-md-4">Location<span class="text-danger paddingLeft">*</span></label>
	              <div class="col-md-8">
	                  <div class="positionRelative">
									<input class="form-control positionRelative"
										id="location${rows.count-1}" name="lcaPlaceOfEmploymentList[${rows.count-1}].location" value="${lcaDTO.lcaPlaceOfEmploymentList[rows.count-1].location}" onclick="initLocationAuto('location${rows.count-1}','country${rows.count-1}','state${rows.count-1}','city${rows.count-1}','zipCode${rows.count-1}','','','','addressLineOne${rows.count-1}','addressLine2${rows.count-1}');"
										placeholder="Enter Location" type="text" field-name="Location" data-validation="required"></input> <i
										class="fa fa-refresh btn btn-primary refresh-icon"
										title="Refresh"
										onClick="refreshGoogleApiLocation('location${rows.count-1}','country${rows.count-1}','state${rows.count-1}','city${rows.count-1}','zipCode${rows.count-1}','addressLineOne${rows.count-1}','addressLine2${rows.count-1}');"></i>
						</div>
	              </div>
	            </div>
         
				<div class="col-xs-12 col-sm-6 col-md-6 minHeight50 mb">
					<label class="col-md-4">Address Line 1</label>
					<div class="col-md-8">
					<input type="text" name="lcaPlaceOfEmploymentList[${rows.count-1}].addressLine1" id="addressLineOne${rows.count-1}"  value="${lcaDTO.lcaPlaceOfEmploymentList[rows.count-1].addressLine1}" class="form-control" placeholder="Address Line 1" field-name="Address Line1"
								data-validation="validate_Space validate_length length1-250"
								data-validation-optional="true">
					</div>
				</div>
				
				<div class="col-xs-12 col-sm-6 col-md-6 minHeight50 mb">
					<label class="col-md-4">Address Line 2</label>
					<div class="col-md-8">
					<input type="text" name="lcaPlaceOfEmploymentList[${rows.count-1}].addressLine2" id="addressLine2${rows.count-1}" value="${lcaDTO.lcaPlaceOfEmploymentList[rows.count-1].addressLine2}" class="form-control" placeholder="Address Line 2" field-name="Address Line2"
								data-validation="validate_Space validate_length length1-250"
								data-validation-optional="true">
					</div>
				</div>
				
				<div class="col-xs-12 col-sm-6 col-md-6 minHeight50 mb">
					<label class="col-md-4">City<span class="text-danger paddingLeft">*</span></label>
					<div class="col-md-8">
					<input type="text" name="lcaPlaceOfEmploymentList[${rows.count-1}].city" id="city${rows.count-1}" value="${lcaDTO.lcaPlaceOfEmploymentList[rows.count-1].city}" class="form-control" placeholder="City" field-name="City"
								data-validation="required validate_length length2-100">
					</div>
				</div>
				
				<div class="col-xs-12 col-sm-6 col-md-6 minHeight50 mb">
					<label class="col-md-4">State<span class="text-danger paddingLeft">*</span></label>
					<div class="col-md-8">
					<input type="text" name="lcaPlaceOfEmploymentList[${rows.count-1}].state" id="state${rows.count-1}" value="${lcaDTO.lcaPlaceOfEmploymentList[rows.count-1].state}" class="form-control" placeholder="State" field-name="State"
								data-validation="required validate_length length2-100">
					</div>
				</div>
				
				<div class="col-xs-12 col-sm-6 col-md-6 minHeight50 mb">
					<label class="col-md-4">Country<span class="text-danger paddingLeft">*</span></label>
					<div class="col-md-8">
					<input type="text" name="lcaPlaceOfEmploymentList[${rows.count-1}].country" id="country${rows.count-1}" value="${lcaDTO.lcaPlaceOfEmploymentList[rows.count-1].country == null ? loginCountryName  : lcaDTO.lcaPlaceOfEmploymentList[rows.count-1].country}"  class="form-control" placeholder="Country" field-name="Country"
								data-validation="required validate_length length2-100">
					</div>
				</div>
				
				
				<div class="col-xs-12 col-sm-6 col-md-6 minHeight50 mb">
					<label class="col-md-4">Zip Code</label>
					<div class="col-md-8">
					<input type="text" name="lcaPlaceOfEmploymentList[${rows.count-1}].zipCode" id="zipCode${rows.count-1}" value="${lcaDTO.lcaPlaceOfEmploymentList[rows.count-1].zipCode}" class="form-control" placeholder="Zip Code"
					field-name="Zip Code" data-validation="validate_int validate_length length2-8" data-validation-optional="true">
					</div>
				</div>
				<!-- rows end -->
          	
          	</div>
          
          </c:forEach>
          </c:if>
         </div>
         
         
         <div id ="dynamicPlaceofEmploymentDiv" ><!-- PlaceofEmployment dynamic div start -->
          
         </div> <!--  PlaceofEmployment dynamic div end -->
         
          <div class="clearfix"></div>
          <div class="col-xs-12 col-sm-12 col-md-12 mb">
          <h5 class="col-xs-8 col-sm-12 col-md-12">Prevailing  Wage</h5>
          </div>
          <div class="clearfix"></div>
         
         
         <div class="col-xs-12 col-sm-6 col-md-6 minHeight50 mb">
            <label class="col-md-4">Wage Level<span class="text-danger paddingLeft">*</span></label>
            <div class="col-md-4">
               <select class="form-control sel" name="wagesLevel" id="wageLevel" field-name="Wage Level" data-validation="required">
               <option value=""> --Select-- </option>
                <option ${lcaDTO.wagesLevel == 'Level 1' ? 'selected' : ''} value="Level 1">Level 1</option>
                <option ${lcaDTO.wagesLevel == 'Level 2' ? 'selected' : ''} value="Level 2">Level 2</option>
                <option ${lcaDTO.wagesLevel == 'Level 3' ? 'selected' : ''} value="Level 3">Level 3</option>
                <option ${lcaDTO.wagesLevel == 'Level 4' ? 'selected' : ''} value="Level 4">Level 4</option>
              </select>
          	</div>
            <div class="col-md-4">
            	<button type="button" class="btn btn-primary">Get prevailing</button>
          	</div>
          </div>
         
           <div class="col-xs-12 col-sm-6 col-md-6 minHeight50 mb">
            <label class="col-md-4 paddingRight">Prevailing  Wage <span class="text-danger paddingLeft">*</span> </label>
            <div class="col-md-8">
            <div class="input-group er ratetype">
                <div class="input-group-btn">
                   <select class="form-control selectpicker" id="privilingWageCurrency" name="privilingWageCurrency" data-style="btn-primary">
						<option value="INR" ${privilingWageCurrency == 'INR' ? 'selected' : ''}>INR</option>
						<option value="AUD" ${privilingWageCurrency == 'AUD' ? 'selected' : ''}>AUD</option>
						<option value="USD" ${privilingWageCurrency == 'USD' ? 'selected' : ''}>USD</option>
						<option value="GBP" ${privilingWageCurrency == 'GBP' ? 'selected' : ''}>GBP</option>
						<option value="SGD" ${privilingWageCurrency == 'SGD' ? 'selected' : ''}>SGD</option>
						<option value="EUR" ${privilingWageCurrency == 'EUR' ? 'selected' : ''}>EUR</option> 
					</select>
                </div>
                <input type="text" name="prevailingWage" id="prevailingWage" value="${lcaDTO.prevailingWage}" class="form-control" value="" field-name="Prevailing Wage"
								data-validation="required validate_Space validate_float validate_length length1-8" 
							    placeholder="Wage Or Rate">
                <div class="input-group-btn ratetype">
                 <select class="selectpicker form-control" id="privilingWageRateType" name="privilingWageRateType" field-name="Rate Per" data-style="btn-primary">
						 <option  value="">Rate Per</option>
						<option ${lcaDTO.privilingWageRateType == 'Hourly' ? 'selected' : ''} value="Hourly">Hourly</option>
						<option ${lcaDTO.privilingWageRateType == 'Weekly' ? 'selected' : ''} value="Weekly">Weekly</option>
						<option ${lcaDTO.privilingWageRateType == 'Bi-Weekly' ? 'selected' : ''} value="Bi-Weekly">Bi-Weekly</option>
						<option ${lcaDTO.privilingWageRateType == 'Semi-Monthly' ? 'selected' : ''} value="Semi-Monthly">Semi-Monthly</option>
						<option ${lcaDTO.privilingWageRateType == 'Monthly' ? 'selected' : ''} value="Monthly">Monthly</option>
						<option ${lcaDTO.privilingWageRateType == 'Bi-Monthly' ? 'selected' : ''} value="Bi-Monthly">Bi-Monthly</option>
						<option ${lcaDTO.privilingWageRateType == 'Yearly' ? 'selected' : ''} value="Yearly">Yearly</option> 
					</select>
                </div>
              </div>
            
          	</div>
          </div>
          
         
          
         <!-- Attachment Add Row  -->
         
         <div class="clearfix"></div>
          <div class="col-xs-12 col-sm-12 col-md-12 mb">
          <h5 class="col-xs-8 col-sm-8 col-md-8">Attachment</h5>
          <div class="col-xs-4 col-sm-4 col-md-4">
         		<div class="pull-right">
          		<button type="button" class="btn btn-primary btn-sm pull-right" onclick="appendDynamicAttachmentDiv();"><i class="fa fa-plus paddingRight"></i>Add</button>
          		<div class="clearfix"></div>
          		</div>
          </div>
          </div>
          <div class="clearfix">
          <c:if test="${empty lcaDTO.lcaDocumentList}">
          <input type="hidden" name="lcaDocumentList[0].docId" value="">
        	<div class="col-md-4 mb">
        		<label class="col-md-12">Title</label>
        		<div class="col-md-12">
        			<input type="text" name="lcaDocumentList[0].documentTitle" id="documentTitle" value="${lcaDTO.lcaDocumentList[0].documentTitle}" class="form-control" placeholder="Document Title" field-name="Document Title" data-validation-optional="true" data-validation="validate_Space validate_length length2-250">
        		</div>
        	</div>
        	
        	<div class="col-md-4 mb">
        		<label class="col-md-12">Attachment</label>
        		<div class="col-md-12">
					<div class="input-group">
					<label class="input-group-btn">
						<span class="btn btn-primary" onchange="uploadLCADocs(0);"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
						<input type="file" style="display: none;" onchange="uploadBtnFile(0);"  id="uploadBtn0" class="upload">
						</span>
					</label>
					<input type="text" class="form-control" name="lcaDocumentList[0].fileInfo[0].docFile" value="${lcaDTO.lcaDocumentList[0].fileName}" readonly id="uploadFile0" placeholder="Files are not selected..." field-name="Upload Document" data-validation-optional="true" data-validation="validatedoc">
					</div>
        		</div>
        	</div>
        	
        	<div class="col-md-4 mb">
        		<label class="col-md-12">Comment</label>
        		<div class="col-md-12">
					<textarea class="form-control" name="lcaDocumentList[0].description" data-validation="validate_Space validate_length length1-500" data-validation-optional="true"  id="description" placeholder="Comment" rows="2">${lcaDTO.lcaDocumentList[0].description}</textarea>
        		</div>
        	</div>
        	
        	</div>
        	  <div class="clearfix"></div>
        	 </c:if> 
        	 
          <c:if test="${!empty lcaDTO.lcaDocumentList}">
          
          <c:forEach var='lcaAttachment' items="${lcaDTO.lcaDocumentList}" varStatus="rows">
         	
          <c:set var="docRowIndex" value="${rows.count-1}" scope="page"/>
          
           <div id ='dynamicAttachmentDiv${rows.count-1}'>
           <input type="hidden" name="lcaDocumentList[${rows.count-1}].docId" id="docId${rows.count-1}" value="${lcaDTO.lcaDocumentList[rows.count-1].docId}">
           <c:if test = "${rows.count >1}">
        	  	  <div class="clearfix col-md-12">
				<div class='pull-right col-md-6'>
				 <button type='button' class='btn btn-default pull-right' onclick='removeDynamicAttachmentDiv(${rows.count-1})'>
				 <i class ='fa fa-remove text-danger paddingRight'></i>Remove</button>
				</div>
				</div>
			
				</c:if>
        	<div class="col-md-4 mb">
        		<label class="col-md-12">Title</label>
        		<div class="col-md-12">
        			<input type="text" name="lcaDocumentList[${rows.count-1}].documentTitle" id="documentTitle${rows.count-1}" value="${lcaDTO.lcaDocumentList[rows.count-1].documentTitle}" class="form-control" placeholder="Document Title" field-name="Document Title" data-validation="validate_Space validate_length length2-250">
        		</div>
        	</div>
        	
        	<div class="col-md-4 mb">
        		<label class="col-md-12">Attachment</label>
        		<div class="col-md-12">
					<div class="input-group">
					<label class="input-group-btn">
						<span class="btn btn-primary" onchange="uploadLCADocs(${rows.count-1});"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
						<input type="file" style="display: none;" onchange="uploadBtnFile(${rows.count-1});"  id="uploadBtn${rows.count-1}" class="upload">
						</span>
					</label>
					<input type="text" class="form-control" name="lcaDocumentList[${rows.count-1}].fileInfo[0].docFile" value="${lcaDTO.lcaDocumentList[rows.count-1].fileName}" readonly id="uploadFile${rows.count-1}" placeholder="Files are not selected..." field-name="Upload Document" data-validation="validatedoc">
					</div>
        		</div>
        	</div>
        	
        	<div class="col-md-4 mb">
        		<label class="col-md-12">Comment</label>
        		<div class="col-md-12">
					<textarea class="form-control" name="lcaDocumentList[${rows.count-1}].description"  id="description${rows.count-1}" placeholder="Comment" rows="2" data-validation="validate_Space validate_length length1-500" data-validation-optional="true">${lcaDTO.lcaDocumentList[rows.count-1].description}</textarea>
        		</div>
        	</div>
        	
        	</div>
        	 
        	  </c:forEach>
        	 </c:if> 
        	 <div id ="appendDynamicAttachmentDiv" ><!-- Attachment dynamic div start -->
          
             </div> <!--  Attachment dynamic div end -->
        	
  <div class="clearfix mt">
    <div class="col-lg-12">
    <div class="form-footer">
      <div class="pull-right  mr">
        <input type="button" value="Save" id="saveLca" onclick="saveLcaDetails();" class="btn btn-primary">
       <input type="button" value="Cancel"
							onClick="javascript:window.location.href = '<c:url value='/app/immigration/viewLCAList'/>'"
							class="btn btn-default ml">
      </div>
      </div>
    </div>
  </div>
  
  </div>
  </form>
	</div>
	 <div id="popUp"></div>
</section>

<!-- content area ends here -->
  
  <jsp:include page="../masterFooter.jsp" />
  
  
  <script type="text/javascript">

  function saveLcaDetails(){
	  privilingWageValidations();
       var validationSettings = {
            errorMessagePosition: 'element',
            borderColorOnError: '',
            scrollToTopOnError: true,
            dateFormat: 'yyyy/mm/dd'
        };
       
       var isTotalValid= totalCountChek();
        var newEmploymentCount = document.getElementById("newEmploymentCount").value;
       if(newEmploymentCount != null && newEmploymentCount != '' && newEmploymentCount !='undefined'){ 
	       if(!isTotalValid){
	       	showLCAWarningPopup("Total Count value is not matched with No.of Positions !!!");
	       	return false;
	       }
        } 
	    if ($('#lcaDetailsForm').validate(false, validationSettings)) {
			  var formData = getFormData("lcaDetailsForm");
			  formData.lcaPlacaOfempDeletedListIds = removedLcaPlsOfEmpmntIds.join();
			  formData.lcaDocumentListIds = removedLcaDocIds.join();
		 $.ajax({
	            type: "POST",
	            data : formData,
	            url:sourceleadUrl+immiModulePath +"/saveLcaDetailsPage",
	            success: function (data) {
	            	location.replace(sourceleadUrl+immiModulePath+"/viewLCAList");
	            },
	        });
	 }
    }
    $('body').on('blur', '#lcaDetailsForm', function () {
        $("#lcaDetailsForm").showHelpOnFocus();
        $("#lcaDetailsForm").validateOnBlur(false, validationSettings);
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

		});
</script>

<script>
	$("#newEmployment").click(function(){
		var ne = $("#newEmploymentCount");
		if(ne.prop("readonly") == true) {
			ne.prop("readonly", false);
			//alert("A");
			//$('#rateValidateId').find('p.jquery_form_error_message').remove();
		}else {
			ne.prop("readonly", true);
			//alert("B");
			
		}
		
	});
	$("#concurrentEmployment").click(function(){
		var ce = $("#concurrentEmploymentCount");
		if(ce.prop("readonly") == true) {
			ce.prop("readonly", false);
		}else {
			ce.prop("readonly", true);
		}
	});
	$("#extention").click(function(){
		var ex = $("#extentionCount");
		if(ex.prop("readonly") == true) {
			ex.prop("readonly", false);
		}else {
			ex.prop("readonly", true);
		}
	});
	$("#changeEmployer").click(function(){
		var che = $("#changeInEmployerCount");
		if(che.prop("readonly") == true) {
			che.prop("readonly", false);
		}else {
			che.prop("readonly", true);
		}
	});
	
	 $("#changeEmployerPrevious").click(function(){
		var ix = $("#changeEmployerPreviousCount");
		if(ix.prop("readonly") == true) {
			ix.prop("readonly", false);
		}else {
			ix.prop("readonly", true);
		}
	}); 
	$("#amendment").click(function(){
		var amendment = $("#amendmentCount");
		if(amendment.prop("readonly") == true) {
			amendment.prop("readonly", false);
		}else {
			amendment.prop("readonly", true);
		}
	});
</script>

<script type="text/javascript">

var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
var finalDateFormatLcaSave = getJSDateFormat(locationDate);

function validDateClass(){
	 var startDate = document.getElementById("startDate").value;
	 if(startDate){
		 $('#lcaStartDateDiv').find('p.jquery_form_error_message').remove(); 
	 }
	 
	 var endDate = document.getElementById("endDate").value;
	 if(endDate){
		 $('#lcaEndDateDiv').find('p.jquery_form_error_message').remove(); 
	 }
}

$('#startDate').datepicker({
    dateFormat: finalDateFormatLcaSave,
    changeMonth: true,
	  changeYear: true, 
    onSelect: function (selected) {
          //var dt = new Date(selected);
          var dt = $(this).datepicker('getDate', '+1d');
          dt.setDate(dt.getDate() + 1);
          $("#endDate").datepicker("option", "minDate", dt);
          validDateClass();
      }
});

$('#endDate').datepicker({
    dateFormat: finalDateFormatLcaSave,
    changeMonth: true,
	  changeYear: true, 
      onSelect: function (selected) {
          //var dt = new Date(selected);
          var dt = $(this).datepicker('getDate', '-1d');
          dt.setDate(dt.getDate() - 1);
          $("#startDate").datepicker("option", "maxDate", dt);
          validDateClass();
      }
});


</script>

<script>
   function clearLcaDateValue(id){
	   $("#"+id).val(""); 
   }
   
</script>

<!-- Place of Employment Add Row Script Starts Here -->
<script type="text/javascript">
   
    function prepareDynamicPlaceofEmploymentDiv(rowId,plsofEmpmtVal){
     var preparePlsofEmpmtdiv ='';
    	/* var plsempmntId = $("#plsempmntId").val(); */
       preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "<div id ='dynamicPlsofEmpmtDiv"+rowId+"'>";
	   
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "   <div class='clearfix'></div>";
	  /*  preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "<input type='hidden' name='lcaPlaceOfEmploymentList["+rowId+"].plsempmntId' id='plsempmntId' value='"+plsempmntId+"'>"; */
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "   <div class='col-xs-12 col-sm-12 col-md-12 mb'>";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "   <h5 class='col-xs-8 col-sm-8 col-md-8'>Place of Employment<span class='text-danger paddingLeft'>*</span></h5> ";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "    <div class='col-xs-4 col-sm-4 col-md-4'>";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "    <div class='pull-right'>";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "   <button type='button' class='btn btn-default pull-right' onclick='removeDynamicPlsofEmpmtDiv("+rowId+")'><i class='fa fa-remove text-danger paddingRight'></i>Remove</button>";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "   <div class='clearfix'></div>";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "   </div>";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "   </div>";
	   
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "   </div>";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "   <div class='clearfix'></div>";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ " <div class='col-xs-12 col-sm-6 col-md-6 minHeight50 mb'>";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "     <label class='col-md-4'>Location<span class='text-danger paddingLeft'>*</span></label>";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "       <div class='col-md-8'>";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "           <div class='positionRelative'>";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ ' 						<input class="form-control positionRelative"';
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ ' 						id="location'+rowId+'" name="lcaPlaceOfEmploymentList['+rowId+'].location"';
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ ' 							placeholder="Enter Location" value="'+(typeof plsofEmpmtVal[1]==="undefined"?"":plsofEmpmtVal[1])+'" field-name="Location" data-validation="required" type="text" onclick="initLocationAuto(\'location'+rowId+'\',\'country'+rowId+'\',\'state'+rowId+'\',\'city'+rowId+'\',\'zipCode'+rowId+'\',\'\',\'\',\'\',\'addressLineOne'+rowId+'\',\'addressLine2'+rowId+'\');"></input> <i';
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ ' 							class="fa fa-refresh btn btn-primary refresh-icon"';
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ ' 							title="Refresh"';
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ ' 							onClick="refreshGoogleApiLocation(\'location'+rowId+'\',\'country'+rowId+'\',\'state'+rowId+'\',\'city'+rowId+'\',\'zipCode'+rowId+'\',\'addressLineOne'+rowId+'\');"></i>';
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ " 			</div>";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "       </div>";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "     </div>";
	   
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "    <div class='col-xs-12 col-sm-6 col-md-6 minHeight50 mb'>";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "      <label class='col-md-4'>Address Line1</label>";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "      <div class='col-md-8'>";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "           <input type='text' class='form-control' id='addressLineOne"+rowId+"'";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ " 						name='lcaPlaceOfEmploymentList["+rowId+"].addressLine1' value='"+(typeof plsofEmpmtVal[2]==="undefined"?"":plsofEmpmtVal[2])+"' field-name='Street Address' placeholder='Enter Address Line1'";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ " 						data-validation='validate_Space validate_length length2-250'";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ " 						data-validation-optional='true'>";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "       </div>";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "     </div>";
	   
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "    <div class='col-xs-12 col-sm-6 col-md-6 minHeight50 mb'>";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "      <label class='col-md-4'>Address Line2</label>";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "      <div class='col-md-8'>";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "           <input type='text' class='form-control' id='addressLine2"+rowId+"'";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ " 						name='lcaPlaceOfEmploymentList["+rowId+"].addressLine2' value='"+(typeof plsofEmpmtVal[3]==="undefined"?"":plsofEmpmtVal[3])+"' field-name='Address Line2' placeholder='Enter Address Line2'";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ " 						data-validation='validate_Space validate_length length2-250'";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ " 						data-validation-optional='true'>";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "       </div>";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "     </div>";
	   
	   
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "     <div class='col-xs-12 col-sm-6 col-md-6 minHeight50 mb'>";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "       <label class='col-md-4'>City<span class='text-danger paddingLeft'>*</span></label>";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "        <div class='col-md-8'>";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "           <input class='form-control' id='city"+rowId+"' name='lcaPlaceOfEmploymentList["+rowId+"].city' value='"+(typeof plsofEmpmtVal[4]==="undefined"?"":plsofEmpmtVal[4])+"' placeholder='City' type='text' field-name='City' data-validation='required validate_Space validate_length length2-250'></input>";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "       </div>";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "      </div>";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "     <div class='col-xs-12 col-sm-6 col-md-6 minHeight50 mb'>";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "       <label class='col-md-4'>State<span class='text-danger paddingLeft'>*</span></label>";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "       <div class='col-md-8'>";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "           <input class='form-control' id='state"+rowId+"' name='lcaPlaceOfEmploymentList["+rowId+"].state' value='"+(typeof plsofEmpmtVal[5]==="undefined"?"":plsofEmpmtVal[5])+"' placeholder='State' type='text' field-name='State' data-validation='required validate_Space validate_length length2-250'></input>";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "       </div>";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "     </div>";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "     <div class='col-xs-12 col-sm-6 col-md-6 minHeight50 mb'>";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "        <label class='col-md-4'>Country<span class='text-danger paddingLeft'>*</span></label>";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "       <div class='col-md-8'>";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "           <input class='form-control' id='country"+rowId+"' name='lcaPlaceOfEmploymentList["+rowId+"].country' value='"+(typeof plsofEmpmtVal[6]==="undefined"?"":plsofEmpmtVal[6])+"' placeholder='Country' value='${loginCountryName}' type='text' field-name='Country' data-validation='required validate_Space validate_length length2-250'></input>";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "       </div>";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "     </div> ";                                                                                              
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "     <div class='col-xs-12 col-sm-6 col-md-6 minHeight50 mb'>";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "       <label class='col-md-4'>Zip Code</label>";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "       <div class='col-md-8'>";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "          <input class='form-control' id='zipCode"+rowId+"' value='"+(typeof plsofEmpmtVal[7]==="undefined"?"":plsofEmpmtVal[7])+"' name='lcaPlaceOfEmploymentList["+rowId+"].zipCode' data-validation='validate_Space validate_length validate_zipcode length2-8' data-validation-optional='true'";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ " 						placeholder='Zip code' type='text' ></input>";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "       </div>";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "     </div>";
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "    	<div class='clearfix'></div>";
	  
	  
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "   </div>";
	   
	   preparePlsofEmpmtdiv =preparePlsofEmpmtdiv+ "   </div>";
		return preparePlsofEmpmtdiv; 
   }
	
   var rowIdPlempt ="${rowIndexPlempt+1}";// set the value for dyamic row when clicked on add button in UI.
   console.log("rowId value is test --->"+rowIdPlempt);
   
   function appendDynamicPlaceofEmploymentDiv(){
	  
	   console.log("rowId value is --->"+rowIdPlempt);
	   $("#dynamicPlaceofEmploymentDiv").append(prepareDynamicPlaceofEmploymentDiv(rowIdPlempt++,"")); 
	   if(rowIdPlempt > 2){
		   $("#placeEmployMntId").hide();
	   }
   }

   var removedLcaPlsOfEmpmntIds = new Array();
   function removeDynamicPlsofEmpmtDiv(id){
	   if($("#plsempmntId"+id).val() !=='undefined'){
	    removedLcaPlsOfEmpmntIds.push($("#plsempmntId"+id).val());
	   }
	   $("#dynamicPlsofEmpmtDiv"+id).remove();
	   
	   rowIdPlempt--;// decrement rowid when deleted in UI.
	   if(rowIdPlempt <= 2){
		   $("#placeEmployMntId").show();
	   }
   } 
 </script>
 

 <!-- Place of Employment Add Row Script END Here -->  	
 
 <!-- Attachment Add Row Script Start Here -->
 
 <script type="text/javascript">
    function prepareDynamicAttachmentDiv(rowId,attachmentVal){
       var prepareAttachmentdiv ='';
       prepareAttachmentdiv =prepareAttachmentdiv+ "<div id ='dynamicAttachmentDiv"+rowId+"'>";
       prepareAttachmentdiv =prepareAttachmentdiv+ "   <div class='clearfix'></div>";
       prepareAttachmentdiv =prepareAttachmentdiv+ "   <div class='col-xs-12 col-sm-12 col-md-12 mb noPad'>";
       prepareAttachmentdiv =prepareAttachmentdiv+ "   <h5 class='col-xs-8 col-sm-8 col-md-8'>Attachment<span class='text-danger paddingLeft'>*</span></h5> ";
       prepareAttachmentdiv =prepareAttachmentdiv+ "    <div class='col-xs-4 col-sm-4 col-md-4'>";
       prepareAttachmentdiv =prepareAttachmentdiv+ "    <div class='pull-right'>";
       prepareAttachmentdiv =prepareAttachmentdiv+ "   <button type='button' class='btn btn-default pull-right' onclick='removeDynamicAttachmentDiv("+rowId+")'><i class='fa fa-remove text-danger paddingRight'></i>Remove</button>";
       prepareAttachmentdiv =prepareAttachmentdiv+ "   <div class='clearfix'></div>";
       prepareAttachmentdiv =prepareAttachmentdiv+ "   </div>";
       prepareAttachmentdiv =prepareAttachmentdiv+ "   </div>";
       prepareAttachmentdiv =prepareAttachmentdiv+ "   </div>";
       prepareAttachmentdiv =prepareAttachmentdiv+ " <div class='col-md-4 mb pl0'>";
       prepareAttachmentdiv =prepareAttachmentdiv+ " <label class='col-md-12'>Title</label>";
       prepareAttachmentdiv =prepareAttachmentdiv+ " <div class='col-md-12'>";
       prepareAttachmentdiv =prepareAttachmentdiv+ "  <input type='text' class='form-control' id='documentTitle"+rowId+"'";
       prepareAttachmentdiv =prepareAttachmentdiv+ " 						name='lcaDocumentList["+rowId+"].documentTitle' value='"+(typeof attachmentVal[1]==="undefined"?"":attachmentVal[1])+"' field-name='Document Title' placeholder='Document Title'";
       prepareAttachmentdiv =prepareAttachmentdiv+ " 						data-validation='required validate_Space validate_length length2-250'>";
       prepareAttachmentdiv =prepareAttachmentdiv+ " </div>";
       prepareAttachmentdiv =prepareAttachmentdiv+ " </div>";
	   
       prepareAttachmentdiv =prepareAttachmentdiv+ " <div class='col-md-4 mb'>";
       prepareAttachmentdiv =prepareAttachmentdiv+ " <label class='col-md-12'>Attachment</label>";
       prepareAttachmentdiv =prepareAttachmentdiv+ " <div class='col-md-12'>";
       prepareAttachmentdiv =prepareAttachmentdiv+ "<div class='input-group errorInptgroup'>";
       prepareAttachmentdiv =prepareAttachmentdiv+ "<label class='input-group-btn'>";
       prepareAttachmentdiv =prepareAttachmentdiv+ "<span class='btn btn-primary' onchange='uploadLCADocs("+rowId+");'><i class='fa fa-folder-open-o'></i>&nbsp;Browse File"
       prepareAttachmentdiv =prepareAttachmentdiv+ "<input type='file' style='display: none;' multiple onchange='uploadBtnFile("+rowId+");' id='uploadBtn" + rowId + "'  class='upload'>";
       prepareAttachmentdiv =prepareAttachmentdiv+ "</span>";
	   prepareAttachmentdiv =prepareAttachmentdiv+ "</label>";
	   prepareAttachmentdiv =prepareAttachmentdiv+ "<input type='text' class='form-control' name='lcaDocumentList["+rowId+"].fileInfo[0].docFile' value='"+(typeof attachmentVal[2]==="undefined"?"":attachmentVal[2])+"' readonly id='uploadFile"+rowId+"' placeholder='Files are not selected...' field-name='Upload Document' data-validation='required validatedoc'>";
	   prepareAttachmentdiv =prepareAttachmentdiv+ "</div>";
	   prepareAttachmentdiv =prepareAttachmentdiv+ " </div>";
	   prepareAttachmentdiv =prepareAttachmentdiv+ " </div>";
	   
	   
	   prepareAttachmentdiv =prepareAttachmentdiv+ " <div class='col-md-4 mb pr0'>";
	   prepareAttachmentdiv =prepareAttachmentdiv+ " <label class='col-md-12'>Comment</label>";
	   prepareAttachmentdiv =prepareAttachmentdiv+ " <div class='col-md-12'>";
	   prepareAttachmentdiv =prepareAttachmentdiv+ " <textarea class='form-control' name='lcaDocumentList["+rowId+"].description' id='description"+rowId+"' value='"+(typeof attachmentVal[3]==="undefined"?"":attachmentVal[3])+"' placeholder='Comment' rows='2' data-validation='validate_Space validate_length length1-500' data-validation-optional='true'>";
	   prepareAttachmentdiv =prepareAttachmentdiv+ "</textarea>";
	   prepareAttachmentdiv =prepareAttachmentdiv+ " </div>";
	   prepareAttachmentdiv =prepareAttachmentdiv+ " </div>";
       
	   prepareAttachmentdiv =prepareAttachmentdiv+ " <div class='clearfix'></div>";
	   prepareAttachmentdiv =prepareAttachmentdiv+ " </div>";
	   prepareAttachmentdiv =prepareAttachmentdiv+ " </div>";
       return prepareAttachmentdiv; 
    }
    
    
    var docRowId ="${docRowIndex+1}"; // set the value for dyamic row when clicked on add button in UI.
    console.log("docRowId value is test --->"+docRowId);
    function appendDynamicAttachmentDiv(){
 	   console.log("rowId value is --->"+docRowId);
 	   $("#appendDynamicAttachmentDiv").append(prepareDynamicAttachmentDiv(docRowId++,"")); 
    }
  
   var removedLcaDocIds = new Array();
    function removeDynamicAttachmentDiv(id){
    	
    	if($("#docId"+id).val() !==  undefined ){
  		   removedLcaDocIds.push($("#docId"+id).val());
  	   }
 	   
 	   $("#dynamicAttachmentDiv"+id).remove();
 	
 	//  $("input[name='lcaDocumentList["+id+"].docId']").remove();// remove the deleted row for saving.
 	// docRowId--;// decrement rowid when deleted in UI.
    } 
   
</script>

<script>
    function uploadLCADocs(id) {
        fileUpload("uploadBtn" + id + "", "LCADocuments", "lcaDetailsForm", "lcaDocumentList["+id+"].fileInfo");
    }

</script>

<script>
    function uploadBtnFile(id) {
        $('#uploadFile' + id).val($("#uploadBtn"+id).val().replace(/C:\\fakepath\\/i, ''));
    }
    
    function showLCAWarningPopup(multiWarnMsg){ // multiWarnMsg populated when validated form and docs required upload docs info.
    	$(".project_inn_mask").remove();
    	
    	var result = "";
    	result = result +"	<div class='project_inn_mask'></div>";
    	result = result +"	   <div id='remove_immigration_pop' class='remove_user_pop remove_user_pop_a modal-body'>";
    	 
    	if (typeof multiWarnMsg != 'undefined' && multiWarnMsg != '') {
    			result = result + " <b class='mt mb'>" + multiWarnMsg + "</b>";
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


 <!-- Attachment Add Row Script END Here -->
 
 <script>
 function newEmployment(){
	 var flag = document.getElementById("newEmployment").checked;
	 if(flag == true){
		 $("#newEmployment").val('0'); 
		 $("#newEmploymentCount").val('');
		 $("#newEmploymentCount").removeAttr("data-validation");
		 $('#newEmploymentCount').find('p.jquery_form_error_message').remove();
	 }else{
		 $("#newEmployment").val('1'); 
		 $("#newEmploymentCount").attr("data-validation","required");
	 } 
 }
 function concurrentEmployment(){
	 var flag = document.getElementById("concurrentEmployment").checked;
	 if(flag == true){
		 $("#concurrentEmployment").val('0'); 
		 $("#concurrentEmploymentCount").val('');
		 $("#concurrentEmploymentCount").removeAttr("data-validation");
	 }else{
		 $("#concurrentEmployment").val('1');
		 $("#concurrentEmploymentCount").attr("data-validation","required");
	 } 
 }
 function extention(){
	 var flag = document.getElementById("extention").checked;
	 if(flag == true){
		 $("#extention").val('0'); 
		 $("#extentionCount").val('');
		 $("#extentionCount").removeAttr("data-validation");
	 }else{
		 $("#extention").val('1');
		 $("#extentionCount").attr("data-validation","required");
	 } 
 }
 function changEmployer(){
	 var flag = document.getElementById("changeEmployer").checked;
	 if(flag == true){
		 $("#changeEmployer").val('0');
		 $("#changeInEmployerCount").val('');
		 $("#changeInEmployerCount").removeAttr("data-validation");
		 
	 }else{
		 $("#changeEmployer").val('1');
		 $("#changeInEmployerCount").attr("data-validation","required");
	 } 
 }
 function approvedEmployment(){
	 var flag = document.getElementById("changeEmployerPrevious").checked;
	 if(flag == true){
		 $("#changeEmployerPrevious").val('0'); 
		 $("#changeEmployerPreviousCount").val('');
		 $("#changeEmployerPreviousCount").removeAttr("data-validation");
		 
	 }else{
		 $("#changeEmployerPrevious").val('1');
		 $("#changeEmployerPreviousCount").attr("data-validation","required");
	 } 
	 
 }
 
 function amendment(){
	 var flag = document.getElementById("amendment").checked;
	 if(flag == true){
		 $("#amendment").val('0'); 
		 $("#amendmentCount").val('');
		 $("#amendmentCount").removeAttr("data-validation");
		
	 }else{
		 $("#amendment").val('1');
		 $("#amendmentCount").attr("data-validation","required");
	 } 
 }
 </script>
 <!-- SOC CODE Autocomplete -->
 
 <script type="text/javascript">
 $("#socCode").autocomplete({ 
	 minLength: 2, 
	 source : function(request, response) {
		 $.ajax({
			 url :sourceleadUrl+immiModulePath+ "/getSocCodeWithSOCOccupationTitleList",
			 type : "GET",
			 data : {
			 	name : request.term,
			 	socType:"socCode"
			 	},
			 dataType : "json",
			 success : function(data) {
				
				 console.log(data);
			 	response(data);
			 }
		 });
	 },
	 select: function( event, ui ) {
	 	$("#socCode").val(ui.item.label);
	 	$.ajax({
	    	type: "GET",
	        url: sourceleadUrl+immiModulePath+"/getOesSocCodeById", 
	        data : {
			 	id : ui.item.value,
			 	socType:"socTitle"
			 	},		
	        success: function(socObj) {
	        	
	        	$("#socOccupationTitle").val(socObj);
	        }
	 }); 
	 	return false;
	 },
	 focus: function(event, ui){
	          return false;
	    }
	 });
</script> 
  <script type="text/javascript">
 $("#socOccupationTitle").autocomplete({ 
	 minLength: 2, 
	 source : function(request, response) {
		 $.ajax({
			 url :sourceleadUrl+immiModulePath+ "/getSocCodeWithSOCOccupationTitleList",
			 type : "GET",
			 data : {
			 	name : request.term,
			 	socType:"socTitle"
			 	},
			 dataType : "json",
			 success : function(data) {
			 	response(data);
			 }
		 });
	 },
	 select: function( event, ui ) {
	 	$("#socOccupationTitle").val(ui.item.label);
	   $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+immiModulePath+"/getOesSocCodeById",
	        data : {
			 	id : ui.item.value,
			 	socType:"socCode"
			 	},
	        success: function(socObj) {
	        	
	        	$("#socCode").val(socObj);
	        }
	 }); 
	 	return false;
	 },
	 focus: function(event, ui){
	          return false;
	    }
 });
</script> 
 
<script type="text/javascript">

 function totalCountChek(){
	 
        var totalCount =	$.isNumeric($('#noOfPositions').val()) ? parseInt($('#noOfPositions').val()):parseInt(0);
   		var newEmpCount =	$.isNumeric($('#newEmploymentCount').val()) ? $('#newEmploymentCount').val():0;
		var conEmpCount =	$.isNumeric($('#concurrentEmploymentCount').val()) ? $('#concurrentEmploymentCount').val():0;
		var extCount =		$.isNumeric($('#extentionCount').val()) ? $('#extentionCount').val():0;
		var chngEmpCount =	$.isNumeric($('#changeInEmployerCount').val()) ? $('#changeInEmployerCount').val():0;
		var chngPreEmpCount= $.isNumeric($('#changeEmployerPreviousCount').val()) ? $('#changeEmployerPreviousCount').val():0;
		var amendMntCount =	 $.isNumeric($('#amendmentCount').val()) ? $('#amendmentCount').val():0;

		var allCounts = parseInt(newEmpCount)+parseInt(conEmpCount)+parseInt(extCount)+parseInt(chngEmpCount)+parseInt(chngPreEmpCount)+parseInt(amendMntCount);
		
			if(allCounts == totalCount){
				
			    return true;
			} else {
				 return false;
			}  
			
 };
</script>

<script type="text/javascript">
function validateLcaNumber() {
	  var lcaNo = document.getElementById("lcaNo").value;
	  var lcaId = $("#lcaId").val();
	  $('#errLcaNoDiv').find('p.jquery_form_error_message').remove();
		 $.ajax({
		    	type: "GET",
		    	 url:sourceleadUrl + immiModulePath + "/validateLcaNumber?"+tokenParamName+"="+tokenValue+"&lcaNo="+lcaNo+"&lcaId="+lcaId, 
		        success: function(data) {
		        	
		        	if(data == true){
		        		
		        		  $('#errLcaNoDiv').find('p.jquery_form_error_message').remove();
		        		  $("input#lcaNo").after("<p class='jquery_form_error_message'>"+lcaNo+" is Already Existed</span>");
						  $("#saveLca").attr('disabled','disabled');
		        	}
		        	else{
		        		 $('input#lcaNo').removeAttr( "record-exist record-exist-errormsg");
		        		 $('#errLcaNoDiv').find('p.jquery_form_error_message').remove(); 
                        $("#saveLca").removeAttr("disabled");
		        	}
		        	
		        }
		       
		    });
};
</script>

<script type="text/javascript">
function validateLcaTitle() {
	  var lcaTitle = document.getElementById("lcaTitle").value;
	  var lcaId = $("#lcaId").val();
	  $('#errLcaTitleDiv').find('p.jquery_form_error_message').remove();
		 $.ajax({
		    	type: "GET",
		    	 url:sourceleadUrl + immiModulePath + "/validateLcaTitle?"+tokenParamName+"="+tokenValue+"&lcaTitle="+lcaTitle+"&lcaId="+lcaId, 
		        success: function(data) {
		        	
		        	if(data == true){
		        		
		        		  $('#errLcaTitleDiv').find('p.jquery_form_error_message').remove();
		        		  $("input#lcaTitle").after("<p class='jquery_form_error_message'>"+lcaTitle+" is Already Existed</span>");
						  $("#saveLca").attr('disabled','disabled');
		        	}
		        	else{
		        		 $('input#lcaTitle').removeAttr( "record-exist record-exist-errormsg");
		        		 $('#errLcaTitleDiv').find('p.jquery_form_error_message').remove(); 
                        $("#saveLca").removeAttr("disabled");
		        	}
		        	
		        }
		       
		    });
};
</script>
 
 <script type="text/javascript">
   function validateNumber(object){ 
	   object.value = object.value.replace(/[^0-9\.]/g,'');
	}
   
  /*  $('#lcaNo').mask('I-999-99999-999999', {
	    translation: {
	        '#': {
	            pattern: "/I{1}[0-9]{14}/", optional: true
	        }
	    },
	   placeholder:""
	});  */
 </script>
 
<script>
    function privilingWageValidations() {
        var offeredWage = document.getElementById("offeredWage").value;
        var offeredWageRateType = document.getElementById("offeredWageRateType").value;
        if (offeredWage != null && offeredWage != "") {
            $('#offeredWageRateType').removeAttr('data-validation-optional');
            $('#offeredWageRateType').attr("data-validation", "required");
            $('#offeredWage').attr("data-validation", "required validate_Space validate_int validate_length length1-8");
        } else {
            $('#offeredWageRateType').removeAttr('data-validation');
            $('#offeredWageRateType').attr("data-validation-optional", "true");
        }
        
        var prevailingWage = document.getElementById("prevailingWage").value;
        var privilingWageRateType = document.getElementById("privilingWageRateType").value;
        if (prevailingWage != null && prevailingWage != "") {
            $('#privilingWageRateType').removeAttr('data-validation-optional');
            $('#privilingWageRateType').attr("data-validation", "required");
            $('#prevailingWage').attr("data-validation", "required validate_Space validate_int validate_length length1-8");
        } else {
            $('#privilingWageRateType').removeAttr('data-validation');
            $('#privilingWageRateType').attr("data-validation-optional", "true");
        }
    }
</script>