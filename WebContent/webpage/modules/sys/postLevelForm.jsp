<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>岗位分类管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
	
		 if(validateForm.form(obj) && validateMyForm()){
			  $("#inputForm").submit();
			  return true;
		  }
		  return false;
		}
		$(document).ready(function() {
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
					//loading('正在提交，请稍等...');
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
			
		});
		function insertRows(){
			 var t01 = $("#tab tr").length+1;
		    var innerHtml = "<tr><td><label class='pull-right'><font color='red'>*</font>岗位级别：</label></td><td><input type='text' nameId='' name='name_"+t01+"' maxlength='50' class='form-control required'/></td>"
		    				+"<td><span onclick='deleteTr(this);' style='cursor:pointer'>&nbsp;&nbsp;删除</span></td></tr>";
		    $("#tab").append(innerHtml);
		}
		  function deleteTr(nowTr,id){
              //多一个parent就代表向前一个标签,
               // 本删除范围为<td><tr>两个标签,即向前两个parent
               //如果多一个parent就会删除整个table
              if(id){
            	  $.ajax({
		                async: false,
		                url: "${ctx}/sys/postLevel/ajaxDelete",
		                data:{id:id},
		                dataType: "json",
		                success: function (data) {
		                	if(data=="1"){
		                		top.layer.alert("删除成功！");
		                           if($(nowTr).parent().parent().attr("id")!="notDelTr"){
		                        	   $(nowTr).parent().parent().remove();
		                           }else{
		                        	   var inputO = $("input[type='text']",$(nowTr).parent().parent());
		                        	   inputO.val("");
		                        	   inputO.attr("nameId","");
		                           }
		                	}else if(data=="2"){
		                		top.layer.alert("当前级别下还有人员不能删除！");
		                	}else if(data=="0"){
		                		top.layer.alert("删除失败！");
		                	}
		                }
            	  });
              }else{
            	 top.layer.alert("删除成功！");
                 if($(nowTr).parent().parent().attr("id")!="notDelTr"){
              	   $(nowTr).parent().parent().remove();
                 }else{
                	var inputO = $("input[type='text']",$(nowTr).parent().parent());
              	   inputO.val("");
              	   inputO.attr("nameId","");
                 }
              }
		} 
		  
		  function validateMyForm(){
			  var valueArray = [];
		      var jsonStr = "[";
		      $("input[type='text']",$("#tab")).each(function(index){
		    	  valueArray[index] = $(this).val();
		    	  jsonStr+="{'name':'"+$(this).val()+"','nameId':'"+$(this).attr("nameId")+"'},";
		      });
		      
		      var tmpArr = valueArray.join(",")+","; 
		      var cfName = "";
		      for(var i=0;i<valueArray.length;i++) { 
			      if(tmpArr.replace(valueArray[i]+",","").indexOf(valueArray[i]+",")>-1) { 
			    	  cfName = valueArray[i]; 
			     	 break;
			      } 
			    } 
		      if(cfName!=""){
		    	  top.layer.alert("职位等级存在重复名称："+cfName);
		    	  return false;
		      }
		      jsonStr = jsonStr.substr(0,jsonStr.length-1)+"]";
		      $("#jsonStr").val(jsonStr);
		      return true;
		  }
	</script>
</head>
<body>
		<form:form id="inputForm" modelAttribute="postLevel" action="${ctx}/sys/postLevel/save" method="post" class="form-horizontal">
		  <input type="hidden" name="postinfoId" value="${param.postInfoId }"/><span id="span1"></span>
		  <input type="hidden" name="jsonStr" id="jsonStr"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" id="tab">
		   <tbody>
		   	 <c:if test="${empty postLevels }">
				<tr id="notDelTr">
					<td width="100px"><label class="pull-right"><font color="red">*</font>岗位级别：</label></td>
					<td class="width-35">
						<input type="text" name="name" nameId="" maxlength="50" class="form-control required" />
					</td>
					<td><span onclick='insertRows()' style='cursor:pointer'>&nbsp;&nbsp;添加</span></td>
				</tr>
			</c:if>
	        <c:if test="${!empty postLevels }">
				<c:forEach items="${postLevels }" var="postLevel" varStatus="status">
		   	   		<c:if test="${status.index==0 }">
		   	   			<tr id="notDelTr">
							<td width="100px"><label class="pull-right"><font color="red">*</font>岗位级别：</label></td>
							<td class="width-35">
								<input type="text" name="name_${postLevel.id}" nameId="${postLevel.id}"  maxlength="50" class="form-control required" value="${postLevel.name }" />
							</td>
							<td><span onclick='insertRows()' style='cursor:pointer'>&nbsp;&nbsp;添加</span><span onclick='deleteTr(this,"${postLevel.id}")' style='cursor:pointer'>&nbsp;&nbsp;删除</span></td>
						</tr>
		   	   		</c:if>
		   	   		<c:if test="${status.index>0 }">
		   	   				<tr>
							<td><label class="pull-right"><font color="red">*</font>岗位级别：</label></td>
							<td class="width-35">
								<input type="text" name="name_${postLevel.id}"  maxlength="50" nameId="${postLevel.id}" class="form-control required" value="${postLevel.name }"/>
							</td>
							<td>
								<span  onclick='deleteTr(this,"${postLevel.id}")' style='cursor:pointer'>&nbsp;&nbsp;删除</span>
							</td>
						</tr>
		   	   		</c:if>
		   	   </c:forEach>
		   	 </c:if>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>