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
		function changeOffic(obj){
			var bl = obj.checked;
			if(!obj.checked){
				$("#checkAllOffice").attr("checked",false);
			}
			changeUserSel();
		}
		
		function checkAllOfficeFun(obj){
			$("input[type='checkbox'][name='chk']").each(function(){
				$(this).attr("checked",obj.checked);
			});
			changeUserSel();
		}
		
		function changeUserSel(){
			var str = "";
			var roleCode = $("#roleCode").val();
			if(roleCode == "nx_teacher"){
				$("input[type='radio'][name='rdo']:checked").each(function(){
					str+=$(this).val()+",";
				});
			}else{
				$("input[type='checkbox'][name='chk']:checked").each(function(){
					str+=$(this).val()+",";
				});
			}
			var officeType = $("#officeId").val();
			var officeLevel = $("#grade").val();
			var aearId = $("#areaId").val();
			var isChecked = "";
			if($("#checkAllOffice").attr("checked")){
				isChecked = "1";
			}
			window.parent.parentOfficeChange(isChecked,str,officeType,officeLevel,aearId);
		}
	</script>
	
</head>
<body>
	<form:form id="searchForm" modelAttribute="office" action="${ctx}/sys/office/selOfficeList/" method="post">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<div class="form-group">
		<div style="padding:5px;">
		<table style="width: 100%">
			<tr>
				<td width="60px">
				<input type="hidden" value="${roleCode}" id="roleCode"/>
				<c:if test="${roleCode != 'nx_teacher' }">
					<input type="checkbox" value="1" id="checkAllOffice" onclick="checkAllOfficeFun(this)"  style="width: 15px; height: 25px;border:none"  name="checkAllOffice"/><label style="cursor:pointer;" for="checkAll">全选</label>
				</c:if>
				</td>
				<td width="70px"><span>机构分类：</span></td>
			<td width="140px">
				<sys:treeselect id="office" name="officeType" value="${office.officeType}" labelName="officeTypeName" labelValue="${office.officeTypeName}"
					title="机构" url="/sys/officeType/treeData" extId="${office.officeType}"  cssClass="form-control"  />
			</td>
			<td width="10px"></td>
			<td width="80px"><span>机构等级：</span></td>
			<td width="140px">
				<form:select path="grade" class="form-control" id="grade">
					<form:options items="${fns:getDictList('sys_office_grade')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</td>
			<td width="10px"></td>
			<td width="70px"><span>归属区域：</span></td>
			<td width="140px">
				<sys:treeselect id="area" name="areaId" value="${office.area.id}" labelName="area.name" labelValue="${office.area.name}"
					title="区域" url="/sys/area/treeData" cssClass="form-control required"/>
			</td>
			<td width="10px"></td>
			<td><button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i>搜索</button></td>
			</tr>
		</table>
		</div>
	</form:form>
	<div style="width:100%;padding:3px;">
	<!-- 表格 -->
	<table id="officeTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable" style="width:100%;">
		<thead>
			<tr>
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
				<td>
					<c:if test="${roleCode != 'nx_teacher' }">
						<c:set var="isCheck" scope="session" value="false"/>
						<c:forEach items="${lsUserOffice}" var="userOffice">
							<c:if test="${userOffice.officeId eq office.id }">
								<c:set var="isCheck" scope="session" value="true"/>
							</c:if>
						</c:forEach>
						<c:if test="${isCheck eq 'true' }">
							<input type="checkbox"  style="width: 15px; height: 25px;border:none" checked="checked"  name = "chk" value = "${office.id}" onclick="changeOffic(this)" id="${office.id}" >
						</c:if>
						<c:if test="${isCheck eq 'false' }">
							<input type="checkbox"  style="width: 15px; height: 25px;border:none" name = "chk" value = "${office.id}" onclick="changeOffic(this)" id="${office.id}" >
						</c:if>
					</c:if>
					<c:if test="${roleCode eq 'nx_teacher' }">
					<c:set var="isCheck" scope="session" value="false"/>
						<c:forEach items="${lsUserOffice}" var="userOffice">
							<c:if test="${userOffice.officeId eq office.id }">
								<c:set var="isCheck" scope="session" value="true"/>
							</c:if>
						</c:forEach>
						 <c:if test="${isCheck eq 'true' }">
						      <input type="radio"  style="width: 15px; height: 25px;border:none" checked="checked"  name = "rdo" value = "${office.id}" onclick="changeOffic(this)" id="${office.id}" >
						  </c:if>
						 <c:if test="${isCheck eq 'false' }">
						       <input type="radio"  style="width: 15px; height: 25px;border:none"  name = "rdo" value = "${office.id}" onclick="changeOffic(this)" id="${office.id}" >
						 </c:if>
					</c:if>
				</td>
				<td>${office.name}</a></td>
				<td>${office.code}</td>
				<td>${office.parentName}</td>
				<td>${fns:getDictLabel(office.grade, 'sys_office_grade', '')}</td>
				<td>${office.distrctName}</td>
				<td>${office.provinceName}</td>
				<td>${office.cityName}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
		<!-- 分页代码 -->
	<table:page page="${page}"></table:page>
	</div>
</body>
</html>