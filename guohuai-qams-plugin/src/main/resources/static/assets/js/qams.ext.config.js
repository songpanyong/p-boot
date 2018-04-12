define(['config', 'qams.config'], function(config, qamsConfig) {
		return {
			'QATYPE': [
				{
					'ID': 'FXPG',
					'NAME': '投资人风险评估',
					'SCORE': [
						{
							'ID': 'R1',
							'NAME': 'R1'
						}, {
							'ID': 'R2',
							'NAME': 'R2'
						}, {
							'ID': 'R3',
							'NAME': 'R3'
						}, {
							'ID': 'R4',
							'NAME': 'R4'
						}, {
							'ID': 'R5',
							'NAME': 'R5'
						}
					]
				}
			],
				
		'TYPES': [
			{
				'ID': 'CWZK',
				'NAME': '财务状况方面'
			}, {
				'ID': 'TZZS',
				'NAME': '投资知识方面'
			}, {
				'ID': 'TZJY',
				'NAME': '投资经验方面'
			}, {
				'ID': 'TZMB',
				'NAME': '投资目标方面'
			}, {
				'ID': 'FXPH',
				'NAME': '风险偏好方面'
			}
		]
		}
})
