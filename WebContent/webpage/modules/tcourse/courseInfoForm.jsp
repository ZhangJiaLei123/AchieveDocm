<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>课程管理管理</title>
	<meta name="decorator" content="default"/>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<link href="${pageContext.request.contextPath}/css/global.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/css/x_pc.css" rel="stylesheet" type="text/css" />
	<script src="${pageContext.request.contextPath}/js/jquery-1.12.4.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/xiong.js"></script>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  $("#inputForm").submit();
			  return true;
		  }
	
		  return false;
		}
		$(document).ready(function() {
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			
					laydate({
			            elem: '#regStartTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
			        });
					laydate({
			            elem: '#regEndTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
			        });
					laydate({
			            elem: '#stuStartTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
			        });
					laydate({
			            elem: '#stuEndTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
			        });
		});
		
		function checkResource(id,name){
			alert(id+"=="+name)
			
		}

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
			    btn: ['确定', '关闭'],
			    yes: function(index, layero){
			    	 var body = top.layer.getChildFrame('body', index);
			         var obj = $('input:radio[name="rdi"]:checked',body);
			         if(obj.length>0){
			        	$("#resResource").val(obj.attr("id"));
			         	$("#mySpan").html(obj.val());
			         }else{
			        	 $("#resResource").val("");
				         $("#mySpan").html("");
			         }
			         setTimeout(function(){top.layer.close(index)}, 100);//延时0.1秒，对应360 7.1版本bug
			         
				  },
				  cancel: function(index){ 
					  
			      }
			}); 	
			
		}
		function changeSelect(obj){
			if(obj.value==2){
				$("#noneDiv0").show();
				$("#noneDiv1").show();
			}else{
				$("#noneDiv0").hide();
				$("#noneDiv1").hide();
			}
			
		}
		//打开对话框(添加修改)
		function openRoleDialog(title,url,width,height,target){
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
			    	 var postDiv = "";
			    	 var postInfoIds = "";
			        	$('input:checkbox[name="chk"]:checked',body).each(function(){
			        		postDiv += $(this).attr("postName")+"--"+$(this).attr("postLevelName")+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
			        		postInfoIds += $(this).attr("id")+",";
			        	});
			        	var showDiv = $("#showDiv").val();
			        	if(showDiv==2){
				        	$("#userBxPost").html(postDiv);
				        	$("#postIdsBx").val(postInfoIds);
			        	}else if(showDiv==3){
			        		$("#userXxPost").html(postDiv);
				        	$("#postIdsXx").val(postInfoIds);
			        	}
			         setTimeout(function(){top.layer.close(index)}, 100);//延时0.1秒，对应360 7.1版本bug
			         
				  },
				  cancel: function(index){ 
					  
			      }
			}); 	
			
		}
		function selShowUser(obj){
			var showDiv = $("#showDiv").val();
        	if(showDiv==2){
				if(obj.value=="1"){
					$("#selShowUserBxDiv").show();
					
				}else{
					$("#selShowUserBxDiv").hide();
				}
        	}else if(showDiv==3){
        		if(obj.value=="1"){
					$("#selShowUserXxDiv").show();
					
				}else{
					$("#selShowUserXxDiv").hide();
				}
        		
        	}
			
		}
		function parentOfficeChange(s){
			var showDiv = $("#showDiv").val();
        	if(showDiv==2){
        		$("#officIdsBx").val(s);
        	}else if(showDiv==3){
        		$("#officIdsXx").val(s);
        	}
			
			
		}
		function parentUserChange(s){
			var showDiv = $("#showDiv").val();
        	if(showDiv==2){
        		$("#userIdsBx").val(s);
        	}else if(showDiv==3){
        		$("#userIdsXx").val(s);
        	}
		}
	</script>
