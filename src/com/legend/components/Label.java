package com.legend.components;

import com.legend.metrics.Value;
import com.legend.model.Element;
import com.legend.model.SizeCallback;

public class Label extends Element {
	
	private Value.Static textWidth;
	private Value.Static textHeight;
	
	public Label(String text) {
		super();
		setText(text);
		textWidth  = new Value.Static(0F);
		textHeight = new Value.Static(0F);
		setWidth(textWidth);
		setHeight(textHeight);
	}
	
	public String getText() {
		return getLayer().getStyle().getText();
	}
	
	public void setText(String text) {
		getLayers().getStyle().setText(text);
	}

	@Override
	public String getDefaultStyle() {
		return "styles/label";
	}

	public Value.Static getTextWidth() {
		return textWidth;
	}

	public Value.Static getTextHeight() {
		return textHeight;
	}
	
	@Override
	public void postCallback(SizeCallback callback) {
		textWidth.setValue(callback.getWidth());
		textHeight.setValue(callback.getHeight());
	}
	
}
