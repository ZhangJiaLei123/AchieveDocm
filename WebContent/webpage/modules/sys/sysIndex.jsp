<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <title>吱吱文档管理系统</title>

	<%@ include file="/webpage/include/head.jsp"%>
	<script src="${ctxStatic}/common/inspinia.js?v=3.2.0"></script>
	<script src="${ctxStatic}/common/contabs.js"></script> 
    <script type="text/javascript">
	$(document).ready(function() {
		 if('${fns:getDictLabel(cookie.theme.value,'theme','默认主题')}' == '天蓝主题'){
			    // 蓝色主题
			        $("body").removeClass("skin-2");
			        $("body").removeClass("skin-3");
			        $("body").addClass("skin-1");
		 }else  if('${fns:getDictLabel(cookie.theme.value,'theme','默认主题')}' == '橙色主题'){
			    // 黄色主题
			        $("body").removeClass("skin-1");
			        $("body").removeClass("skin-2");
			        $("body").addClass("skin-3");
		 }else {
			 // 默认主题
			        $("body").removeClass("skin-2");
			        $("body").removeClass("skin-3");
			        $("body").removeClass("skin-1");
		 };
		 findMenuDaiSh();
		
	 });
	
	function findMenuDaiSh(){
		$('#msgTopFloatTable  tr').each(function(len){
			if(len!=0){
				$(this).remove();
			}
		});
		var a = 0;
		 $.ajax({
	 		    async: false,
	 		    url: "${ctx}/sys/user/findMenuDaiSh",
	 		    type:"post",
	 		    data:{},
	 		    success: function (data) {
	 		    	if(data){
	 		    		data = eval(data)
	 		    		for(var i=0;i<data.length;i++){
	 		    			a++;
	 		    			$("#msgTopFloatTable").append("<tr><td >"+data[i]['menuName']+"</td><td>"+data[i]['count']+"</td></tr>")
	 		    		}
	 		    	}
	 		    }
	 		});
		 if(a==0){
			 $('#msgTopFloatDiv').hide();
		 }
	}
	
	function msgTopFloatDiv(){
		var display = $('#msgTopFloatDiv').css('display');
		if('none'==display){
			$('#msgTopFloatDiv').show();
		}else{
			$('#msgTopFloatDiv').hide();
		}
	}
	
	</script>
<style type="text/css">

