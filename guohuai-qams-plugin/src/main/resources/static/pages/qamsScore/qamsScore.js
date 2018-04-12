/**
 * 评分管理
 */
define([
	'http',
	'qams.config',
	'config',
	'util',
	'extension',
	'qams.ext.config'
], function(http, qamsconfig, config, util, $$,extconfig) {
	return {
		name: 'qamsScore',
		init: function() {
			// js逻辑写在这里
			/**
			 * 数据表格分页、搜索条件配置
			 */
			
			
			var pageOptions = {
				number: 1,
				size: 10,
				que: ''
			}
			
			/**
			 * 表格querystring扩展函数，会在表格每次数据加载时触发，用于自定义querystring
			 * @param {Object} val
			 */
			function getQueryParams(val) {
				var form = document.searchForm
				pageOptions.size = val.limit
				pageOptions.number = parseInt(val.offset / val.limit) + 1
				pageOptions.que = form.que.value.trim()
				return val
			}

			/**
			 * 数据表格配置
			 */
			var tableConfig = {
				ajax: function(origin) {
					http.post(
							qamsconfig.api.qamsScore.search, {
							data: {
								page: pageOptions.number,
								rows: pageOptions.size,
								que: pageOptions.que
							//	status: pageOptions.status
							},
							contentType: 'form'
						},
						function(rlt) {
							origin.success(rlt)
						}
					)
				},
				pageNumber: pageOptions.number,
				pageSize: pageOptions.size,
				pagination: true,
				sidePagination: 'server',
				pageList: [10, 20, 30, 50, 100],
				queryParams: getQueryParams,
				onLoadSuccess: function() {},
				columns: [{
					width: 30,
					align: 'center',
					formatter: function(val, row, index) {
						return (pageOptions.number - 1) * pageOptions.size + index + 1
					}
				},{
					field: 'sid',visible:false,
				}, {
					field: 'name',
				}, {
					field: 'scoreGrade',
				}, {
					field: 'score',
				}]

			}

			/**
			 * 数据表格初始化
			 */
			$('#qamsScoreTable').bootstrapTable(tableConfig)

			/**
			 * 搜索表单初始化
			 */
			$$.searchInit($('#searchForm'), $('#qamsScoreTable'))

			/**
			 * 新建评分按钮点击事件
			 */
			$('#addQamsScore').on('click', function() {
				var qid=$("#qID").val();
				if(qid!="--"){
				/**
				 * 添加会计主体类别表单验证初始化
				 */
				var form = document.addQamsScoreForm;
				
				$(form).validator('destroy');
				util.form.validator.init($(form));

				util.form.reset($('#addQamsScoreForm'));
			
				$("#selectQue").val(qid);
				$('#addQamsScoreModal').modal('show');
				}else{
					alert("没有问卷信息！");
				}
			
			})
			


			


			
			http.post(qamsconfig.api.qamsQue.optGroup, function(datas) {
				if (datas.length > 0) {
					var select = $(document.searchForm.que);
					select.empty();
					datas.forEach(function(item) {
						var optgroup = 
							$('<option value="' + item.value + '">' + item.text + '</option>');
						optgroup.appendTo(select);
					});
				/*	initStat.account = true;
					console.log(inited())
					if (inited()) {
						select.trigger('change');
					}*/
					if($("#qID").val()!="--"){
						http.post(qamsconfig.api.qamsQue.getQueById, {
							data: {
								sid: $("#qID").val()
							},contentType: 'form'
						}, function(result) {
							if (result.errorCode == 0) {
								var data = result;
								$("#onum").val(data.number);
								$("#omis").val(data.minScore);
								$("#omas").val(data.maxScore)
							//	$("#queScore").html(data.minScore+"~"+data.maxScore);
							//	$("#num").html(data.number);
								$("#selectQue").val(this.value);
								$('#qamsScoreTable').bootstrapTable('refresh')
							} else {
								alert(查询失败);
							}
						})
						}
				}
			});
			http.post(qamsconfig.api.qamsQue.optGroup, function(datas) {
				if (datas.length > 0) {
					var select = $(document.addQamsScoreForm.que);
					select.empty();
					datas.forEach(function(item) {
						var optgroup = 
							$('<option value="' + item.value + '">' + item.text + '</option>');
						optgroup.appendTo(select);
					});
				//	initStat.account = true;
				/*	console.log(inited())
					if (inited()) {
						select.trigger('change');
					}*/
				}
			});
			$("#selectQue").on('change', function() {
				http.post(qamsconfig.api.qamsQue.getQueById, {
					data: {
						sid: this.value 
					},contentType: 'form',async: false
				}, function(result) {
					if (result.errorCode == 0) {
						var data = result;
						$("#num").val(data.number);
						$("#mis").val(data.minScore);
						$("#mas").val(data.maxScore)
					} else {
						alert(查询失败);
					}
				});
				var qid=this.value;
				if(qid!="--"){
					http.post(qamsconfig.api.qamsScore.getScores, {
						data: {
							qid: qid
						},
						contentType: 'form'
					}, function(result) {
							var divhtml="";
							if(result.length>0){
								var divhtml="";
								for(var i=0;i<result.length;i++){
									var data=result[i];
									divhtml+='<div class="row">'+
												'<div class="col-sm-6">'+
													'<div class="form-group">'+
														'<label>'+data.scoreGrade+'</label>'+
														'<div class="input-group input-group-sm">'+
															'<span class="input-group-addon">从</span>'+
															'<input name="gradeScores['+i+'].minScore" type="text" class="form-control" placeholder="最小分数" maxlength="'+($("#mas").val().toString().length || 3)+'" required="required" data-validint="'+$("#mis").val()+'-'+$("#mas").val()+'" data-error="分数在'+$("#mis").val()+'-'+$("#mas").val()+'之间" value="'+data.minScore+'">'+
															'<span class="input-group-addon">分</span>'+
														'</div>'+
														'<div class="help-block with-errors text-red"></div>'+
													'</div>'+
												'</div>'+
												'<div class="col-sm-6">'+
													'<div class="form-group">'+
														'<label>&nbsp;</label>'+
														'<div class="input-group input-group-sm">'+
															'<span class="input-group-addon">到</span>'+
															'<input name="gradeScores['+i+'].maxScore" type="text" class="form-control" placeholder="最大分数" maxlength="'+($("#mas").val().toString().length || 3)+'" required="required" data-validint="'+$("#mis").val()+'-'+$("#mas").val()+'" data-error="分数在'+$("#mis").val()+'-'+$("#mas").val()+'之间" value="'+data.maxScore+'">'+
															'<span class="input-group-addon">分</span>'+
														'</div>'+
														'<div class="help-block with-errors text-red"></div>'+
													'</div>'+
												'</div>'+
												'<input type="hidden" name="gradeScores['+i+'].scoreGrade" value="'+data.scoreGrade+'" />'+
											'</div>'
//									divhtml+='<div class="row" style="margin:5px"><lable style="width:40px;float: left" >'+data.scoreGrade+'</lable><input type="hidden" name="gradeScores['+i+'].scoreGrade" value="'+data.scoreGrade+'" /><input name="gradeScores['+i+'].minScore" type="text" class="form-control input-sm"  placeholder="最小分数" style="width:80px;float: left" value='+data.minScore+'><label style="width:40px;float: left;text-align: center" >~</label><input name="gradeScores['+i+'].maxScore" type="text" class="form-control input-sm" style="width:80px;float: left" placeholder="最大分数" value='+data.maxScore+'></div>';
								}
								$("#score").html(divhtml);
								$$.requiredInit($("#score"))
								var form = document.addQamsScoreForm;
								$(form).validator('destroy');
								util.form.validator.init($(form));
							}else{
								http.post(qamsconfig.api.qamsQue.getQueById, {
									data: {
										sid: qid
									},
									contentType: 'form'
								}, function(result) {
									if (result.errorCode == 0) {
										var data = result;
										var type=data.type;
										 var types = new Array();
										types=extconfig.QATYPE;
										var divhtml1="";
										for(var i=0;i<types.length;i++){
											var t=types[i];
											if(type==t.ID){
												 var scores = new Array();
												scores=t.SCORE;
												for(var j=0;j<scores.length;j++){
													var score=scores[j];
													divhtml1+='<div class="row">'+
																	'<div class="col-sm-6">'+
																		'<div class="form-group">'+
																			'<label>'+score.NAME+'</label>'+
																			'<div class="input-group input-group-sm">'+
																				'<span class="input-group-addon">从</span>'+
																				'<input name="gradeScores['+j+'].minScore" type="text" class="form-control" placeholder="最小分数" maxlength="'+($("#mas").val().toString().length || 3)+'" required="required" data-validint="'+$("#mis").val()+'-'+$("#mas").val()+'" data-error="分数在'+$("#mis").val()+'-'+$("#mas").val()+'之间">'+
																				'<span class="input-group-addon">分</span>'+
																			'</div>'+
																			'<div class="help-block with-errors text-red"></div>'+
																		'</div>'+
																	'</div>'+
																	'<div class="col-sm-6">'+
																		'<div class="form-group">'+
																			'<label>&nbsp;</label>'+
																			'<div class="input-group input-group-sm">'+
																				'<span class="input-group-addon">到</span>'+
																				'<input name="gradeScores['+j+'].maxScore" type="text" class="form-control" placeholder="最大分数" maxlength="'+($("#mas").val().toString().length || 3)+'" required="required" data-validint="'+$("#mis").val()+'-'+$("#mas").val()+'" data-error="分数在'+$("#mis").val()+'-'+$("#mas").val()+'之间">'+
																				'<span class="input-group-addon">分</span>'+
																			'</div>'+
																			'<div class="help-block with-errors text-red"></div>'+
																		'</div>'+
																	'</div>'+
																	'<input type="hidden" name="gradeScores['+j+'].scoreGrade" value="'+score.ID+'" />'+
																'</div>'
//													divhtml1+='<div class="row" style="margin:5px"><lable style="width:40px;float: left" >'+score.NAME+'</lable><input type="hidden" name="gradeScores['+j+'].scoreGrade" value="'+score.ID+'" /><input name="gradeScores['+j+'].minScore" type="text" class="form-control input-sm"  placeholder="最小分数" style="width:80px;float: left" ><label style="width:40px;float: left;text-align: center" >~</label><input name="gradeScores['+j+'].maxScore" type="text" class="form-control input-sm" style="width:80px;float: left" placeholder="最大分数"></div>';
												}
											}
										}
										$("#score").html(divhtml1);
										$$.requiredInit($("#score"))
										var form = document.addQamsScoreForm;
										$(form).validator('destroy');
										util.form.validator.init($(form));
									} else {
										alert(查询失败);
									}
								})
							}
					});
					}else{
						$("#score").html("");
					}
				
			});
			$("#qID").on('change', function() {
				if($("#qID").val()!="--"){
				http.post(qamsconfig.api.qamsQue.getQueById, {
					data: {
						sid: this.value 
					},contentType: 'form'
				}, function(result) {
					if (result.errorCode == 0) {
						var data = result;
						$("#onum").html(data.number);
						$("#omis").html(data.minScore);
						$("#omas").html(data.maxScore)
					//	$("#queScore").html(data.minScore+"~"+data.maxScore);
					//	$("#num").html(data.number);
						$("#selectQue").val(this.value);
					} else {
						alert(查询失败);
					}
				})
				}
			});
			/**
			 * 新建评分“保存”按钮点击事件
			 */
			$('#addQamsScoreSubmit').on('click', function() {
				
//				var obj=$("input[name$='minScore']");
//				var flag;
//				$.each(obj, function(){  
//					if($(this).val()==""){
//						flag=false;
//					}
//				});  
//				obj=$("input[name$='maxScore']");
//				$.each(obj, function(){  
//					if($(this).val()==""){
//						flag=false;
//					}
//				});  
//				if(flag==false){
					if (!$('#addQamsScoreForm').validator('doSubmitCheck')) return;
//					return;
//				}else{
				$('#addQamsScoreForm').ajaxSubmit({
					url: qamsconfig.api.qamsScore.save,
					success: function(addResult) {
						if (addResult.errorCode == 0) {
							util.form.reset($('#addQamsScoreForm'))
							$('#addQamsScoreModal').modal('hide')
							$('#qamsScoreTable').bootstrapTable('refresh')
						} else {
							alert(addResult.errorMessage)
						}
					}
				});
//				}
			});
			

		}
	} 

})