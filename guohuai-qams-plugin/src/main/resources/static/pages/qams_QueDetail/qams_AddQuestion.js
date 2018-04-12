/**
 * 会计主体类别
 */
define([
	'http',
	'qams.config',
	'config',
	'util',
	'extension'
], function(http, qamsconfig, config, util, $$) {
	return {
		name: 'qamsQue',
		init: function() {
			// js逻辑写在这里
			/**
			 * 数据表格分页、搜索条件配置
			 */
			var pageOptions = {
				number: 1,
				size: 10,
				oid: '',
				status: ''
			}
			
			/**
			 * 表格querystring扩展函数，会在表格每次数据加载时触发，用于自定义querystring
			 * @param {Object} val
			 */
			function getQueryParams(val) {
				var form = document.searchForm
				pageOptions.size = val.limit
				pageOptions.number = parseInt(val.offset / val.limit) + 1
				pageOptions.oid = form.oid.value.trim()
				pageOptions.status = form.status.value.trim()
				return val
			}

			/**
			 * 数据表格配置
			 */
			var tableConfig = {
				ajax: function(origin) {
					http.post(
							qamsconfig.api.qamsQue.list, {
							data: {
								page: pageOptions.number,
								rows: pageOptions.size,
								oid: pageOptions.oid,
								status: pageOptions.status
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
					field: 'sid',
				}, {
					field: 'qamsQue.name',
				}, {
					field: 'scoreGrade',
				}, {
					field: 'score',
					formatter: function(val, row, index) {
								return row.minScore+'-'+row.maxScore;
					}
				}, {
					width: 256,
					align: 'center',
					formatter: function(val, row) {
						var buttons = [{
							text: '查看',
							type: 'button',
							class: 'item-update',
							isRender: true
						}, {
							text: '删除',
							type: 'button',
							class: 'item-invalid',
							isRender: row.canDelete===true
						}];
						return util.table.formatter.generateButton(buttons, 'acctObjCateTable');
					},
					events: {
						'click .item-update': function(e, value, row) {
							$('#updateQamsQueForm').validator('destroy')

							http.post(qamsconfig.api.QamsQue.detail, {
								data: {
									oid: row.oid
								},
								contentType: 'form'
							}, function(result) {
								if (result.errorCode == 0) {
									var data = result;
									$$.formAutoFix($('#updateQamsQueForm'), data); // 自动填充表单
									util.form.validator.init($('#updateQamsQueForm'))
									$('#updateQamsQueModal').modal('show');

								} else {
									alert(查询失败);
								}
							})
						},
						'click .item-invalid': function(e, value, row) {
							$("#confirmTitle").html("确定删除该评分准则吗？")
							$$.confirm({
								container: $('#doConfirm'),
								trigger: this,
								accept: function() {
									http.post(qamsconfig.api.QamsQue.deleteScore, {
										data: {
											oid: row.oid
										},
										contentType: 'form',
									}, function(result) {
										$('#QamsQueTable').bootstrapTable('refresh')
									})
								}
							})
						}
					}
				}]

			}

			/**
			 * 数据表格初始化
			 */
			$('#QamsQueTable').bootstrapTable(tableConfig)

			/**
			 * 搜索表单初始化
			 */
			$$.searchInit($('#searchForm'), $('#QamsQueTable'))


			/**
			 * 新建会计主体类别按钮点击事件
			 */
			$('#addQamsQue').on('click', function() {
				/**
				 * 添加会计主体类别表单验证初始化
				 */
				var form = document.addAcctObjCateForm
				
				$(form).validator('destroy')
				util.form.validator.init($(form));

				util.form.reset($('#addQamsQueForm'))
				$('#addQamsQueModal').modal('show')
			})


			/**
			 * 新建评分“保存”按钮点击事件
			 */
			$('#addQamsQueSubmit').on('click', function() {
				if (!$('#addQamsQueForm').validator('doSubmitCheck')) return
				
				$('#addQamsQueForm').ajaxSubmit({
					url: qamsconfig.api.QamsQue.save,
					success: function(addResult) {
						if (addResult.errorCode == 0) {
							util.form.reset($('#addQamsQueForm'))
							$('#addQamsQueModal').modal('hide')
							$('#QamsQueTable').bootstrapTable('refresh')
						} else {
							alert(addResult.errorMessage)
						}
					}
				})
			})


			/**
			 * 编辑评分“保存”按钮点击事件
			 */
			$('#updateQamsQueSubmit').on('click', function() {
				if (!$('#updateQamsQueForm').validator('doSubmitCheck')) return

				$('#updateQamsQueForm').ajaxSubmit({
					url: qamsconfig.api.QamsQue.update,
					success: function(addResult) {
						$('#updateQamsQueModal').modal('hide')
						$('#QamsQueTable').bootstrapTable('refresh')
					}
				})
			})
			http.post(qamsconfig.api.qamsQue.optGroup, function(datas) {
				if (datas.length > 0) {
					var select = $(document.searchForm.account);
					select.empty();
					datas.forEach(function(item) {
						var optgroup = $('<optgroup label="' + item.label + '"></optgroup>');
						item.options.forEach(function(option) {
							$('<option value="' + option.value + '">' + option.value + ' - ' + option.text + '</option>').appendTo(optgroup);
						});
						optgroup.appendTo(select);
					});
					initStat.account = true;
					console.log(inited())
					if (inited()) {
						select.trigger('change');
					}
				}
			});

		}
	}

})