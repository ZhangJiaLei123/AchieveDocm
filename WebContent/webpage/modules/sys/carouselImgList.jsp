<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>轮播图管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		});


		//打开对话框(添加修改)
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
			    	try{
			    	 var body = top.layer.getChildFrame('body', index);
			          var inputForm = body.find('#inputForm');
			         var top_iframe;
			         if(target){
			        	 top_iframe = target;//如果指定了iframe，则在改frame中跳转
			         }else{
			        	 top_iframe = top.getActiveTab().attr("name");//获取当前active的tab的iframe 
			         }
			         inputForm.attr("target",top_iframe);//表单提交成功后，从服务器返回的url在当前tab中展示 */
			    	}catch(e){
			    		layer.closeAll();
			    	}
			         var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
			        if(iframeWin.contentWindow.doSubmit() ){
			        	// top.layer.close(index);//关闭对话框。
			        	  setTimeout(function(){top.layer.close(index)}, 100);//延时0.1秒，对应360 7.1版本bug
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
			<shiro:hasPermission name="sys:carouselImg:add">
				<table:addRow url="${ctx}/sys/carouselImg/form" title="轮播图" label="新建"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<%-- <shiro:hasPermission name="sys:carouselImg:edit">
			    <table:editRow url="${ctx}/sys/carouselImg/form" title="轮播图" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission> --%>
			<shiro:hasPermission name="sys:carouselImg:del">
				<table:delRow url="${ctx}/sys/carouselImg/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
		
			</div>
		<div class="pull-right">
			<form:form id="searchForm" modelAttribute="carouselImg" action="${ctx}/sys/carouselImg/" method="post" class="form-inline">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
				<div class="form-group">
						<form:input path="titl" htmlEscape="false" placeholder="请输入标题名称" maxlength="255"  class=" form-control input-sm"/>
				 </div>	
				 <button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 搜索</button>
			</form:form>
			<!--  -->
		</div>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<th  class="sort-column sort">轮播图序号</th>
				<th  class="sort-column titl">标题</th>
				<th  class="sort-column url">链接地址</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="carouselImg">
			<tr>
				<td> <input type="checkbox" id="${carouselImg.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看轮播图', '${ctx}/sys/carouselImg/form?id=${carouselImg.id}','800px', '500px')">
					${carouselImg.sort}
				</a></td>
				<td>
					${carouselImg.titl}
				</td>
				<td>
					${carouselImg.url}
				</td>
				<td>
					<shiro:hasPermission name="sys:carouselImg:edit">
    					<a href="#" onclick="openDialog('修改轮播图', '${ctx}/sys/carouselImg/form?id=${carouselImg.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 编辑</a>
    				</shiro:hasPermission>
					<shiro:hasPermission name="sys:carouselImg:view">
						<a href="#" onclick="openDialogView('查看轮播图', '${ctx}/sys/carouselImg/form?id=${carouselImg.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
    				<%-- <shiro:hasPermission name="sys:carouselImg:del">
						<a href="${ctx}/sys/carouselImg/delete?id=${carouselImg.id}" onclick="return confirmx('确认要删除该轮播图吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission> --%>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
		<!-- 分页代码 -->
	<table:page page="${page}"></table:page>
	<br/>
	<br/>
	</div>
	</div>
</div>
</body>
</html>