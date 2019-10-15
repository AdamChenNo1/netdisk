$("#netdisk").click(function () {
    var data = this.serialize();
    data = (function (data) {
        data=data.replace(/&/g,"\",\"");
        data=data.replace(/=/g,"\":\"");
        data="{\""+data+"\"}";
        return data;
    })(data);
    $.ajax({
        url:this.href,
        type:"POST",
        data:data,
        contentType: "application/json",
        dataType:'json',
        success:function (result) {
            openUrl(result.extend.url);
        }
    })
})

function openUrl(url){
    var a = $('<a href="'+ url +'" target="_blank">链接</a>');  //生成一个临时链接对象
    var d = a.get(0);
    if(document.createEvent){
        var e = document.createEvent('MouseEvents');
        e.initEvent( 'click', true, true );  //模拟点击操作
        d.dispatchEvent(e);
        a.remove();   // 点击后移除该对象
    }
    else if(document.all){
        d.click();
    }
}
