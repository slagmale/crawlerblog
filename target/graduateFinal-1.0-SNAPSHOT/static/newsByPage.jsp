<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/4/10
  Time: 19:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<html>
<head>
    <title>今日新闻</title>
    <link rel="stylesheet" href="../css/articlelist.css">
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="../css/articlelist.css">
</head>
<body>
<h2>
    <a href="https://news.cnblogs.com/n/digg?type=today">博客园今日热门新闻</a>
</h2>


<div id="article">
    <c:forEach items="${newsList}" var="item">
        <div id="title">
            <h2 class="text-primary">${item.title}</h2>
        </div>

        <div id="content">
            <a href="/news/selectNewsById.action?id=${item.id}">阅读全文</a>
        </div>
        <br>
    </c:forEach>
    <font size="2">共 ${page.totalPageCount} 页</font> <font size="2">第
    ${page.pageNow} 页</font> <a href="${pageContext.request.contextPath}/news/selectNewsByPage.action?pageNow=1">首页</a>
    <c:choose>
        <c:when test="${page.pageNow - 1 > 0}">
            <a href="${pageContext.request.contextPath}/news/selectNewsByPage.action?pageNow=${page.pageNow - 1}">上一页</a>
        </c:when>
        <c:when test="${page.pageNow - 1 <= 0}">
            <a href="${pageContext.request.contextPath}/news/selectNewsByPage.action?pageNow=1">上一页</a>
        </c:when>
    </c:choose>
    <c:choose>
        <c:when test="${page.totalPageCount==0}">
            <a href="${pageContext.request.contextPath}/news/selectNewsByPage.action?pageNow=${page.pageNow}">下一页</a>
        </c:when>
        <c:when test="${page.pageNow + 1 < page.totalPageCount}">
            <a href="${pageContext.request.contextPath}/news/selectNewsByPage.action?pageNow=${page.pageNow + 1}">下一页</a>
        </c:when>
        <c:when test="${page.pageNow + 1 >= page.totalPageCount}">
            <a href="${pageContext.request.contextPath}/news/selectNewsByPage.action?pageNow=${page.totalPageCount}">下一页</a>
        </c:when>
    </c:choose>
    <c:choose>
        <c:when test="${page.totalPageCount==0}">
            <a href="${pageContext.request.contextPath}/news/selectNewsByPage.action?pageNow=${page.pageNow}">尾页</a>
        </c:when>
        <c:otherwise>
            <a href="${pageContext.request.contextPath}/news/selectNewsByPage.action?pageNow=${page.totalPageCount}">尾页</a>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
