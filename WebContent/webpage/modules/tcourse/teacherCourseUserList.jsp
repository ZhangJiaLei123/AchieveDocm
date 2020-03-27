<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>学员展示</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function deleteStudyUser(id){
			 layer.confirm('确定要删除吗？', {
					btn: ['确定','取消'] //按钮
				}, function(){
					$.ajax({
				        async: false,
				        url: "${ctx}/teacherDelStuActivityUser",
				        data:{id:id},
				        dataType: "json",
				        success: function (data) {
				    	   if(data){
				    		   layer.confirm('删除成功!', {
				    				btn: ['确定'] //按钮
				    			}, function(){
				    				window.location.reload();
				    			})
				    	   }else{
				    		   layer.confirm('删除失败!', {
				    				btn: ['确定'] //按钮
				    			}, function(){
				    				window.location.reload();
				    			})
				    	   }
				        }
					});
				})
		}
	</script>
</head>
<body >
		<div class="ibox">
			<div class="ibox-content">
			<div class="col-sm-12">
			<div class="pull-right">
				<div class="form-group">
				<form:form id="searchForm" modelAttribute="user" action="${ctx}/course/tcourseInfo/teacherCourseUserList" method="post" class="form-inline">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<input id="courseId" name="courseId" type="hidden" value="${courseId}"/>
				<div class="form-group">
					<table>
						<tr>
							<td>
								<form:input path="name" htmlEscape="false" maxlength="50" class=" form-control input-sm" placeholder="请输入报名人姓名"/>
							</td>
							<td>
							<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i>搜索</button>
							</td>
						</tr>
					</table>
				 </div>	
				 
			</form:form>
			</div>
			</div>
			</div>
			
				<table id="contentTable"
					class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
					<thead>
						<tr>
							<th>序号</th>
							<th>所属机构</th>
							<th>人员姓名</th>
							<th>学习次数</th>
							<th>学习时长</th>
						</tr>
					</thead>
					<tbody>
					
						<c:forEach items="${page.list}" var="userObj" varStatus="status">
							<tr>
				 				<td>${status.index+1 }</td>
								<td>${userObj.officeName}</td>
								<td>${userObj.name}</td>
								<td>${userObj.xxCount }</td>
								<td>${userObj.xxTime }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>

				<!-- 分页代码 -->
				<table:page page="${page}"></table:page>
			</div>
		</div>
</body>
</html>