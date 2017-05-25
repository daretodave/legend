package com.legend.metrics;


public class Locked extends Value {
	
	private ValueObserver observed;
	private Value offset;
	
	public Locked(ValueObserver observed, Value offset) {
		this.observed = observed;
		this.offset = offset;
	}
	
	public Locked(ValueObserver observed, float offset) {
		this(observed, new Value.Static(offset));
	}
	
	public Locked(ValueObserver observed) {
		this(observed, 0F);
	}

	@Override
	protected float compute() {
		return observed.get() + offset.getValue();
	}

}
