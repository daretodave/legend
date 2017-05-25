package com.legend.metrics;

public class ValueObserver {
	
	private Value value;
	private String label;
	private float last;
	private ValueChangedListener listener;
	private float offset;
	
	public interface ValueChangedListener {
		
		public void onValueChange(ValueObserver observer, float previous, float current, String label);
		
	}
	
	public ValueObserver(String label, ValueChangedListener listener) {
		this.label = label;
		this.value = new Value.Static(0F);
		this.listener = listener;
	}
	
	public void set(float value) {
		if(this.value instanceof Value.Static) {
			((Value.Static)this.value).setValue(value);
		} else {
			this.value = new Value.Static(value);
		}
	}
	
	public void set(Value value) {
		this.value = value;
	}
	
	public float get() {
		float resolve = value.getValue()+offset;
		if(last != resolve) {
			//update
			if(listener != null) {
				listener.onValueChange(this, last, resolve, label);
			}
			last = resolve;
		}
		return resolve;
	}
	
	public String toString() {
		return label + ":" + value.getValue();
	}

	public void dirt() {
		value.markDirty();
	}

	public float getOffset() {
		return offset;
	}

	public void setOffset(float offset) {
		this.offset = offset;
	}

}
