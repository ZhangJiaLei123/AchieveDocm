<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>选择用户</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function changeUser(obj){
			var bl = obj.checked;
			if(!bl){
				$("#checkAllUser").attr("checked",false);
			}
			var userIds="" ;
			$('input:checkbox[name="userchk"]:checked').each(function(){
				userIds += this.value+",";
			});
			var name = $("#name").val();
			var userCheckAll =  "";
			if($("#checkAllUser").is(':checked')){
				userCheckAll = "1";
    		}else{
    			userCheckAll = "";
    		}
			window.parent.parentUserChange(userIds,name,userCheckAll);
		}
		
		function checkAllUserFun(obj){
			var bl = obj.checked;
			$('input:checkbox[name="userchk"]').each(function(){
				this.checked = bl;
			});
			changeUser();
		}
	</script>
</head>
<body>
<div style="padding:5px;">
		<!-- 查询条件 -->
	<form:form id="searchForm" modelAttribute="user" action="${ctx}/sys/user/selShowUser?type=${param.type}" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		
		<table>
			<tr>
				<td><input type="checkbox" id="checkAllUser" onclick="checkAllUserFun(this)" <c:if test="${param.userCheckAll==1}">checked</c:if>><label for="checkAllUser">全选</label></td>
				<td style="padding:5px;">用户名：</td>
				<td><input type="text" name="name" id="name" value="${param.name}" maxlength="50" class=" form-control input-sm"/></td>
				<td><button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button></td>
			</tr>
		</table>
	</form:form>
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
	<tr>
		<td>
		<c:forEach items="${page.list}" var="user">
				<div style="width: 100px;float: left">
					<input type="checkbox" id="${user.id}"   name="userchk" onclick="changeUser(this)" 
					<c:if test="${user.isChecked==1 }">checked</c:if> value="${user.id}"><label for="${user.id}" style="cursor:pointer;">${user.name}</label>
				</div>
		</c:forEach>
		</td>
		</tr>
	</table>
	<table:page page="${page}"></table:page>
	</div>
</body>
</html>