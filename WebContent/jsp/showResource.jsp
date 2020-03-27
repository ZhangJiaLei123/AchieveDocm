<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="${pageContext.request.contextPath}/js/jquery-1.12.4.min.js"></script>
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
	    		 $("span",$("#showTable")).each(function(){
		    	    	var key = this.id;
		    	    	var value = data[key];
						if(key=="resourceType"){
							if(value==1){
								$("#"+key).html("PPT");
							}else if(value==2){
								$("#"+key).html("WORD");
							}else if(value==3){
								$("#"+key).html("软教具");
							}

		    	    	}else{
		    	    		$("#"+key).html(value);
		    	    	}
		    	    	
		    	  });
	    		 if(data['resourceSwfUrl']){
			    	  $("#showUrl").val(data['resourceSwfUrl']);
		    		  $("#showTr").show();
	    		 }else{
	    			 $("#showTr").show();
		    		  $("#showTd").html("文件不存在");
	    		 }
	    	}
		    
	    }
	});
}); 
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看资源信息</title>
</head>
<body>
<table id="showTable">
	<tr>
		<td style="width:100px">资源名称：</td>
		<td style="width:150px"><span id="name"></span></td>
		<td style="width:100px">资源类型：</td>
		<td style="width:150px"><span id="resourceType"></span></td>
		<td style="width:100px">资源目录：</td>
		<td style="width:150px"><span id="resourceDirName"> </span></td>
		<td style="width:100px">资源编号：</td>
		<td style="width:150px"><span id="resourceCode"></span></td>
	</tr>
	<tr>
		<td>资源描述：</td>
		<td colspan="7"><span id="remarks"></span></td>
		
	</tr>
	<tr id="showTr" style="display: none">
		<td>资源展示：</td>
		<td colspan="7" id="showTd">
			<input type="hidden" id="showUrl"/>
			<iframe src="${pageContext.request.contextPath}/documentView.jsp" width="100%" height="400px" style="border: none"></iframe>
		</td>
	</tr>
</table>
</body>
</html>