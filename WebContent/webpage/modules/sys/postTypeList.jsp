<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>岗位分类管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			if('${message}'!=''){
				 window.setTimeout(function () { $.jBox.tip('${message}', 'success'); }, 300);
			}
		});
		//打开对话框(添加修改)
		function openDialogPage(title,url,width,height,target){
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
			        if(iframeWin.contentWindow.doSubmit()){
			        	 top.layer.close(index);
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
	<!-- <div class="ibox-title">
		<h5>岗位分类列表 </h5>
		<div class="ibox-tools">
		</div>
	</div>
     -->
    <div class="ibox-content">
<%-- 	<sys:message content="${message}"/> --%>
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="sys:postType:add">
				<a href="#" onclick="openDialogPage('添加岗位分类', '${ctx}/sys/postType/form?postinfoId=${postLevel.postinfoId}','450px', '200px')" class="btn btn-white btn-sm"><i class="fa fa-plus"></i>新增</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:postType:del">
				<table:delRow url="${ctx}/sys/postType/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
			</div>
		<div class="pull-right">
			<form:form id="searchForm" modelAttribute="postType" action="${ctx}/sys/postType/" method="post" class="form-inline">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
				<div class="form-group">
					<form:input path="name" htmlEscape="false" maxlength="200"  class=" form-control input-sm" placeholder="请输入岗位分类"/>
				 </div>	
				 <button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 搜索</button>
				 
			</form:form>
		</div>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<th>序号</th>
				<th  class="sort-column name">岗位分类</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="postType" varStatus="status">
			<tr>
				<td> <input type="checkbox" id="${postType.id}" class="i-checks"></td>
				<td>${status.index +1}</td>
				<td><a  href="#" onclick="openDialogView('查看岗位分类', '${ctx}/sys/postType/form?id=${postType.id}','800px', '500px')">
					${postType.name}
				</a></td>
				<td>
					<shiro:hasPermission name="sys:postType:edit">
    					<a href="#" onclick="openDialogPage('修改岗位分类', '${ctx}/sys/postType/form?id=${postType.id}','450px', '200px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i>编辑</a>
    				</shiro:hasPermission>
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