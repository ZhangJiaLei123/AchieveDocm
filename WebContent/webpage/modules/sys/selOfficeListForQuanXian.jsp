l<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>选择部门</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#grade").prepend("<option value='' >请选择</option>");
			$("#grade").val("${office.grade}");
		});
		function changeOffic(){
			var officIds="" ;
			$('input:checkbox[name="chk"]:checked').each(function(){
				officIds += this.value+",";
			});
			window.parent.parentOfficeChange(officIds);
		}
		
		function checkBoxAll(obj){
			var bl = obj.checked;
			$('input:checkbox[name="chk"]').each(function(){
				this.checked = bl;
			});
		}
		
		function checkAllOfficeFun(obj){
			var type = window.frameElement && window.frameElement.id || '';
			var isCheck = "";
			var officeTyp = $("#officeId").val();
			var officeLevel = $("#grade").val();
			var areaId = $("#areaId").val();
			if(obj.checked){
				 isCheck = "1";
				 officeTyp = $("#officeId").val();
				 officeLevel = $("#grade").val();
				 areaId = $("#areaId").val();
			}
			window.parent.setIsCheckAllOffice(type,isCheck,officeTyp,officeLevel,areaId);
		}
	</script>
	
</head>
<body>
	<form:form id="searchForm" modelAttribute="office" action="${ctx}/sys/office/selOfficeList/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<div class="form-group">
		<div style="padding:5px;">
		<table >
			<tr>
				<c:if test="${param.roleName!='内训师'}">
					<td width="60px"><input type="checkbox" id="checkAllOffice" onclick="checkAllOfficeFun(this)"  style="width: 15px; height: 25px;border:none"  name="checkAllOffice"/><label style="cursor:pointer;" for="checkAll">全选</label></td>
				</c:if>
			<td width="70px"><span>机构分类：</span></td>
			<td>
				<sys:treeselect id="office" name="officeType" value="${office.officeType}" labelName="officeTypeName" labelValue="${office.officeTypeName}"
					title="机构" url="/sys/officeType/treeData" extId="${office.officeType}"  cssClass="form-control"  />
			</td>
			<td width="10px"></td>
			<td width="80px"><span>机构等级：</span></td>
			<td width="100px">
				<form:select path="grade" class="form-control" id="grade">
					<form:options items="${fns:getDictList('sys_office_grade')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</td>
			<td width="10px"></td>
			<td width="70px"><span>归属区域：</span></td>
			<td >
				<sys:treeselect id="area" name="areaId" value="${office.area.id}" labelName="area.name" labelValue="${office.area.name}"
					title="区域" url="/sys/area/treeData" cssClass="form-control required"/>
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
<!-- 			<th><input type="checkbox" id="checkAll" style="width: 15px; height: 25px;border:none" onclick="checkBoxAll(this)"></th> -->
				<th>&nbsp;</th>
				<th>机构名称</th>
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
			<c:choose>
				<c:when test="${param.roleName=='内训师'}">
					<td><input type="radio"  style="width: 15px; height: 25px;border:none" id="chk" name = "chk" value = "${office.id}" onclick="changeOffic()" id="${office.id}" ></td>
				</c:when>
				<c:otherwise>
					<td><input type="checkbox"  style="width: 15px; height: 25px;border:none" id="chk" name = "chk" value = "${office.id}" onclick="changeOffic()" id="${office.id}" ></td>
				</c:otherwise>		
			</c:choose>
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