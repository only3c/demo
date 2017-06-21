var ObjectControl = new Function();
var left=($(window).width()-$("#frame").width())/2-180;//弹框定位的left值
var tp=($(window).height()-$("#frame").height())/2-280;//弹框定位的top值
var oMask = '<div class="mask"></div>'
var img='<div class="imgMask"><img src="images/loading.gif" alt="" /></div>'
//遮照层的宽度
var maskWidth = jQuery(document).width();
//遮照层的高度
var maskHeight = jQuery(document).height();
ObjectControl.prototype = {
	webaction:function(require_type,require_url,require_id,require_data,callback){
		var _params = {
			"id":require_id,
			"message":require_data
		};
		jQuery.ajax({
			url: require_url,
			type: require_type,
			dataType: 'json',
			data:{param:JSON.stringify(_params)},
			success:function(data){
				if(data){
				    callback(data);
				}else{
				}
			},
			error:function(data){
				alert(data.responseText);
			}
		});
	}
};
var ox = new ObjectControl();
function delete_item(obj){
	$(obj).parent().remove();
}
var option_items='';
//编辑任务下拉框内容动态加载
function logic_add_new(obj){
		var b=$('.items_box');
		$(obj).parent().removeClass('choose_logic_add').addClass('choose_logic');
		$(obj).parent().append('<span class="delete_item btn" onclick="delete_item(this)"><img src="images/icon_table_delete.png" alt="删除"></span>');
		$(obj).remove();
		b.append('<div class="choose_logic_add"><span class="c_box"><select style="display: inline-flex"><option value="-1">选择逻辑</option></select></span><span class="add_new btn" onClick="logic_add_new(this)"><img src="images/icon_table_new.png" alt="新增" /></span></div>');
		var select_item=$('.choose_logic_add select');
		var oc = new ObjectControl();
		var param={};
		oc.webaction("get","plan/logic","",param,function(data){
			if(data.code==0){
				var msg=JSON.parse(data.message);
				option_items=msg;
				for(var i=0;i<select_item.length;i++){
					for(var j=0;j<option_items.length;j++){
						var option=document.createElement("option");
						option.text=option_items[j].title;
						option.value=option_items[j].id;
						select_item[i].add(option,null);
					}
				}
				setTimeout(function(){
					$('.searchable-select').remove();
					$('.items_box select').searchableSelect();
				},100);
			}else if(data.code==-1){
				alert(data.message);
			}
		});
}