.float_div{
 	position:absolute;
	width:300px;
	float:right;
	z-index:9999;
	right:0;/*   定位到右上   */
	top:30px;
	background-color:white;
}
</style>
</head>
<body class="fixed-sidebar full-height-layout gray-bg">
    <div id="wrapper">
        <!--左侧导航开始-->
        <nav class="navbar-default navbar-static-side" role="navigation">
            <div class="nav-close"><i class="fa fa-times-circle"></i>
            </div>
            <div class="sidebar-collapse">
                <ul class="nav" id="side-menu">
                    <li class="nav-header">
                        <div class="dropdown profile-element">
                            <span><%-- <img alt="image" class="img-circle" style="height:64px;width:64px;" src="${fns:getUser().photo }" /> --%></span>
                            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                                <span class="clear">
                               <span class="block m-t-xs"><strong class="font-bold">${fns:getUser().name}</strong></span>
                               <span class="text-muted text-xs block">${fns:getUser().roleNames}<b class="caret"></b></span>
                                </span>
                            </a>
                        </div>
                        <div class="logo-element">
                        </div>
                    </li>
     
                  <t:menu  menu="${fns:getTopMenu()}"></t:menu>

                </ul>
            </div>
        </nav>
        <!--左侧导航结束-->
        <!--右侧部分开始-->
        <div id="page-wrapper" class="gray-bg dashbard-1">
            <div class="row border-bottom">
                <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
                    <div class="navbar-header"><a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#"><i class="fa fa-bars"></i> </a>
                        <form role="search" class="navbar-form-custom" method="post" action="search_results.html">
                        </form>
                    </div>
                        <li class="dropdown">
                            <ul class="dropdown-menu dropdown-messages">
                            	 <c:forEach items="${mailPage.list}" var="mailBox">
	                                <li class="m-t-xs">
	                                    <div class="dropdown-messages-box">
	                                   
	                                        <a  href="#" onclick='top.openTab("${ctx}/iim/contact/index?name=${mailBox.sender.name }","通讯录", false)' class="pull-left">
	                                            <img alt="image" class="img-circle" src="${mailBox.sender.photo }">
	                                        </a>
	                                        <div class="media-body">
	                                            <small class="pull-right">${fns:getTime(mailBox.sendtime)}前</small>
	                                            <strong>${mailBox.sender.name }</strong>
	                                            <a class="J_menuItem" href="${ctx}/iim/mailBox/detail?id=${mailBox.id}"> ${fns:abbr(mailBox.mail.title,50)}</a>
	                                            <br>
	                                            <a class="J_menuItem" href="${ctx}/iim/mailBox/detail?id=${mailBox.id}">
	                                             ${mailBox.mail.overview}
	                                            </a>
	                                            <br>
	                                            <small class="text-muted">
	                                            <fmt:formatDate value="${mailBox.sendtime}" pattern="yyyy-MM-dd HH:mm:ss"/></small>
	                                        </div>
	                                    </div>
	                                </li>
	                                <li class="divider"></li>
                                </c:forEach>
                                
                            </ul>
                        </li>
                          <!--
                        <div onclick="msgTopFloatDiv()" style="float: right;padding-top:10px;padding-right: 20px;cursor: pointer;">
                        	待审核消息
                        </div>
                      <div class="float_div" id = "msgTopFloatDiv">
                        	<table id = "msgTopFloatTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
                        		<tr>
                        			<th style="width:50px">模块名称</th>
                        			<th style="width:50px">待审核条数&nbsp;<a href="javascript:void(0)" onclick="findMenuDaiSh()">刷新</a></th>
                        		</tr>
                        	</table>
                        </div>-->
                      <!-- 国际化功能预留接口 

							<!--Language selector menu-->
				
                </nav>
            </div>
            <div class="row content-tabs">
                <nav class="page-tabs J_menuTabs">
                    <div class="page-tabs-content">
                        <a href="javascript:;" class="active J_menuTab" data-id="${ctx}/home">首页</a>
                    </div>
                </nav>
                <a href="${ctx}/logout" class="roll-nav roll-right J_tabExit"><i class="fa fa fa-sign-out"></i>退出</a>
            </div>
            <div class="row J_mainContent" id="content-main">
                <iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="${ctx}/home" frameborder="0" data-id="${ctx}/home" seamless></iframe>
            </div>
        </div>
        <!--右侧部分结束-->
       
       
    </div>
</body>

<!-- 语言切换插件，为国际化功能预留插件 -->
<script type="text/javascript">

$(document).ready(function(){

	$("a.lang-select").click(function(){
		$(".lang-selected").find(".lang-flag").attr("src",$(this).find(".lang-flag").attr("src"));
		$(".lang-selected").find(".lang-flag").attr("alt",$(this).find(".lang-flag").attr("alt"));
		$(".lang-selected").find(".lang-id").text($(this).find(".lang-id").text());
		$(".lang-selected").find(".lang-name").text($(this).find(".lang-name").text());

	});


});

function changeStyle(){
   $.get('${pageContext.request.contextPath}/theme/ace?url='+window.top.location.href,function(result){   window.location.reload();});
}

</script>



<!-- 即时聊天插件 -->
<link href="${ctxStatic}/layer-v2.3/layim/layim.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript">
	var currentId = '${fns:getUser().loginName}';
	var currentName = '${fns:getUser().name}';
	var currentFace ='${fns:getUser().photo}';
	var url="${ctx}";
	var wsServer = 'ws://'+window.document.domain+':8668';

</script>
<script src="${ctxStatic}/layer-v2.3/layim/layer.min.js"></script>
<%-- <script src="${ctxStatic}/layer-v2.3/layim/layim.js"></script>
 --%>
</html>