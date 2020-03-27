<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>机构管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  $("#inputForm").submit();
			  return true;
		  }
	
		  return false;
		}
		$(document).ready(function() {
			$("#name").focus();
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
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
	<style type="text/css">
		span{padding-right:20px;}
	</style>
</head>
<body>
	<form:form id="inputForm" modelAttribute="office" action="${ctx}/sys/office/saveNew" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		    <tr>
		         <td class="width-15 active"><label class="pull-right"><font color="red">*</font>机构名称:</label></td>
		         <td class="width-35" colspan="3"><input type="hidden" name="parent" value="0"><form:input path="name" htmlEscape="false" maxlength="50" class="form-control required"/></td>
		      </tr>
		       <tr>
		         <td  class="width-15 active"><label class="pull-right required"><font color="red">*</font>代　　码:</label></td>
		         <td class="width-35"><form:input path="code" htmlEscape="false" maxlength="50" class="form-control required"/></td>
		         <td class="width-15 active"><label class="pull-right required">法人:</label></td>
		         <td class="width-35"><form:input path="legalPerson" htmlEscape="false" maxlength="50" class="form-control "/></td>
		      </tr>
		      <tr>
		         <td class="width-15 active"><label class="pull-right"><font color="red">*</font>机构分类:</label></td>
		         <td class="width-35" colspan="3"><sys:treeselect id="officeType"  name="officeType" value="${office.officeType}" labelName="office.officeTypeName" labelValue="${office.officeTypeName}"
					title="机构分类" url="/sys/officeType/treeData"  cssClass="form-control required"  /></td>
		      
		      </tr>
		      <tr>
		      	  <td  class="width-15 active" ><label class="pull-right"><font color="red">*</font>归属区域:</label></td>
		         <td class="width-35" colspan="3">
		         	<input type="hidden" id = "distrctIdValue" value="${office.distrctId}"/>
		         	<input type="hidden" id = "provinceIdValue" value="${office.provinceId}"/>
		         	<input type="hidden" id = "cityIdValue" value="${office.cityId}"/>
		         	<select name="distrctId" id="distrctId" style="width:200px" onchange="initProvince(this.value)" class="form-control required">
		         		
		         	</select>
		         	<select name="provinceId" id="provinceId" onchange="initCity(this.value)" style="width:200px" class="form-control required">
		         	
		         	</select>
		         	<select name="cityId" id="cityId" style="width:200px" class="form-control required">
		         	</select>
		         	
		         </td>
		      </tr>
		       <tr>
		          <td class="width-15 active"><label class="pull-right required"><font color="red">*</font>状　　态:</label></td>
		         <td class="width-35"><form:select path="useable" class="form-control">
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				 </td>
		         <td  class="width-15 active" ><label class="pull-right "><font color="red">*</font>机构级别:</label></td>
		         <td class="width-35"><form:select path="grade" class="form-control required">
					<form:options items="${fns:getDictList('sys_office_grade')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select></td>
		      </tr>
		      <tr>
		         <td class="width-15 active"><label class="pull-right">FED级别:</label></td>
		         <td class="width-35"><form:select path="fedLevel" class="form-control">
					<form:options items="${fns:getDictList('sys_officefed_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
					</td>
		         <td class="width-15 active" ><label class="pull-right">对应销售代码:</label></td>
		         <td class="width-35"><form:input path="xsCode" htmlEscape="true" maxlength="50" cssClass="form-control" /></td>
		      </tr>
		       <tr>
		         <td class="width-15 active"><label class="pull-right">品　　牌:</label></td>
		         <td class="width-35" colspan="3">
		         	<form:checkboxes path="brand" items="${fns:getDictList('office_pp')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks "/>
		         </td>
		      </tr>
		      <!-- 
		      <tr>
		         <td class="width-15 active"><label class="pull-right">副负责人:</label></td>
		         <td class="width-35"><sys:treeselect id="deputyPerson" name="deputyPerson.id" value="${office.deputyPerson.id}" labelName="office.deputyPerson.name" labelValue="${office.deputyPerson.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="form-control" allowClear="true" notAllowSelectParent="true"/></td>
		      </tr>
		       -->
		      <tr>
		         <td class="width-15 active"><label class="pull-right">邮政编码:</label></td>
		         <td class="width-35"><form:input path="zipCode" htmlEscape="false" maxlength="50" cssClass="form-control" /></td>
		         <td  class="width-15 active"><label class="pull-right">区号:</label></td>
		         <td class="width-35"><form:input path="areaCode" htmlEscape="false" maxlength="50" cssClass="form-control" /></td>
		      </tr>
		      <tr>
		         <td class="width-15 active"><label class="pull-right">联系电话:</label></td>
		         <td class="width-35"><form:input path="phone" htmlEscape="false" maxlength="50" cssClass="form-control" /></td>
		         <td  class="width-15 active"><label class="pull-right">联&nbsp;系&nbsp;人:</label></td>
		         <td class="width-35"><form:input path="contacts" htmlEscape="false" maxlength="50" cssClass="form-control" /></td>
		      </tr>
		      <tr>
		        <td  class="width-15 active" ><label class="pull-right">传　　真:</label></td>
		         <td class="width-35"><form:input path="fax" htmlEscape="false" maxlength="50" cssClass="form-control" /></td>
		         <td  class="width-15 active"><label class="pull-right">24小时热线:</label></td>
		         <td class="width-35"><form:input path="phone24" htmlEscape="true" maxlength="50" cssClass="form-control" /></td>
		      </tr>
		      <tr>
		      	 <td class="width-15 active" ><label class="pull-right">联系地址:</label></td>
		         <td class="width-35" colspan="3"><form:input path="address" htmlEscape="false" maxlength="200" cssClass="form-control" /></td>
		      </tr>
	      </tbody>
	      </table>
	</form:form>
</body>
</html>