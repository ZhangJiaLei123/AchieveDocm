<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>资源管理列表</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#resourceType").prepend("<option value=''>请选择</option>");
			$("#resourceType").val("");
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
<%-- 	<sys:message content="${message}"/> --%>
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="resource:resourceInfo:add">
				<table:addRow url="${ctx}/resource/resourceInfo/form" title="资源管理"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="resource:resourceInfo:edit">
			    <table:editRow url="${ctx}/resource/resourceInfo/form" title="资源管理" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="resource:resourceInfo:del">
				<table:delRow url="${ctx}/resource/resourceInfo/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
		
			</div>
		<div class="pull-right">
			<form:form id="searchForm" modelAttribute="resourceInfo" action="${ctx}/resource/resourceInfo/" method="post" class="form-inline">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
				<div class="form-group">
					<span>资源目录：</span>
						<sys:treeselect id="resourceDir" name="resourceDir" value="${resourceDir.parent.id}" labelName="parent.name" labelValue="${resourceDir.parent.name}"
							title="资源目录" url="/resource/resourceDir/treeData" extId="${resourceDir.id}" cssClass="form-control required"/>
					<form:select path="resourceType" class="form-control" id="resourceType">
							<form:options items="${fns:getDictList('resource_type')}" itemLabel="label" itemValue="value" htmlEscape="true"/>
					</form:select>
					<form:input path="name" htmlEscape="false" maxlength="300"  class=" form-control input-sm" placeholder="请输入资源名称"/>
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
				<th  >序号</th>
				<th >资源名称</th>
				<th >资源类型</th>
				<th >资源编号</th>
				<th >资源目录</th>
				<th >上传时间</th>
				<th >上传人</th>
				<th >资源状态</th>
				<th >是否公开</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="resourceInfo" varStatus="status"> 
			<tr>
				<td> <input type="checkbox" id="${resourceInfo.id}" class="i-checks"></td>
				<td>${status.index + 1}</td>
				<td title="${resourceInfo.name}">
					<a  href="#" onclick="openDialogView('查看资源管理', '${ctx}/resource/resourceInfo/form?id=${resourceInfo.id}','800px', '500px')">
					<c:choose>  
				        <c:when test="${fn:length(resourceInfo.name) >15 }">  
				           ${fn:substring(resourceInfo.name, 0, 14)}…
				         </c:when>  
				        <c:otherwise>  
				           ${fn:substring(resourceInfo.name, 0, 15)}
				         </c:otherwise>  
				     </c:choose>  
					</a>
				</td>
				<td>
					${fns:getDictLabel(resourceInfo.resourceType, 'resource_type', '')}
				</td>
				<td>
					${resourceInfo.resourceCode}
				</td>
				<td>
					${resourceInfo.resourceDirName}
				</td>
				<td>
					<fmt:formatDate value="${resourceInfo.updateDate}" pattern="yyyy-MM-dd HH:mm" type="both"/>
				</td>
				<td>
					${resourceInfo.updateBy.name}
				</td>
				<td>
					<c:if test="${resourceInfo.resourceStatus==3 }">
						通过
					</c:if>
					${resourceInfo.resourceStatus==null?"待审核":fns:getDictLabel(resourceInfo.resourceStatus, 'approval_record_status', '')}
				</td>
				<td>
					${(resourceInfo.isPublic==null||resourceInfo.isPublic==0)?"否":fns:getDictLabel(resourceInfo.isPublic, 'yes_no', '')}
				</td>
				
				<td>
					<shiro:hasPermission name="resource:resourceInfo:view">
						<a href="#" onclick="openDialogView('查看资源管理', '${ctx}/resource/resourceInfo/showShResourceInfoForm?id=${resourceInfo.id}','800px', '600px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="resource:resourceInfo:edit">
    					<a href="#" onclick="openDialog('修改资源管理', '${ctx}/resource/resourceInfo/form?id=${resourceInfo.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="resource:resourceInfo:del">
						<a href="${ctx}/resource/resourceInfo/delete?id=${resourceInfo.id}" onclick="return confirmx('确认要删除该资源管理吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="resource:resourceInfo:edit">
					<c:if test="${resourceInfo.isPublic==null||resourceInfo.isPublic==0}">
					  	<a href="${ctx}/resource/resourceInfo/isPublic?id=${resourceInfo.id}&isPublic=1" onclick="return confirmx('确认要公开该资源管理吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-edit"></i> 公开</a>
					</c:if>
					<c:if test="${resourceInfo.isPublic==1}">
					  	<a href="${ctx}/resource/resourceInfo/isPublic?id=${resourceInfo.id}&isPublic=0" onclick="return confirmx('确认要取消公开该资源管理吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-edit"></i> 取消公开</a>
					</c:if>	
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