<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>机构类别管理</title>
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
			
		});
	</script>
</head>
<body>
		<form:form id="inputForm" modelAttribute="officeType" action="${ctx}/sys/officeType/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td width="100px" class="active"><label class="pull-right"><font color="red">*</font>组织类别名称：</label></td>
					<td >
						<form:input path="name" htmlEscape="false" maxlength="200" class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="active"><label class="pull-right"><font color="red">*</font>上级机构类别：</label></td>
					<c:choose>
						<c:when test="${(!empty officeType.id) &&(empty officeType.parent.id)}">
							<td >
									无
							</td>
						</c:when>
						<c:otherwise>
							<td >
								<sys:treeselect id="parentId" name="parentId" value="${officeType.parentId}" labelName="officeType.parent.name" labelValue="${officeType.parent.name}"
								title="机构" url="/sys/officeType/treeData?dataid=${officeType.id}" extId="parentId"  cssClass="form-control required" />
							</td>
						</c:otherwise>
					</c:choose>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>