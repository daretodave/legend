package com.legend.event;

public class TouchEvent extends Event {
	
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	public int getButton() {
		return button;
	}
	public int getCount() {
		return count;
	}
	
	public TouchEvent(int type, int method, EventModifiers modifiers, float x, float y, int button, int count) {
		super(type, method, modifiers);
		this.x = x;
		this.y = y;
		this.button = button;
		this.count  = count;
	}

	private final float x;
	private final float y;
	private final int button;
	private final int count;
	
	public boolean isLeft() {
		return button == Event.PRIMARY;
	}
	public boolean isRight() {
		return button == Event.RIGHT;
	}

}
