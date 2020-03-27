<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>选择用户</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		});
	</script>
</head>
<body >
<div class="col-sm-12">
	
	<div class="pull-right">
	<div class="form-group">
	<!--查询条件-->
	<form:form id="searchForm" modelAttribute="user" action="${ctx}/sys/user/findSelUser" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div style="padding:5px;">
			<table>
				<tr>
					<td><form:input path="name" htmlEscape="false" maxlength="200"  class=" form-control input-sm" placeholder="请输入讲师姓名"/></td>
					<td><button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button></td>
				</tr>
			</table>
		</div>
	</form:form>
	</div>
	</div>
	</div>
	<!-- 表格 -->
	<div style="width:100%;padding:3px;">
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable" style="font-size: 10px;" >
		<thead>
			<tr>
				<th ></th>
				<th class="sort-column name">讲师姓名</th>
				<th >机构分类</th>
				<th >组织机构</th>
				<th >岗位信息</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="user">
			<tr style="padding: 0px">
				<td> <input type="radio" name="rdi" value="${user.name}" id="${user.id}"  class="i-checks" <c:if test="${param.teacher==user.name }">checked</c:if>></td>
				<td>
					${user.name}
				</td>
				<td>
					${user.officeTypeName}
				</td>
				<td>
					${user.officeName}
				</td>
				<td>
					${user.postName}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
		<!-- 分页代码 -->
	<table:page page="${page}" ></table:page>
	</div>
</body>
</html>