<%@ page language="java"  pageEncoding="UTF-8"%>  
<!doctype html>
 <head>  
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
<script type="text/JavaScript" src="js/jquery.min.js"></script>  
 <script type="text/javascript" src="js/flexpaper.js"></script>   
 <title>预览文件</title>  
 <script type="text/javascript">
	 $(document).ready(function(){ 
		 var url = parent.document.getElementById("resourceUrl").value;
	 	 var fileType= url.substring(url.lastIndexOf(".")+1,url.length);
	 	 if(fileType.toUpperCase()=="MP4"){
	 		 $("#swfDiv").remove();
	 		  url =  "http://"+window.location.host+"${pageContext.request.contextPath}/"+url.replace("docm/","");
	 		$("#vdeoId").attr("src",url);
	 		 $("#MP4Div").show();
	 	 }else{
	 		 $("#MP4Div").remove();
	 		$("#swfDiv").show();
	 	 }
	});
 </script>
 </head>  
 <body>   
 
 		<div style="display: none" id="swfDiv">
        <div id="viewerPlaceHolder" class="flexpaper_viewer" style="width:100%;height:350px;"></div> 
           <script type="text/javascript"> 
           var url = parent.document.getElementById("showUrl").value;
           //var url = 'http://localhost:8080/docm/userfiles/1/files/resource/resourceInfo/2017/03/aaaaa.swf';
             $('#viewerPlaceHolder').FlexPaperViewer(     
                     { config : {  
                     SWFFile : escape(url),  
                     Scale : 1,   
                     ZoomTransition : 'easeOut',  
                     ZoomTime : 0.5,  
                     ZoomInterval : 0.2,  
                     FitPageOnLoad : false,  
                     FitWidthOnLoad : true,  
                     FullScreenAsMaxWindow : false,  
                     ProgressiveLoading : false,  
                     MinZoomSize : 0.5,  
                     MaxZoomSize : 2,  
                     SearchMatchAll : true,  
                     InitViewMode : 'Portrait',
                     ReaderingOrder :'false',
                     StartAtPage :'',
                     ViewModeToolsVisible : true,  
                     ZoomToolsVisible : true,  
                     NavToolsVisible : true,  
                     CursorToolsVisible : true,  
                     SearchToolsVisible : true,
                     localeChain: 'zh_CN'  
                     }});    
             </script>   
           </div>
           <div style="display: none" id="MP4Div">
           		<video id="vdeoId" src=""  width="100%" height="350px" controls="autobuffer">
           </div>
 </body>  
 </html>  