package com.legend.metrics;

public enum Hook {

	START(0.0F),
	MIDDLE(0.5F),
	END(1.0F);
	
	private float ratio;

	Hook(float ratio) {
		this.ratio = ratio;
	}

	public float point(float construct) {
		return ratio * construct;
	}

}