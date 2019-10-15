//每次隐藏modal，清空验证内容
$(".modal").on("hide.bs.modal", function () {
    $(this).find('.form').bootstrapValidator('destroy');
})
