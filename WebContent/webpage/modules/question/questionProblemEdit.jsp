<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>问卷题目管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
	});
</script>
<script type="text/javascript">
	function operationcheck1() {
		var chk = document.getElementById('operation-checkbox');
		var operationButtonDiv = document
				.getElementsByClassName('operation-button-div');
		if (chk.checked == true) {
			for (var i = 0; i < operationButtonDiv.length; i++) {
				operationButtonDiv[i].style.visibility = "visible";
			}
		} else {
			for (var i = 0; i < operationButtonDiv.length; i++) {
				operationButtonDiv[i].style.visibility = "hidden";
			}
		}
	}
	var validateForm;
	function doSubmit() {//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		
		if (validateForm.form() && validateFormQuestion()) {
			$("#inputForm").submit();
			return true;
		}

		return false;
	}
	
	function validateFormQuestion(){
		var proQueOne = $("#proQueOne").val();
		var proQueTwo = $("#proQueTwo").val();
		var proQueThree = $("#proQueThree").val();
		var proQueFour = $("#proQueFour").val();
		if(proQueFour!=""){
			if(proQueOne==""){
				top.layer.alert("答案1不能为空！");
				return false;
			}if(proQueTwo==""){
				top.layer.alert("答案2不能为空！");
				return false;
			}
			if(proQueThree==""){
				top.layer.alert("答案3不能为空！");
				return false;
			}
			
		}
		if(proQueThree!=""){
			if(proQueOne==""){
				top.layer.alert("答案1不能为空！");
				return false;
			}if(proQueTwo==""){
				top.layer.alert("答案2不能为空！");
				return false;
			}
		}
		if(proQueTwo!=""){
			if(proQueOne==""){
				top.layer.alert("答案1不能为空！");
				return false;
			}
		}
		return true;
	}
	
	
	$(document).ready(
			function() {
				validateForm = $("#inputForm")
						.validate(
								{
									submitHandler : function(form) {
										loading('正在提交，请稍等...');
										form.submit();
									},
									errorContainer : "#messageBox",
									errorPlacement : function(error, element) {
										$("#messageBox").text("输入有误，请先更正。");
										if (element.is(":checkbox")
												|| element.is(":radio")
												|| element.parent().is(
														".input-append")) {
											error.appendTo(element.parent()
													.parent());
										} else {
											error.insertAfter(element);
										}
									}
								});

			});
