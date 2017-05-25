package com.legend.model;

import java.util.LinkedList;

import com.legend.AbstractRenderer;
import com.legend.metrics.ValueObserver;

public class Platform extends Element {
	
	private LinkedList<Element> children = new LinkedList<Element>();

	public void attach(Element entity) {
		synchronized(getChildren()) {
			getChildren().add(entity);
			update();
		}
	}
	
	/**
	 * Only Grid System
	 */
	//private int rows;
	//private int collumns;
    //private float blockw;
    //private float blockh;
	
	private float width;
	private float height;
	
	private boolean recalculate;
	
	public void update() { //determines rows and columns
		recalculate = true;
	}
	
	@Override
	protected void render(AbstractRenderer renderer, float w, float h) {
		super.render(renderer, w, h);
		boolean difference = (this.width != w || this.height != h);
		this.width = w;
		this.height = h;
		if(recalculate || difference) {
			recalculate();
			recalculate = false;
		}
		for(Element entity : getChildren()) {
			float x = entity.getX(); //* blockw;
			float y = entity.getY(); //* blockh;
			w = entity.getWidth();
			h = entity.getHeight();
			renderer.translate(x, y);
			entity.render(renderer, w, h);
			renderer.translate(-x, -y);
		}
	}
	
	private void recalculate() {
//		int left = Integer.MAX_VALUE, right = -1, top = Integer.MAX_VALUE, bottom = -1;
//		if(children.isEmpty()) {
//			return;
//		}
//		for(Element entity : children) {
//			int x = (int) Math.floor(entity.getX());
//			int y = (int) Math.floor(entity.getY());
//			if(x < 0 || y < 0) {
//				throw new IllegalArgumentException("Error in updating Children, a child has a posistion outside of the grid: \n"+entity);
//			}
//			int w = (int) Math.ceil(entity.getWidth());
//			int h = (int) Math.ceil(entity.getHeight());
//			left =  Math.min(x, left);
//			right = Math.max(x+w, right);
//			top = Math.min(y, top);
//			bottom = Math.max(y+h, bottom);
//		}
//		collumns = right-left;
//		rows     = bottom-top;
//		blockw = w/(float)collumns;
//		blockh = h/(float)rows;
		for(Element element : getChildren()) {
			element.clean();
		}
	}
	
	public void onValueChange(int index, ValueObserver observer, float previous,
			float current, String label) {
		update();
	}

	@Override
	public String getDefaultStyle() {
		return "styles/default";
	}

	public LinkedList<Element> getChildren() {
		return children;
	}
	
}
