<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>组织课程关系管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		});
	</script>
</head>
<body>

  <div style="padding:5px;">
	<form:form id="searchForm" modelAttribute="officeCourse" action="${ctx}/course/officeCourse/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<table>
			<td>
				<span>机构名称：</span>
			</td>
			<td>
				<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 搜索</button>
			</td>
		</table>
	</form:form>
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th >名称</th>
				<th >机构等级</th>
				<th>机构分类</th>
				<th>大区</th>
				<th>省</th>
				<th>市</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="officeCourse">
			<tr>
				<td>
					${officeCourse.officeName}
				</td>
				<td>
					${officeCourse.officeGrade}
				</td>
				<td>
					${officeCourse.officeTypeName}
				</td>
				<td>
					${officeCourse.dq}
				</td>
				<td>
					${officeCourse.sf}
				</td>
				<td>
					${officeCourse.sq}
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