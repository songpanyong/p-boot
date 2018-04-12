define([
	'http',
	'operate.config',
	'config',
	'util',
	'extension'
], function(http, operateConfig, config, util, $$) {
	return {
		name: 'menucfg',
		init: function() {

			// 缓存全部角色信息
			var roles = []
			var rolesMap = {}
			http.post(operateConfig.api.role.list, {
				data: {
					system: config.system,
				},
				contentType: 'form'
			}, function(res) {
				roles = res.rows
				var addButtonRoles = $(document.addButtonForm.roles)
				var updateButtonRoles = $(document.updateButtonForm.roles)
				var addFormRoles = $(document.addForm.roles)
				var updateFormRoles = $(document.updateForm.roles)
				roles.forEach(function(item) {
					addButtonRoles.append('<option value="' + item.oid + '">' + item.name + '</option>')
					updateButtonRoles.append('<option value="' + item.oid + '">' + item.name + '</option>')
					addFormRoles.append('<option value="' + item.oid + '">' + item.name + '</option>')
					updateFormRoles.append('<option value="' + item.oid + '">' + item.name + '</option>')
					rolesMap[item.oid] = item.name
				})
				addButtonRoles.select2()
				updateButtonRoles.select2()
				addFormRoles.select2()
				updateFormRoles.select2()
			})

			var menus = []
			var menuObj = {}
			getMenuListCfg(http, operateConfig, config, function(result) {
				menus = result
				menuObj = treeToObjCfg({}, menus)
				$$.treetableInit(menus, $('#menuTable'), getTreeTableColumnsCfg(util, $$, menus, menuObj))
			})

			$('#addButtonTable').bootstrapTable({
				columns: [{
					field: 'text'
				}, {
					field: 'position',
					formatter: function(val) {
						return val === 'table' ? '表格内部' : '独立按钮'
					}
				}, {
					field: 'enable',
					formatter: function(val) {
						return val === 'YES' ? '是' : '否'
					}
				}, {
					field: 'role',
					formatter: function(val) {
						return val ? val.substr(0, 10) : '--'
					}
				}, {
					width: 100,
					align: 'center',
					formatter: function() {
						return '<div class="func-area">' +
							'<a href="javascript:void(0)" class="item-update">编辑</a>' +
							'<a href="javascript:void(0)" class="item-delete">删除</a>' +
							'</div>'
					},
					events: {
						'click .item-update': function(e, value, row) {
							var form = document.updateButtonForm
							buttonUpdateSign = 'add'
							util.form.reset($(form))
							$$.formAutoFix($(form), row)
							$(form.position).trigger('change')
							$(form.roles).val(row.roles).trigger('change')
							$(form).validator('destroy')
							util.form.validator.init($(form))
							$('#updateButtonModal').modal('show')
						},
						'click .item-delete': function(e, value, row) {
							$$.confirm({
								container: $('#deleteConfirm'),
								trigger: this,
								accept: function() {
									var data = $('#addButtonTable').bootstrapTable('getData')
									data.splice(data.indexOf(row), 1)
									$('#addButtonTable').bootstrapTable('load', data)
								}
							})
						}
					}
				}]
			})
			$('#updateButtonTable').bootstrapTable({
				columns: [{
					field: 'text'
				}, {
					field: 'position',
					formatter: function(val) {
						return val === 'table' ? '表格内部' : '独立按钮'
					}
				}, {
					field: 'enable',
					formatter: function(val) {
						return val === 'YES' ? '是' : '否'
					}
				}, {
					field: 'roles',
					formatter: function(val) {
						if (val && val.length > 0) {
							var rolesName = []
							$.each(val, function(index, role) {
								if (rolesMap[role]) {
									rolesName.push(rolesMap[role])
								}
							})
							if (rolesName.length > 0) {
								return rolesName.join()
							} else {
								return '--'
							}
						}
						return '--'
					}
				}, {
					width: 100,
					align: 'center',
					formatter: function() {
						return '<div class="func-area">' +
							'<a href="javascript:void(0)" class="item-update">编辑</a>' +
							'<a href="javascript:void(0)" class="item-delete">删除</a>' +
							'</div>'
					},
					events: {
						'click .item-update': function(e, value, row) {
							var form = document.updateButtonForm
							buttonUpdateSign = 'update'
							util.form.reset($(form))
							$$.formAutoFix($(form), row)
							$(form.position).trigger('change')
							$(form.roles).val(row.roles).trigger('change')
							$(form).validator('destroy')
							util.form.validator.init($(form))
							$('#updateButtonModal').modal('show')
						},
						'click .item-delete': function(e, value, row) {
							$$.confirm({
								container: $('#deleteConfirm'),
								trigger: this,
								accept: function() {
									var data = $('#updateButtonTable').bootstrapTable('getData')
									data.splice(data.indexOf(row), 1)
									$('#updateButtonTable').bootstrapTable('load', data)
								}
							})
						}
					}
				}]
			})

			util.form.validator.init($('#addForm'))

			$("#menuAdd").on('click', function() {
				$('#addModal').modal('show')
			})

			var buttonAddSign = ''
			var buttonUpdateSign = ''

			$('#addButtonAdd').on('click', function() {
				buttonAddSign = 'add'
				$('#addButtonModal').modal('show')
			})

			$('#updateButtonAdd').on('click', function() {
				buttonAddSign = 'update'
				$('#addButtonModal').modal('show')
			})

			$('#addSubmit').on('click', function() {
				var table = $('#menuTable')
				var form = document.addForm
				var modal = $('#addModal')
				var buttonTable = $('#addButtonTable')
				var buttons = buttonTable.bootstrapTable('getData')

				if (!$(form).validator('doSubmitCheck')) return

				var nodeObj = {
					id: util.getRandomString(16),
					pageId: form.pageId.value.trim(),
					text: form.text.value.trim(),
					icon: form.icon.value.trim(),
					parent: form.parent.value,
					roles: $(form.roles).val(),
					buttons: buttons
				}
				if (nodeObj.parent) {
					var parentNode = menuObj[form.parent.value]
					if (!parentNode.children) {
						parentNode.children = []
					}
					parentNode.children.push(nodeObj)
					table.treetable('loadBranch', table.treetable('node', parentNode.id), $$.treetableRowGenerator(nodeObj, getTreeTableColumnsCfg(util, $$, menus, menuObj), parentNode.id, 'add'))
				} else {
					menus.push(nodeObj)
					table.treetable('loadBranch', null, $$.treetableRowGenerator(nodeObj, getTreeTableColumnsCfg(util, $$, menus, menuObj), '', 'add'))
				}
				menuObj[nodeObj.id] = nodeObj
				util.form.reset($(form))
				modal.modal('hide')
				$('#menuUpload').show()
			})

			$('#updateSubmit').on('click', function() {
				var table = $('#menuTable')
				var form = document.updateForm
				var buttons = $('#updateButtonTable').bootstrapTable('getData')

				if (!$(form).validator('doSubmitCheck')) return

				var nodeObj = menuObj[form.id.value]
				nodeObj.text = form.text.value.trim()
				nodeObj.icon = form.icon.value.trim()
				nodeObj.pageId = form.pageId.value.trim()
				nodeObj.roles = $(form.roles).val()
				nodeObj.buttons = buttons

				var row = table.treetable('node', nodeObj.id).row
				var tds = row.find('td')
				tds.each(function(index, item) {
					item.className = 'text-green'
					switch (index) {
						case 0:
							var span = item.children[0]
							item.innerHTML = ''
							item.appendChild(span)
							item.insertAdjacentHTML('beforeend', nodeObj.text)
							break
						case 1:
							var btns = ''
							if (nodeObj.buttons.length) {
								nodeObj.buttons.forEach(function(item) {
									btns += item.text
									btns += ','
								})
								if (btns) {
									btns = btns.substring(0, btns.length - 1)
								}
							}
							item.innerHTML = btns
							break
					}
				})

				util.form.reset($(form))
				$('#updateModal').modal('hide')
				$('#menuUpload').show()
			})

			$(document.addButtonForm.position).on('change', function() {
				if (this.value === 'normal') {
					$('#addNormalButton').show().find(':input').attr('disabled', false)
					$('#addTableButton').hide().find(':input').attr('disabled', 'disabled')
				} else {
					$('#addNormalButton').hide().find(':input').attr('disabled', 'disabled')
					$('#addTableButton').show().find(':input').attr('disabled', false)
				}
				$('#addButtonForm').validator('destroy')
				util.form.validator.init($('#addButtonForm'))
			}).change()

			$(document.updateButtonForm.position).on('change', function() {
				if (this.value === 'normal') {
					$('#updateNormalButton').show().find(':input').attr('disabled', false)
					$('#updateTableButton').hide().find(':input').attr('disabled', 'disabled')
				} else {
					$('#updateNormalButton').hide().find(':input').attr('disabled', 'disabled')
					$('#updateTableButton').show().find(':input').attr('disabled', false)
				}
				$('#updateButtonForm').validator('destroy')
				util.form.validator.init($('#updateButtonForm'))
			}).change()

			$('#addButtonSubmit').on('click', function() {
				var table = null
				var form = document.addButtonForm

				if (!$(form).validator('doSubmitCheck')) return

				switch (buttonAddSign) {
					case 'add':
						table = $('#addButtonTable')
						break
					case 'update':
						table = $('#updateButtonTable')
						break
				}

				var data = table.bootstrapTable('getData')
				data.push({
					id: util.getRandomString(16),
					text: form.text.value.trim(),
					position: form.position.value,
					buttonId: form.buttonId.value.trim(),
					tableId: form.tableId.value.trim(),
					className: form.className.value.trim(),
					enable: form.enable.value,
					roles: $(form.roles).val()
				})
				util.form.reset($(form))
				table.bootstrapTable('load', data)
				$('#addButtonModal').modal('hide')
			})

			$('#updateButtonSubmit').on('click', function() {
				var table = null
				var form = document.updateButtonForm

				if (!$(form).validator('doSubmitCheck')) return

				switch (buttonUpdateSign) {
					case 'add':
						table = $('#addButtonTable')
						break
					case 'update':
						table = $('#updateButtonTable')
						break
				}

				var data = table.bootstrapTable('getData')
				data.forEach(function(item) {
					if (item.id === form.id.value) {
						item.text = form.text.value.trim()
						item.position = form.position.value
						item.buttonId = form.buttonId.value.trim()
						item.tableId = form.tableId.value.trim()
						item.className = form.className.value.trim()
						item.enable = form.enable.value
						item.roles = $(form.roles).val()
					}
				})
				util.form.reset($(form))
				table.bootstrapTable('load', data)
				$('#updateButtonModal').modal('hide')
			})

			$('#menuUpload').on('click', function() {
				http.post(operateConfig.api.menu.save + '?system=' + config.system, {
					data: JSON.stringify(menus)
				}, function(result) {
					getMenuListCfg(http, operateConfig, config)
					$('.text-red').removeClass('text-red')
					$('.text-green').removeClass('text-green')
					$('#menuUpload').hide()
				})
			})

		}
	}
})

