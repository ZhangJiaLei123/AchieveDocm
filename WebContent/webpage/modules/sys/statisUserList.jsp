<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>学员统计</title>
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
	<form:form id="searchForm" modelAttribute="user" action="${ctx}/sys/statis/statisStudentOnlineTime" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
				<form:input path="loginName" htmlEscape="false" maxlength="50" class=" form-control input-sm" placeholder="请输入登录名"/>
			<span>归属部门：</span>
				<sys:treeselect id="office" name="office.id" value="${user.office.id}" labelName="office.name" labelValue="${user.office.name}" 
				title="部门" url="/sys/office/treeData?type=2" cssClass=" form-control input-sm" allowClear="true" notAllowSelectParent="true"/>
				<form:input path="name" htmlEscape="false" maxlength="50" class=" form-control input-sm" placeholder="请输入姓名"/>
				<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
		 </div>	
	</form:form>
	</div>
	<br/>
	</div>
	</div>
	

	
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th class="loginName">登录名</th>
				<th class="name">姓名</th>
				<th class="officeName">归属部门</th>
				<th class="onLineTime">在线时长</th>
			    <th class="courseTime">课程学习时长</th>
			    <th class="studyTime">学习活动学习时长</th>
				<th class="mesanTime">浏览资料时长</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="user">
			<tr>
				<td>${user.loginName}</td>
				<td>${user.name}</td>
				<td>${user.officeName}</td>
				<td>${user.onLineTime}</td>
				<td>${user.courseTime}
					<c:if test="${user.courseTime!=null}">
						&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="openDialog('课程学习时长详情', '${ctx}/sys/statis/courseStudyTime?userId=${user.id}','800px', '400px')">详细</a>
					</c:if>
				</td>
				<td>${user.studyTime}
				<c:if test="${user.studyTime!=null}">
						&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="openDialog('学习活动学习时长详情', '${ctx}/sys/statis/activeStudyTime?userId=${user.id}','800px', '400px')">详细</a>
					</c:if>
				</td>
				<td>${user.mesanTime}
					<c:if test="${user.mesanTime!=null}">
						&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="openDialog('资料浏览时长详情', '${ctx}/sys/statis/mesanStudyTime?userId=${user.id}','800px', '400px')">详细</a>
					</c:if>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<table:page page="${page}"></table:page>
	</div>
	</div>
</body>
</html>