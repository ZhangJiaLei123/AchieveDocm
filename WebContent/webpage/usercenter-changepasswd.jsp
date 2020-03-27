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
	    <style>  
			#login_click{margin-top:30px; height:10px;}  
			#login_click a   
			{  
			    text-decoration:none;  
			    background:	#18A589;  
			    color:#f2f2f2;  
			    padding: 8px 20px 8px 20px;  
			    font-size:16px;  
			    font-family: 微软雅黑,宋体,Arial,Helvetica,Verdana,sans-serif;  
			    font-weight:bold;  
			    border-radius:3px;  
			    -webkit-transition:all linear 0.30s;  
			    -moz-transition:all linear 0.30s;  
			    transition:all linear 0.30s;  
			    }  
			   #login_click a:hover { background:#385f9e; }  
		</style> 		
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
				<form action="${pageContext.request.contextPath}/resourcedownload/tResourceDownload/modPassword" method="post">
					<div class="fr vip_right vip_magRight">
						<!--用户信息  开始 -->
						<div class="cus01">
							<div class="cusName">
								<span>&nbsp;&nbsp;&nbsp;&nbsp;原密码：<input type="password" id="oldPassword" name="oldPassword" style="border: 1px;border-style:solid;"></span>
								<span>&nbsp;&nbsp;&nbsp;&nbsp;新密码：<input type="password" id="newPassword_a" name="newPassword_a" style="border: 1px;border-style:solid;"></span>
								<span> 确认密码：<input type="password" id="newPassword_b" name="newPassword_b" style="border: 1px;border-style:solid;"></span>
								<div id="login_click" class="login_click">  
			       				 	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			       				 	<a id="btlogin" href="javascript:modpassword()">提交</a>  
			       				</div>  
							</div>
						</div>
						<!-- 用户信息  结束 -->
					</div>
				</form>
			</div>
		</div>
		<script type="text/javascript">
			function modpassword(){
				var oldPassword = $("#oldPassword").val()
				var newPassword_a = $("#newPassword_a").val();
				var newPassword_b = $("#newPassword_b").val();
				if(oldPassword.replace(/(^s*)|(s*$)/g, "").length ==0){
					alert("请输入旧密码!");
					return false;
				}
				if(newPassword_a.replace(/(^s*)|(s*$)/g, "").length ==0){
					alert("请输入新密码!");
					return false;
				}
				if(newPassword_a!=newPassword_b){
					alert("两次密码输入不一致");
					return false;
				}
				var url = "${pageContext.request.contextPath}/a/sys/user/modPassword";
			    $.ajax({
			        type: "post",
			        url: url,
			        data: {"oldPassword":oldPassword,"newPassword":newPassword_a,"confirmNewPassword":newPassword_b},
			        cache: false,
			        async : false,
			        dataType: "json",
			        success: function (data ,textStatus, jqXHR)
			        {
			        	alert(data.message);
			        },
			        error:function (XMLHttpRequest, textStatus, errorThrown) {
			            alert("请求失败!");
			        }
			     });
			}
		</script>
	</body>
</html>