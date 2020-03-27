<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>课程管理管理</title>
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
		    openDialog("审核"+'课程信息',"${ctx}/sys/approvalRecord/form?ids="+id+"&url=/course/courseInfo/shList","400px", "300px","");
	}
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>课程管理列表 </h5>
		<div class="ibox-tools">
		</div>
	</div>
    
    <div class="ibox-content">
	<sys:message content="${message}"/>
	
	<!--搜索条件-->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="courseInfo" action="${ctx}/course/courseInfo/shList" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>课程名称：</span>
				<form:input path="couName" htmlEscape="false" maxlength="200"  class=" form-control input-sm"/>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="course:courseInfo:edit">
				 <button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="left" onclick="shResource()" title="审核"><i class="fa fa-file-text-o"></i> 审核</button>
			</shiro:hasPermission>
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
			</div>
		<div class="pull-right">
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 搜索</button>
			
		</div>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<th  class="sort-column couName">课程名称</th>
				<th  class="sort-column resResourceName">资源名称</th>
				<th  >报名时间</th>
				<th  >学习时间</th>
				<th  >发布人</th>
				<th >课程状态</th>
				<th >审核状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="courseInfo">
			<tr>
				<td> <input type="checkbox" id="${courseInfo.id}" class="i-checks"></td>
				<td>
					${courseInfo.couName}
				</a>
				<td>
					${courseInfo.resResourceName}
				</td>
				<td>
					<fmt:formatDate value="${courseInfo.regStartTime}" pattern="yyyy-MM-dd"/>~<fmt:formatDate value="${courseInfo.regEndTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${courseInfo.stuStartTime}" pattern="yyyy-MM-dd"/>~<fmt:formatDate value="${courseInfo.stuEndTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${courseInfo.updateBy.name}
				</td>
				<td>
					${courseInfo.updateBy.name}提交
				</td>
				
				<td>
					${courseInfo.resourceStatus==null?"待审核":fns:getDictLabel(courseInfo.resourceStatus, 'approval_record_status', '')}
				</td>
				<td>
					<a href="${ctx}/course/courseInfo/showResourceInfo?id=${courseInfo.id}" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
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