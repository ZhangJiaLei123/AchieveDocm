<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>资料信息管理</title>
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
			jQuery.validator.addMethod("alnum", function(value, element){
				 return this.optional(element) ||/^[a-zA-Z0-9]+$/.test(value);
				 }, "只能输入英文字母和数字");
		});

		
	</script>
</head>
<body>
		<form:form id="inputForm" modelAttribute="mesanInfo" action="${ctx}/resource/mesanInfo/save" method="post" class="form-horizontal">
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		  		
		   		<tr>
		   			<td class="width-15 active"><label class="pull-right"><font color="red">*</font>资料名称：</label></td>
					</td>
		   		</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">描述：</label></td>
					<td class="width-35">
					</td>
					
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">资料路径：</label></td>
					<td class="width-35">
						<input type="text" id="mesanUrl" path="mesanUrl" htmlEscape="false" maxlength="64" class="form-control "/>
						<sys:ckfinder input="mesanUrl" type="files" uploadPath="/resource/mesanInfo" selectMultiple="true"/>
					</td>
		  		</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>