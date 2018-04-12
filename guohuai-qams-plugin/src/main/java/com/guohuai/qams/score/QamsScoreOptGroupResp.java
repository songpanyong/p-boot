package com.guohuai.qams.score;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class QamsScoreOptGroupResp implements Serializable {

	private static final long serialVersionUID = -8960231470945562617L;

	public QamsScoreOptGroupResp(String label) {
		super();
		this.label = label;
	}

	private String label;
	private List<Options> options = new ArrayList<Options>();

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Options implements Serializable {

		private static final long serialVersionUID = -4783961683173998840L;

		private String text;
		private String value;
	}

}
