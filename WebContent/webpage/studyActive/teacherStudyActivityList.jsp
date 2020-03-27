<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>全部活动</title>
<meta name="decorator" content="default"/>
<meta charset="utf-8">
<meta name="decorator" content="default"/>
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
</script>
<!--头部 end-->
<!--身体 start-->
<div class="row" style="min-height: 510px;padding-bottom:140px;">
	<form:form id="searchForm" modelAttribute="studyActivity" action="${ctx}/teacherSstudyActivity" method="post" class="form-inline">
				<div class="nav_menu">
					<a  href="${pageContext.request.contextPath}/a/studyActivityList">学习活动计划</a>
					<a  href="${pageContext.request.contextPath}/a/gzActivityList">关注计划</a>
					<a  class="active" href="${pageContext.request.contextPath}/a/teacherSstudyActivity">全部学习活动</a>
					<a  href="${pageContext.request.contextPath}/a/train/studyActivity/teacherFormBase" >新建学习活动</a>
					<a  href="${pageContext.request.contextPath}/a/myTeacherStudyActivity">我的学习活动</a>
					<div style="float: right">
						<span><form:input path="name" htmlEscape="false" maxlength="200"  class=" form-control input-sm"/></span>
						<span><button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button></span>
					</div>
				</div>
	<!--查询条件-->
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	</form:form>
	<!-- 工具栏 -->
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th >活动名称</th>
				<th width="80px">讲师</th>
				<th width="80px">类型</th>
				<th width="80px">类别</th>
				<th width="180px">课时时段</th>
				<th width="180px">报名时段</th>
				<th width="50px">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="studyActivity">
			<tr>
				<td title="${studyActivity.name}">
					<c:choose>  
				        <c:when test="${fn:length(studyActivity.name) >25 }">  
				           ${fn:substring(studyActivity.name, 0, 24)}…
				         </c:when>  
				        <c:otherwise>  
				           ${fn:substring(studyActivity.name, 0, 25)}
				         </c:otherwise>  
				     </c:choose>  
				</td>

				<td>
					${studyActivity.createBy.name}
				</td>
				<td>
					${fns:getDictLabel(studyActivity.actType, 'activity_type', '${studyActivity.actType}')}
				</td>
				<td>
					${fns:getDictLabel(studyActivity.actSort, 'activity_sort', '${studyActivity.sort}')}
				</td>
				<td >
					<fmt:formatDate value="${studyActivity.applyStartTime}" pattern="yyyy-MM-dd"/>~<fmt:formatDate value="${studyActivity.applyEndTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${studyActivity.studyEndTime}" pattern="yyyy-MM-dd"/>~<fmt:formatDate value="${studyActivity.studyStartTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					  <a href="${ctx}/teacherFormviewALlActivity?id=${studyActivity.id}" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
		<!-- 分页代码 -->
	<table:page page="${page}"></table:page>
	</div>
<!--尾部 start-->
<div class="copyright">© 云帆互联 2014 汽车服务网 京ICP备14016447号</div>
<!--尾部 end-->

</body>
</html>
    