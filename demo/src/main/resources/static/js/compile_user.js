$(function(){
	//验证信息
	var url="";
	var id="";
	var oc=new ObjectControl();
	url = location.search; //获取url中"?"符后的字串  
	if(url!==""){
		if (url.indexOf("?") != -1) {   
			var str = url.substr(1);   
			strs = str.split("=")[1];
		}
	        id=strs;
	}
	$("#username").blur(function(){//用户名框失去焦点的时候做的验证
		var username=$("#username").val();//用户名
		var param={};
		param["username"]=username;
		param["id"]=id;
		if(username==""){
			$("#user_text").html("用户名不能为空!");
		}
		if(username!==""){
			oc.postRequire("post","user/list/exist","0",param,function(data){
				console.log(data);
				if(data.code=="0"){
					if(data.message=="N"){
						$("#user_text").html("用户名已存在!");
						$("#username").attr("name","N");
					}else if(data.message=="Y"){
						$("#username").attr("name","Y");
					}
				}else if(data.code=="-1"){
					alert(data.message);
				}
			})
		}

	});
	$("#username").focus(function(){//用户名获得焦点的时候做的验证
			$("#user_text").html("");
    });
    $("#password").blur(function(){//密码框失去焦点的时候的验证
    	if($("#password").val()==""){
    		$("#p_text").html("密码不能为空!");
    	}
    });
    $("#password").focus(function(){//密码框获得焦点的时候的验证
			$("#p_text").html("");
    });
    $("#confirm_password").blur(function(){//确认密码框失去焦点的时候的验证
        if($("#confirm_password").val()==""){
        	$("#confirm_text").html("重复密码不能为空!");
        }else if($("#confirm_password").val()!==""&&$("#confirm_password").val()!==$("#password").val()){
        	$("#confirm_text").html("密码不一致!");
        }
    })
    $("#confirm_password").focus(function(){//确认密码框获取焦点的时候的验证
    	$("#confirm_text").html("");
    });
    $("#comment").blur(function(){//备注失去焦点的时候验证
    	if($("#comment").val()==""){
    		$("#comment_text").html("备注不能为空!");
    	}
    	
    })
    $("#comment").focus(function(){
    	$("#comment_text").html("");
    })
	if(id!==""){
		oc.postRequire("get","user/find/"+id+"","","",function(data){
			var message=JSON.parse(data.message);
			if(data.code=="0"){
				$("#username").val(message.username);
				$("#password").val(message.password);
				$("#confirm_password").val(message.password);
				$("#comment").val(message.comment);
				$("#user_type").val(message.is_admin);
			}else if(data.code=="-1"){
				alert(data.message);
			}
		});
	}else if(id==""){
		$.get("user/user_code",function(data) {
			var user_id=data;
			oc.postRequire("get","user/show_common/"+user_id+"","","",function(data){
				var message=JSON.parse(data.message);
			    var message=JSON.parse(data.message);
			    if(data.code=="0"){
			    	if(message.is_admin=="N"){
			    		$("#username").val(message.username);
			    		$("#username").attr("disabled","true");
						$("#password").val(message.password);
						$("#password").attr("name",message.id);
						$("#confirm_password").val(message.password);
						$("#comment").val(message.comment);
						$("#comment").attr("readonly","true");
						$("#user_type").val(message.is_admin);
						$("#liebiao li:eq(0)").remove(); 
			    	}
			    }
			})
        })
	}
	$("#user_type").click(function(){  
		if("block" == $("#liebiao").css("display")){  
			hideLi();  
		}else{  
			showLi();  
		}  
	});            
	$("#liebiao li").each(function(i,v){  
		$(this).click(function(){  
		$("#user_type").val($(this).html());  
			hideLi();  
		});    
	});      
	$("#user_type").blur(function(){  
		setTimeout(hideLi,200);  
	});          
	function showLi(){  
	    $("#liebiao").show();  
	}  
	function hideLi(){  
	    $("#liebiao").hide();  
	}
	$("#btn_submit span").click(function(){//编辑提交的时候判断
		var username=$("#username").val();//用户名
		var password=$("#password").val();//密码
		var confirm_password=$("#confirm_password").val();//确认密码
		var is_admin=$("#user_type").val();//用户类型
		var comment=$("#comment").val();//备注
		var name="";
		name=$("#password").attr("name");//判断name的值
		var name1=$("#username").attr("name");
		console.log(name);
		var param={};
		if(name1=="N"||username==""||password==""||confirm_password==""||is_admin==""||comment==""||confirm_password!==""&&password!==confirm_password){
			if(username==""){
				$("#user_text").html("用户名不能为空");
			}
			if(username!==""&&name1=="N"){
				$("#user_text").html("用户名已存在");
			}
			if(password==""){
				$("#p_text").html("密码不能为空!");
			}
			if(confirm_password==""){
				$("#confirm_text").html("重复密码不能为空!");
				if(confirm_password!==""&&password!==confirm_password){
					$("#confirm_text").html("密码不一致");
				}
			}
			if(comment==""){
				$("#comment_text").html("备注不能为空!");
			}	
			return;
		}
		param["username"]=username;//用户名
		param["password"]=password;//密码
		param["is_admin"]=is_admin;//是否为管理员
		param["comment"]=comment;//备注
		if(id!==""){//编辑
			param["id"]=id;
			console.log(param);
			oc.postRequire("post","user/list/edit","2",param,function(data){
				if(data.code=="0"){
					window.location.href="user";
				}else if(data.code=="-1"){
					alert(data.message);
				}
			});
		}else if(id==""&&name==undefined){//新增
			oc.postRequire("post","user/list/add","5",param,function(data){
				if(data.code=="0"){
					window.location.href="user";
				}else if(data.code=="-1"){
					alert(data.message);
					}
			});
		}else if(id==""&&name!=undefined&&is_admin=="N"){//普通用户的修改
			param["id"]=name;
			oc.postRequire("post","user/list/edit","4",param,function(data){
				if(data.code=="0"){
					alert("修改成功");
				}
			})
		}
    })	
})
