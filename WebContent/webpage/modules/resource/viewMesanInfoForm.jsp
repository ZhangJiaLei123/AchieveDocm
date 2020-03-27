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
			
		});
	</script>
</head>
<body>
		<form:form id="inputForm" modelAttribute="mesanInfo" action="${ctx}/resource/mesanInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		    <tr>
		   		<td class="width-15 active"><label class="pull-right">资料类别：</label></td>
					<td class="width-35">
						${mesanInfo.mesanTypeName}
					</td>
		   			<td class="width-15 active"><label class="pull-right">资料编码：</label></td>
					<td class="width-35">
						${mesanInfo.mesanCode}
					</td>
		   		</tr>
		   		<tr>
		   			<td class="width-15 active"><label class="pull-right"><font color="red">*</font>资料名称：</label></td>
					<td class="width-35">
						${mesanInfo.name}
					</td>
					<td class="width-15 active"><label class="pull-right">上传人：</label></td>
					<td class="width-35">
						${mesanInfo.createBy.name}
					</td>
		   		</tr>
		   		<tr>
		   			<td class="width-15 active"><label class="pull-right"><font color="red">*</font>上传时间：</label></td>
					<td class="width-35">
						<fmt:formatDate value="${mesanInfo.updateDate}" type="both"/>
					</td>
					<td class="width-15 active"><label class="pull-right">资料状态：</label></td>
					<td class="width-35">
						${mesanInfo.approvalStatus==null?"讲师提交":fns:getDictLabel(mesanInfo.approvalStatus, 'approval_record_status', '')}
					</td>
		   		</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">描述：</label></td>
					<td class="width-35" colspan="3">
						${mesanInfo.remarks}
					</td>
					
				</tr>
				<c:if test="${mesanInfo.swfUrl=='' || mesanInfo.swfUrl==null}">
					<tr>
						<td class="width-15 active"><label class="pull-right">下载：</label></td>
						<td class="width-35" colspan="3">
							<a href="${ctx}/resource/mesanInfo/downloadMaterial?mesanInfoId=${mesanInfo.id}" class="btn btn-info btn-xs"> 下载</a>
						</td>
						
					</tr>
				</c:if>
				<c:if test="${mesanInfo.swfUrl!='' && mesanInfo.swfUrl != null}">
					<tr >
						<td colspan="4" height="300px">
							<input type="hidden" value ="${mesanInfo.mesanUrl}" id="resourceUrl">
							<input type="hidden" value ="${mesanInfo.swfUrl}" id="showUrl">
							<div style="width:100%;height: 100%;padding: 2px;">
								<iframe src="/docm/documentView.jsp" width="100%" height="100%" style="border: none"></iframe>
							</div>
						
						</td>
					</tr>
				</c:if>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>