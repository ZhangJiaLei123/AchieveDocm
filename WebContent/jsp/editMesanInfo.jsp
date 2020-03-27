<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<style type="text/css">

.file {
    position: relative;
    display: inline-block;
    background: #D0EEFF;
    border: 1px solid #99D3F5;
    border-radius: 4px;
    padding: 4px 12px;
    overflow: hidden;
    color: #1E88C7;
    text-decoration: none;
    text-indent: 0;
    line-height: 20px;
}
.file input {
    position: absolute;
    font-size: 100px;
    right: 0;
    top: 0;
    opacity: 0;
}
.file:hover {
    background: #AADFFD;
    border-color: #78C3F3;
    color: #004974;
    text-decoration: none;
}
</style>
<%@ include file="/webpage/include/head.jsp"%>
<script src="${pageContext.request.contextPath}/js/jquery-1.12.4.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.ui.main.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-form.js"></script>
<script src="${pageContext.request.contextPath}/js/layer-master/build/layer.js"></script>

<script type="text/javascript">
$(document).ready(function(){ 
	var id = $("#showMeasnInfoId",window.parent.document).val();
	$.ajax({
	    async: false,
	    url: "${pageContext.request.contextPath}/findMesanInfoById",
	    data:{id:id},
	    dataType: "json",
	    success: function (data) {
	    	if(data){
	    		$("#id").val(data['id']);
		    	$("#name").val(data['name']);
		    	$("#mesanUrl").val(data['mesanUrl']);
		    	$("#remarks").val(data['remarks']);
		    	var url = data['mesanUrl'];
		    	if(url){
		    		var fileName = url.substring(url.lastIndexOf("/")+1,url.length);
		    		$("#mesanUrlPreview").html('<li><a href="'+url+'" target="_blank" url='+url+'>'+fileName+'</a>&nbsp;&nbsp;<a onclick="mesanUrlDel(this);" href="javascript:">×</a></li>');
		    	}
	    	}
		    
	    }
	});
	
}); 

function submitForm(){
	$("#editMesanInfoForm").ajaxSubmit({
			type : 'post',
			url : "${pageContext.request.contextPath}/editMesanInfo",
			success : function(data) {
				if(data){
					var index = parent.layer.getFrameIndex(window.name); 
					parent.layer.close(index);
					window.parent.location.reload();
				}
			},
			error : function(data)//服务器响应失败处理函数
			{
				alert("出错");
			}
		});
	} 

function colseWindow(){
	var index = parent.layer.getFrameIndex(window.name); 
	parent.layer.close(index);
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看资源信息</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/editMesanInfo" method="post" id="editMesanInfoForm" >
<table id="showTable" style="margin: 15px;">
	<tr style="height: 40px;">
		 <td >资料名称：</td>
		<td >
			<input type="text" style="width:300px" name="name" id="name">
			<input type="hidden" name="id" id="id">
		</td>
	</tr>
	<tr  style="height: 100px;">
		<td >资料描述：</td>
		<td >
			<textarea rows="4" name="remarks" style="width:300px" id="remarks"></textarea>
		</td>
	</tr>
	<tr style="height: 100px;">
		<td style="width:100px">选择资料：</td>
		<td style="width:250px;font-size: 12px;">
			<input type="hidden" name="mesanUrl" id ="mesanUrl" htmlEscape="false" class="form-control"/>
  			<sys:ckfinder input="mesanUrl" type="files" uploadPath="/resource/mesanInfo" selectMultiple="false"/>
		</td>
	</tr>
</table>
<div style="width:580px;text-align: center;padding-top: 20px;">
	<a href="#"  class="btn btn-primary" onclick="submitForm()">确定</a>
	<a href="#" onclick="colseWindow()"  class="btn btn-primary" >取消</a>
</div>
</form>
</body>
</html>