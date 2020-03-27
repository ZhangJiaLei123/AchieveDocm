<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>区域管理管理</title>
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
	
	<!--查询条件-->
	<div class="row">
	<div class="col-sm-12">
	
	<div class="pull-left">
			<shiro:hasPermission name="sys:district:add">
				<table:addRow url="${ctx}/sys/district/form" title="区域管理"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:district:edit">
			    <table:editRow url="${ctx}/sys/district/form" title="区域管理" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:district:del">
				<table:delRow url="${ctx}/sys/district/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
				<shiro:hasPermission name="sys:district:export">
	       		<table:exportExcel url="${ctx}/sys/district/export"></table:exportExcel><!-- 导出按钮 -->
	       	</shiro:hasPermission>
			<shiro:hasPermission name="sys:district:import">
				<table:importExcel url="${ctx}/sys/district/import"></table:importExcel><!-- 导入按钮 -->
				<a href="${ctx}/sys/district/exportProvinceCity" class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" title="导出省市"><i class="fa fa-file-excel-o"></i>导出省市</a>
			</shiro:hasPermission>
		
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
		
			</div>
		<div class="pull-right">
		<form:form id="searchForm" modelAttribute="district" action="${ctx}/sys/district/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span><form:input path="name" htmlEscape="false" maxlength="300"  class=" form-control input-sm" placeholder="请输入区域名称"/></span>
			<span><button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i>搜索</button></span>
			<span>	</span>
		 </div>	
		</form:form>
		</div>
	<br/>
	</div>
	</div>
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<th  >序号</th>
				<th >大区名称</th>
				<th >大区编码</th>
				<th >省</th>
				<th >市</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="district" varStatus="status">
			<tr>
				<td> <input type="checkbox" id="${district.id}" class="i-checks"></td>
				<td>${status.index+1}</td>
				<td>
					<a  href="#" onclick="openDialogView('查看区域管理', '${ctx}/sys/district/form?id=${district.id}','800px', '500px')">
						${district.name}
					</a>
				</td>
				<td>
					${district.code}
				</td>
				<td>
					${district.provinceName}
				</td>
				<td>
					${district.cityName}
				</td>
				<td>
					<shiro:hasPermission name="sys:district:view">
						<a href="#" onclick="openDialogView('查看区域管理', '${ctx}/sys/district/form?id=${district.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="sys:district:edit">
    					<a href="#" onclick="openDialog('修改区域管理', '${ctx}/sys/district/form?id=${district.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="sys:district:del">
						<a href="${ctx}/sys/district/delete?id=${district.id}&districtCityId=${district.districtCityId}" onclick="return confirmx('确认要删除该区域管理吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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