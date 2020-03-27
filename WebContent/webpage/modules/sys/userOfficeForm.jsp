<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>用户组织关系管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var validateForm;
		var uploaderIsInit;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form() && validateFormVerfy()){
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
			$.ajax({
	               type: "POST",
	               url: "${ctx}/sys/userOffice/findAllRole",
	               data: {},
	               success:function(data){
	            	   var jsonObj = eval(data);
	            	  for(var i=0;i<jsonObj.length;i++){
	            		  	var obj = jsonObj[i];
	            		  	$("#selectRole").append("<option enValue="+obj['enname']+" value='"+obj['id']+"'>"+obj['name']+"</option>");//添加下拉列表
	            	  }
	            	  var roleEnName = $("#roleEnName").val();
	            	  $("#selectRole").val($("#userRoleId").val());
	            	  if(roleEnName == "teacher" || roleEnName=="nx_teacher" || roleEnName =="assignment"){
	      				$("#showOfficeDiv").show();
	      				$("#iframeXxOffeic").attr("src","${ctx}/sys/office/addUserRoleOfficeList?roleCode="+roleEnName+"&userId="+$("#userId").val());
	      				if(roleEnName != "teacher" && roleEnName !="px_system") {
	    					//初始化上传控件
	    					initWebUploadFiles("verifyUrl");
	    					uploaderIsInit = true;
	    					var fileurl = "${userOffice.verifyUrl}";
	    					if(fileurl){
	    						fileName = fileurl.substring(fileurl.lastIndexOf("/")+1,fileurl.length);
	    						$("#showImgDiv").html("<a href='"+fileurl+"'>"+decodeURI(fileName)+"</a>");
	    					}
	    					$("#verifyTr").show();
	    				}else {
	    					$("#verifyTr").hide();
	    				}
	            	 }else{
	      				$("#showOfficeDiv").hide();
	      			}
	               }
	          })
		});
		
		function changeOffice(obj){
			var str =  $("#selectRole").find("option:selected").attr("enValue"); 
			if(str == "teacher" || str=="nx_teacher" || str =="px_system"){
				$("#showOfficeDiv").show();
				$("#iframeXxOffeic").attr("src","${ctx}/sys/office/addUserRoleOfficeList?roleCode="+str+"&userId="+$("#userId").val());
				if(str != "teacher" && str !="px_system") {
					//初始化上传控件
					if(!Boolean(uploaderIsInit)) {
						initWebUploadFiles("verifyUrl");
						uploaderIsInit = true;
					}
					$("#verifyTr").show();
				}else {
					$("#verifyTr").hide();
				}
			}else{
				$("#showOfficeDiv").hide();
				$("#verifyTr").hide();
			}
		}
		
		function parentOfficeChange(isCheckAll,str,officeType,officeLevel,areaId){
			$("#checkAll").val(isCheckAll);
			$("#officeStr").val(str);
			$("#officeType").val(officeType);
			$("#officeLevel").val(officeLevel);
			$("#areaId").val(areaId);
		}
		
		function validateFormVerfy(){
			var str =  $("#selectRole").find("option:selected").attr("enValue"); 
				if(str == "teacher" || str=="nx_teacher" || str =="px_system"){
					
				if($("#checkAll").val()=="" && $("#officeStr").val()==""){
					top.layer.alert("请选择权限机构。");
					return false;
				}
			}
			var role = $("#selectRole").find("option:selected").attr("enValue");
			if(role == "nx_teacher") {
				var resUrl = $("#verifyUrl").val();
				if(!resUrl){
					top.layer.alert("认证资料不能为空，请上传资料。");
					return false;
				}
			}
		
			return true;
		}
	</script>
</head>
<body>
		<form:form id="inputForm" modelAttribute="userOffice" action="${ctx}/sys/userOffice/userRoleSaveOffice" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">权限分类：</label></td>
					<td class="width-35">
						<input type="hidden" id="userId" name="userId" value="${userOffice.userId}"/>
						<input type="hidden" name="officeStr" id="officeStr" value="${userOffice.officeStr }"/>
						<input type="hidden" value="${role.enname}" id="roleEnName"/>
						<input type="hidden" value="${role.id}" id="userRoleId"/>
						<input type="hidden"  id="checkAll" name="checkAll"/>
						<input type="hidden" id="officeType" name="officeType"/>
						<input type="hidden" id="officeLevel" name="officeLevel"/>
						<input type="hidden" id="areaId" name="areaId"/>
						<select id="selectRole" name="roleId" id="roleId"  class="form-control" onchange="changeOffice(this)">
						  
						</select>
					</td>
		  		</tr>
		  		<tr>
		  			<td colspan="2">
		  				<div id="showOfficeDiv" style="display: none">
		  					<iframe width="100%" id ="iframeXxOffeic" height="300px" style="border: none" src="${ctx}/sys/office/addUserRoleOfficeList?roleCode=${role.enname}&userId=${userOffice.userId}"></iframe>
		  				</div>
		  			</td>
		  		</tr>
		  		<tr id="verifyTr" style="display: none">
		  			<td class="width-15 active"><label class="pull-right">认证资料：</label></td>
		  			<td class="width-35">
						<div id="thelist" class="uploader-list">
					    	
					    </div>
						 <div>
						     <div id="filePicker" style="float:left">选择文件</div>&nbsp;&nbsp;
						     <div id="ctlBtn" class="startUploadButton"  style="float:left" >开始上传</div><span style="color:red;font-size: 10px;line-height: 30px">(必须先点击上传文件才能保存)</span>
						     <input type="hidden" value="" id="uploadUrl"/>
				       </div>
				       <div id="filePicker_error"></div>
						<form:hidden id="verifyUrl"  path="verifyUrl" htmlEscape="false" maxlength="300" class="form-control"/>
					</td>
		  		</tr>
		 	</tbody>
		</table>
		<!-- <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
			<tbody>
				<tr>
					<td>审核资料：</td>
					<td>dd</td>
				</tr>
			</tbody>
		</table> -->
	</form:form>
</body>
</html>