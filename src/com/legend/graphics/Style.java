package com.legend.graphics;

import java.util.EnumSet;
import java.util.HashMap;

import com.legend.Cache;
import com.legend.event.ChangeEvent;
import com.legend.metrics.Hook;

public class Style {
	
	public static final int PRIMARY = 0;
	
	public static final int LEFT   = 0;
	public static final int TOP    = 1;
	public static final int RIGHT  = 2;
	public static final int BOTTOM = 3;
	
	public enum Method {
		FILL,
		STROKE,
		TEXT,
		TEXTURE
	}
	public enum Mouse {
		HAND,
		CROSSHAIRS,
		NORMAL
	}

	private float stroke;
	private int[] pallet;
	private int border;
	private int complimentary;
	private String text;
	private Object texture;
	private Object font;
	private Mouse mouse;
	private EnumSet<Method> method;
	private ChangeEvent changeEvent;
	private SimpleGradient simpleGradient;
	private Hook align;
	private Hook verticalAlign;
	private boolean textIsBounded;
	private int textColor;
	private boolean wordWrap;
	private float fontSize;
	
	protected void interpet(String key, String value) {
		Interpreter interpreter = interpreters.get(key.toLowerCase());
		if(interpreter == null) {
			throw new RuntimeException("Can not find interpreter for key:"+key);
		}
		interpreter.act(this, value);
	}
	
	public Style() {
		method = EnumSet.of(Method.FILL);
		pallet = new int[16];
		stroke = 2F;
		border = Color.BLACK;
		simpleGradient = SimpleGradient.HORTIZONTAL;
		mouse = Mouse.NORMAL;
		align = Hook.START;
		verticalAlign = Hook.START;
		wordWrap = true;
		textIsBounded = true;
		textColor = Color.BLACK;
		fontSize = 12F;
		font = Cache.grab("fonts/Roboto-Light");
	}
	
	public static Style derive(String data) {
		Style style = new Style();
		return style;
	}
	
	public Style setColor(int index, int color) {
		boolean change = pallet[index] != color;
		pallet[index] = color;
		if(index == PRIMARY) {
			complimentary = Color.brighter(color, .9F);
		}
		if(change) {
			fireChangeListener();
		}
		return this;
	}
	public int getColor(int index) {
		return pallet[index];
	}
	public Style setColor(int color) {
		return setColor(PRIMARY, color);
	}
	public int getColor() {
		return pallet[PRIMARY];
	}
	public Style setBorderColor(int color) {
		boolean change = this.border != color;
		border = color;
		if(change) {
			fireChangeListener();
		}
		return this;
	}
	public int getBorderColor() {
		return border;
	}
	public Style setStroke(float stroke) {
		boolean change = this.stroke != stroke;
		this.stroke = stroke;
		if(change) {
			fireChangeListener();
		}
		return this;
	}
	public float getStroke() {
		return stroke;
	}

	public String getText() {
		return text;
	}

	public Style setText(String text) {
		boolean change = this.text != text;
		this.text = text;
		if(change) {
			fireChangeListener();
		}
		return this;
	}

	public EnumSet<Method> getMethod() {
		return method;
	}
	public Style setMethod(Method... method) {
		if(method.length == 1) {
			this.method = EnumSet.of(method[0]);
		} else {
			this.method = EnumSet.of(method[0], method);
		}
		fireChangeListener();
		return this;
	}
	public Object getTexture() {
		return texture;
	}
	public Style setRawFont(Object font) {
		boolean change = this.font != font;
		this.font = font;
		if(change) {
			fireChangeListener();
		}
		return this;
	}
	public Style setFont(String font) {
		setRawFont(Cache.grab(font));
		return this;
	}
	public Style setTexture(String texture) {
		setRawTexture(Cache.grab(texture));
		return this;
	}
	public Style setRawTexture(Object texture) {
		boolean change = this.texture != texture;
		this.texture = texture;
		if(change) {
			fireChangeListener();
		}
		return this;
	}
	private void fireChangeListener() {
		if(changeEvent != null) {
			changeEvent.onChange("style");
		}
	}

	public int getComplimentary() {
		return complimentary;
	}
	
	public Object getFont() {
		return font;
	}

	public SimpleGradient getSimpleGradient() {
		return simpleGradient;
	}

	public Style setSimpleGradient(SimpleGradient simpleGradient) {
		this.simpleGradient = simpleGradient;
		return this;
	}
	
	
	private static void interpreter(Interpreter interpreter, String...keys) {
		for(String key : keys) {
			interpreters.put(key, interpreter);
		}
	}
	
	public Mouse getMouse() {
		return mouse;
	}

	public Style setMouse(Mouse mouse) {
		boolean change = this.mouse != mouse;
		this.mouse = mouse;
		if(change) {
			fireChangeListener();
		}
		return this;
	}
	
	public Style setAlign(Hook align) {
		boolean change = this.align != align;
		this.align = align;
		if(change) {
			fireChangeListener();
		}
		return this;
	}
	
