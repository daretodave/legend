package com.legend;

import java.util.ArrayDeque;

import com.legend.event.Button;
import com.legend.event.Event;
import com.legend.event.EventModifiers;
import com.legend.event.InputEvent;
import com.legend.event.TouchEvent;
import com.legend.metrics.ValueObserver;
import com.legend.model.Element;
import com.legend.model.Platform;

public abstract class AbstractSurface extends Platform {
	
	private ArrayDeque<TouchEvent> touch = new ArrayDeque<TouchEvent>();
	private ArrayDeque<InputEvent> input = new ArrayDeque<InputEvent>();
	
	public void onTouchMove(float x, float y, EventModifiers modifiers) {
		touch.add(new TouchEvent(Event.TOUCH, Event.HOVER, modifiers, x, y, Event.PRIMARY, 1));
	}
	
	public void onTouchExit() {
		touch.add(new TouchEvent(Event.TOUCH, Event.EXIT, null, 0F, 0F, Event.PRIMARY, 0));
	}
	
	public void onTouchPress(float x, float y, int button, int count, EventModifiers modifiers) {
		touch.add(new TouchEvent(Event.TOUCH, Event.PRESS, modifiers, x, y, button, count));
	}
	
	public void onTouchRelease(float x, float y, int button, int count, EventModifiers modifiers) {
		touch.add(new TouchEvent(Event.TOUCH, Event.RELEASE, modifiers, x, y, button, count));
	}
	
	public void onTouchDrag(float x, float y, int button, int count, EventModifiers modifiers) {
		touch.add(new TouchEvent(Event.TOUCH, Event.DRAG, modifiers, x, y, button, count));
	}
	
	public void onInputPress(EventModifiers modifiers, Button button, int fallbackKeycode) {
		input.add(new InputEvent(Event.INPUT, Event.PRESS, modifiers, button, fallbackKeycode));
	}
	
	public void onInputRelease(EventModifiers modifiers, Button button, int fallbackKeycode) {
		input.add(new InputEvent(Event.INPUT, Event.RELEASE, modifiers, button, fallbackKeycode));
	}
	
	public void input() {
		while(!touch.isEmpty()) {
			TouchEvent event = touch.poll();
			handle(this, event, false);
		}
		while(!input.isEmpty()) {
			InputEvent event = input.poll();
			handle(this, event, false);
		}
	}

	private boolean handle(Platform platform, InputEvent event, boolean handled) {
		for(Element element : platform.getChildren()) {
			if(element instanceof Platform) {
				handled = handle((Platform) element, event, handled);
			}
			handled = element.preInputEvent(event, handled);
		}
		return false;
	}

	private boolean handle(Platform platform, TouchEvent event, boolean handled) {
		for(Element element : platform.getChildren()) {
			if(element instanceof Platform) {
				handled = handle((Platform) element, event, handled);
			}
			handled = element.preTouchEvent(event, handled);
		}
		return false;
	}

	@Override
	public void onValueChange(ValueObserver observer, float previous,
			float current, String label) {
		super.onValueChange(observer, previous, current, label);
		switch(label) {
		case X:
			setWindowX((int) Math.floor(current));
			break;
		case Y:
			setWindowY((int) Math.floor(current));
			break;
		case W:
			setWindowWidth((int) Math.ceil(current));
			break;
		case H:
			setWindowHeight((int) Math.ceil(current));
			break;
		}
	}
	
	@Override
	public float getX() {
		return 0F;
	}
	@Override
	public float getY() {
		return 0F;
	}
	
	public abstract void setWindowX(int x);
	public abstract void setWindowY(int y);
	public abstract void setWindowWidth(int w);
	public abstract void setWindowHeight(int h);
	public abstract float getCanvasWidth();
	public abstract float getCanvasHeight();
	public abstract void setBackgroundColor(int color);
	
	public void render(AbstractRenderer renderer) {
		input();
		float w = getCanvasWidth();
		float h = getCanvasHeight();
		render(renderer, w, h);
	}
	
}
