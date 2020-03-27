<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>资料信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  var mesanUrl =  $("#mesanUrl").val();
		  if(mesanUrl == ""){
			  layer.alert("资料路径是必填的");
			  return false;
		  }
		  if(checkCode() && validateForm.form()){
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
			//初始化上传控件
			initWebUploadFiles("mesanUrl");
			var fileurl = "${mesanInfo.mesanUrl}";
			if(fileurl){
				fileName = fileurl.substring(fileurl.lastIndexOf("/")+1,fileurl.length);
				$("#showImgDiv").html("<a href='"+fileurl+"'>"+decodeURI(fileName)+"</a>");
			}
			jQuery.validator.addMethod("alnum", function(value, element){
				 return this.optional(element) ||/^[a-zA-Z0-9]+$/.test(value);
				 }, "只能输入英文字母和数字");
			
		});
		function checkCode(){
			var mesanCode = $("#mesanCode").val();
			var bl = false;
			if(mesanCode){
				$.ajax({
				     type: 'POST',
				     url: "${ctx}/resource/mesanInfo/checkMesanCode",
				     async: false,
				     data:{oldMesanCode: encodeURIComponent('${mesanInfo.mesanCode}'),mesanCode:mesanCode},
				     success: function(data){
				    	 if(data != "true"){
								$("#span1").show();
								$("#mesanCode").focus();
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
		<form:form id="inputForm" modelAttribute="mesanInfo" action="${ctx}/resource/mesanInfo/save" method="post" onkeydown="if(event.keyCode==13)return false;" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		    <tr>
		   		<td class="width-15 active"><label class="pull-right"><font color="red">*</font>资料类别：</label></td>
					<td class="width-35">
						<sys:treeselect id="mesanType" name="mesanType" value="${mesanInfo.mesanType}" labelName="mesanTypeName" labelValue="${mesanInfo.mesanTypeName}"
						title="资料类别" url="/resource/mesanDir/treeData" extId="${mesanType}" cssClass="form-control required"/>
					</td>
		   		</tr>
		   		<tr>
		   			<td class="width-15 active"><label class="pull-right"><font color="red">*</font>资料出处：</label></td>
					<td class="width-35">
						<form:input path="mesanCode" onblur="checkCode()" htmlEscape="false" maxlength="200" class="form-control required alnum"/>
						<span id="span1" style="display: none"><label id="name-error" class="error">该资料编码已存在</label></span>
					</td>
		   		</tr>
		   		<tr>
		   			<td class="width-15 active"><label class="pull-right"><font color="red">*</font>资料名称：</label></td>
					<td class="width-35">
						<form:input path="name" htmlEscape="false" maxlength="200" class="form-control required"/>
					</td>
		   		</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">资料简介（50字以内）：</label></td>
					<td class="width-35">
						<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="form-control "/>
					</td>
					
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>资料路径：</label></td>
					<td class="width-35">
						<div id="thelist" class="uploader-list">
					    	
					    </div>
						 <div>
						     <div id="filePicker" style="float:left">选择文件</div>&nbsp;&nbsp;
						     <div id="ctlBtn" class="startUploadButton"  style="float:left" >开始上传</div><span style="color:red;font-size: 10px;line-height: 30px">(必须先点击上传文件才能保存)</span>
						     <input type="hidden" value="" id="uploadUrl"/>
				       </div>
				       <div id="filePicker_error"></div>
						<form:hidden id="mesanUrl" path="mesanUrl" htmlEscape="false" maxlength="64" class="form-control"/>
					</td>
		  		</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>