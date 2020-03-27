<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>资料信息管理</title>
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
	<!-- <div class="ibox-title">
		<h5>资料信息列表 </h5>
	</div> -->
    
    <div class="ibox-content">
<%-- 	<sys:message content="${message}"/> --%>
	<script type="text/javascript">
		function initIndex(){
			$.ajax({
				  url: "${ctx}/sys/approvalRecord/initLuceneIndex",
                  success: function (msg) {
                   alert(msg);
              }
		  });
		}
	</script>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="resource:mesanInfo:add">
				<table:addRow url="${ctx}/resource/mesanInfo/form" title="资料信息"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
<!-- 			<shiro:hasPermission name="resource:mesanInfo:edit">
			    <table:editRow url="${ctx}/resource/mesanInfo/form" title="资料信息" id="contentTable"></table:editRow><!-- 编辑按钮 -->
<!-- 			</shiro:hasPermission> -->
			<shiro:hasPermission name="resource:mesanInfo:del">
				<table:delRow url="${ctx}/resource/mesanInfo/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
		   <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="initIndex()" title="初始化索引">初始索引</button>
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
			</div>
		<div class="pull-right">
			<form:form id="searchForm" modelAttribute="mesanInfo" action="${ctx}/resource/mesanInfo/" method="post" class="form-inline">
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
				<th> <input type="checkbox" class="i-checks"></th>
				<th  >序号</th>
				<th  >资料类别</th>
				<th  >资料编码</th>
				<th  >资料名称</th>
				<th  >资料描述</th>
				<th  >上传时间</th>
				<th  >上传人</th>
				<th  >资料状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="mesanInfo" varStatus="status">
			<tr>
				<td> <input type="checkbox" id="${mesanInfo.id}" class="i-checks"></td>
				<td>${status.index + 1}</td>
				<td>${mesanInfo.mesanTypeName}</td>
				<td>${mesanInfo.mesanCode}</td>
				<td><a title='${mesanInfo.name}'  href="#" onclick="openDialogView('查看资料信息', '${ctx}/resource/mesanInfo/viewForm?id=${mesanInfo.id}','800px', '500px')">
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
					${mesanInfo.approvalStatus==null?"讲师提交":fns:getDictLabel(mesanInfo.approvalStatus, 'approval_record_status', '')}
				</td>
				<td width="220px">
					<shiro:hasPermission name="resource:mesanInfo:view">
						<a href="#" onclick="openDialogView('查看资料信息', '${ctx}/resource/mesanInfo/viewForm?id=${mesanInfo.id}','800px', '600px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="resource:mesanInfo:edit">
    					<a href="#" onclick="openDialog('修改资料信息', '${ctx}/resource/mesanInfo/form?id=${mesanInfo.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission><!-- 
    				<shiro:hasPermission name="resource:mesanInfo:del">
						<a href="${ctx}/resource/mesanInfo/delete?id=${mesanInfo.id}" onclick="return confirmx('确认要删除该资料信息吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission> -->
					<shiro:hasPermission name="resource:mesanInfo:export">
						<a  target="_blank" href="${ctx}/resource/mesanInfo/downloadMaterial?mesanInfoId=${mesanInfo.id}" class="btn btn-info btn-xs"> 下载</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="resource:mesanInfo:edit">
						<c:choose>
							<c:when test="${mesanInfo.isViewTop ==1}">
								<a  href="${ctx}/resource/mesanInfo/setViewTop?id=${mesanInfo.id}&isViewTop=" class="btn btn-info btn-xs"> 取消置顶</a>
							</c:when>
							  <c:otherwise> 
							  	<a   href="${ctx}/resource/mesanInfo/setViewTop?id=${mesanInfo.id}&isViewTop=1" class="btn btn-info btn-xs"> 置顶</a>
							  </c:otherwise>
						</c:choose>
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