<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title>首页</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link href="${pageContext.request.contextPath}/css/global.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/x_pc.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/js/jquery-1.12.4.min.js"></script>
<script src="${pageContext.request.contextPath}/js/xiong.js"></script>
</head>
<body>
<div class="top">
	<div class="row">
    	<div class="top_fl fl">欢迎您访问吱吱文档管理系统</div>
        <div class="top_fr fr">
        	<a class="login" href=""><i class="icon_1"></i>登录</a>|
            <a class="register" href=""><i class="icon_2"></i>注册</a>
        </div>
    </div>
</div>
<!--头部 start-->
<div class="head">
	<div class="row">
    	<a class="logo fl"><img src="${pageContext.request.contextPath}/img/logo.png"></a>
        <div class="nav fr">
        	<a class="active" href="">首页</a>
            <a href="">课程库</a>
           <a href="${pageContext.request.contextPath}/a/studyActivityList">学习活动</a>
            <a href="">资料</a>
            <a href="">个人中心</a>
        </div>
    </div>
    <div class="banner">
        <div class="ban_box">
            <ul>
          	  <c:forEach items="${lsCar}" var="caruser">
                <li class="on"><a href="${caruser.url}"><img src="${caruser.img}/img/banner.png" alt="${caruser.title }" /></a></li>
           	  </c:forEach>
            </ul>
            <ol>
                <li></li>
            </ol>
            <div class="prev"><img src="${pageContext.request.contextPath}/img/prev.png" alt="前一张" /></div>
            <div class="next"><img src="${pageContext.request.contextPath}/img/next.png" alt="后一张" /></div>
        </div>
    </div>
    <script type="text/javascript">
        new BannerTurn('.ban_box','no',3.3,1000)
    </script>
</div>
<!--头部 end-->
    
