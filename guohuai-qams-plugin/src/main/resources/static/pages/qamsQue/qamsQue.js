
/**
 * 新增问卷
 */
define([
	'http',
	'qams.config',
	'config',
	'qams.ext.config',
	'util',
	'extension'
], function(http, qamsconfig, config,extconfig, util, $$) {
	return {
		name: 'qamsQue',
		init: function() {
			 var types = new Array();
			types=extconfig.QATYPE;
			for(var i=0;i<types.length;i++){
				var t=types[i];
			      var option = "<option value='" + t.ID + "'>" + t.NAME + "</option>";
                  $("#bdType").append(option);
			}
			/**
			 * 数据表格分页、搜索条件配置
			 */
			var pageOptions = {
				number: 1,
				size: 10,
				que: '',
				sid:''
			}
			
			/**
			 * 表格querystring扩展函数，会在表格每次数据加载时触发，用于自定义querystring
			 * @param {Object} val
			 */
			function getQueryParams(val) {
				var form = document.searchForm
				pageOptions.size = val.limit
				pageOptions.number = parseInt(val.offset / val.limit) + 1
				//pageOptions.sn = form.que.value.trim()
				return val
			}

			/**
			 * 数据表格配置
			 */
			var tableConfig = {
				ajax: function(origin) {
					http.post(qamsconfig.api.qamsQue.list, {
							data: {
								page: pageOptions.number,
								rows: pageOptions.size,
								sn: pageOptions.que,
								status: pageOptions.status
							},
							contentType: 'form'
						},function(rlt) {
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
				onLoadSuccess: function(field, value, row, $element) {
				  	switch (field) {
	        		//case 'name':toDetail(value,row)
			  	}
			},
				columns: [{
					width: 30,
					align: 'center',
					formatter: function(val, row, index) {
						return (pageOptions.number - 1) * pageOptions.size + index + 1
					}
				}, {
					field: 'name',
				}, {
					field: 'type',
					formatter: function(val) {
						 var types = new Array();
							types=extconfig.QATYPE;
							for(var i=0;i<types.length;i++){
								var t=types[i];
								if (t.ID == val){
									return t.NAME
								}
							}
					}
				},{
					field: 'number',
					formatter: function(val, row) {
						if (val == null || val == undefined) {
							return 0;
						}else{
							return val;
						}
					}
				},{
					field: 'score',
					formatter: function(val, row, index) {
								return row.minScore+'-'+row.maxScore;
					}
				}, {
					width: 256,
					align: 'center',
					formatter: function(val, row) {
						var buttons = [{
							text: '问卷详情',
							type: 'button',
							class: 'item-detail',
							isRender: true
						}, {
							text: '修改',
							type: 'button',
							class: 'item-eidt',
							isRender:true
						}, {
							text: '删除',
							type: 'button',
							class: 'item-invalid',
							isRender:true
						}];
						return util.table.formatter.generateButton(buttons, 'qamsQueTable');
					},
					events: {
						'click .item-detail': function(e, value, row) {
								var sid=row.sid;
								util.nav.dispatch('qams_QueDetail', 'sid=' + sid)
						},
						'click .item-invalid': function(e, value, row) {
							$("#confirmTitle").html("确定删除该问卷吗？")
							$$.confirm({
								container: $('#doConfirm'),
								trigger: this,
								accept: function() {
									http.post(qamsconfig.api.qamsQue.invalid, {
										data: {
											sid: row.sid
										},
										contentType: 'form',
									}, function(result) {
										$('#qamsQueTable').bootstrapTable('refresh')
									})
								}
							})
						},
						'click .item-eidt': function(e, value, row) {
							http.post(qamsconfig.api.qamsQue.detail, {
								data: {
									oid: row.sid
								},
								contentType: 'form',
							}, function(result) {	
								util.form.reset($('#editQamsQueForm')); // 先清理表单
								console.log(result);
								$$.formAutoFix($('#editQamsQueForm'), result);// 自动填充表单
								$("#editQamsQueForm").validator('destroy');// 重置表单验证
								util.form.validator.init($("#editQamsQueForm"));// 初始化表单验证
								 var types = new Array();
									types=extconfig.QATYPE;
									for(var i=0;i<types.length;i++){
										var t=types[i];
										if (t.ID = result.type){
											  var option = "<option value='" + t.ID + "'>" + t.NAME + "</option>";
											$('#editType').append(option);
										}
									}
								$('#editQamsQueModal').modal('show');	
							})
						}
					}
				}]

			}

			/**
			 * 数据表格初始化
			 */
			$('#qamsQueTable').bootstrapTable(tableConfig)
			/**
			 * 搜索表单初始化
			 */
			$$.searchInit($('#searchForm'), $('#QamsQueTable'))
			
			/**
			 * 修改问卷
			 */
			$('#editQamsQueSubmit').on('click', function() {
				if(!$('#editQamsQueForm').validator('doSubmitCheck')) return;
				$('#editQamsQueForm').ajaxSubmit({
					url: qamsconfig.api.qamsQue.edit,
					success: function(result) {
						util.form.reset($('#editQamsQueForm')); // 先清理表单
						$('#editQamsQueModal').modal('hide');
						$('#qamsQueTable').bootstrapTable('refresh');
					}
				})
			})

			/**
			 * 新增问卷
			 */
			$('#addQamsQue').on('click', function() {
				$('#cnameDiv1').removeClass("has-error")
				$('#cnameErr1').html('')
				util.form.reset($('#addQamsQueForm')); // 先清理表单
				util.form.validator.init($("#addQamsQueForm"));
				$('#addQamsQueModal').modal('show')
			})
			/**
			 * 新建“保存”按钮点击事件
			 */
			$('#addQamsQueSubmit').on('click', function() {
				if(!$('#addQamsQueForm').validator('doSubmitCheck')) return;
				$('#addQamsQueForm').ajaxSubmit({
					url: qamsconfig.api.qamsQue.addQamsQue,
					success: function(addResult) {
						if (addResult.errorCode == 0) {
							util.form.reset($('#addQamsQueForm'))
							$('#addQamsQueModal').modal('hide')
							$('#qamsQueTable').bootstrapTable('refresh')
						} else {
							$('#cnameErr1').html(addResult.errorMessage)
							$('#cnameDiv1').addClass("has-error")
						}
					}
				})
			})
		}
	}

})