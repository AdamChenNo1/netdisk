$("#fileAddModal").on("show.bs.modal",function () {
    //文件输入框初始化
    $("#fileInput").fileinput({
        language: 'zh',
        showPreview:true,
        showUpload:true,
        dropZoneEnabled: true,
        browseOnZoneClick:true,
        uploadUrl: getRootPath() + "/netdisk/home",
        uploadAsync:false,
        maxFileSize: 30720,
    }).on('filebatchuploadcomplete',function () {
        $.ajax({
            url:getRootPath()+"/netdisk/home",
            type:"GET",
            success:function (result) {
                //1.显示文件列表
                buildFileList(result);
            }
        });

    });
    $("#continueBtn").on('click',function () {
        $("#fileInput").fileinput('clear');
    });

});



$("#fileAddModal").on("hide.bs.modal",function () {
    $("#fileInput").fileinput('clear');
})


