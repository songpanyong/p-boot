define(['config'], function(config) {
			  this.host = '';
			  return {
			    host: this.host,
			    /**
				 * api 接口提供与服务器异步交互地址
				 * 
				 */
			    api: {
			    	qamsScore: { //评分
						save: this.host + '/' + config.webprefix + '/qams/score/save',
						search: this.host + '/' + config.webprefix + '/qams/score/search',
						update: this.host + '/' + config.webprefix + '/qams/score/update',
						detail: this.host + '/' + config.webprefix + '/qams/score/detail',
						deleteScore: this.host + '/' + config.webprefix + '/qams/score/delete',
						getEnableAcctObjCates: this.host + '/' + config.webprefix + '/qams/score/getEnableAcctObjCates',
						getScores:this.host + '/' + config.webprefix + '/qams/score/getScores' 
					},

					qamsQue: { // 
						optGroup: this.host + '/' + config.webprefix + '/qams/que/options/group',
						addQamsQue: this.host + '/' + config.webprefix + '/qams/que/addQamsQue',
						list: this.host + '/' + config.webprefix + '/qams/que/QamsQueList',
						detail: this.host + '/' + config.webprefix + '/qams/que/detail',
						edit: this.host + '/' + config.webprefix + '/qams/que/edit',
						search: this.host + '/' + config.webprefix + '/qams/que/search',
						update: this.host + '/' + config.webprefix + '/qams/que/update',
						invalid: this.host + '/' + config.webprefix + '/qams/que/invalid',
						getAllNameList: this.host + '/' + config.webprefix + '/qams/que/getAllNameList',
						getAllName: this.host + '/' + config.webprefix + '/qams/que/getAllName',
						qamsQueResult: this.host + '/' + config.webprefix + '/qams/que/qamsQueResult',
						qamsQueResultByid:this.host + '/' + config.webprefix + '/qams/que/qamsQueResultByid',
						qamsQueRespByid:this.host + '/' + config.webprefix + '/qams/que/qamsQueRespByid',
						getQueById: this.host + '/' + config.webprefix + '/qams/que/getQueById'
					},
					qamsQuestion: { // 
						add: this.host + '/' + config.webprefix + '/qams/question/add',
						getQuesById:this.host + '/' + config.webprefix + '/qams/question/getQuesById',
						getAnswer:this.host + '/' + config.webprefix + '/qams/answer/getAnswer',
						del: this.host + '/' + config.webprefix + '/qams/question/del',
						sort: this.host + '/' + config.webprefix + '/qams/question/sort'
					},
			  		qamsAnswerQue: {
			  			saveAnswerQue: this.host + '/' + config.webprefix + '/qams/answerQue/saveAnswerQue',//保存答卷
			  			qamsAnswerQueList: this.host + '/' + config.webprefix + '/qams/answerQue/qamsAnswerQueList',//答卷列表
			  			qamsAnswerQueDetail: this.host + '/' + config.webprefix + '/qams/answerQue/qamsAnswerQueDetail',//答卷详情
			  			answerQueDetail: this.host + '/' + config.webprefix + '/qams/answerQue/answerQueDetail'//调查详情
			  		}
			    }
			} 
})
