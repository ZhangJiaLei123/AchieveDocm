<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>课程管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
	</script>
</head>
<body >
	<!--头部 start-->
	<jsp:include page="/include/head.jsp"></jsp:include>
	<script type="text/javascript">
		$($("a",$("#topA"))[4]).attr("class","active");
	</script>
	<!--头部 end-->
		<div class="row" style="min-height: 510px;padding-bottom:140px;">
		<div class="nav_menu">
				<a class="active"  href="${pageContext.request.contextPath}/a/course/tcourseInfo/list">课程库</a>
				<a href="${pageContext.request.contextPath}/a/course/tcourseInfo/findMyCourseInfo">我的课程</a>
				<a href="${pageContext.request.contextPath}/a/course/courseInfo/teacherFormBase/" >新建课程</a>
				<form:form id="searchForm" modelAttribute="courseInfo" action="${pageContext.request.contextPath}/a/course/tcourseInfo/list" method="post" class="form-inline">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<div style="float: right">
					<span><input type="text" name="couName" value="${courseInfo.couName}" class="input_style" placeholder="课程名称"></span>
					<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i>搜索</button>
				</div>
				</form:form>
			</div>
	<sys:message content="${message}"/>
	<!-- 工具栏 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th width="150px">课程名称</th>
				<th width="150px">资源名称</th>
				<th  width="120px">报名时间</th>
				<th  width="120px">学习时间</th>
				<th width="60px">发布人</th>
				<th width="80px">课程状态</th>
				<th width="50px">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="courseInfo">
			<tr>
				<td title="${courseInfo.couName}">
						<c:choose>  
				        <c:when test="${fn:length(courseInfo.couName) >12 }">  
				           ${fn:substring(courseInfo.couName, 0, 11)}…
				         </c:when>  
				        <c:otherwise>  
				           ${fn:substring(courseInfo.couName, 0, 12)}
				         </c:otherwise>  
				     </c:choose>  
				</a>
				<td title="${courseInfo.resResourceName}">
						<c:choose>  
				        <c:when test="${fn:length(courseInfo.resResourceName) >12 }">  
				           ${fn:substring(courseInfo.resResourceName, 0, 11)}…
				         </c:when>  
				        <c:otherwise>  
				           ${fn:substring(courseInfo.resResourceName, 0, 12)}
				         </c:otherwise>  
				     </c:choose>  
				</td>
				<td>
					<fmt:formatDate value="${courseInfo.regStartTime}" pattern="yyyy-MM-dd"/>~<fmt:formatDate value="${courseInfo.regEndTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${courseInfo.stuStartTime}" pattern="yyyy-MM-dd"/>~<fmt:formatDate value="${courseInfo.stuEndTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${courseInfo.updateBy.name}
				</td>
				<td>
					${courseInfo.updateBy.name}提交
				</td>
				<td>
					<a href="${ctx}/course/courseInfo/teacherManageCourseView?id=${courseInfo.id}" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
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