<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>组织人员</title>
<meta charset="utf-8">
<meta name="decorator" content="default"/>
</head>
<script type="text/javascript">

</script>
<body>
<input type="hidden" id="showResourceId">
<!--头部 start-->
<jsp:include page="/include/head.jsp"></jsp:include>
<script type="text/javascript">
	$($("a",$("#topA"))[2]).attr("class","active");
	$(document).ready(function() {
		$("#resourceType").prepend("<option value=''>请选择</option>");
		$("#resourceType").val($("#resourceTypeValue").val());
	});
</script>
<!--头部 end-->
<!--身体 start-->
			<div class="row" style="min-height: 510px;padding-bottom:140px;">
				<div  style="height: 90px;  border-bottom: 1px solid #d8d8d8;padding-top: 30px;">
					<a style="color: #18a589;margin-right: 40px;"  href="${pageContext.request.contextPath}/a/findStudyResourse">资源库</a>
					<a style="margin-right: 40px;" href="${pageContext.request.contextPath}/a/findMyResourse">我的资源</a>
					<form:form id="searchForm" modelAttribute="resourceInfo" action="${pageContext.request.contextPath}/a/findStudyResourse" method="post" class="form-inline">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div style="float:right">
					<table >
					<tr style="line-height: 32px;">
						<td>
							<input type="hidden" id = "resourceTypeValue" value="${resourceInfo.resourceType}">
							<form:select path="resourceType" class="form-control" id="resourceType">
								<form:options items="${fns:getDictList('resource_type')}" itemLabel="label" itemValue="value" htmlEscape="true"/>
							</form:select>
						</td>
						<td width="5px;"></td>
						<td width="150px;" >
							<form:input style="height: 32px;" path="name" htmlEscape="false" maxlength="300"  class="input_style" placeholder="资源名称"/>
						</td>
						<td width="5px;"></td>
						<td>
							 <button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i>搜索</button>
						</td>
					</tr>
				</table>
				</div>
				</form:form>
			</div>
				<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th >资源名称</th>
				<th >资源编号</th>
				<th >资源类型</th>
				<th >上传时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="resourceInfo" varStatus="status"> 
			<tr>
				<td title="${resourceInfo.name}">
					<c:choose>  
				        <c:when test="${fn:length(resourceInfo.name) >12 }">  
				           ${fn:substring(resourceInfo.name, 0, 11)}…
				         </c:when>  
				        <c:otherwise>  
				           ${fn:substring(resourceInfo.name, 0, 12)}
				         </c:otherwise>  
				     </c:choose>  
				</td>
				<td>
					${resourceInfo.resourceCode}
				</td>
				<td>
					${fns:getDictLabel(resourceInfo.resourceType, 'resource_type', '')}
				</td>
				
				<td>
					<fmt:formatDate value="${resourceInfo.updateDate}" pattern="yyyy-MM-dd HH:mm"  type="both"/>
				</td>
				<td>
					<a href="#" onclick="openDialogView('查看资源管理', '${ctx}/resource/resourceInfo/teacherShowResourceInfo?id=${resourceInfo.id}','800px', '600px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
		<!-- 分页代码 -->
	<table:page page="${page}"></table:page>
		</div>

<!--尾部 start-->
<div class="copyright">© 云帆互联 2014 汽车服务网 京ICP备14016447号</div>
<!--尾部 end-->

</body>
</html>
    