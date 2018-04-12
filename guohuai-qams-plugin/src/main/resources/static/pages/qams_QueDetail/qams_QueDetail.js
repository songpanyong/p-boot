/**
 * 答卷示例
 */
define([
	'http',
	'qams.config',
	'qams.ext.config',
	'config',
	'util',
	'doT.min',
	'extension'
], function(http, qamsconfig, extconfig,config, util,doT, $$) {
	return {
		name: 'qams_QueDetail',
		init: function() { 
			var type="";
			// js逻辑写在这里
			function getchType(val){
				console.log("getTypestart");
				console.log(val);
				var types = new Array();
				types=extconfig.TYPES;
				for(var i=0;i<types.length;i++){
					
					var id=types.id
					console.log(id);
					if(val=id)
					var t=types.name;
				      val = t;
				}
				return val
			}
			
			
			
			//页面ID
			var pageState = {
				sid: util.nav.getHashObj(location.hash).id || '',
				oid: util.nav.getHashObj(location.hash).sid || ''
			};
			if(pageState.oid!=''){
				$("#queID").attr('value',pageState.oid);
				pageState.sid=pageState.oid;
			}
			function addhtml(){
				$("#addanswer").on('click',function() {
					  // var num = $("#answerList >div").length;
					 //  num=num-1;
					   var namestr=$("#addanswer").prev().find('input').get(0).name;
					   var num=namestr.substring(namestr.indexOf("[")+1,namestr.indexOf("]"));
					   num=parseInt(num)+1;
					    $("#addanswer").before("<div  class='form-group'><input type='text' style='width:60px;float:left;margin: 5px' name='answers["+num+"].sn' class='form-control input-sm' required data-error='答案序号不能为空'/><input type='text' class='form-control input-sm' name='answers["+num+"].content'  style='width:350px;float:left;margin: 5px' class='form-control input-sm' required data-error='答案内容不能为空' placeholder='答案内容'><input type='text' style='width:45px;float:left;margin: 5px' class='form-control input-sm' name='answers["+num+"].score' required data-error='答案分数不能为空'  placeholder='分数'><button  type='button' class='btn btn-default btn-sm'  style='margin:5px'>删除</button></div>");
						    $(".form-group > button").click(function(){
						        $(this).parent().remove(); 
						        $("#addQamsQueForm").validator('destroy');
								util.form.validator.init($("#addQamsQueForm"));
						 });
						    $("#addQamsQueForm").validator('destroy');
							util.form.validator.init($("#addQamsQueForm"));
					});
					$('.form-group >button').on('click', function() {
						   $(this).parent().remove();
						   $("#addQamsQueForm").validator('destroy');
							util.form.validator.init($("#addQamsQueForm"));
						
					});
			}
			function editbtn(){
				$('.btn-edit').on('click', function() {
					var qid=$(this).attr("id");
					http.post(qamsconfig.api.qamsQuestion.getQuesById, {
						data: {
							qid: qid
						},
						contentType: 'form'
					}, function(result) {
						if (result.errorCode == 0) {
							var data = result;
							$$.formAutoFix($('#addQamsQueForm'), data); // 自动填充表单
							http.post(qamsconfig.api.qamsQuestion.getAnswer,{
								data: {
									iid: qid
								},
								contentType: 'form'}, function(result) {
								var answers="";
								$("#answerList").html('<div id="addanswer" class="btn btn-default">新增答案</div>');
								for(var num=0;num<result.length;num++){
									var ans=result[num];
									answers+="<div class='form-group'><input type='text' style='width:60px;float:left;margin: 5px' name='answers["+num+"].sn' class='form-control input-sm' value='"+ans.sn+"' required data-error='答案序号不能为空'/><input type='text' class='form-control input-sm' name='answers["+num+"].content' value='"+ans.content+"' style='width:350px;float:left;margin: 5px'  class='form-control input-sm' required data-error='答案内容不能为空' placeholder='答案内容' ><input type='text' style='width:45px;float:left;margin: 5px' class='form-control input-sm' name='answers["+num+"].score' value='"+ans.score+"' required data-error='答案分数不能为空' placeholder='分数' ><button  type='button' class='btn btn-default btn-sm'  style='margin:5px'>删除</button></div>";
								}
								$("#answerList").prepend(answers);
								addhtml();
							});
							util.form.validator.init($('#addQamsQueForm'))
							$('#editTitle').html("编辑调查问题");
							$('#addQuestionModal').modal('show');

						} else {
							alert(查询失败);
						}
					})
				});
			}
			function quedetail(){
				//根据问卷ID查询问卷详情
				http.post(qamsconfig.api.qamsQue.getQueById,{
					data: {
						sid: pageState.sid
					},
					contentType: 'form'
				}, function(json) {
					var types = new Array();
					types=extconfig.QATYPE;
					for(var i=0;i<types.length;i++){
						var t=types[i];
						if (t.ID == json.type){
							 type = t.NAME
						}
					}
					var viewdata = {
						type:type,
						number: json.number,
						minScore: json.minScore,
						maxScore: json.maxScore
					};
					$$.formAutoFix($('#qamsQueDetail'), viewdata);
				});
			}
			function delbtn(){
				$('.btn-del').on('click', function() {
					if(confirm("确认删除吗？")){
						var qid=$(this).attr("id");
						var objes=$(this).parents('.issuemode');
					http.post(qamsconfig.api.qamsQuestion.del, {
							data: {
								qid: qid
							},
							contentType: 'form'
						}, function(result) {
							if (result.errorCode == 0) {
								 objes.remove();
								 quedetail();
							} else {
								alert(查询失败);
							}
						})
					
					}else{
						return;
					}
				});
			}
			function upbtn(){
				$('.btn-up').on('click', function() {
					var old=$(this).parents('tr').find(".cindex").val();
					var newv=$(this).parents('tr').prev().find(".cindex").val();
					if(""!=newv&&undefined!=newv){
					$(this).closest('tr').insertBefore($(this).closest('tr').prev());
					$(this).parents('tr').find(".cindex").val(newv);
					$(this).parents('tr').next().find(".cindex").val(old);
					}
					
				});
			}
			function downbtn(){
				$('.btn-down').on('click', function() {
					var old=$(this).parents('tr').find(".cindex").val();
					var newv=$(this).parents('tr').next().find(".cindex").val();
					if(""!=newv&&undefined!=newv){
					$(this).closest('tr').insertAfter($(this).closest('tr').next());
					$(this).parents('tr').find(".cindex").val(newv);
					$(this).parents('tr').prev().find(".cindex").val(old);
					}
				});
			}
			//问卷切换列表
			http.post(qamsconfig.api.qamsQue.getAllNameList, function(json) {
				var qamsQueOptions = '';
				var select = document.searchForm.queName;
				json.rows.forEach(function(item) {
					qamsQueOptions += '<option value="' + item.sid + '" ' + (item.sid == pageState.sid ? 'selected' : '') + '>' + item.name + '</option>'
				});
				$(select).html(qamsQueOptions);
				
				//改变问卷后刷新页面
				$(document.searchForm.queName).on('change', function() {
					pageState.sid = this.value;
					if(null!=pageState.sid&&""!=pageState.sid){
					//根据问卷ID查询问卷详情
					quedetail();
					//根据问卷ID查询问题答案详情
					http.post(qamsconfig.api.qamsQue.qamsQueResultByid,{
						data: {
							sid: pageState.sid
						},
						contentType: 'form'
					}, function(rlt) {
						var data = rlt.list;
//						console.log(data)
//						var types = new Array();
//						var questypes = new Array();
//						types=rlt.list;
//						for(var i=0;i<types.length;i++){
//							var t =types.chType;
//							extconfig[TYPES].forEach(function(item) {
//								if (item.id === t) {
//									types.chType == item.text
//								}
//							})
//						}
						var t = doT.template($('#dotTemplate').text());
						$("#dataSource").append(t(data));	
						editbtn();
						delbtn();
					});
					}
				}).change();
				
			});
			$(document.searchForm.queName).change();
			
			
			$("#qamsQueAdd").on('click', function() {
				$("#sid").val("");
				$("#answerList").html('<div id="addanswer" class="btn btn-default">新增答案</div>');
				var answers="";
				var character = new Array("A","B","C","D");
				for(var num=0;num<character.length;num++){
					answers+="<div class='form-group'><input type='text' style='width:60px;float:left;margin: 5px' name='answers["+num+"].sn' class='form-control input-sm' value='"+character[num]+"' required data-error='答案序号不能为空'/><input type='text' class='form-control input-sm' name='answers["+num+"].content' style='width:350px;float:left;margin: 5px'  class='form-control input-sm' required data-error='答案内容不能为空' placeholder='答案内容' ><input type='text' style='width:45px;float:left;margin: 5px' class='form-control input-sm' name='answers["+num+"].score' required data-error='答案分数不能为空' placeholder='分数' ><button  type='button' class='btn btn-default btn-sm'  style='margin:5px'>删除</button></div>";
				}
				$("#addanswer").before(answers);
				addhtml();
				var form = document.addQamsQueForm
				
				$(form).validator('destroy')
				util.form.validator.init($(form));

				util.form.reset($('#addQamsQueForm'))
				$('#editTitle').html("新建调查问题");
				$('#addQuestionModal').modal('show')
//				util.nav.dispatch('qams_add_que', 'sid=' + sid)addQuestion
				
			});
			
			$("#qamsQueSort").on('click', function() {
				$("#tbodys").html("");
				if(""!=pageState.sid&&null!=pageState.sid){
				http.post(qamsconfig.api.qamsQue.qamsQueResultByid,{
					data: {
						sid: pageState.sid
					},
					contentType: 'form'
				}, function(rlt) {
					var data = rlt.list;
					console.log(data)
					var t = doT.template($('#sortTemplate').text());
					$("#tbodys").append(t(data));	
					upbtn();
					downbtn();
				});
				}
				$('#sortQuestionModal').modal('show');

			});
			/**
			 * “保存”按钮点击事件
			 */
			$('#saveQamsQueSubmit').on('click', function() {
				if(!$('#addQamsQueForm').validator('doSubmitCheck')) return;
				$("#oid").val($("#queID").val());
				$('#addQamsQueForm').ajaxSubmit({
					url: qamsconfig.api.qamsQuestion.add,     
					success: function(addResult) {
						if (addResult.errorCode == 0) {
						//	$('#addQamsQueForm').reset();
							$("#addQamsQueForm").resetForm();
							$("#sid").val("");
						//	$("#addQamsQueForm").reset();
						//	$("#addQamsQueForm").resetForm();
						//	this.reset();//有效，其他无效
						} else {
							alert(addResult.errorMessage)
						}
					}
				})
			});
			$('#sortQamsQueSubmit').on('click', function() {
				$('#sortQamsQueForm').ajaxSubmit({
					url: qamsconfig.api.qamsQuestion.sort,     
					success: function(addResult) {
						if (addResult.errorCode == 0) {
							$('#sortQuestionModal').modal('hide')
							//根据问卷ID查询问题答案详情
							http.post(qamsconfig.api.qamsQue.qamsQueResultByid,{
								data: {
									sid: $("#queID").val()
								},
								contentType: 'form'
							}, function(rlt) {
								var data = rlt.list;
								console.log(data)
								var t = doT.template($('#dotTemplate').text());
								$("#dataSource").html(t(data));
								editbtn();
								delbtn();
								quedetail();
							});
						} else {
							alert(addResult.errorMessage)
						}
					}
				})
			});
			/**
			 * 提交按钮点击事件
			 */
			$('#addQamsQueSubmit').on('click', function() {
				if(!$('#addQamsQueForm').validator('doSubmitCheck')) return;
				$("#oid").val($("#queID").val());
				$('#addQamsQueForm').ajaxSubmit({
					url: qamsconfig.api.qamsQuestion.add,     
					success: function(addResult) {
						if (addResult.errorCode == 0) {
							$('#addQuestionModal').modal('hide')
							util.form.reset($('#addQamsQueForm'));
							//根据问卷ID查询问题答案详情
							http.post(qamsconfig.api.qamsQue.qamsQueResultByid,{
								data: {
									sid: $("#queID").val()
								},
								contentType: 'form'
							}, function(rlt) {
								var data = rlt.list;
								console.log(data)
								var t = doT.template($('#dotTemplate').text());
								$("#dataSource").html(t(data));
								editbtn();
								delbtn();
								quedetail();
							});
						} else {
							alert(addResult.errorMessage)
						}
					}
				})
				
			});
		}
	}
})