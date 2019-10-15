/*各种方法*/
//1.请求部门信息并显示在下拉列表
function getDepts(ele) {
    $.ajax({
        url:basePath+"/depts",
        type:"GET",
        success:function (result) {
            /*console.log(result)*/
            //清空部门列表
            //$(ele).children().remove();
            $(ele).empty();
            //显示部门信息在下拉列表
            $.each(result.extend.depts,function () {
                var optionEle = $("<option></option>").append(this.deptName).attr("value",this.deptId);
                optionEle.appendTo(ele);
            });
        }
    });
}
//2.查询员工信息并显示在静态框
function getEmp(id){
    $.ajax({
        url:basePath+"/emp/"+id,
        type:"GET",
        success:function (result) {
            //console.log(result);
            var empData = result.extend.emp;
            //console.log(empData.empName);
            $("#empName_update_static").text(empData.empName);
            $("#email_update_input").val(empData.email);
            $("#empUpdateModal input[name=empGender]").val([empData.empGender]);
            $("#empUpdateModal select").val([empData.dId]);
        }
    });
}
