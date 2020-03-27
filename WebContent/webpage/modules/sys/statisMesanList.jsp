<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>资料信息统计</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		});
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<!-- <div class="ibox-title">
		<h5>资料信息列表 </h5>
	</div> -->
    
    <div class="ibox-content">
	<sys:message content="${message}"/>
	
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
		
			</div>
		<div class="pull-right">
			<form:form id="searchForm" modelAttribute="mesanInfo" action="${ctx}/sys/statis/statisMesanList" method="post" class="form-inline">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
				<div class="form-group">
					<form:input path="name" htmlEscape="false" maxlength="300"  class=" form-control input-sm" placeholder="请输入资料名称"/>
				 </div>
				<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 搜索</button>
			</form:form>
		</div>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th  class=" name">资料名称</th>
				<th  class=" mesanCode">资料编码</th>
				<th  class=" mesanTypeName">资料类别</th>
				<th  class=" browseTime">浏览时间</th>
				<th  class=" browseCount">浏览次数</th>
				<th  class=" downLoadCount">下载次数</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="mesanInfo" varStatus="status">
			<tr>
				<td title="${mesanInfo.name}">
					<c:choose>  
				        <c:when test="${fn:length(mesanInfo.name) >25 }">  
				           ${fn:substring(mesanInfo.name, 0, 24)}…
				         </c:when>  
				        <c:otherwise>  
				           ${fn:substring(mesanInfo.name, 0, 25)}
				         </c:otherwise>  
				     </c:choose>  
				</td>
				<td>${mesanInfo.mesanCode}</td>
				<td>${mesanInfo.mesanTypeName}</td>
				<td>${mesanInfo.browseTime}</td>
				<td>
					${mesanInfo.browseCount}
				</td>
				<td>
					${mesanInfo.downLoadCount}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
		<!-- 分页代码 -->
	<table:page page="${page}"></table:page>
	<br/>
	<br/>
	</div>
	</div>
</div>
</body>
</html>