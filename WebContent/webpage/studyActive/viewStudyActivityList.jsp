<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>查看学习活动</title>
<meta charset="utf-8">
<meta name="decorator" content="default"/>
</head>
<script type="text/javascript">
</script>
<body>
<!--头部 start-->
<jsp:include page="/include/head.jsp"></jsp:include>
<script type="text/javascript">
	$($("a",$("#topA"))[5]).attr("class","active");
	
	function gzTrainProgram(id){
			 layer.confirm('确定要关注吗？', {
				btn: ['确定','取消'] //按钮
			}, function(){
				$.ajax({
			        async: false,
			        url: "${ctx}/gzStudyActivity",
			        data:{id:id},
			        dataType: "json",
			        success: function (data) {
			    	   if(data){
			    		   layer.confirm('关注成功!', {
			    				btn: ['确定'] //按钮
			    			}, function(){
			    				window.location.reload()
			    			})
			    	   }else{
			    		   layer.confirm('关注失败!', {
			    				btn: ['确定'] //按钮
			    			}, function(){
			    				window.location.reload();
			    			})
			    	   }
			        }
				});
			})
	}

	function qxGzTrainProgram(id){
			 layer.confirm('确定要取消关注吗？', {
				btn: ['确定','取消'] //按钮
			}, function(){
				$.ajax({
			        async: false,
			        url: "${ctx}/qxGzStudyActivity",
			        data:{id:id},
			        dataType: "json",
			        success: function (data) {
			    	   if(data){
			    		   layer.confirm('取消关注成功!', {
			    				btn: ['确定'] //按钮
			    			}, function(){
			    				window.location.reload()
			    			})
			    	   }else{
			    		   layer.confirm('取消失败!', {
			    				btn: ['确定'] //按钮
			    			}, function(){
			    				window.location.reload();
			    			})
			    	   }
			        }
				});
			})
	}
	
	
</script>
<!--头部 end-->
<!--身体 start-->
		<div class="maincontent">
			<div class="row">
				<div class="nav_menu">
					<a class="active" href="${pageContext.request.contextPath}/a/studyActivityList">学习活动计划</a>
					<a  href="${pageContext.request.contextPath}/a/gzActivityList">关注计划</a>
					<a  href="${pageContext.request.contextPath}/a/teacherSstudyActivity">全部学习活动</a>
					<a  href="${pageContext.request.contextPath}/a/train/studyActivity/teacherFormBase" >新建学习活动</a>
					<a  href="${pageContext.request.contextPath}/a/myTeacherStudyActivity">我的学习活动</a>
				</div>
			</div>
			<div style="padding-top: 10px;">
				<table style="font-size: 14px;">
					<tr style="line-height: 30px;">
						<td width="100px">
							计划名称：
						</td>
						<td>
							${trainProgram.proName }
						</td>
					</tr>
					<tr style="line-height: 30px;">
						<td>
							计划类型：
						</td>
						<td>
							${fns:getDictLabel(trainProgram.proType, 'train_program_type', '')}
						</td>
					</tr>
					<tr style="line-height: 30px;">
						<td>
							计划时间：
						</td>
						<td>
							<fmt:formatDate value="${trainProgram.gzStartTime}" pattern="yyyy-MM-dd"/>~<fmt:formatDate value="${trainProgram.gzEndTime}" pattern="yyyy-MM-dd"/>
						</td>
					</tr>
					<tr style="line-height: 30px;">
						<td>
							计划描述：
						</td>
						<td>
							${trainProgram.remarks }
						</td>
					</tr>
				</table>
			</div>
					<table class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
						<tr>
							<th style="width:400px">活动名称</th>
							<th  style="width:200px">人数</th>
							<th style="width:400px">学习时间范围</th>
							<th style="width:200px">地点</th>
							<th  style="width:100px">操作</th>
						</tr>
						<c:forEach items="${trainProgram.proGramList}" var="obj">
						<c:if test="${obj.id !=null && obj.id!=''}">
							<tr>
								<td>${obj.name }</td>
								<td >${obj.userCount }</td>
								<td >${obj.proTime }</td>
								<td>${obj.space }</td>
								<td >
								
									<c:if test="${null==obj.userFllow ||''== obj.userFllow}">
										<c:if test="${trainProgram.isGz =='true'}">
											<a  onclick="gzTrainProgram('${obj.id}')"  class="btn btn-info btn-xs">关注</a>&nbsp;
										</c:if>
										<c:if  test="${trainProgram.isGz =='false'}">
											<a  class="btn btn-info btn-xs" style="background:#D0D0D0;border:none">不能关注</a>&nbsp;
										</c:if>	
									</c:if>
									<c:if test="${null!=obj.userFllow &&''!= obj.userFllow}">
										<a  onclick="qxGzTrainProgram('${obj.id}')"  class="btn btn-info btn-xs">取消关注</a></span>&nbsp;
									</c:if>
								
								</td>
							</tr>
						</c:if>
						</c:forEach>
					</table>
		</div>
		</form>
<!--尾部 start-->
<div class="copyright">© 云帆互联 2014 汽车服务网 京ICP备14016447号</div>
<!--尾部 end-->

</body>
</html>
    