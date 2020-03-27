<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>审核资源列表</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			 $('#contentTable thead tr th input.i-checks').on('ifChecked', function(event){ //ifCreated 事件应该在插件初始化之前绑定 
		    	  $('#contentTable tbody tr td input.i-checks').iCheck('check');
		    	});

		    $('#contentTable thead tr th input.i-checks').on('ifUnchecked', function(event){ //ifCreated 事件应该在插件初始化之前绑定 
		    	  $('#contentTable tbody tr td input.i-checks').iCheck('uncheck');
		    	});
		    $("#resourceType").prepend("<option value=''>请选择</option>");
			$("#resourceType").val("");
		});
		function shResource(){
			  var size = $("#contentTable tbody tr td input.i-checks:checked").size();
			  if(size == 0 ){
					top.layer.alert('请至少选择一条数据!', {icon: 0, title:'警告'});
					return;
				  }
			 	var id = "";
			    $("#contentTable tbody tr td input.i-checks:checkbox:checked").each(function (){
			    	id+=this.id+","
			    })
			    openDialog("审核"+'资源',"${ctx}/sys/approvalRecord/form?ids="+id+"&url=/resource/resourceInfo/shList","400px", "300px","");
		}
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
    <div class="ibox-content">
	<sys:message content="${message}"/>
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			
			<shiro:hasPermission name="resource:mesanInfo:edit">
			   <button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="left" onclick="shResource()" title="审批"><i class="fa fa-file-text-o"></i> 审核</button>
			</shiro:hasPermission>
			
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
		
			</div>
		<div class="pull-right">
		<form:form id="searchForm" modelAttribute="resourceInfo" action="${ctx}/resource/resourceInfo/shList/" method="post" class="form-inline">
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
				<th >序号</th>
				<th >资源名称</th>
				<th >资源类型</th>
				<th >资源编号</th>
				<th >资源目录</th>
				<th >上传时间</th>
				<th >上传人</th>
				<th >资源状态</th>
				<th >发布状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="resourceInfo" varStatus="status">
			<tr>
				<td>
					<c:if test="${resourceInfo.resourceStatus ==null }">
						 <input type="checkbox" id="${resourceInfo.id}" class="i-checks">
				 	</c:if>
				 	<c:if test="${resourceInfo.resourceStatus eq '1'}">
						 <input type="checkbox" id="${resourceInfo.id}" class="i-checks">
				 	</c:if>
				</td>
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
					<c:if test="${resourceInfo.isCreateAdmin eq '1'}">
						<c:if test="${resourceInfo.resourceStatus eq '3'}">
							未发布
						</c:if>
						<c:if test="${resourceInfo.resourceStatus eq '0'}">
							发布成功
						</c:if>
					</c:if>
					<c:if test="${resourceInfo.isCreateAdmin!=1}">
						非管理员添加
					</c:if>
				</td>
				<td>
					<shiro:hasPermission name="resource:resourceInfo:view">
						<a href="#" onclick="openDialogView('查看资源管理', '${ctx}/resource/resourceInfo/showShResourceInfoForm?id=${resourceInfo.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="resource:resourceInfo:edit">
					<c:if test="${resourceInfo.resourceStatus==3}">
						<a href="${ctx}/resource/resourceInfo/faResourceInfo?resourceId=${resourceInfo.id}&isPublic=0" onclick="return confirmx('确认要发布资源吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-edit"></i>发布资源</a>
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