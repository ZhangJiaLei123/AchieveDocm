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
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>组织课程关系列表 </h5>
		<div class="ibox-tools">
			<a class="collapse-link">
				<i class="fa fa-chevron-up"></i>
			</a>
			<a class="dropdown-toggle" data-toggle="dropdown" href="#">
				<i class="fa fa-wrench"></i>
			</a>
			<ul class="dropdown-menu dropdown-user">
				<li><a href="#">选项1</a>
				</li>
				<li><a href="#">选项2</a>
				</li>
			</ul>
			<a class="close-link">
				<i class="fa fa-times"></i>
			</a>
		</div>
	</div>
    
    <div class="ibox-content">
	<sys:message content="${message}"/>
	
	<!--搜索条件-->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="officeCourse" action="${ctx}/course/officeCourse/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>组织ID：</span>
				<form:input path="officeId" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			<span>课程ID：</span>
				<form:input path="courseId" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			<span>关系类别：</span>
				<form:input path="type" htmlEscape="false" maxlength="10"  class=" form-control input-sm"/>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="course:officeCourse:add">
				<table:addRow url="${ctx}/course/officeCourse/form" title="组织课程关系"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="course:officeCourse:edit">
			    <table:editRow url="${ctx}/course/officeCourse/form" title="组织课程关系" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="course:officeCourse:del">
				<table:delRow url="${ctx}/course/officeCourse/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="course:officeCourse:import">
				<table:importExcel url="${ctx}/course/officeCourse/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="course:officeCourse:export">
	       		<table:exportExcel url="${ctx}/course/officeCourse/export"></table:exportExcel><!-- 导出按钮 -->
	       	</shiro:hasPermission>
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
		
			</div>
		<div class="pull-right">
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 搜索</button>
			
		</div>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<th  class="sort-column remarks">备注信息</th>
				<th  class="sort-column officeId">组织ID</th>
				<th  class="sort-column courseId">课程ID</th>
				<th  class="sort-column type">关系类别</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="officeCourse">
			<tr>
				<td> <input type="checkbox" id="${officeCourse.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看组织课程关系', '${ctx}/course/officeCourse/form?id=${officeCourse.id}','800px', '500px')">
					${officeCourse.remarks}
				</a></td>
				<td>
					${officeCourse.officeId}
				</td>
				<td>
					${officeCourse.courseId}
				</td>
				<td>
					${officeCourse.type}
				</td>
				<td>
					<shiro:hasPermission name="course:officeCourse:view">
						<a href="#" onclick="openDialogView('查看组织课程关系', '${ctx}/course/officeCourse/form?id=${officeCourse.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="course:officeCourse:edit">
    					<a href="#" onclick="openDialog('修改组织课程关系', '${ctx}/course/officeCourse/form?id=${officeCourse.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="course:officeCourse:del">
						<a href="${ctx}/course/officeCourse/delete?id=${officeCourse.id}" onclick="return confirmx('确认要删除该组织课程关系吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
		<!-- 分页代码 -->
	<table:page page="${page}"></table:page>
	<br/>
	<br/>
	</div>
	</div>
</div>
</body>
</html>