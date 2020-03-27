<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>问卷信息管理</title>
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
		<form:form id="inputForm" modelAttribute="questionInfo" action="${ctx}/question/questionInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		        <tr>
		            <td class="width-15 active"><label class="pull-right"><font color="red">*</font>问卷名称：</label></td>
					<td class="width-35">
						<form:input path="queName" htmlEscape="false" maxlength="255" class="form-control required"/>
					</td>
		        </tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">问卷描述：</label></td>
					<td class="width-35">
						<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="form-control "/>
					</td>
				</tr>
				<!-- 
				<tr>
					<td class="width-15 active"><label class="pull-right">文件状态：</label></td>
					<td class="width-35">
						<form:input path="status" htmlEscape="false" maxlength="10" class="form-control "/>
					</td>
					<td class="width-15 active"></td>
		   			<td class="width-35" ></td>
		  		</tr> -->
		  		<c:forEach items="${page.list}" var="questionProblem">
			<tr>
				<td> <input type="checkbox" id="${questionProblem.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看问卷题目', '${ctx}/question/questionProblem/form?id=${questionProblem.id}','800px', '500px')">
					${questionProblem.questionId}
				</a></td>
				<td>
					${fns:getDictLabel(questionProblem.proModel, '', '')}
				</td>
				<td>
					${questionProblem.proSterm}
				</td>
				<td>
					${questionProblem.proAnswer}
				</td>
				<td>
					${questionProblem.proQueOne}
				</td>
				<td>
					${questionProblem.proQueTwo}
				</td>
				<td>
					${questionProblem.proQueThree}
				</td>
				<td>
					${questionProblem.proQueFour}
				</td>
				<td>
					<shiro:hasPermission name="question:questionProblem:view">
						<a href="#" onclick="openDialogView('查看问卷题目', '${ctx}/question/questionProblem/form?id=${questionProblem.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="question:questionProblem:edit">
    					<a href="#" onclick="openDialog('修改问卷题目', '${ctx}/question/questionProblem/form?id=${questionProblem.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="question:questionProblem:del">
						<a href="${ctx}/question/questionProblem/delete?id=${questionProblem.id}" onclick="return confirmx('确认要删除该问卷题目吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>