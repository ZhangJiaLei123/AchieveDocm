<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>资源目录</title>
	<meta name="decorator" content="default"/>
	<%@include file="/webpage/include/treetable.jsp" %>
	<script type="text/javascript">
	
		$(document).ready(function() {
			var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
			var data = ${fns:toJson(list)}, rootId = "0";
			addRow("#treeTableList", tpl, data, rootId, true);
			$("#treeTable").treeTable({expandLevel : 5});
		});
	
		function addRow(list, tpl, data, pid, root){
			var rootTpl = $("#treeRootTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
			for (var i=0; i<data.length; i++){
				var row = data[i];
				if ((${fns:jsGetVal('row.parentId')}) == pid){
					if(pid=="0"){
						$(list).append(Mustache.render(rootTpl, {dict: {},pid:(root?0:pid), row: row}));
					}else{
						$(list).append(Mustache.render(tpl, {dict: {},pid:(root?0:pid), row: row}));
					}
					addRow(list, tpl, data, row.id);
				}
			}
		}

		function refresh(){//刷新
			window.location="${ctx}/resource/mesanDir/treemesanDir";
		}
		
		function openDialog(title,url,width,height,target){
			
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
			    btn: ['确定', '关闭'],
			    yes: function(index, layero){
			    	 var body = top.layer.getChildFrame('body', index);
			         var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
			         var inputForm = body.find('#inputForm');
			         var top_iframe;
			         if(target){
			        	 top_iframe = target;//如果指定了iframe，则在改frame中跳转
			         }else{
			        	 top_iframe = top.getActiveTab().attr("name");//获取当前active的tab的iframe 
			         }
			         inputForm.attr("target",top_iframe);//表单提交成功后，从服务器返回的url在当前tab中展示
			         
			        if(iframeWin.contentWindow.doSubmit() ){
			        	top.layer.close(index);//延时0.1秒，对应360 7.1版本bug
			         }
					
				  },
				  cancel: function(index){ 
			       }
			}); 	
			
		}
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	
    <div class="ibox-content">
	<sys:message content="${message}"/>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="resource:resourceDir:add">
				<button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="left" onclick="openDialog('添加资料目录', '${ctx}/resource/mesanDir/form','800px', '500px')" title="添加资源目录"><i class="fa fa-plus"></i> 添加</button>
			</shiro:hasPermission>
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="refresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
		
		</div>
	</div>
	</div>
	
	<table id="treeTable"  class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead><tr><th>目录名称 </th><th>排序</th><th>操作</th></tr></thead>
		<tbody id="treeTableList"></tbody>
	</table>
	<br/>
	</div>
	</div>
</div>
	<script type="text/template" id="treeTableTpl">
		<tr id="{{row.id}}" pId="{{pid}}">
			<td><a  href="#" onclick="openDialogView('查看资料分类', '${ctx}/resource/mesanDir/form?id={{row.id}}','800px', '500px')">{{row.name}}</a></td>
			<td>{{row.sort}}</td>
			<td>
					<shiro:hasPermission name="resource:mesanDir:view">
						<a href="#" onclick="openDialogView('查看资料目录', '${ctx}/resource/mesanDir/form?id={{row.id}}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="resource:mesanDir:edit">
    					<a href="#" onclick="openDialog('修改资料目录', '${ctx}/resource/mesanDir/form?id={{row.id}}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<c:if test="${mesanDir.parent.id != 'root' && mesanDir.parent.id != ''}">
	    				<shiro:hasPermission name="resource:mesanDir:del">
							<a href="${ctx}/resource/mesanDir/delete?id={{row.id}}" onclick="return confirmx('确认要删除该资源目录吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
						</shiro:hasPermission>
					</c:if>
			</td>
		</tr>
	</script>
	<script type="text/template" id="treeRootTableTpl">
		<tr id="{{row.id}}" pId="{{pid}}">
			<td><a  href="#" onclick="openDialogView('查看资料分类', '${ctx}/resource/mesanDir/form?id={{row.id}}','800px', '500px')">{{row.name}}</a></td>
			<td>{{row.sort}}</td>
			<td>
			</td>
		</tr>
	</script>
</body>
</html>