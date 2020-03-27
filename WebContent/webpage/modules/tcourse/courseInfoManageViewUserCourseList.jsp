<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>学员管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
	});
</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="ibox">
			<div class="ibox-content">
				<sys:message content="${message}" />

				<!-- 工具栏 -->
				<div class="row">
					<div class="col-sm-12">
						<div class="pull-left">
							<shiro:hasPermission name="course:userCourse:add">
								<table:addRow url="${ctx}/course/userCourse/form"
									title="学员课程关系表"></table:addRow>
								<!-- 增加按钮 -->
							</shiro:hasPermission>
						</div>
					</div>

					<!-- 表格 -->
					<table id="contentTable"
						class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
						<thead>
							<tr>
								<th>序号</th>
								<th>所属机构</th>
								<th>人员姓名</th>
								<th>学习次数</th>
								<th>学习时长</th>
								<th>是否通过</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
						<%int num=0; %>
							<c:forEach items="${page.list}" var="userCourse">
								<tr>
								<%num ++; %>
									<td><%=num %></td>
									<td>${userCourse.userName}</td>
									<td>${userCourse.officeName}</td>
									<td></td>
									<td></td>
									<td></td>
									<td><shiro:hasPermission name="course:userCourse:del">
											<a href="${ctx}/course/userCourse/delete?id=${userCourse.id}"
												onclick="return confirmx('确认要删除该学员课程关系表吗？', this.href)"
												class="btn btn-danger btn-xs"><i class="fa fa-trash"></i>
												删除</a>
										</shiro:hasPermission></td>
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
	</div>
</body>
</html>