<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>学习活动</title>
<meta charset="utf-8">
<meta name="decorator" content="default"/>
</head>
<script type="text/javascript">
function submitForm(pageNo){
	$("#pageNo").val(pageNo);
	$("#studyActiveForm").submit();
}

</script>
<body>
<input type="hidden" id="showResourceId">

<!--头部 start-->
<jsp:include page="/include/head.jsp"></jsp:include>
<script type="text/javascript">
	$($("a",$("#topA"))[5]).attr("class","active");
</script>
<!--头部 end-->
<!--身体 start-->
			<div class="row" style="min-height: 510px;padding-bottom:140px;">
				<div class="nav_menu">
					<a  href="${pageContext.request.contextPath}/a/studyActivityList">学习活动计划</a>
					<a  class="active" href="${pageContext.request.contextPath}/a/gzActivityList">关注计划</a>
					<a  href="${pageContext.request.contextPath}/a/teacherSstudyActivity">全部学习活动</a>
					<a  href="${pageContext.request.contextPath}/a/train/studyActivity/teacherFormBase" >新建学习活动</a>
					<a  href="${pageContext.request.contextPath}/a/myTeacherStudyActivity">我的学习活动</a>
					<form:form id="searchForm" modelAttribute="trainProgram" action="${pageContext.request.contextPath}/a/studyActivityList" method="post" class="form-inline">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div style="float: right">
						<span>
						<span><input type="text" name="proName" value="${trainProgram.proName}" class="input_style" placeholder="计划名称"></span>
						<span></span><button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button></span>
					</div>
					</form:form>
				</div>
			<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th >计划名称</th>
				<th width="100px">计划类型</th>
				<th width="200px">计划时间</th>
				<th width="100px;">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="trainProgram" varStatus="status"> 
			<tr>
				<td title="	${trainProgram.proName }">
					<c:choose>  
				        <c:when test="${fn:length(trainProgram.proName) >45 }">  
				           ${fn:substring(trainProgram.proName, 0, 44)}…
				         </c:when>  
				        <c:otherwise>  
				           ${fn:substring(trainProgram.proName, 0, 45)}
				         </c:otherwise>  
				     </c:choose>  
				</td>
				<td>
					<c:if test="${trainProgram.proType=='YD'}">
									月度
					</c:if>
					<c:if test="${trainProgram.proType=='JD'}">
						季度
					</c:if>
				</td>
				<td>
					<fmt:formatDate value="${trainProgram.gzStartTime}" pattern="yyyy-MM-dd"/>~<fmt:formatDate value="${trainProgram.gzEndTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<a  href="${pageContext.request.contextPath}/a/viewStudyActivityList?id=${trainProgram.id}" class="btn btn-info btn-xs"> 查看</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
   <table:page page="${page}"></table:page>
	</div>
<!--尾部 start-->
	<div class="copyright">© 云帆互联 2014 汽车服务网 京ICP备14016447号</div>
<!--尾部 end-->

</body>
</html>
    