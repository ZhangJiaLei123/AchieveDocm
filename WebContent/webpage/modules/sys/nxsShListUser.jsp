]<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/><base>
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
			    openDialog("审核"+'内训师信息',"${ctx}/sys/approvalRecord/form?ids="+id+"&url=/sys/user/nxsShListUser","400px", "300px","");
		}
		function shOneData(id){
			openDialog("审核"+'内训师信息',"${ctx}/sys/approvalRecord/form?ids="+id+"&url=/sys/user/nxsShListUser","400px", "300px","");
		}
		</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<!-- <div class="ibox-title">
			<h5>用户列表 </h5>
			<div class="ibox-tools">
			</div>
	</div> -->
    <div class="ibox-content">
	<sys:message content="${message}"/>
		<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="sys:user:edit">
				 <button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="left" onclick="shResource()" title="审核"><i class="fa fa-file-text-o"></i> 审核</button>
			</shiro:hasPermission>
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
			</div>
		<div class="pull-right">
			<form:form id="searchForm" modelAttribute="user" action="${ctx}/sys/user/nxsShListUser" method="post" class="form-inline">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
				<div class="form-group">
					<span>机构分类：</span>
					<sys:treeselect id="officeType" name="officeType" value="${user.officeType}" labelName="officeTypeName" labelValue="${user.officeTypeName}" 
					title="机构分类" url="/sys/officeType/treeData?type=1" cssClass=" form-control input-sm" />
					<form:input path="officeName" htmlEscape="false" maxlength="50" class=" form-control input-sm" placeholder="请输入组织机构名称"/>
					<form:input path="name" htmlEscape="false" maxlength="50" class=" form-control input-sm" placeholder="请输入真实姓名"/>
				 </div>
				 <button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 搜索</button>
				 
			</form:form>
		</div>
	</div>
	</div>
	
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th><input type="checkbox" class="i-checks"></th>
				<th>序号</th>
				<th>用户名称</th>
				<th>真实姓名</th>
				<th>邮箱</th>
				<th>联系电话</th>
				<th >机构分类</th>
				<th >组织机构</th>
				<th >岗位信息</th>
				<th >审核状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="user" varStatus="status">
			<tr>
				<td> <input type="checkbox" id="${user.id}" class="i-checks"></td>
				<td>${status.index +1}</td>
				<td>${user.loginName}</td>
				<td>${user.name}</td>
				<td>${user.email}</td>
				<td>${user.phone}</td>
				<td>${user.officeTypeName}</td>
				<td>${user.officeName}</td>
				<td>${user.postName}</td>
				
				<td>${user.shStatus==null?"待审核":fns:getDictLabel(user.shStatus, 'approval_record_status', '')}</td>
				<td>
				<c:if test="${user.loginName!='admin'}">
					<shiro:hasPermission name="sys:user:view">
						<a href="#" onclick="shOneData('${user.id}')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 审核</a>
					</shiro:hasPermission>
					<c:if test="${user.role.enname == 'nx_teacher'}">
						<a href="${user.verifyUrl}" download class="btn btn-primary btn-xs" ><i class="fa fa-download"></i>下载认证资料</a>
					</c:if>
				</c:if>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<table:page page="${page}"></table:page>
	<br/>
	<br/>
	</div>
	</div>
	</div>
</body>
</html>