<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>审批意见管理</title>
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
		<form:form id="inputForm" modelAttribute="approvalRecord" action="${ctx}/sys/approvalRecord/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>审批状态：</label></td>
					<td class="width-35">
						<form:radiobuttons path="status" items="${fns:getDictList('approval_record_status')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks required"/>
						<input type="hidden" value="${ids}" name="ids"/>
						<input type="hidden" value="${url}" name="url"/>
					</td>
					</tr>
					<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>审批意见：</label></td>
					<td class="width-35">
						<form:textarea path="opinion" htmlEscape="false" rows="4" maxlength="300" class="form-control required"/>
					</td>
					</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>