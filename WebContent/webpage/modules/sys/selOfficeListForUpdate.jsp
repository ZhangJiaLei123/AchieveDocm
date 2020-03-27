l<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>选择部门</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#grade").prepend("<option value='' >--请选择--</option>");
			$("#grade").val("${office.grade}");
			//区域信息
			initDistrictDao();
			$("#distrctId").val($("#distrctIdValue").val());
			initProvince($("#distrctIdValue").val());
			$("#provinceId").val($("#provinceIdValue").val());
			initCity($("#provinceIdValue").val());
			$("#cityId").val($("#cityIdValue").val()); 
			
			initCheckOffice();
			
		});
		//页面查询及页面加载初始化选中的组织
		function initCheckOffice(){
			if($("#isCheckAll",parent.document).val()){
				$("#checkAllOffice").attr("checked",true);
				$("input[type='checkbox'][name='chk']").each(function(){
					$(this).attr("checked",true);
				})
			}else{
				var checkedIds = $("#officIds",parent.document).val();
				var ids = checkedIds.split(",");
				for(var i=0;i<ids.length;i++ ){
					var obj = $("#"+ids[i]);
					if(obj){
						obj.attr("checked",true);
					}
				}
			}
			
		};
		function checkAllOfficeFun(obj){
			var bl = obj.checked;
			$("input[type='checkbox'][name='chk']").each(function(){
				$(this).attr("checked",bl);
			});
			parent.changeIsCheckAll(obj);
		}
		
		function changeOffic(obj){
			var bl = obj.checked;
			if(!bl){
				$("#checkAllOffice").attr("checked",false);
				var ids = "";
				$("input[type='checkbox'][name='chk']:checked").each(function(){
					ids+=$(this).val()+",";
				});
				parent.changeIsCheckAll($("#checkAllOffice"),ids);
			}
			//修改选中的值
			parent.changeCheckEdIds(obj);
		}
		function initDistrictDao(){
			$.ajax({
				  url: "${ctx}/sys/district/getAllDistrict",
				  type: 'POST',
				  dataType: 'json',
				  async: false,
				  data: {},
				  success: function(responseText){
					  $("#distrctId").html("<option value=''>--请选择--</option>")
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
			initProvince($("#distrctId").val());
		}
		function initProvince(districtId){
			$.ajax({
				  url: "${ctx}/sys/district/getProinceByDistrictId",
				  type: 'POST',
				  dataType: 'json',
				  async: false,
				  data: {districtId:districtId},
				  success: function(responseText){
					  $("#provinceId").html("<option value=''>--请选择--</option>");
					  if(responseText){
					   for(var i=0;i<responseText.length;i++){
						   var obj = responseText[i];
						   $("#provinceId").append("<option value='"+obj['provinceid']+"'>"+obj['proincename']+"</option>");
						 }
					  }else{
						  $("#provinceId").append("");
					  }
				  }
			}); 
			initCity($("#provinceId").val());
		}
		function initCity(provinceId){
			var districtId = $("#distrctId").val();
			$.ajax({
				  url: "${ctx}/sys/district/getCityByDistrictId",
				  type: 'POST',
				  dataType: 'json',
				  async: false,
				  data: {districtId:districtId,provinceId:provinceId},
				  success: function(responseText){
					  $("#cityId").html("<option value=''>--请选择--</option>");
					  if(responseText){
					   for(var i=0;i<responseText.length;i++){
						   var obj = responseText[i];
						   $("#cityId").append("<option value='"+obj['cityid']+"'>"+obj['cityname']+"</option>");
						 }
					  }else{
						  $("#cityId").append("");
					  }
				  }
			}); 
		}
		
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
			$("#searchForm").submit();
		}
	</script>
	
</head>
<body>
	<form:form id="searchForm" modelAttribute="office" action="${ctx}/sys/office/selOfficeListForUpdate" method="post">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="searchType" name="searchType" type="hidden" value="${searchType}"/>
		<div class="form-group">
		<div style="padding:5px;">
		<table style="width: 100%">
			<tr>
				<td width="60px">
					<input type="checkbox" value="1" id="checkAllOffice" onclick="checkAllOfficeFun(this)"  name="checkAllOffice" <c:if test="${condition.orgCheckAll==1}">checked</c:if>/><label style="cursor:pointer;" for="checkAllOffice">全选</label></td>
				<td width="70px"><span>机构分类：</span></td>
			<td width="140px">
				<sys:treeselect id="officeType" name="officeType" value="${office.officeType}" labelName="officeTypeName" labelValue="${office.officeTypeName}"
					title="机构" url="/sys/officeType/treeData" extId=""  cssClass="form-control"  />
			</td>
			<td width="5px"></td>
			<td width="80px"><span>机构等级：</span></td>
			<td width="140px">
				<form:select path="grade" class="form-control">
					<form:options items="${fns:getDictList('sys_office_grade')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</td>
			<td width="5px"></td>
			<td width="70px"><span>归属区域：</span></td>
			<td width="5px"></td>
			<td width="120px;">
					<input type="hidden" id = "distrctIdValue" value="${office.distrctId}"/>
		         	<input type="hidden" id = "provinceIdValue" value="${office.provinceId}"/>
		         	<input type="hidden" id = "cityIdValue" value="${office.cityId}"/>
		         	<select name="distrctId" id="distrctId" style="width:120px" onchange="initProvince(this.value)" class="form-control required">
		         	</select>
				</td>
				<td width="5px"></td>
				<td>
					<select name="provinceId" id="provinceId" onchange="initCity(this.value)" style="width:100px" class="form-control required">
		         	</select>
				</td>
				<td width="5px"></td>
				<td>
					<select name="cityId" id="cityId" style="width:100px" class="form-control required">
		         	</select>
				</td>
				<td width="5px"></td>
			<td width="140px">
				<form:input path="name" htmlEscape="false" maxlength="200" class="form-control" placeholder="请输入机构名称" value="${condition.officeName}"/>
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
				<td width="20px"><input type="checkbox"  style="width: 15px; height: 25px;border:none"  name = "chk" value = "${office.id}" onclick="changeOffic(this)" id="${office.id}" <c:if test="${office.checked==1}">checked</c:if>></td>			
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