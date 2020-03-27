<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>问卷信息管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">


function checkPub(pnum,title,url,width,height){
	if(pnum>0){
		openDialog(title,url,width,height);
	}else{
		top.layer.alert('当前问卷尚未添加题目！', {icon: 0});
	}
}
</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="ibox">
			<!-- <div class="ibox-title">
				<h5>问卷列表</h5>
				<div class="ibox-tools">
				</div>
			</div> -->

			<div class="ibox-content">
				<sys:message content="${message}" />
				<!-- 工具栏 -->
				<div class="row">
					<div class="col-sm-12">
						<div class="pull-left">
							<shiro:hasPermission name="question:questionInfo:add">
								<table:addRow url="${ctx}/question/questionInfo/form"
									title="问卷信息"></table:addRow>
								<!-- 增加按钮 -->
							</shiro:hasPermission>
							<shiro:hasPermission name="question:questionInfo:del">
								<table:delRow url="${ctx}/question/questionInfo/deleteAll"
									id="contentTable"></table:delRow>
								<!-- 删除按钮 -->
							</shiro:hasPermission>
							<button class="btn btn-white btn-sm " data-toggle="tooltip"
								data-placement="left" onclick="sortOrRefresh()" title="刷新">
								<i class="glyphicon glyphicon-repeat"></i> 刷新
							</button>
						</div>
						<div class="pull-right">
							<form:form id="searchForm" modelAttribute="questionInfo"
							action="${ctx}/question/questionInfo/" method="post"
							class="form-inline">
							<input id="pageNo" name="pageNo" type="hidden"
								value="${page.pageNo}" />
							<input id="pageSize" name="pageSize" type="hidden"
								value="${page.pageSize}" />
							<table:sortColumn id="orderBy" name="orderBy"
								value="${page.orderBy}" callback="sortOrRefresh();" />
							<!-- 支持排序 -->
							<div class="form-group">
								<form:input path="queName" htmlEscape="false" maxlength="255"
									class=" form-control input-sm" placeholder="请输入问卷名称"/>
							</div>
								<button class="btn btn-primary btn-rounded btn-outline btn-sm "
								onclick="search()">
								<i class="fa fa-search"></i> 查询
							</button>
						</form:form>
						</div>
					</div>
				</div>

				<!-- 表格 -->
				<table id="contentTable"
					class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
					<thead>
						<tr>
							<th><input type="checkbox" class="i-checks"></th>
							<th>序号</th>
							<th >问卷名称</th>
							<th >问卷描述</th>
							<th >问题总数</th>
							<th >问卷状态</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<%
							int num = 0;
						%>
						<c:forEach items="${page.list}" var="questionInfo">
							<%
								num++;
							%>
							<tr>
								<td><input type="checkbox" id="${questionInfo.id}" class="i-checks"></td>
								<td><%=num%></td>
								<c:choose> 
							     <c:when test="${fn:length(questionInfo.queName) > 15}"> 
							      <td>${fn:substring(questionInfo.queName, 0, 15)}...</td>
							     </c:when> 
							     <c:otherwise> 
							      <td>${questionInfo.queName}</td> 
							     </c:otherwise>
							    </c:choose>
								<c:choose> 
							     <c:when test="${fn:length(questionInfo.remarks) > 15}"> 
							      <td>${fn:substring(questionInfo.remarks, 0, 15)}...</td>
							     </c:when> 
							     <c:otherwise> 
							      <td>${questionInfo.remarks}</td> 
							     </c:otherwise>
							    </c:choose>
		                        <td>${questionInfo.pNum}</td>
								<td>
				<c:if test="${questionInfo.status==1}">新建</c:if>
				<c:if test="${questionInfo.status==2}">发布</c:if>
				</td>
				<td>
					<shiro:hasPermission name="question:questionInfo:view">
						<a href="#" onclick="openDialogView('查看问卷信息', '${ctx}/question/questionInfo/formdetail?id=${questionInfo.id}','800px', '500px')"
											class="btn btn-info btn-xs"><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<c:if test="${questionInfo.status==1}">
					<shiro:hasPermission name="question:questionInfo:edit">
    					<a href="#" onclick="openDialog('修改问卷信息', '${ctx}/question/questionInfo/form?id=${questionInfo.id}','800px', '500px')"
												class="btn btn-success btn-xs"><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="question:questionInfo:del">
						<a href="${ctx}/question/questionInfo/delete?id=${questionInfo.id}"
												onclick="return confirmx('确认要删除该问卷信息吗？', this.href)"
												class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="question:questionProblem:list">
						<a href="${ctx}/question/questionProblem/editproblem?questionid=${questionInfo.id}"
												class="btn btn-success btn-xs"><i class="fa fa-edit"></i> 编辑问卷题目</a>
    				</shiro:hasPermission>
    				</c:if>
    				<shiro:hasPermission name="question:questionRelease:add">
    				    <a href="#" onclick="checkPub(${questionInfo.pNum},'发布问卷', '${ctx}/question/questionRelease/releaseform?questionid=${questionInfo.id}','500px', '300px')"
											class="btn btn-success btn-xs"><i class="fa fa-edit"></i> 发布</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="question:questionRelease:list">
					    <a href="${ctx}/question/questionRelease/listbyquestionid?questionid=${questionInfo.id}"
											class="btn btn-success btn-xs"><i class="fa fa-edit"></i> 发布列表</a>
    				</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
		<!-- 分页代码 -->
	<table:page page="${page}"></table:page>
	<br />
	<br />
	</div>
	</div>
</div>
</body>
</html>