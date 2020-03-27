<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>活动报名审核管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
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
			    		 var userActivityId = $("#userActivityId",body).val();
			    		 var ids = "";
			    		 $("input[type='checkbox'][name='chk']:checked",body).each(function (){
			    			 ids+=$(this).val()+",";
			    		 });
			    		 $.ajax({
						        async: false,
						        url: "${ctx}/teacherAddStuActivityUser",
						        data:{ids:ids,userActivityId:userActivityId},
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
				<table id="contentTable"
					class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
					<thead>
						<tr>
							<th>序号</th>
							<th>所属机构</th>
							<th>人员姓名</th>
						</tr>
					</thead>
					<tbody>
					
						<c:forEach items="${page.list}" var="userObj" varStatus="status">
							<tr>
				 				<td>${status.index+1 }</td>
								<td>${userObj.officeName}</td>
								<td>${userObj.name}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>

				<!-- 分页代码 -->
				<table:page page="${page}"></table:page>
				<br /> <br />
			</div>
		</div>
</body>
</html>