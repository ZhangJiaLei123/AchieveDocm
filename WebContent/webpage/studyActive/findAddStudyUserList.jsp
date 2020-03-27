]<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/><base>
		<script type="text/javascript">
			function checkUserAll(obj){
				var bl = obj.checked;
				$("input[type='checkbox'][name='chk']").each(function (){
					$(this).attr("checked",bl)
				});
			}
		</script>
</head>
<body >
	<div class="wrapper wrapper-content">
			<form:form id="searchForm" modelAttribute="user" action="${ctx}/findAddStudyUserList" method="post" class="form-inline">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<input type="hidden" value="${user.userActivityId}" id="userActivityId" name="userActivityId"> 
				<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
				<table>
					<tr>
						<td width="100px;">
							<input type="checkbox"  id="checkAll"  onclick="checkUserAll(this)"><label for="checkAll" style="cursor: pointer;">全选</label>
							
						</td>
						<td width="100px;">
							<span>机构分类：</span>
						</td>
						<td width="140px;">
							<sys:treeselect id="officeType" name="officeType" value="${user.officeType}" labelName="officeTypeName" labelValue="${user.officeTypeName}" 
						title="机构分类" url="/sys/officeType/treeData?type=1" cssClass=" form-control input-sm" />
						</td>
						<td width="140px;" style="padding-left: 5px;">
							<form:input path="officeName" htmlEscape="false" maxlength="50" class=" form-control input-sm" placeholder="组织机构名称"/>
						</td>
						<td width="140px;" style="padding-left: 5px;">
						<form:input path="name" htmlEscape="false" maxlength="50" class=" form-control input-sm" placeholder="用户名"/>
						</td>
						<td width="80px;" style="padding-left: 5px;">
						<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i>搜索</button>
						</td>
						<td width="80px;">
							
						</td>
					</tr>
				</table>
		
		<div style="padding: 10px;height:200px;">
			<c:forEach items="${page.list}" var="user" varStatus="varStatus">
				<div style="width:100px;float: left"><input type="checkbox" name="chk" id="${user.id}" value="${user.id}" ><label for="${user.id}" style="cursor: pointer;">${user.name}</label></div>
			</c:forEach>
		</div>
	<table:page page="${page}"></table:page>
	</div>
		</form:form>
</body>
</html>