	public Style setVerticalAlign(Hook verticalAlign) {
		boolean change = this.verticalAlign != verticalAlign;
		this.verticalAlign = verticalAlign;
		if(change) {
			fireChangeListener();
		}
		return this;
	}
	
	public Style setTextIsBounded(boolean textIsBounded) {
		boolean change = this.textIsBounded != textIsBounded;
		this.textIsBounded = textIsBounded;
		if(change) {
			fireChangeListener();
		}
		return this;
	}

	public Hook getAlign() {
		return align;
	}



	public boolean isTextIsBounded() {
		return textIsBounded;
	}

	public Hook getVerticalAlign() {
		return verticalAlign;
	}

	private static HashMap<String, Interpreter> interpreters = new HashMap<String, Interpreter>();
	
	public interface Interpreter {
		public void act(Style style, String raw);
	}
	
	public Style copy(ChangeEvent changeEvent) {
		Style style = new Style();
		style.changeEvent = changeEvent;
		style.stroke = stroke;
		for(int i = 0; i < style.pallet.length; i++) {
			style.pallet[i] = pallet[i];
		}
		style.border = border;
		style.text = text;
		style.texture = texture;
		style.method = method;
		style.complimentary = complimentary;
		style.mouse = mouse;
		style.align = align;
		style.verticalAlign = verticalAlign;
		style.textIsBounded = textIsBounded;
		style.textColor = textColor;
		style.font = font;
		style.wordWrap = wordWrap;
		style.fontSize = fontSize;
		return style;
	}

	public int getTextColor() {
		return textColor;
	}

	public Style setTextColor(int textColor) {
		boolean change = this.textColor != textColor;
		this.textColor = textColor;
		if(change) {
			fireChangeListener();
		}
		return this;
	}

	public boolean isWordWrap() {
		return wordWrap;
	}

	public Style setWordWrap(boolean wordWrap) {
		boolean change = this.wordWrap != wordWrap;
		this.wordWrap = wordWrap;
		if(change) {
			fireChangeListener();
		}
		return this;
	}

	public float getFontSize() {
		return fontSize;
	}

	public Style setFontSize(float fontSize) {
		boolean change = this.fontSize != fontSize;
		this.fontSize = fontSize;
		if(change) {
			fireChangeListener();
		}
		return this;
	}

	static {
		interpreter(new Interpreter() {
			@Override
			public void act(Style style, String raw) {
				style.setColor(Color.decode(raw));
			}
		}, "color", "background", "bg", "primary");
		interpreter(new Interpreter() {
			@Override
			public void act(Style style, String raw) {
				style.setTextColor(Color.decode(raw));
			}
		}, "text");
		interpreter(new Interpreter() {
			@Override
			public void act(Style style, String raw) {
				style.setBorderColor(Color.decode(raw));
			}
		}, "border");
		interpreter(new Interpreter() {
			@Override
			public void act(Style style, String raw) {
				float value = Float.parseFloat(raw);
				style.setStroke(value);
			}
		}, "stroke");
		interpreter(new Interpreter() {
			@Override
			public void act(Style style, String raw) {
				float value = Float.parseFloat(raw);
				style.setFontSize(value);
			}
		}, "text_size", "size");
		interpreter(new Interpreter() {
			@Override
			public void act(Style style, String raw) {
				style.setText(raw);
			}
		}, "content");
		interpreter(new Interpreter() {
			@Override
			public void act(Style style, String raw) {
				style.setTexture(raw);
			}
		}, "texture", "image");
		interpreter(new Interpreter() {
			@Override 
			public void act(Style style, String raw) {
				String[] blocks  = raw.split("\\|");
				Method[] methods = new Method[blocks.length];
				for(int i = 0; i < blocks.length; i++) {
					String resolve = blocks[i].toUpperCase().trim();
					Method method  = Method.valueOf(resolve);
					methods[i] = method;
				}
				style.setMethod(methods);
			}
		}, "method", "style");
		interpreter(new Interpreter() {
			@Override
			public void act(Style style, String raw) {
				style.setSimpleGradient(SimpleGradient.valueOf(raw.toUpperCase().trim()));
			}
		}, "sg", "simple_gradient");
		interpreter(new Interpreter() {
			@Override
			public void act(Style style, String raw) {
				style.setMouse(Mouse.valueOf(raw.toUpperCase().trim()));
			}
		}, "mouse");
		interpreter(new Interpreter() {
			@Override
			public void act(Style style, String raw) {
				style.setAlign(Hook.valueOf(raw.toUpperCase().trim()));
			}
		}, "align");
		interpreter(new Interpreter() {
			@Override
			public void act(Style style, String raw) {
				style.setVerticalAlign(Hook.valueOf(raw.toUpperCase().trim()));
			}
		}, "valign");
		interpreter(new Interpreter() {
			@Override
			public void act(Style style, String raw) {
				style.setTextIsBounded(Boolean.parseBoolean(raw));
			}
		}, "text_bounded", "bounded");
		interpreter(new Interpreter() {
			@Override
			public void act(Style style, String raw) {
				style.setWordWrap(Boolean.parseBoolean(raw));
			}
		}, "word_wrap", "wrap");
	}
	
}
