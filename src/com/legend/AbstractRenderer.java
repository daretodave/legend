package com.legend;



public abstract class AbstractRenderer {

	public abstract AbstractDisplay build(float width, float height);

	public abstract void render(AbstractDisplay display);
	
	public abstract void translate(float x, float y);
	
}
