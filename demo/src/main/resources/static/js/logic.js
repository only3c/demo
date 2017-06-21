var pageNumber=1;//默认是第一页
var pageSize=10;//默认传的每页多少行
var name="";//字段的名称
var value="";//收索的关键词
var cout="";//总的页数
var param={};//post请求要发的对象
var left=($(window).width()-$("#frame").width())/2;//弹框定位的left值
var tp=($(window).height()-$("#frame").height())/2;//弹框定位的top值
var oc = new ObjectControl();
function setPage(container, count, pageindex,pageSize,name,value) {
	var container = container;
	var pageindex = pageindex;
	var name=name;
	var value=value;
	var a = [];
	var value=value;
	param["pageSize"]=pageSize;
	param["pageNumber"]=pageindex;

    // if()
	//总页数少于10 全部显示,大于10 显示前3 后3 中间3 其余....
	if (pageindex == 1) {
		a[a.length] = "<li><span class=\"prev\"></span></li>";
	} else {
		a[a.length] = "<li><span class=\"unclick\"></span></li>";
	}
	function setPageList() {
		if (pageindex == i) {
			a[a.length] = "<li><span class=\"bg\">" + i + "</span></li>";
		} else {
			a[a.length] = "<li><span>" + i + "</span></li>";
		}
	}
	//总页数小于10
	if (count <= 10) {
		for (var i = 1; i <= count; i++) {
			setPageList();
		}
	}
	//总页数大于10页
	else {
		if (pageindex <= 4) {
			for (var i = 1; i <= 5; i++) {
				setPageList();
			}
			a[a.length] = "...<li><span>" + count + "</span></li>";
		} else if (pageindex >= count - 3) {
			a[a.length] = "<li><span>1</span></li>...";
			for (var i = count - 4; i <= count; i++) {
				setPageList();
			}
		} else { //当前页在中间部分
			a[a.length] = "<li><span>1</span></li>...";
			for (var i = pageindex - 2; i <= pageindex + 2; i++) {
				setPageList();
			}
			a[a.length] = "...<li><span>" + count + "</span></li>";
		}
	}
	if (pageindex == count) {
		a[a.length] = "<li><span class=\"xnclick\"></span></li>";
	} else {
		a[a.length] = "<li><span class=\"next\"></span></li>";
	}
	container.innerHTML = a.join("");
	var pageClick = function() {
		var oAlink = container.getElementsByTagName("span");
		var inx = pageindex; //初始的页码
		$("#input-txt").val(inx);
		$(".foot-sum .zy").html("共 " + count + "页");
		oAlink[0].onclick = function() { //点击上一页
			if (inx == 1) {
				return false;
			}
			inx--;
			setPage(container, count, inx,pageSize,name,value);
			return false;
		}
		for (var i = 1; i < oAlink.length - 1; i++) { //点击页码
			oAlink[i].onclick = function() {
				inx = parseInt(this.innerHTML);
				setPage(container, count, inx,pageSize,name,value);
				return false;
			}
		}
		oAlink[oAlink.length - 1].onclick = function() { //点击下一页
			if (inx == count) {
				return false;
			}
			inx++;
			setPage(container, count, inx,pageSize,name,value);
			return false;
		}
	}()
	if(name==''&&value==''){
		oc.postRequire("get","logic/list?pageNumber="+pageindex
		+"&pageSize="+pageSize+"","","",function(data){
			if(data.code=="0"){
				$(".table tbody").empty();
				message=JSON.parse(data.message);
				content=message.content;
				cout=message.totalPages;
				superaddition(content);
				jumpBianse();
			}
		});
	}else if(name!=''||value!=''){
		oc.postRequire("post","logic/list/search","0",param,function(data){
			console.log(data);
			if(data.code=="0"){
				message=JSON.parse(data.message);
				content=message.content;
				if(content.length>0){
					$(".table tbody").empty();
					$(".table p").remove();
					superaddition(content);
				    jumpBianse();
				}
			}else if(data.code=="-1"){
				alert(data.message);
			}
	    })
	}
}
//选择行数的change事件
$("#pageSize").change(function(){
	pageSize = $(this).val();
	if(name==""&&value==""){
		GET();
	}else if(name!=''||value!=''){
		param["pageSize"]=pageSize;
		POST();
	}
});
//选择字段的change事件
$("#page_row").change(function(){
	name = $(this).val();	
});
//跳转页面的键盘按下事件
$("#input-txt").keydown(function() {
	var event=window.event||arguments[0];
	var pageNumber= this.value.replace(/[^1-9]/g, '');
	if (pageNumber > cout) {
			pageNumber = cout
		};
	if (pageNumber > 0) {
		if (event.keyCode == 13) {
			setPage($("#foot-num")[0],cout,pageNumber,pageSize,name,value);
		};
	}
})
//页面加载循环
function superaddition(data){
	console.log(data);
	for (var i = 0; i < data.length; i++) {
		$(".table tbody").append("<tr id='"+data[i].id+"'><td width='8.82%' style='text-align: left;'><div class='checkbox'><input  type='checkbox' value='' name='test' title='全选/取消' class='check'  id='checkboxTwoInput"
						+ i
						+ 1
						+ "'/><label for='checkboxTwoInput"
						+ i
						+ 1
						+ "'></label></div><span class='pl5' title='"+data[i].id+"'>"
						+ data[i].id//编号
						+ "</td><td width='8%'>"
						+ data[i].corp_code 
						+ "</span></td><td width='15.66%'>"
						+ data[i].title//
						+ "</td><td width='12.72%'>"
						+ data[i].table_code
					    + "</td><td width='12.72%'>"
						+ data[i].table_name
						+ "</td><td width='6.843%'>"
						+ data[i].creater
						+ "</td><td width='11.745%'>"
						+ data[i].created_time
						+ "</td><td width='6.843%'>"
						+ data[i].modifier
						+ "</td><td width='11.745%'>"
						+ data[i].modified_time
						+ "</td><td width='4.882%'>"
						+ data[i].level + "</td></tr>");
	}
}
function jumpBianse(){
	$(document).ready(function(){//隔行变色 
   		 $(".table tbody tr:even").css("backgroundColor","#f1f1f1");
	})
	//双击跳转
	$(".table tbody tr").dblclick(function(){
	    id=$(this).attr("id");
	    window.location.href="logic_detail?id="+id+"";
	})
	//点击tr input是选择状态  tr增加class属性
	$(".table tbody tr").click(function(){
		var input=$(this).find("input")[0];
		var thinput=$("thead input")[0];
		$(this).toggleClass("tr");  
		console.log(input);
		if(input.type=="checkbox"&&input.name=="test"&&input.checked==false){
			input.checked = true;
			$(this).addClass("tr"); 
		}else if(input.type=="checkbox"&&input.name=="test"&&input.checked==true){
			if(thinput.type=="checkbox"&&input.name=="test"&&input.checked==true){
				thinput.checked=false;
			}
			input.checked = false;
			$(this).removeClass("tr");
		}
	})
}
//页面加载时的GET请求
function GET(){
	oc.postRequire("get","logic/list?pageNumber="+pageNumber
		+"&pageSize="+pageSize+"","","",function(data){
			if(data.code=="0"){
				message=JSON.parse(data.message);
				content=message.content;
				cout=message.totalPages;
				setPage($("#foot-num")[0],cout,pageNumber,pageSize,name,value);
			}else if(data.code=="-1"){
				alert(data.message);
			}
		});
}
GET();
//收索页面的post请求
function POST(){
	oc.postRequire("post","logic/list/search","0",param,function(data){
		if(data.code=="0"){
			message=JSON.parse(data.message);
			content=message.content;
			cout=message.totalPages;
			$(".table tbody").empty();
			if(content.length<=0){
				$(".table p").remove();
				$(".table").append("<p>没有找到与"+value+"相关的信息请重新搜索</p>")
		 	}
		 	setPage($("#foot-num")[0],cout,pageNumber,pageSize,name,value);
		}else if(data.code=="-1"){
			alert(data.message);
		}
	})
}
//鼠标按下时触发的收索
$("#input").keydown(function() {
	var event=window.event||arguments[0];
    value=this.value.replace(/\s+/g,"");
	param["name"]=name;
	param["value"]=value;
	param["pageNumber"]=pageNumber;
	param["pageSize"]=pageSize;
	if(event.keyCode == 13) {
		if(name==""||name=="10"){
			alert("请先选择字段");
			return;
		}
		POST();
		console.log(name);
		console.log(param);
	}
});
//全选
function checkAll(name){
	var el=$("tbody input");
	el.parents("tr").addClass("tr");
	var len = el.length;

	for(var i=0; i<len; i++)
	    {
	       if((el[i].type=="checkbox") && (el[i].name==name))
	        {
	          el[i].checked = true;
	        }
	    }
};

