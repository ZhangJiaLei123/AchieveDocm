<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>报名审核管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
	});
</script>
<script type="text/javascript">
	$(document).ready(
			function() {
				$('#contentTable thead tr th input.i-checks').on(
						'ifChecked',
						function(event) { //ifCreated 事件应该在插件初始化之前绑定 
							$('#contentTable tbody tr td input.i-checks')
									.iCheck('check');
						});

				$('#contentTable thead tr th input.i-checks').on(
						'ifUnchecked',
						function(event) { //ifCreated 事件应该在插件初始化之前绑定 
							$('#contentTable tbody tr td input.i-checks')
									.iCheck('uncheck');
						});

			});

	function examine123(courseId) {
		// var url = $(this).attr('data-url');
		var str = "";
		var ids = "";
		$("#contentTable tbody tr td input.i-checks:checkbox").each(function() {
			if (true == $(this).is(':checked')) {
				str += $(this).attr("id") + ",";
			}
		});
		if (str.substr(str.length - 1) == ',') {
			ids = str.substr(0, str.length - 1);
		}
		if (ids == "") {
			top.layer.alert('请至少选择一条数据!', {
				icon : 0,
				title : '警告'
			});
			return;
		}
		top.layer
				.confirm(
						'确认要全部审核通过吗？',
						{
							icon : 3,
							title : '系统提示'
						},
						function(index) {
							window.location = "/docm/a/course/userCourseReg/registerokall?courseid="
									+ courseId + "&ids=" + ids;
							top.layer.close(index);
						});
	}
</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="ibox">
			<div class="ibox-title">
				<h5>报名审核列表</h5>
				<div class="ibox-tools">
					<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
					</a> <a class="dropdown-toggle" data-toggle="dropdown" href="#"> <i
						class="fa fa-wrench"></i>
					</a>
					<ul class="dropdown-menu dropdown-user">
						<li><a href="#">选项1</a></li>
						<li><a href="#">选项2</a></li>
					</ul>
					<a class="close-link"> <i class="fa fa-times"></i>
					</a>
				</div>
			</div>

			<div class="ibox-content">
				<sys:message content="${message}" />

				<!--搜索条件-->
				<div class="row">
					<div class="col-sm-12">
						<form:form id="searchForm" modelAttribute="userCourseReg"
							action="${ctx}/course/userCourseReg/" method="post"
							class="form-inline">
							<input id="pageNo" name="pageNo" type="hidden"
								value="${page.pageNo}" />
							<input id="pageSize" name="pageSize" type="hidden"
								value="${page.pageSize}" />
							<input id="courseId" name="courseId" type="hidden"
								value="${courseid}" />
							<table:sortColumn id="orderBy" name="orderBy"
								value="${page.orderBy}" callback="sortOrRefresh();" />
							<!-- 支持排序 -->
							<div class="form-group">
								<span>报名人：</span>
								<form:input path="userName" htmlEscape="false" maxlength="64"
									class=" form-control input-sm" />
							</div>
						</form:form>
						<br />
					</div>
				</div>

				<!-- 工具栏 -->
				<div class="row">
					<div class="col-sm-12">
						<div class="pull-left">
							<button class="btn btn-success btn-xs" data-toggle="tooltip"
								data-placement="left" onclick="examine123('${courseid}');"
								title="审核">审核</button>
							<button class="btn btn-white btn-sm " data-toggle="tooltip"
								data-placement="left" onclick="sortOrRefresh()" title="刷新">
								<i class="glyphicon glyphicon-repeat"></i> 刷新
							</button>

						</div>
						<div class="pull-right">
							<button class="btn btn-primary btn-rounded btn-outline btn-sm "
								onclick="search()">
								<i class="fa fa-search"></i> 搜索
							</button>
						</div>
					</div>
				</div>

				<!-- 表格 -->
				<table id="contentTable"
					class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
					<thead>
						<tr>
							<th><input type="checkbox" class="i-checks"></th>
							<th>序号</th>
							<th>报名人</th>
							<th>报名时间</th>
							<th>报名状态</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<%
							int num = 0;
						%>
						<c:forEach items="${page.list}" var="userCourseReg">
							<tr>
								<%
									num++;
								%>
								<td><c:if test="${userCourseReg.auditState eq '0'}">
										<input type="checkbox" id="${userCourseReg.id}"
											class="i-checks">
									</c:if></td>
								<td><%=num%></td>
								<td>${userCourseReg.userName}</td>
								<td><fmt:formatDate value="${userCourseReg.createDate}"
										pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td><c:if test="${userCourseReg.auditState eq '0'}">
				新建
				</c:if> <c:if test="${userCourseReg.auditState eq '1'}">
				通过
				</c:if> <c:if test="${userCourseReg.auditState eq '2'}">
				不通过
				</c:if></td>
								<td><c:if test="${userCourseReg.auditState eq '0'}">
										<shiro:hasPermission name="course:userCourseReg:edit">
											<a
												href="${ctx}/course/userCourseReg/registerok?id=${userCourseReg.id}"
												class="btn btn-success btn-xs"><i class="fa fa-edit"></i>
												审核</a>
										</shiro:hasPermission>
									</c:if></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>

				<!-- 分页代码 -->
				<table:page page="${page}"></table:page>
				<br /> <br />
			</div>
		</div>
	</div>
</body>
</html>