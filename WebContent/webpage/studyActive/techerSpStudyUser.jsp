<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>活动报名审核管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		});
		function checkAllUser(obj){
			var bl = obj.checked;
			$("input[type='checkbox'][name='chk']").each(function (){
				$(this).attr('checked',bl);
			});
		}
		
		function shActivityUser(type){
			var ids = "";
			if("checkAll"==type){
				var count = $('input[type="checkbox"][name="chk"]:checked').length;
				if(count<1){
					alert("请至少选择一条数据。");
					return ;
				}
				$('input[type="checkbox"][name="chk"]:checked').each(function(){
					ids+=$(this).val()+",";
				})
			}else{
				ids = type;
			}
			
			var formHtml = '<div id="subDiv" >'+
						'<table class="table table-bordered">'+
						  ' <tbody>'+
								'<tr>'+
									'<td class="active"><label class="pull-right"><font color="red">*</font>审批状态：</label></td>'+
									'<td >'+
									'<input type="hidden" id="activityid" value="${activityid}">'+
									'<input type="radio" name="rdo" value="1" checked="checked" id="rdoYes"/><label for="rdoYes" style="cursor: pointer;">通过</label>'+
									'<input type="radio" name ="rdo" value="2" id="rdoNo"/><label for="rdoNo"  style="cursor: pointer;">不通过</label>'+
									'</td>'+
									'</tr>'+
									'<tr>'+
									'<td class="active"><label class="pull-right">审批意见：</label></td>'+
									'<td >'+
										'<textarea rows="4" id="textArea" cols="30" maxlength="300" style="border:solid 1px #dadada; "></textarea>'+
									'</td>'+
									'</tr>'+
						 	'</tbody>'+
						'</table>'+
						'</div>';
			
			top.layer.open({
			    type: 1,  
			    area: ['450px', '300px'],
			    title: "审核",
		        maxmin: true, //开启最大化最小化按钮
			    content: formHtml ,
			    btn: ['提交','取消'],
			    yes: function(index,layero){
			    	var rdio = $("input[type='radio']:checked",layero).val();
					var textVlue = $("#textArea",layero).val();
			    	$.ajax({
				        async: false,
				        type:'post',
				        url: "${ctx}/train/userActivityReg/teacherSpActityUser",
				        data:{ids:ids,rdio:rdio,textVlue:textVlue},
				        dataType: "json",
				        success: function (data) {
				        	 top.layer.close(index);
				    	   if(data){
				    		   layer.confirm('审批成功!', {
				    				btn: ['确定'] //按钮
				    			},function(index){
				    				window.location.reload();
				    			})
				    	   }else{
				    		   layer.confirm('审批失败!', {
				    				btn: ['确定'] //按钮
				    			}, function(){
				    				window.location.reload();
				    			})
				    	   }
				        }
					});
			    },
			    cancel: function(index){ 
					  
			     }
			    
			})
		}
		
	</script>
</head>
<body >
		<div class="ibox">
			<div class="ibox-content">
				<!-- 表格 -->
				<input type="checkbox" id="chkAll" onclick="checkAllUser(this)"><label for="chkAll" style="cursor: pointer;">全选</label>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="#"  class="btn btn-success btn-xs" onclick="shActivityUser('checkAll');" title="审核">审核</a>
				<table id="contentTable"
					class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
					<thead>
						<tr>
							<th></th>
							<th>序号</th>
							<th>所属机构</th>
							<th>人员姓名</th>
							<th>审核状态</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
					
						<c:forEach items="${page.list}" var="userObj" varStatus="status">
							<tr>
								<td>
								<c:if test="${userObj.shStatus==0}">
									<input type="checkbox" name="chk" value="${userObj.id}" id="${userObj.id}">
								</c:if>
								</td>
				 				<td>${status.index+1 }</td>
								<td>${userObj.officeName}</td>
								<td>${userObj.name}</td>
								<td>
								<c:if test="${userObj.shStatus==0}">
									待审核
								</c:if>
								<c:if test="${userObj.shStatus==1}">
									审核通过
								</c:if>
								<c:if test="${userObj.shStatus==2}">
									待审核
								</c:if>
								<c:if test="${userObj.shStatus==3}">
									取消报名
								</c:if>
								</td>
								<td>
								<c:if test="${userObj.shStatus==0}">
									<button class="btn btn-success btn-xs" data-toggle="tooltip"
						data-placement="left"
						onclick="shActivityUser('${userObj.id}');"
						title="审核">审核</button>
								</c:if>
								
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>

				<!-- 分页代码 -->
				<table:page page="${page}"></table:page>
				<br /> <br />
			</div>
		</div>
		
		
</body>
</html>