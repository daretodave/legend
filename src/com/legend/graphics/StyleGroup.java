package com.legend.graphics;

import java.util.EnumSet;

import com.legend.event.ChangeEvent;
import com.legend.metrics.Hook;


//add missing
public class StyleGroup extends Style {
	
	@Override
	protected void interpet(String key, String value) {
		for(Layer layer : layers) {
			layer.getStyle().interpet(key, value);
		}
	}

	@Override
	public Style setRawFont(Object font) {
		for(Layer layer : layers) {
			layer.getStyle().setRawFont(font);
		}
		return this;
	}

	@Override
	public Style setFont(String font) {
		for(Layer layer : layers) {
			layer.getStyle().setFont(font);
		}
		return this;
	}

	@Override
	public int getComplimentary() {
		throw new IllegalStateException("This operation can not be completed with a group style.");
	}

	@Override
	public Object getFont() {
		throw new IllegalStateException("This operation can not be completed with a group style.");
	}

	@Override
	public SimpleGradient getSimpleGradient() {
		throw new IllegalStateException("This operation can not be completed with a group style.");
	}

	@Override
	public Mouse getMouse() {
		throw new IllegalStateException("This operation can not be completed with a group style.");
	}

	@Override
	public Style setMouse(Mouse mouse) {
		for(Layer layer : layers) {
			layer.getStyle().setMouse(mouse);
		}
		return this;
	}

	@Override
	public Style setAlign(Hook align) {
		for(Layer layer : layers) {
			layer.getStyle().setAlign(align);
		}
		return this;
	}

	@Override
	public Style setVerticalAlign(Hook verticalAlign) {
		for(Layer layer : layers) {
			layer.getStyle().setVerticalAlign(verticalAlign);
		}
		return this;
	}

	@Override
	public Style setTextIsBounded(boolean textIsBounded) {
		for(Layer layer : layers) {
			layer.getStyle().setTextIsBounded(textIsBounded);
		}
		return this;
	}

	@Override
	public Hook getAlign() {
		throw new IllegalStateException("This operation can not be completed with a group style.");
	}

	@Override
	public boolean isTextIsBounded() {
		throw new IllegalStateException("This operation can not be completed with a group style.");
	}

	@Override
	public Hook getVerticalAlign() {
		throw new IllegalStateException("This operation can not be completed with a group style.");
	}

	@Override
	public int getTextColor() {
		throw new IllegalStateException("This operation can not be completed with a group style.");
	}

	@Override
	public Style setTextColor(int textColor) {
		for(Layer layer : layers) {
			layer.getStyle().setTextColor(textColor);
		}
		return this;
	}

	@Override
	public boolean isWordWrap() {
		throw new IllegalStateException("This operation can not be completed with a group style.");
	}

	@Override
	public Style setWordWrap(boolean wordWrap) {
		for(Layer layer : layers) {
			layer.getStyle().setWordWrap(wordWrap);
		}
		return this;
	}

	@Override
	public float getFontSize() {
		throw new IllegalStateException("This operation can not be completed with a group style.");
	}

	@Override
	public Style setFontSize(float fontSize) {
		for(Layer layer : layers) {
			layer.getStyle().setFontSize(fontSize);
		}
		return this;
	}

	@Override
	public Style setColor(int index, int color) {
		for(Layer layer : layers) {
			layer.getStyle().setColor(index, color);
		}
		return this;
	}
	
	public Style setSimpleGradient(SimpleGradient simpleGradient) {
		for(Layer layer : layers) {
			layer.getStyle().setSimpleGradient(simpleGradient);
		}
		return this;
	}

	@Override
	public int getColor(int index) {
		throw new IllegalStateException("This operation can not be completed with a group style.");
	}

	@Override
	public Style setColor(int color) {
		for(Layer layer : layers) {
			layer.getStyle().setColor(color);
		}
		return this;
	}

	@Override
	public int getColor() {
		throw new IllegalStateException("This operation can not be completed with a group style.");
	}

	@Override
	public Style setBorderColor(int color) {
		for(Layer layer : layers) {
			layer.getStyle().setBorderColor(color);
		}
		return this;
	}

	@Override
	public int getBorderColor() {
		throw new IllegalStateException("This operation can not be completed with a group style.");
	}

	@Override
	public Style setStroke(float stroke) {
		for(Layer layer : layers) {
			layer.getStyle().setStroke(stroke);
		}
		return this;
	}

	@Override
	public float getStroke() {
		throw new IllegalStateException("This operation can not be completed with a group style.");
	}

	@Override
	public String getText() {
		throw new IllegalStateException("This operation can not be completed with a group style.");
	}

	@Override
	public Style setText(String text) {
		for(Layer layer : layers) {
			layer.getStyle().setText(text);
		}
		return this;
	}

	@Override
	public EnumSet<Method> getMethod() {
		throw new IllegalStateException("This operation can not be completed with a group style.");
	}

	@Override
	public Style setMethod(Method... method) {
		for(Layer layer : layers) {
			layer.getStyle().setMethod(method);
		}
		return this;
	}

	@Override
	public Object getTexture() {
		throw new IllegalStateException("This operation can not be completed with a group style.");
	}

	@Override
	public Style setTexture(String texture) {
		for(Layer layer : layers) {
			layer.getStyle().setTexture(texture);
		}
		return this;
	}

	@Override
	public Style setRawTexture(Object texture) {
		for(Layer layer : layers) {
			layer.getStyle().setRawTexture(texture);
		}
		return this;
	}

	@Override
	public Style copy(ChangeEvent changeEvent) {
		throw new IllegalStateException("This operation can not be completed with a group style.");
	}

	private Layer[] layers;
	
	public StyleGroup(Layer[] layers) {
		this.layers = layers;
	}

}
