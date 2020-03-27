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
	<div style="width:100%;height: 100%;padding: 5px;">
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" height="95%">
		   <tbody>
		   		<tr>
					<td class="active" width="100px"><label class="pull-right"><font color="red">*</font>资源名称：</label></td>
					<td width="80px">
						${resourceInfo.name}
					</td>
					<td class="active" width="100px"><label class="pull-right"><font color="red">*</font>资源类别：</label></td>
					<td width="80px">
							${fns:getDictLabel(resourceInfo.resourceType, 'resource_type', '')}
					</td>
					<td class="active" width="100px"><label class="pull-right"><font color="red">*</font>资源目录：</label></td>
					<td width="80px">
						${resourceInfo.resourceDirName}
					</td>
					<td class="active" width="100px"><label class="pull-right"><font color="red">*</font>资源编号：</label></td>
					<td width="80px">
						${resourceInfo.resourceCode}
					</td>
				</tr>
				<tr>
					<td class="active"><label class="pull-right">资源描述：</label></td>
					<td  colspan="7">
						${resourceInfo.remarks}
					</td>
				</tr>
				<tr>
					<td  colspan="8" height="380px">
					<input type="hidden" value ="${resourceInfo.resourceUrl}" id="resourceUrl">
					<input type="hidden" value ="${resourceInfo.resourceSwfUrl}" id="showUrl">
						<div style="width:100%;height: 100%;padding: 2px;">
							<iframe src="/docm/documentView.jsp" width="100%" height="100%" style="border: none"></iframe>
						</div>
					</td>
				</tr>
		 	</tbody>
		</table>
		</div>
</body>
</html>