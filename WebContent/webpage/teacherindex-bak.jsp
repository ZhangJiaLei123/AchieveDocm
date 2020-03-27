<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<!doctype html>
<html>
<head>
<title>首页</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
</head>
<body>

<!--头部 start-->
<jsp:include page="/include/teacherhead.jsp"></jsp:include>
<script type="text/javascript">
	$($("a",$("#topA"))[0]).attr("class","active");
</script>
<!--头部 end-->
<!--身体 start-->
<div class="main">
	<div class="row">
    	<div class="home_main">
            <!--课程-->
            <div class="clearfix">
                <div class="menu_tab of">
                    <h3 class="title fl">近期课程</h3>
                </div>
                <div class="list1">
                	<ul>
                     <c:forEach items="${pageNewLs}" var="courInfo">
                        <li title="${courInfo.cou_name}">
                            <a href="${pageContext.request.contextPath}/a/course/courseInfo/teacherManageCourseView?id=${courInfo.id}" target="_bank">
                                <div class="livecard_img">
                                    <img  src="${courInfo.stu_img}">
                                </div>
                                <div class="livecard_user">
                                 <c:choose>  
							        <c:when test="${fn:length(courInfo.cou_name) >8 }">  
							           <h6 class="fl" >${fn:substring(courInfo.cou_name, 0, 7)}…</h6>
							         </c:when>  
							        <c:otherwise>  
							           <h6 class="fl" >${fn:substring(courInfo.cou_name, 0, 8)}</h6>
							         </c:otherwise>  
							     </c:choose>   
                                    <span class="fr"><i class="icon_4"></i>${courInfo.count}人报名</span>
                                </div>
                            </a>
                        </li>
                        </c:forEach>
                    </ul>
            	</div>
            	<div class="menu_tab of">
                    <h3 class="title fl">课程参与人员</h3>
                </div>
                <div class="list1">
                    <ul>
                     <c:forEach items="${courIngLs}" var="courInfo">
                        <li title="${courInfo.cou_name}">
                            <a href="${pageContext.request.contextPath}/a/course/courseInfo/teacherManageCourseView?id=${courInfo.id}" target="_bank">
                                <div class="livecard_img">
                                    <img src="${courInfo.stu_img}">
                                </div>
                                <div class="livecard_user">
                                     <c:choose>  
							        <c:when test="${fn:length(courInfo.cou_name) >8 }">  
							           <h6 class="fl" >${fn:substring(courInfo.cou_name, 0, 7)}…</h6>
							         </c:when>  
							        <c:otherwise>  
							           <h6 class="fl" >${fn:substring(courInfo.cou_name, 0, 8)}</h6>
							         </c:otherwise>  
							     </c:choose>   
                                    <span class="fr"><i class="icon_4"></i>${courInfo.count}人学习</span>
                                </div>
                            </a>
                        </li>
                       </c:forEach>
                    </ul>
            	</div>
            </div>
            <!--课程 end-->
 <%--        <div style="padding-top: 10px;">
			<div style="float: left;width:555px;font-size: 12px;font-weight: 700;" >
				提醒消息
			</div>
			<div style="float: left;width:550px;margin-left: 30px;font-size: 12px;font-weight: 700;">
				快捷操作
			</div>
			<div style="float: left;width:555px;margin-top: 10px;border: 1px solid #dadada;height:200px;padding-top: 10px;" >
				 
			</div>
			<div style="float: left;width:540px;margin-left: 30px;margin-top: 10px;border: 1px solid #dadada;height:200px;padding-left: 10px;padding-top: 10px;">
				如何快速发布课程？
				<div style="padding-left: 20px;line-height: 20px;padding-top: 5px; color:#29C1E8 ">
					<a href="${pageContext.request.contextPath}/a/course/courseInfo/teacherFormBase/">1：创建课程</a><br />
					<a href="${pageContext.request.contextPath}/a/course/tcourseInfo/findMyCourseInfo/">2：找到刚才创建的课程</a>
				</div><br />
				如何快速发布学习活动？
				<div style="padding-left: 20px;line-height: 20px;padding-top: 5px; color:#29C1E8 ">
					<a href="${pageContext.request.contextPath}/a/train/studyActivity/teacherFormBase/">1：创建学习活动</a><br />
					<a href="${pageContext.request.contextPath}/a/myTeacherStudyActivity">2：找到刚才创建的学习活动</a>
				</div>
			</div>
		</div> --%>
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
    