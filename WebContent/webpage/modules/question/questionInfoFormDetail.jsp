<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<title>问卷信息管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	var validateForm;
	function doSubmit() {//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		if (validateForm.form()) {
			$("#inputForm").submit();
			return true;
		}

		return false;
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
<body>
	<!-- 调研信息 -->
	<c:if test="${not empty userName && userName != '' }">
		<table
			class="  table-condensed dataTables-example dataTable no-footer"
			style="margin-left: 100px;">
			<tbody>
				<tr>
					<td class="width-15 active" style="text-align: center;"><label>调研用户：${userName}</label></td>
					<td class="width-35 active" style="text-align: center;"><label>调研时间：
							${startTime} </label></td>
				</tr>
			</tbody>
		</table>
		<hr style="height: 2px; border: none; border-top: 2px dotted red;" />
	</c:if>

	<!-- 问卷信息 -->
	<div
		style="text-align: center; margin-left: 100px; margin-right: 100px; margin-top: 20px;">
		<label style="font-size: 18px;color:red;">${questionInfo.queName}</label>
	</div>
	<div
		style="text-align: left; text-indent: 2em; margin-left: 100px; margin-right: 100px; margin-top: 20px;">
		<label>${questionInfo.remarks}</label>
	</div>

	<!--  
	<table class="  table-condensed dataTables-example dataTable no-footer"
		style="margin-left: 100px;">
		<tbody>
			<tr>
				<td class="width-15 active" style="text-align: center;"><label>${questionInfo.queName}</label></td>
			</tr>
			<tr>
				<td class="width-35 active" style="text-align: center;" style="margin-left:100px;margin-right:100px;"><label>${questionInfo.remarks}</label></td>
			</tr>
		</tbody>
	</table>-->
	<hr style="height: 2px; border: none; border-top: 2px dotted red;" />
	<!-- 问题信息 -->
	<c:forEach items="${page.list}" var="questionProblem"
		varStatus="status">
		<div
			style="text-align: left; margin-left: 90px; margin-right: 100px; margin-top: 20px;">
			<label>${status.index+1}、${questionProblem.proSterm}</label>
		</div>

		<table
			style="text-align: left; margin-left: 110px; margin-right: 120px; margin-top: 5px;">
			<c:if
				test="${not empty questionProblem.proQueOne && questionProblem.proQueOne!=''}">
				<tr>
					<td><c:choose>
							<c:when
								test="${not empty questionProblem.answer && 
							fn:contains(questionProblem.answer,'a')}">
								<c:if test="${questionProblem.proModel == 1}">
									<input type="radio" checked="checked" disabled="true" />
								</c:if>
								<c:if test="${questionProblem.proModel == 2}">
									<input type="checkbox" checked="checked" disabled="true" />
								</c:if>
							</c:when>
							<c:otherwise>
								<c:if test="${questionProblem.proModel == 1}">
									<input type="radio" disabled="true" />
								</c:if>
								<c:if test="${questionProblem.proModel == 2}">
									<input type="checkbox" disabled="true" />
								</c:if>
							</c:otherwise>
						</c:choose> (A)${questionProblem.proQueOne}</td>
				</tr>
			</c:if>
			<c:if
				test="${not empty questionProblem.proQueTwo && questionProblem.proQueTwo!=''}">
				<tr>
					<td><c:choose>
							<c:when
								test="${not empty questionProblem.answer && 
							fn:contains(questionProblem.answer,'b')}">
								<c:if test="${questionProblem.proModel == 1}">
									<input type="radio" checked="checked" disabled="true" />
								</c:if>
								<c:if test="${questionProblem.proModel == 2}">
									<input type="checkbox" checked="checked" disabled="true" />
								</c:if>
							</c:when>
							<c:otherwise>
								<c:if test="${questionProblem.proModel == 1}">
									<input type="radio" disabled="true" />
								</c:if>
								<c:if test="${questionProblem.proModel == 2}">
									<input type="checkbox" disabled="true" />
								</c:if>
							</c:otherwise>
						</c:choose> (B)${questionProblem.proQueTwo}</td>
				</tr>
			</c:if>
			<c:if
				test="${not empty questionProblem.proQueThree && questionProblem.proQueThree!=''}">
				<tr>
					<td><c:choose>
							<c:when
								test="${not empty questionProblem.answer && 
							fn:contains(questionProblem.answer,'c')}">
								<c:if test="${questionProblem.proModel == 1}">
									<input type="radio" checked="checked" disabled="true" />
								</c:if>
								<c:if test="${questionProblem.proModel == 2}">
									<input type="checkbox" checked="checked" disabled="true" />
								</c:if>
							</c:when>
							<c:otherwise>
								<c:if test="${questionProblem.proModel == 1}">
									<input type="radio" disabled="true" />
								</c:if>
								<c:if test="${questionProblem.proModel == 2}">
									<input type="checkbox" disabled="true" />
								</c:if>
							</c:otherwise>
						</c:choose> (C)${questionProblem.proQueThree}</td>
				</tr>
			</c:if>
			<c:if
				test="${not empty questionProblem.proQueFour && questionProblem.proQueFour!=''}">
				<tr>
					<td><c:choose>
							<c:when
								test="${not empty questionProblem.answer && 
							fn:contains(questionProblem.answer,'d')}">
								<c:if test="${questionProblem.proModel == 1}">
									<input type="radio" checked="checked" disabled="true" />
								</c:if>
								<c:if test="${questionProblem.proModel == 2}">
									<input type="checkbox" checked="checked" disabled="true" />
								</c:if>
							</c:when>
							<c:otherwise>
								<c:if test="${questionProblem.proModel == 1}">
									<input type="radio" disabled="true" />
								</c:if>
								<c:if test="${questionProblem.proModel == 2}">
									<input type="checkbox" disabled="true" />
								</c:if>
							</c:otherwise>
						</c:choose> (D)${questionProblem.proQueFour}</td>
				</tr>
			</c:if>
		</table>
	</c:forEach>








	<%-- <form:form id="inputForm" modelAttribute="questionInfo"
		action="${ctx}/question/questionInfo/save" method="post"
		class="form-horizontal">
		<form:hidden path="id" />
		<sys:message content="${message}" />
		<table
			class="  table-condensed dataTables-example dataTable no-footer"
			style="margin-left: 300px;">
			<tbody>
				<!-- 
				<tr>
					<td class="width-15 active"><label class="pull-right">文件状态：</label></td>
					<td class="width-35">
						<form:input path="status" htmlEscape="false" maxlength="10" class="form-control "/>
					</td>
					<td class="width-15 active"></td>
		   			<td class="width-35" ></td>
		  		</tr> -->
				<%
					int a = 0;
				%>
				<c:forEach items="${page.list}" var="questionProblem">
					<%
						a++;
					%>
					<tr>
						<td><%=a%>、${questionProblem.proSterm}</td>
					</tr>
					<c:if
						test="${not empty questionProblem.proQueOne && questionProblem.proQueOne!=''}">
						<tr>
							<td>
							<c:choose> 
							<c:when test="${not empty questionProblem.answer && 
							fn:contains(questionProblem.answer,'a')}">
							[
							<label style="color: red;">已选</label>
							]   
                            </c:when> 
                            <c:otherwise>   
                            [&nbsp;&nbsp;]
                            </c:otherwise> 
                            </c:choose> 						
							(A)${questionProblem.proQueOne}</td>
						</tr>
					</c:if>
					<c:if
						test="${not empty questionProblem.proQueTwo && questionProblem.proQueTwo!=''}">
						<tr>
							<td>
							<c:choose> 
							<c:when test="${not empty questionProblem.answer && 
							fn:contains(questionProblem.answer,'b')}">
							[
							<label style="color: red;">已选</label>
							]   
                            </c:when> 
                            <c:otherwise>   
                            [&nbsp;&nbsp;]
                            </c:otherwise> 
                            </c:choose> 	
							(B)${questionProblem.proQueTwo}</td>
						</tr>
					</c:if>
					<c:if
						test="${not empty questionProblem.proQueThree && questionProblem.proQueThree!=''}">
						<tr>
							<td>
							<c:choose> 
							<c:when test="${not empty questionProblem.answer && 
							fn:contains(questionProblem.answer,'c')}">
							[
							<label style="color: red;">已选</label>
							]   
                            </c:when> 
                            <c:otherwise>   
                            [&nbsp;&nbsp;]
                            </c:otherwise> 
                            </c:choose> 	
							(C)${questionProblem.proQueThree}
							</td>
						</tr>
					</c:if>
					<c:if
						test="${not empty questionProblem.proQueFour && questionProblem.proQueFour!=''}">
						<tr>
							<td>
							<c:choose> 
							<c:when test="${not empty questionProblem.answer && 
							fn:contains(questionProblem.answer,'d')}">
							[
							<label style="color: red;">已选</label>
							]   
                            </c:when> 
                            <c:otherwise>   
                            [&nbsp;&nbsp;]
                            </c:otherwise> 
                            </c:choose> 	
							(D)${questionProblem.proQueFour}</td>
						</tr>
					</c:if>
					<!-- 	<tr>
						<td>正确答案：${questionProblem.proAnswer}</td>
					</tr>-->
					<tr>
						<td></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</form:form> --%>
</body>
</html>