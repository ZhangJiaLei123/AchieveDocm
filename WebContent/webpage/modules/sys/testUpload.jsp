<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>测试上传文件</title>
	<meta name="decorator" content="default"/>
  <script type="text/javascript">
  function initUploadImg(inputId){
  var $list=$("#thelist");   //这几个初始化全局的百度文档上没说明
  var $btn =$("#ctlBtn");	 //开始上传
  var $clearnFile = $("#clearnFile");
  var thumbnailWidth = 100;   //缩略图高度和宽度 （单位是像素），当宽高度是0~1的时候，是按照百分比计算，具体可以看api文档
  var thumbnailHeight = 100;
  var uploader = WebUploader.create({
      // 选完文件后，是否自动上传。
	       auto: false,
	       // swf文件路径
	       swf: '${ctxStatic}/webupload/Uploader.swf',
	       // 文件接收服务端。
	       server: '${ctx}/sys/uploadFile/upload',
	       // 选择文件的按钮。可选。
	       // 内部根据当前运行是创建，可能是input元素，也可能是flash.
	       pick: '#filePicker',
	       multiple : false,
	       fileNumLimit:1,
	       /* // 只允许选择图片文件。
	       accept: {
	           title: 'files',
	           extensions: 'pdf',
	           mimeTypes: 'application/pdf'
	           //mimeTypes: 'application/pdf,application/vnd.ms-powerpoint,application/msword'
	       }, */
	       method:'POST',
	});
// 当有文件添加进来的时候
	uploader.on( 'fileQueued', function( file ){
	   if($("#showImgDiv")){
		   $("#showImgDiv").remove();
	   }
	   var $li = $(
              '<div id="' + file.id + '" class="file-item thumbnail" style="width:150px">' +
                  '<img>' +
                  '<div style="font-size:10px">' + file.name + '</div>' +
              '</div>'
              ),
         $img = $li.find('img');
      $list.append( $li );
      uploader.makeThumb( file, function( error, src ) {   //webuploader方法
          if ( error ) {
              $img.replaceWith('<span>不能预览</span>');
              return;
          }
          $img.attr( 'src', src );
      }, thumbnailWidth, thumbnailHeight );
      $li.on('click', '.remove-this', function() {
    	    uploader.removeFile( file );
    	})
  });
// 文件上传过程中创建进度条实时显示。
uploader.on( 'uploadProgress', function( file, percentage ) {
  var $li = $( '#'+file.id ),
      $percent = $li.find('.progress span');
  // 避免重复创建
  if ( !$percent.length ) {
      $percent = $('<p class="progress"><span></span></p>')
              .appendTo( $li )
              .find('span');
  }
  $percent.css( 'width', percentage * 100 + '%' );
});

  // 文件上传成功，给item添加成功class, 用样式标记上传成功。
  uploader.on( 'uploadSuccess', function(file,response) {
      $('#'+file.id ).append('<div style="color:red;font-size:10px;">上传成功</div>');
      $('#'+file.id ).append('<a style="color:red;font-size:10px;cursor: pointer;" onclick="downloadFile()">下载</a>');
      $("#download").attr("href",decodeURI(response['_raw']));
      $("#uploadUrl").val(decodeURI(response['_raw']));
  });
  // 文件上传失败，显示上传出错。
  uploader.on( 'uploadError', function( file ) {
      var $li = $( '#'+file.id ),
          $error = $li.find('div.error');
      // 避免重复创建
      if ( !$error.length ) {
          $error = $('<div class="error"></div>').appendTo( $li );
      }
      $error.text('上传失败');
  });
		// 完成上传完了，成功或者失败，先删除进度条。
  uploader.on( 'uploadComplete', function( file ) {
      $( '#'+file.id ).find('.progress').remove();
  });
 $btn.on( 'click', function() {
   uploader.upload();
 });
	 $clearnFile.on('click',function(){
		var files =  uploader.getFiles();   
		uploader.removeFile( files[0] );
	 })
 }
  
 
  $(function(){
	  initUploadImg();
  });
  
  
  function initImg(){
	 
    $("input[type='file'][name='file']").attr("accept","application/pdf");
  }
  
  
  function downloadFile(){
	  
	  window.location.href=$("#uploadUrl").val();
  }
 </script>
 <style type="text/css">
	.startUploadButton{
 	position: relative;
    display: inline-block;
    cursor: pointer;
    background: #00b7ee;
    padding: 10px 15px;
    color: #fff;
    text-align: center;
    border-radius: 3px;
    overflow: hidden;
    margin-left: 20px;
  }
	</style>
</head>
<body>
<span style="font-size:14px;"><div id="uploader-demo" >
    <!--用来存放item-->
    <div id="thelist" class="uploader-list">
    	<div  class="file-item thumbnail" id="showImgDiv" style="width:150px">
    	
         </div>
    </div>
    <div>
     <div id="filePicker" style="float:left">选择文件</div>&nbsp;&nbsp;
     <div id="ctlBtn" class="startUploadButton"  style="float:left" >开始上传</div>
     <div id="clearnFile" class="startUploadButton"  style="float:left" >清空</div>
     <input type="hidden" value="" id="uploadUrl"/>
    </div>
</div>
<a href="" target="_top" id="download">下载</a>
</span>
	<input type="button" onclick="initImg()" value="初始化图片">
</body>
</html>