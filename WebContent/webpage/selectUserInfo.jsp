<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>组织人员</title>
<meta charset="utf-8">
<meta name="decorator" content="default"/>
</head>
<script type="text/javascript">
	
</script>
<body>
<jsp:include page="/include/head.jsp"></jsp:include>
<script type="text/javascript">
	$($("a",$("#topA"))[1]).attr("class","active");
	$(document).ready(function() {
		$("#grade").prepend("<option value=''>请选择</option>");
		//$("#grade").val("");
	});
	
	function exportExcel(){
		top.layer.confirm('确认要导出Excel吗?', {icon: 3, title:'系统提示'}, function(index){
		    //do something
		    	//导出之前备份
		    	var url =  $("#searchForm").attr("action");
		    	var pageNo =  $("#pageNo").val();
		    	var pageSize = $("#pageSize").val();
		    	//导出excel
		        $("#searchForm").attr("action","${ctx}/sys/user/teacherExport");
			    $("#pageNo").val(-1);
				$("#pageSize").val(-1);
				$("#searchForm").submit();

				//导出excel之后还原
				$("#searchForm").attr("action",url);
			    $("#pageNo").val(pageNo);
				$("#pageSize").val(pageSize);
		    top.layer.close(index);
		});
	}
</script>
<!--头部 end-->    
<!--身体 start-->
<div class="row" style="min-height: 530px;">
	<div style="height: 90px;  border-bottom: 1px solid #d8d8d8;padding-top: 30px;" >
		<a style="margin-right: 40px;" href="${pageContext.request.contextPath}/a/selectOfficeInfo">组织机构</a>
		<a style="color: #18a589;margin-right: 40px;" href="${pageContext.request.contextPath}/a/selectUserInfo">人员</a>
		<div style="float:right">
		<form:form id="searchForm" modelAttribute="user" action="${pageContext.request.contextPath}/a/selectUserInfo" method="post" class="form-inline">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table >
			<tr style="line-height: 30px;">
				<td><span>机构分类：</span></td>
				<td width="5px;"></td>
				<td width="150px;" height="20px;">
					<sys:treeselect id="officeType" name="officeType" value="${user.officeType}" labelName="officeTypeName" labelValue="${user.officeTypeName}" 
						title="机构分类" url="/sys/officeType/treeData?type=1" cssClass=" form-control input-sm" />
				</td>
				<td width="5px;"></td>
				<td><form:input path="officeName" htmlEscape="false" maxlength="50" class=" form-control input-sm" placeholder="组织机构名称"/></td>
				<td width="10px;"></td>
				<td>
					<form:input path="name" htmlEscape="false" maxlength="50" class=" form-control input-sm" placeholder="用户名"/>
				</td>
				<td width="10px;"></td>
				<td>
					 <button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i>搜索</button>
				</td>
				<td width="10px;"></td>
				<td>
					<a href ="javascript:void(0)" class="btn btn-primary btn-rounded btn-outline btn-sm" onclick="exportExcel()" ><i class="fa fa-file-excel-o"></i>导出</button>
				</td>
			</tr>
		</table>
		</form:form>
		</div>
	</div>
	
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th >姓名</th>
				<th >联系电话</th>
				<th >EMAIL</th>
				<th >岗位名称</th>
				<th >岗位级别</th>
				<th >岗位分类</th>
				<th >操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="user" varStatus="varStatus">
			<tr>
				<td>${user.name}</td>
				<td>${user.phone}</td>
				<td>${user.email}</td>
				<td>${user.postName}</td>
				<td>${user.postLevelName}</td>
				<td>${user.postTypeName}</td>
				<td >
					<a href="#" onclick="openDialogView('查看用户', '${ctx}/sys/user/teacherViewUserForm?id=${user.id}','800px', '550px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i>查看</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<table:page page="${page}"></table:page>
</div>
<!--尾部 start-->
<div class="copyright">© 云帆互联 2014 汽车服务网 京ICP备14016447号</div>
<!--尾部 end-->

</body>
</html>
    