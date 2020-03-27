<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>区域管理管理</title>
	<meta name="decorator" content="default"/>
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
				rules: {
					code: {remote: "${ctx}/sys/district/checkCode?oldCode=" + encodeURIComponent('${district.code}')},//设置了远程验证，在初始化时必须预先调用一次。
				},
				messages: {
					code: {remote: "该编码已存在"}
				}, 
				submitHandler: function(form){
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
			$("#inputForm").validate().element($("#code"));
			var jsonStr = ${lsMapStr};
			var provinceJson = {};
			var cityJson = {};
			var provinceText = "";
			var cityText = "";
			 if(jsonStr){
			   for(var i=0;i<jsonStr.length;i++){
				   var obj = jsonStr[i];
				   provinceJson[obj['provinceid']] = obj['proincename'];
				   cityJson[obj['cityid']] = obj['cityname'];
				   cityText+=obj['cityname']+",";
			 	 }
			   
				 showProvice(provinceJson);
				 $("#t_province").show();
				 $("#t_city").show();
				 for(var x in provinceJson){
					 provinceText+=provinceJson[x]+",";
					 showLoadCity(x,provinceJson[x],cityJson);
				 }
			  }
			 $("#province").val(provinceText);
			 $("#city").val(cityText);
			 
			 var strValue = "";
			for(var x in provinceJson){
				strValue+=x+",";
			}
			 $("#provinceids").val(strValue);
			 var cityStrValue = "";
			 for(var x in cityJson){
				 cityStrValue+=x+",";
				}
			 $("#cityids").val(cityStrValue);
		});
		
		function showDiv(id){
			if(id=='t_city'){
				var a =$('#t_province').css('display');
				if(a=='none'){
					$("#t_cityShow").show();
				}
			}else if(id=='t_province'){
				$("#t_cityShow").hide();
				var a =$('#t_province').css('display');
				if(a=='none'){
					$("#t_province").show();
					showProvice();
				}
			}
		}
		function hidenDiv(id){
			if(id=='t_city'){
				var a =$('#t_province').css('display');
				if(a=='none'){
					$("#t_cityShow").hide();
				}
			}
			$("#"+id).hide();
		}
		function showProvice(jsonCheck){
			$.ajax({
				  url: "${ctx}/sys/district/getAllProvice",
				  type: 'POST',
				  dataType: 'json',
				  data: {},
				  success: function(responseText){
					  if(responseText){
						   var html = "";
						   for(var i=0;i<responseText.length;i++){
							   var obj = responseText[i];
							  if(jsonCheck && jsonCheck[obj['provinceid']]){
								  html+="<li style=\"width:130px;float:left;\"><input  id='id_"+obj['provinceid']+"' value='"+obj['provinceid']+"' name=\"province_chk\" onclick=\"getCheckProvince(this)\" checked='checked' type=\"checkbox\"><label for='id_"+obj['provinceid']+"' style=\"cursor: pointer;\">"+obj['proincename']+"</label></li>";
							  }else{
							  	html+="<li style=\"width:130px;float:left;\"><input  id='id_"+obj['provinceid']+"' value='"+obj['provinceid']+"' name=\"province_chk\" onclick=\"getCheckProvince(this)\" type=\"checkbox\"><label for='id_"+obj['provinceid']+"' style=\"cursor: pointer;\">"+obj['proincename']+"</label></li>";
							 	}
							 }
						  $("#t_province_ul").html(html);  
					  }
				  }
			}); 
		}
		
		function showCity(chk){
			$.ajax({
				  url: "${ctx}/sys/district/getCityByProvinceId",
				  type: 'POST',
				  dataType: 'json',
				  data: {ids:chk.value},
				  success: function(responseText){
					  if(responseText){
						  var html = "";
						   for(var i=0;i<responseText.length;i++){
							  var obj = responseText[i];
							  html +="<li style='width:160px;float: left;'><input type='checkbox' onclick='getCheckedCity()' value='"+obj['cityid']+"'  name='citychk' id='cityid_"+obj['cityid']+"'><label for='cityid_"+obj['cityid']+"' style='cursor:pointer;font-weight: normal; '>"+obj['cityname']+"</label></li>";
						  }
						  var allHtml = "<div class='t_city_div' id='city_div_"+$(chk).val()+"'>"+
							"<div class='t_city_div_title' >"+$(chk).next().html()+"&nbsp;&nbsp;<input type='checkbox' id='province_city_"+$(chk).val()
							+"' name='checkAllCityChk' onclick='checkAllCity(this)'><label for='province_city_"+$(chk).val()+"' style='cursor:pointer;font-weight: normal; '>全选</label>&nbsp;&nbsp;&nbsp;&nbsp;<span id='span_city_"+$(chk).val()+"' style='cursor:pointer;' onclick=\"showCityUl('"+$(chk).val()+"')\">︽<pan></div>"+
							"<ul style='width:330px;list-style-type: none;float:left;padding-left: 10px' id='t_city_ul_"+$(chk).val()+"'>"+
								html
							"</ul>"+
						"</div>";
						  $("#t_city").append(allHtml); 
						  $("ul[id*='t_city_ul_']").each(function(){
							  var id = $(this).attr("id");
							  var checkId = "t_city_ul_"+$(chk).val();
							  var provinceId = id.replace("t_city_ul_","");
							  if(id!=checkId){
								  $(this).hide();
								  $("#span_city_"+provinceId).html("︾");
							  }else{
								  $("#span_city_"+provinceId).html("︽");
							  }
						  });
					  }
				  }
			}); 
			
		}
		function showLoadCity(id,name,cityJson){
			$.ajax({
				  url: "${ctx}/sys/district/getCityByProvinceId",
				  type: 'POST',
				  dataType: 'json',
				  data: {ids:id},
				  success: function(responseText){
					  if(responseText){
						  var html = "";
						   for(var i=0;i<responseText.length;i++){
							  var obj = responseText[i];
							  if(cityJson[obj['cityid']]){
								  html +="<li style='width:160px;float: left;'><input checked='checked' type='checkbox' onclick='getCheckedCity()' value='"+obj['cityid']+"'  name='citychk' id='cityid_"+obj['cityid']+"'><label for='cityid_"+obj['cityid']+"' style='cursor:pointer;font-weight: normal; '>"+obj['cityname']+"</label></li>";
							  }else{
								  html +="<li style='width:160px;float: left;'><input type='checkbox' onclick='getCheckedCity()' value='"+obj['cityid']+"'  name='citychk' id='cityid_"+obj['cityid']+"'><label for='cityid_"+obj['cityid']+"' style='cursor:pointer;font-weight: normal; '>"+obj['cityname']+"</label></li>";
							  }
							  
						  }
						  var allHtml = "<div class='t_city_div' id='city_div_"+id+"'>"+
							"<div class='t_city_div_title' >"+name+"&nbsp;&nbsp;<input type='checkbox' id='province_city_"+id
							+"' name='checkAllCityChk' onclick='checkAllCity(this)'><label for='province_city_"+id+"' style='cursor:pointer;font-weight: normal; '>全选</label>&nbsp;&nbsp;&nbsp;&nbsp;<span id='span_city_"+id+"' style='cursor:pointer;' onclick=\"showCityUl('"+id+"')\">︾<pan></div>"+
							"<ul style='width:330px;list-style-type: none;float:left;padding-left: 10px;display: none' id='t_city_ul_"+id+"'>"+
								html
							"</ul>"+
						"</div>";
						  $("#t_city").append(allHtml); 
						  
					  }
				  }
			}); 
			
		}
		function checkAllCity(obj){
			 $('input[type=checkbox]',$(obj).parent().parent()).each(function(i){
				  $(this).attr("checked",obj.checked);  
			 });
			 getCheckedCity();
		}
		
		function getCheckProvince(chk){
			var strValue = "";
			var strText = "";
			 $('input:checkbox[name=province_chk]:checked').each(function(i){
			    var obj = $(this);
			    strValue += obj.val()+",";
			    strText += $(this).next().html()+",";       
			  });
			 if(strValue){
				 strValue = strValue.substring(0, strValue.length-1);
			 };
			 if(strText){
				 strText = strText.substring(0, strText.length-1);
			 }
			 $("#province").val(strText);
			 if(chk.checked){
				 if($('#t_city').css('display')=='none'){
					 $('#t_city').show();
				 }
				 showCity(chk);
			 }else{
				 $("#city_div_"+chk.value).remove();
			 }
			 getCheckedCity();
		}
		
	function getCheckedCity(){
		var strValue = "";
		var strText = "";
		 $('input:checkbox[name=citychk]:checked').each(function(i){
		    var obj = $(this);
		    strValue += obj.val()+",";
		    strText += $(this).next().html()+",";       
		  });
		 $("#city").val(strText);
		 $("#cityids").val(strValue);
	}
	function showCityUl(privId){
		var ulObj = $("#t_city_ul_"+privId).is(":hidden");
		var cityUlId = "t_city_ul_"+privId;
		if(ulObj){
			 $("#t_city_ul_"+privId).show();
			 $("ul[id*='t_city_ul_']").each(function(){
				  var id = $(this).attr("id");
				  var provinceId = id.replace("t_city_ul_","");
				  if(id!=cityUlId){
					  $(this).hide();
					  $("#span_city_"+provinceId).html("︾");
				  }else{
					  $("#span_city_"+provinceId).html("︽");
				  }
			  });
		}else{
			$("#t_city_ul_"+privId).hide();
			$("#span_city_"+privId).html("︾");
		}
	}
	</script>
	<style type="text/css">
	
	.t_city_div{
		width:345px;border-radius:5px 5px 0 0;border:1px solid #DDDDDD;margin-top: 5px;float:left;
	}
	.t_city_div_title{
		background-color: #DDDDDD;line-height: 20px;font-weight: bolder;float:left;width: 100%;
	}
	</style>
