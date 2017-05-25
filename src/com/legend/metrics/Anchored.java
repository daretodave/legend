package com.legend.metrics;

import com.legend.AbstractSurface;
import com.legend.model.Element;

public class Anchored extends Value {
	
	public Anchored(Hook ahook, Hook bhook, Element alpha, Element beta, Value offset, Construct construct) {
		this.ahook = ahook;
		this.bhook = bhook;
		this.alpha = alpha;
		this.beta = beta;
		this.offset = offset;
		this.construct = construct;
	}

	private Hook ahook;
	private Hook bhook;
	private Element alpha;
	private Element beta;
	private Value offset;
	private Construct construct;
	
	@Override
	protected float compute() {
		float resolve = 0F;
		switch(construct) {
		case X:
			resolve = beta.getX();
			if(beta instanceof AbstractSurface) {
				resolve += bhook.point(((AbstractSurface) beta).getCanvasWidth());
			} else {
				resolve += bhook.point(beta.getWidth());
			}
			resolve -= ahook.point(alpha.getWidth());
			break;
		case Y:
			resolve = beta.getY();
			if(beta instanceof AbstractSurface) {
				resolve += bhook.point(((AbstractSurface) beta).getCanvasHeight());
			} else {
				resolve += bhook.point(beta.getHeight());
			}
			resolve -= ahook.point(alpha.getHeight());
			break;
		}
		return resolve + offset.getValue();
	}

}
