<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>资源目录列表</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		});
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<!-- <div class="ibox-title">
		<h5>资源目录列表 </h5>
		<div class="ibox-tools">
		</div>
	</div> -->
    <div class="ibox-content">
	<sys:message content="${message }"/>
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="resource:resourceDir:add">
				<table:addRow url="${ctx}/resource/resourceDir/form" title="资源目录"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="resource:resourceDir:edit">
			    <table:editRow url="${ctx}/resource/resourceDir/form" title="资源目录" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
		
			</div>
		<div class="pull-right">
			<form:form id="searchForm" modelAttribute="resourceDir" action="${ctx}/resource/resourceDir/" method="post" class="form-inline">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
				<div class="form-group">
					<form:input path="name" htmlEscape="false" maxlength="200"  class=" form-control input-sm" placeholder="目录名称"/>
				 </div>
				 <button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 搜索</button>
			</form:form>
		</div>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<th  class="sort-column name">目录名称</th>
				<th  class="sort-column sort">排序</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="resourceDir">
			<tr>
				<td> <input type="checkbox" id="${resourceDir.id}" class="i-checks"></td>
				<td>
					<a  href="#" onclick="openDialogView('查看资源目录', '${ctx}/resource/resourceDir/form?id=${resourceDir.id}','800px', '500px')">${resourceDir.name}</a>
				</td>
				<td>
					${resourceDir.sort}
				</td>
				<td>
					<shiro:hasPermission name="resource:resourceDir:view">
						<a href="#" onclick="openDialogView('查看资源目录', '${ctx}/resource/resourceDir/form?id=${resourceDir.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="resource:resourceDir:edit">
    					<a href="#" onclick="openDialog('修改资源目录', '${ctx}/resource/resourceDir/form?id=${resourceDir.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<c:if test="${resourceDir.parent.id != 'root' && resourceDir.parent.id != ''}">
	    				<shiro:hasPermission name="resource:resourceDir:del">
							<a href="${ctx}/resource/resourceDir/delete?id=${resourceDir.id}" onclick="return confirmx('确认要删除该资源目录吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
						</shiro:hasPermission>
					</c:if>
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