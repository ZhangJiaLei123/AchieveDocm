<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>岗位分类管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(fun_ajax() && validateForm.form(obj) ){
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
		//岗位
		function  fun_ajax(){
			var name = $("#name").val();
			var hideName = $("#hideName").val();
			if(name==hideName){
				return true;
			}
			var url = "${ctx}/sys/postType/findNameExists?a="+Math.random();
			var bl = false;
			$.ajax({
				   type: "POST",
				   url: url,
				   async:false,//发送同步请求
				   data:{name:name},
				   dataType: "json",
				   success: function(result){
						if(result == "0"){
							$("#span1").show();
							//$("#name").val("");
							$("#name").focus();
							bl = false;
						}else{
							$("#span1").hide();
							bl = true;
						}
				   }
			});
			return bl;
		}
		function sendAjax(url){
			var json = {};
			$.ajax({
				   type: "POST",
				   url: url,
				   async:false,//发送同步请求
				   dataType: "json",
				   success: function(data){
				   		json = data;
				   }
			});
			return json;
		}
		
		function hideSpan(){
			$("#span1").hide();
		}
	</script>
</head>
<body>
		<form:form id="inputForm" modelAttribute="postType" action="${ctx}/sys/postType/save" method="post" onkeydown="if(event.keyCode==13)return false;"   class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>岗位分类：</label></td>
					<td class="width-35">
						<form:input path="name" id="name" htmlEscape="false" maxlength="50" class="form-control required" onchange="hideSpan()" onblur="fun_ajax()"/>
						<input type="hidden" name="hideName" id="hideName"  value="${postType.name }"/>
						<span id="span1" style="display: none"><label id="name-error" class="error">该名称已存在</label></span>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>