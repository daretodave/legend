package com.legend.event;

public class InputEvent extends Event {
	
	private final Button button;
	private final int fallback;

	public InputEvent(int type, int method, EventModifiers modifiers, Button button, int fallback) {
		super(type, method, modifiers);
		this.button = button;
		this.fallback = fallback;
	}

	public Button getButton() {
		return button;
	}

	public int getFallback() {
		return fallback;
	}

}
