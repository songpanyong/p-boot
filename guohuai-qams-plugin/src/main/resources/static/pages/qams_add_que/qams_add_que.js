/**
 * 新增调查问题
 */
define([
	'http',
	'qams.config',
	'config',
	'util',
	'extension'
], function(http, qamsconfig, config, util, $$) {
	return {
		name: 'qams_add_que',
		init: function() {
			/**
			 * 答案初始化
			 */
			//$('#answerList').bootstrapTable(tableConfig)
			var pageState = {
					oid: util.nav.getHashObj(location.hash).sid || ''
				}
			/**
			 * “保存”按钮点击事件
			 */
			$('#saveQamsQueSubmit').on('click', function() {
				$("#oid").val(pageState.oid);
				$('#addQamsQueForm').ajaxSubmit({
					url: qamsconfig.api.qamsQuestion.add,     
					success: function(addResult) {
						if (addResult.errorCode == 0) {
						//	$('#addQamsQueForm').reset();
							$("#addQamsQueForm").resetForm();
						//	$("#addQamsQueForm").reset();
						//	$("#addQamsQueForm").resetForm();
						//	this.reset();//有效，其他无效
						} else {
							alert(addResult.errorMessage)
						}
					}
				})
			})


			/**
			 * 提交按钮点击事件
			 */
			$('#addQamsQueSubmit').on('click', function() {
				$("#oid").val(pageState.oid);
				$('#addQamsQueForm').ajaxSubmit({
					url: qamsconfig.api.qamsQuestion.add,     
					success: function(addResult) {
						if (addResult.errorCode == 0) {
							var sid=pageState.oid;
							util.nav.dispatch('qams_QueDetail', 'sid=' + sid)
						} else {
							alert(addResult.errorMessage)
						}
					}
				})
				
			});
			$('.wx-form-group >button').on('click', function() {
				   $(this).parent().remove();
				
			});
			var answers="";
			var character = new Array("A","B","C","D");
			for(var num=0;num<character.length;num++){
				answers+="<li  class='wx-form-group'><input type='text' name='answers["+num+"].sn' style='width:40px;margin:5px' value='"+character[num]+"'/><input type='text' class='wx-form-control' name='answers["+num+"].content' id='artName' style='width:300px;margin:5px' >分数<input type='text' class='wx-form-control' name='answers["+num+"].score' style='margin:5px'><button  type='button' class='btn btn-default'  >删除</button></li>";
			}
			$(".vote-add-btn").before(answers);
			$(".vote-add-btn").click(function() {
			    var num = $("#answerId > li").length;
			    $(".vote-add-btn").before("<li  class='wx-form-group'><input type='text' name='answers["+num+"].sn' style='width:40px;margin:5px'><input type='text' class='wx-form-control' name='answers["+num+"].content' id='artName' style='width:300px;margin:5px'>分数<input type='text' class='wx-form-control' name='answers["+num+"].score' style='margin:5px'><button  type='button' class='btn btn-default'  >删除</button></li>");
			    $(".wx-form-group > button").click(function(){
			        $(this).parent().remove(); 
			    });
			});
			
			 $(".wx-form-group > button").click(function(){
			        $(this).parent().remove();
			    });

		}
	}
})