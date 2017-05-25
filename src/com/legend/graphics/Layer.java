package com.legend.graphics;

import com.legend.Cache;
import com.legend.event.ChangeEvent;

public class Layer {
	
	private Style style;
	private Template template;
	
	private ChangeEvent changeEvent;
	
	public Layer() {
		this.style    = new Style();
		this.template = Cache.grab("templates/rect", Template.class);
	}
	
	public Layer(String style, String template) {
		this.style    = Cache.grab(style, Style.class).copy(changeEvent);
		this.template = Cache.grab(template, Template.class);
	}

	public void setOnChangeListener(ChangeEvent changeEvent) {
		this.changeEvent = changeEvent;
	}
	
	public Style getStyle() {
		return style;
	}
	
	public Template getTemplate() {
		return template;
	}
	
	public void setTemplate(String template) {
		this.template = Cache.grab(template, Template.class);
		if(changeEvent != null) {
			changeEvent.onChange("template_change");
		}
	}
	
	public void setStyle(String style) {
		this.style = Cache.grab(style, Style.class).copy(changeEvent);
		if(changeEvent != null) {
			changeEvent.onChange("style_change");
		}
	}

	public Layer copy() {
		return copy(changeEvent);
	}
	
	public Layer copy(ChangeEvent listener) {
		Layer layer = new Layer();
		layer.style = style.copy(listener);
		layer.template = template;
		return layer;
	}
	
}
