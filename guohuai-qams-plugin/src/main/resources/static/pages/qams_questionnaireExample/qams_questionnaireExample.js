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
		name: 'qams_questionnaireExample',
		init: function() {
			// js逻辑写在这里

			//页面ID
			var pageState = {
				sid: util.nav.getHashObj(location.hash).id || ''
			};
			
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
					http.post(qamsconfig.api.qamsQue.qamsQueRespByid,{
						data: {
							sid: pageState.sid
						},
						contentType: 'form'
					}, function(json) {
						var viewdata = {
							sid: json.sid,
							type:formatQueType(json.type),
							number: json.number,
							minScore: json.minScore,
							maxScore: json.maxScore
						};
						$$.formAutoFix($('#qamsQueDetail'), viewdata);
					});
					//根据问卷ID查询问题答案详情
					http.post(qamsconfig.api.qamsQue.qamsQueResultByid,{
						data: {
							sid: pageState.sid
						},
						contentType: 'form'
					}, function(rlt) {
						var data = rlt.list;
						var t = doT.template($('#dotTemplate').text());
						$("#dataSource").html(t(data));
					});
					}
				}).change();
				
			});
			$(document.searchForm.queName).change();
			
			//提交按钮单击事件
			$('#answerQueSave').off().on('click', function() {
				$('#answerQueSave').attr('disabled', true);
				var data = util.form.serializeJson($('#dataSource'))
				var json = {sid: pageState.sid, questions:[]};
				var questions = {};
				
				$.each(data, function(key) {
					if ($('#' + key).hasClass('qams_options')) {
						if (!questions[key]) {
							questions[key] = []
						}
						questions[key].push(data[key]);
					}
				});
				
				$.each(questions, function(key) {
					var q = {
						qid : key,
						answers: questions[key]
					}
					json.questions.push(q);
				});
				//保存
				http.post(qamsconfig.api.qamsAnswerQue.saveAnswerQue,{
					data: JSON.stringify(json)
				},function(rlt) {
					var viewdata = {
						score: rlt.score,
						grade: rlt.grade
					};
					$('#answerQueSave').attr('disabled', false);
					$$.formAutoFix($('#qamsAnswerQueDetail'), viewdata);
					$('#submitModal').modal('show');
				});
			});
			
			//确定按钮
			$('#confirmBtn').off().on('click', function() {
				$('#submitModal').modal('hide');
				$("#dataSource").resetForm();
			});
			
			/**
			 * 格式化问卷类型
			 */
			function formatQueType(val) {
				var types = extconfig.QATYPE;
				$.each(types,function(i,v){
					if (val == v.ID) {
						val = v.NAME;
					}
				});
				return val;
			};
		}
	}
})