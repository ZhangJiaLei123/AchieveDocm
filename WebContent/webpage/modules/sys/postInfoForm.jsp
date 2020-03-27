<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>岗位管理</title>
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
					//loading('正在提交，请稍等...');
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
		<form:form id="inputForm" modelAttribute="postInfo" action="${ctx}/sys/postInfo/save"  onkeydown="if(event.keyCode==13)return false;"  method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>岗位名称：</label></td>
					<td class="width-35">
						<form:input path="name"  onkeydown="if(event.keyCode==13)return false;" htmlEscape="false" maxlength="100" class="form-control required"/>
					</td>
				<!-- </tr>
				<tr> -->
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>岗位分类：</label></td>
					<td class="width-35">
						<form:select path="postType" class="form-control required">
							<form:option value="" label="请选择岗位分类"/>
								<c:forEach items="${postTypes }" var="postType">
									<form:option value="${postType.id }" label="${postType.name }" />
									<c:if test="${postType.id==postInfo.postType}">selected</c:if>
								</c:forEach>
						</form:select>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>