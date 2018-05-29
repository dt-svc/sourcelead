<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="${sourceleadUrl}/resources/source-css/font-awesome.min.css" rel="stylesheet" type="text/css">
<link href="${sourceleadUrl}/resources/source-css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="${sourceleadUrl}/resources/source-css/reset.css">
<link href="${sourceleadUrl}/resources/source-css/source-style.css" rel="stylesheet">
<link href="${sourceleadUrl}/resources/source-css/source-responsive.css" rel="stylesheet">
<link href="${sourceleadUrl}/resources/source-css/careersstyles.css" rel="stylesheet">
<script type="text/javascript">
    var surl = '${sourceleadUrl}';
</script>
<section class="container clearfix mt">
<div class="row">
    <div class="col-md-12">
        <div class="career-searcharea clearfix">
            <div class="col-sm-4 col-md-5">
                <input type="text" class="form-control" placeholder="Keywords" id="jobSearchText"/>
            </div>
            <div class="col-sm-3 col-md-3">
                <select class="form-control select-styles" id="jobLocation">
                    <option value="0">--Select Location--</option>
                    <core:forEach items="${locations}" var='l'>
 <option value="${l.id}">${l.orgLocationName} - ${l.city}${l.state == null || l.state.isEmpty() ? "" : ", ".concat(l.state)}${l.country == null || l.country.isEmpty() ? "" : ", ".concat(l.country)}</option>                    </core:forEach>
                </select>
            </div>
            <div class="col-sm-3 col-md-3">
                <select class="form-control select-styles" id="jobEmploymentType">
                    <option value="">--Select Employment Type--</option>
                    <option>C2C</option>
                    <option>C2C-1099</option>
                    <option>W-2</option>
                    <option>C2H</option>
                    <option>C2H -1099</option>
                    <option>Part Time</option>
                    <option>Full Time</option>
                    <option>Other</option>
                </select>
            </div>
            <div class="col-sm-2 col-md-1"><button type="button" id="jobSearchButton" class="btn btn-primary btn-block">Search</button></div>
        </div>
        <div class="clearfix mt"></div>
    </div>
    </div>
    <div class="clearfix"></div>
    <div class="clearfix mt">
        <div>
            <div class="srch-resultarea">
                <div class="row">
                    <h4 class="h4-header col-sm-9 col-md-9"><span class="text-muted" id='resultTitle'>${totalJobCount} results</span></h4>
                </div>
                <div id="jobResultList">
                    
                </div>
            </div>
        </div>
    </div>
    <div class="clear"></div>
</section>