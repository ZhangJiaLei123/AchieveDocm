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
<style> 
body{text-align:center} 
</style> 
<body>
<input type="hidden" id="showResourceId">
<!--头部 start-->
<jsp:include page="/include/head.jsp"></jsp:include>
<script type="text/javascript">
	$($("a",$("#topA"))[0]).attr("class","active");
</script>
<!--头部 end-->
<!--身体 start-->
<div class="row" style="min-height: 700px;padding-bottom:140px;">
<div  style="height:90px;border-bottom: 0px solid #d8d8d8;padding-top:30px;">
		<form:form id="searchForm" modelAttribute="mesanInfo" action="${pageContext.request.contextPath}/a/searchMesanInfo" method="post" class="form-inline">
		<div align="center" style="margin:0 auto;">
		<table >
		<tr style="line-height: 30px;">
			<td width="200px;" height="520px;">
				<input type="text" name="keywords" class="form-control input-sm" style="width:300px;height:40px;border:1px;border-style: solid;" placeholder="请输入搜索内容">
				<!-- form:input path="name" htmlEscape="false" maxlength="300" class="form-control input-sm" size="30" placeholder="请输入搜索内容"/> -->
			</td>
			<td width="5px;"></td>
			<td>
				<input type="submit" class="btn btn-primary  btn-outline btn-sm" style="width:85px;height:40px;font-size:16px;" value="搜索资料">
			</td>
			<td width="5px;"></td>
		</tr>
		</table>
	</div>
	</form:form>
</div>
</div>
<!--身体 end-->
<!--尾部 start-->
<div class="copyright">© 云帆互联 2014 汽车服务网 京ICP备14016447号</div>
<!--尾部 end-->
</body>
</html>
    