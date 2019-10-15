$(document).ajaxComplete(function(event,jqXHR, options){
    var redFlag = jqXHR.getResponseHeader("redirection");
    var redUrl = jqXHR.getResponseHeader("loginPath");
    if(redFlag == "noAuth"){
        window.location.href = redUrl;
    }
});