<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>课程管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
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
</head>
<body>
	<!--头部 start-->
	<jsp:include page="/include/head.jsp"></jsp:include>
	<script type="text/javascript">
		$($("a",$("#topA"))[4]).attr("class","active");
	</script>
	<!--头部 end-->
		<div class="row" style="min-height: 510px;padding-bottom:140px;">
		<div class="nav_menu" >
				<a  href="${pageContext.request.contextPath}/a/course/tcourseInfo/list">课程库</a>
				<a class="active"  href="${pageContext.request.contextPath}/a/course/tcourseInfo/findMyCourseInfo">我的课程</a>
				<a href="${pageContext.request.contextPath}/a/course/courseInfo/teacherFormBase/" >新建课程</a>
				<form:form id="searchForm" modelAttribute="courseInfo" action="${pageContext.request.contextPath}/a/course/tcourseInfo/findMyCourseInfo" method="post" class="form-inline">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<div style="float: right">
					<span><input type="text" name="couName" value="${courseInfo.couName}" class="input_style" placeholder="课程名称"></span>
					<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i>搜索</button>
				</div>
				</form:form>
			</div>
	<sys:message content="${message}"/>
	<!-- 工具栏 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th  width="120px">课程名称</th>
				<th   width="120px">资源名称</th>
				<th   width="120px">报名时间</th>
				<th   width="120px">学习时间</th>
				<th  width="50px">状态</th>
				<th width="420px;">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="courseInfo">
			<tr>
				<td title="${courseInfo.couName}">
						<c:choose>  
				        <c:when test="${fn:length(courseInfo.couName) >12 }">  
				           ${fn:substring(courseInfo.couName, 0, 11)}…
				         </c:when>  
				        <c:otherwise>  
				           ${fn:substring(courseInfo.couName, 0, 12)}
				         </c:otherwise>  
				     </c:choose>  
				</a>
				<td title="${courseInfo.resResourceName}">
						<c:choose>  
				        <c:when test="${fn:length(courseInfo.resResourceName) >12 }">  
				           ${fn:substring(courseInfo.resResourceName, 0, 11)}…
				         </c:when>  
				        <c:otherwise>  
				           ${fn:substring(courseInfo.resResourceName, 0, 12)}
				         </c:otherwise>  
				     </c:choose>  
				</td>
				<td>
					<fmt:formatDate value="${courseInfo.regStartTime}" pattern="yyyy-MM-dd"/><br />~<fmt:formatDate value="${courseInfo.regEndTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${courseInfo.stuStartTime}" pattern="yyyy-MM-dd"/><br />~<fmt:formatDate value="${courseInfo.stuEndTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${courseInfo.resourceStatus==null?"待审核":fns:getDictLabel(courseInfo.resourceStatus, 'approval_record_status', '')}
				</td>
				<td>
					<a href="${ctx}/course/tcourseInfo/teacherMyCourseView?id=${courseInfo.id}" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					<c:if test="${courseInfo.resourceStatus!='0'}">
					<a href="${ctx}/course/courseInfo/teacherFormBase?id=${courseInfo.id}&edit=1" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i>编辑信息</a>
					<c:choose>
	   					<c:when test="${courseInfo.isImportUser !=1}">
	   							<a href="${ctx}/course/courseInfo/teacherFormBxXx?id=${courseInfo.id}&edit=1&type=1" class="btn btn-info btn-xs"  ><i class="fa fa-edit"></i>编辑必学</a>
	   					</c:when>
	   					<c:otherwise>
		   					<a href = "${ctx}/course/courseInfo/teacherFormImport?id=${courseInfo.id}&edit=1"  class="btn btn-info btn-xs" ><i class="fa fa-edit"></i>修改必学</a>	
	   					</c:otherwise>
	   				</c:choose>
	   				<c:choose>
	   					<c:when test="${courseInfo.isImportXxUser != 1}">
	   						<a href="${ctx}/course/courseInfo/teacherFormBxXx?id=${courseInfo.id}&edit=1&type=0" class="btn btn-info btn-xs"  ><i class="fa fa-edit"></i>编辑选学</a>
	   					</c:when>
	   					<c:otherwise>
		    				<a href = "${ctx}/course/courseInfo/teacherFormImportXx?id=${courseInfo.id}&edit=1"  class="btn btn-info btn-xs"><i class="fa fa-edit"></i>修改选学</a>
	   					</c:otherwise>
	   				</c:choose>
	   				</c:if>
					<c:if test="${courseInfo.resourceStatus==1}">
						<a href="#" onclick="showFailInfo('${courseInfo.id}')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 失败详情</a>
					</c:if>
					<a href="${ctx}/course/courseInfo/teacherDelCourseInfo?id=${courseInfo.id}" onclick="return confirmx('确认要删除该资源吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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