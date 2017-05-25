package com.legend.model;

import com.legend.AbstractDisplay;
import com.legend.AbstractRenderer;
import com.legend.Cache;
import com.legend.event.ChangeEvent;
import com.legend.event.Event;
import com.legend.event.InputEvent;
import com.legend.event.InputListener;
import com.legend.event.TouchEvent;
import com.legend.event.TouchListener;
import com.legend.graphics.Layer;
import com.legend.graphics.Pallet;
import com.legend.graphics.PalletDefinition;
import com.legend.metrics.Anchored;
import com.legend.metrics.Construct;
import com.legend.metrics.Hook;
import com.legend.metrics.Value;
import com.legend.metrics.ValueObserver;
import com.legend.model.State.OnStateChangeListener;

public abstract class Element implements ValueObserver.ValueChangedListener, OnStateChangeListener, ChangeEvent {
	
	public final ValueObserver x;
	public final ValueObserver y;
	public final ValueObserver w;
	public final ValueObserver h;

	private BoxedValue margin;
	private TouchListener touchListener;
	private InputListener inputListener;
	
	private boolean dirty;
	private boolean redraw;
	
	private int index;
	
	private AbstractDisplay display;
	
	private Platform parent;
	
	private State state;
	
	private Pallet pallet;
	
	private Layer layer;
	
	private SizeCallback sizeCallback;
	
	protected final static String X = "x";
	protected final static String Y = "y";
	protected final static String W = "w";
	protected final static String H = "h";
	protected final static String MARGIN = "margin";
	
	public Element() {
		this.state = new State(this);
		this.x = new ValueObserver(X, this);
		this.y = new ValueObserver(Y, this);
		this.w = new ValueObserver(W, this);
		this.h = new ValueObserver(H, this);
		this.margin   = new BoxedValue(MARGIN,  this);
		this.dirty    = true;
		this.sizeCallback = new SizeCallback();
		w.set(1F);
		h.set(1F);
		setStyle(getDefaultStyle());
		layer = pallet.getLayer();
	}
	public Element setLocation(Object x, Object y) {
		if(x instanceof Value) {
			setX((Value) x);
		} else if(x instanceof Float) {
			setX((float) x);
		}
		if(y instanceof Value) {
			setY((Value) y);
		} else if(y instanceof Float) {
			setY((float) y);
		}
		return this;
	}
	public Element setLocation(float x, float y) {
		setX(x);
		setY(y);
		return this;
	}
	public Element setSize(Object w, Object h) {
		if(w instanceof Value) {
			setWidth((Value) w);
		} else if(w instanceof Float) {
			setWidth((float) w);
		}
		if(h instanceof Value) {
			setHeight((Value) h);
		} else if(h instanceof Float) {
			setHeight((float) h);
		}
		return this;
	}
	public Element setSize(float w, float h) {
		setWidth(w);
		setHeight(h);
		return this;
	}
	public Element bound(float x, float y, float w, float h) {
		setLocation(x, y);
		setSize(w, h);
		return this;
	}
	public Element bound(Object x, Object y, Object w, Object h) {
		setLocation(x, y);
		setSize(w, h);
		return this;
	}
	public Element anchor(Element other, Hook xhook, Hook yhook) {
		anchorX(other, xhook);
		anchorY(other, yhook);
		return this;
	}
	public Element anchorX(Hook self, Element other, Hook where) {
		setX(new Anchored(self, where, this, other, new Value.Static(0F), Construct.X));
		return this;
	}
	public Element anchorX(Element other, Hook where) {
		anchorX(where, other, where);
		return this;
	}
	public Element anchorY(Hook self, Element other, Hook where) {
		setY(new Anchored(self, where, this, other, new Value.Static(0F), Construct.Y));
		return this;
	}
	public Element anchorY(Element other, Hook where) {
		anchorY(where, other, where);
		return this;
	}
	public Element setX(float value) {
		x.set(value);
		return this;
	}
	public Element setX(Value value) {
		x.set(value);
		return this;
	}
	public Element setY(float value) {
		y.set(value);
		return this;
	}
	public Element setY(Value value) {
		y.set(value);
		return this;
	}
	public Element setWidth(float value) {
		w.set(value);
		return this;
	}
	public Element setWidth(Value value) {
		w.set(value);
		return this;
	}
	public Element setHeight(float value) {
		h.set(value);
		return this;
	}
	public Element setHeight(Value value) {
		h.set(value);
		return this;
	}
	public Element setMargin(int index, float value) {
		margin.set(index, value);
		return this;
	}
	public Element setMargin(int index, Value value) {
		margin.set(index, value);
		return this;
	}
	public Element setMargin(float left, float top, float right, float bottom) {
		margin.set(BoxedValue.LEFT, left);
		margin.set(BoxedValue.TOP, top);
		margin.set(BoxedValue.RIGHT, right);
		margin.set(BoxedValue.BOTTOM, bottom);
		return this;
	}
	public float getX() {
		return x.get();
	}
	public float getY() {
		return y.get();
	}
	public float getWidth() {
		return w.get();
	}
	public float getHeight() {
		return h.get();
	}
	public float getMargin(int index) {
		return margin.get(index);
	}
	public void update() {
		dirty = true;
		redraw = true;
	}
	public void redraw() {
		redraw = true;
	}
	public Element clean() {
		x.dirt();
		y.dirt();
		w.dirt();
		h.dirt();
		return this;
	}
	
