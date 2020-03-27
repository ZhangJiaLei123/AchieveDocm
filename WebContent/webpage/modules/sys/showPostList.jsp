<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>岗位管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			 $('#contentTable thead tr th input.i-checks').on('ifChecked', function(event){ //ifCreated 事件应该在插件初始化之前绑定 
		    	  $('#contentTable tbody tr td input.i-checks').iCheck('check');
		    });

		    $('#contentTable thead tr th input.i-checks').on('ifUnchecked', function(event){ //ifCreated 事件应该在插件初始化之前绑定 
		    	  $('#contentTable tbody tr td input.i-checks').iCheck('uncheck');
		    });
		    initCheckPost();
		});
		//页面查询及页面加载初始化选中的岗位
		function initCheckPost(){
			if($("#isCheckPost",$("iframe",parent.document).contents()).val()){
				$("#postInfoCheckAll").attr("checked",true);
				$("input[type='checkbox'][name='chk']").each(function(){
					$(this).attr("checked",true);
				})
			}else{
				var checkedIds = $("#postIds",$("iframe",parent.document).contents()).val();
				var ids = checkedIds.split(",");
				for(var i=0;i<ids.length;i++ ){
					var obj = $("#"+ids[i]);
					if(obj){
						obj.attr("checked",true);
					}
				}
			}
			
		};
		function uncheckAll(obj){
			var bl = obj.checked;
			if(!bl){
				$("#postInfoCheckAll").attr("checked",false);
				var ids = "";
				$("input[type='checkbox'][name='chk']:checked").each(function(){
					ids+=$(this).val()+",";
				});
				changeIsCheckPostAll($("#postInfoCheckAll"),ids);
			}
			//修改选中的值
			changeCheckPostIds(obj);
		}
		function  checkAll(obj){
			var bl = obj.checked;
			$("input[type='checkbox'][name='chk']").each(function(){
				$(this).attr("checked",bl);
			});
			changeIsCheckPostAll(obj);
		}
		

		//改变全选值
		function changeIsCheckPostAll(obj,ids){
			var bl = obj.checked;
			if(bl){
				$("#isCheckPost",$("iframe",parent.document).contents()).val("1");
			}else{
				$("#isCheckPost",$("iframe",parent.document).contents()).val("");
				$("#postIds",$("iframe",parent.document).contents()).val(ids);
			}
		}
		

		//选择取消岗位组织页面缓存数据
		function changeCheckPostIds(obj){
			var bl = obj.checked;
			var postIdsObj =  $("#postIds",$("iframe",parent.document).contents());
			var postIds = postIdsObj.val();
			if(bl){
				if(postIds){
					var ids = postIds.split(",");
					var blc = false;
					for(var i=0;i<ids.length;i++ ){
						if(ids[i]==obj.value){
							blc = true;
							break;
						}
					}
					if(!blc){//如果不存在添加值
						postIdsObj.val(postIds+obj.value+",");
					}
				}else{
					postIdsObj.val(obj.value+",");
				}
			}else{
				if(postIds){
					var ids = postIds.split(",");
					var blc = false;
					for(var i=0;i<ids.length;i++ ){
						if(ids[i]==obj.value){
							blc = true;
							break;
						}
					}
					if(blc){//如果存在移除当前值
						postIdsObj.val(postIds.replace((obj.value+","),""));
					}
				}
			}
		}
	</script>
</head>
<body >
<div style="padding:5px;">
	<!--查询条件-->
	<form:form id="searchForm" modelAttribute="postInfo" action="${ctx}/sys/postInfo/showPostListByCondition" method="post" class="form-inline">
		<!-- 以此来判断是必学，还是选学的赋值 -->
		<input type="hidden" name ="postXx" id="postXx" value="${param.type }">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="type" name="type" type="hidden" value="${type}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		
		<table>
			<tr>
				<td width="60px"><input type="checkbox" value="1" id="postInfoCheckAll" onclick="checkAll(this)"  style="width: 15px; height: 25px;border:none"  <c:if test="${param.postCheckAll==1 }">checked</c:if>/><label style="cursor:pointer;" for="checkAll">全选</label></td>
				<td width="80px">岗位名称：</td>
				<td><form:input path="name" htmlEscape="false" maxlength="200"  class=" form-control input-sm"/></td>
				<td width="10px"></td> 
				<td width="80px">岗位类型：</td>
				<td>
					<form:select path="postType"  class="form-control m-b">
						<form:option value="" label="请选择"/>
						<c:forEach var="postType" items="${postTypes }">
							<form:option value="${postType.id }">${postType.name}</form:option>
						</c:forEach>
					</form:select>
					  
				</td>
				<td width="10px"></td> 
				<td>
					<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
				</td>
			</tr>
		</table>
	</form:form>
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th></th>
				<th >岗位名称</th>
				<th >岗位级别</th>
				<th >级别分类</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="postObj">
			<tr>
				<td> 
				<input type="checkbox" name="chk" onclick="uncheckAll(this);" value="${postObj.positLevelId}"
				 postName ="${postObj.name}" postLevelName="${postObj.postLevelName}" id="${postObj.positLevelId}" >
				</td>
				<td>
					${postObj.name}
				</td>
				<td>
					${postObj.postLevelName}
				</td>
				<td>
					${postObj.postinfoTypeName}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
		<!-- 分页代码 -->
	<table:page page="${page}"></table:page>
	</div>
</body>
</html>