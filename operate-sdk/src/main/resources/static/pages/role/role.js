/**
 * 角色管理
 */
define([
		'http',
		'operate.config',
		'config',
		'util',
		'extension'
	],
	function(http, operateConfig, config, util, $$) {
		return {
			name: 'role',
			init: function() {
				var confirm = $('#confirmModal')
					// 缓存选取的权限
				var chosenAuths = []
					// 分页配置
				var pageOptions = {
						number: 1,
						size: 10,
						offset: 0,
						name: ''
					}
					// 权限表格配置
				var tableConfig = {
						ajax: function(origin) {
							http.post(operateConfig.api.role.list, {
								data: {
									page: pageOptions.number,
									rows: pageOptions.size,
									name: pageOptions.name,
									stats: true,
									system: config.system
								},
								contentType: 'form'
							}, function(rlt) {
								origin.success(rlt)
							})
						},
						pageSize: pageOptions.size,
						pagination: true,
						sidePagination: 'server',
						pageList: [10, 20, 30, 50, 100],
						queryParams: getQueryParams,
						columns: [{
							width: 30,
							align: 'center',
							formatter: function(val, row, index) {
								return pageOptions.offset + index + 1
							}
						}, {
							field: 'name'
						}, {
							field: 'rac'
						}, {
							field: 'arc'
						}, {
							field: 'updateTime'
						}, {
							field: 'createTime'
						}, {
							width: 180,
							align: 'center',
							formatter: function() {
								var buttons = [
									/*{
										text: '详情',
										type: 'button',
										class: 'item-detail'
									},*/
									{
										text: '修改',
										type: 'button',
										class: 'item-update'
									}, {
										text: '删除',
										type: 'button',
										class: 'item-delete'
									}
								]
								return util.table.formatter.generateButton(buttons, 'roleTable')
							},
							events: {
								'click .item-detail': function(e, val, row) {
									http.post(operateConfig.api.role.getRoleAuths, {
										data: {
											roleOid: row.oid,
											stats: false,
											system: config.system
										},
										contentType: 'form'
									}, function(result) {
										row.auths = result.rows.map(function(item) {
											return '<p>' + item.name + '</p>'
										}).join('')
										$$.detailAutoFix($('#detailModal'), row)
									})
									$('#detailModal').modal('show')
								},
								'click .item-update': function(e, val, row) {
									var form = document.updateRoleForm
										// 重置和初始化表单验证
									$(form).validator('destroy')
									util.form.validator.init($(form));
									// 权限选择组件初始化
									http.post(operateConfig.api.role.getRoleAuths, {
										data: {
											roleOid: row.oid,
											stats: false,
											system: config.system
										},
										contentType: 'form'
									}, function(result) {
										form.oid.value = row.oid
										form.name.value = row.name
										chosenAuths = result.rows
										http.post(operateConfig.api.auth.list, {
											data: {
												system: config.system
											},
											contentType: 'form'
										}, function(auths) {
											auths.rows.forEach(function(item) {
												if (chosenAuths.indexOf(item) >= 0) {
													console.log('asd')
												}
											})
											var fromArray = auths.rows.filter(function(item) {
													var hasArr = chosenAuths.filter(function(chosen) {
														return chosen.oid === item.oid
													})
													return !hasArr.length
												})
												/*
												$$.switcher({
													container: $('#updateRoleAuths'),
													fromTitle: '可选权限',
													toTitle: '已选权限',
													fromArray: fromArray,
													toArray: chosenAuths,
													field: 'name'
												})
												*/
										})
									})
									$('#updateRoleModal').modal('show')
								},
								'click .item-delete': function(e, val, row) {
									$$.confirm({
										container: $('#confirmModal'),
										trigger: this,
										accept: function() {
											http.post(operateConfig.api.role.delete, {
												data: {
													oid: row.oid
												},
												contentType: 'form'
											}, function() {
												$('#roleTable').bootstrapTable('refresh')
											})
										}
									})
								}
							}
						}]
					}
					// 初始化权限表格
				$('#roleTable').bootstrapTable(tableConfig)
					// 初始化权限表格搜索表单
				$$.searchInit($('#searchForm'), $('#roleTable'))

				// 初始化新建角色表单验证
				util.form.validator.init($('#addRoleForm'))

				// 新建角色按钮点击事件
				$('#addRole').on('click', function() {
					chosenAuths = []
						// 权限选择组件初始化
					http.post(operateConfig.api.auth.list, {
						data: {
							system: config.system
						},
						contentType: 'form'
					}, function(result) {
						/*
						$$.switcher({
							container: $('#addRoleAuths'),
							fromTitle: '可选权限',
							toTitle: '已选权限',
							fromArray: result.rows,
							toArray: chosenAuths,
							field: 'name'
						})
						*/
					})
					$('#addRoleModal').modal('show')
				})

				// 新建角色 - 确定按钮点击事件
				$('#doAddRole').on('click', function() {
					var form = document.addRoleForm
					if (!$(form).validator('doSubmitCheck')) return
					form.systemOid.value = config.system
					chosenAuths.forEach(function(item) {
						$(form).append('<input name="auths" type="hidden" value="' + item.oid + '">')
					})
					$(form).ajaxSubmit({
						url: operateConfig.api.role.save,
						success: function() {
							$(form).find('input[name=auths]').remove()
							util.form.reset($(form))
							$('#roleTable').bootstrapTable('refresh')
							$('#addRoleModal').modal('hide')
						}
					})
				})

				// 修改角色 - 确定按钮点击事件
				$('#doUpdateRole').on('click', function() {
					var form = document.updateRoleForm
					if (!$(form).validator('doSubmitCheck')) return
					form.systemOid.value = config.system
					chosenAuths.forEach(function(item) {
						$(form).append('<input name="auths" type="hidden" value="' + item.oid + '">')
					})
					$(form).ajaxSubmit({
						url: operateConfig.api.role.update,
						success: function() {
							$(form).find('input[name=auths]').remove()
							util.form.reset($(form))
							$('#roleTable').bootstrapTable('refresh')
							$('#updateRoleModal').modal('hide')
						}
					})
				})

				function getQueryParams(val) {
					// 参数 val 是bootstrap-table默认的与服务器交互的数据，包含分页、排序数据
					var form = document.searchForm
						// 分页数据赋值
					pageOptions.size = val.limit
					pageOptions.number = parseInt(val.offset / val.limit) + 1
					pageOptions.offset = val.offset
					pageOptions.name = form.name.value.trim()
					return val
				}
			}
		}
	})