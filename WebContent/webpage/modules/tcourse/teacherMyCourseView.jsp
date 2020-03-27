<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>课程信息查看</title>
<meta name="decorator" content="default" />
<style type="text/css">
</style>
<script type="text/javascript">
$(document).ready(function() {
	
});
</script>
<script type="text/javascript">

	function showLabDiv(num){
		$("li[id^='showLabLi']").each(function(index){
			if((index+1)==num){
				$(this).css("color","#09C7F7");
			}else{
				$(this).css("color","#000");
			}
			
		});
		var myNum = '';
		$("div[id^='showLabDiv']").each(function(index){
			if((index+1)==num){
				$(this).show();
				myNum = num;
			}else{
				$(this).hide();
			}
		});
		$('#iframeLab'+myNum).attr('src', $('#iframeLab'+myNum).attr('src'));
	}
</script>
</head>
<body >
	<!--头部 start-->
	<jsp:include page="/include/head.jsp"></jsp:include>
	<script type="text/javascript">
		$($("a",$("#topA"))[4]).attr("class","active");
	</script>
	<!--头部 end-->
		<div class="row">
			<div class="nav_menu">
				<a href="${pageContext.request.contextPath}/a/course/tcourseInfo/list">课程库</a>
				<a class="active"  href="${pageContext.request.contextPath}/a/course/tcourseInfo/findMyCourseInfo">我的课程</a>
				<a href="${pageContext.request.contextPath}/a/course/courseInfo/teacherFormBase/" >新建课程</a>
			</div>
			<table>
						<tr >
							<td rowspan="5">
								<img alt="image" width="300px" height="180px" src="${courseInfo.stuImg}">
							</td>
						</tr>
						<tr>
							<td style="padding:10px;font-size: 14px;width:100px;">
								课程名称：
							</td>
							<td style="text-align: left;padding: 5px;">
								${courseInfo.couName}&nbsp;&nbsp;<span><a href="#" onclick="openDialogView('预览资源', '${ctx}/teacherViewResourse?id=${courseInfo.resourceId}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 预览资源</a></span>
							</td>
						</tr>
						
						<tr>
							<td style="padding:10px;font-size: 14px;width:100px;">
								报名时间：
							</td>
							<td style="text-align: left;padding: 5px;">
								<fmt:formatDate value="${courseInfo.regStartTime}" pattern="yyyy-MM-dd"/>~
								<fmt:formatDate value="${courseInfo.regEndTime}" pattern="yyyy-MM-dd"/>
							</td>
						</tr>
						<tr>
							<td style="padding:10px;font-size: 14px;width:100px;">
								学习时间：
							</td>
							<td style="text-align: left;padding: 5px;">
								<fmt:formatDate value="${courseInfo.stuStartTime}" pattern="yyyy-MM-dd"/>~
								<fmt:formatDate value="${courseInfo.stuEndTime}" pattern="yyyy-MM-dd"/>
							</td>
						</tr>
						<tr>
							<td style="padding:10px;font-size: 14px;width:100px;">
								课程描述：
							</td>
							<td style="text-align: left;padding: 5px;word-break:break-all;line-height: 20px">
								${courseInfo.couDescribe}
							</td>
						</tr>
				</table>
			<div style="border-top:1px solid #d8d8d8;width:100%;padding: 5px">
				<ul style="margin-left: 30px" id="showLab">
					<li id="showLabLi1" style="float: left;width: 100px;font-weight: 800;color:#09C7F7;cursor: pointer;" onclick="showLabDiv('1')">报名审批</li>
					<li id="showLabLi2" style="float: left;width: 100px;font-weight: 800;cursor: pointer;" onclick="showLabDiv('2')">学员管理</li>
				</ul>
				<div style="padding:10px; display: block" id="showLabDiv1">
					<iframe  id="iframeLab1" style="border: none;width: 100%;height: 500px" src="${ctx}/course/tcourseInfo/techerSpCourseUser?courseId=${courseInfo.id}"></iframe>
				</div>
				<div style="padding:10px; display: none" id="showLabDiv2">
					<iframe  id="iframeLab2" style="border: none;width: 100%;height: 500px" src="${ctx}/course/tcourseInfo/showListCourRegUser?courseId=${courseInfo.id}"></iframe>
				</div>
			</div>
			</div>
<!--尾部 start-->
	<div class="copyright">© 云帆互联 2014 汽车服务网 京ICP备14016447号</div>
<!--尾部 end-->
</body>
</html>