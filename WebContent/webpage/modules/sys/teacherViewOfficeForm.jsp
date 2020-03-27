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
					//loading('正在提交，qwewqasdas请稍等...');
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
		});
	</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="office" action="${ctx}/sys/office/saveNew" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		    <tr>
		         <td class="width-15 active"><label class="pull-right">机构名称:</label></td>
		         <td class="width-35" colspan="3"><input type="hidden" name="parent" value="0">
		         ${office.name }
		         </td>
		      </tr>
		       <tr>
		         <td  class="width-15 active"><label class="pull-right required">代&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码:</label></td>
		         <td class="width-35"> ${office.code }</td>
		         <td class="width-15 active"><label class="pull-right required">法人:</label></td>
		         <td class="width-35">${office.legalPerson }</td>
		      </tr>
		      <tr>
		         <td class="width-15 active"><label class="pull-right">机构分类:</label></td>
		         <td class="width-35">${office.officeTypeName }</td>
		         <td  class="width-15 active" ><label class="pull-right">归属区域:</label></td>
		         <td class="width-35">${office.distrctName}-${office.provinceName}-${office.cityName}</td>
		      </tr>
		       <tr>
		          <td class="width-15 active"><label class="pull-right required">状态:</label></td>
		         <td class="width-35">
		         	${fns:getDictLabel(office.useable, 'office_delflag', '')}
				 </td>
		         <td  class="width-15 active" ><label class="pull-right ">机构级别:</label></td>
		         <td class="width-35">
					${fns:getDictLabel(office.grade, 'sys_office_grade', '')}
				</td> 
		      </tr>
		      <tr>
		         <td class="width-15 active"><label class="pull-right">FED级别:</label></td>
		         <td class="width-35">
					${fns:getDictLabel(office.fedLevel, 'sys_officefed_level', '')}
					</td>
		         <td class="width-15 active" ><label class="pull-right">对应销售代码:</label></td>
		         <td class="width-35">${office.xsCode}</td>
		      </tr> 
		       <tr>
		         <td class="width-15 active"><label class="pull-right">品牌:</label></td>
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
		         <td class="width-35">${office.zipCode}</td>
		         <td  class="width-15 active"><label class="pull-right">区号:</label></td>
		         <td class="width-35">${office.areaCode}</td>
		      </tr>
		      <tr>
		         <td class="width-15 active"><label class="pull-right">联系电话:</label></td>
		         <td class="width-35">${office.phone}</td>
		         <td  class="width-15 active"><label class="pull-right">联系人:</label></td>
		         <td class="width-35">${office.contacts}</td>
		      </tr>
		      <tr>
		        <td  class="width-15 active" ><label class="pull-right">传真:</label></td>
		         <td class="width-35">${office.fax}</td>
		         <td  class="width-15 active"><label class="pull-right">24小时热线:</label></td>
		         <td class="width-35">${office.phone24}</td>
		      </tr>
		      <tr>
		      	 <td class="width-15 active" ><label class="pull-right">联系地址:</label></td>
		         <td class="width-35" colspan="3">${office.address}</td>
		      </tr>
	      </tbody>
	      </table>
</form:form>
</body>
</html>