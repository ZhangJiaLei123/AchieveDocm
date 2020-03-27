<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>活动章节管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		document.getElementById("tabIframe").src = "${ctx}/train/distribuTemp/listscore?activityId=${activityId}";
	});
	function tabChange(activityDirId,activityId) {
		document.getElementById("tabIframe").src = 
			"${ctx}/train/distribuTemp/listscore?activityId="+activityId+"&activityDirId="+activityDirId;
	}
</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<!-- 左侧页面 -->
		<div style="width: 50%; height: 100%;float: left;">
					<table id="contentTable"
						class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
						<tbody>
							<!-- 章  -->
							<c:forEach items="${resturnList}" var="listActivityDir">
								<c:forEach items="${listActivityDir['parent']}"
									var="activityDir">
									<tr>
										<td>
											<span><a onclick="tabChange('${activityDir.id}','${activityId}');">${activityDir.dirName}</a></span>
											<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${activityDir.coutRes}个资源</span><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${activityDir.resSorce}分</span>
										</td>
									</tr>
								</c:forEach>
								<!-- 节  -->
								<c:forEach items="${listActivityDir['son']}" var="activityDir">
									<tr>
										<td style="padding-left: 50px;">
											<a onclick="tabChange('${activityDir.id}','${activityId}');">${activityDir.dirName}</a>
											<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${activityDir.coutRes}个资源</span><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${activityDir.resSorce}分</span>
										</td>
									</tr>
								</c:forEach>
							</c:forEach>
						</tbody>
					</table>
					<!-- 分页代码 -->
					<table:page page="${page}"></table:page>
		</div>
		<!-- 右侧页面 -->
		<div style="width: 50%;height: 550px;float: left;">
			<iframe id="tabIframe" style="width: 97%; border:none; height: 100%;clear: both;" src="${ctx}/train/distribuTemp/listscore?activityId=${activityId}"></iframe>
		</div>
	</div>
	
</body>
</html>