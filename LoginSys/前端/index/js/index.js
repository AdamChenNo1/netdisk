var totalRecord,currentPage;
//1.页面加载完成后，发送ajax请求首页数据
$(function(){
    to_page(1);
});
function to_page(pn) {
    $.ajax({
        url:basePath+"/emps",
        data:"pn="+pn,
        type:"get",
        success:function(result){
            //console.log(result);
            //window.alert("1");
            // alert(result.extend.pageInfo.total);
            //1.显示员工数据
            build_emps_table(result);
            //2.显示分页信息
            build_page_info(result);
            //3.显示分页条信息
            build_page_nav(result);
        }
    });
}
//2.解析员工数据信息
function build_emps_table(result) {
    //清空表格信息
    $("#emps_table").empty();
    var emps = result.extend.pageInfo.list;
    $.each(emps,function(index,item){
        //alert(item.empName);
        var checkBoxId = $("<td><input type='checkbox' class='check_item'></td>");
        var empIdTd = $("<td></td>").append(item.empId);
        var empNameTd = $("<td></td>").append(item.empName);
        var empGenderTd = $("<td></td>").append(item.empGender == "M"?"男":"女");
        var emailTd = $("<td></td>").append(item.email);
        var empDeptTd = $("<td></td>").append(item.department.deptName);
        var editBtn = $("<button></button>")
            .addClass("btn btn-warning btn-sm edit_btn")
            .append(
                $("<span></span>")
                    .addClass("glyphicon glyphicon-pencil")
            ).append("编辑");
        //添加一个自定义属性，表示当前员工id
        editBtn.attr("edit_id",item.empId);
        var deleteBtn = $("<button></button>")
            .addClass("btn btn-danger btn-sm delete_btn")
            .append(
                $("<span></span>")
                    .addClass("glyphicon glyphicon-trash")
            ).append("删除");
        //添加一个自定义属性，代表当前员工id
        deleteBtn.attr("delete_id",item.empId);
        var btnTd = $("<td></td>").append(editBtn).append(" ").append(deleteBtn);
        $("<tr></tr>")
            .append(checkBoxId)
            .append(empIdTd)
            .append(empNameTd)
            .append(empGenderTd)
            .append(emailTd)
            .append(empDeptTd)
            .append(btnTd)
            .appendTo("#emps_table");
    });
}
//3.解析分页信息
function build_page_info(result) {
    //先清空分页信息
    $("#page_info_area").empty();
    $("#page_info_area").append("当前第"+result.extend.pageInfo.pageNum+"页，总共"+result.extend.pageInfo.pages+"页，共"+result.extend.pageInfo.total+"条记录");
    totalRecord = result.extend.pageInfo.total;
    currentPage = result.extend.pageInfo.pageNum;
}
//4.解析分页条信息
function build_page_nav(result) {
    //先清空分页条信息
    $("#page_nav_area").empty();
    var pi = result.extend.pageInfo;
    var ul = $("<ul></ul>").addClass("pagination")

    var firstPageLi = $("<li></li>").append($("<a></a>").append("首页"));
    var prePageLi = $("<li></li>").append($("<a></a>").append("&laquo;"));
    if(pi.hasPreviousPage == false){
        firstPageLi.addClass("disabled");
        prePageLi.addClass("disabled");
    }else{
        firstPageLi.click(function () {
            to_page(1);
        })
        prePageLi.click(function () {
            to_page(pi.prePage);
        })
    }

    var nextPageLi = $("<li></li>").append($("<a></a>").append("&raquo;"));
    var lastPageLi = $("<li></li>").append($("<a></a>").append("末页"));
    if(pi.hasNextPage == false){
        nextPageLi.addClass("disabled");
        lastPageLi.addClass("disabled");
    }else{
        nextPageLi.click(function () {
            to_page(pi.nextPage);
        })
        lastPageLi.click(function () {
            to_page(pi.pages);
        })
    }

    //添加首页和前一页
    ul.append(firstPageLi).append(prePageLi);
    //每一页配置
    $.each(pi.navigatepageNums,function (index,item) {
        var pageNumLi = $("<li></li>").append($("<a></a>").append(item));
        if(pi.pageNum == item){
            pageNumLi.addClass("active");
        }
        ul.append(pageNumLi)
        pageNumLi.click(function () {
            to_page(item);
        })
    });
    //添加下一页和末页
    ul.append(nextPageLi).append(lastPageLi);
    var navEle  = $("<nav></nav>").append(ul);
    navEle.appendTo("#page_nav_area");
}