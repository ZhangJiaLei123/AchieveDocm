<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	$(document).ready(function() {
		
		var str = $("#lsUserPostStr").val();
		if(str){
			var jsonObj = eval(str);
			for(var i=1;i<=jsonObj.length;i++){
				var obj = jsonObj[i-1];
				$("#posttypename"+i).html(obj['posttypename']=='null'?'':obj['posttypename']);
				$("#postname"+i).html(obj['postname']=='null'?'':obj['postname']);
				$("#postlevelname"+i).html(obj['postlevelname']=='null'?'':obj['postlevelname']);
			}
		}
	})
	</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/user/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		    <tr>
		         <td class="active width-15"><label class="pull-right">登录ID：</label></td>
		         <td colspan="2">
		         	${user.loginName}
		         		<input type="hidden" name="lsUserPostStr" id ="lsUserPostStr" value="${lsUserPostStr}">
		         </td>
				 <td class="active"><label class="pull-right">姓名:</label></td>
		         <td colspan="2">
		        	${user.name}
		        </td>
		      </tr>
		       <tr>
		       	 <td class="active"><label class="pull-right">电子邮箱:</label></td>
		         <td colspan="2">
		         	${user.email}
		         </td>
		       	 <td class="active width-15"><label class="pull-right">身份证号:</label></td>
		         <td colspan="2">
		        	${user.idNum} 
		         </td>
		      </tr>
		       <tr>
		         <td class="active"><label class="pull-right">联系电话</label></td>
		         <td colspan="2">
		         	${user.phone} 
		         </td>
				<td class="active"><label class="pull-right">性别:</label></td>
		         <td colspan="2">
			         ${fns:getDictLabel(user.sex, 'sex', '')}
				</td>
				</tr>
				<tr>
				   <td class="active"><label class="pull-right">民族:</label></td>
		         <td colspan="2" >
		         	${user.familyName}
		         </td>
		         <td class="active"><label class="pull-right">状态:</label></td>
		         <td colspan="2" >
		         	 ${fns:getDictLabel(user.loginFlag, 'loginFlag', '')}
		         </td>
		         </tr>
		        <tr>
		         <td class="active"><label class="pull-right">入职日期：</label></td>
		         <td colspan="2">
		         	<fmt:formatDate value="${user.entryDate}" pattern="yyyy-MM-dd"/>
		         </td>
				 <td class="active"><label class="pull-right">备案日期：</label></td>
				 <td colspan="2">
				 <fmt:formatDate value="${user.filingDate}" pattern="yyyy-MM-dd"/>
				 </td>
		      </tr>
		        <tr>
		         <td class="active"><label class="pull-right">机构分类：</label></td>
		         <td colspan="2">
		         	${user.officeTypeName}
		         </td>
				 <td class="active"><label class="pull-right">组织机构:</label></td>
				 <td colspan="2">
					${user.officeName}
				 </td>
		      </tr>
		      <tr id="postTr1">
		         <td class="active width-15"><label class="pull-right">第一岗位分类:</label></td>
		         <td>
		         	<span id="posttypename1"></span>
		         </td>
		         <td class="active width-15" ><label class="pull-right">第一岗位名称:</label></td>
		         <td>
		         	<span id="postname1"></span>
		         </td>
		         <td class="active width-15" ><label class="pull-right">第一岗位级别:</label></td>
		         <td>
		         	<span id="postlevelname1"></span>
		         </td>
		      </tr>
		       <tr id="postTr2">
		         <td class="active width-15"><label class="pull-right">第二岗位分类:</td>
		         <td>
		         	<span id="posttypename2"></span>
		         </td>
		         <td class="active width-15" ><label class="pull-right">第二岗位名称:</td>
		         <td>
		         	<span id="postname2"></span>
		         </td>
		         <td class="active width-15" ><label class="pull-right">第二岗位级别:</td>
		         <td>
		         	<span id="postlevelname2"></span>
		         </td>
		      </tr>
		      
		     <tr id="postTr3">
		         <td class="active width-15"><label class="pull-right">第三岗位分类:</label></td>
		         <td>
		         <span id="posttypename3"></span>
		         </td>
		         <td class="active width-15" ><label class="pull-right">第三岗位名称:</label></td>
		         <td>
		         	<span id="postname3"></span>
		         </td>
		         <td class="active width-15" ><label class="pull-right">第三岗位级别:</label></td>
		         <td>
		         	<span id="postlevelname3"></span>
		         </td>
		      </tr>
		      	 <td class="active"><label class="pull-right">备注:</label></td>
		         <td colspan="5">${user.remarks }</td>
		      
		      </tr>
		      
	</form:form>
</body>
</html>