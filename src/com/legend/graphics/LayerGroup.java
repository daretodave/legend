package com.legend.graphics;

import com.legend.event.ChangeEvent;

public class LayerGroup extends Layer {

	private Layer[] layers;
	private Style groupStyle;
	
	public LayerGroup(Layer... layers) {
		this.groupStyle = new StyleGroup(layers);
		this.layers = layers;
	}
	
	@Override
	public void setOnChangeListener(ChangeEvent changeEvent) {
		throw new IllegalStateException("This operation can not be completed with a group layer.");
	}

	@Override
	public Style getStyle() {
		return groupStyle;
	}

	@Override
	public Template getTemplate() {
		throw new IllegalStateException("This operation can not be completed with a group layer.");
	}

	@Override
	public void setTemplate(String template) {
		for(Layer layer : layers) {
			layer.setTemplate(template);
		}
	}

	@Override
	public void setStyle(String style) {
		for(Layer layer : layers) {
			layer.setStyle(style);
		}
	}

}
