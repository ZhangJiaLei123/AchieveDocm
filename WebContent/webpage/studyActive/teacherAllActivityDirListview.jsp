<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>活动章节管理</title>
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
			    btn: ['提交','关闭'],
			    yes: function(index, layero){
			    	 var body = top.layer.getChildFrame('body', index);
			    	 var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
			         var top_iframe;
			    	 var id = $("#id",body).val();
			    	 var parentId = $("#parentId",body).val();
			    	 var activityId = $("#activityId",body).val();
			    	 var dirName = $("#dirName",body).val();
			    	 $.ajax({
			    		    async: false,
			    		    url: "${ctx}/train/activityDir/save",
			    		    type:"post",
			    		    data:{id:id,parentId:parentId,activityId:activityId,dirName:dirName},
			    		    success: function (data) {
			    		    	if(data){
			    		    		 top.layer.close(index);//延时0.1秒，对应360 7.1版本bug
			    		    		 alert(data);
			    		    		 window.location.reload();
			    		    	}
			    			    
			    		    }
			    		});
				 },
				  cancel: function(index){ 
					  
			      }
			}); 	
			
		}
		//分配资源
		function openDirDialog(title,url,width,height,target){
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
			    btn: ['提交','关闭'],
			    yes: function(index, layero){
			    	 var body = top.layer.getChildFrame('body', index);
			    	 var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
			         var top_iframe;
			         var bodyIfram = $("iframe",body);
			         var strIds = "";
			         var lengIds = 0;
			        
			         bodyIfram.contents().find('input[name="chk"]:checked').each(function(){
			        	 strIds+=this.value+",";
			        	 lengIds++;
			         });
			         if(lengIds<=0){
			        	 top.layer.alert("请至少选择一条需要添加的资源。");
			        	 return ;
			         }
			         var type =  bodyIfram.contents().find('#typeDis').val();
			         var activityId = $("#activityId",body).val();
			         var activityDirId = $("#activityDirId",body).val();
			    	 $.ajax({
			    		    async: false,
			    		    url: "${ctx}/train/distribuTemp/save",
			    		    type:"post",
			    		    data:{strIds:strIds,type:type,activityDirId:activityDirId,activityId:activityId},
			    		    success: function (data) {
			    		    	if(data){
			    		    		 alert("添加成功！");
			    		    		 top.layer.close(index);
			    		    		 location.reload();
			    		    	}
			    			    
			    		    }
			    		}); 
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
<body >
	<div class="wrapper wrapper-content">
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<tbody>
		<!-- 章  -->
		<c:forEach items="${resturnList}" var="listActivityDir">
		<c:forEach items="${listActivityDir['parent']}" var="activityDir">
			<tr>
				<td>
					<span style="border:1px solid #D5D5D5;padding: 3px;width: 100px;text-align: center;">${activityDir.dirName}</span>
					<span>&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="openDirDialogList('查看资源列表','${ctx}/train/distribuTemp/viewResourList?activityId=${activityDir.activityId}&activityDirId=${activityDir.id}','600px','400px')">${activityDir.coutRes}</a>个资源</span>
				</td>
			</tr>
		</c:forEach>
			<!-- 节  -->
		   <c:forEach items="${listActivityDir['son']}" var="activityDir">
			<tr>
				<td style="padding-left: 50px;">
					<span style="border:1px solid #D5D5D5;padding: 3px;width: 100px;text-align: center;">${activityDir.dirName}</span>
					<span>&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="openDirDialogList('查看资源列表','${ctx}/train/distribuTemp/viewResourList?activityId=${activityDir.activityId}&activityDirId=${activityDir.id}','600px','400px')">${activityDir.coutRes}</a>个资源</span>
				</td>
				
			</tr>
		</c:forEach>
		
		</c:forEach>
		</tbody>
	</table>
	
		<!-- 分页代码 -->
	<table:page page="${page}"></table:page>
	<br/>
</div>
</body>
</html>