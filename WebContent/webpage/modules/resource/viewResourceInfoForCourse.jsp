<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>资源信息查看</title>
	<meta name="decorator" content="default"/>
	<script type="text/JavaScript" src="/docm/showswf/js/jquery.min.js"></script>  
 <script type="text/javascript" src="/docm/showswf/js/flexpaper.js"></script>  
	<script type="text/javascript">
	
	</script>
</head>
<body>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td>
					<input type="hidden" value ="${resourceInfo.resourceSwfUrl}" id="showUrl">
						<iframe src="/docm/documentView.jsp" width="100%" height="500px" style="border: none"></iframe>
					</td>
				</tr>
		 	</tbody>
		</table>
</body>
</html>