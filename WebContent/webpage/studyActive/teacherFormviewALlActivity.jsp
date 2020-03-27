<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>全部学习活动</title>
<meta charset="utf-8">
<meta name="decorator" content="default"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
</head>
<script type="text/javascript">
function submitForm(pageNo){
	$("#pageNo").val(pageNo);
	$("#studyActiveForm").submit();
}

</script>
<body>
<input type="hidden" id="showResourceId">

<!--头部 start-->
<jsp:include page="/include/head.jsp"></jsp:include>
<script type="text/javascript">
	$($("a",$("#topA"))[5]).attr("class","active");
	//打开对话框(添加修改)
	function openMyDialog(title,url,width,height,target){
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
		    btn: ['下一步'],
		    yes: function(index, layero){
		    	 var body = top.layer.getChildFrame('body', index);
		    	 var isShowDiv = $("#showDiv",body).val();
		    	 var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
		         var inputForm = body.find('#inputForm');
		        /*  var top_iframe;
		         if(target){
		        	 top_iframe = target;//如果指定了iframe，则在改frame中跳转
		         }else{
		        	 top_iframe = top.getActiveTab().attr("name");//获取当前active的tab的iframe 
		         }
		         inputForm.attr("target",top_iframe);//表单提交成功后，从服务器返回的url在当前tab中展示 */
		         
		    	 if(1==isShowDiv && iframeWin.contentWindow.doSubmit()){
			    	$("#tableOne",body).hide();
			    	$("#tableTwo",body).show();
			    	$("#showDiv",body).val(2);
		    		 $("#secondTdColor",body).css("color","#4DD52B");
			         $("#secondTdImg",body).html("<img src='${pageContext.request.contextPath}/img/u4150.png'/>");
		    	 }else if(2==isShowDiv && iframeWin.contentWindow.doSubmit()){
		    		 $("#tableTwo",body).hide();
		    		 $("#tableThree",body).show();
		    		 $("#showDiv",body).val(3);
		    		 console.log($("#iframeBxOffeic",body).document)
		    		  $('input:checkbox[name="chk"]:checked',$("#iframeBxOffeic",body).document).each(function(){
		    			 alert($(this).val());
		    		 });
		    		 $(layero.find('.layui-layer-btn0')[0]).html("提交");
		    		 $("#threeTdColor",body).css("color","#4DD52B");
				     $("#threeTdImg",body).html("<img src='${pageContext.request.contextPath}/img/u4150.png'/>");
				  	var isCheckAllBx = $("#isCheckAllBx",body).val();
				  	var url = "${ctx}/sys/office/selOfficeList?officeIdsBx=" + $("#officIdsBx",body).val() +"&isCheckAllBx="+isCheckAllBx;
				  	$("#Xx",body).attr("src",url);
		    	 }else if(3==isShowDiv && iframeWin.contentWindow.doSubmit()){
			        	// top.layer.close(index);//关闭对话框。
			        	  top.layer.close(index);//延时0.1秒，对应360 7.1版本bug
			        	  window.location.reload(); 
			        	  
		    	 }
		    	
			 },
			  cancel: function(index){ 
				  
		      }
		}); 	
	}
	
	function showLabDiv(num){
		$("li[id^='showLabLi']").each(function(index){
			if((index+1)==num){
				$(this).css("color","#09C7F7");
			}else{
				$(this).css("color","#000");
			}
			
		});
		var myNum = '';
		$("div[id^='showLabDiv']").each(function(index){
			if((index+1)==num){
				$(this).show();
				myNum = num;
			}else{
				$(this).hide();
			}
		});
		$('#iframeLab'+myNum).attr('src', $('#iframeLab'+myNum).attr('src'));
	}
