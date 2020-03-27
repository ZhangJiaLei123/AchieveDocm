l<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>选择部门</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
	</script>
	
</head>
<body>
	<form:form id="searchForm" modelAttribute="office" action="${ctx}/sys/office/selUserOfficeList/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<div class="form-group">
		<div style="padding:5px;">
		<table >
			<tr>
			<td width="80px"><span>机构分类：</span></td>
			<td>
				<sys:treeselect id="officeType" name="officeType" value="${office.officeType}" labelName="officeTypeName" labelValue="${office.officeTypeName}"
					title="机构" url="/sys/officeType/treeData" extId="${officeType}"  cssClass="form-control"  />
			</td>
			<td width="10px"></td>
			<td width="80px"><span>机构等级：</span></td>
			<td>
				<form:select path="grade" class="form-control">
					<form:options items="${fns:getDictList('sys_office_grade')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</td>
			<td width="10px"></td>
			<td width="80px"><span>机构名称：</span></td>
			<td >
				 	<form:input path="name" htmlEscape="false" maxlength="100" class="form-control"/>
			</td>
			<td width="10px"></td>
			<td><button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button></td>
			</tr>
		</table>
		</div>
	</form:form>
	<div style="width:100%;padding:3px;">
	<!-- 表格 -->
	<table id="officeTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th></th>
				<th>机构名称:</th>
				<th>机构编码</th>
				<th>机构分类</th>
				<th>机构级别</th>
				<th>大区</th>
				<th>省</th>
				<th>市</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="office">
			<tr>
				<td><input type="radio" name = "rdo" value = "${office.name}"  id="${office.id}" <c:if test="${param.officeId==office.id }">checked</c:if>></td>
				<td>${office.name}</a></td>
				<td>${office.code}</td>
				<td>${office.parentName}</td>
				<td>${fns:getDictLabel(office.grade, 'sys_office_grade', '')}</td>
				<td>${office.DQ}</td>
				<td>${office.SF}</td>
				<td>${office.SQ}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
		<!-- 分页代码 -->
	<table:page page="${page}"></table:page>
	</div>
</body>
</html>