</head>
<body>
	<div class="top">
		<div class="row">
	    	<div class="top_fl fl">欢迎您访问吱吱文档管理系统</div>
	        <div class="top_fr fr">
	        	<a class="login" href=""><i class="icon_1"></i>登录</a>|
	<!--             <a class="register" href=""><i class="icon_2"></i>注册</a> -->
	        </div>
	    </div>
	</div>
	<!--头部 start-->
	<jsp:include page="/include/head.jsp"></jsp:include>
	<!--头部 end-->
	<input type="hidden" id="showDiv" value="1">
		<form:form id="inputForm" modelAttribute="courseInfo" action="${ctx}/course/courseInfo/save" method="post" class="form-horizontal">
		<div class="maincontent" style="width: 100%;">
		<div class="row">
		<div class="nav_menu">
				<a class="active"  href="${pageContext.request.contextPath}/a/course/tcourseInfo/list">课程库</a>
				<a href="${pageContext.request.contextPath}/findMyResourse">我的课程</a>
				<a href="${pageContext.request.contextPath}/findMyResourse">新建课程</a>
				<div style="float: right">
					<span>
						<select class="input_style" name="resourceType">
							<option value="" >请选择</option>
						</select>
					<span><input type="text" name="name" value="${name}" class="input_style" placeholder="资源名称"></span>
					<span class="span_button" onclick="submitForm(${page.pageNo})">&nbsp;&nbsp;搜&nbsp;&nbsp;索&nbsp;&nbsp;</span>
					<input type="hidden" value="${page.pageNo}" id="pageNo" name="pageNo">
				</div>
			</div>
		</div>
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table id="tableOne" class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>课程名称：</label></td>
					<td class="width-35">
						<form:input path="couName" htmlEscape="false" maxlength="200" class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>课程描述：</label></td>
					<td class="width-35">
						<form:textarea path="couDescribe" htmlEscape="false" rows="4" maxlength="200" class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">报名时间：</label></td>
					<td class="width-35">
						<input  readonly="readonly"  id="regStartTime" name="regStartTime" type="text" maxlength="20" class="laydate-icon form-control layer-date "
							value="<fmt:formatDate value="${courseInfo.regStartTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
							<input  readonly="readonly"  id="regEndTime" name="regEndTime" type="text" maxlength="20" class="laydate-icon form-control layer-date "
							value="<fmt:formatDate value="${courseInfo.regEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">学习时间：</label></td>
					<td class="width-35">
							<input  readonly="readonly"  id="stuStartTime" name="stuStartTime" type="text" maxlength="20" class="laydate-icon form-control layer-date "
							value="<fmt:formatDate value="${courseInfo.stuStartTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
								<input  readonly="readonly"  id="stuEndTime" name="stuEndTime" type="text" maxlength="20" class="laydate-icon form-control layer-date "
							value="<fmt:formatDate value="${courseInfo.stuEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">课程图片：</label></td>
					<td class="width-35">
						<form:hidden id="stuImg" path="stuImg" htmlEscape="false" maxlength="255" class="form-control"/>
						<sys:ckfinder input="stuImg" type="images" uploadPath="/course/courseInfo" selectMultiple="false"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">选择资源：</label></td>
					<td class="width-35">
						<span id="mySpan"></span>
						<form:hidden id="resResource" path="resResource"  class="form-control"/>
						<a href="#" onclick="openMyDialog('选择资源', '${ctx}/resource/resourceInfo/seleResInfolist','600px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 选择资源</a>
					</td>
		  		</tr>
		  		<tr>
					<td class="width-15 active"><label class="pull-right">通过标准：</label></td>
					<td class="width-35">
						<table>
							<tr>
							<td>
								<form:select path="byStandard" class="form-control" onchange="changeSelect(this)" width="300px">
									<form:options items="${fns:getDictList('courseinfo_by_standard')}" itemLabel="label" itemValue="value" htmlEscape="true"/>
								</form:select>
							</td>
							<td style="width:20px;"></td>
							<td>
								<div id="noneDiv0" style="display:none;">设置分数：</div>
							</td>
							
							<td>
								<div id="noneDiv1" style="display:none;">
									<form:input id="byMark" path="byMark" htmlEscape="false" maxlength="10" class="form-control"/>
								</div>
							</td>
						</tr>
						</table>
					</td>
		  		</tr>
		 	</tbody>
		</table>
		
		<!-- 第二步 -->
		<table id="tableTwo" style="display: none;width: 100%">
			<tr height="30px">
				<td colspan="3"><input type="hidden" id="officIdsBx" name="officIdsBx">
				<iframe id="iframeBxOffeic" width="100%" height="280px" style="border: none" src="${ctx}/sys/office/selOfficeList?a="+Math.random()></iframe></td>
			</tr>
			<tr height="30px">
				<td style="width:100px" >人员岗位：</td>
				<td colspan="3" >
					<span id="userBxPost"></span>
					<input type="hidden" id="postIdsBx" name="postIdsBx">
					<a  href="#" onclick="openRoleDialog('选择岗位', '${ctx}/sys/postInfo/showPostList','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 选择岗位</a>
				</td>
			</tr>
			<tr height="30px">
				<td>是否选择学员：</td>
				<td colspan="2"><input type="radio" name="isBxUser" onclick="selShowUser(this)" value="1">是<input type="radio" name="isBxUser" onclick="selShowUser(this)">否</td>
			</tr>
			<tr>
				<td colspan="3" >
					<div style="display: block" id="selShowUserBxDiv">
						<input type="hidden" id="userIdsBx" name="userIdsBx"/><iframe id="selShowBxUser" width="100%" height="200px" style="border: none" src="${ctx}/sys/user/selShowUser?a="+Math.random()></iframe>
					</div>
				</td>
			</tr>
		</table>
		
		<!-- 第三步 -->
		<table id="tableThree" style="display: none;width: 100%">
			<tr height="30px">
				<td colspan="3"><input type="hidden" id="officIdsXx" name="officIdsXx">
				<iframe width="100%" id ="iframeXxOffeic" height="300px" style="border: none" src="${ctx}/sys/office/selOfficeList?a="+Math.random()></iframe></td>
			</tr>
			<tr height="30px">
				<td style="width:100px" >人员岗位：</td>
				<td colspan="2">
					<span id="userXxPost"></span>
					<input type="hidden" id="postIdsXx" name="postIdsXx">
					<a href="#" onclick="openRoleDialog('选择岗位', '${ctx}/sys/postInfo/showPostList','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 选择岗位</a>
					</td>
			</tr>
			<tr height="30px">
				<td>是否选择学员：</td>
				<td colspan="2"><input type="radio" name="isXxUser" onclick="selShowUser(this)" value="1">是<input type="radio" name="isXxUser" onclick="selShowUser(this)">否</td>
			</tr>
			<tr >
				<td colspan="3" >
					<div style="display: block" id="selShowUserXxDiv">
						<input type="hidden" id="userIdsXx" name = "userIdsXx"/><iframe id="selShowXxUser" width="100%" height="200px" style="border: none" src="${ctx}/sys/user/selShowUser?a="+Math.random()></iframe>
					</div>
				</td>
			</tr>
		</table>
	</form:form>
	<p align="center">
		<a href="#" onclick="openMyDialogNext('添加课程管理', '${ctx}/course/courseInfo/form','900px', '700px')" class="btn btn-info btn-xs" ><i class="fa fa-add-plus"></i>下一步</a>
	</p>
	<!--尾部 start-->
		<div class="copyright">© 云帆互联 2014 汽车服务网 京ICP备14016447号</div>
<!--尾部 end-->
</body>
</html>