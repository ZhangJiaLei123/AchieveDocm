<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>人员列表</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		});
	</script>
</head>
<body>

	<div style="padding:5px">
	<!--搜索条件-->
	<form:form id="searchForm" modelAttribute="postCourse" action="${ctx}//course/courseInfo/showUserCourseList" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="type" name="pageSize" type="hidden" value="${type}"/>
		<input id="id" name="pageSize" type="hidden" value="${courseId}"/>
		<table>
			<tr>
				<td><span>人员姓名：</span></td>
				<td><input type="text" name="userName"  class=" form-control input-sm"/></td>
				<td><button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 搜索</button></td>
			</tr>
		</table>
	</form:form>
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th >人员姓名</th>
				<th >人员岗位</th>
				<th >组织机构</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="userCourse">
			<tr>
				<td>
					${userCourse.userName}
				</td>
				<td>
				</td>
				<td>
					${userCourse.officeName}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
		<!-- 分页代码 -->
	<table:page page="${page}"></table:page>
</div>
</body>
</html>