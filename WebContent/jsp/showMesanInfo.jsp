<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="${pageContext.request.contextPath}/js/jquery-1.12.4.min.js"></script>
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
	    		 $("span",$("#showTable")).each(function(){
		    	    	var key = this.id;
		    	    	var value = data[key];
		    	    	
		    	    	if(key=="mesanUrl"){
		    	    		$("#"+key).html("${pageContext.request.contextPath}/"+value);
		    	    	}else{
		    	    		$("#"+key).html(value);
		    	    	}
		    	    })
	    	}
		    
	    }
	});
}); 
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看资源信息</title>
</head>
<body>
<table id="showTable" style="margin: 15px;">
	<tr>
		<td style="width:100px">资料名称:</td>
		<td style="width:150px"><span id="name"></span></td>
	</tr>
	<tr>
		<td>资源描述:</td>
		<td ><span id="remarks"></span></td>
	</tr>
	<tr>
		<td>选择资源:</td>
		<td ><span id="mesanUrl"></span></td>
	</tr>
</table>
</body>
</html>