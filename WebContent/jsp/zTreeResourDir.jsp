<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选择机构</title>
<script src="${pageContext.request.contextPath}/js/jquery-1.12.4.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/js/zTree/css/demo.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/js/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/zTree/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/zTree/js/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/zTree/js/jquery.ztree.exhide.js"></script>
</head>
<SCRIPT type="text/javascript">
		<!--
		var setting = {
			check: {
				enable: true,
				chkStyle: "radio",
				chkboxType:{ "Y": "s", "N": "s" },
				radioType: "all"
					
			},
			data: {
				key: {
					title: "title"
				},
				simpleData: {
					enable: true
				}
			},
			callback: {
				
			}
		};
		
		$(document).ready(function(){
			var zNodes = "";
			$.ajax({
		        async: false,
		        url: "${pageContext.request.contextPath}/findResourceDirTree",
		        dataType: "json",
		        success: function (data) {
		        	zNodes = data;
		        }
			});
			$.fn.zTree.init($("#treeDemo"), setting,zNodes);
		});
		function getValue(){
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			var checkObj = zTree.getCheckedNodes(true);
			if(checkObj==0){
				window.parent.setValue('','');
				return ;
			}
			checkObj = checkObj[0];
			window.parent.setValue(checkObj['id'],checkObj['name']);
			var index = parent.layer.getFrameIndex(window.name); 
			parent.layer.close(index);

		}
		//-->
	</SCRIPT>
<body>
<div class="content_wrap" style="width:100px;height:150px">
	<div class="zTreeDemoBackground left" style="height:150px">
		<ul id="treeDemo" class="ztree" style="height:150px"></ul>
	</div>
</div>
<div style="width:200px;margin-top:20px;text-align: center">
	<input type="button" onClick="getValue()" value="确定">
</div>

</body>
</html>