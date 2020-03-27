<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(!validateForm.form()){
			  return false;
		  }
		  if(isCardNo()==false){
			  return false;
		  }
		  if(isnum() == false){
			  return false;
		  }
		  if($("#officeId").val() == ''){
			  layer.alert("请选择机构分类");
			  return false;
		  }
		  if(validateForm.form()){
			  $("#inputForm").submit();
			  return true;
		  }
		  return false;
		}
		
		function isCardNo()  
		  {  
			  var card = $("#idNum").val();
		     // 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X  
		     var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;  
		     if(reg.test(card) === false)  
		     {  
		         layer.alert("身份证输入不合法");  
		         return  false;  
		     }  
		     return true;
		  }  
		
			var entryDate = {
				  elem: '#entryDate',
				  format: 'YYYY-MM-DD',
				  istoday: false,
				  choose: function(datas){
					  filingDate.min = datas; //开始日选好后，重置结束日的最小日期
					  filingDate.start = datas;//将结束日的初始值设定为开始日
				  },event: 'focus'
				};
			var filingDate = {
			  elem: '#filingDate',
			  format: 'YYYY-MM-DD',
			  istoday: false,
			  choose: function(datas){
				  entryDate.max = datas; //结束日选好后，重置开始日的最大日期
			  },
			  event: 'focus'
			};
			
		$(document).ready(function() {
			$("#no").focus();
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

			//在ready函数中预先调用一次远程校验函数，是一个无奈的回避案。(刘高峰）
			//否则打开修改对话框，不做任何更改直接submit,这时再触发远程校验，耗时较长，
			//submit函数在等待远程校验结果然后再提交，而layer对话框不会阻塞会直接关闭同时会销毁表单，因此submit没有提交就被销毁了导致提交表单失败。
			/* $("#inputForm").validate().element($("#loginName"));
			$("#inputForm").validate().element($("#idNum"));
			$("#inputForm").validate().element($("#email"));
			$("#inputForm").validate().element($("#phone")); */
			 $.ajax({
	               type: "POST",
	               url: "${ctx}/sys/postType/findAllList",
	               success:function(data){
	            	  var jsonObj = eval(data);
	            	  for(var i=0;i<jsonObj.length;i++){
	            		  	var obj = jsonObj[i];
	            		  	$("#postType1").append("<option value='"+obj['id']+"'>"+obj.name+"</option>");//添加下拉列表
	            		  	$("#postType2").append("<option value='"+obj['id']+"'>"+obj['name']+"</option>");//添加下拉列表
	            		  	$("#postType3").append("<option value='"+obj['id']+"'>"+obj['name']+"</option>");//添加下拉列表
	            	  }
		      		
	            	   //绑定下拉列表的值
	      			var objs = $("#selectPostInfo").val();
	      			if(objs){
	      				objs = eval(objs);
		      			for(var i=1;i<=objs.length;i++){
		      				var o = objs[i-1];
	      					$("#postType"+i).val(o['postType']);
	      					 changePostType($("#postType"+i),i);
	      					$("#postId"+i).val(o['postId']);
	      					changePostLevel($("#postId"+i),i);
	      					$("#postLevel"+i).val(o['postLevel']);
		      			}
	      			 }
	               }
	          })
	      	laydate(entryDate);
			laydate(filingDate);
		});
		function initLayDate(str){
			if(str==1){
				entryDate.max = c; 
			}else if(str==2){
				filingDate.min = $("#entryDate").val();
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
			         var obj = $('input:radio[name="rdo"]:checked',body);
			         $("#officeId").val($(obj).attr("id"));
					 $("#officeName").html($(obj).attr("value"));
			         setTimeout(function(){top.layer.close(index)}, 100);//延时0.1秒，对应360 7.1版本bug
			         
				  },
				  cancel: function(index){ 
					  
			      }
			}); 	
		}
		function changePostType(obj,str){
			 $.ajax({
	               type: "POST",
	               url: "${ctx}/sys/postInfo/findPostListByType",
	               data: {"postType":$(obj).val()},
	               async: false,
	               success:function(data){
	            	   var jsonObj = eval(data);
	            	   $("#postId"+str).html("");
	            	   $("#postId"+str).append("<option value=''>--请选择--</option>");
	            	  for(var i=0;i<jsonObj.length;i++){
	            		  	var obj = jsonObj[i];
	            		  	$("#postId"+str).append("<option value='"+obj['id']+"'>"+obj['name']+"</option>");//添加下拉列表
	            	  }
	               }
	          })
	          changePostLevel($("#postId"+str),str)
		}
		function changePostLevel(obj,str){
			 $.ajax({
	               type: "POST",
	               url: "${ctx}/sys/postInfo/findPostLevelByPost",
	               data: {"postId":$(obj).val()},
	               async: false,
	               success:function(data){
	            	   var jsonObj = eval(data);
	            	   $("#postLevel"+str).html("");
	            	   $("#postLevel"+str).append("<option value=''>--请选择--</option>")
	            	  for(var i=0;i<jsonObj.length;i++){
	            		  	var obj = jsonObj[i];
	            		  	$("#postLevel"+str).append("<option value='"+obj['id']+"'>"+obj['name']+"</option>");//添加下拉列表
	            	  }
	               }
	          })
		}

		function  isnum(){
			var number = $("#phone").val();
			var bl = true;
			if(number != "" && number != null && number != undefined){
				var reg = /^((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)$/;
				if(!reg.test(number)){
					$("#span4").show();
					$("#phone").val("");//不是数字
					$("#phone").focus();
					number = "";
					bl =  false;
				}else{
					bl =  true;
					$("#span4").hide();
				}
			}
			if(bl){
				//验证是否重复
				if(number){
					$.ajax({
					     type: 'POST',
					     url: "${ctx}/sys/user/checkUserPhone",
					     async: false,
					     data:{oldPhone: encodeURIComponent('${user.phone}'),phone:number},
					     success: function(data){
					    	 if(data != "true"){
									$("#span5").show();
									$("#phone").focus();
									bl = false;
								}else{
									$("#span5").hide();
									bl = true;
								}
					    }
					});
				}else{
					$("#span5").hide();
					bl = true;
				}
			}
			return bl;
		}
	
		
		function checkLoginName(){
			var loginName = $("#loginName").val();
			var bl = false;
			if(loginName){
				$.ajax({
				     type: 'POST',
				     url: "${ctx}/sys/user/checkLoginName",
				     async: false,
				     data:{oldLoginName: encodeURIComponent('${user.loginName}'),loginName:loginName},
				     success: function(data){
				    	 if(data != "true"){
								$("#span1").show();
								$("#loginName").focus();
								bl = false;
							}else{
								$("#span1").hide();
								bl = true;
							}
				    }

				});
			}else{
				$("#span1").hide();
				bl = true;
			}
			return bl;	
		}
		
		
		function checkIdNum(){
			var idNum = $("#idNum").val();
			var bl = false;
			if(idNum){
				$.ajax({
				     type: 'POST',
				     url: "${ctx}/sys/user/checkUserIdNum",
				     async: false,
				     data:{oldIdNum: encodeURIComponent('${user.idNum}'),idNum:idNum},
				     success: function(data){
				    	 if(data != "true"){
								$("#span2").show();
								$("#idNum").focus();
								bl = false;
							}else{
								$("#span2").hide();
								bl = true;
							}
				    }

				});
			}else{
				$("#span2").hide();
				bl = true;
			}
			return bl;	
		}
		
		
		function checkEmail(){
			var email = $("#email").val();
			var bl = false;
			if(email){
				$.ajax({
				     type: 'POST',
				     url: "${ctx}/sys/user/checkUserEmail",
				     async: false,
				     data:{oldEmail: '${user.email}',email:email},
				     success: function(data){
				    	 if(data != "true"){
								$("#span3").show();
								$("#email").focus();
								bl = false;
							}else{
								$("#span3").hide();
								bl = true;
							}
				    }

				});
			}else{
				$("#span3").hide();
				bl = true;
			}
			return bl;	
		}
		
	</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/user/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		    <tr>
		         <td class="active width-15"><label class="pull-right"><font color="red">*</font>登录ID:</label></td>
		         <td colspan="2">
		         	<input id="oldLoginName" name="oldLoginName" type="hidden" value="${user.loginName}">
					 <form:input path="loginName" onblur="checkLoginName()" htmlEscape="false" maxlength="50" class="form-control required"/>
					 <span id="span1" style="display: none"><label id="name-error" class="error">该登录ID已存在</label></span>
					 </td>
					<td class="active"><label class="pull-right">入职日期:</label></td>
		         <td colspan="2">
		        	 <input  readonly="readonly"  id="entryDate" name="entryDate"  onmousemove ="initLayDate(1)" type="text" maxlength="20" class="laydate-icon form-control layer-date "
							value="<fmt:formatDate value="${user.entryDate}" pattern="yyyy-MM-dd"/>"/></td>
		      </tr>
		       <tr>
		       	 <td class="active"><label class="pull-right"><font color="red">*</font>电子邮箱:</label></td>
		         <td colspan="2">
		         	<form:input path="email" onblur="checkEmail()" id="email" htmlEscape="false" maxlength="50" class="form-control email required"/>
		         	 <span id="span3" style="display: none"><label id="name-error" class="error">该电子邮箱已存在</label></span>
		         </td>
		       	 <td class="active width-15"><label class="pull-right">备案日期:</label></td>
		         <td colspan="2"><input  readonly="readonly"  id="filingDate" name="filingDate" onmousemove ="initLayDate(2)" type="text" maxlength="20" class="laydate-icon form-control layer-date "
							value="<fmt:formatDate value="${user.filingDate}" pattern="yyyy-MM-dd"/>"/></td>
		      </tr>
		       <tr>
		         <td class="active"><label class="pull-right"><font color="red">*</font>身份证号:</label></td>
		         <td colspan="2">
		         		<form:input path="idNum" id="idNum" onblur="checkIdNum()" htmlEscape="false" maxlength="18" class="form-control required"/>
		         		 <span id="span2" style="display: none"><label id="name-error" class="error">该身份证号已存在</label></span>
		         		</td>
		         </td>
				<td class="active"><label class="pull-right">状态:</label></td>
		         <td colspan="2">
			         <form:select path="loginFlag"  class="form-control">
						<form:options items="${fns:getDictList('loginFlag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</td>
				</tr>
				<tr>
				   <td class="active"><label class="pull-right">民族:</label></td>
		         <td colspan="2" >
		         	<form:input path="familyName" htmlEscape="false" maxlength="20" class="form-control "/>
		         </td>
		         <td class="active"><label class="pull-right">联系电话:</label></td>
		         <td colspan="2" >
		         	<form:input path="phone" id="phone" htmlEscape="false" maxlength="20" class="form-control phone" onblur="isnum();"/>
		         	<span id="span4" style="display: none"><label id="name-error" class="error">请输入正确的联系电话</label></span>
		         	<span id="span5" style="display: none"><label id="name-error" class="error">该联系电话已存在</label></span>
		         	</td>
		         </tr>
		        <tr>
		         <td class="active"><label class="pull-right">
		         	<c:if test="${empty user.id }"><font color="red">*</font></c:if>
		         		密码:</label></td>
		         <td colspan="2">
		           <input id="newPassword" name="newPassword" type="text" autocomplete="off" onfocus="this.type='password'" value="" maxlength="50" minlength="3" class="form-control"/>
					<c:if test="${not empty user.id}"><span class="help-inline">若不修改密码，请留空。</span></c:if></td>
				 <td class="active"><label class="pull-right">性别:</label></td>
				 <td colspan="2">
				 <form:select path="sex"  class="form-control">
					<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select></td>
		      </tr>
		      
		      	 <td class="active"><label class="pull-right"><font color="red">*</font>姓名:</label></td>
		         <td colspan="2"><form:input path="name" htmlEscape="false" maxlength="50" class="form-control required"/></td>
		        <td class="active"><label class="pull-right">备注:</label></td>
		         <td colspan="2"><form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="300" class="form-control"/></td>
		      </tr>
		      <tr>
		      	<td  class="active width-15" >
		      		<label class="pull-right"><font color="red">*</font>机构分类:</label>
		      	</td>
		      	<td colspan="2">
		      	<input type="hidden" name="officeId" id="officeId" value="${user.office.id}"/>
		      		<span id="officeName">${user.officeName}</span>&nbsp;&nbsp;&nbsp;&nbsp;<a  href="#" onclick="openRoleDialog('选择机构', '${ctx}/sys/office/selUserOfficeList?officeId=${user.officeId}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 选择</a>
		      	</td>
		      	<td colspan="2"></td>
		      </tr>
		      <input type="hidden" name="selectPostInfo"  id="selectPostInfo" value="${lsUserPostStr}">
		      <%-- <c:if test="${not empty user.id}">
		       <tr>
		         <td class=""><label class="pull-right">创建时间:</label></td>
		         <td><span class="lbl"><fmt:formatDate value="${user.createDate}" type="both" dateStyle="full"/></span></td>
		         <td class=""><label class="pull-right">最后登陆:</label></td>
		         <td><span class="lbl">IP: ${user.loginIp}&nbsp;&nbsp;&nbsp;&nbsp;时间：<fmt:formatDate value="${user.loginDate}" type="both" dateStyle="full"/></span></td>
		      </tr>
		     </c:if> --%>
		      
	</form:form>
</body>
</html>