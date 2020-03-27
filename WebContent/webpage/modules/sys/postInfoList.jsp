<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>岗位管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			if('${message}'!=''){
				 window.setTimeout(function () { $.jBox.tip('${message}', 'success'); }, 300);
			}
			
		});
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
    <div class="ibox-content">
	<%-- <sys:message content="${message}"/> --%>
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="sys:postInfo:add">
				<table:addRow url="${ctx}/sys/postInfo/form" title="岗位"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:postInfo:del">
				<table:delRow url="${ctx}/sys/postInfo/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
			</div>
		<div class="pull-right">
			<form:form id="searchForm" modelAttribute="postInfo" action="${ctx}/sys/postInfo/" method="post" class="form-inline">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<%-- 	<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 --> --%>
				<div class="form-group">
						<form:input path="name" htmlEscape="false" maxlength="200"  class=" form-control input-sm" placeholder="请输入岗位名称"/>
				 </div>	
				 	<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i>搜索</button>
					
			</form:form>
		</div>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<th>序号</th>
				<th>岗位名称</th>
				<th>岗位类型</th>
				<th>级别数量</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="postInfo" varStatus="status">
			<tr>
				<td><input type="checkbox" id="${postInfo.id}" class="i-checks"></td>
				<td>${status.index+1}</td>
				<td><a  href="#" onclick="openDialogView('查看岗位管理', '${ctx}/sys/postInfo/form?id=${postInfo.id}','800px', '500px')">
					${postInfo.name}
				</a></td>
				<td>
					${postInfo.postinfoTypeName}
				</td>
				<td>
					<a href="#" onclick="openDialogView('查看岗位级别', '${ctx}/sys/postLevel/list?postInfoId=${postInfo.id}','800px', '500px')" class="btn btn-info btn-xs" >${postInfo.countPostLevel}</a>
				</td>
				<td>
					<shiro:hasPermission name="sys:postInfo:edit">
    					<a href="#" onclick="openDialog('修改岗位管理', '${ctx}/sys/postInfo/form?id=${postInfo.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i>编辑</a>
    				</shiro:hasPermission>
					<shiro:hasPermission name="sys:postInfo:edit">
    					<a href="#" onclick="openDialog('添加级别', '${ctx}/sys/postLevel/form?postInfoId=${postInfo.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i>添加级别</a>
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