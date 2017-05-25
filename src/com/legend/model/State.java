package com.legend.model;

public class State {
	
	private int state;
	private OnStateChangeListener listener;
	
	public State(OnStateChangeListener listener) {
		this.listener = listener;
	}
	
	public interface OnStateChangeListener {
		public void onStateChange(int state, boolean instated);
	}
	
	public static final int STANDARD = 0;
	public static final int HOVER    = 2;
	public static final int PRESSED  = 8;
	public static final int FOCUSED  = 32;
	public static final int CHECKED  = 128;
	
	public void to(int state) {
		if(state == State.STANDARD) {
			if(this.state  != 0) {
				this.state = State.STANDARD;
				onChange(State.STANDARD, true);
			}
		} else {
			if(!is(state)) {
				this.state |= state;
				onChange(state, true);
			}
		}
	}
	
	public void remove(int state) {
		if(state == State.STANDARD) {
			return;
		}
		if(is(state)) {
			this.state &= ~state;
			onChange(state, false);
		}
	}
	
	public int asInt() {
		return this.state;
	}
	
	public boolean is(int state) {
		return (this.state & state) == state;
	}
	
	private void onChange(int state, boolean instated) {
		if(listener != null) {
			listener.onStateChange(state, instated);
		}
	}

}
