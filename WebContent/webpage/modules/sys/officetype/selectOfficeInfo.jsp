<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title>组织类别</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link href="${pageContext.request.contextPath}/css/global.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/x_pc.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/js/jquery-1.12.4.min.js"></script>
<script src="${pageContext.request.contextPath}/js/xiong.js"></script>
<script src="${pageContext.request.contextPath}/js/layer-master/build/layer.js"></script>
</head>
<script type="text/javascript">
	function submitForm(pageNo){
		$("#pageNo").val(pageNo);
		$("#officeForm").submit();
	}
	function showOfficeInfo(id){
		var str = "";
		$.ajax({
	        async: false,
	        url: "${pageContext.request.contextPath}/selectOfficeById",
	        data:{id:id},
	        dataType: "json",
	        success: function (data) {
	    	    $("span",$("#showOfficeDiv")).each(function(){
	    	    	var key = this.id;
	    	    	var value = data[key];
	    	    	if("ssdq"==key){//大区
	    	    		$("#"+key).html(data["DQ"]+"&nbsp;&nbsp;"+data["SF"]+"&nbsp;&nbsp;"+data["SQ"]);
	    	    	}else if("useable"==key){//状态
	    	    		$("#"+key).html(value=="0"?"否":"是");
	    	    	}else{
	    	    		$("#"+key,$("#showOfficeDiv")).html(value);
	    	    	}
	    	    })
	        }
		});
		str = $("#showOfficeDiv").html();
		layer.open({
		    type: 1,
		    title: '查看组织信息',
		    maxmin: false,
		    area: ['600px', '400px'],
		    content: str,
		    end: function(){
		      layer.tips('Hi', '#about', {tips: 1})
		    }
		  });
	}
	function selecOfficeType(){
		layer.open({
		    type: 2,
		    title: '选择组织类别',
		    maxmin: false,
		    name:"opOfficeType",
		    area: ['255px', '480px'],
		    content: "${pageContext.request.contextPath}/jsp/zTreeOffice.jsp",
		    end: function(){
		      //layer.tips('Hi', '#about', {tips: 1})
		     // alert(this.index)
		    }
		  });
	}
	function setValue(id,name){
		$("#officeTypeNameSpan").html(name);
		$("#officeTypeId").val(id);
		$("#officeTypeName").val(name);
		layer.closeAll();
	}
</script>
<body>
<jsp:include page="/include/head.jsp"></jsp:include>
<script type="text/javascript">
	$($("a",$("#topA"))[1]).attr("class","active");
</script>
<!--头部 end-->    
<!--身体 start-->
<form action="${pageContext.request.contextPath}/a/selectOfficeInfo" id="officeForm" method="post">
		<div class="maincontent" style="width: 100%;">
			<div class="row">
				<div class="nav_menu">
					<a class="active" href="${pageContext.request.contextPath}/a/selectOfficeInfo">组织机构</a>
					<a href="${pageContext.request.contextPath}/a/selectUserInfo">人员</a>
					<div style="float: right">
						<span class="span_button">导出</span>
						
						<span class="span_button" onclick="selecOfficeType()">机构分类</span>
						<span id="officeTypeNameSpan">${officeTypeName }</span>
						<input type="hidden" name ="officeTypeId" id = "officeTypeId" value="${officeTypeId}">
						<input type="hidden" name ="officeTypeName" id = "officeTypeName" value="${officeTypeName}">
						<span><input type="text" name="officeName" class="input_style" value="${officeName}" placeholder="请输入机构名称"></span>
						<span class="span_button" onclick="submitForm(${page.pageNo})">搜索</span>
						<input type="hidden" name="pageNo" id="pageNo" value="${page.pageNo}">
					
					</div>
				</div>
			</div>
			<div class="con_middle">
				<div class="hdjh_table" >
					<table>
						<tr>
							<td class="back_color">机构名称</td>
							<td class="back_color">机构代码</td>
							<td class="back_color">机构分类</td>
							<td class="back_color">大区</td>
							<td class="back_color">省</td>
							<td class="back_color">市</td>
							<td class="back_color">联系电话</td>
							<td class="back_color">操作</td>
						</tr>
						<c:forEach items="${page.list}" var="office">
							<tr>
								<td class="no_color">${office.name }</td>
								<td class="no_color">${office.code }</td>
								<td class="no_color">${office.officeTypeName }</td>
								<td class="no_color">${office.distrctName }</td>
								<td class="no_color">${office.provinceName }</td>
								<td class="no_color">${office.cityName }</td>
								<td class="no_color">${office.phone }</td>
								<td class="no_color" align="center">
									<div class="guanzhu" onclick="showOfficeInfo('${office.id}')"><span><a>查看</a></span></div>
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
            <div class="pages">
				<ul>
					<li onclick="submitForm(${page.prev})"><a href="javascript:void(0)" >上一页</a></li>
					<c:forEach var="i" begin="1" end="${page.last}" > 
						<c:if test="${i==page.pageNo}">
					      <li class="active" onclick="submitForm(${i})"><a href="javascript:void(0)" > ${i} </a></li>
					    </c:if>
						<c:if test="${i!=page.pageNo }">
					      <li onclick="submitForm(${i})"><a href="javascript:void(0)"  > ${i} </a></li>
					     </c:if>
					</c:forEach>
					<li onclick="submitForm(${page.next})"><a href="javascript:void(0)" >下一页</a></li>
				</ul>
			</div>
			</div>
		</div>
</form>
<!--身体 end-->

<!--尾部 start-->
<div class="copyright">© 云帆互联 2014 汽车服务网 京ICP备14016447号</div>
</body>
</html>
    