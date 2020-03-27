<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>资源下载管理</title>
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
		<h5>资源下载列表 </h5>
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
	
	<!--查询条件-->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="tResourceDownload" action="${ctx}/resourcedownload/tResourceDownload/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>用户ID：</span>
				<sys:treeselect id="user" name="user.id" value="${tResourceDownload.user.id}" labelName="user.name" labelValue="${tResourceDownload.user.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="form-control input-sm" allowClear="true" notAllowSelectParent="true"/>
			<span>资料ID：</span>
				<form:input path="resourceId" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			<span>审核状态：</span>
				<form:radiobuttons class="i-checks" path="status" items="${fns:getDictList('download_check')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="resourcedownload:tResourceDownload:add">
				<table:addRow url="${ctx}/resourcedownload/tResourceDownload/form" title="资源下载"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="resourcedownload:tResourceDownload:edit">
			    <table:editRow url="${ctx}/resourcedownload/tResourceDownload/form" title="资源下载" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="resourcedownload:tResourceDownload:del">
				<table:delRow url="${ctx}/resourcedownload/tResourceDownload/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="resourcedownload:tResourceDownload:import">
				<table:importExcel url="${ctx}/resourcedownload/tResourceDownload/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="resourcedownload:tResourceDownload:export">
	       		<table:exportExcel url="${ctx}/resourcedownload/tResourceDownload/export"></table:exportExcel><!-- 导出按钮 -->
	       	</shiro:hasPermission>
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
		
			</div>
		<div class="pull-right">
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
		</div>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<!-- <th  class="sort-column id">主键</th> -->
				<th  class="sort-column resourceId">资料ID</th>
				<th  class="sort-column user.name">用户</th>
				<th  class="sort-column resourceName">资料名称</th>
				<th  class="sort-column status">审核状态</th>
				<th  class="sort-column createTime">申请时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tResourceDownload">
			<tr>
				<td> <input type="checkbox" id="${tResourceDownload.id}" class="i-checks"></td>
				<!--<td><a  href="#" onclick="openDialogView('查看资源下载', '${ctx}/resourcedownload/tResourceDownload/form?id=${tResourceDownload.id}','800px', '500px')">
					${tResourceDownload.id}
				</a></td> -->
				<td>
					${tResourceDownload.resourceId}
				</td>				
				<td>
					${tResourceDownload.user.name}
				</td>
				<td>
					${tResourceDownload.resourceName}
				</td>
				<td>
					<c:if test="${tResourceDownload.status==1}">待审核</c:if>
					<c:if test="${tResourceDownload.status==2}">通过</c:if>
					<c:if test="${tResourceDownload.status==3}">不通过</c:if>
				</td>
				<td>
					<fmt:formatDate value="${tResourceDownload.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<shiro:hasPermission name="resourcedownload:tResourceDownload:view">
						<a href="#" onclick="openDialogView('查看资源下载', '${ctx}/resourcedownload/tResourceDownload/form?id=${tResourceDownload.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="resourcedownload:tResourceDownload:edit">
    					<a href="#" onclick="openDialog('修改资源下载', '${ctx}/resourcedownload/tResourceDownload/form?id=${tResourceDownload.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i>审核</a>
    				</shiro:hasPermission>
    				<!--<shiro:hasPermission name="resourcedownload:tResourceDownload:del">
						<a href="${ctx}/resourcedownload/tResourceDownload/delete?id=${tResourceDownload.id}" onclick="return confirmx('确认要删除该资源下载吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>-->
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