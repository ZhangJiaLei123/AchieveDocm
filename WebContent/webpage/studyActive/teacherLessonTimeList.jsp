<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>授课时间管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		});
		//打开对话框(添加修改)
		function openMyDialog(title,url,width,height,target){
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
			    btn: ['确定','关闭'],
			    yes: function(index, layero){
			    	 var body = top.layer.getChildFrame('body', index);
			         var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
			         if(iframeWin.contentWindow.doSubmit()){
			        	 var id = $("#id",body).val();
						 var studyId = $("#studyId",body).val();
						 var activityDirIds = $("#activityDirIds",body).val();
						 var lessonStartTime = $("#lessonStartTime",body).val();
						 var lessonEndTime = $("#lessonEndTime",body).val();
				    	 $.ajax({
				    		    async: false,
				    		    url: "${ctx}/train/lessonTime/saveLessonTimeAjax",
				    		    type:"post",
				    		    data:{id:id,studyId:studyId,activityDirIds:activityDirIds,lessonStartTime:lessonStartTime,lessonEndTime:lessonEndTime},
				    		    success: function (data) {
				    		    	if("1"==data){
				    		    		 top.layer.close(index);//延时0.1秒，对应360 7.1版本bug
				    		    		 alert("添加成功！");
				    		    		 window.location.reload();
				    		    	}else if("2"==data){
				    		    		top.layer.close(index);//延时0.1秒，对应360 7.1版本bug
				    		    		 alert("修改成功！");
				    		    		 window.location.reload();
				    		    	}
				    			    
				    		    }
				    		});
			         }
			    	
				 },
				  cancel: function(index){ 
					  
			      }
			}); 	
		}
		//查看章节资源
		function openDirDialogList(title,url,width,height,target){
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
			    btn: ['返回'],
			    yes: function(index, layero){
			    	 top.layer.close(index);
			    	 location.reload();

				 },
				  cancel: function(index){ 
					 // location.reload();

			      }
			}); 	
			
		}
	</script>
</head>
<body class="gray-bg" >
	<div class="wrapper wrapper-content" >
	<div class="ibox" >
	
    <div class="ibox-content" style="float: left">
	<sys:message content="${message}"/>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
				<button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="left" onclick="openMyDialog('新增授课时间', '${ctx}/train/lessonTime/formdir?studyId=${lessonTime.studyId}','800px', '500px')"  title="添加"><i class="fa fa-plus"></i> 添加</button>
			</div>
	</div>
	</div>
	<c:forEach items="${page.list}" var="lessionT">
		<div style="border:1px solid #000;width: 400px;float: left;margin: 10px;padding: 10px;">
				<fmt:formatDate value="${lessionT.lessonStartTime}" pattern="yyyy-MM-dd HH:mm:ss"/>~<fmt:formatDate value="${lessionT.lessonEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				<span>
					<a href="#" onclick="openMyDialog('修改授课时间', '${ctx}/train/lessonTime/formdir?studyId=${lessionT.studyId}&id=${lessionT.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
					&nbsp;&nbsp;<a href="javascript:void(0)"  onclick="openDirDialogList('参与情况','${ctx}/train/distribuTemp/viewCanYuQk?lessionTimeId=${lessionT.id}&activityId=${lessonTime.studyId}','700px','400px')"  class="btn btn-success btn-xs" >参与情况</a></span>
				</span>
					<table id="contentTable"
						 style="width: 300px;">
							<!-- 章  -->
							<c:forEach items="${lessionT.listActivityDir}" var="activityDir">
									<tr height="30px;">
										<td>
											<span><a onclick="tabChange('${activityDir.id}');">${activityDir.dirName}</a></span>
											<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<a href="javascript:void(0)"  onclick="openDirDialogList('查看资源列表','${ctx}/train/distribuTemp/viewResourList?activityId=${activityDir.activityId}&activityDirId=${activityDir.id}','600px','400px')">${activityDir.coutRes}</a>个资源</span>
											
										</td>
									</tr>
								<!-- 节  -->
								<c:forEach items="${activityDir.listActivityDir}" var="activityDirSon">
									<tr height="30px;">
										<td style="padding-left: 50px;">
											<span><a onclick="javascript:void(0)">${activityDirSon.dirName}</a></span>
											<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<a href="javascript:void(0)" onclick="openDirDialogList('查看资源列表','${ctx}/train/distribuTemp/viewResourList?activityId=${activityDirSon.activityId}&activityDirId=${activityDirSon.id}','600px','400px')">${activityDirSon.coutRes}</a>个资源</span>
										</td>
									</tr>
								</c:forEach>
							</c:forEach>
					</table>
		</div>
	
	</c:forEach>
	</div>
	</div>
</div>
</body>
</html>