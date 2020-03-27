<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>组织人员</title>
<meta charset="utf-8">
<meta name="decorator" content="default"/>
</head>
<body>
<jsp:include page="/include/head.jsp"></jsp:include>
<script type="text/javascript">
	$($("a",$("#topA"))[1]).attr("class","active");
	$(document).ready(function() {
		$("#grade").prepend("<option value=''>请选择</option>");
		$("#grade").val("${office.grade}");
		
		initDistrictDao();
		$("#distrctId").val($("#distrctIdValue").val());
		initProvince($("#distrctIdValue").val());
		$("#provinceId").val($("#provinceIdValue").val());
		initCity($("#provinceIdValue").val());
		$("#cityId").val($("#cityIdValue").val()); 
	});

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
				  $("#provinceId").html("<option value=''>--请选择省--</option>");
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
				  $("#cityId").html("<option value=''>--请选择市--</option>");
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
</script>
<!--头部 end-->    
<!--身体 start-->
<div class="row" style="min-height: 530px;">
	<div style="height: 90px;  border-bottom: 1px solid #d8d8d8;padding-top: 30px;" >
		<a style="color: #18a589;margin-right: 40px;" href="${pageContext.request.contextPath}/a/selectOfficeInfo">组织机构</a>
		<a href="${pageContext.request.contextPath}/a/selectUserInfo">人员</a>
		<div style="float:right">
		<form:form id="searchForm" modelAttribute="office" action="${pageContext.request.contextPath}/a/selectOfficeInfo" method="post" class="form-inline">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table >
			<tr style="line-height: 30px;">
				<td><span>机构分类：</span></td>
				<td width="150px;" height="20px;">
					<sys:treeselect id="officeType" name="officeType" value="${office.officeType}" labelName="officeTypeName" labelValue="${office.officeTypeName}"
						title="机构分类" url="/sys/officeType/treeData"  cssClass="form-control" />
				</td>
				<td><span>&nbsp;机构等级&nbsp;</span></td>
				<td>
					<form:select path="grade" class="form-control" id="grade">
						<form:options items="${fns:getDictList('sys_office_grade')}"  itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</td>
				<td><span>&nbsp;归属区域&nbsp;</span></td>
				<td width="150px;">
					<input type="hidden" id = "distrctIdValue" value="${office.distrctId}"/>
		         	<input type="hidden" id = "provinceIdValue" value="${office.provinceId}"/>
		         	<input type="hidden" id = "cityIdValue" value="${office.cityId}"/>
		         	<select name="distrctId" id="distrctId" style="width:150px" onchange="initProvince(this.value)" class="form-control required"></select>
				</td>
				<td>&nbsp;&nbsp;
		         	<select name="provinceId" id="provinceId" onchange="initCity(this.value)" style="width:130px" class="form-control required">
		         	
		         	</select>
		         </td>
		         <td>
		         	&nbsp;&nbsp;<select name="cityId" id="cityId" style="width:130px" class="form-control required">
		         	</select>
		         	</td>
				<td>
					 &nbsp;&nbsp;<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i>搜索</button>
				</td>
			</tr>
		</table>
		</form:form>
		</div>
	</div>
	
	<!-- 表格 -->
	<table id="officeTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th>机构名称</th>
				<th>机构代码</th>
				<th>机构分类</th>
				<th>大区</th>
				<th>省</th>
				<th>市</th>
				<th>联系电话</th>
				<!-- <th>Email</th> -->
				<th width="80px">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="office" varStatus="varStatus">
			<tr>
				<td>${office.name}</a></td>
				<td>${office.code}</td>
				<td>${office.parentName}</td>
				<td>${office.distrctName}</td>
				<td>${office.provinceName}</td>
				<td>${office.cityName}</td>
				<td>${office.phone}</td>
			<%-- 	<td>${office.email}</td> --%>
				<td >
					<a href="#" onclick="openDialogView('查看机构', '${ctx}/sys/office/teacherViewOfficeForm?id=${office.id}','800px', '620px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
		<!-- 分页代码 -->
	<table:page page="${page}"></table:page>
</div>
<!--尾部 start-->
<div class="copyright">© 云帆互联 2014 汽车服务网 京ICP备14016447号</div>
<!--尾部 end-->

</body>
</html>
    