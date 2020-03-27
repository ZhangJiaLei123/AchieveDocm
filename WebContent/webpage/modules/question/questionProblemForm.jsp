<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>问卷题目管理</title>
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
		<form:form id="inputForm" modelAttribute="questionProblem" action="${ctx}/question/questionProblem/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>问卷ID：</label></td>
					<td class="width-35">
						<form:textarea path="questionId" htmlEscape="false" rows="4" maxlength="64" class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>问卷模板（1单选，2多选，3判断）：</label></td>
					<td class="width-35">
						<form:select path="proModel" class="form-control required">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>问卷主干：</label></td>
					<td class="width-35">
						<form:textarea path="proSterm" htmlEscape="false" rows="4" maxlength="2000" class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>问卷答案：</label></td>
					<td class="width-35">
						<form:input path="proAnswer" htmlEscape="false" maxlength="64" class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">答案1：</label></td>
					<td class="width-35">
						<form:textarea path="proQueOne" htmlEscape="false" rows="4" maxlength="2000" class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">答案2：</label></td>
					<td class="width-35">
						<form:textarea path="proQueTwo" htmlEscape="false" rows="4" maxlength="2000" class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">答案3：</label></td>
					<td class="width-35">
						<form:textarea path="proQueThree" htmlEscape="false" rows="4" maxlength="2000" class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">答案4：</label></td>
					<td class="width-35">
						<form:textarea path="proQueFour" htmlEscape="false" rows="4" maxlength="2000" class="form-control "/>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>