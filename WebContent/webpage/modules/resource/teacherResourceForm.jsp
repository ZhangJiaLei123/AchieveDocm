<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>资源管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(checkCode() && validateForm.form()&&validatePageForm()){
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
			//初始化上传控件
			initWebUploadFiles("resourceUrl");
			var fileurl = "${resourceInfo.resourceUrl}";
			if(fileurl){
				fileName = fileurl.substring(fileurl.lastIndexOf("/")+1,fileurl.length);
				$("#showImgDiv").html("<a href='"+fileurl+"'>"+decodeURI(fileName)+"</a>");
			}
			jQuery.validator.addMethod("alnum", function(value, element){
				 return this.optional(element) ||/^[a-zA-Z0-9]+$/.test(value);
				 }, "只能输入英文字母和数字");
			
		});
		
		function validatePageForm(){
			var url = $("#resourceUrl").val();
			if(url==""){
				top.layer.alert("请选择资源，资源路径不能为空。");
				return false;
			}
			return true;
		}

		
		function checkCode(){
			var resourceCode = $("#resourceCode").val();
			var bl = false;
			if(resourceCode){
				$.ajax({
				     type: 'POST',
				     url: "${ctx}/resource/resourceInfo/checkResourceInfoCode",
				     async: false,
				     data:{oldResourceCode: encodeURIComponent('${resourceInfo.resourceCode}'),resourceCode:resourceCode},
				     success: function(data){
				    	 if(data != "true"){
								$("#span1").show();
								$("#resourceCode").focus();
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
		<form:form id="inputForm" modelAttribute="resourceInfo" action="${ctx}/resource/resourceInfo/teacherSaveResource" target="_top" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		   		<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>资源名称：</label></td>
					<td class="width-35">
						<form:input path="name" htmlEscape="false" maxlength="300" class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>资源类别：</label></td>
					<td class="width-35">
						<form:select path="resourceType" class="form-control">
							<form:options items="${fns:getDictList('resource_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>资源路径：</label></td>
					<td class="width-35">
						<div id="thelist" class="uploader-list">
					    	
					    </div>
						 <div>
						     <div id="filePicker" style="float:left">选择文件</div>&nbsp;&nbsp;
						     <div id="ctlBtn" class="startUploadButton"  style="float:left" >开始上传</div><span style="color:red;font-size: 10px;line-height: 30px">(必须先点击上传文件才能保存)</span>
						     <input type="hidden" value="" id="uploadUrl"/>
				       </div>
				       <div id="filePicker_error"></div>
						<form:hidden id="resourceUrl" path="resourceUrl" htmlEscape="false" maxlength="300" class="form-control"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">资源描述：</label></td>
					<td class="width-35">
						<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>资源编号：</label></td>
					<td class="width-35">
						<form:input path="resourceCode" onblur="checkCode()" htmlEscape="false" maxlength="30" class="form-control required alnum"/>
						<span id="span1" style="display: none"><label id="name-error" class="error">该资源编号已存在</label></span>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>资源目录：</label></td>
					<td class="width-35">
					<sys:treeselect id="resourceDir" name="resourceDir" value="${resourceInfo.resourceDir}" labelName="resourceDirName" labelValue="${resourceInfo.resourceDirName}"
					title="资源目录" url="/resource/resourceDir/treeData" extId="${resourceDir}" cssClass="form-control required"/>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>