<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登陆</title>
<link href="./login/style_log.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/login/style.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/login/userpanel.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/login/jquery.ui.all.css">
</head>
<body class="login" mycollectionplug="bind">
<div class="login_m">
<div class="login_logo"></div>
<div class="login_boder">
<div class="login_padding" id="login_model">
<form action="${pageContext.request.contextPath}/login" method="post">
  <h2>用户名</h2>
  <label>
    <input type="text" id="username" name="username" class="txt_input txt_input2" onfocus="if (value ==&#39;Your name&#39;){value =&#39;&#39;}" onblur="if (value ==&#39;&#39;){value=&#39;Your name&#39;}" value="Your name">
  </label>
  <h2>密码</h2>
  <label>
    <input type="password" name="userpwd" id="password" class="txt_input" onfocus="if (value ==&#39;******&#39;){value =&#39;&#39;}" onblur="if (value ==&#39;&#39;){value=&#39;******&#39;}" value="******">
  </label>
  <div class="rem_sub">
  <div class="rem_sub_l">
   </div>
    <label>
      <input type="submit" class="sub_button" name="button" id="submit" value="登录" style="opacity: 0.7;">
    </label>
  </div>
</div>
 </form>

</body></html>