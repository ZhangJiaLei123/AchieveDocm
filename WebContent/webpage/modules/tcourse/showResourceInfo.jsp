<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>课程管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	
	</script>
</head>
<body>
		<table style="width:800px;border:none;" class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
			<tr>
				<td class="width-15 active" width="170px"><label class="pull-right">课程名称：</label></td>
				<td class="width-35 ">
					${courseInfo.couName}
				</td>
				<td class="width-15 active"><label class="pull-right">资源名称：</label></td>
				<td class="width-15 ">${courseInfo.resResourceName}</td>
				<td class="width-15 active"><label class="pull-right">通过标准：</label></td>
				<td class="width-15 ">${courseInfo.byStandard}${courseInfo.byMark}</td>
			</tr>
			<tr>
				<td class="width-15 active"><label class="pull-right">课程描述：</label></td>
				<td class="width-35 " colspan="5" style="word-break:break-all">
					${courseInfo.couDescribe}
				</td>
				
			</tr>
			<tr>
				<td class="width-15 active"><label class="pull-right">报名时间：</label></td>
				<td class="width-35 " colspan="5">
					<fmt:formatDate value="${courseInfo.regStartTime}" pattern="yyyy-MM-dd"/>~<fmt:formatDate value="${courseInfo.regEndTime}" pattern="yyyy-MM-dd"/>
				</td>
				
			</tr>
			<tr>
				<td class="width-15 active"><label class="pull-right">学习时间：</label></td>
				<td class="width-35 " colspan="5">
					<fmt:formatDate value="${courseInfo.stuStartTime}" pattern="yyyy-MM-dd"/>~<fmt:formatDate value="${courseInfo.stuEndTime}" pattern="yyyy-MM-dd"/>
				</td>
				
			</tr>
			<tr>
				<td class="width-15 active"><label class="pull-right">课程图片：</label></td>
				<td class="width-35 " colspan="5">
					<img src="${ctx}/${courseInfo.stuImg}" title="课程图片" width:="100px;" height="100px">
				</td>
				
			</tr>
				<tr>
				<td class="width-15 active"><label class="pull-right">必报名机构列表</label></td>
				<td colspan="5">
					<iframe id="iframeBxOffeic" width="98%" height="240px" style="border: none" src="${ctx}/course/courseInfo/showOfficeCourseList?id=${courseInfo.id}&type=1&a="+Math.random()></iframe>
				</td>
			</tr>
			<tr>
				<td class="width-15 active">
					<label class="pull-right">人员岗位：</label>
				</td>
				<td colspan="5">
					<c:forEach items="${lsBxPostCourse}" var="postCourse">
						<span>${postCourse.postName}-${postCourse.postLevelName}&nbsp;&nbsp;</span>
					</c:forEach>
				</td>
			</tr>
			</tr>
				<tr>
				<td class="width-15 active"><label class="pull-right">可报名机构列表</label></td>
				<td colspan="5">
					<iframe id="iframeBxOffeic" width="98%" height="240px" style="border: none" src="${ctx}/course/courseInfo/showOfficeCourseList?id=${courseInfo.id}&type=1&a="+Math.random()></iframe>
				</td>
			</tr>
			<tr>
				<td class="width-15 active">
					<label class="pull-right">是否选择学员：</label>
				</td>
				<td colspan="5">
					${courseInfo.isBxUser==1?"是":"否" }
				</td>
			</tr>
			<tr>
				<td class="width-15 active">
					<label class="pull-right">必须报名人员列表：</label>
				</td>
				<td colspan="5">
					<iframe id="iframeBxOffeic" width="98%" height="240px" style="border: none" src="${ctx}/course/courseInfo/showUserCourseList?id=${courseInfo.id}&type=0&a="+Math.random()></iframe>
				</td>
			</tr>
				<tr>
			<td class="width-15 active">
					<label class="pull-right">人员岗位：</label>
				</td>
				<td colspan="5">
					<c:forEach items="${lsXxPostCourse}" var="postCourse">
						<span>${postCourse.postName}-${postCourse.postLevelName}&nbsp;&nbsp;</span>
					</c:forEach>
				</td>
			</tr>
			<tr>
				<td class="width-15 active">
					<label class="pull-right">是否选择学员：</label>
				</td>
				<td colspan="5">
					${courseInfo.isBxUser==1?"是":"否" }
				</td>
			</tr>
			<tr>
				<td class="width-15 active">
					<label class="pull-right">可报名人员列表：</label>
				</td>
				<td colspan="5">
					<iframe id="iframeBxOffeic" width="98%" height="240px" style="border: none" src="${ctx}/course/courseInfo/showUserCourseList?id=${courseInfo.id}&type=0&a="+Math.random()></iframe>
				</td>
			</tr>
		</table>
</body>
</html>