	private boolean contains(TouchEvent event) {
		float mx = getX();
		float my = getY();
		float mw = getWidth();
		float mh = getHeight();
		float tx = event.getX();
		float ty = event.getY();
		return tx >= mx && tx <= mx+mw && ty >= my && ty <= my+mh;
	}
	
	public boolean preTouchEvent(TouchEvent event, boolean handled) {
		boolean contains = contains(event);
		switch(event.getMethod()) {
		case Event.HOVER:
			if(contains) {
				state.to(State.HOVER);
			} else {
				state.remove(State.HOVER);
			}
			break;
		case Event.PRESS:
			if(contains) {
				state.to(State.PRESSED);
				state.to(State.FOCUSED);
				internalOnTouch(event, false, handled);
				if(!handled && touchListener != null) {
					handled = touchListener.onTouch(event, false);
				}
			} else {
				state.remove(State.FOCUSED);
			}
			break;
		case Event.RELEASE:
			if(contains) {
				internalOnTouch(event, true, handled);
				if(!handled && touchListener != null) {
					handled = touchListener.onTouch(event, true);
				}
			}
			state.remove(State.PRESSED);
			break;
		case Event.EXIT:
			state.remove(State.HOVER);
			break;
		case Event.DRAG:
			break;
		}
		return handled;
	}
	
	public boolean preInputEvent(InputEvent input, boolean handled) {
		
		return false;
	}
	
	protected void internalOnTouch(TouchEvent event, boolean isClick, boolean handled) {
		
	}
	
	protected void onAttachedTo(Platform platform) {
		
	}
	
	@Override
	public void onValueChange(ValueObserver observer, float previous,
			float current, String label) {
		if(observer == w || observer == h || margin.is(observer)) {
			update();
		}
		if(parent != null) {
			parent.onValueChange(index, observer, previous, current, label);
		}
		//System.out.println("Value changed: " + label);
	}
	
	protected void render(AbstractRenderer renderer, float w, float h) {
		try {
			boolean rebuilt = false;
			if(dirty || display == null || display.differs(w, h)) {
				display = renderer.build(w, h);
				dirty = false;
				rebuilt = true;
				clean();
			}
			if(redraw || rebuilt) {
				redraw = false;
				display.clear();
				layer = pallet.getLayer();
				display.render(layer, sizeCallback);
				postCallback(sizeCallback);
			}
			renderer.render(display);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void postCallback(SizeCallback callback) {
		
	}
	public Layer getLayer(int layer) {
		return pallet.getLayer(layer);
	}
	public Layer getLayers() {
		return pallet.getAllLayers();
	}
	public Layer getLayers(int...layers) {
		return pallet.getLayer(layers);
	}
	public int getIndex() {
		return index;
	}
	protected void setIndex(int index) {
		this.index = index;
	}
	
	public State getState() {
		return state;
	}

	static {
		Cache.load("fonts");
		Cache.load("templates");
		Cache.load("styles");
	}

	@Override
	public void onStateChange(int state, boolean instated) {
		redraw();
	}
	@Override
	public void onChange(String reason) {
		update();
	}
	public void setStyle(String style) {
		this.pallet = Cache.grab(style, PalletDefinition.class).toPallet(this, state);
	}
	
	public abstract String getDefaultStyle();
	public TouchListener getTouchListener() {
		return touchListener;
	}
	public void setTouchListener(TouchListener touchListener) {
		this.touchListener = touchListener;
	}
	public InputListener getInputListener() {
		return inputListener;
	}
	public void setInputListener(InputListener inputListener) {
		this.inputListener = inputListener;
	}
	public Layer getLayer() {
		return layer;
	}
	

}
