
//表格隔行变色
$(document).ready(function(){ 
    $(".table tbody tr:even").css("backgroundColor","#f1f1f1");
})
function checkAll(name){
	var el=$("input");
	var len = el.length;
	for(var i=0; i<len; i++)
	    {
	       if((el[i].type=="checkbox") && (el[i].name==name))
	        {
	          el[i].checked = true;
	        }
	    }
}
function clearAll(name){
	var el=$("input");
	var len = el.length;
	for(var i=0; i<len; i++)
	    {
	        if((el[i].type=="checkbox") && (el[i].name==name))
	        {
	          el[i].checked = false;
	        }
	    }
}
$("#remove").click(function(){
    var tr=$("tbody input[type='checkbox']:checked").parents("tr");
    if(tr.length==0){
        alert("请先选中所选项");
    }
    tr.remove();
})
function setPage(container, count, pageindex) {
    var container = container;
    var count = count;
    var pageindex = pageindex;
    var a = [];
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
        }else if (pageindex >= count - 3) {
            a[a.length] = "<li><span>1</span></li>...";
            for (var i = count - 4; i <= count; i++) {
                setPageList();
            }
        }
        else { //当前页在中间部分
            a[a.length] = "<li><span>1</span></li>...";
            for (var i = pageindex - 2; i <= pageindex + 2; i++) {
                setPageList();
            }
                a[a.length] = "...<li><span>" + count + "</span></li>";
            }
        }
    if (pageindex == count) {
        a[a.length] = "<li><span class=\"xnclick\"></span></li>";
    }else{
        a[a.length] = "<li><span class=\"next\"></span></li>";
    }
    container.innerHTML = a.join("");
    //事件点击
    $("#input-txt").keydown(function(){
      var inx=this.value.replace(/[^1-9]/g,'');
      if(inx>count){inx=count};
      if(inx>0){
        if(event.keyCode==13){setPage(container, count, inx);};
      }
    })
    var pageClick = function() {
        var oAlink = container.getElementsByTagName("span");
        var inx = pageindex; //初始的页码
        // console.log(inx);
        // console.log(count);
        $("#input-txt").val(inx);
        $(".foot-sum .zy").html("共 "+count+"页");
        oAlink[0].onclick = function() { //点击上一页
            if (inx == 1) {
                return false;
            }
            inx--;
            setPage(container, count, inx);
            return false;
        }
        for (var i = 1; i < oAlink.length - 1; i++) { //点击页码
            oAlink[i].onclick = function() {
            inx = parseInt(this.innerHTML);
                setPage(container, count, inx);
                return false;
            }
        }
        oAlink[oAlink.length - 1].onclick = function() { //点击下一页
            if (inx == count) {
                return false;
            }
            inx++;
            setPage(container, count, inx);
            return false;
        }
    }()
}
setPage($("#foot-num")[0],14,1);   