<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>课程查看</title>
<meta name="decorator" content="default" />
<style type="text/css">
#head {
	height: 40px;
	width: 100%;
	text-align: center;
	position: relative;
}

#head ul {
	padding: 0;
	margin: 0 auto;
	height: 100%;
	text-align: center;
	width: 360px;
	height: 30px;
	position: relative;
}

#head ul li {
	list-style: none;
	float: left;
	text-decoration: none;
	display: block;
	width: 80px;
	padding: 5px;
	margin: 0px 0px 0px 0px;
	text-align: center;
	background: #D8D8D8;
}

#head ul li a {
	text-decoration: none;
	color: #000000;
}

#head ul li a:visited {
	text-decoration: none;
	color: #000000;
}

.tab-body {
    padding:10px;
    background:#D8D8D8;
	text-align: center;
	margin: 0 auto;
	width: 80%;
	border: 1px solid #360;
	height: 400px;
}

.tab-body iframe {
    width: 100%;
    height: 100%;
	margin: 0 auto;
}

.tab-body-div {
	background-color: #D8D8D8;
	border: 1px solid #360;
	display: none;
	width: 400px;
	height: 220px;
	margin-top: 0px;
	z-index: 1;
}
</style>
<script type="text/javascript">
	$(document).ready(function() {
	});
</script>
<script type="text/javascript">
	function tabChange(num,courseid,type) {
		var i;
		
			switch (num){
			case 1:document.getElementById("tabIframe").src = 
				"${ctx}/course/userCourse/liststudentinfo?courseid="+courseid+"&type="+type;
				break;
			case 2:document.getElementById("tabIframe").src = 
				"${ctx}/course/userCourse/liststudentinfo?courseid="+courseid+"&type="+type;;
				break;
			case 3:document.getElementById("tabIframe").src = 
				"${ctx}/course/userCourseReg/listbycourseid?courseid="+courseid;
				break;
			case 4:document.getElementById("tabIframe").src = 
				"${ctx}/course/userCourse/showlist?courseid="+courseid;;
				break;
			}
	    for (i = 1; i <= 4; i++) {
		if (i == num) {
				//document.getElementById("d" + i).style.display = "block";
				document.getElementById("L" + i).style.background = "#0080FF";
			} else {
				//document.getElementById("d" + i).style.display = "none";
				document.getElementById("L" + i).style.background = "#D8D8D8";
			}
		}
	}
	var validateForm;
	function doSubmit() {//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		if (validateForm.form()) {
			$("#inputForm").submit();
			return true;
		}

		return false;
	}
	$(document).ready(
			function() {
				validateForm = $("#inputForm")
						.validate(
								{
									submitHandler : function(form) {
										loading('正在提交，请稍等...');
										form.submit();
									},
									errorContainer : "#messageBox",
									errorPlacement : function(error, element) {
										$("#messageBox").text("输入有误，请先更正。");
										if (element.is(":checkbox")
												|| element.is(":radio")
												|| element.parent().is(
														".input-append")) {
											error.appendTo(element.parent()
													.parent());
										} else {
											error.insertAfter(element);
										}
									}
								});

			});
</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="ibox" style="margin-bottom: 0px;">
			<div class="ibox-title">
				<h5>
					<a href="${ctx}/course/courseInfo"> 课程管理 </a>
				</h5>
			</div>
			<div class="ibox-content">
				<sys:message content="${message}" />
				<!-- 课程信息 -->
				<div style="height: 150px;">
					<div id="left"
						style="float: left; width: 60%; height: 100%; margin-top: 20px;">
						<img alt="image" class="img-circle" src="${courseInfo.stuImg}">
					</div>
					<div id="right"
						style="float: left; width: 36%; height: 100%; margin-left: 2%;">
						<label>课程名称：${courseInfo.couName}</label> 
						<br/>
						<label>报名时间：
						<fmt:formatDate value="${courseInfo.regStartTime}" pattern="yyyy-MM-dd"/>~
						<fmt:formatDate value="${courseInfo.regEndTime}" pattern="yyyy-MM-dd"/>
						</label>
						<br/>
						<label>学习时间：
						<fmt:formatDate value="${courseInfo.stuStartTime}" pattern="yyyy-MM-dd"/>~
						<fmt:formatDate value="${courseInfo.stuEndTime}" pattern="yyyy-MM-dd"/>
						</label>
						<br/>
						<label>课程描述：${courseInfo.couDescribe}</label>
					</div>
				</div>
				<hr style="height: 2px; border: none; border-top: 2px dotted red;" />
				<div id="head" style="text-align: center;">
					<ul>
						<li id="L1" onclick="tabChange(1,'${courseInfo.id}',0)" style="background: #0080FF;"><a
							href="#">必学人员</a></li>
						<li id="L2" onclick="tabChange(2,'${courseInfo.id}',1)"><a href="#">可报名人员</a></li>
						<shiro:hasPermission name="course:userCourseReg:list">
						<li id="L3" onclick="tabChange(3,'${courseInfo.id}',0)"><a href="#">报名审批</a></li>
						</shiro:hasPermission>
						<li id="L4" onclick="tabChange(4,'${courseInfo.id}',0)"><a href="#">学员管理</a></li>
					</ul>
				</div>

				<div class="tab-body">
					<iframe id="tabIframe" src="${ctx}/course/userCourse/liststudentinfo?courseid=${courseInfo.id}&type=0"></iframe>
				</div>


				<!-- 
				<div class="tab-body">
					<div id="d1" class="tab-body-div" style="display: block;">
						<ul>
							<li>这里是最新公告信息</li>
							<li>这里是最新公告信息</li>
							<li>这里是最新公告信息</li>
							<li>这里是最新公告信息</li>
						</ul>
					</div>
					<div id="d2" class="tab-body-div">
						<ul>
							<li>这里是今日热点</li>
							<li>这里是今日热点</li>
							<li>这里是今日热点</li>
							<li>这里是今日热点</li>
						</ul>
					</div>
					<div id="d3" class="tab-body-div">
						<ul>
							<li>这里是最新新闻</li>
							<li>这里是最新新闻</li>
							<li>这里是最新新闻</li>
							<li>这里是最新新闻</li>
						</ul>
					</div>
					<div id="d4" class="tab-body-div">
						<ul>
							<li>这里是考试信息</li>
							<li>这里是考试信息</li>
							<li>这里是考试信息</li>
							<li>这里是考试信息</li>
						</ul>
					</div>
				</div> -->
				<br /> <br />
			</div>
		</div>
	</div>
</body>
</html>