</head>
<body>
		<form:form id="inputForm" modelAttribute="district" action="${ctx}/sys/district/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="provinceids" id="provinceids" value=""/>
		<form:hidden path="cityids" id="cityids" value=""/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="active" width="80px"><label class="pull-right"><font color="red">*</font>区域名称：</label></td>
					<td >
						<form:input path="name" htmlEscape="false" maxlength="300" class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="active"><label class="pull-right"><font color="red">*</font>区域编码：</label></td>
					<td >
						<form:input path="code" id="code" htmlEscape="false" class="form-control required" />
					</td>
				</tr>
				<tr>
					<td class="active"><label class="pull-right"><font color="red">*</font>选择省市：</label></td>
					<td >
						<input type="text" class="form-control" id="province" readonly="readonly" onclick="showDiv('t_province')"  placeholder="请选择省份" style="width: 300px"> 
						<input type="text" placeholder="请选择城市" id="city" readonly="readonly" class="form-control" onclick="showDiv('t_city')"  style="width: 355px"> 
						<div style="border:1px solid #DDDDDD;width: 300px;float: left;display: none" id="t_province">
							<ul style="width:300px;list-style-type: none;clear:both;float: left" id="t_province_ul">
								
							</ul>
						</div>
						<div style="width: 300px;height: 200px;float: left;display: none" id="t_cityShow"></div>
						
						<div style="border:1px solid #DDDDDD;width: 355px;float: left;margin-left: 3px;display: none;padding-left: 4px;" id="t_city">
						
						</div>
						
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>

</body>
</html>