define([
  'http',
  'operate.config',
  'config',
  'util',
  'extension'
], function (http, operateConfig, config, util, $$) {
  return {
    name: 'menu',
    init: function () {

      // 缓存全部角色信息
      var roles = []
      http.post(operateConfig.api.role.list, {
        data: {
          system: config.system,
        },
        contentType: 'form'
      }, function (res) {
        roles = res.rows
        var updateButtonRoles = $(document.updateButtonForm.roles)
        var updateFormRoles = $(document.updateForm.roles)
        roles.forEach(function (item) {
          updateButtonRoles.append('<option value="' + item.oid + '">' + item.name + '</option>')
          updateFormRoles.append('<option value="' + item.oid + '">' + item.name + '</option>')
        })
        updateButtonRoles.select2()
        updateFormRoles.select2()
      })

      var menus = []
      var menuObj = {}
      getMenuList(http, operateConfig, config, function (result) {
        menus = result
        menuObj = treeToObj({}, menus)
        $$.treetableInit(menus, $('#menuTable'), getTreeTableColumns(util, $$, menus, menuObj))
      })

      $('#updateButtonTable').bootstrapTable({
        columns: [
          {
            field: 'text'
          },
          {
            field: 'position',
            formatter: function (val) {
              return val === 'table' ? '表格内部' : '独立按钮'
            }
          },
          {
            field: 'enable',
            formatter: function (val) {
              return val === 'YES' ? '是' : '否'
            }
          },
          {
            width: 100,
            align: 'center',
            formatter: function (val, row) {
            	console.log(row)
              return '<div class="func-area">' +
              '<a href="javascript:void(0)" class="item-update">编辑</a>' +
              '</div>'
            },
            events: {
              'click .item-update': function (e, value, row) {
              	console.log(row);
                var form = document.updateButtonForm
                util.form.reset($(form))
                $$.formAutoFix($(form), row)
                $(form.roles).val(row.roles).trigger('change')
                $(form).validator('destroy')
                util.form.validator.init($(form))
                $('#updateButtonModal').modal('show')
              }
            }
          }
        ]
      })

      $('#updateSubmit').on('click', function () {
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
        tds.each(function (index, item) {
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
                nodeObj.buttons.forEach(function (item) {
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

      $('#updateButtonSubmit').on('click', function () {
        var table = $('#updateButtonTable')
        var form = document.updateButtonForm

        if (!$(form).validator('doSubmitCheck')) return

        var data = table.bootstrapTable('getData')
        data.forEach(function (item) {
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

      $('#menuUpload').on('click', function () {
        http.post(operateConfig.api.menu.save + '?system=' + config.system, {
          data: JSON.stringify(menus)
        }, function (result) {
          getMenuList(http, operateConfig, config)
          $('.text-red').removeClass('text-red')
          $('.text-green').removeClass('text-green')
          $('#menuUpload').hide()
        })
      })

    }
  }
})

function getMenuList (http, operateConfig, config, callback) {
  http.post(operateConfig.api.menu.load,{
    data: {
      system: config.system
    },
    contentType: 'form'
  }, function (result) {
    if (callback) {
      callback(result)
    }
  })
}

function getTreeTableColumns (util, $$, menus, menuObj) {
  return [
    {
      field: 'text'
    },
    {
      field: 'buttons',
      formatter: function (val) {
        var rlt = ''
        if (val) {
          val.forEach(function (item) {
            rlt += item.text
            rlt += ','
          })
          if (rlt) {
            rlt = rlt.substring(0, rlt.length - 1)
          }
        }
        return rlt
      }
    },
    {
      width: 150,
      align: 'center',
      formatter: function (val, row) {
        return '<div class="func-area">' +
                 '<a href="javascript:void(0)" class="item-update">编辑</a>' +
               '</div>'
      },
      events: {
        'click .item-update': function (e, value, row) {
          var form = document.updateForm
          util.form.reset($(form))
          $$.formAutoFix($(form), row)
          $(form.roles).val(row.roles).trigger('change')
          $(form).validator('destroy')
          util.form.validator.init($(form))
          $('#updateButtonTable').bootstrapTable('load', row.buttons || [])
          $('#updateModal').modal('show')
        }
      }
    }
  ]
}

function treeToObj (obj, tree) {
  tree.forEach(function (item) {
    obj[item.id] = item
    if (item.children && item.children.length > 0) {
      $.extend(obj, treeToObj({}, item.children))
    }
  })
  return obj
}

function flatten (flattenObj, tree, level) {
  level = level || 0
  tree.forEach(function (item) {
    flattenObj.push({
      id: item.id,
      text: item.text,
      level: level
    })
    if (item.children && item.children.length > 0) {
      flattenObj = flattenObj.concat(flatten([], item.children, level + 1))
    }
  })
  return flattenObj
}