function getMenuListCfg(http, operateConfig, config, callback) {
	http.post(operateConfig.api.menu.load, {
		data: {
			system: config.system
		},
		contentType: 'form'
	}, function(result) {
		if (callback) {
			callback(result)
		}

		var addParent = $(document.addForm.parent).empty()
		var updateParent = $(document.updateForm.parent).empty()

		addParent.append('<option value="">请选择</option>')
		updateParent.append('<option value="">请选择</option>')

		flattenCfg([], result).map(function(item) {
			var option = '<option value="' + item.id + '">'
			for (var i = 0; i < item.level; i += 1) {
				option += '&nbsp;&nbsp;&nbsp;&nbsp;'
			}
			option += item.text + '</option>'
			addParent.append(option)
			updateParent.append(option)
		})
		addParent.select2({
			// minimumResultsForSearch: Infinity,
			templateSelection: function(data) {
				return data.text.trim()
			}
		})
		updateParent.select2({
			// minimumResultsForSearch: Infinity,
			templateSelection: function(data) {
				return data.text.trim()
			}
		})
	})
}

function getTreeTableColumnsCfg(util, $$, menus, menuObj) {
	return [{
		field: 'text'
	}, {
		field: 'buttons',
		formatter: function(val) {
			var rlt = ''
			if (val) {
				val.forEach(function(item) {
					rlt += item.text
					rlt += ','
				})
				if (rlt) {
					rlt = rlt.substring(0, rlt.length - 1)
				}
			}
			return rlt
		}
	}, {
		width: 150,
		align: 'center',
		formatter: function(val, row) {
			return '<div class="func-area">' +
				'<a href="javascript:void(0)" class="item-update">编辑</a>' +
				'<a href="javascript:void(0)" class="item-delete">删除</a>' +
				'</div>'
		},
		events: {
			'click .item-update': function(e, value, row) {
				var form = document.updateForm
				util.form.reset($(form))
				$$.formAutoFix($(form), row)
				$(form.roles).val(row.roles).trigger('change')
				$(form).validator('destroy')
				util.form.validator.init($(form))
				$('#updateButtonTable').bootstrapTable('load', row.buttons || [])
				$('#updateModal').modal('show')
			},
			'click .item-delete': function(e, value, row) {
				$$.confirm({
					container: $('#deleteConfirm'),
					trigger: this,
					accept: function() {
						console.log(row)
						
						var deleteMenu = function(menus, row) {
							for (var i = 0; i < menus.length; i++) {
								var menu = menus[i];
								if (menu.id == row.id) {
									menus.splice(i, 1);
									return;
								} else {
									if (menu.children && menu.children.length > 0) {
										deleteMenu(menu.children, row)
									}
								}
							}
						}
						deleteMenu(menus, row)
						// menus.splice(menus.indexOf(row), 1)
						delete menuObj[row.id]
						$('#menuTable').treetable('removeNode', row.id)
						$('#menuUpload').show()
						console.log(menus)
					}
				})
			}
		}
	}]
}

function treeToObjCfg(obj, tree) {
	tree.forEach(function(item) {
		obj[item.id] = item
		if (item.children && item.children.length > 0) {
			$.extend(obj, treeToObjCfg({}, item.children))
		}
	})
	return obj
}

function flattenCfg(flattenObj, tree, level) {
	level = level || 0
	tree.forEach(function(item) {
		flattenObj.push({
			id: item.id,
			text: item.text,
			level: level
		})
		if (item.children && item.children.length > 0) {
			flattenObj = flattenObj.concat(flattenCfg([], item.children, level + 1))
		}
	})
	return flattenObj
}