<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>资料库</title>
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
	$($("a",$("#topA"))[1]).attr("class","active");
</script>
<!--头部 end-->
<sys:message content="${message}"/>
<!--身体 start-->
 <div class="row" style="min-height: 510px;padding-bottom:140px;">
				<div  style="height: 90px;  border-bottom: 1px solid #d8d8d8;padding-top: 30px;">
					<form:form id="searchForm" modelAttribute="mesanInfo" action="${pageContext.request.contextPath}/a/findMesanInfo" method="post" class="form-inline">
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
					</tr>
				</table>
				</div>
				</form:form>
			</div>
				<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<tbody>
		<c:forEach items="${page.list}" var="mesanInfo" varStatus="status"> 
			<tr>
				<td title="${mesanInfo.name}">
					<c:choose>  
				        <c:when test="${fn:length(mesanInfo.name) >50 }">  
				           ${fn:substring(mesanInfo.name, 0, 49)}…
				         </c:when>  
				        <c:otherwise>  
				           ${fn:substring(mesanInfo.name, 0, 50)}
				         </c:otherwise>  
				     </c:choose>  
				</td>
				<td>
					<fmt:formatDate value="${mesanInfo.createDate}" pattern="yyyy-MM-dd HH:mm" type="both"/>
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
<script type="text/javascript">
	function get(mesanInfoId){
		var url = "${ctx}/resourcedownload/tResourceDownload/add";
	    $.ajax({
	        type: "post",
	        url: url,
	        data: {"mesanInfoId":mesanInfoId},
	        cache: false,
	        async : false,
	        dataType: "json",
	        success: function (data ,textStatus, jqXHR)
	        {
	            if("00000"==data.code){
	               $("#"+mesanInfoId+"_apply").attr('href','javascript:void(0)');
	               $("#"+mesanInfoId+"_apply").attr('class','btn btn-warning btn-xs');
	               $("#"+mesanInfoId+"_apply").text('待审核');
	               return true;
	            }else{
	                alert(data.errorMsg);
	                return false;
	            }
	        },
	        error:function (XMLHttpRequest, textStatus, errorThrown) {
	            alert("请求失败!");
	        }
	     });
	}
</script>
</body>
</html>
    