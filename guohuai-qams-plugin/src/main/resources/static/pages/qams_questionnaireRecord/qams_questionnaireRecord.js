/**
 * 调查记录
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
		name: 'qams_questionnaireRecord',
		init: function() {
			// js逻辑写在这里
			var selectParentOid = "";
			//页面ID
			var pageState = {
				sid: util.nav.getHashObj(location.hash).id || ''
			};
			//第一次加载
			var firstLoad = true;
			/**
			 * 数据表格分页、搜索条件配置
			 */
			var pageOptions = {
				page: 1,
				rows: 10,
				sid:'',
				name: '',
				telephone: '',
				queName: '',
				grade: '',
				startDate: '',
				endDate: '',
				minScore: '',
				maxScore: ''
			};
			
			//查询参数
			function getQueryParams(val) {
				var form = document.searchForm;
				pageOptions.sid = pageState.sid;
				pageOptions.name = form.name.value.trim();
				pageOptions.telephone = form.telephone.value.trim();
				pageOptions.queName = form.queName.value.trim();
				pageOptions.grade = form.grade.value.trim();
				pageOptions.startDate = form.startDate.value.trim();
				pageOptions.endDate = form.endDate.value.trim();
				pageOptions.minScore = form.minScore.value.trim();
				pageOptions.maxScore = form.maxScore.value.trim();
				pageOptions.rows = val.limit;
				pageOptions.page = parseInt(val.offset / val.limit) + 1;
				return val;
			};
			
			//数据表格配置
			var tableConfig = {
				ajax: function(origin) {
					http.post(qamsconfig.api.qamsAnswerQue.qamsAnswerQueList, {
						data: pageOptions,
						contentType: 'form'
					}, function(rlt) {
						origin.success(rlt)
					})
				},
				pageNumber: pageOptions.page,
				pageSize: pageOptions.rows,
				pagination: true,
				sidePagination: 'server',
				pageList: [10, 20, 30, 50, 100],
				queryParams: getQueryParams,
				onLoadSuccess: function() {},
				columns: [{
					width: 60,
					align: 'center',
					formatter: function(val, row, index) {
						return(pageOptions.page - 1) * pageOptions.rows + index + 1
					}
				}, {
					field: 'name',
					align: 'center'
				}, {
					field: 'telephone',
					align: 'center'
				}, {
					field: 'queName',
					align: 'center'
				}, {
					field: 'createTime',
					align: 'center'
				}, {
					field: 'score',
					align: 'center'
				}, {
					field: 'grade',
					align: 'center'
				}, {
					width: 280,
					align: 'center',
					formatter: function(val, row) {
						var buttons = [{
							text: '查看详情',
							type: 'button',
							class: 'item-detail',
							isRender: true
						}]
						return util.table.formatter.generateButton(buttons, 'queResultTable');
					},
					events: {
						'click .item-detail': function(e, value, row) {
							var data = {
								qsName: row.queName,
								createTime: row.createTime
							};
							$$.detailAutoFix($('#queDetail'), data); // 自动填充表单
							$('#queDetailModal').modal('show');
							http.post(qamsconfig.api.qamsAnswerQue.qamsAnswerQueDetail, {
								data: {
									sid: row.sid
								},
								contentType: 'form'
							}, function(rlt) {
								var data = rlt.list;
								var t = doT.template($('#dotTemplate').text());
								$("#dataSource").html(t(data));
							});
						}
					}
				}]
			};
			
			// 搜索表单初始化
			$$.searchInit($('#searchForm'), $('#queResultTable'));
			
			// 问卷切换列表
			http.post(qamsconfig.api.qamsQue.getAllName, function(json) {
				var qamsQueOptions = '';
				var select = document.queForm.qName;
				json.rows.forEach(function(item) {
					qamsQueOptions += '<option value="' + item.sid + '" ' + (item.sid == pageState.sid ? 'selected' : '') + '>' + item.name + '</option>'
				});
				$(select).html(qamsQueOptions);
				// 改变问卷后刷新页面
				$(document.queForm.qName).on('change', function() {
					pageState.sid = this.value;
					pageOptions.sid = this.value;
					if(firstLoad) { 
						$('#queResultTable').bootstrapTable(tableConfig);
						firstLoad = false;
					} else {
						$('#queResultTable').bootstrapTable('refresh');
					}
				}).change();
			});
			$(document.queForm.qName).change();
			$("#qId").val(pageState.sid);
		}
	}
})