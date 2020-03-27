<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(checkCard()==false){
			  return false;
		  }
		  if(isnum() === false){
			  return false;
		  }
		  if($("#officeId").val() == ''){
			  layer.alert("请选择机构分类");
			  return false;
		  }
		  var postType1 = $('#postType1 option:selected').val();//选中的值
		  if(postType1 == ''){
			  layer.alert("请选择第一岗位分类");
			  return false;
		  }
		  var postId1 = $('#postId1 option:selected').val();//选中的值
		  if(postId1 == ''){
			  layer.alert("请选择第一岗位名称");
			  return false;
		  }
		  var postLevel1 = $('#postLevel1 option:selected').val();//选中的值
		  if(postLevel1 == ''){
			  layer.alert("请选择第一岗位级别");
			  return false;
		  }
		  if(validateForm.form()){
			  $("#inputForm").submit();
			  return true;
		  }
		  return false;
		}
	
		$(document).ready(function() {
			$("#no").focus();
			validateForm = $("#inputForm").validate({
				rules: {
					loginName: {remote: "${ctx}/sys/user/checkLoginName?oldLoginName=" + encodeURIComponent('${user.loginName}')}//设置了远程验证，在初始化时必须预先调用一次。
				},
				messages: {
					loginName: {remote: "用户登录名已存在"},
					confirmNewPassword: {equalTo: "输入与上面相同的密码"}
				},
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
			$("#inputForm").validate().element($("#loginName"));
			var start = {
					  elem: '#entryDate',
					  format: 'YYYY-MM-DD',
					  max: '2099-06-16', //最大日期
					  istime: true,
					  istoday: false,
					  choose: function(datas){
					     end.min = datas; //开始日选好后，重置结束日的最小日期
					     end.start = datas //将结束日的初始值设定为开始日
					  },event: 'focus'
					};
					var end = {
					  elem: '#filingDate',
					  format: 'YYYY-MM-DD',
					  max: '2099-06-16',
					  istime: true,
					  istoday: false,
					  choose: function(datas){
					    start.max = datas; //结束日选好后，重置开始日的最大日期
					  },event: 'focus'
					};
			laydate(start);
			laydate(end);
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
	       
		});
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
		var vcity={ 11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",  
	            21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",  
	            33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",  
	            42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",  
	            51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",  
	            63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"  
	           };  
	  
		checkCard = function()  
		{  
		    var card = document.getElementById('idNum').value;  
		    //是否为空  
		    if(card === '')  
		    {  
		        layer.alert('请输入身份证号，身份证号不能为空');  
		        document.getElementById('idNum').focus;  
		        return false;  
		    }  
		    //校验长度，类型  
		    if(isCardNo(card) === false)  
		    {  
		        layer.alert('您输入的身份证号码不正确，请重新输入');  
		        document.getElementById('idNum').focus;  
		        return false;  
		    }  
		    //检查省份  
		    if(checkProvince(card) === false)  
		    {  
		        layer.alert('您输入的身份证号码不正确,请重新输入');  
		        document.getElementById('idNum').focus;  
		        return false;  
		    }  
		    //校验生日  
		    if(checkBirthday(card) === false)  
		    {  
		        layer.alert('您输入的身份证号码生日不正确,请重新输入');  
		        document.getElementById('idNum').focus();  
		        return false;  
		    }  
		    //检验位的检测  
		    if(checkParity(card) === false){  
		        layer.alert('您的身份证校验位不正确,请重新输入');  
		        document.getElementById('idNum').focus();  
		        return false;  
		    }  
		    return true;
		};  
		  
		  
		//检查号码是否符合规范，包括长度，类型  
		isCardNo = function(card)  
		{  
		    //身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X  
		    var reg = /(^\d{15}$)|(^\d{17}(\d|X)$)/;  
		    if(reg.test(card) === false)  
		    {  
		        return false;  
		    }  
		  
		    return true;  
		};  
		  
		//取身份证前两位,校验省份  
		checkProvince = function(card)  
		{  
		    var province = card.substr(0,2);  
		    if(vcity[province] == undefined)  
		    {  
		        return false;  
		    }  
		    return true;  
		};  
		  
		//检查生日是否正确  
		checkBirthday = function(card)  
		{  
		    var len = card.length;  
		    //身份证15位时，次序为省（3位）市（3位）年（2位）月（2位）日（2位）校验位（3位），皆为数字  
		    if(len == '15')  
		    {  
		        var re_fifteen = /^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/;   
		        var arr_data = card.match(re_fifteen);  
		        var year = arr_data[2];  
		        var month = arr_data[3];  
		        var day = arr_data[4];  
		        var birthday = new Date('19'+year+'/'+month+'/'+day);  
		        return verifyBirthday('19'+year,month,day,birthday);  
		    }  
		    //身份证18位时，次序为省（3位）市（3位）年（4位）月（2位）日（2位）校验位（4位），校验位末尾可能为X  
		    if(len == '18')  
		    {  
		        var re_eighteen = /^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/;  
		        var arr_data = card.match(re_eighteen);  
		        var year = arr_data[2];  
		        var month = arr_data[3];  
		        var day = arr_data[4];  
		        var birthday = new Date(year+'/'+month+'/'+day);  
		        return verifyBirthday(year,month,day,birthday);  
		    }  
		    return false;  
		};  
		  
		//校验日期  
		verifyBirthday = function(year,month,day,birthday)  
		{  
		    var now = new Date();  
		    var now_year = now.getFullYear();  
		    //年月日是否合理  
		    if(birthday.getFullYear() == year && (birthday.getMonth() + 1) == month && birthday.getDate() == day)  
		    {  
		        //判断年份的范围（3岁到100岁之间)  
		        var time = now_year - year;  
		        if(time >= 3 && time <= 100)  
		        {  
		            return true;  
		        }  
		        return false;  
		    }  
		    return false;  
		};  
		  
		//校验位的检测  
		checkParity = function(card)  
		{  
		    //15位转18位  
		    card = changeFivteenToEighteen(card);  
		    var len = card.length;  
		    if(len == '18')  
		    {  
		        var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);   
		        var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');   
		        var cardTemp = 0, i, valnum;   
		        for(i = 0; i < 17; i ++)   
		        {   
		            cardTemp += card.substr(i, 1) * arrInt[i];   
		        }   
		        valnum = arrCh[cardTemp % 11];   
		        if (valnum == card.substr(17, 1))   
		        {  
		            return true;  
		        }  
		        return false;  
		    }  
		    return false;  
		};  
		  
		//15位转18位身份证号  
		changeFivteenToEighteen = function(card)  
		{  
		    if(card.length == '15')  
		    {  
		        var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);   
		        var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');   
		        var cardTemp = 0, i;     
		        card = card.substr(0, 6) + '19' + card.substr(6, card.length - 6);  
		        for(i = 0; i < 17; i ++)   
		        {   
		            cardTemp += card.substr(i, 1) * arrInt[i];   
		        }   
		        card += arrCh[cardTemp % 11];   
		        return card;  
		    }  
		    return card;  
		};
		function  isnum(){
			var number = $("#phone").val();
			if(number != "" && number != null && number != undefined){
				var reg = /^((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)$/;
				if(!reg.test(number)){
					layer.alert("请输入正确的手机号码!");
					$("#phone").val("");//不是数字
					$("#phone").focus();
					return false;
				}
			}
			return true;
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
					 <form:input path="loginName" htmlEscape="false" maxlength="50" class="form-control required"/></td>
					<td class="active"><label class="pull-right"><font color="red">*</font>入职日期:</label></td>
		         <td colspan="2">
		        	 <input  readonly="readonly"  id="entryDate" name="entryDate" type="text" maxlength="20" class="laydate-icon form-control layer-date required"
							value="<fmt:formatDate value="${user.entryDate}" pattern="yyyy-MM-dd"/>"/></td>
		      </tr>
		       <tr>
		       	 <td class="active"><label class="pull-right"><font color="red">*</font>电子邮箱:</label></td>
		         <td colspan="2">
		         	<form:input path="email" htmlEscape="false" maxlength="100" class="form-control email required"/>
		         </td>
		       	 <td class="active width-15"><label class="pull-right"><font color="red">*</font>备案日期:</label></td>
		         <td colspan="2"><input  readonly="readonly"  id="filingDate" name="filingDate" type="text" maxlength="20" class="laydate-icon form-control layer-date required"
							value="<fmt:formatDate value="${user.filingDate}" pattern="yyyy-MM-dd"/>"/></td>
		      </tr>
		       <tr>
		         <td class="active"><label class="pull-right"><font color="red">*</font>身份证号:</label></td>
		         <td colspan="2">
		         		<form:input path="idNum" htmlEscape="false" maxlength="50" class="form-control required" onblur="checkCard();"/></td>
		         </td>
				<td class="active"><label class="pull-right">状态:</label></td>
		         <td colspan="2">
			         <form:select path="loginFlag"  class="form-control">
						<form:options items="${fns:getDictList('loginFlag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</td>
				</tr>
				<tr>
				   <td class="active"><label class="pull-right"><font color="red">*</font>民族:</label></td>
		         <td colspan="2" >
		         	<form:input path="familyName" htmlEscape="false" maxlength="100" class="form-control required"/>
		         </td>
		         <td class="active"><label class="pull-right">联系电话:</label></td>
		         <td colspan="2" ><form:input path="phone" htmlEscape="false" maxlength="20" class="form-control phone" onblur="isnum();"/></td>
		         </tr>
		        <tr>
		         <td class="active"><label class="pull-right">
		         	<c:if test="${empty user.id }"><font color="red">*</font></c:if>
		         		密码:</label></td>
		         <td colspan="2"><input id="newPassword" name="newPassword" type="password" value="" maxlength="50" minlength="3" class="form-control ${empty user.id?'required':''}"/>
					<c:if test="${not empty user.id}"><span class="help-inline">若不修改密码，请留空。</span></c:if></td>
				 <td class="active"><label class="pull-right"><font color="red">*</font>性别:</label></td>
				 <td colspan="2">
				 <form:select path="sex"  class="form-control">
					<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select></td>
		      </tr>
		     
		      <tr id="postTr1">
		         <td class="active width-15"><label class="pull-right"><font color="red">*</font>第一岗位分类:</label></td>
		         <td>
		         	 <select id="postType1" name="postType" class="form-control" onchange="changePostType(this,'1')"><option value="">--请选择--</option></select>
		         </td>
		         <td class="active width-15" ><label class="pull-right"><font color="red">*</font>第一岗位名称:</label></td>
		         <td>
		         	 <select id="postId1" name="postId" class="form-control" onchange="changePostLevel(this,'1')"><option value="">--请选择--</option></select>
		         </td>
		         <td class="active width-15" ><label class="pull-right"><font color="red">*</font>第一岗位级别:</label></td>
		         <td>
		         	<select id="postLevel1" name="postLevel" class="form-control">
		         		<option value="">--请选择--</option>
		         	</select>
		         </td>
		      </tr>
		       <tr id="postTr2">
		         <td class="active width-15"><label class="pull-right">第二岗位分类:</td>
		         <td>
		         	<input type="hidden" name="postInfoStr" id="postInfoStr">
		         	<select id="postType2" name="postType" class="form-control" onchange="changePostType(this,'2')"><option value="">--请选择--</option></select>
		         </td>
		         <td class="active width-15" ><label class="pull-right">第二岗位名称:</td>
		         <td>
		         	 <select id="postId2" name="postId" class="form-control" onchange="changePostLevel(this,'2')"><option value="">--请选择--</option></select>
		         </td>
		         <td class="active width-15" ><label class="pull-right">第二岗位级别:</td>
		         <td>
		         	<select id="postLevel2" name="postLevel" class="form-control">
		         		<option value="">--请选择--</option>
		         	</select>
		         </td>
		      </tr>
		      
		     <tr id="postTr3">
		         <td class="active width-15"><label class="pull-right">第三岗位分类:</label></td>
		         <td>
		         	 <select id="postType3" name="postType" class="form-control" onchange="changePostType(this,'3')"><option value="">--请选择--</option></select>
		         </td>
		         <td class="active width-15" ><label class="pull-right">第三岗位名称:</label></td>
		         <td>
		         	 <select id="postId3" name="postId" class="form-control" onchange="changePostLevel(this,'3')"><option value="">--请选择--</option></select>
		         </td>
		         <td class="active width-15" ><label class="pull-right">第三岗位级别:</label></td>
		         <td>
		         	<select id="postLevel3" name="postLevel" class="form-control">
		         		<option value="">--请选择--</option>
		         	</select>
		         </td>
		      </tr>
		      	 <td class="active"><label class="pull-right">姓名:</label></td>
		         <td colspan="2"><form:input path="name" htmlEscape="false" maxlength="50" class="form-control"/></td>
		        <td class="active"><label class="pull-right">备注:</label></td>
		         <td colspan="2"><form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="form-control"/></td>
		      </tr>
		      <tr>
		      	<td  class="active width-15" >
		      		<label class="pull-right"><font color="red">*</font>机构分类:</label>
		      	</td>
		      	<td width="140px">
					<sys:treeselect id="office" name="officeType"
					 value="${office.officeType}" labelName="officeTypeName" labelValue="${office.officeTypeName}"
					title="机构" url="/sys/officeType/treeData" extId="${office.officeType}"  cssClass="form-control"  />
				</td>
<!-- 		      	<td colspan="2"> -->
<%-- 		      	<input type="hidden" name="officeId" id="officeId" value="${user.officeId }"/> --%>
<%-- 		      		<span id="officeName">${user.officeName}</span>&nbsp;&nbsp;&nbsp;&nbsp;<a  href="#" onclick="openRoleDialog('选择机构', '${ctx}/sys/office/selUserOfficeList?officeId=${user.officeId}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 选择机构</a> --%>
<%-- 		      		<span id="officeName">${user.officeName}</span>&nbsp;&nbsp;&nbsp;&nbsp;<a  href="#" onclick="openRoleDialog('选择机构', '${ctx}/sys/officeType/toSelectOfficeType','400px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 选择</a> --%>
<!-- 		      	</td> -->
		      	<td colspan="2">&nbsp;</td>
		      </tr>
		      <input type="hidden" name="selectPostInfo"  id="selectPostInfo" value="${lsUserPostStr}">
		       <tr>
		  			<td colspan="6">
<!-- 		  				<div id="showOfficeDiv" style="display: display"> -->
						<div id="showOfficeDiv">
		  					<iframe width="100%" id ="iframeXxOffeic" height="300px" style="border: none" src="${ctx}/sys/office/selOfficeList?type=qx&a="+Math.random()></iframe>
		  				</div>
		  			</td>
		  		</tr>
		     </tbody>
		     </table>
	</form:form>
</body>
</html>