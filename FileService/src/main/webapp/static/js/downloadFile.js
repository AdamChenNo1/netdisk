$(document).on("click",".download_btn",function(){
    var fileName = $(this).attr("download_name");
    window.location.href = getRootPath() + "/netdisk/home/" + fileName;
});