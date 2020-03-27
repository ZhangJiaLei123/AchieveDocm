<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>

<html>
<head>
<meta name="decorator" content="default"/>
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
	var id = $("#showResourceId",window.parent.document).val();
	$.ajax({
	    async: false,
	    url: "${pageContext.request.contextPath}/findResourseById",
	    data:{id:id},
	    dataType: "json",
	    success: function (data) {
	    	if(data){
	    		$("#id").val(data['id']);
		    	$("#name").val(data['name']);
		    	$("#resourceType").val(data['resourceType']);
		    	$("#remarks").val(data['remarks']);
		    	$("#resourceCode").val(data['resourceCode']);
		    	$("#resourceDirName").html(data['resourceDirName']);
		    	$("#resourceDir").val(data['resourceDir']);
		    	$("#resourceUrl").val(data['resourceUrl']);
		    	$("#resourceUrlPreview").html("");
		    	var url = data['resourceUrl'];
		    	if(url){
		    		var fileName = url.substring(url.lastIndexOf("/")+1,url.length);
		    		$("#resourceUrlPreview").html('<li><a href="'+url+'" target="_blank" url='+url+'>'+fileName+'</a>&nbsp;&nbsp;<a onclick="resourceUrlDel(this);" href="javascript:">×</a></li>');
		    	}
	    	}
		    
	    }
	});
	
}); 

function submitForm(){
	$("#editResourceForm").ajaxSubmit({
		type : 'post',
		url : "${pageContext.request.contextPath}/editResource",
		success : function(data) {
			if(data){
				if($("#id").val()){
					alert('添加成功！');
				}else{
					alert('修改成功！');
				}
				var index = parent.layer.getFrameIndex(window.name); 
				parent.layer.close(index);
				
				parent.location.href = "${pageContext.request.contextPath}/findMyResourse";
				
			}
		},
		error : function(data)//服务器响应失败处理函数
		{
			alert("出错");
		}
	});
} 
	
function selectResourceDirTree(){
	layer.open({
	    type: 2,
	    title: '选择资源类别',
	    maxmin: false,
	    name:"opOfficeType",
	    area: ['255px', '280px'],
	    content: "${pageContext.request.contextPath}/jsp/zTreeResourDir.jsp",
	    end: function(){
	      //layer.tips('Hi', '#about', {tips: 1})
	     // alert(this.index)
	    }
	  });
} 
function setValue(id,value){
	$("#resourceDirName").html(value);
	$("#resourceDir").html(id);
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
<form action="${pageContext.request.contextPath}/editResource" method="post" id="editResourceForm" enctype="multipart/form-data">
<table id="showTable" style="margin: 15px;">
	<tr  style="height: 40px;">
		<td>资源名称：</td>
		<td colspan="3">
			<input type="text" id ="name" name="name">
		</td>
	</tr>
	<tr style="height: 100px;">
		<td style="width:100px">选择资源：</td>
		<td style="width:150px;font-size: 12px;" >
			<input type="hidden" name="id" id ="id">
			<input type="hidden" name="resourceUrl" id ="resourceUrl" htmlEscape="false" class="form-control"/>
			<sys:ckfinder input="resourceUrl" type="files" uploadPath="/resource/mesanInfo" selectMultiple="false"/>
		</td>
		<td style="width:100px">资源类型：</td>
		<td style="width:150px">
			<select id="resourceType" name ="resourceType">
				<option value="1" >PPT</option>
				<option value="2" >Word</option>
				<option value="3" >软教具</option>
			</select>
		</td>
	</tr>
	<tr  style="height: 100px;">
		<td>资源描述：</td>
		<td colspan="3">
			<textarea rows="4" name="remarks" id="remarks" style="width:400px"></textarea>
		</td>
	</tr>
	<tr  style="height: 40px;">
		<td>资源编号：</td>
		<td colspan="3"><input type="text" id ="resourceCode" name="resourceCode"></td>
	</tr  style="height: 40px;">
		<tr>
		<td>资源目录：</td>
		<td colspan=3>
			<span id="resourceDirName"></span>&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="hidden" name="resourceDir" id="resourceDir">
			<a onclick="selectResourceDirTree()"  class="btn btn-primary" >选择资源目录</a>
	</tr>
</table>
<div style="width:580px;text-align: center;padding-top: 20px;">
	<a href="#"  class="btn btn-primary" onclick="submitForm()">确定</a>
	<a href="#" onclick="colseWindow()"  class="btn btn-primary" >取消</a>
</div>
</form>
</body>
</html>