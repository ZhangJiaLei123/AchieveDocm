<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>资料信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	$(document).ready(function() {
		 $('#contentTable thead tr th input.i-checks').on('ifChecked', function(event){ //ifCreated 事件应该在插件初始化之前绑定 
	    	  $('#contentTable tbody tr td input.i-checks').iCheck('check');
	    	});

	    $('#contentTable thead tr th input.i-checks').on('ifUnchecked', function(event){ //ifCreated 事件应该在插件初始化之前绑定 
	    	  $('#contentTable tbody tr td input.i-checks').iCheck('uncheck');
	    	});
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
		    openDialog("审核"+'资源管理',"${ctx}/sys/approvalRecord/form?ids="+id+"&url=/resource/mesanInfo/shList","400px", "300px","");
	}
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<!-- <div class="ibox-title">
		<h5>资料信息列表 </h5>
	</div> -->
    
    <div class="ibox-content">
	<sys:message content="${message}"/>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="resource:resourceInfo:edit">
			   <button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="left" onclick="shResource()" title="审核"><i class="fa fa-file-text-o"></i> 审核</button>
			</shiro:hasPermission>
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
		
			</div>
		<div class="pull-right">
			<form:form id="searchForm" modelAttribute="mesanInfo" action="${ctx}/resource/mesanInfo/shList" method="post" class="form-inline">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
				<div class="form-group">
					<form:input path="name" htmlEscape="false" maxlength="300"  class=" form-control input-sm" placeholder="请输入资料名称"/>
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
				<th>  <input type="checkbox" class="i-checks"></th>
				<th>序号</th>
				<th  >资料名称</th>
				<th  >资料描述</th>
				<th  >上传时间</th>
				<th  >上传人</th>
				<th  >资料状态</th>
				<th>发布状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="mesanInfo" varStatus="status">
			<tr>
				<td>
				 <c:if test="${mesanInfo.approvalStatus == null}">
						<input type="checkbox" id="${mesanInfo.id}" class="i-checks">
				 	</c:if>
				 	<c:if test="${mesanInfo.approvalStatus eq '1'}">
						<input type="checkbox" id="${mesanInfo.id}" class="i-checks">
				 	</c:if>
				</td>
				<td>${status.index+1}</td>
				<td><a  title='${mesanInfo.name}'  href="#" onclick="openDialogView('查看资料信息', '${ctx}/resource/mesanInfo/viewForm?id=${mesanInfo.id}','800px', '500px')">
					<c:choose>  
				        <c:when test="${fn:length(mesanInfo.name) >8 }">  
				           ${fn:substring(mesanInfo.name, 0, 7)}…
				         </c:when>  
				        <c:otherwise>  
				           ${fn:substring(mesanInfo.name, 0, 8)}
				         </c:otherwise>  
				     </c:choose>   
				</a></td>
				<td title=' ${mesanInfo.remarks}'>
					<c:choose>  
				        <c:when test="${fn:length(mesanInfo.remarks) >8 }">  
				           ${fn:substring(mesanInfo.remarks, 0, 7)}…
				         </c:when>  
				        <c:otherwise>  
				           ${fn:substring(mesanInfo.remarks, 0, 8)}
				         </c:otherwise>  
				     </c:choose>  
				</td>
				<td>
					<fmt:formatDate value="${mesanInfo.createDate}" pattern="yyyy-MM-dd HH:mm" type="both"/>
				</td>
				<td>
					${mesanInfo.createBy.name}
				</td>
				<td>
					<c:if test="${mesanInfo.approvalStatus==3 }">
						通过
					</c:if>
					${mesanInfo.approvalStatus==null?"讲师提交":fns:getDictLabel(mesanInfo.approvalStatus, 'approval_record_status', '')}
				</td>
				<td>
				<c:if test="${mesanInfo.isCreateAdmin eq '1'}">
						<c:if test="${mesanInfo.approvalStatus eq '3'}">
							未发布
						</c:if>
						<c:if test="${mesanInfo.approvalStatus eq '0'}">
							发布成功
						</c:if>
					</c:if>
					<c:if test="${mesanInfo.isCreateAdmin!=1}">
						非管理员添加
					</c:if>
				</td>
				<td>
					<shiro:hasPermission name="resource:mesanInfo:edit">
						<c:if test="${mesanInfo.approvalStatus==3}">
							<a href="${ctx}/resource/mesanInfo/fbMesanInfo?resourceId=${mesanInfo.id}" onclick="return confirmx('确认要发布资料吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-edit"></i>发布资料</a>
						</c:if>
					</shiro:hasPermission>
					<shiro:hasPermission name="resource:mesanInfo:view">
						<a href="#" onclick="openDialogView('查看资料信息', '${ctx}/resource/mesanInfo/viewForm?id=${mesanInfo.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
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