/**
 * 会计科目
 */
define([
	'http',
	'qams.config',
	'config',
	'util',
	'extension'
], function(http, accountconfig, config, util, $$) {
	return {
		name: 'QamsScore',
		init: function() {
			// js逻辑写在这里
			
			var selectParentOid = ""
				
			/**
			 * 数据表格分页、搜索条件配置
			 */
			var pageOptions = {
				number: 1,
				size: 10,
				name: '',
				type: ''
			}
			
		}
	}
})