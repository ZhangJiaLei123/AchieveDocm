<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<script type="text/javascript">
//var studentIndexUrl = "http://localhost/new-new/index.html";
var studentIndexUrl = "http://101.200.160.204:8888/docm_student/index.html";
//var studentIndexUrl = "http://localhost:8280/docm_student/index.html";
window.location.href =  studentIndexUrl+"?id="+ "${fns:getUser().id}";
</script>
</body>
</html>