<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:url value="/resources/css/styles.css" var="cssmy"/>
<link href="${cssmy}" rel="stylesheet">
<script src="/resources/js/jquery-1.11.3.min.js"></script>

             
             
<c:if test='${empty type}'>
<script src="${SourceLeadWeb_vmsUrl}/resources/js/signer.js" type="text/javascript"></script>
<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
</c:if>

 <c:if test='${!empty type}'>
 <script src="${vmsUrl}/resources/js/signer.js" type="text/javascript"></script>
 <link href="/resources/css/font-awesome.min.css?staticTime=${staticTime}" rel="stylesheet">
 <link href="/resources/css/sourcelead-main.css?staticTime=${staticTime}" rel="stylesheet">
<link rel="stylesheet" href="/resources/css/timesheet.css?staticTime=${staticTime}" type="text/css" >
 <link href="/resources/css/bootstrap.min.css" rel="stylesheet">
 <script src="/resources/js/bootstrap.min.js"></script> 
 </c:if>

<script>
	$(document).ready(function() {
		var data = '${type}';
		var permission = '${permission}';
		Signer.uploadDoc(data,permission);
		/* var firstName = '${SourceLeadWeb_user.firstName}';
		var lastName = '${SourceLeadWeb_user.lastName}';
		$("#txtsignname").val(firstName);
		if(lastName !=null && lastName != '' && lastName != 'undefined'){
			$("#txtinitialname").val(lastName);
		}else{
			$("#txtinitialname").val('');
		} */
});
</script> 

<c:if test='${!empty type}'>
<header class="publicpageHeader">
<div class="clearfix">
	<div class="publicpage-logo col-md-2 text-center">
		<a href="javascript:void(0)" title="Source Lead"> <i> <img src="/resources/images/logo-sourceld.png" alt="Source Lead"></i></a>
	</div>
	</div>
</header>
</c:if>


 <div id="backShadow"></div>
  <div id="popup"></div>
  <div id="successPop"></div>
  
<section class="content-wraper container">

<section class="content-header clearfix">
    <div class="text-center clearfix" id="hideSig">
      <h2>Create Signature</h2>
    </div>
    <c:if test='${empty type}'>
    <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a>&raquo;<a href="${SourceLeadWeb_vmsUrl}/contractListbyStatus">Contracts List</a>&raquo;Create Signature</div>
  </c:if>
  </section>
            <div id="addSign">
                <div class="cancelSign" id ="cancelId">
                    <button type="button" onclick="Signer.hideSignModal()" class="close" aria-hidden="true">&times;</button>
                </div>
                <div>
                    <div class="form-group row mt40">
                        <label  class="col-xs-3 text-right" for="ex1">Select Font Style</label>
                        <div class="col-xs-7 dropdown-select">
                            <!-- <input class="form-control" id="ex1" type="text"> -->
                            <select id="ddnFontStyle" class="form-control" onchange="Signer.createSignaturePreview()">
                                <option value="1">Style 1</option>
                                <option value="2">Style 2</option>
                                <option value="3">Style 3</option>
                            </select>
                        </div>
                    </div>
                    
                    <div class="form-group row">
                        <label for="txtsignname"  class="col-xs-3 text-right">Signatory Name</label>
                        <div class="col-xs-7" id="signatoryNameDiv">
                            <input class="form-control" id="txtsignname" field-name="Signatory Name" type="text" value="${signatory}">
                        </div>
                    </div>
                    <div class="form-group row">
                         <label class="col-xs-3 text-right" for="txtinitialname">Initial</label>
                        <div class="col-xs-7">
                            <input class="form-control" id="txtinitialname" type="text" value="${initial}">
                        </div>
                    </div>
                </div>
                <div class="clearfix">
                <div class="col-md-3 col-md-offset-3">
                    <button id="btnShowPreview" class="btn btn-primary btn-block" onclick="Signer.createSignaturePreview()">Preview</button>
					</div>
					<div class="col-md-3">                   
					<button id="btnShowPreview" class="btn btn-success btn-block" onclick="Signer.createSignaturePreview();Signer.storeStyle()">Use This Sign</button>
					</div>
                </div>
                <div id="signPreview" style="display: none;">
                    
                    <div class="clearfix">
                    <img id="signImage" src="">
                    </div>
                    
                    <div class="clearfix">
                    <img id="initialImage" src="">
                    </div>
                    
                </div>
            </div>
            
            <div>

                <div class="signContainer width100percnt">
                    <div>
                        <iframe id="fuploadframe" name="fuploadframe" src="" height="0px" width="0px" frameborder="0" scrolling="no"></iframe>
                    </div>
                    <div class="row" id="signerType">
                        <div class="container panel mb0">
                        <div class="panel-body panelbody-custom">
                            <label for="ddnSignType" class="col-xs-3 text-right mt5">Select signing type</label>
                            <div class="col-xs-6 dropdown-select">
                            <select class="form-control input-sm" id="ddnSignType" onchange="Signer.setSigningType()">
                                <option value="0">Define and send to recipient</option>
                                <option value="1">Sign Document</option>
                          </select>
                          </div>
                        </div>
                        </div>
                    </div>

                    <div class="sign-header">
                        <div id="signStatus">
                            <div class="div-right">
<!--                                 <button value="Sign This Document" id="btnSignNext" class="btn btn-success">Sign Next</button> -->
                                <button value="Sign This Document" id="btnComplete" class="btn btn-success">Complete</button>
                            </div>
                        </div>
                        <input type="hidden" id="sourceUrl" value="${vmsUrl}">
                        <input id="contractOrgLocationId" value="${contract.contractOrgLocationId.id}" type="hidden">
                        <input type="hidden" id="contractId" value="${contract.id}">
                    </div>
                    <div class="sign-container">
                        <div class="sign-button-holder signActions">
                            <div class="sign-buttons">
                                <ul>
                                    <li id="btnAddSignature" title="Customize signature" onclick="Signer.addSignaturePlaceholder()">
                                        <!-- <img id="btnAddSignature" title="Add new signature" alt="Add new signature" src="../navinleon@gmail.com/addsign.png" onclick="Signer.addSignaturePlaceholder()"> -->
                                    </li>
                                    <li id="btnAddInitial" title="Customize initial" onclick="Signer.addInitialPlaceholder()">
                                        <!-- <img id="btnAddInitial" title="Add new initial" alt="Add new initial" src="../navinleon@gmail.com/addinitial.png" onclick="Signer.addInitialPlaceholder()"> -->
                                    </li>
                                    <li id="btnPreviewSignature" title="Preview signature" onclick="Signer.previewSignature()">
                                        <!-- <img id="btnPreviewSignature" title="Preview signature" alt="Preview signature" src="../navinleon@gmail.com/previewsign.png" onclick="Signer.previewSignature()"> -->
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <div id="pdf-preview">

                        </div>
                    </div>
                </div>
            </div>
</section>

            
 <c:if test='${empty type}'>
 <jsp:include page="../masterFooter.jsp" />
 </c:if>
