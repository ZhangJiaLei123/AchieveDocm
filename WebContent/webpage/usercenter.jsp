<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/usercenter/css/common.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/usercenter/css/shopsManager.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/usercenter/js/jquery-1.8.0.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/usercenter/js/common.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/usercenter/js/navTop.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/usercenter/js/jquery.circliful.min.js"></script>
		<title>个人中心</title>
	</head>
	<body>
	<jsp:include page="/include/head.jsp"></jsp:include>
	<script type="text/javascript">
		$($("a",$("#topA"))[3]).attr("class","active");
		function search(){
			var keywords = $("#keywords").val() 
			alert(keywords);
		}
	</script>	
		<!--[if lte IE 7]>
	<div class="old-browser-popup" id="old" >
	    亲，您当前正在使用旧版本的IE浏览器，为了保证您的浏览体验，鹰目建议您使用：
	    <label class="chrome-borwser-ico"></label>
	    <a href="http://rj.baidu.com/soft/detail/14744.html?ald" target="_blank">谷歌浏览器</a>&nbsp;或&nbsp;&nbsp;
	    <label class="firefox-borwser-ico"></label>
	    <a href="http://rj.baidu.com/soft/detail/11843.html?ald" target="_blank">火狐浏览器</a>
	    <span id="oldclose"></span>
	</div>
	<![endif]-->

		<!-- 内容  开始-->
		<div class="wrap">
			<div class="vip_cont c100 clearfix">
				<!--左边列表导航  开始-->
				<div class="fl vip_left vip_magLeft">
					<dl>
						<dt>我的账号</dt>
						<dd>
							<p><a href="${pageContext.request.contextPath}/a/sys/user/usercenter">基本资料</a></p>
							<p><a href="${pageContext.request.contextPath}/webpage/usercenter-changepasswd.jsp">修改密码</a></p>
						</dd>
					</dl>
				</div>
				<!--左边列表导航  结束-->

				<!--右边列表内容  开始-->
				<div class="fr vip_right vip_magRight">
					<!--用户信息  开始 -->
					<div class="cus01">
						<div class="cusImg">
							<img src="${pageContext.request.contextPath}/images/headimg.png"/>
						</div>
						<div class="cusName">
							<p title="用户名称">${user.name}</p>
							<span title="分众传媒有限公司">邮箱：${user.email}</span>
							<span>电话：${user.phone}</span>
						</div>
					</div>
					<!-- 用户信息  结束 -->
				</div>
			</div>
		</div>

		<!-- 内容  结束-->
	</body>

</html>