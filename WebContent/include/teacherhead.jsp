<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<link href="${pageContext.request.contextPath}/css/global.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/x_pc.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/js/jquery-1.12.4.min.js"></script>
<script src="${pageContext.request.contextPath}/js/xiong.js"></script>
 <div class="top">
	<div class="row">
    	<div class="top_fl fl">欢迎您访问吱吱文档管理系统</div>
        <div class="top_fr fr">
            ${fns:getUser().name} [资深]
        	<a class="login" href="${pageContext.request.contextPath}/a/logout">退出</a>
        </div>
    </div>
</div>
<!--头部 start-->
<div class="head">
	<div class="row">
    	<a class="logo fl"><%-- <img src="${pageContext.request.contextPath}/img/logo.png"> --%></a>
        <div class="nav fr" id="topA">
        	<a href="${pageContext.request.contextPath}/a/teacherindex">首页</a>
            <!--<a href="${pageContext.request.contextPath}/a/selectOfficeInfo">组织人员上</a>-->
            <!-- <a href="${pageContext.request.contextPath}/a/findStudyResourse">学习资源</a>-->
            <a href="${pageContext.request.contextPath}/a/findMesanInfo">全部资料</a>
            <a href="${pageContext.request.contextPath}/a/findMyMesanInfo">我的资料</a>
            <a href="${pageContext.request.contextPath}/a/findMesanInfo">个人中心</a>
            <!--<a href="${pageContext.request.contextPath}/a/course/tcourseInfo/list">课程</a>-->
            <!--<a href="${pageContext.request.contextPath}/a/studyActivityList">学习活动</a>-->
            <!-- <a href="">数据统计</a> -->
        </div>
    </div>
    <script type="text/javascript">
        new BannerTurn('.ban_box','yes',3.3,5000);
    </script>
</div>
 

<!--头部 end-->