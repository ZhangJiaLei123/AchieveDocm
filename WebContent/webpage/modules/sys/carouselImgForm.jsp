<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>轮播图管理</title>
	<style type="text/css">
		#jiacu {font-weight: bold}
		body{overflow: hidden;}
	</style>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  var img = $("#img").val();
		  if(img ==''){
			  layer.alert("请选择图片");
			  return false;
		  }
		  if(checkTitle() && checkSort() && validateForm.form()){
			  $("#inputForm").submit();
			  return true;
		  }
	
		  return false;
		}
		$(document).ready(function() {
			$("#no").focus();
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
			
			initWebUploadImg("img");
		});
		
		
		function checknumber(){
			document.getElementById("span").innerHTML="";
			var sort = document.getElementById("sort").value;
			if(isNaN(sort)){
				document.getElementById("sort").value = "";
				document.getElementById("span").innerHTML="<font color='cc3366' id='jiacu'>请输入数字</font>";
			}
			
		}
		
	

  function downloadFile(){
	  window.location.href=$("#uploadUrl").val();
  }
  
  
  function checkSort(){
		var sort = $("#sort").val();
		var bl = false;
		if(sort){
			$.ajax({
			     type: 'GET',
			     url: "${ctx}/sys/carouselImg/checkSort?oldsort="+ encodeURIComponent('${carouselImg.sort}')+"&sort="+sort ,
			     async: false,
			     success: function(data){
			    	 if(data != "true"){
							$("#span1").show();
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
  function checkTitle(){
		var titl = $("#titl").val();
		var bl = false;
		if(titl){
			$.ajax({
			     type: 'POST',
			     url: "${ctx}/sys/carouselImg/checkTitl",
			     async: false,
			     data:{oldtitl:'${carouselImg.titl}',titl:titl},
			     success: function(data){
			    	 if(data != "true"){
							$("#span2").show();
							$("#tit2").focus();
							bl = false;
						}else{
							$("#span2").hide();
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
	<style type="text/css">
	.startUploadButton{
 	position: relative;
    display: inline-block;
    cursor: pointer;
    background: #00b7ee;
    padding: 10px 15px;
    color: #fff;
    text-align: center;
    border-radius: 3px;
    overflow: hidden;
    margin-left: 20px;
  }
	</style>
</head>
<body style="overflow-x:auto;overflow-y:auto;">
		<form:form id="inputForm" modelAttribute="carouselImg" action="${ctx}/sys/carouselImg/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>标题：</label></td>
					<td class="width-35">
						<form:input path="titl" id="titl" onblur="checkTitle()" htmlEscape="false" maxlength="35"  class="form-control required"/>
						<span id="span2" style="display: none"><label id="name-error" class="error">该标题已存在</label></span>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>链接地址：</label></td>
					<td class="width-35">
						<form:input path="url" htmlEscape="false" maxlength="300" class="form-control url required"  />
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>图像：</label></td>
					<td class="width-35">
					 <!--用来存放item-->
				    <div id="thelist" class="uploader-list">
				    	<c:if test="${carouselImg.img !=null}">
					    	<div  class="file-item thumbnail" id="showImgDiv" style="width:100px;height: 100px;">
					    		<img  src="${carouselImg.img}" style="width:100%;height:100%">
					         </div>
				         </c:if>
				    </div>
				    <div>
				     <div id="filePicker" style="float:left">选择文件</div>&nbsp;&nbsp;
				     <div id="ctlBtn" class="startUploadButton"  style="float:left" >开始上传</div><span style="color:red;font-size: 10px;line-height: 30px">(必须先点击上传文件才能保存)</span>
				     <input type="hidden" value="" id="uploadUrl"/>
				    </div>
						<span><font color="red">（图片大小500*200）</font></span>
						<form:hidden path="img" id="img" htmlEscape="false" maxlength="255" class="form-control required"/>
					</td>
					
				</tr>
				
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>排序：</label></td>
					<td class="width-35">
					     <input id="oldsort" name="oldsort" type="hidden" value="${carouselImg.sort}">
						<form:input path="sort" id="sort" onblur="checkSort()" htmlEscape="false" class="form-control required digits"/>
						<span id="span1" style="display: none"><label id="name-error" class="error">该排序已存在</label></span>
						<span id="span"></span>
					</td>
		  		</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">描述：</label></td>
					<td class="width-35">
						<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="form-control "/>
					</td>
				</tr>
				
		
	</form:form>
</body>
</html>