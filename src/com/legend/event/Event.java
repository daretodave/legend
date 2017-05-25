package com.legend.event;

public abstract class Event {
	
	public static final int RELEASE = 0;
	public static final int PRESS   = 1;
	public static final int DRAG    = 2;
	public static final int HOVER   = 3;
	public static final int EXIT    = 4;
	
	public static final int TOUCH = 0;
	public static final int INPUT = 1;
	
	public static final int PRIMARY = 1;
	public static final int MIDDLE  = 2;
	public static final int RIGHT   = 3;
	
	private final int type;
	private final int method;
	private final EventModifiers modifiers;
	
	public Event(int type, int method, EventModifiers modifiers) {
		this.type = type;
		this.method = method;
		this.modifiers = modifiers;
	}
	
	public int getType() {
		return type;
	}
	
	public int getMethod() {
		return method;
	}

	public EventModifiers getModifiers() {
		return modifiers;
	}


}
