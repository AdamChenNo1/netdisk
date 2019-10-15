<%--
  Created by IntelliJ IDEA.
  User: 陈依伦
  Date: 2019/8/10
  Time: 18:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="content-type" content="text/html" charset="utf-8">
    <title>员工列表</title>
    <!--引入样式-->
    <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <!-- 可选的 Bootstrap 主题文件（一般不用引入） -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
</head>
<body>
<!--搭建显示页面-->
<div class="container">
    <!--标题-->
    <div class="row">
        <div class="col-md-12">
            <h1>SSM-CRUD</h1>
        </div>
    </div>
    <!--按钮-->
    <div class="row">
        <div class="col-md-4 col-md-offset-8">
            <button class="btn btn-primary">
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
            </button>
            <button class="btn btn-danger">
                <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>删除
            </button>
        </div>
    </div>
    <!--表格数据-->
    <div class="row">
        <div class="col-md-12">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>#</th>
                    <th>#empName</th>
                    <th>gender</th>
                    <th>email</th>
                    <th>deptName</th>
                    <th>操作</th>
                </tr>

                </thead>
                <tbody>
                <c:forEach items="${pageInfo.list}" var="emp">
                    <tr>
                        <th>${emp.empId}</th>
                        <th>${emp.empName}</th>
                        <th>${emp.empGender=="M"?"男":"女"}</th>
                        <th>${emp.email}</th>
                        <th>${emp.department.deptName}</th>
                        <th>
                            <button type="button" class="btn btn-warning" aria-label="Left Align">
                                <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>编辑
                            </button>
                            <button type="button" class="btn btn-danger" aria-label="Left Align">
                                <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>删除
                            </button>
                        </th>
                    </tr>
                </c:forEach>

                </tbody>
            </table>
        </div>
    </div>
    <!--分页信息-->
    <div class="row">
        <!-- 分页文字信息 -->
        <div class="col-md-6">
            当前第${pageInfo.pageNum}页，总共${pageInfo.pages}页，共${pageInfo.total}条记录
        </div>
        <!-- 分页条 -->
        <div class="col-md-6">
            <nav aria-label="Page navigation">
                <ul class="pagination">
                    <li>
                        <a href="emps?pn=1">首页</a>
                    </li>
                    <c:if test="${pageInfo.hasPreviousPage}">
                        <li>
                            <a href="#" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>
                    </c:if>

                    <c:forEach items="${pageInfo.navigatepageNums}" var="page_Num">
                        <c:if test="${page_Num == pageInfo.pageNum}">
                            <li class="active"><a href="#">${page_Num}</a></li>
                        </c:if>
                        <c:if test="${page_Num != pageInfo.pageNum}">
                            <li ><a href="emps?pn=${page_Num}">${page_Num}</a></li>
                        </c:if>
                    </c:forEach>

                    <c:if test="${pageInfo.hasNextPage}">
                        <li>
                            <a href="emps?pn=${pageInfo.pageNum+1}" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                    </c:if>
                    <li>
                        <a href="emps?pn=${pageInfo.pages}">末页</a>
                    </li>
                </ul>
            </nav>
        </div>
    </div>
</div>

<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
<script src="https://cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js"></script>
<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"></script>
</body>
</html>
