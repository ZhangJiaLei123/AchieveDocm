<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>资源目录管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		
		  if(checkSort() && validateForm.form() ){
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
				errorContainer: "#messageBox" ,
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
		
		function checkSort(){
			var sort = $("#sort").val();
			var bl = false;
			if(sort){
				$.ajax({
				     type: 'GET',
				     url: "${ctx}/resource/resourceDir/checkResourceDirSort?oldResourceDirSort=" + encodeURIComponent('${resourceDir.sort}')+"&sort="+sort ,
				     async: false,
				     success: function(data){
				    	 if(data != "true"){
								$("#span1").show();
								//$("#name").val("");
								$("#sort").focus();
								bl = false;
							}else{
								$("#span1").hide();
								bl = true;
							}
				    }

				});
			}else{
				$("#span1").hide();
				bl = true;
			}
			return bl;	
		}
	</script>
</head>
<body>
		<form:form id="inputForm" modelAttribute="resourceDir" action="${ctx}/resource/resourceDir/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>目录名称：</label></td>
					<td class="width-35">
						<form:input path="name" htmlEscape="false" maxlength="200" class="form-control required"/>
					</td>
					<c:if test="${resourceDir.parent.id != 'root' && resourceDir.parent.id != ''}">
						<td class="width-15 active"><label class="pull-right"><font color="red">*</font>上级目录：</label></td>
						<td class="width-35">
							<sys:treeselect id="resourceDir" name="parent.id" value="${resourceDir.parent.id}" labelName="parent.name" labelValue="${resourceDir.parent.name}"
						title="资源目录" url="/resource/resourceDir/treeData?dataid=${resourceDir.id}" extId="${resourceDir.id}" cssClass="form-control required"/>
						</td>
					</c:if>
					<c:if test="${resourceDir.parent.id == 'root' || resourceDir.parent.id == ''}">
						<td class="width-15 active"><label class="pull-right">上级目录：</label></td>
						<td class="width-35">
							无
						</td>
					</c:if>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>排序：</label></td>
					<td class="width-35">
						<form:input path="sort" onblur="checkSort()" htmlEscape="false" class="form-control required digits"/>
						<span id="span1" style="display: none"><label id="name-error" class="error">该排序已存在</label></span>
					</td>
					<td class="width-15 active"></td>
					<td></td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>