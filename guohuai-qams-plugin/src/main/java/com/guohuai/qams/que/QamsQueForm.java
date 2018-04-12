
package com.guohuai.qams.que;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QamsQueForm implements Serializable {

	private static final long serialVersionUID = -7228870596117718572L;
	
	private String sid;
	private String name;
	private String type;
	private String status;

}

