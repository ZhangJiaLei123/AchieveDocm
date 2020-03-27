<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>机构类别管理</title>
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
		<h5>机构类别列表 </h5>
		<div class="ibox-tools">
		</div>
	</div>
    
    <div class="ibox-content">
	<sys:message content="${message}"/>
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="sys:officeType:add">
				<table:addRow url="${ctx}/sys/officeType/form" width="800px" height="450px" title="机构类别"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:officeType:edit">
			    <table:editRow url="${ctx}/sys/officeType/form" title="机构类别" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:officeType:del">
				<table:delRow url="${ctx}/sys/officeType/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
			</div>
		<div class="pull-right">
			<form:form id="searchForm" modelAttribute="officeType" action="${ctx}/sys/officeType/" method="post" class="form-inline">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
				<div class="form-group">
						<form:input path="name" htmlEscape="false" maxlength="200"  class=" form-control input-sm" placeholder="组织类别名称"/>
				 </div>
				 <button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
				 
			</form:form>
		</div>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<th  class="sort-column name">组织类别名称</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="officeType">
			<tr>
				<td> <input type="checkbox" id="${officeType.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看机构类别', '${ctx}/sys/officeType/form?id=${officeType.id}','800px', '500px')">
					${officeType.name}
				</a></td>
				<td>
					<shiro:hasPermission name="sys:officeType:view">
						<a href="#" onclick="openDialogView('查看机构类别', '${ctx}/sys/officeType/form?id=${officeType.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="sys:officeType:edit">
    					<a href="#" onclick="openDialog('修改机构类别', '${ctx}/sys/officeType/form?id=${officeType.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<c:if test="${ resourceDir.parent.id != ''}">
	    				<shiro:hasPermission name="sys:officeType:del">
	    					<c:if test="${officeType.id!='root'}">
								<a href="${ctx}/sys/officeType/delete?id=${officeType.id}" onclick="return confirmx('确认要删除该机构类别吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
							</c:if>
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