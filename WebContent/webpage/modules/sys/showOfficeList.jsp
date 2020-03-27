<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>部门列表</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#grade").prepend("<option value=''>请选择机构等级</option>");
			$("#grade").val($("#gradeValue").val());
			initDistrictDao();
			$("#distrctId").val($("#distrctIdValue").val());
			/* initProvince($("#distrctIdValue").val());
			$("#provinceId").val($("#provinceIdValue").val());
			initCity($("#provinceIdValue").val());
			$("#cityId").val($("#cityIdValue").val());  */
		});
		function changeOffic(){
			var officIds="" ;
			$('input:checkbox[name="chk"]:checked').each(function(){
				officIds += this.value+",";
			});
			window.parent.parentOfficeChange(officIds);
		}
		
		function initDistrictDao(){
			$.ajax({
				  url: "${ctx}/sys/district/getAllDistrict",
				  type: 'POST',
				  dataType: 'json',
				  async: false,
				  data: {},
				  success: function(responseText){
					  $("#distrctId").html("<option value=''>--请选择大区--</option>")
					  if(responseText){
					   for(var i=0;i<responseText.length;i++){
						   var obj = responseText[i];
						   $("#distrctId").append("<option value='"+obj['id']+"'>"+obj['name']+"</option>");
						 }
					  }else{
						  $("#distrctId").append("");
					  }
				  }
			}); 
		}
	
	</script>
	
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
    <div class="ibox-content">
    <sys:message content="${message}"/>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="sys:office:add">
				<table:addRow url="${ctx}/sys/office/form?parent.id=${office.id}" title="机构" width="800px" height="620px" ></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:office:import">
				<table:importExcel url="${ctx}/sys/office/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:office:export">
	       		<table:exportExcel url="${ctx}/sys/office/export"></table:exportExcel><!-- 导出按钮 -->
	       	</shiro:hasPermission>
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
			</div>
		<div class="pull-right">
		<form:form id="searchForm" modelAttribute="office" action="${ctx}/sys/office/showOfficeList/" method="post" class="form-inline">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<div class="form-group">
			<table>
				<tr>
				<td><span>机构分类：</span></td>
				<td width="5px;"></td>
				<td width="150px;" height="20px;">
					<sys:treeselect id="officeType" name="officeType" value="${office.officeType}" labelName="officeTypeName" labelValue="${office.officeTypeName}"
						title="机构分类" url="/sys/officeType/treeData"  cssClass="form-control" />
				</td>
				<td width="5px;"></td>
				<!-- <td><span>机构等级</span></td> -->
				<td width="10px;"></td>
				<td>
					<input type="hidden" value="${office.grade}" id="gradeValue" />
					<form:select path="grade" class="form-control" id="grade">
						<form:options items="${fns:getDictList('sys_office_grade')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</td>
				<td width="5px;"></td>
				<!-- <td><span>归属区域</span></td> -->
				<td width="5px;"></td>
				<td width="150px;">
					<input type="hidden" id = "distrctIdValue" value="${office.distrctId}"/>
		         	<select name="distrctId" id="distrctId" style="width:150px" onchange="initProvince(this.value)" class="form-control required">
		         		
		         	</select>
				</td>
				</tr>
				</table>
			 </div>
			 <button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i>搜索</button>
			 
		 </form:form>
		</div>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="officeTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th></th>
				<th>序号</th>
				<th>机构名称</th>
<!-- 				<th>机构编码</th> -->
				<th>机构分类</th>
				<th>机构级别</th>
				<th>FED级别</th>
				<th>状态</th>
				<th>大区</th>
				<th>省</th>
				<th>市</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="office" varStatus="varStatus">
			<tr>
				<td><input type="checkbox" name = "chk" value = "${office.id}" onclick="changeOffic()" id="${office.id}" class="i-checks"></td>
				<td>${varStatus.index+1}</td>
				<td>${office.name}</a></td>
				<td>${office.parentName}</td>
				<td>${fns:getDictLabel(office.grade, 'sys_office_grade', '')}</td>
				<td>${fns:getDictLabel(office.fedLevel, 'sys_officefed_level', '')}</td>
				<td>${fns:getDictLabel(office.useable, 'office_delflag', '')}</td>
				<td>${office.distrctName}</td>
				<td>${office.provinceName}</td>
				<td>${office.cityName}</td>
				<td>
				<shiro:hasPermission name="sys:office:view">
					<a href="#" onclick="openDialogView('查看机构', '${ctx}/sys/office/form?id=${office.id}','800px', '620px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="sys:office:edit">
					<a href="#" onclick="openDialog('修改机构', '${ctx}/sys/office/form?id=${office.id}','800px', '620px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="sys:office:del">
					<a href="${ctx}/sys/office/delete?id=${office.id}" onclick="return confirmx('要删除该机构吗？', this.href)" class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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