</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="ibox" style="margin-bottom: 0px;">
			<div class="ibox-title">
				<h5>
					<a href="${ctx}/question/questionInfo"> 问卷列表 </a>
				</h5>
				<h5>>> 题目编辑</h5>
			</div>
		</div>

		<div id="left"
			style="float: left; width: 60%; height: 100%; margin-top: 20px;">

			<div class="ibox">
				<div class="ibox-title">
					<h5>问卷预览</h5>
					<div style="float: right;">
						<input type="checkbox" id="operation-checkbox"
							onclick="operationcheck1()">显示操作按钮
					</div>
				</div>
				<div class="ibox-content">
					<sys:message content="${message}" />
					<table
						class="  table-condensed dataTables-example dataTable no-footer"
						style="text-align: center; width: 100%;">
						<tbody>
							<tr>
								<td class="width-15 active" style="text-align: center;"><label>${questionInfo.queName}</label></td>
							</tr>
							<tr>
								<td class="width-35 active" style="text-align: center;"><label>${questionInfo.remarks}</label></td>
							</tr>
						</tbody>
					</table>
					<table
						class="  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr
								style="height: 2px; border: none; border-top: 2px dotted #185598;" />
							<%
								int a = 0;
							%>
							<c:forEach items="${page.list}" var="questionProblem">
								<%
									a++;
								%>
								<tr>
									<td style="width: 180px;">
										<div class="operation-button-div"
											style="visibility: hidden; float: left;">
											<shiro:hasPermission name="question:questionProblem:edit">
						<a href="${ctx}/question/questionProblem/editproblem?questionid=${questionProblem.questionId}&problemid=${questionProblem.id}"
											class="btn btn-info btn-xs">编辑</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="question:questionProblem:del">
						<a href="${ctx}/question/questionProblem/delete?id=${questionProblem.id}"
											class="btn btn-info btn-xs">删除</a>
					</shiro:hasPermission>
											<shiro:hasPermission name="question:questionProblem:edit">
						<a href="${ctx}/question/questionProblem/upordownsort?questionid=${questionProblem.questionId}&type=up&problemid=${questionProblem.id}"
											class="btn btn-info btn-xs">上移</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="question:questionProblem:edit">
						<a href="${ctx}/question/questionProblem/upordownsort?questionid=${questionProblem.questionId}&type=down&problemid=${questionProblem.id}"
											class="btn btn-info btn-xs">下移</a>
					</shiro:hasPermission>
										</div>
									</td>
									<td><%=a%>、${questionProblem.proSterm}</td>
								</tr>
								<c:if
									test="${not empty questionProblem.proQueOne && questionProblem.proQueOne!=''}">
									<tr>
										<td style="width: 100px;"></td>
										<td>(A)${questionProblem.proQueOne}</td>
									</tr>
								</c:if>
								<c:if
									test="${not empty questionProblem.proQueTwo && questionProblem.proQueTwo!=''}">
									<tr>
										<td style="width: 100px;"></td>
										<td>(B)${questionProblem.proQueTwo}</td>
									</tr>
								</c:if>
								<c:if
									test="${not empty questionProblem.proQueThree && questionProblem.proQueThree!=''}">
									<tr>
										<td style="width: 100px;"></td>
										<td>(C)${questionProblem.proQueThree}</td>
									</tr>
								</c:if>
								<c:if
									test="${not empty questionProblem.proQueFour && questionProblem.proQueFour!=''}">
									<tr>
										<td style="width: 100px;"></td>
										<td>(D)${questionProblem.proQueFour}</td>
									</tr>
								</c:if>
								<tr>
									<td></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<!-- 分页代码 -->
	<table:page page="${page}"></table:page>
	<br />
	<br />
				</div>
			</div>
		</div>
	</div>

	<div id="right"
		style="float: left; width: 36%; height: 100%; margin-left: 2%;">
		<div class="ibox">
			<div class="ibox-title">
				<h5>新增问卷题目</h5>
			</div>
			<div class="ibox-content">
				<form:form id="inputForm" modelAttribute="questionProblem"
					action="${ctx}/question/questionProblem/save" method="post"
					class="form-horizontal">
					<form:hidden path="id" />
					<form:hidden path="questionId" value="${questionInfo.id}" />
					<table
						class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<!-- 	<tr>
								<td class="width-15 active"><label class="pull-right"><font
										color="red">*</font>问卷ID：</label></td>
								<td class="width-35"><form:textarea path="questionId"
										htmlEscape="false" rows="1" maxlength="64"
										class="form-control required" /></td>
							</tr> -->
							<tr>
								<td class="width-15 active"><label class="pull-right"><font
										color="red">*</font>问卷类型：</label></td>
								<td class="width-35"><form:select path="proModel"
										class="form-control required">
										<form:option value="1" label="单选" />
										<form:option value="2" label="多选" />
										<form:options items="${fns:getDictList('')}" itemLabel="label"
											itemValue="value" htmlEscape="false" />
									</form:select></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right"><font
										color="red">*</font>问卷主干：</label></td>
								<td class="width-35"><form:textarea path="proSterm"
										htmlEscape="false" rows="4" maxlength="2000"
										class="form-control required" /></td>
							</tr>
							<!-- 				<tr>
								<td class="width-15 active"><label class="pull-right"><font
										color="red">*</font>问卷答案：</label></td>
								<td class="width-35"><form:input path="proAnswer"
										htmlEscape="false" maxlength="64"
										class="form-control required" /></td>
							</tr> -->
							<tr>
								<td class="width-15 active"><label class="pull-right">答案1：</label></td>
								<td class="width-35"><form:textarea path="proQueOne"
										htmlEscape="false" rows="1" maxlength="2000"
										class="form-control " /></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">答案2：</label></td>
								<td class="width-35"><form:textarea path="proQueTwo"
										htmlEscape="false" rows="1" maxlength="2000"
										class="form-control " /></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">答案3：</label></td>
								<td class="width-35"><form:textarea path="proQueThree"
										htmlEscape="false" rows="1" maxlength="2000"
										class="form-control " /></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">答案4：</label></td>
								<td class="width-35"><form:textarea path="proQueFour"
										htmlEscape="false" rows="1" maxlength="2000"
										class="form-control " /></td>
							</tr>
						</tbody>
					</table>
				</form:form>
				<input type="button" value="提交" onclick="doSubmit();"
					class="btn btn-info btn-xs" />
			</div>
		</div>
	</div>
</body>
</html>