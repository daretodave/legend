package com.legend.components;

import com.legend.model.Element;

public class Button extends Element {
	
	public Button(String text) {
		super();
		setText(text);
	}

	@Override
	public String getDefaultStyle() {
		return "styles/button";
	} 
	
	public String getText() {
		return getLayer().getStyle().getText();
	}
	
	public void setText(String text) {
		getLayers().getStyle().setText(text);
	}

}
