<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>全部活动</title>
<meta name="decorator" content="default"/>
<meta charset="utf-8">
</head>
<script type="text/javascript">
function submitForm(pageNo){
	$("#pageNo").val(pageNo);
	$("#studyActiveForm").submit();
}

</script>
<body>
<input type="hidden" id="showResourceId">

<!--头部 start-->
<jsp:include page="/include/head.jsp"></jsp:include>
<script type="text/javascript">
	$($("a",$("#topA"))[5]).attr("class","active");

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
	<!--头部 end-->
	<!--身体 start-->
	<div class="maincontent" style="width: 100%;">
		<div class="row">
			<div class="nav_menu">
				<a href="${pageContext.request.contextPath}/a/studyActivityList">学习活动计划</a>
				<a href="${pageContext.request.contextPath}/a/gzActivityList">关注计划</a>
				<a href="${pageContext.request.contextPath}/a/teacherSstudyActivity">全部学习活动</a>
				<a href="${pageContext.request.contextPath}/a/train/studyActivity/teacherFormBase">新建学习活动</a>
				<a class="active"
					href="${pageContext.request.contextPath}/a/myTeacherStudyActivity">我的学习活动</a>
			</div>
		</div>
		<div class="maincontent">
			<div class="wrapper wrapper-content">
				<table>
						<tr>
							<td rowspan="4">
								<img alt="image" width="300px" height="180px" src="${studyActivity.actImg}">
							</td>
						</tr>
						<tr height="20px">
							<td style="width:100px;padding-left: 10px;">
								开课时间段：
							</td>
							<td style="text-align: left;padding: 5px;">
								<fmt:formatDate value="${studyActivity.studyStartTime}" pattern="yyyy-MM-dd"/>&nbsp;~
								<fmt:formatDate value="${studyActivity.studyEndTime}" pattern="yyyy-MM-dd"/>
							</td>
							<td style="width:80px;height: 30px;">
								活动类型：
							</td>
							<td style="text-align: left;padding: 5px;">
								${fns:getDictLabel(studyActivity.actType,'activity_type','')}
							</td>
							<td style="width:80px;height: 30px;">
								总人数：
							</td>
							<td style="text-align: left;padding: 5px;">
								${studyActivity.userCount}
							</td>
						</tr>
						<tr>
							<td  style="width:100px;padding-left: 10px;">
								报名时间段：
							</td>
							<td style="text-align: left;padding: 5px;">
								<fmt:formatDate value="${studyActivity.applyStartTime}" pattern="yyyy-MM-dd"/>&nbsp;~
								<fmt:formatDate value="${studyActivity.applyEndTime}" pattern="yyyy-MM-dd"/>
							</td>
							<td>
								活动地点：
							</td>
							<td style="text-align: left;padding: 5px;" colspan="3">
								${studyActivity.space}
							</td>
						</tr>
						<tr>
							<td  style="width:100px;padding-left: 10px;">
								活动描述：
							</td>
							<td colspan="5" style="text-align: left;padding: 5px;line-height: 20px;word-break:break-all">
								${studyActivity.remarks}
							</td>
						</tr>
				</table>
			</div>
			<div style="border-top:1px solid #d8d8d8;width:100%;padding: 5px">
				<ul style="margin-left: 30px" id="showLab">
					<li id="showLabLi1" style="float: left;width: 100px;font-weight: 800;cursor: pointer;color: #09C7F7;" onclick="showLabDiv('1')">报名审批</li>
					<li id="showLabLi2" style="float: left;width: 100px;font-weight: 800;cursor: pointer;" onclick="showLabDiv('2')">学员管理</li>
					<li id="showLabLi3" style="float: left;width: 100px;font-weight: 800;cursor: pointer;" onclick="showLabDiv('3')">编辑目录</li>
					<li id="showLabLi4" style="float: left;width: 100px;font-weight: 800;cursor: pointer;" onclick="showLabDiv('4')">成绩设定</li>
					<li id="showLabLi5" style="float: left;width: 100px;font-weight: 800;cursor: pointer;" onclick="showLabDiv('5')">授课时间</li>
				</ul>
				<div style="padding:10px;" id="showLabDiv1">
					<iframe id="iframeLab1" style="border: none;width: 100%;height: 100%;height: 500px" src="${ctx}/techerSpStudyUser?activityId=${studyActivity.id}"></iframe>
				</div>
				<div style="padding:10px;display: none" id="showLabDiv2">
					<iframe id="iframeLab2" style="border: none;width: 100%;height: 100%;height: 500px" src="${ctx}/listbyactivityid?activityId=${studyActivity.id}"></iframe>
				</div>
				<div style="padding:10px; display: none" id="showLabDiv3">
					<iframe  id="iframeLab3" style="border: none;width: 100%;height: 500px" src="${ctx}/teacherActivityDirListview?activityId=${studyActivity.id}"></iframe>
				</div>
				<div style="padding:10px; display: none" id="showLabDiv4">
					<iframe  id="iframeLab4" style="border: none;width: 100%;height: 700px" src="${ctx}/teacherAllListViewScore?activityId=${studyActivity.id}"></iframe>
				</div>
				<div style="padding:10px; display: none" id="showLabDiv5">
					<iframe  id="iframeLab5" style="border: none;width: 100%;height: 500px" src="${ctx}/teacherLessonTimeList?activityId=${studyActivity.id}"></iframe>
				</div>
			</div>
		</div>
	</div>
	<!--尾部 start-->
	<div class="copyright">© 云帆互联 2014 汽车服务网 京ICP备14016447号</div>
	<!--尾部 end-->

</body>
</html>
    