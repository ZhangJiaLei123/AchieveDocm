<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>学员学习课程时长详情</title>
	<meta name="decorator" content="default"/>
</head>
<body>
	<div class="wrapper wrapper-content">
	<div class="ibox">
    <sys:message content="${message}"/>
		<!-- 查询条件 -->
	<div class="row">
	<div class="col-sm-12">
	<div class="pull-right">
	<form:form id="searchForm" modelAttribute="courseInfo" action="${ctx}/sys/statis/courseStudyTime" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="userId" name="userId" type="hidden" value="${courseInfo.userId}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
				<form:input path="couName" htmlEscape="false" maxlength="50" class=" form-control input-sm" placeholder="请输入课程名称"/>
				<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
		 </div>	
	</form:form>
	</div>
	</div>
	</div>
	

	
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th class=" couName">课程名称</th>
			    <th class=" courseTime">课程学习时长</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="course">
			<tr>
				<td>${course.couName}</td>
				<td>${course.courseTime}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<table:page page="${page}"></table:page>
	</div>
	</div>
</body>
</html>