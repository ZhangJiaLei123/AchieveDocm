<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>组织人员</title>
<meta name="decorator" content="default"/>
<meta charset="utf-8">
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

	function showFailInfo(id){
		var str = "";
		$.ajax({
	        async: false,
	        url: "${pageContext.request.contextPath}/a/findAppRecByResId",
	        data:{id:id},
	        dataType: "json",
	        success: function (data) {
	    	   if(data){
	    		   str = "<div style='padding:10px;line-height:20px'>审核失败原因：<br/>&nbsp;&nbsp;&nbsp;&nbsp;"+data['opinion']+"</div>"
	    	   }
	        }
		});
		layer.open({
		    type: 1,
		    title: '失败详情',
		    maxmin: false,
		    area: ['300px', '150px'],
		    content: str,
		    end: function(){
		      layer.tips('Hi', '#about', {tips: 1})
		    }
		  });
	}
</script>
<!--头部 end-->
<!--身体 start-->
			<div class="row" style="min-height: 510px;padding-bottom:140px;">
				<div class="nav_menu">
					<a  href="${pageContext.request.contextPath}/a/studyActivityList">学习活动计划</a>
					<a  href="${pageContext.request.contextPath}/a/gzActivityList">关注计划</a>
					<a  href="${pageContext.request.contextPath}/a/teacherSstudyActivity">全部学习活动</a>
					<a href="${pageContext.request.contextPath}/a/train/studyActivity/teacherFormBase">新建学习活动</a>
					<a  class="active" href="${pageContext.request.contextPath}/a/myTeacherStudyActivity">我的学习活动</a>
					<form:form id="searchForm" modelAttribute="studyActivity" action="${ctx}/myTeacherStudyActivity" method="post" class="form-inline">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div style="float: right">
						<span><form:input path="name" htmlEscape="false" maxlength="200"  class=" form-control input-sm"/></span>
						<span><button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button></span>
					</div>
					</form:form>
				</div>
	<!--查询条件-->
	<div>
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th >活动名称</th>
				<th width="80px">讲师</th>
				<th width="80px">类型</th>
				<th width="80px">类别</th>
				<th width="90px">课时时段</th>
				<th width="90px">报名时段</th>
				<th width="60px">状态</th>
				<th width="420px">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="studyActivity">
			<tr>
				<td title="${studyActivity.name}">
					<c:choose>  
				        <c:when test="${fn:length(studyActivity.name) >12 }">  
				           ${fn:substring(studyActivity.name, 0, 11)}…
				         </c:when>  
				        <c:otherwise>  
				           ${fn:substring(studyActivity.name, 0, 12)}
				         </c:otherwise>  
				     </c:choose>  
				</td>

				<td>
					${studyActivity.createBy.name}
				</td>
				<td>
					${fns:getDictLabel(studyActivity.actType, 'activity_type', '${studyActivity.actType}')}
				</td>
				<td>
					${fns:getDictLabel(studyActivity.actSort, 'activity_sort', '${studyActivity.sort}')}
				</td>
				<td>
					<fmt:formatDate value="${studyActivity.studyStartTime}" pattern="yyyy-MM-dd"/><br />~<fmt:formatDate value="${studyActivity.studyEndTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${studyActivity.applyStartTime}" pattern="yyyy-MM-dd"/><br />~<fmt:formatDate value="${studyActivity.applyEndTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
				${studyActivity.resourceStatus==null?"待审核":fns:getDictLabel(studyActivity.resourceStatus, 'approval_record_status', '')}
				</td>
				<td>
					<a href="${ctx}/teacherFormviewStudyActivity?id=${studyActivity.id}" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					<c:if test="${studyActivity.resourceStatus!='0'}">
					<a href="${pageContext.request.contextPath}/a/train/studyActivity/teacherFormBase?id=${studyActivity.id}&edit=1" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i>编辑信息</a>
    				<c:choose>
	   					<c:when test="${studyActivity.isImportUser !=1}">
							<a href="${ctx}/train/studyActivity/teacherFormBxXx?id=${studyActivity.id}&edit=1&type=1" class="btn btn-info btn-xs"  ><i class="fa fa-edit"></i>编辑必学</a>
	   					</c:when>
	   					<c:otherwise>
							<a href = "${ctx}/train/studyActivity/teacherFormImport?id=${studyActivity.id}&edit=1"  class="btn btn-info btn-xs" ><i class="fa fa-edit"></i>修改必学</a>			
	   					</c:otherwise>
	   				</c:choose>
	   				<c:choose>
	   					<c:when test="${studyActivity.isImportXxUser != 1}">
							<a href="${ctx}/train/studyActivity/teacherFormBxXx?id=${studyActivity.id}&edit=1&type=0" class="btn btn-info btn-xs"  ><i class="fa fa-edit"></i>编辑选学</a>
	   					</c:when>
	   					<c:otherwise>
							 <a href = "${ctx}/train/studyActivity/teacherFormImportXx?id=${studyActivity.id}&edit=1"  class="btn btn-info btn-xs"><i class="fa fa-edit"></i>修改选学</a>
	   					</c:otherwise>
	   				</c:choose>
	   				</c:if>
					<c:if test="${studyActivity.resourceStatus==1}">
						<a href="#" onclick="showFailInfo('${studyActivity.id}')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 失败详情</a>
					</c:if>
					<a href="${ctx}/train/studyActivity/teacherDelStudyActity?id=${studyActivity.id}" onclick="return confirmx('确认要删除该资源吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</div>
		<!-- 分页代码 -->
	<table:page page="${page}"></table:page>
</div>
<!--尾部 start-->
<div class="copyright">© 云帆互联 2014 汽车服务网 京ICP备14016447号</div>
<!--尾部 end-->

</body>
</html>
    