<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>课程学员管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function deleteStudyUser(id){
			 layer.confirm('确定要删除吗？', {
					btn: ['确定','取消'] //按钮
				}, function(){
					$.ajax({
				        async: false,
				        url: "${ctx}/course/tcourseInfo/teacherDelCourseUser",
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
		//打开对话框(添加修改)
		function openAddUserDialog(title,url,width,height,target){
			if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端，就使用自适应大小弹窗
				width='auto';
				height='auto';
			}else{//如果是PC端，根据用户设置的width和height显示。
			}
			top.layer.open({
			    type: 2,  
			    area: [width, height],
			    title: title,
		        maxmin: true, //开启最大化最小化按钮
			    content: url ,
			    btn: ['提交'],
			    yes: function(index, layero){
			    	 var body = top.layer.getChildFrame('body', index);
			    	 var countChk = $("input[type='checkbox'][name='chk']:checked",body).length;
			    	 if(countChk<1){
			    		 top.layer.alert("请至少选择一个学员！");
			    		 return;
			    	 }else{
			    		 var courserId = $("#userActivityId",body).val();
			    		 var ids = "";
			    		 $("input[type='checkbox'][name='chk']:checked",body).each(function (){
			    			 ids+=$(this).val()+",";
			    		 });
			    		 $.ajax({
						        async: false,
						        url: "${ctx}/course/tcourseInfo/teacherAddCourseUserInManager",
						        data:{ids:ids,courserId:courserId},
						        dataType: "json",
						        success: function (data) {
						        	top.layer.close(index);//延时0.1秒，对应360 7.1版本bug
						    	   if(data){
						    		   layer.confirm('添加成功!', {
						    				btn: ['确定'] //按钮
						    			}, function(){
						    				
						    				window.location.reload();
						    			})
						    	   }else{
						    		   layer.confirm('添加失败!', {
						    				btn: ['确定'] //按钮
						    			}, function(){
						    				window.location.reload();
						    			})
						    	   }
						        }
							});
			    	 }
				  },
				  cancel: function(index){ 
			       }
			}); 	
			
		}
	</script>
</head>
<body >
		<div class="ibox">
			<div class="ibox-content">
				<form:form id="searchForm" modelAttribute="user" action="${ctx}/course/tcourseInfo/showListCourRegUser" method="post" class="form-inline">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<input id="courseId" name="courseId" type="hidden" value="${courseId}"/>
				<a href="#" onclick="openAddUserDialog('新增', '${ctx}/course/tcourseInfo/findAddCourseUserList?userActivityId=${courseId}','700px', '400px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 新增</a>
				<table id="contentTable"
					class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
					<thead>
						<tr>
							<th>序号</th>
							<th>所属机构</th>
							<th>报名人</th>
							<th>学习次数</th>
							<th>学习时长</th>
							<th>是否通过</th>
							<th>操作</th>
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
								<td>
									<c:if test="${courserInfo.byStandard eq '1'}">
										不统计
									</c:if>
									<c:if test="${courserInfo.byStandard eq '2'}">
										<c:if test="${userObj.xxCount >0 }">
											通过
										</c:if>
										<c:if test="${userObj.xxCount == 0 }">
											不通过
										</c:if>
									</c:if>
								</td>
								<td><a href="#" onclick="deleteStudyUser('${userObj.id}')"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>

				<!-- 分页代码 -->
				<table:page page="${page}"></table:page>
				<br /> <br />
				</form:form>
			</div>
		</div>
</body>
</html>