//取消全选
function clearAll(name){
	var el=$("tbody input");
	el.parents("tr").removeClass("tr");
	var len = el.length;
	for(var i=0; i<len; i++)
	    {
	        if((el[i].type=="checkbox") && (el[i].name==name))
	        {
	          el[i].checked = false;
	        }
	    }
};
//弹框
function frame(){
	var input=$("thead input")[0];
	if(input.type=="checkbox"&&input.name=="test"&&input.checked==true){
		input.checked = false;
	}
	$("#frame").css("left",""+left+"px");
    $("#frame").css("top",""+tp+"px");
    $("#frame").show();
    $("#frame").animate({
    	opacity:"1"
    },1000);
    $("#frame").animate({
    	opacity:"0"
    },1000);
    if(name==""&&value==""){
		GET();
	}else if(name!=''&&value!=''){
		param["pageSize"]=pageSize;
		POST();
	}
    setTimeout(function(){
        $("#frame").hide();
    },2000)
}
//删除
$("#remove").click(function(){
    var tr=$("tbody input[type='checkbox']:checked").parents("tr");
    for(var i=0,ID="";i<tr.length;i++){
    	var r=$(tr[i]).attr("id");
	    if(i<tr.length-1){
	    	ID+=r+",";
	    }else{
	    	 ID+=r;
	    }     
    }
    console.log(ID);
    id=$(tr).attr("id");
    var param = {};
    param["id"] = ID;
    console.log(param);
    if(tr.length==0){
        alert("请先选中所选项");
        return;
    }
    if(!window.confirm("确定要删除该数据吗?")){
        return;
    }
    oc.postRequire("post","logic/list/delete","1",param,function(data){
    	console.log(data);
    	if(data.code=="0"){
    		$("#frame").html("删除成功");
			frame();
    	}else if(data.code=="-1"){
    		$("#frame").html("删除失败");
    		frame();
    	}
	});
})
//编辑
$("#compile").click(function(){
    var tr=$("tbody input[type='checkbox']:checked").parents("tr");
    if(tr.length==1){
    	id=$(tr).attr("id");
		window.location.href="logic_detail?id="+id+"";
    }else{
        alert("只能选择一项");
    }
})