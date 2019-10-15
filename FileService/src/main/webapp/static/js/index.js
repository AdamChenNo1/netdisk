$(document).ready(function(){
    var rootPath = getRootPath();

    $.ajax({
        url:rootPath+"/netdisk/home",
        type:"GET",
        success:function (result) {
            //1.显示文件列表
            buildFileList(result);
        }
    })
})

function buildFileList(result) {


    $("#file_table").empty();
    var fileList = result.extend.info.fileList;
    $.each(fileList,function(index,item){
        var checkBoxTd = $("<td><input type='checkbox' class='check_item'></td>");
        var fileIdTd = $("<td></td>").append(index+1);
        var fileNameTd = $("<td></td>").append(item.fileName);
        var fileSizeTd = $("<td></td>").append(item.size);
        var fileAttrTd = $("<td></td>>").append(item.isFile);

        var downloadBtn = $("<button></button>")
            .addClass("btn btn-success btn-sm download_btn")
            .append(
                $("<span></span>")
                    .addClass("glyphicon glyphicon-arrow-down")
            ).append((item.isFile=="file")?"下载":"打开");
        //添加一个自定义属性，表示当前文件名称
        downloadBtn.attr("download_name",item.fileName);
        //downloadBtn.prop("href","netdisk/userspace/test/"+item.fileName);

        var deleteBtn = $("<button></button>")
            .addClass("btn btn-danger btn-sm delete_btn")
            .append(
                $("<span></span>")
                    .addClass("glyphicon glyphicon-trash")
            ).append("删除");
        //添加一个自定义属性，代表当前文件名称
        deleteBtn.attr("delete_name",item.fileName);
        var btnTd = $("<td align=\"center\"></td>").append(downloadBtn).append(" ").append(deleteBtn);
        $("<tr></tr>")
            .append(checkBoxTd)
            .append(fileIdTd)
            .append(fileNameTd)
            .append(fileSizeTd)
            .append(fileAttrTd)
            .append(btnTd)
            .appendTo("#file_table");
    })
}