</script>
	<!--头部 end-->
	<!--身体 start-->
	<div class="maincontent" style="width: 100%;">
		<div class="row">
			<div class="nav_menu">
				<a href="${pageContext.request.contextPath}/a/studyActivityList">学习活动计划</a>
				<a href="${pageContext.request.contextPath}/a/gzActivityList">关注计划</a>
				<a class="active"
					href="${pageContext.request.contextPath}/a/teacherSstudyActivity">全部学习活动</a>
				<a  href="${pageContext.request.contextPath}/a/train/studyActivity/teacherFormBase" >新建学习活动</a>
				<a 
					href="${pageContext.request.contextPath}/a/myTeacherStudyActivity">我的学习活动</a>
			</div>
		</div>
		<div class="maincontent">
			<div class="wrapper wrapper-content">
				<table>
						<tr>
							<td rowspan="4">
								<img alt="image" width="300px" height="180px" src="${studyActivity.actImg}">
							</td>
						</tr>
						<tr height="20px">
							<td style="width:100px;padding-left: 10px;">
								开课时间段：
							</td>
							<td style="text-align: left;padding: 5px;">
								<fmt:formatDate value="${studyActivity.studyStartTime}" pattern="yyyy-MM-dd"/>&nbsp;~
								<fmt:formatDate value="${studyActivity.studyEndTime}" pattern="yyyy-MM-dd"/>
							</td>
							<td style="width:80px;height: 30px;">
								活动类型：
							</td>
							<td style="text-align: left;padding: 5px;">
								${fns:getDictLabel(studyActivity.actType,'activity_type','')}
							</td>
							<td style="width:80px;height: 30px;">
								总人数：
							</td>
							<td style="text-align: left;padding: 5px;">
								${studyActivity.userCount}
							</td>
						</tr>
						<tr>
							<td  style="width:100px;padding-left: 10px;">
								报名时间段：
							</td>
							<td style="text-align: left;padding: 5px;">
								<fmt:formatDate value="${studyActivity.applyStartTime}" pattern="yyyy-MM-dd"/>&nbsp;~
								<fmt:formatDate value="${studyActivity.applyEndTime}" pattern="yyyy-MM-dd"/>
							</td>
							<td>
								活动地点：
							</td>
							<td style="text-align: left;padding: 5px;" colspan="3">
								${studyActivity.space}
							</td>
						</tr>
						<tr>
							<td  style="width:100px;padding-left: 10px;">
								活动描述：
							</td>
							<td colspan="5" style="text-align: left;padding: 5px;word-break:break-all">
								${studyActivity.remarks}
							</td>
						</tr>
				</table>
			</div>
			<div style="border-top:1px solid #d8d8d8;width:100%;padding: 5px">
				<ul style="margin-left: 30px" id="showLab">
					<li id="showLabLi1" style="float: left;color:#09C7F7;width: 100px;font-weight: 800;cursor: pointer;" onclick="showLabDiv('1')">学员展示</li>
					<li id="showLabLi2" style="float: left;width: 100px;font-weight: 800;cursor: pointer;" onclick="showLabDiv('2')">查看目录</li>
					<li id="showLabLi3" style="float: left;width: 100px;font-weight: 800;cursor: pointer;" onclick="showLabDiv('3')">成绩展示</li>
					<li id="showLabLi5" style="float: left;width: 100px;font-weight: 800;cursor: pointer;" onclick="showLabDiv('4')">授课时间</li>
				</ul>
				<div style="padding:10px;display: block;" id="showLabDiv1">
					<iframe id="iframeLab2" style="border: none;width: 100%;height: 100%;height: 500px" src="${ctx}/listAllbyactivityid?activityId=${studyActivity.id}"></iframe>
				</div>
				<div style="padding:10px; display: none" id="showLabDiv2">
					<iframe  id="iframeLab2" style="border: none;width: 100%;height: 500px" src="${ctx}/teacherAllActivityDirListview?activityId=${studyActivity.id}"></iframe>
				</div>
				<div style="padding:10px; display: none" id="showLabDiv3">
					<iframe  id="iframeLab3" style="border: none;width: 100%;height: 700px" src="${ctx}/teacherListViewScore?activityId=${studyActivity.id}"></iframe>
				</div>
				<div style="padding:10px; display: none" id="showLabDiv4">
					<iframe  id="iframeLab4" style="border: none;width: 100%;height: 500px" src="${ctx}/teacherAllLessonTimeList?activityId=${studyActivity.id}"></iframe>
				</div>
			</div>
		</div>
	</div>
	<!--尾部 start-->
	<div class="copyright">© 云帆互联 2014 汽车服务网 京ICP备14016447号</div>
	<!--尾部 end-->

</body>
</html>
    