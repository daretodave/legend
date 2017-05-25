package com.legend.graphics;

public enum SimpleGradient {

	VERTICAL(0F, 0F, 1F, 0F),
	HORTIZONTAL(0F, 0F, 0F, 1F),
    DIAGONOL(0F, 0F, 1F, 1F);

	private SimpleGradient(float xratioa, float yratioa, float xratiob,
			float yratiob) {
		this.xratioa = xratioa;
		this.yratioa = yratioa;
		this.xratiob = xratiob;
		this.yratiob = yratiob;
	}

	private final float xratioa;
	private final float yratioa;
	private final float xratiob;
	private final float yratiob;

	public float xa(float x) {
		return xratioa * x;
	}

	public float ya(float y) {
		return yratioa * y;
	}

	public float xb(float x) {
		return xratiob * x;
	}

	public float yb(float y) {
		return yratiob * y;
	}

}