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
	function search(){
		var keywords = $("#keywords").val() 
		alert(keywords);
	}
</script>
<!--头部 end-->
<!--身体 start-->
<div class="row" style="min-height: 700px;padding-bottom:140px;">
<div  style="height:90px;border-bottom: 0px solid #d8d8d8;padding-top:30px;">
		<form:form id="searchForm" modelAttribute="mesanInfo" action="${pageContext.request.contextPath}/a/searchMesanInfo" method="post" class="form-inline">
		<div>
		<table >
		<tr style="line-height: 30px;">
			<td width="200px;" height="10px;">
				<input id="keywords" name="keywords" type="text" value="${keywords}" class="form-control input-sm" style="width:260px;height:35px;border:1px;border-style: solid;" placeholder="请输入搜索内容">
			</td>
			<td width="5px;"></td>
			<td>
				<input type="submit" class="btn btn-primary  btn-outline btn-sm" style="width:55px;height:35px;font-size:15px;" value="搜索">
			</td>
			<td width="5px;"></td>
		</tr>
		</table>
	</div>
	</form:form>
</div>
<c:if test="${searchResult==null}">
	没有搜索到相关资料
</c:if>
<c:forEach items="${searchResult}" var="mesanInfo" varStatus="status"> 
	<div>
	    <p style="font-size: 18px;"><a href="#" style="color: #6CA6CD;" onclick="openDialogView('查看资料信息', '${ctx}/resource/mesanInfo/teacherViewFormByName?mesanName=${mesanInfo.name}','800px', '500px')">${mesanInfo.name}</a></p>
		<p style="width: 100%;height: auto;word-wrap:break-word;word-break:break-all;overflow: hidden;">
		${mesanInfo.contentDesc}
		</p>
	</div>
	<br/>
</c:forEach>

</div>
<!--身体 end-->
<!--尾部 start-->
<div class="copyright">© 云帆互联 2014 汽车服务网 京ICP备14016447号</div>
<!--尾部 end-->
</body>
</html>
    