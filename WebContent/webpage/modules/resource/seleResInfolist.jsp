<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>资源管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		
		});
		
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
    <div class="ibox-content">
	<sys:message content="${message}"/>
	
	<!--搜索条件-->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-right">
	<div class="form-group">
	<form:form id="searchForm" modelAttribute="resourceInfo" action="${ctx}/resource/resourceInfo/seleResInfolist" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
		<table>
			<tr>
				<td width="80px;">
					<span>资源目录：</span>
			</td>
			<td>
				<sys:treeselect id="resourceDir" name="resourceDir" value="${resourceDir.parent.id}" labelName="parent.name" labelValue="${resourceDir.parent.name}"
					title="资源目录" url="/resource/resourceDir/treeData" extId="${resourceDir.id}" cssClass="form-control" />
			</td>
			<td  width="10px;"></td>
			<td>
				<form:input path="name" htmlEscape="false" maxlength="300"  placeholder="请输入资源名称"  class=" form-control input-sm" style="width:150px;"/>
				</td>
				<td><button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 搜索</button></td>
				</tr>
			</table>
			
		 </div>	
	</form:form>
	</div>
	</div>
	</div>
	</div>
	

	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th></th>
				<th  >资源名称</th>
				<th  >资源类型</th>
				<th  >上传时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="resourceInfo">
			<tr>
				<td><input type="radio" name="rdi" value="${resourceInfo.name}" id="${resourceInfo.id}" ></td>
				<td>
					<a  href="#" onclick="openDialogView('查看资源管理', '${ctx}/resource/resourceInfo/form?id=${resourceInfo.id}','800px', '500px')">
						${resourceInfo.name}
					</a>
				</td>
				<td>
					${fns:getDictLabel(resourceInfo.resourceType, 'resource_type', '')}
				</td>
				<td>
					<fmt:formatDate value="${resourceInfo.updateDate}" type="both"/>
				</td>
				<td>
					<shiro:hasPermission name="resource:resourceInfo:view">
						<a href="#" onclick="openDialogView('资源预览', '${ctx}/teacherViewResourse?id=${resourceInfo.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 预览</a>
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