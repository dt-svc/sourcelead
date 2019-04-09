var Signer = (function() {
    let timer = 0;
    let pdfPageHeight = 1056; // in px
    let signOffset = 40;
    let signingType = 0;
    let url = '';

    //Get page settings
    let getPageSettings = function() {
        return {
            pdfPageHeight: pdfPageHeight,
            signOffset: signOffset
        };
    }


    //Upload pdf doc for signing
    let uploadDoc = function(publicUrl,permission) {
    	let sourceleadUrl = '';
    	var result = "";
    	if(publicUrl == 'public' && permission == 'No'){
            	result = result +"<div class='col-md-6 col-md-offset-3 inner-widget pending-user'>";
            	result = result +"	<i class='fa fa-clock-o fa-3x noPad'></i>";
            	result = result +"<p class='reason-msg'>Document Already signed..<br></p>";
            	$("#successPop").empty();
            	$("#successPop").append(result);
            	$('.signContainer').hide();
            	$('#hideSig').hide();
    		
    		
    	}else{
    		init();
    	if(publicUrl == 'public')
    		{
    		 url = publicUrl;
    		 sourceleadUrl = $("#sourceUrl").val();
    		 sourceleadUrl = sourceleadUrl.concat('/ws');
    		}
    	else
    		{
    		 sourceleadUrl = $("#sourceUrl").val();
    		}
    	let contractId = $("#contractId").val();
    	 $.ajax({
    	      type : "POST",
    	       url: sourceleadUrl+"/signer?&contractId="+contractId,       
    	       success :function(jsonStr) {
                    if (jsonStr.trim() !== '') {
                        	let jsonObj = JSON.parse(jsonStr.replace(/'/g, '"'));
                            let userId = jsonObj.UserId;
                            localStorage.setItem('userId', userId);
                        	let pages = jsonObj.pageCount;
                        	localStorage.setItem('pages', pages);
                	    	uploadDone(pages, userId,sourceleadUrl);
                    }
    	      },
    	  });
    	}
    }
    
    let uploadDone = function(pages, userId,sourceleadUrl){
    	initializeDrager();
        $('#signerType').show();
        $('.signContainer,.signActions').show();
        $('.askUserDetails,.addRecipients').hide();
        $('#btnAddSignature,#btnAddInitial,#btnSendToRecip').show();
        
        window.setTimeout(function() {
        loadPages(pages, userId,sourceleadUrl);
        }, 10000);
    }
    
    function genran() {
        try {
            var chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz";
            var string_length = 10;
            var randomstring = '';
            for (var i = 0; i < string_length; i++) {
                var rnum = Math.floor(Math.random() * chars.length);
                randomstring += chars.substring(rnum, rnum + 1);
            }
            return randomstring;
        }
        catch (e) {
            return 10;
        }
        return false;
    }

    //Get stored coordinates
    let getCoordinatesData = function(sign) {
        let userId = localStorage.getItem('userId');
        let data = {
            "signatures": [],
            "initials": [],
            "userId": userId,
            "signatureWidth": 100,
            "signatureHeight": 50,
            "initialsWidth": 200,
            "initialsHeight": 70
        }
        let scrollTop = $('#pdf-preview').scrollTop();
        let allPages = $('.pdf-page').map((a, b) => {
            return {
                y: $(b).position().top + scrollTop,
                dom: $(b)
            };
        }).get();
        $(".sign-signature").each((index, dom) => {
            let top = parseFloat($(dom).css('top').replace('px', ''));
            let left = parseFloat($(dom).css('left').replace('px', ''));
            let pageIndex = allPages.sort(function(a, b) {
                return a.y - b.y;
            }).findIndex((a) => {
                return (a.y > top)
            });
            top = top - (Signer.getPageSettings().pdfPageHeight * (pageIndex - 1)) + ((sign) ? 0 : Signer.getPageSettings().signOffset);
            data.signatures.push({
                x: (((left * 3)) / 4),
                y: ((top * 3) + ((sign) ? 12 : 0)) / 4,
                pageNumber: pageIndex
            });
        });
        $(".sign-initial").each((index, dom) => {
            let top = parseFloat($(dom).css('top').replace('px', ''));
            let left = parseFloat($(dom).css('left').replace('px', ''));
            let pageIndex = allPages.sort(function(a, b) {
                return a.y - b.y;
            }).findIndex((a) => {
                return (a.y > top)
            });
            top = top - (Signer.getPageSettings().pdfPageHeight * (pageIndex - 1)) + ((sign) ? 0 : Signer.getPageSettings().signOffset);
            data.initials.push({
                x: ((left * 3) - 1) / 4,
                y: ((top * 3) + ((sign) ? 12 : 0)) / 4,
                pageNumber: pageIndex
            })
        });
        return data;
    }

    //Add placeholder for signature
    let addSignaturePlaceholder = function() {
        let len = $('.sign-container').find(".sign-signature").length;
        let scrollTop = $('#pdf-preview').scrollTop();
        scrollTop = (scrollTop * 3) / 4;
        let userId = localStorage.getItem('userId');
        if (signingType === 1) {
            $('#pdf-preview').prepend('<div id="signature-' + (len + 1) + '" class="sign-signature signature insertedSign" style="top:' + (100 + scrollTop) + 'pt;left:100px;z-index:' + (len + 1) + '"><img src="../' + userId + '/signatures/signedImage.jpeg" class="insertSignImg"><a class="delete-sign"></a></div>');
        } else {
            $('#pdf-preview').prepend('<div id="signature-' + (len + 1) + '" class="sign-signature signature" style="top:' + (100 + scrollTop) + 'pt;left:100px;z-index:' + (len + 1) + '"><a class="signLabel">Select signing type</a><a class="delete-sign"></a></div>');
        }

        $('.sign-container').find(".sign-signature").eq(0).drags();
        Signer.saveCoordinates();
        $('#btnSendToRecip').removeClass('disabled');
        attachEvents();
        validateSigning();
    }

    //Add placeholder for initial
    let addInitialPlaceholder = function() {
        let len = $('.sign-container').find(".sign-initial").length;
        let scrollTop = $('#pdf-preview').scrollTop();
        scrollTop = (scrollTop * 3) / 4;
        let userId = localStorage.getItem('userId');
        if (signingType === 1) {
            $('#pdf-preview').prepend('<div id="initial-' + (len + 1) + '" class="sign-initial signature insertedSign" style="top:' + (100 + scrollTop) + 'pt;left:200px;z-index:' + (len + 1) + '"><img src="../' + userId + '/signatures/initialsImage.jpeg" class="insertSignImg"></div>');
        } else {
            $('#pdf-preview').prepend('<div id="initial-' + (len + 1) + '" class="sign-initial signature" style="top:' + (100 + scrollTop) + 'pt;left:200px;z-index:' + (len + 1) + '"><a class="signLabel">Select signing type</a><a class="delete-sign"></a></div>');
        }
        $('.sign-container').find(".sign-initial").eq(0).drags();
        Signer.saveCoordinates();
        $('#btnSendToRecip').removeClass('disabled');
        attachEvents();
        validateSigning();
    }

    //Remove signature or placeholder on the pdf
    let deleteSign = function(me) {
        let isRecipient = (localStorage.getItem('recipient'));
        let thisSig = me.closest('.signature');
        if (isRecipient || thisSig.hasClass('insertedSign')) {
            thisSig.removeClass('insertedSign');
            thisSig.find('.insertSignImg').remove();
            thisSig.find('.delete-sign').remove();
            let deleteLink = '<a class="delete-sign"></a>';
            if (thisSig.hasClass('sign-initial')) {
                thisSig.append('<a class="signLabel">Select signing type</a>' + (!isRecipient ? deleteLink : ''));
            } else {
                thisSig.append('<a class="signLabel">Select signing type</a>' + (!isRecipient ? deleteLink : ''));
            }
        } else {
            me.closest(".signature").remove();
            if ($(".signature").length === 0) {
                $('#btnSendToRecip,#btnSignDoc').addClass('disabled');
            }
            Signer.saveCoordinates();
        }
        validateSigning();
        validateSignCount();
        attachEvents();
        return false;
    }

    //Save coordinates
    let saveCoordinates = function() {
    	
       	let sourceleadUrl = '';
    	if(url == 'public')
    		{
    		 sourceleadUrl = $("#sourceUrl").val();
    		 sourceleadUrl = sourceleadUrl.concat('/ws');
    		}
    	else
    		{
    		 sourceleadUrl = $("#sourceUrl").val();
    		}
    	
    	 $.ajax({
   	        type : "POST",
   	        url: sourceleadUrl+"/saveSignatureCoordinates",
	   	    data: JSON.stringify(getCoordinatesData()),
	        contentType: "application/json; charset=utf-8",
	        dataType: "json",
	        success: function(data) {
                if (data && data.coordinatsFileName) {
                    localStorage.setItem("coordinates", data.coordinatsFileName);
                }
            },
            error: function(data) {
                data = JSON.parse(data.responseText.replace(/'/g, '"'));
                if (data && data.coordinatsFileName) {
                    localStorage.setItem("coordinates", data.coordinatsFileName);
                }
            }
   	  });
    }


    //Create signature preview
    let createSignaturePreview = function(type) {
    	
      	let sourceleadUrl = '';
    	if(url == 'public')
    		{
    		 sourceleadUrl = $("#sourceUrl").val();
    		 sourceleadUrl = sourceleadUrl.concat('/ws');
    		}
    	else
    		{
    		 sourceleadUrl = $("#sourceUrl").val();
    		}
        let userId = localStorage.getItem('userId');
        var userSign = userId.replace('/unsigned.pdf', '');
        let sigText = $('#txtsignname').val();
        let iniText = $('#txtinitialname').val();

        if(sigText != null && sigText != '' && sigText !='undefined'){
        	 $("#signatoryNameDiv").find('p.jquery_form_error_message').remove();
        	 let reqParam = {
        	            "fontId": parseInt($('#ddnFontStyle').val(), 10),
        	            "userId": userId,
        	            "signatureText": sigText,
        	            "initialText": iniText
        	        }
        	 
        	 $.ajax({
        	        type : "POST",
        	        url: sourceleadUrl+"/signImage",
        	        data: JSON.stringify(reqParam),
    	            contentType: "application/json; charset=utf-8",
    	            dataType: "json",
    	            success: function(data) {
    	            	var json = JSON.parse(data);
    	            	if(url == 'public'){
    	            	    $('#signImage').attr('src', ''+ userSign + '/signatures/' + json.SignatureFileName + '?burst=' + getBurst());
    	    	            $('#initialImage').attr('src', ''+ userSign + '/signatures/' + json.InitialFileName + '?burst=' + getBurst());
    	    	              window.setTimeout(() => {
    	    	                    $('#signImage').attr('src', ''+ userSign + '/signatures/' + json.SignatureFileName + '?burst=' + getBurst());
    	    	                    $('#initialImage').attr('src', ''+ userSign + '/signatures/' + json.InitialFileName + '?burst=' + getBurst());
    	    	              }, 500);
    	    	            $("#signPreview").show();
    	            	}else{
    	            	    $('#signImage').attr('src', '../' + userSign + '/signatures/' + json.SignatureFileName + '?burst=' + getBurst());
        	                $('#initialImage').attr('src', '../' + userSign + '/signatures/' + json.InitialFileName + '?burst=' + getBurst());
        	                 window.setTimeout(() => {
        	                    $('#signImage').attr('src', '../' + userSign + '/signatures/' + json.SignatureFileName + '?burst=' + getBurst());
        	                    $('#initialImage').attr('src', '../' + userSign + '/signatures/' + json.InitialFileName + '?burst=' + getBurst());
        	                 }, 500);
        	                $("#signPreview").show();
    	            	}
    	            },
    	            error: function(data) {
    	            }
        	  });
        }else{
       	 $("#signatoryNameDiv").find('p.jquery_form_error_message').remove();
         $("#signatoryNameDiv").append("<p class='jquery_form_error_message'>Signatory Name Is Required</p>");
         $("#signPreview").hide();
        }
       
    }


    let attachEvents = function() {
        $('.signature').off('click').on('click', function() {
            addSignAndScrollToNext($(this), false);
        });
        $('#btnSignNext').off('click').on('click', function() {
            addSignAndScrollToNext($(this), true);
        });
        $('#btnComplete').off('click').on('click', function() {
            Signer.signMyDocument()
        });
        $('.delete-sign').off('click').on('click', function(event) {
            Signer.deleteSign($(this));
            event.stopImmediatePropagation();
        });
    }

    let addSignAndScrollToNext = function(me, ondemand) {
        if (signingType === 1) {
            isRecipient = (localStorage.getItem('userId'));
            var userSign = isRecipient.replace('/unsigned.pdf', '');
        }
        if (userSign) {
            let signPlaceHolders = $('.signature').map((a, b) => {
                let meObj = $(b);
                return {
                    id: meObj.attr('id'),
                    dom: meObj,
                    position: {
                        top: parseFloat(meObj.css('top').replace('px', '')),
                        left: parseFloat(meObj.css('left').replace('px', ''))
                    }
                };
            }).get().sort(function(a, b) {
                return a.position.top - b.position.top
            });
            let thisIndex;
            let allsigns = $('.signature');
            if (!ondemand) {
                thisIndex = signPlaceHolders.findIndex((a) => {
                    return (a.id === me.attr('id'));
                });
                me.find('.signLabel').remove();
                me.find('a').show();
                if (me.find('.insertSignImg').length === 0) {
                    if ((localStorage.getItem('recipient'))) {
                        me.append('<a class="delete-sign"></a>');
                    }
                    if (me.hasClass('sign-initial')) {
                    	if(url == 'public'){
                    		me.append('<img src="' + userSign + '/signatures/initialsImage.jpeg" class="insertSignImg">');
                    	}else{
                    		me.append('<img src="../' + userSign + '/signatures/initialsImage.jpeg" class="insertSignImg">');
                    	}
                    } else {
                    	if(url == 'public'){
                    		 me.append('<img src="' + userSign + '/signatures/signedImage.jpeg" class="insertSignImg">');
                    	}else{
                    		 me.append('<img src="../' + userSign + '/signatures/signedImage.jpeg" class="insertSignImg">');
                    	}
                    }
                }
                let signedCount = $('.insertSignImg').length;
                if (allsigns.length !== signedCount) {
                    thisIndex++;
                }
                if (allsigns.length === thisIndex) {
                    thisIndex = signPlaceHolders.findIndex((a, index) => {
                        return (!a.dom.hasClass('insertedSign'));
                    });
                    thisIndex--;
                }
                me.addClass('insertedSign');
                validateSignCount();
                attachEvents();
            } else {
                let sigIndex = me.attr('index');
                if (sigIndex) {
                    thisIndex = parseInt(sigIndex, 10) + 1;
                    if (allsigns.length === thisIndex) {
                        thisIndex = signPlaceHolders.findIndex((a, index) => {
                            return (!a.dom.hasClass('insertedSign'));
                        });
                        thisIndex--;
                    }
                } else {
                    thisIndex = signPlaceHolders.findIndex((a) => {
                        return (!a.dom.hasClass('insertedSign'));
                    });
                    if (thisIndex === 0 && $('#pdf-preview').scrollTop() < 500) {
                        thisIndex = 1;
                    } else {
                        thisIndex--;
                    }
                }
                if (thisIndex < 0) {
                    thisIndex = 0;
                }

                let len = allsigns.length;
                for (let i = thisIndex; i < len; i++) {
                    if (!signPlaceHolders[i].dom.hasClass('insertedSign')) {
                        thisIndex = i;
                        me.attr('index', thisIndex);
                        break;
                    }
                }
            }

            let nextSign = signPlaceHolders[thisIndex];
            if (nextSign) {
                $('#pdf-preview').animate({
                    scrollTop: nextSign.position.top - 150
                }, 600);
            }
            $('#btnSignDoc').removeClass('disabled');
            allsigns.each(function() {
                if (!$(this).hasClass('insertedSign')) {
                    $('#btnSignDoc').addClass('disabled');
                }
            });
        }
    }

    let validateSignCount = function() {
        let signedCount = $('.insertSignImg').length;
        let totalSignatureCount = $('.signature').length;
        $('#btnSignDoc').addClass('disabled');
        if (signedCount === totalSignatureCount) {
            $('#btnSignNext').hide();
            $('#btnComplete').show();
            $('#btnSignDoc').removeClass('disabled');
        } else {
            if (totalSignatureCount > 0) {
                if (signingType === 1) {
                    $('#btnSignNext').show();
                }
            }
            $('#btnComplete').hide();
        }
    }

    let validateSigning = function() {
        $('.signature').each(() => {
            if (!$(this).hasClass('insertedSign')) {
                $('#btnSignDoc, #btnSignNext').addClass('disabled');
            }
        });
        if (signingType === 1) {
            $('#btnSignNext').addClass('disabled');
            if ($('.signature').length > 0) {
                $('#btnSignNext').removeClass('disabled');
            }
        }
    }

    //Load all pdf pages on screen
    let loadPages = function(pages, userId,sourceleadUrl) {
        if (pages) {
            pages = parseInt(pages, 10);
            let pageHtml = '';
            for (let i = 0; i < pages; i++) {
            	if(url == 'public'){
            		pageHtml += '<div class="pdf-page"><img src="' + userId + '-' + (i + 1) + '.png?burst=' + (genran()) + '"><div class="pdf-page-number">' + (i + 1) + '</div></div>'
            	}else{
            		pageHtml += '<div class="pdf-page"><img src="../' + userId + '-' + (i + 1) + '.png?burst=' + (genran()) + '"><div class="pdf-page-number">' + (i + 1) + '</div></div>'
            	}
            }
            if ($('#pdf-preview').length === 0) {
                $(".sign-container").append('<div id="pdf-preview"></div>');
            }
            $("#pdf-preview").html(pageHtml);
        }
    }
    


    //Sign document if valid
    let signMyDocument = function() {
        if (!$('#btnSignDoc').hasClass('disabled')) {
            let coordinates = localStorage.getItem('coordinates');
            let userId = localStorage.getItem('userId');
            let pages = localStorage.getItem('pages');
            let fontStyle = localStorage.getItem('fontStyle');
            let isRecipient = (localStorage.getItem('recipient'));
            if (isRecipient) {
                userId = localStorage.getItem('recipient');
            }
            processDocumentSigning(userId, fontStyle);
        }
    }

    // Call API to sign the document
    let processDocumentSigning = function(userId, fontStyle) {
        $('#pdf-preview').scrollTop(0);
        let data = getCoordinatesData(true);
        //Construct parameter
        let reqParams = {
            'signatures': data.signatures,
            'initials': data.initials,
            'userId': userId,
            'fontId': fontStyle,
            "signatureWidth": 65,
            "signatureHeight": 24,
            "initialsWidth": 50,
            "initialsHeight": 24
        }
        let firstInitial = $('#initial-0');
        if (firstInitial.length > 0) {
            let pos = firstInitial.find('img');
            if (pos.length > 0) {
                reqParams.initialsWidth = pos.width();
                reqParams.initialsHeight = pos.height() - 8;
            }
        }
        let firstSignature = $('#signature-0');
        if (firstSignature.length > 0) {
            let pos = firstSignature.find('img');
            if (pos.length > 0) {
                reqParams.signatureWidth = pos.width();
                reqParams.signatureHeight = pos.height() - 8;
            }
        }

        //Make api call
        
      	let sourceleadUrl = '';
    	if(url == 'public')
    		{
    		 sourceleadUrl = $("#sourceUrl").val();
    		 sourceleadUrl = sourceleadUrl.concat('/ws');
    		}
    	else
    		{
    		 sourceleadUrl = $("#sourceUrl").val();
    		}
        
    	let contractId = $("#contractId").val();
    	 $.ajax({
    	      type : "POST",
    	      data: JSON.stringify(reqParams),
              contentType: "application/json; charset=utf-8",
              dataType: "json",
    	       url: sourceleadUrl+"/signDocument?&contractId="+contractId,       
    	       success: function(data) {
               	var result = "";
               	if(url == 'public'){
                	result = result +"<div class='col-md-6 col-md-offset-3 inner-widget success-user'>";
                	result = result +"	<i class='fa fa-clock-o fa-3x noPad'></i>";
                	result = result +"<p class='reason-msg'>Document successfully signed..<br></p>";
                	$("#successPop").append(result);
                	$('.signContainer').hide();
                	$('#hideSig').hide();
               	}else{
               		result = result +"	<div class='project_inn_mask'></div>"; 
                	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
                	result = result +"		<p>Document successfully signed..</p>";
                	result = result +'		<b href="javascript:void(0)"><input id="confirm-id" type="button" value="Ok" onclick="successHidePopuop();"  style="background-color: #5cb85c; width : 100%"></b>';
                	result = result +"	</div>";
                	$("#popup").append(result);
                	$("#remove_user_pop").slideDown(200);
                	$(".project_inn_mask").fadeIn(200);
               	}
               },
               error: function(data) {
                result = result +"	<div class='project_inn_mask'></div>"; 
               	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
               	result = result +"		<p>Error occured..</p>";
               	result = result +'		<b href="javascript:void(0)"><input id="confirm-id" type="button" value="Ok" onclick="errorHidePopuop();" style="width : 100%"></b>';
               	result = result +"	</div>";
               	$("#popup").append(result);
               	$("#remove_user_pop").slideDown(200);
               	$(".project_inn_mask").fadeIn(200);
               }
    	  });
        
    }
    

    //Get query string
    let getQueryString = function(name, target) {
        name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
        var regexS = "[\\?&]" + name + "=([^&#]*)";
        var regex = new RegExp(regexS);
        var results = regex.exec(target);
        if (results == null)
            return "";
        else
            return decodeURIComponent(results[1].replace(/\+/g, " "));
    };

    //Get random burst number
    let getBurst = function() {
        return (new Date()).getTime();
    }

    //Initialize dragger ability
    let initializeDrager = function() {
        (function($) {
            $.fn.drags = function(opt) {

                opt = $.extend({
                    handle: "",
                    cursor: "move"
                }, opt);

                if (opt.handle === "") {
                    var $el = this;
                } else {
                    var $el = this.find(opt.handle);
                }

                return $el.css('cursor', opt.cursor).on("mousedown", function(e) {
                    if (opt.handle === "") {
                        var $drag = $(this).addClass('draggable');
                    } else {
                        var $drag = $(this).addClass('active-handle').parent().addClass('draggable');
                    }
                    var z_idx = $drag.css('z-index'),
                        drg_h = $drag.outerHeight(),
                        drg_w = $drag.outerWidth(),
                        pos_y = $drag.offset().top + drg_h - e.pageY,
                        pos_x = $drag.offset().left + drg_w - e.pageX;
                    $drag.css('z-index', 1000).parents().on("mousemove", function(e) {
                        $('.draggable').offset({
                            top: (e.pageY + pos_y - drg_h),
                            left: (e.pageX + pos_x - drg_w)
                        }).on("mouseup", function() {
                            $(this).removeClass('draggable').css('z-index', z_idx);
                        });
                    });
                    e.preventDefault(); // disable selection
                }).on("mouseup", function() {
                    if (opt.handle === "") {
                        $(this).removeClass('draggable');
                        Signer.saveCoordinates();
                    } else {
                        $(this).removeClass('active-handle').parent().removeClass('draggable');
                    }

                });

            }
        })(jQuery);
    }

    //Hide signature modal
    let hideSignModal = function() {
        $('#addSign,#backShadow').hide();
    }

    // Close new pdf file upload
    let closeUploadDoc = function() {
        $('#signUpload').hide();
        $('#signStatus').show();
    }

    //Store selected font style
    let storeStyle = function() {
        localStorage.setItem('fontStyle', $('#ddnFontStyle').val());
        localStorage.setItem('signature', $('#txtsignname').val());
        localStorage.setItem('initial', $('#txtinitialname').val());
        hideSignModal();
    }

    //Set sign type
    let setSigningType = function() {
        signingType = parseInt($('#ddnSignType').val(), 10);
        $('#signStatus').show();
        $('#btnSignNext').hide();
        if (signingType === 1) {
            $('#btnSignNext').show();
            $("#btnAddSignature").addClass("disabled","disabled");
            $("#btnAddInitial").addClass("disabled","disabled");
            $('#btnAddSignature').removeAttr("onClick");
            $('#btnAddInitial').removeAttr("onClick");
        }else {
        	 $('#btnSignNext').hide();
        	 $("#btnAddSignature").removeClass("disabled");
        	 $("#btnAddInitial").removeClass("disabled");
        	 $('#btnAddSignature').attr("onClick","Signer.addSignaturePlaceholder()");
             $('#btnAddInitial').attr("onClick","Signer.addInitialPlaceholder()");
        }
        validateSigning();
    }

    //Preview signature to user
    let previewSignature = function() {
    	$('#addSign,#backShadow').show();
    	$('#cancelId').show();
    }

    
    //Initialize Signer
    let init = function() {
        $(document).ready(() => {
        	$('#addSign,#backShadow').show();
        	$('#cancelId').hide();
        });
    }
    
//    init();
    
    //Exposed public methods
    return {
        uploadDoc: uploadDoc,
        addSignaturePlaceholder: addSignaturePlaceholder,
        addInitialPlaceholder: addInitialPlaceholder,
        saveCoordinates: saveCoordinates,
        deleteSign: deleteSign,
        signMyDocument: signMyDocument,
        hideSignModal: hideSignModal,
        createSignaturePreview: createSignaturePreview,
        storeStyle: storeStyle,
        previewSignature: previewSignature,
        getPageSettings: getPageSettings,
        setSigningType: setSigningType,
    }
})();

function successHidePopuop(){
	 $("#popUp").empty();
  	 $(".project_inn_mask").fadeOut(200);
  	 $("#remove_user_pop").hide();
  	 location.replace(sourceleadUrl+"/contractListbyStatus");
}

function errorHidePopuop(){
	 $("#popUp").empty();
	 $("#remove_user_pop").hide();
 	 $(".project_inn_mask").fadeOut(200);
}