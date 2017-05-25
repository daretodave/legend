package com.legend;

import java.util.EnumSet;

import com.legend.graphics.Layer;
import com.legend.graphics.Style;
import com.legend.graphics.Style.Method;
import com.legend.graphics.Template;
import com.legend.model.SizeCallback;

public abstract class AbstractDisplay {
	
	protected float w;
	protected float h;
	
	public AbstractDisplay(float w, float h) {
		this.w = w;
		this.h = h;
	}
	
	public boolean differs(float w, float h) {
		return this.w != w || this.h != h;
	}
	
	public abstract void clear();

	public void render(Layer layer, SizeCallback callback) {
		Style style = layer.getStyle();
		Template template = layer.getTemplate();
		EnumSet<Method> methods = style.getMethod();
		render(template, style, methods, callback);
	}

	public abstract void render(Template template, Style style, EnumSet<Method> methods, SizeCallback callback);
}
