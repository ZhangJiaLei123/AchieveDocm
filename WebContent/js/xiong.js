// JavaScript Document

// 网站常用js面向对象封装
// 839471670@qq.com


//首页banner

function BannerTurn(id,isAuto,scale,speed){
	//var oDiv=document.getElementById('div');
	$(id).css({'height':$(id).width()/scale+'px'})
	$(window).resize(function(){
		$(id).css({'height':$(id).width()/scale+'px'})
	})
	this.oPrev=$(id).find('.prev')[0];
	this.oNext=$(id).find('.next')[0];

	this.oUl=$(id).find('ul')[0];
	this.aUlLi=this.oUl.getElementsByTagName('li');
	this.iPicLength=this.aUlLi.length;
	this.oOl=$(id).find('ol')[0];
	this.sTitHtml=this.oOl.innerHTML;
//删除这段代码可以在按钮上加数字//
	for(var i=1; i<this.iPicLength; i++){
		this.oOl.innerHTML+=this.sTitHtml;
	}
//删除这段代码可以在按钮上加数字//
	this.aOlLi=this.oOl.getElementsByTagName('li');
	this.aOlLi[0].className='on';

	this.now=0;
	var _this=this;
	
	
	for(var i=0;i<this.aOlLi.length;i++){
		this.aOlLi[i].index=i;
		this.aOlLi[i].onclick=function(){
			_this.now=this.index;
			_this.tab();
		}
	}
	
	this.oPrev.onclick=function(){
		_this.now--;
		if(_this.now==-1){
			_this.now=_this.aOlLi.length-1;
		}
		_this.tab();
	}
	this.oNext.onclick=function(){
		_this.now++;
		if(_this.now==_this.aOlLi.length){
			_this.now=0;
		}
		_this.tab();
	}
	if(isAuto=='yes'){
		this.timer=setInterval(this.oNext.onclick,speed);
		$(id).hover(function(){
				clearInterval(_this.timer);
			},function(){
				_this.timer=setInterval(_this.oNext.onclick,speed);
		})
	}
	
}

BannerTurn.prototype.tab=function(){
	for(var i=0;i<this.aOlLi.length;i++){
		this.aUlLi[i].className=this.aOlLi[i].className='';
		$(this.aUlLi[i]).css({'opacity':'0'});
	}
	this.aUlLi[this.now].className=this.aOlLi[this.now].className='on';
	$(this.aUlLi[this.now]).animate({opacity:'1'});
}
//首页banner


//学习活动 倒计时
var dDay=0;
var dHour=0;
var dMinute=0;
var dSec=0;
var iMore=1501; // 需要倒计时的秒数.
function doubleZore(num){
	if(num<10){
		num='0'+num;
	}
	return num;
}
function countTime(){
	dDay=Math.floor(iMore/(24*60*60));
	dHour=Math.floor((iMore-dDay*24*60*60)/(60*60));
	dMinute=Math.floor((iMore-dDay*24*60*60-dHour*60*60)/60);
	dSec=Math.floor(iMore-dDay*24*60*60-dHour*60*60-dMinute*60);
	$('.time .day').text(doubleZore(dHour));
	$('.time .hour').text(doubleZore(dMinute));
	$('.time .second').text(doubleZore(dSec));
	iMore--;
	if(iMore==0){
		iMore=900;
	}
}
setInterval(countTime,1000)


//学习活动 选项卡
function ChanceBtn(id,btnClass,contClass){
	var _this=this;

	this.k=0;
	
	$(id).find(btnClass).click(function(){
		_this.k=$(this).index();
		$(this).siblings().removeClass('on');
		$(this).addClass('on');
		$(this).parents(id).find(contClass).removeClass('on');
		$(this).parents(id).find(contClass).eq(_this.k).addClass('on');
	})
}
jQuery(document).ready(function($) {
		new ChanceBtn('.detail_detail','.tab a','.detail_con');
		new ChanceBtn('.home_main','.menu_tab .tab span','.list1 ul');
		
});


//学习记录 音频
function submitTryit()
{
var t=document.getElementById("TestCode").value;
t=t.replace(/=/gi,"w3equalsign");
t=t.replace(/script/gi,"w3scrw3ipttag");

document.getElementById("code").value=t;

document.getElementById("tryitform").action="v.asp";

validateForm();

document.getElementById("tryitform").submit();
}

function validateForm()
{
var code=document.getElementById("code").value;
if (code.length>5000)
	{
	document.getElementById("code").value="<h1>Error</h1>";
	}
}