<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>我的资源</title>
<meta charset="utf-8">
<meta name="decorator" content="default"/>
</head>
<script type="text/javascript">
</script>
<body>
<input type="hidden" id="showResourceId">
<!--头部 start-->
<jsp:include page="/include/head.jsp"></jsp:include>
<script type="text/javascript">
	$($("a",$("#topA"))[2]).attr("class","active");

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
		         var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
		         var inputForm = body.find('#inputForm');
		         var top_iframe;
		        if(iframeWin.contentWindow.doSubmit() ){
		        	 top.layer.close(index);//关闭对话框。
		        	//window.location.reload();
		         }
				
			  },
			  cancel: function(index){ 
		       }
		}); 	
		
	}
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
<!--头部 end-->
<sys:message content="${message}"/>
<!--身体 start-->
			<div class="row" style="min-height: 510px;padding-bottom:140px;">
				<div  style="height: 90px;  border-bottom: 1px solid #d8d8d8;padding-top: 30px;">
					<!--<a style="margin-right: 40px;"  href="${pageContext.request.contextPath}/a/findMesanInfo">资料库</a>-->
					<a style="color: #18a589;margin-right: 40px;" href="${pageContext.request.contextPath}/a/findMyMesanInfo">我的资料</a>
					<form:form id="searchForm" modelAttribute="mesanInfo" action="${pageContext.request.contextPath}/a/findMyMesanInfo" method="post" class="form-inline">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div style="float:right">
					<table >
					<tr style="line-height: 30px;">
						<td width="150px;" height="20px;">
							<form:input path="name" htmlEscape="false" maxlength="300"  class=" form-control input-sm" placeholder="请输入资料名称"/>
						</td>
						<td width="5px;"></td>
						<td>
							 <button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i>搜索</button>
						</td>
						<td width="5px;"></td>
						<td>
						<a href="#" onclick="openDialog('添加文档', '${ctx}/resource/mesanInfo/teacherMesanInfoForm','800px', '500px')" class="btn btn-primary btn-rounded btn-outline btn-sm" >上传</a>
						</td>
					</tr>
				</table>
				</div>
				</form:form>
			</div>
				<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th >资料名称</th>
				<th >资料类别</th>
				<th >上传时间</th>
				<th width="300px;">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="mesanInfo" varStatus="status"> 
			<tr>
				<td title="${mesanInfo.name}">
					<c:choose>  
				        <c:when test="${fn:length(mesanInfo.name) >30 }">  
				           ${fn:substring(mesanInfo.name, 0, 29)}…
				         </c:when>  
				        <c:otherwise>  
				           ${fn:substring(mesanInfo.name, 0, 30)}
				         </c:otherwise>  
				     </c:choose>  
				</td>
				<td>${mesanInfo.mesanTypeName}</td>
				<td>
					<fmt:formatDate value="${mesanInfo.createDate}" pattern="yyyy-MM-dd HH:mm" type="both"/>
				</td>
				<td >
					<a href="#" onclick="openDialogView('查看资料信息', '${ctx}/resource/mesanInfo/teacherViewForm?id=${mesanInfo.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					<a target="_blank" href="${ctx}/resource/mesanInfo/downloadMaterial?mesanInfoId=${mesanInfo.id}" class="btn btn-info btn-xs"> 下载</a>
    				<c:if test="${mesanInfo.approvalStatus!='0'}">
    					<a href="#" onclick="openDialog('编辑资料信息', '${ctx}/resource/mesanInfo/teacherMesanInfoForm?id=${mesanInfo.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 编辑</a>
					</c:if>
					<!-- <a href="${ctx}/resource/mesanInfo/teacherDelMesanInfo?id=${mesanInfo.id}" onclick="return confirmx('确认要删除该资料信息吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a> -->
					
					<c:if test="${mesanInfo.approvalStatus==1}">
						<a href="#" onclick="showFailInfo('${mesanInfo.id}')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 失败详情</a>
					</c:if>
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
    