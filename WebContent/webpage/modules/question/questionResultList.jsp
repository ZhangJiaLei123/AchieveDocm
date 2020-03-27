<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<title>用户答题信息表管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
	});
</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="ibox">
			<div class="ibox-title">
				<a href="${ctx}/question/questionInfo">
					<h5>问卷列表</h5>
				</a>
				<h5>>> 调研列表</h5>
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
				<table
					class="  table-condensed dataTables-example dataTable no-footer"
					style="text-align: center;" align="center" valign="center">
					<tbody>
						<tr>
							<td class="width-15 active"><label>问卷名称：${questionInfo.queName}</label></td>
							<td class="width-15 active"><label>发布人：${questionRelease.userName}</label></td>
							<td class="width-15 active"><label>使用时间：<fmt:formatDate
										value="${questionRelease.startTime}"
										pattern="yyyy-MM-dd HH:mm:ss" /> ~<fmt:formatDate
										value="${questionRelease.endTime}"
										pattern="yyyy-MM-dd HH:mm:ss" />
							</label></td>
						</tr>
					</tbody>
				</table>
				<hr style="height: 2px; border: none; border-top: 2px dotted red;" />

				<!--查询条件-->
				<div class="row">
					<div class="col-sm-12">
						<form:form id="searchForm" modelAttribute="questionResult"
							action="${ctx}/question/questionResult/" method="post"
							class="form-inline">
							<input id="pageNo" name="pageNo" type="hidden"
								value="${page.pageNo}" />
							<input id="pageSize" name="pageSize" type="hidden"
								value="${page.pageSize}" />
							<table:sortColumn id="orderBy" name="orderBy"
								value="${page.orderBy}" callback="sortOrRefresh();" />
							<!-- 支持排序 -->
							<div class="form-group"></div>
						</form:form>
						<br />
					</div>
				</div>

				<!-- 工具栏 -->
				<div class="row">
					<div class="col-sm-12">
						<div class="pull-left">
							<shiro:hasPermission name="question:questionInfo:view">
								<a href="#"
									onclick="openDialogView('查看答题信息', '${ctx}/question/questionProblem/formdetailtotal?questionid=${questionInfo.id}&releaseid=${questionRelease.id}','800px', '500px')"
									class="btn btn-info btn-xs"><i class="fa fa-search-plus"></i>
									统计结果</a>
							</shiro:hasPermission>
						</div>
						<div class="pull-right">
						<!--	<button class="btn btn-primary btn-rounded btn-outline btn-sm "
								onclick="search()">
								<i class="fa fa-search"></i> 查询
							</button>  -->
						</div>
					</div>
				</div>

				<!-- 表格 -->
				<table id="contentTable"
					class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
					<thead>
						<tr>
							<th>序号</th>
							<th>调研时间</th>
							<th>调研用户</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<%
							int a = 0;
						%>
						<c:forEach items="${page.list}" var="questionResult">
							<%
								a++;
							%>
							<tr>
								<td><%=a%></td>
								<td><fmt:formatDate value="${questionResult.createDate}"
										pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td>${questionResult.userName}</td>
								<td><shiro:hasPermission name="question:questionInfo:view">
										<a href="#"
											onclick="openDialogView('查看答题信息', '${ctx}/question/questionProblem/formdetailbyuid?releaseid=${questionRelease.id}&questionid=${questionInfo.id}&uid=${questionResult.userId}&uname=${questionResult.userName}&stime=<fmt:formatDate
										value="${questionResult.createDate}"
										pattern="yyyy-MM-dd HH:mm:ss" />','800px', '500px')"
											class="btn btn-info btn-xs"><i class="fa fa-search-plus"></i>
											查看</a>
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
</body>
</html>