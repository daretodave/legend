package com.legend.model;

import com.legend.metrics.Value;
import com.legend.metrics.ValueObserver;

public class BoxedValue {
	
	private ValueObserver[] box;
	
	public static final int LEFT   = 0;
	public static final int TOP    = 1;
	public static final int RIGHT  = 2;
	public static final int BOTTOM = 3;
	
	public BoxedValue(String label, ValueObserver.ValueChangedListener listener) {
		box = new ValueObserver[4];
		box[LEFT]   = new ValueObserver("left_"  +label, listener);
		box[TOP]    = new ValueObserver("top_"   +label, listener);
		box[RIGHT]  = new ValueObserver("right_" +label, listener);
		box[BOTTOM] = new ValueObserver("bottom_"+label, listener);
	}
	
	public boolean is(ValueObserver observer) {
		for(ValueObserver compared : box) {
			if(observer.equals(compared)) {
				return true;
			}
		}
		return false;
	}
	
	public void set(int index, float value) {
		box[index].set(value);
	}
	
	public void set(int index, Value value) {
		box[index].set(value);
	}
	
	public float get(int index) {
		return box[index].get();
	}

}