<!--身体 start-->
<div class="main">
	<div class="row">
    	<div class="home_main">
            <!--课程-->
            <div class="clearfix">
                <div class="menu_tab of">
                    <h3 class="title fl">课程</h3>
                    <div class="tab fr">
                        <span class="on">新课程</span>
                        <span>进程中</span>
                        <span >经典课程</span>
                    </div>
                </div>
                <div class="list1">
                    <ul>
                        <li>
                            <a href="">
                                <div class="livecard_img">
                                    <img src="${pageContext.request.contextPath}/img/img1.png">
                                </div>
                                <div class="livecard_text">
                                    <h3>上通五菱初级课程基础知识与wbt</h3>
                                    <p><i class="icon_3"></i>2017年3月1日12:00开课</p>
                                    <p>未来您要学会手动挡操作原理</p>
                                </div>
                                <div class="livecard_user">
                                    <h6 class="fl">云帆互联</h6>
                                    <span class="fr"><i class="icon_4"></i>2时10分</span>
                                </div>
                            </a>
                        </li>
                        <li>
                            <a href="">
                                <div class="livecard_img">
                                    <img src="${pageContext.request.contextPath}/img/img1.png">
                                </div>
                                <div class="livecard_text">
                                    <h3>上通五菱初级课程基础知识与wbt</h3>
                                    <p><i class="icon_3"></i>2017年3月1日12:00开课</p>
                                    <p>未来您要学会手动挡操作原理</p>
                                </div>
                                <div class="livecard_user">
                                    <h6 class="fl">云帆互联</h6>
                                    <span class="fr"><i class="icon_4"></i>2时10分</span>
                                </div>
                            </a>
                        </li>
                        <li>
                            <a href="">
                                <div class="livecard_img">
                                    <img src="${pageContext.request.contextPath}/img/img1.png">
                                </div>
                                <div class="livecard_text">
                                    <h3>上通五菱初级课程基础知识与wbt</h3>
                                    <p><i class="icon_3"></i>2017年3月1日12:00开课</p>
                                    <p>未来您要学会手动挡操作原理</p>
                                </div>
                                <div class="livecard_user">
                                    <h6 class="fl">云帆互联</h6>
                                    <span class="fr"><i class="icon_4"></i>2时10分</span>
                                </div>
                            </a>
                        </li>
                        <li>
                            <a href="">
                                <div class="livecard_img">
                                    <img src="${pageContext.request.contextPath}/img/img1.png">
                                </div>
                                <div class="livecard_text">
                                    <h3>上通五菱初级课程基础知识与wbt</h3>
                                    <p><i class="icon_3"></i>2017年3月1日12:00开课</p>
                                    <p>未来您要学会手动挡操作原理</p>
                                </div>
                                <div class="livecard_user">
                                    <h6 class="fl">云帆互联</h6>
                                    <span class="fr"><i class="icon_4"></i>2时10分</span>
                                </div>
                            </a>
                        </li>
                    </ul>
            	</div>
            </div>
            <!--课程 end-->

            <!--学习活动-->
            <div class="clearfix">
                <div class="menu_tab of">
                    <h3 class="title fl">学习活动</h3>
                    <div class="tab fr">
                        <span class="on">新课程</span>
                        <span>进程中</span>
                        <span>经典课程</span>
                    </div>
                </div>
                <div class="list2">
                    <ul>
                        <li>
                            <a href="">
                                <div class="livecard_img">
                                    <img src="${pageContext.request.contextPath}/img/img1.png">
                                </div>
                                <div class="livecard_text">
                                    <h3>上通五菱初级课程基础知识与wbt</h3>
                                    <p><i class="icon_3"></i>2017年3月1日12:00开课</p>
                                    <p>未来您要学会手动挡操作原理</p>
                                </div>
                                <div class="livecard_user">
                                    <h6 class="fl">云帆互联</h6>
                                    <span class="fr"><i class="icon_4"></i>2时10分</span>
                                </div>
                            </a>
                            <div class="details">
                                <h3 class="title">学习活动:上通五菱初级课程基础知识与wbt</h3>
                                <p>国内比较领先的真正的Swift项目实战开发类IOS就业课程，同时加入了Jquery Mobile,PhoneGap,Html5,CSS3跨平台开发课程，技术最新。在家学习，北上广深就业，真实课堂全程高清直播，全程与讲师在线互动，遇到不懂之处，现场提问，现场解答。 讲师在线检查与评论作业。在线助教，解决一切开发中遇到的困难，督促作业完成情况与质量。小班教学，满15人开班。 缺课或跟不上当前进度，开小灶补课，让您学习无忧。</p>
                            </div>
                        </li>
                        <li>
                            <a href="">
                                <div class="livecard_img">
                                    <img src="${pageContext.request.contextPath}/img/img1.png">
                                </div>
                                <div class="livecard_text">
                                    <h3>上通五菱初级课程基础知识与wbt</h3>
                                    <p><i class="icon_3"></i>2017年3月1日12:00开课</p>
                                    <p>未来您要学会手动挡操作原理</p>
                                </div>
                                <div class="livecard_user">
                                    <h6 class="fl">云帆互联</h6>
                                    <span class="fr"><i class="icon_4"></i>2时10分</span>
                                </div>
                            </a>
                            <div class="details">
                                <h3 class="title">学习活动:上通五菱初级课程基础知识与wbt</h3>
                                <p>国内比较领先的真正的Swift项目实战开发类IOS就业课程，同时加入了Jquery Mobile,PhoneGap,Html5,CSS3跨平台开发课程，技术最新。在家学习，北上广深就业，真实课堂全程高清直播，全程与讲师在线互动，遇到不懂之处，现场提问，现场解答。 讲师在线检查与评论作业。在线助教，解决一切开发中遇到的困难，督促作业完成情况与质量。小班教学，满15人开班。 缺课或跟不上当前进度，开小灶补课，让您学习无忧。</p>
                            </div>
                        </li>
                        <li>
                            <a href="">
                                <div class="livecard_img">
                                    <img src="${pageContext.request.contextPath}/img/img1.png">
                                </div>
                                <div class="livecard_text">
                                    <h3>上通五菱初级课程基础知识与wbt</h3>
                                    <p><i class="icon_3"></i>2017年3月1日12:00开课</p>
                                    <p>未来您要学会手动挡操作原理</p>
                                </div>
                                <div class="livecard_user">
                                    <h6 class="fl">云帆互联</h6>
                                    <span class="fr"><i class="icon_4"></i>2时10分</span>
                                </div>
                            </a>
                            <div class="details">
                                <h3 class="title">学习活动:上通五菱初级课程基础知识与wbt</h3>
                                <p>国内比较领先的真正的Swift项目实战开发类IOS就业课程，同时加入了Jquery Mobile,PhoneGap,Html5,CSS3跨平台开发课程，技术最新。在家学习，北上广深就业，真实课堂全程高清直播，全程与讲师在线互动，遇到不懂之处，现场提问，现场解答。 讲师在线检查与评论作业。在线助教，解决一切开发中遇到的困难，督促作业完成情况与质量。小班教学，满15人开班。 缺课或跟不上当前进度，开小灶补课，让您学习无忧。</p>
                            </div>
                        </li>
                    </ul>
            	</div>
            </div>
            <!--学习活动 end-->
            
            <!--测评-->
            <div class="clearfix">
                <div class="menu_tab of">
                    <h3 class="title fl">测评</h3>
                    <div class="tab fr">
                        <span class="on">新课程</span>
                        <span>进程中</span>
                        <span>经典课程</span>
                    </div>
                </div>
                <div class="list1">
                    <ul>
                        <li>
                            <a href="">
                                <div class="livecard_img">
                                    <img src="${pageContext.request.contextPath}/img/img1.png">
                                </div>
                                <div class="livecard_text">
                                    <h3>上通五菱初级课程基础知识与wbt</h3>
                                    <p><i class="icon_3"></i>2017年3月1日12:00开课</p>
                                    <p>未来您要学会手动挡操作原理</p>
                                </div>
                                <div class="livecard_user">
                                    <h6 class="fl">云帆互联</h6>
                                    <span class="fr"><i class="icon_4"></i>2时10分</span>
                                </div>
                            </a>
                        </li>
                        <li>
                            <a href="">
                                <div class="livecard_img">
                                    <img src="${pageContext.request.contextPath}/img/img1.png">
                                </div>
                                <div class="livecard_text">
                                    <h3>上通五菱初级课程基础知识与wbt</h3>
                                    <p><i class="icon_3"></i>2017年3月1日12:00开课</p>
                                    <p>未来您要学会手动挡操作原理</p>
                                </div>
                                <div class="livecard_user">
                                    <h6 class="fl">云帆互联</h6>
                                    <span class="fr"><i class="icon_4"></i>2时10分</span>
                                </div>
                            </a>
                        </li>
                        <li>
                            <a href="">
                                <div class="livecard_img">
                                    <img src="${pageContext.request.contextPath}/img/img1.png">
                                </div>
                                <div class="livecard_text">
                                    <h3>上通五菱初级课程基础知识与wbt</h3>
                                    <p><i class="icon_3"></i>2017年3月1日12:00开课</p>
                                    <p>未来您要学会手动挡操作原理</p>
                                </div>
                                <div class="livecard_user">
                                    <h6 class="fl">云帆互联</h6>
                                    <span class="fr"><i class="icon_4"></i>2时10分</span>
                                </div>
                            </a>
                        </li>
                        <li>
                            <a href="">
                                <div class="livecard_img">
                                    <img src="${pageContext.request.contextPath}/img/img1.png">
                                </div>
                                <div class="livecard_text">
                                    <h3>上通五菱初级课程基础知识与wbt</h3>
                                    <p><i class="icon_3"></i>2017年3月1日12:00开课</p>
                                    <p>未来您要学会手动挡操作原理</p>
                                </div>
                                <div class="livecard_user">
                                    <h6 class="fl">云帆互联</h6>
                                    <span class="fr"><i class="icon_4"></i>2时10分</span>
                                </div>
                            </a>
                        </li>
                    </ul>
            	</div>
            </div>
            <!--测评 end-->
        </div>
    </div>
</div>
<!--身体 end-->

<!--尾部 start-->
<div class="copyright">© 云帆互联 2014 汽车服务网 京ICP备14016447号</div>
<!--尾部 end-->

</body>
</html>
    