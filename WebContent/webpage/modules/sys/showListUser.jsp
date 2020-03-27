]<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/><base>
		<script type="text/javascript">

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
			    	 var body = top.layer.getChildFrame('body', index);
			    	 var strVal = "["
			    	  for(var i=1;i<=3;i++){
			    		  var obj = $('#postTr'+i,body);
			    		  var str = "{"
			    		  $("select",obj).each(function(){
			    			  str+="'"+$(this).attr('name')+"':'"+$(this).val()+"',";
			    		  });
			    		  str = str.substring(0, str.length -1)+"}";
			    		  strVal+=str+",";
			    	  }
			    	 strVal = strVal.substring(0, strVal.length-1)+"]";
			    	 $('#postInfoStr',body).val(strVal);
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
			        	// top.layer.close(index);//关闭对话框。
			        	  setTimeout(function(){top.layer.close(index)}, 100);//延时0.1秒，对应360 7.1版本bug
			         }
				  },
				  cancel: function(index){ 
			       }
			}); 	
			
		}
		//打开对话框(添加修改)
		function openRoleDialog(title,url,width,height,target){
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
			<shiro:hasPermission name="sys:user:add">
				<table:addRow url="${ctx}/sys/user/form" title="用户" width="1000px" height="600px"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:user:edit">
			    <table:editRow url="${ctx}/sys/user/form" id="contentTable"  title="用户" width="1000px;" height="600px" ></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:user:del">
				<table:delRow url="${ctx}/sys/user/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
 			<shiro:hasPermission name="sys:user:import"> 
 				<table:importExcel url="${ctx}/sys/user/import"></table:importExcel><!-- 导入按钮 --> 
 			</shiro:hasPermission> 
			<shiro:hasPermission name="sys:user:export">
	       		<table:exportExcel url="${ctx}/sys/user/export"></table:exportExcel><!-- 导出按钮 -->
	       </shiro:hasPermission>
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
		
			</div>
			</div>
			<div class="col-sm-12">
		<div class="pull-right" style="margin-top:20px;">
			<form:form id="searchForm" modelAttribute="user" action="${ctx}/sys/user/showListUser" method="post" class="form-inline">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
				<div class="form-group">
						<form:input path="name" htmlEscape="false" maxlength="50" class=" form-control input-sm" placeholder="用户名"/>
				 </div>	
				 <button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i>搜索</button>
			</form:form>
		</div>
	</div>
	</div>
	
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th><input type="checkbox" class="i-checks"></th>
				<th>序号</th>
				<th >用户名称</th>
				<th >真实姓名</th>
				<th >邮箱</th>
				<th >手机号码</th>
				<th >权限类型</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="user" varStatus="varStatus">
			<tr>
				<td> <input type="checkbox" id="${user.id}" class="i-checks"></td>
				<td>${varStatus.index+1}</td>
				<td>${user.loginName}</td>
				<td>${user.name}</td>
				<td>${user.email}</td>
				<td>${user.phone}</td>
				<td>${user.roleName}</td>
<%-- 				<td>${user.roleName}</td> --%>
				<td>
				<c:if test="${user.loginName!='admin'}">
					<shiro:hasPermission name="sys:user:edit">
						<a href="#" onclick="openDialog('修改用户', '${ctx}/sys/user/form?id=${user.id}','1000px', '600px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i>编辑</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="sys:user:edit">
						<c:if test="${user.id != loginUser.id}">
							<a href="#" onclick="openRoleDialog('设置权限', '${ctx}/sys/userOffice/form?userId=${user.id}','1000px', '600px')" class="btn btn-danger btn-xs"><i class="fa fa-trash"></i>设置权限</a>
						</c:if>
					</shiro:hasPermission>
					<shiro:hasPermission name="sys:user:view">
						<a href="#" onclick="openDialogView('查看用户', '${ctx}/sys/user/form?id=${user.id}','1000px', '600px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i>查看</a>
					</shiro:hasPermission>
					<c:if test="${user.role.enname == 'nx_teacher'}">
						<a href="${user.verifyUrl}" download class="btn btn-primary btn-xs" ><i class="fa fa-download"></i>下载认证资料</a>
					</c:if>
<%-- 					<shiro:hasPermission name="sys:user:del"> --%>
<%-- 						<a href="${ctx}/sys/user/delete?id=${user.id}" onclick="return confirmx('确认要删除该用户吗？', this.href)" class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a> --%>
<%-- 					</shiro:hasPermission> --%>
				</c:if>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<table:page page="${page}"></table:page>
		<br/>
	<br/>
	</div>
	</div>
	</div>

</body>
</html>