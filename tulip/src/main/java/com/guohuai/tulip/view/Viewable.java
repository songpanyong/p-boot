package com.guohuai.tulip.view;

import java.io.Serializable;

public abstract interface Viewable extends Serializable {

	public Response showView();

	public Response showView(boolean init);
	
}
