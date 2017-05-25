package com.legend.graphics;

public class Template {
	
	public static final String DEFAULT = "rect";

	private final int size;
	
	private float[] x;
	private float[] y;
	
	public Template(float... xy) {
		size = xy.length/2;
		x = new float[size];
		y = new float[size];
		for(int i = 0, o = 0; i < xy.length; i += 2, o++) {
			x[o] = xy[i];
			y[o] = xy[i + 1];
		}
	}
	
	public float getX(int index, float width) {
		return x[index] * width;
	}
	
	public float getY(int index, float height) {
		return y[index] * height;
	}
	
	public int getSize() {
		return size;
	}
	
	public static Template derive(String data) {
		String[] division = data.split(",");
		float[] resolve = new float[division.length];
		for(int i = 0; i < resolve.length; i++) {
			resolve[i] = new Float(division[i].trim());
		}
		return new Template(resolve);
	}

}
