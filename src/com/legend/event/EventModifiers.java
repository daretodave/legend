package com.legend.event;

public class EventModifiers {
	
	public boolean isControl() {
		return control;
	}
	public boolean isAlt() {
		return alt;
	}
	public boolean isShift() {
		return shift;
	}
	
	public EventModifiers(boolean control, boolean alt, boolean shift) {
		this.control = control;
		this.alt = alt;
		this.shift = shift;
	}
	
	private final boolean control;
	private final boolean alt;
	private final boolean shift;

}
