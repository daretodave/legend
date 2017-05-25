package com.legend.metrics;

public abstract class Value {
	
	private float resolve;
	private boolean dirty;
	
	private float min = Integer.MIN_VALUE;
	private float max = Integer.MAX_VALUE;
	
	protected abstract float compute();
	
	public float getValue() {
		if(dirty) {
			resolve = compute();
			dirty = false;
		}
		return Math.max(Math.min(resolve, max), min);
	}
	
	public static Locked locked(ValueObserver observed) {
		return new Locked(observed);
	}
	
	public static Locked locked(ValueObserver observed, float offset) {
		return new Locked(observed, offset);
	}
	
	public static Locked locked(ValueObserver observed, Value offset) {
		return new Locked(observed, offset);
	}
	
	public static Static value(float value) {
		return new Static(value);
	}

	public boolean isDirty() {
		return dirty;
	}

	public void markDirty() {
		this.dirty = true;
	}
	
	public float getMin() {
		return min;
	}

	public void setMin(float min) {
		this.min = min;
	}

	public float getMax() {
		return max;
	}

	public void setMax(float max) {
		this.max = max;
	}

	public static class Static extends Value {
		
		private float value;
		
		public Static(float value) {
			setValue(value);
		}
		
		public void setValue(float value) {
			this.value = value;
			markDirty();
		}

		@Override
		protected float compute() {
			return value;
		}
		
	}

}
