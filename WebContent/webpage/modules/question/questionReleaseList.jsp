<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>问卷发布管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		laydate({
			elem : '#startTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			event : 'focus' //响应事件。如果没有传入event，则按照默认的click
		});
		laydate({
			elem : '#endTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			event : 'focus' //响应事件。如果没有传入event，则按照默认的click
		});
	});
</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="ibox">
			<div class="ibox-title">
				<h5><a href="${ctx}/question/questionInfo">
					问卷列表
				</a></h5>
				<h5>>> 发布列表</h5>
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
							<td class="width-15 active"><label>问卷题目：${questionInfo.queName}</label></td>
							<td class="width-35 active"><label>问卷描述：${questionInfo.remarks}</label></td>
							<td class="width-35 active"><label>问题总数： ${questionInfo.pNum}</label></td>
						</tr>
					</tbody>
				</table>
				<hr style="height: 2px; border: none; border-top: 2px dotted red;" />
				<!--查询条件-->
				<div class="row">
					<div class="col-sm-12">
						<form:form id="searchForm" modelAttribute="questionRelease"
							action="${ctx}/question/questionRelease/" method="post"
							class="form-inline">
							<form:hidden path="questionInfoId" value="${questionInfo.id}" />
							<input id="pageNo" name="pageNo" type="hidden"
								value="${page.pageNo}" />
							<input id="pageSize" name="pageSize" type="hidden"
								value="${page.pageSize}" />
							<table:sortColumn id="orderBy" name="orderBy"
								value="${page.orderBy}" callback="sortOrRefresh();" />
							<!-- 支持排序 -->
							<div class="form-group">
								<!-- <span>开始时间：</span> <input id="startTime" name="startTime"
									type="text" maxlength="20"
									class="laydate-icon form-control layer-date input-sm"
									value="<fmt:formatDate value="${questionRelease.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" />
								<span>结束时间：</span> <input id="endTime" name="endTime"
									type="text" maxlength="20"
									class="laydate-icon form-control layer-date input-sm"
									value="<fmt:formatDate value="${questionRelease.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" />
								 		<span>问卷id：</span>
				<form:input path="questionInfoId" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
		 -->
		 						<span>发布人：</span> 
		 						<input id="userName" name="userName" type="text" maxlength="20" class="form-control input-sm" />
							</div>
						</form:form>
						<br />
					</div>
				</div>

				<!-- 工具栏 -->
				<div class="row">
					<div class="col-sm-12">
						<div class="pull-right">
							<button class="btn btn-primary btn-rounded btn-outline btn-sm "
								onclick="search()">
								<i class="fa fa-search"></i> 查询
							</button>
						</div>
					</div>
				</div>

				<!-- 表格 -->
				<table id="contentTable"
					class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
					<thead>
						<tr>
							<th>序号</th>
							<th>开始时间</th>
							<th>结束时间</th>
							<th>发布人</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<%
							int a = 0;
						%>
						<c:forEach items="${page.list}" var="questionRelease">
							<%
								a++;
							%>
							<tr>
								<td><%=a%></td>
								<td><fmt:formatDate value="${questionRelease.startTime}"
										pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td><fmt:formatDate value="${questionRelease.endTime}"
										pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td>${questionRelease.userName}</td>
								<td><shiro:hasPermission
										name="question:questionResult:list">
										<a
											href="${ctx}/question/questionResult/infolist?releaseid=${questionRelease.id}"
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