//逻辑开发页面执行取数方式的选择功能
function way_change(str){
	if(str=="TIME"){
		$('.appoint_time input').slideDown();
		$('#s_type em').slideDown();
		$('.t_select_box').slideDown();
		$('.check_note').slideDown();
		$('.d_select_box').slideUp();
	}else if(str=="DEFAULT"){
		$('#start_time').slideDown();
		$('#date_type').slideDown();
		$('#s_type em').slideDown();
		$('.t_select_box').slideDown();
		$('#finish_time').slideUp();
		$('.check_note').slideUp();
		$('#start_end_index').slideUp();
		$('.d_select_box').slideDown();
		$('#em').slideDown();
	}else{
		$('.appoint_time input').slideUp();
		$('#s_type em').slideUp();
		$('.t_select_box').slideUp();
		$('.check_note').slideUp();
		$('.d_select_box').slideUp();
	}
}
//计划管理编辑任务页面执行周期选择功能
function changevalue(str){
	if(str=="CYCLE"){
		$('.m_time').show();
		$('.t_item_box').show();
		$('.calendar').hide();
	}else if(str=="TIMING"){
		$('.m_time').hide();
		$('.t_item_box').hide();
		$('.calendar').show();
	}
}
//任务逻辑下拉选项
function select_option(){
	//计划管理编辑任务页面选择文件类型的选择
	var select_items=$('.items_box select');
	var oc = new ObjectControl();
	var require_data={};
	oc.webaction("get","plan/logic","",require_data,function(data){
		console.log(data);
		if(data.code==0){
			var msg=JSON.parse(data.message);
			option_items=msg;
			for(var i=0;i<select_items.length;i++){
				for(var j=0;j<msg.length;j++){
					var option=document.createElement("option");
					option.text=msg[j].title;
					option.value=msg[j].id;
					select_items[i].add(option,null);
				}
			}
		}else if(data.code==-1){
			alert(data.message);
		}
	});
}
//计划管理编辑任务界面提交功能
function editor_manage(){
	var GetRequest_info=GetRequest();
	var param=GetRequest_info.id;
	var b=$('.choose_logic_add');
	var oc = new ObjectControl();
	var target=[];
	var push_type=$("#changeFile").val();//目标类型
	if(push_type=="FILE_PATH"){
		$(".toast").show();
		$.get("plan/file/path",function(data){
			data=JSON.parse(data);
			if(data.code=="0"){
				$('.toast input').val(data.message);
			}else if(data.code=="-1"){
				alert(data.message);
			}
		})
	}
	$("#changeFile").change(function(){
		$("#local_file").val("");
		if($(this).val()=="FILE_PATH"){
			$("#local_file").attr('placeholder','请输入存储路径');
			$(".toast").show();
			$.get("plan/file/path",function(data){
				if(data.code=="0"){
					$('.toast input').val(data.message);
				}else if(data.code=="-1"){
					alert(data.message);
				}
			})
	    }else if($(this).val()=="TARGET_TABLE"){
	    	$("#local_file").attr('placeholder','请输入目标表名称');
	    	$(".toast").hide();
	    }
		push_type= $(this).val();
	})
	if(typeof(param)!=='undefined'){
		$('.m_title .e_manage').html('编辑任务');
		var manage_data={};
		oc.webaction("get","plan/find/"+param+"","",manage_data,function(data){
			if(data.code==0){
				var data=JSON.parse(data.message);
				console.log(data);
				var strs=data.logic_list.split(',');
				var select_items=$('.items_box select');
				var op = new ObjectControl();
				var r_data={};
				op.webaction("get","plan/logic","",r_data,function(data){
					if(data.code==0){
						var msg=JSON.parse(data.message);
						for(var i=0;i<select_items.length;i++){
							for(var j=0;j<msg.length;j++){
								var option=document.createElement("option");
								option.text=msg[j].title;
								option.value=msg[j].id;
								select_items[i].add(option,null);
							}
						}
						for(var c=0;c<msg.length;c++){
							if(strs.length==1&&msg[c].id==strs[0]){
								if(msg[c].id==strs[0]){
									var count=$(".choose_logic_add select").get(0).options.length;
									for(var i=0;i<count;i++){
										if($(".choose_logic_add select").get(0).options[i].text == msg[c].title)
										{
											$(".choose_logic_add select").get(0).options[i].selected = true;
											break;
										}
									}
								}
							}
							else if(strs.length>1){
								if(msg[c].id==strs[strs.length-1]){
									var count=$(".choose_logic_add select").get(0).options.length;
									for(var i=0;i<count;i++){
										if($(".choose_logic_add select").get(0).options[i].text == msg[c].title){
											$(".choose_logic_add select").get(0).options[i].selected = true;
											break;
										}
									}
								}
								for(var i=0;i<strs.length-1;i++){
									if(msg[c].id==strs[i]){
										$('<div class="choose_logic flag">'
										+'<span class="c_box">'
										+'<select style="display: inline-flex">'
										+'<option value="-1">选择逻辑</option>'
										+'<option value="'+strs[i]+'" selected="selected">'+msg[c].title+'</option>'
										+'</select>'
										+'</span>'
										+'<span class="delete_item btn" onclick="delete_item(this)"><img src="images/icon_table_delete.png" alt="删除" /></span>'
										+'</div>').insertBefore(b);
									}
								}
							}
						}
						var select_flag=$('.flag select');
						for(var i=0;i<select_flag.length;i++){
							for(var j=0;j<msg.length;j++){
								var option=document.createElement("option");
								option.text=msg[j].title;
								option.value=msg[j].id;
								if(select_flag[i].value!==option.value){
									select_flag[i].add(option,null);
								}
							}
						}
					}else if(data.code==-1){
						alert(data.message);
					}
				});
				$('#name').val(data.title);
				$('#manage_type').val(data.plan_type);
				$('#corp_code').val(data.corp_code);
				// $('#target_table').val(data.target_table);
				if(data.push_type=="TARGET_TABLE"){
					$("#changeFile option[value='TARGET_TABLE']").attr("selected",true);
					$("#local_file").val(data.target_table);
					$(".toast").hide();
				}else if(data.push_type=="FILE_PATH"){
					$("#changeFile option[value='FILE_PATH']").attr("selected",true);
					$("#local_file").val(data.file_path);
				}
				if(data.plan_type=='CYCLE'){
					$('.m_time').show();
					$('.t_item_box').show();
					$('.calendar').hide();
					$('#par_time').val(data.cycle_time);//时间
				}else if(data.plan_type=='TIMING'){
					$('.m_time').hide();
					$('.t_item_box').hide();
					$('.calendar').show();
					$('#clocking').val(data.cycle_time);
				}
				$('#is_show').val(data.on_off);
			}else if(data.code==-1){
				alert(data.message);
			}
		});
	}else if(typeof(param)=='undefined'){
		$('.m_title .e_manage').html('新增任务');
		select_option();
	}
	$('.btn_submit').on('click',function(){
		var corp_code=$('#corp_code').val();//商家编号
		var oc = new ObjectControl();
		var content=$("#local_file").val();
		if($('#manage_type').val()=='CYCLE'){
			if(corp_code!==''&&$('#name').val()!==''&&$('#par_time').val()!==''&&$('#is_show').val()!==''&&content!==''){
				if($('#par_time').val().replace(/[^0-9_]/g,'')){
				var m_select=$(".items_box select");
			    for(var i=0,manage_logic="";i<m_select.length;i++){
			    	var v=$(m_select[i]).val();
				    if(i<m_select.length-1){
				    	manage_logic+=v+",";
				    }else{
				    	manage_logic+=v;
				    }
			    }
			
			    var logic_param = {};
			    var par_time=$('#par_time').val();
			    var plan_statue='';
			    if($('#is_show').val()=='ON'){
			    	plan_statue='START';
			    }else if($('#is_show').val()=='OFF'){
			    	plan_statue='STOP';
			    }
				if(typeof(param)!=='undefined'){
					var edit_manage={"corp_code":corp_code,"id":param,"title":$('#name').val(),"logic_list":manage_logic,"plan_type":$('#manage_type').val(),"cycle_time":par_time,"on_off":$('#is_show').val(),"status":plan_statue,"target":JSON.stringify({"push_type":push_type,"content":content})};
					console.log(edit_manage);
					if(manage_logic!="-1"){
						oc.webaction("post","plan/list/edit","plan_edit",edit_manage,function(data){
							if(data.code==0){
								window.location.href='plan';
							}else if(data.code==-1){
								alert(data.message);
							}
						});
					}else{
						alert("请选择逻辑！");
					}
					
				}else{
					var add_manage={"corp_code":corp_code,"title":$('#name').val(),"logic_list":manage_logic,"plan_type":$('#manage_type').val(),"cycle_time":par_time,"on_off":$('#is_show').val(),"status":plan_statue,"target":JSON.stringify({"push_type":push_type,"content":content})};
					console.log(add_manage);
					if(manage_logic!="-1"){
						oc.webaction("post","plan/list/add","plan_add",add_manage,function(data){
							if(data.code==0){
								window.location.href='plan';
							}else if(data.code==-1){
								alert(data.message);
							}
						});
					}else{
						alert("请选择逻辑！");
					}
					
				}
			 }else{
				 $("#frame").html("时间只能为数字！");
                 kuang();
			 }
			}else{
				alert('请将信息填写完整！');
			}
		
		}else if($('#manage_type').val()=='TIMING'){
			if(corp_code!==''&&$('#name').val()!==''&&$('#clocking').val()!==''&&$('#is_show').val()!==''&&content!==''){
				var m_select=$(".items_box select");
			    for(var i=0,manage_logic="";i<m_select.length;i++){
			    	var v=$(m_select[i]).val();
				   if(i<m_select.length-1){
				    	manage_logic+=v+",";
				    }else{
				    	manage_logic+=v;
				    }
			    }
			    var logic_param = {};
			     var plan_statue='';
			    if($('#is_show').val()=='ON'){
			    	plan_statue='START';
			    }else if($('#is_show').val()=='OFF'){
			    	plan_statue='STOP';
			    }
				if(typeof(param)!=='undefined'){
					var edit_manage={"corp_code":corp_code,"id":param,"title":$('#name').val(),"logic_list":manage_logic,"plan_type":$('#manage_type').val(),"cycle_time":$('#clocking').val(),"on_off":$('#is_show').val(),"status":plan_statue,"target":JSON.stringify({"push_type":push_type,"content":content})};
					console.log(edit_manage);
					if(manage_logic!="-1"){
					oc.webaction("post","plan/list/edit","plan_edit",edit_manage,function(data){
						if(data.code==0){
							window.location.href='plan';
						}else if(data.code==-1){
							alert(data.message);
						}
					});
					}else{
						alert('请选择逻辑！');
					}
				}else{
					var add_manage={"corp_code":corp_code,"title":$('#name').val(),"logic_list":manage_logic,"plan_type":$('#manage_type').val(),"cycle_time":$('#clocking').val(),"on_off":$('#is_show').val(),"status":plan_statue,"target":JSON.stringify({"push_type":push_type,"content":content})};
					console.log(add_manage);
					if(manage_logic!="-1"){
					oc.webaction("post","plan/list/add","plan_add",add_manage,function(data){
						if(data.code==0){
							window.location.href='plan';
						}else if(data.code==-1){
							alert(data.message);
						}
					});
					}else{
						alert("请选择逻辑！");
					}
				}
			}else{
				alert('请将信息填写完整！');
			}
		}
	});
	//加载带搜索框的select样式
	setTimeout(function(){
		$('.items_box select').searchableSelect();
	},1000);
}
//截取URL后面的参数
function GetRequest() {
	   var url = location.search; //获取url中"?"符后的字串
	   var theRequest = new Object();
	   if (url.indexOf("?") != -1) {
		      var str = url.substr(1);
		      strs = str.split("&");
		      for(var i = 0; i < strs.length; i ++) {
	          theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);
        }
     }
   return theRequest;
}
//取数配置新增、编辑功能
function set_or_add_param(){
	var myTextarea = document.getElementById('sentence');
	var CodeMirrorEditor = CodeMirror.fromTextArea(myTextarea, {
        matchBrackets: true,
        tabMode: "indent",
        mode: "text/x-mysql",
	});
	$('#logic_type').change(function(){
		if($(this).val()=="SEARCH"){
			$('.span').hide();
		}else if($(this).val()=="PROCEDURE_TABLE"){
			$('.span').show();
		}
	})
	$.post("logic/queryLog/list",function(data){
		for(var i=0;i<data.length;i++){
			$('#selectNum').append("<option value='"+data[i].query_type_title+"'>"
				+data[i].query_type_title
                +"</option>")
		}
	})
	var GetRequest_info=GetRequest();
	var param=GetRequest_info.id;
	if(typeof(param)!=='undefined'){
		$('.c_title .s_param').html('取数配置-编辑');
		var oc = new ObjectControl();
		var logic_data = {
		};
		var logic_id='';
		var logic_url="logic/find/"+param+"";
		oc.webaction("get",logic_url,logic_id,logic_data,function(data){
			console.log(data);
			if(data.code==0){
				var message=JSON.parse(data.message);
				$('#title').val(message.title);
				$('#table_code').val(message.table_code);
				$('#table_name').val(message.table_name);
				$('#creationdate_field').val(message.src_created_date_field);
				$('#c_t_type').val(message.src_created_date_type);
				$('#modifieddate_field').val(message.src_modified_date_field);
				$('#m_t_type').val(message.src_modified_date_type);
				$('#s_p_type').val(message.query_type);
				$('#level').val(message.level);
				$('#corp_code').val(message.corp_code);
				$('#date_type').val(message.time_format);//日期格式
				$('#data_source option[value="'+message.data_source +'"]').attr("selected",true);
				CodeMirrorEditor.setValue(message.sql_expression);
				if(message.logic_type=='SEARCH'){
					$("#logic_type option[value='SEARCH']").attr("selected",true);
				}else if(message.logic_type=='PROCEDURE_TABLE'){
					$("#logic_type option[value='PROCEDURE_TABLE']").attr("selected",true);
					$('.span').show();
					$('#procedure_table').val(message.procedure_table);
				}
				if(message.query_type=='TIME'){
					$('.appoint_time input').show();
					$('.check_note').show();
					$('#start_time').val(message.start_time);
					$('#finish_time').val(message.finish_time);
					// $('#date_type').val(message.time_format);
					$('#s_type em').slideDown();
					$('.t_select_box').slideDown();
					$('#selectData').val(message.src_created_or_modified);
					if(message.begin_and_end=='1'){
						$('#start_end_index').attr('checked','checked')
					}
				}else if(message.query_type=='DEFAULT'){
					$('#start_time').show();
					$('#date_type').show();
					$('#s_type em').slideDown();
					$('.t_select_box').slideDown();
					$('#start_time').val(message.last_query_time);
					// $('#date_type').val(message.time_format);
					$('#selectData').val(message.created_or_modified);
					$("#selectNum option[value='"+message.incremental_query_time+"']").attr("selected",true);
				}
			}else if(data.code==-1){
				alert(data.message);
			}
		});
	}else if(typeof(param)=='undefined'){
		$('.c_title .s_param').html('取数配置-新增');
	}
	$('.btn_submit').on('click',function(){
		var corp_code=$('#corp_code').val();
		var data_source=$('#data_source').val();//数据源
		console.log(corp_code);
		$('#selectData').val();
		if($('#start_end_index').attr('checked')=='checked'){
			$('#start_end_index').val("1");
		}else{
			$('#start_end_index').val("2");
		}
		var oc = new ObjectControl();
		if($('#logic_type').val()=="SEARCH"){
			if($('#s_p_type').val()=='TIME'){
				if(corp_code!=''&&$('#title').val()!==''&&$('#table_code').val()!==''&&$('#table_name').val()!==''&&$('#creationdate').val()!==''&&$('#modifieddate').val()!==''&&$('#level').val()!==''&&$('#start_time').val()!==''&&$('#finish_time').val()!==''&&$('#date_type').val()!==''&&CodeMirrorEditor.value!==''&&$('#start_end_index').val()!=''&&$('#selectData').val()!=''){
					if(typeof(param)!=='undefined'){
						var edit_param = {
							"time_format": $('#date_type').val(),
							"data_source": data_source,
							"corp_code": corp_code,
							"logic_type": $('#logic_type').val(),
							"id": param,
							"title": $('#title').val(),
							"table_code": $('#table_code').val(),
							"table_name": $('#table_name').val(),
							"src_created_date_field": $('#creationdate_field').val(),
							"src_created_date_type": $('#c_t_type').val(),
							"src_modified_date_field": $('#modifieddate_field').val(),
							"src_modified_date_type": $('#m_t_type').val(),
							"level": $('#level').val(),
							"query_type": $('#s_p_type').val(),
							"start_time": $('#start_time').val(),
							"finish_time": $('#finish_time').val(),
							"sql_expression": CodeMirrorEditor.getValue(),
							"begin_and_end": $('#start_end_index').val(),
							"created_or_modified": $('#selectData').val()
						};
						oc.webaction("post","logic/list/edit",'logic_edit',edit_param,function(data){
							if(data.code==0){
								window.location.href='logic';
							}else if(data.code==-1){
								alert(data.message)
							}
						});
					}else{
						var add_param = {
							"time_format": $('#date_type').val(),
							"data_source": data_source,
							"corp_code": corp_code,
							"logic_type": $('#logic_type').val(),
							"title": $('#title').val(),
							"table_code": $('#table_code').val(),
							"table_name": $('#table_name').val(),
							"src_created_date_field": $('#creationdate_field').val(),
							"src_created_date_type": $('#c_t_type').val(),
							"src_modified_date_field": $('#modifieddate_field').val(),
							"src_modified_date_type": $('#m_t_type').val(),
							"level": $('#level').val(),
							"query_type": $('#s_p_type').val(),
							"start_time": $('#start_time').val(),
							"finish_time": $('#finish_time').val(),
							"sql_expression": CodeMirrorEditor.getValue(),
							"begin_and_end": $('#start_end_index').val(),
							"created_or_modified": $('#selectData').val()
						};
						oc.webaction("post","logic/list/add",'logic_add',add_param,function(data){
							if(data.code==0){
								window.location.href='logic';
							}else if(data.code==-1){
								alert(data.message)
							}
						});
					}
				}else{
					alert('请将信息填写完整！');
				}
			}else if($('#s_p_type').val()=='DEFAULT'){
				if(corp_code!=''&&$('#selectNum').val()!==''&&$('#title').val()!==''&&$('#table_code').val()!==''&&$('#table_name').val()!==''&&$('#creationdate').val()!==''&&$('#modifieddate').val()!==''&&$('#level').val()!==''&&$('#start_time').val()!==''&&$('#date_type').val()!==''&&CodeMirrorEditor.value!==''){
					if(typeof(param)!=='undefined'){
						var edit_param = {
							"time_format": $('#date_type').val(),
							"data_source": data_source,
							"corp_code": corp_code,
							"incremental_query_time": $('#selectNum').val(),
							"logic_type": $('#logic_type').val(),
							"id": param,
							"title": $('#title').val(),
							"table_code": $('#table_code').val(),
							"table_name": $('#table_name').val(),
							"src_created_date_field": $('#creationdate_field').val(),
							"src_created_date_type": $('#c_t_type').val(),
							"src_modified_date_field": $('#modifieddate_field').val(),
							"src_modified_date_type": $('#m_t_type').val(),
							"level": $('#level').val(),
							"last_query_time": $('#start_time').val(),
							"query_type": $('#s_p_type').val(),
							"created_or_modified": $('#selectData').val(),
							"sql_expression": CodeMirrorEditor.getValue()
						};
						oc.webaction("post","logic/list/edit",'logic_edit',edit_param,function(data){
							if(data.code==0){
								window.location.href='logic';
							}else if(data.code==-1){
								alert(data.message)
							}
						});
					}else{
						var add_param = {
							"time_format": $('#date_type').val(),
							"data_source": data_source,
							"corp_code": corp_code,
							"incremental_query_time": $('#selectNum').val(),
							"logic_type": $('#logic_type').val(),
							"title": $('#title').val(),
							"table_code": $('#table_code').val(),
							"table_name": $('#table_name').val(),
							"src_created_date_field": $('#creationdate_field').val(),
							"src_created_date_type": $('#c_t_type').val(),
							"src_modified_date_field": $('#modifieddate_field').val(),
							"src_modified_date_type": $('#m_t_type').val(),
							"level": $('#level').val(),
							"last_query_time": $('#start_time').val(),
							"query_type": $('#s_p_type').val(),
							"created_or_modified": $('#selectData').val(),
							"sql_expression": CodeMirrorEditor.getValue()
						};
						oc.webaction("post","logic/list/add",'logic_add',add_param,function(data){
							if(data.code==0){
								window.location.href='logic';
							}else if(data.code==-1){
								alert(data.message)
							}
						});
					}
				}else{
					alert('请将信息填写完整！');
				}
			}else{
				if($('#date_type').val()!==''&&corp_code!=''&&$('#title').val()!==''&&$('#table_code').val()!==''&&$('#table_name').val()!==''&&$('#creationdate_field').val()!==''&&$('#modifieddate_field').val()!==''&&$('#level').val()!==''&&CodeMirrorEditor.value!==''){
					if(typeof(param)!=='undefined'){
						var edit_param={"time_format":$('#date_type').val(),"data_source":data_source,"corp_code":corp_code,"logic_type":$('#logic_type').val(),"id":param,"title":$('#title').val(),"table_code":$('#table_code').val(),"table_name":$('#table_name').val(),"src_created_date_field":$('#creationdate_field').val(),"src_created_date_type":$('#c_t_type').val(),"src_modified_date_field":$('#modifieddate_field').val(),"src_modified_date_type":$('#m_t_type').val(),"level":$('#level').val(),"query_type":$('#s_p_type').val(),"created_or_modified":$('#selectData').val(),"sql_expression":CodeMirrorEditor.getValue()};
						oc.webaction("post","logic/list/edit",'logic_edit',edit_param,function(data){
							if(data.code==0){
								window.location.href='logic';
							}else if(data.code==-1){
								alert(data.message)
							}
						});
					}else{
						var add_param = {
							"time_format": $('#date_type').val(),
							"data_source": data_source,
							"corp_code": corp_code,
							"logic_type": $('#logic_type').val(),
							"title": $('#title').val(),
							"table_code": $('#table_code').val(),
							"table_name": $('#table_name').val(),
							"src_created_date_field": $('#creationdate_field').val(),
							"src_created_date_type": $('#c_t_type').val(),
							"src_modified_date_field": $('#modifieddate_field').val(),
							"src_modified_date_type": $('#m_t_type').val(),
							"level": $('#level').val(),
							"query_type": $('#s_p_type').val(),
							"created_or_modified": $('#selectData').val(),
							"sql_expression": CodeMirrorEditor.getValue()
						};
						oc.webaction("post","logic/list/add",'logic_add',add_param,function(data){
							if(data.code==0){
								window.location.href='logic';
							}else if(data.code==-1){
								alert(data.message)
							}
						});
					}
				}else{
					alert('请将信息填写完整！');
				}
			}
		}else if($('#logic_type').val()=="PROCEDURE_TABLE"){
			if($('#s_p_type').val()=='TIME'){
				if(corp_code!=''&&$('#procedure_table').val()!==''&&$('#title').val()!==''&&$('#table_code').val()!==''&&$('#table_name').val()!==''&&$('#creationdate').val()!==''&&$('#modifieddate').val()!==''&&$('#level').val()!==''&&$('#start_time').val()!==''&&$('#finish_time').val()!==''&&$('#date_type').val()!==''&&CodeMirrorEditor.value!==''&&$('#start_end_index').val()!=''&&$('#selectData').val()!=''){
					if(typeof(param)!=='undefined'){
						var edit_param = {
							"time_format": $('#date_type').val(),
							"data_source": data_source,
							"corp_code": corp_code,
							"logic_type": $('#logic_type').val(),
							"procedure_table": $('#procedure_table').val(),
							"id": param,
							"title": $('#title').val(),
							"table_code": $('#table_code').val(),
							"table_name": $('#table_name').val(),
							"src_created_date_field": $('#creationdate_field').val(),
							"src_created_date_type": $('#c_t_type').val(),
							"src_modified_date_field": $('#modifieddate_field').val(),
							"src_modified_date_type": $('#m_t_type').val(),
							"level": $('#level').val(),
							"query_type": $('#s_p_type').val(),
							"start_time": $('#start_time').val(),
							"finish_time": $('#finish_time').val(),
							"sql_expression": CodeMirrorEditor.getValue(),
							"begin_and_end": $('#start_end_index').val(),
							"created_or_modified": $('#selectData').val()
						};
						oc.webaction("post","logic/list/edit",'logic_edit',edit_param,function(data){
							if(data.code==0){
								window.location.href='logic';
							}else if(data.code==-1){
								alert(data.message)
							}
						});
					}else{
						var add_param = {
							"time_format": $('#date_type').val(),
							"data_source": data_source,
							"corp_code": corp_code,
							"logic_type": $('#logic_type').val(),
							"procedure_table": $('#procedure_table').val(),
							"title": $('#title').val(),
							"table_code": $('#table_code').val(),
							"table_name": $('#table_name').val(),
							"src_created_date_field": $('#creationdate_field').val(),
							"src_created_date_type": $('#c_t_type').val(),
							"src_modified_date_field": $('#modifieddate_field').val(),
							"src_modified_date_type": $('#m_t_type').val(),
							"level": $('#level').val(),
							"query_type": $('#s_p_type').val(),
							"start_time": $('#start_time').val(),
							"finish_time": $('#finish_time').val(),
							"sql_expression": CodeMirrorEditor.getValue(),
							"begin_and_end": $('#start_end_index').val(),
							"created_or_modified": $('#selectData').val()
						};
						oc.webaction("post","logic/list/add",'logic_add',add_param,function(data){
							if(data.code==0){
								window.location.href='logic';
							}else if(data.code==-1){
								alert(data.message)
							}
						});
					}
				}else{
					alert('请将信息填写完整！');
				}
			}else if($('#s_p_type').val()=='DEFAULT'){
				if(corp_code!=''&&$('#procedure_table').val()!==''&&$('#title').val()!==''&&$('#table_code').val()!==''&&$('#table_name').val()!==''&&$('#creationdate').val()!==''&&$('#modifieddate').val()!==''&&$('#level').val()!==''&&$('#start_time').val()!==''&&$('#date_type').val()!==''&&CodeMirrorEditor.value!==''){
					if(typeof(param)!=='undefined'){
						var edit_param = {
							"time_format": $('#date_type').val(),
							"data_source": data_source,
							"corp_code": corp_code,
							"logic_type": $('#logic_type').val(),
							"procedure_table": $('#procedure_table').val(),
							"id": param,
							"title": $('#title').val(),
							"table_code": $('#table_code').val(),
							"table_name": $('#table_name').val(),
							"src_created_date_field": $('#creationdate_field').val(),
							"src_created_date_type": $('#c_t_type').val(),
							"src_modified_date_field": $('#modifieddate_field').val(),
							"src_modified_date_type": $('#m_t_type').val(),
							"level": $('#level').val(),
							"last_query_time": $('#start_time').val(),
							"query_type": $('#s_p_type').val(),
							"created_or_modified": $('#selectData').val(),
							"sql_expression": CodeMirrorEditor.getValue()
						};
						oc.webaction("post","logic/list/edit",'logic_edit',edit_param,function(data){
							if(data.code==0){
								window.location.href='logic';
							}else if(data.code==-1){
								alert(data.message)
							}
						});
					}else{
						var add_param = {
							"time_format": $('#date_type').val(),
							"data_source": data_source,
							"corp_code": corp_code,
							"logic_type": $('#logic_type').val(),
							"procedure_table": $('#procedure_table').val(),
							"title": $('#title').val(),
							"table_code": $('#table_code').val(),
							"table_name": $('#table_name').val(),
							"src_created_date_field": $('#creationdate_field').val(),
							"src_created_date_type": $('#c_t_type').val(),
							"src_modified_date_field": $('#modifieddate_field').val(),
							"src_modified_date_type": $('#m_t_type').val(),
							"level": $('#level').val(),
							"last_query_time": $('#start_time').val(),
							"time_format": $('#date_type').val(),
							"query_type": $('#s_p_type').val(),
							"created_or_modified": $('#selectData').val(),
							"sql_expression": CodeMirrorEditor.getValue()
						};
						oc.webaction("post","logic/list/add",'logic_add',add_param,function(data){
							if(data.code==0){
								window.location.href='logic';
							}else if(data.code==-1){
								alert(data.message)
							}
						});
					}
				}else{
					alert('请将信息填写完整！');
				}
			}else{
				if($('#date_type').val()!==''&&corp_code!=''&&$('#procedure_table').val()!==''&&$('#title').val()!==''&&$('#table_code').val()!==''&&$('#table_name').val()!==''&&$('#creationdate_field').val()!==''&&$('#modifieddate_field').val()!==''&&$('#level').val()!==''&&CodeMirrorEditor.value!==''){
					if(typeof(param)!=='undefined'){
						var edit_param = {
							"time_format": $('#date_type').val(),
							"data_source": data_source,
							"corp_code": corp_code,
							"logic_type": $('#logic_type').val(),
							"procedure_table": $('#procedure_table').val(),
							"id": param,
							"title": $('#title').val(),
							"table_code": $('#table_code').val(),
							"table_name": $('#table_name').val(),
							"src_created_date_field": $('#creationdate_field').val(),
							"src_created_date_type": $('#c_t_type').val(),
							"src_modified_date_field": $('#modifieddate_field').val(),
							"src_modified_date_type": $('#m_t_type').val(),
							"level": $('#level').val(),
							"query_type": $('#s_p_type').val(),
							"created_or_modified": $('#selectData').val(),
							"sql_expression": CodeMirrorEditor.getValue()
						};
						oc.webaction("post","logic/list/edit",'logic_edit',edit_param,function(data){
							if(data.code==0){
								window.location.href='logic';
							}else if(data.code==-1){
								alert(data.message)
							}
						});
					}else{
						var add_param = {
							"time_format": $('#date_type').val(),
							"data_source": data_source,
							"corp_code": corp_code,
							"logic_type": $('#logic_type').val(),
							"procedure_table": $('#procedure_table').val(),
							"title": $('#title').val(),
							"table_code": $('#table_code').val(),
							"table_name": $('#table_name').val(),
							"src_created_date_field": $('#creationdate_field').val(),
							"src_created_date_type": $('#c_t_type').val(),
							"src_modified_date_field": $('#modifieddate_field').val(),
							"src_modified_date_type": $('#m_t_type').val(),
							"level": $('#level').val(),
							"query_type": $('#s_p_type').val(),
							"created_or_modified": $('#selectData').val(),
							"sql_expression": CodeMirrorEditor.getValue()
						};
						oc.webaction("post","logic/list/add",'logic_add',add_param,function(data){
							if(data.code==0){
								window.location.href='logic';
							}else if(data.code==-1){
								alert(data.message)
							}
						});
					}
				}else{
					alert('请将信息填写完整！');
				}
			}
		}
	});
	//逻辑开发的预览功能
	var pageNumber=1;//默认是第一页
    var pageSize=10;//默认传的每页多少行
    var add_param="";//先设置这个变量为空，是编辑的情况下
    var editor_param="";//新增的变量
    var cout="";
    function yanZheng(){//预览功能的验证和请求方法
    	if($('#s_p_type').val()=='TIME'){
			if($('#title').val()!==''&&$('#table_code').val()!==''&&$('#table_name').val()!==''&&$('#creationdate_field').val()!==''&&$('#modifieddate_field').val()!==''&&$('#level').val()!==''&&$('#start_time').val()!==''&&$('#finish_time').val()!==''&&$('#date_type').val()!==''&&CodeMirrorEditor.value!==''){
				if(typeof(param)!=='undefined'){
					jQuery('#main_body').hide();
					jQuery('#content').show();
					add_param={"pageNumber":pageNumber,"pageSize":pageSize,"id":param,"title":$('#title').val(),"table_code":$('#table_code').val(),"table_name":$('#table_name').val(),"src_created_date_field":$('#creationdate_field').val(),"src_created_date_type":$('#c_t_type').val(),"src_modified_date_field":$('#modifieddate_field').val(),"src_modified_date_type":$('#m_t_type').val(),"level":$('#level').val(),"query_type":$('#s_p_type').val(),"start_time":$('#start_time').val(),"finish_time":$('#finish_time').val(),"time_format":$('#date_type').val(),"sql_expression":CodeMirrorEditor.getValue()};
					shade();
					ox.webaction("post","logic/preview/list",'1',add_param,function(data){
						console.log(data);
						if(data.code=="0"){
                			message=JSON.parse(data.message);
		                	content=message.content;
		                	cout=message.totalPages;
		                	superaddition(content);
		                	jumpBianse();
		                	setPage($("#foot-num")[0],cout,pageNumber,pageSize,add_param,editor_param);
		            	}else if(data.code=="-1"){
		            		toShade();
		                	alert(data.message);
		               }
		            })
				}else{
					jQuery('#main_body').hide();
					jQuery('#content').show();
					editor_param={"pageNumber":pageNumber,"pageSize":pageSize,"title":$('#title').val(),"table_code":$('#table_code').val(),"table_name":$('#table_name').val(),"src_created_date_field":$('#creationdate_field').val(),"src_created_date_type":$('#c_t_type').val(),"src_modified_date_field":$('#modifieddate_field').val(),"src_modified_date_type":$('#m_t_type').val(),"level":$('#level').val(),"query_type":$('#s_p_type').val(),"start_time":$('#start_time').val(),"finish_time":$('#finish_time').val(),"time_format":$('#date_type').val(),"sql_expression":CodeMirrorEditor.getValue()};
		   			shade();
		   			ox.webaction("post","logic/preview/list",'2',editor_param,function(data){
		   				console.log(data);
						if(data.code=="0"){
                			message=JSON.parse(data.message);
		                	content=message.content;
		                	cout=message.totalPages;
		                	superaddition(content);
		                	jumpBianse();
		                	setPage($("#foot-num")[0],cout,pageNumber,pageSize,add_param,editor_param);
		            	}else if(data.code=="-1"){
		            		toShade();
		                	alert(data.message);       	
		               }
		            })
				}
			}else{
				alert('请将信息填写完整！');
			}
		}else if($('#s_p_type').val()=='DEFAULT'){
			if($('#title').val()!==''&&$('#table_code').val()!==''&&$('#table_name').val()!==''&&$('#creationdate_field').val()!==''&&$('#modifieddate_field').val()!==''&&$('#level').val()!==''&&$('#start_time').val()!==''&&$('#date_type').val()!==''&&CodeMirrorEditor.value!==''){
				if(typeof(param)!=='undefined'){
					jQuery('#main_body').hide();
					jQuery('#content').show();
					add_param={"pageNumber":pageNumber,"pageSize":pageSize,"id":param,"title":$('#title').val(),"table_code":$('#table_code').val(),"table_name":$('#table_name').val(),"src_created_date_field":$('#creationdate_field').val(),"src_created_date_type":$('#c_t_type').val(),"src_modified_date_field":$('#modifieddate_field').val(),"src_modified_date_type":$('#m_t_type').val(),"level":$('#level').val(),"last_query_time":$('#start_time').val(),"time_format":$('#date_type').val(),"query_type":$('#s_p_type').val(),"created_or_modified":$('#selectData').val(),"sql_expression":CodeMirrorEditor.getValue()};
		   			shade();
		   			ox.webaction("post","logic/preview/list",'2',add_param,function(data){
		   				console.log(data);
						if(data.code=="0"){
                			message=JSON.parse(data.message);
		                	content=message.resultList;
		                	cout=message.totalPages;
		                	superaddition(content);
		                	jumpBianse();
		                	setPage($("#foot-num")[0],cout,pageNumber,pageSize,add_param,editor_param);
		            	}else if(data.code=="-1"){
		            		toShade();
		                	alert(data.message);  	
		               }
		            })
				}else{
					jQuery('#main_body').hide();
					jQuery('#content').show();
					editor_param={"pageNumber":pageNumber,"pageSize":pageSize,"title":$('#title').val(),"table_code":$('#table_code').val(),"table_name":$('#table_name').val(),"src_created_date_field":$('#creationdate_field').val(),"src_created_date_type":$('#c_t_type').val(),"src_modified_date_field":$('#modifieddate_field').val(),"src_modified_date_type":$('#m_t_type').val(),"level":$('#level').val(),"last_query_time":$('#start_time').val(),"time_format":$('#date_type').val(),"query_type":$('#s_p_type').val(),"created_or_modified":$('#selectData').val(),"sql_expression":CodeMirrorEditor.getValue()};
					shade();
					ox.webaction("post","logic/preview/list",'2',editor_param,function(data){
						console.log(data);
						if(data.code=="0"){
                			message=JSON.parse(data.message);
		                	content=message.resultList;
		                	cout=message.totalPages;
		                	superaddition(content);
		                	jumpBianse();
		                	setPage($("#foot-num")[0],cout,pageNumber,pageSize,add_param,editor_param);
		            	}else if(data.code=="-1"){
		            		toShade();
		                	alert(data.message);
		               }
		            })
				}
			}else{
				alert('请将信息填写完整！');
			}
		}else{
			if($('#title').val()!==''&&$('#table_code').val()!==''&&$('#table_name').val()!==''&&$('#creationdate_field').val()!==''&&$('#modifieddate_field').val()!==''&&$('#level').val()!==''&&CodeMirrorEditor.value!==''){
				if(typeof(param)!=='undefined'){
					jQuery('#main_body').hide();
					jQuery('#content').show();
					add_param={"pageNumber":pageNumber,"pageSize":pageSize,"id":param,"title":$('#title').val(),"table_code":$('#table_code').val(),"table_name":$('#table_name').val(),"src_created_date_field":$('#creationdate_field').val(),"src_created_date_type":$('#c_t_type').val(),"src_modified_date_field":$('#modifieddate_field').val(),"src_modified_date_type":$('#m_t_type').val(),"level":$('#level').val(),"query_type":$('#s_p_type').val(),"sql_expression":CodeMirrorEditor.getValue()};
					shade();
					ox.webaction("post","logic/preview/list",'2',add_param,function(data){
						console.log(data);
						if(data.code=="0"){
                			message=JSON.parse(data.message);
		                	content=message.resultList;
		                	cout=message.totalPages;
		                	superaddition(content);
		                	jumpBianse();
		                	setPage($("#foot-num")[0],cout,pageNumber,pageSize,add_param,editor_param);
		            	}else if(data.code=="-1"){
		            		toShade();
		                	alert(data.message);
		               }
		            })
				}else{
					jQuery('#main_body').hide();
					jQuery('#content').show();
					editor_param={"pageNumber":pageNumber,"pageSize":pageSize,"title":$('#title').val(),"table_code":$('#table_code').val(),"table_name":$('#table_name').val(),"src_created_date_field":$('#creationdate_field').val(),"src_created_date_type":$('#c_t_type').val(),"src_modified_date_field":$('#modifieddate_field').val(),"src_modified_date_type":$('#m_t_type').val(),"level":$('#level').val(),"query_type":$('#s_p_type').val(),"sql_expression":CodeMirrorEditor.getValue()};
					shade();
					ox.webaction("post","logic/preview/list",'2',editor_param,function(data){
						console.log(data);
						if(data.code=="0"){
                			message=JSON.parse(data.message);
		                	content=message.resultList;
		                	cout=message.totalPages;
		                	superaddition(content);
		                	jumpBianse();
		                	setPage($("#foot-num")[0],cout,pageNumber,pageSize,add_param,editor_param);
		            	}else if(data.code=="-1"){
		            		toShade();
		                	alert(data.message);
		               }
		            })
				}
			}else{
				alert('请将信息填写完整！');
			}
		}
    };
    //预览的关闭功能
    $("#reurn").click(function(){
    	jQuery('#main_body').show();
	    jQuery('#content').hide();
    })
    //跳转页面的键盘按下事件
    $("#input-txt").keydown(function() {
	    var event=window.event||arguments[0];
		pageNumber= this.value.replace(/[^1-9]/g, '');
		if (pageNumber > cout) {
				pageNumber = cout
			};
		if (pageNumber > 0) {
			if (event.keyCode == 13) {
				yanZheng();
			};
		}
	})
    $("#pageSize").change(function(){
    	pageSize = $(this).val();
    	yanZheng();
    })
	$('.submit_box p').on('click',function(){
		yanZheng();
	});
}
//回收站取数设置只读
function param_return(){
	var myTextarea = document.getElementById('sentence');
	var CodeMirrorEditor = CodeMirror.fromTextArea(myTextarea, {
		readOnly:"nocursor",
        matchBrackets: true,
        tabMode: "indent",
        mode: "text/x-mysql",
        textWrapping: true,
        // lineWrapping:true,
	});
	var GetRequest_info=GetRequest();
	var param=GetRequest_info.id;
	if(typeof(param)!=='undefined'){
		$('.c_title .s_param').html('取数配置-回收站');
		var oc = new ObjectControl();
		var require_data = {
		};
		var require_id='';
		var require_url="logic/find/"+param+"";
		oc.webaction("get",require_url,require_id,require_data,function(data){
			console.log(data);
			if(data.code==0){
				var message=JSON.parse(data.message);
				$('#title').val(message.title);
				$('#table_code').val(message.table_code);
				$('#table_name').val(message.table_name);
				$('#creationdate_field').val(message.src_created_date_field);
				$('#c_t_type').val(message.src_created_date_type);
				$('#modifieddate_field').val(message.src_modified_date_field);
				$('#m_t_type').val(message.src_modified_date_type);
				$('#s_p_type').val(message.query_type);
				$('#level').val(message.level);
				$('#corp_code').val(message.corp_code);
				$('#date_type').val(message.time_format);
				$('#data_source option[value="'+message.data_source +'"]').attr("selected",true);
				CodeMirrorEditor.setValue(message.sql_expression);
				if(message.logic_type=='SEARCH'){
					$("#logic_type option[value='SEARCH']").attr("selected",true);
				}else if(message.logic_type=='PROCEDURE_TABLE'){
					$("#logic_type option[value='PROCEDURE_TABLE']").attr("selected",true);
					$('.span').show();
					$('#procedure_table').val(message.procedure_table);
				}
				if(message.query_type=='TIME'){
					$('.appoint_time input').show();
					$('.check_note').show();
					$('#s_type em').slideDown();
					$('.t_select_box').slideDown();
					$('#start_time').val(message.start_time);
					$('#finish_time').val(message.finish_time);
					// $('#date_type').val(message.time_format);
					$('#selectData').val(message.created_or_modified);
					if(message.begin_and_end=='1'){
						$('#start_end_index').attr('checked','checked')
					}
				}else if(message.query_type=='DEFAULT'){
					$('#start_time').show();
					$('#date_type').show();
					$('#s_type em').slideDown();
					$('.t_select_box').slideDown();
					$('#start_time').val(message.last_query_time);
					// $('#date_type').val(message.time_format);
					$('#selectData').val(message.created_or_modified);
				}
			}else if(data.code==-1){
				alert(data);
			}
		});
	}
}
//回收站编辑任务只读
function manage_return() {
	var GetRequest_info=GetRequest();
	var param=GetRequest_info.id;
	var b=$('.choose_logic_add');
	if(typeof(param)!=='undefined'){
		$('.m_title .e_manage').html('编辑任务-回收站');
		var oc = new ObjectControl();
		var readonly_data={};
		oc.webaction("get","plan/find/"+param+"","",readonly_data,function(data){
			if(data.code==0){
				var data=JSON.parse(data.message);
				var strs=data.logic_list.split(',');
				var select_items=$('.items_box select');
				var op = new ObjectControl();
				var r_data={};
				op.webaction("get","plan/logic","",r_data,function(data){
					if(data.code==0){
						var msg=JSON.parse(data.message);
						for(var i=0;i<select_items.length;i++){
							for(var j=0;j<msg.length;j++){
								var option=document.createElement("option");
								option.text=msg[j].title;
								option.value=msg[j].id;
								select_items[i].add(option,null);
							}
						}
						for(var c=0;c<msg.length;c++){
							if(strs.length==1&&msg[c].id==strs[0]){
								if(msg[c].id==strs[0]){
									var count=$(".choose_logic_add select").get(0).options.length;
									for(var i=0;i<count;i++){
										if($(".choose_logic_add select").get(0).options[i].text == msg[c].title)
										{
											$(".choose_logic_add select").get(0).options[i].selected = true;
											break;
										}
									}
								}
							}
							else if(strs.length>1){
								if(msg[c].id==strs[strs.length-1]){
									var count=$(".choose_logic_add select").get(0).options.length;
									for(var i=0;i<count;i++){
										if($(".choose_logic_add select").get(0).options[i].text == msg[c].title){
											$(".choose_logic_add select").get(0).options[i].selected = true;
											break;
										}
									}
								}
								for(var i=0;i<strs.length-1;i++){
									if(msg[c].id==strs[i]){
										$('<div class="choose_logic flag">'
										+'<span class="c_box">'
										+'<select style="display: inline-flex" disabled="disabled">'
										+'<option value="-1">选择逻辑</option>'
										+'<option value="'+strs[i]+'" selected="selected">'+msg[c].title+'</option>'
										+'</select>'
										+'</span>'
										+'<span class="delete_item btn"><img src="images/icon_table_delete.png" alt="删除" /></span>'
										+'</div>').insertBefore(b);
									}
								}
							}
						}
						var select_flag=$('.flag select');
						for(var i=0;i<select_flag.length;i++){
							for(var j=0;j<msg.length;j++){
								var option=document.createElement("option");
								option.text=msg[j].title;
								option.value=msg[j].id;
								if(select_flag[i].value!==option.value){
									select_flag[i].add(option,null);
								}
							}
						}
					}else if(data.code==-1){
						alert(data.message);
					}
				});
				$('#name').val(data.title);
				$('#manage_type').val(data.plan_type);
				$('#corp_code').val(data.corp_code);
				// $('#target_table').val(data.target_table);
				if(data.push_type=="TARGET_TABLE"){
					$("#changeFile option[value='TARGET_TABLE']").attr("selected",true);
					$("#local_file").val(data.target_table);
				}else if(data.push_type=="FILE_PATH"){
					$("#changeFile option[value='FILE_PATH']").attr("selected",true);
					$("#local_file").val(data.file_path);
				}
				if(data.plan_type=='CYCLE'){
					$('.m_time').show();
					$('.t_item_box').show();
					$('.calendar').hide();
					$('#par_time').val(data.cycle_time);//时间
				}else if(data.plan_type=='TIMING'){
					$('.m_time').hide();
					$('.t_item_box').hide();
					$('.calendar').show();
					$('#clocking').val(data.cycle_time);
				}
				$('#is_show').val(data.on_off);
			}else if(data.code==-1){
				alert(data.message);
			}
		});
	}
}
//参数配置页面的提交功能
var interval;
function check_param(){
	//绑定input text,password类型
	var oc = new ObjectControl();
	jQuery(":text").focus(function() {
		var _this = this;
		interval = setInterval(function() {
			bindBlur(_this);
		}, 100);
	}).blur(function(event) {
		clearInterval(interval);
	});
	oc.webaction("post","/param/find","6","",function(data){
		console.log(data);
		var message=JSON.parse(data.message);
		console.log(message);
		if(message==""){
			reurn;
		}

		if(data.code=="0"){
			$('#user_code').val(message[0].value);
			$('#user_code').attr('date',message[0].id);
			$('#app_code').val(message[2].value);
			$('#app_code').attr('date',message[2].id);
			$('#corp_code').val(message[1].value);
			$('#corp_code').attr('date',message[1].id);
			if(message[3].value=='OFF'){
				 $('#failData').removeClass("bg");
				 $('#failData').attr("date","OFF");
				 $('#failDataSpan').removeClass("ON");
			}
		}else if(data.code=="-1"){
			alert(data.message);
		}
	})
	//开关
	$('.switchf div').click(function(){
		var on_off=$(this).attr("date");
        var div=$(this);
        var span=$(this).find("span");
		console.log(on_off);
		var param={};
		param['on_off']=on_off;
		oc.webaction("post","param/find/logPush","7",param,function(data){
			console.log(data);
			if(data.code=="0"){
				if(data.message=="close"){
					div.removeClass("bg");
                    span.removeClass("ON");
                    div.attr("date","OFF");
				}else if(data.message=="open"){
					div.addClass("bg");
                    span.addClass("ON");
                    div.attr("date","ON");
				}
			}
		})
	})
	$('.btn_submit').on('click',function(){
		var implementer_code=$('#user_code').val();//实施人员编号
		var implementer_name=$('#user_code').attr('data-describe');//实施人员name
		var implementer_id=$('#user_code').attr('name');//实施人员编号id;
		var implementer_num=$('#user_code').attr('date');
		var app_code=$('#app_code').val()//客户端编号
		var app_id=$('#app_code').attr('name');//客户端编号id;
		var app_name=$('#app_code').attr('data-describe');//客户端name
		var app_num=$('#app_code').attr('date');
		var corp_code=$('#corp_code').val();//公司编号
		var crop_id=$('#corp_code').attr('name');//公司编号id;
		var crop_name=$('#corp_code').attr('data-describe');
		var crop_num=$('#corp_code').attr('date');
		var set_param={};
		set_param["implementer_code"]=implementer_code;
		set_param["implementer_id"]=implementer_id;
		set_param["implementer_name"]=implementer_name;
		set_param["implementer_num"]=implementer_num;
		set_param["app_code"]=app_code;
		set_param["app_id"]=app_id;
		set_param["app_name"]=app_name;
		set_param["app_num"]=app_num;
		set_param["corp_code"]=corp_code;
		set_param["crop_id"]=crop_id;
		set_param["crop_name"]=crop_name;
		set_param["crop_num"]=crop_num;
		console.log(set_param);
		if(implementer_code!==''&&app_code!==''&&corp_code!==''){
			oc.webaction("post","/parameter/add","set_parameter",set_param,function(data){
				console.log(data);
				if(data.code==0){
					window.location.href='logic';
				}else if(data.code==-1){
					alert(data.message);
				}
			});
		}
	})
}
//绑定验证
var count = {};//计算input输入不为空的个数
var inputNum=3;//校验个数
var displayInput;//提交按钮
var bindBlur = function(el){//绑定函数，根据校验规则调用相应的校验函数
	var	_this = jQuery(el);
	var obj = _this.val();
	if(obj!==''){
		$('label.'+el.id).css('display',"none");
		count[_this.attr("id")];
	}else{
		$('label.'+el.id).css('display',"block");
		if(count[_this.attr("id")]){
			delete count[_this.attr("id")];
			$(".btn_submit").attr("disabled",true);
			$(".btn_submit").addClass("disabled");
		}
		return false;
	}
	count[_this.attr("id")] = true;
	var countNum = 0;
	for(var i in count){
		countNum ++;
	}
	if(countNum == inputNum){
		$(".btn_submit").removeClass("disabled");
	    $(".btn_submit").attr("disabled",false);
	}
	return true;
};
function bindBlur2(){
	    var GetRequest_info=GetRequest();
	    var id=GetRequest_info.id;
		var obj =$('#name').val();
		var oc = new ObjectControl();
		var param_blur={"title":obj,"id":id};
		oc.webaction("post","/plan/check/title","verify",param_blur,function(data){
			console.log(data);
			var msg=data.message;
			if(msg=="Y"){
				$('label.name').css('display',"none");
			}else if(msg=="N"){
				$('label.name').css('display',"block");
			}
		});
};
//表格的页面加载循环部分
function superaddition(data){
	console.log(data);
	var a=JSON.parse(data);
	var th;
	var tr;
	for (var key in a[0]) {
        th+="<th class='th'>"+key+"</th>";
	}
	$(".table tbody").empty();
	$(".table thead").empty();
	$(".table thead").append("<tr>"+th+"</tr>");
	for(var i=0;i<a.length;i++){
		tr="<tr id='"+a[i].ID+"'>";
		for (var key in a[i]) {
	        tr+="<td>"+a[i][key]+"</td>";
		}
	    tr+="</tr>";
	    $(".table tbody").append(tr);
	}
	toShade();
}
//表格的分页部分
function setPage(container, count, pageindex,pageSize,add_param,editor_param) {
	var container = container;
	var pageindex = pageindex;
	var a = [];
	function dian(inx){
		if(add_param!==""){//当是编辑的预览
			add_param["pageSize"]=pageSize;
			add_param["pageNumber"]=inx;
			shade();
		    ox.webaction("post","logic/preview/list",'2',add_param,function(data){
				 if(data.code=="0"){
		                message=JSON.parse(data.message);
				        content=message.resultList;
				        cout=message.totalPages;
				        superaddition(content);
				        jumpBianse();
			        }else if(data.code=="-1"){
			        		toShade();
			            	alert(data.message);
			           }
			})
		}else if(editor_param!==""){//当是新增的预览
			editor_param["pageSize"]=pageSize;
			editor_param["pageNumber"]=inx;
			shade();
			ox.webaction("post","logic/preview/list",'2',editor_param,function(data){
				    if(data.code=="0"){
		                message=JSON.parse(data.message);
				        content=message.resultList;
				        cout=message.totalPages;
				        superaddition(content);
				        jumpBianse();
			        }else if(data.code=="-1"){
			        	    toShade();
			            	alert(data.message);
			        }
			})
		}
	}
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
		// console.log(inx);
		// console.log(count);
		$("#input-txt").val(inx);
		$(".foot-sum .zy").html("共 " + count + "页");
		oAlink[0].onclick = function() { //点击上一页
			if (inx == 1) {
				return false;
			}
			inx--;
			setPage(container, count, inx,pageSize,add_param,editor_param);
			dian(inx);
			return false;
		}
		for (var i = 1; i < oAlink.length - 1; i++) { //点击页码
			oAlink[i].onclick = function() {
				inx = parseInt(this.innerHTML);
				dian(inx);
				setPage(container, count, inx,pageSize,add_param,editor_param);
				return false;
			}
		}
		oAlink[oAlink.length - 1].onclick = function() { //点击下一页
			if (inx == count) {
				return false;
			}
			inx++;
			dian(inx);
			setPage(container, count, inx,pageSize,add_param,editor_param);
			return false;
		}
	}()
}
//隔行变色
function jumpBianse(){
	$(document).ready(function(){//隔行变色
   		 $(".table tbody tr:even").css("backgroundColor","#f1f1f1");
	})
}
function shade(){//正在加载中
    	jQuery('body').append(oMask);
	    jQuery('body').append(img);
	    jQuery('.mask').width(maskWidth).height(maskHeight);
	    jQuery('.imgMask').css({"top":maskHeight/2+"px","left":maskWidth/2+"px"});
    }
function toShade(){//去除遮罩
        jQuery('.mask').remove();
        jQuery('.imgMask').remove();
}
//弹框
function kuang(){
    $("#frame").css("left",""+left+"px");
    $("#frame").css("top",""+tp+"px");
    $("#frame").show();
    $("#frame").animate({
        opacity:"1"
    },1000);
    $("#frame").animate({
        opacity:"0"
    },1000);
    setTimeout(function(){
        $("#frame").hide();
    },2000)
}

